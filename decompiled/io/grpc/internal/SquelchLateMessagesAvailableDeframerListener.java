/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.internal.ForwardingDeframerListener;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.MessageDeframer;
import io.grpc.internal.StreamListener;
import java.io.Closeable;

final class SquelchLateMessagesAvailableDeframerListener
extends ForwardingDeframerListener {
    private final MessageDeframer.Listener delegate;
    private boolean closed;

    public SquelchLateMessagesAvailableDeframerListener(MessageDeframer.Listener delegate) {
        this.delegate = delegate;
    }

    @Override
    protected MessageDeframer.Listener delegate() {
        return this.delegate;
    }

    @Override
    public void messagesAvailable(StreamListener.MessageProducer producer) {
        if (this.closed) {
            if (producer instanceof Closeable) {
                GrpcUtil.closeQuietly((Closeable)((Object)producer));
            }
            return;
        }
        super.messagesAvailable(producer);
    }

    @Override
    public void deframerClosed(boolean hasPartialMessage) {
        this.closed = true;
        super.deframerClosed(hasPartialMessage);
    }

    @Override
    public void deframeFailed(Throwable cause) {
        this.closed = true;
        super.deframeFailed(cause);
    }
}

