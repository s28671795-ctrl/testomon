/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.Attributes;
import io.grpc.InternalChannelz;
import io.grpc.InternalInstrumented;
import io.grpc.InternalLogId;
import io.grpc.InternalWithLogId;
import io.grpc.ServerStreamTracer;
import io.grpc.internal.InternalServer;
import io.grpc.internal.ObjectPool;
import io.grpc.internal.ServerListener;
import io.grpc.internal.ServerTransportListener;
import io.grpc.internal.TransportTracer;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerTransport;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiator;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.netty.bootstrap.ServerBootstrap;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFactory;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelFutureListener;
import io.grpc.netty.shaded.io.netty.channel.ChannelInitializer;
import io.grpc.netty.shaded.io.netty.channel.ChannelOption;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.channel.EventLoop;
import io.grpc.netty.shaded.io.netty.channel.EventLoopGroup;
import io.grpc.netty.shaded.io.netty.channel.ServerChannel;
import io.grpc.netty.shaded.io.netty.channel.group.ChannelGroup;
import io.grpc.netty.shaded.io.netty.channel.group.ChannelGroupFuture;
import io.grpc.netty.shaded.io.netty.channel.group.ChannelGroupFutureListener;
import io.grpc.netty.shaded.io.netty.channel.group.DefaultChannelGroup;
import io.grpc.netty.shaded.io.netty.util.AbstractReferenceCounted;
import io.grpc.netty.shaded.io.netty.util.ReferenceCounted;
import io.grpc.netty.shaded.io.netty.util.concurrent.Future;
import io.grpc.netty.shaded.io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

class NettyServer
implements InternalServer,
InternalWithLogId {
    private static final Logger log = Logger.getLogger(InternalServer.class.getName());
    private final InternalLogId logId;
    private final List<? extends SocketAddress> addresses;
    private final ChannelFactory<? extends ServerChannel> channelFactory;
    private final Map<ChannelOption<?>, ?> channelOptions;
    private final Map<ChannelOption<?>, ?> childChannelOptions;
    private final ProtocolNegotiator protocolNegotiator;
    private final int maxStreamsPerConnection;
    private final ObjectPool<? extends EventLoopGroup> bossGroupPool;
    private final ObjectPool<? extends EventLoopGroup> workerGroupPool;
    private final boolean forceHeapBuffer;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerListener listener;
    private final ChannelGroup channelGroup;
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
    private final ReferenceCounted sharedResourceReferenceCounter = new SharedResourceReferenceCounter();
    private final List<? extends ServerStreamTracer.Factory> streamTracerFactories;
    private final TransportTracer.Factory transportTracerFactory;
    private final InternalChannelz channelz;
    private volatile List<InternalInstrumented<InternalChannelz.SocketStats>> listenSocketStatsList = Collections.emptyList();
    private volatile boolean terminated;
    private final EventLoop bossExecutor;

    NettyServer(List<? extends SocketAddress> addresses, ChannelFactory<? extends ServerChannel> channelFactory, Map<ChannelOption<?>, ?> channelOptions, Map<ChannelOption<?>, ?> childChannelOptions, ObjectPool<? extends EventLoopGroup> bossGroupPool, ObjectPool<? extends EventLoopGroup> workerGroupPool, boolean forceHeapBuffer, ProtocolNegotiator protocolNegotiator, List<? extends ServerStreamTracer.Factory> streamTracerFactories, TransportTracer.Factory transportTracerFactory, int maxStreamsPerConnection, boolean autoFlowControl, int flowControlWindow, int maxMessageSize, int maxHeaderListSize, long keepAliveTimeInNanos, long keepAliveTimeoutInNanos, long maxConnectionIdleInNanos, long maxConnectionAgeInNanos, long maxConnectionAgeGraceInNanos, boolean permitKeepAliveWithoutCalls, long permitKeepAliveTimeInNanos, int maxRstCount, long maxRstPeriodNanos, Attributes eagAttributes, InternalChannelz channelz) {
        this.addresses = Preconditions.checkNotNull(addresses, "addresses");
        this.channelFactory = Preconditions.checkNotNull(channelFactory, "channelFactory");
        Preconditions.checkNotNull(channelOptions, "channelOptions");
        this.channelOptions = new HashMap(channelOptions);
        Preconditions.checkNotNull(childChannelOptions, "childChannelOptions");
        this.childChannelOptions = new HashMap(childChannelOptions);
        this.bossGroupPool = Preconditions.checkNotNull(bossGroupPool, "bossGroupPool");
        this.workerGroupPool = Preconditions.checkNotNull(workerGroupPool, "workerGroupPool");
        this.forceHeapBuffer = forceHeapBuffer;
        this.bossGroup = bossGroupPool.getObject();
        this.bossExecutor = this.bossGroup.next();
        this.channelGroup = new DefaultChannelGroup(this.bossExecutor);
        this.workerGroup = workerGroupPool.getObject();
        this.protocolNegotiator = Preconditions.checkNotNull(protocolNegotiator, "protocolNegotiator");
        this.streamTracerFactories = Preconditions.checkNotNull(streamTracerFactories, "streamTracerFactories");
        this.transportTracerFactory = transportTracerFactory;
        this.maxStreamsPerConnection = maxStreamsPerConnection;
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
        this.channelz = Preconditions.checkNotNull(channelz);
        this.logId = InternalLogId.allocate(this.getClass(), addresses.isEmpty() ? "No address" : String.valueOf(addresses));
    }

    @Override
    public SocketAddress getListenSocketAddress() {
        Iterator it = this.channelGroup.iterator();
        if (it.hasNext()) {
            return ((Channel)it.next()).localAddress();
        }
        return this.addresses.isEmpty() ? null : this.addresses.get(0);
    }

    public List<SocketAddress> getListenSocketAddresses() {
        ArrayList<SocketAddress> listenSocketAddresses = new ArrayList<SocketAddress>();
        for (Channel c : this.channelGroup) {
            listenSocketAddresses.add(c.localAddress());
        }
        if (listenSocketAddresses.isEmpty()) {
            listenSocketAddresses.addAll(this.addresses);
        }
        return listenSocketAddresses;
    }

    @Override
    public InternalInstrumented<InternalChannelz.SocketStats> getListenSocketStats() {
        List<InternalInstrumented<InternalChannelz.SocketStats>> savedListenSocketStatsList = this.listenSocketStatsList;
        return savedListenSocketStatsList.isEmpty() ? null : savedListenSocketStatsList.get(0);
    }

    @Override
    public List<InternalInstrumented<InternalChannelz.SocketStats>> getListenSocketStatsList() {
        return this.listenSocketStatsList;
    }

    @Override
    public void start(ServerListener serverListener) throws IOException {
        ChannelOption<?> key;
        this.listener = Preconditions.checkNotNull(serverListener, "serverListener");
        final ServerBootstrap b = new ServerBootstrap();
        b.option(ChannelOption.ALLOCATOR, Utils.getByteBufAllocator(this.forceHeapBuffer));
        b.childOption(ChannelOption.ALLOCATOR, Utils.getByteBufAllocator(this.forceHeapBuffer));
        b.group(this.bossExecutor, this.workerGroup);
        b.channelFactory(this.channelFactory);
        b.childOption(ChannelOption.SO_KEEPALIVE, true);
        if (this.channelOptions != null) {
            for (Map.Entry<ChannelOption<?>, ?> entry : this.channelOptions.entrySet()) {
                key = entry.getKey();
                b.option(key, entry.getValue());
            }
        }
        if (this.childChannelOptions != null) {
            for (Map.Entry<ChannelOption<?>, ?> entry : this.childChannelOptions.entrySet()) {
                key = entry.getKey();
                b.childOption(key, entry.getValue());
            }
        }
        b.childHandler(new ChannelInitializer<Channel>(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void initChannel(Channel ch) {
                ServerTransportListener transportListener;
                ChannelPromise channelDone = ch.newPromise();
                long maxConnectionAgeInNanos = NettyServer.this.maxConnectionAgeInNanos;
                if (maxConnectionAgeInNanos != Long.MAX_VALUE) {
                    maxConnectionAgeInNanos = (long)((0.9 + Math.random() * 0.2) * (double)maxConnectionAgeInNanos);
                }
                NettyServerTransport transport = new NettyServerTransport(ch, channelDone, NettyServer.this.protocolNegotiator, NettyServer.this.streamTracerFactories, NettyServer.this.transportTracerFactory.create(), NettyServer.this.maxStreamsPerConnection, NettyServer.this.autoFlowControl, NettyServer.this.flowControlWindow, NettyServer.this.maxMessageSize, NettyServer.this.maxHeaderListSize, NettyServer.this.keepAliveTimeInNanos, NettyServer.this.keepAliveTimeoutInNanos, NettyServer.this.maxConnectionIdleInNanos, maxConnectionAgeInNanos, NettyServer.this.maxConnectionAgeGraceInNanos, NettyServer.this.permitKeepAliveWithoutCalls, NettyServer.this.permitKeepAliveTimeInNanos, NettyServer.this.maxRstCount, NettyServer.this.maxRstPeriodNanos, NettyServer.this.eagAttributes);
                NettyServer nettyServer = NettyServer.this;
                synchronized (nettyServer) {
                    if (NettyServer.this.terminated) {
                        ch.close();
                        return;
                    }
                    NettyServer.this.sharedResourceReferenceCounter.retain();
                    transportListener = NettyServer.this.listener.transportCreated(transport);
                }
                transport.start(transportListener);
                final class LoopReleaser
                implements ChannelFutureListener {
                    private boolean done;

                    LoopReleaser() {
                    }

                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!this.done) {
                            this.done = true;
                            NettyServer.this.sharedResourceReferenceCounter.release();
                        }
                    }
                }
                LoopReleaser loopReleaser = new LoopReleaser();
                channelDone.addListener(loopReleaser);
                ch.closeFuture().addListener(loopReleaser);
            }
        });
        Future<Map<ChannelFuture, SocketAddress>> bindCallFuture = this.bossExecutor.submit(new Callable<Map<ChannelFuture, SocketAddress>>(){

            @Override
            public Map<ChannelFuture, SocketAddress> call() {
                HashMap<ChannelFuture, SocketAddress> bindFutures = new HashMap<ChannelFuture, SocketAddress>();
                for (SocketAddress address : NettyServer.this.addresses) {
                    ChannelFuture future = b.bind(address);
                    NettyServer.this.channelGroup.add(future.channel());
                    bindFutures.put(future, address);
                }
                return bindFutures;
            }
        });
        Map<ChannelFuture, SocketAddress> channelFutures = bindCallFuture.awaitUninterruptibly().getNow();
        if (!bindCallFuture.isSuccess()) {
            this.channelGroup.close().awaitUninterruptibly();
            throw new IOException(String.format("Failed to bind to addresses %s", this.addresses), bindCallFuture.cause());
        }
        ArrayList<ListenSocket> socketStats = new ArrayList<ListenSocket>();
        for (Map.Entry<ChannelFuture, SocketAddress> entry : channelFutures.entrySet()) {
            ChannelFuture future = entry.getKey();
            if (!future.awaitUninterruptibly().isSuccess()) {
                this.channelGroup.close().awaitUninterruptibly();
                throw new IOException(String.format("Failed to bind to address %s", entry.getValue()), future.cause());
            }
            final ListenSocket listenSocketStats = new ListenSocket(future.channel());
            this.channelz.addListenSocket(listenSocketStats);
            socketStats.add(listenSocketStats);
            future.channel().closeFuture().addListener(new ChannelFutureListener(){

                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    NettyServer.this.channelz.removeListenSocket(listenSocketStats);
                }
            });
        }
        this.listenSocketStatsList = Collections.unmodifiableList(socketStats);
    }

    @Override
    public void shutdown() {
        if (this.terminated) {
            return;
        }
        ChannelGroupFuture groupFuture = this.channelGroup.close().addListener(new ChannelGroupFutureListener(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void operationComplete(ChannelGroupFuture future) throws Exception {
                if (!future.isSuccess()) {
                    log.log(Level.WARNING, "Error closing server channel group", future.cause());
                }
                NettyServer.this.sharedResourceReferenceCounter.release();
                NettyServer.this.protocolNegotiator.close();
                NettyServer.this.listenSocketStatsList = Collections.emptyList();
                NettyServer nettyServer = NettyServer.this;
                synchronized (nettyServer) {
                    NettyServer.this.listener.serverShutdown();
                    NettyServer.this.terminated = true;
                }
            }
        });
        try {
            groupFuture.await();
        }
        catch (InterruptedException e) {
            log.log(Level.FINE, "Interrupted while shutting down", e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public InternalLogId getLogId() {
        return this.logId;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("logId", this.logId.getId()).add("addresses", this.addresses).toString();
    }

    private static final class ListenSocket
    implements InternalInstrumented<InternalChannelz.SocketStats> {
        private final InternalLogId id;
        private final Channel ch;

        ListenSocket(Channel ch) {
            this.ch = ch;
            this.id = InternalLogId.allocate(this.getClass(), String.valueOf(ch.localAddress()));
        }

        @Override
        public ListenableFuture<InternalChannelz.SocketStats> getStats() {
            final SettableFuture<InternalChannelz.SocketStats> ret = SettableFuture.create();
            if (this.ch.eventLoop().inEventLoop()) {
                ret.set(new InternalChannelz.SocketStats(null, this.ch.localAddress(), null, Utils.getSocketOptions(this.ch), null));
                return ret;
            }
            this.ch.eventLoop().submit(new Runnable(){

                @Override
                public void run() {
                    ret.set(new InternalChannelz.SocketStats(null, ch.localAddress(), null, Utils.getSocketOptions(ch), null));
                }
            }).addListener(new GenericFutureListener<Future<Object>>(){

                @Override
                public void operationComplete(Future<Object> future) throws Exception {
                    if (!future.isSuccess()) {
                        ret.setException(future.cause());
                    }
                }
            });
            return ret;
        }

        @Override
        public InternalLogId getLogId() {
            return this.id;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("logId", this.id.getId()).add("channel", this.ch).toString();
        }
    }

    class SharedResourceReferenceCounter
    extends AbstractReferenceCounted {
        SharedResourceReferenceCounter() {
        }

        @Override
        protected void deallocate() {
            try {
                if (NettyServer.this.bossGroup != null) {
                    NettyServer.this.bossGroupPool.returnObject(NettyServer.this.bossGroup);
                }
            }
            finally {
                NettyServer.this.bossGroup = null;
                try {
                    if (NettyServer.this.workerGroup != null) {
                        NettyServer.this.workerGroupPool.returnObject(NettyServer.this.workerGroup);
                    }
                }
                finally {
                    NettyServer.this.workerGroup = null;
                }
            }
        }

        @Override
        public ReferenceCounted touch(Object hint) {
            return this;
        }
    }
}

