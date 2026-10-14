/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongSupplier;
import scala.Function0;
import scala.compat.java8.functionConverterImpls.AsJavaLongSupplier;
import scala.compat.java8.functionConverterImpls.RichFunction0AsLongSupplier;

public final class RichFunction0AsLongSupplier$ {
    public static final RichFunction0AsLongSupplier$ MODULE$ = new RichFunction0AsLongSupplier$();

    public final LongSupplier asJava$extension(Function0 $this) {
        return new AsJavaLongSupplier($this);
    }

    public final int hashCode$extension(Function0 $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(Function0 $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction0AsLongSupplier)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function0<Object> function0 = x$1 == null ? null : ((RichFunction0AsLongSupplier)x$1).scala$compat$java8$functionConverterImpls$RichFunction0AsLongSupplier$$underlying();
        Function0 function02 = $this;
        Function0<Object> function03 = function0;
        if (function02 != null) {
            if (!function02.equals(function03)) return false;
            return true;
        }
        if (function03 == null) return true;
        return false;
    }

    private RichFunction0AsLongSupplier$() {
    }
}

