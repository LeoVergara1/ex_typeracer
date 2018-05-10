defmodule ExTyperacer.Logic.PersonRepo do

  alias ExTyperacer.Repo
  alias ExTyperacer.Person
  import Ecto.Query, only: [from: 2]

  def save_person(person) do
    changeset = Person.changeset( %Person{}, Map.from_struct(person))
    case changeset.valid? do
      true -> Repo.insert changeset
      false -> changeset.errors
    end
  end

  def find_user_by_username(username) do
    query = from u in Person, where: u.username == ^username, select: u
    Repo.all(query)
  end
end