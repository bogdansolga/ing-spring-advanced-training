package ing.hubs.spring.advanced.training.customer.inbound.port;

import ing.hubs.spring.advanced.training.customer.outbound.port.MessagingOutboundPort;
import ing.hubs.spring.advanced.training.dto.customer.CustomerDTO;
import ing.hubs.spring.advanced.training.marker.port.InboundPort;
import ing.hubs.spring.advanced.training.message.command.order.ProcessOrderCommand;
import ing.hubs.spring.advanced.training.message.event.customer.CustomerCreatedEvent;
import ing.hubs.spring.advanced.training.message.event.customer.CustomerUpdatedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderChargedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderCreatedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderNotChargedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderShippedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class CustomerService implements InboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private final MessagingOutboundPort messagingOutboundPort;

    @Autowired
    public CustomerService(final MessagingOutboundPort messagingOutboundPort) {
        this.messagingOutboundPort = messagingOutboundPort;
    }

    @Transactional
    public void createCustomer(final CustomerDTO customerDTO) {
        LOGGER.info("Creating the customer '{}' [{}]...", customerDTO.getName(), customerDTO.getEmail());

        final long customerId = saveCustomerInTheDatabase(customerDTO);

        // insert magic here
        messagingOutboundPort.publishCustomerCreatedEvent(
                new CustomerCreatedEvent(getNextMessageId(), getNextEventId(), customerId, customerDTO.getEmail()));
    }

    private long saveCustomerInTheDatabase(final CustomerDTO customerDTO) {
        // TODO insert magic here
        sleepALittle();

        return new Random(10000).nextLong();
    }

    @Transactional
    public void handleOrderCreated(final OrderCreatedEvent orderCreatedMessage) {
        final long customerId = orderCreatedMessage.getCustomerId();

        LOGGER.info("Updating the customer with the ID {}...", customerId);
        updateCustomer(customerId);

        //TODO insert magic here

        messagingOutboundPort.publishCustomerUpdatedEvent(
                new CustomerUpdatedEvent(getNextMessageId(), getNextEventId(), customerId));
    }

    @Transactional
    public void handleOrderCharged(final OrderChargedEvent orderChargedEvent) {
        LOGGER.info("The order with the ID {} of the customer {} was successfully charged, updating it",
                orderChargedEvent.getOrderId(), orderChargedEvent.getCustomerId());

        // TODO insert magic here

        messagingOutboundPort.publishProcessOrderCommand(
                new ProcessOrderCommand(getNextMessageId(), getNextEventId(), "something", 220));
    }

    @Transactional
    public void handleOrderNotCharged(final OrderNotChargedEvent orderNotChargedEvent) {
        LOGGER.warn("The order with the ID {} of the customer {} could not be charged - reason: '{}'",
                orderNotChargedEvent.getOrderId(), orderNotChargedEvent.getCustomerId(), orderNotChargedEvent.getReason());

        // TODO insert magic here
    }

    @Transactional
    public void handleOrderShipped(final OrderShippedEvent orderShippedEvent) {
        final long customerId = orderShippedEvent.getCustomerId();
        final long orderId = orderShippedEvent.getOrderId();
        LOGGER.info("The order with the ID {} of the customer {} was successfully shipped!", orderId, customerId);

        // TODO insert any further magic here
    }

    private void updateCustomer(long customerId) {
        // TODO insert magic here
        sleepALittle();
    }

    private long getNextMessageId() {
        return new Random(900000).nextLong();
    }

    private long getNextEventId() {
        // returned from the saved database event, before sending it (using transactional messaging)
        return new Random(900000).nextLong();
    }

    // simulate a long running operation

    private void sleepALittle() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
