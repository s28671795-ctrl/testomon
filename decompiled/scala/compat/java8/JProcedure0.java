/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import scala.Function0;
import scala.runtime.BoxedUnit;

@FunctionalInterface
public interface JProcedure0
extends Function0<BoxedUnit> {
    public void applyVoid();

    @Override
    default public BoxedUnit apply() {
        this.applyVoid();
        return BoxedUnit.UNIT;
    }
}

