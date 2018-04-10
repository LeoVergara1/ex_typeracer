defmodule ExTyperacer.Timer do

  use GenServer
  require Logger

  def start_link() do
    GenServer.start_link __MODULE__, %{}
  end

  ## Gen server starts here!

  def init(_state) do
		ExTyperacerWeb.Endpoint.subscribe "timer:start", []
    # Adding state
    state = %{timer_ref: nil, timer: nil}
    {:ok, state}
  end

  def handle_info(:update, %{timer: 0}) do
    broadcast 0, %{message: "Se acabo el tiempo!"}
    {:noreply, %{timer_ref: nil, timer: 0}}
  end

  def handle_info(:update, %{timer: time}) do
    leftover = time-1
    timer_ref = schedule_timer 1_000
    broadcast leftover, %{message: "Contando..."}
    {:noreply, %{timer_ref: timer_ref, timer: leftover}}
  end

	def handle_info(%{event: "start_timer"}, %{timer_ref: old_timer_ref}) do
    cancel_timer(old_timer_ref)
		duration = 3
		timer_ref = schedule_timer 1_000
		broadcast duration, %{message: "Start time", uuid: 12}
		{:noreply, %{timer_ref: timer_ref, timer: duration, uuid: 12}}
	end

  defp schedule_timer(interval) do
    Process.send_after self(), :update, interval
  end

  defp cancel_timer(nil), do: :ok
  defp cancel_timer(ref), do: Process.cancel_timer(ref)

  defp broadcast(time, response) do
    IO.inspect response
    ExTyperacerWeb.Endpoint.broadcast! "timer:update", "new_time", %{ response: response.message, time: time}
  end

end
