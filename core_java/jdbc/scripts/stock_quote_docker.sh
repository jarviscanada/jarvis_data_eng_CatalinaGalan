#!/bin/bash

# Set base_path
base_path="/usr/local/app/jdbc"

# Set the password environment variable for psql
export PGPASSWORD=$POSTGRES_PASSWORD

# Execute the SQL files to set up database
psql -h localhost -U $POSTGRES_USER -d postgres -f "$base_path/sql/stockquote/stock_quote_database.sql"
psql -h localhost -U $POSTGRES_USER -d stock_quote -f "$base_path/sql/stockquote/quote.sql"
psql -h localhost -U $POSTGRES_USER -d stock_quote -f "$base_path/sql/stockquote/position.sql"

# Unset the password environment variable for security
unset PGPASSWORD

echo "Database setup completed successfully."

# Run application
java -jar $base_path/lib/jdbc.jar

