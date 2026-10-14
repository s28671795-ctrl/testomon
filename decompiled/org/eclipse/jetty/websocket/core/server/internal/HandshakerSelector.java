/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server.internal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.server.Handshaker;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiator;
import org.eclipse.jetty.websocket.core.server.internal.RFC6455Handshaker;
import org.eclipse.jetty.websocket.core.server.internal.RFC8441Handshaker;

public class HandshakerSelector
implements Handshaker {
    private final RFC6455Handshaker rfc6455 = new RFC6455Handshaker();
    private final RFC8441Handshaker rfc8441 = new RFC8441Handshaker();

    @Override
    public boolean upgradeRequest(WebSocketNegotiator negotiator, HttpServletRequest request, HttpServletResponse response, WebSocketComponents components, Configuration.Customizer defaultCustomizer) throws IOException {
        return this.rfc6455.upgradeRequest(negotiator, request, response, components, defaultCustomizer) || !response.isCommitted() && this.rfc8441.upgradeRequest(negotiator, request, response, components, defaultCustomizer);
    }
}

