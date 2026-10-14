/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Function;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.AsJavaFunction;
import scala.compat.java8.functionConverterImpls.RichFunction1AsFunction;

public final class RichFunction1AsFunction$ {
    public static final RichFunction1AsFunction$ MODULE$ = new RichFunction1AsFunction$();

    public final <T, R> Function<T, R> asJava$extension(Function1<T, R> $this) {
        return new AsJavaFunction<T, R>($this);
    }

    public final <T, R> int hashCode$extension(Function1<T, R> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T, R> boolean equals$extension(Function1<T, R> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction1AsFunction)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function1 function1 = x$1 == null ? null : ((RichFunction1AsFunction)x$1).scala$compat$java8$functionConverterImpls$RichFunction1AsFunction$$underlying();
        Function1<T, R> function12 = $this;
        Function1 function13 = function1;
        if (function12 != null) {
            if (!function12.equals(function13)) return false;
            return true;
        }
        if (function13 == null) return true;
        return false;
    }

    private RichFunction1AsFunction$() {
    }
}

