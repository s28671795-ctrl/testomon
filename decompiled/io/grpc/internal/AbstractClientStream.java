/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.Codec;
import io.grpc.Compressor;
import io.grpc.Deadline;
import io.grpc.Decompressor;
import io.grpc.DecompressorRegistry;
import io.grpc.Grpc;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.AbstractStream;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.Framer;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.GzipInflatingBuffer;
import io.grpc.internal.InsightBuilder;
import io.grpc.internal.MessageFramer;
import io.grpc.internal.ReadableBuffer;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.TransportTracer;
import io.grpc.internal.WritableBuffer;
import io.grpc.internal.WritableBufferAllocator;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public abstract class AbstractClientStream
extends AbstractStream
implements ClientStream,
MessageFramer.Sink {
    private static final Logger log = Logger.getLogger(AbstractClientStream.class.getName());
    private final TransportTracer transportTracer;
    private final Framer framer;
    private boolean shouldBeCountedForInUse;
    private boolean useGet;
    private Metadata headers;
    private volatile boolean cancelled;

    protected AbstractClientStream(WritableBufferAllocator bufferAllocator, StatsTraceContext statsTraceCtx, TransportTracer transportTracer, Metadata headers, CallOptions callOptions, boolean useGet) {
        Preconditions.checkNotNull(headers, "headers");
        this.transportTracer = Preconditions.checkNotNull(transportTracer, "transportTracer");
        this.shouldBeCountedForInUse = GrpcUtil.shouldBeCountedForInUse(callOptions);
        this.useGet = useGet;
        if (!useGet) {
            this.framer = new MessageFramer(this, bufferAllocator, statsTraceCtx);
            this.headers = headers;
        } else {
            this.framer = new GetFramer(headers, statsTraceCtx);
        }
    }

    @Override
    public void setDeadline(Deadline deadline) {
        this.headers.discardAll(GrpcUtil.TIMEOUT_KEY);
        long effectiveTimeout = Math.max(0L, deadline.timeRemaining(TimeUnit.NANOSECONDS));
        this.headers.put(GrpcUtil.TIMEOUT_KEY, effectiveTimeout);
    }

    @Override
    public void setMaxOutboundMessageSize(int maxSize) {
        this.framer.setMaxOutboundMessageSize(maxSize);
    }

    @Override
    public void setMaxInboundMessageSize(int maxSize) {
        this.transportState().setMaxInboundMessageSize(maxSize);
    }

    @Override
    public final void setFullStreamDecompression(boolean fullStreamDecompression) {
        this.transportState().setFullStreamDecompression(fullStreamDecompression);
    }

    @Override
    public final void setDecompressorRegistry(DecompressorRegistry decompressorRegistry) {
        this.transportState().setDecompressorRegistry(decompressorRegistry);
    }

    @Override
    protected abstract TransportState transportState();

    @Override
    public final void start(ClientStreamListener listener) {
        this.transportState().setListener(listener);
        if (!this.useGet) {
            this.abstractClientStreamSink().writeHeaders(this.headers, null);
            this.headers = null;
        }
    }

    protected abstract Sink abstractClientStreamSink();

    @Override
    protected final Framer framer() {
        return this.framer;
    }

    public final boolean shouldBeCountedForInUse() {
        return this.shouldBeCountedForInUse;
    }

    @Override
    public final void deliverFrame(WritableBuffer frame, boolean endOfStream, boolean flush, int numMessages) {
        Preconditions.checkArgument(frame != null || endOfStream, "null frame before EOS");
        this.abstractClientStreamSink().writeFrame(frame, endOfStream, flush, numMessages);
    }

    @Override
    public final void halfClose() {
        if (!this.transportState().isOutboundClosed()) {
            this.transportState().setOutboundClosed();
            this.endOfMessages();
        }
    }

    @Override
    public final void cancel(Status reason) {
        Preconditions.checkArgument(!reason.isOk(), "Should not cancel with OK status");
        this.cancelled = true;
        this.abstractClientStreamSink().cancel(reason);
    }

    @Override
    public final boolean isReady() {
        return super.isReady() && !this.cancelled;
    }

    @Override
    public final void appendTimeoutInsight(InsightBuilder insight) {
        Attributes attrs = this.getAttributes();
        insight.appendKeyValue("remote_addr", attrs.get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR));
    }

    protected TransportTracer getTransportTracer() {
        return this.transportTracer;
    }

    private class GetFramer
    implements Framer {
        private Metadata headers;
        private boolean closed;
        private final StatsTraceContext statsTraceCtx;
        private byte[] payload;

        public GetFramer(Metadata headers, StatsTraceContext statsTraceCtx) {
            this.headers = Preconditions.checkNotNull(headers, "headers");
            this.statsTraceCtx = Preconditions.checkNotNull(statsTraceCtx, "statsTraceCtx");
        }

        @Override
        public void writePayload(InputStream message) {
            Preconditions.checkState(this.payload == null, "writePayload should not be called multiple times");
            try {
                this.payload = ByteStreams.toByteArray(message);
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.statsTraceCtx.outboundMessage(0);
            this.statsTraceCtx.outboundMessageSent(0, this.payload.length, this.payload.length);
            this.statsTraceCtx.outboundUncompressedSize(this.payload.length);
            this.statsTraceCtx.outboundWireSize(this.payload.length);
        }

        @Override
        public void flush() {
        }

        @Override
        public boolean isClosed() {
            return this.closed;
        }

        @Override
        public void close() {
            this.closed = true;
            Preconditions.checkState(this.payload != null, "Lack of request message. GET request is only supported for unary requests");
            AbstractClientStream.this.abstractClientStreamSink().writeHeaders(this.headers, this.payload);
            this.payload = null;
            this.headers = null;
        }

        @Override
        public void dispose() {
            this.closed = true;
            this.payload = null;
            this.headers = null;
        }

        @Override
        public Framer setMessageCompression(boolean enable) {
            return this;
        }

        @Override
        public Framer setCompressor(Compressor compressor) {
            return this;
        }

        @Override
        public void setMaxOutboundMessageSize(int maxSize) {
        }
    }

    protected static abstract class TransportState
    extends AbstractStream.TransportState {
        private final StatsTraceContext statsTraceCtx;
        private boolean listenerClosed;
        private ClientStreamListener listener;
        private boolean fullStreamDecompression;
        private DecompressorRegistry decompressorRegistry = DecompressorRegistry.getDefaultInstance();
        private boolean deframerClosed = false;
        private Runnable deframerClosedTask;
        private volatile boolean outboundClosed;
        private boolean statusReported;
        private boolean statusReportedIsOk;

        protected TransportState(int maxMessageSize, StatsTraceContext statsTraceCtx, TransportTracer transportTracer, CallOptions options) {
            super(maxMessageSize, statsTraceCtx, transportTracer);
            this.statsTraceCtx = Preconditions.checkNotNull(statsTraceCtx, "statsTraceCtx");
            if (options.getOnReadyThreshold() != null) {
                this.setOnReadyThreshold(options.getOnReadyThreshold());
            }
        }

        private void setFullStreamDecompression(boolean fullStreamDecompression) {
            this.fullStreamDecompression = fullStreamDecompression;
        }

        private void setDecompressorRegistry(DecompressorRegistry decompressorRegistry) {
            Preconditions.checkState(this.listener == null, "Already called start");
            this.decompressorRegistry = Preconditions.checkNotNull(decompressorRegistry, "decompressorRegistry");
        }

        @VisibleForTesting
        public final void setListener(ClientStreamListener listener) {
            Preconditions.checkState(this.listener == null, "Already called setListener");
            this.listener = Preconditions.checkNotNull(listener, "listener");
        }

        @Override
        public void deframerClosed(boolean hasPartialMessage) {
            Preconditions.checkState(this.statusReported, "status should have been reported on deframer closed");
            this.deframerClosed = true;
            if (this.statusReportedIsOk && hasPartialMessage) {
                this.transportReportStatus(Status.INTERNAL.withDescription("Encountered end-of-stream mid-frame"), true, new Metadata());
            }
            if (this.deframerClosedTask != null) {
                this.deframerClosedTask.run();
                this.deframerClosedTask = null;
            }
        }

        @Override
        protected final ClientStreamListener listener() {
            return this.listener;
        }

        private final void setOutboundClosed() {
            this.outboundClosed = true;
        }

        protected final boolean isOutboundClosed() {
            return this.outboundClosed;
        }

        protected void inboundHeadersReceived(Metadata headers) {
            String messageEncoding;
            Preconditions.checkState(!this.statusReported, "Received headers on closed stream");
            this.statsTraceCtx.clientInboundHeaders(headers);
            boolean compressedStream = false;
            String streamEncoding = headers.get(GrpcUtil.CONTENT_ENCODING_KEY);
            if (this.fullStreamDecompression && streamEncoding != null) {
                if (streamEncoding.equalsIgnoreCase("gzip")) {
                    this.setFullStreamDecompressor(new GzipInflatingBuffer());
                    compressedStream = true;
                } else if (!streamEncoding.equalsIgnoreCase("identity")) {
                    this.deframeFailed(Status.INTERNAL.withDescription(String.format("Can't find full stream decompressor for %s", streamEncoding)).asRuntimeException());
                    return;
                }
            }
            if ((messageEncoding = headers.get(GrpcUtil.MESSAGE_ENCODING_KEY)) != null) {
                Decompressor decompressor = this.decompressorRegistry.lookupDecompressor(messageEncoding);
                if (decompressor == null) {
                    this.deframeFailed(Status.INTERNAL.withDescription(String.format("Can't find decompressor for %s", messageEncoding)).asRuntimeException());
                    return;
                }
                if (decompressor != Codec.Identity.NONE) {
                    if (compressedStream) {
                        this.deframeFailed(Status.INTERNAL.withDescription("Full stream and gRPC message encoding cannot both be set").asRuntimeException());
                        return;
                    }
                    this.setDecompressor(decompressor);
                }
            }
            this.listener().headersRead(headers);
        }

        protected void inboundDataReceived(ReadableBuffer frame) {
            Preconditions.checkNotNull(frame, "frame");
            boolean needToCloseFrame = true;
            try {
                if (this.statusReported) {
                    log.log(Level.INFO, "Received data on closed stream");
                    return;
                }
                needToCloseFrame = false;
                this.deframe(frame);
            }
            finally {
                if (needToCloseFrame) {
                    frame.close();
                }
            }
        }

        protected void inboundTrailersReceived(Metadata trailers, Status status) {
            Preconditions.checkNotNull(status, "status");
            Preconditions.checkNotNull(trailers, "trailers");
            if (this.statusReported) {
                log.log(Level.INFO, "Received trailers on closed stream:\n {1}\n {2}", new Object[]{status, trailers});
                return;
            }
            this.statsTraceCtx.clientInboundTrailers(trailers);
            this.transportReportStatus(status, false, trailers);
        }

        public final void transportReportStatus(Status status, boolean stopDelivery, Metadata trailers) {
            this.transportReportStatus(status, ClientStreamListener.RpcProgress.PROCESSED, stopDelivery, trailers);
        }

        public final void transportReportStatus(final Status status, final ClientStreamListener.RpcProgress rpcProgress, boolean stopDelivery, final Metadata trailers) {
            Preconditions.checkNotNull(status, "status");
            Preconditions.checkNotNull(trailers, "trailers");
            if (this.statusReported && !stopDelivery) {
                return;
            }
            this.statusReported = true;
            this.statusReportedIsOk = status.isOk();
            this.onStreamDeallocated();
            if (this.deframerClosed) {
                this.deframerClosedTask = null;
                this.closeListener(status, rpcProgress, trailers);
            } else {
                this.deframerClosedTask = new Runnable(){

                    @Override
                    public void run() {
                        this.closeListener(status, rpcProgress, trailers);
                    }
                };
                this.closeDeframer(stopDelivery);
            }
        }

        private void closeListener(Status status, ClientStreamListener.RpcProgress rpcProgress, Metadata trailers) {
            if (!this.listenerClosed) {
                this.listenerClosed = true;
                this.statsTraceCtx.streamClosed(status);
                if (this.getTransportTracer() != null) {
                    this.getTransportTracer().reportStreamClosed(status.isOk());
                }
                this.listener().closed(status, rpcProgress, trailers);
            }
        }
    }

    protected static interface Sink {
        public void writeHeaders(Metadata var1, @Nullable byte[] var2);

        public void writeFrame(@Nullable WritableBuffer var1, boolean var2, boolean var3, int var4);

        public void cancel(Status var1);
    }
}

