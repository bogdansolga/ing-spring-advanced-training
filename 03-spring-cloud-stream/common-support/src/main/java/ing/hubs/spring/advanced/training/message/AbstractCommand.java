package ing.hubs.spring.advanced.training.message;

public abstract class AbstractCommand extends AbstractMessage<AbstractMessageType.CommandMessage> {

    public AbstractCommand(long messageId) {
        super(messageId);
    }
}
