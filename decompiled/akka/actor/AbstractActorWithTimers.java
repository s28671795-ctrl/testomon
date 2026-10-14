/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Actor;
import akka.actor.TimerScheduler;
import akka.actor.TimerSchedulerImpl;
import akka.actor.Timers;
import scala.Option;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005e1Qa\u0001\u0003\u0002\u0002%AQ!\u0005\u0001\u0005\u0002IAQ\u0001\u0006\u0001\u0005\u0006U\u0011q#\u00112tiJ\f7\r^!di>\u0014x+\u001b;i)&lWM]:\u000b\u0005\u00151\u0011!B1di>\u0014(\"A\u0004\u0002\t\u0005\\7.Y\u0002\u0001'\r\u0001!B\u0004\t\u0003\u00171i\u0011\u0001B\u0005\u0003\u001b\u0011\u0011Q\"\u00112tiJ\f7\r^!di>\u0014\bCA\u0006\u0010\u0013\t\u0001BA\u0001\u0004US6,'o]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003M\u0001\"a\u0003\u0001\u0002\u0013\u001d,G\u000fV5nKJ\u001cX#\u0001\f\u0011\u0005-9\u0012B\u0001\r\u0005\u00059!\u0016.\\3s'\u000eDW\rZ;mKJ\u0004")
public abstract class AbstractActorWithTimers
extends AbstractActor
implements Timers {
    private TimerSchedulerImpl akka$actor$Timers$$_timers;

    @Override
    public /* synthetic */ void akka$actor$Timers$$super$aroundPreRestart(Throwable reason, Option message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    public /* synthetic */ void akka$actor$Timers$$super$aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    public /* synthetic */ void akka$actor$Timers$$super$aroundReceive(PartialFunction receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    public final TimerScheduler timers() {
        return Timers.timers$(this);
    }

    @Override
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Timers.aroundPreRestart$(this, reason, message);
    }

    @Override
    public void aroundPostStop() {
        Timers.aroundPostStop$(this);
    }

    @Override
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Timers.aroundReceive$(this, receive2, msg);
    }

    @Override
    public TimerSchedulerImpl akka$actor$Timers$$_timers() {
        return this.akka$actor$Timers$$_timers;
    }

    @Override
    public final void akka$actor$Timers$_setter_$akka$actor$Timers$$_timers_$eq(TimerSchedulerImpl x$1) {
        this.akka$actor$Timers$$_timers = x$1;
    }

    public final TimerScheduler getTimers() {
        return this.timers();
    }

    public AbstractActorWithTimers() {
        Timers.$init$(this);
        Statics.releaseFence();
    }
}

