defmodule ExTyperacer.Repo.Migrations.AddTableSocr do
  use Ecto.Migration

  def change do
    create table("score") do
      add :paragraph, :string
      add :game, :string
      add :score, :string
      add :person, :string
      add :positios, {:array, :string}

      timestamps()
    end
  end
end
