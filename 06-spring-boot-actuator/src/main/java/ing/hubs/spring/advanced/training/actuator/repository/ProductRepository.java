package ing.hubs.spring.advanced.training.actuator.repository;

import ing.hubs.spring.advanced.training.actuator.model.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * A simple Spring Data {@link CrudRepository} for the {@link Product} entity
 *
 * @author bogdan.solga
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
