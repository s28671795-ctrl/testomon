/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.ConsistentHashingGroup;
import akka.routing.ConsistentHashingRouter$emptyConsistentHashMapping$;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple4;
import scala.collection.immutable.Iterable;
import scala.runtime.AbstractFunction4;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class ConsistentHashingGroup$
extends AbstractFunction4<Iterable<String>, Object, PartialFunction<Object, Object>, String, ConsistentHashingGroup>
implements Serializable {
    public static final ConsistentHashingGroup$ MODULE$ = new ConsistentHashingGroup$();

    public int $lessinit$greater$default$2() {
        return 0;
    }

    public PartialFunction<Object, Object> $lessinit$greater$default$3() {
        return ConsistentHashingRouter$emptyConsistentHashMapping$.MODULE$;
    }

    public String $lessinit$greater$default$4() {
        return "akka.actor.default-dispatcher";
    }

    @Override
    public final String toString() {
        return "ConsistentHashingGroup";
    }

    @Override
    public ConsistentHashingGroup apply(Iterable<String> paths, int virtualNodesFactor, PartialFunction<Object, Object> hashMapping, String routerDispatcher) {
        return new ConsistentHashingGroup(paths, virtualNodesFactor, hashMapping, routerDispatcher);
    }

    public int apply$default$2() {
        return 0;
    }

    public PartialFunction<Object, Object> apply$default$3() {
        return ConsistentHashingRouter$emptyConsistentHashMapping$.MODULE$;
    }

    public String apply$default$4() {
        return "akka.actor.default-dispatcher";
    }

    public Option<Tuple4<Iterable<String>, Object, PartialFunction<Object, Object>, String>> unapply(ConsistentHashingGroup x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<Iterable<String>, Object, PartialFunction<Object, Object>, String>>(new Tuple4<Iterable<String>, Integer, PartialFunction<Object, Object>, String>(x$0.paths(), BoxesRunTime.boxToInteger(x$0.virtualNodesFactor()), x$0.hashMapping(), x$0.routerDispatcher()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ConsistentHashingGroup$.class);
    }

    private ConsistentHashingGroup$() {
    }
}

