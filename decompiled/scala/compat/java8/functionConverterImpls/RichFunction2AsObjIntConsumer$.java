/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ObjIntConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.AsJavaObjIntConsumer;
import scala.compat.java8.functionConverterImpls.RichFunction2AsObjIntConsumer;
import scala.runtime.BoxedUnit;

public final class RichFunction2AsObjIntConsumer$ {
    public static final RichFunction2AsObjIntConsumer$ MODULE$ = new RichFunction2AsObjIntConsumer$();

    public final <T> ObjIntConsumer<T> asJava$extension(Function2<T, Object, BoxedUnit> $this) {
        return new AsJavaObjIntConsumer<T>($this);
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
        if (!(object instanceof RichFunction2AsObjIntConsumer)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function2 function2 = x$1 == null ? null : ((RichFunction2AsObjIntConsumer)x$1).scala$compat$java8$functionConverterImpls$RichFunction2AsObjIntConsumer$$underlying();
        Function2<T, Object, BoxedUnit> function22 = $this;
        Function2 function23 = function2;
        if (function22 != null) {
            if (!function22.equals(function23)) return false;
            return true;
        }
        if (function23 == null) return true;
        return false;
    }

    private RichFunction2AsObjIntConsumer$() {
    }
}

