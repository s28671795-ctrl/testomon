/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.SupervisorStrategy;
import akka.routing.Pool$;
import akka.routing.Resizer;
import akka.routing.TailChoppingPool;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple7;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.AbstractFunction7;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class TailChoppingPool$
extends AbstractFunction7<Object, Option<Resizer>, FiniteDuration, FiniteDuration, SupervisorStrategy, String, Object, TailChoppingPool>
implements Serializable {
    public static final TailChoppingPool$ MODULE$ = new TailChoppingPool$();

    public Option<Resizer> $lessinit$greater$default$2() {
        return None$.MODULE$;
    }

    public SupervisorStrategy $lessinit$greater$default$5() {
        return Pool$.MODULE$.defaultSupervisorStrategy();
    }

    public String $lessinit$greater$default$6() {
        return "akka.actor.default-dispatcher";
    }

    public boolean $lessinit$greater$default$7() {
        return false;
    }

    @Override
    public final String toString() {
        return "TailChoppingPool";
    }

    @Override
    public TailChoppingPool apply(int nrOfInstances, Option<Resizer> resizer, FiniteDuration within, FiniteDuration interval, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return new TailChoppingPool(nrOfInstances, resizer, within, interval, supervisorStrategy, routerDispatcher, usePoolDispatcher);
    }

    public Option<Resizer> apply$default$2() {
        return None$.MODULE$;
    }

    public SupervisorStrategy apply$default$5() {
        return Pool$.MODULE$.defaultSupervisorStrategy();
    }

    public String apply$default$6() {
        return "akka.actor.default-dispatcher";
    }

    public boolean apply$default$7() {
        return false;
    }

    public Option<Tuple7<Object, Option<Resizer>, FiniteDuration, FiniteDuration, SupervisorStrategy, String, Object>> unapply(TailChoppingPool x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple7<Object, Option<Resizer>, FiniteDuration, FiniteDuration, SupervisorStrategy, String, Object>>(new Tuple7<Integer, Option<Resizer>, FiniteDuration, FiniteDuration, SupervisorStrategy, String, Boolean>(BoxesRunTime.boxToInteger(x$0.nrOfInstances()), x$0.resizer(), x$0.within(), x$0.interval(), x$0.supervisorStrategy(), x$0.routerDispatcher(), BoxesRunTime.boxToBoolean(x$0.usePoolDispatcher())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TailChoppingPool$.class);
    }

    private TailChoppingPool$() {
    }
}

