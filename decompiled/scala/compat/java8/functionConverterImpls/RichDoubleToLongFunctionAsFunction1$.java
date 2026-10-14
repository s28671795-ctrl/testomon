/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleToLongFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaDoubleToLongFunction;
import scala.compat.java8.functionConverterImpls.RichDoubleToLongFunctionAsFunction1;

public final class RichDoubleToLongFunctionAsFunction1$ {
    public static final RichDoubleToLongFunctionAsFunction1$ MODULE$ = new RichDoubleToLongFunctionAsFunction1$();

    public final Function1<Object, Object> asScala$extension(DoubleToLongFunction $this) {
        return new FromJavaDoubleToLongFunction($this);
    }

    public final int hashCode$extension(DoubleToLongFunction $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(DoubleToLongFunction $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichDoubleToLongFunctionAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        DoubleToLongFunction doubleToLongFunction = x$1 == null ? null : ((RichDoubleToLongFunctionAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichDoubleToLongFunctionAsFunction1$$underlying();
        DoubleToLongFunction doubleToLongFunction2 = $this;
        DoubleToLongFunction doubleToLongFunction3 = doubleToLongFunction;
        if (doubleToLongFunction2 != null) {
            if (!doubleToLongFunction2.equals(doubleToLongFunction3)) return false;
            return true;
        }
        if (doubleToLongFunction3 == null) return true;
        return false;
    }

    private RichDoubleToLongFunctionAsFunction1$() {
    }
}

