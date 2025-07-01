package ing.hubs.spring.advanced.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleRabbitMQSubscriber {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(SimpleRabbitMQSubscriber.class);
		springApplication.setWebApplicationType(WebApplicationType.SERVLET);
		springApplication.setLogStartupInfo(true);
		springApplication.run(args);
	}
}
