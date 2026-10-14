/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.Scheduler;
import akka.routing.Routee;
import akka.routing.TailChoppingRoutees;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.collection.immutable.IndexedSeq;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.ModuleSerializationProxy;

public final class TailChoppingRoutees$
implements Serializable {
    public static final TailChoppingRoutees$ MODULE$ = new TailChoppingRoutees$();

    public final String toString() {
        return "TailChoppingRoutees";
    }

    public TailChoppingRoutees apply(Scheduler scheduler, IndexedSeq<Routee> routees, FiniteDuration within, FiniteDuration interval, ExecutionContext ec) {
        return new TailChoppingRoutees(scheduler, routees, within, interval, ec);
    }

    public Option<Tuple4<Scheduler, IndexedSeq<Routee>, FiniteDuration, FiniteDuration>> unapply(TailChoppingRoutees x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<Scheduler, IndexedSeq<Routee>, FiniteDuration, FiniteDuration>>(new Tuple4<Scheduler, IndexedSeq<Routee>, FiniteDuration, FiniteDuration>(x$0.scheduler(), x$0.routees(), x$0.within(), x$0.interval()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TailChoppingRoutees$.class);
    }

    private TailChoppingRoutees$() {
    }
}

