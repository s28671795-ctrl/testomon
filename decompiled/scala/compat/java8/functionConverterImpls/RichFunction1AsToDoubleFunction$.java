/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToDoubleFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.AsJavaToDoubleFunction;
import scala.compat.java8.functionConverterImpls.RichFunction1AsToDoubleFunction;

public final class RichFunction1AsToDoubleFunction$ {
    public static final RichFunction1AsToDoubleFunction$ MODULE$ = new RichFunction1AsToDoubleFunction$();

    public final <T> ToDoubleFunction<T> asJava$extension(Function1<T, Object> $this) {
        return new AsJavaToDoubleFunction<T>($this);
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
        if (!(object instanceof RichFunction1AsToDoubleFunction)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function1 function1 = x$1 == null ? null : ((RichFunction1AsToDoubleFunction)x$1).scala$compat$java8$functionConverterImpls$RichFunction1AsToDoubleFunction$$underlying();
        Function1<T, Object> function12 = $this;
        Function1 function13 = function1;
        if (function12 != null) {
            if (!function12.equals(function13)) return false;
            return true;
        }
        if (function13 == null) return true;
        return false;
    }

    private RichFunction1AsToDoubleFunction$() {
    }
}

