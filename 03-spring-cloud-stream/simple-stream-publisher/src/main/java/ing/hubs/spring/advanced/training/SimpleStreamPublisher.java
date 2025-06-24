package ing.hubs.spring.advanced.training;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpleStreamPublisher {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SimpleStreamPublisher.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);
    }

    @Bean
    public ApplicationRunner runner(StreamBridge streamBridge) {
        return args -> {
            String message = "Our first Spring Cloud Stream message";
            streamBridge.send("first-output", message);
            System.out.println("Successfully sent message via Spring Cloud Stream: " + message);
        };
    }
}