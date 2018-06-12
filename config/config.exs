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

#Configures Guardian

config :ex_typeracer, ExTyperacer.Auth.Guardian,
  issuer: "ex_typeracer", # Name of your app/company/product
  secret_key: "AVp1NjzxTCrf5467/cGIjkeRxLOkKp5k1b6z9HjkLX0L3L5f5UVHE9uf3MkxnPNE", # Replace this with the output of the mix command
  serializer: ExTyperacer.GuardianSerializer

# Import environment specific config. This must remain at the bottom
# of this file so it overrides the configuration defined above.

#Config to facebook
config :ueberauth, Ueberauth,
  providers: [
    facebook: {Ueberauth.Strategy.Facebook, []}
  ]

config :ueberauth, Ueberauth.Strategy.Facebook.OAuth,
client_id: System.get_env("FACEBOOK_APP_ID"),
client_secret: System.get_env("FACEBOOK_APP_SECRET")


#Config adapter email

config :ex_typeracer, ExTyperacer.Mail.Mailer,
  adapter: Bamboo.SMTPAdapter,
  server: System.get_env("SES_SERVER"),
  port:  System.get_env("SES_PORT"),
  username: System.get_env("SMTP_USERNAME"),
  password: System.get_env("SMTP_PASSWORD"),
  tls: :if_available, # can be `:always` or `:never`
  allowed_tls_versions: [:"tlsv1", :"tlsv1.1", :"tlsv1.2"], # or {":system", ALLOWED_TLS_VERSIONS"} w/ comma seprated values (e.g. "tlsv1.1,tlsv1.2")
  ssl: true, # can be `true`
  retries: 1

import_config "#{Mix.env}.exs"
