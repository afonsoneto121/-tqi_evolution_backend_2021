#!/bin/bash

docker-compose -f docker/docker-compose.yml up -d
docker-compose -f api-banco/docker/docker-compose.yml up -d
docker-compose -f transfer/docker/docker-compose.yml up -d