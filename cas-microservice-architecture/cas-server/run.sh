#!/bin/bash

CONTAINER_NAME=cas-server
VERSION=1.0.0

ROUTE=$(ip route get 255.255.255.255)
if [ $? -ne 0 ]; then
    echo 'Network is unreacheable'
fi
IP=$(echo $ROUTE | awk '{print $6}')

if [ $? -eq 0 ]; then
    docker ps -a | grep "$CONTAINER_NAME"
    if [ $? -eq 0 ]; then
        docker stop "$CONTAINER_NAME"
        docker rm "$CONTAINER_NAME"
    fi

    docker run --name "$CONTAINER_NAME" \
           --add-host portal.alexandre.com.br:$IP \
           --add-host api.alexandre.com.br:$IP \
           --user jetty \
           -p 8080:8080 \
           -p 8443:8443 \
           -p 8580:9999 \
           -d "alexandre/$CONTAINER_NAME:$VERSION"
fi
