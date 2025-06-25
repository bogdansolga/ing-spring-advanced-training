package ing.hubs.spring.advanced.training.message.command.order;

import ing.hubs.spring.advanced.training.marker.message.Channel;
import ing.hubs.spring.advanced.training.marker.message.MessageDetails;
import ing.hubs.spring.advanced.training.marker.message.Service;
import ing.hubs.spring.advanced.training.message.AbstractCommand;

import java.util.Objects;

@MessageDetails(
        publisher = Service.BILLING_SERVICE,
        subscribers = Service.RESTAURANT_SERVICE,
        channel = Channel.PROCESS_ORDER
)
public class ProcessOrderCommand extends AbstractCommand {

    private static final String NAME = "ProcessOrder";

    private final String productName;
    private final double orderTotal;
    private final long customerId;

    public ProcessOrderCommand(final long customerId, final long messageId, final String productName, final double orderTotal) {
        super(messageId);
        this.customerId = customerId;
        this.productName = productName;
        this.orderTotal = orderTotal;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getProductName() {
        return productName;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessOrderCommand that = (ProcessOrderCommand) o;
        return Double.compare(that.orderTotal, orderTotal) == 0 &&
                customerId == that.customerId &&
                Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, orderTotal, customerId);
    }

}
