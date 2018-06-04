defmodule ExTyperacerWeb.LoginController do
  use ExTyperacerWeb, :controller
  alias ExTyperacer.{Person, Logic.PersonRepo, Auth.Guardian}

  plug Ueberauth
  plug :put_layout, "login.html"

  def index(conn, _params) do
    changeset = PersonRepo.change_user(%Person{})
    maybe_user = Guardian.Plug.current_resource(conn)
    conn
      |> render("index.html", changeset: changeset, maybe_user: maybe_user)
  end

  def login(conn, %{"person" => %{"username" => username, "password" => password}}) do
    PersonRepo.authenticate_user(username, password)
    |> login_reply(conn)
  end

  defp login_reply({:error, error}, conn) do
    conn
    |> put_flash(:error, error)
    |> redirect(to: "/login")
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

end
