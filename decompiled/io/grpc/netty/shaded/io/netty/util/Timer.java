/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util;

import io.grpc.netty.shaded.io.netty.util.Timeout;
import io.grpc.netty.shaded.io.netty.util.TimerTask;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface Timer {
    public Timeout newTimeout(TimerTask var1, long var2, TimeUnit var4);

    public Set<Timeout> stop();
}

