/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server.internal;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.FrameHandler;
import org.eclipse.jetty.websocket.core.server.FrameHandlerFactory;
import org.eclipse.jetty.websocket.core.server.ServerUpgradeRequest;
import org.eclipse.jetty.websocket.core.server.ServerUpgradeResponse;
import org.eclipse.jetty.websocket.core.server.WebSocketCreator;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiation;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiator;

public class CreatorNegotiator
extends WebSocketNegotiator.AbstractNegotiator {
    private final WebSocketCreator creator;
    private final FrameHandlerFactory factory;

    public CreatorNegotiator(WebSocketCreator creator, FrameHandlerFactory factory2) {
        this(creator, factory2, null);
    }

    public CreatorNegotiator(WebSocketCreator creator, FrameHandlerFactory factory2, Configuration.Customizer customizer) {
        super(customizer);
        this.creator = creator;
        this.factory = factory2;
    }

    public WebSocketCreator getWebSocketCreator() {
        return this.creator;
    }

    @Override
    public FrameHandler negotiate(WebSocketNegotiation negotiation) throws IOException {
        ServletContext servletContext = negotiation.getRequest().getServletContext();
        if (servletContext == null) {
            throw new IllegalStateException("null servletContext from request");
        }
        ServerUpgradeRequest upgradeRequest = new ServerUpgradeRequest(negotiation);
        ServerUpgradeResponse upgradeResponse = new ServerUpgradeResponse(negotiation);
        AtomicReference result = new AtomicReference();
        ((ContextHandler.Context)servletContext).getContextHandler().handle(() -> result.set(this.creator.createWebSocket(upgradeRequest, upgradeResponse)));
        Object websocketPojo = result.get();
        if (upgradeResponse.isCommitted()) {
            return null;
        }
        if (websocketPojo == null) {
            upgradeResponse.sendError(503, "WebSocket Endpoint Creation Refused");
            return null;
        }
        return this.factory.newFrameHandler(websocketPojo, upgradeRequest, upgradeResponse);
    }

    @Override
    public String toString() {
        return String.format("%s@%x{%s,%s}", this.getClass().getSimpleName(), this.hashCode(), this.creator, this.factory);
    }
}

