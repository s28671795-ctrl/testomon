/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.server;

import org.eclipse.jetty.websocket.server.JettyServerUpgradeRequest;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeResponse;

public interface JettyWebSocketCreator {
    public Object createWebSocket(JettyServerUpgradeRequest var1, JettyServerUpgradeResponse var2);
}

