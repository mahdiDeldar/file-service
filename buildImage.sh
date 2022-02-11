#!/usr/bin/env bash

./gradlew clean bootJar
mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)
docker build -t shervin/file_server:$(./gradlew properties -q | grep version | awk '{print $2}') .
docker tag shervin/file_server:$(./gradlew properties -q | grep version | awk '{print $2}') shervin/file_server:latest
