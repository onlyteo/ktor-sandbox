# Ktor CRUD Exposed

This example shows a CRUD architecture using the Exposed framework.

## Architecture

The example consists of a `Frontend` and a `Backend` application.

```mermaid
graph TD
    A[Ktor Frontend]:::ktor
    B[Ktor Backend]:::ktor
    C[(H2 Database)]:::h2

    A -- REST --> B
    subgraph .
    B -- JDBC --> C
    end

    classDef ktor fill: #8d53f9, stroke: #000000, color: #000000
    classDef h2 fill: #f5f242, stroke: #000000, color: #000000
```

The `Frontend` application communicates with the `Backend` application using REST.
