/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongSupplier;
import scala.Function0;
import scala.compat.java8.functionConverterImpls.FromJavaLongSupplier;
import scala.compat.java8.functionConverterImpls.RichLongSupplierAsFunction0;

public final class RichLongSupplierAsFunction0$ {
    public static final RichLongSupplierAsFunction0$ MODULE$ = new RichLongSupplierAsFunction0$();

    public final Function0<Object> asScala$extension(LongSupplier $this) {
        return new FromJavaLongSupplier($this);
    }

    public final int hashCode$extension(LongSupplier $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(LongSupplier $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichLongSupplierAsFunction0)) return false;
        boolean bl = true;
        if (!bl) return false;
        LongSupplier longSupplier = x$1 == null ? null : ((RichLongSupplierAsFunction0)x$1).scala$compat$java8$functionConverterImpls$RichLongSupplierAsFunction0$$underlying();
        LongSupplier longSupplier2 = $this;
        LongSupplier longSupplier3 = longSupplier;
        if (longSupplier2 != null) {
            if (!longSupplier2.equals(longSupplier3)) return false;
            return true;
        }
        if (longSupplier3 == null) return true;
        return false;
    }

    private RichLongSupplierAsFunction0$() {
    }
}

