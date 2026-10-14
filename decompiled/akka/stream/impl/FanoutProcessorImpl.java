/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.Cancellable;
import akka.actor.Props;
import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.StreamSubscriptionTimeoutTerminationMode;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$CancelTermination$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$NoopTermination$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$WarnTermination$;
import akka.stream.impl.ActorProcessorImpl;
import akka.stream.impl.ActorProcessorImpl$SubscriptionTimeout$;
import akka.stream.impl.FanoutOutputs;
import akka.stream.impl.FanoutProcessorImpl$;
import akka.stream.impl.TransferPhase;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.PartialFunction;
import scala.Tuple2;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005%qA\u0002\f\u0018\u0011\u0003YRD\u0002\u0004 /!\u00051\u0004\t\u0005\u0006O\u0005!\t!\u000b\u0005\u0006U\u0005!\ta\u000b\u0004\u0006?]\u00011\u0004\u0011\u0005\tg\u0011\u0011\t\u0011)A\u0005i!)q\u0005\u0002C\u0001\t\"Qq\t\u0002I\u0001\u0002\u0007\u0005\u000b\u0011\u0002%\t\u000fY#!\u0019!C\u0001/\"1\u0001\f\u0002Q\u0001\n-Cq!\u0017\u0003C\u0002\u0013\u0005!\f\u0003\u0004\\\t\u0001\u0006Ia\u0015\u0005\b9\u0012\u0011\r\u0011\"\u0001^\u0011\u00199G\u0001)A\u0005=\"9\u0001\u000e\u0002b\u0001\n\u0003J\u0007BB7\u0005A\u0003%!\u000eC\u0004o\t\t\u0007I\u0011A8\t\rM$\u0001\u0015!\u0003q\u0011\u0015!H\u0001\"\u0011v\u0011\u0015IH\u0001\"\u0011v\u0011\u0015QH\u0001\"\u0001v\u0011\u0015YH\u0001\"\u0001}\u0003M1\u0015M\\8viB\u0013xnY3tg>\u0014\u0018*\u001c9m\u0015\tA\u0012$\u0001\u0003j[Bd'B\u0001\u000e\u001c\u0003\u0019\u0019HO]3b[*\tA$\u0001\u0003bW.\f\u0007C\u0001\u0010\u0002\u001b\u00059\"a\u0005$b]>,H\u000f\u0015:pG\u0016\u001c8o\u001c:J[Bd7CA\u0001\"!\t\u0011S%D\u0001$\u0015\u0005!\u0013!B:dC2\f\u0017B\u0001\u0014$\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001\u001e\u0003\u0015\u0001(o\u001c9t)\ta#\u0007\u0005\u0002.a5\taF\u0003\u000207\u0005)\u0011m\u0019;pe&\u0011\u0011G\f\u0002\u0006!J|\u0007o\u001d\u0005\u0006g\r\u0001\r\u0001N\u0001\u000bCR$(/\u001b2vi\u0016\u001c\bCA\u001b7\u001b\u0005I\u0012BA\u001c\u001a\u0005)\tE\u000f\u001e:jEV$Xm\u001d\u0015\u0003\u0003e\u0002\"AO\u001f\u000e\u0003mR!\u0001P\u000e\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002?w\tY\u0011J\u001c;fe:\fG.\u00119jQ\t\u0001\u0011h\u0005\u0002\u0005\u0003B\u0011aDQ\u0005\u0003\u0007^\u0011!#Q2u_J\u0004&o\\2fgN|'/S7qYR\u0011QI\u0012\t\u0003=\u0011AQa\r\u0004A\u0002Q\n1\u0001\u001f\u00133!\u0011\u0011\u0013jS*\n\u0005)\u001b#A\u0002+va2,'\u0007\u0005\u0002M#6\tQJ\u0003\u0002O\u001f\u0006AA-\u001e:bi&|gN\u0003\u0002QG\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005Ik%A\u0004$j]&$X\rR;sCRLwN\u001c\t\u0003kQK!!V\r\u0003QM#(/Z1n'V\u00147o\u0019:jaRLwN\u001c+j[\u0016|W\u000f\u001e+fe6Lg.\u0019;j_:lu\u000eZ3\u0002\u000fQLW.Z8viV\t1*\u0001\u0005uS6,w.\u001e;!\u0003-!\u0018.\\3pkRlu\u000eZ3\u0016\u0003M\u000bA\u0002^5nK>,H/T8eK\u0002\nA\u0002^5nK>,H\u000fV5nKJ,\u0012A\u0018\t\u0004?\n$W\"\u00011\u000b\u0005\u0005\\\u0012\u0001B;uS2L!a\u00191\u0003\u0013=\u0003H/[8o-\u0006d\u0007CA\u0017f\u0013\t1gFA\u0006DC:\u001cW\r\u001c7bE2,\u0017!\u0004;j[\u0016|W\u000f\u001e+j[\u0016\u0014\b%\u0001\bqe&l\u0017M]=PkR\u0004X\u000f^:\u0016\u0003)\u0004\"AH6\n\u00051<\"!\u0004$b]>,HoT;uaV$8/A\bqe&l\u0017M]=PkR\u0004X\u000f^:!\u0003\u001d\u0011XO\u001c8j]\u001e,\u0012\u0001\u001d\t\u0003=EL!A]\f\u0003\u001bQ\u0013\u0018M\\:gKJ\u0004\u0006.Y:f\u0003!\u0011XO\u001c8j]\u001e\u0004\u0013\u0001\u00049v[B4\u0015N\\5tQ\u0016$G#\u0001<\u0011\u0005\t:\u0018B\u0001=$\u0005\u0011)f.\u001b;\u0002\u0011A|7\u000f^*u_B\f!\"\u00194uKJ4E.^:i\u0003I\u0019XO\u0019+j[\u0016|W\u000f\u001e%b]\u0012d\u0017N\\4\u0016\u0003u\u0004\"A`@\u000e\u0003\u0011IA!!\u0001\u0002\u0004\t9!+Z2fSZ,\u0017bAA\u0003]\t)\u0011i\u0019;pe\"\u0012A!\u000f")
@InternalApi
public class FanoutProcessorImpl
extends ActorProcessorImpl {
    private final /* synthetic */ Tuple2 x$2;
    private final FiniteDuration timeout;
    private final StreamSubscriptionTimeoutTerminationMode timeoutMode;
    private final Cancellable timeoutTimer;
    private final FanoutOutputs primaryOutputs;
    private final TransferPhase running;

    public static Props props(Attributes attributes) {
        return FanoutProcessorImpl$.MODULE$.props(attributes);
    }

    public FiniteDuration timeout() {
        return this.timeout;
    }

    public StreamSubscriptionTimeoutTerminationMode timeoutMode() {
        return this.timeoutMode;
    }

    public Cancellable timeoutTimer() {
        return this.timeoutTimer;
    }

    @Override
    public FanoutOutputs primaryOutputs() {
        return this.primaryOutputs;
    }

    public TransferPhase running() {
        return this.running;
    }

    @Override
    public void pumpFinished() {
        this.primaryInputs().cancel();
        this.primaryOutputs().complete();
    }

    @Override
    public void postStop() {
        super.postStop();
        Cancellable cancellable = this.timeoutTimer();
        Cancellable cancellable2 = OptionVal$Some$.MODULE$.unapply(cancellable);
        if (!OptionVal$.MODULE$.isEmpty$extension(cancellable2)) {
            Cancellable timer = OptionVal$.MODULE$.get$extension(cancellable2);
            timer.cancel();
            return;
        }
    }

    public void afterFlush() {
        this.context().stop(this.self());
    }

    @Override
    public PartialFunction<Object, BoxedUnit> subTimeoutHandling() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ FanoutProcessorImpl $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (ActorProcessorImpl$SubscriptionTimeout$.MODULE$.equals(A1)) {
                    if (!this.$outer.primaryOutputs().subscribed()) {
                        StreamSubscriptionTimeoutTerminationMode streamSubscriptionTimeoutTerminationMode = this.$outer.timeoutMode();
                        if (StreamSubscriptionTimeoutTerminationMode$CancelTermination$.MODULE$.equals(streamSubscriptionTimeoutTerminationMode)) {
                            this.$outer.primaryInputs().cancel();
                            this.$outer.context().stop(this.$outer.self());
                        } else if (StreamSubscriptionTimeoutTerminationMode$WarnTermination$.MODULE$.equals(streamSubscriptionTimeoutTerminationMode)) {
                            this.$outer.log().warning("Subscription timeout for {}", this.$outer);
                        } else if (StreamSubscriptionTimeoutTerminationMode$NoopTermination$.MODULE$.equals(streamSubscriptionTimeoutTerminationMode)) {
                        } else {
                            throw new MatchError(streamSubscriptionTimeoutTerminationMode);
                        }
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return ActorProcessorImpl$SubscriptionTimeout$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public FanoutProcessorImpl(Attributes attributes) {
        super(attributes);
        Cancellable cancellable;
        ActorAttributes.StreamSubscriptionTimeout streamSubscriptionTimeout = (ActorAttributes.StreamSubscriptionTimeout)attributes.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.StreamSubscriptionTimeout.class));
        if (streamSubscriptionTimeout == null) {
            throw new MatchError(streamSubscriptionTimeout);
        }
        FiniteDuration timeout2 = streamSubscriptionTimeout.timeout();
        StreamSubscriptionTimeoutTerminationMode timeoutMode = streamSubscriptionTimeout.mode();
        this.x$2 = new Tuple2<FiniteDuration, StreamSubscriptionTimeoutTerminationMode>(timeout2, timeoutMode);
        this.timeout = (FiniteDuration)this.x$2._1();
        this.timeoutMode = (StreamSubscriptionTimeoutTerminationMode)this.x$2._2();
        StreamSubscriptionTimeoutTerminationMode streamSubscriptionTimeoutTerminationMode = this.timeoutMode();
        StreamSubscriptionTimeoutTerminationMode streamSubscriptionTimeoutTerminationMode2 = StreamSubscriptionTimeoutTerminationMode$.MODULE$.noop();
        if (streamSubscriptionTimeoutTerminationMode == null ? streamSubscriptionTimeoutTerminationMode2 != null : !streamSubscriptionTimeoutTerminationMode.equals(streamSubscriptionTimeoutTerminationMode2)) {
            cancellable = OptionVal$Some$.MODULE$.apply(this.context().system().scheduler().scheduleOnce(this.timeout(), this.self(), (Object)ActorProcessorImpl$SubscriptionTimeout$.MODULE$, (ExecutionContext)this.context().dispatcher(), this.self()));
        } else {
            OptionVal$.MODULE$.None();
            cancellable = null;
        }
        this.timeoutTimer = cancellable;
        Attributes.InputBuffer inputBuffer = (Attributes.InputBuffer)attributes.mandatoryAttribute(ClassTag$.MODULE$.apply(Attributes.InputBuffer.class));
        this.primaryOutputs = new FanoutOutputs(this, inputBuffer){
            private final /* synthetic */ FanoutProcessorImpl $outer;

            public void afterShutdown() {
                this.$outer.afterFlush();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super(inputBuffer$1.max(), inputBuffer$1.initial(), $outer.self(), $outer);
            }
        };
        this.running = new TransferPhase(this.primaryInputs().NeedsInput().$amp$amp(this.primaryOutputs().NeedsDemand()), () -> this.primaryOutputs().enqueueOutputElement(this.primaryInputs().dequeueInputElement()));
        this.initialPhase(1, this.running());
    }
}

