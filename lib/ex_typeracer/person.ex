defmodule ExTyperacer.Person do
  use Ecto.Schema
  import Ecto.Changeset


  schema "persons" do
    field :email, :string
    field :lastname, :string
    field :name, :string
    field :password, :string
    field :username, :string

    timestamps()
  end

  def changeset(person, attrs) do
    person
    |> cast(attrs, [:name, :lastname, :email, :password, :username])
    |> validate_required([:name, :lastname, :email, :password, :username])
    |> unsafe_validate_unique([:email], ExTyperacer.Repo, message: "Email already registered")
    |> unsafe_validate_unique([:username], ExTyperacer.Repo, message: "Username already registered")
  end
end
