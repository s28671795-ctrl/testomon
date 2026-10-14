/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import com.typesafe.config.Config;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.regex.Pattern;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

public final class Helpers$ {
    public static final Helpers$ MODULE$ = new Helpers$();
    private static final boolean isWindows = MODULE$.toRootLowerCase(System.getProperty("os.name", "")).indexOf("win") >= 0;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.SSS");
    private static final ZoneId timeZone = ZoneId.systemDefault();

    public String toRootLowerCase(String s2) {
        return s2.toLowerCase(Locale.ROOT);
    }

    public boolean isWindows() {
        return isWindows;
    }

    public Pattern makePattern(String s2) {
        return Pattern.compile(new StringBuilder(6).append("^\\Q").append(s2.replace("?", "\\E.\\Q").replace("*", "\\E.*\\Q")).append("\\E$").toString());
    }

    public int compareIdentityHash(Object a, Object b) {
        long diff = ((long)System.identityHashCode(a) & 0xFFFFFFFFL) - ((long)System.identityHashCode(b) & 0xFFFFFFFFL);
        if (diff > 0L) {
            return 1;
        }
        if (diff < 0L) {
            return -1;
        }
        return 0;
    }

    public <T> Comparator<T> identityHashComparator(Comparator<T> comp) {
        return new Comparator<T>(comp){
            private final Comparator comp$1;

            public Comparator<T> reversed() {
                return Comparator.super.reversed();
            }

            public Comparator<T> thenComparing(Comparator<? super T> x$1) {
                return Comparator.super.thenComparing(x$1);
            }

            public <U> Comparator<T> thenComparing(Function<? super T, ? extends U> x$1, Comparator<? super U> x$2) {
                return Comparator.super.thenComparing(x$1, x$2);
            }

            public <U extends Comparable<? super U>> Comparator<T> thenComparing(Function<? super T, ? extends U> x$1) {
                return Comparator.super.thenComparing(x$1);
            }

            public Comparator<T> thenComparingInt(ToIntFunction<? super T> x$1) {
                return Comparator.super.thenComparingInt(x$1);
            }

            public Comparator<T> thenComparingLong(ToLongFunction<? super T> x$1) {
                return Comparator.super.thenComparingLong(x$1);
            }

            public Comparator<T> thenComparingDouble(ToDoubleFunction<? super T> x$1) {
                return Comparator.super.thenComparingDouble(x$1);
            }

            public int compare(T a, T b) {
                int n = Helpers$.MODULE$.compareIdentityHash(a, b);
                switch (n) {
                    case 0: {
                        if (BoxesRunTime.equals(a, b)) break;
                        return this.comp$1.compare(a, b);
                    }
                }
                return n;
            }
            {
                this.comp$1 = comp$1;
            }
        };
    }

    public String currentTimeMillisToUTCString(long timestamp) {
        long timeOfDay = timestamp % 86400000L;
        long hours = timeOfDay / 3600000L;
        long minutes = timeOfDay / 60000L % 60L;
        long seconds = timeOfDay / 1000L % 60L;
        long ms = timeOfDay % 1000L;
        StringBuilder sb = new StringBuilder(15);
        Serializable serializable = hours < 10L ? sb.append(0) : BoxedUnit.UNIT;
        sb.append(hours);
        sb.append(':');
        Serializable serializable2 = minutes < 10L ? sb.append(0) : BoxedUnit.UNIT;
        sb.append(minutes);
        sb.append(':');
        Serializable serializable3 = seconds < 10L ? sb.append(0) : BoxedUnit.UNIT;
        sb.append(seconds);
        sb.append('.');
        Serializable serializable4 = ms < 100L ? sb.append(0) : BoxedUnit.UNIT;
        Serializable serializable5 = ms < 10L ? sb.append(0) : BoxedUnit.UNIT;
        sb.append(ms);
        sb.append("UTC");
        return sb.toString();
    }

    private DateTimeFormatter formatter() {
        return formatter;
    }

    private ZoneId timeZone() {
        return timeZone;
    }

    public String timestamp(long time) {
        return this.formatter().format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), this.timeZone()));
    }

    public final String base64chars() {
        return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+~";
    }

    public String base64(long l, StringBuilder sb) {
        while (true) {
            sb.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+~".charAt((int)l & 0x3F));
            long next = l >>> 6;
            if (next == 0L) {
                return sb.toString();
            }
            l = next;
        }
    }

    public StringBuilder base64$default$2() {
        return new StringBuilder("$");
    }

    public final <A> A Requiring(A value) {
        return value;
    }

    public final Config ConfigOps(Config config) {
        return config;
    }

    private Helpers$() {
    }
}

