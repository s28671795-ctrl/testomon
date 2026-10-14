/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport.netty;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NettyUtils {
    private static final long DEFAULT_SHUTDOWN_QUIET_PERIOD = 0L;
    private static final long DEFAULT_SHUTDOWN_MAX_TIMEOUT = 1000L;
    private static final boolean USE_NIO;
    private static final boolean EPOLL_AVAILABLE;
    private static final boolean KQUEUE_AVAILABLE;
    private static final Logger LOGGER;

    private NettyUtils() {
    }

    public static Class<? extends Channel> getDefaultSocketChannelType() {
        if (EPOLL_AVAILABLE && !USE_NIO) {
            return EpollSocketChannel.class;
        }
        if (KQUEUE_AVAILABLE && !USE_NIO) {
            return KQueueSocketChannel.class;
        }
        return NioSocketChannel.class;
    }

    public static EventLoopGroup createDefaultEventLoopGroup(String name, int numThreads) {
        if (EPOLL_AVAILABLE && !USE_NIO) {
            return new EpollEventLoopGroup(numThreads, new ThreadPerTaskExecutor(new DefaultThreadFactory("ldaptive-" + name, true, 5)));
        }
        if (KQUEUE_AVAILABLE && !USE_NIO) {
            return new KQueueEventLoopGroup(numThreads, new ThreadPerTaskExecutor(new DefaultThreadFactory("ldaptive-" + name, true, 5)));
        }
        return new NioEventLoopGroup(numThreads, new ThreadPerTaskExecutor(new DefaultThreadFactory("ldaptive-" + name, true, 5)));
    }

    public static void shutdownGracefully(EventLoopGroup workerGroup) {
        CountDownLatch shutdownLatch = new CountDownLatch(1);
        workerGroup.shutdownGracefully(0L, 1000L, TimeUnit.MILLISECONDS).addListener(f -> {
            shutdownLatch.countDown();
            if (!f.isSuccess()) {
                if (f.cause() != null) {
                    LOGGER.warn("Could not shutdown worker group {}", (Object)workerGroup, (Object)f.cause());
                } else {
                    LOGGER.warn("Could not shutdown worker group {}", (Object)workerGroup);
                }
            } else {
                LOGGER.trace("worker group {} gracefully shutdown", (Object)workerGroup);
            }
        });
        try {
            if (!shutdownLatch.await(2000L, TimeUnit.MILLISECONDS)) {
                LOGGER.warn("Shutdown max timeout was not honored for worker group {}", (Object)workerGroup);
            }
        }
        catch (InterruptedException e) {
            LOGGER.warn("Interrupted during shutdown for worker group {}", (Object)workerGroup);
        }
    }

    static {
        boolean kqueueAvailable;
        boolean epollAvailable;
        USE_NIO = Boolean.valueOf(System.getProperty("org.ldaptive.transport.netty.useNio", "false"));
        LOGGER = LoggerFactory.getLogger(NettyUtils.class);
        try {
            Class.forName("io.netty.channel.epoll.Epoll");
            epollAvailable = Epoll.isAvailable();
        }
        catch (Exception e) {
            LOGGER.debug("Error detecting Epoll: {}:{}", (Object)e.getClass(), (Object)e.getMessage());
            epollAvailable = false;
        }
        EPOLL_AVAILABLE = epollAvailable;
        LOGGER.debug("Detected Epoll transport: {}", (Object)EPOLL_AVAILABLE);
        try {
            Class.forName("io.netty.channel.kqueue.KQueue");
            kqueueAvailable = KQueue.isAvailable();
        }
        catch (Exception e) {
            LOGGER.debug("Error detecting KQueue: {}:{}", (Object)e.getClass(), (Object)e.getMessage());
            kqueueAvailable = false;
        }
        KQUEUE_AVAILABLE = kqueueAvailable;
        LOGGER.debug("Detected KQueue transport: {}", (Object)KQUEUE_AVAILABLE);
        LOGGER.debug("Overriding to use Nio transport: {}", (Object)USE_NIO);
    }
}

