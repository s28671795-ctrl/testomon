/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntToDoubleFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaIntToDoubleFunction;
import scala.compat.java8.functionConverterImpls.RichIntToDoubleFunctionAsFunction1;

public final class RichIntToDoubleFunctionAsFunction1$ {
    public static final RichIntToDoubleFunctionAsFunction1$ MODULE$ = new RichIntToDoubleFunctionAsFunction1$();

    public final Function1<Object, Object> asScala$extension(IntToDoubleFunction $this) {
        return new FromJavaIntToDoubleFunction($this);
    }

    public final int hashCode$extension(IntToDoubleFunction $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(IntToDoubleFunction $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichIntToDoubleFunctionAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        IntToDoubleFunction intToDoubleFunction = x$1 == null ? null : ((RichIntToDoubleFunctionAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichIntToDoubleFunctionAsFunction1$$underlying();
        IntToDoubleFunction intToDoubleFunction2 = $this;
        IntToDoubleFunction intToDoubleFunction3 = intToDoubleFunction;
        if (intToDoubleFunction2 != null) {
            if (!intToDoubleFunction2.equals(intToDoubleFunction3)) return false;
            return true;
        }
        if (intToDoubleFunction3 == null) return true;
        return false;
    }

    private RichIntToDoubleFunctionAsFunction1$() {
    }
}

