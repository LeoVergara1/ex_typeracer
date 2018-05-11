defmodule ExTyperacerWeb.Router do
  use ExTyperacerWeb, :router

  pipeline :browser do
    plug :accepts, ["html"]
    plug :fetch_session
    plug :fetch_flash
    plug :protect_from_forgery
    plug :put_secure_browser_headers
  end

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/", ExTyperacerWeb do
    pipe_through :browser # Use the default browser stack

    get "/", PageController, :index
    get "/racer", PageController, :racer
    post "/", PageController, :login
    post "/new_user", PageController, :new_user
  end

  # Other scopes may use custom stacks.
  # scope "/api", ExTyperacerWeb do
  #   pipe_through :api
  # end
end
