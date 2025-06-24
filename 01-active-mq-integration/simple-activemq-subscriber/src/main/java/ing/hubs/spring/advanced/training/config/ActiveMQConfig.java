package ing.hubs.spring.advanced.training.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMQConfig {
    @Bean
    public Queue firstQueue() {
        return new Queue("first-queue", true);
    }

    @Bean
    public Exchange firstExchange() {
        return new DirectExchange("first-exchange");
    }

    @Bean
    public Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue)
                             .to(exchange)
                             .with("first-routing-key")
                             .noargs();
    }
}
