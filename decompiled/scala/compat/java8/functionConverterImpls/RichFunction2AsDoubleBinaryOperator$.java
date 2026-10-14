/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleBinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.AsJavaDoubleBinaryOperator;
import scala.compat.java8.functionConverterImpls.RichFunction2AsDoubleBinaryOperator;

public final class RichFunction2AsDoubleBinaryOperator$ {
    public static final RichFunction2AsDoubleBinaryOperator$ MODULE$ = new RichFunction2AsDoubleBinaryOperator$();

    public final DoubleBinaryOperator asJava$extension(Function2 $this) {
        return new AsJavaDoubleBinaryOperator($this);
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
        if (!(object instanceof RichFunction2AsDoubleBinaryOperator)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function2<Object, Object, Object> function2 = x$1 == null ? null : ((RichFunction2AsDoubleBinaryOperator)x$1).scala$compat$java8$functionConverterImpls$RichFunction2AsDoubleBinaryOperator$$underlying();
        Function2 function22 = $this;
        Function2<Object, Object, Object> function23 = function2;
        if (function22 != null) {
            if (!function22.equals(function23)) return false;
            return true;
        }
        if (function23 == null) return true;
        return false;
    }

    private RichFunction2AsDoubleBinaryOperator$() {
    }
}

