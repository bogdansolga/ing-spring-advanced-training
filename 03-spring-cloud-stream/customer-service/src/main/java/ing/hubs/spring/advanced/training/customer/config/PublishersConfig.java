package ing.hubs.spring.advanced.training.customer.config;

import ing.hubs.spring.advanced.training.helper.MessageCreator;
import ing.hubs.spring.advanced.training.message.command.order.ProcessOrderCommand;
import ing.hubs.spring.advanced.training.message.event.customer.CustomerCreatedEvent;
import ing.hubs.spring.advanced.training.message.event.customer.CustomerUpdatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PublishersConfig {

    @Bean
    public Function<CustomerCreatedEvent, Message<CustomerCreatedEvent>> customerCreatedProducer() {
        return MessageCreator::create;
    }

    @Bean
    public Function<CustomerUpdatedEvent, Message<CustomerUpdatedEvent>> customerUpdatedProducer() {
        return MessageCreator::create;
    }

    @Bean
    public Function<ProcessOrderCommand, Message<ProcessOrderCommand>> processOrderProducer() {
        return MessageCreator::create;
    }
}
