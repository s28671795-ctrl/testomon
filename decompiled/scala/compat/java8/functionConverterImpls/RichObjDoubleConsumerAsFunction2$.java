/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ObjDoubleConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.FromJavaObjDoubleConsumer;
import scala.compat.java8.functionConverterImpls.RichObjDoubleConsumerAsFunction2;
import scala.runtime.BoxedUnit;

public final class RichObjDoubleConsumerAsFunction2$ {
    public static final RichObjDoubleConsumerAsFunction2$ MODULE$ = new RichObjDoubleConsumerAsFunction2$();

    public final <T> Function2<T, Object, BoxedUnit> asScala$extension(ObjDoubleConsumer<T> $this) {
        return new FromJavaObjDoubleConsumer<T>($this);
    }

    public final <T> int hashCode$extension(ObjDoubleConsumer<T> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(ObjDoubleConsumer<T> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichObjDoubleConsumerAsFunction2)) return false;
        boolean bl = true;
        if (!bl) return false;
        ObjDoubleConsumer objDoubleConsumer = x$1 == null ? null : ((RichObjDoubleConsumerAsFunction2)x$1).scala$compat$java8$functionConverterImpls$RichObjDoubleConsumerAsFunction2$$underlying();
        ObjDoubleConsumer<T> objDoubleConsumer2 = $this;
        ObjDoubleConsumer objDoubleConsumer3 = objDoubleConsumer;
        if (objDoubleConsumer2 != null) {
            if (!objDoubleConsumer2.equals(objDoubleConsumer3)) return false;
            return true;
        }
        if (objDoubleConsumer3 == null) return true;
        return false;
    }

    private RichObjDoubleConsumerAsFunction2$() {
    }
}

