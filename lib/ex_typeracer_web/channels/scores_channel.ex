defmodule ExTyperacerWeb.ScoresChannel do

  use Phoenix.Channel
  require Logger
  alias ExTyperacer.Structs.Player

  def join("scores", payload, socket) do
    Logger.warn " ::::::::: Scores Join Payload ::::::::"
    {:ok, socket}
  end

	def handle_in("scores:set", payload, socket) do
    Logger.warn " ::::::::: Scores:Set :::::::: Insert score"
    #:ets.insert(:scoresGlobalMap, { payload["user"], payload["score"] })
    IO.inspect payload["uuid"]
    [{_,game}] = :ets.lookup(:"#{payload["uuid"]}","game")
    IO.inspect payload
   # player = for element <- game.players, element.username == payload["user"], do: element 
    player = Enum.find(game.players, fn %Player{username: u} -> u == payload["user"] end)
    IO.inspect player
    IO.inspect payload["score"]
    player = %Player{ player | score: payload["score"]}
    players = for element <- game.players, element.username != payload["user"], do: element 
    new_list_player = [player] ++ players
    IO.inspect new_list_player
    :ets.insert(:"#{game.uuid}", {"game", game} )
		broadcast! socket, "scores:show", payload
    {:noreply, socket}
	end

	def handle_in("scores:get", payload, socket) do
    Logger.warn " ::::::::: Scores:Get :::::::: Getting scores"
		broadcast! socket, "scores:show", payload
    {:noreply, socket}
	end

end

