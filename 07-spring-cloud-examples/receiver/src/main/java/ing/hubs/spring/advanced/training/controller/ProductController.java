package ing.hubs.spring.advanced.training.controller;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping
    @Retry(name = "product-retry", fallbackMethod = "getProductFallback")
    public String getProduct() {
        if (System.currentTimeMillis() % 2 == 0) {
            throw new RuntimeException("An error occurred");
        }
        return "Product";
    }

    public String getProductFallback() {
        return "Fallback error Product";
    }
}
