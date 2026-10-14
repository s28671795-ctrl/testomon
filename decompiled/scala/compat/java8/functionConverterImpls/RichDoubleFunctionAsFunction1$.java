/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaDoubleFunction;
import scala.compat.java8.functionConverterImpls.RichDoubleFunctionAsFunction1;

public final class RichDoubleFunctionAsFunction1$ {
    public static final RichDoubleFunctionAsFunction1$ MODULE$ = new RichDoubleFunctionAsFunction1$();

    public final <R> Function1<Object, R> asScala$extension(DoubleFunction<R> $this) {
        return new FromJavaDoubleFunction<R>($this);
    }

    public final <R> int hashCode$extension(DoubleFunction<R> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <R> boolean equals$extension(DoubleFunction<R> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichDoubleFunctionAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        DoubleFunction doubleFunction = x$1 == null ? null : ((RichDoubleFunctionAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichDoubleFunctionAsFunction1$$underlying();
        DoubleFunction<R> doubleFunction2 = $this;
        DoubleFunction doubleFunction3 = doubleFunction;
        if (doubleFunction2 != null) {
            if (!doubleFunction2.equals(doubleFunction3)) return false;
            return true;
        }
        if (doubleFunction3 == null) return true;
        return false;
    }

    private RichDoubleFunctionAsFunction1$() {
    }
}

