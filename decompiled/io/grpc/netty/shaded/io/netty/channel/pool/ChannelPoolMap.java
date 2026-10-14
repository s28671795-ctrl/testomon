/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.pool;

import io.grpc.netty.shaded.io.netty.channel.pool.ChannelPool;

public interface ChannelPoolMap<K, P extends ChannelPool> {
    public P get(K var1);

    public boolean contains(K var1);
}

