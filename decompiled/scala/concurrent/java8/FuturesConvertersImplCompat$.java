/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent.java8;

import scala.concurrent.ExecutionContext$parasitic$;

public final class FuturesConvertersImplCompat$ {
    public static final FuturesConvertersImplCompat$ MODULE$ = new FuturesConvertersImplCompat$();

    public ExecutionContext$parasitic$ InternalCallbackExecutor() {
        return ExecutionContext$parasitic$.MODULE$;
    }

    private FuturesConvertersImplCompat$() {
    }
}

