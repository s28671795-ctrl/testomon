/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Predicate;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaPredicate;
import scala.compat.java8.functionConverterImpls.RichPredicateAsFunction1;

public final class RichPredicateAsFunction1$ {
    public static final RichPredicateAsFunction1$ MODULE$ = new RichPredicateAsFunction1$();

    public final <T> Function1<T, Object> asScala$extension(Predicate<T> $this) {
        return new FromJavaPredicate<T>($this);
    }

    public final <T> int hashCode$extension(Predicate<T> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(Predicate<T> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichPredicateAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        Predicate predicate = x$1 == null ? null : ((RichPredicateAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichPredicateAsFunction1$$underlying();
        Predicate<T> predicate2 = $this;
        Predicate predicate3 = predicate;
        if (predicate2 != null) {
            if (!predicate2.equals(predicate3)) return false;
            return true;
        }
        if (predicate3 == null) return true;
        return false;
    }

    private RichPredicateAsFunction1$() {
    }
}

