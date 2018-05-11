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

  pipeline :maybe_browser_auth do
    plug(Guardian.Plug.VerifySession)
    plug(Guardian.Plug.VerifyHeader, realm: "Bearer")
    plug(Guardian.Plug.LoadResource)
  end
  
  pipeline :ensure_authed_access do
    plug(Guardian.Plug.EnsureAuthenticated, %{"typ" => "access", handler: MyApp.HttpErrorHandler})
  end

  scope "/", ExTyperacerWeb do
    pipe_through :browser # Use the default browser stack

    get "/", PageController, :index
    get "/racer", PageController, :racer
    post "/new_user", PageController, :new_user
  end

  # Other scopes may use custom stacks.
  # scope "/api", ExTyperacerWeb do
  #   pipe_through :api
  # end
end
