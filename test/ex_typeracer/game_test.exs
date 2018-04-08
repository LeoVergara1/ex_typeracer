defmodule ExTyperacer.Structs.GameTest do
  use ExUnit.Case
  alias ExTyperacer.Structs.Game
  doctest Game

  test "a new game" do
    game = Game.new()
    assert game.paragraph
    assert Enum.count(game.players) == 0
  end

end

