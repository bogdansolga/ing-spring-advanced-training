package ing.hubs.spring.advanced.training.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("net.safedata.microservices.training.order.domain.repository")
public class PersistenceConfig {
}
