package ing.hubs.spring.advanced.training.message.event.order;

import ing.hubs.spring.advanced.training.marker.message.Channel;
import ing.hubs.spring.advanced.training.marker.message.MessageDetails;
import ing.hubs.spring.advanced.training.marker.message.Service;
import ing.hubs.spring.advanced.training.message.AbstractDomainEvent;

@MessageDetails(
        publisher = Service.DELIVERY_SERVICE,
        subscribers = {
                Service.RESTAURANT_SERVICE,
                Service.ORDER_SERVICE
        },
        channel = Channel.ORDER_DELIVERED
)
public class OrderDeliveredEvent extends AbstractDomainEvent {

    private static final String NAME = "OrderDelivered";

    private final long customerId;
    private final long orderId;

    public OrderDeliveredEvent(final long messageId, final long eventId, final long customerId, final long orderId) {
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

    @Override
    public String toString() {
        return "customerId: " + customerId +
                ", orderId: " + orderId +
                '}';
    }
}
