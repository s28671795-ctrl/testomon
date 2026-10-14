/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntSupplier;
import scala.Function0;
import scala.compat.java8.functionConverterImpls.FromJavaIntSupplier;
import scala.compat.java8.functionConverterImpls.RichIntSupplierAsFunction0;

public final class RichIntSupplierAsFunction0$ {
    public static final RichIntSupplierAsFunction0$ MODULE$ = new RichIntSupplierAsFunction0$();

    public final Function0<Object> asScala$extension(IntSupplier $this) {
        return new FromJavaIntSupplier($this);
    }

    public final int hashCode$extension(IntSupplier $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(IntSupplier $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichIntSupplierAsFunction0)) return false;
        boolean bl = true;
        if (!bl) return false;
        IntSupplier intSupplier = x$1 == null ? null : ((RichIntSupplierAsFunction0)x$1).scala$compat$java8$functionConverterImpls$RichIntSupplierAsFunction0$$underlying();
        IntSupplier intSupplier2 = $this;
        IntSupplier intSupplier3 = intSupplier;
        if (intSupplier2 != null) {
            if (!intSupplier2.equals(intSupplier3)) return false;
            return true;
        }
        if (intSupplier3 == null) return true;
        return false;
    }

    private RichIntSupplierAsFunction0$() {
    }
}

