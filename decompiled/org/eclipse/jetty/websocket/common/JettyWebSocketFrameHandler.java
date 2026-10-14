/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.common;

import java.lang.invoke.MethodHandle;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.websocket.api.BatchMode;
import org.eclipse.jetty.websocket.api.UpgradeRequest;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.eclipse.jetty.websocket.api.WebSocketContainer;
import org.eclipse.jetty.websocket.api.WriteCallback;
import org.eclipse.jetty.websocket.api.exceptions.InvalidWebSocketException;
import org.eclipse.jetty.websocket.api.exceptions.MessageTooLargeException;
import org.eclipse.jetty.websocket.api.exceptions.ProtocolException;
import org.eclipse.jetty.websocket.api.exceptions.UpgradeException;
import org.eclipse.jetty.websocket.api.exceptions.WebSocketTimeoutException;
import org.eclipse.jetty.websocket.common.JettyWebSocketFrame;
import org.eclipse.jetty.websocket.common.JettyWebSocketFrameHandlerFactory;
import org.eclipse.jetty.websocket.common.WebSocketSession;
import org.eclipse.jetty.websocket.core.CloseStatus;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.FrameHandler;
import org.eclipse.jetty.websocket.core.exception.BadPayloadException;
import org.eclipse.jetty.websocket.core.exception.CloseException;
import org.eclipse.jetty.websocket.core.exception.InvalidSignatureException;
import org.eclipse.jetty.websocket.core.exception.WebSocketException;
import org.eclipse.jetty.websocket.core.internal.messages.MessageSink;
import org.eclipse.jetty.websocket.core.internal.util.InvokerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyWebSocketFrameHandler
implements FrameHandler {
    private final AutoLock lock = new AutoLock();
    private final Logger log;
    private final WebSocketContainer container;
    private final Object endpointInstance;
    private final BatchMode batchMode;
    private final AtomicBoolean closeNotified = new AtomicBoolean();
    private MethodHandle openHandle;
    private MethodHandle closeHandle;
    private MethodHandle errorHandle;
    private MethodHandle textHandle;
    private final Class<? extends MessageSink> textSinkClass;
    private MethodHandle binaryHandle;
    private final Class<? extends MessageSink> binarySinkClass;
    private MethodHandle frameHandle;
    private MethodHandle pingHandle;
    private MethodHandle pongHandle;
    private UpgradeRequest upgradeRequest;
    private UpgradeResponse upgradeResponse;
    private final Configuration.Customizer customizer;
    private MessageSink textSink;
    private MessageSink binarySink;
    private MessageSink activeMessageSink;
    private WebSocketSession session;
    private SuspendState state = SuspendState.DEMANDING;
    private Frame delayedFrame;
    private Callback delayedCallback;

    public JettyWebSocketFrameHandler(WebSocketContainer container, Object endpointInstance, MethodHandle openHandle, MethodHandle closeHandle, MethodHandle errorHandle, MethodHandle textHandle, MethodHandle binaryHandle, Class<? extends MessageSink> textSinkClass, Class<? extends MessageSink> binarySinkClass, MethodHandle frameHandle, MethodHandle pingHandle, MethodHandle pongHandle, BatchMode batchMode, Configuration.Customizer customizer) {
        this.log = LoggerFactory.getLogger(endpointInstance.getClass());
        this.container = container;
        this.endpointInstance = endpointInstance;
        this.openHandle = openHandle;
        this.closeHandle = closeHandle;
        this.errorHandle = errorHandle;
        this.textHandle = textHandle;
        this.binaryHandle = binaryHandle;
        this.textSinkClass = textSinkClass;
        this.binarySinkClass = binarySinkClass;
        this.frameHandle = frameHandle;
        this.pingHandle = pingHandle;
        this.pongHandle = pongHandle;
        this.batchMode = batchMode;
        this.customizer = customizer;
    }

    public void setUpgradeRequest(UpgradeRequest upgradeRequest) {
        this.upgradeRequest = upgradeRequest;
    }

    public void setUpgradeResponse(UpgradeResponse upgradeResponse) {
        this.upgradeResponse = upgradeResponse;
    }

    public UpgradeRequest getUpgradeRequest() {
        return this.upgradeRequest;
    }

    public UpgradeResponse getUpgradeResponse() {
        return this.upgradeResponse;
    }

    public BatchMode getBatchMode() {
        return this.batchMode;
    }

    public WebSocketSession getSession() {
        return this.session;
    }

    @Override
    public void onOpen(CoreSession coreSession, Callback callback) {
        try {
            this.customizer.customize(coreSession);
            this.session = new WebSocketSession(this.container, coreSession, this);
            if (!this.session.isOpen()) {
                throw new IllegalStateException("Session is not open");
            }
            this.frameHandle = InvokerUtils.bindTo(this.frameHandle, this.session);
            this.openHandle = InvokerUtils.bindTo(this.openHandle, this.session);
            this.closeHandle = InvokerUtils.bindTo(this.closeHandle, this.session);
            this.errorHandle = InvokerUtils.bindTo(this.errorHandle, this.session);
            this.textHandle = InvokerUtils.bindTo(this.textHandle, this.session);
            this.binaryHandle = InvokerUtils.bindTo(this.binaryHandle, this.session);
            this.pingHandle = InvokerUtils.bindTo(this.pingHandle, this.session);
            this.pongHandle = InvokerUtils.bindTo(this.pongHandle, this.session);
            Executor executor2 = this.container.getExecutor();
            if (this.textHandle != null) {
                this.textSink = JettyWebSocketFrameHandlerFactory.createMessageSink(this.textHandle, this.textSinkClass, executor2, this.session);
            }
            if (this.binaryHandle != null) {
                this.binarySink = JettyWebSocketFrameHandlerFactory.createMessageSink(this.binaryHandle, this.binarySinkClass, executor2, this.session);
            }
            if (this.openHandle != null) {
                this.openHandle.invoke();
            }
            if (this.session.isOpen()) {
                this.container.notifySessionListeners(listener -> listener.onWebSocketSessionOpened(this.session));
            }
            callback.succeeded();
            this.demand();
        }
        catch (Throwable cause) {
            callback.failed(new WebSocketException(this.endpointInstance.getClass().getSimpleName() + " OPEN method error: " + cause.getMessage(), cause));
        }
    }

    @Override
    public void onFrame(Frame frame, Callback callback) {
        try (AutoLock l = this.lock.lock();){
            switch (this.state) {
                case DEMANDING: {
                    break;
                }
                case SUSPENDING: {
                    assert (this.delayedFrame == null && this.delayedCallback == null);
                    this.delayedFrame = frame;
                    this.delayedCallback = callback;
                    this.state = SuspendState.SUSPENDED;
                    return;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
            if (frame.getOpCode() == 8) {
                this.state = SuspendState.CLOSED;
            }
        }
        if (this.frameHandle != null) {
            try {
                this.frameHandle.invoke(new JettyWebSocketFrame(frame));
            }
            catch (Throwable cause) {
                throw new WebSocketException(this.endpointInstance.getClass().getSimpleName() + " FRAME method error: " + cause.getMessage(), cause);
            }
        }
        switch (frame.getOpCode()) {
            case 8: {
                this.onCloseFrame(frame, callback);
                break;
            }
            case 9: {
                this.onPingFrame(frame, callback);
                break;
            }
            case 10: {
                this.onPongFrame(frame, callback);
                break;
            }
            case 1: {
                this.onTextFrame(frame, callback);
                break;
            }
            case 2: {
                this.onBinaryFrame(frame, callback);
                break;
            }
            case 0: {
                this.onContinuationFrame(frame, callback);
                break;
            }
            default: {
                callback.failed(new IllegalStateException());
            }
        }
    }

    @Override
    public void onError(Throwable cause, Callback callback) {
        try {
            cause = JettyWebSocketFrameHandler.convertCause(cause);
            if (this.errorHandle != null) {
                this.errorHandle.invoke(cause);
            } else if (this.log.isDebugEnabled()) {
                this.log.warn("Unhandled Error: Endpoint {}", (Object)this.endpointInstance.getClass().getName(), (Object)cause);
            } else {
                this.log.warn("Unhandled Error: Endpoint {} : {}", (Object)this.endpointInstance.getClass().getName(), (Object)cause.toString());
            }
            callback.succeeded();
        }
        catch (Throwable t2) {
            WebSocketException wsError = new WebSocketException(this.endpointInstance.getClass().getSimpleName() + " ERROR method error: " + cause.getMessage(), t2);
            wsError.addSuppressed(cause);
            callback.failed(wsError);
        }
    }

    private void onCloseFrame(Frame frame, Callback callback) {
        this.notifyOnClose(CloseStatus.getCloseStatus(frame), callback);
    }

    @Override
    public void onClosed(CloseStatus closeStatus, Callback callback) {
        Callback delayedCallback;
        try (AutoLock l = this.lock.lock();){
            this.state = SuspendState.CLOSED;
            this.delayedFrame = null;
            delayedCallback = this.delayedCallback;
            this.delayedCallback = null;
        }
        CloseException closeException = new CloseException(closeStatus.getCode(), closeStatus.getCause());
        if (delayedCallback != null) {
            delayedCallback.failed(closeException);
        }
        if (this.textSink != null) {
            this.textSink.fail(closeException);
        }
        if (this.binarySink != null) {
            this.binarySink.fail(closeException);
        }
        this.notifyOnClose(closeStatus, callback);
        this.container.notifySessionListeners(listener -> listener.onWebSocketSessionClosed(this.session));
    }

    private void notifyOnClose(CloseStatus closeStatus, Callback callback) {
        if (!this.closeNotified.compareAndSet(false, true)) {
            callback.failed(new ClosedChannelException());
            return;
        }
        try {
            if (this.closeHandle != null) {
                this.closeHandle.invoke(closeStatus.getCode(), closeStatus.getReason());
            }
            callback.succeeded();
        }
        catch (Throwable cause) {
            callback.failed(new WebSocketException(this.endpointInstance.getClass().getSimpleName() + " CLOSE method error: " + cause.getMessage(), cause));
        }
    }

    public String toString() {
        return String.format("%s@%x[%s]", this.getClass().getSimpleName(), this.hashCode(), this.endpointInstance.getClass().getName());
    }

    private void acceptMessage(Frame frame, Callback callback) {
        if (this.activeMessageSink == null) {
            callback.succeeded();
            this.demand();
            return;
        }
        this.activeMessageSink.accept(frame, callback);
        if (frame.isFin()) {
            this.activeMessageSink = null;
        }
    }

    private void onBinaryFrame(Frame frame, Callback callback) {
        if (this.activeMessageSink == null) {
            this.activeMessageSink = this.binarySink;
        }
        this.acceptMessage(frame, callback);
    }

    private void onContinuationFrame(Frame frame, Callback callback) {
        this.acceptMessage(frame, callback);
    }

    private void onPingFrame(Frame frame, final Callback callback) {
        if (this.pingHandle != null) {
            try {
                ByteBuffer payload = frame.getPayload();
                if (payload == null) {
                    payload = BufferUtil.EMPTY_BUFFER;
                }
                this.pingHandle.invoke(payload);
            }
            catch (Throwable cause) {
                throw new WebSocketException(this.endpointInstance.getClass().getSimpleName() + " PING method error: " + cause.getMessage(), cause);
            }
            callback.succeeded();
            this.demand();
        } else {
            this.getSession().getRemote().sendPong(frame.getPayload(), new WriteCallback(){

                @Override
                public void writeSuccess() {
                    callback.succeeded();
                    JettyWebSocketFrameHandler.this.demand();
                }

                @Override
                public void writeFailed(Throwable x) {
                    callback.succeeded();
                    JettyWebSocketFrameHandler.this.demand();
                }
            });
        }
    }

    private void onPongFrame(Frame frame, Callback callback) {
        if (this.pongHandle != null) {
            try {
                ByteBuffer payload = frame.getPayload();
                if (payload == null) {
                    payload = BufferUtil.EMPTY_BUFFER;
                }
                this.pongHandle.invoke(payload);
            }
            catch (Throwable cause) {
                throw new WebSocketException(this.endpointInstance.getClass().getSimpleName() + " PONG method error: " + cause.getMessage(), cause);
            }
        }
        callback.succeeded();
        this.demand();
    }

    private void onTextFrame(Frame frame, Callback callback) {
        if (this.activeMessageSink == null) {
            this.activeMessageSink = this.textSink;
        }
        this.acceptMessage(frame, callback);
    }

    @Override
    public boolean isDemanding() {
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void suspend() {
        try (AutoLock l = this.lock.lock();){
            switch (this.state) {
                case DEMANDING: {
                    this.state = SuspendState.SUSPENDING;
                    return;
                }
                default: {
                    throw new IllegalStateException(this.state.name());
                }
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    public void resume() {
        needDemand = false;
        frame = null;
        callback = null;
        l = this.lock.lock();
        try {
            switch (2.$SwitchMap$org$eclipse$jetty$websocket$common$JettyWebSocketFrameHandler$SuspendState[this.state.ordinal()]) {
                case 1: {
                    throw new IllegalStateException("Already Resumed");
                }
                case 3: {
                    needDemand = true;
                    frame = this.delayedFrame;
                    callback = this.delayedCallback;
                    this.delayedFrame = null;
                    this.delayedCallback = null;
                    this.state = SuspendState.DEMANDING;
                    ** break;
lbl17:
                    // 1 sources

                    break;
                }
                case 2: {
                    if (this.delayedFrame != null) {
                        throw new IllegalStateException();
                    }
                    this.state = SuspendState.DEMANDING;
                    ** break;
lbl23:
                    // 1 sources

                    break;
                }
                default: {
                    throw new IllegalStateException(this.state.name());
                }
            }
        }
        finally {
            if (l != null) {
                l.close();
            }
        }
        if (needDemand) {
            if (frame != null) {
                this.onFrame(frame, callback);
            } else {
                this.session.getCoreSession().demand(1L);
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    private void demand() {
        demand = false;
        l = this.lock.lock();
        try {
            switch (2.$SwitchMap$org$eclipse$jetty$websocket$common$JettyWebSocketFrameHandler$SuspendState[this.state.ordinal()]) {
                case 1: {
                    demand = true;
                    ** break;
lbl8:
                    // 1 sources

                    break;
                }
                case 2: {
                    this.state = SuspendState.SUSPENDED;
                    ** break;
lbl12:
                    // 1 sources

                    break;
                }
                default: {
                    throw new IllegalStateException(this.state.name());
                }
            }
        }
        finally {
            if (l != null) {
                l.close();
            }
        }
        if (demand) {
            this.session.getCoreSession().demand(1L);
        }
    }

    public static Throwable convertCause(Throwable cause) {
        if (cause instanceof org.eclipse.jetty.websocket.core.exception.MessageTooLargeException) {
            return new MessageTooLargeException(cause.getMessage(), cause);
        }
        if (cause instanceof org.eclipse.jetty.websocket.core.exception.ProtocolException) {
            return new ProtocolException(cause.getMessage(), cause);
        }
        if (cause instanceof BadPayloadException) {
            return new org.eclipse.jetty.websocket.api.exceptions.BadPayloadException(cause.getMessage(), cause);
        }
        if (cause instanceof CloseException) {
            return new org.eclipse.jetty.websocket.api.exceptions.CloseException(((CloseException)cause).getStatusCode(), cause.getMessage(), cause);
        }
        if (cause instanceof org.eclipse.jetty.websocket.core.exception.WebSocketTimeoutException) {
            return new WebSocketTimeoutException(cause.getMessage(), cause);
        }
        if (cause instanceof InvalidSignatureException) {
            return new InvalidWebSocketException(cause.getMessage(), cause);
        }
        if (cause instanceof org.eclipse.jetty.websocket.core.exception.UpgradeException) {
            org.eclipse.jetty.websocket.core.exception.UpgradeException ue = (org.eclipse.jetty.websocket.core.exception.UpgradeException)cause;
            return new UpgradeException(ue.getRequestURI(), ue.getResponseStatusCode(), cause);
        }
        return cause;
    }

    private static enum SuspendState {
        DEMANDING,
        SUSPENDING,
        SUSPENDED,
        CLOSED;

    }
}

