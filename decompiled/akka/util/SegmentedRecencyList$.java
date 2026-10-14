/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.OptionVal$;
import akka.util.SegmentedRecencyList;
import scala.collection.immutable.Seq;

@InternalApi
public final class SegmentedRecencyList$ {
    public static final SegmentedRecencyList$ MODULE$ = new SegmentedRecencyList$();

    public <A> SegmentedRecencyList<A> empty(Seq<Object> limits) {
        OptionVal$.MODULE$.None();
        return new SegmentedRecencyList(limits, null);
    }

    private SegmentedRecencyList$() {
    }
}

