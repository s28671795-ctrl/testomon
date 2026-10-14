/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.RandomGroup;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.immutable.Iterable;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class RandomGroup$
extends AbstractFunction2<Iterable<String>, String, RandomGroup>
implements Serializable {
    public static final RandomGroup$ MODULE$ = new RandomGroup$();

    public String $lessinit$greater$default$2() {
        return "akka.actor.default-dispatcher";
    }

    @Override
    public final String toString() {
        return "RandomGroup";
    }

    @Override
    public RandomGroup apply(Iterable<String> paths, String routerDispatcher) {
        return new RandomGroup(paths, routerDispatcher);
    }

    public String apply$default$2() {
        return "akka.actor.default-dispatcher";
    }

    public Option<Tuple2<Iterable<String>, String>> unapply(RandomGroup x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Iterable<String>, String>>(new Tuple2<Iterable<String>, String>(x$0.paths(), x$0.routerDispatcher()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RandomGroup$.class);
    }

    private RandomGroup$() {
    }
}

