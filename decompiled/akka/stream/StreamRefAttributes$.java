/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.StreamRefAttributes;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.time.Duration;
import scala.concurrent.duration.FiniteDuration;

public final class StreamRefAttributes$ {
    public static final StreamRefAttributes$ MODULE$ = new StreamRefAttributes$();

    public Attributes subscriptionTimeout(FiniteDuration timeout2) {
        return Attributes$.MODULE$.apply(new StreamRefAttributes.SubscriptionTimeout(timeout2));
    }

    public Attributes subscriptionTimeout(Duration timeout2) {
        return this.subscriptionTimeout(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2)));
    }

    public Attributes bufferCapacity(int capacity) {
        return Attributes$.MODULE$.apply(new StreamRefAttributes.BufferCapacity(capacity));
    }

    public Attributes demandRedeliveryInterval(FiniteDuration timeout2) {
        return Attributes$.MODULE$.apply(new StreamRefAttributes.DemandRedeliveryInterval(timeout2));
    }

    public Attributes demandRedeliveryInterval(Duration timeout2) {
        return this.demandRedeliveryInterval(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2)));
    }

    public Attributes finalTerminationSignalDeadline(FiniteDuration timeout2) {
        return Attributes$.MODULE$.apply(new StreamRefAttributes.FinalTerminationSignalDeadline(timeout2));
    }

    public Attributes finalTerminationSignalDeadline(Duration timeout2) {
        return this.finalTerminationSignalDeadline(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2)));
    }

    private StreamRefAttributes$() {
    }
}

