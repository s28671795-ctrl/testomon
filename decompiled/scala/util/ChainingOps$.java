/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import scala.Function1;
import scala.runtime.BoxesRunTime;
import scala.util.ChainingOps;

public final class ChainingOps$ {
    public static final ChainingOps$ MODULE$ = new ChainingOps$();

    public final <U, A> A tap$extension(A $this, Function1<A, U> f) {
        f.apply($this);
        return $this;
    }

    public final <B, A> B pipe$extension(A $this, Function1<A, B> f) {
        return f.apply($this);
    }

    public final <A> int hashCode$extension(A $this) {
        return $this.hashCode();
    }

    public final <A> boolean equals$extension(A $this, Object x$1) {
        if (x$1 instanceof ChainingOps) {
            Object var3_3;
            Object v0 = var3_3 = x$1 == null ? null : ((ChainingOps)x$1).scala$util$ChainingOps$$self();
            if (BoxesRunTime.equals($this, var3_3)) {
                return true;
            }
        }
        return false;
    }

    private ChainingOps$() {
    }
}

