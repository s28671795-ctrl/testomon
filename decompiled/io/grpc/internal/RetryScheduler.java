/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

public interface RetryScheduler {
    public void schedule(Runnable var1);

    public void reset();
}

