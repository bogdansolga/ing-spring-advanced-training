package ing.hubs.spring.advanced.training.message.query.order;

import ing.hubs.spring.advanced.training.marker.message.Channel;
import ing.hubs.spring.advanced.training.marker.message.MessageDetails;
import ing.hubs.spring.advanced.training.marker.message.Service;
import ing.hubs.spring.advanced.training.message.AbstractQuery;

@MessageDetails(
        publisher = Service.ORDER_SERVICE,
        subscribers = Service.BILLING_SERVICE,
        channel = Channel.FIND_ORDER
)
public class FindOrderQuery extends AbstractQuery {

    private static final String NAME = "FindOrder";

    private final long customerId;
    private final long orderId;

    public FindOrderQuery(final long messageId, final long customerId, final long orderId) {
        super(messageId);
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
