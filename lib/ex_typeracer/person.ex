defmodule ExTyperacer.Person do
  use Ecto.Schema
  import Ecto.Changeset


  schema "persons" do
    field :email, :string
    field :lastname, :string
    field :name, :string
    field :password, :string

    timestamps()
  end

  @doc false
  def changeset(person, attrs) do
    person
    |> cast(attrs, [:name, :lastname, :email, :password])
    |> validate_required([:name, :lastname, :email, :password])
  end
end
