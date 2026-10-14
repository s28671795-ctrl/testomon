/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ObjLongConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.AsJavaObjLongConsumer;
import scala.compat.java8.functionConverterImpls.RichFunction2AsObjLongConsumer;
import scala.runtime.BoxedUnit;

public final class RichFunction2AsObjLongConsumer$ {
    public static final RichFunction2AsObjLongConsumer$ MODULE$ = new RichFunction2AsObjLongConsumer$();

    public final <T> ObjLongConsumer<T> asJava$extension(Function2<T, Object, BoxedUnit> $this) {
        return new AsJavaObjLongConsumer<T>($this);
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
        if (!(object instanceof RichFunction2AsObjLongConsumer)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function2 function2 = x$1 == null ? null : ((RichFunction2AsObjLongConsumer)x$1).scala$compat$java8$functionConverterImpls$RichFunction2AsObjLongConsumer$$underlying();
        Function2<T, Object, BoxedUnit> function22 = $this;
        Function2 function23 = function2;
        if (function22 != null) {
            if (!function22.equals(function23)) return false;
            return true;
        }
        if (function23 == null) return true;
        return false;
    }

    private RichFunction2AsObjLongConsumer$() {
    }
}

