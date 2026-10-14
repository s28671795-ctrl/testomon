/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToLongBiFunction;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.FromJavaToLongBiFunction;
import scala.compat.java8.functionConverterImpls.RichToLongBiFunctionAsFunction2;

public final class RichToLongBiFunctionAsFunction2$ {
    public static final RichToLongBiFunctionAsFunction2$ MODULE$ = new RichToLongBiFunctionAsFunction2$();

    public final <T, U> Function2<T, U, Object> asScala$extension(ToLongBiFunction<T, U> $this) {
        return new FromJavaToLongBiFunction<T, U>($this);
    }

    public final <T, U> int hashCode$extension(ToLongBiFunction<T, U> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T, U> boolean equals$extension(ToLongBiFunction<T, U> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichToLongBiFunctionAsFunction2)) return false;
        boolean bl = true;
        if (!bl) return false;
        ToLongBiFunction toLongBiFunction = x$1 == null ? null : ((RichToLongBiFunctionAsFunction2)x$1).scala$compat$java8$functionConverterImpls$RichToLongBiFunctionAsFunction2$$underlying();
        ToLongBiFunction<T, U> toLongBiFunction2 = $this;
        ToLongBiFunction toLongBiFunction3 = toLongBiFunction;
        if (toLongBiFunction2 != null) {
            if (!toLongBiFunction2.equals(toLongBiFunction3)) return false;
            return true;
        }
        if (toLongBiFunction3 == null) return true;
        return false;
    }

    private RichToLongBiFunctionAsFunction2$() {
    }
}

