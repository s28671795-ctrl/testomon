/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiPredicate;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.FromJavaBiPredicate;
import scala.compat.java8.functionConverterImpls.RichBiPredicateAsFunction2;

public final class RichBiPredicateAsFunction2$ {
    public static final RichBiPredicateAsFunction2$ MODULE$ = new RichBiPredicateAsFunction2$();

    public final <T, U> Function2<T, U, Object> asScala$extension(BiPredicate<T, U> $this) {
        return new FromJavaBiPredicate<T, U>($this);
    }

    public final <T, U> int hashCode$extension(BiPredicate<T, U> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T, U> boolean equals$extension(BiPredicate<T, U> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichBiPredicateAsFunction2)) return false;
        boolean bl = true;
        if (!bl) return false;
        BiPredicate biPredicate = x$1 == null ? null : ((RichBiPredicateAsFunction2)x$1).scala$compat$java8$functionConverterImpls$RichBiPredicateAsFunction2$$underlying();
        BiPredicate<T, U> biPredicate2 = $this;
        BiPredicate biPredicate3 = biPredicate;
        if (biPredicate2 != null) {
            if (!biPredicate2.equals(biPredicate3)) return false;
            return true;
        }
        if (biPredicate3 == null) return true;
        return false;
    }

    private RichBiPredicateAsFunction2$() {
    }
}

