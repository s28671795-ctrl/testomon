/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Function;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaFunction;
import scala.compat.java8.functionConverterImpls.RichFunctionAsFunction1;

public final class RichFunctionAsFunction1$ {
    public static final RichFunctionAsFunction1$ MODULE$ = new RichFunctionAsFunction1$();

    public final <T, R> Function1<T, R> asScala$extension(Function<T, R> $this) {
        return new FromJavaFunction<T, R>($this);
    }

    public final <T, R> int hashCode$extension(Function<T, R> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T, R> boolean equals$extension(Function<T, R> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunctionAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function function = x$1 == null ? null : ((RichFunctionAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichFunctionAsFunction1$$underlying();
        Function<T, R> function2 = $this;
        Function function3 = function;
        if (function2 != null) {
            if (!function2.equals(function3)) return false;
            return true;
        }
        if (function3 == null) return true;
        return false;
    }

    private RichFunctionAsFunction1$() {
    }
}

