package br.com.practice.springkafka.domain.api;

import br.com.practice.springkafka.domain.model.Greeting;
import br.com.practice.springkafka.domain.model.Message;
import br.com.practice.springkafka.domain.service.messages.producer.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final SendMessage send;

    @PostMapping
    public void publish(final @RequestBody Message message) {
        send.sendMessage(message);
    }

    @PostMapping("/greeting")
    public void publishGreeting(final @RequestBody Greeting greeting) {
        send.sendMessage(greeting);
    }
}
