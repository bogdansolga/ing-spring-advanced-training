package ing.hubs.spring.advanced.training.message.event.customer;

import ing.hubs.spring.advanced.training.marker.message.Channel;
import ing.hubs.spring.advanced.training.marker.message.MessageDetails;
import ing.hubs.spring.advanced.training.marker.message.Service;
import ing.hubs.spring.advanced.training.message.AbstractDomainEvent;

@MessageDetails(
        publisher = Service.CUSTOMER_SERVICE,
        subscribers = Service.BILLING_SERVICE,
        channel = Channel.CUSTOMER_CREATED
)
public class CustomerCreatedEvent extends AbstractDomainEvent {

    private static final String NAME = "CustomerCreated";

    private final long customerId;
    private final String customerEmail;

    public CustomerCreatedEvent(final long messageId, final long eventId, final long customerId, final String customerEmail) {
        super(messageId, eventId);
        this.customerId = customerId;
        this.customerEmail = customerEmail;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
}
