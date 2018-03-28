defmodule ExTyperacerWeb.PlayersChannel do
  use Phoenix.Channel
  require Logger

  def join("players", payload, socket) do
    [{_, map}] = :ets.lookup(:mapShared, "users")
    usersUpdated = Map.put_new(map, socket.id, String.to_atom(Integer.to_string(payload["uuid"])))
    :ets.insert(:mapShared, {"users", usersUpdated})
    Logger.info ":: Players:channel:: Conexión de un usuario"
    {:ok, socket}
  end

  def handle_in("get_list", payload, socket) do
    [{_, map}] = :ets.lookup(:mapShared, "users")
    broadcast! socket, "players_list", %{"users" => map}
    {:noreply, socket}
  end

  def terminate(reason, socket) do
    [{_, map}] = :ets.lookup(:mapShared, "users")
    mapUpdated = Map.delete(map, socket.id)
    Logger.info ":: Players:channel:: Desconexión de un usuario. Actualizando usuarios activos"
    :ets.insert(:mapShared, {"users", mapUpdated})
    broadcast! socket, "players_list", %{"users" => mapUpdated}
    :ok
  end

end

