defmodule ExTyperacerWeb.ScoresChannel do

  use Phoenix.Channel
  require Logger

  def join("scores", payload, socket) do
    Logger.warn " ::::::::: Scores Join Payload ::::::::"
    {:ok, socket}
  end

	def handle_in("scores:set", payload, socket) do
    Logger.warn " ::::::::: Scores:Set :::::::: Insert score"
		:ets.insert(:scoresGlobalMap, { payload["user"], payload["score"] })
    {:noreply, socket}
	end

	def handle_in("scores:get", payload, socket) do
    Logger.warn " ::::::::: Scores:Get :::::::: Getting scores"
		broadcast! socket, "scores:show", payload
    {:noreply, socket}
	end

end

