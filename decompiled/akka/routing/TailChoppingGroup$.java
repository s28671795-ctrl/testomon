/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.TailChoppingGroup;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.collection.immutable.Iterable;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.AbstractFunction4;
import scala.runtime.ModuleSerializationProxy;

public final class TailChoppingGroup$
extends AbstractFunction4<Iterable<String>, FiniteDuration, FiniteDuration, String, TailChoppingGroup>
implements Serializable {
    public static final TailChoppingGroup$ MODULE$ = new TailChoppingGroup$();

    public String $lessinit$greater$default$4() {
        return "akka.actor.default-dispatcher";
    }

    @Override
    public final String toString() {
        return "TailChoppingGroup";
    }

    @Override
    public TailChoppingGroup apply(Iterable<String> paths, FiniteDuration within, FiniteDuration interval, String routerDispatcher) {
        return new TailChoppingGroup(paths, within, interval, routerDispatcher);
    }

    public String apply$default$4() {
        return "akka.actor.default-dispatcher";
    }

    public Option<Tuple4<Iterable<String>, FiniteDuration, FiniteDuration, String>> unapply(TailChoppingGroup x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<Iterable<String>, FiniteDuration, FiniteDuration, String>>(new Tuple4<Iterable<String>, FiniteDuration, FiniteDuration, String>(x$0.paths(), x$0.within(), x$0.interval(), x$0.routerDispatcher()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TailChoppingGroup$.class);
    }

    private TailChoppingGroup$() {
    }
}

