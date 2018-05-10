defmodule ExTyperacer.Logic.PersonRepo do

  alias ExTyperacer.Repo
  alias ExTyperacer.Person

  def save_person(person) do
    changeset = Person.changeset( %Person{}, Map.from_struct(person))
    case changeset.valid? do
      true -> Repo.insert changeset
      false -> changeset.errors
    end
  end
end