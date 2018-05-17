defmodule ExTyperacer.TimerServer do

  use GenServer
  require Kernel
  alias ExTyperacer.Logic.{Game, Player}
  alias ExTyperacer.GameServer
  
  def start_link() do
    GenServer.start_link(__MODULE__, %{})
  end
  
  def init(state) do
    IO.puts "Init timer"
    ExTyperacerWeb.Endpoint.subscribe "timer:start", []
    #timer = Process.send_after(self(), {:work, counter, uuid}, 1_000)
    #timer = Process.send_after(self(), {:work, 30, 3989},1_000)
    #IO.inspect timer
    {:ok, state}
  end

  def handle_call({:reset_timer, counter, uuid}, _from, %{timer: timer}) do
    cancel_timer(timer)
    timer = Process.send_after(self(), {:work, counter, uuid}, 1_000)
    {:reply, :ok, %{timer: timer}}
  end

  def handle_call({:stop_timer}, _from, %{timer: timer}) do
    :timer.cancel(timer)
    {:reply, :ok, %{timer: timer}}
  end


  def handle_call(:get_timer_ref, _from, %{timer: timer}) do
    {:reply, timer, %{timer: timer}}
  end

  def handle_info({:work, 0, uuid}, state) do
    IO.puts "Finished proccess"
    broadcast 0, %{message: "new_time_", uuid: uuid, select: "new_time_"}
    {:noreply, state}
  end

  def handle_info({:work, counter, uuid}, %{timer: timer}) do
    cancel_timer(timer)
    IO.puts "Counting..."
    IO.inspect uuid
    IO.inspect counter
    broadcast counter, %{message: "Counting..", uuid: uuid, select: "new_time_"}
    counter = counter - 1
    timer = Process.send_after(self(), {:work, counter, uuid},1_000)
    {:noreply, %{timer: timer}}
  end

  def handle_info({:waiting, 0, uuid}, %{timer: timer}) do
    IO.puts "Finished procces of waiting"
    broadcast 0, %{message: "Counting", uuid: uuid, select: "waiting_time_"}
    {:noreply, %{timer: timer}}
  end

  def handle_info({:waiting, counter, uuid}, %{timer: timer}) do
    IO.puts "waiting..."
    IO.inspect counter
    broadcast counter, %{message: "waiting", uuid: uuid, select: "waiting_time_"}
    counter = counter - 1
    timer = Process.send_after(self(), {:waiting, counter, uuid},1_000)
    {:noreply, %{timer: timer}}
  end

  def handle_info({:kill_timer, 0, name, game}, %{timer: timer}) do
    GameServer.save_score_by_paragraph name, game
    GameServer.delete_game_in_ets(name)
    broadcast(0, %{message: "playing", uuid: game.uuid, select: "playing_time_" }, Game.get_list_positions(game) ) 
    {:noreply, %{timer: timer}}
  end

  def handle_info({:kill_timer, counter, name, game}, %{timer: timer}) do
    counter = counter - 1
    if counter < 120 do 
      broadcast counter, %{message: "playing", uuid: game.uuid, select: "playing_time_"} 
    end
    IO.inspect "Kill second: #{counter}"
    timer = Process.send_after(self(), {:kill_timer, counter, name, game}, 1_000)
    {:noreply, %{timer: timer}}
  end

  def handle_info({:start_timer, counter, name, game}, state) do
    GameServer.update_status_game name, "playing"
    IO.inspect "Se inicia destrucción"
    send(self(), {:kill_timer, 180, name, game})
    timer = Process.send_after(self(), {:work, counter, game.uuid}, 1_000)
    {:noreply, %{timer: timer}}
  end

  def handle_info({:start_timer_waiting, counter, name, game}, state) do
    GameServer.update_status_game name, "waiting"
    send(self(), {:kill_timer, 180, name, game})
    timer = Process.send_after(self(), {:waiting, counter, game.uuid}, 1_000)
    {:noreply, %{timer: timer}}
  end
    # So that unhanded messages don't error
  def handle_info(_, state) do
      IO.inspect "No matching any method"
      {:ok, state}
  end

  defp cancel_timer(ref), do: Process.cancel_timer(ref)

  defp broadcast(time, response) do
    IO.inspect response
    ExTyperacerWeb.Endpoint.broadcast! "timer:update","#{response.select}#{response.uuid}", %{ time: time, response: response.message}
  end

  defp broadcast(time, response, positions) do
    IO.inspect response
    ExTyperacerWeb.Endpoint.broadcast! "timer:update","#{response.select}#{response.uuid}", %{ time: time, response: response.message, positions: positions}
  end

end