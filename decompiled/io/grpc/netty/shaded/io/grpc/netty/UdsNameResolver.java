/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;
import io.grpc.netty.shaded.io.netty.channel.unix.DomainSocketAddress;
import java.util.ArrayList;
import java.util.Collections;

final class UdsNameResolver
extends NameResolver {
    private NameResolver.Listener2 listener;
    private final String authority;

    UdsNameResolver(String authority, String targetPath) {
        Preconditions.checkArgument(authority == null, "non-null authority not supported");
        this.authority = targetPath;
    }

    @Override
    public String getServiceAuthority() {
        return this.authority;
    }

    @Override
    public void start(NameResolver.Listener2 listener) {
        Preconditions.checkState(this.listener == null, "already started");
        this.listener = Preconditions.checkNotNull(listener, "listener");
        this.resolve();
    }

    @Override
    public void refresh() {
        this.resolve();
    }

    private void resolve() {
        NameResolver.ResolutionResult.Builder resolutionResultBuilder = NameResolver.ResolutionResult.newBuilder();
        ArrayList<EquivalentAddressGroup> servers = new ArrayList<EquivalentAddressGroup>(1);
        servers.add(new EquivalentAddressGroup(new DomainSocketAddress(this.authority)));
        resolutionResultBuilder.setAddresses(Collections.unmodifiableList(servers));
        this.listener.onResult(resolutionResultBuilder.build());
    }

    @Override
    public void shutdown() {
    }
}

