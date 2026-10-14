/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiFunction;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.FromJavaBiFunction;
import scala.compat.java8.functionConverterImpls.RichBiFunctionAsFunction2;

public final class RichBiFunctionAsFunction2$ {
    public static final RichBiFunctionAsFunction2$ MODULE$ = new RichBiFunctionAsFunction2$();

    public final <T, U, R> Function2<T, U, R> asScala$extension(BiFunction<T, U, R> $this) {
        return new FromJavaBiFunction<T, U, R>($this);
    }

    public final <T, U, R> int hashCode$extension(BiFunction<T, U, R> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T, U, R> boolean equals$extension(BiFunction<T, U, R> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichBiFunctionAsFunction2)) return false;
        boolean bl = true;
        if (!bl) return false;
        BiFunction biFunction = x$1 == null ? null : ((RichBiFunctionAsFunction2)x$1).scala$compat$java8$functionConverterImpls$RichBiFunctionAsFunction2$$underlying();
        BiFunction<T, U, R> biFunction2 = $this;
        BiFunction biFunction3 = biFunction;
        if (biFunction2 != null) {
            if (!biFunction2.equals(biFunction3)) return false;
            return true;
        }
        if (biFunction3 == null) return true;
        return false;
    }

    private RichBiFunctionAsFunction2$() {
    }
}

