/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.SupervisorStrategy;
import akka.routing.ConsistentHashingPool;
import akka.routing.ConsistentHashingRouter$emptyConsistentHashMapping$;
import akka.routing.Pool$;
import akka.routing.Resizer;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple7;
import scala.runtime.AbstractFunction7;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class ConsistentHashingPool$
extends AbstractFunction7<Object, Option<Resizer>, Object, PartialFunction<Object, Object>, SupervisorStrategy, String, Object, ConsistentHashingPool>
implements Serializable {
    public static final ConsistentHashingPool$ MODULE$ = new ConsistentHashingPool$();

    public Option<Resizer> $lessinit$greater$default$2() {
        return None$.MODULE$;
    }

    public int $lessinit$greater$default$3() {
        return 0;
    }

    public PartialFunction<Object, Object> $lessinit$greater$default$4() {
        return ConsistentHashingRouter$emptyConsistentHashMapping$.MODULE$;
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
        return "ConsistentHashingPool";
    }

    @Override
    public ConsistentHashingPool apply(int nrOfInstances, Option<Resizer> resizer, int virtualNodesFactor, PartialFunction<Object, Object> hashMapping, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return new ConsistentHashingPool(nrOfInstances, resizer, virtualNodesFactor, hashMapping, supervisorStrategy, routerDispatcher, usePoolDispatcher);
    }

    public Option<Resizer> apply$default$2() {
        return None$.MODULE$;
    }

    public int apply$default$3() {
        return 0;
    }

    public PartialFunction<Object, Object> apply$default$4() {
        return ConsistentHashingRouter$emptyConsistentHashMapping$.MODULE$;
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

    public Option<Tuple7<Object, Option<Resizer>, Object, PartialFunction<Object, Object>, SupervisorStrategy, String, Object>> unapply(ConsistentHashingPool x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple7<Object, Option<Resizer>, Object, PartialFunction<Object, Object>, SupervisorStrategy, String, Object>>(new Tuple7<Integer, Option<Resizer>, Integer, PartialFunction<Object, Object>, SupervisorStrategy, String, Boolean>(BoxesRunTime.boxToInteger(x$0.nrOfInstances()), x$0.resizer(), BoxesRunTime.boxToInteger(x$0.virtualNodesFactor()), x$0.hashMapping(), x$0.supervisorStrategy(), x$0.routerDispatcher(), BoxesRunTime.boxToBoolean(x$0.usePoolDispatcher())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ConsistentHashingPool$.class);
    }

    private ConsistentHashingPool$() {
    }
}

