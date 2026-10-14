/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToIntFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaToIntFunction;
import scala.compat.java8.functionConverterImpls.RichToIntFunctionAsFunction1;

public final class RichToIntFunctionAsFunction1$ {
    public static final RichToIntFunctionAsFunction1$ MODULE$ = new RichToIntFunctionAsFunction1$();

    public final <T> Function1<T, Object> asScala$extension(ToIntFunction<T> $this) {
        return new FromJavaToIntFunction<T>($this);
    }

    public final <T> int hashCode$extension(ToIntFunction<T> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(ToIntFunction<T> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichToIntFunctionAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        ToIntFunction toIntFunction = x$1 == null ? null : ((RichToIntFunctionAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichToIntFunctionAsFunction1$$underlying();
        ToIntFunction<T> toIntFunction2 = $this;
        ToIntFunction toIntFunction3 = toIntFunction;
        if (toIntFunction2 != null) {
            if (!toIntFunction2.equals(toIntFunction3)) return false;
            return true;
        }
        if (toIntFunction3 == null) return true;
        return false;
    }

    private RichToIntFunctionAsFunction1$() {
    }
}

