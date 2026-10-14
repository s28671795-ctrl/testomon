/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import scala.Function3;
import scala.runtime.BoxedUnit;

@FunctionalInterface
public interface JProcedure3<T1, T2, T3>
extends Function3<T1, T2, T3, BoxedUnit> {
    public void applyVoid(T1 var1, T2 var2, T3 var3);

    @Override
    default public BoxedUnit apply(T1 T1, T2 T2, T3 T3) {
        this.applyVoid(T1, T2, T3);
        return BoxedUnit.UNIT;
    }
}

