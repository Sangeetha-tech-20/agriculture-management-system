@echo off
echo ===========================================
echo   Agriculture Management System Startup
echo ===========================================
echo.

echo Starting MySQL Database...
echo (Make sure MySQL is running)
echo.

echo Starting Backend Server...
cd /d "C:\Users\ELCOT\Desktop\final oose"
start "Backend Server" cmd /k "mvn spring-boot:run"

echo Waiting for backend to start...
timeout /t 15 /nobreak > nul

echo.
echo Starting Frontend Server...
start "Frontend Server" cmd /k "node server.js"

echo.
echo ===========================================
echo   Servers Starting...
echo ===========================================
echo.
echo Backend will be available at: http://localhost:8080
echo Frontend will be available at: http://localhost:3000
echo.
echo IMPORTANT: Open index.html and check if API_BASE_URL is set to:
echo http://localhost:8080/api
echo.
echo Press any key to continue...
pause > nul