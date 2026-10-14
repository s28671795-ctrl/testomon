/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk.javaapi;

import java.util.concurrent.CompletionStage;
import scala.concurrent.ExecutionContext$parasitic$;
import scala.concurrent.Future;
import scala.concurrent.impl.FutureConvertersImpl;

public final class FutureConverters$ {
    public static final FutureConverters$ MODULE$ = new FutureConverters$();

    public <T> CompletionStage<T> asJava(Future<T> f) {
        if (f instanceof FutureConvertersImpl.P) {
            return ((FutureConvertersImpl.P)f).wrapped();
        }
        if (f instanceof CompletionStage) {
            return (CompletionStage)((Object)f);
        }
        FutureConvertersImpl.CF<T> cf = new FutureConvertersImpl.CF<T>(f);
        f.onComplete(cf, ExecutionContext$parasitic$.MODULE$);
        return cf;
    }

    public <T> Future<T> asScala(CompletionStage<T> cs) {
        if (cs instanceof FutureConvertersImpl.CF) {
            return ((FutureConvertersImpl.CF)cs).wrapped();
        }
        if (cs instanceof Future) {
            return (Future)((Object)cs);
        }
        FutureConvertersImpl.P<T> p = new FutureConvertersImpl.P<T>(cs);
        cs.whenComplete(p);
        return p;
    }

    private FutureConverters$() {
    }
}

