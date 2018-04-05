defmodule ExTyperacer.Structs.Game do

  defstruct players: nil, paragraph: nil, state: nil, uuid: nil

  alias ExTyperacer.Structs.Game
  alias ExTyperacer.Structs.Player

  def initGame(username) do
    {_,text} = File.read("lib/resources/words.txt")
    paragraphs = String.split(text,"\n\n")
    random_number = :rand.uniform(length(paragraphs)-1)
    textRun = Enum.at(paragraphs, random_number)
    %Game{ players: [%Player{username: username}], 
                    paragraph: textRun, state: :active, 
                    uuid: "#{:rand.uniform(9000)}-#{username}" }
  end

  def addPlayer(game, username) do
    newPlayer = %Player{username: username}
    %{game | players: game.players ++ [newPlayer]}
  end

end