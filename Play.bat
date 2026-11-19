@echo off
setlocal
cd /d "%~dp0"

where java >nul 2>nul
if errorlevel 1 (
    echo Java is not installed.
    echo Install Java 8 or newer from https://adoptium.net then run Play.bat again.
    pause
    exit /b 1
)

java -jar TextAdventure.jar

echo.
pause
