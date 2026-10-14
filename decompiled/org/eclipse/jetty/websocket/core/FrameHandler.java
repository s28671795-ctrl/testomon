/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core;

import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.websocket.core.CloseStatus;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.IncomingFrames;

public interface FrameHandler
extends IncomingFrames {
    public void onOpen(CoreSession var1, Callback var2);

    @Override
    public void onFrame(Frame var1, Callback var2);

    public void onError(Throwable var1, Callback var2);

    public void onClosed(CloseStatus var1, Callback var2);

    default public boolean isDemanding() {
        return false;
    }
}

