/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

public class WebSocketAdapter
implements WebSocketListener {
    private volatile Session session;
    private RemoteEndpoint remote;

    public RemoteEndpoint getRemote() {
        return this.remote;
    }

    public Session getSession() {
        return this.session;
    }

    public boolean isConnected() {
        Session sess = this.session;
        return sess != null && sess.isOpen();
    }

    public boolean isNotConnected() {
        return !this.isConnected();
    }

    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
    }

    @Override
    public void onWebSocketConnect(Session sess) {
        this.session = sess;
        this.remote = sess.getRemote();
    }

    @Override
    public void onWebSocketError(Throwable cause) {
    }

    @Override
    public void onWebSocketText(String message) {
    }
}

