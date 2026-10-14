/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.BinaryLog;
import io.grpc.ClientInterceptor;
import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import io.grpc.ForwardingChannelBuilder2;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolver;
import io.grpc.ProxyDetector;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public abstract class ForwardingChannelBuilder<T extends ForwardingChannelBuilder<T>>
extends ForwardingChannelBuilder2<T> {
    protected ForwardingChannelBuilder() {
    }

    @Override
    @Deprecated
    protected abstract ManagedChannelBuilder<?> delegate();

    @Override
    public T directExecutor() {
        this.delegate().directExecutor();
        return this.thisT();
    }

    @Override
    public T executor(Executor executor2) {
        this.delegate().executor(executor2);
        return this.thisT();
    }

    @Override
    public T offloadExecutor(Executor executor2) {
        this.delegate().offloadExecutor(executor2);
        return this.thisT();
    }

    @Override
    public T intercept(List<ClientInterceptor> interceptors) {
        this.delegate().intercept(interceptors);
        return this.thisT();
    }

    @Override
    public T intercept(ClientInterceptor ... interceptors) {
        this.delegate().intercept(interceptors);
        return this.thisT();
    }

    @Override
    public T userAgent(String userAgent) {
        this.delegate().userAgent(userAgent);
        return this.thisT();
    }

    @Override
    public T overrideAuthority(String authority) {
        this.delegate().overrideAuthority(authority);
        return this.thisT();
    }

    @Override
    public T usePlaintext() {
        this.delegate().usePlaintext();
        return this.thisT();
    }

    @Override
    public T useTransportSecurity() {
        this.delegate().useTransportSecurity();
        return this.thisT();
    }

    @Override
    @Deprecated
    public T nameResolverFactory(NameResolver.Factory resolverFactory) {
        this.delegate().nameResolverFactory(resolverFactory);
        return this.thisT();
    }

    @Override
    public T defaultLoadBalancingPolicy(String policy) {
        this.delegate().defaultLoadBalancingPolicy(policy);
        return this.thisT();
    }

    @Override
    public T decompressorRegistry(DecompressorRegistry registry) {
        this.delegate().decompressorRegistry(registry);
        return this.thisT();
    }

    @Override
    public T compressorRegistry(CompressorRegistry registry) {
        this.delegate().compressorRegistry(registry);
        return this.thisT();
    }

    @Override
    public T idleTimeout(long value, TimeUnit unit) {
        this.delegate().idleTimeout(value, unit);
        return this.thisT();
    }

    @Override
    public T maxInboundMessageSize(int max) {
        this.delegate().maxInboundMessageSize(max);
        return this.thisT();
    }

    @Override
    public T maxInboundMetadataSize(int max) {
        this.delegate().maxInboundMetadataSize(max);
        return this.thisT();
    }

    @Override
    public T keepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
        this.delegate().keepAliveTime(keepAliveTime, timeUnit);
        return this.thisT();
    }

    @Override
    public T keepAliveTimeout(long keepAliveTimeout, TimeUnit timeUnit) {
        this.delegate().keepAliveTimeout(keepAliveTimeout, timeUnit);
        return this.thisT();
    }

    @Override
    public T keepAliveWithoutCalls(boolean enable) {
        this.delegate().keepAliveWithoutCalls(enable);
        return this.thisT();
    }

    @Override
    public T maxRetryAttempts(int maxRetryAttempts) {
        this.delegate().maxRetryAttempts(maxRetryAttempts);
        return this.thisT();
    }

    @Override
    public T maxHedgedAttempts(int maxHedgedAttempts) {
        this.delegate().maxHedgedAttempts(maxHedgedAttempts);
        return this.thisT();
    }

    @Override
    public T retryBufferSize(long bytes) {
        this.delegate().retryBufferSize(bytes);
        return this.thisT();
    }

    @Override
    public T perRpcBufferLimit(long bytes) {
        this.delegate().perRpcBufferLimit(bytes);
        return this.thisT();
    }

    @Override
    public T disableRetry() {
        this.delegate().disableRetry();
        return this.thisT();
    }

    @Override
    public T enableRetry() {
        this.delegate().enableRetry();
        return this.thisT();
    }

    @Override
    public T setBinaryLog(BinaryLog binaryLog) {
        this.delegate().setBinaryLog(binaryLog);
        return this.thisT();
    }

    @Override
    public T maxTraceEvents(int maxTraceEvents) {
        this.delegate().maxTraceEvents(maxTraceEvents);
        return this.thisT();
    }

    @Override
    public T proxyDetector(ProxyDetector proxyDetector) {
        this.delegate().proxyDetector(proxyDetector);
        return this.thisT();
    }

    @Override
    public T defaultServiceConfig(@Nullable Map<String, ?> serviceConfig) {
        this.delegate().defaultServiceConfig(serviceConfig);
        return this.thisT();
    }

    @Override
    public T disableServiceConfigLookUp() {
        this.delegate().disableServiceConfigLookUp();
        return this.thisT();
    }

    private T thisT() {
        ForwardingChannelBuilder thisT = this;
        return (T)thisT;
    }
}

