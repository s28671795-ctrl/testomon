/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util.concurrent;

import io.grpc.netty.shaded.io.netty.util.concurrent.EventExecutor;

public interface EventExecutorChooserFactory {
    public EventExecutorChooser newChooser(EventExecutor[] var1);

    public static interface EventExecutorChooser {
        public EventExecutor next();
    }
}

