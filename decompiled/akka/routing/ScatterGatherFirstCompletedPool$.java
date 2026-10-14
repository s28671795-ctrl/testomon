/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.SupervisorStrategy;
import akka.routing.Pool$;
import akka.routing.Resizer;
import akka.routing.ScatterGatherFirstCompletedPool;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple6;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.AbstractFunction6;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class ScatterGatherFirstCompletedPool$
extends AbstractFunction6<Object, Option<Resizer>, FiniteDuration, SupervisorStrategy, String, Object, ScatterGatherFirstCompletedPool>
implements Serializable {
    public static final ScatterGatherFirstCompletedPool$ MODULE$ = new ScatterGatherFirstCompletedPool$();

    public Option<Resizer> $lessinit$greater$default$2() {
        return None$.MODULE$;
    }

    public SupervisorStrategy $lessinit$greater$default$4() {
        return Pool$.MODULE$.defaultSupervisorStrategy();
    }

    public String $lessinit$greater$default$5() {
        return "akka.actor.default-dispatcher";
    }

    public boolean $lessinit$greater$default$6() {
        return false;
    }

    @Override
    public final String toString() {
        return "ScatterGatherFirstCompletedPool";
    }

    @Override
    public ScatterGatherFirstCompletedPool apply(int nrOfInstances, Option<Resizer> resizer, FiniteDuration within, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return new ScatterGatherFirstCompletedPool(nrOfInstances, resizer, within, supervisorStrategy, routerDispatcher, usePoolDispatcher);
    }

    public Option<Resizer> apply$default$2() {
        return None$.MODULE$;
    }

    public SupervisorStrategy apply$default$4() {
        return Pool$.MODULE$.defaultSupervisorStrategy();
    }

    public String apply$default$5() {
        return "akka.actor.default-dispatcher";
    }

    public boolean apply$default$6() {
        return false;
    }

    public Option<Tuple6<Object, Option<Resizer>, FiniteDuration, SupervisorStrategy, String, Object>> unapply(ScatterGatherFirstCompletedPool x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple6<Object, Option<Resizer>, FiniteDuration, SupervisorStrategy, String, Object>>(new Tuple6<Integer, Option<Resizer>, FiniteDuration, SupervisorStrategy, String, Boolean>(BoxesRunTime.boxToInteger(x$0.nrOfInstances()), x$0.resizer(), x$0.within(), x$0.supervisorStrategy(), x$0.routerDispatcher(), BoxesRunTime.boxToBoolean(x$0.usePoolDispatcher())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ScatterGatherFirstCompletedPool$.class);
    }

    private ScatterGatherFirstCompletedPool$() {
    }
}

