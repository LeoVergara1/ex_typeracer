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
    kwl = params["person"]
          |> map_to_kwl

    struct(%Person{}, kwl)
    |> PersonRepo.save_person

    render conn, "index.html"
  end

  defp map_to_kwl(map) do
    for {k, v} <- map, do: {String.to_atom(k), v}
  end
end
