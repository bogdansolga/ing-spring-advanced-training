package ing.hubs.spring.advanced.training.order.config;

import ing.hubs.spring.advanced.training.helper.MessageCreator;
import ing.hubs.spring.advanced.training.message.command.order.ChargeOrderCommand;
import ing.hubs.spring.advanced.training.message.event.order.OrderCreatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Function;

@Configuration
public class ProducersConfig {

    @Bean
    public Function<OrderCreatedEvent, Message<OrderCreatedEvent>> orderCreatedProducer() {
        return MessageCreator::create;
    }

    @Bean
    public Function<ChargeOrderCommand, Message<ChargeOrderCommand>> chargeOrderProducer() {
        return MessageCreator::create;
    }
}
