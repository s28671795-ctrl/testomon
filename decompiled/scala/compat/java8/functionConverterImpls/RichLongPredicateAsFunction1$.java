/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongPredicate;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaLongPredicate;
import scala.compat.java8.functionConverterImpls.RichLongPredicateAsFunction1;

public final class RichLongPredicateAsFunction1$ {
    public static final RichLongPredicateAsFunction1$ MODULE$ = new RichLongPredicateAsFunction1$();

    public final Function1<Object, Object> asScala$extension(LongPredicate $this) {
        return new FromJavaLongPredicate($this);
    }

    public final int hashCode$extension(LongPredicate $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(LongPredicate $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichLongPredicateAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        LongPredicate longPredicate = x$1 == null ? null : ((RichLongPredicateAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichLongPredicateAsFunction1$$underlying();
        LongPredicate longPredicate2 = $this;
        LongPredicate longPredicate3 = longPredicate;
        if (longPredicate2 != null) {
            if (!longPredicate2.equals(longPredicate3)) return false;
            return true;
        }
        if (longPredicate3 == null) return true;
        return false;
    }

    private RichLongPredicateAsFunction1$() {
    }
}

