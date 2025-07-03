package ing.hubs.spring.advanced.training.order.inbound.port;

import ing.hubs.spring.advanced.training.marker.port.InboundPort;
import ing.hubs.spring.advanced.training.message.command.order.CreateOrderCommand;
import ing.hubs.spring.advanced.training.message.event.customer.CustomerCreatedEvent;
import ing.hubs.spring.advanced.training.message.event.customer.CustomerUpdatedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderChargedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderNotChargedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderShippedEvent;

public interface MessagingInboundPort extends InboundPort {

    void createOrder(final CreateOrderCommand createOrderCommand);

    void handleCustomerUpdated(CustomerUpdatedEvent customerUpdatedEvent);

    CustomerUpdatedEvent handleCustomerUpdated(CustomerCreatedEvent customerCreatedEvent);

    void handleOrderCharged(OrderChargedEvent orderChargedEvent);

    void handleOrderNotCharged(OrderNotChargedEvent orderNotChargedEvent);

    void handleOrderShipped(OrderShippedEvent orderShippedEvent);
}
