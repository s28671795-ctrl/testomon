/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.FromJavaBiConsumer;
import scala.compat.java8.functionConverterImpls.RichBiConsumerAsFunction2;
import scala.runtime.BoxedUnit;

public final class RichBiConsumerAsFunction2$ {
    public static final RichBiConsumerAsFunction2$ MODULE$ = new RichBiConsumerAsFunction2$();

    public final <T, U> Function2<T, U, BoxedUnit> asScala$extension(BiConsumer<T, U> $this) {
        return new FromJavaBiConsumer<T, U>($this);
    }

    public final <T, U> int hashCode$extension(BiConsumer<T, U> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T, U> boolean equals$extension(BiConsumer<T, U> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichBiConsumerAsFunction2)) return false;
        boolean bl = true;
        if (!bl) return false;
        BiConsumer biConsumer = x$1 == null ? null : ((RichBiConsumerAsFunction2)x$1).scala$compat$java8$functionConverterImpls$RichBiConsumerAsFunction2$$underlying();
        BiConsumer<T, U> biConsumer2 = $this;
        BiConsumer biConsumer3 = biConsumer;
        if (biConsumer2 != null) {
            if (!biConsumer2.equals(biConsumer3)) return false;
            return true;
        }
        if (biConsumer3 == null) return true;
        return false;
    }

    private RichBiConsumerAsFunction2$() {
    }
}

