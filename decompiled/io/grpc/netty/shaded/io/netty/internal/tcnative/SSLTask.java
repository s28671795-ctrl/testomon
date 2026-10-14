/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.internal.tcnative;

abstract class SSLTask
implements Runnable {
    private static final Runnable NOOP = new Runnable(){

        @Override
        public void run() {
        }
    };
    private final long ssl;
    private int returnValue;
    private boolean complete;
    protected boolean didRun;

    protected SSLTask(long ssl) {
        this.ssl = ssl;
    }

    @Override
    public final void run() {
        this.run(NOOP);
    }

    protected final void run(final Runnable completeCallback) {
        if (!this.didRun) {
            this.didRun = true;
            this.runTask(this.ssl, new TaskCallback(){

                @Override
                public void onResult(long ssl, int result) {
                    SSLTask.this.returnValue = result;
                    SSLTask.this.complete = true;
                    completeCallback.run();
                }
            });
        } else {
            completeCallback.run();
        }
    }

    protected abstract void runTask(long var1, TaskCallback var3);

    static interface TaskCallback {
        public void onResult(long var1, int var3);
    }
}

