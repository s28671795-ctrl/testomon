/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AbstractSchedulerBase;
import akka.actor.Cancellable;
import akka.util.JavaDurationConverters;
import java.time.Duration;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;

public abstract class AbstractScheduler
extends AbstractSchedulerBase {
    @Override
    @Deprecated
    public abstract Cancellable schedule(FiniteDuration var1, FiniteDuration var2, Runnable var3, ExecutionContext var4);

    @Override
    @Deprecated
    public Cancellable schedule(Duration duration, Duration duration2, Runnable runnable2, ExecutionContext executionContext) {
        return this.schedule(JavaDurationConverters.asFiniteDuration(duration), JavaDurationConverters.asFiniteDuration(duration2), runnable2, executionContext);
    }

    @Override
    public abstract Cancellable scheduleOnce(FiniteDuration var1, Runnable var2, ExecutionContext var3);

    @Override
    public Cancellable scheduleOnce(Duration duration, Runnable runnable2, ExecutionContext executionContext) {
        return this.scheduleOnce(JavaDurationConverters.asFiniteDuration(duration), runnable2, executionContext);
    }

    @Override
    public abstract double maxFrequency();
}

