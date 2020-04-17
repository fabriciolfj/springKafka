package br.com.practice.springkafka.domain.service.messages.consumer;

import br.com.practice.springkafka.domain.model.Greeting;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerMessageGreeting {

    @KafkaListener(topics = "json", containerFactory = "greetingKafkaListenerContainerFactory", groupId = "teste")
    public void listen(final Greeting greeting) {
        System.out.println("Json: " + greeting.toString());
    }
}
