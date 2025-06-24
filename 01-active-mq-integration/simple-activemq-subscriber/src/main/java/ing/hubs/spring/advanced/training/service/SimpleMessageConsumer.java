package ing.hubs.spring.advanced.training.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class SimpleMessageConsumer {

    @JmsListener(destination = "first-queue")
    public void handleMessage(String message) {
        System.out.println("Received the message: " + message);
    }
}
