/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import scala.Function1;
import scala.runtime.BoxedUnit;

@FunctionalInterface
public interface JProcedure1<T1>
extends Function1<T1, BoxedUnit> {
    public void applyVoid(T1 var1);

    @Override
    default public BoxedUnit apply(T1 T1) {
        this.applyVoid(T1);
        return BoxedUnit.UNIT;
    }
}

