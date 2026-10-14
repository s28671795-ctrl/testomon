/*
 * Decompiled with CFR 0.152.
 */
package scala.ref;

import scala.Option;
import scala.Option$;
import scala.ref.SoftReference;

public final class SoftReference$ {
    public static final SoftReference$ MODULE$ = new SoftReference$();

    public <T> SoftReference<T> apply(T value) {
        return new SoftReference<T>(value);
    }

    public <T> Option<T> unapply(SoftReference<T> sr) {
        return Option$.MODULE$.apply(((java.lang.ref.SoftReference)sr.underlying()).get());
    }

    private SoftReference$() {
    }
}

