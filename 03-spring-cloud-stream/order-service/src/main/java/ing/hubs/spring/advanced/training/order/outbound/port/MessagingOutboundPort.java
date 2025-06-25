package ing.hubs.spring.advanced.training.order.outbound.port;

import ing.hubs.spring.advanced.training.marker.port.OutboundPort;
import ing.hubs.spring.advanced.training.message.command.order.ChargeOrderCommand;
import ing.hubs.spring.advanced.training.message.command.order.ShipOrderCommand;
import ing.hubs.spring.advanced.training.message.event.order.OrderCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishOrderCreatedEvent(final OrderCreatedEvent orderCreatedEvent);

    void publishChargeOrderCommand(final ChargeOrderCommand chargeOrderCommand);

    void publishShipOrderCommand(final ShipOrderCommand shipOrderCommand);
}
