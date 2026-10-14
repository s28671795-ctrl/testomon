/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern.internal;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy$Escalate$;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.pattern.BackoffReset;
import akka.pattern.BackoffSupervisor$;
import akka.pattern.BackoffSupervisor$StartChild$;
import akka.pattern.ForwardDeathLetters$;
import akka.pattern.ForwardTo;
import akka.pattern.HandleBackoff;
import akka.pattern.HandlingWhileStopped;
import akka.pattern.ReplyWith;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\rb!B\n\u0015\u0001YQ\u0002\u0002\u0003\u0018\u0001\u0005\u000b\u0007I\u0011\u0001\u0019\t\u0011Q\u0002!\u0011!Q\u0001\nEB\u0001\"\u000e\u0001\u0003\u0006\u0004%\tA\u000e\u0005\t\u0005\u0002\u0011\t\u0011)A\u0005o!A1\t\u0001B\u0001B\u0003%A\t\u0003\u0005M\u0001\t\u0005\t\u0015!\u0003E\u0011!i\u0005A!b\u0001\n\u0003q\u0005\u0002\u0003*\u0001\u0005\u0003\u0005\u000b\u0011B(\t\u0011M\u0003!\u0011!Q\u0001\nQC\u0001b\u0016\u0001\u0003\u0002\u0003\u0006I\u0001\u0017\u0005\t7\u0002\u0011\t\u0011)A\u00059\"Aq\f\u0001B\u0001B\u0003%\u0001\rC\u0003m\u0001\u0011\u0005Q\u000eC\u0004z\u0001\t\u0007I\u0011\t>\t\rm\u0004\u0001\u0015!\u0003Y\u0011\u0015a\b\u0001\"\u0001~\u0011\u0019\t)\u0001\u0001C\u0001{\"9\u0011q\u0001\u0001\u0005\u0012\u0005%!a\u0006\"bG.|gMZ(o'R|\u0007oU;qKJ4\u0018n]8s\u0015\t)b#\u0001\u0005j]R,'O\\1m\u0015\t9\u0002$A\u0004qCR$XM\u001d8\u000b\u0003e\tA!Y6lCN)\u0001aG\u0011(WA\u0011AdH\u0007\u0002;)\ta$A\u0003tG\u0006d\u0017-\u0003\u0002!;\t1\u0011I\\=SK\u001a\u0004\"AI\u0013\u000e\u0003\rR!\u0001\n\r\u0002\u000b\u0005\u001cGo\u001c:\n\u0005\u0019\u001a#!B!di>\u0014\bC\u0001\u0015*\u001b\u00051\u0012B\u0001\u0016\u0017\u00055A\u0015M\u001c3mK\n\u000b7m[8gMB\u0011!\u0005L\u0005\u0003[\r\u0012A\"Q2u_JdunZ4j]\u001e\f!b\u00195jY\u0012\u0004&o\u001c9t\u0007\u0001)\u0012!\r\t\u0003EIJ!aM\u0012\u0003\u000bA\u0013x\u000e]:\u0002\u0017\rD\u0017\u000e\u001c3Qe>\u00048\u000fI\u0001\nG\"LG\u000e\u001a(b[\u0016,\u0012a\u000e\t\u0003q}r!!O\u001f\u0011\u0005ijR\"A\u001e\u000b\u0005qz\u0013A\u0002\u001fs_>$h(\u0003\u0002?;\u00051\u0001K]3eK\u001aL!\u0001Q!\u0003\rM#(/\u001b8h\u0015\tqT$\u0001\u0006dQ&dGMT1nK\u0002\n!\"\\5o\u0005\u0006\u001c7n\u001c4g!\t)%*D\u0001G\u0015\t9\u0005*\u0001\u0005ekJ\fG/[8o\u0015\tIU$\u0001\u0006d_:\u001cWO\u001d:f]RL!a\u0013$\u0003\u001d\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]\u0006QQ.\u0019=CC\u000e\\wN\u001a4\u0002\u000bI,7/\u001a;\u0016\u0003=\u0003\"\u0001\u000b)\n\u0005E3\"\u0001\u0004\"bG.|gM\u001a*fg\u0016$\u0018A\u0002:fg\u0016$\b%\u0001\u0007sC:$w.\u001c$bGR|'\u000f\u0005\u0002\u001d+&\u0011a+\b\u0002\u0007\t>,(\r\\3\u0002\u0011M$(/\u0019;fOf\u0004\"AI-\n\u0005i\u001b#AE*va\u0016\u0014h/[:peN#(/\u0019;fOf\fA\u0003[1oI2LgnZ,iS2,7\u000b^8qa\u0016$\u0007C\u0001\u0015^\u0013\tqfC\u0001\u000bIC:$G.\u001b8h/\"LG.Z*u_B\u0004X\rZ\u0001\u0011M&t\u0017\r\\*u_BlUm]:bO\u0016\u00042\u0001H1d\u0013\t\u0011WD\u0001\u0004PaRLwN\u001c\t\u00059\u00114\u0017.\u0003\u0002f;\tIa)\u001e8di&|g.\r\t\u00039\u001dL!\u0001[\u000f\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u001dU&\u00111.\b\u0002\b\u0005>|G.Z1o\u0003\u0019a\u0014N\\5u}QQa\u000e]9sgR,ho\u001e=\u0011\u0005=\u0004Q\"\u0001\u000b\t\u000b9j\u0001\u0019A\u0019\t\u000bUj\u0001\u0019A\u001c\t\u000b\rk\u0001\u0019\u0001#\t\u000b1k\u0001\u0019\u0001#\t\u000b5k\u0001\u0019A(\t\u000bMk\u0001\u0019\u0001+\t\u000b]k\u0001\u0019\u0001-\t\u000bmk\u0001\u0019\u0001/\t\u000b}k\u0001\u0019\u00011\u0002%M,\b/\u001a:wSN|'o\u0015;sCR,w-_\u000b\u00021\u0006\u00192/\u001e9feZL7o\u001c:TiJ\fG/Z4zA\u0005aqN\u001c+fe6Lg.\u0019;fIV\ta\u0010E\u0002\u0000\u0003\u0003i\u0011\u0001A\u0005\u0004\u0003\u0007)#a\u0002*fG\u0016Lg/Z\u0001\be\u0016\u001cW-\u001b<f\u0003QA\u0017M\u001c3mK6+7o]1hKR{7\t[5mIR!\u00111BA\t!\ra\u0012QB\u0005\u0004\u0003\u001fi\"\u0001B+oSRDa!a\u0005\u0013\u0001\u00041\u0017aA7tO\"\u001a\u0001!a\u0006\u0011\t\u0005e\u0011qD\u0007\u0003\u00037Q1!!\b\u0019\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003C\tYBA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public class BackoffOnStopSupervisor
implements Actor,
HandleBackoff,
ActorLogging {
    private final Props childProps;
    private final String childName;
    public final FiniteDuration akka$pattern$internal$BackoffOnStopSupervisor$$minBackoff;
    public final FiniteDuration akka$pattern$internal$BackoffOnStopSupervisor$$maxBackoff;
    private final BackoffReset reset;
    public final double akka$pattern$internal$BackoffOnStopSupervisor$$randomFactor;
    public final SupervisorStrategy akka$pattern$internal$BackoffOnStopSupervisor$$strategy;
    private final HandlingWhileStopped handlingWhileStopped;
    private final Option<Function1<Object, Object>> finalStopMessage;
    private final SupervisorStrategy supervisorStrategy;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private Option<ActorRef> child;
    private int restartCount;
    private boolean finalStopMessageReceived;
    private ActorContext context;
    private ActorRef self;

    @Override
    public LoggingAdapter log() {
        return ActorLogging.log$(this);
    }

    @Override
    public void preStart() {
        HandleBackoff.preStart$(this);
    }

    @Override
    public void startChild() {
        HandleBackoff.startChild$(this);
    }

    @Override
    public PartialFunction<Object, BoxedUnit> handleBackoff() {
        return HandleBackoff.handleBackoff$(this);
    }

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
    public void postStop() throws Exception {
        Actor.postStop$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Actor.postRestart$(this, reason);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public LoggingAdapter akka$actor$ActorLogging$$_log() {
        return this.akka$actor$ActorLogging$$_log;
    }

    @Override
    public void akka$actor$ActorLogging$$_log_$eq(LoggingAdapter x$1) {
        this.akka$actor$ActorLogging$$_log = x$1;
    }

    @Override
    public Option<ActorRef> child() {
        return this.child;
    }

    @Override
    public void child_$eq(Option<ActorRef> x$1) {
        this.child = x$1;
    }

    @Override
    public int restartCount() {
        return this.restartCount;
    }

    @Override
    public void restartCount_$eq(int x$1) {
        this.restartCount = x$1;
    }

    @Override
    public boolean finalStopMessageReceived() {
        return this.finalStopMessageReceived;
    }

    @Override
    public void finalStopMessageReceived_$eq(boolean x$1) {
        this.finalStopMessageReceived = x$1;
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

    @Override
    public Props childProps() {
        return this.childProps;
    }

    @Override
    public String childName() {
        return this.childName;
    }

    @Override
    public BackoffReset reset() {
        return this.reset;
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return this.supervisorStrategy;
    }

    public PartialFunction<Object, BoxedUnit> onTerminated() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ BackoffOnStopSupervisor $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Terminated) {
                    Terminated terminated = (Terminated)A1;
                    ActorRef ref = terminated.actor();
                    if (this.$outer.child().contains(ref)) {
                        int n;
                        this.$outer.child_$eq(None$.MODULE$);
                        if (this.$outer.finalStopMessageReceived()) {
                            this.$outer.context().stop(this.$outer.self());
                            return (B1)BoxedUnit.UNIT;
                        }
                        SupervisorStrategy supervisorStrategy = this.$outer.akka$pattern$internal$BackoffOnStopSupervisor$$strategy;
                        if (supervisorStrategy instanceof OneForOneStrategy) {
                            OneForOneStrategy oneForOneStrategy = (OneForOneStrategy)supervisorStrategy;
                            n = oneForOneStrategy.maxNrOfRetries();
                        } else {
                            n = -1;
                        }
                        int maxNrOfRetries = n;
                        int nextRestartCount = this.$outer.restartCount() + 1;
                        if (maxNrOfRetries == -1 || nextRestartCount <= maxNrOfRetries) {
                            FiniteDuration restartDelay = BackoffSupervisor$.MODULE$.calculateDelay(this.$outer.restartCount(), this.$outer.akka$pattern$internal$BackoffOnStopSupervisor$$minBackoff, this.$outer.akka$pattern$internal$BackoffOnStopSupervisor$$maxBackoff, this.$outer.akka$pattern$internal$BackoffOnStopSupervisor$$randomFactor);
                            this.$outer.context().system().scheduler().scheduleOnce(restartDelay, this.$outer.self(), (Object)BackoffSupervisor$StartChild$.MODULE$, (ExecutionContext)this.$outer.context().dispatcher(), this.$outer.self());
                            this.$outer.restartCount_$eq(nextRestartCount);
                            return (B1)BoxedUnit.UNIT;
                        }
                        this.$outer.log().debug("Terminating on restart #{} which exceeds max allowed restarts ({})", BoxesRunTime.boxToInteger(nextRestartCount), BoxesRunTime.boxToInteger(maxNrOfRetries));
                        this.$outer.context().stop(this.$outer.self());
                        return (B1)BoxedUnit.UNIT;
                    }
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof Terminated) {
                    Terminated terminated = (Terminated)object;
                    ActorRef ref = terminated.actor();
                    if (this.$outer.child().contains(ref)) {
                        return true;
                    }
                }
                return false;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return this.onTerminated().orElse(this.handleBackoff());
    }

    @Override
    public void handleMessageToChild(Object msg) {
        Option<ActorRef> option = this.child();
        if (option instanceof Some) {
            Some some = (Some)option;
            ActorRef c = (ActorRef)some.value();
            c.forward(msg, this.context());
            if (!this.finalStopMessageReceived()) {
                Option<Function1<Object, Object>> option2 = this.finalStopMessage;
                if (option2 instanceof Some) {
                    Some some2 = (Some)option2;
                    Function1 fsm = (Function1)some2.value();
                    this.finalStopMessageReceived_$eq(BoxesRunTime.unboxToBoolean(fsm.apply(msg)));
                } else if (None$.MODULE$.equals(option2)) {
                } else {
                    throw new MatchError(option2);
                }
                return;
            }
            return;
        }
        if (None$.MODULE$.equals(option)) {
            Some some;
            Function1 fsm;
            Option<Function1<Object, Object>> option3 = this.finalStopMessage;
            if (option3 instanceof Some && BoxesRunTime.unboxToBoolean((fsm = (Function1)(some = (Some)option3).value()).apply(msg))) {
                this.context().stop(this.self());
            } else {
                HandlingWhileStopped handlingWhileStopped = this.handlingWhileStopped;
                if (ForwardDeathLetters$.MODULE$.equals(handlingWhileStopped)) {
                    this.context().system().deadLetters().forward(msg, this.context());
                } else if (handlingWhileStopped instanceof ForwardTo) {
                    ForwardTo forwardTo = (ForwardTo)handlingWhileStopped;
                    ActorRef h2 = forwardTo.handler();
                    h2.forward(msg, this.context());
                } else if (handlingWhileStopped instanceof ReplyWith) {
                    ReplyWith replyWith = (ReplyWith)handlingWhileStopped;
                    Object r = replyWith.msg();
                    this.sender().$bang(r, this.self());
                } else {
                    throw new MatchError(handlingWhileStopped);
                }
            }
            return;
        }
        throw new MatchError(option);
    }

    public BackoffOnStopSupervisor(Props childProps, String childName, FiniteDuration minBackoff, FiniteDuration maxBackoff, BackoffReset reset, double randomFactor, SupervisorStrategy strategy, HandlingWhileStopped handlingWhileStopped, Option<Function1<Object, Object>> finalStopMessage) {
        SupervisorStrategy supervisorStrategy;
        this.childProps = childProps;
        this.childName = childName;
        this.akka$pattern$internal$BackoffOnStopSupervisor$$minBackoff = minBackoff;
        this.akka$pattern$internal$BackoffOnStopSupervisor$$maxBackoff = maxBackoff;
        this.reset = reset;
        this.akka$pattern$internal$BackoffOnStopSupervisor$$randomFactor = randomFactor;
        this.akka$pattern$internal$BackoffOnStopSupervisor$$strategy = strategy;
        this.handlingWhileStopped = handlingWhileStopped;
        this.finalStopMessage = finalStopMessage;
        Actor.$init$(this);
        HandleBackoff.$init$(this);
        ActorLogging.$init$(this);
        PartialFunction<Throwable, SupervisorStrategy.Directive> decider = Actor.supervisorStrategy$(this).decider();
        SupervisorStrategy supervisorStrategy2 = strategy;
        if (supervisorStrategy2 instanceof OneForOneStrategy) {
            OneForOneStrategy oneForOneStrategy = (OneForOneStrategy)supervisorStrategy2;
            supervisorStrategy = new OneForOneStrategy(oneForOneStrategy.maxNrOfRetries(), oneForOneStrategy.withinTimeRange(), oneForOneStrategy.loggingEnabled(), (PartialFunction<Throwable, SupervisorStrategy.Directive>)((Object)new Serializable(this, decider){
                private static final long serialVersionUID = 0L;
                private final /* synthetic */ BackoffOnStopSupervisor $outer;
                private final PartialFunction decider$1;

                public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    A1 A1 = x1;
                    SupervisorStrategy.Directive defaultDirective = (SupervisorStrategy.Directive)this.decider$1.applyOrElse(A1, (Function1<Object, SupervisorStrategy$Escalate$> & Serializable)(A1 x$1) -> SupervisorStrategy$Escalate$.MODULE$);
                    return this.$outer.akka$pattern$internal$BackoffOnStopSupervisor$$strategy.decider().applyOrElse(A1, (Function1<Object, SupervisorStrategy.Directive> & Serializable)(A1 x$2) -> defaultDirective);
                }

                public final boolean isDefinedAt(Throwable x1) {
                    Throwable throwable = x1;
                    return true;
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.decider$1 = decider$1;
                }

                private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                    return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1(java.lang.Object ), $anonfun$applyOrElse$2(akka.actor.SupervisorStrategy$Directive java.lang.Object )}, serializedLambda);
                }
            }));
        } else {
            supervisorStrategy = supervisorStrategy2;
        }
        this.supervisorStrategy = supervisorStrategy;
        Statics.releaseFence();
    }
}

