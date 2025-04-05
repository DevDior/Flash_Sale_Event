#!/bin/bash

docker rm -f flash-sale-container
docker build -t flash-sale-event .
docker run -d \
  --name flash-sale-container \
  -p 8080:8080 \
  flash-sale-event
