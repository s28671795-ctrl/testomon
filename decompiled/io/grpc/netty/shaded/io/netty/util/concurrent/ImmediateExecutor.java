/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util.concurrent;

import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;
import java.util.concurrent.Executor;

public final class ImmediateExecutor
implements Executor {
    public static final ImmediateExecutor INSTANCE = new ImmediateExecutor();

    private ImmediateExecutor() {
    }

    @Override
    public void execute(Runnable command) {
        ObjectUtil.checkNotNull(command, "command").run();
    }
}

