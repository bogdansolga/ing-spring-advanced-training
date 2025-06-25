package ing.hubs.spring.advanced.training.billing.outbound.port;

import ing.hubs.spring.advanced.training.message.event.order.OrderChargedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderNotChargedEvent;
import ing.hubs.spring.advanced.training.marker.port.OutboundPort;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishOrderChargedEvent(final OrderChargedEvent orderChargedEvent);

    void publishOrderNotChargedEvent(final OrderNotChargedEvent orderNotChargedEvent);
}
