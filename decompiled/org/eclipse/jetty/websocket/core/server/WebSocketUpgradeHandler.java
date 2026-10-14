/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.eclipse.jetty.http.pathmap.PathSpec;
import org.eclipse.jetty.http.pathmap.ServletPathSpec;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.server.WebSocketMappings;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiator;

public class WebSocketUpgradeHandler
extends HandlerWrapper {
    private final WebSocketMappings mappings;
    private final Configuration.ConfigurationCustomizer customizer = new Configuration.ConfigurationCustomizer();

    public WebSocketUpgradeHandler() {
        this(new WebSocketComponents());
    }

    public WebSocketUpgradeHandler(WebSocketComponents components) {
        this.mappings = new WebSocketMappings(components);
    }

    public void addMapping(String pathSpec, WebSocketNegotiator negotiator) {
        this.mappings.addMapping(new ServletPathSpec(pathSpec), negotiator);
    }

    public void addMapping(PathSpec pathSpec, WebSocketNegotiator negotiator) {
        this.mappings.addMapping(pathSpec, negotiator);
    }

    public Configuration getConfiguration() {
        return this.customizer;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (this.mappings.upgrade(request, response, this.customizer)) {
            return;
        }
        if (!baseRequest.isHandled()) {
            super.handle(target, baseRequest, request, response);
        }
    }
}

