package br.com.practice.springkafka.domain.service.messages.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerMessageFilter {

    /*
    O que for correspondente ao filtro, será descaratada.
    * */
    @KafkaListener(topics = "pratice", containerFactory = "filterKafkaListenerContainerFactory", groupId = "filter")
    public void listen(final String message) {
        System.out.println("Received message: " + message + " ,filtrer.");
    }
}
