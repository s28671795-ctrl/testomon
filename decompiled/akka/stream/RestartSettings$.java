/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.RestartSettings;
import akka.stream.RestartSettings$LogSettings$;
import akka.util.ConstantFun$;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.time.Duration;
import scala.concurrent.duration.FiniteDuration;

public final class RestartSettings$ {
    public static final RestartSettings$ MODULE$ = new RestartSettings$();

    public RestartSettings apply(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor) {
        return new RestartSettings(minBackoff, maxBackoff, randomFactor, Integer.MAX_VALUE, minBackoff, RestartSettings$LogSettings$.MODULE$.defaultSettings(), ConstantFun$.MODULE$.anyToTrue());
    }

    public RestartSettings create(Duration minBackoff, Duration maxBackoff, double randomFactor) {
        return new RestartSettings(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(minBackoff)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(maxBackoff)), randomFactor, Integer.MAX_VALUE, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(minBackoff)), RestartSettings$LogSettings$.MODULE$.defaultSettings(), ConstantFun$.MODULE$.anyToTrue());
    }

    public RestartSettings.LogSettings createLogSettings(int logLevel) {
        return RestartSettings$LogSettings$.MODULE$.apply(logLevel);
    }

    private RestartSettings$() {
    }
}

