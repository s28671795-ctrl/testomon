/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalStableApi;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

@InternalStableApi
public final class JavaDurationConverters$ {
    public static final JavaDurationConverters$ MODULE$ = new JavaDurationConverters$();

    public FiniteDuration asFiniteDuration(java.time.Duration duration) {
        return JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(this.JavaDurationOps(duration));
    }

    public final java.time.Duration JavaDurationOps(java.time.Duration self) {
        return self;
    }

    public final Duration ScalaDurationOps(Duration self) {
        return self;
    }

    private JavaDurationConverters$() {
    }
}

