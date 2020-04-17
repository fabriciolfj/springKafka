package br.com.practice.springkafka.domain.service.messages.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerMessage {

    @KafkaListener(topics = "pratice", groupId = "foo")
    public void listen(final String message) {
        System.out.println("Received message in group foo: " + message);
    }
}
