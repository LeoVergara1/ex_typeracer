defmodule ExTyperacerWeb.ScoresChannel do

  use Phoenix.Channel
  require Logger
  alias ExTyperacer.Logic.Game
  alias ExTyperacer.Logic.Player
  alias ExTyperacer.GameServer

  def join("scores", payload, socket) do
    Logger.warn " ::::::::: Scores Join Payload ::::::::"
    {:ok, socket}
  end

	def handle_in("scores:set", payload, socket) do
    Logger.warn " ::::::::: Scores:Set :::::::: Insert score"
    [{_,game_server}] = :ets.lookup(:"#{payload["name_rom"]}","game")
    player = GameServer.find_player game_server, payload["user"]
    player = Player.update_socere_player(player, payload["score"])
    GameServer.update_socere_player game_server, player
    players = GameServer.players game_server
	 	broadcast! socket, "scores:show", %{"game" => players}
    {:noreply, socket}
	end

	def handle_in("scores:get", payload, socket) do
    Logger.warn " ::::::::: Scores:Get :::::::: Getting scores"
		broadcast! socket, "scores:show", payload
    {:noreply, socket}
  end
  
  def handle_in("scores:position", payload, socket) do
    Logger.warn "::::: Winner :::::"
    [{_,game_server}] = :ets.lookup(:"#{payload["name_room"]}","game")
    player = GameServer.find_player game_server, payload["username"]
    GameServer.add_player_to_position game_server, player
    game = GameServer.get_game game_server
    positions =
    game.positions
    |> Enum.uniq_by(fn(x) -> x.username end)
    |> Enum.reverse
    broadcast! socket, "socore:winer_show", %{positions: positions, uuid_game: game.uuid}
    {:noreply, socket}
  end

end

