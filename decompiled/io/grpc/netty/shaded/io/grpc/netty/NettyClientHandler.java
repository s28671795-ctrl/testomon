/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.base.Ticker;
import io.grpc.Attributes;
import io.grpc.ChannelLogger;
import io.grpc.InternalChannelz;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.ClientTransport;
import io.grpc.internal.GrpcAttributes;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.Http2Ping;
import io.grpc.internal.InUseStateAggregator;
import io.grpc.internal.KeepAliveManager;
import io.grpc.internal.TransportTracer;
import io.grpc.netty.shaded.io.grpc.netty.AbstractNettyHandler;
import io.grpc.netty.shaded.io.grpc.netty.CancelClientStreamCommand;
import io.grpc.netty.shaded.io.grpc.netty.ClientTransportLifecycleManager;
import io.grpc.netty.shaded.io.grpc.netty.CreateStreamCommand;
import io.grpc.netty.shaded.io.grpc.netty.ForcefulCloseCommand;
import io.grpc.netty.shaded.io.grpc.netty.GracefulCloseCommand;
import io.grpc.netty.shaded.io.grpc.netty.GrpcHttp2HeadersUtils;
import io.grpc.netty.shaded.io.grpc.netty.NettyClientStream;
import io.grpc.netty.shaded.io.grpc.netty.SendGrpcFrameCommand;
import io.grpc.netty.shaded.io.grpc.netty.SendPingCommand;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.grpc.netty.WriteBufferingAndExceptionHandler;
import io.grpc.netty.shaded.io.grpc.netty.WriteQueue;
import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufUtil;
import io.grpc.netty.shaded.io.netty.buffer.Unpooled;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelFutureListener;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DecoratingHttp2FrameWriter;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2Connection;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2ConnectionEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2FrameReader;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2FrameWriter;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2HeadersEncoder;
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
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2HeadersEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2InboundFrameLogger;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2OutboundFrameLogger;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Settings;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Stream;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2StreamVisitor;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.StreamBufferingEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.WeightedFairQueueByteDistributor;
import io.grpc.netty.shaded.io.netty.handler.logging.LogLevel;
import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;
import io.perfmark.PerfMark;
import io.perfmark.Tag;
import io.perfmark.TaskCloseable;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

class NettyClientHandler
extends AbstractNettyHandler {
    private static final Logger logger = Logger.getLogger(NettyClientHandler.class.getName());
    static final Object NOOP_MESSAGE = new Object();
    private static final Status EXHAUSTED_STREAMS_STATUS = Status.UNAVAILABLE.withDescription("Stream IDs have been exhausted");
    private static final long USER_PING_PAYLOAD = 1111L;
    private final Http2Connection.PropertyKey streamKey;
    private final ClientTransportLifecycleManager lifecycleManager;
    private final KeepAliveManager keepAliveManager;
    private final Supplier<Stopwatch> stopwatchFactory;
    private final TransportTracer transportTracer;
    private final Attributes eagAttributes;
    private final String authority;
    private final InUseStateAggregator<Http2Stream> inUseState = new InUseStateAggregator<Http2Stream>(){

        @Override
        protected void handleInUse() {
            NettyClientHandler.this.lifecycleManager.notifyInUse(true);
        }

        @Override
        protected void handleNotInUse() {
            NettyClientHandler.this.lifecycleManager.notifyInUse(false);
        }
    };
    private WriteQueue clientWriteQueue;
    private Http2Ping ping;
    private Attributes attributes;
    private InternalChannelz.Security securityInfo;
    private Status abruptGoAwayStatus;
    private Status channelInactiveReason;

    static NettyClientHandler newHandler(ClientTransportLifecycleManager lifecycleManager, @Nullable KeepAliveManager keepAliveManager, boolean autoFlowControl, int flowControlWindow, int maxHeaderListSize, Supplier<Stopwatch> stopwatchFactory, Runnable tooManyPingsRunnable, TransportTracer transportTracer, Attributes eagAttributes, String authority, ChannelLogger negotiationLogger, Ticker ticker) {
        Preconditions.checkArgument(maxHeaderListSize > 0, "maxHeaderListSize must be positive");
        GrpcHttp2HeadersUtils.GrpcHttp2ClientHeadersDecoder headersDecoder = new GrpcHttp2HeadersUtils.GrpcHttp2ClientHeadersDecoder(maxHeaderListSize);
        DefaultHttp2FrameReader frameReader = new DefaultHttp2FrameReader(headersDecoder);
        DefaultHttp2HeadersEncoder encoder = new DefaultHttp2HeadersEncoder(Http2HeadersEncoder.NEVER_SENSITIVE, false, 16, Integer.MAX_VALUE);
        DefaultHttp2FrameWriter frameWriter = new DefaultHttp2FrameWriter(encoder);
        DefaultHttp2Connection connection = new DefaultHttp2Connection(false);
        WeightedFairQueueByteDistributor dist = new WeightedFairQueueByteDistributor(connection);
        dist.allocationQuantum(16384);
        DefaultHttp2RemoteFlowController controller = new DefaultHttp2RemoteFlowController((Http2Connection)connection, dist);
        connection.remote().flowController(controller);
        return NettyClientHandler.newHandler(connection, frameReader, frameWriter, lifecycleManager, keepAliveManager, autoFlowControl, flowControlWindow, maxHeaderListSize, stopwatchFactory, tooManyPingsRunnable, transportTracer, eagAttributes, authority, negotiationLogger, ticker);
    }

    @VisibleForTesting
    static NettyClientHandler newHandler(Http2Connection connection, Http2FrameReader frameReader, Http2FrameWriter frameWriter, ClientTransportLifecycleManager lifecycleManager, KeepAliveManager keepAliveManager, boolean autoFlowControl, int flowControlWindow, int maxHeaderListSize, Supplier<Stopwatch> stopwatchFactory, Runnable tooManyPingsRunnable, TransportTracer transportTracer, Attributes eagAttributes, String authority, ChannelLogger negotiationLogger, Ticker ticker) {
        Preconditions.checkNotNull(connection, "connection");
        Preconditions.checkNotNull(frameReader, "frameReader");
        Preconditions.checkNotNull(lifecycleManager, "lifecycleManager");
        Preconditions.checkArgument(flowControlWindow > 0, "flowControlWindow must be positive");
        Preconditions.checkArgument(maxHeaderListSize > 0, "maxHeaderListSize must be positive");
        Preconditions.checkNotNull(stopwatchFactory, "stopwatchFactory");
        Preconditions.checkNotNull(tooManyPingsRunnable, "tooManyPingsRunnable");
        Preconditions.checkNotNull(eagAttributes, "eagAttributes");
        Preconditions.checkNotNull(authority, "authority");
        Http2FrameLogger frameLogger = new Http2FrameLogger(LogLevel.DEBUG, NettyClientHandler.class);
        frameReader = new Http2InboundFrameLogger(frameReader, frameLogger);
        frameWriter = new Http2OutboundFrameLogger(frameWriter, frameLogger);
        PingCountingFrameWriter pingCounter = new PingCountingFrameWriter(frameWriter);
        frameWriter = pingCounter;
        StreamBufferingEncoder encoder = new StreamBufferingEncoder(new DefaultHttp2ConnectionEncoder(connection, frameWriter));
        connection.local().flowController(new DefaultHttp2LocalFlowController(connection, 0.5f, true));
        DefaultHttp2ConnectionDecoder decoder = new DefaultHttp2ConnectionDecoder(connection, encoder, frameReader);
        transportTracer.setFlowControlWindowReader(new Utils.FlowControlReader(connection));
        Http2Settings settings = new Http2Settings();
        settings.pushEnabled(false);
        settings.initialWindowSize(flowControlWindow);
        settings.maxConcurrentStreams(0L);
        settings.maxHeaderListSize(maxHeaderListSize);
        return new NettyClientHandler(decoder, encoder, settings, negotiationLogger, lifecycleManager, keepAliveManager, stopwatchFactory, tooManyPingsRunnable, transportTracer, eagAttributes, authority, autoFlowControl, pingCounter, ticker);
    }

    private NettyClientHandler(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder, Http2Settings settings, ChannelLogger negotiationLogger, ClientTransportLifecycleManager lifecycleManager, KeepAliveManager keepAliveManager, Supplier<Stopwatch> stopwatchFactory, final Runnable tooManyPingsRunnable, TransportTracer transportTracer, Attributes eagAttributes, String authority, boolean autoFlowControl, AbstractNettyHandler.PingLimiter pingLimiter, Ticker ticker) {
        super(null, decoder, encoder, settings, negotiationLogger, autoFlowControl, pingLimiter, ticker);
        this.lifecycleManager = lifecycleManager;
        this.keepAliveManager = keepAliveManager;
        this.stopwatchFactory = stopwatchFactory;
        this.transportTracer = Preconditions.checkNotNull(transportTracer);
        this.eagAttributes = eagAttributes;
        this.authority = authority;
        this.attributes = Attributes.newBuilder().set(GrpcAttributes.ATTR_CLIENT_EAG_ATTRS, eagAttributes).build();
        this.decoder().frameListener(new FrameListener());
        Http2Connection connection = encoder.connection();
        this.streamKey = connection.newKey();
        connection.addListener(new Http2ConnectionAdapter(){

            @Override
            public void onGoAwayReceived(int lastStreamId, long errorCode, ByteBuf debugData) {
                byte[] debugDataBytes = ByteBufUtil.getBytes(debugData);
                NettyClientHandler.this.goingAway(errorCode, debugDataBytes);
                if (errorCode == Http2Error.ENHANCE_YOUR_CALM.code()) {
                    String data = new String(debugDataBytes, CharsetUtil.UTF_8);
                    logger.log(Level.WARNING, "Received GOAWAY with ENHANCE_YOUR_CALM. Debug data: {0}", data);
                    if ("too_many_pings".equals(data)) {
                        tooManyPingsRunnable.run();
                    }
                }
            }

            @Override
            public void onStreamActive(Http2Stream stream) {
                if (NettyClientHandler.this.connection().numActiveStreams() == 1 && NettyClientHandler.this.keepAliveManager != null) {
                    NettyClientHandler.this.keepAliveManager.onTransportActive();
                }
            }

            @Override
            public void onStreamClosed(Http2Stream stream) {
                NettyClientHandler.this.inUseState.updateObjectInUse(stream, false);
                if (NettyClientHandler.this.connection().numActiveStreams() == 0 && NettyClientHandler.this.keepAliveManager != null) {
                    NettyClientHandler.this.keepAliveManager.onTransportIdle();
                }
            }
        });
    }

    Attributes getAttributes() {
        return this.attributes;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof CreateStreamCommand) {
            this.createStream((CreateStreamCommand)msg, promise);
        } else if (msg instanceof SendGrpcFrameCommand) {
            this.sendGrpcFrame(ctx, (SendGrpcFrameCommand)msg, promise);
        } else if (msg instanceof CancelClientStreamCommand) {
            this.cancelStream(ctx, (CancelClientStreamCommand)msg, promise);
        } else if (msg instanceof SendPingCommand) {
            this.sendPingFrame(ctx, (SendPingCommand)msg, promise);
        } else if (msg instanceof GracefulCloseCommand) {
            this.gracefulClose(ctx, (GracefulCloseCommand)msg, promise);
        } else if (msg instanceof ForcefulCloseCommand) {
            this.forcefulClose(ctx, (ForcefulCloseCommand)msg, promise);
        } else if (msg == NOOP_MESSAGE) {
            ctx.write(Unpooled.EMPTY_BUFFER, promise);
        } else {
            throw new AssertionError((Object)("Write called for unexpected type: " + msg.getClass().getName()));
        }
    }

    void startWriteQueue(Channel channel) {
        this.clientWriteQueue = new WriteQueue(channel);
    }

    WriteQueue getWriteQueue() {
        return this.clientWriteQueue;
    }

    ClientTransportLifecycleManager getLifecycleManager() {
        return this.lifecycleManager;
    }

    void returnProcessedBytes(Http2Stream stream, int bytes) {
        try {
            this.decoder().flowController().consumeBytes(stream, bytes);
        }
        catch (Http2Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void onHeadersRead(int streamId, Http2Headers headers, boolean endStream) {
        if (streamId != 1) {
            NettyClientStream.TransportState stream = this.clientStream(this.requireHttp2Stream(streamId));
            PerfMark.event("NettyClientHandler.onHeadersRead", stream.tag());
            stream.transportHeadersReceived(headers, endStream);
        }
        if (this.keepAliveManager != null) {
            this.keepAliveManager.onDataReceived();
        }
    }

    private void onDataRead(int streamId, ByteBuf data, int padding, boolean endOfStream) {
        this.flowControlPing().onDataRead(data.readableBytes(), padding);
        NettyClientStream.TransportState stream = this.clientStream(this.requireHttp2Stream(streamId));
        PerfMark.event("NettyClientHandler.onDataRead", stream.tag());
        stream.transportDataReceived(data, endOfStream);
        if (this.keepAliveManager != null) {
            this.keepAliveManager.onDataReceived();
        }
    }

    private void onRstStreamRead(int streamId, long errorCode) {
        NettyClientStream.TransportState stream = this.clientStream(this.connection().stream(streamId));
        if (stream != null) {
            PerfMark.event("NettyClientHandler.onRstStreamRead", stream.tag());
            Status status = this.statusFromH2Error(null, "RST_STREAM closed stream", errorCode, null);
            stream.transportReportStatus(status, errorCode == Http2Error.REFUSED_STREAM.code() ? ClientStreamListener.RpcProgress.REFUSED : ClientStreamListener.RpcProgress.PROCESSED, false, new Metadata());
            if (this.keepAliveManager != null) {
                this.keepAliveManager.onDataReceived();
            }
        }
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        logger.fine("Network channel being closed by the application.");
        if (ctx.channel().isActive()) {
            this.lifecycleManager.notifyShutdown(Status.UNAVAILABLE.withDescription("Transport closed for unknown reason"));
        }
        super.close(ctx, promise);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        try {
            logger.fine("Network channel is closed");
            Status status = Status.UNAVAILABLE.withDescription("Network closed for unknown reason");
            this.lifecycleManager.notifyShutdown(status);
            final Status streamStatus = this.channelInactiveReason != null ? this.channelInactiveReason : this.lifecycleManager.getShutdownStatus();
            try {
                this.cancelPing(this.lifecycleManager.getShutdownThrowable());
                this.connection().forEachActiveStream(new Http2StreamVisitor(){

                    @Override
                    public boolean visit(Http2Stream stream) throws Http2Exception {
                        NettyClientStream.TransportState clientStream = NettyClientHandler.this.clientStream(stream);
                        if (clientStream != null) {
                            clientStream.transportReportStatus(streamStatus, false, new Metadata());
                        }
                        return true;
                    }
                });
            }
            finally {
                this.lifecycleManager.notifyTerminated(status);
            }
        }
        finally {
            super.channelInactive(ctx);
            if (this.keepAliveManager != null) {
                this.keepAliveManager.onTransportTermination();
            }
        }
    }

    @Override
    public void handleProtocolNegotiationCompleted(Attributes attributes, InternalChannelz.Security securityInfo) {
        this.attributes = this.attributes.toBuilder().setAll(attributes).build();
        this.securityInfo = securityInfo;
        super.handleProtocolNegotiationCompleted(attributes, securityInfo);
        NettyClientHandler.writeBufferingAndRemove(this.ctx().channel());
    }

    static void writeBufferingAndRemove(Channel channel) {
        ObjectUtil.checkNotNull(channel, "channel");
        ChannelHandlerContext handlerCtx = channel.pipeline().context(WriteBufferingAndExceptionHandler.class);
        if (handlerCtx == null) {
            return;
        }
        ((WriteBufferingAndExceptionHandler)handlerCtx.handler()).writeBufferedAndRemove(handlerCtx);
    }

    @Override
    public Attributes getEagAttributes() {
        return this.eagAttributes;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    InternalChannelz.Security getSecurityInfo() {
        return this.securityInfo;
    }

    @Override
    protected void onConnectionError(ChannelHandlerContext ctx, boolean outbound, Throwable cause, Http2Exception http2Ex) {
        logger.log(Level.FINE, "Caught a connection error", cause);
        this.lifecycleManager.notifyShutdown(Utils.statusFromThrowable(cause));
        super.onConnectionError(ctx, outbound, cause, http2Ex);
    }

    @Override
    protected void onStreamError(ChannelHandlerContext ctx, boolean outbound, Throwable cause, Http2Exception.StreamException http2Ex) {
        NettyClientStream.TransportState stream = this.clientStream(this.connection().stream(http2Ex.streamId()));
        if (stream != null) {
            stream.transportReportStatus(Utils.statusFromThrowable(cause), false, new Metadata());
        } else {
            logger.log(Level.FINE, "Stream error for unknown stream " + http2Ex.streamId(), cause);
        }
        super.onStreamError(ctx, outbound, cause, http2Ex);
    }

    @Override
    protected boolean isGracefulShutdownComplete() {
        return super.isGracefulShutdownComplete() && ((StreamBufferingEncoder)this.encoder()).numBufferedStreams() == 0;
    }

    private void createStream(CreateStreamCommand command, ChannelPromise promise) throws Exception {
        int streamId;
        if (this.lifecycleManager.getShutdownThrowable() != null) {
            command.stream().setNonExistent();
            command.stream().transportReportStatus(this.lifecycleManager.getShutdownStatus(), ClientStreamListener.RpcProgress.MISCARRIED, true, new Metadata());
            promise.setFailure(this.lifecycleManager.getShutdownThrowable());
            return;
        }
        try {
            streamId = this.incrementAndGetNextStreamId();
        }
        catch (StatusException e) {
            command.stream().setNonExistent();
            promise.setFailure(e);
            if (!this.connection().goAwaySent()) {
                logger.fine("Stream IDs have been exhausted for this connection. Initiating graceful shutdown of the connection.");
                this.lifecycleManager.notifyShutdown(e.getStatus());
                this.close(this.ctx(), this.ctx().newPromise());
            }
            return;
        }
        if (this.connection().goAwayReceived()) {
            Status s2 = this.abruptGoAwayStatus;
            int maxActiveStreams = this.connection().local().maxActiveStreams();
            int lastStreamId = this.connection().local().lastStreamKnownByPeer();
            if (s2 == null) {
                s2 = Status.INTERNAL.withDescription("Failed due to abrupt GOAWAY, but can't find GOAWAY details");
            } else if (streamId > lastStreamId) {
                s2 = s2.augmentDescription("stream id: " + streamId + ", GOAWAY Last-Stream-ID:" + lastStreamId);
            } else if (this.connection().local().numActiveStreams() == maxActiveStreams) {
                s2 = s2.augmentDescription("At MAX_CONCURRENT_STREAMS limit. limit: " + maxActiveStreams);
            }
            if (streamId > lastStreamId || this.connection().local().numActiveStreams() == maxActiveStreams) {
                command.stream().setNonExistent();
                command.stream().transportReportStatus(s2, ClientStreamListener.RpcProgress.MISCARRIED, true, new Metadata());
                promise.setFailure(s2.asRuntimeException());
                return;
            }
        }
        NettyClientStream.TransportState stream = command.stream();
        Http2Headers headers = command.headers();
        stream.setId(streamId);
        try (TaskCloseable ignore = PerfMark.traceTask("NettyClientHandler.createStream");){
            PerfMark.linkIn(command.getLink());
            PerfMark.attachTag(stream.tag());
            this.createStreamTraced(streamId, stream, headers, command.isGet(), command.shouldBeCountedForInUse(), promise);
        }
    }

    private void createStreamTraced(final int streamId, final NettyClientStream.TransportState stream, Http2Headers headers, boolean isGet, final boolean shouldBeCountedForInUse, final ChannelPromise promise) {
        ChannelPromise tempPromise = this.ctx().newPromise();
        this.encoder().writeHeaders(this.ctx(), streamId, headers, 0, isGet, tempPromise).addListener(new ChannelFutureListener(){

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    Http2Stream http2Stream = NettyClientHandler.this.connection().stream(streamId);
                    if (http2Stream != null) {
                        stream.getStatsTraceContext().clientOutboundHeaders();
                        http2Stream.setProperty(NettyClientHandler.this.streamKey, stream);
                        if (shouldBeCountedForInUse) {
                            NettyClientHandler.this.inUseState.updateObjectInUse(http2Stream, true);
                        }
                        stream.setHttp2Stream(http2Stream);
                    }
                    promise.setSuccess();
                } else {
                    Throwable cause = future.cause();
                    if (cause instanceof StreamBufferingEncoder.Http2GoAwayException) {
                        StreamBufferingEncoder.Http2GoAwayException e = (StreamBufferingEncoder.Http2GoAwayException)cause;
                        Status status = NettyClientHandler.this.statusFromH2Error(Status.Code.UNAVAILABLE, "GOAWAY closed buffered stream", e.errorCode(), e.debugData());
                        cause = status.asRuntimeException();
                        stream.transportReportStatus(status, ClientStreamListener.RpcProgress.MISCARRIED, true, new Metadata());
                    } else if (cause instanceof StreamBufferingEncoder.Http2ChannelClosedException) {
                        Status status = NettyClientHandler.this.lifecycleManager.getShutdownStatus();
                        if (status == null) {
                            status = Status.UNAVAILABLE.withCause(cause).withDescription("Connection closed while stream is buffered");
                        }
                        stream.transportReportStatus(status, ClientStreamListener.RpcProgress.MISCARRIED, true, new Metadata());
                    }
                    promise.setFailure(cause);
                }
            }
        });
    }

    private void cancelStream(ChannelHandlerContext ctx, CancelClientStreamCommand cmd, ChannelPromise promise) {
        NettyClientStream.TransportState stream = cmd.stream();
        try (TaskCloseable ignore = PerfMark.traceTask("NettyClientHandler.cancelStream");){
            PerfMark.attachTag(stream.tag());
            PerfMark.linkIn(cmd.getLink());
            Status reason = cmd.reason();
            if (reason != null) {
                stream.transportReportStatus(reason, true, new Metadata());
            }
            if (!cmd.stream().isNonExistent()) {
                this.encoder().writeRstStream(ctx, stream.id(), Http2Error.CANCEL.code(), promise);
            } else {
                promise.setSuccess();
            }
        }
    }

    private void sendGrpcFrame(ChannelHandlerContext ctx, SendGrpcFrameCommand cmd, ChannelPromise promise) {
        try (TaskCloseable ignore = PerfMark.traceTask("NettyClientHandler.sendGrpcFrame");){
            PerfMark.attachTag(cmd.stream().tag());
            PerfMark.linkIn(cmd.getLink());
            this.encoder().writeData(ctx, cmd.stream().id(), cmd.content(), 0, cmd.endStream(), promise);
        }
    }

    private void sendPingFrame(ChannelHandlerContext ctx, SendPingCommand msg, ChannelPromise promise) {
        try (TaskCloseable ignore = PerfMark.traceTask("NettyClientHandler.sendPingFrame");){
            PerfMark.linkIn(msg.getLink());
            this.sendPingFrameTraced(ctx, msg, promise);
        }
    }

    private void sendPingFrameTraced(ChannelHandlerContext ctx, SendPingCommand msg, ChannelPromise promise) {
        ClientTransport.PingCallback callback = msg.callback();
        Executor executor2 = msg.executor();
        if (this.ping != null) {
            promise.setSuccess();
            this.ping.addCallback(callback, executor2);
            return;
        }
        promise.setSuccess();
        promise = this.ctx().newPromise();
        long data = 1111L;
        Stopwatch stopwatch = this.stopwatchFactory.get();
        stopwatch.start();
        this.ping = new Http2Ping(data, stopwatch);
        this.ping.addCallback(callback, executor2);
        this.encoder().writePing(ctx, false, 1111L, promise);
        ctx.flush();
        final Http2Ping finalPing = this.ping;
        promise.addListener(new ChannelFutureListener(){

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    NettyClientHandler.this.transportTracer.reportKeepAliveSent();
                } else {
                    Throwable cause = future.cause();
                    if (cause instanceof ClosedChannelException && (cause = NettyClientHandler.this.lifecycleManager.getShutdownThrowable()) == null) {
                        cause = Status.UNKNOWN.withDescription("Ping failed but for unknown reason.").withCause(future.cause()).asException();
                    }
                    finalPing.failed(cause);
                    if (NettyClientHandler.this.ping == finalPing) {
                        NettyClientHandler.this.ping = null;
                    }
                }
            }
        });
    }

    private void gracefulClose(ChannelHandlerContext ctx, GracefulCloseCommand msg, ChannelPromise promise) throws Exception {
        this.lifecycleManager.notifyShutdown(msg.getStatus());
        this.flush(ctx);
        this.close(ctx, promise);
    }

    private void forcefulClose(final ChannelHandlerContext ctx, final ForcefulCloseCommand msg, ChannelPromise promise) throws Exception {
        this.connection().forEachActiveStream(new Http2StreamVisitor(){

            @Override
            public boolean visit(Http2Stream stream) throws Http2Exception {
                NettyClientStream.TransportState clientStream = NettyClientHandler.this.clientStream(stream);
                Tag tag = clientStream != null ? clientStream.tag() : PerfMark.createTag();
                try (TaskCloseable ignore = PerfMark.traceTask("NettyClientHandler.forcefulClose");){
                    PerfMark.linkIn(msg.getLink());
                    PerfMark.attachTag(tag);
                    if (clientStream != null) {
                        clientStream.transportReportStatus(msg.getStatus(), true, new Metadata());
                        NettyClientHandler.this.resetStream(ctx, stream.id(), Http2Error.CANCEL.code(), ctx.newPromise());
                    }
                    stream.close();
                    boolean bl = true;
                    return bl;
                }
            }
        });
        this.close(ctx, promise);
    }

    private void goingAway(long errorCode, byte[] debugData) {
        Status finalStatus = this.statusFromH2Error(Status.Code.UNAVAILABLE, "GOAWAY shut down transport", errorCode, debugData);
        this.lifecycleManager.notifyGracefulShutdown(finalStatus);
        this.abruptGoAwayStatus = this.statusFromH2Error(Status.Code.UNAVAILABLE, "Abrupt GOAWAY closed unsent stream", errorCode, debugData);
        final Status abruptGoAwayStatusConservative = this.statusFromH2Error(null, "Abrupt GOAWAY closed sent stream", errorCode, debugData);
        final boolean mayBeHittingNettyBug = errorCode != Http2Error.NO_ERROR.code();
        this.clientWriteQueue.drainNow();
        if (this.lifecycleManager.notifyShutdown(finalStatus)) {
            this.channelInactiveReason = this.statusFromH2Error(null, "Connection closed after GOAWAY", errorCode, debugData);
        }
        final int lastKnownStream = this.connection().local().lastStreamKnownByPeer();
        try {
            this.connection().forEachActiveStream(new Http2StreamVisitor(){

                @Override
                public boolean visit(Http2Stream stream) throws Http2Exception {
                    if (stream.id() > lastKnownStream) {
                        NettyClientStream.TransportState clientStream = NettyClientHandler.this.clientStream(stream);
                        if (clientStream != null) {
                            ClientStreamListener.RpcProgress progress = mayBeHittingNettyBug ? ClientStreamListener.RpcProgress.PROCESSED : ClientStreamListener.RpcProgress.REFUSED;
                            clientStream.transportReportStatus(abruptGoAwayStatusConservative, progress, false, new Metadata());
                        }
                        stream.close();
                    }
                    return true;
                }
            });
        }
        catch (Http2Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void cancelPing(Throwable t2) {
        if (this.ping != null) {
            this.ping.failed(t2);
            this.ping = null;
        }
    }

    private Status statusFromH2Error(Status.Code statusCode, String context, long errorCode, byte[] debugData) {
        Status status = GrpcUtil.Http2Error.statusForCode(errorCode);
        if (statusCode == null) {
            statusCode = status.getCode();
        }
        String debugString = "";
        if (debugData != null && debugData.length > 0) {
            debugString = ", debug data: " + new String(debugData, CharsetUtil.UTF_8);
        }
        return statusCode.toStatus().withDescription(context + ". " + status.getDescription() + debugString);
    }

    private NettyClientStream.TransportState clientStream(Http2Stream stream) {
        return stream == null ? null : (NettyClientStream.TransportState)stream.getProperty(this.streamKey);
    }

    private int incrementAndGetNextStreamId() throws StatusException {
        int nextStreamId = this.connection().local().incrementAndGetNextStreamId();
        if (nextStreamId < 0) {
            logger.fine("Stream IDs have been exhausted for this connection. Initiating graceful shutdown of the connection.");
            throw EXHAUSTED_STREAMS_STATUS.asException();
        }
        return nextStreamId;
    }

    private Http2Stream requireHttp2Stream(int streamId) {
        Http2Stream stream = this.connection().stream(streamId);
        if (stream == null) {
            throw new AssertionError((Object)("Stream does not exist: " + streamId));
        }
        return stream;
    }

    private static class PingCountingFrameWriter
    extends DecoratingHttp2FrameWriter
    implements AbstractNettyHandler.PingLimiter {
        private int pingCount;

        public PingCountingFrameWriter(Http2FrameWriter delegate) {
            super(delegate);
        }

        @Override
        public boolean isPingAllowed() {
            return this.pingCount < 2;
        }

        @Override
        public ChannelFuture writeHeaders(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endStream, ChannelPromise promise) {
            this.pingCount = 0;
            return super.writeHeaders(ctx, streamId, headers, padding, endStream, promise);
        }

        @Override
        public ChannelFuture writeHeaders(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endStream, ChannelPromise promise) {
            this.pingCount = 0;
            return super.writeHeaders(ctx, streamId, headers, streamDependency, weight, exclusive, padding, endStream, promise);
        }

        @Override
        public ChannelFuture writeWindowUpdate(ChannelHandlerContext ctx, int streamId, int windowSizeIncrement, ChannelPromise promise) {
            this.pingCount = 0;
            return super.writeWindowUpdate(ctx, streamId, windowSizeIncrement, promise);
        }

        @Override
        public ChannelFuture writePing(ChannelHandlerContext ctx, boolean ack, long data, ChannelPromise promise) {
            if (!ack) {
                ++this.pingCount;
            }
            return super.writePing(ctx, ack, data, promise);
        }

        @Override
        public ChannelFuture writeData(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endStream, ChannelPromise promise) {
            if (data.isReadable()) {
                this.pingCount = 0;
            }
            return super.writeData(ctx, streamId, data, padding, endStream, promise);
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
                NettyClientHandler.this.attributes = NettyClientHandler.this.lifecycleManager.filterAttributes(NettyClientHandler.this.attributes);
                NettyClientHandler.this.lifecycleManager.notifyReady();
            }
        }

        @Override
        public int onDataRead(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endOfStream) throws Http2Exception {
            NettyClientHandler.this.onDataRead(streamId, data, padding, endOfStream);
            return padding;
        }

        @Override
        public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endStream) throws Http2Exception {
            NettyClientHandler.this.onHeadersRead(streamId, headers, endStream);
        }

        @Override
        public void onRstStreamRead(ChannelHandlerContext ctx, int streamId, long errorCode) throws Http2Exception {
            NettyClientHandler.this.onRstStreamRead(streamId, errorCode);
        }

        @Override
        public void onPingAckRead(ChannelHandlerContext ctx, long ackPayload) throws Http2Exception {
            Http2Ping p = NettyClientHandler.this.ping;
            if (ackPayload == NettyClientHandler.this.flowControlPing().payload()) {
                NettyClientHandler.this.flowControlPing().updateWindow();
                logger.log(Level.FINE, "Window: {0}", NettyClientHandler.this.decoder().flowController().initialWindowSize(NettyClientHandler.this.connection().connectionStream()));
            } else if (p != null) {
                if (p.payload() == ackPayload) {
                    p.complete();
                    NettyClientHandler.this.ping = null;
                } else {
                    logger.log(Level.WARNING, "Received unexpected ping ack. Expecting {0}, got {1}", new Object[]{p.payload(), ackPayload});
                }
            } else {
                logger.warning("Received unexpected ping ack. No ping outstanding");
            }
            if (NettyClientHandler.this.keepAliveManager != null) {
                NettyClientHandler.this.keepAliveManager.onDataReceived();
            }
        }

        @Override
        public void onPingRead(ChannelHandlerContext ctx, long data) throws Http2Exception {
            if (NettyClientHandler.this.keepAliveManager != null) {
                NettyClientHandler.this.keepAliveManager.onDataReceived();
            }
        }
    }
}

