/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.impl.ActorSubscriberMessage;
import akka.stream.impl.ActorSubscriberMessage$OnComplete$;
import akka.stream.impl.SubscriptionTimeoutException;
import akka.stream.impl.fusing.SubSink;
import akka.stream.impl.fusing.SubSink$RequestOne$;
import akka.stream.impl.fusing.SubSource$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function1;
import scala.MatchError;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005\u0005}c!\u0002\n\u0014\u0005eY\u0002\u0002\u0003\u001c\u0001\u0005\u0003\u0005\u000b\u0011B\u001c\t\u0013\t\u0003!Q1A\u0005\u0002M\u0019\u0005\u0002C(\u0001\u0005\u0003\u0005\u000b\u0011\u0002#\t\u000bA\u0003A\u0011A)\t\u000fU\u0003!\u0019!C\u0001-\"1!\f\u0001Q\u0001\n]CQa\u0017\u0001\u0005BqCq\u0001\u0019\u0001C\u0002\u0013\u0005\u0013\r\u0003\u0004c\u0001\u0001\u0006Ia\t\u0005\bG\u0002\u0011\r\u0011\"\u0003e\u0011\u0019!\b\u0001)A\u0005K\")Q\u000f\u0001C\u0001m\")A\u0010\u0001C\u0001{\")a\u0010\u0001C\u0001\u007f\"9\u0011q\u0003\u0001\u0005\u0002\u0005e\u0001bBA\u001a\u0001\u0011\u0005\u0013Q\u0007\u0005\b\u0003\u001b\u0002A\u0011IA(\u0005%\u0019VOY*pkJ\u001cWM\u0003\u0002\u0015+\u00051a-^:j]\u001eT!AF\f\u0002\t%l\u0007\u000f\u001c\u0006\u00031e\taa\u001d;sK\u0006l'\"\u0001\u000e\u0002\t\u0005\\7.Y\u000b\u00039%\u001a\"\u0001A\u000f\u0011\u0007y\t3%D\u0001 \u0015\t\u0001s#A\u0003ti\u0006<W-\u0003\u0002#?\tQqI]1qQN#\u0018mZ3\u0011\u0007\u0011*s%D\u0001\u0018\u0013\t1sCA\u0006T_V\u00148-Z*iCB,\u0007C\u0001\u0015*\u0019\u0001!QA\u000b\u0001C\u00021\u0012\u0011\u0001V\u0002\u0001#\ti3\u0007\u0005\u0002/c5\tqFC\u00011\u0003\u0015\u00198-\u00197b\u0013\t\u0011tFA\u0004O_RD\u0017N\\4\u0011\u00059\"\u0014BA\u001b0\u0005\r\te._\u0001\u0005]\u0006lW\r\u0005\u00029\u007f9\u0011\u0011(\u0010\t\u0003u=j\u0011a\u000f\u0006\u0003y-\na\u0001\u0010:p_Rt\u0014B\u0001 0\u0003\u0019\u0001&/\u001a3fM&\u0011\u0001)\u0011\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005yz\u0013\u0001E3yi\u0016\u0014h.\u00197DC2d'-Y2l+\u0005!\u0005c\u0001\u0010F\u000f&\u0011ai\b\u0002\u000e\u0003NLhnY\"bY2\u0014\u0017mY6\u0011\u0005!ceBA%K\u001b\u0005\u0019\u0012BA&\u0014\u0003\u001d\u0019VOY*j].L!!\u0014(\u0003\u000f\r{W.\\1oI*\u00111jE\u0001\u0012Kb$XM\u001d8bY\u000e\u000bG\u000e\u001c2bG.\u0004\u0013A\u0002\u001fj]&$h\bF\u0002S'R\u00032!\u0013\u0001(\u0011\u00151D\u00011\u00018\u0011\u0015\u0011E\u00011\u0001E\u0003\ryW\u000f^\u000b\u0002/B\u0019A\u0005W\u0014\n\u0005e;\"AB(vi2,G/\u0001\u0003pkR\u0004\u0013!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\tQ\f\u0005\u0002%=&\u0011ql\u0006\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!B:iCB,W#A\u0012\u0002\rMD\u0017\r]3!\u0003\u0019\u0019H/\u0019;vgV\tQ\rE\u0002g_Fl\u0011a\u001a\u0006\u0003Q&\fa!\u0019;p[&\u001c'B\u00016l\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0003Y6\fA!\u001e;jY*\ta.\u0001\u0003kCZ\f\u0017B\u00019h\u0005=\tEo\\7jGJ+g-\u001a:f]\u000e,\u0007C\u0001\u0018s\u0013\t\u0019xF\u0001\u0004B]f\u0014VMZ\u0001\bgR\fG/^:!\u00035\u0001Xo\u001d5Tk\n\u001cHO]3b[R\u0011qO\u001f\t\u0003]aL!!_\u0018\u0003\tUs\u0017\u000e\u001e\u0005\u0006w2\u0001\raJ\u0001\u0005K2,W.A\td_6\u0004H.\u001a;f'V\u00147\u000f\u001e:fC6$\u0012a^\u0001\u000eM\u0006LGnU;cgR\u0014X-Y7\u0015\u0007]\f\t\u0001C\u0004\u0002\u00049\u0001\r!!\u0002\u0002\u0005\u0015D\b\u0003BA\u0004\u0003#qA!!\u0003\u0002\u000e9\u0019!(a\u0003\n\u0003AJ1!a\u00040\u0003\u001d\u0001\u0018mY6bO\u0016LA!a\u0005\u0002\u0016\tIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0004\u0003\u001fy\u0013a\u0002;j[\u0016|W\u000f\u001e\u000b\u0005\u00037\t\t\u0003E\u0002/\u0003;I1!a\b0\u0005\u001d\u0011un\u001c7fC:Dq!a\t\u0010\u0001\u0004\t)#A\u0001e!\u0011\t9#a\f\u000e\u0005\u0005%\"\u0002BA\u0016\u0003[\t\u0001\u0002Z;sCRLwN\u001c\u0006\u0003U>JA!!\r\u0002*\tqa)\u001b8ji\u0016$UO]1uS>t\u0017aC2sK\u0006$X\rT8hS\u000e$B!a\u000e\u0002JI1\u0011\u0011HA\u001f\u0003\u00072a!a\u000f\u0011\u0001\u0005]\"\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004c\u0001\u0010\u0002@%\u0019\u0011\u0011I\u0010\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000e\u00042AHA#\u0013\r\t9e\b\u0002\u000b\u001fV$\b*\u00198eY\u0016\u0014\bBBA&!\u0001\u0007Q,A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0005u_N#(/\u001b8h)\u00059\u0004f\u0001\u0001\u0002TA!\u0011QKA.\u001b\t\t9FC\u0002\u0002Ze\t!\"\u00198o_R\fG/[8o\u0013\u0011\ti&a\u0016\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public final class SubSource<T>
extends GraphStage<SourceShape<T>> {
    public final String akka$stream$impl$fusing$SubSource$$name;
    private final AsyncCallback<SubSink.Command> externalCallback;
    private final Outlet<T> out;
    private final SourceShape<T> shape;
    private final AtomicReference<Object> akka$stream$impl$fusing$SubSource$$status;

    public AsyncCallback<SubSink.Command> externalCallback() {
        return this.externalCallback;
    }

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name(new StringBuilder(11).append("SubSource(").append(this.akka$stream$impl$fusing$SubSource$$name).append(")").toString());
    }

    @Override
    public SourceShape<T> shape() {
        return this.shape;
    }

    public AtomicReference<Object> akka$stream$impl$fusing$SubSource$$status() {
        return this.akka$stream$impl$fusing$SubSource$$status;
    }

    public void pushSubstream(T elem) {
        Object object = this.akka$stream$impl$fusing$SubSource$$status().get();
        if (object instanceof AsyncCallback) {
            AsyncCallback asyncCallback = (AsyncCallback)object;
            asyncCallback.invoke(new ActorSubscriberMessage.OnNext(elem));
            return;
        }
        throw new IllegalStateException("cannot push to uninitialized substream");
    }

    public void completeSubstream() {
        Object object = this.akka$stream$impl$fusing$SubSource$$status().get();
        if (object instanceof AsyncCallback) {
            AsyncCallback asyncCallback = (AsyncCallback)object;
            asyncCallback.invoke(ActorSubscriberMessage$OnComplete$.MODULE$);
            return;
        }
        if (object == null) {
            if (!this.akka$stream$impl$fusing$SubSource$$status().compareAndSet(null, ActorSubscriberMessage$OnComplete$.MODULE$)) {
                ((AsyncCallback)this.akka$stream$impl$fusing$SubSource$$status().get()).invoke(ActorSubscriberMessage$OnComplete$.MODULE$);
                return;
            }
            return;
        }
        if (object instanceof ActorSubscriberMessage.OnError) {
            return;
        }
        if (ActorSubscriberMessage$OnComplete$.MODULE$.equals(object)) {
            return;
        }
        throw new RuntimeException();
    }

    public void failSubstream(Throwable ex) {
        Object object = this.akka$stream$impl$fusing$SubSource$$status().get();
        if (object instanceof AsyncCallback) {
            AsyncCallback asyncCallback = (AsyncCallback)object;
            asyncCallback.invoke(new ActorSubscriberMessage.OnError(ex));
            return;
        }
        if (object == null) {
            ActorSubscriberMessage.OnError failure = new ActorSubscriberMessage.OnError(ex);
            if (!this.akka$stream$impl$fusing$SubSource$$status().compareAndSet(null, failure)) {
                ((AsyncCallback)this.akka$stream$impl$fusing$SubSource$$status().get()).invoke(failure);
                return;
            }
            return;
        }
        if (ActorSubscriberMessage$OnComplete$.MODULE$.equals(object)) {
            return;
        }
        if (object instanceof ActorSubscriberMessage.OnError) {
            return;
        }
        throw new RuntimeException();
    }

    public boolean timeout(FiniteDuration d) {
        return this.akka$stream$impl$fusing$SubSource$$status().compareAndSet(null, new ActorSubscriberMessage.OnError(new SubscriptionTimeoutException(new StringBuilder(48).append("Substream Source(").append(this.akka$stream$impl$fusing$SubSource$$name).append(") has not been materialized in ").append(d).toString())));
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this){
            private final IllegalStateException materializationException;
            private final /* synthetic */ SubSource $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            private void setCB(AsyncCallback<ActorSubscriberMessage> cb) {
                block7: {
                    Object object;
                    while ((object = this.$outer.akka$stream$impl$fusing$SubSource$$status().get()) == null) {
                        if (!this.$outer.akka$stream$impl$fusing$SubSource$$status().compareAndSet(null, cb)) continue;
                        break block7;
                    }
                    if (ActorSubscriberMessage$OnComplete$.MODULE$.equals(object)) {
                        this.completeStage();
                    } else if (object instanceof ActorSubscriberMessage.OnError) {
                        ActorSubscriberMessage.OnError onError2 = (ActorSubscriberMessage.OnError)object;
                        Throwable ex = onError2.cause();
                        this.failStage(ex);
                    } else if (object instanceof AsyncCallback) {
                        this.failStage(OptionVal$.MODULE$.getOrElse$extension(this.materializationException, this.createMaterializedTwiceException()));
                    } else {
                        throw new RuntimeException();
                    }
                }
            }

            public void preStart() {
                AsyncCallback<ActorSubscriberMessage> ourOwnCallback = this.getAsyncCallback((Function1<ActorSubscriberMessage, Object> & Serializable)x0$1 -> {
                    $anon$11.$anonfun$preStart$2(this, x0$1);
                    return BoxedUnit.UNIT;
                });
                this.setCB(ourOwnCallback);
            }

            public void onPull() {
                this.$outer.externalCallback().invoke(SubSink$RequestOne$.MODULE$);
            }

            public void onDownstreamFinish(Throwable cause) {
                this.$outer.externalCallback().invoke(new SubSink.Cancel(cause));
            }

            private IllegalStateException createMaterializedTwiceException() {
                return new IllegalStateException(new StringBuilder(56).append("Substream Source(").append(this.$outer.akka$stream$impl$fusing$SubSource$$name).append(") cannot be materialized more than once").toString());
            }

            public static final /* synthetic */ void $anonfun$preStart$2($anon$11 $this, ActorSubscriberMessage x0$1) {
                ActorSubscriberMessage actorSubscriberMessage = x0$1;
                if (ActorSubscriberMessage$OnComplete$.MODULE$.equals(actorSubscriberMessage)) {
                    $this.completeStage();
                    return;
                }
                if (actorSubscriberMessage instanceof ActorSubscriberMessage.OnError) {
                    ActorSubscriberMessage.OnError onError2 = (ActorSubscriberMessage.OnError)actorSubscriberMessage;
                    Throwable ex = onError2.cause();
                    $this.failStage(ex);
                    return;
                }
                if (actorSubscriberMessage instanceof ActorSubscriberMessage.OnNext) {
                    ActorSubscriberMessage.OnNext onNext = (ActorSubscriberMessage.OnNext)actorSubscriberMessage;
                    Object elem = onNext.element();
                    $this.push($this.$outer.out(), elem);
                    return;
                }
                throw new MatchError(actorSubscriberMessage);
            }
            {
                IllegalStateException illegalStateException;
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                OutHandler.$init$(this);
                if ($outer.akka$stream$impl$fusing$SubSource$$status().get() instanceof AsyncCallback) {
                    illegalStateException = OptionVal$Some$.MODULE$.apply(this.createMaterializedTwiceException());
                } else {
                    OptionVal$.MODULE$.None();
                    illegalStateException = null;
                }
                this.materializationException = illegalStateException;
                this.setHandler($outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$preStart$2$adapted(akka.stream.impl.fusing.SubSource$$anon$11 akka.stream.impl.ActorSubscriberMessage )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return this.akka$stream$impl$fusing$SubSource$$name;
    }

    public SubSource(String name, AsyncCallback<SubSink.Command> externalCallback) {
        this.akka$stream$impl$fusing$SubSource$$name = name;
        this.externalCallback = externalCallback;
        this.out = Outlet$.MODULE$.apply(new StringBuilder(15).append("SubSource(").append(name).append(").out").toString());
        this.shape = new SourceShape<T>(this.out());
        this.akka$stream$impl$fusing$SubSource$$status = new AtomicReference();
    }
}

