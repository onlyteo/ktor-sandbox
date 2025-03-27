# Docker Development Environment

### Start containers
```shell
docker compose -f ./kafka/docker-compose.yaml up -d
```

### Stop containers
```shell
docker compose -f ./kafka/docker-compose.yaml stop
```

### Delete containers
```shell
docker compose -f ./kafka/docker-compose.yaml down -v
```
