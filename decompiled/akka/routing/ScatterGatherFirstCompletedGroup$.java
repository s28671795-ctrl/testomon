/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.ScatterGatherFirstCompletedGroup;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.collection.immutable.Iterable;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.AbstractFunction3;
import scala.runtime.ModuleSerializationProxy;

public final class ScatterGatherFirstCompletedGroup$
extends AbstractFunction3<Iterable<String>, FiniteDuration, String, ScatterGatherFirstCompletedGroup>
implements Serializable {
    public static final ScatterGatherFirstCompletedGroup$ MODULE$ = new ScatterGatherFirstCompletedGroup$();

    public String $lessinit$greater$default$3() {
        return "akka.actor.default-dispatcher";
    }

    @Override
    public final String toString() {
        return "ScatterGatherFirstCompletedGroup";
    }

    @Override
    public ScatterGatherFirstCompletedGroup apply(Iterable<String> paths, FiniteDuration within, String routerDispatcher) {
        return new ScatterGatherFirstCompletedGroup(paths, within, routerDispatcher);
    }

    public String apply$default$3() {
        return "akka.actor.default-dispatcher";
    }

    public Option<Tuple3<Iterable<String>, FiniteDuration, String>> unapply(ScatterGatherFirstCompletedGroup x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<Iterable<String>, FiniteDuration, String>>(new Tuple3<Iterable<String>, FiniteDuration, String>(x$0.paths(), x$0.within(), x$0.routerDispatcher()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ScatterGatherFirstCompletedGroup$.class);
    }

    private ScatterGatherFirstCompletedGroup$() {
    }
}

