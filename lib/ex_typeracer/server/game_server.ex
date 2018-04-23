defmodule ExTyperacer.GameServer do

  use GenServer
  require Logger
  alias ExTyperacer.Logic.{Game, Player}

  # Client Interface

  def start_link(game_name) do
    GenServer.start_link(__MODULE__, {game_name}, name: via_tuple(game_name))
  end

  # Auxiliar functions

  def via_tuple(game_name) do
    {:via, Registry, {ExTyperacer.GameRegistry, game_name}}
  end

  def game_pid(game_name) do
    game_name
    |> via_tuple()
    |> GenServer.whereis()
  end

  # Server Callbacks

  def init({_game_name}) do
    game =
      Game.get_a_paragraph()
      |> Game.new

    {:ok, game}
  end

  # def handle_call(:atom, from, state) do
  # end

  # def handle_cast() do
  # end

  # def handle_info(:timeout, state) do
  # end

  # def terminate(_reason, _state) do
  #   :ok
  # end

end
