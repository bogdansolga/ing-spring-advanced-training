package ing.hubs.spring.advanced.training.actuator.config;

import ing.hubs.spring.advanced.training.actuator.model.Product;
import ing.hubs.spring.advanced.training.actuator.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.annotation.PostConstruct;

/**
 * A simple {@link javax.sql.DataSource} configuration, which:
 * <ul>
 *     <li>configures the JPA repositories, using the {@link EnableJpaRepositories} annotation</li>
 *     <li>inserts a simple {@link Product} in the auto-wired [in-memory] database</li>
 * </ul>
 */
@Configuration
@EnableJpaRepositories("ing.hubs.spring.advanced.training.actuator.repository")
public class DataSourceConfig {

    private final ProductService productService;

    @Autowired
    public DataSourceConfig(final ProductService productService) {
        this.productService = productService;
    }

    @PostConstruct
    public void init() {
        final Product product = new Product();
        product.setName("A default product");
        productService.create(product);
    }
}
