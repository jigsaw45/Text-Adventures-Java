#!/usr/bin/env sh
set -e
cd "$(dirname "$0")"

rm -rf build
mkdir -p build/classes

find src -name '*.java' > build/sources.txt
javac --release 8 -encoding UTF-8 -d build/classes @build/sources.txt
jar --create --file TextAdventure.jar --main-class Main -C build/classes .

echo "Built TextAdventure.jar"
