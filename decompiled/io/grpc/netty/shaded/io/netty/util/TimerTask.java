/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util;

import io.grpc.netty.shaded.io.netty.util.Timeout;

public interface TimerTask {
    public void run(Timeout var1) throws Exception;
}

