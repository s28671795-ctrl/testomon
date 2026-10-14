/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ExtendedActorSystem;
import akka.actor.Props;
import akka.actor.Props$;
import akka.event.ActorClassificationUnsubscriber;
import java.util.concurrent.atomic.AtomicInteger;
import scala.Function1;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

public final class ActorClassificationUnsubscriber$ {
    public static final ActorClassificationUnsubscriber$ MODULE$ = new ActorClassificationUnsubscriber$();
    private static final AtomicInteger unsubscribersCount = new AtomicInteger(0);

    private AtomicInteger unsubscribersCount() {
        return unsubscribersCount;
    }

    public ActorRef start(ActorSystem system, String busName, Function1<ActorRef, BoxedUnit> unsubscribe, boolean debug) {
        boolean debug2 = system.settings().config().getBoolean("akka.actor.debug.event-stream");
        return ((ExtendedActorSystem)system).systemActorOf(this.props(busName, unsubscribe, debug2), new StringBuilder(32).append("actorClassificationUnsubscriber-").append(this.unsubscribersCount().incrementAndGet()).toString());
    }

    public boolean start$default$4() {
        return false;
    }

    private Props props(String busName, Function1<ActorRef, BoxedUnit> unsubscribe, boolean debug) {
        return Props$.MODULE$.apply(ActorClassificationUnsubscriber.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{busName, unsubscribe, BoxesRunTime.boxToBoolean(debug)}));
    }

    private ActorClassificationUnsubscriber$() {
    }
}

