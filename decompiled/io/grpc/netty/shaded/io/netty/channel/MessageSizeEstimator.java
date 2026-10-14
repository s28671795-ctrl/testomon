/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

public interface MessageSizeEstimator {
    public Handle newHandle();

    public static interface Handle {
        public int size(Object var1);
    }
}

