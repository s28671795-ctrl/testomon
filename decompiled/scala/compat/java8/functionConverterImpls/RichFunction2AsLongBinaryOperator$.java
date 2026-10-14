/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongBinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.AsJavaLongBinaryOperator;
import scala.compat.java8.functionConverterImpls.RichFunction2AsLongBinaryOperator;

public final class RichFunction2AsLongBinaryOperator$ {
    public static final RichFunction2AsLongBinaryOperator$ MODULE$ = new RichFunction2AsLongBinaryOperator$();

    public final LongBinaryOperator asJava$extension(Function2 $this) {
        return new AsJavaLongBinaryOperator($this);
    }

    public final int hashCode$extension(Function2 $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(Function2 $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction2AsLongBinaryOperator)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function2<Object, Object, Object> function2 = x$1 == null ? null : ((RichFunction2AsLongBinaryOperator)x$1).scala$compat$java8$functionConverterImpls$RichFunction2AsLongBinaryOperator$$underlying();
        Function2 function22 = $this;
        Function2<Object, Object, Object> function23 = function2;
        if (function22 != null) {
            if (!function22.equals(function23)) return false;
            return true;
        }
        if (function23 == null) return true;
        return false;
    }

    private RichFunction2AsLongBinaryOperator$() {
    }
}

