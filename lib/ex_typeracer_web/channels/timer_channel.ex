defmodule ExTyperacerWeb.TimerChannel do

  use Phoenix.Channel

  def join("timer:update", _msg, socket) do
    {:ok, socket}
  end

  def handle_in("new_time", msg, socket) do
    push socket, "new_time", msg
    {:noreply, socket}
  end

  def handle_in("start_timer", payload, socket) do
    IO.inspect payload
		ExTyperacerWeb.Endpoint.broadcast("timer:start", "start_timer", %{uuid: payload["name_room"]})
		{:noreply, socket}
	end

end
