defmodule ExTyperacer.Logic.PersonRepo do

  alias ExTyperacer.Repo
  alias ExTyperacer.Person
  import Ecto.Query, only: [from: 2]
  alias Comeonin.Bcrypt

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
    |> List.first
  end

  def check_password(person, password) do
    case Bcrypt.checkpw(password, person.password) do
      true -> {:ok, person}
      false -> {:error, "Incorrect username or password"}
    end
  end

  def get_user!(id), do: Repo.get!(Person, id)

  def list_users do
    Repo.all(Person)
  end

  def change_user(%Person{} = user) do
    Person.changeset(user, %{})
  end

  def authenticate_user(username, password)do
    find_user_by_username(username)
    |> check_password(password)
  end

end