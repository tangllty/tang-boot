#!/bin/sh

APPLICATION="tang-admin-*.jar"
PID=$(ps -ef | grep $APPLICATION | grep -v grep | awk '{print $2}')

if [ -n "$PID" ]; then
	kill -9 $PID
	echo Application stop success
else
	echo Application not found
fi
