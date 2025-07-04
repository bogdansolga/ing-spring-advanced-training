package ing.hubs.spring.advanced.training.customer.inbound.adapter;

import ing.hubs.spring.advanced.training.dto.customer.CustomerDTO;
import ing.hubs.spring.advanced.training.customer.inbound.port.CustomerService;
import ing.hubs.spring.advanced.training.marker.adapter.InboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController implements InboundAdapter {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody final CustomerDTO customerDTO) {
        customerService.createCustomer(customerDTO);

        return ResponseEntity.ok("The customer was successfully created");
    }
}
