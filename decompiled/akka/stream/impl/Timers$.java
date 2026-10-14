/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.FiniteDuration$;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;

@InternalApi
public final class Timers$ {
    public static final Timers$ MODULE$ = new Timers$();

    public FiniteDuration timeoutCheckInterval(FiniteDuration timeout2) {
        if (timeout2.$greater(new package.DurationInt(package$.MODULE$.DurationInt(1)).second())) {
            return new package.DurationInt(package$.MODULE$.DurationInt(1)).second();
        }
        return FiniteDuration$.MODULE$.apply(scala.math.package$.MODULE$.min(scala.math.package$.MODULE$.max(timeout2.toNanos() / 8L, new package.DurationInt(package$.MODULE$.DurationInt(100)).millis().toNanos()), timeout2.toNanos() / 2L), TimeUnit.NANOSECONDS);
    }

    private Timers$() {
    }
}

