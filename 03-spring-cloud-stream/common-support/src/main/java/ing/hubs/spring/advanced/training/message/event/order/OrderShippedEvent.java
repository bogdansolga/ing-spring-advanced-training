package ing.hubs.spring.advanced.training.message.event.order;

import ing.hubs.spring.advanced.training.marker.message.Channel;
import ing.hubs.spring.advanced.training.marker.message.MessageDetails;
import ing.hubs.spring.advanced.training.marker.message.Service;
import ing.hubs.spring.advanced.training.message.AbstractDomainEvent;

@MessageDetails(
        publisher = Service.SHIPPING_SERVICE,
        subscribers = {
                Service.BILLING_SERVICE,
                Service.CUSTOMER_SERVICE,
                Service.ORDER_SERVICE
        },
        channel = Channel.ORDER_SHIPPED
)
public class OrderShippedEvent extends AbstractDomainEvent {

    private static final String NAME = "OrderShipped";

    private final long customerId;
    private final long orderId;

    public OrderShippedEvent(final long messageId, final long eventId, final long customerId, long orderId) {
        super(messageId, eventId);
        this.customerId = customerId;
        this.orderId = orderId;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getOrderId() {
        return orderId;
    }
}
