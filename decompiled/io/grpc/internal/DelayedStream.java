/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.Compressor;
import io.grpc.Deadline;
import io.grpc.DecompressorRegistry;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.InsightBuilder;
import io.grpc.internal.NoopClientStream;
import io.grpc.internal.StreamListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.CheckReturnValue;
import javax.annotation.concurrent.GuardedBy;

class DelayedStream
implements ClientStream {
    private volatile boolean passThrough;
    private ClientStreamListener listener;
    private ClientStream realStream;
    @GuardedBy(value="this")
    private Status error;
    @GuardedBy(value="this")
    private List<Runnable> pendingCalls = new ArrayList<Runnable>();
    @GuardedBy(value="this")
    private DelayedStreamListener delayedListener;
    @GuardedBy(value="this")
    private long startTimeNanos;
    @GuardedBy(value="this")
    private long streamSetTimeNanos;
    private List<Runnable> preStartPendingCalls = new ArrayList<Runnable>();

    DelayedStream() {
    }

    @Override
    public void setMaxInboundMessageSize(final int maxSize) {
        Preconditions.checkState(this.listener == null, "May only be called before start");
        this.preStartPendingCalls.add(new Runnable(){

            @Override
            public void run() {
                DelayedStream.this.realStream.setMaxInboundMessageSize(maxSize);
            }
        });
    }

    @Override
    public void setMaxOutboundMessageSize(final int maxSize) {
        Preconditions.checkState(this.listener == null, "May only be called before start");
        this.preStartPendingCalls.add(new Runnable(){

            @Override
            public void run() {
                DelayedStream.this.realStream.setMaxOutboundMessageSize(maxSize);
            }
        });
    }

    @Override
    public void setDeadline(final Deadline deadline) {
        Preconditions.checkState(this.listener == null, "May only be called before start");
        this.preStartPendingCalls.add(new Runnable(){

            @Override
            public void run() {
                DelayedStream.this.realStream.setDeadline(deadline);
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void appendTimeoutInsight(InsightBuilder insight) {
        DelayedStream delayedStream = this;
        synchronized (delayedStream) {
            if (this.listener == null) {
                return;
            }
            if (this.realStream != null) {
                insight.appendKeyValue("buffered_nanos", this.streamSetTimeNanos - this.startTimeNanos);
                this.realStream.appendTimeoutInsight(insight);
            } else {
                insight.appendKeyValue("buffered_nanos", System.nanoTime() - this.startTimeNanos);
                insight.append("waiting_for_connection");
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CheckReturnValue
    final Runnable setStream(ClientStream stream) {
        ClientStreamListener savedListener;
        DelayedStream delayedStream = this;
        synchronized (delayedStream) {
            if (this.realStream != null) {
                return null;
            }
            this.setRealStream(Preconditions.checkNotNull(stream, "stream"));
            savedListener = this.listener;
            if (savedListener == null) {
                assert (this.pendingCalls.isEmpty());
                this.pendingCalls = null;
                this.passThrough = true;
            }
        }
        if (savedListener == null) {
            return null;
        }
        this.internalStart(savedListener);
        return new Runnable(){

            @Override
            public void run() {
                DelayedStream.this.drainPendingCalls();
            }
        };
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void drainPendingCalls() {
        assert (this.realStream != null);
        assert (!this.passThrough);
        List<Runnable> toRun = new ArrayList<Runnable>();
        DelayedStreamListener delayedListener = null;
        while (true) {
            DelayedStream delayedStream = this;
            synchronized (delayedStream) {
                if (this.pendingCalls.isEmpty()) {
                    this.pendingCalls = null;
                    this.passThrough = true;
                    delayedListener = this.delayedListener;
                    break;
                }
                ArrayList<Runnable> tmp = toRun;
                toRun = this.pendingCalls;
                this.pendingCalls = tmp;
            }
            for (Runnable runnable2 : toRun) {
                runnable2.run();
            }
            toRun.clear();
        }
        if (delayedListener != null) {
            delayedListener.drainPendingCallbacks();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void delayOrExecute(Runnable runnable2) {
        Preconditions.checkState(this.listener != null, "May only be called after start");
        DelayedStream delayedStream = this;
        synchronized (delayedStream) {
            if (!this.passThrough) {
                this.pendingCalls.add(runnable2);
                return;
            }
        }
        runnable2.run();
    }

    @Override
    public void setAuthority(final String authority) {
        Preconditions.checkState(this.listener == null, "May only be called before start");
        Preconditions.checkNotNull(authority, "authority");
        this.preStartPendingCalls.add(new Runnable(){

            @Override
            public void run() {
                DelayedStream.this.realStream.setAuthority(authority);
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void start(ClientStreamListener listener) {
        boolean savedPassThrough;
        Status savedError;
        Preconditions.checkNotNull(listener, "listener");
        Preconditions.checkState(this.listener == null, "already started");
        DelayedStream delayedStream = this;
        synchronized (delayedStream) {
            savedError = this.error;
            savedPassThrough = this.passThrough;
            if (!savedPassThrough) {
                this.delayedListener = new DelayedStreamListener(listener);
                listener = this.delayedListener;
            }
            this.listener = listener;
            this.startTimeNanos = System.nanoTime();
        }
        if (savedError != null) {
            listener.closed(savedError, ClientStreamListener.RpcProgress.PROCESSED, new Metadata());
            return;
        }
        if (savedPassThrough) {
            this.internalStart(listener);
        }
    }

    private void internalStart(ClientStreamListener listener) {
        for (Runnable runnable2 : this.preStartPendingCalls) {
            runnable2.run();
        }
        this.preStartPendingCalls = null;
        this.realStream.start(listener);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Attributes getAttributes() {
        ClientStream savedRealStream;
        DelayedStream delayedStream = this;
        synchronized (delayedStream) {
            savedRealStream = this.realStream;
        }
        if (savedRealStream != null) {
            return savedRealStream.getAttributes();
        }
        return Attributes.EMPTY;
    }

    @Override
    public void writeMessage(final InputStream message) {
        Preconditions.checkState(this.listener != null, "May only be called after start");
        Preconditions.checkNotNull(message, "message");
        if (this.passThrough) {
            this.realStream.writeMessage(message);
        } else {
            this.delayOrExecute(new Runnable(){

                @Override
                public void run() {
                    DelayedStream.this.realStream.writeMessage(message);
                }
            });
        }
    }

    @Override
    public void flush() {
        Preconditions.checkState(this.listener != null, "May only be called after start");
        if (this.passThrough) {
            this.realStream.flush();
        } else {
            this.delayOrExecute(new Runnable(){

                @Override
                public void run() {
                    DelayedStream.this.realStream.flush();
                }
            });
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void cancel(final Status reason) {
        Preconditions.checkState(this.listener != null, "May only be called after start");
        Preconditions.checkNotNull(reason, "reason");
        boolean delegateToRealStream = true;
        DelayedStream delayedStream = this;
        synchronized (delayedStream) {
            if (this.realStream == null) {
                this.setRealStream(NoopClientStream.INSTANCE);
                delegateToRealStream = false;
                this.error = reason;
            }
        }
        if (delegateToRealStream) {
            this.delayOrExecute(new Runnable(){

                @Override
                public void run() {
                    DelayedStream.this.realStream.cancel(reason);
                }
            });
        } else {
            this.drainPendingCalls();
            this.onEarlyCancellation(reason);
            this.listener.closed(reason, ClientStreamListener.RpcProgress.PROCESSED, new Metadata());
        }
    }

    protected void onEarlyCancellation(Status reason) {
    }

    @GuardedBy(value="this")
    private void setRealStream(ClientStream realStream) {
        Preconditions.checkState(this.realStream == null, "realStream already set to %s", (Object)this.realStream);
        this.realStream = realStream;
        this.streamSetTimeNanos = System.nanoTime();
    }

    @Override
    public void halfClose() {
        Preconditions.checkState(this.listener != null, "May only be called after start");
        this.delayOrExecute(new Runnable(){

            @Override
            public void run() {
                DelayedStream.this.realStream.halfClose();
            }
        });
    }

    @Override
    public void request(final int numMessages) {
        Preconditions.checkState(this.listener != null, "May only be called after start");
        if (this.passThrough) {
            this.realStream.request(numMessages);
        } else {
            this.delayOrExecute(new Runnable(){

                @Override
                public void run() {
                    DelayedStream.this.realStream.request(numMessages);
                }
            });
        }
    }

    @Override
    public void optimizeForDirectExecutor() {
        Preconditions.checkState(this.listener == null, "May only be called before start");
        this.preStartPendingCalls.add(new Runnable(){

            @Override
            public void run() {
                DelayedStream.this.realStream.optimizeForDirectExecutor();
            }
        });
    }

    @Override
    public void setCompressor(final Compressor compressor) {
        Preconditions.checkState(this.listener == null, "May only be called before start");
        Preconditions.checkNotNull(compressor, "compressor");
        this.preStartPendingCalls.add(new Runnable(){

            @Override
            public void run() {
                DelayedStream.this.realStream.setCompressor(compressor);
            }
        });
    }

    @Override
    public void setFullStreamDecompression(final boolean fullStreamDecompression) {
        Preconditions.checkState(this.listener == null, "May only be called before start");
        this.preStartPendingCalls.add(new Runnable(){

            @Override
            public void run() {
                DelayedStream.this.realStream.setFullStreamDecompression(fullStreamDecompression);
            }
        });
    }

    @Override
    public void setDecompressorRegistry(final DecompressorRegistry decompressorRegistry) {
        Preconditions.checkState(this.listener == null, "May only be called before start");
        Preconditions.checkNotNull(decompressorRegistry, "decompressorRegistry");
        this.preStartPendingCalls.add(new Runnable(){

            @Override
            public void run() {
                DelayedStream.this.realStream.setDecompressorRegistry(decompressorRegistry);
            }
        });
    }

    @Override
    public boolean isReady() {
        if (this.passThrough) {
            return this.realStream.isReady();
        }
        return false;
    }

    @Override
    public void setMessageCompression(final boolean enable) {
        Preconditions.checkState(this.listener != null, "May only be called after start");
        if (this.passThrough) {
            this.realStream.setMessageCompression(enable);
        } else {
            this.delayOrExecute(new Runnable(){

                @Override
                public void run() {
                    DelayedStream.this.realStream.setMessageCompression(enable);
                }
            });
        }
    }

    @VisibleForTesting
    ClientStream getRealStream() {
        return this.realStream;
    }

    private static class DelayedStreamListener
    implements ClientStreamListener {
        private final ClientStreamListener realListener;
        private volatile boolean passThrough;
        @GuardedBy(value="this")
        private List<Runnable> pendingCallbacks = new ArrayList<Runnable>();

        public DelayedStreamListener(ClientStreamListener listener) {
            this.realListener = listener;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void delayOrExecute(Runnable runnable2) {
            DelayedStreamListener delayedStreamListener = this;
            synchronized (delayedStreamListener) {
                if (!this.passThrough) {
                    this.pendingCallbacks.add(runnable2);
                    return;
                }
            }
            runnable2.run();
        }

        @Override
        public void messagesAvailable(final StreamListener.MessageProducer producer) {
            if (this.passThrough) {
                this.realListener.messagesAvailable(producer);
            } else {
                this.delayOrExecute(new Runnable(){

                    @Override
                    public void run() {
                        realListener.messagesAvailable(producer);
                    }
                });
            }
        }

        @Override
        public void onReady() {
            if (this.passThrough) {
                this.realListener.onReady();
            } else {
                this.delayOrExecute(new Runnable(){

                    @Override
                    public void run() {
                        realListener.onReady();
                    }
                });
            }
        }

        @Override
        public void headersRead(final Metadata headers) {
            this.delayOrExecute(new Runnable(){

                @Override
                public void run() {
                    realListener.headersRead(headers);
                }
            });
        }

        @Override
        public void closed(final Status status, final ClientStreamListener.RpcProgress rpcProgress, final Metadata trailers) {
            this.delayOrExecute(new Runnable(){

                @Override
                public void run() {
                    realListener.closed(status, rpcProgress, trailers);
                }
            });
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void drainPendingCallbacks() {
            assert (!this.passThrough);
            List<Runnable> toRun = new ArrayList<Runnable>();
            while (true) {
                DelayedStreamListener delayedStreamListener = this;
                synchronized (delayedStreamListener) {
                    if (this.pendingCallbacks.isEmpty()) {
                        this.pendingCallbacks = null;
                        this.passThrough = true;
                        break;
                    }
                    ArrayList<Runnable> tmp = toRun;
                    toRun = this.pendingCallbacks;
                    this.pendingCallbacks = tmp;
                }
                for (Runnable runnable2 : toRun) {
                    runnable2.run();
                }
                toRun.clear();
            }
        }
    }
}

