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

## Transação
- um mecanismo no kafka para garantir a entrega de apenas uma vez da mensagem, assim evitando inconsistências no processamento
- dedica ao produtor de mensagem, onde é aberta uma transação, caso alguma exceção não verificada ocorra, a mensagem é revertida.
- fluxo de funcionamento
  - fornecemos um prefixo no transação id
  - o produtor, com base nesse prefixo, é registro no cordenador de transação, que fica dentro de cada broker
  - o produtor envia a mensagem, caso não ocorra nenhuma exceção, ele recebe a confirmação que a transação foi comitada.
  - o consumidor pode optar em receber mensagens comitadas ou não comitadas, no caso de comitadas, apenas mensagens que tiveram sua transação confirmada.
- abaixo as propriedades salientadas, do produtor e consumidor:
````
spring:
  application.name: accounts-service
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      key-deserializer: org.apache.kafka.common.serialization.LongDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: "*"
        isolation.level: read_committed # ler mensagens comitdadas
      enable-auto-commit: false
#      auto-offset-reset: earliest
    producer:
      key-serializer: org.apache.kafka.common.serialization.LongSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
      transaction-id-prefix: tx- #prefixo da transação
      
````

### transação kafka e transação banco de dados
- quando produzimos uma mensagem e em seguida salvamos no banco de dados, ou salvamos no banco de dados e produzimos uma mensagem, tanto a transação kafka e da base de dados são as mesmas, para isso utilize a anotação com o parâmetro abaixo:
````
    @KafkaListener(
            id = "transactions",
            topics = "transactions",
            groupId = "a",
            concurrency = "3")
    @Transactional("kafkaTransactionManager") -> anotação
    public void listen(Order order) {
        LOG.info("Received: {}", order);
        process(order);
    }
````
- quando consumimos uma mensagem e não produzimos outra, apenas salvamos no banco de dados como parte do processo, temos apenas a transação da base de dados para configurar.
````
    @KafkaListener(
            id = "orders",
            topics = "orders",
            groupId = "a",
            concurrency = "3")
    @Transactional("transactionManager") -> anotação
    public void listen(Order order) 
````
### instancias zumbis
- diante o uso de transação, o prefixo fornecido pelo produtor é adicionado por um incremento, por exemplo: tx-1.
- quando a aplicação produra cai e uma nova instância é inicializada
- o identificador do produtor no coordenador da transação muda para tx-2 por exemplo
- e os produtos registrados com id antigo, são finalizados e suas mensagens não serão entregues.
- uma forma de controlar produtos registrados no coordenador defazados
- um produto que ocorre quando temos as aplicações rodando em um k8s
  
