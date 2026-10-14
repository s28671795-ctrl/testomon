/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Props;
import akka.pattern.BackoffOnFailureOptions;
import akka.pattern.BackoffOnStopOptions;
import akka.pattern.BackoffSupervisor$GetCurrentChild$;
import akka.pattern.BackoffSupervisor$GetRestartCount$;
import akka.pattern.BackoffSupervisor$Reset$;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;
import scala.Function0;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.math.package$;
import scala.util.Try$;

public final class BackoffSupervisor$ {
    public static final BackoffSupervisor$ MODULE$ = new BackoffSupervisor$();

    public Props props(BackoffOnStopOptions options) {
        return options.props();
    }

    public Props props(BackoffOnFailureOptions options) {
        return options.props();
    }

    public BackoffSupervisor$GetCurrentChild$ getCurrentChild() {
        return BackoffSupervisor$GetCurrentChild$.MODULE$;
    }

    public BackoffSupervisor$Reset$ reset() {
        return BackoffSupervisor$Reset$.MODULE$;
    }

    public BackoffSupervisor$GetRestartCount$ getRestartCount() {
        return BackoffSupervisor$GetRestartCount$.MODULE$;
    }

    public FiniteDuration calculateDelay(int restartCount, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor) {
        double rnd = 1.0 + ThreadLocalRandom.current().nextDouble() * randomFactor;
        Duration calculatedDuration = (Duration)Try$.MODULE$.apply((Function0<Duration> & Serializable)() -> maxBackoff.min(minBackoff.$times(package$.MODULE$.pow(2.0, restartCount))).$times(rnd)).getOrElse((Function0<FiniteDuration> & Serializable)() -> maxBackoff);
        Duration duration = calculatedDuration;
        if (duration instanceof FiniteDuration) {
            FiniteDuration finiteDuration = (FiniteDuration)duration;
            return finiteDuration;
        }
        return maxBackoff;
    }

    private BackoffSupervisor$() {
    }
}

