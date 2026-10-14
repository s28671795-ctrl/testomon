/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.DoNotCall;
import io.grpc.BinaryLog;
import io.grpc.BindableService;
import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import io.grpc.HandlerRegistry;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerCallExecutorSupplier;
import io.grpc.ServerInterceptor;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServerStreamTracer;
import io.grpc.ServerTransportFilter;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public abstract class ForwardingServerBuilder<T extends ServerBuilder<T>>
extends ServerBuilder<T> {
    protected ForwardingServerBuilder() {
    }

    @DoNotCall(value="Unsupported")
    public static ServerBuilder<?> forPort(int port) {
        throw new UnsupportedOperationException("Subclass failed to hide static factory");
    }

    protected abstract ServerBuilder<?> delegate();

    @Override
    public T directExecutor() {
        this.delegate().directExecutor();
        return this.thisT();
    }

    @Override
    public T executor(@Nullable Executor executor2) {
        this.delegate().executor(executor2);
        return this.thisT();
    }

    @Override
    public T callExecutor(ServerCallExecutorSupplier executorSupplier) {
        this.delegate().callExecutor(executorSupplier);
        return this.thisT();
    }

    @Override
    public T addService(ServerServiceDefinition service2) {
        this.delegate().addService(service2);
        return this.thisT();
    }

    @Override
    public T addService(BindableService bindableService) {
        this.delegate().addService(bindableService);
        return this.thisT();
    }

    @Override
    public T intercept(ServerInterceptor interceptor) {
        this.delegate().intercept(interceptor);
        return this.thisT();
    }

    @Override
    public T addTransportFilter(ServerTransportFilter filter2) {
        this.delegate().addTransportFilter(filter2);
        return this.thisT();
    }

    @Override
    public T addStreamTracerFactory(ServerStreamTracer.Factory factory2) {
        this.delegate().addStreamTracerFactory(factory2);
        return this.thisT();
    }

    @Override
    public T fallbackHandlerRegistry(@Nullable HandlerRegistry fallbackRegistry) {
        this.delegate().fallbackHandlerRegistry(fallbackRegistry);
        return this.thisT();
    }

    @Override
    public T useTransportSecurity(File certChain, File privateKey) {
        this.delegate().useTransportSecurity(certChain, privateKey);
        return this.thisT();
    }

    @Override
    public T useTransportSecurity(InputStream certChain, InputStream privateKey) {
        this.delegate().useTransportSecurity(certChain, privateKey);
        return this.thisT();
    }

    @Override
    public T decompressorRegistry(@Nullable DecompressorRegistry registry) {
        this.delegate().decompressorRegistry(registry);
        return this.thisT();
    }

    @Override
    public T compressorRegistry(@Nullable CompressorRegistry registry) {
        this.delegate().compressorRegistry(registry);
        return this.thisT();
    }

    @Override
    public T handshakeTimeout(long timeout2, TimeUnit unit) {
        this.delegate().handshakeTimeout(timeout2, unit);
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
    public T maxConnectionIdle(long maxConnectionIdle, TimeUnit timeUnit) {
        this.delegate().maxConnectionIdle(maxConnectionIdle, timeUnit);
        return this.thisT();
    }

    @Override
    public T maxConnectionAge(long maxConnectionAge, TimeUnit timeUnit) {
        this.delegate().maxConnectionAge(maxConnectionAge, timeUnit);
        return this.thisT();
    }

    @Override
    public T maxConnectionAgeGrace(long maxConnectionAgeGrace, TimeUnit timeUnit) {
        this.delegate().maxConnectionAgeGrace(maxConnectionAgeGrace, timeUnit);
        return this.thisT();
    }

    @Override
    public T permitKeepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
        this.delegate().permitKeepAliveTime(keepAliveTime, timeUnit);
        return this.thisT();
    }

    @Override
    public T permitKeepAliveWithoutCalls(boolean permit) {
        this.delegate().permitKeepAliveWithoutCalls(permit);
        return this.thisT();
    }

    @Override
    public T maxInboundMessageSize(int bytes) {
        this.delegate().maxInboundMessageSize(bytes);
        return this.thisT();
    }

    @Override
    public T maxInboundMetadataSize(int bytes) {
        this.delegate().maxInboundMetadataSize(bytes);
        return this.thisT();
    }

    @Override
    public T setBinaryLog(BinaryLog binaryLog) {
        this.delegate().setBinaryLog(binaryLog);
        return this.thisT();
    }

    @Override
    public Server build() {
        return this.delegate().build();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.delegate()).toString();
    }

    private T thisT() {
        ForwardingServerBuilder thisT = this;
        return (T)thisT;
    }
}

