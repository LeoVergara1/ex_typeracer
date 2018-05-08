defmodule ExTyperacer.GameServer do

  use GenServer
  require Kernel
  require Logger
  alias ExTyperacer.Logic.{Game, Player}

  # Client Interface

  def start_link(game_name) do
    #GenServer.start_link(__MODULE__, {game_name}, name: via_tuple(game_name))
    GenServer.start_link(__MODULE__, {game_name}, name: via_tuple(game_name))
    via_tuple(game_name)
  end

  def add_player(name, username) do
    GenServer.cast(name, {:add_player, username})
  end

  def find_player(name, username) do
    GenServer.call name, {:find_a_player, username}
  end

  def plays(name, username, letter) do
    GenServer.call name, username, letter
  end

  def players(name) do
    GenServer.call name, {:get_players} 
  end

  def paragraph_of_game(name) do
    GenServer.call name, {:resumen}
  end

  def update_socere_player(name, player) do
    GenServer.cast name, {:update_socere_player, player}
  end

  def get_game(name) do
    GenServer.call name, {:get_game}
  end

  def add_player_to_position(name, player) do
    GenServer.cast name, {:add_player_to_position, player}
  end

  def start_timer(name, counter) do
    game = GenServer.call name, {:get_game}
    {:ok, timer} = game.timer
    send(timer , {:start_timer, counter, name, game})
  end

  def start_timer_waiting(name, counter) do 
    game = GenServer.call name, {:get_game}
    {:ok, timer} = game.timer
    send(timer , {:start_timer_waiting, counter, name, game})
  end

  def update_status_game(name, status) do 
    GenServer.call name, {:update_status, status}
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

  def handle_call({:resumen}, _from, state) do
    {:reply, state.paragraph, state}
  end

  def handle_cast({:add_player, username}, state) do
    game = Game.add_player(state, username)
    {:noreply, game}
  end

  def handle_call({:get_a_paragraph}, state) do
    Game.get_a_paragraph
    {:reply, "hola"}
  end

  def handle_call({:update_status, status}, _from, state) do
    game = Game.update_status(state, status)
    {:reply, game, game}
  end

  def handle_call({:find_a_player, username}, _from, state) do
    player = Game.find_a_player(state, username)
    {:reply, player, state}
  end

  def handle_call({:plays, username, letter}, _from, state) do
    game = Game.plays(state, username, letter)
    {:reply, game, state}
  end

  def handle_call({:get_players}, _from, state) do
    {:reply, state.players, state}
  end

  def handle_cast({:update_socere_player, player}, state) do
    game = Game.update_socere_player(state, player)
    {:noreply, game}
  end

  def handle_call({:get_game}, _from, state) do
    {:reply, state, state}
  end

  def handle_cast({:add_player_to_position, player }, state) do
    game = Game.add_position_to_player state, player
    {:noreply, game}
  end

  # def handle_info(:timeout, state) do
  # end

  # def terminate(_reason, _state) do
  #   :ok
  # end

end
