defmodule ExTyperacerWeb.PageController do
  use ExTyperacerWeb, :controller

  def index(conn, _params) do
    render conn, "index.html"
  end

  def racer(conn, _params) do 
    render conn, "racer.html"
  end 
end
