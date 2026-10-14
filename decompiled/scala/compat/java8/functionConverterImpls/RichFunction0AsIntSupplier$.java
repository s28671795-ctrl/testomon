/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntSupplier;
import scala.Function0;
import scala.compat.java8.functionConverterImpls.AsJavaIntSupplier;
import scala.compat.java8.functionConverterImpls.RichFunction0AsIntSupplier;

public final class RichFunction0AsIntSupplier$ {
    public static final RichFunction0AsIntSupplier$ MODULE$ = new RichFunction0AsIntSupplier$();

    public final IntSupplier asJava$extension(Function0 $this) {
        return new AsJavaIntSupplier($this);
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
        if (!(object instanceof RichFunction0AsIntSupplier)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function0<Object> function0 = x$1 == null ? null : ((RichFunction0AsIntSupplier)x$1).scala$compat$java8$functionConverterImpls$RichFunction0AsIntSupplier$$underlying();
        Function0 function02 = $this;
        Function0<Object> function03 = function0;
        if (function02 != null) {
            if (!function02.equals(function03)) return false;
            return true;
        }
        if (function03 == null) return true;
        return false;
    }

    private RichFunction0AsIntSupplier$() {
    }
}

