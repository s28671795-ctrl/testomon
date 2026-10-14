/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.Routee;
import akka.routing.ScatterGatherFirstCompletedRoutees;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.immutable.IndexedSeq;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class ScatterGatherFirstCompletedRoutees$
extends AbstractFunction2<IndexedSeq<Routee>, FiniteDuration, ScatterGatherFirstCompletedRoutees>
implements Serializable {
    public static final ScatterGatherFirstCompletedRoutees$ MODULE$ = new ScatterGatherFirstCompletedRoutees$();

    @Override
    public final String toString() {
        return "ScatterGatherFirstCompletedRoutees";
    }

    @Override
    public ScatterGatherFirstCompletedRoutees apply(IndexedSeq<Routee> routees, FiniteDuration within) {
        return new ScatterGatherFirstCompletedRoutees(routees, within);
    }

    public Option<Tuple2<IndexedSeq<Routee>, FiniteDuration>> unapply(ScatterGatherFirstCompletedRoutees x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<IndexedSeq<Routee>, FiniteDuration>>(new Tuple2<IndexedSeq<Routee>, FiniteDuration>(x$0.routees(), x$0.within()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ScatterGatherFirstCompletedRoutees$.class);
    }

    private ScatterGatherFirstCompletedRoutees$() {
    }
}

