defmodule ExTyperacer.Score do
  use Ecto.Schema
  import Ecto.Changeset


  schema "score" do
    field :paragraph, :string
    field :game, :string
    field :score, :string
    field :person, :string
    field :positios, {:array, :string}

    timestamps()
  end

  def changeset(person, attrs) do
    person
    |> cast(attrs, [:paragraph, :game, :email, :score, :person, :array])
    |> validate_required([:paragraph, :game, :email, :score, :person, :array])
  end

end