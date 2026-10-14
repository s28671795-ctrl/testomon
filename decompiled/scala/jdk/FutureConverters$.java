/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.util.concurrent.CompletionStage;
import scala.concurrent.Future;

public final class FutureConverters$ {
    public static final FutureConverters$ MODULE$ = new FutureConverters$();

    public <T> Future<T> FutureOps(Future<T> f) {
        return f;
    }

    public <T> CompletionStage<T> CompletionStageOps(CompletionStage<T> cs) {
        return cs;
    }

    private FutureConverters$() {
    }
}

