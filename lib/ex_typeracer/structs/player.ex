defmodule ExTyperacer.Structs.Player do
  @moduledoc """
  This module handle the Player data and maybe the behaviour
  """
  alias __MODULE__

  @enforce_keys [:username]
  defstruct username: nil, paragraph_typed: "", score: 0

  @doc """
  The player sends a letter to the paragraph typed
  to increase the score and try to win
  TODO: This needs a test for Player Module
  """
  def typing_a_letter(player, letter, game_paragraph) do
    paragraph = player.paragraph_typed <> letter
    partial_paragraph = String.slice(game_paragraph, 0, String.length(paragraph))
    matches = for l1 <- String.codepoints(paragraph),
      l2 <- String.codepoints(partial_paragraph),
      do: {l1, l2, l1 == l2}
    IO.inspect matches
    %Player{ player |
      paragraph_typed: paragraph,
      score: round(String.length(paragraph) * 100 / String.length(game_paragraph) )
    }
  end

end
