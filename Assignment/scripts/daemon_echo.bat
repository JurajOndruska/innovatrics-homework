@echo off
echo Started (%TIME%): %1
:loop
timeout -t 5 /nobreak > nul 2>&1
echo Loop (%TIME%): %2
goto loop