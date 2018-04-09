defmodule ExTyperacerWeb.RoomChannel do

  alias ExTyperacer.Structs.Game

  require Logger

  use Phoenix.Channel

  def join("room:new", _payload, socket) do
    Logger.info ":: Room:channel:: Conexión a una sala"
    {:ok, socket}
  end

  def handle_in("get_text", _payload, socket) do
    {:noreply, socket}
  end

  def handle_in("init_reace", payload, socket) do
    username = payload["username"]
    game = Game.new(Game.get_a_paragraph()) |> Game.add_player(username)
    IO.inspect game
    :ets.new(:"#{game.uuid}", [:named_table, :public])
    :ets.insert(:"#{game.uuid}", {"game", game} )
    [{"list", list_rooms}] = :ets.lookup(:list_rooms, "list")
    :ets.insert(:list_rooms, { "list", list_rooms ++ [game.uuid] } )
    {:reply,
    {:ok, %{"list" => list_rooms,
            "process" => game.uuid,
            "userList" => game.players,
            "user" => payload["username"]
          }
    },
    socket}
  end

  def handle_in("join_race", payload, socket) do
    username = payload["username"]
    uuidGame = payload["uuid"]
    IO.inspect "Estoy aqui"
    IO.inspect uuidGame
    [{_,game}] = :ets.lookup(:"#{uuidGame}","game")
    [{"list", list_rooms}] = :ets.lookup(:list_rooms, "list")
    game = Game.add_player(game, username)
    IO.inspect game
    :ets.insert(:"#{game.uuid}", {"game", game} )
    {:reply,
    {:ok, %{"list" => list_rooms,
            "process" => game.uuid,
            "userList" => game.players,
            "user" => payload["username"]
          }
    },
    socket}
  end


  def handle_in("get_romms", _payload, socket) do
    [{_, list_rooms}] = :ets.lookup(:list_rooms, "list")
    broadcast! socket, "list_rooms", %{"rooms" => list_rooms}
    {:noreply, socket}
  end

  def handle_in("show_run_area", uuidGame, socket) do
    [{_,game}] = :ets.lookup(:"#{uuidGame}","game")
    broadcast! socket, "#{uuidGame}", %{"data" => game.paragraph}
    {:noreply, socket}
  end

  def handle_in("updating_players", uuidGame, socket) do
    [{_,game}] = :ets.lookup(:"#{uuidGame}","game")
    broadcast! socket, "updating_player_#{uuidGame}", %{"game" => game}
    {:noreply, socket}
  end

end
