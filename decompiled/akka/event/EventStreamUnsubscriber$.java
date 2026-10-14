/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ExtendedActorSystem;
import akka.actor.Props;
import akka.actor.Props$;
import akka.event.EventStream;
import akka.event.EventStreamUnsubscriber;
import akka.event.Logging;
import akka.event.Logging$;
import java.util.concurrent.atomic.AtomicInteger;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

public final class EventStreamUnsubscriber$ {
    public static final EventStreamUnsubscriber$ MODULE$ = new EventStreamUnsubscriber$();
    private static final AtomicInteger unsubscribersCount = new AtomicInteger(0);

    public boolean $lessinit$greater$default$2() {
        return false;
    }

    private AtomicInteger unsubscribersCount() {
        return unsubscribersCount;
    }

    private Props props(EventStream eventStream, boolean debug) {
        return Props$.MODULE$.apply(EventStreamUnsubscriber.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{eventStream, BoxesRunTime.boxToBoolean(debug)})).withDispatcher("akka.actor.internal-dispatcher");
    }

    public ActorRef start(ActorSystem system, EventStream stream) {
        boolean debug = system.settings().config().getBoolean("akka.actor.debug.event-stream");
        ActorRef unsubscriber = ((ExtendedActorSystem)system).systemActorOf(this.props(stream, debug), new StringBuilder(24).append("eventStreamUnsubscriber-").append(this.unsubscribersCount().incrementAndGet()).toString());
        if (debug) {
            stream.publish(new Logging.Debug(Logging$.MODULE$.simpleName(this.getClass()), this.getClass(), new StringBuilder(30).append("registering unsubscriber with ").append(stream).toString()));
        }
        stream.initUnsubscriber(unsubscriber);
        return unsubscriber;
    }

    private EventStreamUnsubscriber$() {
    }
}

