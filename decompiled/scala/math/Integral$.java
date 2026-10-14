/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import java.io.Serializable;
import scala.math.Integral;
import scala.runtime.ModuleSerializationProxy;

public final class Integral$
implements Serializable {
    public static final Integral$ MODULE$ = new Integral$();

    public <T> Integral<T> apply(Integral<T> integral) {
        return integral;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Integral$.class);
    }

    private Integral$() {
    }
}

