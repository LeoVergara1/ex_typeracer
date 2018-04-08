defmodule ExTyperacer.Structs.Game do
  @moduledoc """
  This module handle the logic of TypeRacer Game
  """

  @enforce_keys [:paragraph]
  defstruct players: nil, paragraph: nil

  alias __MODULE__
  alias ExTyperacer.Structs.Player

  @doc """
  Creates s new game with a paragrapah to play and type.
  This game starts with zero players.
  """
  def new do
    %Game{
      players: [],
      paragraph: get_a_paragraph()
    }
  end

  @doc """
  Adds a new player to a game.
  TODO: Validates if the user already exists
  """
  def add_player(game, username) do
    new_player = %Player{username: username}
    %{game | players: [new_player | game.players]}
  end

  def new_game_with_one_player(username) do
    new() |> add_player(username)
  end

  @doc """
  Gets a new paragraph, at this moment is from file,
  eventually, it will be from a webservice or database
  """
  def get_a_paragraph do
    {_,text} = File.read("lib/resources/words.txt")
    paragraphs = String.split(text,"\n\n")
    random_number = :rand.uniform(length(paragraphs)-1)
    Enum.at(paragraphs, random_number)
  end

end
