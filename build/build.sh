#!/bin/bash
echo "Building gradlew api-banco"
cd ../api-banco/ && ./gradlew build -x test && cd ../build
echo "Finished gradlew api-banco"
echo "Building gradlew transfer"
cd ../transfer/ && ./gradlew build -x test && cd ../build
echo "Finished gradlew transfer"


