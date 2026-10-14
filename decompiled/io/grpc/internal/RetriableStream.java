/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.ClientStreamTracer;
import io.grpc.Compressor;
import io.grpc.Deadline;
import io.grpc.DecompressorRegistry;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.SynchronizationContext;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.HedgingPolicy;
import io.grpc.internal.InsightBuilder;
import io.grpc.internal.NoopClientStream;
import io.grpc.internal.RetryPolicy;
import io.grpc.internal.StreamListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.CheckForNull;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

abstract class RetriableStream<ReqT>
implements ClientStream {
    @VisibleForTesting
    static final Metadata.Key<String> GRPC_PREVIOUS_RPC_ATTEMPTS = Metadata.Key.of("grpc-previous-rpc-attempts", Metadata.ASCII_STRING_MARSHALLER);
    @VisibleForTesting
    static final Metadata.Key<String> GRPC_RETRY_PUSHBACK_MS = Metadata.Key.of("grpc-retry-pushback-ms", Metadata.ASCII_STRING_MARSHALLER);
    private static final Status CANCELLED_BECAUSE_COMMITTED = Status.CANCELLED.withDescription("Stream thrown away because RetriableStream committed");
    private final MethodDescriptor<ReqT, ?> method;
    private final Executor callExecutor;
    private final Executor listenerSerializeExecutor = new SynchronizationContext(new Thread.UncaughtExceptionHandler(){

        @Override
        public void uncaughtException(Thread t2, Throwable e) {
            throw Status.fromThrowable(e).withDescription("Uncaught exception in the SynchronizationContext. Re-thrown.").asRuntimeException();
        }
    });
    private final ScheduledExecutorService scheduledExecutorService;
    private final Metadata headers;
    @Nullable
    private final RetryPolicy retryPolicy;
    @Nullable
    private final HedgingPolicy hedgingPolicy;
    private final boolean isHedging;
    private final Object lock = new Object();
    private final ChannelBufferMeter channelBufferUsed;
    private final long perRpcBufferLimit;
    private final long channelBufferLimit;
    @Nullable
    private final Throttle throttle;
    @GuardedBy(value="lock")
    private final InsightBuilder closedSubstreamsInsight = new InsightBuilder();
    private volatile State state = new State(new ArrayList<BufferEntry>(8), Collections.emptyList(), null, null, false, false, false, 0);
    private final AtomicBoolean noMoreTransparentRetry = new AtomicBoolean();
    private final AtomicInteger localOnlyTransparentRetries = new AtomicInteger();
    private final AtomicInteger inFlightSubStreams = new AtomicInteger();
    private SavedCloseMasterListenerReason savedCloseMasterListenerReason;
    @GuardedBy(value="lock")
    private long perRpcBufferUsed;
    private ClientStreamListener masterListener;
    @GuardedBy(value="lock")
    private FutureCanceller scheduledRetry;
    @GuardedBy(value="lock")
    private FutureCanceller scheduledHedging;
    private long nextBackoffIntervalNanos;
    private Status cancellationStatus;
    private boolean isClosed;
    private static Random random = new Random();

    RetriableStream(MethodDescriptor<ReqT, ?> method2, Metadata headers, ChannelBufferMeter channelBufferUsed, long perRpcBufferLimit, long channelBufferLimit, Executor callExecutor, ScheduledExecutorService scheduledExecutorService, @Nullable RetryPolicy retryPolicy, @Nullable HedgingPolicy hedgingPolicy, @Nullable Throttle throttle) {
        this.method = method2;
        this.channelBufferUsed = channelBufferUsed;
        this.perRpcBufferLimit = perRpcBufferLimit;
        this.channelBufferLimit = channelBufferLimit;
        this.callExecutor = callExecutor;
        this.scheduledExecutorService = scheduledExecutorService;
        this.headers = headers;
        this.retryPolicy = retryPolicy;
        if (retryPolicy != null) {
            this.nextBackoffIntervalNanos = retryPolicy.initialBackoffNanos;
        }
        this.hedgingPolicy = hedgingPolicy;
        Preconditions.checkArgument(retryPolicy == null || hedgingPolicy == null, "Should not provide both retryPolicy and hedgingPolicy");
        this.isHedging = hedgingPolicy != null;
        this.throttle = throttle;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @CheckReturnValue
    private Runnable commit(final Substream winningSubstream) {
        Object object = this.lock;
        synchronized (object) {
            Future<?> hedgingFuture;
            Future<?> retryFuture;
            boolean wasCancelled;
            if (this.state.winningSubstream != null) {
                return null;
            }
            final Collection<Substream> savedDrainedSubstreams = this.state.drainedSubstreams;
            this.state = this.state.committed(winningSubstream);
            this.channelBufferUsed.addAndGet(-this.perRpcBufferUsed);
            boolean bl = wasCancelled = this.scheduledRetry != null ? this.scheduledRetry.isCancelled() : false;
            if (this.scheduledRetry != null) {
                retryFuture = this.scheduledRetry.markCancelled();
                this.scheduledRetry = null;
            } else {
                retryFuture = null;
            }
            if (this.scheduledHedging != null) {
                hedgingFuture = this.scheduledHedging.markCancelled();
                this.scheduledHedging = null;
            } else {
                hedgingFuture = null;
            }
            class CommitTask
            implements Runnable {
                CommitTask() {
                }

                @Override
                public void run() {
                    for (Substream substream : savedDrainedSubstreams) {
                        if (substream == winningSubstream) continue;
                        substream.stream.cancel(CANCELLED_BECAUSE_COMMITTED);
                    }
                    if (retryFuture != null) {
                        retryFuture.cancel(false);
                        if (!wasCancelled && RetriableStream.this.inFlightSubStreams.decrementAndGet() == Integer.MIN_VALUE) {
                            assert (RetriableStream.this.savedCloseMasterListenerReason != null);
                            RetriableStream.this.listenerSerializeExecutor.execute(new Runnable(){

                                @Override
                                public void run() {
                                    RetriableStream.this.isClosed = true;
                                    RetriableStream.this.masterListener.closed(RetriableStream.this.savedCloseMasterListenerReason.status, RetriableStream.this.savedCloseMasterListenerReason.progress, RetriableStream.this.savedCloseMasterListenerReason.metadata);
                                }
                            });
                        }
                    }
                    if (hedgingFuture != null) {
                        hedgingFuture.cancel(false);
                    }
                    RetriableStream.this.postCommit();
                }
            }
            return new CommitTask();
        }
    }

    abstract void postCommit();

    private void commitAndRun(Substream winningSubstream) {
        Runnable postCommitTask = this.commit(winningSubstream);
        if (postCommitTask != null) {
            this.callExecutor.execute(postCommitTask);
        }
    }

    @Nullable
    private Substream createSubstream(int previousAttemptCount, boolean isTransparentRetry) {
        int inFlight;
        do {
            if ((inFlight = this.inFlightSubStreams.get()) >= 0) continue;
            return null;
        } while (!this.inFlightSubStreams.compareAndSet(inFlight, inFlight + 1));
        Substream sub = new Substream(previousAttemptCount);
        final BufferSizeTracer bufferSizeTracer = new BufferSizeTracer(sub);
        ClientStreamTracer.Factory tracerFactory = new ClientStreamTracer.Factory(){

            @Override
            public ClientStreamTracer newClientStreamTracer(ClientStreamTracer.StreamInfo info, Metadata headers) {
                return bufferSizeTracer;
            }
        };
        Metadata newHeaders = this.updateHeaders(this.headers, previousAttemptCount);
        sub.stream = this.newSubstream(newHeaders, tracerFactory, previousAttemptCount, isTransparentRetry);
        return sub;
    }

    abstract ClientStream newSubstream(Metadata var1, ClientStreamTracer.Factory var2, int var3, boolean var4);

    @VisibleForTesting
    final Metadata updateHeaders(Metadata originalHeaders, int previousAttemptCount) {
        Metadata newHeaders = new Metadata();
        newHeaders.merge(originalHeaders);
        if (previousAttemptCount > 0) {
            newHeaders.put(GRPC_PREVIOUS_RPC_ATTEMPTS, String.valueOf(previousAttemptCount));
        }
        return newHeaders;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void drain(Substream substream) {
        int index = 0;
        int chunk = 128;
        ArrayList<BufferEntry> list = null;
        boolean streamStarted = false;
        Runnable onReadyRunnable = null;
        block3: while (true) {
            State savedState;
            Iterator iterator2 = this.lock;
            synchronized (iterator2) {
                savedState = this.state;
                if (savedState.winningSubstream != null && savedState.winningSubstream != substream) {
                    break;
                }
                if (savedState.cancelled) {
                    break;
                }
                if (index == savedState.buffer.size()) {
                    this.state = savedState.substreamDrained(substream);
                    if (!this.isReady()) {
                        return;
                    }
                    onReadyRunnable = new Runnable(){

                        @Override
                        public void run() {
                            if (!RetriableStream.this.isClosed) {
                                RetriableStream.this.masterListener.onReady();
                            }
                        }
                    };
                    break;
                }
                if (substream.closed) {
                    return;
                }
                int stop = Math.min(index + chunk, savedState.buffer.size());
                if (list == null) {
                    list = new ArrayList<BufferEntry>(savedState.buffer.subList(index, stop));
                } else {
                    list.clear();
                    list.addAll(savedState.buffer.subList(index, stop));
                }
                index = stop;
            }
            iterator2 = list.iterator();
            do {
                if (!iterator2.hasNext()) continue block3;
                BufferEntry bufferEntry = (BufferEntry)iterator2.next();
                bufferEntry.runWith(substream);
                if (bufferEntry instanceof StartEntry) {
                    streamStarted = true;
                }
                savedState = this.state;
            } while ((savedState.winningSubstream == null || savedState.winningSubstream == substream) && !savedState.cancelled);
        }
        if (onReadyRunnable != null) {
            this.listenerSerializeExecutor.execute(onReadyRunnable);
            return;
        }
        if (!streamStarted) {
            substream.stream.start(new Sublistener(substream));
        }
        substream.stream.cancel(this.state.winningSubstream == substream ? this.cancellationStatus : CANCELLED_BECAUSE_COMMITTED);
    }

    @CheckReturnValue
    @Nullable
    abstract Status prestart();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void start(ClientStreamListener listener) {
        this.masterListener = listener;
        Status shutdownStatus = this.prestart();
        if (shutdownStatus != null) {
            this.cancel(shutdownStatus);
            return;
        }
        Object object = this.lock;
        synchronized (object) {
            this.state.buffer.add(new StartEntry());
        }
        Substream substream = this.createSubstream(0, false);
        if (substream == null) {
            return;
        }
        if (this.isHedging) {
            FutureCanceller scheduledHedgingRef = null;
            Object object2 = this.lock;
            synchronized (object2) {
                this.state = this.state.addActiveHedge(substream);
                if (this.hasPotentialHedging(this.state) && (this.throttle == null || this.throttle.isAboveThreshold())) {
                    this.scheduledHedging = scheduledHedgingRef = new FutureCanceller(this.lock);
                }
            }
            if (scheduledHedgingRef != null) {
                scheduledHedgingRef.setFuture(this.scheduledExecutorService.schedule(new HedgingRunnable(scheduledHedgingRef), this.hedgingPolicy.hedgingDelayNanos, TimeUnit.NANOSECONDS));
            }
        }
        this.drain(substream);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void pushbackHedging(@Nullable Integer delayMillis) {
        FutureCanceller future;
        Future<?> futureToBeCancelled;
        if (delayMillis == null) {
            return;
        }
        if (delayMillis < 0) {
            this.freezeHedging();
            return;
        }
        Object object = this.lock;
        synchronized (object) {
            if (this.scheduledHedging == null) {
                return;
            }
            futureToBeCancelled = this.scheduledHedging.markCancelled();
            this.scheduledHedging = future = new FutureCanceller(this.lock);
        }
        if (futureToBeCancelled != null) {
            futureToBeCancelled.cancel(false);
        }
        future.setFuture(this.scheduledExecutorService.schedule(new HedgingRunnable(future), (long)delayMillis.intValue(), TimeUnit.MILLISECONDS));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void cancel(Status reason) {
        Substream noopSubstream = new Substream(0);
        noopSubstream.stream = new NoopClientStream();
        Runnable runnable2 = this.commit(noopSubstream);
        if (runnable2 != null) {
            Object object = this.lock;
            synchronized (object) {
                this.state = this.state.substreamDrained(noopSubstream);
            }
            runnable2.run();
            this.safeCloseMasterListener(reason, ClientStreamListener.RpcProgress.PROCESSED, new Metadata());
            return;
        }
        Substream winningSubstreamToCancel = null;
        Object object = this.lock;
        synchronized (object) {
            if (this.state.drainedSubstreams.contains(this.state.winningSubstream)) {
                winningSubstreamToCancel = this.state.winningSubstream;
            } else {
                this.cancellationStatus = reason;
            }
            this.state = this.state.cancelled();
        }
        if (winningSubstreamToCancel != null) {
            winningSubstreamToCancel.stream.cancel(reason);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void delayOrExecute(BufferEntry bufferEntry) {
        Collection<Substream> savedDrainedSubstreams;
        Iterator<Substream> iterator2 = this.lock;
        synchronized (iterator2) {
            if (!this.state.passThrough) {
                this.state.buffer.add(bufferEntry);
            }
            savedDrainedSubstreams = this.state.drainedSubstreams;
        }
        for (Substream substream : savedDrainedSubstreams) {
            bufferEntry.runWith(substream);
        }
    }

    @Override
    public final void writeMessage(InputStream message) {
        throw new IllegalStateException("RetriableStream.writeMessage() should not be called directly");
    }

    final void sendMessage(final ReqT message) {
        State savedState = this.state;
        if (savedState.passThrough) {
            savedState.winningSubstream.stream.writeMessage(this.method.streamRequest(message));
            return;
        }
        class SendMessageEntry
        implements BufferEntry {
            SendMessageEntry() {
            }

            @Override
            public void runWith(Substream substream) {
                substream.stream.writeMessage(RetriableStream.this.method.streamRequest(message));
                substream.stream.flush();
            }
        }
        this.delayOrExecute(new SendMessageEntry());
    }

    @Override
    public final void request(final int numMessages) {
        State savedState = this.state;
        if (savedState.passThrough) {
            savedState.winningSubstream.stream.request(numMessages);
            return;
        }
        class RequestEntry
        implements BufferEntry {
            RequestEntry() {
            }

            @Override
            public void runWith(Substream substream) {
                substream.stream.request(numMessages);
            }
        }
        this.delayOrExecute(new RequestEntry());
    }

    @Override
    public final void flush() {
        State savedState = this.state;
        if (savedState.passThrough) {
            savedState.winningSubstream.stream.flush();
            return;
        }
        class FlushEntry
        implements BufferEntry {
            FlushEntry() {
            }

            @Override
            public void runWith(Substream substream) {
                substream.stream.flush();
            }
        }
        this.delayOrExecute(new FlushEntry());
    }

    @Override
    public final boolean isReady() {
        for (Substream substream : this.state.drainedSubstreams) {
            if (!substream.stream.isReady()) continue;
            return true;
        }
        return false;
    }

    @Override
    public void optimizeForDirectExecutor() {
        class OptimizeDirectEntry
        implements BufferEntry {
            OptimizeDirectEntry() {
            }

            @Override
            public void runWith(Substream substream) {
                substream.stream.optimizeForDirectExecutor();
            }
        }
        this.delayOrExecute(new OptimizeDirectEntry());
    }

    @Override
    public final void setCompressor(final Compressor compressor) {
        class CompressorEntry
        implements BufferEntry {
            CompressorEntry() {
            }

            @Override
            public void runWith(Substream substream) {
                substream.stream.setCompressor(compressor);
            }
        }
        this.delayOrExecute(new CompressorEntry());
    }

    @Override
    public final void setFullStreamDecompression(final boolean fullStreamDecompression) {
        class FullStreamDecompressionEntry
        implements BufferEntry {
            FullStreamDecompressionEntry() {
            }

            @Override
            public void runWith(Substream substream) {
                substream.stream.setFullStreamDecompression(fullStreamDecompression);
            }
        }
        this.delayOrExecute(new FullStreamDecompressionEntry());
    }

    @Override
    public final void setMessageCompression(final boolean enable) {
        class MessageCompressionEntry
        implements BufferEntry {
            MessageCompressionEntry() {
            }

            @Override
            public void runWith(Substream substream) {
                substream.stream.setMessageCompression(enable);
            }
        }
        this.delayOrExecute(new MessageCompressionEntry());
    }

    @Override
    public final void halfClose() {
        class HalfCloseEntry
        implements BufferEntry {
            HalfCloseEntry() {
            }

            @Override
            public void runWith(Substream substream) {
                substream.stream.halfClose();
            }
        }
        this.delayOrExecute(new HalfCloseEntry());
    }

    @Override
    public final void setAuthority(final String authority) {
        class AuthorityEntry
        implements BufferEntry {
            AuthorityEntry() {
            }

            @Override
            public void runWith(Substream substream) {
                substream.stream.setAuthority(authority);
            }
        }
        this.delayOrExecute(new AuthorityEntry());
    }

    @Override
    public final void setDecompressorRegistry(final DecompressorRegistry decompressorRegistry) {
        class DecompressorRegistryEntry
        implements BufferEntry {
            DecompressorRegistryEntry() {
            }

            @Override
            public void runWith(Substream substream) {
                substream.stream.setDecompressorRegistry(decompressorRegistry);
            }
        }
        this.delayOrExecute(new DecompressorRegistryEntry());
    }

    @Override
    public final void setMaxInboundMessageSize(final int maxSize) {
        class MaxInboundMessageSizeEntry
        implements BufferEntry {
            MaxInboundMessageSizeEntry() {
            }

            @Override
            public void runWith(Substream substream) {
                substream.stream.setMaxInboundMessageSize(maxSize);
            }
        }
        this.delayOrExecute(new MaxInboundMessageSizeEntry());
    }

    @Override
    public final void setMaxOutboundMessageSize(final int maxSize) {
        class MaxOutboundMessageSizeEntry
        implements BufferEntry {
            MaxOutboundMessageSizeEntry() {
            }

            @Override
            public void runWith(Substream substream) {
                substream.stream.setMaxOutboundMessageSize(maxSize);
            }
        }
        this.delayOrExecute(new MaxOutboundMessageSizeEntry());
    }

    @Override
    public final void setDeadline(final Deadline deadline) {
        class DeadlineEntry
        implements BufferEntry {
            DeadlineEntry() {
            }

            @Override
            public void runWith(Substream substream) {
                substream.stream.setDeadline(deadline);
            }
        }
        this.delayOrExecute(new DeadlineEntry());
    }

    @Override
    public final Attributes getAttributes() {
        if (this.state.winningSubstream != null) {
            return this.state.winningSubstream.stream.getAttributes();
        }
        return Attributes.EMPTY;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void appendTimeoutInsight(InsightBuilder insight) {
        State currentState;
        Object object = this.lock;
        synchronized (object) {
            insight.appendKeyValue("closed", this.closedSubstreamsInsight);
            currentState = this.state;
        }
        if (currentState.winningSubstream != null) {
            InsightBuilder substreamInsight = new InsightBuilder();
            currentState.winningSubstream.stream.appendTimeoutInsight(substreamInsight);
            insight.appendKeyValue("committed", substreamInsight);
        } else {
            InsightBuilder openSubstreamsInsight = new InsightBuilder();
            for (Substream sub : currentState.drainedSubstreams) {
                InsightBuilder substreamInsight = new InsightBuilder();
                sub.stream.appendTimeoutInsight(substreamInsight);
                openSubstreamsInsight.append(substreamInsight);
            }
            insight.appendKeyValue("open", openSubstreamsInsight);
        }
    }

    @VisibleForTesting
    static void setRandom(Random random) {
        RetriableStream.random = random;
    }

    @GuardedBy(value="lock")
    private boolean hasPotentialHedging(State state) {
        return state.winningSubstream == null && state.hedgingAttemptCount < this.hedgingPolicy.maxAttempts && !state.hedgingFrozen;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void freezeHedging() {
        Future<?> futureToBeCancelled = null;
        Object object = this.lock;
        synchronized (object) {
            if (this.scheduledHedging != null) {
                futureToBeCancelled = this.scheduledHedging.markCancelled();
                this.scheduledHedging = null;
            }
            this.state = this.state.freezeHedging();
        }
        if (futureToBeCancelled != null) {
            futureToBeCancelled.cancel(false);
        }
    }

    private void safeCloseMasterListener(final Status status, final ClientStreamListener.RpcProgress progress, final Metadata metadata) {
        this.savedCloseMasterListenerReason = new SavedCloseMasterListenerReason(status, progress, metadata);
        if (this.inFlightSubStreams.addAndGet(Integer.MIN_VALUE) == Integer.MIN_VALUE) {
            this.listenerSerializeExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    RetriableStream.this.isClosed = true;
                    RetriableStream.this.masterListener.closed(status, progress, metadata);
                }
            });
        }
    }

    private static final class FutureCanceller {
        final Object lock;
        @GuardedBy(value="lock")
        Future<?> future;
        @GuardedBy(value="lock")
        boolean cancelled;

        FutureCanceller(Object lock) {
            this.lock = lock;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void setFuture(Future<?> future) {
            boolean wasCancelled;
            Object object = this.lock;
            synchronized (object) {
                wasCancelled = this.cancelled;
                if (!wasCancelled) {
                    this.future = future;
                }
            }
            if (wasCancelled) {
                future.cancel(false);
            }
        }

        @CheckForNull
        @GuardedBy(value="lock")
        Future<?> markCancelled() {
            this.cancelled = true;
            return this.future;
        }

        @GuardedBy(value="lock")
        boolean isCancelled() {
            return this.cancelled;
        }
    }

    private static final class HedgingPlan {
        final boolean isHedgeable;
        @Nullable
        final Integer hedgingPushbackMillis;

        public HedgingPlan(boolean isHedgeable, @Nullable Integer hedgingPushbackMillis) {
            this.isHedgeable = isHedgeable;
            this.hedgingPushbackMillis = hedgingPushbackMillis;
        }
    }

    private static final class RetryPlan {
        final boolean shouldRetry;
        final long backoffNanos;

        RetryPlan(boolean shouldRetry, long backoffNanos) {
            this.shouldRetry = shouldRetry;
            this.backoffNanos = backoffNanos;
        }
    }

    static final class Throttle {
        private static final int THREE_DECIMAL_PLACES_SCALE_UP = 1000;
        final int maxTokens;
        final int threshold;
        final int tokenRatio;
        final AtomicInteger tokenCount = new AtomicInteger();

        Throttle(float maxTokens, float tokenRatio) {
            this.tokenRatio = (int)(tokenRatio * 1000.0f);
            this.maxTokens = (int)(maxTokens * 1000.0f);
            this.threshold = this.maxTokens / 2;
            this.tokenCount.set(this.maxTokens);
        }

        @VisibleForTesting
        boolean isAboveThreshold() {
            return this.tokenCount.get() > this.threshold;
        }

        @VisibleForTesting
        boolean onQualifiedFailureThenCheckIsAboveThreshold() {
            int decremented;
            int currentCount;
            boolean updated;
            do {
                if ((currentCount = this.tokenCount.get()) != 0) continue;
                return false;
            } while (!(updated = this.tokenCount.compareAndSet(currentCount, Math.max(decremented = currentCount - 1000, 0))));
            return decremented > this.threshold;
        }

        @VisibleForTesting
        void onSuccess() {
            int incremented;
            boolean updated;
            int currentCount;
            while ((currentCount = this.tokenCount.get()) != this.maxTokens && !(updated = this.tokenCount.compareAndSet(currentCount, Math.min(incremented = currentCount + this.tokenRatio, this.maxTokens)))) {
            }
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Throttle)) {
                return false;
            }
            Throttle that = (Throttle)o;
            return this.maxTokens == that.maxTokens && this.tokenRatio == that.tokenRatio;
        }

        public int hashCode() {
            return Objects.hashCode(this.maxTokens, this.tokenRatio);
        }
    }

    static final class ChannelBufferMeter {
        private final AtomicLong bufferUsed = new AtomicLong();

        ChannelBufferMeter() {
        }

        @VisibleForTesting
        long addAndGet(long newBytesUsed) {
            return this.bufferUsed.addAndGet(newBytesUsed);
        }
    }

    class BufferSizeTracer
    extends ClientStreamTracer {
        private final Substream substream;
        @GuardedBy(value="lock")
        long bufferNeeded;

        BufferSizeTracer(Substream substream) {
            this.substream = substream;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void outboundWireSize(long bytes) {
            if (((RetriableStream)RetriableStream.this).state.winningSubstream != null) {
                return;
            }
            Runnable postCommitTask = null;
            Object object = RetriableStream.this.lock;
            synchronized (object) {
                if (((RetriableStream)RetriableStream.this).state.winningSubstream != null || this.substream.closed) {
                    return;
                }
                this.bufferNeeded += bytes;
                if (this.bufferNeeded <= RetriableStream.this.perRpcBufferUsed) {
                    return;
                }
                if (this.bufferNeeded > RetriableStream.this.perRpcBufferLimit) {
                    this.substream.bufferLimitExceeded = true;
                } else {
                    long savedChannelBufferUsed = RetriableStream.this.channelBufferUsed.addAndGet(this.bufferNeeded - RetriableStream.this.perRpcBufferUsed);
                    RetriableStream.this.perRpcBufferUsed = this.bufferNeeded;
                    if (savedChannelBufferUsed > RetriableStream.this.channelBufferLimit) {
                        this.substream.bufferLimitExceeded = true;
                    }
                }
                if (this.substream.bufferLimitExceeded) {
                    postCommitTask = RetriableStream.this.commit(this.substream);
                }
            }
            if (postCommitTask != null) {
                postCommitTask.run();
            }
        }
    }

    private static final class Substream {
        ClientStream stream;
        boolean closed;
        boolean bufferLimitExceeded;
        final int previousAttemptCount;

        Substream(int previousAttemptCount) {
            this.previousAttemptCount = previousAttemptCount;
        }
    }

    private static final class State {
        final boolean passThrough;
        @Nullable
        final List<BufferEntry> buffer;
        final Collection<Substream> drainedSubstreams;
        final Collection<Substream> activeHedges;
        final int hedgingAttemptCount;
        @Nullable
        final Substream winningSubstream;
        final boolean cancelled;
        final boolean hedgingFrozen;

        State(@Nullable List<BufferEntry> buffer, Collection<Substream> drainedSubstreams, Collection<Substream> activeHedges, @Nullable Substream winningSubstream, boolean cancelled, boolean passThrough, boolean hedgingFrozen, int hedgingAttemptCount) {
            this.buffer = buffer;
            this.drainedSubstreams = Preconditions.checkNotNull(drainedSubstreams, "drainedSubstreams");
            this.winningSubstream = winningSubstream;
            this.activeHedges = activeHedges;
            this.cancelled = cancelled;
            this.passThrough = passThrough;
            this.hedgingFrozen = hedgingFrozen;
            this.hedgingAttemptCount = hedgingAttemptCount;
            Preconditions.checkState(!passThrough || buffer == null, "passThrough should imply buffer is null");
            Preconditions.checkState(!passThrough || winningSubstream != null, "passThrough should imply winningSubstream != null");
            Preconditions.checkState(!passThrough || drainedSubstreams.size() == 1 && drainedSubstreams.contains(winningSubstream) || drainedSubstreams.size() == 0 && winningSubstream.closed, "passThrough should imply winningSubstream is drained");
            Preconditions.checkState(!cancelled || winningSubstream != null, "cancelled should imply committed");
        }

        @CheckReturnValue
        State cancelled() {
            return new State(this.buffer, this.drainedSubstreams, this.activeHedges, this.winningSubstream, true, this.passThrough, this.hedgingFrozen, this.hedgingAttemptCount);
        }

        @CheckReturnValue
        State substreamDrained(Substream substream) {
            Collection<Substream> drainedSubstreams;
            Preconditions.checkState(!this.passThrough, "Already passThrough");
            if (substream.closed) {
                drainedSubstreams = this.drainedSubstreams;
            } else if (this.drainedSubstreams.isEmpty()) {
                drainedSubstreams = Collections.singletonList(substream);
            } else {
                drainedSubstreams = new ArrayList<Substream>(this.drainedSubstreams);
                drainedSubstreams.add(substream);
                drainedSubstreams = Collections.unmodifiableCollection(drainedSubstreams);
            }
            boolean passThrough = this.winningSubstream != null;
            List<BufferEntry> buffer = this.buffer;
            if (passThrough) {
                Preconditions.checkState(this.winningSubstream == substream, "Another RPC attempt has already committed");
                buffer = null;
            }
            return new State(buffer, drainedSubstreams, this.activeHedges, this.winningSubstream, this.cancelled, passThrough, this.hedgingFrozen, this.hedgingAttemptCount);
        }

        @CheckReturnValue
        State substreamClosed(Substream substream) {
            substream.closed = true;
            if (this.drainedSubstreams.contains(substream)) {
                Collection<Substream> drainedSubstreams = new ArrayList<Substream>(this.drainedSubstreams);
                drainedSubstreams.remove(substream);
                drainedSubstreams = Collections.unmodifiableCollection(drainedSubstreams);
                return new State(this.buffer, drainedSubstreams, this.activeHedges, this.winningSubstream, this.cancelled, this.passThrough, this.hedgingFrozen, this.hedgingAttemptCount);
            }
            return this;
        }

        @CheckReturnValue
        State committed(Substream winningSubstream) {
            Collection<Substream> drainedSubstreams;
            Preconditions.checkState(this.winningSubstream == null, "Already committed");
            boolean passThrough = false;
            List<BufferEntry> buffer = this.buffer;
            if (this.drainedSubstreams.contains(winningSubstream)) {
                passThrough = true;
                buffer = null;
                drainedSubstreams = Collections.singleton(winningSubstream);
            } else {
                drainedSubstreams = Collections.emptyList();
            }
            return new State(buffer, drainedSubstreams, this.activeHedges, winningSubstream, this.cancelled, passThrough, this.hedgingFrozen, this.hedgingAttemptCount);
        }

        @CheckReturnValue
        State freezeHedging() {
            if (this.hedgingFrozen) {
                return this;
            }
            return new State(this.buffer, this.drainedSubstreams, this.activeHedges, this.winningSubstream, this.cancelled, this.passThrough, true, this.hedgingAttemptCount);
        }

        @CheckReturnValue
        State addActiveHedge(Substream substream) {
            Collection<Substream> activeHedges;
            Preconditions.checkState(!this.hedgingFrozen, "hedging frozen");
            Preconditions.checkState(this.winningSubstream == null, "already committed");
            if (this.activeHedges == null) {
                activeHedges = Collections.singleton(substream);
            } else {
                activeHedges = new ArrayList<Substream>(this.activeHedges);
                activeHedges.add(substream);
                activeHedges = Collections.unmodifiableCollection(activeHedges);
            }
            int hedgingAttemptCount = this.hedgingAttemptCount + 1;
            return new State(this.buffer, this.drainedSubstreams, activeHedges, this.winningSubstream, this.cancelled, this.passThrough, this.hedgingFrozen, hedgingAttemptCount);
        }

        @CheckReturnValue
        State removeActiveHedge(Substream substream) {
            Collection<Substream> activeHedges = new ArrayList<Substream>(this.activeHedges);
            activeHedges.remove(substream);
            activeHedges = Collections.unmodifiableCollection(activeHedges);
            return new State(this.buffer, this.drainedSubstreams, activeHedges, this.winningSubstream, this.cancelled, this.passThrough, this.hedgingFrozen, this.hedgingAttemptCount);
        }

        @CheckReturnValue
        State replaceActiveHedge(Substream oldOne, Substream newOne) {
            Collection<Substream> activeHedges = new ArrayList<Substream>(this.activeHedges);
            activeHedges.remove(oldOne);
            activeHedges.add(newOne);
            activeHedges = Collections.unmodifiableCollection(activeHedges);
            return new State(this.buffer, this.drainedSubstreams, activeHedges, this.winningSubstream, this.cancelled, this.passThrough, this.hedgingFrozen, this.hedgingAttemptCount);
        }
    }

    private final class Sublistener
    implements ClientStreamListener {
        final Substream substream;

        Sublistener(Substream substream) {
            this.substream = substream;
        }

        @Override
        public void headersRead(final Metadata headers) {
            if (this.substream.previousAttemptCount > 0) {
                headers.discardAll(GRPC_PREVIOUS_RPC_ATTEMPTS);
                headers.put(GRPC_PREVIOUS_RPC_ATTEMPTS, String.valueOf(this.substream.previousAttemptCount));
            }
            RetriableStream.this.commitAndRun(this.substream);
            if (((RetriableStream)RetriableStream.this).state.winningSubstream == this.substream) {
                if (RetriableStream.this.throttle != null) {
                    RetriableStream.this.throttle.onSuccess();
                }
                RetriableStream.this.listenerSerializeExecutor.execute(new Runnable(){

                    @Override
                    public void run() {
                        RetriableStream.this.masterListener.headersRead(headers);
                    }
                });
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void closed(Status status, ClientStreamListener.RpcProgress rpcProgress, Metadata trailers) {
            Object object = RetriableStream.this.lock;
            synchronized (object) {
                RetriableStream.this.state = RetriableStream.this.state.substreamClosed(this.substream);
                RetriableStream.this.closedSubstreamsInsight.append((Object)status.getCode());
            }
            if (RetriableStream.this.inFlightSubStreams.decrementAndGet() == Integer.MIN_VALUE) {
                assert (RetriableStream.this.savedCloseMasterListenerReason != null);
                RetriableStream.this.listenerSerializeExecutor.execute(new Runnable(){

                    @Override
                    public void run() {
                        RetriableStream.this.isClosed = true;
                        RetriableStream.this.masterListener.closed(RetriableStream.this.savedCloseMasterListenerReason.status, RetriableStream.this.savedCloseMasterListenerReason.progress, RetriableStream.this.savedCloseMasterListenerReason.metadata);
                    }
                });
                return;
            }
            if (this.substream.bufferLimitExceeded) {
                RetriableStream.this.commitAndRun(this.substream);
                if (((RetriableStream)RetriableStream.this).state.winningSubstream == this.substream) {
                    RetriableStream.this.safeCloseMasterListener(status, rpcProgress, trailers);
                }
                return;
            }
            if (rpcProgress == ClientStreamListener.RpcProgress.MISCARRIED && RetriableStream.this.localOnlyTransparentRetries.incrementAndGet() > 1000) {
                RetriableStream.this.commitAndRun(this.substream);
                if (((RetriableStream)RetriableStream.this).state.winningSubstream == this.substream) {
                    Status tooManyTransparentRetries = Status.INTERNAL.withDescription("Too many transparent retries. Might be a bug in gRPC").withCause(status.asRuntimeException());
                    RetriableStream.this.safeCloseMasterListener(tooManyTransparentRetries, rpcProgress, trailers);
                }
                return;
            }
            if (((RetriableStream)RetriableStream.this).state.winningSubstream == null) {
                if (rpcProgress == ClientStreamListener.RpcProgress.MISCARRIED || rpcProgress == ClientStreamListener.RpcProgress.REFUSED && RetriableStream.this.noMoreTransparentRetry.compareAndSet(false, true)) {
                    final Substream newSubstream = RetriableStream.this.createSubstream(this.substream.previousAttemptCount, true);
                    if (newSubstream == null) {
                        return;
                    }
                    if (RetriableStream.this.isHedging) {
                        Object object2 = RetriableStream.this.lock;
                        synchronized (object2) {
                            RetriableStream.this.state = RetriableStream.this.state.replaceActiveHedge(this.substream, newSubstream);
                        }
                    }
                    RetriableStream.this.callExecutor.execute(new Runnable(){

                        @Override
                        public void run() {
                            RetriableStream.this.drain(newSubstream);
                        }
                    });
                    return;
                }
                if (rpcProgress == ClientStreamListener.RpcProgress.DROPPED) {
                    if (RetriableStream.this.isHedging) {
                        RetriableStream.this.freezeHedging();
                    }
                } else {
                    RetriableStream.this.noMoreTransparentRetry.set(true);
                    if (RetriableStream.this.isHedging) {
                        HedgingPlan hedgingPlan = this.makeHedgingDecision(status, trailers);
                        if (hedgingPlan.isHedgeable) {
                            RetriableStream.this.pushbackHedging(hedgingPlan.hedgingPushbackMillis);
                        }
                        Object object3 = RetriableStream.this.lock;
                        synchronized (object3) {
                            RetriableStream.this.state = RetriableStream.this.state.removeActiveHedge(this.substream);
                            if (hedgingPlan.isHedgeable && (RetriableStream.this.hasPotentialHedging(RetriableStream.this.state) || !((RetriableStream)RetriableStream.this).state.activeHedges.isEmpty())) {
                                return;
                            }
                        }
                    }
                    RetryPlan retryPlan = this.makeRetryDecision(status, trailers);
                    if (retryPlan.shouldRetry) {
                        FutureCanceller scheduledRetryCopy;
                        final Substream newSubstream = RetriableStream.this.createSubstream(this.substream.previousAttemptCount + 1, false);
                        if (newSubstream == null) {
                            return;
                        }
                        Object object4 = RetriableStream.this.lock;
                        synchronized (object4) {
                            scheduledRetryCopy = new FutureCanceller(RetriableStream.this.lock);
                            RetriableStream.this.scheduledRetry = scheduledRetryCopy;
                        }
                        class RetryBackoffRunnable
                        implements Runnable {
                            RetryBackoffRunnable() {
                            }

                            /*
                             * WARNING - Removed try catching itself - possible behaviour change.
                             */
                            @Override
                            public void run() {
                                Object object = scheduledRetryCopy.lock;
                                synchronized (object) {
                                    if (scheduledRetryCopy.isCancelled()) {
                                        return;
                                    }
                                    scheduledRetryCopy.markCancelled();
                                }
                                RetriableStream.this.callExecutor.execute(new Runnable(){

                                    @Override
                                    public void run() {
                                        RetriableStream.this.drain(newSubstream);
                                    }
                                });
                            }
                        }
                        scheduledRetryCopy.setFuture(RetriableStream.this.scheduledExecutorService.schedule(new RetryBackoffRunnable(), retryPlan.backoffNanos, TimeUnit.NANOSECONDS));
                        return;
                    }
                }
            }
            RetriableStream.this.commitAndRun(this.substream);
            if (((RetriableStream)RetriableStream.this).state.winningSubstream == this.substream) {
                RetriableStream.this.safeCloseMasterListener(status, rpcProgress, trailers);
            }
        }

        private RetryPlan makeRetryDecision(Status status, Metadata trailer) {
            if (RetriableStream.this.retryPolicy == null) {
                return new RetryPlan(false, 0L);
            }
            boolean shouldRetry = false;
            long backoffNanos = 0L;
            boolean isRetryableStatusCode = ((RetriableStream)RetriableStream.this).retryPolicy.retryableStatusCodes.contains((Object)status.getCode());
            Integer pushbackMillis = this.getPushbackMills(trailer);
            boolean isThrottled = false;
            if (RetriableStream.this.throttle != null && (isRetryableStatusCode || pushbackMillis != null && pushbackMillis < 0)) {
                boolean bl = isThrottled = !RetriableStream.this.throttle.onQualifiedFailureThenCheckIsAboveThreshold();
            }
            if (((RetriableStream)RetriableStream.this).retryPolicy.maxAttempts > this.substream.previousAttemptCount + 1 && !isThrottled) {
                if (pushbackMillis == null) {
                    if (isRetryableStatusCode) {
                        shouldRetry = true;
                        backoffNanos = (long)((double)RetriableStream.this.nextBackoffIntervalNanos * random.nextDouble());
                        RetriableStream.this.nextBackoffIntervalNanos = Math.min((long)((double)RetriableStream.this.nextBackoffIntervalNanos * ((RetriableStream)RetriableStream.this).retryPolicy.backoffMultiplier), ((RetriableStream)RetriableStream.this).retryPolicy.maxBackoffNanos);
                    }
                } else if (pushbackMillis >= 0) {
                    shouldRetry = true;
                    backoffNanos = TimeUnit.MILLISECONDS.toNanos(pushbackMillis.intValue());
                    RetriableStream.this.nextBackoffIntervalNanos = ((RetriableStream)RetriableStream.this).retryPolicy.initialBackoffNanos;
                }
            }
            return new RetryPlan(shouldRetry, backoffNanos);
        }

        private HedgingPlan makeHedgingDecision(Status status, Metadata trailer) {
            Integer pushbackMillis = this.getPushbackMills(trailer);
            boolean isFatal = !((RetriableStream)RetriableStream.this).hedgingPolicy.nonFatalStatusCodes.contains((Object)status.getCode());
            boolean isThrottled = false;
            if (RetriableStream.this.throttle != null && (!isFatal || pushbackMillis != null && pushbackMillis < 0)) {
                boolean bl = isThrottled = !RetriableStream.this.throttle.onQualifiedFailureThenCheckIsAboveThreshold();
            }
            if (!(isFatal || isThrottled || status.isOk() || pushbackMillis == null || pushbackMillis <= 0)) {
                pushbackMillis = 0;
            }
            return new HedgingPlan(!isFatal && !isThrottled, pushbackMillis);
        }

        @Nullable
        private Integer getPushbackMills(Metadata trailer) {
            String pushbackStr = trailer.get(GRPC_RETRY_PUSHBACK_MS);
            Integer pushbackMillis = null;
            if (pushbackStr != null) {
                try {
                    pushbackMillis = Integer.valueOf(pushbackStr);
                }
                catch (NumberFormatException e) {
                    pushbackMillis = -1;
                }
            }
            return pushbackMillis;
        }

        @Override
        public void messagesAvailable(final StreamListener.MessageProducer producer) {
            State savedState = RetriableStream.this.state;
            Preconditions.checkState(savedState.winningSubstream != null, "Headers should be received prior to messages.");
            if (savedState.winningSubstream != this.substream) {
                GrpcUtil.closeQuietly(producer);
                return;
            }
            RetriableStream.this.listenerSerializeExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    RetriableStream.this.masterListener.messagesAvailable(producer);
                }
            });
        }

        @Override
        public void onReady() {
            if (!RetriableStream.this.isReady()) {
                return;
            }
            RetriableStream.this.listenerSerializeExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    if (!RetriableStream.this.isClosed) {
                        RetriableStream.this.masterListener.onReady();
                    }
                }
            });
        }
    }

    private static interface BufferEntry {
        public void runWith(Substream var1);
    }

    private static final class SavedCloseMasterListenerReason {
        private final Status status;
        private final ClientStreamListener.RpcProgress progress;
        private final Metadata metadata;

        SavedCloseMasterListenerReason(Status status, ClientStreamListener.RpcProgress progress, Metadata metadata) {
            this.status = status;
            this.progress = progress;
            this.metadata = metadata;
        }
    }

    private final class HedgingRunnable
    implements Runnable {
        final FutureCanceller scheduledHedgingRef;

        HedgingRunnable(FutureCanceller scheduledHedging) {
            this.scheduledHedgingRef = scheduledHedging;
        }

        @Override
        public void run() {
            final Substream newSubstream = RetriableStream.this.createSubstream(((RetriableStream)RetriableStream.this).state.hedgingAttemptCount, false);
            if (newSubstream == null) {
                return;
            }
            RetriableStream.this.callExecutor.execute(new Runnable(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public void run() {
                    boolean cancelled = false;
                    FutureCanceller future = null;
                    Object object = RetriableStream.this.lock;
                    synchronized (object) {
                        if (HedgingRunnable.this.scheduledHedgingRef.isCancelled()) {
                            cancelled = true;
                        } else {
                            RetriableStream.this.state = RetriableStream.this.state.addActiveHedge(newSubstream);
                            if (RetriableStream.this.hasPotentialHedging(RetriableStream.this.state) && (RetriableStream.this.throttle == null || RetriableStream.this.throttle.isAboveThreshold())) {
                                future = new FutureCanceller(RetriableStream.this.lock);
                                RetriableStream.this.scheduledHedging = future;
                            } else {
                                RetriableStream.this.state = RetriableStream.this.state.freezeHedging();
                                RetriableStream.this.scheduledHedging = null;
                            }
                        }
                    }
                    if (cancelled) {
                        newSubstream.stream.start(new Sublistener(newSubstream));
                        newSubstream.stream.cancel(Status.CANCELLED.withDescription("Unneeded hedging"));
                        return;
                    }
                    if (future != null) {
                        future.setFuture(RetriableStream.this.scheduledExecutorService.schedule(new HedgingRunnable(future), ((RetriableStream)RetriableStream.this).hedgingPolicy.hedgingDelayNanos, TimeUnit.NANOSECONDS));
                    }
                    RetriableStream.this.drain(newSubstream);
                }
            });
        }
    }

    class StartEntry
    implements BufferEntry {
        StartEntry() {
        }

        @Override
        public void runWith(Substream substream) {
            substream.stream.start(new Sublistener(substream));
        }
    }
}

