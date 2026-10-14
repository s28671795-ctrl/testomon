/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.WritePendingException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.Utf8Appendable;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.websocket.core.Behavior;
import org.eclipse.jetty.websocket.core.CloseStatus;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.ExtensionConfig;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.FrameHandler;
import org.eclipse.jetty.websocket.core.IncomingFrames;
import org.eclipse.jetty.websocket.core.OutgoingFrames;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.WebSocketConstants;
import org.eclipse.jetty.websocket.core.exception.CloseException;
import org.eclipse.jetty.websocket.core.exception.ProtocolException;
import org.eclipse.jetty.websocket.core.exception.WebSocketTimeoutException;
import org.eclipse.jetty.websocket.core.exception.WebSocketWriteTimeoutException;
import org.eclipse.jetty.websocket.core.internal.ExtensionStack;
import org.eclipse.jetty.websocket.core.internal.FragmentingFlusher;
import org.eclipse.jetty.websocket.core.internal.FrameFlusher;
import org.eclipse.jetty.websocket.core.internal.Negotiated;
import org.eclipse.jetty.websocket.core.internal.WebSocketConnection;
import org.eclipse.jetty.websocket.core.internal.WebSocketSessionState;
import org.eclipse.jetty.websocket.core.internal.util.FrameValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketCoreSession
implements IncomingFrames,
CoreSession,
Dumpable {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketCoreSession.class);
    private static final CloseStatus NO_CODE = new CloseStatus(1005);
    private final WebSocketComponents components;
    private final Behavior behavior;
    private final WebSocketSessionState sessionState = new WebSocketSessionState();
    private final FrameHandler handler;
    private final Negotiated negotiated;
    private final boolean demanding;
    private final Flusher flusher = new Flusher(this);
    private final ExtensionStack extensionStack;
    private int maxOutgoingFrames = -1;
    private final AtomicInteger numOutgoingFrames = new AtomicInteger();
    private WebSocketConnection connection;
    private boolean autoFragment = true;
    private long maxFrameSize = 65536L;
    private int inputBufferSize = 4096;
    private int outputBufferSize = 4096;
    private long maxBinaryMessageSize = 65536L;
    private long maxTextMessageSize = 65536L;
    private Duration idleTimeout = WebSocketConstants.DEFAULT_IDLE_TIMEOUT;
    private Duration writeTimeout = WebSocketConstants.DEFAULT_WRITE_TIMEOUT;
    private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public WebSocketCoreSession(FrameHandler handler, Behavior behavior, Negotiated negotiated, WebSocketComponents components) {
        this.components = components;
        this.handler = handler;
        this.behavior = behavior;
        this.negotiated = negotiated;
        this.demanding = handler.isDemanding();
        this.extensionStack = negotiated.getExtensions();
        this.extensionStack.initialize(new IncomingAdaptor(), new OutgoingAdaptor(), this);
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    protected void handle(Runnable runnable2) {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(this.classLoader);
            runnable2.run();
        }
        finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    public boolean isDemanding() {
        return this.demanding;
    }

    public ExtensionStack getExtensionStack() {
        return this.negotiated.getExtensions();
    }

    public FrameHandler getHandler() {
        return this.handler;
    }

    @Override
    public String getNegotiatedSubProtocol() {
        return this.negotiated.getSubProtocol();
    }

    @Override
    public Duration getIdleTimeout() {
        return this.idleTimeout;
    }

    @Override
    public void setIdleTimeout(Duration timeout2) {
        this.idleTimeout = timeout2;
        if (this.connection != null) {
            this.connection.getEndPoint().setIdleTimeout(timeout2.toMillis());
        }
    }

    @Override
    public Duration getWriteTimeout() {
        return this.writeTimeout;
    }

    @Override
    public void setWriteTimeout(Duration timeout2) {
        this.writeTimeout = timeout2;
        if (this.getConnection() != null) {
            this.getConnection().getFrameFlusher().setIdleTimeout(timeout2.toMillis());
        }
    }

    @Override
    public SocketAddress getLocalAddress() {
        return this.getConnection().getEndPoint().getLocalSocketAddress();
    }

    @Override
    public SocketAddress getRemoteAddress() {
        return this.getConnection().getEndPoint().getRemoteSocketAddress();
    }

    @Override
    public boolean isInputOpen() {
        return this.sessionState.isInputOpen();
    }

    @Override
    public boolean isOutputOpen() {
        return this.sessionState.isOutputOpen();
    }

    public boolean isClosed() {
        return this.sessionState.isClosed();
    }

    public void setWebSocketConnection(WebSocketConnection connection) {
        connection.getEndPoint().setIdleTimeout(this.idleTimeout.toMillis());
        connection.getFrameFlusher().setIdleTimeout(this.writeTimeout.toMillis());
        this.extensionStack.setLastDemand(connection::demand);
        this.connection = connection;
    }

    @Override
    public void close(Callback callback) {
        this.close(NO_CODE, callback);
    }

    @Override
    public void close(int statusCode, String reason, Callback callback) {
        this.close(new CloseStatus(statusCode, reason), callback);
    }

    private void close(CloseStatus closeStatus, Callback callback) {
        this.sendFrame(closeStatus.toFrame(), callback, false);
    }

    @Override
    public ByteBufferPool getByteBufferPool() {
        return this.connection.getBufferPool();
    }

    public void onEof() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onEof() {}", (Object)this);
        }
        if (this.sessionState.onEof()) {
            this.closeConnection(this.sessionState.getCloseStatus(), Callback.NOOP);
        }
    }

    private void closeConnection(CloseStatus closeStatus, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("closeConnection() {} {}", (Object)closeStatus, (Object)this);
        }
        this.abort();
        this.extensionStack.close();
        if (closeStatus.isAbnormal() && closeStatus.getCause() != null) {
            Callback errorCallback = Callback.from(() -> {
                try {
                    this.handle(() -> this.handler.onClosed(closeStatus, callback));
                }
                catch (Throwable e) {
                    LOG.warn("Failure from onClosed on handler {}", (Object)this.handler, (Object)e);
                    callback.failed(e);
                }
            });
            Throwable cause = closeStatus.getCause();
            try {
                this.handle(() -> this.handler.onError(cause, errorCallback));
            }
            catch (Throwable e) {
                if (e != cause) {
                    cause.addSuppressed(e);
                }
                LOG.warn("Failure from onError on handler {}", (Object)this.handler, (Object)cause);
                errorCallback.failed(cause);
            }
        } else {
            try {
                this.handle(() -> this.handler.onClosed(closeStatus, callback));
            }
            catch (Throwable e) {
                LOG.warn("Failure from onClosed on handler {}", (Object)this.handler, (Object)e);
                callback.failed(e);
            }
        }
    }

    public void processConnectionError(Throwable cause, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("processConnectionError {}", (Object)this, (Object)cause);
        }
        int code = cause instanceof CloseException ? ((CloseException)cause).getStatusCode() : (cause instanceof Utf8Appendable.NotUtf8Exception ? 1007 : (cause instanceof WebSocketWriteTimeoutException ? 1006 : (cause instanceof WebSocketTimeoutException || cause instanceof TimeoutException || cause instanceof SocketTimeoutException ? 1001 : 1006)));
        CloseStatus closeStatus = new CloseStatus(code, cause);
        if (CloseStatus.isTransmittableStatusCode(code)) {
            this.close(closeStatus, callback);
        } else if (this.sessionState.onClosed(closeStatus)) {
            this.closeConnection(closeStatus, callback);
        }
    }

    public void processHandlerError(Throwable cause, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("processHandlerError {}", (Object)this, (Object)cause);
        }
        int code = cause instanceof CloseException ? ((CloseException)cause).getStatusCode() : (cause instanceof ClosedChannelException ? 1006 : (cause instanceof Utf8Appendable.NotUtf8Exception ? 1007 : (cause instanceof WebSocketTimeoutException || cause instanceof TimeoutException || cause instanceof SocketTimeoutException ? 1001 : (this.behavior == Behavior.CLIENT ? 1008 : 1011))));
        CloseStatus closeStatus = new CloseStatus(code, cause);
        if (CloseStatus.isTransmittableStatusCode(code)) {
            this.close(closeStatus, callback);
        } else if (this.sessionState.onClosed(closeStatus)) {
            this.closeConnection(closeStatus, callback);
        }
    }

    public void onOpen() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onOpen() {}", (Object)this);
        }
        this.sessionState.onConnected();
        if (LOG.isDebugEnabled()) {
            LOG.debug("ConnectionState: Transition to CONNECTED");
        }
        Callback openCallback = Callback.from(() -> {
            this.sessionState.onOpen();
            if (LOG.isDebugEnabled()) {
                LOG.debug("ConnectionState: Transition to OPEN");
            }
            if (!this.demanding) {
                this.autoDemand();
            }
        }, x -> {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error during OPEN", (Throwable)x);
            }
            this.processHandlerError(new CloseException(1011, (Throwable)x), Callback.NOOP);
        });
        try {
            this.handle(() -> this.handler.onOpen(this, openCallback));
        }
        catch (Throwable t2) {
            openCallback.failed(t2);
            throw new RuntimeException(t2);
        }
    }

    @Override
    public void demand(long n) {
        if (!this.demanding) {
            throw new IllegalStateException("FrameHandler is not demanding: " + this);
        }
        this.getExtensionStack().demand(n);
    }

    public void autoDemand() {
        this.getExtensionStack().demand(1L);
    }

    @Override
    public boolean isRsv1Used() {
        return this.getExtensionStack().isRsv1Used();
    }

    @Override
    public boolean isRsv2Used() {
        return this.getExtensionStack().isRsv2Used();
    }

    @Override
    public boolean isRsv3Used() {
        return this.getExtensionStack().isRsv3Used();
    }

    public WebSocketConnection getConnection() {
        return this.connection;
    }

    public Executor getExecutor() {
        return this.connection.getExecutor();
    }

    @Override
    public void onFrame(Frame frame, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onFrame({})", (Object)frame);
        }
        try {
            FrameValidation.assertValidIncoming(frame, this);
        }
        catch (Throwable t2) {
            callback.failed(t2);
            return;
        }
        this.negotiated.getExtensions().onFrame(frame, callback);
    }

    @Override
    public void sendFrame(Frame frame, Callback callback, boolean batch) {
        if (this.maxOutgoingFrames > 0 && frame.isDataFrame()) {
            callback = Callback.from(callback, this.numOutgoingFrames::decrementAndGet);
            if (this.numOutgoingFrames.incrementAndGet() > this.maxOutgoingFrames) {
                callback.failed(new WritePendingException());
                return;
            }
        }
        try {
            FrameValidation.assertValidOutgoing(frame, this);
        }
        catch (Throwable t3) {
            LOG.warn("Invalid outgoing frame: {}", (Object)frame, (Object)t3);
            callback.failed(t3);
            return;
        }
        try {
            boolean closeConnection;
            if (LOG.isDebugEnabled()) {
                LOG.debug("sendFrame({}, {}, {})", frame, callback, batch);
            }
            if (closeConnection = this.sessionState.onOutgoingFrame(frame)) {
                Callback c = callback;
                Callback closeConnectionCallback = Callback.from(() -> this.closeConnection(this.sessionState.getCloseStatus(), c), t2 -> this.closeConnection(this.sessionState.getCloseStatus(), Callback.from(c, t2)));
                this.flusher.sendFrame(frame, closeConnectionCallback, false);
            } else {
                this.flusher.sendFrame(frame, callback, batch);
            }
        }
        catch (Throwable t4) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failed sendFrame() {}", (Object)t4.toString());
            }
            if (frame.getOpCode() == 8) {
                CloseStatus closeStatus = CloseStatus.getCloseStatus(frame);
                if (closeStatus.isAbnormal() && this.sessionState.onClosed(closeStatus)) {
                    this.closeConnection(closeStatus, Callback.from(callback, t4));
                } else {
                    callback.failed(t4);
                }
            }
            callback.failed(t4);
        }
    }

    @Override
    public void flush(Callback callback) {
        this.flusher.sendFrame(FrameFlusher.FLUSH_FRAME, callback, false);
    }

    @Override
    public void abort() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("abort(): {}", (Object)this);
        }
        this.connection.cancelDemand();
        this.connection.getEndPoint().close();
    }

    @Override
    public boolean isAutoFragment() {
        return this.autoFragment;
    }

    @Override
    public void setAutoFragment(boolean autoFragment) {
        this.autoFragment = autoFragment;
    }

    @Override
    public long getMaxFrameSize() {
        return this.maxFrameSize;
    }

    @Override
    public void setMaxFrameSize(long maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    public int getOutputBufferSize() {
        return this.outputBufferSize;
    }

    @Override
    public void setOutputBufferSize(int outputBufferSize) {
        this.outputBufferSize = outputBufferSize;
    }

    @Override
    public int getInputBufferSize() {
        return this.inputBufferSize;
    }

    @Override
    public void setInputBufferSize(int inputBufferSize) {
        this.inputBufferSize = inputBufferSize;
        if (this.connection != null) {
            this.connection.setInputBufferSize(inputBufferSize);
        }
    }

    @Override
    public long getMaxBinaryMessageSize() {
        return this.maxBinaryMessageSize;
    }

    @Override
    public void setMaxBinaryMessageSize(long maxSize) {
        this.maxBinaryMessageSize = maxSize;
    }

    @Override
    public long getMaxTextMessageSize() {
        return this.maxTextMessageSize;
    }

    @Override
    public void setMaxTextMessageSize(long maxSize) {
        this.maxTextMessageSize = maxSize;
    }

    @Override
    public int getMaxOutgoingFrames() {
        return this.maxOutgoingFrames;
    }

    @Override
    public void setMaxOutgoingFrames(int maxOutgoingFrames) {
        this.maxOutgoingFrames = maxOutgoingFrames;
    }

    @Override
    public String dump() {
        return Dumpable.dump(this);
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        Dumpable.dumpObjects(out, indent, this, "subprotocol=" + this.negotiated.getSubProtocol(), this.negotiated.getExtensions(), this.handler);
    }

    @Override
    public List<ExtensionConfig> getNegotiatedExtensions() {
        return this.negotiated.getExtensions().getNegotiatedExtensions();
    }

    @Override
    public Map<String, List<String>> getParameterMap() {
        return this.negotiated.getParameterMap();
    }

    @Override
    public String getProtocolVersion() {
        return this.negotiated.getProtocolVersion();
    }

    @Override
    public URI getRequestURI() {
        return this.negotiated.getRequestURI();
    }

    @Override
    public boolean isSecure() {
        return this.negotiated.isSecure();
    }

    @Override
    public Behavior getBehavior() {
        return this.behavior;
    }

    @Override
    public WebSocketComponents getWebSocketComponents() {
        return this.components;
    }

    public String toString() {
        return String.format("WSCoreSession@%x{%s,%s,%s,af=%b,i/o=%d/%d,fs=%d}->%s", new Object[]{this.hashCode(), this.behavior, this.sessionState, this.negotiated, this.autoFragment, this.inputBufferSize, this.outputBufferSize, this.maxFrameSize, this.handler});
    }

    private class Flusher
    extends FragmentingFlusher {
        public Flusher(Configuration configuration) {
            super(configuration);
        }

        @Override
        void forwardFrame(Frame frame, Callback callback, boolean batch) {
            WebSocketCoreSession.this.negotiated.getExtensions().sendFrame(frame, callback, batch);
        }
    }

    private class IncomingAdaptor
    implements IncomingFrames {
        private IncomingAdaptor() {
        }

        @Override
        public void onFrame(Frame frame, Callback callback) {
            Callback closeCallback = null;
            try {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("receiveFrame({}, {}) - connectionState={}, handler={}", frame, callback, WebSocketCoreSession.this.sessionState, WebSocketCoreSession.this.handler);
                }
                boolean closeConnection = WebSocketCoreSession.this.sessionState.onIncomingFrame(frame);
                if (frame.getOpCode() != 8) {
                    Callback handlerCallback = WebSocketCoreSession.this.isDemanding() ? callback : Callback.from(() -> {
                        callback.succeeded();
                        WebSocketCoreSession.this.autoDemand();
                    }, callback::failed);
                    WebSocketCoreSession.this.handle(() -> WebSocketCoreSession.this.handler.onFrame(frame, handlerCallback));
                    return;
                }
                WebSocketCoreSession.this.connection.cancelDemand();
                closeCallback = closeConnection ? Callback.from(() -> WebSocketCoreSession.this.closeConnection(WebSocketCoreSession.this.sessionState.getCloseStatus(), callback), t2 -> {
                    WebSocketCoreSession.this.sessionState.onError((Throwable)t2);
                    WebSocketCoreSession.this.closeConnection(WebSocketCoreSession.this.sessionState.getCloseStatus(), callback);
                }) : Callback.from(() -> {
                    if (WebSocketCoreSession.this.sessionState.isOutputOpen()) {
                        CloseStatus closeStatus = CloseStatus.getCloseStatus(frame);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("ConnectionState: sending close response {}", (Object)closeStatus);
                        }
                        WebSocketCoreSession.this.close(closeStatus == null ? CloseStatus.NO_CODE_STATUS : closeStatus, callback);
                    } else {
                        callback.succeeded();
                    }
                }, x -> WebSocketCoreSession.this.processHandlerError((Throwable)x, callback));
                WebSocketCoreSession.this.handler.onFrame(frame, closeCallback);
            }
            catch (Throwable t3) {
                if (closeCallback != null) {
                    closeCallback.failed(t3);
                }
                callback.failed(t3);
            }
        }
    }

    private class OutgoingAdaptor
    implements OutgoingFrames {
        private OutgoingAdaptor() {
        }

        @Override
        public void sendFrame(Frame frame, Callback callback, boolean batch) {
            try {
                WebSocketCoreSession.this.connection.enqueueFrame(frame, callback, batch);
            }
            catch (ProtocolException e) {
                callback.failed(e);
            }
        }
    }
}

