#!/bin/bash

SSL_HOME="$HOME/ssl"

CONTAINER_NAME=docker-nginx

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
           -p 80:80 \
           -p 443:443 \
           -v "$PWD/conf/default.conf":/etc/nginx/conf.d/default.conf \
           -v "$SSL_HOME":/etc/nginx/ssl \
           -d nginx
fi
