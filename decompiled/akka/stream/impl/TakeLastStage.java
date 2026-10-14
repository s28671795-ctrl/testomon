/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.SinkShape;
import akka.stream.SinkShape$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import scala.Tuple2;
import scala.collection.immutable.List;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Queue;
import scala.collection.mutable.Queue$;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005I4Q!\u0003\u0006\u0003\u001dAA\u0001\"\u000f\u0001\u0003\u0002\u0003\u0006IA\u000f\u0005\u0006{\u0001!\tA\u0010\u0005\b\u0005\u0002\u0011\r\u0011\"\u0001D\u0011\u00199\u0005\u0001)A\u0005\t\"9\u0001\n\u0001b\u0001\n\u0003J\u0005B\u0002&\u0001A\u0003%\u0001\u0004C\u0003L\u0001\u0011\u0005C\nC\u0003_\u0001\u0011\u0005sLA\u0007UC.,G*Y:u'R\fw-\u001a\u0006\u0003\u00171\tA![7qY*\u0011QBD\u0001\u0007gR\u0014X-Y7\u000b\u0003=\tA!Y6lCV\u0011\u0011CH\n\u0003\u0001I\u0001Ba\u0005\f\u0019W5\tAC\u0003\u0002\u0016\u0019\u0005)1\u000f^1hK&\u0011q\u0003\u0006\u0002 \u000fJ\f\u0007\u000f[*uC\u001e,w+\u001b;i\u001b\u0006$XM]5bY&TX\r\u001a,bYV,\u0007cA\r\u001b95\tA\"\u0003\u0002\u001c\u0019\tI1+\u001b8l'\"\f\u0007/\u001a\t\u0003;ya\u0001\u0001B\u0003 \u0001\t\u0007\u0011EA\u0001U\u0007\u0001\t\"A\t\u0015\u0011\u0005\r2S\"\u0001\u0013\u000b\u0003\u0015\nQa]2bY\u0006L!a\n\u0013\u0003\u000f9{G\u000f[5oOB\u00111%K\u0005\u0003U\u0011\u00121!\u00118z!\ras&M\u0007\u0002[)\u0011a\u0006J\u0001\u000bG>t7-\u001e:sK:$\u0018B\u0001\u0019.\u0005\u00191U\u000f^;sKB\u0019!g\u000e\u000f\u000e\u0003MR!\u0001N\u001b\u0002\u0013%lW.\u001e;bE2,'B\u0001\u001c%\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003qM\u00121aU3r\u0003\u0005q\u0007CA\u0012<\u0013\taDEA\u0002J]R\fa\u0001P5oSRtDCA B!\r\u0001\u0005\u0001H\u0007\u0002\u0015!)\u0011H\u0001a\u0001u\u0005\u0011\u0011N\\\u000b\u0002\tB\u0019\u0011$\u0012\u000f\n\u0005\u0019c!!B%oY\u0016$\u0018aA5oA\u0005)1\u000f[1qKV\t\u0001$\u0001\u0004tQ\u0006\u0004X\rI\u0001 GJ,\u0017\r^3M_\u001eL7-\u00118e\u001b\u0006$XM]5bY&TX\r\u001a,bYV,GCA'Z!\u0011\u0019c\nU\u0016\n\u0005=##A\u0002+va2,'GE\u0002R'Z3AAU\u0004\u0001!\naAH]3gS:,W.\u001a8u}A\u00111\u0003V\u0005\u0003+R\u0011qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\t\u0003']K!\u0001\u0017\u000b\u0003\u0013%s\u0007*\u00198eY\u0016\u0014\b\"\u0002.\b\u0001\u0004Y\u0016aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\bCA\r]\u0013\tiFB\u0001\u0006BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002AB\u0011\u0011\r\u001b\b\u0003E\u001a\u0004\"a\u0019\u0013\u000e\u0003\u0011T!!\u001a\u0011\u0002\rq\u0012xn\u001c;?\u0013\t9G%\u0001\u0004Qe\u0016$WMZ\u0005\u0003S*\u0014aa\u0015;sS:<'BA4%Q\t\u0001A\u000e\u0005\u0002na6\taN\u0003\u0002p\u001d\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005Et'aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public final class TakeLastStage<T>
extends GraphStageWithMaterializedValue<SinkShape<T>, Future<Seq<T>>> {
    public final int akka$stream$impl$TakeLastStage$$n;
    private final Inlet<T> in;
    private final SinkShape<T> shape;

    public Inlet<T> in() {
        return this.in;
    }

    @Override
    public SinkShape<T> shape() {
        return this.shape;
    }

    @Override
    public Tuple2<GraphStageLogic, Future<Seq<T>>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        Promise p = Promise$.MODULE$.apply();
        return new Tuple2<GraphStageLogic, Future<Seq<T>>>((GraphStageLogic)((Object)new InHandler(this, p){
            private final Queue<T> buffer;
            private int count;
            private final /* synthetic */ TakeLastStage $outer;
            private final Promise p$1;

            public void preStart() {
                this.pull(this.$outer.in());
            }

            public void onPush() {
                Object object;
                this.buffer.enqueue(this.grab(this.$outer.in()));
                if (this.count < this.$outer.akka$stream$impl$TakeLastStage$$n) {
                    ++this.count;
                    object = BoxedUnit.UNIT;
                } else {
                    object = this.buffer.dequeue();
                }
                this.pull(this.$outer.in());
            }

            public void onUpstreamFinish() {
                List<A> elements = this.buffer.toList();
                this.buffer.clear();
                this.p$1.trySuccess(elements);
                this.completeStage();
            }

            public void onUpstreamFailure(Throwable ex) {
                this.p$1.tryFailure(ex);
                this.failStage(ex);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.p$1 = p$1;
                super($outer.shape());
                InHandler.$init$(this);
                this.buffer = Queue$.MODULE$.empty();
                this.count = 0;
                this.setHandler($outer.in(), this);
            }
        }), p.future());
    }

    public String toString() {
        return "TakeLastStage";
    }

    public TakeLastStage(int n) {
        this.akka$stream$impl$TakeLastStage$$n = n;
        if (n <= 0) {
            throw new IllegalArgumentException("requirement failed: n must be greater than 0");
        }
        this.in = Inlet$.MODULE$.apply("takeLast.in");
        this.shape = SinkShape$.MODULE$.of(this.in());
    }
}

