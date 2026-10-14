/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToDoubleFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaToDoubleFunction;
import scala.compat.java8.functionConverterImpls.RichToDoubleFunctionAsFunction1;

public final class RichToDoubleFunctionAsFunction1$ {
    public static final RichToDoubleFunctionAsFunction1$ MODULE$ = new RichToDoubleFunctionAsFunction1$();

    public final <T> Function1<T, Object> asScala$extension(ToDoubleFunction<T> $this) {
        return new FromJavaToDoubleFunction<T>($this);
    }

    public final <T> int hashCode$extension(ToDoubleFunction<T> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(ToDoubleFunction<T> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichToDoubleFunctionAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        ToDoubleFunction toDoubleFunction = x$1 == null ? null : ((RichToDoubleFunctionAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichToDoubleFunctionAsFunction1$$underlying();
        ToDoubleFunction<T> toDoubleFunction2 = $this;
        ToDoubleFunction toDoubleFunction3 = toDoubleFunction;
        if (toDoubleFunction2 != null) {
            if (!toDoubleFunction2.equals(toDoubleFunction3)) return false;
            return true;
        }
        if (toDoubleFunction3 == null) return true;
        return false;
    }

    private RichToDoubleFunctionAsFunction1$() {
    }
}

