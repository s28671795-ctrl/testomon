/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.Predef$;
import scala.compat.java8.collectionImpl.package$;
import scala.compat.java8.converterImpl.AccumulateIntArray;
import scala.jdk.IntAccumulator;

public final class AccumulateIntArray$ {
    public static final AccumulateIntArray$ MODULE$ = new AccumulateIntArray$();

    public final IntAccumulator accumulate$extension(int[] $this) {
        return Predef$.MODULE$.wrapIntArray($this).to(package$.MODULE$.IntAccumulator());
    }

    public final int hashCode$extension(int[] $this) {
        return $this.hashCode();
    }

    public final boolean equals$extension(int[] $this, Object x$1) {
        int[] nArray;
        Object object = x$1;
        boolean bl = object instanceof AccumulateIntArray;
        return bl && $this == (nArray = x$1 == null ? null : ((AccumulateIntArray)x$1).scala$compat$java8$converterImpl$AccumulateIntArray$$underlying());
    }

    private AccumulateIntArray$() {
    }
}

