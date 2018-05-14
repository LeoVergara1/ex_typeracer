defmodule ExTyperacerWeb.PageController do
  use ExTyperacerWeb, :controller
  alias ExTyperacer.Repo
  alias ExTyperacer.Person
  alias ExTyperacer.Logic.PersonRepo
  alias ExTyperacer.Auth.Guardian

  def index(conn, _params) do
    changeset = PersonRepo.change_user(%Person{})
    maybe_user = Guardian.Plug.current_resource(conn)
    message = if maybe_user != nil do
      "Someone is logged in"
    else
      "Ya estas registrado"
    end
    conn
      |> put_flash(:info, message)
      |> render("index.html", changeset: changeset, action: page_path(conn, :login), maybe_user: maybe_user)
  end

  def login(conn, %{"person" => %{"username" => username, "password" => password}}) do
    PersonRepo.authenticate_user(username, password)
    |> login_reply(conn)
  end

  defp login_reply({:error, error}, conn) do
    conn
    |> put_flash(:error, error)
    |> redirect(to: "/")
  end

  defp login_reply({:ok, person}, conn) do
    conn
    |> put_flash(:success, "Welcome back!")
    |> Guardian.Plug.sign_in(person)
    |> redirect(to: "/")
  end

  def logout(conn, _) do
    conn
    |> Guardian.Plug.sign_out()
    |> redirect(to: page_path(conn, :login))
  end

  def secret(conn, _params) do
    render(conn, "secret.html")
  end

  def racer(conn, _params) do
    render conn, "racer.html"
  end

  def new_user(conn, params) do
    kwl = params["person"]
          |> map_to_kwl

    struct(%Person{}, kwl)
    |> PersonRepo.save_person
    redirect(conn, to: "/")
  end

  defp map_to_kwl(map) do
    for {k, v} <- map, do: {String.to_atom(k), v}
  end
end
