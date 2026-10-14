/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntConsumer;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaIntConsumer;
import scala.compat.java8.functionConverterImpls.RichIntConsumerAsFunction1;
import scala.runtime.BoxedUnit;

public final class RichIntConsumerAsFunction1$ {
    public static final RichIntConsumerAsFunction1$ MODULE$ = new RichIntConsumerAsFunction1$();

    public final Function1<Object, BoxedUnit> asScala$extension(IntConsumer $this) {
        return new FromJavaIntConsumer($this);
    }

    public final int hashCode$extension(IntConsumer $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(IntConsumer $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichIntConsumerAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        IntConsumer intConsumer = x$1 == null ? null : ((RichIntConsumerAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichIntConsumerAsFunction1$$underlying();
        IntConsumer intConsumer2 = $this;
        IntConsumer intConsumer3 = intConsumer;
        if (intConsumer2 != null) {
            if (!intConsumer2.equals(intConsumer3)) return false;
            return true;
        }
        if (intConsumer3 == null) return true;
        return false;
    }

    private RichIntConsumerAsFunction1$() {
    }
}

