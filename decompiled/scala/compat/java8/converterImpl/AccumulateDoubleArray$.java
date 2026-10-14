/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.Predef$;
import scala.compat.java8.collectionImpl.package$;
import scala.compat.java8.converterImpl.AccumulateDoubleArray;
import scala.jdk.DoubleAccumulator;

public final class AccumulateDoubleArray$ {
    public static final AccumulateDoubleArray$ MODULE$ = new AccumulateDoubleArray$();

    public final DoubleAccumulator accumulate$extension(double[] $this) {
        return Predef$.MODULE$.wrapDoubleArray($this).to(package$.MODULE$.DoubleAccumulator());
    }

    public final int hashCode$extension(double[] $this) {
        return $this.hashCode();
    }

    public final boolean equals$extension(double[] $this, Object x$1) {
        double[] dArray;
        Object object = x$1;
        boolean bl = object instanceof AccumulateDoubleArray;
        return bl && $this == (dArray = x$1 == null ? null : ((AccumulateDoubleArray)x$1).scala$compat$java8$converterImpl$AccumulateDoubleArray$$underlying());
    }

    private AccumulateDoubleArray$() {
    }
}

