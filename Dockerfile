FROM maven:3.9.11-eclipse-temurin-17

WORKDIR /app

RUN apt-get update \
  && apt-get install -y --no-install-recommends \
    python3 \
    curl \
  && rm -rf /var/lib/apt/lists/*

COPY pom.xml .

RUN mvn -B dependency:go-offline

COPY src ./src

RUN mkdir -p docker

COPY docker/entrypoint.sh /app/docker/entrypoint.sh

RUN chmod +x /app/docker/entrypoint.sh

ENTRYPOINT ["/app/docker/entrypoint.sh"]