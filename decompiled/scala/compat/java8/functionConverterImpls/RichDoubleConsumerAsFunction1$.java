/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleConsumer;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaDoubleConsumer;
import scala.compat.java8.functionConverterImpls.RichDoubleConsumerAsFunction1;
import scala.runtime.BoxedUnit;

public final class RichDoubleConsumerAsFunction1$ {
    public static final RichDoubleConsumerAsFunction1$ MODULE$ = new RichDoubleConsumerAsFunction1$();

    public final Function1<Object, BoxedUnit> asScala$extension(DoubleConsumer $this) {
        return new FromJavaDoubleConsumer($this);
    }

    public final int hashCode$extension(DoubleConsumer $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(DoubleConsumer $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichDoubleConsumerAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        DoubleConsumer doubleConsumer = x$1 == null ? null : ((RichDoubleConsumerAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichDoubleConsumerAsFunction1$$underlying();
        DoubleConsumer doubleConsumer2 = $this;
        DoubleConsumer doubleConsumer3 = doubleConsumer;
        if (doubleConsumer2 != null) {
            if (!doubleConsumer2.equals(doubleConsumer3)) return false;
            return true;
        }
        if (doubleConsumer3 == null) return true;
        return false;
    }

    private RichDoubleConsumerAsFunction1$() {
    }
}

