/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Props;
import akka.pattern.BackoffOnFailureOptions;
import akka.pattern.BackoffOnFailureOptionsImpl;
import akka.pattern.BackoffOnFailureOptionsImpl$;
import akka.pattern.BackoffOnStopOptions;
import akka.pattern.BackoffOnStopOptionsImpl;
import akka.pattern.BackoffOnStopOptionsImpl$;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.time.Duration;
import scala.concurrent.duration.FiniteDuration;

public final class BackoffOpts$ {
    public static final BackoffOpts$ MODULE$ = new BackoffOpts$();

    public BackoffOnFailureOptions onFailure(Props childProps, String childName, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor) {
        return new BackoffOnFailureOptionsImpl(childProps, childName, minBackoff, maxBackoff, randomFactor, BackoffOnFailureOptionsImpl$.MODULE$.apply$default$6(), BackoffOnFailureOptionsImpl$.MODULE$.apply$default$7(), BackoffOnFailureOptionsImpl$.MODULE$.apply$default$8());
    }

    public BackoffOnFailureOptions onFailure(Props childProps, String childName, Duration minBackoff, Duration maxBackoff, double randomFactor) {
        return this.onFailure(childProps, childName, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(minBackoff)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(maxBackoff)), randomFactor);
    }

    public BackoffOnStopOptions onStop(Props childProps, String childName, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor) {
        return new BackoffOnStopOptionsImpl(childProps, childName, minBackoff, maxBackoff, randomFactor, BackoffOnStopOptionsImpl$.MODULE$.apply$default$6(), BackoffOnStopOptionsImpl$.MODULE$.apply$default$7(), BackoffOnStopOptionsImpl$.MODULE$.apply$default$8(), BackoffOnStopOptionsImpl$.MODULE$.apply$default$9());
    }

    public BackoffOnStopOptions onStop(Props childProps, String childName, Duration minBackoff, Duration maxBackoff, double randomFactor) {
        return this.onStop(childProps, childName, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(minBackoff)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(maxBackoff)), randomFactor);
    }

    private BackoffOpts$() {
    }
}

