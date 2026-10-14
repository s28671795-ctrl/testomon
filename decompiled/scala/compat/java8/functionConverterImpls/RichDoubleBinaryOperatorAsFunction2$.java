/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleBinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.FromJavaDoubleBinaryOperator;
import scala.compat.java8.functionConverterImpls.RichDoubleBinaryOperatorAsFunction2;

public final class RichDoubleBinaryOperatorAsFunction2$ {
    public static final RichDoubleBinaryOperatorAsFunction2$ MODULE$ = new RichDoubleBinaryOperatorAsFunction2$();

    public final Function2<Object, Object, Object> asScala$extension(DoubleBinaryOperator $this) {
        return new FromJavaDoubleBinaryOperator($this);
    }

    public final int hashCode$extension(DoubleBinaryOperator $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(DoubleBinaryOperator $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichDoubleBinaryOperatorAsFunction2)) return false;
        boolean bl = true;
        if (!bl) return false;
        DoubleBinaryOperator doubleBinaryOperator = x$1 == null ? null : ((RichDoubleBinaryOperatorAsFunction2)x$1).scala$compat$java8$functionConverterImpls$RichDoubleBinaryOperatorAsFunction2$$underlying();
        DoubleBinaryOperator doubleBinaryOperator2 = $this;
        DoubleBinaryOperator doubleBinaryOperator3 = doubleBinaryOperator;
        if (doubleBinaryOperator2 != null) {
            if (!doubleBinaryOperator2.equals(doubleBinaryOperator3)) return false;
            return true;
        }
        if (doubleBinaryOperator3 == null) return true;
        return false;
    }

    private RichDoubleBinaryOperatorAsFunction2$() {
    }
}

