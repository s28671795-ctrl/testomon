/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntUnaryOperator;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaIntUnaryOperator;
import scala.compat.java8.functionConverterImpls.RichIntUnaryOperatorAsFunction1;

public final class RichIntUnaryOperatorAsFunction1$ {
    public static final RichIntUnaryOperatorAsFunction1$ MODULE$ = new RichIntUnaryOperatorAsFunction1$();

    public final Function1<Object, Object> asScala$extension(IntUnaryOperator $this) {
        return new FromJavaIntUnaryOperator($this);
    }

    public final int hashCode$extension(IntUnaryOperator $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(IntUnaryOperator $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichIntUnaryOperatorAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        IntUnaryOperator intUnaryOperator = x$1 == null ? null : ((RichIntUnaryOperatorAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichIntUnaryOperatorAsFunction1$$underlying();
        IntUnaryOperator intUnaryOperator2 = $this;
        IntUnaryOperator intUnaryOperator3 = intUnaryOperator;
        if (intUnaryOperator2 != null) {
            if (!intUnaryOperator2.equals(intUnaryOperator3)) return false;
            return true;
        }
        if (intUnaryOperator3 == null) return true;
        return false;
    }

    private RichIntUnaryOperatorAsFunction1$() {
    }
}

