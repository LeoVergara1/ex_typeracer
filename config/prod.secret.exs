use Mix.Config

# In this file, we keep production configuration that
# you'll likely want to automate and keep away from
# your version control system.
#
# You should document the content of this
# file or create a script for recreating it, since it's
# kept out of version control and might be hard to recover
# or recreate for your teammates (or yourself later on).
config :ex_typeracer, ExTyperacerWeb.Endpoint,
  secret_key_base: "8dM1B4Cv1fo/cxKkpd6PzxlHIsQxyet0GjJxiNwCm68mhH8V24HdK5SWRZ5uJbzz"

# Configure your database
config :ex_typeracer, ExTyperacer.Repo,
  adapter: Ecto.Adapters.Postgres,
  username: "postgres",
  password: "postgres",
  database: "ex_typeracer_dev",
  hostname: "localhost",
  pool_size: 15
