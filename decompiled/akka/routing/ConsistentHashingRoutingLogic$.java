/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.ExtendedActorSystem;
import akka.routing.ConsistentHashingRouter$emptyConsistentHashMapping$;
import akka.routing.ConsistentHashingRoutingLogic;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple3;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class ConsistentHashingRoutingLogic$
implements Serializable {
    public static final ConsistentHashingRoutingLogic$ MODULE$ = new ConsistentHashingRoutingLogic$();

    public int $lessinit$greater$default$2() {
        return 0;
    }

    public PartialFunction<Object, Object> $lessinit$greater$default$3() {
        return ConsistentHashingRouter$emptyConsistentHashMapping$.MODULE$;
    }

    public Address defaultAddress(ActorSystem system) {
        return ((ExtendedActorSystem)system).provider().getDefaultAddress();
    }

    public ConsistentHashingRoutingLogic apply(ActorSystem system, int virtualNodesFactor, PartialFunction<Object, Object> hashMapping) {
        return new ConsistentHashingRoutingLogic(system, virtualNodesFactor, hashMapping);
    }

    public int apply$default$2() {
        return 0;
    }

    public PartialFunction<Object, Object> apply$default$3() {
        return ConsistentHashingRouter$emptyConsistentHashMapping$.MODULE$;
    }

    public Option<Tuple3<ActorSystem, Object, PartialFunction<Object, Object>>> unapply(ConsistentHashingRoutingLogic x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<ActorSystem, Object, PartialFunction<Object, Object>>>(new Tuple3<ActorSystem, Integer, PartialFunction<Object, Object>>(x$0.system(), BoxesRunTime.boxToInteger(x$0.virtualNodesFactor()), x$0.hashMapping()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ConsistentHashingRoutingLogic$.class);
    }

    private ConsistentHashingRoutingLogic$() {
    }
}

