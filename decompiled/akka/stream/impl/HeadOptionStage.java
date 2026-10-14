/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.AbruptStageTerminationException;
import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.SinkShape;
import akka.stream.SinkShape$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Tuple2;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u001d4Q\u0001C\u0005\u0003\u001b=AQa\r\u0001\u0005\u0002QBqa\u000e\u0001C\u0002\u0013\u0005\u0001\b\u0003\u0004=\u0001\u0001\u0006I!\u000f\u0005\b{\u0001\u0011\r\u0011\"\u0011?\u0011\u0019y\u0004\u0001)A\u0005/!)\u0001\t\u0001C!\u0003\")1\u000b\u0001C!)\ny\u0001*Z1e\u001fB$\u0018n\u001c8Ti\u0006<WM\u0003\u0002\u000b\u0017\u0005!\u0011.\u001c9m\u0015\taQ\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u001d\u0005!\u0011m[6b+\t\u0001Rd\u0005\u0002\u0001#A!!#F\f+\u001b\u0005\u0019\"B\u0001\u000b\f\u0003\u0015\u0019H/Y4f\u0013\t12CA\u0010He\u0006\u0004\bn\u0015;bO\u0016<\u0016\u000e\u001e5NCR,'/[1mSj,GMV1mk\u0016\u00042\u0001G\r\u001c\u001b\u0005Y\u0011B\u0001\u000e\f\u0005%\u0019\u0016N\\6TQ\u0006\u0004X\r\u0005\u0002\u001d;1\u0001A!\u0002\u0010\u0001\u0005\u0004\u0001#!\u0001+\u0004\u0001E\u0011\u0011e\n\t\u0003E\u0015j\u0011a\t\u0006\u0002I\u0005)1oY1mC&\u0011ae\t\u0002\b\u001d>$\b.\u001b8h!\t\u0011\u0003&\u0003\u0002*G\t\u0019\u0011I\\=\u0011\u0007-r\u0003'D\u0001-\u0015\ti3%\u0001\u0006d_:\u001cWO\u001d:f]RL!a\f\u0017\u0003\r\u0019+H/\u001e:f!\r\u0011\u0013gG\u0005\u0003e\r\u0012aa\u00149uS>t\u0017A\u0002\u001fj]&$h\bF\u00016!\r1\u0004aG\u0007\u0002\u0013\u0005\u0011\u0011N\\\u000b\u0002sA\u0019\u0001DO\u000e\n\u0005mZ!!B%oY\u0016$\u0018aA5oA\u0005)1\u000f[1qKV\tq#\u0001\u0004tQ\u0006\u0004X\rI\u0001 GJ,\u0017\r^3M_\u001eL7-\u00118e\u001b\u0006$XM]5bY&TX\r\u001a,bYV,GC\u0001\"O!\u0011\u00113)\u0012\u0016\n\u0005\u0011\u001b#A\u0002+va2,'GE\u0002G\u0011.3Aa\u0012\u0004\u0001\u000b\naAH]3gS:,W.\u001a8u}A\u0011!#S\u0005\u0003\u0015N\u0011qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\t\u0003%1K!!T\n\u0003\u0013%s\u0007*\u00198eY\u0016\u0014\b\"B(\u0007\u0001\u0004\u0001\u0016aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\bC\u0001\rR\u0013\t\u00116B\u0001\u0006BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002+B\u0011a+\u0018\b\u0003/n\u0003\"\u0001W\u0012\u000e\u0003eS!AW\u0010\u0002\rq\u0012xn\u001c;?\u0013\ta6%\u0001\u0004Qe\u0016$WMZ\u0005\u0003=~\u0013aa\u0015;sS:<'B\u0001/$Q\t\u0001\u0011\r\u0005\u0002cK6\t1M\u0003\u0002e\u001b\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\u0019\u001c'aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public final class HeadOptionStage<T>
extends GraphStageWithMaterializedValue<SinkShape<T>, Future<Option<T>>> {
    private final Inlet<T> in = Inlet$.MODULE$.apply("headOption.in");
    private final SinkShape<T> shape = SinkShape$.MODULE$.of(this.in());

    public Inlet<T> in() {
        return this.in;
    }

    @Override
    public SinkShape<T> shape() {
        return this.shape;
    }

    @Override
    public Tuple2<GraphStageLogic, Future<Option<T>>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        Promise p = Promise$.MODULE$.apply();
        return new Tuple2<GraphStageLogic, Future<Option<T>>>((GraphStageLogic)((Object)new InHandler(this, p){
            private final /* synthetic */ HeadOptionStage $outer;
            private final Promise p$2;

            public void preStart() {
                this.pull(this.$outer.in());
            }

            public void onPush() {
                this.p$2.trySuccess(Option$.MODULE$.apply(this.grab(this.$outer.in())));
                this.completeStage();
            }

            public void onUpstreamFinish() {
                this.p$2.trySuccess(None$.MODULE$);
                this.completeStage();
            }

            public void onUpstreamFailure(Throwable ex) {
                this.p$2.tryFailure(ex);
                this.failStage(ex);
            }

            public void postStop() {
                if (!this.p$2.isCompleted()) {
                    this.p$2.failure(new AbruptStageTerminationException(this));
                    return;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.p$2 = p$2;
                super($outer.shape());
                InHandler.$init$(this);
                this.setHandler($outer.in(), this);
            }
        }), p.future());
    }

    public String toString() {
        return "HeadOptionStage";
    }
}

