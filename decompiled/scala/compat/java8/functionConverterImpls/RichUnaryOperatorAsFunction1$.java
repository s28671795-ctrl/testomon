/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.UnaryOperator;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaUnaryOperator;
import scala.compat.java8.functionConverterImpls.RichUnaryOperatorAsFunction1;

public final class RichUnaryOperatorAsFunction1$ {
    public static final RichUnaryOperatorAsFunction1$ MODULE$ = new RichUnaryOperatorAsFunction1$();

    public final <T> Function1<T, T> asScala$extension(UnaryOperator<T> $this) {
        return new FromJavaUnaryOperator<T>($this);
    }

    public final <T> int hashCode$extension(UnaryOperator<T> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(UnaryOperator<T> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichUnaryOperatorAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        UnaryOperator unaryOperator = x$1 == null ? null : ((RichUnaryOperatorAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichUnaryOperatorAsFunction1$$underlying();
        UnaryOperator<T> unaryOperator2 = $this;
        UnaryOperator unaryOperator3 = unaryOperator;
        if (unaryOperator2 != null) {
            if (!unaryOperator2.equals(unaryOperator3)) return false;
            return true;
        }
        if (unaryOperator3 == null) return true;
        return false;
    }

    private RichUnaryOperatorAsFunction1$() {
    }
}

