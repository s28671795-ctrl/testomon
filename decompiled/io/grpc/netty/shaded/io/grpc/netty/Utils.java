/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.InternalChannelz;
import io.grpc.InternalMetadata;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.SharedResourceHolder;
import io.grpc.internal.TransportFrameUtil;
import io.grpc.internal.TransportTracer;
import io.grpc.netty.shaded.io.grpc.netty.GrpcHttp2HeadersUtils;
import io.grpc.netty.shaded.io.grpc.netty.GrpcHttp2OutboundHeaders;
import io.grpc.netty.shaded.io.grpc.netty.NettySocketSupport;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufAllocator;
import io.grpc.netty.shaded.io.netty.buffer.PooledByteBufAllocator;
import io.grpc.netty.shaded.io.netty.buffer.UnpooledByteBufAllocator;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelConfig;
import io.grpc.netty.shaded.io.netty.channel.ChannelFactory;
import io.grpc.netty.shaded.io.netty.channel.ChannelOption;
import io.grpc.netty.shaded.io.netty.channel.EventLoopGroup;
import io.grpc.netty.shaded.io.netty.channel.ReflectiveChannelFactory;
import io.grpc.netty.shaded.io.netty.channel.ServerChannel;
import io.grpc.netty.shaded.io.netty.channel.nio.NioEventLoopGroup;
import io.grpc.netty.shaded.io.netty.channel.socket.nio.NioServerSocketChannel;
import io.grpc.netty.shaded.io.netty.channel.socket.nio.NioSocketChannel;
import io.grpc.netty.shaded.io.netty.handler.codec.DecoderException;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Connection;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Exception;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2FlowController;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Headers;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Stream;
import io.grpc.netty.shaded.io.netty.util.AsciiString;
import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.grpc.netty.shaded.io.netty.util.NettyRuntime;
import io.grpc.netty.shaded.io.netty.util.concurrent.DefaultThreadFactory;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.UnresolvedAddressException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.net.ssl.SSLException;

class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class.getName());
    public static final AsciiString STATUS_OK = AsciiString.of("200");
    public static final AsciiString HTTP_METHOD = AsciiString.of("POST");
    public static final AsciiString HTTP_GET_METHOD = AsciiString.of("GET");
    public static final AsciiString HTTPS = AsciiString.of("https");
    public static final AsciiString HTTP = AsciiString.of("http");
    public static final AsciiString CONTENT_TYPE_HEADER = AsciiString.of(GrpcUtil.CONTENT_TYPE_KEY.name());
    public static final AsciiString CONTENT_TYPE_GRPC = AsciiString.of("application/grpc");
    public static final AsciiString TE_HEADER = AsciiString.of(GrpcUtil.TE_HEADER.name());
    public static final AsciiString TE_TRAILERS = AsciiString.of("trailers");
    public static final AsciiString USER_AGENT = AsciiString.of(GrpcUtil.USER_AGENT_KEY.name());
    public static final SharedResourceHolder.Resource<EventLoopGroup> NIO_BOSS_EVENT_LOOP_GROUP = new DefaultEventLoopGroupResource(1, "grpc-nio-boss-ELG", EventLoopGroupType.NIO);
    public static final SharedResourceHolder.Resource<EventLoopGroup> NIO_WORKER_EVENT_LOOP_GROUP = new DefaultEventLoopGroupResource(0, "grpc-nio-worker-ELG", EventLoopGroupType.NIO);
    public static final SharedResourceHolder.Resource<EventLoopGroup> DEFAULT_BOSS_EVENT_LOOP_GROUP;
    public static final SharedResourceHolder.Resource<EventLoopGroup> DEFAULT_WORKER_EVENT_LOOP_GROUP;
    public static final ChannelFactory<? extends ServerChannel> DEFAULT_SERVER_CHANNEL_FACTORY;
    public static final Class<? extends Channel> DEFAULT_CLIENT_CHANNEL_TYPE;
    public static final Class<? extends Channel> EPOLL_DOMAIN_CLIENT_CHANNEL_TYPE;
    @Nullable
    private static final Constructor<? extends EventLoopGroup> EPOLL_EVENT_LOOP_GROUP_CONSTRUCTOR;

    public static ByteBufAllocator getByteBufAllocator(boolean forceHeapBuffer) {
        if (Boolean.parseBoolean(System.getProperty("io.grpc.netty.shaded.io.grpc.netty.useCustomAllocator", "true"))) {
            String allocType = System.getProperty("io.grpc.netty.shaded.io.netty.allocator.type", "pooled");
            if (allocType.toLowerCase(Locale.ROOT).equals("unpooled")) {
                logger.log(Level.FINE, "Using unpooled allocator");
                return UnpooledByteBufAllocator.DEFAULT;
            }
            boolean defaultPreferDirect = PooledByteBufAllocator.defaultPreferDirect();
            logger.log(Level.FINE, "Using custom allocator: forceHeapBuffer={0}, defaultPreferDirect={1}", new Object[]{forceHeapBuffer, defaultPreferDirect});
            if (forceHeapBuffer || !defaultPreferDirect) {
                return ByteBufAllocatorPreferHeapHolder.allocator;
            }
            return ByteBufAllocatorPreferDirectHolder.allocator;
        }
        logger.log(Level.FINE, "Using default allocator");
        return ByteBufAllocator.DEFAULT;
    }

    private static ByteBufAllocator createByteBufAllocator(boolean preferDirect) {
        int maxOrder;
        logger.log(Level.FINE, "Creating allocator, preferDirect=" + preferDirect);
        if (System.getProperty("io.grpc.netty.shaded.io.netty.allocator.maxOrder") == null) {
            maxOrder = 8;
            logger.log(Level.FINE, "Forcing maxOrder=" + maxOrder);
        } else {
            maxOrder = PooledByteBufAllocator.defaultMaxOrder();
            logger.log(Level.FINE, "Using default maxOrder=" + maxOrder);
        }
        return new PooledByteBufAllocator(preferDirect, PooledByteBufAllocator.defaultNumHeapArena(), preferDirect ? PooledByteBufAllocator.defaultNumDirectArena() : 0, PooledByteBufAllocator.defaultPageSize(), maxOrder, PooledByteBufAllocator.defaultSmallCacheSize(), PooledByteBufAllocator.defaultNormalCacheSize(), PooledByteBufAllocator.defaultUseCacheForAllThreads());
    }

    public static Metadata convertHeaders(Http2Headers http2Headers) {
        if (http2Headers instanceof GrpcHttp2HeadersUtils.GrpcHttp2InboundHeaders) {
            GrpcHttp2HeadersUtils.GrpcHttp2InboundHeaders h2 = (GrpcHttp2HeadersUtils.GrpcHttp2InboundHeaders)http2Headers;
            return InternalMetadata.newMetadata(h2.numHeaders(), h2.namesAndValues());
        }
        return InternalMetadata.newMetadata(Utils.convertHeadersToArray(http2Headers));
    }

    @CheckReturnValue
    private static byte[][] convertHeadersToArray(Http2Headers http2Headers) {
        byte[][] headerValues = new byte[http2Headers.size() * 2][];
        int i = 0;
        for (Map.Entry<CharSequence, CharSequence> entry : http2Headers) {
            headerValues[i++] = Utils.bytes(entry.getKey());
            headerValues[i++] = Utils.bytes(entry.getValue());
        }
        return TransportFrameUtil.toRawSerializedHeaders(headerValues);
    }

    private static byte[] bytes(CharSequence seq) {
        if (seq instanceof AsciiString) {
            AsciiString str = (AsciiString)seq;
            return str.isEntireArrayUsed() ? str.array() : str.toByteArray();
        }
        return seq.toString().getBytes(CharsetUtil.UTF_8);
    }

    public static Http2Headers convertClientHeaders(Metadata headers, AsciiString scheme2, AsciiString defaultPath, AsciiString authority, AsciiString method2, AsciiString userAgent) {
        Preconditions.checkNotNull(defaultPath, "defaultPath");
        Preconditions.checkNotNull(authority, "authority");
        Preconditions.checkNotNull(method2, "method");
        headers.discardAll(GrpcUtil.CONTENT_TYPE_KEY);
        headers.discardAll(GrpcUtil.TE_HEADER);
        headers.discardAll(GrpcUtil.USER_AGENT_KEY);
        return GrpcHttp2OutboundHeaders.clientRequestHeaders(TransportFrameUtil.toHttp2Headers(headers), authority, defaultPath, method2, scheme2, userAgent);
    }

    public static Http2Headers convertServerHeaders(Metadata headers) {
        headers.discardAll(GrpcUtil.CONTENT_TYPE_KEY);
        headers.discardAll(GrpcUtil.TE_HEADER);
        headers.discardAll(GrpcUtil.USER_AGENT_KEY);
        return GrpcHttp2OutboundHeaders.serverResponseHeaders(TransportFrameUtil.toHttp2Headers(headers));
    }

    public static Metadata convertTrailers(Http2Headers http2Headers) {
        if (http2Headers instanceof GrpcHttp2HeadersUtils.GrpcHttp2InboundHeaders) {
            GrpcHttp2HeadersUtils.GrpcHttp2InboundHeaders h2 = (GrpcHttp2HeadersUtils.GrpcHttp2InboundHeaders)http2Headers;
            return InternalMetadata.newMetadata(h2.numHeaders(), h2.namesAndValues());
        }
        return InternalMetadata.newMetadata(Utils.convertHeadersToArray(http2Headers));
    }

    public static Http2Headers convertTrailers(Metadata trailers, boolean headersSent) {
        if (!headersSent) {
            return Utils.convertServerHeaders(trailers);
        }
        return GrpcHttp2OutboundHeaders.serverResponseTrailers(TransportFrameUtil.toHttp2Headers(trailers));
    }

    public static Status statusFromThrowable(Throwable t2) {
        Status s2 = Status.fromThrowable(t2);
        if (s2.getCode() != Status.Code.UNKNOWN) {
            return s2;
        }
        if (t2 instanceof ClosedChannelException) {
            if (t2.getCause() != null) {
                return Status.UNAVAILABLE.withDescription("channel closed").withCause(t2);
            }
            ClosedChannelException extraT = new ClosedChannelException();
            extraT.initCause(t2);
            return Status.UNKNOWN.withDescription("channel closed").withCause(extraT);
        }
        if (t2 instanceof DecoderException && t2.getCause() instanceof SSLException) {
            return Status.UNAVAILABLE.withDescription("ssl exception").withCause(t2);
        }
        if (t2 instanceof IOException) {
            return Status.UNAVAILABLE.withDescription("io exception").withCause(t2);
        }
        if (t2 instanceof UnresolvedAddressException) {
            return Status.UNAVAILABLE.withDescription("unresolved address").withCause(t2);
        }
        if (t2 instanceof Http2Exception) {
            return Status.INTERNAL.withDescription("http2 exception").withCause(t2);
        }
        return s2;
    }

    @VisibleForTesting
    static boolean isEpollAvailable() {
        try {
            return (Boolean)Class.forName("io.grpc.netty.shaded.io.netty.channel.epoll.Epoll").getDeclaredMethod("isAvailable", new Class[0]).invoke(null, new Object[0]);
        }
        catch (ClassNotFoundException e) {
            return false;
        }
        catch (Exception e) {
            throw new RuntimeException("Exception while checking Epoll availability", e);
        }
    }

    private static Throwable getEpollUnavailabilityCause() {
        try {
            return (Throwable)Class.forName("io.grpc.netty.shaded.io.netty.channel.epoll.Epoll").getDeclaredMethod("unavailabilityCause", new Class[0]).invoke(null, new Object[0]);
        }
        catch (Exception e) {
            return e;
        }
    }

    private static Class<? extends Channel> epollChannelType() {
        try {
            Class<Channel> channelType = Class.forName("io.grpc.netty.shaded.io.netty.channel.epoll.EpollSocketChannel").asSubclass(Channel.class);
            return channelType;
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load EpollSocketChannel", e);
        }
    }

    private static Class<? extends Channel> epollDomainSocketChannelType() {
        try {
            Class<Channel> channelType = Class.forName("io.grpc.netty.shaded.io.netty.channel.epoll.EpollDomainSocketChannel").asSubclass(Channel.class);
            return channelType;
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load EpollDomainSocketChannel", e);
        }
    }

    private static Constructor<? extends EventLoopGroup> epollEventLoopGroupConstructor() {
        try {
            return Class.forName("io.grpc.netty.shaded.io.netty.channel.epoll.EpollEventLoopGroup").asSubclass(EventLoopGroup.class).getConstructor(Integer.TYPE, ThreadFactory.class);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load EpollEventLoopGroup", e);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("EpollEventLoopGroup constructor not found", e);
        }
    }

    private static Class<? extends ServerChannel> epollServerChannelType() {
        try {
            Class<ServerChannel> serverSocketChannel = Class.forName("io.grpc.netty.shaded.io.netty.channel.epoll.EpollServerSocketChannel").asSubclass(ServerChannel.class);
            return serverSocketChannel;
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load EpollServerSocketChannel", e);
        }
    }

    private static EventLoopGroup createEpollEventLoopGroup(int parallelism, ThreadFactory threadFactory) {
        Preconditions.checkState(EPOLL_EVENT_LOOP_GROUP_CONSTRUCTOR != null, "Epoll is not available");
        try {
            return EPOLL_EVENT_LOOP_GROUP_CONSTRUCTOR.newInstance(parallelism, threadFactory);
        }
        catch (Exception e) {
            throw new RuntimeException("Cannot create Epoll EventLoopGroup", e);
        }
    }

    private static ChannelFactory<ServerChannel> nioServerChannelFactory() {
        return new ChannelFactory<ServerChannel>(){

            @Override
            public ServerChannel newChannel() {
                return new NioServerSocketChannel();
            }
        };
    }

    @Nullable
    static ChannelOption<Integer> maybeGetTcpUserTimeoutOption() {
        return Utils.getEpollChannelOption("TCP_USER_TIMEOUT");
    }

    @Nullable
    private static <T> ChannelOption<T> getEpollChannelOption(String optionName) {
        if (Utils.isEpollAvailable()) {
            try {
                return (ChannelOption)Class.forName("io.grpc.netty.shaded.io.netty.channel.epoll.EpollChannelOption").getField(optionName).get(null);
            }
            catch (Exception e) {
                throw new RuntimeException("ChannelOption(" + optionName + ") is not available", e);
            }
        }
        return null;
    }

    static InternalChannelz.SocketOptions getSocketOptions(Channel channel) {
        Integer timeoutMillis;
        ChannelConfig config = channel.config();
        InternalChannelz.SocketOptions.Builder b = new InternalChannelz.SocketOptions.Builder();
        Integer lingerSeconds = config.getOption(ChannelOption.SO_LINGER);
        if (lingerSeconds != null) {
            b.setSocketOptionLingerSeconds(lingerSeconds);
        }
        if ((timeoutMillis = config.getOption(ChannelOption.SO_TIMEOUT)) != null) {
            b.setSocketOptionTimeoutMillis(timeoutMillis);
        }
        for (Map.Entry<ChannelOption<?>, Object> opt : config.getOptions().entrySet()) {
            ChannelOption<?> key = opt.getKey();
            if (key.equals(ChannelOption.SO_LINGER) || key.equals(ChannelOption.SO_TIMEOUT)) continue;
            Object value = opt.getValue();
            b.addOption(key.name(), String.valueOf(value));
        }
        NettySocketSupport.NativeSocketOptions nativeOptions = NettySocketSupport.getNativeSocketOptions(channel);
        if (nativeOptions != null) {
            b.setTcpInfo(nativeOptions.tcpInfo);
            for (Map.Entry entry : nativeOptions.otherInfo.entrySet()) {
                b.addOption((String)entry.getKey(), (String)entry.getValue());
            }
        }
        return b.build();
    }

    private Utils() {
    }

    static /* synthetic */ ByteBufAllocator access$000(boolean x0) {
        return Utils.createByteBufAllocator(x0);
    }

    static {
        if (Utils.isEpollAvailable()) {
            DEFAULT_CLIENT_CHANNEL_TYPE = Utils.epollChannelType();
            EPOLL_DOMAIN_CLIENT_CHANNEL_TYPE = Utils.epollDomainSocketChannelType();
            DEFAULT_SERVER_CHANNEL_FACTORY = new ReflectiveChannelFactory<ServerChannel>(Utils.epollServerChannelType());
            EPOLL_EVENT_LOOP_GROUP_CONSTRUCTOR = Utils.epollEventLoopGroupConstructor();
            DEFAULT_BOSS_EVENT_LOOP_GROUP = new DefaultEventLoopGroupResource(1, "grpc-default-boss-ELG", EventLoopGroupType.EPOLL);
            DEFAULT_WORKER_EVENT_LOOP_GROUP = new DefaultEventLoopGroupResource(0, "grpc-default-worker-ELG", EventLoopGroupType.EPOLL);
        } else {
            logger.log(Level.FINE, "Epoll is not available, using Nio.", Utils.getEpollUnavailabilityCause());
            DEFAULT_SERVER_CHANNEL_FACTORY = Utils.nioServerChannelFactory();
            DEFAULT_CLIENT_CHANNEL_TYPE = NioSocketChannel.class;
            EPOLL_DOMAIN_CLIENT_CHANNEL_TYPE = null;
            DEFAULT_BOSS_EVENT_LOOP_GROUP = NIO_BOSS_EVENT_LOOP_GROUP;
            DEFAULT_WORKER_EVENT_LOOP_GROUP = NIO_WORKER_EVENT_LOOP_GROUP;
            EPOLL_EVENT_LOOP_GROUP_CONSTRUCTOR = null;
        }
    }

    private static enum EventLoopGroupType {
        NIO,
        EPOLL;

    }

    static final class FlowControlReader
    implements TransportTracer.FlowControlReader {
        private final Http2Stream connectionStream;
        private final Http2FlowController local;
        private final Http2FlowController remote;

        FlowControlReader(Http2Connection connection) {
            this.local = connection.remote().flowController();
            this.remote = connection.local().flowController();
            this.connectionStream = connection.connectionStream();
        }

        @Override
        public TransportTracer.FlowControlWindows read() {
            return new TransportTracer.FlowControlWindows(this.local.windowSize(this.connectionStream), this.remote.windowSize(this.connectionStream));
        }
    }

    private static final class DefaultEventLoopGroupResource
    implements SharedResourceHolder.Resource<EventLoopGroup> {
        private final String name;
        private final int numEventLoops;
        private final EventLoopGroupType eventLoopGroupType;

        DefaultEventLoopGroupResource(int numEventLoops, String name, EventLoopGroupType eventLoopGroupType) {
            this.name = name;
            this.numEventLoops = numEventLoops == 0 && System.getProperty("io.grpc.netty.shaded.io.netty.eventLoopThreads") == null ? NettyRuntime.availableProcessors() : numEventLoops;
            this.eventLoopGroupType = eventLoopGroupType;
        }

        @Override
        public EventLoopGroup create() {
            DefaultThreadFactory threadFactory = new DefaultThreadFactory(this.name, true);
            switch (this.eventLoopGroupType) {
                case NIO: {
                    return new NioEventLoopGroup(this.numEventLoops, threadFactory);
                }
                case EPOLL: {
                    return Utils.createEpollEventLoopGroup(this.numEventLoops, threadFactory);
                }
            }
            throw new AssertionError((Object)("Unknown/Unsupported EventLoopGroupType: " + (Object)((Object)this.eventLoopGroupType)));
        }

        @Override
        public void close(EventLoopGroup instance) {
            instance.shutdownGracefully(0L, 0L, TimeUnit.SECONDS);
        }

        public String toString() {
            return this.name;
        }
    }

    private static final class ByteBufAllocatorPreferHeapHolder {
        private static final ByteBufAllocator allocator = Utils.access$000(false);

        private ByteBufAllocatorPreferHeapHolder() {
        }
    }

    private static final class ByteBufAllocatorPreferDirectHolder {
        private static final ByteBufAllocator allocator = Utils.access$000(true);

        private ByteBufAllocatorPreferDirectHolder() {
        }
    }
}

