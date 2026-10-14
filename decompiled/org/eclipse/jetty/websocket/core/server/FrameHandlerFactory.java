/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server;

import org.eclipse.jetty.websocket.core.FrameHandler;
import org.eclipse.jetty.websocket.core.server.ServerUpgradeRequest;
import org.eclipse.jetty.websocket.core.server.ServerUpgradeResponse;

public interface FrameHandlerFactory {
    public FrameHandler newFrameHandler(Object var1, ServerUpgradeRequest var2, ServerUpgradeResponse var3);
}

