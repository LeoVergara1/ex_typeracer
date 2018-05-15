defmodule ExTyperacerWeb.LobbyChannel do
  use Phoenix.Channel

  def join("lobby", payload, socket) do
    [{_, users_list}] = :ets.lookup(:mapShared, "users")
    users_list = List.insert_at(users_list, length(users_list)+1, payload)
    :ets.insert(:mapShared, {"users", users_list})
    {:ok, socket}
  end

  def handle_in("new_message", payload, socket) do
    broadcast! socket, "new_message", payload
    {:noreply, socket}
  end
end

