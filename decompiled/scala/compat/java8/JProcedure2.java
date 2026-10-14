/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import scala.Function2;
import scala.runtime.BoxedUnit;

@FunctionalInterface
public interface JProcedure2<T1, T2>
extends Function2<T1, T2, BoxedUnit> {
    public void applyVoid(T1 var1, T2 var2);

    @Override
    default public BoxedUnit apply(T1 T1, T2 T2) {
        this.applyVoid(T1, T2);
        return BoxedUnit.UNIT;
    }
}

