/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.messages;

import java.lang.invoke.MethodHandle;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.internal.messages.DispatchedMessageSink;
import org.eclipse.jetty.websocket.core.internal.messages.MessageReader;

public class ReaderMessageSink
extends DispatchedMessageSink {
    public ReaderMessageSink(CoreSession session, MethodHandle methodHandle) {
        super(session, methodHandle);
    }

    @Override
    public MessageReader newSink(Frame frame) {
        return new MessageReader(this.session.getInputBufferSize());
    }
}

