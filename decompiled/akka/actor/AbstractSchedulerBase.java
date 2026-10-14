/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Scheduler;
import java.time.Duration;
import scala.Function0;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005Y1QAA\u0002\u0002\u0002!AQa\u0005\u0001\u0005\u0002Q\u0011Q#\u00112tiJ\f7\r^*dQ\u0016$W\u000f\\3s\u0005\u0006\u001cXM\u0003\u0002\u0005\u000b\u0005)\u0011m\u0019;pe*\ta!\u0001\u0003bW.\f7\u0001A\n\u0004\u0001%y\u0001C\u0001\u0006\u000e\u001b\u0005Y!\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\n\u00059Y!AB!osJ+g\r\u0005\u0002\u0011#5\t1!\u0003\u0002\u0013\u0007\tI1k\u00195fIVdWM]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003U\u0001\"\u0001\u0005\u0001")
public abstract class AbstractSchedulerBase
implements Scheduler {
    @Override
    public Cancellable scheduleWithFixedDelay(FiniteDuration initialDelay, FiniteDuration delay, Runnable runnable2, ExecutionContext executor2) {
        return Scheduler.scheduleWithFixedDelay$((Scheduler)this, initialDelay, delay, runnable2, executor2);
    }

    @Override
    public final Cancellable scheduleWithFixedDelay(Duration initialDelay, Duration delay, Runnable runnable2, ExecutionContext executor2) {
        return Scheduler.scheduleWithFixedDelay$((Scheduler)this, initialDelay, delay, runnable2, executor2);
    }

    @Override
    public final Cancellable scheduleWithFixedDelay(FiniteDuration initialDelay, FiniteDuration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.scheduleWithFixedDelay$((Scheduler)this, initialDelay, delay, receiver, message, executor2, sender);
    }

    @Override
    public final ActorRef scheduleWithFixedDelay$default$6(FiniteDuration initialDelay, FiniteDuration delay, ActorRef receiver, Object message) {
        return Scheduler.scheduleWithFixedDelay$default$6$(this, initialDelay, delay, receiver, message);
    }

    @Override
    public final Cancellable scheduleWithFixedDelay(Duration initialDelay, Duration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.scheduleWithFixedDelay$((Scheduler)this, initialDelay, delay, receiver, message, executor2, sender);
    }

    @Override
    public final Cancellable scheduleAtFixedRate(FiniteDuration initialDelay, FiniteDuration interval, Runnable runnable2, ExecutionContext executor2) {
        return Scheduler.scheduleAtFixedRate$((Scheduler)this, initialDelay, interval, runnable2, executor2);
    }

    @Override
    public final Cancellable scheduleAtFixedRate(Duration initialDelay, Duration interval, Runnable runnable2, ExecutionContext executor2) {
        return Scheduler.scheduleAtFixedRate$((Scheduler)this, initialDelay, interval, runnable2, executor2);
    }

    @Override
    public final Cancellable scheduleAtFixedRate(FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.scheduleAtFixedRate$((Scheduler)this, initialDelay, interval, receiver, message, executor2, sender);
    }

    @Override
    public final ActorRef scheduleAtFixedRate$default$6(FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message) {
        return Scheduler.scheduleAtFixedRate$default$6$(this, initialDelay, interval, receiver, message);
    }

    @Override
    public final Cancellable scheduleAtFixedRate(Duration initialDelay, Duration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.scheduleAtFixedRate$((Scheduler)this, initialDelay, interval, receiver, message, executor2, sender);
    }

    @Override
    public final Cancellable schedule(FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.schedule$((Scheduler)this, initialDelay, interval, receiver, message, executor2, sender);
    }

    @Override
    public final ActorRef schedule$default$6(FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message) {
        return Scheduler.schedule$default$6$(this, initialDelay, interval, receiver, message);
    }

    @Override
    public final Cancellable schedule(Duration initialDelay, Duration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.schedule$((Scheduler)this, initialDelay, interval, receiver, message, executor2, sender);
    }

    @Override
    public final Cancellable schedule(FiniteDuration initialDelay, FiniteDuration interval, Function0<BoxedUnit> f, ExecutionContext executor2) {
        return Scheduler.schedule$((Scheduler)this, initialDelay, interval, f, executor2);
    }

    @Override
    public Cancellable schedule(Duration initialDelay, Duration interval, Runnable runnable2, ExecutionContext executor2) {
        return Scheduler.schedule$((Scheduler)this, initialDelay, interval, runnable2, executor2);
    }

    @Override
    public final Cancellable scheduleOnce(FiniteDuration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.scheduleOnce$((Scheduler)this, delay, receiver, message, executor2, sender);
    }

    @Override
    public final ActorRef scheduleOnce$default$5(FiniteDuration delay, ActorRef receiver, Object message) {
        return Scheduler.scheduleOnce$default$5$(this, delay, receiver, message);
    }

    @Override
    public final Cancellable scheduleOnce(Duration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.scheduleOnce$((Scheduler)this, delay, receiver, message, executor2, sender);
    }

    @Override
    public final Cancellable scheduleOnce(FiniteDuration delay, Function0<BoxedUnit> f, ExecutionContext executor2) {
        return Scheduler.scheduleOnce$((Scheduler)this, delay, f, executor2);
    }

    @Override
    public Cancellable scheduleOnce(Duration delay, Runnable runnable2, ExecutionContext executor2) {
        return Scheduler.scheduleOnce$((Scheduler)this, delay, runnable2, executor2);
    }

    public AbstractSchedulerBase() {
        Scheduler.$init$(this);
    }
}

