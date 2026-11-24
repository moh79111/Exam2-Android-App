@echo off
setlocal
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0tools\prepare-gradle-wrapper.ps1"
if errorlevel 1 (
  echo.
  echo Setup failed. Check your internet connection and try again.
  pause
  exit /b 1
)
echo.
echo Project setup completed. You may now open this folder in Android Studio.
pause
