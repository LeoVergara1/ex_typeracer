defmodule ExTyperacer.Repo.Migrations.AddColumTokenToPerson do
  use Ecto.Migration

  def change do
    alter table(:persons) do
      add :token, :string
    end
  end
end
