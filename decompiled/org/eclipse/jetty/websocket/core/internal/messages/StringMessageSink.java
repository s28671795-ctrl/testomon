/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.messages;

import java.lang.invoke.MethodHandle;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.Utf8StringBuilder;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.exception.MessageTooLargeException;
import org.eclipse.jetty.websocket.core.internal.messages.AbstractMessageSink;

public class StringMessageSink
extends AbstractMessageSink {
    private Utf8StringBuilder out;
    private int size = 0;

    public StringMessageSink(CoreSession session, MethodHandle methodHandle) {
        super(session, methodHandle);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void accept(Frame frame, Callback callback) {
        try {
            this.size += frame.getPayloadLength();
            long maxTextMessageSize = this.session.getMaxTextMessageSize();
            if (maxTextMessageSize > 0L && (long)this.size > maxTextMessageSize) {
                throw new MessageTooLargeException(String.format("Text message too large: (actual) %,d > (configured max text message size) %,d", this.size, maxTextMessageSize));
            }
            if (this.out == null) {
                this.out = new Utf8StringBuilder(this.session.getInputBufferSize());
            }
            this.out.append(frame.getPayload());
            if (frame.isFin()) {
                this.methodHandle.invoke(this.out.toString());
            }
            callback.succeeded();
            this.session.demand(1L);
        }
        catch (Throwable t2) {
            callback.failed(t2);
        }
        finally {
            if (frame.isFin()) {
                this.size = 0;
                this.out = null;
            }
        }
    }

    @Override
    public void fail(Throwable failure) {
        if (this.out != null) {
            this.out.reset();
        }
    }
}

