package ing.hubs.spring.advanced.training.billing.inbound.port;

import ing.hubs.spring.advanced.training.billing.service.PaymentService;
import ing.hubs.spring.advanced.training.dto.order.OrderChargingStatusDTO;
import ing.hubs.spring.advanced.training.dto.order.PaymentDTO;
import ing.hubs.spring.advanced.training.message.command.order.ChargeOrderCommand;
import ing.hubs.spring.advanced.training.message.event.order.OrderChargedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderNotChargedEvent;
import ing.hubs.spring.advanced.training.billing.outbound.port.MessagingOutboundPort;
import ing.hubs.spring.advanced.training.marker.port.InboundPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BillingService implements InboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingService.class);

    private final MessagingOutboundPort messagingOutboundPort;
    private final PaymentService paymentService;

    @Autowired
    public BillingService(final MessagingOutboundPort messagingOutboundPort, final PaymentService paymentService) {
        this.messagingOutboundPort = messagingOutboundPort;
        this.paymentService = paymentService;
    }

    @Transactional
    public void chargeOrder(final ChargeOrderCommand chargeOrderCommand) {
        final long customerId = chargeOrderCommand.getCustomerId();
        final long orderId = chargeOrderCommand.getOrderId();
        final double orderTotal = chargeOrderCommand.getOrderTotal();

        LOGGER.info("Charging the customer with the ID {}, for the order with Id {}, for {} {}...", customerId, orderId,
                orderTotal, chargeOrderCommand.getCurrency());

        final int usedPaymentMethod = getPaymentMethod();
        final OrderChargingStatusDTO orderChargingStatus = paymentService.charge(usedPaymentMethod, orderTotal);

        // TODO insert magic here
        sleepALittle();

        if (orderChargingStatus.isSuccessful()) {
            LOGGER.info("The customer {} was successfully charged for the order {}", customerId, orderId);
            messagingOutboundPort.publishOrderChargedEvent(
                    new OrderChargedEvent(getNextMessageId(), getNextEventId(), customerId, orderId));
        } else {
            final String failureReason = orderChargingStatus.getFailureReason()
                                                            .orElse("Cannot charge the card");
            LOGGER.warn("The customer {} could not be charged for the order {} - '{}'", customerId, orderId, failureReason);
            messagingOutboundPort.publishOrderNotChargedEvent(
                    new OrderNotChargedEvent(getNextMessageId(), getNextEventId(), customerId, orderId,
                            failureReason)
            );
        }
    }

    @Transactional
    public List<PaymentDTO> getPaymentsForCustomer(long customerId) {
        // TODO insert magic here
        return new ArrayList<>();
    }

    private int getPaymentMethod() {
        // return the user's payment methods
        return 2;
    }

    private long getNextMessageId() {
        return new Random(900000).nextLong();
    }

    private long getNextEventId() {
        // returned from the saved database event, before sending it (using transactional messaging)
        return new Random(900000).nextLong();
    }

    // simulate a long running operation
    private void sleepALittle() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
