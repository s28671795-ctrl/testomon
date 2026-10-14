/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.bootstrap;

import io.grpc.netty.shaded.io.netty.bootstrap.AbstractBootstrap;
import io.grpc.netty.shaded.io.netty.bootstrap.BootstrapConfig;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelFutureListener;
import io.grpc.netty.shaded.io.netty.channel.ChannelPipeline;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.channel.EventLoop;
import io.grpc.netty.shaded.io.netty.channel.EventLoopGroup;
import io.grpc.netty.shaded.io.netty.resolver.AddressResolver;
import io.grpc.netty.shaded.io.netty.resolver.AddressResolverGroup;
import io.grpc.netty.shaded.io.netty.resolver.DefaultAddressResolverGroup;
import io.grpc.netty.shaded.io.netty.util.concurrent.Future;
import io.grpc.netty.shaded.io.netty.util.concurrent.FutureListener;
import io.grpc.netty.shaded.io.netty.util.concurrent.GenericFutureListener;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;
import io.grpc.netty.shaded.io.netty.util.internal.logging.InternalLogger;
import io.grpc.netty.shaded.io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class Bootstrap
extends AbstractBootstrap<Bootstrap, Channel> {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Bootstrap.class);
    private final BootstrapConfig config = new BootstrapConfig(this);
    private ExternalAddressResolver externalResolver;
    private volatile boolean disableResolver;
    private volatile SocketAddress remoteAddress;

    public Bootstrap() {
    }

    private Bootstrap(Bootstrap bootstrap) {
        super(bootstrap);
        this.externalResolver = bootstrap.externalResolver;
        this.disableResolver = bootstrap.disableResolver;
        this.remoteAddress = bootstrap.remoteAddress;
    }

    public Bootstrap resolver(AddressResolverGroup<?> resolver2) {
        this.externalResolver = resolver2 == null ? null : new ExternalAddressResolver(resolver2);
        this.disableResolver = false;
        return this;
    }

    public Bootstrap disableResolver() {
        this.externalResolver = null;
        this.disableResolver = true;
        return this;
    }

    public Bootstrap remoteAddress(SocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
        return this;
    }

    public Bootstrap remoteAddress(String inetHost, int inetPort) {
        this.remoteAddress = InetSocketAddress.createUnresolved(inetHost, inetPort);
        return this;
    }

    public Bootstrap remoteAddress(InetAddress inetHost, int inetPort) {
        this.remoteAddress = new InetSocketAddress(inetHost, inetPort);
        return this;
    }

    public ChannelFuture connect() {
        this.validate();
        SocketAddress remoteAddress = this.remoteAddress;
        if (remoteAddress == null) {
            throw new IllegalStateException("remoteAddress not set");
        }
        return this.doResolveAndConnect(remoteAddress, this.config.localAddress());
    }

    public ChannelFuture connect(String inetHost, int inetPort) {
        return this.connect(InetSocketAddress.createUnresolved(inetHost, inetPort));
    }

    public ChannelFuture connect(InetAddress inetHost, int inetPort) {
        return this.connect(new InetSocketAddress(inetHost, inetPort));
    }

    public ChannelFuture connect(SocketAddress remoteAddress) {
        ObjectUtil.checkNotNull(remoteAddress, "remoteAddress");
        this.validate();
        return this.doResolveAndConnect(remoteAddress, this.config.localAddress());
    }

    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
        ObjectUtil.checkNotNull(remoteAddress, "remoteAddress");
        this.validate();
        return this.doResolveAndConnect(remoteAddress, localAddress);
    }

    private ChannelFuture doResolveAndConnect(final SocketAddress remoteAddress, final SocketAddress localAddress) {
        ChannelFuture regFuture = this.initAndRegister();
        final Channel channel = regFuture.channel();
        if (regFuture.isDone()) {
            if (!regFuture.isSuccess()) {
                return regFuture;
            }
            return this.doResolveAndConnect0(channel, remoteAddress, localAddress, channel.newPromise());
        }
        final AbstractBootstrap.PendingRegistrationPromise promise = new AbstractBootstrap.PendingRegistrationPromise(channel);
        regFuture.addListener(new ChannelFutureListener(){

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                Throwable cause = future.cause();
                if (cause != null) {
                    promise.setFailure(cause);
                } else {
                    promise.registered();
                    Bootstrap.this.doResolveAndConnect0(channel, remoteAddress, localAddress, promise);
                }
            }
        });
        return promise;
    }

    private ChannelFuture doResolveAndConnect0(final Channel channel, SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
        try {
            AddressResolver<SocketAddress> resolver2;
            if (this.disableResolver) {
                Bootstrap.doConnect(remoteAddress, localAddress, promise);
                return promise;
            }
            EventLoop eventLoop = channel.eventLoop();
            try {
                resolver2 = ExternalAddressResolver.getOrDefault(this.externalResolver).getResolver(eventLoop);
            }
            catch (Throwable cause) {
                channel.close();
                return promise.setFailure(cause);
            }
            if (!resolver2.isSupported(remoteAddress) || resolver2.isResolved(remoteAddress)) {
                Bootstrap.doConnect(remoteAddress, localAddress, promise);
                return promise;
            }
            Future<SocketAddress> resolveFuture = resolver2.resolve(remoteAddress);
            if (resolveFuture.isDone()) {
                Throwable resolveFailureCause = resolveFuture.cause();
                if (resolveFailureCause != null) {
                    channel.close();
                    promise.setFailure(resolveFailureCause);
                } else {
                    Bootstrap.doConnect(resolveFuture.getNow(), localAddress, promise);
                }
                return promise;
            }
            resolveFuture.addListener((GenericFutureListener<Future<SocketAddress>>)new FutureListener<SocketAddress>(){

                @Override
                public void operationComplete(Future<SocketAddress> future) throws Exception {
                    if (future.cause() != null) {
                        channel.close();
                        promise.setFailure(future.cause());
                    } else {
                        Bootstrap.doConnect(future.getNow(), localAddress, promise);
                    }
                }
            });
        }
        catch (Throwable cause) {
            promise.tryFailure(cause);
        }
        return promise;
    }

    private static void doConnect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise connectPromise) {
        final Channel channel = connectPromise.channel();
        channel.eventLoop().execute(new Runnable(){

            @Override
            public void run() {
                if (localAddress == null) {
                    channel.connect(remoteAddress, connectPromise);
                } else {
                    channel.connect(remoteAddress, localAddress, connectPromise);
                }
                connectPromise.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        });
    }

    @Override
    void init(Channel channel) {
        ChannelPipeline p = channel.pipeline();
        p.addLast(this.config.handler());
        Bootstrap.setChannelOptions(channel, this.newOptionsArray(), logger);
        Bootstrap.setAttributes(channel, this.newAttributesArray());
    }

    @Override
    public Bootstrap validate() {
        super.validate();
        if (this.config.handler() == null) {
            throw new IllegalStateException("handler not set");
        }
        return this;
    }

    @Override
    public Bootstrap clone() {
        return new Bootstrap(this);
    }

    public Bootstrap clone(EventLoopGroup group) {
        Bootstrap bs = new Bootstrap(this);
        bs.group = group;
        return bs;
    }

    public final BootstrapConfig config() {
        return this.config;
    }

    final SocketAddress remoteAddress() {
        return this.remoteAddress;
    }

    final AddressResolverGroup<?> resolver() {
        if (this.disableResolver) {
            return null;
        }
        return ExternalAddressResolver.getOrDefault(this.externalResolver);
    }

    static final class ExternalAddressResolver {
        final AddressResolverGroup<SocketAddress> resolverGroup;

        ExternalAddressResolver(AddressResolverGroup<?> resolverGroup) {
            this.resolverGroup = resolverGroup;
        }

        static AddressResolverGroup<SocketAddress> getOrDefault(ExternalAddressResolver externalResolver) {
            if (externalResolver == null) {
                DefaultAddressResolverGroup defaultResolverGroup = DefaultAddressResolverGroup.INSTANCE;
                return defaultResolverGroup;
            }
            return externalResolver.resolverGroup;
        }
    }
}

