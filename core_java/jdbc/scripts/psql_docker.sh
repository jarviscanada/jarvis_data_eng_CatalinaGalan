#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e
# set environment vars from .env file
source .env

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
  echo "Docker is not running. Please start Docker and try again."
  exit 1
fi

# Check if the container already exists
docker container inspect jrvs-psql &> /dev/null
container_status=$?

# If the container exists, start it
if [ $container_status -eq 0 ]; then
    echo "Starting existing container jrvs-psql"
    docker start jrvs-psql
else
    # Ensure necessary environment variables are set
    : "${POSTGRES_USER:?Need to set POSTGRES_USER}"
    : "${POSTGRES_PASSWORD:?Need to set POSTGRES_PASSWORD}"

    # Create Docker volume and run the PostgreSQL container
    docker volume create pgdata
    docker run --name jrvs-psql -e POSTGRES_USER=$POSTGRES_USER -e POSTGRES_PASSWORD=$POSTGRES_PASSWORD -d \
     -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine

    # Wait for PostgreSQL to start
    echo "Waiting for PostgreSQL to start..."
    until docker exec jrvs-psql pg_isready -U $POSTGRES_USER -d postgres; do
      sleep 1
    done
fi

# Set the password environment variable for psql
export PGPASSWORD=$POSTGRES_PASSWORD

# Set base_path depending on environment
if [ -f "/.dockerenv" ]; then
  # Inside Docker
  base_path="/usr/local/app/jdbc"
else
  # Local environment
  base_path="$(dirname "$0")/../sql/stockquote"
fi

# Execute the SQL files
psql -h localhost -U $POSTGRES_USER -d postgres -f "$base_path/stock_quote_database.sql"
psql psql -h localhost -U $POSTGRES_USER -d stock_quote -f "$base_path/quote.sql"
psql -h localhost -U $POSTGRES_USER -d stock_quote -f "$base_path/position.sql"

# Unset the password environment variable for security
unset PGPASSWORD

echo "Database setup completed successfully."

# Define a cleanup function to stop the container
cleanup() {
  echo
  echo "Stopping container jrvs-psql"
  docker stop jrvs-psql
}

# Trap script exit to ensure the container is stopped
trap cleanup EXIT

# Run your Java application
java -jar target/jdbc*.jar
