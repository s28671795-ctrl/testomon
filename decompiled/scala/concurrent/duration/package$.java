/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent.duration;

import java.util.concurrent.TimeUnit;
import scala.Tuple2;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

public final class package$ {
    public static final package$ MODULE$ = new package$();

    public final TimeUnit DAYS() {
        return TimeUnit.DAYS;
    }

    public final TimeUnit HOURS() {
        return TimeUnit.HOURS;
    }

    public final TimeUnit MICROSECONDS() {
        return TimeUnit.MICROSECONDS;
    }

    public final TimeUnit MILLISECONDS() {
        return TimeUnit.MILLISECONDS;
    }

    public final TimeUnit MINUTES() {
        return TimeUnit.MINUTES;
    }

    public final TimeUnit NANOSECONDS() {
        return TimeUnit.NANOSECONDS;
    }

    public final TimeUnit SECONDS() {
        return TimeUnit.SECONDS;
    }

    /*
     * WARNING - void declaration
     */
    public Duration pairIntToDuration(Tuple2<Object, TimeUnit> p) {
        void apply_unit;
        TimeUnit timeUnit = p._2();
        long apply_length = p._1$mcI$sp();
        return new FiniteDuration(apply_length, (TimeUnit)apply_unit);
    }

    /*
     * WARNING - void declaration
     */
    public FiniteDuration pairLongToDuration(Tuple2<Object, TimeUnit> p) {
        void apply_unit;
        TimeUnit timeUnit = p._2();
        long apply_length = p._1$mcJ$sp();
        return new FiniteDuration(apply_length, (TimeUnit)apply_unit);
    }

    public Tuple2<Object, TimeUnit> durationToPair(Duration d) {
        return new Tuple2<Object, TimeUnit>(d.length(), d.unit());
    }

    public final int DurationInt(int n) {
        return n;
    }

    public final long DurationLong(long n) {
        return n;
    }

    public final double DurationDouble(double d) {
        return d;
    }

    public final int IntMult(int i) {
        return i;
    }

    public final long LongMult(long i) {
        return i;
    }

    public final double DoubleMult(double f) {
        return f;
    }

    private package$() {
    }
}

