/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util;

import io.grpc.netty.shaded.io.netty.util.Timer;
import io.grpc.netty.shaded.io.netty.util.TimerTask;

public interface Timeout {
    public Timer timer();

    public TimerTask task();

    public boolean isExpired();

    public boolean isCancelled();

    public boolean cancel();
}

