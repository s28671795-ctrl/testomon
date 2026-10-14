/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  reactor.blockhound.BlockHound$Builder
 *  reactor.blockhound.integration.BlockHoundIntegration
 */
package io.grpc.netty.shaded.io.netty.util.internal;

import io.grpc.netty.shaded.io.netty.util.concurrent.FastThreadLocalThread;
import io.grpc.netty.shaded.io.netty.util.internal.SuppressJava6Requirement;
import java.util.function.Function;
import java.util.function.Predicate;
import reactor.blockhound.BlockHound;
import reactor.blockhound.integration.BlockHoundIntegration;

class Hidden {
    Hidden() {
    }

    @SuppressJava6Requirement(reason="BlockHound is Java 8+, but this class is only loaded by it's SPI")
    public static final class NettyBlockHoundIntegration
    implements BlockHoundIntegration {
        public void applyTo(BlockHound.Builder builder) {
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.channel.nio.NioEventLoop", "handleLoopException");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.channel.kqueue.KQueueEventLoop", "handleLoopException");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.channel.epoll.EpollEventLoop", "handleLoopException");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.util.HashedWheelTimer", "start");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.util.HashedWheelTimer", "stop");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.util.HashedWheelTimer$Worker", "waitForNextTick");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.util.concurrent.SingleThreadEventExecutor", "confirmShutdown");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.buffer.PoolArena", "lock");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.buffer.PoolSubpage", "lock");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.buffer.PoolChunk", "allocateRun");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.buffer.PoolChunk", "free");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.handler.ssl.SslHandler", "handshake");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.handler.ssl.SslHandler", "runAllDelegatedTasks");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.handler.ssl.SslHandler", "runDelegatedTasks");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.util.concurrent.GlobalEventExecutor", "takeTask");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.util.concurrent.GlobalEventExecutor", "addTask");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.util.concurrent.SingleThreadEventExecutor", "takeTask");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.util.concurrent.SingleThreadEventExecutor", "addTask");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.handler.ssl.ReferenceCountedOpenSslClientContext$ExtendedTrustManagerVerifyCallback", "verify");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.handler.ssl.JdkSslContext$Defaults", "init");
            builder.allowBlockingCallsInside("sun.security.ssl.SSLEngineImpl", "unwrap");
            builder.allowBlockingCallsInside("sun.security.ssl.SSLEngineImpl", "wrap");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.resolver.dns.UnixResolverDnsServerAddressStreamProvider", "parse");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.resolver.dns.UnixResolverDnsServerAddressStreamProvider", "parseEtcResolverSearchDomains");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.resolver.dns.UnixResolverDnsServerAddressStreamProvider", "parseEtcResolverOptions");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.resolver.HostsFileEntriesProvider$ParserImpl", "parse");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.util.NetUtil$SoMaxConnAction", "run");
            builder.allowBlockingCallsInside("io.grpc.netty.shaded.io.netty.util.internal.PlatformDependent", "createTempFile");
            builder.nonBlockingThreadPredicate((Function)new Function<Predicate<Thread>, Predicate<Thread>>(){

                @Override
                public Predicate<Thread> apply(final Predicate<Thread> p) {
                    return new Predicate<Thread>(){

                        @Override
                        @SuppressJava6Requirement(reason="Predicate#test")
                        public boolean test(Thread thread2) {
                            return p.test(thread2) || thread2 instanceof FastThreadLocalThread && !((FastThreadLocalThread)thread2).permitBlockingCalls();
                        }
                    };
                }
            });
        }

        public int compareTo(BlockHoundIntegration o) {
            return 0;
        }
    }
}

