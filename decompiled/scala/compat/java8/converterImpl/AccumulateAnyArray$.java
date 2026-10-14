/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.Predef$;
import scala.collection.IterableFactory$;
import scala.compat.java8.collectionImpl.package$;
import scala.compat.java8.converterImpl.AccumulateAnyArray;
import scala.jdk.AnyAccumulator;
import scala.runtime.BoxesRunTime;

public final class AccumulateAnyArray$ {
    public static final AccumulateAnyArray$ MODULE$ = new AccumulateAnyArray$();

    public final <A> AnyAccumulator<A> accumulate$extension(Object $this) {
        return Predef$.MODULE$.genericWrapArray($this).to(IterableFactory$.MODULE$.toFactory(package$.MODULE$.Accumulator()));
    }

    public final <A> int hashCode$extension(Object $this) {
        return $this.hashCode();
    }

    public final <A> boolean equals$extension(Object $this, Object x$1) {
        Object object;
        Object object2 = x$1;
        boolean bl = object2 instanceof AccumulateAnyArray;
        return bl && BoxesRunTime.equals($this, object = x$1 == null ? null : ((AccumulateAnyArray)x$1).scala$compat$java8$converterImpl$AccumulateAnyArray$$underlying());
    }

    private AccumulateAnyArray$() {
    }
}

