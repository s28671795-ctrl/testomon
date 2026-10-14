/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.Context;

abstract class ContextRunnable
implements Runnable {
    private final Context context;

    protected ContextRunnable(Context context) {
        this.context = context;
    }

    @Override
    public final void run() {
        Context previous = this.context.attach();
        try {
            this.runInContext();
        }
        finally {
            this.context.detach(previous);
        }
    }

    public abstract void runInContext();
}

