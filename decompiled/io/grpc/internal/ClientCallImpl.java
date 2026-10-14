/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.ClientCall;
import io.grpc.ClientStreamTracer;
import io.grpc.Codec;
import io.grpc.Compressor;
import io.grpc.CompressorRegistry;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Deadline;
import io.grpc.DecompressorRegistry;
import io.grpc.InternalConfigSelector;
import io.grpc.InternalDecompressorRegistry;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.internal.CallTracer;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.ContextRunnable;
import io.grpc.internal.FailingClientStream;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.InsightBuilder;
import io.grpc.internal.LogExceptionRunnable;
import io.grpc.internal.ManagedChannelServiceConfig;
import io.grpc.internal.NoopClientStream;
import io.grpc.internal.RetriableStream;
import io.grpc.internal.SerializeReentrantCallsDirectExecutor;
import io.grpc.internal.SerializingExecutor;
import io.grpc.internal.StreamListener;
import io.perfmark.Link;
import io.perfmark.PerfMark;
import io.perfmark.Tag;
import io.perfmark.TaskCloseable;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

final class ClientCallImpl<ReqT, RespT>
extends ClientCall<ReqT, RespT> {
    private static final Logger log = Logger.getLogger(ClientCallImpl.class.getName());
    private static final byte[] FULL_STREAM_DECOMPRESSION_ENCODINGS = "gzip".getBytes(Charset.forName("US-ASCII"));
    private static final double NANO_TO_SECS = 1.0 * (double)TimeUnit.SECONDS.toNanos(1L);
    private final MethodDescriptor<ReqT, RespT> method;
    private final Tag tag;
    private final Executor callExecutor;
    private final boolean callExecutorIsDirect;
    private final CallTracer channelCallsTracer;
    private final Context context;
    private CancellationHandler cancellationHandler;
    private final boolean unaryRequest;
    private CallOptions callOptions;
    private ClientStream stream;
    private boolean cancelCalled;
    private boolean halfCloseCalled;
    private final ClientStreamProvider clientStreamProvider;
    private final ScheduledExecutorService deadlineCancellationExecutor;
    private boolean fullStreamDecompression;
    private DecompressorRegistry decompressorRegistry = DecompressorRegistry.getDefaultInstance();
    private CompressorRegistry compressorRegistry = CompressorRegistry.getDefaultInstance();

    ClientCallImpl(MethodDescriptor<ReqT, RespT> method2, Executor executor2, CallOptions callOptions, ClientStreamProvider clientStreamProvider, ScheduledExecutorService deadlineCancellationExecutor, CallTracer channelCallsTracer, @Nullable InternalConfigSelector configSelector) {
        this.method = method2;
        this.tag = PerfMark.createTag(method2.getFullMethodName(), System.identityHashCode(this));
        if (executor2 == MoreExecutors.directExecutor()) {
            this.callExecutor = new SerializeReentrantCallsDirectExecutor();
            this.callExecutorIsDirect = true;
        } else {
            this.callExecutor = new SerializingExecutor(executor2);
            this.callExecutorIsDirect = false;
        }
        this.channelCallsTracer = channelCallsTracer;
        this.context = Context.current();
        this.unaryRequest = method2.getType() == MethodDescriptor.MethodType.UNARY || method2.getType() == MethodDescriptor.MethodType.SERVER_STREAMING;
        this.callOptions = callOptions;
        this.clientStreamProvider = clientStreamProvider;
        this.deadlineCancellationExecutor = deadlineCancellationExecutor;
        PerfMark.event("ClientCall.<init>", this.tag);
    }

    ClientCallImpl<ReqT, RespT> setFullStreamDecompression(boolean fullStreamDecompression) {
        this.fullStreamDecompression = fullStreamDecompression;
        return this;
    }

    ClientCallImpl<ReqT, RespT> setDecompressorRegistry(DecompressorRegistry decompressorRegistry) {
        this.decompressorRegistry = decompressorRegistry;
        return this;
    }

    ClientCallImpl<ReqT, RespT> setCompressorRegistry(CompressorRegistry compressorRegistry) {
        this.compressorRegistry = compressorRegistry;
        return this;
    }

    @VisibleForTesting
    static void prepareHeaders(Metadata headers, DecompressorRegistry decompressorRegistry, Compressor compressor, boolean fullStreamDecompression) {
        headers.discardAll(GrpcUtil.CONTENT_LENGTH_KEY);
        headers.discardAll(GrpcUtil.MESSAGE_ENCODING_KEY);
        if (compressor != Codec.Identity.NONE) {
            headers.put(GrpcUtil.MESSAGE_ENCODING_KEY, compressor.getMessageEncoding());
        }
        headers.discardAll(GrpcUtil.MESSAGE_ACCEPT_ENCODING_KEY);
        byte[] advertisedEncodings = InternalDecompressorRegistry.getRawAdvertisedMessageEncodings(decompressorRegistry);
        if (advertisedEncodings.length != 0) {
            headers.put(GrpcUtil.MESSAGE_ACCEPT_ENCODING_KEY, advertisedEncodings);
        }
        headers.discardAll(GrpcUtil.CONTENT_ENCODING_KEY);
        headers.discardAll(GrpcUtil.CONTENT_ACCEPT_ENCODING_KEY);
        if (fullStreamDecompression) {
            headers.put(GrpcUtil.CONTENT_ACCEPT_ENCODING_KEY, FULL_STREAM_DECOMPRESSION_ENCODINGS);
        }
    }

    @Override
    public void start(ClientCall.Listener<RespT> observer, Metadata headers) {
        try (TaskCloseable ignore = PerfMark.traceTask("ClientCall.start");){
            PerfMark.attachTag(this.tag);
            this.startInternal(observer, headers);
        }
    }

    private void startInternal(ClientCall.Listener<RespT> observer, Metadata headers) {
        boolean deadlineExceeded;
        Compressor compressor;
        Preconditions.checkState(this.stream == null, "Already started");
        Preconditions.checkState(!this.cancelCalled, "call was cancelled");
        Preconditions.checkNotNull(observer, "observer");
        Preconditions.checkNotNull(headers, "headers");
        if (this.context.isCancelled()) {
            this.stream = NoopClientStream.INSTANCE;
            final ClientCall.Listener<RespT> finalObserver = observer;
            class ClosedByContext
            extends ContextRunnable {
                ClosedByContext() {
                    super(ClientCallImpl.this.context);
                }

                @Override
                public void runInContext() {
                    ClientCallImpl.this.closeObserver(finalObserver, Contexts.statusFromCancelled(ClientCallImpl.this.context), new Metadata());
                }
            }
            this.callExecutor.execute(new ClosedByContext());
            return;
        }
        this.applyMethodConfig();
        final String compressorName = this.callOptions.getCompressor();
        if (compressorName != null) {
            compressor = this.compressorRegistry.lookupCompressor(compressorName);
            if (compressor == null) {
                this.stream = NoopClientStream.INSTANCE;
                final ClientCall.Listener<RespT> finalObserver = observer;
                class ClosedByNotFoundCompressor
                extends ContextRunnable {
                    ClosedByNotFoundCompressor() {
                        super(ClientCallImpl.this.context);
                    }

                    @Override
                    public void runInContext() {
                        ClientCallImpl.this.closeObserver(finalObserver, Status.INTERNAL.withDescription(String.format("Unable to find compressor by name %s", compressorName)), new Metadata());
                    }
                }
                this.callExecutor.execute(new ClosedByNotFoundCompressor());
                return;
            }
        } else {
            compressor = Codec.Identity.NONE;
        }
        ClientCallImpl.prepareHeaders(headers, this.decompressorRegistry, compressor, this.fullStreamDecompression);
        Deadline effectiveDeadline = this.effectiveDeadline();
        boolean contextIsDeadlineSource = effectiveDeadline != null && effectiveDeadline.equals(this.context.getDeadline());
        this.cancellationHandler = new CancellationHandler(effectiveDeadline, contextIsDeadlineSource);
        boolean bl = deadlineExceeded = effectiveDeadline != null && this.cancellationHandler.remainingNanos <= 0L;
        if (!deadlineExceeded) {
            this.stream = this.clientStreamProvider.newStream(this.method, this.callOptions, headers, this.context);
        } else {
            ClientStreamTracer[] tracers = GrpcUtil.getClientStreamTracers(this.callOptions, headers, 0, false);
            String deadlineName = contextIsDeadlineSource ? "Context" : "CallOptions";
            Long nameResolutionDelay = this.callOptions.getOption(ClientStreamTracer.NAME_RESOLUTION_DELAYED);
            String description = String.format("ClientCall started after %s deadline was exceeded %.9f seconds ago. Name resolution delay %.9f seconds.", deadlineName, (double)this.cancellationHandler.remainingNanos / NANO_TO_SECS, nameResolutionDelay == null ? 0.0 : (double)nameResolutionDelay.longValue() / NANO_TO_SECS);
            this.stream = new FailingClientStream(Status.DEADLINE_EXCEEDED.withDescription(description), tracers);
        }
        if (this.callExecutorIsDirect) {
            this.stream.optimizeForDirectExecutor();
        }
        if (this.callOptions.getAuthority() != null) {
            this.stream.setAuthority(this.callOptions.getAuthority());
        }
        if (this.callOptions.getMaxInboundMessageSize() != null) {
            this.stream.setMaxInboundMessageSize(this.callOptions.getMaxInboundMessageSize());
        }
        if (this.callOptions.getMaxOutboundMessageSize() != null) {
            this.stream.setMaxOutboundMessageSize(this.callOptions.getMaxOutboundMessageSize());
        }
        if (effectiveDeadline != null) {
            this.stream.setDeadline(effectiveDeadline);
        }
        this.stream.setCompressor(compressor);
        if (this.fullStreamDecompression) {
            this.stream.setFullStreamDecompression(this.fullStreamDecompression);
        }
        this.stream.setDecompressorRegistry(this.decompressorRegistry);
        this.channelCallsTracer.reportCallStarted();
        this.stream.start(new ClientStreamListenerImpl(observer));
        this.cancellationHandler.setUp();
    }

    private void applyMethodConfig() {
        Integer existingLimit;
        ManagedChannelServiceConfig.MethodInfo info = this.callOptions.getOption(ManagedChannelServiceConfig.MethodInfo.KEY);
        if (info == null) {
            return;
        }
        if (info.timeoutNanos != null) {
            Deadline newDeadline = Deadline.after(info.timeoutNanos, TimeUnit.NANOSECONDS);
            Deadline existingDeadline = this.callOptions.getDeadline();
            if (existingDeadline == null || newDeadline.compareTo(existingDeadline) < 0) {
                this.callOptions = this.callOptions.withDeadline(newDeadline);
            }
        }
        if (info.waitForReady != null) {
            CallOptions callOptions = this.callOptions = info.waitForReady != false ? this.callOptions.withWaitForReady() : this.callOptions.withoutWaitForReady();
        }
        if (info.maxInboundMessageSize != null) {
            existingLimit = this.callOptions.getMaxInboundMessageSize();
            this.callOptions = existingLimit != null ? this.callOptions.withMaxInboundMessageSize(Math.min(existingLimit, info.maxInboundMessageSize)) : this.callOptions.withMaxInboundMessageSize(info.maxInboundMessageSize);
        }
        if (info.maxOutboundMessageSize != null) {
            existingLimit = this.callOptions.getMaxOutboundMessageSize();
            this.callOptions = existingLimit != null ? this.callOptions.withMaxOutboundMessageSize(Math.min(existingLimit, info.maxOutboundMessageSize)) : this.callOptions.withMaxOutboundMessageSize(info.maxOutboundMessageSize);
        }
    }

    @Nullable
    private Deadline effectiveDeadline() {
        return ClientCallImpl.min(this.callOptions.getDeadline(), this.context.getDeadline());
    }

    @Nullable
    private static Deadline min(@Nullable Deadline deadline0, @Nullable Deadline deadline1) {
        if (deadline0 == null) {
            return deadline1;
        }
        if (deadline1 == null) {
            return deadline0;
        }
        return deadline0.minimum(deadline1);
    }

    @Override
    public void request(int numMessages) {
        try (TaskCloseable ignore = PerfMark.traceTask("ClientCall.request");){
            PerfMark.attachTag(this.tag);
            Preconditions.checkState(this.stream != null, "Not started");
            Preconditions.checkArgument(numMessages >= 0, "Number requested must be non-negative");
            this.stream.request(numMessages);
        }
    }

    @Override
    public void cancel(@Nullable String message, @Nullable Throwable cause) {
        try (TaskCloseable ignore = PerfMark.traceTask("ClientCall.cancel");){
            PerfMark.attachTag(this.tag);
            this.cancelInternal(message, cause);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void cancelInternal(@Nullable String message, @Nullable Throwable cause) {
        if (message == null && cause == null) {
            cause = new CancellationException("Cancelled without a message or cause");
            log.log(Level.WARNING, "Cancelling without a message or cause is suboptimal", cause);
        }
        if (this.cancelCalled) {
            return;
        }
        this.cancelCalled = true;
        try {
            if (this.stream != null) {
                Status status = Status.CANCELLED;
                status = message != null ? status.withDescription(message) : status.withDescription("Call cancelled without message");
                if (cause != null) {
                    status = status.withCause(cause);
                }
                this.stream.cancel(status);
            }
        }
        finally {
            if (this.cancellationHandler != null) {
                this.cancellationHandler.tearDown();
            }
        }
    }

    @Override
    public void halfClose() {
        try (TaskCloseable ignore = PerfMark.traceTask("ClientCall.halfClose");){
            PerfMark.attachTag(this.tag);
            this.halfCloseInternal();
        }
    }

    private void halfCloseInternal() {
        Preconditions.checkState(this.stream != null, "Not started");
        Preconditions.checkState(!this.cancelCalled, "call was cancelled");
        Preconditions.checkState(!this.halfCloseCalled, "call already half-closed");
        this.halfCloseCalled = true;
        this.stream.halfClose();
    }

    @Override
    public void sendMessage(ReqT message) {
        try (TaskCloseable ignore = PerfMark.traceTask("ClientCall.sendMessage");){
            PerfMark.attachTag(this.tag);
            this.sendMessageInternal(message);
        }
    }

    private void sendMessageInternal(ReqT message) {
        Preconditions.checkState(this.stream != null, "Not started");
        Preconditions.checkState(!this.cancelCalled, "call was cancelled");
        Preconditions.checkState(!this.halfCloseCalled, "call was half-closed");
        try {
            if (this.stream instanceof RetriableStream) {
                RetriableStream retriableStream = (RetriableStream)this.stream;
                retriableStream.sendMessage(message);
            } else {
                this.stream.writeMessage(this.method.streamRequest(message));
            }
        }
        catch (RuntimeException e) {
            this.stream.cancel(Status.CANCELLED.withCause(e).withDescription("Failed to stream message"));
            return;
        }
        catch (Error e) {
            this.stream.cancel(Status.CANCELLED.withDescription("Client sendMessage() failed with Error"));
            throw e;
        }
        if (!this.unaryRequest) {
            this.stream.flush();
        }
    }

    @Override
    public void setMessageCompression(boolean enabled) {
        Preconditions.checkState(this.stream != null, "Not started");
        this.stream.setMessageCompression(enabled);
    }

    @Override
    public boolean isReady() {
        if (this.halfCloseCalled) {
            return false;
        }
        return this.stream.isReady();
    }

    @Override
    public Attributes getAttributes() {
        if (this.stream != null) {
            return this.stream.getAttributes();
        }
        return Attributes.EMPTY;
    }

    private void closeObserver(ClientCall.Listener<RespT> observer, Status status, Metadata trailers) {
        observer.onClose(status, trailers);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("method", this.method).toString();
    }

    private class ClientStreamListenerImpl
    implements ClientStreamListener {
        private final ClientCall.Listener<RespT> observer;
        private Status exceptionStatus;

        public ClientStreamListenerImpl(ClientCall.Listener<RespT> observer) {
            this.observer = Preconditions.checkNotNull(observer, "observer");
        }

        private void exceptionThrown(Status status) {
            this.exceptionStatus = status;
            ClientCallImpl.this.stream.cancel(status);
        }

        @Override
        public void headersRead(final Metadata headers) {
            try (TaskCloseable ignore = PerfMark.traceTask("ClientStreamListener.headersRead");){
                PerfMark.attachTag(ClientCallImpl.this.tag);
                final Link link = PerfMark.linkOut();
                final class HeadersRead
                extends ContextRunnable {
                    HeadersRead() {
                        super(ClientCallImpl.this.context);
                    }

                    @Override
                    public void runInContext() {
                        try (TaskCloseable ignore = PerfMark.traceTask("ClientCall$Listener.headersRead");){
                            PerfMark.attachTag(ClientCallImpl.this.tag);
                            PerfMark.linkIn(link);
                            this.runInternal();
                        }
                    }

                    private void runInternal() {
                        if (ClientStreamListenerImpl.this.exceptionStatus != null) {
                            return;
                        }
                        try {
                            ClientStreamListenerImpl.this.observer.onHeaders(headers);
                        }
                        catch (Throwable t2) {
                            ClientStreamListenerImpl.this.exceptionThrown(Status.CANCELLED.withCause(t2).withDescription("Failed to read headers"));
                        }
                    }
                }
                ClientCallImpl.this.callExecutor.execute(new HeadersRead());
            }
        }

        @Override
        public void messagesAvailable(final StreamListener.MessageProducer producer) {
            try (TaskCloseable ignore = PerfMark.traceTask("ClientStreamListener.messagesAvailable");){
                PerfMark.attachTag(ClientCallImpl.this.tag);
                final Link link = PerfMark.linkOut();
                final class MessagesAvailable
                extends ContextRunnable {
                    MessagesAvailable() {
                        super(ClientCallImpl.this.context);
                    }

                    @Override
                    public void runInContext() {
                        try (TaskCloseable ignore = PerfMark.traceTask("ClientCall$Listener.messagesAvailable");){
                            PerfMark.attachTag(ClientCallImpl.this.tag);
                            PerfMark.linkIn(link);
                            this.runInternal();
                        }
                    }

                    private void runInternal() {
                        if (ClientStreamListenerImpl.this.exceptionStatus != null) {
                            GrpcUtil.closeQuietly(producer);
                            return;
                        }
                        try {
                            InputStream message;
                            while ((message = producer.next()) != null) {
                                try {
                                    ClientStreamListenerImpl.this.observer.onMessage(ClientCallImpl.this.method.parseResponse(message));
                                }
                                catch (Throwable t2) {
                                    GrpcUtil.closeQuietly(message);
                                    throw t2;
                                }
                                message.close();
                            }
                        }
                        catch (Throwable t3) {
                            GrpcUtil.closeQuietly(producer);
                            ClientStreamListenerImpl.this.exceptionThrown(Status.CANCELLED.withCause(t3).withDescription("Failed to read message."));
                        }
                    }
                }
                ClientCallImpl.this.callExecutor.execute(new MessagesAvailable());
            }
        }

        @Override
        public void closed(Status status, ClientStreamListener.RpcProgress rpcProgress, Metadata trailers) {
            try (TaskCloseable ignore = PerfMark.traceTask("ClientStreamListener.closed");){
                PerfMark.attachTag(ClientCallImpl.this.tag);
                this.closedInternal(status, rpcProgress, trailers);
            }
        }

        private void closedInternal(Status status, ClientStreamListener.RpcProgress rpcProgress, Metadata trailers) {
            Deadline deadline = ClientCallImpl.this.effectiveDeadline();
            if (status.getCode() == Status.Code.CANCELLED && deadline != null && deadline.isExpired()) {
                status = ClientCallImpl.this.cancellationHandler.formatDeadlineExceededStatus();
                trailers = new Metadata();
            }
            final Status savedStatus = status;
            final Metadata savedTrailers = trailers;
            final Link link = PerfMark.linkOut();
            final class StreamClosed
            extends ContextRunnable {
                StreamClosed() {
                    super(ClientCallImpl.this.context);
                }

                @Override
                public void runInContext() {
                    try (TaskCloseable ignore = PerfMark.traceTask("ClientCall$Listener.onClose");){
                        PerfMark.attachTag(ClientCallImpl.this.tag);
                        PerfMark.linkIn(link);
                        this.runInternal();
                    }
                }

                private void runInternal() {
                    ClientCallImpl.this.cancellationHandler.tearDown();
                    Status status = savedStatus;
                    Metadata trailers = savedTrailers;
                    if (ClientStreamListenerImpl.this.exceptionStatus != null) {
                        status = ClientStreamListenerImpl.this.exceptionStatus;
                        trailers = new Metadata();
                    }
                    try {
                        ClientCallImpl.this.closeObserver(ClientStreamListenerImpl.this.observer, status, trailers);
                    }
                    finally {
                        ClientCallImpl.this.channelCallsTracer.reportCallEnded(status.isOk());
                    }
                }
            }
            ClientCallImpl.this.callExecutor.execute(new StreamClosed());
        }

        @Override
        public void onReady() {
            if (ClientCallImpl.this.method.getType().clientSendsOneMessage()) {
                return;
            }
            try (TaskCloseable ignore = PerfMark.traceTask("ClientStreamListener.onReady");){
                PerfMark.attachTag(ClientCallImpl.this.tag);
                final Link link = PerfMark.linkOut();
                final class StreamOnReady
                extends ContextRunnable {
                    StreamOnReady() {
                        super(ClientCallImpl.this.context);
                    }

                    @Override
                    public void runInContext() {
                        try (TaskCloseable ignore = PerfMark.traceTask("ClientCall$Listener.onReady");){
                            PerfMark.attachTag(ClientCallImpl.this.tag);
                            PerfMark.linkIn(link);
                            this.runInternal();
                        }
                    }

                    private void runInternal() {
                        if (ClientStreamListenerImpl.this.exceptionStatus != null) {
                            return;
                        }
                        try {
                            ClientStreamListenerImpl.this.observer.onReady();
                        }
                        catch (Throwable t2) {
                            ClientStreamListenerImpl.this.exceptionThrown(Status.CANCELLED.withCause(t2).withDescription("Failed to call onReady."));
                        }
                    }
                }
                ClientCallImpl.this.callExecutor.execute(new StreamOnReady());
            }
        }
    }

    private final class CancellationHandler
    implements Runnable,
    Context.CancellationListener {
        private final boolean contextIsDeadlineSource;
        private final boolean hasDeadline;
        private final long remainingNanos;
        private volatile ScheduledFuture<?> deadlineCancellationFuture;
        private volatile boolean tearDownCalled;

        CancellationHandler(Deadline deadline, boolean contextIsDeadlineSource) {
            this.contextIsDeadlineSource = contextIsDeadlineSource;
            if (deadline == null) {
                this.hasDeadline = false;
                this.remainingNanos = 0L;
            } else {
                this.hasDeadline = true;
                this.remainingNanos = deadline.timeRemaining(TimeUnit.NANOSECONDS);
            }
        }

        void setUp() {
            if (this.tearDownCalled) {
                return;
            }
            if (this.hasDeadline && !this.contextIsDeadlineSource && ClientCallImpl.this.deadlineCancellationExecutor != null) {
                this.deadlineCancellationFuture = ClientCallImpl.this.deadlineCancellationExecutor.schedule(new LogExceptionRunnable(this), this.remainingNanos, TimeUnit.NANOSECONDS);
            }
            ClientCallImpl.this.context.addListener(this, MoreExecutors.directExecutor());
            if (this.tearDownCalled) {
                this.tearDown();
            }
        }

        void tearDown() {
            this.tearDownCalled = true;
            ScheduledFuture<?> deadlineCancellationFuture = this.deadlineCancellationFuture;
            if (deadlineCancellationFuture != null) {
                deadlineCancellationFuture.cancel(false);
            }
            ClientCallImpl.this.context.removeListener(this);
        }

        @Override
        public void cancelled(Context context) {
            if (this.hasDeadline && this.contextIsDeadlineSource && context.cancellationCause() instanceof TimeoutException) {
                ClientCallImpl.this.stream.cancel(this.formatDeadlineExceededStatus());
                return;
            }
            ClientCallImpl.this.stream.cancel(Contexts.statusFromCancelled(context));
        }

        @Override
        public void run() {
            ClientCallImpl.this.stream.cancel(this.formatDeadlineExceededStatus());
        }

        Status formatDeadlineExceededStatus() {
            long seconds = Math.abs(this.remainingNanos) / TimeUnit.SECONDS.toNanos(1L);
            long nanos = Math.abs(this.remainingNanos) % TimeUnit.SECONDS.toNanos(1L);
            StringBuilder buf = new StringBuilder();
            buf.append(this.contextIsDeadlineSource ? "Context" : "CallOptions");
            buf.append(" deadline exceeded after ");
            if (this.remainingNanos < 0L) {
                buf.append('-');
            }
            buf.append(seconds);
            buf.append(String.format(Locale.US, ".%09d", nanos));
            buf.append("s. ");
            Long nsDelay = ClientCallImpl.this.callOptions.getOption(ClientStreamTracer.NAME_RESOLUTION_DELAYED);
            buf.append(String.format(Locale.US, "Name resolution delay %.9f seconds.", nsDelay == null ? 0.0 : (double)nsDelay.longValue() / NANO_TO_SECS));
            if (ClientCallImpl.this.stream != null) {
                InsightBuilder insight = new InsightBuilder();
                ClientCallImpl.this.stream.appendTimeoutInsight(insight);
                buf.append(" ");
                buf.append(insight);
            }
            return Status.DEADLINE_EXCEEDED.withDescription(buf.toString());
        }
    }

    static interface ClientStreamProvider {
        public ClientStream newStream(MethodDescriptor<?, ?> var1, CallOptions var2, Metadata var3, Context var4);
    }
}

