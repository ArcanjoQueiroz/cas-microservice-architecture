#!/bin/bash

SSL_HOME="$HOME/ssl"
CONTAINER_NAME=product-service
VERSION=1.0.0

cp "$SSL_HOME/certificate.jks" .

mvn clean install

docker build -t "alexandre/$CONTAINER_NAME:$VERSION" \
             --build-arg KEYSTORE=certificate.jks \
             --build-arg KEYSTORE_PASSWORD=changeit .

rm certificate.jks
