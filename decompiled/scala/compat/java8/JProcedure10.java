/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import scala.Function10;
import scala.runtime.BoxedUnit;

@FunctionalInterface
public interface JProcedure10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>
extends Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, BoxedUnit> {
    public void applyVoid(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

    @Override
    default public BoxedUnit apply(T1 T1, T2 T2, T3 T3, T4 T4, T5 T5, T6 T6, T7 T7, T8 T8, T9 T9, T10 T10) {
        this.applyVoid(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10);
        return BoxedUnit.UNIT;
    }
}

