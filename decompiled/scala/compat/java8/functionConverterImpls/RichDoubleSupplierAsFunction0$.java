/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleSupplier;
import scala.Function0;
import scala.compat.java8.functionConverterImpls.FromJavaDoubleSupplier;
import scala.compat.java8.functionConverterImpls.RichDoubleSupplierAsFunction0;

public final class RichDoubleSupplierAsFunction0$ {
    public static final RichDoubleSupplierAsFunction0$ MODULE$ = new RichDoubleSupplierAsFunction0$();

    public final Function0<Object> asScala$extension(DoubleSupplier $this) {
        return new FromJavaDoubleSupplier($this);
    }

    public final int hashCode$extension(DoubleSupplier $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(DoubleSupplier $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichDoubleSupplierAsFunction0)) return false;
        boolean bl = true;
        if (!bl) return false;
        DoubleSupplier doubleSupplier = x$1 == null ? null : ((RichDoubleSupplierAsFunction0)x$1).scala$compat$java8$functionConverterImpls$RichDoubleSupplierAsFunction0$$underlying();
        DoubleSupplier doubleSupplier2 = $this;
        DoubleSupplier doubleSupplier3 = doubleSupplier;
        if (doubleSupplier2 != null) {
            if (!doubleSupplier2.equals(doubleSupplier3)) return false;
            return true;
        }
        if (doubleSupplier3 == null) return true;
        return false;
    }

    private RichDoubleSupplierAsFunction0$() {
    }
}

