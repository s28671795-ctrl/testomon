/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaLongFunction;
import scala.compat.java8.functionConverterImpls.RichLongFunctionAsFunction1;

public final class RichLongFunctionAsFunction1$ {
    public static final RichLongFunctionAsFunction1$ MODULE$ = new RichLongFunctionAsFunction1$();

    public final <R> Function1<Object, R> asScala$extension(LongFunction<R> $this) {
        return new FromJavaLongFunction<R>($this);
    }

    public final <R> int hashCode$extension(LongFunction<R> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <R> boolean equals$extension(LongFunction<R> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichLongFunctionAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        LongFunction longFunction = x$1 == null ? null : ((RichLongFunctionAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichLongFunctionAsFunction1$$underlying();
        LongFunction<R> longFunction2 = $this;
        LongFunction longFunction3 = longFunction;
        if (longFunction2 != null) {
            if (!longFunction2.equals(longFunction3)) return false;
            return true;
        }
        if (longFunction3 == null) return true;
        return false;
    }

    private RichLongFunctionAsFunction1$() {
    }
}

