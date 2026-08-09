#!/usr/bin/env bash

set -euo pipefail

cleanup() {
  echo "Stopping containers..."
  docker compose down --remove-orphans
}

trap cleanup EXIT

docker compose up --build -d

echo "Waiting for Allure report..."

until curl --silent --fail http://localhost:8080/index.html > /dev/null; do
  sleep 1
done

echo "Opening Allure report..."

PROFILE_DIR="$(mktemp -d)"

chromium \
  --user-data-dir="$PROFILE_DIR" \
  --no-first-run \
  --disable-default-apps \
  --app=http://localhost:8080 \
  &

BROWSER_PID=$!

wait "$BROWSER_PID"