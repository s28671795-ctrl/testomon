/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.AsJavaLongFunction;
import scala.compat.java8.functionConverterImpls.RichFunction1AsLongFunction;

public final class RichFunction1AsLongFunction$ {
    public static final RichFunction1AsLongFunction$ MODULE$ = new RichFunction1AsLongFunction$();

    public final <R> LongFunction<R> asJava$extension(Function1<Object, R> $this) {
        return new AsJavaLongFunction<R>($this);
    }

    public final <R> int hashCode$extension(Function1<Object, R> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <R> boolean equals$extension(Function1<Object, R> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction1AsLongFunction)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function1 function1 = x$1 == null ? null : ((RichFunction1AsLongFunction)x$1).scala$compat$java8$functionConverterImpls$RichFunction1AsLongFunction$$underlying();
        Function1<Object, R> function12 = $this;
        Function1 function13 = function1;
        if (function12 != null) {
            if (!function12.equals(function13)) return false;
            return true;
        }
        if (function13 == null) return true;
        return false;
    }

    private RichFunction1AsLongFunction$() {
    }
}

