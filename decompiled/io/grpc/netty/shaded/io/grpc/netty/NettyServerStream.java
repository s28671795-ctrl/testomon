/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.AbstractServerStream;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.TransportTracer;
import io.grpc.internal.WritableBuffer;
import io.grpc.netty.shaded.io.grpc.netty.CancelServerStreamCommand;
import io.grpc.netty.shaded.io.grpc.netty.NettyReadableBuffer;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerHandler;
import io.grpc.netty.shaded.io.grpc.netty.NettyWritableBuffer;
import io.grpc.netty.shaded.io.grpc.netty.NettyWritableBufferAllocator;
import io.grpc.netty.shaded.io.grpc.netty.SendGrpcFrameCommand;
import io.grpc.netty.shaded.io.grpc.netty.SendResponseHeadersCommand;
import io.grpc.netty.shaded.io.grpc.netty.StreamIdHolder;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.grpc.netty.WriteQueue;
import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelFutureListener;
import io.grpc.netty.shaded.io.netty.channel.EventLoop;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Headers;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Stream;
import io.perfmark.Link;
import io.perfmark.PerfMark;
import io.perfmark.Tag;
import io.perfmark.TaskCloseable;
import java.util.logging.Level;
import java.util.logging.Logger;

class NettyServerStream
extends AbstractServerStream {
    private static final Logger log = Logger.getLogger(NettyServerStream.class.getName());
    private final Sink sink = new Sink();
    private final TransportState state;
    private final WriteQueue writeQueue;
    private final Attributes attributes;
    private final String authority;
    private final int streamId;

    public NettyServerStream(Channel channel, TransportState state, Attributes transportAttrs, String authority, StatsTraceContext statsTraceCtx) {
        super(new NettyWritableBufferAllocator(channel.alloc()), statsTraceCtx);
        this.state = Preconditions.checkNotNull(state, "transportState");
        this.writeQueue = state.handler.getWriteQueue();
        this.attributes = Preconditions.checkNotNull(transportAttrs);
        this.authority = authority;
        this.streamId = this.transportState().id();
    }

    @Override
    protected TransportState transportState() {
        return this.state;
    }

    @Override
    protected Sink abstractServerStreamSink() {
        return this.sink;
    }

    @Override
    public Attributes getAttributes() {
        return this.attributes;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    @Override
    public int streamId() {
        return this.streamId;
    }

    public static class TransportState
    extends AbstractServerStream.TransportState
    implements StreamIdHolder {
        private final Http2Stream http2Stream;
        private final NettyServerHandler handler;
        private final EventLoop eventLoop;
        private final Tag tag;

        public TransportState(NettyServerHandler handler, EventLoop eventLoop, Http2Stream http2Stream, int maxMessageSize, StatsTraceContext statsTraceCtx, TransportTracer transportTracer, String methodName) {
            super(maxMessageSize, statsTraceCtx, transportTracer);
            this.http2Stream = Preconditions.checkNotNull(http2Stream, "http2Stream");
            this.handler = Preconditions.checkNotNull(handler, "handler");
            this.eventLoop = eventLoop;
            this.tag = PerfMark.createTag(methodName, http2Stream.id());
        }

        @Override
        public void runOnTransportThread(final Runnable r) {
            if (this.eventLoop.inEventLoop()) {
                r.run();
            } else {
                final Link link = PerfMark.linkOut();
                this.eventLoop.execute(new Runnable(){

                    @Override
                    public void run() {
                        try (TaskCloseable ignore = PerfMark.traceTask("NettyServerStream$TransportState.runOnTransportThread");){
                            PerfMark.attachTag(tag);
                            PerfMark.linkIn(link);
                            r.run();
                        }
                    }
                });
            }
        }

        @Override
        public void bytesRead(int processedBytes) {
            this.handler.returnProcessedBytes(this.http2Stream, processedBytes);
            this.handler.getWriteQueue().scheduleFlush();
        }

        @Override
        public void deframeFailed(Throwable cause) {
            log.log(Level.WARNING, "Exception processing message", cause);
            Status status = Status.fromThrowable(cause);
            this.transportReportStatus(status);
            this.handler.getWriteQueue().enqueue(CancelServerStreamCommand.withReason(this, status), true);
        }

        private void onWriteFrameData(ChannelFuture future, int numMessages, int numBytes) {
            if (future.isSuccess()) {
                this.onSentBytes(numBytes);
                this.getTransportTracer().reportMessageSent(numMessages);
            } else {
                this.handleWriteFutureFailures(future);
            }
        }

        private void handleWriteFutureFailures(ChannelFuture future) {
            if (future.isSuccess() || this.isStreamDeallocated()) {
                return;
            }
            this.http2ProcessingFailed(Utils.statusFromThrowable(future.cause()));
        }

        protected void http2ProcessingFailed(Status status) {
            this.transportReportStatus(status);
            this.handler.getWriteQueue().enqueue(CancelServerStreamCommand.withReset(this, status), true);
        }

        void inboundDataReceived(ByteBuf frame, boolean endOfStream) {
            super.inboundDataReceived(new NettyReadableBuffer(frame.retain()), endOfStream);
        }

        @Override
        public int id() {
            return this.http2Stream.id();
        }

        @Override
        public Tag tag() {
            return this.tag;
        }
    }

    private class Sink
    implements AbstractServerStream.Sink {
        private Sink() {
        }

        @Override
        public void writeHeaders(Metadata headers, boolean flush) {
            try (TaskCloseable ignore = PerfMark.traceTask("NettyServerStream$Sink.writeHeaders");){
                Http2Headers http2headers = Utils.convertServerHeaders(headers);
                SendResponseHeadersCommand headersCommand = SendResponseHeadersCommand.createHeaders(NettyServerStream.this.transportState(), http2headers);
                NettyServerStream.this.writeQueue.enqueue(headersCommand, flush).addListener(arg_0 -> Sink.lambda$writeHeaders$0(NettyServerStream.this.transportState(), arg_0));
            }
        }

        @Override
        public void writeFrame(WritableBuffer frame, boolean flush, int numMessages) {
            try (TaskCloseable ignore = PerfMark.traceTask("NettyServerStream$Sink.writeFrame");){
                Preconditions.checkArgument(numMessages >= 0);
                ByteBuf bytebuf = ((NettyWritableBuffer)frame).bytebuf().touch();
                int numBytes = bytebuf.readableBytes();
                NettyServerStream.this.onSendingBytes(numBytes);
                ChannelFutureListener failureListener = future -> NettyServerStream.this.transportState().onWriteFrameData(future, numMessages, numBytes);
                NettyServerStream.this.writeQueue.enqueue(new SendGrpcFrameCommand(NettyServerStream.this.transportState(), bytebuf, false), flush).addListener(failureListener);
            }
        }

        @Override
        public void writeTrailers(Metadata trailers, boolean headersSent, Status status) {
            try (TaskCloseable ignore = PerfMark.traceTask("NettyServerStream$Sink.writeTrailers");){
                Http2Headers http2Trailers = Utils.convertTrailers(trailers, headersSent);
                SendResponseHeadersCommand trailersCommand = SendResponseHeadersCommand.createTrailers(NettyServerStream.this.transportState(), http2Trailers, status);
                NettyServerStream.this.writeQueue.enqueue(trailersCommand, true).addListener(arg_0 -> Sink.lambda$writeTrailers$2(NettyServerStream.this.transportState(), arg_0));
            }
        }

        @Override
        public void cancel(Status status) {
            try (TaskCloseable ignore = PerfMark.traceTask("NettyServerStream$Sink.cancel");){
                NettyServerStream.this.writeQueue.enqueue(CancelServerStreamCommand.withReset(NettyServerStream.this.transportState(), status), true);
            }
        }

        private static /* synthetic */ void lambda$writeTrailers$2(TransportState rec$, ChannelFuture x$0) throws Exception {
            rec$.handleWriteFutureFailures(x$0);
        }

        private static /* synthetic */ void lambda$writeHeaders$0(TransportState rec$, ChannelFuture x$0) throws Exception {
            rec$.handleWriteFutureFailures(x$0);
        }
    }
}

