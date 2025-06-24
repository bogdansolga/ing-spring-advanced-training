package ing.hubs.spring.advanced.training.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SimpleMessageConsumer {

    @RabbitListener(queues = "first-queue")
    public void handleMessage(String message) {
        System.out.println("Received the message: " + message);
    }
}
