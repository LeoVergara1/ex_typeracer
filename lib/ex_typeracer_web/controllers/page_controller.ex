defmodule ExTyperacerWeb.PageController do
  use ExTyperacerWeb, :controller
  
  def index(conn, _params) do
    render conn, "index.html"
  end

  def racer(conn, _params) do 
    render conn, "racer.html"
  end 

  def new_user(conn, params) do
    IO.inspect params
    render conn, "index.html" 
  end
end
