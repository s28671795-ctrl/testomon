/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiator;
import org.eclipse.jetty.websocket.core.server.internal.HandshakerSelector;

public interface Handshaker {
    public static Handshaker newInstance() {
        return new HandshakerSelector();
    }

    public boolean upgradeRequest(WebSocketNegotiator var1, HttpServletRequest var2, HttpServletResponse var3, WebSocketComponents var4, Configuration.Customizer var5) throws IOException;
}

