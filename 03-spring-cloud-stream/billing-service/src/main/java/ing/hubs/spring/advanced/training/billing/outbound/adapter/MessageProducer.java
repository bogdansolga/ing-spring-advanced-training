package ing.hubs.spring.advanced.training.billing.outbound.adapter;

import ing.hubs.spring.advanced.training.helper.MessagePublisher;
import ing.hubs.spring.advanced.training.message.OutputBindings;
import ing.hubs.spring.advanced.training.message.event.order.OrderChargedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderNotChargedEvent;
import ing.hubs.spring.advanced.training.billing.outbound.port.MessagingOutboundPort;
import ing.hubs.spring.advanced.training.marker.adapter.OutboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer implements MessagingOutboundPort, OutboundAdapter {

    private final MessagePublisher messagePublisher;

    @Autowired
    public MessageProducer(final MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void publishOrderChargedEvent(final OrderChargedEvent orderChargedEvent) {
        messagePublisher.sendMessage(OutputBindings.ORDER_CHARGED, orderChargedEvent);
    }

    @Override
    public void publishOrderNotChargedEvent(final OrderNotChargedEvent orderNotChargedEvent) {
    }
}
