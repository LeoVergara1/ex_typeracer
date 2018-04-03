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
    username = payload["username"]
    procees = "#{:rand.uniform(9000)}-#{username}"
    paragraphs = String.split(text,"\n\n")
    random_number = :rand.uniform(length(paragraphs)-1)
    {:reply, 
    {:ok, %{"text" => Enum.at(paragraphs, random_number),
            "process" => procees
          }
    },
    socket}
  end

end