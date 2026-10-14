/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.PrettyDuration$PrettyPrintableDuration$;
import scala.concurrent.duration.Duration;

public final class PrettyDuration$ {
    public static final PrettyDuration$ MODULE$ = new PrettyDuration$();

    public String format(Duration duration) {
        return PrettyDuration$PrettyPrintableDuration$.MODULE$.pretty$extension(this.PrettyPrintableDuration(duration));
    }

    public String format(Duration duration, boolean includeNanos, int precision) {
        return PrettyDuration$PrettyPrintableDuration$.MODULE$.pretty$extension(this.PrettyPrintableDuration(duration), includeNanos, precision);
    }

    public Duration PrettyPrintableDuration(Duration duration) {
        return duration;
    }

    private PrettyDuration$() {
    }
}

