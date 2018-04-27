
# ExTyperacer

To start your Phoenix server:

  * Install dependencies with `mix deps.get`
  * Create and migrate your database with `mix ecto.create && mix ecto.migrate`
  * Install Node.js dependencies with `cd assets && npm install`
  * Start Phoenix endpoint with `mix phx.server`

Now you can visit [`localhost:4000`](http://localhost:4000) from your browser.

Ready to run in production? Please [check our deployment guides](http://www.phoenixframework.org/docs/deployment).

## Learn more

  * Official website: http://www.phoenixframework.org/
  * Guides: http://phoenixframework.org/docs/overview
  * Docs: https://hexdocs.pm/phoenix
  * Mailing list: http://groups.google.com/group/phoenix-talk
  * Source: https://github.com/phoenixframework/phoenix

## If you want to deploy without jeinkins, you should have the next steps

  * file in .deliver/config

  ``` java
  APP="ex_typeracer"

BUILD_HOST="35.196.108.175"
BUILD_USER="brandon"
BUILD_AT="/home/brandon/builds"

STAGING_HOSTS="192.168.60.8"
STAGING_USER="vagrant"
TEST_AT="/home/vagrant/releases"

PRODUCTION_HOSTS="35.196.160.161"
PRODUCTION_USER="brandon"
DELIVER_TO="/home/brandon/releases"

pre_erlang_get_and_update_deps() {
  local _prod_secret_path="$WORKSPACE/$APP/config/prod.secret.exs"

  if [ "$TARGET_MIX_ENV" = "prod" ]; then
    status "Copying '$_prod_secret_path' file to build host"
    scp "$_prod_secret_path" "$BUILD_USER@$BUILD_HOST:$BUILD_AT/config/prod.secret.exs"

  fi
}

pre_erlang_clean_compile() {
  local _profile_path="$WORKSPACE/$APP/config/profile"

  if [ "$TARGET_MIX_ENV" = "prod" ]; then
    status "Copying '$_profile_path' file to build host"
    scp "$_profile_path" "$BUILD_USER@$BUILD_HOST:$BUILD_AT/../.profile"

    status "Preparing assets with: brunch build and phoenix.digest"
    __sync_remote "
      set -e
      cd '$BUILD_AT/assets'

      npm install

      node_modules/brunch/bin/brunch build --production

      cd '$BUILD_AT'
      APP='$APP' MIX_ENV='$TARGET_MIX_ENV' $BUILD_CMD phoenix.digest $SILENCE
    "
  fi
}

  ```

  * file in config/profile

  ``` java
  export DATABASE_USERNAME=postgres
  export DATABASE_PASSWORD=postgres
  export DATABASE_NAME=ex_typeracer_dev
  # local machine
  export DATABASE_HOST=localhost
  export DATABASE_PORT=5432

  ```

  * Finally you should running './deploy'

