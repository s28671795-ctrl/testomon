/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntPredicate;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaIntPredicate;
import scala.compat.java8.functionConverterImpls.RichIntPredicateAsFunction1;

public final class RichIntPredicateAsFunction1$ {
    public static final RichIntPredicateAsFunction1$ MODULE$ = new RichIntPredicateAsFunction1$();

    public final Function1<Object, Object> asScala$extension(IntPredicate $this) {
        return new FromJavaIntPredicate($this);
    }

    public final int hashCode$extension(IntPredicate $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(IntPredicate $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichIntPredicateAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        IntPredicate intPredicate = x$1 == null ? null : ((RichIntPredicateAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichIntPredicateAsFunction1$$underlying();
        IntPredicate intPredicate2 = $this;
        IntPredicate intPredicate3 = intPredicate;
        if (intPredicate2 != null) {
            if (!intPredicate2.equals(intPredicate3)) return false;
            return true;
        }
        if (intPredicate3 == null) return true;
        return false;
    }

    private RichIntPredicateAsFunction1$() {
    }
}

