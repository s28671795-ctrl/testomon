/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.jetty.http2.BufferingFlowControlStrategy;
import org.eclipse.jetty.http2.FlowControlStrategy;
import org.eclipse.jetty.http2.HTTP2Connection;
import org.eclipse.jetty.http2.ISession;
import org.eclipse.jetty.http2.api.Session;
import org.eclipse.jetty.http2.api.server.ServerSessionListener;
import org.eclipse.jetty.http2.generator.Generator;
import org.eclipse.jetty.http2.parser.RateControl;
import org.eclipse.jetty.http2.parser.ServerParser;
import org.eclipse.jetty.http2.parser.WindowRateControl;
import org.eclipse.jetty.http2.server.HTTP2ServerConnection;
import org.eclipse.jetty.http2.server.HTTP2ServerSession;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.RetainableByteBufferPool;
import org.eclipse.jetty.server.AbstractConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.annotation.Name;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.component.Graceful;
import org.eclipse.jetty.util.component.LifeCycle;

@ManagedObject
public abstract class AbstractHTTP2ServerConnectionFactory
extends AbstractConnectionFactory {
    private final HTTP2SessionContainer sessionContainer = new HTTP2SessionContainer();
    private final HttpConfiguration httpConfiguration;
    private int maxDynamicTableSize = 4096;
    private int initialSessionRecvWindow = 0x100000;
    private int initialStreamRecvWindow = 524288;
    private int maxConcurrentStreams = 128;
    private int maxHeaderBlockFragment = 0;
    private int maxFrameLength = 16384;
    private int maxSettingsKeys = 64;
    private boolean connectProtocolEnabled = true;
    private RateControl.Factory rateControlFactory = new WindowRateControl.Factory(50);
    private FlowControlStrategy.Factory flowControlStrategyFactory = () -> new BufferingFlowControlStrategy(0.5f);
    private long streamIdleTimeout;
    private boolean useInputDirectByteBuffers;
    private boolean useOutputDirectByteBuffers;

    public AbstractHTTP2ServerConnectionFactory(@Name(value="config") HttpConfiguration httpConfiguration) {
        this(httpConfiguration, "h2");
    }

    protected AbstractHTTP2ServerConnectionFactory(@Name(value="config") HttpConfiguration httpConfiguration, String ... protocols) {
        super(protocols);
        for (String p : protocols) {
            if (HTTP2ServerConnection.isSupportedProtocol(p)) continue;
            throw new IllegalArgumentException("Unsupported HTTP2 Protocol variant: " + p);
        }
        this.addBean(this.sessionContainer);
        this.httpConfiguration = Objects.requireNonNull(httpConfiguration);
        this.addBean(httpConfiguration);
        this.setInputBufferSize(16393);
        this.setUseInputDirectByteBuffers(httpConfiguration.isUseInputDirectByteBuffers());
        this.setUseOutputDirectByteBuffers(httpConfiguration.isUseOutputDirectByteBuffers());
    }

    @ManagedAttribute(value="The HPACK dynamic table maximum size")
    public int getMaxDynamicTableSize() {
        return this.maxDynamicTableSize;
    }

    public void setMaxDynamicTableSize(int maxDynamicTableSize) {
        this.maxDynamicTableSize = maxDynamicTableSize;
    }

    @ManagedAttribute(value="The initial size of session's flow control receive window")
    public int getInitialSessionRecvWindow() {
        return this.initialSessionRecvWindow;
    }

    public void setInitialSessionRecvWindow(int initialSessionRecvWindow) {
        this.initialSessionRecvWindow = initialSessionRecvWindow;
    }

    @ManagedAttribute(value="The initial size of stream's flow control receive window")
    public int getInitialStreamRecvWindow() {
        return this.initialStreamRecvWindow;
    }

    public void setInitialStreamRecvWindow(int initialStreamRecvWindow) {
        this.initialStreamRecvWindow = initialStreamRecvWindow;
    }

    @ManagedAttribute(value="The max number of concurrent streams per session")
    public int getMaxConcurrentStreams() {
        return this.maxConcurrentStreams;
    }

    public void setMaxConcurrentStreams(int maxConcurrentStreams) {
        this.maxConcurrentStreams = maxConcurrentStreams;
    }

    @ManagedAttribute(value="The max header block fragment")
    public int getMaxHeaderBlockFragment() {
        return this.maxHeaderBlockFragment;
    }

    public void setMaxHeaderBlockFragment(int maxHeaderBlockFragment) {
        this.maxHeaderBlockFragment = maxHeaderBlockFragment;
    }

    public FlowControlStrategy.Factory getFlowControlStrategyFactory() {
        return this.flowControlStrategyFactory;
    }

    public void setFlowControlStrategyFactory(FlowControlStrategy.Factory flowControlStrategyFactory) {
        this.flowControlStrategyFactory = flowControlStrategyFactory;
    }

    @ManagedAttribute(value="The stream idle timeout in milliseconds")
    public long getStreamIdleTimeout() {
        return this.streamIdleTimeout;
    }

    public void setStreamIdleTimeout(long streamIdleTimeout) {
        this.streamIdleTimeout = streamIdleTimeout;
    }

    @ManagedAttribute(value="The max frame length in bytes")
    public int getMaxFrameLength() {
        return this.maxFrameLength;
    }

    public void setMaxFrameLength(int maxFrameLength) {
        this.maxFrameLength = maxFrameLength;
    }

    @ManagedAttribute(value="The max number of keys in all SETTINGS frames")
    public int getMaxSettingsKeys() {
        return this.maxSettingsKeys;
    }

    public void setMaxSettingsKeys(int maxSettingsKeys) {
        this.maxSettingsKeys = maxSettingsKeys;
    }

    @ManagedAttribute(value="Whether CONNECT requests supports a protocol")
    public boolean isConnectProtocolEnabled() {
        return this.connectProtocolEnabled;
    }

    public void setConnectProtocolEnabled(boolean connectProtocolEnabled) {
        this.connectProtocolEnabled = connectProtocolEnabled;
    }

    public RateControl.Factory getRateControlFactory() {
        return this.rateControlFactory;
    }

    public void setRateControlFactory(RateControl.Factory rateControlFactory) {
        this.rateControlFactory = Objects.requireNonNull(rateControlFactory);
    }

    @ManagedAttribute(value="Whether to use direct ByteBuffers for reading")
    public boolean isUseInputDirectByteBuffers() {
        return this.useInputDirectByteBuffers;
    }

    public void setUseInputDirectByteBuffers(boolean useInputDirectByteBuffers) {
        this.useInputDirectByteBuffers = useInputDirectByteBuffers;
    }

    @ManagedAttribute(value="Whether to use direct ByteBuffers for writing")
    public boolean isUseOutputDirectByteBuffers() {
        return this.useOutputDirectByteBuffers;
    }

    public void setUseOutputDirectByteBuffers(boolean useOutputDirectByteBuffers) {
        this.useOutputDirectByteBuffers = useOutputDirectByteBuffers;
    }

    public HttpConfiguration getHttpConfiguration() {
        return this.httpConfiguration;
    }

    protected Map<Integer, Integer> newSettings() {
        HashMap<Integer, Integer> settings = new HashMap<Integer, Integer>();
        settings.put(1, this.getMaxDynamicTableSize());
        settings.put(4, this.getInitialStreamRecvWindow());
        int maxConcurrentStreams = this.getMaxConcurrentStreams();
        if (maxConcurrentStreams >= 0) {
            settings.put(3, maxConcurrentStreams);
        }
        settings.put(6, this.getHttpConfiguration().getRequestHeaderSize());
        settings.put(8, this.isConnectProtocolEnabled() ? 1 : 0);
        return settings;
    }

    @Override
    public Connection newConnection(Connector connector, EndPoint endPoint) {
        ServerSessionListener listener = this.newSessionListener(connector, endPoint);
        Generator generator = new Generator(connector.getByteBufferPool(), this.isUseOutputDirectByteBuffers(), this.getMaxDynamicTableSize(), this.getMaxHeaderBlockFragment());
        FlowControlStrategy flowControl = this.getFlowControlStrategyFactory().newFlowControlStrategy();
        HTTP2ServerSession session = new HTTP2ServerSession(connector.getScheduler(), endPoint, generator, listener, flowControl);
        session.setMaxLocalStreams(this.getMaxConcurrentStreams());
        session.setMaxRemoteStreams(this.getMaxConcurrentStreams());
        long streamIdleTimeout = this.getStreamIdleTimeout();
        if (streamIdleTimeout > 0L) {
            session.setStreamIdleTimeout(streamIdleTimeout);
        }
        session.setInitialSessionRecvWindow(this.getInitialSessionRecvWindow());
        session.setWriteThreshold(this.getHttpConfiguration().getOutputBufferSize());
        session.setConnectProtocolEnabled(this.isConnectProtocolEnabled());
        ServerParser parser = this.newServerParser(connector, session, this.getRateControlFactory().newRateControl(endPoint));
        parser.setMaxFrameLength(this.getMaxFrameLength());
        parser.setMaxSettingsKeys(this.getMaxSettingsKeys());
        RetainableByteBufferPool retainableByteBufferPool = connector.getByteBufferPool().asRetainableByteBufferPool();
        HTTP2ServerConnection connection = new HTTP2ServerConnection(retainableByteBufferPool, connector.getExecutor(), endPoint, this.httpConfiguration, parser, session, this.getInputBufferSize(), listener);
        connection.setUseInputDirectByteBuffers(this.isUseInputDirectByteBuffers());
        connection.setUseOutputDirectByteBuffers(this.isUseOutputDirectByteBuffers());
        connection.addEventListener(this.sessionContainer);
        return this.configure(connection, connector, endPoint);
    }

    protected abstract ServerSessionListener newSessionListener(Connector var1, EndPoint var2);

    protected ServerParser newServerParser(Connector connector, ServerParser.Listener listener, RateControl rateControl) {
        return new ServerParser(connector.getByteBufferPool(), listener, this.getMaxDynamicTableSize(), this.getHttpConfiguration().getRequestHeaderSize(), rateControl);
    }

    @ManagedObject(value="The container of HTTP/2 sessions")
    public static class HTTP2SessionContainer
    implements Connection.Listener,
    Graceful,
    Dumpable {
        private final Set<ISession> sessions = ConcurrentHashMap.newKeySet();
        private final AtomicReference<CompletableFuture<Void>> shutdown = new AtomicReference();

        @Override
        public void onOpened(Connection connection) {
            ISession session = ((HTTP2Connection)connection).getSession();
            this.sessions.add(session);
            LifeCycle.start(session);
            if (this.isShutdown()) {
                this.shutdown(session);
            }
        }

        @Override
        public void onClosed(Connection connection) {
            ISession session = ((HTTP2Connection)connection).getSession();
            if (this.sessions.remove(session)) {
                LifeCycle.stop(session);
            }
        }

        public Set<Session> getSessions() {
            return new HashSet<Session>(this.sessions);
        }

        @ManagedAttribute(value="The number of HTTP/2 sessions", readonly=true)
        public int getSize() {
            return this.sessions.size();
        }

        @Override
        public CompletableFuture<Void> shutdown() {
            CompletableFuture<Void> result = new CompletableFuture<Void>();
            if (this.shutdown.compareAndSet(null, result)) {
                CompletableFuture.allOf((CompletableFuture[])this.sessions.stream().map(this::shutdown).toArray(CompletableFuture[]::new)).whenComplete((v, x) -> {
                    if (x == null) {
                        result.complete((Void)v);
                    } else {
                        result.completeExceptionally((Throwable)x);
                    }
                });
                return result;
            }
            return this.shutdown.get();
        }

        @Override
        public boolean isShutdown() {
            return this.shutdown.get() != null;
        }

        private CompletableFuture<Void> shutdown(ISession session) {
            return session.shutdown();
        }

        @Override
        public String dump() {
            return Dumpable.dump(this);
        }

        @Override
        public void dump(Appendable out, String indent) throws IOException {
            Dumpable.dumpObjects(out, indent, this, this.sessions);
        }

        public String toString() {
            return String.format("%s@%x[size=%d]", this.getClass().getSimpleName(), this.hashCode(), this.getSize());
        }
    }
}

