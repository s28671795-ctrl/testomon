/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaIntFunction;
import scala.compat.java8.functionConverterImpls.RichIntFunctionAsFunction1;

public final class RichIntFunctionAsFunction1$ {
    public static final RichIntFunctionAsFunction1$ MODULE$ = new RichIntFunctionAsFunction1$();

    public final <R> Function1<Object, R> asScala$extension(IntFunction<R> $this) {
        return new FromJavaIntFunction<R>($this);
    }

    public final <R> int hashCode$extension(IntFunction<R> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <R> boolean equals$extension(IntFunction<R> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichIntFunctionAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        IntFunction intFunction = x$1 == null ? null : ((RichIntFunctionAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichIntFunctionAsFunction1$$underlying();
        IntFunction<R> intFunction2 = $this;
        IntFunction intFunction3 = intFunction;
        if (intFunction2 != null) {
            if (!intFunction2.equals(intFunction3)) return false;
            return true;
        }
        if (intFunction3 == null) return true;
        return false;
    }

    private RichIntFunctionAsFunction1$() {
    }
}

