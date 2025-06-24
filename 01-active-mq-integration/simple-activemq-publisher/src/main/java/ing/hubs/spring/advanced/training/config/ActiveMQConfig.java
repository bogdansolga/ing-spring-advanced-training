package ing.hubs.spring.advanced.training.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class ActiveMQConfig {
    // No queue definitions needed - ActiveMQ creates them automatically
}
