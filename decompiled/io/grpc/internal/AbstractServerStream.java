/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.Decompressor;
import io.grpc.InternalStatus;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.AbstractStream;
import io.grpc.internal.MessageFramer;
import io.grpc.internal.ReadableBuffer;
import io.grpc.internal.ServerStream;
import io.grpc.internal.ServerStreamListener;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.TransportTracer;
import io.grpc.internal.WritableBuffer;
import io.grpc.internal.WritableBufferAllocator;
import javax.annotation.Nullable;

public abstract class AbstractServerStream
extends AbstractStream
implements ServerStream,
MessageFramer.Sink {
    private final MessageFramer framer;
    private final StatsTraceContext statsTraceCtx;
    private boolean outboundClosed;
    private boolean headersSent;

    protected AbstractServerStream(WritableBufferAllocator bufferAllocator, StatsTraceContext statsTraceCtx) {
        this.statsTraceCtx = Preconditions.checkNotNull(statsTraceCtx, "statsTraceCtx");
        this.framer = new MessageFramer(this, bufferAllocator, statsTraceCtx);
    }

    @Override
    protected abstract TransportState transportState();

    protected abstract Sink abstractServerStreamSink();

    @Override
    protected final MessageFramer framer() {
        return this.framer;
    }

    @Override
    public final void writeHeaders(Metadata headers, boolean flush) {
        Preconditions.checkNotNull(headers, "headers");
        this.headersSent = true;
        this.abstractServerStreamSink().writeHeaders(headers, flush);
    }

    @Override
    public final void deliverFrame(WritableBuffer frame, boolean endOfStream, boolean flush, int numMessages) {
        if (frame == null) {
            assert (endOfStream);
            return;
        }
        if (endOfStream) {
            flush = false;
        }
        this.abstractServerStreamSink().writeFrame(frame, flush, numMessages);
    }

    @Override
    public final void close(Status status, Metadata trailers) {
        Preconditions.checkNotNull(status, "status");
        Preconditions.checkNotNull(trailers, "trailers");
        if (!this.outboundClosed) {
            this.outboundClosed = true;
            this.endOfMessages();
            this.addStatusToTrailers(trailers, status);
            this.transportState().setClosedStatus(status);
            this.abstractServerStreamSink().writeTrailers(trailers, this.headersSent, status);
        }
    }

    private void addStatusToTrailers(Metadata trailers, Status status) {
        trailers.discardAll(InternalStatus.CODE_KEY);
        trailers.discardAll(InternalStatus.MESSAGE_KEY);
        trailers.put(InternalStatus.CODE_KEY, status);
        if (status.getDescription() != null) {
            trailers.put(InternalStatus.MESSAGE_KEY, status.getDescription());
        }
    }

    @Override
    public final void cancel(Status status) {
        this.abstractServerStreamSink().cancel(status);
    }

    @Override
    public final boolean isReady() {
        return super.isReady();
    }

    @Override
    public final void setDecompressor(Decompressor decompressor) {
        this.transportState().setDecompressor(Preconditions.checkNotNull(decompressor, "decompressor"));
    }

    @Override
    public Attributes getAttributes() {
        return Attributes.EMPTY;
    }

    @Override
    public String getAuthority() {
        return null;
    }

    @Override
    public final void setListener(ServerStreamListener serverStreamListener) {
        this.transportState().setListener(serverStreamListener);
    }

    @Override
    public StatsTraceContext statsTraceContext() {
        return this.statsTraceCtx;
    }

    @Override
    public void setOnReadyThreshold(int numBytes) {
        super.setOnReadyThreshold(numBytes);
    }

    protected static abstract class TransportState
    extends AbstractStream.TransportState {
        private boolean listenerClosed;
        private ServerStreamListener listener;
        private final StatsTraceContext statsTraceCtx;
        private boolean endOfStream = false;
        private boolean deframerClosed = false;
        private boolean immediateCloseRequested = false;
        private Runnable deframerClosedTask;
        @Nullable
        private Status closedStatus;

        protected TransportState(int maxMessageSize, StatsTraceContext statsTraceCtx, TransportTracer transportTracer) {
            super(maxMessageSize, statsTraceCtx, Preconditions.checkNotNull(transportTracer, "transportTracer"));
            this.statsTraceCtx = Preconditions.checkNotNull(statsTraceCtx, "statsTraceCtx");
        }

        public final void setListener(ServerStreamListener listener) {
            Preconditions.checkState(this.listener == null, "setListener should be called only once");
            this.listener = Preconditions.checkNotNull(listener, "listener");
        }

        @Override
        public final void onStreamAllocated() {
            super.onStreamAllocated();
            this.getTransportTracer().reportRemoteStreamStarted();
        }

        @Override
        public void deframerClosed(boolean hasPartialMessage) {
            this.deframerClosed = true;
            if (this.endOfStream && !this.immediateCloseRequested) {
                if (hasPartialMessage) {
                    this.deframeFailed(Status.INTERNAL.withDescription("Encountered end-of-stream mid-frame").asRuntimeException());
                    this.deframerClosedTask = null;
                    return;
                }
                this.listener.halfClosed();
            }
            if (this.deframerClosedTask != null) {
                this.deframerClosedTask.run();
                this.deframerClosedTask = null;
            }
        }

        @Override
        protected ServerStreamListener listener() {
            return this.listener;
        }

        public void inboundDataReceived(ReadableBuffer frame, boolean endOfStream) {
            Preconditions.checkState(!this.endOfStream, "Past end of stream");
            this.deframe(frame);
            if (endOfStream) {
                this.endOfStream = true;
                this.closeDeframer(false);
            }
        }

        public final void transportReportStatus(final Status status) {
            Preconditions.checkArgument(!status.isOk(), "status must not be OK");
            this.onStreamDeallocated();
            if (this.deframerClosed) {
                this.deframerClosedTask = null;
                this.closeListener(status);
            } else {
                this.deframerClosedTask = new Runnable(){

                    @Override
                    public void run() {
                        this.closeListener(status);
                    }
                };
                this.immediateCloseRequested = true;
                this.closeDeframer(true);
            }
        }

        public void complete() {
            this.onStreamDeallocated();
            if (this.deframerClosed) {
                this.deframerClosedTask = null;
                this.closeListener(Status.OK);
            } else {
                this.deframerClosedTask = new Runnable(){

                    @Override
                    public void run() {
                        this.closeListener(Status.OK);
                    }
                };
                this.immediateCloseRequested = true;
                this.closeDeframer(true);
            }
        }

        private void closeListener(Status newStatus) {
            Preconditions.checkState(!newStatus.isOk() || this.closedStatus != null);
            if (!this.listenerClosed) {
                if (!newStatus.isOk()) {
                    this.statsTraceCtx.streamClosed(newStatus);
                    this.getTransportTracer().reportStreamClosed(false);
                } else {
                    this.statsTraceCtx.streamClosed(this.closedStatus);
                    this.getTransportTracer().reportStreamClosed(this.closedStatus.isOk());
                }
                this.listenerClosed = true;
                this.listener().closed(newStatus);
            }
        }

        private void setClosedStatus(Status closeStatus) {
            Preconditions.checkState(this.closedStatus == null, "closedStatus can only be set once");
            this.closedStatus = closeStatus;
        }
    }

    protected static interface Sink {
        public void writeHeaders(Metadata var1, boolean var2);

        public void writeFrame(WritableBuffer var1, boolean var2, int var3);

        public void writeTrailers(Metadata var1, boolean var2, Status var3);

        public void cancel(Status var1);
    }
}

