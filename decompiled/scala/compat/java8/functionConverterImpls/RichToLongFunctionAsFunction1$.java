/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToLongFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaToLongFunction;
import scala.compat.java8.functionConverterImpls.RichToLongFunctionAsFunction1;

public final class RichToLongFunctionAsFunction1$ {
    public static final RichToLongFunctionAsFunction1$ MODULE$ = new RichToLongFunctionAsFunction1$();

    public final <T> Function1<T, Object> asScala$extension(ToLongFunction<T> $this) {
        return new FromJavaToLongFunction<T>($this);
    }

    public final <T> int hashCode$extension(ToLongFunction<T> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(ToLongFunction<T> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichToLongFunctionAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        ToLongFunction toLongFunction = x$1 == null ? null : ((RichToLongFunctionAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichToLongFunctionAsFunction1$$underlying();
        ToLongFunction<T> toLongFunction2 = $this;
        ToLongFunction toLongFunction3 = toLongFunction;
        if (toLongFunction2 != null) {
            if (!toLongFunction2.equals(toLongFunction3)) return false;
            return true;
        }
        if (toLongFunction3 == null) return true;
        return false;
    }

    private RichToLongFunctionAsFunction1$() {
    }
}

