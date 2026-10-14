/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.grpc.ExperimentalApi;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReferenceArray;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class MethodDescriptor<ReqT, RespT> {
    private final MethodType type;
    private final String fullMethodName;
    @Nullable
    private final String serviceName;
    private final Marshaller<ReqT> requestMarshaller;
    private final Marshaller<RespT> responseMarshaller;
    @Nullable
    private final Object schemaDescriptor;
    private final boolean idempotent;
    private final boolean safe;
    private final boolean sampledToLocalTracing;
    private final AtomicReferenceArray<Object> rawMethodNames = new AtomicReferenceArray(2);

    final Object getRawMethodName(int transportOrdinal) {
        return this.rawMethodNames.get(transportOrdinal);
    }

    final void setRawMethodName(int transportOrdinal, Object o) {
        this.rawMethodNames.lazySet(transportOrdinal, o);
    }

    @Deprecated
    public static <RequestT, ResponseT> MethodDescriptor<RequestT, ResponseT> create(MethodType type, String fullMethodName, Marshaller<RequestT> requestMarshaller, Marshaller<ResponseT> responseMarshaller) {
        return new MethodDescriptor<RequestT, ResponseT>(type, fullMethodName, requestMarshaller, responseMarshaller, null, false, false, false);
    }

    private MethodDescriptor(MethodType type, String fullMethodName, Marshaller<ReqT> requestMarshaller, Marshaller<RespT> responseMarshaller, Object schemaDescriptor, boolean idempotent, boolean safe, boolean sampledToLocalTracing) {
        assert (!safe || idempotent) : "safe should imply idempotent";
        this.type = Preconditions.checkNotNull(type, "type");
        this.fullMethodName = Preconditions.checkNotNull(fullMethodName, "fullMethodName");
        this.serviceName = MethodDescriptor.extractFullServiceName(fullMethodName);
        this.requestMarshaller = Preconditions.checkNotNull(requestMarshaller, "requestMarshaller");
        this.responseMarshaller = Preconditions.checkNotNull(responseMarshaller, "responseMarshaller");
        this.schemaDescriptor = schemaDescriptor;
        this.idempotent = idempotent;
        this.safe = safe;
        this.sampledToLocalTracing = sampledToLocalTracing;
    }

    public MethodType getType() {
        return this.type;
    }

    public String getFullMethodName() {
        return this.fullMethodName;
    }

    @Nullable
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/5635")
    public String getServiceName() {
        return this.serviceName;
    }

    @Nullable
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/5635")
    public String getBareMethodName() {
        return MethodDescriptor.extractBareMethodName(this.fullMethodName);
    }

    public RespT parseResponse(InputStream input) {
        return this.responseMarshaller.parse(input);
    }

    public InputStream streamRequest(ReqT requestMessage) {
        return this.requestMarshaller.stream(requestMessage);
    }

    public ReqT parseRequest(InputStream input) {
        return this.requestMarshaller.parse(input);
    }

    public InputStream streamResponse(RespT response) {
        return this.responseMarshaller.stream(response);
    }

    public Marshaller<ReqT> getRequestMarshaller() {
        return this.requestMarshaller;
    }

    public Marshaller<RespT> getResponseMarshaller() {
        return this.responseMarshaller;
    }

    @Nullable
    public Object getSchemaDescriptor() {
        return this.schemaDescriptor;
    }

    public boolean isIdempotent() {
        return this.idempotent;
    }

    public boolean isSafe() {
        return this.safe;
    }

    public boolean isSampledToLocalTracing() {
        return this.sampledToLocalTracing;
    }

    public static String generateFullMethodName(String fullServiceName, String methodName) {
        return Preconditions.checkNotNull(fullServiceName, "fullServiceName") + "/" + Preconditions.checkNotNull(methodName, "methodName");
    }

    @Nullable
    public static String extractFullServiceName(String fullMethodName) {
        int index = Preconditions.checkNotNull(fullMethodName, "fullMethodName").lastIndexOf(47);
        if (index == -1) {
            return null;
        }
        return fullMethodName.substring(0, index);
    }

    @Nullable
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/5635")
    public static String extractBareMethodName(String fullMethodName) {
        int index = Preconditions.checkNotNull(fullMethodName, "fullMethodName").lastIndexOf(47);
        if (index == -1) {
            return null;
        }
        return fullMethodName.substring(index + 1);
    }

    @CheckReturnValue
    public static <ReqT, RespT> Builder<ReqT, RespT> newBuilder() {
        return MethodDescriptor.newBuilder(null, null);
    }

    @CheckReturnValue
    public static <ReqT, RespT> Builder<ReqT, RespT> newBuilder(Marshaller<ReqT> requestMarshaller, Marshaller<RespT> responseMarshaller) {
        return new Builder().setRequestMarshaller(requestMarshaller).setResponseMarshaller(responseMarshaller);
    }

    @CheckReturnValue
    public Builder<ReqT, RespT> toBuilder() {
        return this.toBuilder(this.requestMarshaller, this.responseMarshaller);
    }

    @CheckReturnValue
    public <NewReqT, NewRespT> Builder<NewReqT, NewRespT> toBuilder(Marshaller<NewReqT> requestMarshaller, Marshaller<NewRespT> responseMarshaller) {
        return MethodDescriptor.newBuilder().setRequestMarshaller(requestMarshaller).setResponseMarshaller(responseMarshaller).setType(this.type).setFullMethodName(this.fullMethodName).setIdempotent(this.idempotent).setSafe(this.safe).setSampledToLocalTracing(this.sampledToLocalTracing).setSchemaDescriptor(this.schemaDescriptor);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("fullMethodName", this.fullMethodName).add("type", (Object)this.type).add("idempotent", this.idempotent).add("safe", this.safe).add("sampledToLocalTracing", this.sampledToLocalTracing).add("requestMarshaller", this.requestMarshaller).add("responseMarshaller", this.responseMarshaller).add("schemaDescriptor", this.schemaDescriptor).omitNullValues().toString();
    }

    public static final class Builder<ReqT, RespT> {
        private Marshaller<ReqT> requestMarshaller;
        private Marshaller<RespT> responseMarshaller;
        private MethodType type;
        private String fullMethodName;
        private boolean idempotent;
        private boolean safe;
        private Object schemaDescriptor;
        private boolean sampledToLocalTracing;

        private Builder() {
        }

        public Builder<ReqT, RespT> setRequestMarshaller(Marshaller<ReqT> requestMarshaller) {
            this.requestMarshaller = requestMarshaller;
            return this;
        }

        public Builder<ReqT, RespT> setResponseMarshaller(Marshaller<RespT> responseMarshaller) {
            this.responseMarshaller = responseMarshaller;
            return this;
        }

        public Builder<ReqT, RespT> setType(MethodType type) {
            this.type = type;
            return this;
        }

        public Builder<ReqT, RespT> setFullMethodName(String fullMethodName) {
            this.fullMethodName = fullMethodName;
            return this;
        }

        public Builder<ReqT, RespT> setSchemaDescriptor(@Nullable Object schemaDescriptor) {
            this.schemaDescriptor = schemaDescriptor;
            return this;
        }

        public Builder<ReqT, RespT> setIdempotent(boolean idempotent) {
            this.idempotent = idempotent;
            if (!idempotent) {
                this.safe = false;
            }
            return this;
        }

        public Builder<ReqT, RespT> setSafe(boolean safe) {
            this.safe = safe;
            if (safe) {
                this.idempotent = true;
            }
            return this;
        }

        public Builder<ReqT, RespT> setSampledToLocalTracing(boolean value) {
            this.sampledToLocalTracing = value;
            return this;
        }

        @CheckReturnValue
        public MethodDescriptor<ReqT, RespT> build() {
            return new MethodDescriptor(this.type, this.fullMethodName, this.requestMarshaller, this.responseMarshaller, this.schemaDescriptor, this.idempotent, this.safe, this.sampledToLocalTracing);
        }
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2222")
    public static interface PrototypeMarshaller<T>
    extends ReflectableMarshaller<T> {
        @Nullable
        public T getMessagePrototype();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2222")
    public static interface ReflectableMarshaller<T>
    extends Marshaller<T> {
        public Class<T> getMessageClass();
    }

    public static interface Marshaller<T> {
        public InputStream stream(T var1);

        public T parse(InputStream var1);
    }

    public static enum MethodType {
        UNARY,
        CLIENT_STREAMING,
        SERVER_STREAMING,
        BIDI_STREAMING,
        UNKNOWN;


        public final boolean clientSendsOneMessage() {
            return this == UNARY || this == SERVER_STREAMING;
        }

        public final boolean serverSendsOneMessage() {
            return this == UNARY || this == CLIENT_STREAMING;
        }
    }
}

