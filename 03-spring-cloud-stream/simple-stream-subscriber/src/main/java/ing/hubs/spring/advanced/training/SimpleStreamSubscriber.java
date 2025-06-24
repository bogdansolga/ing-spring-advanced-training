package ing.hubs.spring.advanced.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class SimpleStreamSubscriber {

    public static void main(String[] args) {
        SpringApplication.run(SimpleStreamSubscriber.class, args);
    }

    @Bean
    public Consumer<String> firstConsumer() {
        return message -> {
            System.out.println("Received Spring Cloud Stream message: " + message);
        };
    }
}