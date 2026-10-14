/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiFunction;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.AsJavaBiFunction;
import scala.compat.java8.functionConverterImpls.RichFunction2AsBiFunction;

public final class RichFunction2AsBiFunction$ {
    public static final RichFunction2AsBiFunction$ MODULE$ = new RichFunction2AsBiFunction$();

    public final <T, U, R> BiFunction<T, U, R> asJava$extension(Function2<T, U, R> $this) {
        return new AsJavaBiFunction<T, U, R>($this);
    }

    public final <T, U, R> int hashCode$extension(Function2<T, U, R> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T, U, R> boolean equals$extension(Function2<T, U, R> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction2AsBiFunction)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function2 function2 = x$1 == null ? null : ((RichFunction2AsBiFunction)x$1).scala$compat$java8$functionConverterImpls$RichFunction2AsBiFunction$$underlying();
        Function2<T, U, R> function22 = $this;
        Function2 function23 = function2;
        if (function22 != null) {
            if (!function22.equals(function23)) return false;
            return true;
        }
        if (function23 == null) return true;
        return false;
    }

    private RichFunction2AsBiFunction$() {
    }
}

