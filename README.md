# Ktor Sandbox
This repository contain sandbox projects to showcase features of the Ktor framework.

## Use case
Most examples implement a "hello world" style logic that returns a greeting message when the user inputs a name.

* A user inputs the name "John" and clicks "Submit"
* The system generates a greeting "Hello John!" back to the user

The examples typically consists of a "frontend" and a "backend" application. If the frontend is a JavaScript
application then there is often also a "frontend API" application.

```mermaid
graph TD
    A1[Ktor Frontend]
    B1[Ktor Backend]

    A2[React Frontend]
    B2[Ktor Frontend API]
    C2[Ktor Backend]

    A1:::ktor --> B1:::ktor
    
    A2:::react --> B2:::ktor
    B2:::ktor --> C2:::ktor
    
    classDef react fill: #58c4dc, stroke: #000000, color: #000000
    classDef ktor fill: #6373ff, stroke: #000000, color: #000000
    classDef spring fill: #80ea6e, stroke: #000000, color: #000000
    classDef oauth2 fill: #c98979, stroke: #000000, color: #000000
```

## Examples
Read details about the examples in their respective project roots.

* [CRUD Exposed](./ktor-crud-exposed)
* [OAuth2 Client React](./ktor-oauth2-client-react)
* [OTEL Observability](./ktor-otel-observability)
* [Kafka CQRS](./ktor-kafka-cqrs)
