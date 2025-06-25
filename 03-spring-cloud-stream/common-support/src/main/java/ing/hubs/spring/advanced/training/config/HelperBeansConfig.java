package ing.hubs.spring.advanced.training.config;

import ing.hubs.spring.advanced.training.helper.MessagePublisher;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class HelperBeansConfig {

    @Bean
    public MessagePublisher messagePublisher(StreamBridge streamBridge) {
        return new MessagePublisher(streamBridge);
    }
}
