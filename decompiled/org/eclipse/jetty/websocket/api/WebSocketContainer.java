/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketSessionListener;

public interface WebSocketContainer {
    public Executor getExecutor();

    public Collection<Session> getOpenSessions();

    public void addSessionListener(WebSocketSessionListener var1);

    public boolean removeSessionListener(WebSocketSessionListener var1);

    public void notifySessionListeners(Consumer<WebSocketSessionListener> var1);
}

