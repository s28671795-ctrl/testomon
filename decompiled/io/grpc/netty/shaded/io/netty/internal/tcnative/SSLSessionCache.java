/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.internal.tcnative;

public interface SSLSessionCache {
    public boolean sessionCreated(long var1, long var3);

    public long getSession(long var1, byte[] var3);
}

