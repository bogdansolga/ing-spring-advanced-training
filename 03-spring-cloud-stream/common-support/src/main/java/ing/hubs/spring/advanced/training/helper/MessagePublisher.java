package ing.hubs.spring.advanced.training.helper;

import ing.hubs.spring.advanced.training.message.AbstractMessage;
import ing.hubs.spring.advanced.training.message.OutputBindings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

    private final StreamBridge streamBridge;

    @Autowired
    public MessagePublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Async
    public <Message extends AbstractMessage<?>> void sendMessage(OutputBindings binding, Message message) {
        streamBridge.send(binding.getBindingName(), message);
    }
}
