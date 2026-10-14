/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.InternalKnownTransport;
import io.grpc.InternalMethodDescriptor;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.internal.AbstractClientStream;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.Http2ClientStreamTransportState;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.TransportTracer;
import io.grpc.internal.WritableBuffer;
import io.grpc.netty.shaded.io.grpc.netty.CancelClientStreamCommand;
import io.grpc.netty.shaded.io.grpc.netty.CreateStreamCommand;
import io.grpc.netty.shaded.io.grpc.netty.NettyClientHandler;
import io.grpc.netty.shaded.io.grpc.netty.NettyClientTransport;
import io.grpc.netty.shaded.io.grpc.netty.NettyReadableBuffer;
import io.grpc.netty.shaded.io.grpc.netty.NettyWritableBuffer;
import io.grpc.netty.shaded.io.grpc.netty.NettyWritableBufferAllocator;
import io.grpc.netty.shaded.io.grpc.netty.SendGrpcFrameCommand;
import io.grpc.netty.shaded.io.grpc.netty.StreamIdHolder;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.grpc.netty.WriteQueue;
import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.Unpooled;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelFutureListener;
import io.grpc.netty.shaded.io.netty.channel.EventLoop;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Headers;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Stream;
import io.grpc.netty.shaded.io.netty.util.AsciiString;
import io.perfmark.PerfMark;
import io.perfmark.Tag;
import io.perfmark.TaskCloseable;
import javax.annotation.Nullable;

class NettyClientStream
extends AbstractClientStream {
    private static final InternalMethodDescriptor methodDescriptorAccessor = new InternalMethodDescriptor(NettyClientTransport.class.getName().contains("grpc.netty.shaded") ? InternalKnownTransport.NETTY_SHADED : InternalKnownTransport.NETTY);
    private final Sink sink = new Sink();
    private final TransportState state;
    private final WriteQueue writeQueue;
    private final MethodDescriptor<?, ?> method;
    private AsciiString authority;
    private final AsciiString scheme;
    private final AsciiString userAgent;

    NettyClientStream(TransportState state, MethodDescriptor<?, ?> method2, Metadata headers, Channel channel, AsciiString authority, AsciiString scheme2, AsciiString userAgent, StatsTraceContext statsTraceCtx, TransportTracer transportTracer, CallOptions callOptions, boolean useGetForSafeMethods) {
        super(new NettyWritableBufferAllocator(channel.alloc()), statsTraceCtx, transportTracer, headers, callOptions, useGetForSafeMethods && method2.isSafe());
        this.state = Preconditions.checkNotNull(state, "transportState");
        this.writeQueue = state.handler.getWriteQueue();
        this.method = Preconditions.checkNotNull(method2, "method");
        this.authority = Preconditions.checkNotNull(authority, "authority");
        this.scheme = Preconditions.checkNotNull(scheme2, "scheme");
        this.userAgent = userAgent;
    }

    @Override
    protected TransportState transportState() {
        return this.state;
    }

    @Override
    protected Sink abstractClientStreamSink() {
        return this.sink;
    }

    @Override
    public void setAuthority(String authority) {
        this.authority = AsciiString.of(Preconditions.checkNotNull(authority, "authority"));
    }

    @Override
    public Attributes getAttributes() {
        return this.state.handler.getAttributes();
    }

    public static abstract class TransportState
    extends Http2ClientStreamTransportState
    implements StreamIdHolder {
        private static final int NON_EXISTENT_ID = -1;
        private final String methodName;
        private final NettyClientHandler handler;
        private final EventLoop eventLoop;
        private int id;
        private Http2Stream http2Stream;
        private Tag tag;

        protected TransportState(NettyClientHandler handler, EventLoop eventLoop, int maxMessageSize, StatsTraceContext statsTraceCtx, TransportTracer transportTracer, String methodName, CallOptions options) {
            super(maxMessageSize, statsTraceCtx, transportTracer, options);
            this.methodName = Preconditions.checkNotNull(methodName, "methodName");
            this.handler = Preconditions.checkNotNull(handler, "handler");
            this.eventLoop = Preconditions.checkNotNull(eventLoop, "eventLoop");
            this.tag = PerfMark.createTag(methodName);
        }

        @Override
        public int id() {
            return this.id;
        }

        public void setId(int id) {
            Preconditions.checkArgument(id > 0, "id must be positive %s", id);
            Preconditions.checkState(this.id == 0, "id has been previously set: %s", this.id);
            this.id = id;
            this.tag = PerfMark.createTag(this.methodName, id);
        }

        void setNonExistent() {
            Preconditions.checkState(this.id == 0, "Id has been previously set: %s", this.id);
            this.id = -1;
        }

        boolean isNonExistent() {
            return this.id == -1 || this.id == 0;
        }

        public void setHttp2Stream(Http2Stream http2Stream) {
            Preconditions.checkNotNull(http2Stream, "http2Stream");
            Preconditions.checkState(this.http2Stream == null, "Can only set http2Stream once");
            this.http2Stream = http2Stream;
            this.onStreamAllocated();
            this.getTransportTracer().reportLocalStreamStarted();
        }

        @Nullable
        public Http2Stream http2Stream() {
            return this.http2Stream;
        }

        protected abstract Status statusFromFailedFuture(ChannelFuture var1);

        @Override
        protected void http2ProcessingFailed(Status status, boolean stopDelivery, Metadata trailers) {
            this.transportReportStatus(status, stopDelivery, trailers);
            this.handler.getWriteQueue().enqueue(new CancelClientStreamCommand(this, status), true);
        }

        private void onWriteFrameData(ChannelFuture future, int numMessages, int numBytes) {
            if (future.isSuccess() && this.http2Stream() == null) {
                return;
            }
            if (future.isSuccess()) {
                this.onSentBytes(numBytes);
                this.getTransportTracer().reportMessageSent(numMessages);
            } else if (!this.isStreamDeallocated()) {
                this.http2ProcessingFailed(this.statusFromFailedFuture(future), true, new Metadata());
            }
        }

        @Override
        public void runOnTransportThread(Runnable r) {
            if (this.eventLoop.inEventLoop()) {
                r.run();
            } else {
                this.eventLoop.execute(r);
            }
        }

        @Override
        public void bytesRead(int processedBytes) {
            this.handler.returnProcessedBytes(this.http2Stream, processedBytes);
            this.handler.getWriteQueue().scheduleFlush();
        }

        @Override
        public void deframeFailed(Throwable cause) {
            this.http2ProcessingFailed(Status.fromThrowable(cause), true, new Metadata());
        }

        void transportHeadersReceived(Http2Headers headers, boolean endOfStream) {
            if (endOfStream) {
                if (!this.isOutboundClosed()) {
                    this.handler.getWriteQueue().enqueue(new CancelClientStreamCommand(this, null), true);
                }
                this.transportTrailersReceived(Utils.convertTrailers(headers));
            } else {
                this.transportHeadersReceived(Utils.convertHeaders(headers));
            }
        }

        void transportDataReceived(ByteBuf frame, boolean endOfStream) {
            this.transportDataReceived(new NettyReadableBuffer(frame.retain()), endOfStream);
        }

        @Override
        public final Tag tag() {
            return this.tag;
        }
    }

    private class Sink
    implements AbstractClientStream.Sink {
        private Sink() {
        }

        @Override
        public void writeHeaders(Metadata headers, byte[] requestPayload) {
            try (TaskCloseable ignore = PerfMark.traceTask("NettyClientStream$Sink.writeHeaders");){
                this.writeHeadersInternal(headers, requestPayload);
            }
        }

        private void writeHeadersInternal(Metadata headers, byte[] requestPayload) {
            AsciiString httpMethod;
            boolean get2;
            AsciiString defaultPath = (AsciiString)methodDescriptorAccessor.geRawMethodName(NettyClientStream.this.method);
            if (defaultPath == null) {
                defaultPath = new AsciiString("/" + NettyClientStream.this.method.getFullMethodName());
                methodDescriptorAccessor.setRawMethodName(NettyClientStream.this.method, defaultPath);
            }
            boolean bl = get2 = requestPayload != null;
            if (get2) {
                defaultPath = new AsciiString(defaultPath + "?" + BaseEncoding.base64().encode(requestPayload));
                httpMethod = Utils.HTTP_GET_METHOD;
            } else {
                httpMethod = Utils.HTTP_METHOD;
            }
            Http2Headers http2Headers = Utils.convertClientHeaders(headers, NettyClientStream.this.scheme, defaultPath, NettyClientStream.this.authority, httpMethod, NettyClientStream.this.userAgent);
            ChannelFutureListener failureListener = new ChannelFutureListener(){

                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        Status s2 = NettyClientStream.this.transportState().handler.getLifecycleManager().getShutdownStatus();
                        if (s2 == null) {
                            s2 = NettyClientStream.this.transportState().statusFromFailedFuture(future);
                        }
                        if (NettyClientStream.this.transportState().isNonExistent()) {
                            NettyClientStream.this.transportState().transportReportStatus(s2, ClientStreamListener.RpcProgress.MISCARRIED, true, new Metadata());
                        } else {
                            NettyClientStream.this.transportState().transportReportStatus(s2, ClientStreamListener.RpcProgress.PROCESSED, true, new Metadata());
                        }
                    }
                }
            };
            NettyClientStream.this.writeQueue.enqueue(new CreateStreamCommand(http2Headers, NettyClientStream.this.transportState(), NettyClientStream.this.shouldBeCountedForInUse(), get2), !NettyClientStream.this.method.getType().clientSendsOneMessage() || get2).addListener(failureListener);
        }

        private void writeFrameInternal(WritableBuffer frame, boolean endOfStream, boolean flush, int numMessages) {
            Preconditions.checkArgument(numMessages >= 0);
            ByteBuf bytebuf = frame == null ? Unpooled.EMPTY_BUFFER : ((NettyWritableBuffer)frame).bytebuf().touch();
            int numBytes = bytebuf.readableBytes();
            if (numBytes > 0) {
                NettyClientStream.this.onSendingBytes(numBytes);
                ChannelFutureListener failureListener = future -> NettyClientStream.this.transportState().onWriteFrameData(future, numMessages, numBytes);
                NettyClientStream.this.writeQueue.enqueue(new SendGrpcFrameCommand(NettyClientStream.this.transportState(), bytebuf, endOfStream), flush).addListener(failureListener);
            } else {
                NettyClientStream.this.writeQueue.enqueue(new SendGrpcFrameCommand(NettyClientStream.this.transportState(), bytebuf, endOfStream), flush);
            }
        }

        @Override
        public void writeFrame(WritableBuffer frame, boolean endOfStream, boolean flush, int numMessages) {
            try (TaskCloseable ignore = PerfMark.traceTask("NettyClientStream$Sink.writeFrame");){
                this.writeFrameInternal(frame, endOfStream, flush, numMessages);
            }
        }

        @Override
        public void cancel(Status status) {
            try (TaskCloseable ignore = PerfMark.traceTask("NettyClientStream$Sink.cancel");){
                NettyClientStream.this.writeQueue.enqueue(new CancelClientStreamCommand(NettyClientStream.this.transportState(), status), true);
            }
        }
    }
}

