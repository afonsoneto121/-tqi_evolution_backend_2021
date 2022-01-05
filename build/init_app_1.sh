#!/bin/bash

export PORT=3030
export NAME="BANCO A"
echo "Starting $NAME in ${PORT}"
java -jar ../api-banco/build/libs/api-banco-0.0.1-SNAPSHOT.jar
