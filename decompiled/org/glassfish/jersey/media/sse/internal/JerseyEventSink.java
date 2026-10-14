/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse.internal;

import java.io.Flushable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Provider;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.SseEventSink;
import org.glassfish.jersey.internal.jsr166.Flow;
import org.glassfish.jersey.internal.jsr166.JerseyFlowSubscriber;
import org.glassfish.jersey.media.sse.LocalizationMessages;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.server.AsyncContext;
import org.glassfish.jersey.server.ChunkedOutput;

class JerseyEventSink
extends ChunkedOutput<OutboundSseEvent>
implements SseEventSink,
Flushable,
JerseyFlowSubscriber<Object> {
    private static final Logger LOGGER = Logger.getLogger(JerseyEventSink.class.getName());
    private static final byte[] SSE_EVENT_DELIMITER = "\n".getBytes(StandardCharsets.UTF_8);
    private Flow.Subscription subscription = null;
    private final AtomicBoolean subscribed = new AtomicBoolean(false);
    private volatile MediaType implicitMediaType = null;

    JerseyEventSink(Provider<AsyncContext> asyncContextProvider) {
        super(SSE_EVENT_DELIMITER, asyncContextProvider);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        if (subscription == null) {
            throw new NullPointerException(LocalizationMessages.PARAM_NULL("subscription"));
        }
        if (this.subscribed.getAndSet(true)) {
            subscription.cancel();
            return;
        }
        this.subscription = subscription;
        if (this.isClosed()) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override
    public void onNext(Object item) {
        if (item == null) {
            throw new NullPointerException(LocalizationMessages.PARAM_NULL("outboundSseEvent"));
        }
        try {
            this.checkClosed();
            MediaType implicitType = this.resolveMediaType(item);
            if (MediaType.SERVER_SENT_EVENTS_TYPE.equals(implicitType)) {
                this.write((OutboundSseEvent)item);
            } else {
                this.write(new OutboundEvent.Builder().mediaType(implicitType).data(item).build());
            }
        }
        catch (Throwable e) {
            LOGGER.log(Level.SEVERE, LocalizationMessages.EVENT_SINK_NEXT_FAILED(), e);
            this.cancelSubscription();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (throwable == null) {
            throw new NullPointerException(LocalizationMessages.PARAM_NULL("throwable"));
        }
        try {
            LOGGER.log(Level.SEVERE, LocalizationMessages.EVENT_SOURCE_DEFAULT_ONERROR(), throwable);
            super.close();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, LocalizationMessages.EVENT_SINK_CLOSE_FAILED(), e);
        }
    }

    @Override
    public void onComplete() {
        try {
            super.close();
        }
        catch (Throwable e) {
            LOGGER.log(Level.SEVERE, LocalizationMessages.EVENT_SINK_CLOSE_FAILED(), e);
        }
    }

    @Override
    public void close() {
        try {
            this.cancelSubscription();
            super.close();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, LocalizationMessages.EVENT_SINK_CLOSE_FAILED(), e);
        }
    }

    @Override
    public CompletionStage<?> send(OutboundSseEvent event) {
        this.checkClosed();
        try {
            this.write(event);
            return CompletableFuture.completedFuture(null);
        }
        catch (IOException e) {
            CompletableFuture future = new CompletableFuture();
            future.completeExceptionally(e);
            return future;
        }
    }

    @Override
    public void flush() throws IOException {
        super.flushQueue();
    }

    @Override
    protected void onClose(Exception e) {
        this.cancelSubscription();
    }

    private void cancelSubscription() {
        if (this.subscription != null) {
            this.subscription.cancel();
        }
    }

    private void checkClosed() {
        if (this.isClosed()) {
            this.cancelSubscription();
            throw new IllegalStateException(LocalizationMessages.EVENT_SOURCE_ALREADY_CLOSED());
        }
    }

    private MediaType resolveMediaType(Object item) {
        if (this.implicitMediaType == null) {
            Class<?> clazz = item.getClass();
            if (String.class.equals(clazz) || Number.class.isAssignableFrom(clazz) || Character.class.equals(clazz) || Boolean.class.equals(clazz)) {
                this.implicitMediaType = MediaType.TEXT_PLAIN_TYPE;
                return this.implicitMediaType;
            }
            this.implicitMediaType = MediaType.APPLICATION_JSON_TYPE;
        }
        return this.implicitMediaType;
    }
}

