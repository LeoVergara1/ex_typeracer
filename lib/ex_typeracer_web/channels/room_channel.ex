defmodule ExTyperacerWeb.RoomChannel do
  use Phoenix.Channel
  require Logger

  def join("room:new", payload, socket) do
    Logger.info ":: Room:channel:: Conexión a una sala"
    {_,text} = File.read("lib/resources/words.txt")
    IO.inspect text
    paragraphs = String.split(text,"\n\n")
    random_number = :rand.uniform(length(paragraphs)-1)
    IO.inspect random_number
    {:ok, %{"text" => Enum.at(paragraphs, random_number)}, socket}
  end

  def handle_in("get_text", payload, socket) do
    {:noreply, socket}
  end

end