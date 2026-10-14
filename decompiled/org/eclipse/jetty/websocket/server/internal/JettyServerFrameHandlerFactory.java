/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.server.internal;

import jakarta.servlet.ServletContext;
import org.eclipse.jetty.websocket.common.JettyWebSocketFrameHandler;
import org.eclipse.jetty.websocket.common.JettyWebSocketFrameHandlerFactory;
import org.eclipse.jetty.websocket.core.FrameHandler;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.server.FrameHandlerFactory;
import org.eclipse.jetty.websocket.core.server.ServerUpgradeRequest;
import org.eclipse.jetty.websocket.core.server.ServerUpgradeResponse;
import org.eclipse.jetty.websocket.server.JettyWebSocketServerContainer;
import org.eclipse.jetty.websocket.server.internal.DelegatedServerUpgradeRequest;
import org.eclipse.jetty.websocket.server.internal.DelegatedServerUpgradeResponse;

public class JettyServerFrameHandlerFactory
extends JettyWebSocketFrameHandlerFactory
implements FrameHandlerFactory {
    public static JettyServerFrameHandlerFactory getFactory(ServletContext servletContext) {
        JettyWebSocketServerContainer container = JettyWebSocketServerContainer.getContainer(servletContext);
        return container == null ? null : container.getBean(JettyServerFrameHandlerFactory.class);
    }

    public JettyServerFrameHandlerFactory(JettyWebSocketServerContainer container, WebSocketComponents components) {
        super(container, components);
    }

    @Override
    public FrameHandler newFrameHandler(Object websocketPojo, ServerUpgradeRequest upgradeRequest, ServerUpgradeResponse upgradeResponse) {
        JettyWebSocketFrameHandler frameHandler = super.newJettyFrameHandler(websocketPojo);
        frameHandler.setUpgradeRequest(new DelegatedServerUpgradeRequest(upgradeRequest));
        frameHandler.setUpgradeResponse(new DelegatedServerUpgradeResponse(upgradeResponse));
        return frameHandler;
    }
}

