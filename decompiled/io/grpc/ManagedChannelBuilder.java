/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.BinaryLog;
import io.grpc.ClientInterceptor;
import io.grpc.ClientTransportFilter;
import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import io.grpc.ExperimentalApi;
import io.grpc.Internal;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelProvider;
import io.grpc.MetricSink;
import io.grpc.NameResolver;
import io.grpc.ProxyDetector;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public abstract class ManagedChannelBuilder<T extends ManagedChannelBuilder<T>> {
    public static ManagedChannelBuilder<?> forAddress(String name, int port) {
        return ManagedChannelProvider.provider().builderForAddress(name, port);
    }

    public static ManagedChannelBuilder<?> forTarget(String target) {
        return ManagedChannelProvider.provider().builderForTarget(target);
    }

    public abstract T directExecutor();

    public abstract T executor(Executor var1);

    public T offloadExecutor(Executor executor2) {
        throw new UnsupportedOperationException();
    }

    public abstract T intercept(List<ClientInterceptor> var1);

    public abstract T intercept(ClientInterceptor ... var1);

    @Internal
    protected T interceptWithTarget(InterceptorFactory factory2) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/10652")
    public T addTransportFilter(ClientTransportFilter filter2) {
        throw new UnsupportedOperationException();
    }

    public abstract T userAgent(String var1);

    public abstract T overrideAuthority(String var1);

    public T usePlaintext() {
        throw new UnsupportedOperationException();
    }

    public T useTransportSecurity() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1770")
    public abstract T nameResolverFactory(NameResolver.Factory var1);

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
    public T defaultLoadBalancingPolicy(String policy) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1704")
    public abstract T decompressorRegistry(DecompressorRegistry var1);

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1704")
    public abstract T compressorRegistry(CompressorRegistry var1);

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2022")
    public abstract T idleTimeout(long var1, TimeUnit var3);

    public T maxInboundMessageSize(int bytes) {
        Preconditions.checkArgument(bytes >= 0, "bytes must be >= 0");
        return this.thisT();
    }

    public T maxInboundMetadataSize(int bytes) {
        Preconditions.checkArgument(bytes > 0, "maxInboundMetadataSize must be > 0");
        return this.thisT();
    }

    public T keepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    public T keepAliveTimeout(long keepAliveTimeout, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    public T keepAliveWithoutCalls(boolean enable) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/3982")
    public T maxRetryAttempts(int maxRetryAttempts) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/3982")
    public T maxHedgedAttempts(int maxHedgedAttempts) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/3982")
    public T retryBufferSize(long bytes) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/3982")
    public T perRpcBufferLimit(long bytes) {
        throw new UnsupportedOperationException();
    }

    public T disableRetry() {
        throw new UnsupportedOperationException();
    }

    public T enableRetry() {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4017")
    public T setBinaryLog(BinaryLog binaryLog) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4471")
    public T maxTraceEvents(int maxTraceEvents) {
        throw new UnsupportedOperationException();
    }

    public T proxyDetector(ProxyDetector proxyDetector) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/5189")
    public T defaultServiceConfig(@Nullable Map<String, ?> serviceConfig) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/5189")
    public T disableServiceConfigLookUp() {
        throw new UnsupportedOperationException();
    }

    @Internal
    protected T addMetricSink(MetricSink metricSink) {
        throw new UnsupportedOperationException();
    }

    public abstract ManagedChannel build();

    private T thisT() {
        ManagedChannelBuilder thisT = this;
        return (T)thisT;
    }

    @Internal
    protected static interface InterceptorFactory {
        public ClientInterceptor newInterceptor(String var1);
    }
}

