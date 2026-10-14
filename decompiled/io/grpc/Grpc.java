/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Attributes;
import io.grpc.ChannelCredentials;
import io.grpc.ExperimentalApi;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ManagedChannelRegistry;
import io.grpc.ServerBuilder;
import io.grpc.ServerCredentials;
import io.grpc.ServerRegistry;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import javax.net.ssl.SSLSession;

public final class Grpc {
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1710")
    public static final Attributes.Key<SocketAddress> TRANSPORT_ATTR_REMOTE_ADDR = Attributes.Key.create("io.grpc.Grpc.TRANSPORT_ATTR_REMOTE_ADDR");
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1710")
    public static final Attributes.Key<SocketAddress> TRANSPORT_ATTR_LOCAL_ADDR = Attributes.Key.create("io.grpc.Grpc.TRANSPORT_ATTR_LOCAL_ADDR");
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1710")
    public static final Attributes.Key<SSLSession> TRANSPORT_ATTR_SSL_SESSION = Attributes.Key.create("io.grpc.Grpc.TRANSPORT_ATTR_SSL_SESSION");

    private Grpc() {
    }

    public static ManagedChannelBuilder<?> newChannelBuilder(String target, ChannelCredentials creds) {
        return ManagedChannelRegistry.getDefaultRegistry().newChannelBuilder(target, creds);
    }

    public static ManagedChannelBuilder<?> newChannelBuilderForAddress(String host2, int port, ChannelCredentials creds) {
        return Grpc.newChannelBuilder(Grpc.authorityFromHostAndPort(host2, port), creds);
    }

    private static String authorityFromHostAndPort(String host2, int port) {
        try {
            return new URI(null, null, host2, port, null, null, null).getAuthority();
        }
        catch (URISyntaxException ex) {
            throw new IllegalArgumentException("Invalid host or port: " + host2 + " " + port, ex);
        }
    }

    public static ServerBuilder<?> newServerBuilderForPort(int port, ServerCredentials creds) {
        return ServerRegistry.getDefaultRegistry().newServerBuilderForPort(port, creds);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @Documented
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4972")
    public static @interface TransportAttr {
    }
}

