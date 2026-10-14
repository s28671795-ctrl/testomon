/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.common;

import java.io.IOException;
import java.net.SocketAddress;
import java.time.Duration;
import java.util.Objects;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.websocket.api.CloseStatus;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.SuspendToken;
import org.eclipse.jetty.websocket.api.UpgradeRequest;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketContainer;
import org.eclipse.jetty.websocket.api.WriteCallback;
import org.eclipse.jetty.websocket.common.JettyWebSocketFrameHandler;
import org.eclipse.jetty.websocket.common.JettyWebSocketRemoteEndpoint;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketSession
implements Session,
SuspendToken,
Dumpable {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketSession.class);
    private final CoreSession coreSession;
    private final JettyWebSocketFrameHandler frameHandler;
    private final JettyWebSocketRemoteEndpoint remoteEndpoint;
    private final UpgradeRequest upgradeRequest;
    private final UpgradeResponse upgradeResponse;

    public WebSocketSession(WebSocketContainer container, CoreSession coreSession, JettyWebSocketFrameHandler frameHandler) {
        this.frameHandler = Objects.requireNonNull(frameHandler);
        this.coreSession = Objects.requireNonNull(coreSession);
        this.upgradeRequest = frameHandler.getUpgradeRequest();
        this.upgradeResponse = frameHandler.getUpgradeResponse();
        this.remoteEndpoint = new JettyWebSocketRemoteEndpoint(coreSession, frameHandler.getBatchMode());
        container.notifySessionListeners(listener -> listener.onWebSocketSessionCreated(this));
    }

    @Override
    public void close() {
        this.coreSession.close(1000, null, Callback.NOOP);
    }

    @Override
    public void close(CloseStatus closeStatus) {
        this.coreSession.close(closeStatus.getCode(), closeStatus.getPhrase(), Callback.NOOP);
    }

    @Override
    public void close(int statusCode, String reason) {
        this.coreSession.close(statusCode, reason, Callback.NOOP);
    }

    @Override
    public void close(int statusCode, String reason, WriteCallback callback) {
        this.coreSession.close(statusCode, reason, Callback.from(callback::writeSuccess, callback::writeFailed));
    }

    @Override
    public WebSocketBehavior getBehavior() {
        switch (this.coreSession.getBehavior()) {
            case CLIENT: {
                return WebSocketBehavior.CLIENT;
            }
            case SERVER: {
                return WebSocketBehavior.SERVER;
            }
        }
        return null;
    }

    @Override
    public Duration getIdleTimeout() {
        return this.coreSession.getIdleTimeout();
    }

    @Override
    public int getInputBufferSize() {
        return this.coreSession.getInputBufferSize();
    }

    @Override
    public int getOutputBufferSize() {
        return this.coreSession.getOutputBufferSize();
    }

    @Override
    public long getMaxBinaryMessageSize() {
        return this.coreSession.getMaxBinaryMessageSize();
    }

    @Override
    public long getMaxTextMessageSize() {
        return this.coreSession.getMaxTextMessageSize();
    }

    @Override
    public long getMaxFrameSize() {
        return this.coreSession.getMaxFrameSize();
    }

    @Override
    public boolean isAutoFragment() {
        return this.coreSession.isAutoFragment();
    }

    @Override
    public void setIdleTimeout(Duration duration) {
        this.coreSession.setIdleTimeout(duration);
    }

    @Override
    public void setInputBufferSize(int size) {
        this.coreSession.setInputBufferSize(size);
    }

    @Override
    public void setOutputBufferSize(int size) {
        this.coreSession.setOutputBufferSize(size);
    }

    @Override
    public void setMaxBinaryMessageSize(long size) {
        this.coreSession.setMaxBinaryMessageSize(size);
    }

    @Override
    public void setMaxTextMessageSize(long size) {
        this.coreSession.setMaxTextMessageSize(size);
    }

    @Override
    public void setMaxFrameSize(long maxFrameSize) {
        this.coreSession.setMaxFrameSize(maxFrameSize);
    }

    @Override
    public void setAutoFragment(boolean autoFragment) {
        this.coreSession.setAutoFragment(autoFragment);
    }

    @Override
    public String getProtocolVersion() {
        return this.upgradeRequest.getProtocolVersion();
    }

    @Override
    public JettyWebSocketRemoteEndpoint getRemote() {
        return this.remoteEndpoint;
    }

    @Override
    public boolean isOpen() {
        return this.coreSession.isOutputOpen();
    }

    @Override
    public boolean isSecure() {
        return this.upgradeRequest.isSecure();
    }

    @Override
    public void disconnect() {
        this.coreSession.abort();
    }

    @Override
    public SocketAddress getLocalAddress() {
        return this.coreSession.getLocalAddress();
    }

    @Override
    public SocketAddress getRemoteAddress() {
        return this.coreSession.getRemoteAddress();
    }

    @Override
    public UpgradeRequest getUpgradeRequest() {
        return this.upgradeRequest;
    }

    @Override
    public UpgradeResponse getUpgradeResponse() {
        return this.upgradeResponse;
    }

    @Override
    public SuspendToken suspend() {
        this.frameHandler.suspend();
        return this;
    }

    @Override
    public void resume() {
        this.frameHandler.resume();
    }

    public CoreSession getCoreSession() {
        return this.coreSession;
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        Dumpable.dumpObjects(out, indent, this, this.upgradeRequest, this.coreSession, this.remoteEndpoint, this.frameHandler);
    }

    @Override
    public String dumpSelf() {
        return String.format("%s@%x[behavior=%s,idleTimeout=%dms]", new Object[]{this.getClass().getSimpleName(), this.hashCode(), this.getPolicy().getBehavior(), this.getIdleTimeout().toMillis()});
    }

    public String toString() {
        return String.format("WebSocketSession[%s,to=%s,%s,%s]", new Object[]{this.getBehavior(), this.getIdleTimeout(), this.coreSession, this.frameHandler});
    }
}

