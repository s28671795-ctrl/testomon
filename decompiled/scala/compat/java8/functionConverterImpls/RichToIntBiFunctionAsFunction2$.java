/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToIntBiFunction;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.FromJavaToIntBiFunction;
import scala.compat.java8.functionConverterImpls.RichToIntBiFunctionAsFunction2;

public final class RichToIntBiFunctionAsFunction2$ {
    public static final RichToIntBiFunctionAsFunction2$ MODULE$ = new RichToIntBiFunctionAsFunction2$();

    public final <T, U> Function2<T, U, Object> asScala$extension(ToIntBiFunction<T, U> $this) {
        return new FromJavaToIntBiFunction<T, U>($this);
    }

    public final <T, U> int hashCode$extension(ToIntBiFunction<T, U> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T, U> boolean equals$extension(ToIntBiFunction<T, U> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichToIntBiFunctionAsFunction2)) return false;
        boolean bl = true;
        if (!bl) return false;
        ToIntBiFunction toIntBiFunction = x$1 == null ? null : ((RichToIntBiFunctionAsFunction2)x$1).scala$compat$java8$functionConverterImpls$RichToIntBiFunctionAsFunction2$$underlying();
        ToIntBiFunction<T, U> toIntBiFunction2 = $this;
        ToIntBiFunction toIntBiFunction3 = toIntBiFunction;
        if (toIntBiFunction2 != null) {
            if (!toIntBiFunction2.equals(toIntBiFunction3)) return false;
            return true;
        }
        if (toIntBiFunction3 == null) return true;
        return false;
    }

    private RichToIntBiFunctionAsFunction2$() {
    }
}

