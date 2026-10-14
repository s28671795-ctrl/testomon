/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.messages;

import java.lang.invoke.MethodHandle;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.internal.messages.DispatchedMessageSink;
import org.eclipse.jetty.websocket.core.internal.messages.MessageInputStream;
import org.eclipse.jetty.websocket.core.internal.messages.MessageSink;

public class InputStreamMessageSink
extends DispatchedMessageSink {
    public InputStreamMessageSink(CoreSession session, MethodHandle methodHandle) {
        super(session, methodHandle);
    }

    @Override
    public MessageSink newSink(Frame frame) {
        return new MessageInputStream();
    }
}

