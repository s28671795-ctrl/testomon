/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.math.Ordering$Boolean$;
import scala.runtime.RichBoolean;

public final class RichBoolean$ {
    public static final RichBoolean$ MODULE$ = new RichBoolean$();

    public final Ordering$Boolean$ ord$extension(boolean $this) {
        return Ordering$Boolean$.MODULE$;
    }

    public final int hashCode$extension(boolean $this) {
        return Boolean.hashCode($this);
    }

    public final boolean equals$extension(boolean $this, Object x$1) {
        boolean bl;
        return x$1 instanceof RichBoolean && $this == (bl = ((RichBoolean)x$1).self());
    }

    private RichBoolean$() {
    }
}

