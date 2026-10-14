/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.RoutingLogic;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.immutable.IndexedSeq;
import scala.package$;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class Router$
extends AbstractFunction2<RoutingLogic, IndexedSeq<Routee>, Router>
implements Serializable {
    public static final Router$ MODULE$ = new Router$();

    public IndexedSeq<Routee> $lessinit$greater$default$2() {
        return package$.MODULE$.Vector().empty();
    }

    @Override
    public final String toString() {
        return "Router";
    }

    @Override
    public Router apply(RoutingLogic logic, IndexedSeq<Routee> routees) {
        return new Router(logic, routees);
    }

    public IndexedSeq<Routee> apply$default$2() {
        return package$.MODULE$.Vector().empty();
    }

    public Option<Tuple2<RoutingLogic, IndexedSeq<Routee>>> unapply(Router x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<RoutingLogic, IndexedSeq<Routee>>>(new Tuple2<RoutingLogic, IndexedSeq<Routee>>(x$0.logic(), x$0.routees()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Router$.class);
    }

    private Router$() {
    }
}

