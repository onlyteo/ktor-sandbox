# Ktor Kafka CQRS

This example shows a CQRS architecture using Ktor applications and messaging using Apache Kafka.

## Prerequisites

* Java Runtime - e.g. [Temurin JDK](https://adoptium.net), [OpenJDK](https://openjdk.org) or [Oracle JDK](https://www.oracle.com/java)
* [NodeJS Runtime](https://nodejs.org)
* [NPM](https://www.npmjs.com) or [Yarn](https://yarnpkg.com)
* [Docker](https://www.docker.com)

## Run

Start Kafka broker:
```bash
docker compose -f ../docker/kafka/docker-compose.yml up -d
```

Start Backend application:
```bash
../gradlew :ktor-kafka-cqrs:backend:run
```

Start Frontend API application:

```bash
../gradlew :ktor-kafka-cqrs:frontend-api:run
```

Start Frontend application (this should open a browser window):
```bash
yarn --cwd ./frontend install
yarn --cwd ./frontend start
```

## Architecture

```mermaid
graph TD
    subgraph Kafka
        E[Person Topic]:::kafka
        F[Greeting Topic]:::kafka
    end
    subgraph Apps
        A[React Frontend]:::react
        B[Ktor Frontend API]:::ktor
        C[Ktor Backend]:::ktor
    end

    A -- REST --> B
    B --> E
    E --> C
    C --> F
    F --> B

    classDef react fill: #58c4dc, stroke: #000000, color: #000000
    classDef ktor fill: #8d53f9, stroke: #000000, color: #000000
    classDef kafka fill: #ffa500, stroke: #000000, color: #000000
```

### Backend

The Backend is a REST API application based on Ktor.

### Frontend API

The Frontend API is a REST API application based on Ktor.

### Frontend

The Frontend is a JavaScript web application based on ReactJS and using the React Bootstrap framework.