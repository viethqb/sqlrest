::	
:: Author: tang	
:: Date: 2024-03-06
::	
@echo off
title sqlrest-executor
setlocal enabledelayedexpansion
cls

:: Java class to start
set APP_MAINCLASS=org.dromara.sqlrest.executor.ExecutorApplication

:: Java application root directory
set APP_HOME=%~dp0
set APP_HOME=%APP_HOME%\..\
cd %APP_HOME%
set APP_HOME=%cd%

set APP_BIN_PATH=%APP_HOME%\bin
set APP_LIB_PATH=%APP_HOME%\lib\common
set APP_EXT_PATH=%APP_HOME%\lib\webmvc
set APP_ROLE_PATH=%APP_HOME%\lib\executor
set APP_CONF_PATH=%APP_HOME%\conf\executor
set APP_DRIVERS_PATH=%APP_HOME%\drivers

:: Read configuration file parameters
for /f "delims=" %%i in ('type "%APP_HOME%\conf\config.ini"^| find /i "="') do set %%i

:: Set DEBUG port
set DEBUG_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=18092
:: Java virtual machine startup parameters
set JAVA_OPTS=-server -Xms4096m -Xmx4096m -Xmn2048m -XX:+DisableExplicitGC %DEBUG_OPTS% -Djava.awt.headless=true -Dfile.encoding=UTF-8 -Doracle.jdbc.J2EE13Compliant=true

:: Print environment information
echo System Information:
echo ********************************************************
echo COMPUTERNAME=%COMPUTERNAME%
echo OS=%OS%
echo.
echo APP_HOME=%APP_HOME%
echo APP_MAINCLASS=%APP_MAINCLASS%
echo CLASSPATH=%APP_CONF_PATH%;%APP_LIB_PATH%\*;%APP_EXT_PATH%\*;%APP_ROLE_PATH%\*
echo CURRENT_DATE=%date% %time%:~0,8%
echo ********************************************************

:: Execute java
echo Starting %APP_MAINCLASS% ...
echo java -classpath %APP_CONF_PATH%;%APP_LIB_PATH%\*;%APP_EXT_PATH%\*;%APP_ROLE_PATH%\* %JAVA_OPTS% %APP_MAINCLASS%
echo .
java -classpath %APP_CONF_PATH%;%APP_LIB_PATH%\*;%APP_EXT_PATH%\*;%APP_ROLE_PATH%\* %JAVA_OPTS% %APP_MAINCLASS%

:exit
pause
