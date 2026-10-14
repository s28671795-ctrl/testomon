/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.streamref;

import akka.Done;
import akka.Done$;
import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.InvalidPartnerActorException;
import akka.stream.Materializer;
import akka.stream.RemoteStreamRefActorTerminatedException;
import akka.stream.SinkShape;
import akka.stream.SinkShape$;
import akka.stream.SourceRef;
import akka.stream.StreamRefAttributes;
import akka.stream.StreamRefSettings;
import akka.stream.StreamRefSubscriptionTimeoutException;
import akka.stream.SubscriptionWithCancelException$NoMoreElementsNeeded$;
import akka.stream.TargetRefNotInitializedYetException;
import akka.stream.impl.streamref.SinkRefStageImpl$;
import akka.stream.impl.streamref.SourceRefImpl;
import akka.stream.impl.streamref.StreamRefsMaster;
import akka.stream.impl.streamref.StreamRefsMaster$;
import akka.stream.impl.streamref.StreamRefsProtocol;
import akka.stream.impl.streamref.StreamRefsProtocol$Ack$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import akka.stream.stage.StageLogging;
import akka.util.OptionVal;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import akka.util.PrettyDuration$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.MatchError;
import scala.Tuple2;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u0005UrA\u0002\t\u0012\u0011\u0003)\u0012D\u0002\u0004\u001c#!\u0005Q\u0003\b\u0005\u0006G\u0005!\t!\n\u0004\bM\u0005\u0001\n1%\u000b(\u0011\u0015A3A\"\u0001*\r\u0015Y\u0012CA\u000bG\u0011!\u0001WA!b\u0001\n\u0003\t\u0007\u0002\u00035\u0006\u0005\u0003\u0005\u000b\u0011\u00022\t\r\r*A\u0011A\fj\u0011\u001daWA1A\u0005\u00025Da!]\u0003!\u0002\u0013q\u0007\"\u0002:\u0006\t\u0003\u001a\b\"\u0002;\u0006\t\u0013)\bbBA\u0002\u000b\u0011\u0005\u0013Q\u0001\u0005\t\u0003\u0007)A\u0011I\f\u0002\u001e!9\u00111F\u0003\u0005B\u00055\u0012\u0001E*j].\u0014VMZ*uC\u001e,\u0017*\u001c9m\u0015\t\u00112#A\u0005tiJ,\u0017-\u001c:fM*\u0011A#F\u0001\u0005S6\u0004HN\u0003\u0002\u0017/\u000511\u000f\u001e:fC6T\u0011\u0001G\u0001\u0005C.\\\u0017\r\u0005\u0002\u001b\u00035\t\u0011C\u0001\tTS:\\'+\u001a4Ti\u0006<W-S7qYN\u0011\u0011!\b\t\u0003=\u0005j\u0011a\b\u0006\u0002A\u0005)1oY1mC&\u0011!e\b\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012!\u0007\u0002\u000e\u0003\u000e$xN\u001d*fMN#\u0018mZ3\u0014\u0005\ri\u0012a\u0001:fMV\t!\u0006\u0005\u0002,]5\tAF\u0003\u0002./\u0005)\u0011m\u0019;pe&\u0011q\u0006\f\u0002\t\u0003\u000e$xN\u001d*fM&\u00121!\r\u0004\u0005e\r\u00011GA\u0007=Y>\u001c\u0017\r\u001c\u0011dQ&dGMP\n\u0004cQb\u0004CA\u001b;\u001b\u00051$BA\u001c9\u0003\u0011a\u0017M\\4\u000b\u0003e\nAA[1wC&\u00111H\u000e\u0002\u0007\u001f\nTWm\u0019;\u0011\u0005u\u001aQ\"A\u0001)\u0005\u0005y\u0004C\u0001!D\u001b\u0005\t%B\u0001\"\u0018\u0003)\tgN\\8uCRLwN\\\u0005\u0003\t\u0006\u00131\"\u00138uKJt\u0017\r\\!qS\"\u0012\u0001aP\u000b\u0003\u000fR\u001b\"!\u0002%\u0011\t%ce*X\u0007\u0002\u0015*\u00111*F\u0001\u0006gR\fw-Z\u0005\u0003\u001b*\u0013qd\u0012:ba\"\u001cF/Y4f/&$\b.T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f!\ry\u0005KU\u0007\u0002+%\u0011\u0011+\u0006\u0002\n'&t7n\u00155ba\u0016\u0004\"a\u0015+\r\u0001\u0011)Q+\u0002b\u0001-\n\u0011\u0011J\\\t\u0003/j\u0003\"A\b-\n\u0005e{\"a\u0002(pi\"Lgn\u001a\t\u0003=mK!\u0001X\u0010\u0003\u0007\u0005s\u0017\u0010E\u0002P=JK!aX\u000b\u0003\u0013M{WO]2f%\u00164\u0017!E5oSRL\u0017\r\u001c)beRtWM\u001d*fMV\t!\rE\u0002dM*j\u0011\u0001\u001a\u0006\u0003K^\tA!\u001e;jY&\u0011q\r\u001a\u0002\n\u001fB$\u0018n\u001c8WC2\f!#\u001b8ji&\fG\u000eU1si:,'OU3gAQ\u0011!n\u001b\t\u00045\u0015\u0011\u0006\"\u00021\t\u0001\u0004\u0011\u0017AA5o+\u0005q\u0007cA(p%&\u0011\u0001/\u0006\u0002\u0006\u0013:dW\r^\u0001\u0004S:\u0004\u0013!B:iCB,W#\u0001(\u0002\u001d%t\u0017\u000e^5bYJ+gMT1nKV\ta\u000f\u0005\u0002x}:\u0011\u0001\u0010 \t\u0003s~i\u0011A\u001f\u0006\u0003w\u0012\na\u0001\u0010:p_Rt\u0014BA? \u0003\u0019\u0001&/\u001a3fM&\u0019q0!\u0001\u0003\rM#(/\u001b8h\u0015\tix$A\u0010de\u0016\fG/\u001a'pO&\u001c\u0017I\u001c3NCR,'/[1mSj,GMV1mk\u0016$B!a\u0002\u0002\u0014A1a$!\u0003\u0002\u000euK1!a\u0003 \u0005\u0019!V\u000f\u001d7feA\u0019\u0011*a\u0004\n\u0007\u0005E!JA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u001d\t)\"\u0004a\u0001\u0003/\t1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\u00042aTA\r\u0013\r\tY\"\u0006\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001cHCBA\u0004\u0003?\t\t\u0003C\u0004\u0002\u00169\u0001\r!a\u0006\t\u000f\u0005\rb\u00021\u0001\u0002&\u0005\tR-Y4fe6\u000bG/\u001a:jC2L'0\u001a:\u0011\u0007=\u000b9#C\u0002\u0002*U\u0011A\"T1uKJL\u0017\r\\5{KJ\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003_\u00012!NA\u0019\u0013\tyh\u0007\u000b\u0002\u0006\u007f\u0001")
@InternalApi
public final class SinkRefStageImpl<In>
extends GraphStageWithMaterializedValue<SinkShape<In>, SourceRef<In>> {
    private final ActorRef initialPartnerRef;
    private final Inlet<In> in;

    public ActorRef initialPartnerRef() {
        return this.initialPartnerRef;
    }

    public Inlet<In> in() {
        return this.in;
    }

    @Override
    public SinkShape<In> shape() {
        return SinkShape$.MODULE$.of(this.in());
    }

    private String initialRefName() {
        ActorRef actorRef2 = this.initialPartnerRef();
        ActorRef actorRef3 = OptionVal$Some$.MODULE$.unapply(actorRef2);
        if (!OptionVal$.MODULE$.isEmpty$extension(actorRef3)) {
            ActorRef ref = OptionVal$.MODULE$.get$extension(actorRef3);
            return ref.toString();
        }
        return "<no-initial-ref>";
    }

    @Override
    public Tuple2<GraphStageLogic, SourceRef<In>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        throw new IllegalStateException("Not supported");
    }

    @Override
    public Tuple2<GraphStageLogic, SourceRef<In>> createLogicAndMaterializedValue(Attributes inheritedAttributes, Materializer eagerMaterializer) {
        StageLogging logic = new StageLogging(this, eagerMaterializer, inheritedAttributes){
            private final StreamRefsMaster streamRefsMaster;
            private final StreamRefAttributes.SubscriptionTimeout subscriptionTimeout;
            private final String stageActorName;
            private final GraphStageLogic.StageActor self;
            private final ActorRef ref;
            private ActorRef partnerRef;
            private final String SubscriptionTimeoutTimerKey;
            private long remoteCumulativeDemandReceived;
            private long remoteCumulativeDemandConsumed;
            private Try<Done> completedBeforeRemoteConnected;
            private Try<Done> finishedWithAwaitingPartnerTermination;
            private LoggingAdapter akka$stream$stage$StageLogging$$_log;
            private final /* synthetic */ SinkRefStageImpl $outer;

            public LoggingAdapter log() {
                return StageLogging.log$(this);
            }

            public LoggingAdapter akka$stream$stage$StageLogging$$_log() {
                return this.akka$stream$stage$StageLogging$$_log;
            }

            public void akka$stream$stage$StageLogging$$_log_$eq(LoggingAdapter x$1) {
                this.akka$stream$stage$StageLogging$$_log = x$1;
            }

            public Class<?> logSource() {
                return SinkRefStageImpl.class;
            }

            public String stageActorName() {
                return this.stageActorName;
            }

            public ActorRef ref() {
                return this.ref;
            }

            public ActorRef selfSender() {
                return this.ref();
            }

            private ActorRef partnerRef() {
                return this.partnerRef;
            }

            private void partnerRef_$eq(ActorRef x$1) {
                this.partnerRef = x$1;
            }

            private ActorRef getPartnerRef() {
                ActorRef actorRef2 = this.partnerRef();
                ActorRef actorRef3 = OptionVal$Some$.MODULE$.unapply(actorRef2);
                if (!OptionVal$.MODULE$.isEmpty$extension(actorRef3)) {
                    ActorRef ref = OptionVal$.MODULE$.get$extension(actorRef3);
                    return ref;
                }
                throw new TargetRefNotInitializedYetException();
            }

            public String SubscriptionTimeoutTimerKey() {
                return this.SubscriptionTimeoutTimerKey;
            }

            private long remoteCumulativeDemandReceived() {
                return this.remoteCumulativeDemandReceived;
            }

            private void remoteCumulativeDemandReceived_$eq(long x$1) {
                this.remoteCumulativeDemandReceived = x$1;
            }

            private long remoteCumulativeDemandConsumed() {
                return this.remoteCumulativeDemandConsumed;
            }

            private void remoteCumulativeDemandConsumed_$eq(long x$1) {
                this.remoteCumulativeDemandConsumed = x$1;
            }

            private Try<Done> completedBeforeRemoteConnected() {
                return this.completedBeforeRemoteConnected;
            }

            private void completedBeforeRemoteConnected_$eq(Try<Done> x$1) {
                this.completedBeforeRemoteConnected = x$1;
            }

            public void preStart() {
                ActorRef actorRef2 = this.$outer.initialPartnerRef();
                ActorRef actorRef3 = OptionVal$Some$.MODULE$.unapply(actorRef2);
                if (!OptionVal$.MODULE$.isEmpty$extension(actorRef3)) {
                    ActorRef ref = OptionVal$.MODULE$.get$extension(actorRef3);
                    this.log().debug("[{}] Created SinkRef, pointing to remote Sink receiver: {}, local worker: {}", this.stageActorName(), new OptionVal<ActorRef>(this.$outer.initialPartnerRef()), this.self.ref());
                    this.observeAndValidateSender(ref, "Illegal initialPartnerRef! This may be a bug, please report your usage and complete stack trace on the issue tracker: https://github.com/akka/akka");
                    this.tryPull();
                    return;
                }
                this.log().debug("[{}] Created SinkRef with initial partner, local worker: {}, subscription timeout: {}", this.stageActorName(), this.self.ref(), PrettyDuration$.MODULE$.format(this.subscriptionTimeout.timeout()));
                this.scheduleOnce((Object)this.SubscriptionTimeoutTimerKey(), this.subscriptionTimeout.timeout());
            }

            public Function1<Tuple2<ActorRef, Object>, BoxedUnit> initialReceive() {
                return (Function1<Tuple2, Object> & Serializable)x0$1 -> {
                    $anon$1.$anonfun$initialReceive$1(this, x0$1);
                    return BoxedUnit.UNIT;
                };
            }

            public void onPush() {
                StreamRefsProtocol.SequencedOnNext<In> elem = this.grabSequenced(this.$outer.in());
                this.getPartnerRef().$bang(elem, this.selfSender());
                this.log().debug("[{}] Sending sequenced: {} to {}", this.stageActorName(), elem, this.getPartnerRef());
                this.tryPull();
            }

            private void tryPull() {
                if (this.remoteCumulativeDemandConsumed() < this.remoteCumulativeDemandReceived() && !this.hasBeenPulled(this.$outer.in()) && !this.isClosed(this.$outer.in())) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            public void onTimer(Object timerKey) {
                Object object = timerKey;
                String string = this.SubscriptionTimeoutTimerKey();
                Object object2 = object;
                if (!(string != null ? !string.equals(object2) : object2 != null)) {
                    this.log().debug("[{}] Subscription timed out", this.stageActorName());
                    StreamRefSubscriptionTimeoutException ex = new StreamRefSubscriptionTimeoutException(new StringBuilder(109).append("[").append(this.stageActorName()).append("] Remote side did not subscribe (materialize) handed out Source reference [").append(this.ref()).append("], ").append("within subscription timeout: ").append(PrettyDuration$.MODULE$.format(this.subscriptionTimeout.timeout())).append("!").toString());
                    throw ex;
                }
                throw new IllegalArgumentException(new StringBuilder(19).append("Unknown timer key: ").append(object).toString());
            }

            private <T> StreamRefsProtocol.SequencedOnNext<T> grabSequenced(Inlet<T> in) {
                StreamRefsProtocol.SequencedOnNext<T> onNext = new StreamRefsProtocol.SequencedOnNext<T>(this.remoteCumulativeDemandConsumed(), this.grab(in));
                this.remoteCumulativeDemandConsumed_$eq(this.remoteCumulativeDemandConsumed() + 1L);
                return onNext;
            }

            public void onUpstreamFailure(Throwable ex) {
                this.log().debug("[{}] Upstream failure, partnerRef [{}]", this.stageActorName(), new OptionVal<ActorRef>(this.partnerRef()));
                ActorRef actorRef2 = this.partnerRef();
                ActorRef actorRef3 = OptionVal$Some$.MODULE$.unapply(actorRef2);
                if (!OptionVal$.MODULE$.isEmpty$extension(actorRef3)) {
                    ActorRef ref = OptionVal$.MODULE$.get$extension(actorRef3);
                    ref.$bang(new StreamRefsProtocol.RemoteStreamFailure(ex.getMessage()), this.selfSender());
                    this.finishedWithAwaitingPartnerTermination = OptionVal$.MODULE$.apply(new Failure<T>(ex));
                    this.setKeepGoing(true);
                    return;
                }
                this.completedBeforeRemoteConnected_$eq((Try<Done>)OptionVal$.MODULE$.apply(new Failure<T>(ex)));
                this.setKeepGoing(true);
            }

            public void onUpstreamFinish() {
                this.log().debug("[{}] Upstream finish, partnerRef [{}]", this.stageActorName(), new OptionVal<ActorRef>(this.partnerRef()));
                ActorRef actorRef2 = this.partnerRef();
                ActorRef actorRef3 = OptionVal$Some$.MODULE$.unapply(actorRef2);
                if (!OptionVal$.MODULE$.isEmpty$extension(actorRef3)) {
                    ActorRef ref = OptionVal$.MODULE$.get$extension(actorRef3);
                    ref.$bang(new StreamRefsProtocol.RemoteStreamCompleted(this.remoteCumulativeDemandConsumed()), this.selfSender());
                    this.finishedWithAwaitingPartnerTermination = OptionVal$.MODULE$.apply(new Success<Done$>(Done$.MODULE$));
                    this.setKeepGoing(true);
                    return;
                }
                this.completedBeforeRemoteConnected_$eq((Try<Done>)OptionVal$.MODULE$.apply(new Success<Done$>(Done$.MODULE$)));
                this.setKeepGoing(true);
            }

            public void observeAndValidateSender(ActorRef partner, String failureMsg) throws InvalidPartnerActorException {
                if (OptionVal$.MODULE$.isEmpty$extension(this.partnerRef())) {
                    Success success;
                    Done done;
                    Try<Done> try_;
                    Try<Done> try_2;
                    this.partnerRef_$eq(OptionVal$.MODULE$.apply(partner));
                    partner.$bang(new StreamRefsProtocol.OnSubscribeHandshake(this.self.ref()), this.selfSender());
                    this.cancelTimer(this.SubscriptionTimeoutTimerKey());
                    this.self.watch(partner);
                    Try<Done> try_3 = this.completedBeforeRemoteConnected();
                    Try<Done> try_4 = OptionVal$Some$.MODULE$.unapply(try_3);
                    if (!OptionVal$.MODULE$.isEmpty$extension(try_4) && (try_2 = OptionVal$.MODULE$.get$extension(try_4)) instanceof Failure) {
                        Failure failure = (Failure)try_2;
                        Throwable ex = failure.exception();
                        this.log().warning("[{}] Stream already terminated with exception before remote side materialized, sending failure: {}", (Object)this.stageActorName(), (Object)ex);
                        partner.$bang(new StreamRefsProtocol.RemoteStreamFailure(ex.getMessage()), this.selfSender());
                        this.finishedWithAwaitingPartnerTermination = OptionVal$.MODULE$.apply(new Failure<T>(ex));
                        this.setKeepGoing(true);
                        return;
                    }
                    Try<Done> try_5 = OptionVal$Some$.MODULE$.unapply(try_3);
                    if (!OptionVal$.MODULE$.isEmpty$extension(try_5) && (try_ = OptionVal$.MODULE$.get$extension(try_5)) instanceof Success && Done$.MODULE$.equals(done = (Done)(success = (Success)try_).value())) {
                        this.log().warning("[{}] Stream already completed before remote side materialized, failing now.", (Object)this.stageActorName());
                        partner.$bang(new StreamRefsProtocol.RemoteStreamCompleted(this.remoteCumulativeDemandConsumed()), this.selfSender());
                        this.finishedWithAwaitingPartnerTermination = OptionVal$.MODULE$.apply(new Success<Done$>(Done$.MODULE$));
                        this.setKeepGoing(true);
                        return;
                    }
                    ActorRef actorRef2 = partner;
                    ActorRef actorRef3 = this.getPartnerRef();
                    if (actorRef2 == null ? actorRef3 != null : !((Object)actorRef2).equals(actorRef3)) {
                        InvalidPartnerActorException ex = new InvalidPartnerActorException(partner, this.getPartnerRef(), failureMsg);
                        partner.$bang(new StreamRefsProtocol.RemoteStreamFailure(ex.getMessage()), this.selfSender());
                        throw ex;
                    }
                    return;
                }
            }

            public static final /* synthetic */ void $anonfun$initialReceive$1($anon$1 $this, Tuple2 x0$1) {
                T2 T2;
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null && (T2 = tuple2._2()) instanceof Terminated) {
                    Terminated terminated = (Terminated)T2;
                    ActorRef ref = terminated.actor();
                    $this.log().debug("[{}] remote terminated [{}], partnerRef: [{}], finishedWithAwaitingPartnerTermination: [{}]", $this.stageActorName(), ref, new OptionVal<ActorRef>($this.partnerRef()), new OptionVal<Try<Done>>($this.finishedWithAwaitingPartnerTermination));
                    ActorRef actorRef2 = ref;
                    ActorRef actorRef3 = $this.getPartnerRef();
                    if (!(actorRef2 != null ? !((Object)actorRef2).equals(actorRef3) : actorRef3 != null)) {
                        Try<Done> try_;
                        Try<Done> try_2 = $this.finishedWithAwaitingPartnerTermination;
                        Try<Done> try_3 = OptionVal$Some$.MODULE$.unapply(try_2);
                        if (!OptionVal$.MODULE$.isEmpty$extension(try_3) && (try_ = OptionVal$.MODULE$.get$extension(try_3)) instanceof Failure) {
                            Failure failure = (Failure)try_;
                            Throwable ex = failure.exception();
                            $this.failStage(ex);
                        } else {
                            Try<Done> try_4 = OptionVal$Some$.MODULE$.unapply(try_2);
                            if (!OptionVal$.MODULE$.isEmpty$extension(try_4)) {
                                $this.completeStage();
                            } else {
                                $this.failStage(new RemoteStreamRefActorTerminatedException(new StringBuilder(118).append("Remote target receiver of data ").append(new OptionVal<ActorRef>($this.partnerRef())).append(" terminated. ").append("Local stream terminating, message loss (on remote side) may have happened.").toString()));
                            }
                        }
                        return;
                    }
                    return;
                }
                if (tuple2 != null) {
                    ActorRef sender = (ActorRef)tuple2._1();
                    T2 T22 = tuple2._2();
                    if (T22 instanceof StreamRefsProtocol.CumulativeDemand) {
                        StreamRefsProtocol.CumulativeDemand cumulativeDemand = (StreamRefsProtocol.CumulativeDemand)T22;
                        long d = cumulativeDemand.seqNr();
                        $this.observeAndValidateSender(sender, "Illegal sender for CumulativeDemand");
                        if ($this.remoteCumulativeDemandReceived() < d) {
                            $this.remoteCumulativeDemandReceived_$eq(d);
                            $this.log().debug("[{}] Received cumulative demand [{}], consumable demand: [{}]", $this.stageActorName(), new StreamRefsProtocol.CumulativeDemand(d), BoxesRunTime.boxToLong($this.remoteCumulativeDemandReceived() - $this.remoteCumulativeDemandConsumed()));
                        }
                        $this.tryPull();
                        return;
                    }
                }
                if (tuple2 != null) {
                    ActorRef sender = (ActorRef)tuple2._1();
                    T2 T23 = tuple2._2();
                    if (T23 instanceof StreamRefsProtocol.RemoteStreamCompleted) {
                        if (OptionVal$.MODULE$.isEmpty$extension($this.finishedWithAwaitingPartnerTermination)) {
                            $this.log().debug("[{}] Remote downstream cancelled", $this.stageActorName());
                            $this.self.unwatch(sender);
                            $this.cancelStage(SubscriptionWithCancelException$NoMoreElementsNeeded$.MODULE$);
                            sender.$bang(StreamRefsProtocol$Ack$.MODULE$, $this.selfSender());
                            return;
                        }
                        return;
                    }
                }
                if (tuple2 != null) {
                    ActorRef sender = (ActorRef)tuple2._1();
                    T2 T24 = tuple2._2();
                    if (T24 instanceof StreamRefsProtocol.RemoteStreamFailure) {
                        StreamRefsProtocol.RemoteStreamFailure remoteStreamFailure = (StreamRefsProtocol.RemoteStreamFailure)T24;
                        String msg = remoteStreamFailure.msg();
                        if (OptionVal$.MODULE$.isEmpty$extension($this.finishedWithAwaitingPartnerTermination)) {
                            $this.log().debug("[{}] Remote downstream failed: {}", $this.stageActorName(), msg);
                            $this.self.unwatch(sender);
                            $this.cancelStage(new RemoteStreamRefActorTerminatedException(new StringBuilder(26).append("Remote downstream failed: ").append(msg).toString()));
                            sender.$bang(StreamRefsProtocol$Ack$.MODULE$, $this.selfSender());
                            return;
                        }
                        return;
                    }
                }
                if (tuple2 != null) {
                    ActorRef sender = (ActorRef)tuple2._1();
                    T2 msg = tuple2._2();
                    $this.log().debug("[{}] Unexpected message {} from {}", $this.stageActorName(), msg, sender);
                    return;
                }
                throw new MatchError(tuple2);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                StageLogging.$init$(this);
                InHandler.$init$(this);
                this.streamRefsMaster = (StreamRefsMaster)StreamRefsMaster$.MODULE$.apply(eagerMaterializer$1.system());
                StreamRefSettings settings = eagerMaterializer$1.settings().streamRefSettings();
                this.subscriptionTimeout = inheritedAttributes$1.get(new StreamRefAttributes.SubscriptionTimeout(settings.subscriptionTimeout()), ClassTag$.MODULE$.apply(StreamRefAttributes.SubscriptionTimeout.class));
                this.stageActorName = this.streamRefsMaster.nextSinkRefStageName();
                this.self = this.getEagerStageActor(eagerMaterializer$1, this.initialReceive());
                this.ref = this.self.ref();
                OptionVal$.MODULE$.None();
                this.partnerRef = null;
                this.SubscriptionTimeoutTimerKey = "SubscriptionTimeoutKey";
                this.remoteCumulativeDemandReceived = 0L;
                this.remoteCumulativeDemandConsumed = 0L;
                OptionVal$.MODULE$.None();
                this.completedBeforeRemoteConnected = null;
                OptionVal$.MODULE$.None();
                this.finishedWithAwaitingPartnerTermination = null;
                this.setHandler($outer.in(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$initialReceive$1$adapted(akka.stream.impl.streamref.SinkRefStageImpl$$anon$1 scala.Tuple2 )}, serializedLambda);
            }
        };
        return new Tuple2<GraphStageLogic, SourceRef<In>>((GraphStageLogic)((Object)logic), new SourceRefImpl(((ActorRefStage)((Object)logic)).ref()));
    }

    public String toString() {
        return new StringBuilder(2).append(Logging$.MODULE$.simpleName(this.getClass())).append("(").append(this.initialRefName()).append(")").toString();
    }

    public SinkRefStageImpl(ActorRef initialPartnerRef) {
        this.initialPartnerRef = initialPartnerRef;
        this.in = Inlet$.MODULE$.apply(new StringBuilder(5).append(Logging$.MODULE$.simpleName(this.getClass())).append("(").append(this.initialRefName()).append(").in").toString());
    }

    private static interface ActorRefStage {
        public ActorRef ref();
    }
}

