package ing.hubs.spring.advanced.training.order.outbound.adapter;

import ing.hubs.spring.advanced.training.order.domain.model.Order;
import ing.hubs.spring.advanced.training.order.domain.repository.OrderRepository;
import ing.hubs.spring.advanced.training.order.outbound.port.PersistenceOutboundPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersistenceOutboundAdapter implements PersistenceOutboundPort {

    private final OrderRepository orderRepository;

    @Autowired
    public PersistenceOutboundAdapter(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional(
            readOnly = false,
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED
    )
    public long save(Order order) {
        return orderRepository.save(order)
                              .getId();
    }
}
