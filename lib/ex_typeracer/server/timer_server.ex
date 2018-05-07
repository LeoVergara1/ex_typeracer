defmodule ExTyperacer.TimerServer do

  use GenServer
  require Kernel
  alias ExTyperacer.Logic.{Game, Player}
  
  def start_link() do
    GenServer.start_link(__MODULE__, %{})
  end
  
  def init(state) do
    IO.puts "Init timer"
    ExTyperacerWeb.Endpoint.subscribe "timer:start", []
    #timer = Process.send_after(self(), {:work, counter, uuid}, 1_000)
    {:ok, state}
  end

  def handle_call({:reset_timer, counter, uuid}, _from, %{timer: timer}) do
    :timer.cancel(timer)
    timer = Process.send_after(self(), {:work, counter, uuid}, 1_000)
    {:reply, :ok, %{timer: timer}}
  end

  def handle_info({:work, 0, uuid}, state) do
    IO.puts "Finished proccess"
    broadcast 0, %{message: "new_time_", uuid: uuid, select: "new_time_"}
    {:noreply, state}
  end

  def handle_info({:work, counter, uuid}, state) do
    IO.puts "Counting..."
    IO.inspect uuid
    IO.inspect counter
    broadcast counter, %{message: "Counting..", uuid: uuid, select: "new_time_"}
    counter = counter - 1
    timer = Process.send_after(self(), {:work, counter, uuid},1_000)
    {:noreply, %{timer: timer}}
  end

  def handle_info({:waiting, 0, uuid}, state) do
    IO.puts "Finished procces of waiting"
    broadcast 0, %{message: "Counting", uuid: uuid, select: "waiting_time_"}
    {:noreply, state}
  end

  def handle_info({:waiting, counter, uuid}, state) do
    IO.puts "waiting..."
    IO.inspect uuid
    IO.inspect counter
    broadcast counter, %{message: "waiting", uuid: uuid, select: "waiting_time_"}
    counter = counter - 1
    timer = Process.send_after(self(), {:waiting, counter, uuid},1_000)
    {:noreply, %{timer: timer}}
  end

  def handle_info({:start_timer, counter, uuid}, state) do
    timer = Process.send_after(self(), {:work, counter, uuid}, 1_000)
    {:noreply, %{timer: timer}}
  end

  def handle_info({:start_timer_waiting, counter, uuid}, state) do
    timer = Process.send_after(self(), {:waiting, counter, uuid}, 1_000)
    {:noreply, %{timer: timer}}
  end
    # So that unhanded messages don't error
  def handle_info(_, state) do
      IO.inspect "No matching any method"
      {:ok, state}
  end

  defp broadcast(time, response) do
    IO.inspect response
    ExTyperacerWeb.Endpoint.broadcast! "timer:update","#{response.select}#{response.uuid}", %{ time: time, response: response.message}
  end

end