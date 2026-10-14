/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.Predef$;
import scala.compat.java8.collectionImpl.package$;
import scala.compat.java8.converterImpl.AccumulateLongArray;
import scala.jdk.LongAccumulator;

public final class AccumulateLongArray$ {
    public static final AccumulateLongArray$ MODULE$ = new AccumulateLongArray$();

    public final LongAccumulator accumulate$extension(long[] $this) {
        return Predef$.MODULE$.wrapLongArray($this).to(package$.MODULE$.LongAccumulator());
    }

    public final int hashCode$extension(long[] $this) {
        return $this.hashCode();
    }

    public final boolean equals$extension(long[] $this, Object x$1) {
        long[] lArray;
        Object object = x$1;
        boolean bl = object instanceof AccumulateLongArray;
        return bl && $this == (lArray = x$1 == null ? null : ((AccumulateLongArray)x$1).scala$compat$java8$converterImpl$AccumulateLongArray$$underlying());
    }

    private AccumulateLongArray$() {
    }
}

