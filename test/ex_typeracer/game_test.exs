defmodule ExTyperacer.Structs.GameTest do

  @moduledoc """
  Maybe we should move the paragraph getting to a module
  """

  use ExUnit.Case
  alias ExTyperacer.Structs.Game
  alias ExTyperacer.Structs.Player
  doctest Game


  @doc """
  We must remove the dependency for obtain text
  """
  test "a new game" do
    game = Game.new("Hello world")
    assert game.paragraph
    assert Enum.count(game.players) == 0
    assert Enum.count(game.letters) == 11
  end

  test "add a new player" do
    game = Game.new("Hello MD")
    game = Game.add_player(game, "neodevelop")
    assert Enum.count(game.players) == 1
  end

  test "add the same player twice" do
  end

  test "playing the game" do
    game = Game.new("Hello MakingDevs.") |> Game.add_player("neodevelop")
    assert game.paragraph == "Hello MakingDevs."

    #"Hello" |> String.codepoints |> Enum.each(&(Game.plays(game, "neodevelop",&1)))
    game = Game.plays game, "neodevelop", "H"
    game = Game.plays game, "neodevelop", "e"
    game = Game.plays game, "neodevelop", "l"
    game = Game.plays game, "neodevelop", "l"
    game = Game.plays game, "neodevelop", "o"
    player = Enum.find(game.players, fn %Player{username: "neodevelop"} -> :ok end)
    assert player.paragraph_typed == "Hello"
    assert player.score == 29
    assert Enum.count(game.players) == 1
  end

end

