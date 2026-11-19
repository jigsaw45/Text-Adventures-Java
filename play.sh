#!/usr/bin/env sh
cd "$(dirname "$0")" || exit 1

if ! command -v java >/dev/null 2>&1; then
    echo "Java is not installed. Install Java 8 or newer from https://adoptium.net"
    exit 1
fi

java -jar TextAdventure.jar
