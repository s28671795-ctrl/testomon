/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntBinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.FromJavaIntBinaryOperator;
import scala.compat.java8.functionConverterImpls.RichIntBinaryOperatorAsFunction2;

public final class RichIntBinaryOperatorAsFunction2$ {
    public static final RichIntBinaryOperatorAsFunction2$ MODULE$ = new RichIntBinaryOperatorAsFunction2$();

    public final Function2<Object, Object, Object> asScala$extension(IntBinaryOperator $this) {
        return new FromJavaIntBinaryOperator($this);
    }

    public final int hashCode$extension(IntBinaryOperator $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(IntBinaryOperator $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichIntBinaryOperatorAsFunction2)) return false;
        boolean bl = true;
        if (!bl) return false;
        IntBinaryOperator intBinaryOperator = x$1 == null ? null : ((RichIntBinaryOperatorAsFunction2)x$1).scala$compat$java8$functionConverterImpls$RichIntBinaryOperatorAsFunction2$$underlying();
        IntBinaryOperator intBinaryOperator2 = $this;
        IntBinaryOperator intBinaryOperator3 = intBinaryOperator;
        if (intBinaryOperator2 != null) {
            if (!intBinaryOperator2.equals(intBinaryOperator3)) return false;
            return true;
        }
        if (intBinaryOperator3 == null) return true;
        return false;
    }

    private RichIntBinaryOperatorAsFunction2$() {
    }
}

