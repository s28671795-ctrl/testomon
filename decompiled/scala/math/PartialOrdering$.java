/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import java.io.Serializable;
import scala.math.PartialOrdering;
import scala.runtime.ModuleSerializationProxy;

public final class PartialOrdering$
implements Serializable {
    public static final PartialOrdering$ MODULE$ = new PartialOrdering$();

    public <T> PartialOrdering<T> apply(PartialOrdering<T> ev) {
        return ev;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(PartialOrdering$.class);
    }

    private PartialOrdering$() {
    }
}

