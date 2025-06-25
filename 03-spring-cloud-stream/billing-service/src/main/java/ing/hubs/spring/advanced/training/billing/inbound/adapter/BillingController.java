package ing.hubs.spring.advanced.training.billing.inbound.adapter;

import ing.hubs.spring.advanced.training.dto.order.PaymentDTO;
import ing.hubs.spring.advanced.training.billing.inbound.port.BillingService;
import ing.hubs.spring.advanced.training.marker.adapter.InboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController implements InboundAdapter {

    private final BillingService billingService;

    @Autowired
    public BillingController(final BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/{customerId}")
    public List<PaymentDTO> getPaymentsForCustomer(@PathVariable final long customerId) {
        return billingService.getPaymentsForCustomer(customerId);
    }
}
