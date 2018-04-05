defmodule ExTyperacerWeb.RoomChannel do
  alias ExTyperacer.Structs.Game
  alias ExTyperacer.Structs.Player
  use Phoenix.Channel
  require Logger

  def join("room:new", payload, socket) do
    Logger.info ":: Room:channel:: Conexión a una sala"
    {:ok, socket}
  end

  def handle_in("get_text", payload, socket) do
    {:noreply, socket}
  end

  def handle_in("init_reace", payload, socket) do
    username = payload["username"]
    game = Game.initGame(username)
    :ets.new(:"#{game.uuid}", [:named_table, :public])
    :ets.insert(:"#{game.uuid}", {"game", game} )
    [{"list", list_rooms}] = :ets.lookup(:list_rooms, "list")
    :ets.insert(:list_rooms, { "list", list_rooms ++ [game.uuid] } )
    {:reply, 
    {:ok, %{"list" => list_rooms,
            "process" => game.uuid,
            "user" => payload["username"]
          }
    },
    socket}
  end

  def handle_in("get_romms", payload, socket) do
    [{_, list_rooms}] = :ets.lookup(:list_rooms, "list")
    broadcast! socket, "list_rooms", %{"rooms" => list_rooms}
    {:noreply, socket}
  end

  def handle_in("show_run_area", payload, socket) do 
    IO.inspect payload
    [{_,game}] = :ets.lookup(:"#{payload}","game")
    broadcast! socket, "#{payload}", %{"data" => game.paragraph}
    {:noreply, socket}
  end

end