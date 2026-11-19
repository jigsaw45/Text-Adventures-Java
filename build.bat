@echo off
setlocal
cd /d "%~dp0"

if exist build rmdir /s /q build
mkdir build\classes

dir /s /b src\*.java > build\sources.txt
javac --release 8 -encoding UTF-8 -d build\classes @build\sources.txt
if errorlevel 1 (
    echo Build failed.
    pause
    exit /b 1
)

jar --create --file TextAdventure.jar --main-class Main -C build\classes .
echo Built TextAdventure.jar
pause
