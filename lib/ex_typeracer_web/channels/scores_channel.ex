defmodule ExTyperacerWeb.ScoresChannel do

  use Phoenix.Channel
  require Logger
  alias ExTyperacer.Structs.Game
  alias ExTyperacer.Structs.Player

  def join("scores", payload, socket) do
    Logger.warn " ::::::::: Scores Join Payload ::::::::"
    {:ok, socket}
  end

	def handle_in("scores:set", payload, socket) do
    Logger.warn " ::::::::: Scores:Set :::::::: Insert score"
    IO.inspect payload["uuid"]
    [{_,game}] = :ets.lookup(:"#{payload["uuid"]}","game")
    IO.inspect payload
    player = Enum.find(game.players, fn %Player{username: u} -> u == payload["user"] end)
    IO.inspect player
    IO.inspect payload["score"]
    player = Player.update_socere_player(player, payload["score"])
    game = Game.update_socere_player(game, player)
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

