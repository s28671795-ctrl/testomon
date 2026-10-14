/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.event.Logging$;
import akka.stream.impl.ActorPublisher;
import akka.stream.impl.ActorSubscription;
import akka.stream.impl.Cancel;
import akka.stream.impl.DefaultOutputTransferStates;
import akka.stream.impl.ExposedPublisher;
import akka.stream.impl.Outputs;
import akka.stream.impl.Pump;
import akka.stream.impl.ReactiveStreamsCompliance;
import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.RequestMore;
import akka.stream.impl.SubReceive;
import akka.stream.impl.SubscribePending$;
import akka.stream.impl.TransferState;
import java.io.Serializable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import scala.Function1;
import scala.None$;
import scala.PartialFunction;
import scala.Some;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00055d!B\u0011#\u0001\u0019B\u0003\u0002C\u001a\u0001\u0005\u000b\u0007I\u0011A\u001b\t\u0011m\u0002!\u0011!Q\u0001\nYB\u0001\u0002\u0010\u0001\u0003\u0006\u0004%\t!\u0010\u0005\t\u0003\u0002\u0011\t\u0011)A\u0005}!)!\t\u0001C\u0001\u0007\"Iq\t\u0001a\u0001\u0002\u0004%\t\u0002\u0013\u0005\n\u001f\u0002\u0001\r\u00111A\u0005\u0012AC\u0011B\u0016\u0001A\u0002\u0003\u0005\u000b\u0015B%\t\u0013]\u0003\u0001\u0019!a\u0001\n#A\u0006\"C1\u0001\u0001\u0004\u0005\r\u0011\"\u0005c\u0011%!\u0007\u00011A\u0001B\u0003&\u0011\fC\u0004f\u0001\u0001\u0007I\u0011\u00034\t\u000f)\u0004\u0001\u0019!C\tW\"1Q\u000e\u0001Q!\n\u001dDqA\u001c\u0001A\u0002\u0013Eq\u000eC\u0004t\u0001\u0001\u0007I\u0011\u0003;\t\rY\u0004\u0001\u0015)\u0003q\u0011\u00159\b\u0001\"\u0011p\u0011\u0015A\b\u0001\"\u0011g\u0011\u0015I\b\u0001\"\u0011{\u0011\u001dq\bA1A\u0005\niDaa \u0001!\u0002\u0013Y\bBBA\u0001\u0001\u0011\u0005q\u000eC\u0004\u0002\u0004\u0001!\t!!\u0002\t\u000f\u0005-\u0001\u0001\"\u0011\u0002\u000e!9\u0011q\u0002\u0001\u0005B\u00055\u0001bBA\t\u0001\u0011\u0005\u00131\u0003\u0005\u0007\u0003c\u0001A\u0011I8\t\u000f\u0005M\u0002\u0001\"\u0005\u00026!9\u0011Q\b\u0001\u0005\n\u0005}\u0002bBA&\u0001\u0011E\u0011Q\n\u0005\b\u0003;\u0002A\u0011CA'\u00055\u0019\u0016.\u001c9mK>+H\u000f];ug*\u00111\u0005J\u0001\u0005S6\u0004HN\u0003\u0002&M\u000511\u000f\u001e:fC6T\u0011aJ\u0001\u0005C.\\\u0017mE\u0002\u0001S=\u0002\"AK\u0017\u000e\u0003-R\u0011\u0001L\u0001\u0006g\u000e\fG.Y\u0005\u0003]-\u0012a!\u00118z%\u00164\u0007C\u0001\u00192\u001b\u0005\u0011\u0013B\u0001\u001a#\u0005m!UMZ1vYR|U\u000f\u001e9viR\u0013\u0018M\\:gKJ\u001cF/\u0019;fg\u0006)\u0011m\u0019;pe\u000e\u0001Q#\u0001\u001c\u0011\u0005]JT\"\u0001\u001d\u000b\u0005M2\u0013B\u0001\u001e9\u0005!\t5\r^8s%\u00164\u0017AB1di>\u0014\b%\u0001\u0003qk6\u0004X#\u0001 \u0011\u0005Az\u0014B\u0001!#\u0005\u0011\u0001V/\u001c9\u0002\u000bA,X\u000e\u001d\u0011\u0002\rqJg.\u001b;?)\r!UI\u0012\t\u0003a\u0001AQaM\u0003A\u0002YBQ\u0001P\u0003A\u0002y\n\u0001#\u001a=q_N,G\rU;cY&\u001c\b.\u001a:\u0016\u0003%\u00032\u0001\r&M\u0013\tY%E\u0001\bBGR|'\u000fU;cY&\u001c\b.\u001a:\u0011\u0005)j\u0015B\u0001(,\u0005\r\te._\u0001\u0015Kb\u0004xn]3e!V\u0014G.[:iKJ|F%Z9\u0015\u0005E#\u0006C\u0001\u0016S\u0013\t\u00196F\u0001\u0003V]&$\bbB+\b\u0003\u0003\u0005\r!S\u0001\u0004q\u0012\n\u0014!E3ya>\u001cX\r\u001a)vE2L7\u000f[3sA\u0005Q1/\u001e2tGJL'-\u001a:\u0016\u0003e\u00032AW0M\u001b\u0005Y&B\u0001/^\u0003=\u0011X-Y2uSZ,7\u000f\u001e:fC6\u001c(\"\u00010\u0002\u0007=\u0014x-\u0003\u0002a7\nQ1+\u001e2tGJL'-\u001a:\u0002\u001dM,(m]2sS\n,'o\u0018\u0013fcR\u0011\u0011k\u0019\u0005\b+*\t\t\u00111\u0001Z\u0003-\u0019XOY:de&\u0014WM\u001d\u0011\u0002!\u0011|wO\\:ue\u0016\fW\u000eR3nC:$W#A4\u0011\u0005)B\u0017BA5,\u0005\u0011auN\\4\u0002)\u0011|wO\\:ue\u0016\fW\u000eR3nC:$w\fJ3r)\t\tF\u000eC\u0004V\u001b\u0005\u0005\t\u0019A4\u0002#\u0011|wO\\:ue\u0016\fW\u000eR3nC:$\u0007%A\ne_^t7\u000f\u001e:fC6\u001cu.\u001c9mKR,G-F\u0001q!\tQ\u0013/\u0003\u0002sW\t9!i\\8mK\u0006t\u0017a\u00063po:\u001cHO]3b[\u000e{W\u000e\u001d7fi\u0016$w\fJ3r)\t\tV\u000fC\u0004V!\u0005\u0005\t\u0019\u00019\u0002)\u0011|wO\\:ue\u0016\fWnQ8na2,G/\u001a3!\u0003=!W-\\1oI\u00063\u0018-\u001b7bE2,\u0017a\u00033f[\u0006tGmQ8v]R\f!b];ce\u0016\u001cW-\u001b<f+\u0005Y\bC\u0001\u0019}\u0013\ti(E\u0001\u0006Tk\n\u0014VmY3jm\u0016\f1bX:vEJ,7-Z5wK\u0006aql];ce\u0016\u001cW-\u001b<fA\u0005a\u0011n]*vEN\u001c'/\u001b2fI\u0006!RM\\9vKV,w*\u001e;qkR,E.Z7f]R$2!UA\u0004\u0011\u0019\tI\u0001\u0007a\u0001\u0019\u0006!Q\r\\3n\u0003!\u0019w.\u001c9mKR,G#A)\u0002\r\r\fgnY3m\u0003\u0015)'O]8s)\r\t\u0016Q\u0003\u0005\b\u0003/Y\u0002\u0019AA\r\u0003\u0005)\u0007\u0003BA\u000e\u0003WqA!!\b\u0002(9!\u0011qDA\u0013\u001b\t\t\tCC\u0002\u0002$Q\na\u0001\u0010:p_Rt\u0014\"\u0001\u0017\n\u0007\u0005%2&A\u0004qC\u000e\\\u0017mZ3\n\t\u00055\u0012q\u0006\u0002\n)\"\u0014xn^1cY\u0016T1!!\u000b,\u0003!I7o\u00117pg\u0016$\u0017AE2sK\u0006$XmU;cg\u000e\u0014\u0018\u000e\u001d;j_:$\"!a\u000e\u0011\u0007i\u000bI$C\u0002\u0002<m\u0013AbU;cg\u000e\u0014\u0018\u000e\u001d;j_:\f\u0001c];cg\u000e\u0014\u0018NY3QK:$\u0017N\\4\u0015\u0007E\u000b\t\u0005C\u0004\u0002Dy\u0001\r!!\u0012\u0002\u0017M,(m]2sS\n,'o\u001d\t\u0006\u00037\t9%W\u0005\u0005\u0003\u0013\nyCA\u0002TKF\fqc^1ji&tw-\u0012=q_N,G\rU;cY&\u001c\b.\u001a:\u0016\u0005\u0005=\u0003\u0003BA)\u0003/r1aNA*\u0013\r\t)\u0006O\u0001\u0006\u0003\u000e$xN]\u0005\u0005\u00033\nYFA\u0004SK\u000e,\u0017N^3\u000b\u0007\u0005U\u0003(A\te_^t7\u000f\u001e:fC6\u0014VO\u001c8j]\u001eD3\u0001AA1!\u0011\t\u0019'!\u001b\u000e\u0005\u0005\u0015$bAA4M\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005-\u0014Q\r\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
@InternalApi
public class SimpleOutputs
implements DefaultOutputTransferStates {
    private final ActorRef actor;
    private final Pump pump;
    private ActorPublisher<Object> exposedPublisher;
    private Subscriber<Object> subscriber;
    private long downstreamDemand;
    private boolean downstreamCompleted;
    private final SubReceive _subreceive;
    private TransferState NeedsDemand;

    @Override
    public TransferState NeedsDemandOrCancel() {
        return DefaultOutputTransferStates.NeedsDemandOrCancel$(this);
    }

    @Override
    public boolean isOpen() {
        return Outputs.isOpen$(this);
    }

    @Override
    public TransferState NeedsDemand() {
        return this.NeedsDemand;
    }

    @Override
    public void akka$stream$impl$DefaultOutputTransferStates$_setter_$NeedsDemand_$eq(TransferState x$1) {
        this.NeedsDemand = x$1;
    }

    public ActorRef actor() {
        return this.actor;
    }

    public Pump pump() {
        return this.pump;
    }

    public ActorPublisher<Object> exposedPublisher() {
        return this.exposedPublisher;
    }

    public void exposedPublisher_$eq(ActorPublisher<Object> x$1) {
        this.exposedPublisher = x$1;
    }

    public Subscriber<Object> subscriber() {
        return this.subscriber;
    }

    public void subscriber_$eq(Subscriber<Object> x$1) {
        this.subscriber = x$1;
    }

    public long downstreamDemand() {
        return this.downstreamDemand;
    }

    public void downstreamDemand_$eq(long x$1) {
        this.downstreamDemand = x$1;
    }

    public boolean downstreamCompleted() {
        return this.downstreamCompleted;
    }

    public void downstreamCompleted_$eq(boolean x$1) {
        this.downstreamCompleted = x$1;
    }

    @Override
    public boolean demandAvailable() {
        return this.downstreamDemand() > 0L;
    }

    @Override
    public long demandCount() {
        return this.downstreamDemand();
    }

    @Override
    public SubReceive subreceive() {
        return this._subreceive();
    }

    private SubReceive _subreceive() {
        return this._subreceive;
    }

    public boolean isSubscribed() {
        return this.subscriber() != null;
    }

    @Override
    public void enqueueOutputElement(Object elem) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(elem);
        this.downstreamDemand_$eq(this.downstreamDemand() - 1L);
        ReactiveStreamsCompliance$.MODULE$.tryOnNext(this.subscriber(), elem);
    }

    @Override
    public void complete() {
        if (!this.downstreamCompleted()) {
            this.downstreamCompleted_$eq(true);
            if (this.exposedPublisher() != null) {
                this.exposedPublisher().shutdown(None$.MODULE$);
            }
            if (this.subscriber() != null) {
                ReactiveStreamsCompliance$.MODULE$.tryOnComplete(this.subscriber());
                return;
            }
            return;
        }
    }

    @Override
    public void cancel() {
        if (!this.downstreamCompleted()) {
            this.downstreamCompleted_$eq(true);
            if (this.exposedPublisher() != null) {
                this.exposedPublisher().shutdown(None$.MODULE$);
                return;
            }
            return;
        }
    }

    @Override
    public void error(Throwable e) {
        if (!this.downstreamCompleted()) {
            this.downstreamCompleted_$eq(true);
            if (this.exposedPublisher() != null) {
                this.exposedPublisher().shutdown(new Some<Throwable>(e));
            }
            if (this.subscriber() != null && !(e instanceof ReactiveStreamsCompliance.SpecViolation)) {
                ReactiveStreamsCompliance$.MODULE$.tryOnError(this.subscriber(), e);
                return;
            }
            return;
        }
    }

    @Override
    public boolean isClosed() {
        return this.downstreamCompleted() && this.subscriber() != null;
    }

    public Subscription createSubscription() {
        return new ActorSubscription<Object>(this.actor(), this.subscriber());
    }

    public void akka$stream$impl$SimpleOutputs$$subscribePending(Seq<Subscriber<Object>> subscribers) {
        subscribers.foreach((Function1<Subscriber, Object> & Serializable)sub -> {
            SimpleOutputs.$anonfun$subscribePending$1(this, sub);
            return BoxedUnit.UNIT;
        });
    }

    public PartialFunction<Object, BoxedUnit> waitingExposedPublisher() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ SimpleOutputs $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof ExposedPublisher) {
                    ExposedPublisher exposedPublisher = (ExposedPublisher)A1;
                    ActorPublisher<Object> publisher = exposedPublisher.publisher();
                    this.$outer.exposedPublisher_$eq(publisher);
                    this.$outer.subreceive().become(this.$outer.downstreamRunning());
                    return (B1)BoxedUnit.UNIT;
                }
                throw new IllegalStateException(new StringBuilder(53).append("The first message must be ExposedPublisher but was [").append(A1).append("]").toString());
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof ExposedPublisher) {
                    return true;
                }
                return true;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> downstreamRunning() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ SimpleOutputs $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (SubscribePending$.MODULE$.equals(A1)) {
                    this.$outer.akka$stream$impl$SimpleOutputs$$subscribePending(this.$outer.exposedPublisher().takePendingSubscribers());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof RequestMore) {
                    RequestMore requestMore = (RequestMore)A1;
                    long elements = requestMore.demand();
                    if (elements < 1L) {
                        this.$outer.error(ReactiveStreamsCompliance$.MODULE$.numberOfElementsInRequestMustBePositiveException());
                        return (B1)BoxedUnit.UNIT;
                    }
                    this.$outer.downstreamDemand_$eq(this.$outer.downstreamDemand() + elements);
                    if (this.$outer.downstreamDemand() < 1L) {
                        this.$outer.downstreamDemand_$eq(Long.MAX_VALUE);
                    }
                    this.$outer.pump().pump();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Cancel) {
                    this.$outer.downstreamCompleted_$eq(true);
                    this.$outer.exposedPublisher().shutdown(new Some<Throwable>(new ActorPublisher.NormalShutdownException()));
                    this.$outer.pump().pump();
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (SubscribePending$.MODULE$.equals(object)) {
                    return true;
                }
                if (object instanceof RequestMore) {
                    return true;
                }
                return object instanceof Cancel;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public static final /* synthetic */ void $anonfun$subscribePending$1(SimpleOutputs $this, Subscriber sub) {
        if ($this.subscriber() == null) {
            $this.subscriber_$eq(sub);
            ReactiveStreamsCompliance$.MODULE$.tryOnSubscribe($this.subscriber(), $this.createSubscription());
            return;
        }
        ReactiveStreamsCompliance$.MODULE$.rejectAdditionalSubscriber(sub, String.valueOf(Logging$.MODULE$.simpleName($this)));
    }

    public SimpleOutputs(ActorRef actor, Pump pump) {
        this.actor = actor;
        this.pump = pump;
        Outputs.$init$(this);
        DefaultOutputTransferStates.$init$(this);
        this.downstreamDemand = 0L;
        this.downstreamCompleted = false;
        this._subreceive = new SubReceive(this.waitingExposedPublisher());
        Statics.releaseFence();
    }
}

