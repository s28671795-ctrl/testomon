/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToIntBiFunction;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.AsJavaToIntBiFunction;
import scala.compat.java8.functionConverterImpls.RichFunction2AsToIntBiFunction;

public final class RichFunction2AsToIntBiFunction$ {
    public static final RichFunction2AsToIntBiFunction$ MODULE$ = new RichFunction2AsToIntBiFunction$();

    public final <T, U> ToIntBiFunction<T, U> asJava$extension(Function2<T, U, Object> $this) {
        return new AsJavaToIntBiFunction<T, U>($this);
    }

    public final <T, U> int hashCode$extension(Function2<T, U, Object> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T, U> boolean equals$extension(Function2<T, U, Object> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction2AsToIntBiFunction)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function2 function2 = x$1 == null ? null : ((RichFunction2AsToIntBiFunction)x$1).scala$compat$java8$functionConverterImpls$RichFunction2AsToIntBiFunction$$underlying();
        Function2<T, U, Object> function22 = $this;
        Function2 function23 = function2;
        if (function22 != null) {
            if (!function22.equals(function23)) return false;
            return true;
        }
        if (function23 == null) return true;
        return false;
    }

    private RichFunction2AsToIntBiFunction$() {
    }
}

