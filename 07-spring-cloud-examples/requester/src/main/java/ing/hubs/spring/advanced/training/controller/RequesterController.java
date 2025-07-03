package ing.hubs.spring.advanced.training.controller;

import ing.hubs.spring.advanced.training.RequesterService;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/invoke")
public class RequesterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequesterController.class);

    private final RetryRegistry retryRegistry;

    @Autowired
    public RequesterController(RetryRegistry retryRegistry) {
        this.retryRegistry = retryRegistry;
    }

    @GetMapping
    public String invokeService() {
        RestTemplate restTemplate = new RestTemplate();

        Retry retry = retryRegistry.retry("first");
        //CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("first");

        try {
            String result = retry.executeCheckedSupplier(() -> restTemplate.getForObject("http://localhost:8081/product", String.class));
            //String result = circuitBreaker.executeSupplier(() -> restTemplate.getForObject("http://localhost:8081/product", String.class));
            LOGGER.info("Received result: {}", result);
            return result;
        } catch (Throwable e) {
            LOGGER.error("Failed to get the product - {}", e.getMessage());
            //throw new RuntimeException(e);
            return "Failed to get the product";
        }
    }
}
