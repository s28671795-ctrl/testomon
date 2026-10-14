/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.messages;

import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.websocket.core.Frame;

public interface MessageSink {
    public void accept(Frame var1, Callback var2);

    public void fail(Throwable var1);
}

