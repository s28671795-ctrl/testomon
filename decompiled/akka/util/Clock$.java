/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.annotation.InternalApi;
import akka.util.Clock;
import akka.util.NanoClock;
import akka.util.ScheduledClock;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;

@InternalApi
public final class Clock$
implements ExtensionId<Clock>,
ExtensionIdProvider {
    public static final Clock$ MODULE$ = new Clock$();

    static {
        ExtensionId.$init$(MODULE$);
    }

    @Override
    public Extension apply(ActorSystem system) {
        return ExtensionId.apply$((ExtensionId)this, system);
    }

    @Override
    public Extension apply(ClassicActorSystemProvider system) {
        return ExtensionId.apply$((ExtensionId)this, system);
    }

    @Override
    public final int hashCode() {
        return ExtensionId.hashCode$(this);
    }

    @Override
    public final boolean equals(Object other) {
        return ExtensionId.equals$(this, other);
    }

    @Override
    public Clock get(ActorSystem system) {
        return (Clock)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public Clock get(ClassicActorSystemProvider system) {
        return (Clock)ExtensionId.get$((ExtensionId)this, system);
    }

    public Clock$ lookup() {
        return this;
    }

    @Override
    public Clock createExtension(ExtendedActorSystem system) {
        FiniteDuration interval = new package.DurationLong(package$.MODULE$.DurationLong(system.settings().config().getDuration("akka.scheduled-clock-interval", TimeUnit.MILLISECONDS))).millis();
        if (interval.$greater(Duration$.MODULE$.Zero())) {
            return new ScheduledClock(interval, system.scheduler(), system.dispatcher());
        }
        return new NanoClock();
    }

    private Clock$() {
    }
}

