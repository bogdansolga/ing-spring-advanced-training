package ing.hubs.spring.advanced.training.order.inbound.adapter;

import ing.hubs.spring.advanced.training.marker.adapter.InboundAdapter;
import ing.hubs.spring.advanced.training.message.command.order.CreateOrderCommand;
import ing.hubs.spring.advanced.training.message.event.customer.CustomerCreatedEvent;
import ing.hubs.spring.advanced.training.message.event.customer.CustomerUpdatedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderChargedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderNotChargedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderShippedEvent;
import ing.hubs.spring.advanced.training.order.inbound.port.MessagingInboundPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class MessagingInboundAdapter implements InboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingInboundAdapter.class);

    private final MessagingInboundPort messagingInboundPort;

    @Autowired
    public MessagingInboundAdapter(final MessagingInboundPort messagingInboundPort) {
        this.messagingInboundPort = messagingInboundPort;
    }

    @Bean
    public Consumer<CreateOrderCommand> createOrder() {
        return createOrderCommand -> {
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            LOGGER.debug("Received a '{}' command, the ordered item is '{}', the customer ID is {}",
                    createOrderCommand.getName(), createOrderCommand.getProductName(), createOrderCommand.getCustomerId());

            messagingInboundPort.createOrder(createOrderCommand);
        };
    }

    @Bean
    public Consumer<CustomerUpdatedEvent> customerUpdated() {
        return customerUpdatedEvent -> {
            LOGGER.debug("Received a '{}' event, the ID of the updated customer is {}",
                    customerUpdatedEvent.getName(), customerUpdatedEvent.getCustomerId());

            messagingInboundPort.handleCustomerUpdated(customerUpdatedEvent);
        };
    }

    @Bean
    public Consumer<OrderChargedEvent> orderCharged() {
        return orderChargedEvent -> {
            LOGGER.debug("Received a '{}' event for the order {} of the customer {}",
                    orderChargedEvent.getName(), orderChargedEvent.getOrderId(), orderChargedEvent.getCustomerId());

            messagingInboundPort.handleOrderCharged(orderChargedEvent);
        };
    }

    @Bean
    public Function<CustomerCreatedEvent, CustomerUpdatedEvent> customerUpdatedAsFunction() {
        return customerUpdatedEvent -> {
            LOGGER.debug("Received a '{}' event, the ID of the updated customer is {}",
                    customerUpdatedEvent.getName(), customerUpdatedEvent.getCustomerId());

            return messagingInboundPort.handleCustomerUpdated(customerUpdatedEvent);
        };
    }

    @Bean
    public Consumer<OrderNotChargedEvent> orderNotCharged() {
        return orderNotChargedEvent -> {
            LOGGER.warn("Received a '{}' event for the order {} of the customer {}",
                    orderNotChargedEvent.getName(), orderNotChargedEvent.getOrderId(), orderNotChargedEvent.getCustomerId());

            messagingInboundPort.handleOrderNotCharged(orderNotChargedEvent);
        };
    }

    @Bean
    public Consumer<OrderShippedEvent> orderShipped() {
        return orderShippedEvent -> {
            LOGGER.debug("Received a '{}' event for the order {} of the customer {}",
                    orderShippedEvent.getName(), orderShippedEvent.getOrderId(), orderShippedEvent.getCustomerId());

            messagingInboundPort.handleOrderShipped(orderShippedEvent);
        };
    }
}
