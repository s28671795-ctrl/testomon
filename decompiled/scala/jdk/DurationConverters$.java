/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.time.Duration;
import scala.concurrent.duration.FiniteDuration;

public final class DurationConverters$ {
    public static final DurationConverters$ MODULE$ = new DurationConverters$();

    public Duration JavaDurationOps(Duration duration) {
        return duration;
    }

    public final FiniteDuration ScalaDurationOps(FiniteDuration duration) {
        return duration;
    }

    private DurationConverters$() {
    }
}

