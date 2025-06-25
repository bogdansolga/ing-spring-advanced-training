package training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringCloudGatewayDemo {

    public static void main(String[] args) {
        new SpringApplication(SpringCloudGatewayDemo.class).run(args);
    }
}
