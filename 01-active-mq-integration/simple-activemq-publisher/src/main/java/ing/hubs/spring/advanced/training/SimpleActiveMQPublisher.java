package ing.hubs.spring.advanced.training;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class SimpleActiveMQPublisher {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(SimpleActiveMQPublisher.class);
		springApplication.setWebApplicationType(WebApplicationType.NONE);
		springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args);
	}

	@Bean
	public ApplicationRunner runner(JmsTemplate jmsTemplate) {
		return args -> {
			jmsTemplate.convertAndSend("first-queue", "Our first message");
			System.out.println("Successfully sent a message to the 'first-queue' queue");
		};
	}
}
