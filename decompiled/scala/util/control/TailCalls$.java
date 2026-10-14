/*
 * Decompiled with CFR 0.152.
 */
package scala.util.control;

import scala.Function0;
import scala.util.control.TailCalls;

public final class TailCalls$ {
    public static final TailCalls$ MODULE$ = new TailCalls$();

    public <A> TailCalls.TailRec<A> tailcall(Function0<TailCalls.TailRec<A>> rest) {
        return new TailCalls.Call<A>(rest);
    }

    public <A> TailCalls.TailRec<A> done(A result) {
        return new TailCalls.Done<A>(result);
    }

    private TailCalls$() {
    }
}

