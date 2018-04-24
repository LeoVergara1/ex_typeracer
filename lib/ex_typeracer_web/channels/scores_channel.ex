defmodule ExTyperacerWeb.ScoresChannel do

  use Phoenix.Channel
  require Logger
  alias ExTyperacer.Logic.Game
  alias ExTyperacer.Logic.Player

  def join("scores", payload, socket) do
    Logger.warn " ::::::::: Scores Join Payload ::::::::"
    {:ok, socket}
  end

	def handle_in("scores:set", payload, socket) do
    Logger.warn " ::::::::: Scores:Set :::::::: Insert score"
    [{_,game}] = :ets.lookup(:"#{payload["uuid"]}","game")
    player = Enum.find(game.players, fn %Player{username: u} -> u == payload["user"] end)
    player = Player.update_socere_player(player, payload["score"])
    game = Game.update_socere_player(game, player)
    :ets.insert(:"#{game.uuid}", {"game", game} )
		broadcast! socket, "scores:show", %{"game" => game.players}
    {:noreply, socket}
	end

	def handle_in("scores:get", payload, socket) do
    Logger.warn " ::::::::: Scores:Get :::::::: Getting scores"
		broadcast! socket, "scores:show", payload
    {:noreply, socket}
	end

end

