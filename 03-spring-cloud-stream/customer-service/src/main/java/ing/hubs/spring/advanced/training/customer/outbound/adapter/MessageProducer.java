package ing.hubs.spring.advanced.training.customer.outbound.adapter;

import ing.hubs.spring.advanced.training.customer.outbound.port.MessagingOutboundPort;
import ing.hubs.spring.advanced.training.helper.MessagePublisher;
import ing.hubs.spring.advanced.training.message.OutputBindings;
import ing.hubs.spring.advanced.training.message.command.order.ProcessOrderCommand;
import ing.hubs.spring.advanced.training.message.event.customer.CustomerCreatedEvent;
import ing.hubs.spring.advanced.training.message.event.customer.CustomerUpdatedEvent;
import ing.hubs.spring.advanced.training.marker.adapter.OutboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer implements MessagingOutboundPort, OutboundAdapter {

    private final MessagePublisher messagePublisher;

    @Autowired
    public MessageProducer(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void publishCustomerCreatedEvent(final CustomerCreatedEvent customerCreatedEvent) {
        messagePublisher.sendMessage(OutputBindings.CUSTOMER_CREATED, customerCreatedEvent);
    }

    @Override
    public void publishCustomerUpdatedEvent(final CustomerUpdatedEvent customerUpdatedEvent) {
        messagePublisher.sendMessage(OutputBindings.CUSTOMER_CREATED, customerUpdatedEvent);
    }

    @Override
    public void publishProcessOrderCommand(ProcessOrderCommand processOrderCommand) {
        messagePublisher.sendMessage(OutputBindings.PROCESS_ORDER, processOrderCommand);
    }
}
