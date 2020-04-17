package br.com.practice.springkafka.domain.service.messages.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ConsomerMessageHeaders {

    /*
    Pode consumir dados de uma partição em específico, conforme o exemplo abaixo:
    @KafkaListener(
          topicPartitions = @TopicPartition(topic = "topicName",
          partitionOffsets = {
            @PartitionOffset(partition = "0", initialOffset = "0"),
            @PartitionOffset(partition = "3", initialOffset = "0")
        }))
     obs: sempre que o serviço for inicilizado, caso o offset seja maior que 0, este consumirá os dados de 0 ate o ultimo offset sempre.

     Para consumer partições específicas, sem levar em consideração o deslocamento, utilizamos:
     @KafkaListener(topicPartitions = @TopicPartition(topic = "topicName", partitions = { "0", "1" }))
    * */
    @KafkaListener(topics = "pratice", groupId = "customer")
    public void listenWithHeaders(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println("Received Message: " + message + "from partition: " + partition + " GroupId: customer");
    }
}
