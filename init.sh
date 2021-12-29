#bin/bashcd

docker-compose -f docker/docker-compose.yml up -d
docker-compose -f api-banco/docker/docker-compose.yml up -d