/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import scala.Function5;
import scala.runtime.BoxedUnit;

@FunctionalInterface
public interface JProcedure5<T1, T2, T3, T4, T5>
extends Function5<T1, T2, T3, T4, T5, BoxedUnit> {
    public void applyVoid(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

    @Override
    default public BoxedUnit apply(T1 T1, T2 T2, T3 T3, T4 T4, T5 T5) {
        this.applyVoid(T1, T2, T3, T4, T5);
        return BoxedUnit.UNIT;
    }
}

