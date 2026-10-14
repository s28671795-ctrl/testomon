/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongBinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.FromJavaLongBinaryOperator;
import scala.compat.java8.functionConverterImpls.RichLongBinaryOperatorAsFunction2;

public final class RichLongBinaryOperatorAsFunction2$ {
    public static final RichLongBinaryOperatorAsFunction2$ MODULE$ = new RichLongBinaryOperatorAsFunction2$();

    public final Function2<Object, Object, Object> asScala$extension(LongBinaryOperator $this) {
        return new FromJavaLongBinaryOperator($this);
    }

    public final int hashCode$extension(LongBinaryOperator $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(LongBinaryOperator $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichLongBinaryOperatorAsFunction2)) return false;
        boolean bl = true;
        if (!bl) return false;
        LongBinaryOperator longBinaryOperator = x$1 == null ? null : ((RichLongBinaryOperatorAsFunction2)x$1).scala$compat$java8$functionConverterImpls$RichLongBinaryOperatorAsFunction2$$underlying();
        LongBinaryOperator longBinaryOperator2 = $this;
        LongBinaryOperator longBinaryOperator3 = longBinaryOperator;
        if (longBinaryOperator2 != null) {
            if (!longBinaryOperator2.equals(longBinaryOperator3)) return false;
            return true;
        }
        if (longBinaryOperator3 == null) return true;
        return false;
    }

    private RichLongBinaryOperatorAsFunction2$() {
    }
}

