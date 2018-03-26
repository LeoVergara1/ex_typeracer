# This file is responsible for configuring your application
# and its dependencies with the aid of the Mix.Config module.
#
# This configuration file is loaded before any dependency and
# is restricted to this project.
use Mix.Config

# General application configuration
config :ex_typeracer,
  ecto_repos: [ExTyperacer.Repo]

# Configures the endpoint
config :ex_typeracer, ExTyperacerWeb.Endpoint,
  url: [host: "localhost"],
  secret_key_base: "lxMN/1bBaGFkouBrWSBrfC/rkBWWv0sFaW44ytAsQcgR5HNsr/SQNa3zyX0VCtqy",
  render_errors: [view: ExTyperacerWeb.ErrorView, accepts: ~w(html json)],
  pubsub: [name: ExTyperacer.PubSub,
           adapter: Phoenix.PubSub.PG2]

# Configures Elixir's Logger
config :logger, :console,
  format: "$time $metadata[$level] $message\n",
  metadata: [:user_id]

# Import environment specific config. This must remain at the bottom
# of this file so it overrides the configuration defined above.
import_config "#{Mix.env}.exs"
