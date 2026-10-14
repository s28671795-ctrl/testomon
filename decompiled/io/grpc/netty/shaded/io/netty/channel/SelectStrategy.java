/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.util.IntSupplier;

public interface SelectStrategy {
    public static final int SELECT = -1;
    public static final int CONTINUE = -2;
    public static final int BUSY_WAIT = -3;

    public int calculateStrategy(IntSupplier var1, boolean var2) throws Exception;
}

