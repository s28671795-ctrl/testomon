/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.actor.ActorRef;
import akka.dispatch.sysmsg.DeathWatchNotification;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class DeathWatchNotification$
extends AbstractFunction3<ActorRef, Object, Object, DeathWatchNotification>
implements Serializable {
    public static final DeathWatchNotification$ MODULE$ = new DeathWatchNotification$();

    @Override
    public final String toString() {
        return "DeathWatchNotification";
    }

    @Override
    public DeathWatchNotification apply(ActorRef actor, boolean existenceConfirmed, boolean addressTerminated) {
        return new DeathWatchNotification(actor, existenceConfirmed, addressTerminated);
    }

    public Option<Tuple3<ActorRef, Object, Object>> unapply(DeathWatchNotification x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<ActorRef, Object, Object>>(new Tuple3<ActorRef, Boolean, Boolean>(x$0.actor(), BoxesRunTime.boxToBoolean(x$0.existenceConfirmed()), BoxesRunTime.boxToBoolean(x$0.addressTerminated())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DeathWatchNotification$.class);
    }

    private DeathWatchNotification$() {
    }
}

