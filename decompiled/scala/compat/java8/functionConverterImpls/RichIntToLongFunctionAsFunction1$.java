/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntToLongFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaIntToLongFunction;
import scala.compat.java8.functionConverterImpls.RichIntToLongFunctionAsFunction1;

public final class RichIntToLongFunctionAsFunction1$ {
    public static final RichIntToLongFunctionAsFunction1$ MODULE$ = new RichIntToLongFunctionAsFunction1$();

    public final Function1<Object, Object> asScala$extension(IntToLongFunction $this) {
        return new FromJavaIntToLongFunction($this);
    }

    public final int hashCode$extension(IntToLongFunction $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(IntToLongFunction $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichIntToLongFunctionAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        IntToLongFunction intToLongFunction = x$1 == null ? null : ((RichIntToLongFunctionAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichIntToLongFunctionAsFunction1$$underlying();
        IntToLongFunction intToLongFunction2 = $this;
        IntToLongFunction intToLongFunction3 = intToLongFunction;
        if (intToLongFunction2 != null) {
            if (!intToLongFunction2.equals(intToLongFunction3)) return false;
            return true;
        }
        if (intToLongFunction3 == null) return true;
        return false;
    }

    private RichIntToLongFunctionAsFunction1$() {
    }
}

