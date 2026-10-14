/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.ValueOf;
import scala.runtime.BoxesRunTime;

public final class ValueOf$ {
    public static final ValueOf$ MODULE$ = new ValueOf$();

    public final <T> int hashCode$extension(T $this) {
        return $this.hashCode();
    }

    public final <T> boolean equals$extension(T $this, Object x$1) {
        if (x$1 instanceof ValueOf) {
            Object var3_3;
            Object v0 = var3_3 = x$1 == null ? null : ((ValueOf)x$1).value();
            if (BoxesRunTime.equals($this, var3_3)) {
                return true;
            }
        }
        return false;
    }

    private ValueOf$() {
    }
}

