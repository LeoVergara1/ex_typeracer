#defmodule  ExTyperacer.Auth.Auth do
#  alias Comeonin.Bcrypt
#
#  def authenticate_user(username, plain_text_password) do
#    query = from u in User, where: u.username == ^username
#    Repo.one(query)
#    |> check_password(plain_text_password)
#  end
#
#  defp check_password(nil, _), do: {:error, "Incorrect username or password"}
#
#  defp check_password(user, plain_text_password) do
#    case Bcrypt.checkpw(plain_text_password, user.password) do
#      true -> {:ok, user}
#      false -> {:error, "Incorrect username or password"}
#    end
#  end
#end
#