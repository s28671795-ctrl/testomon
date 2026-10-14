/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongConsumer;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaLongConsumer;
import scala.compat.java8.functionConverterImpls.RichLongConsumerAsFunction1;
import scala.runtime.BoxedUnit;

public final class RichLongConsumerAsFunction1$ {
    public static final RichLongConsumerAsFunction1$ MODULE$ = new RichLongConsumerAsFunction1$();

    public final Function1<Object, BoxedUnit> asScala$extension(LongConsumer $this) {
        return new FromJavaLongConsumer($this);
    }

    public final int hashCode$extension(LongConsumer $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(LongConsumer $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichLongConsumerAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        LongConsumer longConsumer = x$1 == null ? null : ((RichLongConsumerAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichLongConsumerAsFunction1$$underlying();
        LongConsumer longConsumer2 = $this;
        LongConsumer longConsumer3 = longConsumer;
        if (longConsumer2 != null) {
            if (!longConsumer2.equals(longConsumer3)) return false;
            return true;
        }
        if (longConsumer3 == null) return true;
        return false;
    }

    private RichLongConsumerAsFunction1$() {
    }
}

