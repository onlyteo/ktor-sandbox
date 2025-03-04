# Docker

```shell
docker compose -f ./kafka/docker-compose.yml up -d
```

```shell
docker compose -f ./kafka/docker-compose.yml stop
```

```shell
docker compose -f ./kafka/docker-compose.yml rm -s -v -f
```

```shell
docker volume rm sandbox.kafka-data sandbox.kafka-secrets sandbox.kafka-init-data sandbox.kafka-init-secrets
```
