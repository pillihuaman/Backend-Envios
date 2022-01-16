#!/usr/bin/env sh
chmod +x gradlew
chmod +x gradlew.bat
./gradlew clean pushImage --refresh-dependencies --stacktrace
