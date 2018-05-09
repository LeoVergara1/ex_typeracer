defmodule ExTyperacer.Repo.Migrations.AddRowPerson do
  use Ecto.Migration

  def change do
    alter table(:persons) do
      add :username, :string
    end
  end
end
