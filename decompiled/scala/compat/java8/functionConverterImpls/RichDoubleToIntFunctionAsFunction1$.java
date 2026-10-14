/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleToIntFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaDoubleToIntFunction;
import scala.compat.java8.functionConverterImpls.RichDoubleToIntFunctionAsFunction1;

public final class RichDoubleToIntFunctionAsFunction1$ {
    public static final RichDoubleToIntFunctionAsFunction1$ MODULE$ = new RichDoubleToIntFunctionAsFunction1$();

    public final Function1<Object, Object> asScala$extension(DoubleToIntFunction $this) {
        return new FromJavaDoubleToIntFunction($this);
    }

    public final int hashCode$extension(DoubleToIntFunction $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(DoubleToIntFunction $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichDoubleToIntFunctionAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        DoubleToIntFunction doubleToIntFunction = x$1 == null ? null : ((RichDoubleToIntFunctionAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichDoubleToIntFunctionAsFunction1$$underlying();
        DoubleToIntFunction doubleToIntFunction2 = $this;
        DoubleToIntFunction doubleToIntFunction3 = doubleToIntFunction;
        if (doubleToIntFunction2 != null) {
            if (!doubleToIntFunction2.equals(doubleToIntFunction3)) return false;
            return true;
        }
        if (doubleToIntFunction3 == null) return true;
        return false;
    }

    private RichDoubleToIntFunctionAsFunction1$() {
    }
}

