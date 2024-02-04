#!/bin/bash

TOKEN=$(./keycloak-tokens.sh)

curl -H "Authorization: Bearer ${TOKEN}" \
  -H "X-Request-ID: 1234" http://localhost:8090/v1/create \
  -H "Content-Type: application/json" \
  -d '{"debug":{"mode":"stub","stub":"success"},"ad":{"title":"my title","description":"my description","adType":"demand","visibility":"public","productId":"23423423"}}'
