/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import io.grpc.Internal;
import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import io.grpc.netty.shaded.io.grpc.netty.UdsNameResolver;
import io.grpc.netty.shaded.io.netty.channel.unix.DomainSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@Internal
public final class UdsNameResolverProvider
extends NameResolverProvider {
    private static final String SCHEME = "unix";

    @Override
    public UdsNameResolver newNameResolver(URI targetUri, NameResolver.Args args2) {
        if (SCHEME.equals(targetUri.getScheme())) {
            return new UdsNameResolver(targetUri.getAuthority(), UdsNameResolverProvider.getTargetPathFromUri(targetUri));
        }
        return null;
    }

    static String getTargetPathFromUri(URI targetUri) {
        Preconditions.checkArgument(SCHEME.equals(targetUri.getScheme()), "scheme must be unix");
        String targetPath = targetUri.getPath();
        if (targetPath == null) {
            targetPath = Preconditions.checkNotNull(targetUri.getSchemeSpecificPart(), "targetPath");
        }
        return targetPath;
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
    protected int priority() {
        return 3;
    }

    @Override
    public Collection<Class<? extends SocketAddress>> getProducedSocketAddressTypes() {
        return Collections.singleton(DomainSocketAddress.class);
    }
}

