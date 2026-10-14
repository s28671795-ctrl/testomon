/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.Attributes;
import io.grpc.InternalChannelz;
import io.grpc.InternalLogId;
import io.grpc.ServerStreamTracer;
import io.grpc.Status;
import io.grpc.internal.ServerTransport;
import io.grpc.internal.ServerTransportListener;
import io.grpc.internal.TransportTracer;
import io.grpc.netty.shaded.io.grpc.netty.ForcefulCloseCommand;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerHandler;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiator;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.grpc.netty.WriteBufferingAndExceptionHandler;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelFutureListener;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.util.concurrent.Future;
import io.grpc.netty.shaded.io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

class NettyServerTransport
implements ServerTransport {
    private static final Logger connectionLog = Logger.getLogger(String.format("%s.connections", NettyServerTransport.class.getName()));
    private static final ImmutableList<String> QUIET_EXCEPTIONS = ImmutableList.of("NativeIoException");
    private final InternalLogId logId;
    private final Channel channel;
    private final ChannelPromise channelUnused;
    private final ProtocolNegotiator protocolNegotiator;
    private final int maxStreams;
    private NettyServerHandler grpcHandler;
    private ServerTransportListener listener;
    private boolean terminated;
    private final boolean autoFlowControl;
    private final int flowControlWindow;
    private final int maxMessageSize;
    private final int maxHeaderListSize;
    private final long keepAliveTimeInNanos;
    private final long keepAliveTimeoutInNanos;
    private final long maxConnectionIdleInNanos;
    private final long maxConnectionAgeInNanos;
    private final long maxConnectionAgeGraceInNanos;
    private final boolean permitKeepAliveWithoutCalls;
    private final long permitKeepAliveTimeInNanos;
    private final int maxRstCount;
    private final long maxRstPeriodNanos;
    private final Attributes eagAttributes;
    private final List<? extends ServerStreamTracer.Factory> streamTracerFactories;
    private final TransportTracer transportTracer;

    NettyServerTransport(Channel channel, ChannelPromise channelUnused, ProtocolNegotiator protocolNegotiator, List<? extends ServerStreamTracer.Factory> streamTracerFactories, TransportTracer transportTracer, int maxStreams, boolean autoFlowControl, int flowControlWindow, int maxMessageSize, int maxHeaderListSize, long keepAliveTimeInNanos, long keepAliveTimeoutInNanos, long maxConnectionIdleInNanos, long maxConnectionAgeInNanos, long maxConnectionAgeGraceInNanos, boolean permitKeepAliveWithoutCalls, long permitKeepAliveTimeInNanos, int maxRstCount, long maxRstPeriodNanos, Attributes eagAttributes) {
        this.channel = Preconditions.checkNotNull(channel, "channel");
        this.channelUnused = channelUnused;
        this.protocolNegotiator = Preconditions.checkNotNull(protocolNegotiator, "protocolNegotiator");
        this.streamTracerFactories = Preconditions.checkNotNull(streamTracerFactories, "streamTracerFactories");
        this.transportTracer = Preconditions.checkNotNull(transportTracer, "transportTracer");
        this.maxStreams = maxStreams;
        this.autoFlowControl = autoFlowControl;
        this.flowControlWindow = flowControlWindow;
        this.maxMessageSize = maxMessageSize;
        this.maxHeaderListSize = maxHeaderListSize;
        this.keepAliveTimeInNanos = keepAliveTimeInNanos;
        this.keepAliveTimeoutInNanos = keepAliveTimeoutInNanos;
        this.maxConnectionIdleInNanos = maxConnectionIdleInNanos;
        this.maxConnectionAgeInNanos = maxConnectionAgeInNanos;
        this.maxConnectionAgeGraceInNanos = maxConnectionAgeGraceInNanos;
        this.permitKeepAliveWithoutCalls = permitKeepAliveWithoutCalls;
        this.permitKeepAliveTimeInNanos = permitKeepAliveTimeInNanos;
        this.maxRstCount = maxRstCount;
        this.maxRstPeriodNanos = maxRstPeriodNanos;
        this.eagAttributes = Preconditions.checkNotNull(eagAttributes, "eagAttributes");
        SocketAddress remote = channel.remoteAddress();
        this.logId = InternalLogId.allocate(this.getClass(), remote != null ? remote.toString() : null);
    }

    public void start(ServerTransportListener listener) {
        Preconditions.checkState(this.listener == null, "Handler already registered");
        this.listener = listener;
        this.grpcHandler = this.createHandler(listener, this.channelUnused);
        ChannelHandler negotiationHandler = this.protocolNegotiator.newHandler(this.grpcHandler);
        WriteBufferingAndExceptionHandler bufferingHandler = new WriteBufferingAndExceptionHandler(negotiationHandler);
        final class TerminationNotifier
        implements ChannelFutureListener {
            boolean done;

            TerminationNotifier() {
            }

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!this.done) {
                    this.done = true;
                    NettyServerTransport.this.notifyTerminated(NettyServerTransport.this.grpcHandler.connectionError());
                }
            }
        }
        TerminationNotifier terminationNotifier = new TerminationNotifier();
        this.channelUnused.addListener(terminationNotifier);
        this.channel.closeFuture().addListener(terminationNotifier);
        this.channel.pipeline().addLast(bufferingHandler);
    }

    @Override
    public ScheduledExecutorService getScheduledExecutorService() {
        return this.channel.eventLoop();
    }

    @Override
    public void shutdown() {
        if (this.channel.isOpen()) {
            this.channel.close();
        }
    }

    @Override
    public void shutdownNow(Status reason) {
        if (this.channel.isOpen()) {
            this.channel.writeAndFlush(new ForcefulCloseCommand(reason));
        }
    }

    @Override
    public InternalLogId getLogId() {
        return this.logId;
    }

    Channel channel() {
        return this.channel;
    }

    @VisibleForTesting
    static Level getLogLevel(Throwable t2) {
        if (t2.getClass().equals(IOException.class) || t2.getClass().equals(SocketException.class) || QUIET_EXCEPTIONS.contains(t2.getClass().getSimpleName())) {
            return Level.FINE;
        }
        return Level.INFO;
    }

    private void notifyTerminated(Throwable t2) {
        if (t2 != null) {
            connectionLog.log(NettyServerTransport.getLogLevel(t2), "Transport failed", t2);
        }
        if (!this.terminated) {
            this.terminated = true;
            this.listener.transportTerminated();
        }
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
                result.set(NettyServerTransport.this.getStatsHelper(NettyServerTransport.this.channel));
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
        Preconditions.checkState(ch.eventLoop().inEventLoop());
        return new InternalChannelz.SocketStats(this.transportTracer.getStats(), this.channel.localAddress(), this.channel.remoteAddress(), Utils.getSocketOptions(ch), this.grpcHandler == null ? null : this.grpcHandler.getSecurityInfo());
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("logId", this.logId.getId()).add("channel", this.channel).toString();
    }

    private NettyServerHandler createHandler(ServerTransportListener transportListener, ChannelPromise channelUnused) {
        return NettyServerHandler.newHandler(transportListener, channelUnused, this.streamTracerFactories, this.transportTracer, this.maxStreams, this.autoFlowControl, this.flowControlWindow, this.maxHeaderListSize, this.maxMessageSize, this.keepAliveTimeInNanos, this.keepAliveTimeoutInNanos, this.maxConnectionIdleInNanos, this.maxConnectionAgeInNanos, this.maxConnectionAgeGraceInNanos, this.permitKeepAliveWithoutCalls, this.permitKeepAliveTimeInNanos, this.maxRstCount, this.maxRstPeriodNanos, this.eagAttributes);
    }
}

