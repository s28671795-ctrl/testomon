/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ObjDoubleConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.AsJavaObjDoubleConsumer;
import scala.compat.java8.functionConverterImpls.RichFunction2AsObjDoubleConsumer;
import scala.runtime.BoxedUnit;

public final class RichFunction2AsObjDoubleConsumer$ {
    public static final RichFunction2AsObjDoubleConsumer$ MODULE$ = new RichFunction2AsObjDoubleConsumer$();

    public final <T> ObjDoubleConsumer<T> asJava$extension(Function2<T, Object, BoxedUnit> $this) {
        return new AsJavaObjDoubleConsumer<T>($this);
    }

    public final <T> int hashCode$extension(Function2<T, Object, BoxedUnit> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(Function2<T, Object, BoxedUnit> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction2AsObjDoubleConsumer)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function2 function2 = x$1 == null ? null : ((RichFunction2AsObjDoubleConsumer)x$1).scala$compat$java8$functionConverterImpls$RichFunction2AsObjDoubleConsumer$$underlying();
        Function2<T, Object, BoxedUnit> function22 = $this;
        Function2 function23 = function2;
        if (function22 != null) {
            if (!function22.equals(function23)) return false;
            return true;
        }
        if (function23 == null) return true;
        return false;
    }

    private RichFunction2AsObjDoubleConsumer$() {
    }
}

