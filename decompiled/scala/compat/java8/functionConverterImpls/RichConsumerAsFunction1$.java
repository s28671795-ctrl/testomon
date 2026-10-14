/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Consumer;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.FromJavaConsumer;
import scala.compat.java8.functionConverterImpls.RichConsumerAsFunction1;
import scala.runtime.BoxedUnit;

public final class RichConsumerAsFunction1$ {
    public static final RichConsumerAsFunction1$ MODULE$ = new RichConsumerAsFunction1$();

    public final <T> Function1<T, BoxedUnit> asScala$extension(Consumer<T> $this) {
        return new FromJavaConsumer<T>($this);
    }

    public final <T> int hashCode$extension(Consumer<T> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T> boolean equals$extension(Consumer<T> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichConsumerAsFunction1)) return false;
        boolean bl = true;
        if (!bl) return false;
        Consumer consumer = x$1 == null ? null : ((RichConsumerAsFunction1)x$1).scala$compat$java8$functionConverterImpls$RichConsumerAsFunction1$$underlying();
        Consumer<T> consumer2 = $this;
        Consumer consumer3 = consumer;
        if (consumer2 != null) {
            if (!consumer2.equals(consumer3)) return false;
            return true;
        }
        if (consumer3 == null) return true;
        return false;
    }

    private RichConsumerAsFunction1$() {
    }
}

