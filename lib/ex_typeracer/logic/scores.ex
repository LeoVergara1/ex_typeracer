defmodule ExTyperacer.Logic.Scores do

  alias ExTyperacer.Repo
  alias ExTyperacer.Score
  import Ecto.Query, only: [from: 2]

  def save_score(score) do
    changeset = Score.changeset( %Score{}, Map.from_struct(score))
    case changeset.valid? do
      true -> Repo.insert changeset
      false -> changeset.errors
    end
  end


end