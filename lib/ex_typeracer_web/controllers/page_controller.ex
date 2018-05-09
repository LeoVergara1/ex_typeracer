defmodule ExTyperacerWeb.PageController do
  use ExTyperacerWeb, :controller
  alias ExTyperacer.Repo
  alias ExTyperacer.Person

  def index(conn, _params) do
    render conn, "index.html"
  end

  def racer(conn, _params) do 
    render conn, "racer.html"
  end 

  def new_user(conn, params) do
    IO.inspect params["person"]["password"]
    Repo.insert %Person{name: params["person"]["name"], lastname: params["person"]["lastname"], email: params["person"]["email"], password: params["person"]["password"]}
    render conn, "index.html" 
  end
end
