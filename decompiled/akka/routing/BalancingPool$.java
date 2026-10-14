/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.SupervisorStrategy;
import akka.routing.BalancingPool;
import akka.routing.Pool$;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class BalancingPool$
extends AbstractFunction3<Object, SupervisorStrategy, String, BalancingPool>
implements Serializable {
    public static final BalancingPool$ MODULE$ = new BalancingPool$();

    public SupervisorStrategy $lessinit$greater$default$2() {
        return Pool$.MODULE$.defaultSupervisorStrategy();
    }

    public String $lessinit$greater$default$3() {
        return "akka.actor.default-dispatcher";
    }

    @Override
    public final String toString() {
        return "BalancingPool";
    }

    @Override
    public BalancingPool apply(int nrOfInstances, SupervisorStrategy supervisorStrategy, String routerDispatcher) {
        return new BalancingPool(nrOfInstances, supervisorStrategy, routerDispatcher);
    }

    public SupervisorStrategy apply$default$2() {
        return Pool$.MODULE$.defaultSupervisorStrategy();
    }

    public String apply$default$3() {
        return "akka.actor.default-dispatcher";
    }

    public Option<Tuple3<Object, SupervisorStrategy, String>> unapply(BalancingPool x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<Object, SupervisorStrategy, String>>(new Tuple3<Integer, SupervisorStrategy, String>(BoxesRunTime.boxToInteger(x$0.nrOfInstances()), x$0.supervisorStrategy(), x$0.routerDispatcher()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(BalancingPool$.class);
    }

    private BalancingPool$() {
    }
}

