#!/usr/bin/env bash

here=$(dirname "$0")

cd "$here/.."

sonar-scanner \
  -Dsonar.projectKey=todomvc-ddd-kotlin \
  -Dsonar.sources=. \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=b414437718d349f44f6e945043130b6ede0f1403
