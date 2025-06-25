package ing.hubs.spring.advanced.training.billing.config;

import ing.hubs.spring.advanced.training.helper.MessageCreator;
import ing.hubs.spring.advanced.training.message.event.order.OrderChargedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Function;

@Configuration
public class ProducersConfig {

    @Bean
    public Function<OrderChargedEvent, Message<OrderChargedEvent>> orderChargedProducer() {
        return MessageCreator::create;
    }
}
