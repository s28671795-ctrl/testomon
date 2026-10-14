/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import io.grpc.InternalServiceProviders;
import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import io.grpc.internal.DnsNameResolver;
import io.grpc.internal.GrpcUtil;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;

public final class DnsNameResolverProvider
extends NameResolverProvider {
    private static final String SCHEME = "dns";
    private static final boolean IS_ANDROID = InternalServiceProviders.isAndroid(DnsNameResolverProvider.class.getClassLoader());

    @Override
    public NameResolver newNameResolver(URI targetUri, NameResolver.Args args2) {
        if (SCHEME.equals(targetUri.getScheme())) {
            String targetPath = Preconditions.checkNotNull(targetUri.getPath(), "targetPath");
            Preconditions.checkArgument(targetPath.startsWith("/"), "the path component (%s) of the target (%s) must start with '/'", (Object)targetPath, (Object)targetUri);
            String name = targetPath.substring(1);
            return new DnsNameResolver(targetUri.getAuthority(), name, args2, GrpcUtil.SHARED_CHANNEL_EXECUTOR, Stopwatch.createUnstarted(), IS_ANDROID);
        }
        return null;
    }

    @Override
    public String getDefaultScheme() {
        return SCHEME;
    }

    @Override
    protected boolean isAvailable() {
        return true;
    }

    @Override
    public int priority() {
        return 5;
    }

    @Override
    public Collection<Class<? extends SocketAddress>> getProducedSocketAddressTypes() {
        return Collections.singleton(InetSocketAddress.class);
    }
}

