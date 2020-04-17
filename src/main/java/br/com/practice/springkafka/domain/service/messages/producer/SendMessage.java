package br.com.practice.springkafka.domain.service.messages.producer;

import br.com.practice.springkafka.domain.model.Greeting;
import br.com.practice.springkafka.domain.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMessage {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, Greeting> greetingKafkaTemplate;

    @Value("${message.topic.name}")
    private String topic;

    public void sendMessage(final Message message) {
        var future = kafkaTemplate.send(topic, message.toString());
        future.addCallback(result -> {
            System.out.println("Sent message=[" + message.toString() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
        }, error -> {
            System.out.println("Unable to send message=["
                    + message.toString() + "] due to : " + error.getMessage());
        });
    }

    public void sendMessage(final Greeting greeting) {
        var future = greetingKafkaTemplate.send("json", greeting);
        future.addCallback(result -> {
            System.out.println("Sent message=[" + greeting.toString() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
        }, error -> {
            System.out.println("Unable to send message=["
                    + greeting.toString() + "] due to : " + error.getMessage());
        });
    }
}
