/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import scala.Int$;
import scala.MatchError;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.FiniteDuration$;

public final class DurationConverters$ {
    public static final DurationConverters$ MODULE$ = new DurationConverters$();

    public final FiniteDuration toScala(Duration duration) {
        FiniteDuration finiteDuration;
        long originalSeconds = duration.getSeconds();
        int originalNanos = duration.getNano();
        if (originalNanos == 0) {
            finiteDuration = originalSeconds == 0L ? Duration$.MODULE$.Zero() : FiniteDuration$.MODULE$.apply(originalSeconds, TimeUnit.SECONDS);
        } else if (originalSeconds == 0L) {
            finiteDuration = FiniteDuration$.MODULE$.apply(Int$.MODULE$.int2long(originalNanos), TimeUnit.NANOSECONDS);
        } else {
            try {
                long secondsAsNanos = Math.multiplyExact(originalSeconds, 1000000000L);
                long totalNanos = secondsAsNanos + (long)originalNanos;
                if (!(totalNanos < 0L && secondsAsNanos < 0L || totalNanos > 0L && secondsAsNanos > 0L)) {
                    throw new ArithmeticException();
                }
                finiteDuration = FiniteDuration$.MODULE$.apply(totalNanos, TimeUnit.NANOSECONDS);
            }
            catch (ArithmeticException arithmeticException) {
                throw new IllegalArgumentException(new StringBuilder(54).append("Java duration ").append(duration).append(" cannot be expressed as a Scala duration").toString());
            }
        }
        return finiteDuration;
    }

    public final Duration toJava(FiniteDuration duration) {
        Duration duration2;
        if (duration.length() == 0L) {
            duration2 = Duration.ZERO;
        } else {
            Duration duration3;
            TimeUnit timeUnit = duration.unit();
            if (((Object)((Object)TimeUnit.NANOSECONDS)).equals((Object)timeUnit)) {
                duration3 = Duration.ofNanos(duration.length());
            } else if (((Object)((Object)TimeUnit.MICROSECONDS)).equals((Object)timeUnit)) {
                duration3 = Duration.of(duration.length(), ChronoUnit.MICROS);
            } else if (((Object)((Object)TimeUnit.MILLISECONDS)).equals((Object)timeUnit)) {
                duration3 = Duration.ofMillis(duration.length());
            } else if (((Object)((Object)TimeUnit.SECONDS)).equals((Object)timeUnit)) {
                duration3 = Duration.ofSeconds(duration.length());
            } else if (((Object)((Object)TimeUnit.MINUTES)).equals((Object)timeUnit)) {
                duration3 = Duration.ofMinutes(duration.length());
            } else if (((Object)((Object)TimeUnit.HOURS)).equals((Object)timeUnit)) {
                duration3 = Duration.ofHours(duration.length());
            } else if (((Object)((Object)TimeUnit.DAYS)).equals((Object)timeUnit)) {
                duration3 = Duration.ofDays(duration.length());
            } else {
                throw new MatchError((Object)timeUnit);
            }
            duration2 = duration3;
        }
        return duration2;
    }

    public final Duration DurationOps(Duration duration) {
        return duration;
    }

    public final FiniteDuration FiniteDurationops(FiniteDuration duration) {
        return duration;
    }

    private DurationConverters$() {
    }
}

