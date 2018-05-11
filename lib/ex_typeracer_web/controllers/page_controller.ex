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
      IO.puts "Someone is logged in"
    else
      IO.puts "No one is logged in"
    end
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
