/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongUnaryOperator;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaLongUnaryOperator;
import scala.compat.java8.functionConverterImpls.RichLongUnaryOperatorAsFunction1;

public final class RichLongUnaryOperatorAsFunction1$ {
    public static final RichLongUnaryOperatorAsFunction1$ MODULE$ = new RichLongUnaryOperatorAsFunction1$();

    public final Function1<Object, Object> asScala$extension(LongUnaryOperator $this) {
        return new FromJavaLongUnaryOperator($this);
    }

    public final int hashCode$extension(LongUnaryOperator $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(LongUnaryOperator $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichLongUnaryOperatorAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        LongUnaryOperator longUnaryOperator = x$1 == null ? null : ((RichLongUnaryOperatorAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichLongUnaryOperatorAsFunction1$$underlying();
        LongUnaryOperator longUnaryOperator2 = $this;
        LongUnaryOperator longUnaryOperator3 = longUnaryOperator;
        if (longUnaryOperator2 != null) {
            if (!longUnaryOperator2.equals(longUnaryOperator3)) return false;
            return true;
        }
        if (longUnaryOperator3 == null) return true;
        return false;
    }

    private RichLongUnaryOperatorAsFunction1$() {
    }
}

