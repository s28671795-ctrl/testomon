/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Function1;
import scala.Option;

public final class Function1$ {
    public static final Function1$ MODULE$ = new Function1$();

    public final <A, B> Function1<A, Option<B>> UnliftOps(Function1<A, Option<B>> f) {
        return f;
    }

    private Function1$() {
    }
}

