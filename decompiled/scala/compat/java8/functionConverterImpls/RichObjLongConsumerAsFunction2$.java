/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ObjLongConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.FromJavaObjLongConsumer;
import scala.compat.java8.functionConverterImpls.RichObjLongConsumerAsFunction2;
import scala.runtime.BoxedUnit;

public final class RichObjLongConsumerAsFunction2$ {
    public static final RichObjLongConsumerAsFunction2$ MODULE$ = new RichObjLongConsumerAsFunction2$();

    public final <T> Function2<T, Object, BoxedUnit> asScala$extension(ObjLongConsumer<T> $this) {
        return new FromJavaObjLongConsumer<T>($this);
    }

    public final <T> int hashCode$extension(ObjLongConsumer<T> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(ObjLongConsumer<T> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichObjLongConsumerAsFunction2)) return false;
        boolean bl = true;
        if (!bl) return false;
        ObjLongConsumer objLongConsumer = x$1 == null ? null : ((RichObjLongConsumerAsFunction2)x$1).scala$compat$java8$functionConverterImpls$RichObjLongConsumerAsFunction2$$underlying();
        ObjLongConsumer<T> objLongConsumer2 = $this;
        ObjLongConsumer objLongConsumer3 = objLongConsumer;
        if (objLongConsumer2 != null) {
            if (!objLongConsumer2.equals(objLongConsumer3)) return false;
            return true;
        }
        if (objLongConsumer3 == null) return true;
        return false;
    }

    private RichObjLongConsumerAsFunction2$() {
    }
}

