defmodule ExTyperacerWeb.Router do
  use ExTyperacerWeb, :router

  pipeline :browser do
    plug :accepts, ["html"]
    plug :fetch_session
    plug :fetch_flash
    plug :protect_from_forgery
    plug :put_secure_browser_headers
  end

  pipeline :auth do
    plug ExTyperacer.Auth.Pipeline
  end

  pipeline :ensure_auth do
    plug Guardian.Plug.EnsureAuthenticated
  end

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/", ExTyperacerWeb do
    pipe_through [:browser, :auth] # Use the default browser stack

    get "/", PageController, :index
    get "/racer/:name_rom", PageController, :racer
    get "/racer", PageController, :racer
    post "/", PageController, :login
    post "/logout", PageController, :logout
    post "/new_user", PageController, :new_user
    get "/recovery/:token/:username", PageController, :recovery
    post "/restore_pass", PageController, :restore_pass
  end

  scope "/ranking", ExTyperacerWeb do
    pipe_through [:browser, :auth] # Use the default browser stack

    get "/", RankingController, :index
  end


  scope "/auth", ExTyperacerWeb do
    pipe_through [:browser, :auth] # Use the default browser stack

    get "/:provider", AuthController, :request
    get "/:provider/callback", AuthController, :callback
  end

  scope "/auth", ExTyperacerWeb do
    pipe_through [:browser, :auth] # Use the default browser stack

    get "/:provider", AuthController, :request
    get "/:provider/callback", AuthController, :callback
  end

  scope "/login", ExTyperacerWeb do
    pipe_through [:browser, :auth] # Use the default browser stack
    get "/", LoginController, :index
    post "/", LoginController, :login
  end

  # Other scopes may use custom stacks.
  # scope "/api", ExTyperacerWeb do
  #   pipe_through :api
  # end
end
