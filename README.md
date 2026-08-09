# Petclinic API

## Java
openjdk 17.0.10 2024-01-16
OpenJDK Runtime Environment (build 17.0.10+7-Ubuntu-122.04.1)
OpenJDK 64-Bit Server VM (build 17.0.10+7-Ubuntu-122.04.1, mixed mode, sharing)

## Springboot
4.1.0

## Lounch test
```
mvn clean test
```

### Allure reports
```
mvn clean test
mvn allure:report
cd target/site/allure-maven-plugin/

optionaly ->
python3 -m http.server 8080 | <ur browser cli>
```

## docker compose
...