/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import scala.Function6;
import scala.runtime.BoxedUnit;

@FunctionalInterface
public interface JProcedure6<T1, T2, T3, T4, T5, T6>
extends Function6<T1, T2, T3, T4, T5, T6, BoxedUnit> {
    public void applyVoid(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

    @Override
    default public BoxedUnit apply(T1 T1, T2 T2, T3 T3, T4 T4, T5 T5, T6 T6) {
        this.applyVoid(T1, T2, T3, T4, T5, T6);
        return BoxedUnit.UNIT;
    }
}

