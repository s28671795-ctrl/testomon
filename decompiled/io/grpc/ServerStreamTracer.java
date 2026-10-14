/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Attributes;
import io.grpc.Context;
import io.grpc.ExperimentalApi;
import io.grpc.ForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.ServerCall;
import io.grpc.StreamTracer;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2861")
@ThreadSafe
public abstract class ServerStreamTracer
extends StreamTracer {
    public Context filterContext(Context context) {
        return context;
    }

    public void serverCallStarted(ServerCallInfo<?, ?> callInfo) {
        this.serverCallStarted(ReadOnlyServerCall.create(callInfo));
    }

    @Deprecated
    public void serverCallStarted(ServerCall<?, ?> call) {
    }

    @Deprecated
    private static final class ReadOnlyServerCall<ReqT, RespT>
    extends ForwardingServerCall<ReqT, RespT> {
        private final ServerCallInfo<ReqT, RespT> callInfo;

        private static <ReqT, RespT> ReadOnlyServerCall<ReqT, RespT> create(ServerCallInfo<ReqT, RespT> callInfo) {
            return new ReadOnlyServerCall<ReqT, RespT>(callInfo);
        }

        private ReadOnlyServerCall(ServerCallInfo<ReqT, RespT> callInfo) {
            this.callInfo = callInfo;
        }

        @Override
        public MethodDescriptor<ReqT, RespT> getMethodDescriptor() {
            return this.callInfo.getMethodDescriptor();
        }

        @Override
        public Attributes getAttributes() {
            return this.callInfo.getAttributes();
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public String getAuthority() {
            return this.callInfo.getAuthority();
        }

        @Override
        protected ServerCall<ReqT, RespT> delegate() {
            throw new UnsupportedOperationException();
        }
    }

    public static abstract class ServerCallInfo<ReqT, RespT> {
        public abstract MethodDescriptor<ReqT, RespT> getMethodDescriptor();

        public abstract Attributes getAttributes();

        @Nullable
        public abstract String getAuthority();
    }

    public static abstract class Factory {
        public abstract ServerStreamTracer newServerStreamTracer(String var1, Metadata var2);
    }
}

