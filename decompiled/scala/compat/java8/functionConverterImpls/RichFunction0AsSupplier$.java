/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Supplier;
import scala.Function0;
import scala.compat.java8.functionConverterImpls.AsJavaSupplier;
import scala.compat.java8.functionConverterImpls.RichFunction0AsSupplier;

public final class RichFunction0AsSupplier$ {
    public static final RichFunction0AsSupplier$ MODULE$ = new RichFunction0AsSupplier$();

    public final <T> Supplier<T> asJava$extension(Function0<T> $this) {
        return new AsJavaSupplier<T>($this);
    }

    public final <T> int hashCode$extension(Function0<T> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(Function0<T> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction0AsSupplier)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function0 function0 = x$1 == null ? null : ((RichFunction0AsSupplier)x$1).scala$compat$java8$functionConverterImpls$RichFunction0AsSupplier$$underlying();
        Function0<T> function02 = $this;
        Function0 function03 = function0;
        if (function02 != null) {
            if (!function02.equals(function03)) return false;
            return true;
        }
        if (function03 == null) return true;
        return false;
    }

    private RichFunction0AsSupplier$() {
    }
}

