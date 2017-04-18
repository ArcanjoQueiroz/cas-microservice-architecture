#!/bin/bash

CONTAINER_NAME=customer-service
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
           -p 9090:8080 \
           -p 9443:8443 \
           -p 9590:9999 \
           -d "alexandre/$CONTAINER_NAME:$VERSION"
fi
