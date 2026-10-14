/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core;

import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.websocket.core.Frame;

public interface OutgoingFrames {
    public void sendFrame(Frame var1, Callback var2, boolean var3);
}

