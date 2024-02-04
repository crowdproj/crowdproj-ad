#!/bin/bash

JKS_PASS=app123456
APP_NAME=crowdproj-ad

# Create directories
mkdir -p ca
mkdir -p envoy/certs
mkdir -p app/certs
mkdir -p keycloak/certs

# Generate CA private key
openssl genpkey -algorithm RSA -out ca/ca.key

# Generate CA certificate
openssl req -x509 -new -nodes -key ca/ca.key -sha256 -days 365 -out ca/ca.crt -subj "/CN=my_ca"

# envoy certificates ----------------------------------------------------

# Generate envoy_sidecar private key
openssl genpkey -algorithm RSA -out envoy/certs/envoy_sidecar.key

# Generate a certificate signing request (CSR) for envoy_sidecar
openssl req -new -key envoy/certs/envoy_sidecar.key -out envoy/certs/envoy_sidecar.csr -subj "/CN=envoy_sidecar"

# Sign the CSR with the CA to get the envoy_sidecar certificate
openssl x509 -req -in envoy/certs/envoy_sidecar.csr -CA ca/ca.crt -CAkey ca/ca.key -CAcreateserial -out envoy/certs/envoy_sidecar.crt -days 365 -sha256

# app certificates ----------------------------------------------------

# Generate app private key
openssl genpkey -algorithm RSA -out app/certs/$APP_NAME.key

# Generate a CSR for app
openssl req -new -key app/certs/$APP_NAME.key -out app/certs/$APP_NAME.csr -subj "/CN=$APP_NAME"

# Sign the CSR with the CA to get the app certificate
openssl x509 -req -in app/certs/$APP_NAME.csr -CA ca/ca.crt -CAkey ca/ca.key -CAcreateserial -out app/certs/$APP_NAME.crt -days 365 -sha256

openssl pkcs12 -export -out app/certs/$APP_NAME.p12 -inkey app/certs/$APP_NAME.key -in app/certs/$APP_NAME.crt -name $APP_NAME -passout pass:$JKS_PASS
keytool -v -importkeystore -srckeystore app/certs/$APP_NAME.p12 -srcstoretype pkcs12 -destkeystore app/certs/$APP_NAME.jks -deststoretype JKS -srcstorepass $JKS_PASS -deststorepass $JKS_PASS

# keycloack certificates ----------------------------------------------------

# Generate Keycloak private key and certificate
openssl genpkey -algorithm RSA -out keycloak/certs/keycloak.key
openssl req -new -key keycloak/certs/keycloak.key -out keycloak/certs/keycloak.csr -subj "/CN=keycloak"
openssl x509 -req -in keycloak/certs/keycloak.csr -CA ca/ca.crt -CAkey ca/ca.key -CAcreateserial -out keycloak/certs/keycloak.crt -days 365 -sha256

# Convert Keycloak certificates to PKCS12 format
openssl pkcs12 -export -out keycloak/certs/keycloak.p12 -inkey keycloak/certs/keycloak.key -in keycloak/certs/keycloak.crt -name keycloak -passout pass:$JKS_PASS

# Convert PKCS12 to JKS
keytool -importkeystore -srckeystore keycloak/certs/keycloak.p12 -srcstoretype pkcs12 -destkeystore keycloak/certs/keycloak.jks -deststoretype JKS -srcstorepass $JKS_PASS -deststorepass $JKS_PASS

echo "Certificates generated successfully!"
