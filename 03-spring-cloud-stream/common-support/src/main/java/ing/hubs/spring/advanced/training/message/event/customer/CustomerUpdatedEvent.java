package ing.hubs.spring.advanced.training.message.event.customer;

import ing.hubs.spring.advanced.training.marker.message.Channel;
import ing.hubs.spring.advanced.training.marker.message.MessageDetails;
import ing.hubs.spring.advanced.training.marker.message.Service;
import ing.hubs.spring.advanced.training.message.AbstractDomainEvent;

@MessageDetails(
        publisher = Service.CUSTOMER_SERVICE,
        subscribers = Service.ORDER_SERVICE,
        channel = Channel.CUSTOMER_UPDATED
)
public class CustomerUpdatedEvent extends AbstractDomainEvent {

    private static final String NAME = "CustomerUpdated";

    private final long customerId;

    public CustomerUpdatedEvent(final long messageId, final long eventId, final long customerId) {
        super(messageId, eventId);
        this.customerId = customerId;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public long getCustomerId() {
        return customerId;
    }
}
