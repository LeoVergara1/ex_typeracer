defmodule ExTyperacer.Structs.Game do
  @moduledoc """
  This module handle the logic of TypeRacer Game
  """

  @enforce_keys [:paragraph]
  defstruct players: [], paragraph: nil, letters: [], uuid: nil, positions: []

  alias __MODULE__
  alias ExTyperacer.Structs.Player

  @doc """
  Creates a new game with a paragrapah to play and type.
  This game starts with zero players.
  """
  def new(paragraph) do
    %Game{ paragraph: paragraph, letters: String.codepoints(paragraph), uuid: :rand.uniform(10000) }
  end

  @doc """
  Adds a new player to a game.
  TODO: Validates if the user already exists
  """
  def add_player(game, username) do
    new_player = %Player{username: username}
    %{game | players: [new_player | game.players]}
  end

  @doc """
  Plays with one letter for one player
  """
  def plays(game, username, letter) do
    player = find_a_player(game, username)
    players = game.players -- [player]
    player_updated = Player.typing_a_letter(player, letter, game.paragraph)
    new_positions = player_ends?(player_updated) ++ game.positions
    %Game{game | players: [ player_updated | players], positions: new_positions }
  end

  defp player_ends?(%Player{score: 100} = player), do: [player]
  defp player_ends?(_), do: []

  def find_a_player(game, username) do
    Enum.find(game.players, fn %Player{username: u} -> u == username end)
  end

  def new_game_with_one_player(paragraph, username) do
    paragraph |> new() |> add_player(username)
  end

  @doc """
  Gets a new paragraph, at this moment is from file,
  eventually, it will be from a webservice or database
  """
  def get_a_paragraph do
    {_,text} = File.read("lib/resources/letters.txt")
    paragraphs = String.split(text,"\n\n")
    random_number = :rand.uniform(length(paragraphs)-1)
    Enum.at(paragraphs, random_number)
  end

end
