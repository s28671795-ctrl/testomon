/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongToIntFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaLongToIntFunction;
import scala.compat.java8.functionConverterImpls.RichLongToIntFunctionAsFunction1;

public final class RichLongToIntFunctionAsFunction1$ {
    public static final RichLongToIntFunctionAsFunction1$ MODULE$ = new RichLongToIntFunctionAsFunction1$();

    public final Function1<Object, Object> asScala$extension(LongToIntFunction $this) {
        return new FromJavaLongToIntFunction($this);
    }

    public final int hashCode$extension(LongToIntFunction $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(LongToIntFunction $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichLongToIntFunctionAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        LongToIntFunction longToIntFunction = x$1 == null ? null : ((RichLongToIntFunctionAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichLongToIntFunctionAsFunction1$$underlying();
        LongToIntFunction longToIntFunction2 = $this;
        LongToIntFunction longToIntFunction3 = longToIntFunction;
        if (longToIntFunction2 != null) {
            if (!longToIntFunction2.equals(longToIntFunction3)) return false;
            return true;
        }
        if (longToIntFunction3 == null) return true;
        return false;
    }

    private RichLongToIntFunctionAsFunction1$() {
    }
}

