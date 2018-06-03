defmodule ExTyperacerWeb.LoginController do
  use ExTyperacerWeb, :controller

  plug Ueberauth
  plug :put_layout, "login.html"

  def index(conn, _params) do
    render(conn, "index.html")
  end

end
