# Ktor REST API

This example shows a React frontend and Ktor REST API.

## Prerequisites

* Java Runtime - e.g. [Temurin JDK](https://adoptium.net) or [OpenJDK](https://openjdk.org)
* [NodeJS Runtime](https://nodejs.org)
* [NPM](https://www.npmjs.com) or [Yarn](https://yarnpkg.com)

## Run

Start Backend application:
```bash
../../gradlew :apps:ktor-api-rest:backend:run
```

Start Frontend API application:

```bash
../../gradlew :apps:ktor-api-rest:frontend-api:run
```

Start Frontend application (this should open a browser window):
```bash
yarn --cwd ./frontend install
yarn --cwd ./frontend start
```

## Architecture

```mermaid
graph TD
    A[React Frontend]:::react
    B[Ktor Frontend API]:::ktor
    C[Ktor Backend]:::ktor

    A -- REST --> B
    B -- REST --> C

    classDef react fill: #58c4dc, stroke: #000000, color: #000000
    classDef ktor fill: #8d53f9, stroke: #000000, color: #000000
```

### Backend

The Backend is a REST API application based on Ktor.

### Frontend API

The Frontend API is a REST API application based on Ktor.

### Frontend

The Frontend is a JavaScript web application based on ReactJS and using the React Bootstrap framework.
