defmodule ExTyperacerWeb.PageController do
  use ExTyperacerWeb, :controller
  alias ExTyperacer.Repo
  alias ExTyperacer.Person
  alias ExTyperacer.Logic.PersonRepo
  alias ExTyperacer.Auth.Guardian
  plug Ueberauth

  def index(conn, _params) do
    [changeset, maybe_user, message] = chack_session conn
    conn
      |> put_flash(:info, message)
      |> render("index.html", changeset: changeset, action: page_path(conn, :login), maybe_user: maybe_user)
  end

  def secret(conn, _params) do
    render(conn, "secret.html")
  end

  def racer(conn, %{"name_rom" => name_rom}) do
    [changeset, maybe_user, message] = chack_session conn
    conn
      |> put_flash(:info, message)
      |> render("racer.html", changeset: changeset, action: page_path(conn, :login), maybe_user: maybe_user, name_room: name_rom)
  end

  def racer(conn, _params) do
    changeset = PersonRepo.change_user(%Person{})
    maybe_user = Guardian.Plug.current_resource(conn)
    message = if maybe_user != nil do
      "Someone is logged in"
    else
      "Ya estas registrado ? "
    end
    conn
      |> put_flash(:info, message)
      |> render("racer.html", changeset: changeset, action: page_path(conn, :login), maybe_user: maybe_user,  name_room: nil)
  end

  def new_user(conn, params) do
    kwl = params["person"]
          |> map_to_kwl

    struct(%Person{}, kwl)
    |> PersonRepo.save_person
    |> PersonRepo.send_email_register(kwl[:password])
    redirect(conn, to: "/")
  end

  def recovery(conn, %{"token" => token, "username" => username}) do
    {user_id, view_token} =
    case Phoenix.Token.verify(ExTyperacerWeb.Endpoint, username, token, max_age: 7200) do
      {:ok, user_id} ->
        IO.puts "Validao"
        user_id = user_id
        {user_id, true}
      {:error, _} ->
        IO.puts "El token expiro"
        false
        {0, false}
    end

    conn = put_layout conn, false
    render(conn, "recovery.html", id: user_id, maybe_user: nil, token: view_token)
  end

  def restore_pass(conn, %{"id" => id, "new_password" => new_password} ) do
    IO.puts "Entro aquí"
    response = PersonRepo.find_user_by_id(String.to_integer(id["id"]))
    IO.inspect response
    case response do
      {:ok, person} ->
        res = PersonRepo.update_person person, new_password
        IO.inspect "Esta es la respuesta"
        IO.inspect res
      _ ->
        "No found"
    end
    redirect(conn, to: "/")
  end

  defp map_to_kwl(map) do
    for {k, v} <- map, do: {String.to_atom(k), v}
  end

  defp chack_session(conn) do
    changeset = PersonRepo.change_user(%Person{})
    maybe_user = Guardian.Plug.current_resource(conn)
    message = if maybe_user != nil do
      "Someone is logged in"
    else
      "Ya estas registrado ? "
    end
    [changeset, maybe_user, message]
  end
end
