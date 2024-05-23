@echo off

rem Into the root directory of the project
cd %~dp0..

echo Building the application...
mvn clean package -Dmaven.test.skip=true -Pprod
echo Application built successfully
