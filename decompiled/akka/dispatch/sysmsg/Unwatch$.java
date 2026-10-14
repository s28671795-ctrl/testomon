/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.actor.ActorRef;
import akka.dispatch.sysmsg.Unwatch;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class Unwatch$
extends AbstractFunction2<ActorRef, ActorRef, Unwatch>
implements Serializable {
    public static final Unwatch$ MODULE$ = new Unwatch$();

    @Override
    public final String toString() {
        return "Unwatch";
    }

    @Override
    public Unwatch apply(ActorRef watchee, ActorRef watcher) {
        return new Unwatch(watchee, watcher);
    }

    public Option<Tuple2<ActorRef, ActorRef>> unapply(Unwatch x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<ActorRef, ActorRef>>(new Tuple2<ActorRef, ActorRef>(x$0.watchee(), x$0.watcher()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Unwatch$.class);
    }

    private Unwatch$() {
    }
}

