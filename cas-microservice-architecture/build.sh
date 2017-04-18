#!/bin/bash
if [ -z "$JAVA_HOME" ]; then
    echo "JAVA_HOME is not set. Configure JAVA_HOME environment variable. Example: export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64"
    exit 1
fi

SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")

echo "Configuring SSL"
cd $SCRIPT_PATH/ssl && ./configure_ssl.sh
if [ $? -ne 0 ]; then
    echo "Error on Configuring SSL"
    exit 1
fi

echo "Building and Installing CXF CAS Client"
cd $SCRIPT_PATH/cxf-cas-client && ./install.sh
if [ $? -ne 0 ]; then
    echo "Error on Building and Installing CXF CAS Client"
    exit 1
fi

echo "Building and Running CAS Server"
cd $SCRIPT_PATH/cas-server && ./build.sh && ./run.sh
if [ $? -ne 0 ]; then
    echo "Error on Build and Running CAS Server"
    exit 1
fi

echo "Building and Running Product Service"
cd $SCRIPT_PATH/product-service && ./build.sh && ./run.sh
if [ $? -ne 0 ]; then
    echo "Error on Build and Running Product Service"
    exit 1
fi

echo "Building and Running Customer Service"
cd $SCRIPT_PATH/customer-service && ./build.sh && ./run.sh
if [ $? -ne 0 ]; then
    echo "Error on Build and Running Customer Service"
    exit 1
fi

echo "Building and Running Nginx"
cd $SCRIPT_PATH/nginx && ./run.sh
if [ $? -ne 0 ]; then
    echo "Error on Build and Running Nginx"
    exit 1
fi
