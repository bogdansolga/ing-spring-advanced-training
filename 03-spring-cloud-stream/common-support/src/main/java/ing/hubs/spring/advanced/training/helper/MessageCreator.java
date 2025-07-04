package ing.hubs.spring.advanced.training.helper;

import ing.hubs.spring.advanced.training.message.AbstractMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import java.util.UUID;

public final class MessageCreator {

    @SuppressWarnings("rawtypes")
    public static <Payload extends AbstractMessage> Message<Payload> create(final Payload payload) {
        return MessageBuilder.withPayload(payload)
                             .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                             .setHeader("correlationId", UUID.randomUUID().toString())
                             .setHeader("version", "1.2")
                             .setErrorChannelName("error_channel_" + payload.getName()) // just an ex
                             .setReplyChannelName("reply_channel_" + payload.getName())
                             .build();
    }
}
