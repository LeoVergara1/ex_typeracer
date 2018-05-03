defmodule ExTyperacer.TimerServer do

  use GenServer
  require Kernel
  alias ExTyperacer.Logic.{Game, Player}
  
  def start_link() do
    GenServer.start_link(__MODULE__, %{})
  end
  
  def init(state) do
    IO.puts "Inicia timer"
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
    {:noreply, state}
  end

  def handle_info({:work, counter, uuid}, state) do
    IO.puts "Counting..."
    IO.inspect counter
    counter = counter - 1
    timer = Process.send_after(self(), {:work, counter, uuid},1_000)
    {:noreply, %{timer: timer}}
  end


  def handle_info({:start_timer, counter, uuid}, state) do
    IO.inspect "Estoy en el nuevo param"
    timer = Process.send_after(self(), {:work, counter, uuid}, 1_000)
    {:noreply, %{timer: timer}}
  end
    # So that unhanded messages don't error
  def handle_info(_, state) do
      {:ok, state}
  end

end