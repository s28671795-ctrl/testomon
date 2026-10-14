/*
 * Decompiled with CFR 0.152.
 */
package scala.ref;

import scala.Option;
import scala.Option$;
import scala.ref.WeakReference;

public final class WeakReference$ {
    public static final WeakReference$ MODULE$ = new WeakReference$();

    public <T> WeakReference<T> apply(T value) {
        return new WeakReference<T>(value);
    }

    public <T> Option<T> unapply(WeakReference<T> wr) {
        return Option$.MODULE$.apply(wr.underlying().get());
    }

    private WeakReference$() {
    }
}

