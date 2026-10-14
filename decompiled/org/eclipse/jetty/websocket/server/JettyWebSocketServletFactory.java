/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.server;

import java.util.Set;
import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.server.JettyWebSocketCreator;

public interface JettyWebSocketServletFactory
extends WebSocketPolicy {
    public void addMapping(String var1, JettyWebSocketCreator var2);

    public void register(Class<?> var1);

    public void setCreator(JettyWebSocketCreator var1);

    public JettyWebSocketCreator getMapping(String var1);

    public boolean removeMapping(String var1);

    public Set<String> getAvailableExtensionNames();

    @Override
    default public WebSocketBehavior getBehavior() {
        return WebSocketBehavior.SERVER;
    }
}

