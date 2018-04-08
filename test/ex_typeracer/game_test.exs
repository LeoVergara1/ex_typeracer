defmodule ExTyperacer.Structs.GameTest do
  use ExUnit.Case
  alias ExTyperacer.Structs.Game
  alias ExTyperacer.Structs.Player
  doctest Game

  @doc """
  We must remove the dependency for obtain text
  """
  test "a new game" do
    game = Game.new()
    assert game.paragraph
    assert Enum.count(game.players) == 0
  end

  test "add a new player" do
    game = Game.new()
    game = Game.add_player(game, "neodevelop")
    assert Enum.count(game.players) == 1
  end

  test "add the same player twice" do
  end

  test "playing the game" do
    game = %{ Game.new() |
      paragraph: "Hello Typeracer, we're MakingDevs."
    } |> Game.add_player("neodevelop")
    assert game.paragraph == "Hello Typeracer, we're MakingDevs."

    game = Game.plays game, "neodevelop", "Hello"
    player = Enum.find(game.players, fn %Player{username: "neodevelop"} -> :ok end)
    assert player.paragraph_typed == "Hello"
    assert player.score == 15
    assert Enum.count(game.players) == 1
  end

end

