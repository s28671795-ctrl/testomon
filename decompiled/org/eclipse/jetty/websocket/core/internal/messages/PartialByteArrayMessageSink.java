/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.messages;

import java.lang.invoke.MethodHandle;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.internal.messages.AbstractMessageSink;

public class PartialByteArrayMessageSink
extends AbstractMessageSink {
    private static final byte[] EMPTY_BUFFER = new byte[0];

    public PartialByteArrayMessageSink(CoreSession session, MethodHandle methodHandle) {
        super(session, methodHandle);
    }

    @Override
    public void accept(Frame frame, Callback callback) {
        try {
            if (frame.hasPayload() || frame.isFin()) {
                byte[] buffer = frame.hasPayload() ? BufferUtil.toArray(frame.getPayload()) : EMPTY_BUFFER;
                this.methodHandle.invoke(buffer, frame.isFin());
            }
            callback.succeeded();
            this.session.demand(1L);
        }
        catch (Throwable t2) {
            callback.failed(t2);
        }
    }
}

