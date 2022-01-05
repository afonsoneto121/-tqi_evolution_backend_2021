#!/bin/bash

export PORT=3031
export NAME="BANCO B"
echo "Starting $NAME in ${PORT}"
java -jar ../api-banco/build/libs/api-banco-0.0.1-SNAPSHOT.jar
