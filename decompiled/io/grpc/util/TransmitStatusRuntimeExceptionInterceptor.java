/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.Attributes;
import io.grpc.ExperimentalApi;
import io.grpc.ForwardingServerCall;
import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.internal.SerializingExecutor;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2189")
public final class TransmitStatusRuntimeExceptionInterceptor
implements ServerInterceptor {
    private TransmitStatusRuntimeExceptionInterceptor() {
    }

    public static ServerInterceptor instance() {
        return new TransmitStatusRuntimeExceptionInterceptor();
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        final SerializingServerCall<ReqT, RespT> serverCall = new SerializingServerCall<ReqT, RespT>(call);
        ServerCall.Listener<ReqT> listener = next.startCall(serverCall, headers);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(listener){

            @Override
            public void onMessage(ReqT message) {
                try {
                    super.onMessage(message);
                }
                catch (StatusRuntimeException e) {
                    this.closeWithException(e);
                }
            }

            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                }
                catch (StatusRuntimeException e) {
                    this.closeWithException(e);
                }
            }

            @Override
            public void onCancel() {
                try {
                    super.onCancel();
                }
                catch (StatusRuntimeException e) {
                    this.closeWithException(e);
                }
            }

            @Override
            public void onComplete() {
                try {
                    super.onComplete();
                }
                catch (StatusRuntimeException e) {
                    this.closeWithException(e);
                }
            }

            @Override
            public void onReady() {
                try {
                    super.onReady();
                }
                catch (StatusRuntimeException e) {
                    this.closeWithException(e);
                }
            }

            private void closeWithException(StatusRuntimeException t2) {
                Metadata metadata = t2.getTrailers();
                if (metadata == null) {
                    metadata = new Metadata();
                }
                serverCall.close(t2.getStatus(), metadata);
            }
        };
    }

    private static class SerializingServerCall<ReqT, RespT>
    extends ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT> {
        private static final String ERROR_MSG = "Encountered error during serialized access";
        private final SerializingExecutor serializingExecutor = new SerializingExecutor(MoreExecutors.directExecutor());
        private boolean closeCalled = false;

        SerializingServerCall(ServerCall<ReqT, RespT> delegate) {
            super(delegate);
        }

        @Override
        public void sendMessage(final RespT message) {
            this.serializingExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    SerializingServerCall.super.sendMessage(message);
                }
            });
        }

        @Override
        public void request(final int numMessages) {
            this.serializingExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    SerializingServerCall.super.request(numMessages);
                }
            });
        }

        @Override
        public void sendHeaders(final Metadata headers) {
            this.serializingExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    SerializingServerCall.super.sendHeaders(headers);
                }
            });
        }

        @Override
        public void close(final Status status, final Metadata trailers) {
            this.serializingExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    if (!closeCalled) {
                        closeCalled = true;
                        SerializingServerCall.super.close(status, trailers);
                    }
                }
            });
        }

        @Override
        public boolean isReady() {
            final SettableFuture retVal = SettableFuture.create();
            this.serializingExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    retVal.set(SerializingServerCall.super.isReady());
                }
            });
            try {
                return (Boolean)retVal.get();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(ERROR_MSG, e);
            }
            catch (ExecutionException e) {
                throw new RuntimeException(ERROR_MSG, e);
            }
        }

        @Override
        public boolean isCancelled() {
            final SettableFuture retVal = SettableFuture.create();
            this.serializingExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    retVal.set(SerializingServerCall.super.isCancelled());
                }
            });
            try {
                return (Boolean)retVal.get();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(ERROR_MSG, e);
            }
            catch (ExecutionException e) {
                throw new RuntimeException(ERROR_MSG, e);
            }
        }

        @Override
        public void setMessageCompression(final boolean enabled) {
            this.serializingExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    SerializingServerCall.super.setMessageCompression(enabled);
                }
            });
        }

        @Override
        @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/11021")
        public void setOnReadyThreshold(final int numBytes) {
            this.serializingExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    SerializingServerCall.super.setOnReadyThreshold(numBytes);
                }
            });
        }

        @Override
        public void setCompression(final String compressor) {
            this.serializingExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    SerializingServerCall.super.setCompression(compressor);
                }
            });
        }

        @Override
        public Attributes getAttributes() {
            final SettableFuture retVal = SettableFuture.create();
            this.serializingExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    retVal.set(SerializingServerCall.super.getAttributes());
                }
            });
            try {
                return (Attributes)retVal.get();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(ERROR_MSG, e);
            }
            catch (ExecutionException e) {
                throw new RuntimeException(ERROR_MSG, e);
            }
        }

        @Override
        @Nullable
        public String getAuthority() {
            final SettableFuture retVal = SettableFuture.create();
            this.serializingExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    retVal.set(SerializingServerCall.super.getAuthority());
                }
            });
            try {
                return (String)retVal.get();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(ERROR_MSG, e);
            }
            catch (ExecutionException e) {
                throw new RuntimeException(ERROR_MSG, e);
            }
        }
    }
}

