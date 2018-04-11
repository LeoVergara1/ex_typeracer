p = spawn fn ->
  receive do
    x -> IO.puts x
  end
end