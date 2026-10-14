/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoublePredicate;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaDoublePredicate;
import scala.compat.java8.functionConverterImpls.RichDoublePredicateAsFunction1;

public final class RichDoublePredicateAsFunction1$ {
    public static final RichDoublePredicateAsFunction1$ MODULE$ = new RichDoublePredicateAsFunction1$();

    public final Function1<Object, Object> asScala$extension(DoublePredicate $this) {
        return new FromJavaDoublePredicate($this);
    }

    public final int hashCode$extension(DoublePredicate $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(DoublePredicate $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichDoublePredicateAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        DoublePredicate doublePredicate = x$1 == null ? null : ((RichDoublePredicateAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichDoublePredicateAsFunction1$$underlying();
        DoublePredicate doublePredicate2 = $this;
        DoublePredicate doublePredicate3 = doublePredicate;
        if (doublePredicate2 != null) {
            if (!doublePredicate2.equals(doublePredicate3)) return false;
            return true;
        }
        if (doublePredicate3 == null) return true;
        return false;
    }

    private RichDoublePredicateAsFunction1$() {
    }
}

