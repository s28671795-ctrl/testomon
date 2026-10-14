/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.BinaryLog;
import io.grpc.BindableService;
import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import io.grpc.ExperimentalApi;
import io.grpc.HandlerRegistry;
import io.grpc.Server;
import io.grpc.ServerCallExecutorSupplier;
import io.grpc.ServerInterceptor;
import io.grpc.ServerProvider;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServerStreamTracer;
import io.grpc.ServerTransportFilter;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public abstract class ServerBuilder<T extends ServerBuilder<T>> {
    public static ServerBuilder<?> forPort(int port) {
        return ServerProvider.provider().builderForPort(port);
    }

    public abstract T directExecutor();

    public abstract T executor(@Nullable Executor var1);

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/8274")
    public T callExecutor(ServerCallExecutorSupplier executorSupplier) {
        return this.thisT();
    }

    public abstract T addService(ServerServiceDefinition var1);

    public abstract T addService(BindableService var1);

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/7925")
    public final T addServices(List<ServerServiceDefinition> services) {
        Preconditions.checkNotNull(services, "services");
        for (ServerServiceDefinition service2 : services) {
            this.addService(service2);
        }
        return this.thisT();
    }

    public T intercept(ServerInterceptor interceptor) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2132")
    public T addTransportFilter(ServerTransportFilter filter2) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2861")
    public T addStreamTracerFactory(ServerStreamTracer.Factory factory2) {
        throw new UnsupportedOperationException();
    }

    public abstract T fallbackHandlerRegistry(@Nullable HandlerRegistry var1);

    public abstract T useTransportSecurity(File var1, File var2);

    public T useTransportSecurity(InputStream certChain, InputStream privateKey) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1704")
    public abstract T decompressorRegistry(@Nullable DecompressorRegistry var1);

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1704")
    public abstract T compressorRegistry(@Nullable CompressorRegistry var1);

    public T handshakeTimeout(long timeout2, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/9009")
    public T keepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/9009")
    public T keepAliveTimeout(long keepAliveTimeout, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/9009")
    public T maxConnectionIdle(long maxConnectionIdle, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/9009")
    public T maxConnectionAge(long maxConnectionAge, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/9009")
    public T maxConnectionAgeGrace(long maxConnectionAgeGrace, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/9009")
    public T permitKeepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/9009")
    public T permitKeepAliveWithoutCalls(boolean permit) {
        throw new UnsupportedOperationException();
    }

    public T maxInboundMessageSize(int bytes) {
        Preconditions.checkArgument(bytes >= 0, "bytes must be >= 0");
        return this.thisT();
    }

    public T maxInboundMetadataSize(int bytes) {
        Preconditions.checkArgument(bytes > 0, "maxInboundMetadataSize must be > 0");
        return this.thisT();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4017")
    public T setBinaryLog(BinaryLog binaryLog) {
        throw new UnsupportedOperationException();
    }

    public abstract Server build();

    private T thisT() {
        ServerBuilder thisT = this;
        return (T)thisT;
    }
}

