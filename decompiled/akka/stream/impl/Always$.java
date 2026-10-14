/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.TransferState;

@InternalApi
public final class Always$
implements TransferState {
    public static final Always$ MODULE$ = new Always$();

    static {
        TransferState.$init$(MODULE$);
    }

    @Override
    public boolean isExecutable() {
        return TransferState.isExecutable$(this);
    }

    @Override
    public TransferState $bar$bar(TransferState other) {
        return TransferState.$bar$bar$(this, other);
    }

    @Override
    public TransferState $amp$amp(TransferState other) {
        return TransferState.$amp$amp$(this, other);
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    private Always$() {
    }
}

