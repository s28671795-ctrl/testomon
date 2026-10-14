/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.common;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.FutureCallback;
import org.eclipse.jetty.websocket.api.BatchMode;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.WriteCallback;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.exception.ProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyWebSocketRemoteEndpoint
implements RemoteEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(JettyWebSocketRemoteEndpoint.class);
    private final CoreSession coreSession;
    private byte messageType = (byte)-1;
    private BatchMode batchMode;

    public JettyWebSocketRemoteEndpoint(CoreSession coreSession, BatchMode batchMode) {
        this.coreSession = Objects.requireNonNull(coreSession);
        this.batchMode = batchMode;
    }

    @Override
    public void sendString(String text) throws IOException {
        this.sendBlocking(new Frame(1).setPayload(text));
    }

    @Override
    public void sendString(String text, WriteCallback callback) {
        Callback cb = callback == null ? Callback.NOOP : Callback.from(callback::writeSuccess, callback::writeFailed);
        this.coreSession.sendFrame(new Frame(1).setPayload(text), cb, this.isBatch());
    }

    @Override
    public void sendBytes(ByteBuffer data) throws IOException {
        this.sendBlocking(new Frame(2).setPayload(data));
    }

    @Override
    public void sendBytes(ByteBuffer data, WriteCallback callback) {
        this.coreSession.sendFrame(new Frame(2).setPayload(data), Callback.from(callback::writeSuccess, callback::writeFailed), this.isBatch());
    }

    @Override
    public void sendPartialBytes(ByteBuffer fragment, boolean isLast) throws IOException {
        FutureCallback b = new FutureCallback();
        this.sendPartialBytes(fragment, isLast, b);
        b.block();
    }

    @Override
    public void sendPartialBytes(ByteBuffer fragment, boolean isLast, WriteCallback callback) {
        this.sendPartialBytes(fragment, isLast, Callback.from(callback::writeSuccess, callback::writeFailed));
    }

    private void sendPartialBytes(ByteBuffer fragment, boolean isLast, Callback callback) {
        Frame frame;
        switch (this.messageType) {
            case -1: {
                frame = new Frame(2);
                this.messageType = (byte)2;
                break;
            }
            case 2: {
                frame = new Frame(0);
                break;
            }
            default: {
                callback.failed(new ProtocolException("Attempt to send Partial Binary during active opcode " + this.messageType));
                return;
            }
        }
        frame.setPayload(fragment);
        frame.setFin(isLast);
        this.coreSession.sendFrame(frame, callback, this.isBatch());
        if (isLast) {
            this.messageType = (byte)-1;
        }
    }

    @Override
    public void sendPartialString(String fragment, boolean isLast) throws IOException {
        FutureCallback b = new FutureCallback();
        this.sendPartialText(fragment, isLast, b);
        b.block();
    }

    @Override
    public void sendPartialString(String fragment, boolean isLast, WriteCallback callback) {
        this.sendPartialText(fragment, isLast, Callback.from(callback::writeSuccess, callback::writeFailed));
    }

    @Override
    public void sendPing(ByteBuffer applicationData) throws IOException {
        this.sendBlocking(new Frame(9).setPayload(applicationData));
    }

    @Override
    public void sendPing(ByteBuffer applicationData, WriteCallback callback) {
        this.coreSession.sendFrame(new Frame(9).setPayload(applicationData), Callback.from(callback::writeSuccess, callback::writeFailed), false);
    }

    @Override
    public void sendPong(ByteBuffer applicationData) throws IOException {
        this.sendBlocking(new Frame(10).setPayload(applicationData));
    }

    @Override
    public void sendPong(ByteBuffer applicationData, WriteCallback callback) {
        this.coreSession.sendFrame(new Frame(10).setPayload(applicationData), Callback.from(callback::writeSuccess, callback::writeFailed), false);
    }

    private void sendPartialText(String fragment, boolean isLast, Callback callback) {
        Frame frame;
        switch (this.messageType) {
            case -1: {
                frame = new Frame(1);
                this.messageType = 1;
                break;
            }
            case 1: {
                frame = new Frame(0);
                break;
            }
            default: {
                callback.failed(new ProtocolException("Attempt to send Partial Text during active opcode " + this.messageType));
                return;
            }
        }
        frame.setPayload(BufferUtil.toBuffer(fragment, StandardCharsets.UTF_8));
        frame.setFin(isLast);
        this.coreSession.sendFrame(frame, callback, this.isBatch());
        if (isLast) {
            this.messageType = (byte)-1;
        }
    }

    private void sendBlocking(Frame frame) throws IOException {
        FutureCallback b = new FutureCallback();
        this.coreSession.sendFrame(frame, b, false);
        b.block();
    }

    @Override
    public BatchMode getBatchMode() {
        return this.batchMode;
    }

    @Override
    public void setBatchMode(BatchMode mode) {
        this.batchMode = mode;
    }

    @Override
    public int getMaxOutgoingFrames() {
        return this.coreSession.getMaxOutgoingFrames();
    }

    @Override
    public void setMaxOutgoingFrames(int maxOutgoingFrames) {
        this.coreSession.setMaxOutgoingFrames(maxOutgoingFrames);
    }

    private boolean isBatch() {
        return BatchMode.ON == this.batchMode;
    }

    @Override
    public SocketAddress getRemoteAddress() {
        return this.coreSession.getRemoteAddress();
    }

    @Override
    public void flush() throws IOException {
        FutureCallback b = new FutureCallback();
        this.coreSession.flush(b);
        b.block();
    }
}

