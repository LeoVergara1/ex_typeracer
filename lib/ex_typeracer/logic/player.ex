defmodule ExTyperacer.Logic.Player do
  @moduledoc """
  This module handle the Player data and maybe the behaviour
  """
  alias __MODULE__

  @enforce_keys [:username]
  defstruct username: nil, paragraph_typed: "", score: 0, counting?: true

  @doc """
  The player sends a letter to the paragraph typed
  to increase the score and try to win
  TODO: This needs a test for Player Module
  """
  def typing_a_letter(player, letter, game_paragraph) do
    paragraph = player.paragraph_typed <> letter
    case player.counting? do
      false -> %Player{ player | paragraph_typed: paragraph }
      true ->
        partial_paragraph = String.slice(game_paragraph, 0, String.length(paragraph))

        paragraph_in_letters = paragraph |> String.codepoints
        partial_paragraph_in_letters = partial_paragraph |> String.codepoints

        case List.last(paragraph_in_letters) == List.last(partial_paragraph_in_letters) do
          true ->
            %Player{ player |
              paragraph_typed: paragraph,
              score: round(String.length(paragraph) * 100 / String.length(game_paragraph) ),
              counting?: true
            }
          false ->
            %Player{ player |
              paragraph_typed: paragraph,
              score: round((String.length(paragraph) - 1) * 100 / String.length(game_paragraph) ),
              counting?: false
            }
        end

    end
  end

  def update_socere_player(player, score) do
    score = Float.ceil(score,2)
    %Player{ player | score: score} 
  end

end
