# Spring Kafka

## Configuração do kafka

O arquivo kafka/config/zookeeper.properties, linha dataDir= colocar aonde ficarão os logs.
O arquivo kafka/config/server.properties, log.dirs= colocar o diretório onde ficarão os logs.

## Inicialização

Subindo zookeeper:
```
./bin/zookeeper-server-start.sh config/zookeeper.properties
```
Subindo kafka
```
./bin/kafka-server-start.sh config/server.properties 
```

## Criando topics manualmente
```
bin/kafka-topics.sh --create \
  --zookeeper localhost:2181 \
  --replication-factor 1 --partitions 1 \
  --topic mytopic

```
