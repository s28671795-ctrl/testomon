/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import scala.Function7;
import scala.runtime.BoxedUnit;

@FunctionalInterface
public interface JProcedure7<T1, T2, T3, T4, T5, T6, T7>
extends Function7<T1, T2, T3, T4, T5, T6, T7, BoxedUnit> {
    public void applyVoid(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

    @Override
    default public BoxedUnit apply(T1 T1, T2 T2, T3 T3, T4 T4, T5 T5, T6 T6, T7 T7) {
        this.applyVoid(T1, T2, T3, T4, T5, T6, T7);
        return BoxedUnit.UNIT;
    }
}

