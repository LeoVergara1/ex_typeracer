defmodule ExTyperacer.Structs.Player do
  @moduledoc """
  This module handle the Player data and maybe the behaviour
  """
  alias __MODULE__

  @enforce_keys [:username]
  defstruct username: nil, paragraph_typed: "", score: 0

  def typing_a_letter(player, letter, game_paragraph) do
    paragraph = player.paragraph_typed <> letter
    %Player{ player |
      paragraph_typed: paragraph,
      score: round(String.length(paragraph) * 100 / String.length(game_paragraph) )
    }
  end

end
