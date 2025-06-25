package ing.hubs.spring.advanced.training.billing.service;

import ing.hubs.spring.advanced.training.dto.order.OrderChargingStatusDTO;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    // invokes a payment gateway (ex: Braintree) and returns the charging result
    public synchronized OrderChargingStatusDTO charge(final int paymentMethodId, final double amount) {
        return System.currentTimeMillis() % 2 == 0 ? new OrderChargingStatusDTO() :
                new OrderChargingStatusDTO(false, "The card has expired");
    }
}
