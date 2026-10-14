/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.stub;

import com.google.common.base.Preconditions;
import io.grpc.CallCredentials;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.Deadline;
import io.grpc.ExperimentalApi;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@CheckReturnValue
@ThreadSafe
public abstract class AbstractStub<S extends AbstractStub<S>> {
    private final Channel channel;
    private final CallOptions callOptions;

    protected AbstractStub(Channel channel) {
        this(channel, CallOptions.DEFAULT);
    }

    protected AbstractStub(Channel channel, CallOptions callOptions) {
        this.channel = Preconditions.checkNotNull(channel, "channel");
        this.callOptions = Preconditions.checkNotNull(callOptions, "callOptions");
    }

    public final Channel getChannel() {
        return this.channel;
    }

    public final CallOptions getCallOptions() {
        return this.callOptions;
    }

    protected abstract S build(Channel var1, CallOptions var2);

    public static <T extends AbstractStub<T>> T newStub(StubFactory<T> factory2, Channel channel) {
        return AbstractStub.newStub(factory2, channel, CallOptions.DEFAULT);
    }

    public static <T extends AbstractStub<T>> T newStub(StubFactory<T> factory2, Channel channel, CallOptions callOptions) {
        return factory2.newStub(channel, callOptions);
    }

    public final S withDeadline(@Nullable Deadline deadline) {
        return this.build(this.channel, this.callOptions.withDeadline(deadline));
    }

    public final S withDeadlineAfter(long duration, TimeUnit unit) {
        return this.build(this.channel, this.callOptions.withDeadlineAfter(duration, unit));
    }

    public final S withExecutor(Executor executor2) {
        return this.build(this.channel, this.callOptions.withExecutor(executor2));
    }

    public final S withCompression(String compressorName) {
        return this.build(this.channel, this.callOptions.withCompression(compressorName));
    }

    @Deprecated
    public final S withChannel(Channel newChannel) {
        return this.build(newChannel, this.callOptions);
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1869")
    public final <T> S withOption(CallOptions.Key<T> key, T value) {
        return this.build(this.channel, this.callOptions.withOption(key, value));
    }

    public final S withInterceptors(ClientInterceptor ... interceptors) {
        return this.build(ClientInterceptors.intercept(this.channel, interceptors), this.callOptions);
    }

    public final S withCallCredentials(CallCredentials credentials) {
        return this.build(this.channel, this.callOptions.withCallCredentials(credentials));
    }

    public final S withWaitForReady() {
        return this.build(this.channel, this.callOptions.withWaitForReady());
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2563")
    public final S withMaxInboundMessageSize(int maxSize) {
        return this.build(this.channel, this.callOptions.withMaxInboundMessageSize(maxSize));
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2563")
    public final S withMaxOutboundMessageSize(int maxSize) {
        return this.build(this.channel, this.callOptions.withMaxOutboundMessageSize(maxSize));
    }

    public static interface StubFactory<T extends AbstractStub<T>> {
        public T newStub(Channel var1, CallOptions var2);
    }
}

