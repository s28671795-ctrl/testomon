/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.stub;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import io.grpc.stub.StreamObserver;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class ClientCalls {
    private static final Logger logger = Logger.getLogger(ClientCalls.class.getName());
    @VisibleForTesting
    static boolean rejectRunnableOnExecutor = !Strings.isNullOrEmpty(System.getenv("GRPC_CLIENT_CALL_REJECT_RUNNABLE")) && Boolean.parseBoolean(System.getenv("GRPC_CLIENT_CALL_REJECT_RUNNABLE"));
    static final CallOptions.Key<StubType> STUB_TYPE_OPTION = CallOptions.Key.create("internal-stub-type");

    private ClientCalls() {
    }

    public static <ReqT, RespT> void asyncUnaryCall(ClientCall<ReqT, RespT> call, ReqT req, StreamObserver<RespT> responseObserver) {
        Preconditions.checkNotNull(responseObserver, "responseObserver");
        ClientCalls.asyncUnaryRequestCall(call, req, responseObserver, false);
    }

    public static <ReqT, RespT> void asyncServerStreamingCall(ClientCall<ReqT, RespT> call, ReqT req, StreamObserver<RespT> responseObserver) {
        Preconditions.checkNotNull(responseObserver, "responseObserver");
        ClientCalls.asyncUnaryRequestCall(call, req, responseObserver, true);
    }

    public static <ReqT, RespT> StreamObserver<ReqT> asyncClientStreamingCall(ClientCall<ReqT, RespT> call, StreamObserver<RespT> responseObserver) {
        Preconditions.checkNotNull(responseObserver, "responseObserver");
        return ClientCalls.asyncStreamingRequestCall(call, responseObserver, false);
    }

    public static <ReqT, RespT> StreamObserver<ReqT> asyncBidiStreamingCall(ClientCall<ReqT, RespT> call, StreamObserver<RespT> responseObserver) {
        Preconditions.checkNotNull(responseObserver, "responseObserver");
        return ClientCalls.asyncStreamingRequestCall(call, responseObserver, true);
    }

    public static <ReqT, RespT> RespT blockingUnaryCall(ClientCall<ReqT, RespT> call, ReqT req) {
        try {
            return ClientCalls.getUnchecked(ClientCalls.futureUnaryCall(call, req));
        }
        catch (Error | RuntimeException e) {
            throw ClientCalls.cancelThrow(call, e);
        }
    }

    public static <ReqT, RespT> RespT blockingUnaryCall(Channel channel, MethodDescriptor<ReqT, RespT> method2, CallOptions callOptions, ReqT req) {
        ThreadlessExecutor executor2 = new ThreadlessExecutor();
        boolean interrupt = false;
        ClientCall<ReqT, RespT> call = channel.newCall(method2, callOptions.withOption(STUB_TYPE_OPTION, StubType.BLOCKING).withExecutor(executor2));
        try {
            ListenableFuture<RespT> responseFuture = ClientCalls.futureUnaryCall(call, req);
            while (!responseFuture.isDone()) {
                try {
                    executor2.waitAndDrain();
                }
                catch (InterruptedException e) {
                    interrupt = true;
                    call.cancel("Thread interrupted", e);
                }
            }
            executor2.shutdown();
            RespT RespT = ClientCalls.getUnchecked(responseFuture);
            return RespT;
        }
        catch (Error | RuntimeException e) {
            throw ClientCalls.cancelThrow(call, e);
        }
        finally {
            if (interrupt) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static <ReqT, RespT> Iterator<RespT> blockingServerStreamingCall(ClientCall<ReqT, RespT> call, ReqT req) {
        BlockingResponseStream<RespT> result = new BlockingResponseStream<RespT>(call);
        ClientCalls.asyncUnaryRequestCall(call, req, result.listener());
        return result;
    }

    public static <ReqT, RespT> Iterator<RespT> blockingServerStreamingCall(Channel channel, MethodDescriptor<ReqT, RespT> method2, CallOptions callOptions, ReqT req) {
        ClientCall<ReqT, RespT> call = channel.newCall(method2, callOptions.withOption(STUB_TYPE_OPTION, StubType.BLOCKING));
        BlockingResponseStream<RespT> result = new BlockingResponseStream<RespT>(call);
        ClientCalls.asyncUnaryRequestCall(call, req, result.listener());
        return result;
    }

    public static <ReqT, RespT> ListenableFuture<RespT> futureUnaryCall(ClientCall<ReqT, RespT> call, ReqT req) {
        GrpcFuture<RespT> responseFuture = new GrpcFuture<RespT>(call);
        ClientCalls.asyncUnaryRequestCall(call, req, new UnaryStreamToFuture<RespT>(responseFuture));
        return responseFuture;
    }

    private static <V> V getUnchecked(Future<V> future) {
        try {
            return future.get();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw Status.CANCELLED.withDescription("Thread interrupted").withCause(e).asRuntimeException();
        }
        catch (ExecutionException e) {
            throw ClientCalls.toStatusRuntimeException(e.getCause());
        }
    }

    private static StatusRuntimeException toStatusRuntimeException(Throwable t2) {
        for (Throwable cause = Preconditions.checkNotNull(t2, "t"); cause != null; cause = cause.getCause()) {
            if (cause instanceof StatusException) {
                StatusException se = (StatusException)cause;
                return new StatusRuntimeException(se.getStatus(), se.getTrailers());
            }
            if (!(cause instanceof StatusRuntimeException)) continue;
            StatusRuntimeException se = (StatusRuntimeException)cause;
            return new StatusRuntimeException(se.getStatus(), se.getTrailers());
        }
        return Status.UNKNOWN.withDescription("unexpected exception").withCause(t2).asRuntimeException();
    }

    private static RuntimeException cancelThrow(ClientCall<?, ?> call, Throwable t2) {
        try {
            call.cancel(null, t2);
        }
        catch (Error | RuntimeException e) {
            logger.log(Level.SEVERE, "RuntimeException encountered while closing call", e);
        }
        if (t2 instanceof RuntimeException) {
            throw (RuntimeException)t2;
        }
        if (t2 instanceof Error) {
            throw (Error)t2;
        }
        throw new AssertionError((Object)t2);
    }

    private static <ReqT, RespT> void asyncUnaryRequestCall(ClientCall<ReqT, RespT> call, ReqT req, StreamObserver<RespT> responseObserver, boolean streamingResponse) {
        ClientCalls.asyncUnaryRequestCall(call, req, new StreamObserverToCallListenerAdapter<ReqT, RespT>(responseObserver, new CallToStreamObserverAdapter<ReqT>(call, streamingResponse)));
    }

    private static <ReqT, RespT> void asyncUnaryRequestCall(ClientCall<ReqT, RespT> call, ReqT req, StartableListener<RespT> responseListener) {
        ClientCalls.startCall(call, responseListener);
        try {
            call.sendMessage(req);
            call.halfClose();
        }
        catch (Error | RuntimeException e) {
            throw ClientCalls.cancelThrow(call, e);
        }
    }

    private static <ReqT, RespT> StreamObserver<ReqT> asyncStreamingRequestCall(ClientCall<ReqT, RespT> call, StreamObserver<RespT> responseObserver, boolean streamingResponse) {
        CallToStreamObserverAdapter<ReqT> adapter = new CallToStreamObserverAdapter<ReqT>(call, streamingResponse);
        ClientCalls.startCall(call, new StreamObserverToCallListenerAdapter<ReqT, RespT>(responseObserver, adapter));
        return adapter;
    }

    private static <ReqT, RespT> void startCall(ClientCall<ReqT, RespT> call, StartableListener<RespT> responseListener) {
        call.start(responseListener, new Metadata());
        responseListener.onStart();
    }

    static enum StubType {
        BLOCKING,
        FUTURE,
        ASYNC;

    }

    private static final class ThreadlessExecutor
    extends ConcurrentLinkedQueue<Runnable>
    implements Executor {
        private static final Logger log = Logger.getLogger(ThreadlessExecutor.class.getName());
        private static final Object SHUTDOWN = new Object();
        private volatile Object waiter;

        ThreadlessExecutor() {
        }

        public void waitAndDrain() throws InterruptedException {
            ThreadlessExecutor.throwIfInterrupted();
            Runnable runnable2 = (Runnable)this.poll();
            if (runnable2 == null) {
                this.waiter = Thread.currentThread();
                try {
                    while ((runnable2 = (Runnable)this.poll()) == null) {
                        LockSupport.park(this);
                        ThreadlessExecutor.throwIfInterrupted();
                    }
                }
                finally {
                    this.waiter = null;
                }
            }
            do {
                ThreadlessExecutor.runQuietly(runnable2);
            } while ((runnable2 = (Runnable)this.poll()) != null);
        }

        public void shutdown() {
            Runnable runnable2;
            this.waiter = SHUTDOWN;
            while ((runnable2 = (Runnable)this.poll()) != null) {
                ThreadlessExecutor.runQuietly(runnable2);
            }
        }

        private static void runQuietly(Runnable runnable2) {
            try {
                runnable2.run();
            }
            catch (Throwable t2) {
                log.log(Level.WARNING, "Runnable threw exception", t2);
            }
        }

        private static void throwIfInterrupted() throws InterruptedException {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        }

        @Override
        public void execute(Runnable runnable2) {
            this.add(runnable2);
            Object waiter = this.waiter;
            if (waiter != SHUTDOWN) {
                LockSupport.unpark((Thread)waiter);
            } else if (this.remove(runnable2) && rejectRunnableOnExecutor) {
                throw new RejectedExecutionException();
            }
        }
    }

    private static final class BlockingResponseStream<T>
    implements Iterator<T> {
        private final BlockingQueue<Object> buffer = new ArrayBlockingQueue<Object>(3);
        private final StartableListener<T> listener = new QueuingListener();
        private final ClientCall<?, T> call;
        private Object last;

        BlockingResponseStream(ClientCall<?, T> call) {
            this.call = call;
        }

        StartableListener<T> listener() {
            return this.listener;
        }

        private Object waitForNext() {
            boolean interrupt = false;
            while (true) {
                try {
                    Object object = this.buffer.take();
                    return object;
                }
                catch (InterruptedException ie) {
                    interrupt = true;
                    this.call.cancel("Thread interrupted", ie);
                    continue;
                }
                break;
            }
            finally {
                if (interrupt) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        @Override
        public boolean hasNext() {
            while (this.last == null) {
                this.last = this.waitForNext();
            }
            if (this.last instanceof StatusRuntimeException) {
                StatusRuntimeException e = (StatusRuntimeException)this.last;
                throw e.getStatus().asRuntimeException(e.getTrailers());
            }
            return this.last != this;
        }

        @Override
        public T next() {
            if (!(this.last instanceof StatusRuntimeException) && this.last != this) {
                this.call.request(1);
            }
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            Object tmp = this.last;
            this.last = null;
            return (T)tmp;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private final class QueuingListener
        extends StartableListener<T> {
            private boolean done;

            QueuingListener() {
                this.done = false;
            }

            @Override
            public void onHeaders(Metadata headers) {
            }

            @Override
            public void onMessage(T value) {
                Preconditions.checkState(!this.done, "ClientCall already closed");
                BlockingResponseStream.this.buffer.add(value);
            }

            @Override
            public void onClose(Status status, Metadata trailers) {
                Preconditions.checkState(!this.done, "ClientCall already closed");
                if (status.isOk()) {
                    BlockingResponseStream.this.buffer.add(BlockingResponseStream.this);
                } else {
                    BlockingResponseStream.this.buffer.add(status.asRuntimeException(trailers));
                }
                this.done = true;
            }

            @Override
            void onStart() {
                BlockingResponseStream.this.call.request(1);
            }
        }
    }

    private static final class GrpcFuture<RespT>
    extends AbstractFuture<RespT> {
        private final ClientCall<?, RespT> call;

        GrpcFuture(ClientCall<?, RespT> call) {
            this.call = call;
        }

        @Override
        protected void interruptTask() {
            this.call.cancel("GrpcFuture was cancelled", null);
        }

        @Override
        protected boolean set(@Nullable RespT resp) {
            return super.set(resp);
        }

        @Override
        protected boolean setException(Throwable throwable) {
            return super.setException(throwable);
        }

        @Override
        protected String pendingToString() {
            return MoreObjects.toStringHelper(this).add("clientCall", this.call).toString();
        }
    }

    private static final class UnaryStreamToFuture<RespT>
    extends StartableListener<RespT> {
        private final GrpcFuture<RespT> responseFuture;
        private RespT value;
        private boolean isValueReceived = false;

        UnaryStreamToFuture(GrpcFuture<RespT> responseFuture) {
            this.responseFuture = responseFuture;
        }

        @Override
        public void onHeaders(Metadata headers) {
        }

        @Override
        public void onMessage(RespT value) {
            if (this.isValueReceived) {
                throw Status.INTERNAL.withDescription("More than one value received for unary call").asRuntimeException();
            }
            this.value = value;
            this.isValueReceived = true;
        }

        @Override
        public void onClose(Status status, Metadata trailers) {
            if (status.isOk()) {
                if (!this.isValueReceived) {
                    this.responseFuture.setException(Status.INTERNAL.withDescription("No value received for unary call").asRuntimeException(trailers));
                }
                this.responseFuture.set(this.value);
            } else {
                this.responseFuture.setException(status.asRuntimeException(trailers));
            }
        }

        @Override
        void onStart() {
            ((GrpcFuture)this.responseFuture).call.request(2);
        }
    }

    private static final class StreamObserverToCallListenerAdapter<ReqT, RespT>
    extends StartableListener<RespT> {
        private final StreamObserver<RespT> observer;
        private final CallToStreamObserverAdapter<ReqT> adapter;
        private boolean firstResponseReceived;

        StreamObserverToCallListenerAdapter(StreamObserver<RespT> observer, CallToStreamObserverAdapter<ReqT> adapter) {
            this.observer = observer;
            this.adapter = adapter;
            if (observer instanceof ClientResponseObserver) {
                ClientResponseObserver clientResponseObserver = (ClientResponseObserver)observer;
                clientResponseObserver.beforeStart(adapter);
            }
            ((CallToStreamObserverAdapter)adapter).freeze();
        }

        @Override
        public void onHeaders(Metadata headers) {
        }

        @Override
        public void onMessage(RespT message) {
            if (this.firstResponseReceived && !((CallToStreamObserverAdapter)this.adapter).streamingResponse) {
                throw Status.INTERNAL.withDescription("More than one responses received for unary or client-streaming call").asRuntimeException();
            }
            this.firstResponseReceived = true;
            this.observer.onNext(message);
            if (((CallToStreamObserverAdapter)this.adapter).streamingResponse && ((CallToStreamObserverAdapter)this.adapter).autoRequestEnabled) {
                this.adapter.request(1);
            }
        }

        @Override
        public void onClose(Status status, Metadata trailers) {
            if (status.isOk()) {
                this.observer.onCompleted();
            } else {
                this.observer.onError(status.asRuntimeException(trailers));
            }
        }

        @Override
        public void onReady() {
            if (((CallToStreamObserverAdapter)this.adapter).onReadyHandler != null) {
                ((CallToStreamObserverAdapter)this.adapter).onReadyHandler.run();
            }
        }

        @Override
        void onStart() {
            if (((CallToStreamObserverAdapter)this.adapter).initialRequest > 0) {
                this.adapter.request(((CallToStreamObserverAdapter)this.adapter).initialRequest);
            }
        }
    }

    private static final class CallToStreamObserverAdapter<ReqT>
    extends ClientCallStreamObserver<ReqT> {
        private boolean frozen;
        private final ClientCall<ReqT, ?> call;
        private final boolean streamingResponse;
        private Runnable onReadyHandler;
        private int initialRequest = 1;
        private boolean autoRequestEnabled = true;
        private boolean aborted = false;
        private boolean completed = false;

        CallToStreamObserverAdapter(ClientCall<ReqT, ?> call, boolean streamingResponse) {
            this.call = call;
            this.streamingResponse = streamingResponse;
        }

        private void freeze() {
            this.frozen = true;
        }

        @Override
        public void onNext(ReqT value) {
            Preconditions.checkState(!this.aborted, "Stream was terminated by error, no further calls are allowed");
            Preconditions.checkState(!this.completed, "Stream is already completed, no further calls are allowed");
            this.call.sendMessage(value);
        }

        @Override
        public void onError(Throwable t2) {
            this.call.cancel("Cancelled by client with StreamObserver.onError()", t2);
            this.aborted = true;
        }

        @Override
        public void onCompleted() {
            this.call.halfClose();
            this.completed = true;
        }

        @Override
        public boolean isReady() {
            return this.call.isReady();
        }

        @Override
        public void setOnReadyHandler(Runnable onReadyHandler) {
            if (this.frozen) {
                throw new IllegalStateException("Cannot alter onReadyHandler after call started. Use ClientResponseObserver");
            }
            this.onReadyHandler = onReadyHandler;
        }

        @Override
        public void disableAutoInboundFlowControl() {
            this.disableAutoRequestWithInitial(1);
        }

        @Override
        public void disableAutoRequestWithInitial(int request) {
            if (this.frozen) {
                throw new IllegalStateException("Cannot disable auto flow control after call started. Use ClientResponseObserver");
            }
            Preconditions.checkArgument(request >= 0, "Initial requests must be non-negative");
            this.initialRequest = request;
            this.autoRequestEnabled = false;
        }

        @Override
        public void request(int count) {
            if (!this.streamingResponse && count == 1) {
                this.call.request(2);
            } else {
                this.call.request(count);
            }
        }

        @Override
        public void setMessageCompression(boolean enable) {
            this.call.setMessageCompression(enable);
        }

        @Override
        public void cancel(@Nullable String message, @Nullable Throwable cause) {
            this.call.cancel(message, cause);
        }
    }

    private static abstract class StartableListener<T>
    extends ClientCall.Listener<T> {
        private StartableListener() {
        }

        abstract void onStart();
    }
}

