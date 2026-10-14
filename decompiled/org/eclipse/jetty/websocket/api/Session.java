/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

import java.io.Closeable;
import java.net.SocketAddress;
import org.eclipse.jetty.websocket.api.CloseStatus;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.SuspendToken;
import org.eclipse.jetty.websocket.api.UpgradeRequest;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.api.WriteCallback;

public interface Session
extends WebSocketPolicy,
Closeable {
    @Override
    public void close();

    public void close(CloseStatus var1);

    public void close(int var1, String var2);

    default public void close(int statusCode, String reason, WriteCallback callback) {
        try {
            this.close(statusCode, reason);
            callback.writeSuccess();
        }
        catch (Throwable t2) {
            callback.writeFailed(t2);
        }
    }

    public void disconnect();

    public SocketAddress getLocalAddress();

    default public WebSocketPolicy getPolicy() {
        return this;
    }

    public String getProtocolVersion();

    public RemoteEndpoint getRemote();

    public SocketAddress getRemoteAddress();

    public UpgradeRequest getUpgradeRequest();

    public UpgradeResponse getUpgradeResponse();

    public boolean isOpen();

    public boolean isSecure();

    public SuspendToken suspend();
}

