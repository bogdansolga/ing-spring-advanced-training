package ing.hubs.spring.advanced.training.message;

public abstract class AbstractQuery extends AbstractMessage<AbstractMessageType.QueryMessage> {

    public AbstractQuery(long messageId) {
        super(messageId);
    }
}
