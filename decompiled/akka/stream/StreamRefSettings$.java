/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorSystem;
import akka.stream.StreamRefSettings;
import akka.stream.impl.streamref.StreamRefSettingsImpl;
import com.typesafe.config.Config;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;

public final class StreamRefSettings$ {
    public static final StreamRefSettings$ MODULE$ = new StreamRefSettings$();

    public StreamRefSettings create(ActorSystem system) {
        return this.apply(system);
    }

    public StreamRefSettings apply(ActorSystem system) {
        return this.apply(system.settings().config().getConfig("akka.stream.materializer.stream-ref"));
    }

    public StreamRefSettings create(Config c) {
        return this.apply(c);
    }

    public StreamRefSettings apply(Config c) {
        return new StreamRefSettingsImpl(c.getInt("buffer-capacity"), new package.DurationLong(package$.MODULE$.DurationLong(c.getDuration("demand-redelivery-interval", TimeUnit.MILLISECONDS))).millis(), new package.DurationLong(package$.MODULE$.DurationLong(c.getDuration("subscription-timeout", TimeUnit.MILLISECONDS))).millis(), new package.DurationLong(package$.MODULE$.DurationLong(c.getDuration("final-termination-signal-deadline", TimeUnit.MILLISECONDS))).millis());
    }

    private StreamRefSettings$() {
    }
}

