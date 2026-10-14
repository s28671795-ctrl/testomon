/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BooleanSupplier;
import scala.Function0;
import scala.compat.java8.functionConverterImpls.FromJavaBooleanSupplier;
import scala.compat.java8.functionConverterImpls.RichBooleanSupplierAsFunction0;

public final class RichBooleanSupplierAsFunction0$ {
    public static final RichBooleanSupplierAsFunction0$ MODULE$ = new RichBooleanSupplierAsFunction0$();

    public final Function0<Object> asScala$extension(BooleanSupplier $this) {
        return new FromJavaBooleanSupplier($this);
    }

    public final int hashCode$extension(BooleanSupplier $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(BooleanSupplier $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichBooleanSupplierAsFunction0)) return false;
        boolean bl = true;
        if (!bl) return false;
        BooleanSupplier booleanSupplier = x$1 == null ? null : ((RichBooleanSupplierAsFunction0)x$1).scala$compat$java8$functionConverterImpls$RichBooleanSupplierAsFunction0$$underlying();
        BooleanSupplier booleanSupplier2 = $this;
        BooleanSupplier booleanSupplier3 = booleanSupplier;
        if (booleanSupplier2 != null) {
            if (!booleanSupplier2.equals(booleanSupplier3)) return false;
            return true;
        }
        if (booleanSupplier3 == null) return true;
        return false;
    }

    private RichBooleanSupplierAsFunction0$() {
    }
}

