defmodule ExTyperacer.Repo.Migrations.CreatePersons do
  use Ecto.Migration

  def change do
    create table(:persons) do
      add :name, :string
      add :lastname, :string
      add :email, :string
      add :password, :string

      timestamps()
    end

  end
end
