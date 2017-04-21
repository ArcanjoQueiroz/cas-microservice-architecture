#!/bin/bash
if [ -z "$JAVA_HOME" ]; then
    echo "JAVA_HOME is not set. Configure JAVA_HOME environment variable. Example: export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64"
    exit 1
fi

SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")

echo "Running CAS Server"
cd $SCRIPT_PATH/cas-server && ./run.sh
if [ $? -ne 0 ]; then
    echo "Error on Running CAS Server"
    exit 1
fi

echo "Running Product Service"
cd $SCRIPT_PATH/product-service && ./run.sh
if [ $? -ne 0 ]; then
    echo "Error on Running Product Service"
    exit 1
fi

echo "Running Customer Service"
cd $SCRIPT_PATH/customer-service && ./run.sh
if [ $? -ne 0 ]; then
    echo "Error on Running Customer Service"
    exit 1
fi

echo "Running Nginx"
cd $SCRIPT_PATH/nginx && ./run.sh
if [ $? -ne 0 ]; then
    echo "Error on Running Nginx"
    exit 1
fi
