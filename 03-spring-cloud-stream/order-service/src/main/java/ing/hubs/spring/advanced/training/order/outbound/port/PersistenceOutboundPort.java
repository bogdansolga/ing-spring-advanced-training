package ing.hubs.spring.advanced.training.order.outbound.port;

import ing.hubs.spring.advanced.training.marker.port.OutboundPort;
import ing.hubs.spring.advanced.training.order.domain.model.Order;

public interface PersistenceOutboundPort extends OutboundPort {
    long save(Order order);
}
