#!/bin/sh

x=1
fruits='echo $@'
./gradlew run --args="$fruits"