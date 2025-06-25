package ing.hubs.spring.advanced.training.order.inbound.port;

import ing.hubs.spring.advanced.training.dto.order.OrderDTO;
import ing.hubs.spring.advanced.training.marker.port.InboundPort;

public interface RestInboundPort extends InboundPort {

    void createOrder(final OrderDTO orderDTO);
}
