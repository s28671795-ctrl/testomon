/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Supplier;
import scala.Function0;
import scala.compat.java8.functionConverterImpls.FromJavaSupplier;
import scala.compat.java8.functionConverterImpls.RichSupplierAsFunction0;

public final class RichSupplierAsFunction0$ {
    public static final RichSupplierAsFunction0$ MODULE$ = new RichSupplierAsFunction0$();

    public final <T> Function0<T> asScala$extension(Supplier<T> $this) {
        return new FromJavaSupplier<T>($this);
    }

    public final <T> int hashCode$extension(Supplier<T> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(Supplier<T> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichSupplierAsFunction0)) return false;
        boolean bl = true;
        if (!bl) return false;
        Supplier supplier = x$1 == null ? null : ((RichSupplierAsFunction0)x$1).scala$compat$java8$functionConverterImpls$RichSupplierAsFunction0$$underlying();
        Supplier<T> supplier2 = $this;
        Supplier supplier3 = supplier;
        if (supplier2 != null) {
            if (!supplier2.equals(supplier3)) return false;
            return true;
        }
        if (supplier3 == null) return true;
        return false;
    }

    private RichSupplierAsFunction0$() {
    }
}

