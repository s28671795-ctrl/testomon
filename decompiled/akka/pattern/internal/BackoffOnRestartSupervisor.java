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
import akka.actor.SupervisorStrategy$Restart$;
import akka.actor.SupervisorStrategy$Stop$;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.pattern.BackoffReset;
import akka.pattern.BackoffSupervisor;
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

@ScalaSignature(bytes="\u0006\u0005\u0005ma!B\n\u0015\u0001YQ\u0002\u0002\u0003\u0018\u0001\u0005\u000b\u0007I\u0011\u0001\u0019\t\u0011Q\u0002!\u0011!Q\u0001\nEB\u0001\"\u000e\u0001\u0003\u0006\u0004%\tA\u000e\u0005\t\u0005\u0002\u0011\t\u0011)A\u0005o!A1\t\u0001B\u0001B\u0003%A\t\u0003\u0005M\u0001\t\u0005\t\u0015!\u0003E\u0011!i\u0005A!b\u0001\n\u0003q\u0005\u0002\u0003*\u0001\u0005\u0003\u0005\u000b\u0011B(\t\u0011M\u0003!\u0011!Q\u0001\nQC\u0001b\u0016\u0001\u0003\u0002\u0003\u0006I\u0001\u0017\u0005\t7\u0002\u0011\t\u0011)A\u00059\")q\f\u0001C\u0001A\"91\u000e\u0001b\u0001\n\u0003b\u0007BB7\u0001A\u0003%\u0001\fC\u0003o\u0001\u0011\u0005q\u000eC\u0003z\u0001\u0011\u0005!\u0010C\u0003|\u0001\u0011\u0005!\u0010C\u0003}\u0001\u0011EQP\u0001\u000eCC\u000e\\wN\u001a4P]J+7\u000f^1siN+\b/\u001a:wSN|'O\u0003\u0002\u0016-\u0005A\u0011N\u001c;fe:\fGN\u0003\u0002\u00181\u00059\u0001/\u0019;uKJt'\"A\r\u0002\t\u0005\\7.Y\n\u0006\u0001m\tse\u000b\t\u00039}i\u0011!\b\u0006\u0002=\u0005)1oY1mC&\u0011\u0001%\b\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\t*S\"A\u0012\u000b\u0005\u0011B\u0012!B1di>\u0014\u0018B\u0001\u0014$\u0005\u0015\t5\r^8s!\tA\u0013&D\u0001\u0017\u0013\tQcCA\u0007IC:$G.\u001a\"bG.|gM\u001a\t\u0003E1J!!L\u0012\u0003\u0019\u0005\u001bGo\u001c:M_\u001e<\u0017N\\4\u0002\u0015\rD\u0017\u000e\u001c3Qe>\u00048o\u0001\u0001\u0016\u0003E\u0002\"A\t\u001a\n\u0005M\u001a#!\u0002)s_B\u001c\u0018aC2iS2$\u0007K]8qg\u0002\n\u0011b\u00195jY\u0012t\u0015-\\3\u0016\u0003]\u0002\"\u0001O \u000f\u0005ej\u0004C\u0001\u001e\u001e\u001b\u0005Y$B\u0001\u001f0\u0003\u0019a$o\\8u}%\u0011a(H\u0001\u0007!J,G-\u001a4\n\u0005\u0001\u000b%AB*ue&twM\u0003\u0002?;\u0005Q1\r[5mI:\u000bW.\u001a\u0011\u0002\u00155LgNQ1dW>4g\r\u0005\u0002F\u00156\taI\u0003\u0002H\u0011\u0006AA-\u001e:bi&|gN\u0003\u0002J;\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005-3%A\u0004$j]&$X\rR;sCRLwN\\\u0001\u000b[\u0006D()Y2l_\u001a4\u0017!\u0002:fg\u0016$X#A(\u0011\u0005!\u0002\u0016BA)\u0017\u00051\u0011\u0015mY6pM\u001a\u0014Vm]3u\u0003\u0019\u0011Xm]3uA\u0005a!/\u00198e_64\u0015m\u0019;peB\u0011A$V\u0005\u0003-v\u0011a\u0001R8vE2,\u0017\u0001C:ue\u0006$XmZ=\u0011\u0005\tJ\u0016B\u0001.$\u0005Eye.\u001a$pe>sWm\u0015;sCR,w-_\u0001\u0015Q\u0006tG\r\\5oO^C\u0017\u000e\\3Ti>\u0004\b/\u001a3\u0011\u0005!j\u0016B\u00010\u0017\u0005QA\u0015M\u001c3mS:<w\u000b[5mKN#x\u000e\u001d9fI\u00061A(\u001b8jiz\"\u0012\"Y2eK\u001a<\u0007.\u001b6\u0011\u0005\t\u0004Q\"\u0001\u000b\t\u000b9b\u0001\u0019A\u0019\t\u000bUb\u0001\u0019A\u001c\t\u000b\rc\u0001\u0019\u0001#\t\u000b1c\u0001\u0019\u0001#\t\u000b5c\u0001\u0019A(\t\u000bMc\u0001\u0019\u0001+\t\u000b]c\u0001\u0019\u0001-\t\u000bmc\u0001\u0019\u0001/\u0002%M,\b/\u001a:wSN|'o\u0015;sCR,w-_\u000b\u00021\u0006\u00192/\u001e9feZL7o\u001c:TiJ\fG/Z4zA\u0005\u0001s/Y5u\u0007\"LG\u000e\u001a+fe6Lg.\u0019;fI\n+gm\u001c:f\u0005\u0006\u001c7n\u001c4g)\t\u0001H\u000f\u0005\u0002re6\t\u0001!\u0003\u0002tK\t9!+Z2fSZ,\u0007\"B;\u0010\u0001\u00041\u0018\u0001C2iS2$'+\u001a4\u0011\u0005\t:\u0018B\u0001=$\u0005!\t5\r^8s%\u00164\u0017\u0001D8o)\u0016\u0014X.\u001b8bi\u0016$W#\u00019\u0002\u000fI,7-Z5wK\u0006!\u0002.\u00198eY\u0016lUm]:bO\u0016$vn\u00115jY\u0012$2A`A\u0002!\tar0C\u0002\u0002\u0002u\u0011A!\u00168ji\"9\u0011Q\u0001\nA\u0002\u0005\u001d\u0011aA7tOB\u0019A$!\u0003\n\u0007\u0005-QDA\u0002B]fD3\u0001AA\b!\u0011\t\t\"a\u0006\u000e\u0005\u0005M!bAA\u000b1\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005e\u00111\u0003\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
@InternalApi
public class BackoffOnRestartSupervisor
implements Actor,
HandleBackoff,
ActorLogging {
    private final Props childProps;
    private final String childName;
    public final FiniteDuration akka$pattern$internal$BackoffOnRestartSupervisor$$minBackoff;
    public final FiniteDuration akka$pattern$internal$BackoffOnRestartSupervisor$$maxBackoff;
    private final BackoffReset reset;
    public final double akka$pattern$internal$BackoffOnRestartSupervisor$$randomFactor;
    public final OneForOneStrategy akka$pattern$internal$BackoffOnRestartSupervisor$$strategy;
    private final HandlingWhileStopped handlingWhileStopped;
    private final OneForOneStrategy supervisorStrategy;
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
    public OneForOneStrategy supervisorStrategy() {
        return this.supervisorStrategy;
    }

    public PartialFunction<Object, BoxedUnit> waitChildTerminatedBeforeBackoff(ActorRef childRef) {
        return new Serializable(this, childRef){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ BackoffOnRestartSupervisor $outer;
            private final ActorRef childRef$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Terminated) {
                    Terminated terminated = (Terminated)A1;
                    ActorRef actorRef2 = terminated.actor();
                    ActorRef actorRef3 = this.childRef$1;
                    ActorRef actorRef4 = actorRef2;
                    if (!(actorRef3 != null ? !((Object)actorRef3).equals(actorRef4) : actorRef4 != null)) {
                        this.$outer.context().become(this.$outer.receive());
                        this.$outer.child_$eq(None$.MODULE$);
                        FiniteDuration restartDelay = BackoffSupervisor$.MODULE$.calculateDelay(this.$outer.restartCount(), this.$outer.akka$pattern$internal$BackoffOnRestartSupervisor$$minBackoff, this.$outer.akka$pattern$internal$BackoffOnRestartSupervisor$$maxBackoff, this.$outer.akka$pattern$internal$BackoffOnRestartSupervisor$$randomFactor);
                        this.$outer.context().system().scheduler().scheduleOnce(restartDelay, this.$outer.self(), (Object)BackoffSupervisor$StartChild$.MODULE$, (ExecutionContext)this.$outer.context().dispatcher(), this.$outer.self());
                        this.$outer.restartCount_$eq(this.$outer.restartCount() + 1);
                        return (B1)BoxedUnit.UNIT;
                    }
                }
                if (BackoffSupervisor$StartChild$.MODULE$.equals(A1)) {
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof Terminated) {
                    Terminated terminated = (Terminated)object;
                    ActorRef actorRef2 = terminated.actor();
                    ActorRef actorRef3 = this.childRef$1;
                    ActorRef actorRef4 = actorRef2;
                    if (!(actorRef3 != null ? !((Object)actorRef3).equals(actorRef4) : actorRef4 != null)) {
                        return true;
                    }
                }
                return BackoffSupervisor$StartChild$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.childRef$1 = childRef$1;
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> onTerminated() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ BackoffOnRestartSupervisor $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Terminated) {
                    Terminated terminated = (Terminated)A1;
                    ActorRef c = terminated.actor();
                    this.$outer.log().debug(new StringBuilder(47).append("Terminating, because child [").append(c).append("] terminated itself").toString());
                    this.$outer.context().stop(this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof Terminated;
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
            return;
        }
        if (None$.MODULE$.equals(option)) {
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
            return;
        }
        throw new MatchError(option);
    }

    public BackoffOnRestartSupervisor(Props childProps, String childName, FiniteDuration minBackoff, FiniteDuration maxBackoff, BackoffReset reset, double randomFactor, OneForOneStrategy strategy, HandlingWhileStopped handlingWhileStopped) {
        this.childProps = childProps;
        this.childName = childName;
        this.akka$pattern$internal$BackoffOnRestartSupervisor$$minBackoff = minBackoff;
        this.akka$pattern$internal$BackoffOnRestartSupervisor$$maxBackoff = maxBackoff;
        this.reset = reset;
        this.akka$pattern$internal$BackoffOnRestartSupervisor$$randomFactor = randomFactor;
        this.akka$pattern$internal$BackoffOnRestartSupervisor$$strategy = strategy;
        this.handlingWhileStopped = handlingWhileStopped;
        Actor.$init$(this);
        HandleBackoff.$init$(this);
        ActorLogging.$init$(this);
        PartialFunction<Throwable, SupervisorStrategy.Directive> decider = Actor.supervisorStrategy$(this).decider();
        this.supervisorStrategy = new OneForOneStrategy(strategy.maxNrOfRetries(), strategy.withinTimeRange(), strategy.loggingEnabled(), (PartialFunction<Throwable, SupervisorStrategy.Directive>)((Object)new Serializable(this, decider){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ BackoffOnRestartSupervisor $outer;
            private final PartialFunction decider$1;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                SupervisorStrategy.Directive defaultDirective = (SupervisorStrategy.Directive)this.decider$1.applyOrElse(A1, (Function1<Object, SupervisorStrategy$Escalate$> & Serializable)(A1 x$1) -> SupervisorStrategy$Escalate$.MODULE$);
                SupervisorStrategy.Directive directive = (SupervisorStrategy.Directive)this.$outer.akka$pattern$internal$BackoffOnRestartSupervisor$$strategy.decider().applyOrElse(A1, (Function1<Object, SupervisorStrategy.Directive> & Serializable)(A1 x$2) -> defaultDirective);
                if (SupervisorStrategy$Restart$.MODULE$.equals(directive)) {
                    Object object;
                    int nextRestartCount = this.$outer.restartCount() + 1;
                    if (this.$outer.akka$pattern$internal$BackoffOnRestartSupervisor$$strategy.withinTimeRange().isFinite() && this.$outer.restartCount() == 0) {
                        FiniteDuration finiteWithinTimeRange = (FiniteDuration)this.$outer.akka$pattern$internal$BackoffOnRestartSupervisor$$strategy.withinTimeRange();
                        object = this.$outer.context().system().scheduler().scheduleOnce(finiteWithinTimeRange, this.$outer.self(), (Object)new BackoffSupervisor.ResetRestartCount(nextRestartCount), (ExecutionContext)this.$outer.context().dispatcher(), this.$outer.self());
                    } else {
                        object = BoxedUnit.UNIT;
                    }
                    ActorRef childRef = this.$outer.sender();
                    if (this.$outer.akka$pattern$internal$BackoffOnRestartSupervisor$$strategy.maxNrOfRetries() >= 0 && nextRestartCount > this.$outer.akka$pattern$internal$BackoffOnRestartSupervisor$$strategy.maxNrOfRetries()) {
                        this.$outer.log().debug("Terminating on restart #{} which exceeds max allowed restarts ({})", BoxesRunTime.boxToInteger(nextRestartCount), BoxesRunTime.boxToInteger(this.$outer.akka$pattern$internal$BackoffOnRestartSupervisor$$strategy.maxNrOfRetries()));
                        this.$outer.context().become(this.$outer.receive());
                        this.$outer.context().stop(this.$outer.self());
                    } else {
                        this.$outer.context().become(this.$outer.waitChildTerminatedBeforeBackoff(childRef).orElse(this.$outer.handleBackoff()));
                    }
                    return (B1)SupervisorStrategy$Stop$.MODULE$;
                }
                return (B1)directive;
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
        Statics.releaseFence();
    }
}

