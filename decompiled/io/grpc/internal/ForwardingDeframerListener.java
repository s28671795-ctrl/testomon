/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.internal.MessageDeframer;
import io.grpc.internal.StreamListener;

abstract class ForwardingDeframerListener
implements MessageDeframer.Listener {
    ForwardingDeframerListener() {
    }

    protected abstract MessageDeframer.Listener delegate();

    @Override
    public void bytesRead(int numBytes) {
        this.delegate().bytesRead(numBytes);
    }

    @Override
    public void messagesAvailable(StreamListener.MessageProducer producer) {
        this.delegate().messagesAvailable(producer);
    }

    @Override
    public void deframerClosed(boolean hasPartialMessage) {
        this.delegate().deframerClosed(hasPartialMessage);
    }

    @Override
    public void deframeFailed(Throwable cause) {
        this.delegate().deframeFailed(cause);
    }
}

