/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Consumer;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.AsJavaConsumer;
import scala.compat.java8.functionConverterImpls.RichFunction1AsConsumer;
import scala.runtime.BoxedUnit;

public final class RichFunction1AsConsumer$ {
    public static final RichFunction1AsConsumer$ MODULE$ = new RichFunction1AsConsumer$();

    public final <T> Consumer<T> asJava$extension(Function1<T, BoxedUnit> $this) {
        return new AsJavaConsumer<T>($this);
    }

    public final <T> int hashCode$extension(Function1<T, BoxedUnit> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(Function1<T, BoxedUnit> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction1AsConsumer)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function1 function1 = x$1 == null ? null : ((RichFunction1AsConsumer)x$1).scala$compat$java8$functionConverterImpls$RichFunction1AsConsumer$$underlying();
        Function1<T, BoxedUnit> function12 = $this;
        Function1 function13 = function1;
        if (function12 != null) {
            if (!function12.equals(function13)) return false;
            return true;
        }
        if (function13 == null) return true;
        return false;
    }

    private RichFunction1AsConsumer$() {
    }
}

