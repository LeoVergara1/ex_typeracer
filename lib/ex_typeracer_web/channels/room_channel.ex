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

  def handle_in("init_reace", payload, socket) do
    {_,text} = File.read("lib/resources/words.txt")
    paragraphs = String.split(text,"\n\n")
    random_number = :rand.uniform(length(paragraphs)-1)
    textRun = Enum.at(paragraphs, random_number) 
    IO.inspect textRun
    username = payload["username"]
    procees = "#{:rand.uniform(9000)}-#{username}"
    :ets.new(:"#{procees}", [:named_table, :public])
    :ets.insert(:"#{procees}", { "users", [payload["username"]] } )
    :ets.insert(:"#{procees}", { "text", textRun } )
    [{"list", list_rooms}] = :ets.lookup(:list_rooms, "list")
    list_rooms = List.insert_at(list_rooms, length(list_rooms), procees)
    IO.inspect list_rooms 
    :ets.insert(:list_rooms, { "list", list_rooms } )
    {:reply, 
    {:ok, %{"list" => list_rooms,
            "process" => procees,
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
    [{_,mi_text}] = :ets.lookup(:"${payload}","text")
    broadcast! socket, "f", %{"data" => payload}
    {:noreply, socket}
  end

end