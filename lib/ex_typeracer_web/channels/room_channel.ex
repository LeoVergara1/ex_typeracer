defmodule ExTyperacerWeb.RoomChannel do
  use Phoenix.Channel
  require Logger

  def join("room:new", payload, socket) do
    Logger.info ":: Room:channel:: Conexión a una sala"
    {:ok, socket}
  end

  def handle_in("get_text", payload, socket) do
    {:noreply, socket}
  end

end