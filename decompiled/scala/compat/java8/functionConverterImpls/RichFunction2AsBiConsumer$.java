/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.AsJavaBiConsumer;
import scala.compat.java8.functionConverterImpls.RichFunction2AsBiConsumer;
import scala.runtime.BoxedUnit;

public final class RichFunction2AsBiConsumer$ {
    public static final RichFunction2AsBiConsumer$ MODULE$ = new RichFunction2AsBiConsumer$();

    public final <T, U> BiConsumer<T, U> asJava$extension(Function2<T, U, BoxedUnit> $this) {
        return new AsJavaBiConsumer<T, U>($this);
    }

    public final <T, U> int hashCode$extension(Function2<T, U, BoxedUnit> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T, U> boolean equals$extension(Function2<T, U, BoxedUnit> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction2AsBiConsumer)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function2 function2 = x$1 == null ? null : ((RichFunction2AsBiConsumer)x$1).scala$compat$java8$functionConverterImpls$RichFunction2AsBiConsumer$$underlying();
        Function2<T, U, BoxedUnit> function22 = $this;
        Function2 function23 = function2;
        if (function22 != null) {
            if (!function22.equals(function23)) return false;
            return true;
        }
        if (function23 == null) return true;
        return false;
    }

    private RichFunction2AsBiConsumer$() {
    }
}

