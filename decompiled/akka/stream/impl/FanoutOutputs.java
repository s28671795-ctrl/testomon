/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.stream.impl.ActorPublisher;
import akka.stream.impl.ActorPublisher$;
import akka.stream.impl.ActorSubscription;
import akka.stream.impl.ActorSubscriptionWithCursor;
import akka.stream.impl.Cancel;
import akka.stream.impl.DefaultOutputTransferStates;
import akka.stream.impl.ExposedPublisher;
import akka.stream.impl.Outputs;
import akka.stream.impl.Pump;
import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.RequestMore;
import akka.stream.impl.ResizableMultiReaderRingBuffer;
import akka.stream.impl.SubReceive;
import akka.stream.impl.SubscribePending$;
import akka.stream.impl.SubscriberManagement;
import akka.stream.impl.SubscriptionWithCursor;
import akka.stream.impl.TransferState;
import java.io.Serializable;
import org.reactivestreams.Subscriber;
import scala.Function1;
import scala.None$;
import scala.PartialFunction;
import scala.Some;
import scala.collection.immutable.List;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005UfA\u0002\u0015*\u0003\u0003is\u0006\u0003\u0005A\u0001\t\u0015\r\u0011\"\u0001C\u0011!1\u0005A!A!\u0002\u0013\u0019\u0005\u0002C$\u0001\u0005\u000b\u0007I\u0011\u0001\"\t\u0011!\u0003!\u0011!Q\u0001\n\rC\u0001\"\u0013\u0001\u0003\u0002\u0003\u0006IA\u0013\u0005\t!\u0002\u0011)\u0019!C\u0001#\"AQ\u000b\u0001B\u0001B\u0003%!\u000bC\u0003W\u0001\u0011\u0005q\u000bC\u0004^\u0001\u0001\u0007I\u0011\u00020\t\u000f\t\u0004\u0001\u0019!C\u0005G\"1\u0011\u000e\u0001Q!\n}CQA\u001b\u0001\u0005\u0002y+Aa\u001b\u0001!Y\")Q\u000f\u0001C!m\"Y\u0011q\u0002\u0001A\u0002\u0003\u0007I\u0011CA\t\u0011-\tI\u0002\u0001a\u0001\u0002\u0004%\t\"a\u0007\t\u0017\u0005}\u0001\u00011A\u0001B\u0003&\u00111\u0003\u0005\n\u0003C\u0001\u0001\u0019!C\u0005\u0003GA\u0011\"a\u000b\u0001\u0001\u0004%I!!\f\t\u0011\u0005E\u0002\u0001)Q\u0005\u0003KA\u0001\"a\r\u0001\u0001\u0004%IA\u0018\u0005\n\u0003k\u0001\u0001\u0019!C\u0005\u0003oAq!a\u000f\u0001A\u0003&q\f\u0003\u0004\u0002>\u0001!\tE\u0018\u0005\b\u0003\u007f\u0001A\u0011IA\u0012\u0011%\t\t\u0005\u0001b\u0001\n\u0003\n\u0019\u0005\u0003\u0005\u0002L\u0001\u0001\u000b\u0011BA#\u0011\u001d\ti\u0005\u0001C\u0001\u0003\u001fBq!!\u0016\u0001\t\u0003\n9\u0006C\u0004\u0002Z\u0001!\t%a\u0016\t\u000f\u0005m\u0003\u0001\"\u0011\u0002^!1\u00111\u0010\u0001\u0005\u0002yCq!! \u0001\r\u0003\t9\u0006C\u0004\u0002\u0000\u0001!\t&!!\t\u000f\u0005\u001d\u0005\u0001\"\u0003\u0002X!9\u0011\u0011\u0012\u0001\u0005R\u0005-\u0005bBAI\u0001\u0011E\u0013q\u000b\u0005\b\u0003'\u0003A\u0011CAK\u0011\u001d\t)\u000b\u0001C\t\u0003+\u0013QBR1o_V$x*\u001e;qkR\u001c(B\u0001\u0016,\u0003\u0011IW\u000e\u001d7\u000b\u00051j\u0013AB:ue\u0016\fWNC\u0001/\u0003\u0011\t7n[1\u0014\t\u0001\u0001dG\u000f\t\u0003cQj\u0011A\r\u0006\u0002g\u0005)1oY1mC&\u0011QG\r\u0002\u0007\u0003:L(+\u001a4\u0011\u0005]BT\"A\u0015\n\u0005eJ#a\u0007#fM\u0006,H\u000e^(viB,H\u000f\u0016:b]N4WM]*uCR,7\u000fE\u00028wuJ!\u0001P\u0015\u0003)M+(m]2sS\n,'/T1oC\u001e,W.\u001a8u!\t\td(\u0003\u0002@e\t\u0019\u0011I\\=\u0002\u001b5\f\u0007PQ;gM\u0016\u00148+\u001b>f\u0007\u0001)\u0012a\u0011\t\u0003c\u0011K!!\u0012\u001a\u0003\u0007%sG/\u0001\bnCb\u0014UO\u001a4feNK'0\u001a\u0011\u0002#%t\u0017\u000e^5bY\n+hMZ3s'&TX-\u0001\nj]&$\u0018.\u00197Ck\u001a4WM]*ju\u0016\u0004\u0013\u0001B:fY\u001a\u0004\"a\u0013(\u000e\u00031S!!T\u0017\u0002\u000b\u0005\u001cGo\u001c:\n\u0005=c%\u0001C!di>\u0014(+\u001a4\u0002\tA,X\u000e]\u000b\u0002%B\u0011qgU\u0005\u0003)&\u0012A\u0001U;na\u0006)\u0001/^7qA\u00051A(\u001b8jiz\"R\u0001W-[7r\u0003\"a\u000e\u0001\t\u000b\u0001C\u0001\u0019A\"\t\u000b\u001dC\u0001\u0019A\"\t\u000b%C\u0001\u0019\u0001&\t\u000bAC\u0001\u0019\u0001*\u0002\u0017}\u001bXOY:de&\u0014W\rZ\u000b\u0002?B\u0011\u0011\u0007Y\u0005\u0003CJ\u0012qAQ8pY\u0016\fg.A\b`gV\u00147o\u0019:jE\u0016$w\fJ3r)\t!w\r\u0005\u00022K&\u0011aM\r\u0002\u0005+:LG\u000fC\u0004i\u0015\u0005\u0005\t\u0019A0\u0002\u0007a$\u0013'\u0001\u0007`gV\u00147o\u0019:jE\u0016$\u0007%\u0001\u0006tk\n\u001c8M]5cK\u0012\u0014\u0011a\u0015\u0019\u0003[J\u00042a\u000e8q\u0013\ty\u0017FA\u000eBGR|'oU;cg\u000e\u0014\u0018\u000e\u001d;j_:<\u0016\u000e\u001e5DkJ\u001cxN\u001d\t\u0003cJd\u0001\u0001B\u0005t\u001b\u0005\u0005\t\u0011!B\u0001i\n\u0019q\fJ\u0019\u0012\u0005uj\u0014AE2sK\u0006$XmU;cg\u000e\u0014\u0018\u000e\u001d;j_:$\"a^=\u0011\u0005alQ\"\u0001\u0001\t\u000bit\u0001\u0019A>\u0002\u0015M,(m]2sS\n,'\u000fM\u0002}\u0003\u0017\u0001R!`A\u0003\u0003\u0013i\u0011A \u0006\u0004\u007f\u0006\u0005\u0011a\u0004:fC\u000e$\u0018N^3tiJ,\u0017-\\:\u000b\u0005\u0005\r\u0011aA8sO&\u0019\u0011q\u0001@\u0003\u0015M+(m]2sS\n,'\u000fE\u0002r\u0003\u0017!!\"!\u0004z\u0003\u0003\u0005\tQ!\u0001u\u0005\ryFEM\u0001\u0011Kb\u0004xn]3e!V\u0014G.[:iKJ,\"!a\u0005\u0011\t]\n)\"P\u0005\u0004\u0003/I#AD!di>\u0014\b+\u001e2mSNDWM]\u0001\u0015Kb\u0004xn]3e!V\u0014G.[:iKJ|F%Z9\u0015\u0007\u0011\fi\u0002\u0003\u0005i!\u0005\u0005\t\u0019AA\n\u0003E)\u0007\u0010]8tK\u0012\u0004VO\u00197jg\",'\u000fI\u0001\u0016I><hn\u001d;sK\u0006l')\u001e4gKJ\u001c\u0006/Y2f+\t\t)\u0003E\u00022\u0003OI1!!\u000b3\u0005\u0011auN\\4\u00023\u0011|wO\\:ue\u0016\fWNQ;gM\u0016\u00148\u000b]1dK~#S-\u001d\u000b\u0004I\u0006=\u0002\u0002\u00035\u0014\u0003\u0003\u0005\r!!\n\u0002-\u0011|wO\\:ue\u0016\fWNQ;gM\u0016\u00148\u000b]1dK\u0002\n1\u0003Z8x]N$(/Z1n\u0007>l\u0007\u000f\\3uK\u0012\fq\u0003Z8x]N$(/Z1n\u0007>l\u0007\u000f\\3uK\u0012|F%Z9\u0015\u0007\u0011\fI\u0004C\u0004i-\u0005\u0005\t\u0019A0\u0002)\u0011|wO\\:ue\u0016\fWnQ8na2,G/\u001a3!\u0003=!W-\\1oI\u00063\u0018-\u001b7bE2,\u0017a\u00033f[\u0006tGmQ8v]R\f!b];ce\u0016\u001cW-\u001b<f+\t\t)\u0005E\u00028\u0003\u000fJ1!!\u0013*\u0005)\u0019VO\u0019*fG\u0016Lg/Z\u0001\fgV\u0014'/Z2fSZ,\u0007%\u0001\u000bf]F,X-^3PkR\u0004X\u000f^#mK6,g\u000e\u001e\u000b\u0004I\u0006E\u0003BBA*9\u0001\u0007Q(\u0001\u0003fY\u0016l\u0017\u0001C2p[BdW\r^3\u0015\u0003\u0011\faaY1oG\u0016d\u0017!B3se>\u0014Hc\u00013\u0002`!9\u0011\u0011M\u0010A\u0002\u0005\r\u0014!A3\u0011\t\u0005\u0015\u0014Q\u000f\b\u0005\u0003O\n\tH\u0004\u0003\u0002j\u0005=TBAA6\u0015\r\ti'Q\u0001\u0007yI|w\u000e\u001e \n\u0003MJ1!a\u001d3\u0003\u001d\u0001\u0018mY6bO\u0016LA!a\u001e\u0002z\tIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0004\u0003g\u0012\u0014\u0001C5t\u00072|7/\u001a3\u0002\u001b\u00054G/\u001a:TQV$Hm\\<o\u0003M\u0011X-];fgR4%o\\7VaN$(/Z1n)\r!\u00171\u0011\u0005\b\u0003\u000b\u0013\u0003\u0019AA\u0013\u0003!)G.Z7f]R\u001c\u0018\u0001E:vEN\u001c'/\u001b2f!\u0016tG-\u001b8h\u0003!\u0019\b.\u001e;e_^tGc\u00013\u0002\u000e\"1\u0011q\u0012\u0013A\u0002}\u000b\u0011bY8na2,G/\u001a3\u0002\u001d\r\fgnY3m+B\u001cHO]3b[\u00069r/Y5uS:<W\t\u001f9pg\u0016$\u0007+\u001e2mSNDWM]\u000b\u0003\u0003/\u0003B!!'\u0002 :\u00191*a'\n\u0007\u0005uE*A\u0003BGR|'/\u0003\u0003\u0002\"\u0006\r&a\u0002*fG\u0016Lg/\u001a\u0006\u0004\u0003;c\u0015!\u00053po:\u001cHO]3b[J+hN\\5oO\"\u001a\u0001!!+\u0011\t\u0005-\u0016\u0011W\u0007\u0003\u0003[S1!a,.\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003g\u000biKA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public abstract class FanoutOutputs
implements DefaultOutputTransferStates,
SubscriberManagement<Object> {
    private final int maxBufferSize;
    private final int initialBufferSize;
    private final ActorRef self;
    private final Pump pump;
    private boolean _subscribed;
    private ActorPublisher<Object> exposedPublisher;
    private long downstreamBufferSpace;
    private boolean downstreamCompleted;
    private final SubReceive subreceive;
    private ResizableMultiReaderRingBuffer<Object> akka$stream$impl$SubscriberManagement$$buffer;
    private List<ActorSubscriptionWithCursor<? super Object>> akka$stream$impl$SubscriberManagement$$subscriptions;
    private long akka$stream$impl$SubscriberManagement$$pendingFromUpstream;
    private SubscriberManagement.EndOfStream akka$stream$impl$SubscriberManagement$$endOfStream;
    private TransferState NeedsDemand;

    @Override
    public String bufferDebug() {
        return SubscriberManagement.bufferDebug$(this);
    }

    @Override
    public List<SubscriptionWithCursor> cursors() {
        return SubscriberManagement.cursors$(this);
    }

    @Override
    public void moreRequested(SubscriptionWithCursor subscription, long elements) {
        SubscriberManagement.moreRequested$(this, subscription, elements);
    }

    @Override
    public void pushToDownstream(Object value) {
        SubscriberManagement.pushToDownstream$(this, value);
    }

    @Override
    public void completeDownstream() {
        SubscriberManagement.completeDownstream$(this);
    }

    @Override
    public void abortDownstream(Throwable cause) {
        SubscriberManagement.abortDownstream$(this, cause);
    }

    @Override
    public void registerSubscriber(Subscriber<? super Object> subscriber) {
        SubscriberManagement.registerSubscriber$(this, subscriber);
    }

    @Override
    public void unregisterSubscription(SubscriptionWithCursor subscription) {
        SubscriberManagement.unregisterSubscription$(this, subscription);
    }

    @Override
    public TransferState NeedsDemandOrCancel() {
        return DefaultOutputTransferStates.NeedsDemandOrCancel$(this);
    }

    @Override
    public boolean isOpen() {
        return Outputs.isOpen$(this);
    }

    @Override
    public ResizableMultiReaderRingBuffer<Object> akka$stream$impl$SubscriberManagement$$buffer() {
        return this.akka$stream$impl$SubscriberManagement$$buffer;
    }

    @Override
    public List<ActorSubscriptionWithCursor<? super Object>> akka$stream$impl$SubscriberManagement$$subscriptions() {
        return this.akka$stream$impl$SubscriberManagement$$subscriptions;
    }

    @Override
    public void akka$stream$impl$SubscriberManagement$$subscriptions_$eq(List<ActorSubscriptionWithCursor<? super Object>> x$1) {
        this.akka$stream$impl$SubscriberManagement$$subscriptions = x$1;
    }

    @Override
    public long akka$stream$impl$SubscriberManagement$$pendingFromUpstream() {
        return this.akka$stream$impl$SubscriberManagement$$pendingFromUpstream;
    }

    @Override
    public void akka$stream$impl$SubscriberManagement$$pendingFromUpstream_$eq(long x$1) {
        this.akka$stream$impl$SubscriberManagement$$pendingFromUpstream = x$1;
    }

    @Override
    public SubscriberManagement.EndOfStream akka$stream$impl$SubscriberManagement$$endOfStream() {
        return this.akka$stream$impl$SubscriberManagement$$endOfStream;
    }

    @Override
    public void akka$stream$impl$SubscriberManagement$$endOfStream_$eq(SubscriberManagement.EndOfStream x$1) {
        this.akka$stream$impl$SubscriberManagement$$endOfStream = x$1;
    }

    @Override
    public final void akka$stream$impl$SubscriberManagement$_setter_$akka$stream$impl$SubscriberManagement$$buffer_$eq(ResizableMultiReaderRingBuffer<Object> x$1) {
        this.akka$stream$impl$SubscriberManagement$$buffer = x$1;
    }

    @Override
    public TransferState NeedsDemand() {
        return this.NeedsDemand;
    }

    @Override
    public void akka$stream$impl$DefaultOutputTransferStates$_setter_$NeedsDemand_$eq(TransferState x$1) {
        this.NeedsDemand = x$1;
    }

    @Override
    public int maxBufferSize() {
        return this.maxBufferSize;
    }

    @Override
    public int initialBufferSize() {
        return this.initialBufferSize;
    }

    public Pump pump() {
        return this.pump;
    }

    private boolean _subscribed() {
        return this._subscribed;
    }

    private void _subscribed_$eq(boolean x$1) {
        this._subscribed = x$1;
    }

    public boolean subscribed() {
        return this._subscribed();
    }

    public ActorSubscriptionWithCursor<? super Object> createSubscription(Subscriber<? super Object> subscriber) {
        this._subscribed_$eq(true);
        return new ActorSubscriptionWithCursor<Object>(this.self, subscriber);
    }

    public ActorPublisher<Object> exposedPublisher() {
        return this.exposedPublisher;
    }

    public void exposedPublisher_$eq(ActorPublisher<Object> x$1) {
        this.exposedPublisher = x$1;
    }

    private long downstreamBufferSpace() {
        return this.downstreamBufferSpace;
    }

    private void downstreamBufferSpace_$eq(long x$1) {
        this.downstreamBufferSpace = x$1;
    }

    private boolean downstreamCompleted() {
        return this.downstreamCompleted;
    }

    private void downstreamCompleted_$eq(boolean x$1) {
        this.downstreamCompleted = x$1;
    }

    @Override
    public boolean demandAvailable() {
        return this.downstreamBufferSpace() > 0L;
    }

    @Override
    public long demandCount() {
        return this.downstreamBufferSpace();
    }

    @Override
    public SubReceive subreceive() {
        return this.subreceive;
    }

    @Override
    public void enqueueOutputElement(Object elem) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(elem);
        this.downstreamBufferSpace_$eq(this.downstreamBufferSpace() - 1L);
        this.pushToDownstream(elem);
    }

    @Override
    public void complete() {
        if (!this.downstreamCompleted()) {
            this.downstreamCompleted_$eq(true);
            this.completeDownstream();
            return;
        }
    }

    @Override
    public void cancel() {
        this.complete();
    }

    @Override
    public void error(Throwable e) {
        if (!this.downstreamCompleted()) {
            this.downstreamCompleted_$eq(true);
            this.abortDownstream(e);
            if (this.exposedPublisher() != null) {
                this.exposedPublisher().shutdown(new Some<Throwable>(e));
                return;
            }
            return;
        }
    }

    @Override
    public boolean isClosed() {
        return this.downstreamCompleted();
    }

    public abstract void afterShutdown();

    @Override
    public void requestFromUpstream(long elements) {
        this.downstreamBufferSpace_$eq(this.downstreamBufferSpace() + elements);
    }

    public void akka$stream$impl$FanoutOutputs$$subscribePending() {
        this.exposedPublisher().takePendingSubscribers().foreach((Function1<Subscriber, Object> & Serializable)subscriber -> {
            this.registerSubscriber(subscriber);
            return BoxedUnit.UNIT;
        });
    }

    @Override
    public void shutdown(boolean completed2) {
        if (this.exposedPublisher() != null) {
            if (completed2) {
                this.exposedPublisher().shutdown(None$.MODULE$);
            } else {
                this.exposedPublisher().shutdown(ActorPublisher$.MODULE$.SomeNormalShutdownReason());
            }
        }
        this.afterShutdown();
    }

    @Override
    public void cancelUpstream() {
        this.downstreamCompleted_$eq(true);
    }

    public PartialFunction<Object, BoxedUnit> waitingExposedPublisher() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ FanoutOutputs $outer;

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
            private final /* synthetic */ FanoutOutputs $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (SubscribePending$.MODULE$.equals(A1)) {
                    this.$outer.akka$stream$impl$FanoutOutputs$$subscribePending();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof RequestMore) {
                    RequestMore requestMore = (RequestMore)A1;
                    ActorSubscription<T> subscription = requestMore.subscription();
                    long elements = requestMore.demand();
                    this.$outer.moreRequested((ActorSubscriptionWithCursor)subscription, elements);
                    this.$outer.pump().pump();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Cancel) {
                    Cancel cancel = (Cancel)A1;
                    ActorSubscription<T> subscription = cancel.subscription();
                    this.$outer.unregisterSubscription((ActorSubscriptionWithCursor)subscription);
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

    public FanoutOutputs(int maxBufferSize, int initialBufferSize, ActorRef self, Pump pump) {
        this.maxBufferSize = maxBufferSize;
        this.initialBufferSize = initialBufferSize;
        this.self = self;
        this.pump = pump;
        Outputs.$init$(this);
        DefaultOutputTransferStates.$init$(this);
        SubscriberManagement.$init$(this);
        this._subscribed = false;
        this.downstreamBufferSpace = 0L;
        this.downstreamCompleted = false;
        this.subreceive = new SubReceive(this.waitingExposedPublisher());
        Statics.releaseFence();
    }
}

