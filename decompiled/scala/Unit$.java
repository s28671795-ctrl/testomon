/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.AnyValCompanion;
import scala.runtime.BoxedUnit;

public final class Unit$
implements AnyValCompanion {
    public static final Unit$ MODULE$ = new Unit$();

    public BoxedUnit box(BoxedUnit x) {
        return BoxedUnit.UNIT;
    }

    public void unbox(Object x) {
        BoxedUnit cfr_ignored_0 = (BoxedUnit)x;
    }

    public String toString() {
        return "object scala.Unit";
    }

    private Unit$() {
    }
}

