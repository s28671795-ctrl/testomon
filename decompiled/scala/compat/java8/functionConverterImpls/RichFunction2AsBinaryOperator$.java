/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.AsJavaBinaryOperator;
import scala.compat.java8.functionConverterImpls.RichFunction2AsBinaryOperator;

public final class RichFunction2AsBinaryOperator$ {
    public static final RichFunction2AsBinaryOperator$ MODULE$ = new RichFunction2AsBinaryOperator$();

    public final <T> BinaryOperator<T> asJava$extension(Function2<T, T, T> $this) {
        return new AsJavaBinaryOperator<T>($this);
    }

    public final <T> int hashCode$extension(Function2<T, T, T> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(Function2<T, T, T> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction2AsBinaryOperator)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function2 function2 = x$1 == null ? null : ((RichFunction2AsBinaryOperator)x$1).scala$compat$java8$functionConverterImpls$RichFunction2AsBinaryOperator$$underlying();
        Function2<T, T, T> function22 = $this;
        Function2 function23 = function2;
        if (function22 != null) {
            if (!function22.equals(function23)) return false;
            return true;
        }
        if (function23 == null) return true;
        return false;
    }

    private RichFunction2AsBinaryOperator$() {
    }
}

