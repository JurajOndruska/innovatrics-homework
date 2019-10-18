@echo off
set loopcount=%2
echo Started (%TIME%): %1
:loop
timeout -t 1 /nobreak > nul 2>&1
echo Loop (%TIME%): %loopcount%
set /a loopcount=loopcount-1
if %loopcount%==0 goto exitloop
goto loop
:exitloop
echo Finished (%TIME%): %3