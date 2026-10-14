/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Predicate;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.AsJavaPredicate;
import scala.compat.java8.functionConverterImpls.RichFunction1AsPredicate;

public final class RichFunction1AsPredicate$ {
    public static final RichFunction1AsPredicate$ MODULE$ = new RichFunction1AsPredicate$();

    public final <T> Predicate<T> asJava$extension(Function1<T, Object> $this) {
        return new AsJavaPredicate<T>($this);
    }

    public final <T> int hashCode$extension(Function1<T, Object> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(Function1<T, Object> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction1AsPredicate)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function1 function1 = x$1 == null ? null : ((RichFunction1AsPredicate)x$1).scala$compat$java8$functionConverterImpls$RichFunction1AsPredicate$$underlying();
        Function1<T, Object> function12 = $this;
        Function1 function13 = function1;
        if (function12 != null) {
            if (!function12.equals(function13)) return false;
            return true;
        }
        if (function13 == null) return true;
        return false;
    }

    private RichFunction1AsPredicate$() {
    }
}

