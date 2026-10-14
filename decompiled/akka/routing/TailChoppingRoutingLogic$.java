/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.Scheduler;
import akka.routing.TailChoppingRoutingLogic;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.AbstractFunction4;
import scala.runtime.ModuleSerializationProxy;

public final class TailChoppingRoutingLogic$
extends AbstractFunction4<Scheduler, FiniteDuration, FiniteDuration, ExecutionContext, TailChoppingRoutingLogic>
implements Serializable {
    public static final TailChoppingRoutingLogic$ MODULE$ = new TailChoppingRoutingLogic$();

    @Override
    public final String toString() {
        return "TailChoppingRoutingLogic";
    }

    @Override
    public TailChoppingRoutingLogic apply(Scheduler scheduler, FiniteDuration within, FiniteDuration interval, ExecutionContext context) {
        return new TailChoppingRoutingLogic(scheduler, within, interval, context);
    }

    public Option<Tuple4<Scheduler, FiniteDuration, FiniteDuration, ExecutionContext>> unapply(TailChoppingRoutingLogic x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<Scheduler, FiniteDuration, FiniteDuration, ExecutionContext>>(new Tuple4<Scheduler, FiniteDuration, FiniteDuration, ExecutionContext>(x$0.scheduler(), x$0.within(), x$0.interval(), x$0.context()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TailChoppingRoutingLogic$.class);
    }

    private TailChoppingRoutingLogic$() {
    }
}

