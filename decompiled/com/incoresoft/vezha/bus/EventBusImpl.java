/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.bus;

import com.google.inject.Inject;
import com.incoresoft.middleware.eventbus.Bus;
import com.incoresoft.middleware.eventbus.BusListener;
import com.incoresoft.middleware.eventbus.BusMessage;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.mq.MessageQueueListener;
import com.incoresoft.plugin.commons.mq.ResponseMessage;
import com.incoresoft.vezha.bus.BusMessageImpl;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;

public class EventBusImpl
implements Bus {
    private static final String SUBJECT_PREFIX = "eventbus";
    private final MessageQueueConnector connector;
    private final Map<BusListener, MessageQueueListener> registeredBusListeners = new ConcurrentHashMap<BusListener, MessageQueueListener>();

    @Override
    public void publish(@NotNull String subject, byte[] data) {
        this.connector.publish(EventBusImpl.formatFullSubject(subject), data);
    }

    @Override
    public void publish(@NotNull String subject, @NotNull String data) {
        this.publish(subject, data.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void publish(@NotNull String subject, @NotNull BusMessage message) {
        this.publish(subject, message.data());
    }

    @Override
    public CompletableFuture<BusMessage> request(@NotNull String subject, byte[] data) {
        return CompletableFuture.supplyAsync(() -> {
            Message response = this.connector.request(EventBusImpl.formatFullSubject(subject), data, Duration.ofSeconds(30L));
            return response != null ? new BusMessageImpl(response.getData(), response.getReplyTo()) : null;
        });
    }

    @Override
    public CompletableFuture<BusMessage> request(@NotNull String subject, @NotNull String data) {
        return this.request(subject, data.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public CompletableFuture<BusMessage> request(@NotNull String subject, @NotNull BusMessage message) {
        return this.request(subject, message.data());
    }

    @Override
    public boolean addListener(final @NotNull BusListener listener) {
        if (this.registeredBusListeners.containsKey(listener)) {
            return true;
        }
        MessageQueueListener messageQueueListener = new MessageQueueListener(){

            @Override
            public String subject() {
                return EventBusImpl.formatFullSubject(listener.subject());
            }

            @Override
            public Message messageReceived(Message message) {
                listener.messageReceived(new BusMessageImpl(message.getData(), message.getReplyTo()));
                return ResponseMessage.empty();
            }
        };
        this.connector.addListener(messageQueueListener);
        this.registeredBusListeners.put(listener, messageQueueListener);
        return true;
    }

    @Override
    public boolean removeListener(@NotNull BusListener listener) {
        if (!this.registeredBusListeners.containsKey(listener)) {
            return true;
        }
        return this.connector.removeListener(this.registeredBusListeners.remove(listener));
    }

    @NotNull
    private static String formatFullSubject(@NotNull String subject) {
        return subject.startsWith("_INBOX") ? subject : "eventbus." + Objects.requireNonNull(subject);
    }

    @Inject
    public EventBusImpl(MessageQueueConnector connector) {
        this.connector = connector;
    }
}

