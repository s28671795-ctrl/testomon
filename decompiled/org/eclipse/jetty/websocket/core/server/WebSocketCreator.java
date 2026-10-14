/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server;

import org.eclipse.jetty.websocket.core.server.ServerUpgradeRequest;
import org.eclipse.jetty.websocket.core.server.ServerUpgradeResponse;

public interface WebSocketCreator {
    public Object createWebSocket(ServerUpgradeRequest var1, ServerUpgradeResponse var2);
}

