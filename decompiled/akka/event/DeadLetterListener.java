/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogMarker$;
import akka.actor.ActorRef;
import akka.actor.ActorRef$;
import akka.actor.AllDeadLetters;
import akka.actor.DeadLetter;
import akka.actor.DeadLetterActorRef;
import akka.actor.DeadLetterSuppression;
import akka.actor.Dropped;
import akka.actor.SupervisorStrategy;
import akka.actor.UnhandledMessage;
import akka.actor.WrappedMessage;
import akka.actor.WrappedMessage$;
import akka.annotation.InternalApi;
import akka.event.EventStream;
import akka.event.Logging$;
import akka.event.Logging$Info$;
import akka.util.PrettyDuration$;
import akka.util.PrettyDuration$PrettyPrintableDuration$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.concurrent.duration.Deadline;
import scala.concurrent.duration.Deadline$;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005c\u0001\u0002\r\u001a\u0001yAQa\u000b\u0001\u0005\u00021Bqa\f\u0001C\u0002\u0013\u0005\u0001\u0007\u0003\u00045\u0001\u0001\u0006I!\r\u0005\bk\u0001\u0011\r\u0011\"\u00057\u0011\u0019Q\u0004\u0001)A\u0005o!91\b\u0001b\u0001\n\u0013a\u0004B\u0002!\u0001A\u0003%Q\bC\u0004B\u0001\u0001\u0007I\u0011\u0003\u001c\t\u000f\t\u0003\u0001\u0019!C\t\u0007\"1\u0011\n\u0001Q!\n]BQA\u0013\u0001\u0005B-CQ\u0001\u0014\u0001\u0005B5CQ\u0001\u0018\u0001\u0005BuCQa\u001a\u0001\u0005B-CQ\u0001\u001b\u0001\u0005\n-CQ!\u001b\u0001\u0005\u0002)DQa\u001c\u0001\u0005\n)DQ\u0001\u001d\u0001\u0005\n)DQ!\u001d\u0001\u0005\nIDQ! \u0001\u0005\nyDq!a\u0003\u0001\t\u0013\ti\u0001C\u0004\u0002.\u0001!I!a\f\t\u000f\u0005m\u0002\u0001\"\u0003\u0002>\t\u0011B)Z1e\u0019\u0016$H/\u001a:MSN$XM\\3s\u0015\tQ2$A\u0003fm\u0016tGOC\u0001\u001d\u0003\u0011\t7n[1\u0004\u0001M\u0019\u0001aH\u0013\u0011\u0005\u0001\u001aS\"A\u0011\u000b\u0003\t\nQa]2bY\u0006L!\u0001J\u0011\u0003\r\u0005s\u0017PU3g!\t1\u0013&D\u0001(\u0015\tA3$A\u0003bGR|'/\u0003\u0002+O\t)\u0011i\u0019;pe\u00061A(\u001b8jiz\"\u0012!\f\t\u0003]\u0001i\u0011!G\u0001\fKZ,g\u000e^*ue\u0016\fW.F\u00012!\tq#'\u0003\u000243\tYQI^3oiN#(/Z1n\u00031)g/\u001a8u'R\u0014X-Y7!\u0003!i\u0017\r_\"pk:$X#A\u001c\u0011\u0005\u0001B\u0014BA\u001d\"\u0005\rIe\u000e^\u0001\n[\u0006D8i\\;oi\u0002\n!$[:BY^\f\u0017p\u001d'pO\u001eLgn\u001a#fC\u0012dU\r\u001e;feN,\u0012!\u0010\t\u0003AyJ!aP\u0011\u0003\u000f\t{w\u000e\\3b]\u0006Y\u0012n]!mo\u0006L8\u000fT8hO&tw\rR3bI2+G\u000f^3sg\u0002\nQaY8v]R\f\u0011bY8v]R|F%Z9\u0015\u0005\u0011;\u0005C\u0001\u0011F\u0013\t1\u0015E\u0001\u0003V]&$\bb\u0002%\n\u0003\u0003\u0005\raN\u0001\u0004q\u0012\n\u0014AB2pk:$\b%\u0001\u0005qe\u0016\u001cF/\u0019:u)\u0005!\u0015a\u00039pgR\u0014Vm\u001d;beR$\"\u0001\u0012(\t\u000b=c\u0001\u0019\u0001)\u0002\rI,\u0017m]8o!\t\t\u0016L\u0004\u0002S/:\u00111KV\u0007\u0002)*\u0011Q+H\u0001\u0007yI|w\u000e\u001e \n\u0003\tJ!\u0001W\u0011\u0002\u000fA\f7m[1hK&\u0011!l\u0017\u0002\n)\"\u0014xn^1cY\u0016T!\u0001W\u0011\u0002\u0015A\u0014XMU3ti\u0006\u0014H\u000fF\u0002E=~CQaT\u0007A\u0002ACQ\u0001Y\u0007A\u0002\u0005\fq!\\3tg\u0006<W\rE\u0002!E\u0012L!aY\u0011\u0003\r=\u0003H/[8o!\t\u0001S-\u0003\u0002gC\t\u0019\u0011I\\=\u0002\u0011A|7\u000f^*u_B\fa\"\u001b8de\u0016lWM\u001c;D_VtG/A\u0004sK\u000e,\u0017N^3\u0016\u0003-\u0004\"\u0001\\7\u000e\u0003\u0001I!A\\\u0015\u0003\u000fI+7-Z5wK\u0006A\"/Z2fSZ,w+\u001b;i\u00032<\u0018-_:M_\u001e<\u0017N\\4\u00025I,7-Z5wK^KG\u000f['bq\u000e{WO\u001c;M_\u001e<\u0017N\\4\u00023I,7-Z5wK^KG\u000f[*vgB,g\u000e\u001a'pO\u001eLgn\u001a\u000b\u0003WNDQ\u0001^\nA\u0002U\fqb];ta\u0016tG\rR;sCRLwN\u001c\t\u0003mnl\u0011a\u001e\u0006\u0003qf\f\u0001\u0002Z;sCRLwN\u001c\u0006\u0003u\u0006\n!bY8oGV\u0014(/\u001a8u\u0013\taxO\u0001\bGS:LG/\u001a#ve\u0006$\u0018n\u001c8\u0002)I,7-Z5wK^CWM\\*vgB,g\u000eZ3e)\u0011Yw0!\u0001\t\u000bQ$\u0002\u0019A;\t\u000f\u0005\rA\u00031\u0001\u0002\u0006\u0005y1/^:qK:$G)Z1eY&tW\rE\u0002w\u0003\u000fI1!!\u0003x\u0005!!U-\u00193mS:,\u0017!\u00047pO\u0012+\u0017\r\u001a'fiR,'\u000fF\u0003E\u0003\u001f\tI\u0002C\u0004\u0002\u0012U\u0001\r!a\u0005\u0002\u0003\u0011\u00042AJA\u000b\u0013\r\t9b\n\u0002\u000f\u00032dG)Z1e\u0019\u0016$H/\u001a:t\u0011\u001d\tY\"\u0006a\u0001\u0003;\tq\u0001Z8oK6\u001bx\r\u0005\u0003\u0002 \u0005\u001db\u0002BA\u0011\u0003G\u0001\"aU\u0011\n\u0007\u0005\u0015\u0012%\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003S\tYC\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003K\t\u0013AB5t%\u0016\fG\u000eF\u0002>\u0003cAq!a\r\u0017\u0001\u0004\t)$A\u0002t]\u0012\u00042AJA\u001c\u0013\r\tId\n\u0002\t\u0003\u000e$xN\u001d*fM\u0006\u0019\u0012n],sCB\u0004X\rZ*vaB\u0014Xm]:fIR\u0019Q(a\u0010\t\u000f\u0005Eq\u00031\u0001\u0002\u0014\u0001")
public class DeadLetterListener
implements Actor {
    private final EventStream eventStream;
    private final int maxCount;
    private final boolean isAlwaysLoggingDeadLetters;
    private int count;
    private ActorContext context;
    private ActorRef self;

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
    }

    @Override
    @InternalApi
    public void aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    @InternalApi
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    @InternalApi
    public void aroundPostRestart(Throwable reason) {
        Actor.aroundPostRestart$(this, reason);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return Actor.supervisorStrategy$(this);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public ActorContext context() {
        return this.context;
    }

    @Override
    public final ActorRef self() {
        return this.self;
    }

    @Override
    public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
        this.context = x$1;
    }

    @Override
    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
        this.self = x$1;
    }

    public EventStream eventStream() {
        return this.eventStream;
    }

    public int maxCount() {
        return this.maxCount;
    }

    private boolean isAlwaysLoggingDeadLetters() {
        return this.isAlwaysLoggingDeadLetters;
    }

    public int count() {
        return this.count;
    }

    public void count_$eq(int x$1) {
        this.count = x$1;
    }

    @Override
    public void preStart() {
        this.eventStream().subscribe(this.self(), DeadLetter.class);
        this.eventStream().subscribe(this.self(), Dropped.class);
        this.eventStream().subscribe(this.self(), UnhandledMessage.class);
    }

    @Override
    public void postRestart(Throwable reason) {
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) {
    }

    @Override
    public void postStop() {
        this.eventStream().unsubscribe(this.self());
    }

    public void akka$event$DeadLetterListener$$incrementCount() {
        if (this.count() == Integer.MAX_VALUE) {
            Logging$.MODULE$.getLogger(this).info("Resetting DeadLetterListener counter after reaching Int.MaxValue.");
            this.count_$eq(1);
            return;
        }
        this.count_$eq(this.count() + 1);
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        if (this.isAlwaysLoggingDeadLetters()) {
            return this.receiveWithAlwaysLogging();
        }
        Duration duration = this.context().system().settings().LogDeadLettersSuspendDuration();
        if (duration instanceof FiniteDuration) {
            FiniteDuration finiteDuration = (FiniteDuration)duration;
            return this.akka$event$DeadLetterListener$$receiveWithSuspendLogging(finiteDuration);
        }
        return this.receiveWithMaxCountLogging();
    }

    private PartialFunction<Object, BoxedUnit> receiveWithAlwaysLogging() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ DeadLetterListener $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof AllDeadLetters) {
                    AllDeadLetters allDeadLetters = (AllDeadLetters)A1;
                    if (!this.$outer.akka$event$DeadLetterListener$$isWrappedSuppressed(allDeadLetters)) {
                        this.$outer.akka$event$DeadLetterListener$$incrementCount();
                        this.$outer.akka$event$DeadLetterListener$$logDeadLetter(allDeadLetters, "");
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof AllDeadLetters;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    private PartialFunction<Object, BoxedUnit> receiveWithMaxCountLogging() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ DeadLetterListener $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof AllDeadLetters) {
                    AllDeadLetters allDeadLetters = (AllDeadLetters)A1;
                    if (!this.$outer.akka$event$DeadLetterListener$$isWrappedSuppressed(allDeadLetters)) {
                        this.$outer.akka$event$DeadLetterListener$$incrementCount();
                        if (this.$outer.count() == this.$outer.maxCount()) {
                            this.$outer.akka$event$DeadLetterListener$$logDeadLetter(allDeadLetters, ", no more dead letters will be logged");
                            this.$outer.context().stop(this.$outer.self());
                            return (B1)BoxedUnit.UNIT;
                        }
                        this.$outer.akka$event$DeadLetterListener$$logDeadLetter(allDeadLetters, "");
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof AllDeadLetters;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> akka$event$DeadLetterListener$$receiveWithSuspendLogging(FiniteDuration suspendDuration) {
        return new Serializable(this, suspendDuration){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ DeadLetterListener $outer;
            private final FiniteDuration suspendDuration$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof AllDeadLetters) {
                    AllDeadLetters allDeadLetters = (AllDeadLetters)A1;
                    if (!this.$outer.akka$event$DeadLetterListener$$isWrappedSuppressed(allDeadLetters)) {
                        this.$outer.akka$event$DeadLetterListener$$incrementCount();
                        if (this.$outer.count() == this.$outer.maxCount()) {
                            String doneMsg = new StringBuilder(48).append(", no more dead letters will be logged in next [").append(PrettyDuration$PrettyPrintableDuration$.MODULE$.pretty$extension(PrettyDuration$.MODULE$.PrettyPrintableDuration(this.suspendDuration$1))).append("]").toString();
                            this.$outer.akka$event$DeadLetterListener$$logDeadLetter(allDeadLetters, doneMsg);
                            this.$outer.context().become(this.$outer.akka$event$DeadLetterListener$$receiveWhenSuspended(this.suspendDuration$1, Deadline$.MODULE$.now().$plus(this.suspendDuration$1)));
                            return (B1)BoxedUnit.UNIT;
                        }
                        this.$outer.akka$event$DeadLetterListener$$logDeadLetter(allDeadLetters, "");
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof AllDeadLetters;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.suspendDuration$1 = suspendDuration$1;
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> akka$event$DeadLetterListener$$receiveWhenSuspended(FiniteDuration suspendDuration, Deadline suspendDeadline) {
        return new Serializable(this, suspendDeadline, suspendDuration){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ DeadLetterListener $outer;
            private final Deadline suspendDeadline$1;
            private final FiniteDuration suspendDuration$2;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof AllDeadLetters) {
                    AllDeadLetters allDeadLetters = (AllDeadLetters)A1;
                    if (!this.$outer.akka$event$DeadLetterListener$$isWrappedSuppressed(allDeadLetters)) {
                        this.$outer.akka$event$DeadLetterListener$$incrementCount();
                        if (this.suspendDeadline$1.isOverdue()) {
                            String doneMsg = new StringBuilder(58).append(", of which ").append(this.$outer.count() - this.$outer.maxCount() - 1).append(" were not logged. The counter will be reset now").toString();
                            this.$outer.akka$event$DeadLetterListener$$logDeadLetter(allDeadLetters, doneMsg);
                            this.$outer.count_$eq(0);
                            this.$outer.context().become(this.$outer.akka$event$DeadLetterListener$$receiveWithSuspendLogging(this.suspendDuration$2));
                            return (B1)BoxedUnit.UNIT;
                        }
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof AllDeadLetters;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.suspendDeadline$1 = suspendDeadline$1;
                this.suspendDuration$2 = suspendDuration$2;
            }
        };
    }

    public void akka$event$DeadLetterListener$$logDeadLetter(AllDeadLetters d, String doneMsg) {
        String string;
        String origin = this.isReal(d.sender()) ? new StringBuilder(6).append(" from ").append(d.sender()).toString() : "";
        Object unwrapped = WrappedMessage$.MODULE$.unwrap(d.message());
        String messageStr = unwrapped.getClass().getName();
        String wrappedIn = d.message() instanceof WrappedMessage ? new StringBuilder(14).append(" wrapped in [").append(d.message().getClass().getName()).append("]").toString() : "";
        AllDeadLetters allDeadLetters = d;
        if (allDeadLetters instanceof Dropped) {
            Dropped dropped = (Dropped)allDeadLetters;
            String destination = this.isReal(d.recipient()) ? new StringBuilder(4).append(" to ").append(d.recipient()).toString() : "";
            string = new StringBuilder(55).append("Message [").append(messageStr).append("]").append(wrappedIn).append(origin).append(destination).append(" was dropped. ").append(dropped.reason()).append(". ").append("[").append(this.count()).append("] dead letters encountered").append(doneMsg).append(". ").toString();
        } else if (allDeadLetters instanceof UnhandledMessage) {
            String destination = this.isReal(d.recipient()) ? new StringBuilder(4).append(" to ").append(d.recipient()).toString() : "";
            string = new StringBuilder(55).append("Message [").append(messageStr).append("]").append(wrappedIn).append(origin).append(destination).append(" was unhandled. ").append("[").append(this.count()).append("] dead letters encountered").append(doneMsg).append(". ").toString();
        } else {
            string = new StringBuilder(139).append("Message [").append(messageStr).append("]").append(wrappedIn).append(origin).append(" to ").append(d.recipient()).append(" was not delivered. ").append("[").append(this.count()).append("] dead letters encountered").append(doneMsg).append(". ").append("If this is not an expected behavior then ").append(d.recipient()).append(" may have terminated unexpectedly. ").toString();
        }
        String logMessage = string;
        this.eventStream().publish(Logging$Info$.MODULE$.apply(d.recipient().path().toString(), d.recipient().getClass(), new StringBuilder(139).append(logMessage).append("This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' ").append("and 'akka.log-dead-letters-during-shutdown'.").toString(), Logging$.MODULE$.emptyMDC(), ActorLogMarker$.MODULE$.deadLetter(messageStr)));
    }

    private boolean isReal(ActorRef snd) {
        return snd != ActorRef$.MODULE$.noSender() && snd != this.context().system().deadLetters() && !(snd instanceof DeadLetterActorRef);
    }

    public boolean akka$event$DeadLetterListener$$isWrappedSuppressed(AllDeadLetters d) {
        WrappedMessage wrappedMessage;
        Object object = d.message();
        return object instanceof WrappedMessage && (wrappedMessage = (WrappedMessage)object).message() instanceof DeadLetterSuppression;
    }

    public DeadLetterListener() {
        Actor.$init$(this);
        this.eventStream = this.context().system().eventStream();
        this.maxCount = this.context().system().settings().LogDeadLetters();
        this.isAlwaysLoggingDeadLetters = this.maxCount() == Integer.MAX_VALUE;
        this.count = 0;
        Statics.releaseFence();
    }
}

