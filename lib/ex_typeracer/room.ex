defmodule ExTyperacer.Room do

  alias ExTyperacer.Structs.Game
  
  def start(%Game{} = game) do
    spawn ExTyperacer.Room, :handle, [game]
  end

  def add_player(game_server, username) do
    send game_server, { self(), :add_player, "brandon" }
  end

  def handle(%Game{} = game) do
    receive do
      {pid, :add_player, username} ->
        IO.puts "Adding player"
        game_updated = Game.add_player game, username
        send pid, {:add_player, :ok, username}
        handle(game_updated)
      {pid, :show_game} ->
        IO.puts "Showing the game"
        send pid, {:current_game, game}
        handle(game)
      {pid, :death, reason} ->
        send pid, "Game Over"
        IO.puts "Game Over #{inspect pid}"
      _ -> 
          IO.puts "Game Server is not understanding"
          handle(game)
    end
  end

  def mapTest do
    receive do
      { pid, x }  -> 
        IO.inspect x
        send pid, "Everuthing is fine"
        #greet() 
      {pid, :death, reason} ->
        send pid, "Everuthing os wrong"
        IO.puts "bye for #{reason}"
      _ ->
          IO.puts "No entiendo"
          #greet()
    end
  end

  def ping do
    receive do
      { pid, msg } ->
        IO.inspect msg
        #send pid, [ pid: self(), msg: "ping"]
        #send pid, {pid, msg}
        #pid2 = spawn ExTyperacer.Room, :pong, []
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