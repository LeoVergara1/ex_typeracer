defmodule ExTyperacerWeb.PageController do
  use ExTyperacerWeb, :controller

  def index(conn, _params) do
    render conn, "index.html"
  end
end
