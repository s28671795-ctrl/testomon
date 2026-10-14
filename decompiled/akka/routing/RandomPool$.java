/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.SupervisorStrategy;
import akka.routing.Pool$;
import akka.routing.RandomPool;
import akka.routing.Resizer;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple5;
import scala.runtime.AbstractFunction5;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class RandomPool$
extends AbstractFunction5<Object, Option<Resizer>, SupervisorStrategy, String, Object, RandomPool>
implements Serializable {
    public static final RandomPool$ MODULE$ = new RandomPool$();

    public Option<Resizer> $lessinit$greater$default$2() {
        return None$.MODULE$;
    }

    public SupervisorStrategy $lessinit$greater$default$3() {
        return Pool$.MODULE$.defaultSupervisorStrategy();
    }

    public String $lessinit$greater$default$4() {
        return "akka.actor.default-dispatcher";
    }

    public boolean $lessinit$greater$default$5() {
        return false;
    }

    @Override
    public final String toString() {
        return "RandomPool";
    }

    @Override
    public RandomPool apply(int nrOfInstances, Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return new RandomPool(nrOfInstances, resizer, supervisorStrategy, routerDispatcher, usePoolDispatcher);
    }

    public Option<Resizer> apply$default$2() {
        return None$.MODULE$;
    }

    public SupervisorStrategy apply$default$3() {
        return Pool$.MODULE$.defaultSupervisorStrategy();
    }

    public String apply$default$4() {
        return "akka.actor.default-dispatcher";
    }

    public boolean apply$default$5() {
        return false;
    }

    public Option<Tuple5<Object, Option<Resizer>, SupervisorStrategy, String, Object>> unapply(RandomPool x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple5<Object, Option<Resizer>, SupervisorStrategy, String, Object>>(new Tuple5<Integer, Option<Resizer>, SupervisorStrategy, String, Boolean>(BoxesRunTime.boxToInteger(x$0.nrOfInstances()), x$0.resizer(), x$0.supervisorStrategy(), x$0.routerDispatcher(), BoxesRunTime.boxToBoolean(x$0.usePoolDispatcher())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RandomPool$.class);
    }

    private RandomPool$() {
    }
}

