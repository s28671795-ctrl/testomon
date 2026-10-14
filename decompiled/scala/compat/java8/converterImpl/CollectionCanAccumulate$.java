/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.collection.IterableFactory$;
import scala.collection.IterableOnce;
import scala.compat.java8.collectionImpl.package$;
import scala.compat.java8.converterImpl.CollectionCanAccumulate;
import scala.jdk.AnyAccumulator;

public final class CollectionCanAccumulate$ {
    public static final CollectionCanAccumulate$ MODULE$ = new CollectionCanAccumulate$();

    public final <A> AnyAccumulator<A> accumulate$extension(IterableOnce<A> $this) {
        return $this.iterator().to(IterableFactory$.MODULE$.toFactory(package$.MODULE$.Accumulator()));
    }

    public final <A> int hashCode$extension(IterableOnce<A> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <A> boolean equals$extension(IterableOnce<A> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof CollectionCanAccumulate)) return false;
        boolean bl = true;
        if (!bl) return false;
        IterableOnce iterableOnce = x$1 == null ? null : ((CollectionCanAccumulate)x$1).scala$compat$java8$converterImpl$CollectionCanAccumulate$$underlying();
        IterableOnce<A> iterableOnce2 = $this;
        IterableOnce iterableOnce3 = iterableOnce;
        if (iterableOnce2 != null) {
            if (!iterableOnce2.equals(iterableOnce3)) return false;
            return true;
        }
        if (iterableOnce3 == null) return true;
        return false;
    }

    private CollectionCanAccumulate$() {
    }
}

