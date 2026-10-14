/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.FromJavaBinaryOperator;
import scala.compat.java8.functionConverterImpls.RichBinaryOperatorAsFunction2;

public final class RichBinaryOperatorAsFunction2$ {
    public static final RichBinaryOperatorAsFunction2$ MODULE$ = new RichBinaryOperatorAsFunction2$();

    public final <T> Function2<T, T, T> asScala$extension(BinaryOperator<T> $this) {
        return new FromJavaBinaryOperator<T>($this);
    }

    public final <T> int hashCode$extension(BinaryOperator<T> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(BinaryOperator<T> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichBinaryOperatorAsFunction2)) return false;
        boolean bl = true;
        if (!bl) return false;
        BinaryOperator binaryOperator = x$1 == null ? null : ((RichBinaryOperatorAsFunction2)x$1).scala$compat$java8$functionConverterImpls$RichBinaryOperatorAsFunction2$$underlying();
        BinaryOperator<T> binaryOperator2 = $this;
        BinaryOperator binaryOperator3 = binaryOperator;
        if (binaryOperator2 != null) {
            if (!binaryOperator2.equals(binaryOperator3)) return false;
            return true;
        }
        if (binaryOperator3 == null) return true;
        return false;
    }

    private RichBinaryOperatorAsFunction2$() {
    }
}

