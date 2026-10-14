/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.RoundRobinGroup;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.immutable.Iterable;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class RoundRobinGroup$
extends AbstractFunction2<Iterable<String>, String, RoundRobinGroup>
implements Serializable {
    public static final RoundRobinGroup$ MODULE$ = new RoundRobinGroup$();

    public String $lessinit$greater$default$2() {
        return "akka.actor.default-dispatcher";
    }

    @Override
    public final String toString() {
        return "RoundRobinGroup";
    }

    @Override
    public RoundRobinGroup apply(Iterable<String> paths, String routerDispatcher) {
        return new RoundRobinGroup(paths, routerDispatcher);
    }

    public String apply$default$2() {
        return "akka.actor.default-dispatcher";
    }

    public Option<Tuple2<Iterable<String>, String>> unapply(RoundRobinGroup x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Iterable<String>, String>>(new Tuple2<Iterable<String>, String>(x$0.paths(), x$0.routerDispatcher()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RoundRobinGroup$.class);
    }

    private RoundRobinGroup$() {
    }
}

