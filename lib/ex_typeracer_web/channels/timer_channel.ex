defmodule ExTyperacerWeb.TimerChannel do

  use Phoenix.Channel

  def join("timer:update", _msg, socket) do
    IO.puts(" Alguien se conecto al timer channel!!!!!! ")
    {:ok, socket}
  end

  def handle_in("new_time", msg, socket) do
    push socket, "new_time", msg
		IO.puts "=======> =====> ========> New Timer Handle!!"
    {:noreply, socket}
  end

	def handle_in("start_timer", _, socket) do
		IO.puts "=======> =====> ========> Start Timer!!"
		ExTyperacerWeb.Endpoint.broadcast("timer:start", "start_timer", %{})
		{:noreply, socket}
	end

end
