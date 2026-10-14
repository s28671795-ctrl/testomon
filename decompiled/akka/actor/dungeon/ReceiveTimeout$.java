/*
 * Decompiled with CFR 0.152.
 */
package akka.actor.dungeon;

import akka.actor.ActorCell$;
import akka.actor.Cancellable;
import scala.Tuple2;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;

public final class ReceiveTimeout$ {
    public static final ReceiveTimeout$ MODULE$ = new ReceiveTimeout$();
    private static final Tuple2<Duration, Cancellable> emptyReceiveTimeoutData = new Tuple2<Duration.Infinite, Cancellable>(Duration$.MODULE$.Undefined(), ActorCell$.MODULE$.emptyCancellable());

    public final Tuple2<Duration, Cancellable> emptyReceiveTimeoutData() {
        return emptyReceiveTimeoutData;
    }

    private ReceiveTimeout$() {
    }
}

