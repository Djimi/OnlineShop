#!/bin/bash

# Function to handle CTRL + C
cleanup() {
    echo "Stopping all processes..."
    kill 0
    exit 0
}

# Trap CTRL + C and call cleanup
trap cleanup SIGINT

# Start 3 parallel curl processes
for i in {1..1}; do
  while true; do
    sleep 0.001  # Sleep for 3 milliseconds
    curl -v --location --request GET 'http://127.0.0.1:8081/inventories/api/version'
  done &
done

# Wait for all background processes
wait

