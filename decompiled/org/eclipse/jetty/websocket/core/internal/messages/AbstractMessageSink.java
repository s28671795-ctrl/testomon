/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.messages;

import java.lang.invoke.MethodHandle;
import java.util.Objects;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.internal.messages.MessageSink;

public abstract class AbstractMessageSink
implements MessageSink {
    protected final CoreSession session;
    protected final MethodHandle methodHandle;

    public AbstractMessageSink(CoreSession session, MethodHandle methodHandle) {
        this.session = Objects.requireNonNull(session, "CoreSession");
        this.methodHandle = Objects.requireNonNull(methodHandle, "MethodHandle");
    }

    @Override
    public void fail(Throwable failure) {
    }
}

