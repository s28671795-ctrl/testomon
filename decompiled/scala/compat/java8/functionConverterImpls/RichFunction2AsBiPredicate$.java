/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiPredicate;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.AsJavaBiPredicate;
import scala.compat.java8.functionConverterImpls.RichFunction2AsBiPredicate;

public final class RichFunction2AsBiPredicate$ {
    public static final RichFunction2AsBiPredicate$ MODULE$ = new RichFunction2AsBiPredicate$();

    public final <T, U> BiPredicate<T, U> asJava$extension(Function2<T, U, Object> $this) {
        return new AsJavaBiPredicate<T, U>($this);
    }

    public final <T, U> int hashCode$extension(Function2<T, U, Object> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T, U> boolean equals$extension(Function2<T, U, Object> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction2AsBiPredicate)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function2 function2 = x$1 == null ? null : ((RichFunction2AsBiPredicate)x$1).scala$compat$java8$functionConverterImpls$RichFunction2AsBiPredicate$$underlying();
        Function2<T, U, Object> function22 = $this;
        Function2 function23 = function2;
        if (function22 != null) {
            if (!function22.equals(function23)) return false;
            return true;
        }
        if (function23 == null) return true;
        return false;
    }

    private RichFunction2AsBiPredicate$() {
    }
}

