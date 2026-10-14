/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.messages;

import java.lang.invoke.MethodHandle;
import java.util.Objects;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.Utf8StringBuilder;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.internal.messages.AbstractMessageSink;

public class PartialStringMessageSink
extends AbstractMessageSink {
    private Utf8StringBuilder out;

    public PartialStringMessageSink(CoreSession session, MethodHandle methodHandle) {
        super(session, methodHandle);
        Objects.requireNonNull(methodHandle, "MethodHandle");
    }

    @Override
    public void accept(Frame frame, Callback callback) {
        try {
            if (this.out == null) {
                this.out = new Utf8StringBuilder(this.session.getInputBufferSize());
            }
            this.out.append(frame.getPayload());
            if (frame.isFin()) {
                this.methodHandle.invoke(this.out.toString(), true);
                this.out = null;
            } else {
                this.methodHandle.invoke(this.out.takePartialString(), false);
            }
            callback.succeeded();
            this.session.demand(1L);
        }
        catch (Throwable t2) {
            callback.failed(t2);
        }
    }
}

