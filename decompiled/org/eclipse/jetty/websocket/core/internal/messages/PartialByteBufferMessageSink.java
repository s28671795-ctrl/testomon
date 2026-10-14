/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.messages;

import java.lang.invoke.MethodHandle;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.internal.messages.AbstractMessageSink;

public class PartialByteBufferMessageSink
extends AbstractMessageSink {
    public PartialByteBufferMessageSink(CoreSession session, MethodHandle methodHandle) {
        super(session, methodHandle);
    }

    @Override
    public void accept(Frame frame, Callback callback) {
        try {
            if (frame.hasPayload() || frame.isFin()) {
                this.methodHandle.invoke(frame.getPayload(), frame.isFin());
            }
            callback.succeeded();
            this.session.demand(1L);
        }
        catch (Throwable t2) {
            callback.failed(t2);
        }
    }
}

