/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.internal;

import akka.annotation.InternalApi;
import akka.dispatch.Batchable;

@InternalApi
public final class ScalaBatchable$ {
    public static final ScalaBatchable$ MODULE$ = new ScalaBatchable$();

    public boolean isBatchable(Runnable runnable2) {
        Runnable runnable3 = runnable2;
        if (runnable3 instanceof Batchable) {
            Batchable batchable = (Batchable)runnable3;
            return batchable.isBatchable();
        }
        return runnable3 instanceof scala.concurrent.Batchable;
    }

    private ScalaBatchable$() {
    }
}

