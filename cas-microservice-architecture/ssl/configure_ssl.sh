#!/bin/bash

# Certificate path
CERTIFICATE_FILE_NAME="certificate"

SSL_HOME="$HOME/ssl"

KEY_OUT="$SSL_HOME/$CERTIFICATE_FILE_NAME.key"
CSR_OUT="$SSL_HOME/$CERTIFICATE_FILE_NAME.csr"
CRT_OUT="$SSL_HOME/$CERTIFICATE_FILE_NAME.crt"

P12_OUT="$SSL_HOME/$CERTIFICATE_FILE_NAME.p12"
P12_NAME="alexandre.com.br"
P12_PASSWORD="changeit"

JKS_OUT="$SSL_HOME/$CERTIFICATE_FILE_NAME.jks"
JKS_ALIAS="alexandre.com.br"
JKS_PASSWORD="changeit"

# Certificate info
COUNTRY="BR"
STATE="Sao Paulo"
LOCATION="Sao Paulo"
ORGANIZATION="Alexandre Enterprises"
ORGANIZATION_UNIT="Departamento de Pesquisa e Desenvolvimento"
COMMON_NAME="*.alexandre.com.br"

# Keystore Info
KEYSTORE=$JAVA_HOME/jre/lib/security/cacerts
KEYSTORE_PASSWORD="changeit"
KEYSTORE_ALIAS=$COMMON_NAME

clean() {
    SSL_HOME="$HOME/ssl"
    echo "Removing old SSL_HOME"
    rm -rf "$SSL_HOME"

    echo "Creating SSL directory"
    mkdir -p "$SSL_HOME"
}

generate_certificate() {
    openssl req -nodes -newkey rsa:2048 \
                -keyout "$KEY_OUT" \
                -out "$CSR_OUT" \
                -subj "/C=$COUNTRY/ST=$STATE/L=$LOCATION/O=$ORGANIZATION/OU=$ORGANIZATION_UNIT/CN=$COMMON_NAME"
    openssl x509 -req \
                 -days 365 -in "$CSR_OUT" -signkey "$KEY_OUT" -out "$CRT_OUT"
}

import_certificate_into_keystore() {
    remove_certificate_from_keystore
    sudo $JAVA_HOME/bin/keytool -import \
                                -trustcacerts \
                                -file "$CRT_OUT" \
                                -alias "$KEYSTORE_ALIAS" \
                                -keystore "$KEYSTORE" \
                                -storepass "$KEYSTORE_PASSWORD" \
                                -noprompt
    verify_certificate_from_keystore
}

remove_certificate_from_keystore() {
    sudo $JAVA_HOME/bin/keytool -delete \
                                -alias "$KEYSTORE_ALIAS" \
                                -keystore "$KEYSTORE" \
                                -storepass "$KEYSTORE_PASSWORD"
}

verify_certificate_from_keystore() {
    sudo $JAVA_HOME/bin/keytool -list -v \
                                -keystore "$KEYSTORE" \
                                -alias "$KEYSTORE_ALIAS" \
                                -storepass "$KEYSTORE_PASSWORD"
    if [ $? -eq 1 ]; then
        echo "Ocorreu um erro ao gerar o certificado ssl de alias $KEYSTORE_ALIAS na keystore $KEYSTORE"
    fi
}

convert_key_crt_to_p12() {
    openssl pkcs12 -export \
                   -name "$P12_NAME" \
                   -in "$CRT_OUT" \
                   -inkey "$KEY_OUT" \
                   -out "$P12_OUT" \
                   -password "pass:$P12_PASSWORD"
}

convert_p12_to_jks() {
    sudo $JAVA_HOME/bin/keytool \
                    -importkeystore \
                    -destkeystore "$JKS_OUT" \
                    -deststorepass "$JKS_PASSWORD" \
                    -srckeystore "$P12_OUT" \
                    -srcstoretype pkcs12 \
                    -srcstorepass "$P12_PASSWORD" \
                    -alias "$JKS_ALIAS"
}

clean
generate_certificate
import_certificate_into_keystore
convert_key_crt_to_p12
convert_p12_to_jks
