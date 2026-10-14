/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Ticker;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.ChannelLogger;
import io.grpc.ClientStreamTracer;
import io.grpc.InternalChannelz;
import io.grpc.InternalLogId;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientTransport;
import io.grpc.internal.ConnectionClientTransport;
import io.grpc.internal.FailingClientStream;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.Http2Ping;
import io.grpc.internal.KeepAliveManager;
import io.grpc.internal.ManagedClientTransport;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.TransportTracer;
import io.grpc.netty.shaded.io.grpc.netty.ClientTransportLifecycleManager;
import io.grpc.netty.shaded.io.grpc.netty.ForcefulCloseCommand;
import io.grpc.netty.shaded.io.grpc.netty.GracefulCloseCommand;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyClientHandler;
import io.grpc.netty.shaded.io.grpc.netty.NettyClientStream;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiator;
import io.grpc.netty.shaded.io.grpc.netty.SendPingCommand;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.grpc.netty.WriteBufferingAndExceptionHandler;
import io.grpc.netty.shaded.io.netty.bootstrap.Bootstrap;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFactory;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelFutureListener;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelOption;
import io.grpc.netty.shaded.io.netty.channel.EventLoop;
import io.grpc.netty.shaded.io.netty.channel.EventLoopGroup;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.StreamBufferingEncoder;
import io.grpc.netty.shaded.io.netty.util.AsciiString;
import io.grpc.netty.shaded.io.netty.util.concurrent.Future;
import io.grpc.netty.shaded.io.netty.util.concurrent.GenericFutureListener;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

class NettyClientTransport
implements ConnectionClientTransport {
    private final InternalLogId logId;
    private final Map<ChannelOption<?>, ?> channelOptions;
    private final SocketAddress remoteAddress;
    private final ChannelFactory<? extends Channel> channelFactory;
    private final EventLoopGroup group;
    private final ProtocolNegotiator negotiator;
    private final String authorityString;
    private final AsciiString authority;
    private final AsciiString userAgent;
    private final boolean autoFlowControl;
    private final int flowControlWindow;
    private final int maxMessageSize;
    private final int maxHeaderListSize;
    private KeepAliveManager keepAliveManager;
    private final long keepAliveTimeNanos;
    private final long keepAliveTimeoutNanos;
    private final boolean keepAliveWithoutCalls;
    private final AsciiString negotiationScheme;
    private final Runnable tooManyPingsRunnable;
    private NettyClientHandler handler;
    private Channel channel;
    private Status statusExplainingWhyTheChannelIsNull;
    private ClientTransportLifecycleManager lifecycleManager;
    private final TransportTracer transportTracer;
    private final Attributes eagAttributes;
    private final NettyChannelBuilder.LocalSocketPicker localSocketPicker;
    private final ChannelLogger channelLogger;
    private final boolean useGetForSafeMethods;
    private final Ticker ticker;

    NettyClientTransport(SocketAddress address, ChannelFactory<? extends Channel> channelFactory, Map<ChannelOption<?>, ?> channelOptions, EventLoopGroup group, ProtocolNegotiator negotiator, boolean autoFlowControl, int flowControlWindow, int maxMessageSize, int maxHeaderListSize, long keepAliveTimeNanos, long keepAliveTimeoutNanos, boolean keepAliveWithoutCalls, String authority, @Nullable String userAgent, Runnable tooManyPingsRunnable, TransportTracer transportTracer, Attributes eagAttributes, NettyChannelBuilder.LocalSocketPicker localSocketPicker, ChannelLogger channelLogger, boolean useGetForSafeMethods, Ticker ticker) {
        this.negotiator = Preconditions.checkNotNull(negotiator, "negotiator");
        this.negotiationScheme = this.negotiator.scheme();
        this.remoteAddress = Preconditions.checkNotNull(address, "address");
        this.group = Preconditions.checkNotNull(group, "group");
        this.channelFactory = channelFactory;
        this.channelOptions = Preconditions.checkNotNull(channelOptions, "channelOptions");
        this.autoFlowControl = autoFlowControl;
        this.flowControlWindow = flowControlWindow;
        this.maxMessageSize = maxMessageSize;
        this.maxHeaderListSize = maxHeaderListSize;
        this.keepAliveTimeNanos = keepAliveTimeNanos;
        this.keepAliveTimeoutNanos = keepAliveTimeoutNanos;
        this.keepAliveWithoutCalls = keepAliveWithoutCalls;
        this.authorityString = authority;
        this.authority = new AsciiString(authority);
        this.userAgent = new AsciiString(GrpcUtil.getGrpcUserAgent("netty", userAgent));
        this.tooManyPingsRunnable = Preconditions.checkNotNull(tooManyPingsRunnable, "tooManyPingsRunnable");
        this.transportTracer = Preconditions.checkNotNull(transportTracer, "transportTracer");
        this.eagAttributes = Preconditions.checkNotNull(eagAttributes, "eagAttributes");
        this.localSocketPicker = Preconditions.checkNotNull(localSocketPicker, "localSocketPicker");
        this.logId = InternalLogId.allocate(this.getClass(), this.remoteAddress.toString());
        this.channelLogger = Preconditions.checkNotNull(channelLogger, "channelLogger");
        this.useGetForSafeMethods = useGetForSafeMethods;
        this.ticker = Preconditions.checkNotNull(ticker, "ticker");
    }

    @Override
    public void ping(final ClientTransport.PingCallback callback, final Executor executor2) {
        if (this.channel == null) {
            executor2.execute(new Runnable(){

                @Override
                public void run() {
                    callback.onFailure(NettyClientTransport.this.statusExplainingWhyTheChannelIsNull.asException());
                }
            });
            return;
        }
        ChannelFutureListener failureListener = new ChannelFutureListener(){

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    Status s2 = NettyClientTransport.this.statusFromFailedFuture(future);
                    Http2Ping.notifyFailed(callback, executor2, s2.asException());
                }
            }
        };
        this.handler.getWriteQueue().enqueue(new SendPingCommand(callback, executor2), true).addListener(failureListener);
    }

    @Override
    public ClientStream newStream(MethodDescriptor<?, ?> method2, Metadata headers, CallOptions callOptions, ClientStreamTracer[] tracers) {
        Preconditions.checkNotNull(method2, "method");
        Preconditions.checkNotNull(headers, "headers");
        if (this.channel == null) {
            return new FailingClientStream(this.statusExplainingWhyTheChannelIsNull, tracers);
        }
        StatsTraceContext statsTraceCtx = StatsTraceContext.newClientContext(tracers, this.getAttributes(), headers);
        return new NettyClientStream(new NettyClientStream.TransportState(this.handler, this.channel.eventLoop(), this.maxMessageSize, statsTraceCtx, this.transportTracer, method2.getFullMethodName(), callOptions){

            @Override
            protected Status statusFromFailedFuture(ChannelFuture f) {
                return NettyClientTransport.this.statusFromFailedFuture(f);
            }
        }, method2, headers, this.channel, this.authority, this.negotiationScheme, this.userAgent, statsTraceCtx, this.transportTracer, callOptions, this.useGetForSafeMethods);
    }

    @Override
    public Runnable start(ManagedClientTransport.Listener transportListener) {
        Object tcpUserTimeout;
        this.lifecycleManager = new ClientTransportLifecycleManager(Preconditions.checkNotNull(transportListener, "listener"));
        EventLoop eventLoop = this.group.next();
        if (this.keepAliveTimeNanos != Long.MAX_VALUE) {
            this.keepAliveManager = new KeepAliveManager(new KeepAliveManager.ClientKeepAlivePinger(this), eventLoop, this.keepAliveTimeNanos, this.keepAliveTimeoutNanos, this.keepAliveWithoutCalls);
        }
        this.handler = NettyClientHandler.newHandler(this.lifecycleManager, this.keepAliveManager, this.autoFlowControl, this.flowControlWindow, this.maxHeaderListSize, GrpcUtil.STOPWATCH_SUPPLIER, this.tooManyPingsRunnable, this.transportTracer, this.eagAttributes, this.authorityString, this.channelLogger, this.ticker);
        ChannelHandler negotiationHandler = this.negotiator.newHandler(this.handler);
        Bootstrap b = new Bootstrap();
        b.option(ChannelOption.ALLOCATOR, Utils.getByteBufAllocator(false));
        b.group(eventLoop);
        b.channelFactory(this.channelFactory);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        if (this.keepAliveTimeNanos != Long.MAX_VALUE && (tcpUserTimeout = Utils.maybeGetTcpUserTimeoutOption()) != null) {
            b.option(tcpUserTimeout, (int)TimeUnit.NANOSECONDS.toMillis(this.keepAliveTimeoutNanos));
        }
        for (Map.Entry entry : this.channelOptions.entrySet()) {
            b.option((ChannelOption)entry.getKey(), entry.getValue());
        }
        WriteBufferingAndExceptionHandler bufferingHandler = new WriteBufferingAndExceptionHandler(negotiationHandler);
        b.handler(bufferingHandler);
        ChannelFuture channelFuture = b.register();
        if (channelFuture.isDone() && !channelFuture.isSuccess()) {
            this.channel = null;
            Throwable t2 = channelFuture.cause();
            if (t2 == null) {
                t2 = new IllegalStateException("Channel is null, but future doesn't have a cause");
            }
            this.statusExplainingWhyTheChannelIsNull = Utils.statusFromThrowable(t2);
            return new Runnable(){

                @Override
                public void run() {
                    NettyClientTransport.this.lifecycleManager.notifyTerminated(NettyClientTransport.this.statusExplainingWhyTheChannelIsNull);
                }
            };
        }
        this.channel = channelFuture.channel();
        this.handler.startWriteQueue(this.channel);
        this.channel.writeAndFlush(NettyClientHandler.NOOP_MESSAGE).addListener(new ChannelFutureListener(){

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    NettyClientTransport.this.lifecycleManager.notifyTerminated(Utils.statusFromThrowable(future.cause()));
                }
            }
        });
        SocketAddress localAddress = this.localSocketPicker.createSocketAddress(this.remoteAddress, this.eagAttributes);
        if (localAddress != null) {
            this.channel.connect(this.remoteAddress, localAddress);
        } else {
            this.channel.connect(this.remoteAddress);
        }
        if (this.keepAliveManager != null) {
            this.keepAliveManager.onTransportStarted();
        }
        return null;
    }

    @Override
    public void shutdown(Status reason) {
        if (this.channel == null) {
            return;
        }
        if (this.channel.isOpen()) {
            this.handler.getWriteQueue().enqueue(new GracefulCloseCommand(reason), true);
        }
    }

    @Override
    public void shutdownNow(final Status reason) {
        if (this.channel != null && this.channel.isOpen()) {
            this.handler.getWriteQueue().enqueue(new Runnable(){

                @Override
                public void run() {
                    NettyClientTransport.this.lifecycleManager.notifyShutdown(reason);
                    NettyClientTransport.this.channel.write(new ForcefulCloseCommand(reason));
                }
            }, true);
        }
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("logId", this.logId.getId()).add("remoteAddress", this.remoteAddress).add("channel", this.channel).toString();
    }

    @Override
    public InternalLogId getLogId() {
        return this.logId;
    }

    @Override
    public Attributes getAttributes() {
        return this.handler.getAttributes();
    }

    @Override
    public ListenableFuture<InternalChannelz.SocketStats> getStats() {
        final SettableFuture<InternalChannelz.SocketStats> result = SettableFuture.create();
        if (this.channel.eventLoop().inEventLoop()) {
            result.set(this.getStatsHelper(this.channel));
            return result;
        }
        this.channel.eventLoop().submit(new Runnable(){

            @Override
            public void run() {
                result.set(NettyClientTransport.this.getStatsHelper(NettyClientTransport.this.channel));
            }
        }).addListener(new GenericFutureListener<Future<Object>>(){

            @Override
            public void operationComplete(Future<Object> future) throws Exception {
                if (!future.isSuccess()) {
                    result.setException(future.cause());
                }
            }
        });
        return result;
    }

    private InternalChannelz.SocketStats getStatsHelper(Channel ch) {
        assert (ch.eventLoop().inEventLoop());
        return new InternalChannelz.SocketStats(this.transportTracer.getStats(), this.channel.localAddress(), this.channel.remoteAddress(), Utils.getSocketOptions(ch), this.handler == null ? null : this.handler.getSecurityInfo());
    }

    @VisibleForTesting
    Channel channel() {
        return this.channel;
    }

    @VisibleForTesting
    KeepAliveManager keepAliveManager() {
        return this.keepAliveManager;
    }

    private Status statusFromFailedFuture(ChannelFuture f) {
        Throwable t2 = f.cause();
        if (t2 instanceof ClosedChannelException || t2 instanceof StreamBufferingEncoder.Http2ChannelClosedException) {
            Status shutdownStatus = this.lifecycleManager.getShutdownStatus();
            if (shutdownStatus == null) {
                return Status.UNKNOWN.withDescription("Channel closed but for unknown reason").withCause(new ClosedChannelException().initCause(t2));
            }
            return shutdownStatus;
        }
        return Utils.statusFromThrowable(t2);
    }
}

