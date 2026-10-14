/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.pool;

import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.util.concurrent.Future;
import io.grpc.netty.shaded.io.netty.util.concurrent.Promise;
import java.io.Closeable;

public interface ChannelPool
extends Closeable {
    public Future<Channel> acquire();

    public Future<Channel> acquire(Promise<Channel> var1);

    public Future<Void> release(Channel var1);

    public Future<Void> release(Channel var1, Promise<Void> var2);

    @Override
    public void close();
}

