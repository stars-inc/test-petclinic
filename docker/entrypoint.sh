#!/usr/bin/env bash

set +e

echo "Starting API tests..."

mvn -B clean test
TEST_EXIT_CODE=$?

echo "Generating Allure report..."

mvn -B allure:report
REPORT_EXIT_CODE=$?

if [ "$REPORT_EXIT_CODE" -ne 0 ]; then
  echo "Allure report generation failed"
  exit "$REPORT_EXIT_CODE"
fi

if [ ! -f target/site/allure-maven-plugin/index.html ]; then
  echo "Allure index.html was not found"
  exit 1
fi

echo "Allure report is available at http://localhost:8080"

python3 -m http.server 8080 \
  --bind 0.0.0.0 \
  --directory target/site/allure-maven-plugin