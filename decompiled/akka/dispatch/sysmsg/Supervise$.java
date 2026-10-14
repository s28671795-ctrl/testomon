/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.actor.ActorRef;
import akka.dispatch.sysmsg.Supervise;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class Supervise$
extends AbstractFunction2<ActorRef, Object, Supervise>
implements Serializable {
    public static final Supervise$ MODULE$ = new Supervise$();

    @Override
    public final String toString() {
        return "Supervise";
    }

    @Override
    public Supervise apply(ActorRef child, boolean async) {
        return new Supervise(child, async);
    }

    public Option<Tuple2<ActorRef, Object>> unapply(Supervise x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<ActorRef, Object>>(new Tuple2<ActorRef, Boolean>(x$0.child(), BoxesRunTime.boxToBoolean(x$0.async())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Supervise$.class);
    }

    private Supervise$() {
    }
}

