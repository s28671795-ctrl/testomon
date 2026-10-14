/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ObjIntConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.FromJavaObjIntConsumer;
import scala.compat.java8.functionConverterImpls.RichObjIntConsumerAsFunction2;
import scala.runtime.BoxedUnit;

public final class RichObjIntConsumerAsFunction2$ {
    public static final RichObjIntConsumerAsFunction2$ MODULE$ = new RichObjIntConsumerAsFunction2$();

    public final <T> Function2<T, Object, BoxedUnit> asScala$extension(ObjIntConsumer<T> $this) {
        return new FromJavaObjIntConsumer<T>($this);
    }

    public final <T> int hashCode$extension(ObjIntConsumer<T> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(ObjIntConsumer<T> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichObjIntConsumerAsFunction2)) return false;
        boolean bl = true;
        if (!bl) return false;
        ObjIntConsumer objIntConsumer = x$1 == null ? null : ((RichObjIntConsumerAsFunction2)x$1).scala$compat$java8$functionConverterImpls$RichObjIntConsumerAsFunction2$$underlying();
        ObjIntConsumer<T> objIntConsumer2 = $this;
        ObjIntConsumer objIntConsumer3 = objIntConsumer;
        if (objIntConsumer2 != null) {
            if (!objIntConsumer2.equals(objIntConsumer3)) return false;
            return true;
        }
        if (objIntConsumer3 == null) return true;
        return false;
    }

    private RichObjIntConsumerAsFunction2$() {
    }
}

