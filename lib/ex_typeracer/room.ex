defmodule ExTyperacer.Room do
  
  def greet do
    receive do
      { pid, x } when is_bitstring(x) -> 
        IO.puts x
        send pid, "Everuthing is fine"
        greet()
      {pid, :death, reason} ->
        send pid, "Everuthing os wrong"
        IO.puts "bye for #{reason}"
      _ -> 
          IO.puts "No entiendo"
          greet()
    end
  end

  def ping do
    receive do
      { pid, msg } ->
        IO.inspect msg
        #send pid, [ pid: self(), msg: "ping"]
        #send pid, {pid, msg}
        #pid2 = ExTyperacer.Room, :pong, []
        send pid, { self(), "Ping"}
        ping()
      _ -> 
          IO.puts "No entiendo ping"
          ping()
    end
  end

  def pong do
    receive do
      { pid, msg }  ->
        IO.inspect msg
        send pid, { self(), "Pong"}
        pong()
      _ -> 
          IO.puts "No entiendo pong"
          pong()
    end
  end

end