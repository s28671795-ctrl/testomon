/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk.javaapi;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import scala.MatchError;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;

public final class DurationConverters$ {
    public static final DurationConverters$ MODULE$ = new DurationConverters$();

    /*
     * WARNING - void declaration
     */
    public FiniteDuration toScala(Duration duration) {
        FiniteDuration finiteDuration;
        long originalSeconds = duration.getSeconds();
        int originalNanos = duration.getNano();
        if (originalNanos == 0) {
            if (originalSeconds == 0L) {
                return Duration$.MODULE$.Zero();
            }
            TimeUnit apply_unit = TimeUnit.SECONDS;
            return new FiniteDuration(originalSeconds, apply_unit);
        }
        if (originalSeconds == 0L) {
            void apply_unit;
            TimeUnit timeUnit = TimeUnit.NANOSECONDS;
            long apply_length = originalNanos;
            return new FiniteDuration(apply_length, (TimeUnit)apply_unit);
        }
        try {
            long secondsAsNanos = Math.multiplyExact(originalSeconds, 1000000000L);
            long totalNanos = secondsAsNanos + (long)originalNanos;
            if (!(totalNanos < 0L && secondsAsNanos < 0L || totalNanos > 0L && secondsAsNanos > 0L)) {
                throw new ArithmeticException();
            }
            TimeUnit apply_unit = TimeUnit.NANOSECONDS;
            finiteDuration = new FiniteDuration(totalNanos, apply_unit);
            Object var13_9 = null;
        }
        catch (ArithmeticException arithmeticException) {
            throw new IllegalArgumentException(new StringBuilder(54).append("Java duration ").append(duration).append(" cannot be expressed as a Scala duration").toString());
        }
        return finiteDuration;
    }

    public Duration toJava(FiniteDuration duration) {
        if (duration.length() == 0L) {
            return Duration.ZERO;
        }
        TimeUnit timeUnit = duration.unit();
        if (((Object)((Object)TimeUnit.NANOSECONDS)).equals((Object)timeUnit)) {
            return Duration.ofNanos(duration.length());
        }
        if (((Object)((Object)TimeUnit.MICROSECONDS)).equals((Object)timeUnit)) {
            return Duration.of(duration.length(), ChronoUnit.MICROS);
        }
        if (((Object)((Object)TimeUnit.MILLISECONDS)).equals((Object)timeUnit)) {
            return Duration.ofMillis(duration.length());
        }
        if (((Object)((Object)TimeUnit.SECONDS)).equals((Object)timeUnit)) {
            return Duration.ofSeconds(duration.length());
        }
        if (((Object)((Object)TimeUnit.MINUTES)).equals((Object)timeUnit)) {
            return Duration.ofMinutes(duration.length());
        }
        if (((Object)((Object)TimeUnit.HOURS)).equals((Object)timeUnit)) {
            return Duration.ofHours(duration.length());
        }
        if (((Object)((Object)TimeUnit.DAYS)).equals((Object)timeUnit)) {
            return Duration.ofDays(duration.length());
        }
        throw new MatchError((Object)timeUnit);
    }

    private DurationConverters$() {
    }
}

