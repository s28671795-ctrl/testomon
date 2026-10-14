/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.util.JavaVersion$;
import scala.MatchError;
import scala.collection.immutable.Nil$;
import scala.reflect.ClassTag;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@InternalApi
public final class FlightRecorderLoader$ {
    public static final FlightRecorderLoader$ MODULE$ = new FlightRecorderLoader$();

    public <T> T load(ClassicActorSystemProvider casp, String fqcn, T fallback, ClassTag<T> evidence$1) {
        ExtendedActorSystem system = (ExtendedActorSystem)casp.classicSystem();
        if (JavaVersion$.MODULE$.majorVersion() >= 11 && system.settings().config().getBoolean("akka.java-flight-recorder.enabled")) {
            Try<T> try_ = system.dynamicAccess().createInstanceFor(fqcn, Nil$.MODULE$, evidence$1);
            if (try_ instanceof Success) {
                Success success = (Success)try_;
                Object jfr = success.value();
                return jfr;
            }
            if (try_ instanceof Failure) {
                Failure failure = (Failure)try_;
                Throwable ex = failure.exception();
                system.log().warning("Failed to load JFR flight recorder, falling back to noop. Exception: {}", (Object)ex.toString());
                return fallback;
            }
            throw new MatchError(try_);
        }
        return fallback;
    }

    private FlightRecorderLoader$() {
    }
}

