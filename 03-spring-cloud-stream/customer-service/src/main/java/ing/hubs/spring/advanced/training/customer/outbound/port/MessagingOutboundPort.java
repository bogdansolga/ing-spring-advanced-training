package ing.hubs.spring.advanced.training.customer.outbound.port;

import ing.hubs.spring.advanced.training.marker.port.OutboundPort;
import ing.hubs.spring.advanced.training.message.command.order.ProcessOrderCommand;
import ing.hubs.spring.advanced.training.message.event.customer.CustomerCreatedEvent;
import ing.hubs.spring.advanced.training.message.event.customer.CustomerUpdatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishCustomerCreatedEvent(final CustomerCreatedEvent customerCreatedEvent);

    void publishCustomerUpdatedEvent(final CustomerUpdatedEvent customerUpdatedEvent);

    void publishProcessOrderCommand(ProcessOrderCommand processOrderCommand);
}
