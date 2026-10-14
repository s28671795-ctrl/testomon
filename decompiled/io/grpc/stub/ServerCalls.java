/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.stub;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.Status;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;

public final class ServerCalls {
    @VisibleForTesting
    static final String TOO_MANY_REQUESTS = "Too many requests";
    @VisibleForTesting
    static final String MISSING_REQUEST = "Half-closed without a request";

    private ServerCalls() {
    }

    public static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncUnaryCall(UnaryMethod<ReqT, RespT> method2) {
        return new UnaryServerCallHandler<ReqT, RespT>(method2, false);
    }

    public static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncServerStreamingCall(ServerStreamingMethod<ReqT, RespT> method2) {
        return new UnaryServerCallHandler<ReqT, RespT>(method2, true);
    }

    public static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncClientStreamingCall(ClientStreamingMethod<ReqT, RespT> method2) {
        return new StreamingServerCallHandler<ReqT, RespT>(method2, false);
    }

    public static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncBidiStreamingCall(BidiStreamingMethod<ReqT, RespT> method2) {
        return new StreamingServerCallHandler<ReqT, RespT>(method2, true);
    }

    public static void asyncUnimplementedUnaryCall(MethodDescriptor<?, ?> methodDescriptor, StreamObserver<?> responseObserver) {
        Preconditions.checkNotNull(methodDescriptor, "methodDescriptor");
        Preconditions.checkNotNull(responseObserver, "responseObserver");
        responseObserver.onError(Status.UNIMPLEMENTED.withDescription(String.format("Method %s is unimplemented", methodDescriptor.getFullMethodName())).asRuntimeException());
    }

    public static <ReqT> StreamObserver<ReqT> asyncUnimplementedStreamingCall(MethodDescriptor<?, ?> methodDescriptor, StreamObserver<?> responseObserver) {
        ServerCalls.asyncUnimplementedUnaryCall(methodDescriptor, responseObserver);
        return new NoopStreamObserver();
    }

    static class NoopStreamObserver<V>
    implements StreamObserver<V> {
        NoopStreamObserver() {
        }

        @Override
        public void onNext(V value) {
        }

        @Override
        public void onError(Throwable t2) {
        }

        @Override
        public void onCompleted() {
        }
    }

    private static final class ServerCallStreamObserverImpl<ReqT, RespT>
    extends ServerCallStreamObserver<RespT> {
        final ServerCall<ReqT, RespT> call;
        private final boolean serverStreamingOrBidi;
        volatile boolean cancelled;
        private boolean frozen;
        private boolean autoRequestEnabled = true;
        private boolean sentHeaders;
        private Runnable onReadyHandler;
        private Runnable onCancelHandler;
        private boolean aborted = false;
        private boolean completed = false;
        private Runnable onCloseHandler;

        ServerCallStreamObserverImpl(ServerCall<ReqT, RespT> call, boolean serverStreamingOrBidi) {
            this.call = call;
            this.serverStreamingOrBidi = serverStreamingOrBidi;
        }

        private void freeze() {
            this.frozen = true;
        }

        @Override
        public void setMessageCompression(boolean enable) {
            this.call.setMessageCompression(enable);
        }

        @Override
        public void setCompression(String compression) {
            this.call.setCompression(compression);
        }

        @Override
        public void onNext(RespT response) {
            if (this.cancelled && this.serverStreamingOrBidi) {
                throw Status.CANCELLED.withDescription("call already cancelled. Use ServerCallStreamObserver.setOnCancelHandler() to disable this exception").asRuntimeException();
            }
            Preconditions.checkState(!this.aborted, "Stream was terminated by error, no further calls are allowed");
            Preconditions.checkState(!this.completed, "Stream is already completed, no further calls are allowed");
            if (!this.sentHeaders) {
                this.call.sendHeaders(new Metadata());
                this.sentHeaders = true;
            }
            this.call.sendMessage(response);
        }

        @Override
        public void onError(Throwable t2) {
            Metadata metadata = Status.trailersFromThrowable(t2);
            if (metadata == null) {
                metadata = new Metadata();
            }
            this.call.close(Status.fromThrowable(t2), metadata);
            this.aborted = true;
        }

        @Override
        public void onCompleted() {
            this.call.close(Status.OK, new Metadata());
            this.completed = true;
        }

        @Override
        public boolean isReady() {
            return this.call.isReady();
        }

        @Override
        public void setOnReadyHandler(Runnable r) {
            Preconditions.checkState(!this.frozen, "Cannot alter onReadyHandler after initialization. May only be called during the initial call to the application, before the service returns its StreamObserver");
            this.onReadyHandler = r;
        }

        @Override
        public boolean isCancelled() {
            return this.call.isCancelled();
        }

        @Override
        public void setOnCancelHandler(Runnable onCancelHandler) {
            Preconditions.checkState(!this.frozen, "Cannot alter onCancelHandler after initialization. May only be called during the initial call to the application, before the service returns its StreamObserver");
            this.onCancelHandler = onCancelHandler;
        }

        @Override
        public void setOnReadyThreshold(int numBytes) {
            Preconditions.checkState(!this.frozen, "Cannot alter setOnReadyThreshold after initialization. May only be called during the initial call to the application, before the service returns its StreamObserver");
            this.call.setOnReadyThreshold(numBytes);
        }

        @Override
        public void disableAutoInboundFlowControl() {
            this.disableAutoRequest();
        }

        @Override
        public void disableAutoRequest() {
            Preconditions.checkState(!this.frozen, "Cannot disable auto flow control after initialization");
            this.autoRequestEnabled = false;
        }

        @Override
        public void request(int count) {
            this.call.request(count);
        }

        @Override
        public void setOnCloseHandler(Runnable onCloseHandler) {
            Preconditions.checkState(!this.frozen, "Cannot alter onCloseHandler after initialization. May only be called during the initial call to the application, before the service returns its StreamObserver");
            this.onCloseHandler = onCloseHandler;
        }
    }

    private static interface StreamingRequestMethod<ReqT, RespT> {
        public StreamObserver<ReqT> invoke(StreamObserver<RespT> var1);
    }

    private static interface UnaryRequestMethod<ReqT, RespT> {
        public void invoke(ReqT var1, StreamObserver<RespT> var2);
    }

    private static final class StreamingServerCallHandler<ReqT, RespT>
    implements ServerCallHandler<ReqT, RespT> {
        private final StreamingRequestMethod<ReqT, RespT> method;
        private final boolean bidi;

        StreamingServerCallHandler(StreamingRequestMethod<ReqT, RespT> method2, boolean bidi) {
            this.method = method2;
            this.bidi = bidi;
        }

        @Override
        public ServerCall.Listener<ReqT> startCall(ServerCall<ReqT, RespT> call, Metadata headers) {
            ServerCallStreamObserverImpl<ReqT, RespT> responseObserver = new ServerCallStreamObserverImpl<ReqT, RespT>(call, this.bidi);
            StreamObserver<ReqT> requestObserver = this.method.invoke(responseObserver);
            ((ServerCallStreamObserverImpl)responseObserver).freeze();
            if (((ServerCallStreamObserverImpl)responseObserver).autoRequestEnabled) {
                call.request(1);
            }
            return new StreamingServerCallListener(requestObserver, responseObserver, call);
        }

        private final class StreamingServerCallListener
        extends ServerCall.Listener<ReqT> {
            private final StreamObserver<ReqT> requestObserver;
            private final ServerCallStreamObserverImpl<ReqT, RespT> responseObserver;
            private final ServerCall<ReqT, RespT> call;
            private boolean halfClosed = false;

            StreamingServerCallListener(StreamObserver<ReqT> requestObserver, ServerCallStreamObserverImpl<ReqT, RespT> responseObserver, ServerCall<ReqT, RespT> call) {
                this.requestObserver = requestObserver;
                this.responseObserver = responseObserver;
                this.call = call;
            }

            @Override
            public void onMessage(ReqT request) {
                this.requestObserver.onNext(request);
                if (this.responseObserver.autoRequestEnabled) {
                    this.call.request(1);
                }
            }

            @Override
            public void onHalfClose() {
                this.halfClosed = true;
                this.requestObserver.onCompleted();
            }

            @Override
            public void onCancel() {
                if (this.responseObserver.onCancelHandler != null) {
                    this.responseObserver.onCancelHandler.run();
                } else {
                    this.responseObserver.cancelled = true;
                }
                if (!this.halfClosed) {
                    this.requestObserver.onError(Status.CANCELLED.withDescription("client cancelled").asRuntimeException());
                }
            }

            @Override
            public void onReady() {
                if (this.responseObserver.onReadyHandler != null) {
                    this.responseObserver.onReadyHandler.run();
                }
            }

            @Override
            public void onComplete() {
                if (this.responseObserver.onCloseHandler != null) {
                    this.responseObserver.onCloseHandler.run();
                }
            }
        }
    }

    private static final class UnaryServerCallHandler<ReqT, RespT>
    implements ServerCallHandler<ReqT, RespT> {
        private final UnaryRequestMethod<ReqT, RespT> method;
        private final boolean serverStreaming;

        UnaryServerCallHandler(UnaryRequestMethod<ReqT, RespT> method2, boolean serverStreaming) {
            this.method = method2;
            this.serverStreaming = serverStreaming;
        }

        @Override
        public ServerCall.Listener<ReqT> startCall(ServerCall<ReqT, RespT> call, Metadata headers) {
            Preconditions.checkArgument(call.getMethodDescriptor().getType().clientSendsOneMessage(), "asyncUnaryRequestCall is only for clientSendsOneMessage methods");
            ServerCallStreamObserverImpl<ReqT, RespT> responseObserver = new ServerCallStreamObserverImpl<ReqT, RespT>(call, this.serverStreaming);
            call.request(2);
            return new UnaryServerCallListener(responseObserver, call);
        }

        private final class UnaryServerCallListener
        extends ServerCall.Listener<ReqT> {
            private final ServerCall<ReqT, RespT> call;
            private final ServerCallStreamObserverImpl<ReqT, RespT> responseObserver;
            private boolean canInvoke = true;
            private boolean wasReady;
            private ReqT request;

            UnaryServerCallListener(ServerCallStreamObserverImpl<ReqT, RespT> responseObserver, ServerCall<ReqT, RespT> call) {
                this.call = call;
                this.responseObserver = responseObserver;
            }

            @Override
            public void onMessage(ReqT request) {
                if (this.request != null) {
                    this.call.close(Status.INTERNAL.withDescription(ServerCalls.TOO_MANY_REQUESTS), new Metadata());
                    this.canInvoke = false;
                    return;
                }
                this.request = request;
            }

            @Override
            public void onHalfClose() {
                if (!this.canInvoke) {
                    return;
                }
                if (this.request == null) {
                    this.call.close(Status.INTERNAL.withDescription(ServerCalls.MISSING_REQUEST), new Metadata());
                    return;
                }
                UnaryServerCallHandler.this.method.invoke(this.request, this.responseObserver);
                this.request = null;
                this.responseObserver.freeze();
                if (this.wasReady) {
                    this.onReady();
                }
            }

            @Override
            public void onCancel() {
                if (this.responseObserver.onCancelHandler != null) {
                    this.responseObserver.onCancelHandler.run();
                } else {
                    this.responseObserver.cancelled = true;
                }
            }

            @Override
            public void onReady() {
                this.wasReady = true;
                if (this.responseObserver.onReadyHandler != null) {
                    this.responseObserver.onReadyHandler.run();
                }
            }

            @Override
            public void onComplete() {
                if (this.responseObserver.onCloseHandler != null) {
                    this.responseObserver.onCloseHandler.run();
                }
            }
        }
    }

    public static interface BidiStreamingMethod<ReqT, RespT>
    extends StreamingRequestMethod<ReqT, RespT> {
        @Override
        public StreamObserver<ReqT> invoke(StreamObserver<RespT> var1);
    }

    public static interface ClientStreamingMethod<ReqT, RespT>
    extends StreamingRequestMethod<ReqT, RespT> {
        @Override
        public StreamObserver<ReqT> invoke(StreamObserver<RespT> var1);
    }

    public static interface ServerStreamingMethod<ReqT, RespT>
    extends UnaryRequestMethod<ReqT, RespT> {
        @Override
        public void invoke(ReqT var1, StreamObserver<RespT> var2);
    }

    public static interface UnaryMethod<ReqT, RespT>
    extends UnaryRequestMethod<ReqT, RespT> {
        @Override
        public void invoke(ReqT var1, StreamObserver<RespT> var2);
    }
}

