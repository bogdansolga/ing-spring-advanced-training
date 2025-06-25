package ing.hubs.spring.advanced.training.order.domain.repository;

import ing.hubs.spring.advanced.training.order.domain.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
