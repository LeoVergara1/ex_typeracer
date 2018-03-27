defmodule ExTyperacerWeb.PlayersChannel do
  use Phoenix.Channel

  def join("players", payload, socket) do
    IO.inspect payload
    [{_, users_list}] = :ets.lookup(:mapShared, "users")
    users_list = List.insert_at(users_list, length(users_list), payload)
    :ets.insert(:mapShared, {"users", users_list})
    {:ok, socket}
  end

  def handle_in("get_list", payload, socket) do
    [{_, users_list}] = :ets.lookup(:mapShared, "users")
    broadcast! socket, "players_list", %{"users:" => users_list}
    {:noreply, socket}
  end

end

