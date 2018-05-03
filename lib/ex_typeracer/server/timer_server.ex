defmodule ExTyperacer.TimerServer do

  use GenServer
  require Kernel
  alias ExTyperacer.Logic.{Game, Player}
  
  def start_link() do
    GenServer.start_link(__MODULE__, %{})
  end
  
  def init(state) do
    IO.puts "Inicia timer"
    #timer = Process.send_after(self(), {:work, counter}, 1_000)
    {:ok, state}
  end

  def handle_call({:reset_timer, counter}, _from, %{timer: timer}) do
    :timer.cancel(timer)
    timer = Process.send_after(self(), {:work, counter}, 1_000)
    {:reply, :ok, %{timer: timer}}
  end

  def handle_info({:work, counter}, state) do
    # Do the{ work, counter} you desire here
    IO.puts "Aquí"
    # Start the timer again
    timer = Process.send_after(self(), {:work, counter},1_000)

    {:noreply, %{timer: timer}}
  end

  def handle_info({:start_timer, counter}, state) do
    IO.inspect "Estoy en el nuevo param"
    timer = Process.send_after(self(), {:work, counter}, 1_000)
    {:noreply, %{timer: timer}}
  end
    # So that unhanded messages don't error
  def handle_info(_, state) do
      {:ok, state}
  end

end