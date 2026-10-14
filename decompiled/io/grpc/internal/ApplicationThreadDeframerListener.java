/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.internal.MessageDeframer;
import io.grpc.internal.StreamListener;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;

final class ApplicationThreadDeframerListener
implements MessageDeframer.Listener {
    private final TransportExecutor transportExecutor;
    private final MessageDeframer.Listener storedListener;
    private final Queue<InputStream> messageReadQueue = new ArrayDeque<InputStream>();

    public ApplicationThreadDeframerListener(MessageDeframer.Listener listener, TransportExecutor transportExecutor) {
        this.storedListener = Preconditions.checkNotNull(listener, "listener");
        this.transportExecutor = Preconditions.checkNotNull(transportExecutor, "transportExecutor");
    }

    @Override
    public void bytesRead(final int numBytes) {
        this.transportExecutor.runOnTransportThread(new Runnable(){

            @Override
            public void run() {
                ApplicationThreadDeframerListener.this.storedListener.bytesRead(numBytes);
            }
        });
    }

    @Override
    public void messagesAvailable(StreamListener.MessageProducer producer) {
        InputStream message;
        while ((message = producer.next()) != null) {
            this.messageReadQueue.add(message);
        }
    }

    @Override
    public void deframerClosed(final boolean hasPartialMessage) {
        this.transportExecutor.runOnTransportThread(new Runnable(){

            @Override
            public void run() {
                ApplicationThreadDeframerListener.this.storedListener.deframerClosed(hasPartialMessage);
            }
        });
    }

    @Override
    public void deframeFailed(final Throwable cause) {
        this.transportExecutor.runOnTransportThread(new Runnable(){

            @Override
            public void run() {
                ApplicationThreadDeframerListener.this.storedListener.deframeFailed(cause);
            }
        });
    }

    public InputStream messageReadQueuePoll() {
        return this.messageReadQueue.poll();
    }

    public static interface TransportExecutor {
        public void runOnTransportThread(Runnable var1);
    }
}

