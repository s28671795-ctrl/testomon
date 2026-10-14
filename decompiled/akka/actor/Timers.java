/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.actor.ActorCell;
import akka.actor.ActorRef;
import akka.actor.AutoReceivedMessage;
import akka.actor.Stash;
import akka.actor.TimerScheduler;
import akka.actor.TimerSchedulerImpl;
import akka.dispatch.Envelope;
import akka.dispatch.Envelope$;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import scala.Option;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\t4qa\u0003\u0007\u0011\u0002\u0007\u0005\u0011\u0003C\u0003\u001d\u0001\u0011\u0005Q\u0004C\u0003\"\u0001\u0011%!\u0005C\u0004'\u0001\t\u0007I\u0011B\u0014\t\u000b-\u0002AQ\u0001\u0017\t\rA\u0002A\u0011\u000b\b2\u0011\u0019A\u0005\u0001\"\u0015\u000f;!1\u0011\n\u0001C)\u001d)C1B\u0016\u0001\u0011\u0002\u0007\u0005\t\u0011\"\u0003X5\"Y1\f\u0001I\u0001\u0004\u0003\u0005I\u0011B\u000f]\u0011-i\u0006\u0001%A\u0002\u0002\u0003%IAX1\u0003\rQKW.\u001a:t\u0015\tia\"A\u0003bGR|'OC\u0001\u0010\u0003\u0011\t7n[1\u0004\u0001M\u0019\u0001A\u0005\r\u0011\u0005M1R\"\u0001\u000b\u000b\u0003U\tQa]2bY\u0006L!a\u0006\u000b\u0003\r\u0005s\u0017PU3g!\tI\"$D\u0001\r\u0013\tYBBA\u0003BGR|'/\u0001\u0004%S:LG\u000f\n\u000b\u0002=A\u00111cH\u0005\u0003AQ\u0011A!\u00168ji\u0006I\u0011m\u0019;pe\u000e+G\u000e\\\u000b\u0002GA\u0011\u0011\u0004J\u0005\u0003K1\u0011\u0011\"Q2u_J\u001cU\r\u001c7\u0002\u000f}#\u0018.\\3sgV\t\u0001\u0006\u0005\u0002\u001aS%\u0011!\u0006\u0004\u0002\u0013)&lWM]*dQ\u0016$W\u000f\\3s\u00136\u0004H.\u0001\u0004uS6,'o]\u000b\u0002[A\u0011\u0011DL\u0005\u0003_1\u0011a\u0002V5nKJ\u001c6\r[3ek2,'/\u0001\tbe>,h\u000e\u001a)sKJ+7\u000f^1siR\u0019aD\r!\t\u000bM*\u0001\u0019\u0001\u001b\u0002\rI,\u0017m]8o!\t)TH\u0004\u00027w9\u0011qGO\u0007\u0002q)\u0011\u0011\bE\u0001\u0007yI|w\u000e\u001e \n\u0003UI!\u0001\u0010\u000b\u0002\u000fA\f7m[1hK&\u0011ah\u0010\u0002\n)\"\u0014xn^1cY\u0016T!\u0001\u0010\u000b\t\u000b\u0005+\u0001\u0019\u0001\"\u0002\u000f5,7o]1hKB\u00191cQ#\n\u0005\u0011#\"AB(qi&|g\u000e\u0005\u0002\u0014\r&\u0011q\t\u0006\u0002\u0004\u0003:L\u0018AD1s_VtG\rU8tiN#x\u000e]\u0001\u000eCJ|WO\u001c3SK\u000e,\u0017N^3\u0015\u0007yYE\u000bC\u0003M\u000f\u0001\u0007Q*A\u0004sK\u000e,\u0017N^3\u0011\u00059\u000bfBA\rP\u0013\t\u0001F\"A\u0003BGR|'/\u0003\u0002S'\n9!+Z2fSZ,'B\u0001)\r\u0011\u0015)v\u00011\u0001F\u0003\ri7oZ\u0001\u0017gV\u0004XM\u001d\u0013be>,h\u000e\u001a)sKJ+7\u000f^1siR\u0019a\u0004W-\t\u000bMB\u0001\u0019\u0001\u001b\t\u000b\u0005C\u0001\u0019\u0001\"\n\u0005AR\u0012\u0001F:va\u0016\u0014H%\u0019:pk:$\u0007k\\:u'R|\u0007/\u0003\u0002I5\u0005\u00192/\u001e9fe\u0012\n'o\\;oIJ+7-Z5wKR\u0019ad\u00181\t\u000b1S\u0001\u0019A'\t\u000bUS\u0001\u0019A#\n\u0005%S\u0002")
public interface Timers
extends Actor {
    public void akka$actor$Timers$_setter_$akka$actor$Timers$$_timers_$eq(TimerSchedulerImpl var1);

    public /* synthetic */ void akka$actor$Timers$$super$aroundPreRestart(Throwable var1, Option var2);

    public /* synthetic */ void akka$actor$Timers$$super$aroundPostStop();

    public /* synthetic */ void akka$actor$Timers$$super$aroundReceive(PartialFunction var1, Object var2);

    private ActorCell actorCell() {
        return (ActorCell)this.context();
    }

    public TimerSchedulerImpl akka$actor$Timers$$_timers();

    public static /* synthetic */ TimerScheduler timers$(Timers $this) {
        return $this.timers();
    }

    default public TimerScheduler timers() {
        return this.akka$actor$Timers$$_timers();
    }

    public static /* synthetic */ void aroundPreRestart$(Timers $this, Throwable reason, Option message) {
        $this.aroundPreRestart(reason, message);
    }

    @Override
    default public void aroundPreRestart(Throwable reason, Option<Object> message) {
        this.timers().cancelAll();
        this.akka$actor$Timers$$super$aroundPreRestart(reason, message);
    }

    public static /* synthetic */ void aroundPostStop$(Timers $this) {
        $this.aroundPostStop();
    }

    @Override
    default public void aroundPostStop() {
        this.timers().cancelAll();
        this.akka$actor$Timers$$super$aroundPostStop();
    }

    public static /* synthetic */ void aroundReceive$(Timers $this, PartialFunction receive2, Object msg) {
        $this.aroundReceive(receive2, msg);
    }

    @Override
    default public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Object object = msg;
        if (object instanceof TimerSchedulerImpl.TimerMsg) {
            Object m4;
            TimerSchedulerImpl.TimerMsg timerMsg = (TimerSchedulerImpl.TimerMsg)object;
            Object object2 = this.akka$actor$Timers$$_timers().interceptTimerMsg(timerMsg);
            Object object3 = OptionVal$Some$.MODULE$.unapply(object2);
            if (!OptionVal$.MODULE$.isEmpty$extension(object3) && (m4 = OptionVal$.MODULE$.get$extension(object3)) instanceof AutoReceivedMessage) {
                AutoReceivedMessage autoReceivedMessage = (AutoReceivedMessage)m4;
                ((ActorCell)this.context()).autoReceiveMessage(Envelope$.MODULE$.apply(autoReceivedMessage, this.self(), this.context().system()));
            } else {
                Object object4 = OptionVal$Some$.MODULE$.unapply(object2);
                if (!OptionVal$.MODULE$.isEmpty$extension(object4)) {
                    Object m5 = OptionVal$.MODULE$.get$extension(object4);
                    if (this instanceof Stash) {
                        Envelope qual$1 = this.actorCell().currentMessage();
                        Object x$1 = m5;
                        ActorRef x$2 = qual$1.copy$default$2();
                        this.actorCell().currentMessage_$eq(qual$1.copy(x$1, x$2));
                    }
                    this.akka$actor$Timers$$super$aroundReceive(receive2, m5);
                }
            }
            return;
        }
        this.akka$actor$Timers$$super$aroundReceive(receive2, msg);
    }

    public static void $init$(Timers $this) {
        $this.akka$actor$Timers$_setter_$akka$actor$Timers$$_timers_$eq(new TimerSchedulerImpl($this.context()));
    }
}

