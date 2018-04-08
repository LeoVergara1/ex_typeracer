defmodule ExTyperacer.Structs.Player do
  @moduledoc """
  This module handle the Player data and maybe the behaviour
  """

  @enforce_keys [:username]
  defstruct username: nil, paragraph_typed: ""

end
