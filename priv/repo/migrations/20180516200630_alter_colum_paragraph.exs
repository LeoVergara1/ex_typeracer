defmodule ExTyperacer.Repo.Migrations.AlterColumParagraph do
  use Ecto.Migration

  def change do
    alter table(:score) do
      modify :paragraph, :text
    end
  end
end
