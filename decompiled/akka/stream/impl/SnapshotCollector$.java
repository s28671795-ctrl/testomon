/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Props$;
import akka.annotation.InternalApi;
import akka.stream.impl.SnapshotCollector;
import java.io.Serializable;
import scala.Function0;
import scala.collection.immutable.Set;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;

@InternalApi
public final class SnapshotCollector$ {
    public static final SnapshotCollector$ MODULE$ = new SnapshotCollector$();

    public Props props(Set<ActorRef> streamActors, FiniteDuration timeout2, ActorRef replyTo) {
        return Props$.MODULE$.apply((Function0<SnapshotCollector> & Serializable)() -> new SnapshotCollector(streamActors, timeout2, replyTo), ClassTag$.MODULE$.apply(SnapshotCollector.class));
    }

    private SnapshotCollector$() {
    }
}

