/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import java.io.Serializable;
import scala.math.Fractional;
import scala.runtime.ModuleSerializationProxy;

public final class Fractional$
implements Serializable {
    public static final Fractional$ MODULE$ = new Fractional$();

    public <T> Fractional<T> apply(Fractional<T> frac) {
        return frac;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Fractional$.class);
    }

    private Fractional$() {
    }
}

