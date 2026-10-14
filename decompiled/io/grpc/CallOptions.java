/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.grpc.CallCredentials;
import io.grpc.ClientStreamTracer;
import io.grpc.Deadline;
import io.grpc.ExperimentalApi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@CheckReturnValue
@Immutable
public final class CallOptions {
    public static final CallOptions DEFAULT;
    @Nullable
    private final Deadline deadline;
    @Nullable
    private final Executor executor;
    @Nullable
    private final String authority;
    @Nullable
    private final CallCredentials credentials;
    @Nullable
    private final String compressorName;
    private final Object[][] customOptions;
    private final List<ClientStreamTracer.Factory> streamTracerFactories;
    @Nullable
    private final Boolean waitForReady;
    @Nullable
    private final Integer maxInboundMessageSize;
    @Nullable
    private final Integer maxOutboundMessageSize;
    @Nullable
    private final Integer onReadyThreshold;

    private CallOptions(Builder builder) {
        this.deadline = builder.deadline;
        this.executor = builder.executor;
        this.authority = builder.authority;
        this.credentials = builder.credentials;
        this.compressorName = builder.compressorName;
        this.customOptions = builder.customOptions;
        this.streamTracerFactories = builder.streamTracerFactories;
        this.waitForReady = builder.waitForReady;
        this.maxInboundMessageSize = builder.maxInboundMessageSize;
        this.maxOutboundMessageSize = builder.maxOutboundMessageSize;
        this.onReadyThreshold = builder.onReadyThreshold;
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1767")
    public CallOptions withAuthority(@Nullable String authority) {
        Builder builder = CallOptions.toBuilder(this);
        builder.authority = authority;
        return builder.build();
    }

    public CallOptions withCallCredentials(@Nullable CallCredentials credentials) {
        Builder builder = CallOptions.toBuilder(this);
        builder.credentials = credentials;
        return builder.build();
    }

    public CallOptions withCompression(@Nullable String compressorName) {
        Builder builder = CallOptions.toBuilder(this);
        builder.compressorName = compressorName;
        return builder.build();
    }

    public CallOptions withDeadline(@Nullable Deadline deadline) {
        Builder builder = CallOptions.toBuilder(this);
        builder.deadline = deadline;
        return builder.build();
    }

    public CallOptions withDeadlineAfter(long duration, TimeUnit unit) {
        return this.withDeadline(Deadline.after(duration, unit));
    }

    @Nullable
    public Deadline getDeadline() {
        return this.deadline;
    }

    public CallOptions withWaitForReady() {
        Builder builder = CallOptions.toBuilder(this);
        builder.waitForReady = Boolean.TRUE;
        return builder.build();
    }

    public CallOptions withoutWaitForReady() {
        Builder builder = CallOptions.toBuilder(this);
        builder.waitForReady = Boolean.FALSE;
        return builder.build();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/11021")
    public CallOptions withOnReadyThreshold(int numBytes) {
        Preconditions.checkArgument(numBytes > 0, "numBytes must be positive: %s", numBytes);
        Builder builder = CallOptions.toBuilder(this);
        builder.onReadyThreshold = numBytes;
        return builder.build();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/11021")
    public CallOptions clearOnReadyThreshold() {
        Builder builder = CallOptions.toBuilder(this);
        builder.onReadyThreshold = null;
        return builder.build();
    }

    @Nullable
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/11021")
    public Integer getOnReadyThreshold() {
        return this.onReadyThreshold;
    }

    @Nullable
    public String getCompressor() {
        return this.compressorName;
    }

    @Nullable
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1767")
    public String getAuthority() {
        return this.authority;
    }

    @Nullable
    public CallCredentials getCredentials() {
        return this.credentials;
    }

    public CallOptions withExecutor(@Nullable Executor executor2) {
        Builder builder = CallOptions.toBuilder(this);
        builder.executor = executor2;
        return builder.build();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2861")
    public CallOptions withStreamTracerFactory(ClientStreamTracer.Factory factory2) {
        ArrayList<ClientStreamTracer.Factory> newList = new ArrayList<ClientStreamTracer.Factory>(this.streamTracerFactories.size() + 1);
        newList.addAll(this.streamTracerFactories);
        newList.add(factory2);
        Builder builder = CallOptions.toBuilder(this);
        builder.streamTracerFactories = Collections.unmodifiableList(newList);
        return builder.build();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2861")
    public List<ClientStreamTracer.Factory> getStreamTracerFactories() {
        return this.streamTracerFactories;
    }

    public <T> CallOptions withOption(Key<T> key, T value) {
        Preconditions.checkNotNull(key, "key");
        Preconditions.checkNotNull(value, "value");
        Builder builder = CallOptions.toBuilder(this);
        int existingIdx = -1;
        for (int i = 0; i < this.customOptions.length; ++i) {
            if (!key.equals(this.customOptions[i][0])) continue;
            existingIdx = i;
            break;
        }
        builder.customOptions = new Object[this.customOptions.length + (existingIdx == -1 ? 1 : 0)][2];
        System.arraycopy(this.customOptions, 0, builder.customOptions, 0, this.customOptions.length);
        if (existingIdx == -1) {
            builder.customOptions[this.customOptions.length] = new Object[]{key, value};
        } else {
            builder.customOptions[existingIdx] = new Object[]{key, value};
        }
        return builder.build();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1869")
    public <T> T getOption(Key<T> key) {
        Preconditions.checkNotNull(key, "key");
        for (int i = 0; i < this.customOptions.length; ++i) {
            if (!key.equals(this.customOptions[i][0])) continue;
            return (T)this.customOptions[i][1];
        }
        return (T)((Key)key).defaultValue;
    }

    @Nullable
    public Executor getExecutor() {
        return this.executor;
    }

    public boolean isWaitForReady() {
        return Boolean.TRUE.equals(this.waitForReady);
    }

    Boolean getWaitForReady() {
        return this.waitForReady;
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2563")
    public CallOptions withMaxInboundMessageSize(int maxSize) {
        Preconditions.checkArgument(maxSize >= 0, "invalid maxsize %s", maxSize);
        Builder builder = CallOptions.toBuilder(this);
        builder.maxInboundMessageSize = maxSize;
        return builder.build();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2563")
    public CallOptions withMaxOutboundMessageSize(int maxSize) {
        Preconditions.checkArgument(maxSize >= 0, "invalid maxsize %s", maxSize);
        Builder builder = CallOptions.toBuilder(this);
        builder.maxOutboundMessageSize = maxSize;
        return builder.build();
    }

    @Nullable
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2563")
    public Integer getMaxInboundMessageSize() {
        return this.maxInboundMessageSize;
    }

    @Nullable
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2563")
    public Integer getMaxOutboundMessageSize() {
        return this.maxOutboundMessageSize;
    }

    private static Builder toBuilder(CallOptions other) {
        Builder builder = new Builder();
        builder.deadline = other.deadline;
        builder.executor = other.executor;
        builder.authority = other.authority;
        builder.credentials = other.credentials;
        builder.compressorName = other.compressorName;
        builder.customOptions = other.customOptions;
        builder.streamTracerFactories = other.streamTracerFactories;
        builder.waitForReady = other.waitForReady;
        builder.maxInboundMessageSize = other.maxInboundMessageSize;
        builder.maxOutboundMessageSize = other.maxOutboundMessageSize;
        return builder;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("deadline", this.deadline).add("authority", this.authority).add("callCredentials", this.credentials).add("executor", this.executor != null ? this.executor.getClass() : null).add("compressorName", this.compressorName).add("customOptions", Arrays.deepToString((Object[])this.customOptions)).add("waitForReady", this.isWaitForReady()).add("maxInboundMessageSize", this.maxInboundMessageSize).add("maxOutboundMessageSize", this.maxOutboundMessageSize).add("streamTracerFactories", this.streamTracerFactories).toString();
    }

    static {
        Builder b = new Builder();
        b.customOptions = new Object[0][2];
        b.streamTracerFactories = Collections.emptyList();
        DEFAULT = b.build();
    }

    public static final class Key<T> {
        private final String debugString;
        private final T defaultValue;

        private Key(String debugString, T defaultValue) {
            this.debugString = debugString;
            this.defaultValue = defaultValue;
        }

        public T getDefault() {
            return this.defaultValue;
        }

        public String toString() {
            return this.debugString;
        }

        @Deprecated
        @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1869")
        public static <T> Key<T> of(String debugString, T defaultValue) {
            Preconditions.checkNotNull(debugString, "debugString");
            return new Key<T>(debugString, defaultValue);
        }

        public static <T> Key<T> create(String debugString) {
            Preconditions.checkNotNull(debugString, "debugString");
            return new Key<Object>(debugString, null);
        }

        public static <T> Key<T> createWithDefault(String debugString, T defaultValue) {
            Preconditions.checkNotNull(debugString, "debugString");
            return new Key<T>(debugString, defaultValue);
        }
    }

    static class Builder {
        Deadline deadline;
        Executor executor;
        String authority;
        CallCredentials credentials;
        String compressorName;
        Object[][] customOptions;
        List<ClientStreamTracer.Factory> streamTracerFactories;
        Boolean waitForReady;
        Integer maxInboundMessageSize;
        Integer maxOutboundMessageSize;
        Integer onReadyThreshold;

        Builder() {
        }

        private CallOptions build() {
            return new CallOptions(this);
        }
    }
}

