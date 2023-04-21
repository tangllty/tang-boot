#!/bin/sh

VERSION=1.5.2
APPLICATION=tang-admin-${VERSION}.jar
PID=$(ps -ef | grep $APPLICATION | grep -v grep | awk '{print $2}')

if [ -n "$PID" ]; then
	kill -9 $PID
	echo Application stop success
else
	echo Application not found
fi
