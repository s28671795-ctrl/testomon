/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.OneForOneStrategy;
import akka.actor.OneForOneStrategy$;
import akka.actor.Props;
import akka.actor.SupervisorStrategy$;
import akka.pattern.BackoffOnStopOptionsImpl;
import akka.pattern.BackoffReset;
import akka.pattern.ForwardDeathLetters$;
import akka.pattern.HandlingWhileStopped;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple9;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class BackoffOnStopOptionsImpl$
implements Serializable {
    public static final BackoffOnStopOptionsImpl$ MODULE$ = new BackoffOnStopOptionsImpl$();

    public <T> Option<BackoffReset> $lessinit$greater$default$6() {
        return None$.MODULE$;
    }

    public <T> OneForOneStrategy $lessinit$greater$default$7() {
        return new OneForOneStrategy(OneForOneStrategy$.MODULE$.apply$default$1(), OneForOneStrategy$.MODULE$.apply$default$2(), OneForOneStrategy$.MODULE$.apply$default$3(), SupervisorStrategy$.MODULE$.defaultStrategy().decider());
    }

    public <T> HandlingWhileStopped $lessinit$greater$default$8() {
        return ForwardDeathLetters$.MODULE$;
    }

    public <T> Option<Function1<Object, Object>> $lessinit$greater$default$9() {
        return None$.MODULE$;
    }

    public final String toString() {
        return "BackoffOnStopOptionsImpl";
    }

    public <T> BackoffOnStopOptionsImpl<T> apply(Props childProps, String childName, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Option<BackoffReset> reset, OneForOneStrategy supervisorStrategy, HandlingWhileStopped handlingWhileStopped, Option<Function1<Object, Object>> finalStopMessage) {
        return new BackoffOnStopOptionsImpl(childProps, childName, minBackoff, maxBackoff, randomFactor, reset, supervisorStrategy, handlingWhileStopped, finalStopMessage);
    }

    public <T> Option<BackoffReset> apply$default$6() {
        return None$.MODULE$;
    }

    public <T> OneForOneStrategy apply$default$7() {
        return new OneForOneStrategy(OneForOneStrategy$.MODULE$.apply$default$1(), OneForOneStrategy$.MODULE$.apply$default$2(), OneForOneStrategy$.MODULE$.apply$default$3(), SupervisorStrategy$.MODULE$.defaultStrategy().decider());
    }

    public <T> HandlingWhileStopped apply$default$8() {
        return ForwardDeathLetters$.MODULE$;
    }

    public <T> Option<Function1<Object, Object>> apply$default$9() {
        return None$.MODULE$;
    }

    public <T> Option<Tuple9<Props, String, FiniteDuration, FiniteDuration, Object, Option<BackoffReset>, OneForOneStrategy, HandlingWhileStopped, Option<Function1<Object, Object>>>> unapply(BackoffOnStopOptionsImpl<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple9<Props, String, FiniteDuration, FiniteDuration, Object, Option<BackoffReset>, OneForOneStrategy, HandlingWhileStopped, Option<Function1<Object, Object>>>>(new Tuple9<Props, String, FiniteDuration, FiniteDuration, Double, Option<BackoffReset>, OneForOneStrategy, HandlingWhileStopped, Option<Function1<Object, Object>>>(x$0.childProps(), x$0.childName(), x$0.minBackoff(), x$0.maxBackoff(), BoxesRunTime.boxToDouble(x$0.randomFactor()), x$0.reset(), x$0.supervisorStrategy(), x$0.handlingWhileStopped(), x$0.finalStopMessage()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(BackoffOnStopOptionsImpl$.class);
    }

    private BackoffOnStopOptionsImpl$() {
    }
}

