/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntBinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.AsJavaIntBinaryOperator;
import scala.compat.java8.functionConverterImpls.RichFunction2AsIntBinaryOperator;

public final class RichFunction2AsIntBinaryOperator$ {
    public static final RichFunction2AsIntBinaryOperator$ MODULE$ = new RichFunction2AsIntBinaryOperator$();

    public final IntBinaryOperator asJava$extension(Function2 $this) {
        return new AsJavaIntBinaryOperator($this);
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
        if (!(object instanceof RichFunction2AsIntBinaryOperator)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function2<Object, Object, Object> function2 = x$1 == null ? null : ((RichFunction2AsIntBinaryOperator)x$1).scala$compat$java8$functionConverterImpls$RichFunction2AsIntBinaryOperator$$underlying();
        Function2 function22 = $this;
        Function2<Object, Object, Object> function23 = function2;
        if (function22 != null) {
            if (!function22.equals(function23)) return false;
            return true;
        }
        if (function23 == null) return true;
        return false;
    }

    private RichFunction2AsIntBinaryOperator$() {
    }
}

