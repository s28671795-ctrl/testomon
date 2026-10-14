/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleUnaryOperator;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaDoubleUnaryOperator;
import scala.compat.java8.functionConverterImpls.RichDoubleUnaryOperatorAsFunction1;

public final class RichDoubleUnaryOperatorAsFunction1$ {
    public static final RichDoubleUnaryOperatorAsFunction1$ MODULE$ = new RichDoubleUnaryOperatorAsFunction1$();

    public final Function1<Object, Object> asScala$extension(DoubleUnaryOperator $this) {
        return new FromJavaDoubleUnaryOperator($this);
    }

    public final int hashCode$extension(DoubleUnaryOperator $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(DoubleUnaryOperator $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichDoubleUnaryOperatorAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        DoubleUnaryOperator doubleUnaryOperator = x$1 == null ? null : ((RichDoubleUnaryOperatorAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichDoubleUnaryOperatorAsFunction1$$underlying();
        DoubleUnaryOperator doubleUnaryOperator2 = $this;
        DoubleUnaryOperator doubleUnaryOperator3 = doubleUnaryOperator;
        if (doubleUnaryOperator2 != null) {
            if (!doubleUnaryOperator2.equals(doubleUnaryOperator3)) return false;
            return true;
        }
        if (doubleUnaryOperator3 == null) return true;
        return false;
    }

    private RichDoubleUnaryOperatorAsFunction1$() {
    }
}

