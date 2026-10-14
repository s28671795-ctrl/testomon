/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import java.io.Serializable;
import scala.math.Numeric;
import scala.runtime.ModuleSerializationProxy;

public final class Numeric$
implements Serializable {
    public static final Numeric$ MODULE$ = new Numeric$();

    public <T> Numeric<T> apply(Numeric<T> num) {
        return num;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Numeric$.class);
    }

    private Numeric$() {
    }
}

