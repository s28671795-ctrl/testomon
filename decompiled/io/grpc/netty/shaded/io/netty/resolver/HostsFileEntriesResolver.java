/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.resolver;

import io.grpc.netty.shaded.io.netty.resolver.DefaultHostsFileEntriesResolver;
import io.grpc.netty.shaded.io.netty.resolver.ResolvedAddressTypes;
import java.net.InetAddress;

public interface HostsFileEntriesResolver {
    public static final HostsFileEntriesResolver DEFAULT = new DefaultHostsFileEntriesResolver();

    public InetAddress address(String var1, ResolvedAddressTypes var2);
}

