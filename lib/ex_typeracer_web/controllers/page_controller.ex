defmodule ExTyperacerWeb.PageController do
  use ExTyperacerWeb, :controller
  alias ExTyperacer.Repo
  alias ExTyperacer.Person
  alias ExTyperacer.Logic.PersonRepo

  def index(conn, _params) do
    render conn, "index.html"
  end

  def racer(conn, _params) do 
    render conn, "racer.html"
  end 

  def new_user(conn, params) do
    #IO.inspect params["person"]
    #IO.inspect params["person"]["password"]
    #Map.merge{%Person{}, params["person"]}
    %Person{name: params["person"]["name"], lastname: params["person"]["lastname"], email: params["person"]["email"], password: params["person"]["password"], username: params["person"]["username"]}
    |> PersonRepo.save_person
    render conn, "index.html" 
  end
end
