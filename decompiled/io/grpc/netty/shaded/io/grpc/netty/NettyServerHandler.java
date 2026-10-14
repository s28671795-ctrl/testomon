/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Ticker;
import io.grpc.Attributes;
import io.grpc.ChannelLogger;
import io.grpc.InternalChannelz;
import io.grpc.InternalMetadata;
import io.grpc.InternalStatus;
import io.grpc.Metadata;
import io.grpc.ServerStreamTracer;
import io.grpc.Status;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.KeepAliveEnforcer;
import io.grpc.internal.KeepAliveManager;
import io.grpc.internal.LogExceptionRunnable;
import io.grpc.internal.MaxConnectionIdleManager;
import io.grpc.internal.ServerTransportListener;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.TransportTracer;
import io.grpc.netty.shaded.io.grpc.netty.AbstractNettyHandler;
import io.grpc.netty.shaded.io.grpc.netty.CancelServerStreamCommand;
import io.grpc.netty.shaded.io.grpc.netty.ForcefulCloseCommand;
import io.grpc.netty.shaded.io.grpc.netty.GracefulServerCloseCommand;
import io.grpc.netty.shaded.io.grpc.netty.GrpcHttp2HeadersUtils;
import io.grpc.netty.shaded.io.grpc.netty.Http2ControlFrameLimitEncoder;
import io.grpc.netty.shaded.io.grpc.netty.NettyClientHandler;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerStream;
import io.grpc.netty.shaded.io.grpc.netty.SendGrpcFrameCommand;
import io.grpc.netty.shaded.io.grpc.netty.SendResponseHeadersCommand;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.grpc.netty.WriteQueue;
import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufUtil;
import io.grpc.netty.shaded.io.netty.buffer.Unpooled;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelFutureListener;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpHeaderNames;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DecoratingHttp2FrameWriter;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2Connection;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2ConnectionEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2FrameReader;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2FrameWriter;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2Headers;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2LocalFlowController;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Connection;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2ConnectionAdapter;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Error;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Exception;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2FrameAdapter;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2FrameLogger;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2FrameReader;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2FrameWriter;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Headers;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2InboundFrameLogger;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2OutboundFrameLogger;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Settings;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Stream;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2StreamVisitor;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.WeightedFairQueueByteDistributor;
import io.grpc.netty.shaded.io.netty.handler.logging.LogLevel;
import io.grpc.netty.shaded.io.netty.util.AsciiString;
import io.grpc.netty.shaded.io.netty.util.ReferenceCountUtil;
import io.perfmark.PerfMark;
import io.perfmark.Tag;
import io.perfmark.TaskCloseable;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

class NettyServerHandler
extends AbstractNettyHandler {
    private static final Logger logger = Logger.getLogger(NettyServerHandler.class.getName());
    private static final long KEEPALIVE_PING = 57005L;
    @VisibleForTesting
    static final long GRACEFUL_SHUTDOWN_PING = 40715087873L;
    private static final long GRACEFUL_SHUTDOWN_PING_TIMEOUT_NANOS = TimeUnit.SECONDS.toNanos(10L);
    private static final boolean DISABLE_CONNECTION_HEADER_CHECK = Boolean.parseBoolean(System.getProperty("io.grpc.netty.shaded.io.grpc.netty.disableConnectionHeaderCheck", "false"));
    private final Http2Connection.PropertyKey streamKey;
    private final ServerTransportListener transportListener;
    private final int maxMessageSize;
    private final long keepAliveTimeInNanos;
    private final long keepAliveTimeoutInNanos;
    private final long maxConnectionAgeInNanos;
    private final long maxConnectionAgeGraceInNanos;
    private final int maxRstCount;
    private final long maxRstPeriodNanos;
    private final List<? extends ServerStreamTracer.Factory> streamTracerFactories;
    private final TransportTracer transportTracer;
    private final KeepAliveEnforcer keepAliveEnforcer;
    private final Attributes eagAttributes;
    private final Ticker ticker;
    private Attributes negotiationAttributes;
    private InternalChannelz.Security securityInfo;
    private Attributes attributes;
    private Throwable connectionError;
    private boolean teWarningLogged;
    private WriteQueue serverWriteQueue;
    private AsciiString lastKnownAuthority;
    @CheckForNull
    private KeepAliveManager keepAliveManager;
    @CheckForNull
    private MaxConnectionIdleManager maxConnectionIdleManager;
    @CheckForNull
    private ScheduledFuture<?> maxConnectionAgeMonitor;
    @CheckForNull
    private GracefulShutdown gracefulShutdown;
    private int rstCount;
    private long lastRstNanoTime;

    static NettyServerHandler newHandler(ServerTransportListener transportListener, ChannelPromise channelUnused, List<? extends ServerStreamTracer.Factory> streamTracerFactories, TransportTracer transportTracer, int maxStreams, boolean autoFlowControl, int flowControlWindow, int maxHeaderListSize, int maxMessageSize, long keepAliveTimeInNanos, long keepAliveTimeoutInNanos, long maxConnectionIdleInNanos, long maxConnectionAgeInNanos, long maxConnectionAgeGraceInNanos, boolean permitKeepAliveWithoutCalls, long permitKeepAliveTimeInNanos, int maxRstCount, long maxRstPeriodNanos, Attributes eagAttributes) {
        Preconditions.checkArgument(maxHeaderListSize > 0, "maxHeaderListSize must be positive: %s", maxHeaderListSize);
        Http2FrameLogger frameLogger = new Http2FrameLogger(LogLevel.DEBUG, NettyServerHandler.class);
        GrpcHttp2HeadersUtils.GrpcHttp2ServerHeadersDecoder headersDecoder = new GrpcHttp2HeadersUtils.GrpcHttp2ServerHeadersDecoder(maxHeaderListSize);
        Http2InboundFrameLogger frameReader = new Http2InboundFrameLogger(new DefaultHttp2FrameReader(headersDecoder), frameLogger);
        Http2OutboundFrameLogger frameWriter = new Http2OutboundFrameLogger(new DefaultHttp2FrameWriter(), frameLogger);
        return NettyServerHandler.newHandler(channelUnused, frameReader, frameWriter, transportListener, streamTracerFactories, transportTracer, maxStreams, autoFlowControl, flowControlWindow, maxHeaderListSize, maxMessageSize, keepAliveTimeInNanos, keepAliveTimeoutInNanos, maxConnectionIdleInNanos, maxConnectionAgeInNanos, maxConnectionAgeGraceInNanos, permitKeepAliveWithoutCalls, permitKeepAliveTimeInNanos, maxRstCount, maxRstPeriodNanos, eagAttributes, Ticker.systemTicker());
    }

    static NettyServerHandler newHandler(ChannelPromise channelUnused, Http2FrameReader frameReader, Http2FrameWriter frameWriter, ServerTransportListener transportListener, List<? extends ServerStreamTracer.Factory> streamTracerFactories, TransportTracer transportTracer, int maxStreams, boolean autoFlowControl, int flowControlWindow, int maxHeaderListSize, int maxMessageSize, long keepAliveTimeInNanos, long keepAliveTimeoutInNanos, long maxConnectionIdleInNanos, long maxConnectionAgeInNanos, long maxConnectionAgeGraceInNanos, boolean permitKeepAliveWithoutCalls, long permitKeepAliveTimeInNanos, int maxRstCount, long maxRstPeriodNanos, Attributes eagAttributes, Ticker ticker) {
        Preconditions.checkArgument(maxStreams > 0, "maxStreams must be positive: %s", maxStreams);
        Preconditions.checkArgument(flowControlWindow > 0, "flowControlWindow must be positive: %s", flowControlWindow);
        Preconditions.checkArgument(maxHeaderListSize > 0, "maxHeaderListSize must be positive: %s", maxHeaderListSize);
        Preconditions.checkArgument(maxMessageSize > 0, "maxMessageSize must be positive: %s", maxMessageSize);
        DefaultHttp2Connection connection = new DefaultHttp2Connection(true);
        WeightedFairQueueByteDistributor dist = new WeightedFairQueueByteDistributor(connection);
        dist.allocationQuantum(16384);
        DefaultHttp2RemoteFlowController controller = new DefaultHttp2RemoteFlowController((Http2Connection)connection, dist);
        connection.remote().flowController(controller);
        KeepAliveEnforcer keepAliveEnforcer = new KeepAliveEnforcer(permitKeepAliveWithoutCalls, permitKeepAliveTimeInNanos, TimeUnit.NANOSECONDS);
        connection.local().flowController(new DefaultHttp2LocalFlowController(connection, 0.5f, true));
        frameWriter = new WriteMonitoringFrameWriter(frameWriter, keepAliveEnforcer);
        Http2ConnectionEncoder encoder = new DefaultHttp2ConnectionEncoder(connection, frameWriter);
        encoder = new Http2ControlFrameLimitEncoder(encoder, 10000);
        DefaultHttp2ConnectionDecoder decoder = new DefaultHttp2ConnectionDecoder(connection, encoder, frameReader);
        Http2Settings settings = new Http2Settings();
        settings.initialWindowSize(flowControlWindow);
        settings.maxConcurrentStreams(maxStreams);
        settings.maxHeaderListSize(maxHeaderListSize);
        if (ticker == null) {
            ticker = Ticker.systemTicker();
        }
        return new NettyServerHandler(channelUnused, connection, transportListener, streamTracerFactories, transportTracer, decoder, encoder, settings, maxMessageSize, keepAliveTimeInNanos, keepAliveTimeoutInNanos, maxConnectionIdleInNanos, maxConnectionAgeInNanos, maxConnectionAgeGraceInNanos, keepAliveEnforcer, autoFlowControl, maxRstCount, maxRstPeriodNanos, eagAttributes, ticker);
    }

    private NettyServerHandler(ChannelPromise channelUnused, final Http2Connection connection, ServerTransportListener transportListener, List<? extends ServerStreamTracer.Factory> streamTracerFactories, TransportTracer transportTracer, Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder, Http2Settings settings, int maxMessageSize, long keepAliveTimeInNanos, long keepAliveTimeoutInNanos, long maxConnectionIdleInNanos, long maxConnectionAgeInNanos, long maxConnectionAgeGraceInNanos, final KeepAliveEnforcer keepAliveEnforcer, boolean autoFlowControl, int maxRstCount, long maxRstPeriodNanos, Attributes eagAttributes, Ticker ticker) {
        super(channelUnused, decoder, encoder, settings, new ServerChannelLogger(), autoFlowControl, null, ticker);
        final MaxConnectionIdleManager maxConnectionIdleManager = maxConnectionIdleInNanos == Long.MAX_VALUE ? null : new MaxConnectionIdleManager(maxConnectionIdleInNanos);
        connection.addListener(new Http2ConnectionAdapter(){

            @Override
            public void onStreamActive(Http2Stream stream) {
                if (connection.numActiveStreams() == 1) {
                    keepAliveEnforcer.onTransportActive();
                    if (maxConnectionIdleManager != null) {
                        maxConnectionIdleManager.onTransportActive();
                    }
                }
            }

            @Override
            public void onStreamClosed(Http2Stream stream) {
                if (connection.numActiveStreams() == 0) {
                    keepAliveEnforcer.onTransportIdle();
                    if (maxConnectionIdleManager != null) {
                        maxConnectionIdleManager.onTransportIdle();
                    }
                }
            }
        });
        Preconditions.checkArgument(maxMessageSize >= 0, "maxMessageSize must be non-negative: %s", maxMessageSize);
        this.maxMessageSize = maxMessageSize;
        this.keepAliveTimeInNanos = keepAliveTimeInNanos;
        this.keepAliveTimeoutInNanos = keepAliveTimeoutInNanos;
        this.maxConnectionIdleManager = maxConnectionIdleManager;
        this.maxConnectionAgeInNanos = maxConnectionAgeInNanos;
        this.maxConnectionAgeGraceInNanos = maxConnectionAgeGraceInNanos;
        this.keepAliveEnforcer = Preconditions.checkNotNull(keepAliveEnforcer, "keepAliveEnforcer");
        this.maxRstCount = maxRstCount;
        this.maxRstPeriodNanos = maxRstPeriodNanos;
        this.eagAttributes = Preconditions.checkNotNull(eagAttributes, "eagAttributes");
        this.ticker = Preconditions.checkNotNull(ticker, "ticker");
        this.lastRstNanoTime = ticker.read();
        this.streamKey = encoder.connection().newKey();
        this.transportListener = Preconditions.checkNotNull(transportListener, "transportListener");
        this.streamTracerFactories = Preconditions.checkNotNull(streamTracerFactories, "streamTracerFactories");
        this.transportTracer = Preconditions.checkNotNull(transportTracer, "transportTracer");
        this.decoder().frameListener(new FrameListener());
    }

    @Nullable
    Throwable connectionError() {
        return this.connectionError;
    }

    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
        this.serverWriteQueue = new WriteQueue(ctx.channel());
        if (this.maxConnectionAgeInNanos != Long.MAX_VALUE) {
            this.maxConnectionAgeMonitor = ctx.executor().schedule(new LogExceptionRunnable(new Runnable(){

                @Override
                public void run() {
                    if (NettyServerHandler.this.gracefulShutdown == null) {
                        NettyServerHandler.this.gracefulShutdown = new GracefulShutdown("max_age", NettyServerHandler.this.maxConnectionAgeGraceInNanos);
                        NettyServerHandler.this.gracefulShutdown.start(ctx);
                        ctx.flush();
                    }
                }
            }), this.maxConnectionAgeInNanos, TimeUnit.NANOSECONDS);
        }
        if (this.maxConnectionIdleManager != null) {
            this.maxConnectionIdleManager.start(new Runnable(){

                @Override
                public void run() {
                    if (NettyServerHandler.this.gracefulShutdown == null) {
                        NettyServerHandler.this.gracefulShutdown = new GracefulShutdown("max_idle", null);
                        NettyServerHandler.this.gracefulShutdown.start(ctx);
                        ctx.flush();
                    }
                }
            }, ctx.executor());
        }
        if (this.keepAliveTimeInNanos != Long.MAX_VALUE) {
            this.keepAliveManager = new KeepAliveManager(new KeepAlivePinger(ctx), ctx.executor(), this.keepAliveTimeInNanos, this.keepAliveTimeoutInNanos, true);
            this.keepAliveManager.onTransportStarted();
        }
        assert (this.encoder().connection().equals(this.decoder().connection()));
        this.transportTracer.setFlowControlWindowReader(new Utils.FlowControlReader(this.encoder().connection()));
        super.handlerAdded(ctx);
    }

    private void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers) throws Http2Exception {
        try {
            if (!DISABLE_CONNECTION_HEADER_CHECK && headers.contains(HttpHeaderNames.CONNECTION)) {
                this.resetStream(ctx, streamId, Http2Error.PROTOCOL_ERROR.code(), ctx.newPromise());
                return;
            }
            if (headers.authority() == null) {
                List hosts = headers.getAll(HttpHeaderNames.HOST);
                if (hosts.size() > 1) {
                    this.respondWithHttpError(ctx, streamId, 400, Status.Code.INTERNAL, "Multiple host headers");
                    return;
                }
                if (!hosts.isEmpty()) {
                    headers.add(Http2Headers.PseudoHeaderName.AUTHORITY.value(), (CharSequence)hosts.get(0));
                }
            }
            headers.remove(HttpHeaderNames.HOST);
            CharSequence path = headers.path();
            if (path == null) {
                this.respondWithHttpError(ctx, streamId, 404, Status.Code.UNIMPLEMENTED, "Expected path but is missing");
                return;
            }
            if (path.charAt(0) != '/') {
                this.respondWithHttpError(ctx, streamId, 404, Status.Code.UNIMPLEMENTED, String.format("Expected path to start with /: %s", path));
                return;
            }
            String method2 = path.subSequence(1, path.length()).toString();
            CharSequence contentType = (CharSequence)headers.get(Utils.CONTENT_TYPE_HEADER);
            if (contentType == null) {
                this.respondWithHttpError(ctx, streamId, 415, Status.Code.INTERNAL, "Content-Type is missing from the request");
                return;
            }
            String contentTypeString = contentType.toString();
            if (!GrpcUtil.isGrpcContentType(contentTypeString)) {
                this.respondWithHttpError(ctx, streamId, 415, Status.Code.INTERNAL, String.format("Content-Type '%s' is not supported", contentTypeString));
                return;
            }
            if (!Utils.HTTP_METHOD.contentEquals(headers.method())) {
                this.respondWithHttpError(ctx, streamId, 405, Status.Code.INTERNAL, String.format("Method '%s' is not supported", headers.method()));
                return;
            }
            if (!this.teWarningLogged && !Utils.TE_TRAILERS.contentEquals((CharSequence)headers.get(Utils.TE_HEADER))) {
                logger.warning(String.format("Expected header TE: %s, but %s is received. This means some intermediate proxy may not support trailers", Utils.TE_TRAILERS, headers.get(Utils.TE_HEADER)));
                this.teWarningLogged = true;
            }
            Http2Stream http2Stream = this.requireHttp2Stream(streamId);
            Metadata metadata = Utils.convertHeaders(headers);
            StatsTraceContext statsTraceCtx = StatsTraceContext.newServerContext(this.streamTracerFactories, method2, metadata);
            NettyServerStream.TransportState state = new NettyServerStream.TransportState(this, ctx.channel().eventLoop(), http2Stream, this.maxMessageSize, statsTraceCtx, this.transportTracer, method2);
            try (TaskCloseable ignore = PerfMark.traceTask("NettyServerHandler.onHeadersRead");){
                PerfMark.attachTag(state.tag());
                String authority = this.getOrUpdateAuthority((AsciiString)headers.authority());
                NettyServerStream stream = new NettyServerStream(ctx.channel(), state, this.attributes, authority, statsTraceCtx);
                this.transportListener.streamCreated(stream, method2, metadata);
                state.onStreamAllocated();
                http2Stream.setProperty(this.streamKey, state);
            }
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "Exception in onHeadersRead()", e);
            throw this.newStreamException(streamId, e);
        }
    }

    private String getOrUpdateAuthority(AsciiString authority) {
        if (authority == null) {
            return null;
        }
        if (!authority.equals(this.lastKnownAuthority)) {
            this.lastKnownAuthority = authority;
        }
        return this.lastKnownAuthority.toString();
    }

    private void onDataRead(int streamId, ByteBuf data, int padding, boolean endOfStream) throws Http2Exception {
        this.flowControlPing().onDataRead(data.readableBytes(), padding);
        try {
            NettyServerStream.TransportState stream = this.serverStream(this.requireHttp2Stream(streamId));
            if (stream == null) {
                return;
            }
            try (TaskCloseable ignore = PerfMark.traceTask("NettyServerHandler.onDataRead");){
                PerfMark.attachTag(stream.tag());
                stream.inboundDataReceived(data, endOfStream);
            }
        }
        catch (Throwable e) {
            logger.log(Level.WARNING, "Exception in onDataRead()", e);
            throw this.newStreamException(streamId, e);
        }
    }

    private void onRstStreamRead(int streamId, long errorCode) throws Http2Exception {
        block12: {
            if (this.maxRstCount > 0) {
                long now = this.ticker.read();
                if (now - this.lastRstNanoTime > this.maxRstPeriodNanos) {
                    this.lastRstNanoTime = now;
                    this.rstCount = 1;
                } else {
                    ++this.rstCount;
                    if (this.rstCount > this.maxRstCount) {
                        throw new Http2Exception(Http2Error.ENHANCE_YOUR_CALM, "too_many_rststreams"){

                            @Override
                            public Throwable fillInStackTrace() {
                                return this;
                            }
                        };
                    }
                }
            }
            try {
                NettyServerStream.TransportState stream = this.serverStream(this.connection().stream(streamId));
                if (stream == null) break block12;
                try (TaskCloseable ignore = PerfMark.traceTask("NettyServerHandler.onRstStreamRead");){
                    PerfMark.attachTag(stream.tag());
                    stream.transportReportStatus(Status.CANCELLED.withDescription("RST_STREAM received for code " + errorCode));
                }
            }
            catch (Throwable e) {
                logger.log(Level.WARNING, "Exception in onRstStreamRead()", e);
                throw this.newStreamException(streamId, e);
            }
        }
    }

    @Override
    protected void onConnectionError(ChannelHandlerContext ctx, boolean outbound, Throwable cause, Http2Exception http2Ex) {
        logger.log(Level.FINE, "Connection Error", cause);
        this.connectionError = cause;
        super.onConnectionError(ctx, outbound, cause, http2Ex);
    }

    @Override
    protected void onStreamError(ChannelHandlerContext ctx, boolean outbound, Throwable cause, Http2Exception.StreamException http2Ex) {
        NettyServerStream.TransportState serverStream = this.serverStream(this.connection().stream(Http2Exception.streamId(http2Ex)));
        Level level = Level.WARNING;
        if (serverStream == null && http2Ex.error() == Http2Error.STREAM_CLOSED) {
            level = Level.FINE;
        }
        logger.log(level, "Stream Error", cause);
        Tag tag = serverStream != null ? serverStream.tag() : PerfMark.createTag();
        try (TaskCloseable ignore = PerfMark.traceTask("NettyServerHandler.onStreamError");){
            PerfMark.attachTag(tag);
            if (serverStream != null) {
                serverStream.transportReportStatus(Utils.statusFromThrowable(cause));
            }
            super.onStreamError(ctx, outbound, cause, http2Ex);
        }
    }

    @Override
    public void handleProtocolNegotiationCompleted(Attributes attrs, InternalChannelz.Security securityInfo) {
        this.negotiationAttributes = attrs;
        this.securityInfo = securityInfo;
        super.handleProtocolNegotiationCompleted(attrs, securityInfo);
        NettyClientHandler.writeBufferingAndRemove(this.ctx().channel());
    }

    @Override
    public Attributes getEagAttributes() {
        return this.eagAttributes;
    }

    InternalChannelz.Security getSecurityInfo() {
        return this.securityInfo;
    }

    @VisibleForTesting
    KeepAliveManager getKeepAliveManagerForTest() {
        return this.keepAliveManager;
    }

    @VisibleForTesting
    void setKeepAliveManagerForTest(KeepAliveManager keepAliveManager) {
        this.keepAliveManager = keepAliveManager;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        try {
            if (this.keepAliveManager != null) {
                this.keepAliveManager.onTransportTermination();
            }
            if (this.maxConnectionIdleManager != null) {
                this.maxConnectionIdleManager.onTransportTermination();
            }
            if (this.maxConnectionAgeMonitor != null) {
                this.maxConnectionAgeMonitor.cancel(false);
            }
            final Status status = Status.UNAVAILABLE.withDescription("connection terminated for unknown reason");
            this.connection().forEachActiveStream(new Http2StreamVisitor(){

                @Override
                public boolean visit(Http2Stream stream) throws Http2Exception {
                    NettyServerStream.TransportState serverStream = NettyServerHandler.this.serverStream(stream);
                    if (serverStream != null) {
                        serverStream.transportReportStatus(status);
                    }
                    return true;
                }
            });
        }
        finally {
            super.channelInactive(ctx);
        }
    }

    WriteQueue getWriteQueue() {
        return this.serverWriteQueue;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof SendGrpcFrameCommand) {
            this.sendGrpcFrame(ctx, (SendGrpcFrameCommand)msg, promise);
        } else if (msg instanceof SendResponseHeadersCommand) {
            this.sendResponseHeaders(ctx, (SendResponseHeadersCommand)msg, promise);
        } else if (msg instanceof CancelServerStreamCommand) {
            this.cancelStream(ctx, (CancelServerStreamCommand)msg, promise);
        } else if (msg instanceof GracefulServerCloseCommand) {
            this.gracefulClose(ctx, (GracefulServerCloseCommand)msg, promise);
        } else if (msg instanceof ForcefulCloseCommand) {
            this.forcefulClose(ctx, (ForcefulCloseCommand)msg, promise);
        } else {
            AssertionError e = new AssertionError((Object)("Write called for unexpected type: " + msg.getClass().getName()));
            ReferenceCountUtil.release(msg);
            promise.setFailure((Throwable)((Object)e));
            throw e;
        }
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        this.gracefulClose(ctx, new GracefulServerCloseCommand("app_requested"), promise);
        ctx.flush();
    }

    void returnProcessedBytes(Http2Stream http2Stream, int bytes) {
        try {
            this.decoder().flowController().consumeBytes(http2Stream, bytes);
        }
        catch (Http2Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void closeStreamWhenDone(ChannelPromise promise, final Http2Stream stream) {
        promise.addListener(new ChannelFutureListener(){

            @Override
            public void operationComplete(ChannelFuture future) {
                NettyServerHandler.this.serverStream(stream).complete();
            }
        });
    }

    private static void streamGone(int streamId, ChannelPromise promise) {
        promise.setFailure(new IllegalStateException("attempting to write to stream " + streamId + " that no longer exists"){

            @Override
            public synchronized Throwable fillInStackTrace() {
                return this;
            }
        });
    }

    private void sendGrpcFrame(ChannelHandlerContext ctx, SendGrpcFrameCommand cmd, ChannelPromise promise) throws Http2Exception {
        try (TaskCloseable ignore = PerfMark.traceTask("NettyServerHandler.sendGrpcFrame");){
            PerfMark.attachTag(cmd.stream().tag());
            PerfMark.linkIn(cmd.getLink());
            int streamId = cmd.stream().id();
            Http2Stream stream = this.connection().stream(streamId);
            if (stream == null) {
                cmd.release();
                NettyServerHandler.streamGone(streamId, promise);
                return;
            }
            if (cmd.endStream()) {
                this.closeStreamWhenDone(promise, stream);
            }
            this.encoder().writeData(ctx, streamId, cmd.content(), 0, cmd.endStream(), promise);
        }
    }

    private void sendResponseHeaders(ChannelHandlerContext ctx, SendResponseHeadersCommand cmd, ChannelPromise promise) throws Http2Exception {
        try (TaskCloseable ignore = PerfMark.traceTask("NettyServerHandler.sendResponseHeaders");){
            PerfMark.attachTag(cmd.stream().tag());
            PerfMark.linkIn(cmd.getLink());
            int streamId = cmd.stream().id();
            Http2Stream stream = this.connection().stream(streamId);
            if (stream == null) {
                NettyServerHandler.streamGone(streamId, promise);
                return;
            }
            if (cmd.endOfStream()) {
                this.closeStreamWhenDone(promise, stream);
            }
            this.encoder().writeHeaders(ctx, streamId, cmd.headers(), 0, cmd.endOfStream(), promise);
        }
    }

    private void cancelStream(ChannelHandlerContext ctx, CancelServerStreamCommand cmd, ChannelPromise promise) {
        try (TaskCloseable ignore = PerfMark.traceTask("NettyServerHandler.cancelStream");){
            PerfMark.attachTag(cmd.stream().tag());
            PerfMark.linkIn(cmd.getLink());
            cmd.stream().transportReportStatus(cmd.reason());
            if (NettyServerHandler.shouldCloseStreamWithHeaders(cmd, this.connection())) {
                Metadata md = new Metadata();
                md.put(InternalStatus.CODE_KEY, cmd.reason());
                if (cmd.reason().getDescription() != null) {
                    md.put(InternalStatus.MESSAGE_KEY, cmd.reason().getDescription());
                }
                Http2Headers headers = Utils.convertServerHeaders(md);
                this.encoder().writeHeaders(ctx, cmd.stream().id(), headers, 0, true, promise);
            } else {
                this.encoder().writeRstStream(ctx, cmd.stream().id(), Http2Error.CANCEL.code(), promise);
            }
        }
    }

    private static boolean shouldCloseStreamWithHeaders(CancelServerStreamCommand cmd, Http2Connection conn) {
        if (!cmd.wantsHeaders()) {
            return false;
        }
        Http2Stream stream = conn.stream(cmd.stream().id());
        return stream != null && !stream.isHeadersSent();
    }

    private void gracefulClose(ChannelHandlerContext ctx, GracefulServerCloseCommand msg, ChannelPromise promise) throws Exception {
        if (this.gracefulShutdown == null) {
            Long graceTimeInNanos = null;
            if (msg.getGraceTimeUnit() != null) {
                graceTimeInNanos = msg.getGraceTimeUnit().toNanos(msg.getGraceTime());
            }
            this.gracefulShutdown = new GracefulShutdown(msg.getGoAwayDebugString(), graceTimeInNanos);
            this.gracefulShutdown.start(ctx);
        }
        promise.setSuccess();
    }

    private void forcefulClose(final ChannelHandlerContext ctx, final ForcefulCloseCommand msg, ChannelPromise promise) throws Exception {
        super.close(ctx, promise);
        this.connection().forEachActiveStream(new Http2StreamVisitor(){

            @Override
            public boolean visit(Http2Stream stream) throws Http2Exception {
                NettyServerStream.TransportState serverStream = NettyServerHandler.this.serverStream(stream);
                if (serverStream != null) {
                    try (TaskCloseable ignore = PerfMark.traceTask("NettyServerHandler.forcefulClose");){
                        PerfMark.attachTag(serverStream.tag());
                        PerfMark.linkIn(msg.getLink());
                        serverStream.transportReportStatus(msg.getStatus());
                        NettyServerHandler.this.resetStream(ctx, stream.id(), Http2Error.CANCEL.code(), ctx.newPromise());
                    }
                }
                stream.close();
                return true;
            }
        });
    }

    private void respondWithHttpError(ChannelHandlerContext ctx, int streamId, int code, Status.Code statusCode, String msg) {
        Metadata metadata = new Metadata();
        metadata.put(InternalStatus.CODE_KEY, statusCode.toStatus());
        metadata.put(InternalStatus.MESSAGE_KEY, msg);
        byte[][] serialized = InternalMetadata.serialize(metadata);
        Http2Headers headers = (Http2Headers)new DefaultHttp2Headers(true, serialized.length / 2).status("" + code).set(Utils.CONTENT_TYPE_HEADER, "text/plain; charset=utf-8");
        for (int i = 0; i < serialized.length; i += 2) {
            headers.add(new AsciiString(serialized[i], false), new AsciiString(serialized[i + 1], false));
        }
        this.encoder().writeHeaders(ctx, streamId, headers, 0, false, ctx.newPromise());
        ByteBuf msgBuf = ByteBufUtil.writeUtf8(ctx.alloc(), (CharSequence)msg);
        this.encoder().writeData(ctx, streamId, msgBuf, 0, true, ctx.newPromise());
    }

    private Http2Stream requireHttp2Stream(int streamId) {
        Http2Stream stream = this.connection().stream(streamId);
        if (stream == null) {
            throw new AssertionError((Object)("Stream does not exist: " + streamId));
        }
        return stream;
    }

    private NettyServerStream.TransportState serverStream(Http2Stream stream) {
        return stream == null ? null : (NettyServerStream.TransportState)stream.getProperty(this.streamKey);
    }

    private Http2Exception newStreamException(int streamId, Throwable cause) {
        return Http2Exception.streamError(streamId, Http2Error.INTERNAL_ERROR, cause, Strings.nullToEmpty(cause.getMessage()), new Object[0]);
    }

    private static Level toJavaLogLevel(ChannelLogger.ChannelLogLevel level) {
        switch (level) {
            case ERROR: {
                return Level.FINE;
            }
            case WARNING: {
                return Level.FINER;
            }
        }
        return Level.FINEST;
    }

    private static class ServerChannelLogger
    extends ChannelLogger {
        private static final Logger log = Logger.getLogger(ChannelLogger.class.getName());

        private ServerChannelLogger() {
        }

        @Override
        public void log(ChannelLogger.ChannelLogLevel level, String message) {
            log.log(NettyServerHandler.toJavaLogLevel(level), message);
        }

        @Override
        public void log(ChannelLogger.ChannelLogLevel level, String messageFormat, Object ... args2) {
            this.log(level, MessageFormat.format(messageFormat, args2));
        }
    }

    private static class WriteMonitoringFrameWriter
    extends DecoratingHttp2FrameWriter {
        private final KeepAliveEnforcer keepAliveEnforcer;

        public WriteMonitoringFrameWriter(Http2FrameWriter delegate, KeepAliveEnforcer keepAliveEnforcer) {
            super(delegate);
            this.keepAliveEnforcer = keepAliveEnforcer;
        }

        @Override
        public ChannelFuture writeData(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endStream, ChannelPromise promise) {
            this.keepAliveEnforcer.resetCounters();
            return super.writeData(ctx, streamId, data, padding, endStream, promise);
        }

        @Override
        public ChannelFuture writeHeaders(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endStream, ChannelPromise promise) {
            this.keepAliveEnforcer.resetCounters();
            return super.writeHeaders(ctx, streamId, headers, padding, endStream, promise);
        }

        @Override
        public ChannelFuture writeHeaders(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endStream, ChannelPromise promise) {
            this.keepAliveEnforcer.resetCounters();
            return super.writeHeaders(ctx, streamId, headers, streamDependency, weight, exclusive, padding, endStream, promise);
        }
    }

    private final class GracefulShutdown {
        String goAwayMessage;
        @CheckForNull
        Long graceTimeInNanos;
        boolean pingAckedOrTimeout;
        Future<?> pingFuture;

        GracefulShutdown(@Nullable String goAwayMessage, Long graceTimeInNanos) {
            this.goAwayMessage = goAwayMessage;
            this.graceTimeInNanos = graceTimeInNanos;
        }

        void start(final ChannelHandlerContext ctx) {
            NettyServerHandler.this.goAway(ctx, Integer.MAX_VALUE, Http2Error.NO_ERROR.code(), ByteBufUtil.writeAscii(ctx.alloc(), (CharSequence)this.goAwayMessage), ctx.newPromise());
            this.pingFuture = ctx.executor().schedule(new Runnable(){

                @Override
                public void run() {
                    GracefulShutdown.this.secondGoAwayAndClose(ctx);
                }
            }, GRACEFUL_SHUTDOWN_PING_TIMEOUT_NANOS, TimeUnit.NANOSECONDS);
            NettyServerHandler.this.encoder().writePing(ctx, false, 40715087873L, ctx.newPromise());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void secondGoAwayAndClose(ChannelHandlerContext ctx) {
            if (this.pingAckedOrTimeout) {
                return;
            }
            this.pingAckedOrTimeout = true;
            Preconditions.checkNotNull(this.pingFuture, "pingFuture");
            this.pingFuture.cancel(false);
            NettyServerHandler.this.goAway(ctx, NettyServerHandler.this.connection().remote().lastStreamCreated(), Http2Error.NO_ERROR.code(), ByteBufUtil.writeAscii(ctx.alloc(), (CharSequence)this.goAwayMessage), ctx.newPromise());
            long savedGracefulShutdownTimeMillis = NettyServerHandler.this.gracefulShutdownTimeoutMillis();
            long overriddenGraceTime = this.graceTimeOverrideMillis(savedGracefulShutdownTimeMillis);
            try {
                NettyServerHandler.this.gracefulShutdownTimeoutMillis(overriddenGraceTime);
                NettyServerHandler.super.close(ctx, ctx.newPromise());
            }
            catch (Exception e) {
                NettyServerHandler.this.onError(ctx, true, e);
            }
            finally {
                NettyServerHandler.this.gracefulShutdownTimeoutMillis(savedGracefulShutdownTimeMillis);
            }
        }

        private long graceTimeOverrideMillis(long originalMillis) {
            if (this.graceTimeInNanos == null) {
                return originalMillis;
            }
            if (this.graceTimeInNanos == Long.MAX_VALUE) {
                return -1L;
            }
            return TimeUnit.NANOSECONDS.toMillis(this.graceTimeInNanos);
        }
    }

    private final class KeepAlivePinger
    implements KeepAliveManager.KeepAlivePinger {
        final ChannelHandlerContext ctx;

        KeepAlivePinger(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void ping() {
            ChannelFuture pingFuture = NettyServerHandler.this.encoder().writePing(this.ctx, false, 57005L, this.ctx.newPromise());
            this.ctx.flush();
            pingFuture.addListener(new ChannelFutureListener(){

                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        NettyServerHandler.this.transportTracer.reportKeepAliveSent();
                    }
                }
            });
        }

        @Override
        public void onPingTimeout() {
            try {
                NettyServerHandler.this.forcefulClose(this.ctx, new ForcefulCloseCommand(Status.UNAVAILABLE.withDescription("Keepalive failed. The connection is likely gone")), this.ctx.newPromise());
            }
            catch (Exception ex) {
                try {
                    NettyServerHandler.this.exceptionCaught(this.ctx, ex);
                }
                catch (Exception ex2) {
                    logger.log(Level.WARNING, "Exception while propagating exception", ex2);
                    logger.log(Level.WARNING, "Original failure", ex);
                }
            }
        }
    }

    private class FrameListener
    extends Http2FrameAdapter {
        private boolean firstSettings = true;

        private FrameListener() {
        }

        @Override
        public void onSettingsRead(ChannelHandlerContext ctx, Http2Settings settings) {
            if (this.firstSettings) {
                this.firstSettings = false;
                NettyServerHandler.this.attributes = NettyServerHandler.this.transportListener.transportReady(NettyServerHandler.this.negotiationAttributes);
            }
        }

        @Override
        public int onDataRead(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endOfStream) throws Http2Exception {
            if (NettyServerHandler.this.keepAliveManager != null) {
                NettyServerHandler.this.keepAliveManager.onDataReceived();
            }
            NettyServerHandler.this.onDataRead(streamId, data, padding, endOfStream);
            return padding;
        }

        @Override
        public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endStream) throws Http2Exception {
            if (NettyServerHandler.this.keepAliveManager != null) {
                NettyServerHandler.this.keepAliveManager.onDataReceived();
            }
            NettyServerHandler.this.onHeadersRead(ctx, streamId, headers);
            if (endStream) {
                NettyServerHandler.this.onDataRead(streamId, Unpooled.EMPTY_BUFFER, 0, endStream);
            }
        }

        @Override
        public void onRstStreamRead(ChannelHandlerContext ctx, int streamId, long errorCode) throws Http2Exception {
            if (NettyServerHandler.this.keepAliveManager != null) {
                NettyServerHandler.this.keepAliveManager.onDataReceived();
            }
            NettyServerHandler.this.onRstStreamRead(streamId, errorCode);
        }

        @Override
        public void onPingRead(ChannelHandlerContext ctx, long data) throws Http2Exception {
            if (NettyServerHandler.this.keepAliveManager != null) {
                NettyServerHandler.this.keepAliveManager.onDataReceived();
            }
            if (!NettyServerHandler.this.keepAliveEnforcer.pingAcceptable()) {
                ByteBuf debugData = ByteBufUtil.writeAscii(ctx.alloc(), (CharSequence)"too_many_pings");
                NettyServerHandler.this.goAway(ctx, NettyServerHandler.this.connection().remote().lastStreamCreated(), Http2Error.ENHANCE_YOUR_CALM.code(), debugData, ctx.newPromise());
                Status status = Status.RESOURCE_EXHAUSTED.withDescription("Too many pings from client");
                try {
                    NettyServerHandler.this.forcefulClose(ctx, new ForcefulCloseCommand(status), ctx.newPromise());
                }
                catch (Exception ex) {
                    NettyServerHandler.this.onError(ctx, true, ex);
                }
            }
        }

        @Override
        public void onPingAckRead(ChannelHandlerContext ctx, long data) throws Http2Exception {
            if (NettyServerHandler.this.keepAliveManager != null) {
                NettyServerHandler.this.keepAliveManager.onDataReceived();
            }
            if (data == NettyServerHandler.this.flowControlPing().payload()) {
                NettyServerHandler.this.flowControlPing().updateWindow();
                logger.log(Level.FINE, "Window: {0}", NettyServerHandler.this.decoder().flowController().initialWindowSize(NettyServerHandler.this.connection().connectionStream()));
            } else if (data == 40715087873L) {
                if (NettyServerHandler.this.gracefulShutdown == null) {
                    logger.warning("Received GRACEFUL_SHUTDOWN_PING Ack but gracefulShutdown is null");
                } else {
                    NettyServerHandler.this.gracefulShutdown.secondGoAwayAndClose(ctx);
                }
            } else if (data != 57005L) {
                logger.warning("Received unexpected ping ack. No ping outstanding");
            }
        }
    }
}

