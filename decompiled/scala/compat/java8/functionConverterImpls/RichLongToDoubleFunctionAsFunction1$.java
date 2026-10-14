/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongToDoubleFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaLongToDoubleFunction;
import scala.compat.java8.functionConverterImpls.RichLongToDoubleFunctionAsFunction1;

public final class RichLongToDoubleFunctionAsFunction1$ {
    public static final RichLongToDoubleFunctionAsFunction1$ MODULE$ = new RichLongToDoubleFunctionAsFunction1$();

    public final Function1<Object, Object> asScala$extension(LongToDoubleFunction $this) {
        return new FromJavaLongToDoubleFunction($this);
    }

    public final int hashCode$extension(LongToDoubleFunction $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(LongToDoubleFunction $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichLongToDoubleFunctionAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        LongToDoubleFunction longToDoubleFunction = x$1 == null ? null : ((RichLongToDoubleFunctionAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichLongToDoubleFunctionAsFunction1$$underlying();
        LongToDoubleFunction longToDoubleFunction2 = $this;
        LongToDoubleFunction longToDoubleFunction3 = longToDoubleFunction;
        if (longToDoubleFunction2 != null) {
            if (!longToDoubleFunction2.equals(longToDoubleFunction3)) return false;
            return true;
        }
        if (longToDoubleFunction3 == null) return true;
        return false;
    }

    private RichLongToDoubleFunctionAsFunction1$() {
    }
}

