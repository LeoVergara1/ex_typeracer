defmodule ExTyperacer.Repo.Migrations.AddConstrainToPerson do
  use Ecto.Migration

  def change do
    create index("persons", [:email, :username], unique: true)
  end
end
