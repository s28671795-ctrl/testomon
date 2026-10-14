/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.UnaryOperator;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.AsJavaUnaryOperator;
import scala.compat.java8.functionConverterImpls.RichFunction1AsUnaryOperator;

public final class RichFunction1AsUnaryOperator$ {
    public static final RichFunction1AsUnaryOperator$ MODULE$ = new RichFunction1AsUnaryOperator$();

    public final <T> UnaryOperator<T> asJava$extension(Function1<T, T> $this) {
        return new AsJavaUnaryOperator<T>($this);
    }

    public final <T> int hashCode$extension(Function1<T, T> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(Function1<T, T> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction1AsUnaryOperator)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function1 function1 = x$1 == null ? null : ((RichFunction1AsUnaryOperator)x$1).scala$compat$java8$functionConverterImpls$RichFunction1AsUnaryOperator$$underlying();
        Function1<T, T> function12 = $this;
        Function1 function13 = function1;
        if (function12 != null) {
            if (!function12.equals(function13)) return false;
            return true;
        }
        if (function13 == null) return true;
        return false;
    }

    private RichFunction1AsUnaryOperator$() {
    }
}

