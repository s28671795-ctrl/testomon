/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToDoubleBiFunction;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.AsJavaToDoubleBiFunction;
import scala.compat.java8.functionConverterImpls.RichFunction2AsToDoubleBiFunction;

public final class RichFunction2AsToDoubleBiFunction$ {
    public static final RichFunction2AsToDoubleBiFunction$ MODULE$ = new RichFunction2AsToDoubleBiFunction$();

    public final <T, U> ToDoubleBiFunction<T, U> asJava$extension(Function2<T, U, Object> $this) {
        return new AsJavaToDoubleBiFunction<T, U>($this);
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
        if (!(object instanceof RichFunction2AsToDoubleBiFunction)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function2 function2 = x$1 == null ? null : ((RichFunction2AsToDoubleBiFunction)x$1).scala$compat$java8$functionConverterImpls$RichFunction2AsToDoubleBiFunction$$underlying();
        Function2<T, U, Object> function22 = $this;
        Function2 function23 = function2;
        if (function22 != null) {
            if (!function22.equals(function23)) return false;
            return true;
        }
        if (function23 == null) return true;
        return false;
    }

    private RichFunction2AsToDoubleBiFunction$() {
    }
}

