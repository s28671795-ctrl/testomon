/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.collection.IterableOnce;
import scala.compat.java8.collectionImpl.package$;
import scala.compat.java8.converterImpl.AccumulateLongCollection;
import scala.jdk.LongAccumulator;

public final class AccumulateLongCollection$ {
    public static final AccumulateLongCollection$ MODULE$ = new AccumulateLongCollection$();

    public final LongAccumulator accumulate$extension(IterableOnce $this) {
        return $this.iterator().to(package$.MODULE$.LongAccumulator());
    }

    public final int hashCode$extension(IterableOnce $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(IterableOnce $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof AccumulateLongCollection)) return false;
        boolean bl = true;
        if (!bl) return false;
        IterableOnce<Object> iterableOnce = x$1 == null ? null : ((AccumulateLongCollection)x$1).scala$compat$java8$converterImpl$AccumulateLongCollection$$underlying();
        IterableOnce iterableOnce2 = $this;
        IterableOnce<Object> iterableOnce3 = iterableOnce;
        if (iterableOnce2 != null) {
            if (!iterableOnce2.equals(iterableOnce3)) return false;
            return true;
        }
        if (iterableOnce3 == null) return true;
        return false;
    }

    private AccumulateLongCollection$() {
    }
}

