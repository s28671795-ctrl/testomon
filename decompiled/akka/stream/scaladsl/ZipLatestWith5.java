/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape5;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith5$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function5;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005b\u0001B\f\u0019\u0001}A\u0001\u0002\u0013\u0001\u0003\u0006\u0004%\t!\u0013\u0005\t\u001b\u0002\u0011\t\u0011)A\u0005\u0015\"Aa\n\u0001BC\u0002\u0013\u0005q\n\u0003\u0005T\u0001\t\u0005\t\u0015!\u0003Q\u0011\u0015!\u0006\u0001\"\u0001V\u0011\u0015Q\u0006\u0001\"\u0011\\\u0011\u001dy\u0006A1A\u0005B\u0001Da!\u0019\u0001!\u0002\u00139\u0003\"\u00022\u0001\t\u0003\u0019\u0007bB4\u0001\u0005\u0004%\t\u0001\u001b\u0005\u0007Y\u0002\u0001\u000b\u0011B5\t\u000f5\u0004!\u0019!C\u0001]\"1\u0001\u000f\u0001Q\u0001\n=Dq!\u001d\u0001C\u0002\u0013\u0005!\u000f\u0003\u0004u\u0001\u0001\u0006Ia\u001d\u0005\bk\u0002\u0011\r\u0011\"\u0001w\u0011\u0019A\b\u0001)A\u0005o\"9\u0011\u0010\u0001b\u0001\n\u0003Q\bB\u0002?\u0001A\u0003%1\u0010C\u0003U\u0001\u0011\u0005Q\u0010\u0003\u0004\u0000\u0001\u0011\u0005\u0013\u0011\u0001\u0005\b\u0003\u001b\u0001A\u0011IA\b\u00059Q\u0016\u000e\u001d'bi\u0016\u001cHoV5uQVR!!\u0007\u000e\u0002\u0011M\u001c\u0017\r\\1eg2T!a\u0007\u000f\u0002\rM$(/Z1n\u0015\u0005i\u0012\u0001B1lW\u0006\u001c\u0001!F\u0004![ij\u0004i\u0011$\u0014\u0005\u0001\t\u0003c\u0001\u0012&O5\t1E\u0003\u0002%5\u0005)1\u000f^1hK&\u0011ae\t\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007\u0003\u0003\u0015*WebtHQ#\u000e\u0003iI!A\u000b\u000e\u0003\u0017\u0019\u000bg.\u00138TQ\u0006\u0004X-\u000e\t\u0003Y5b\u0001\u0001B\u0003/\u0001\t\u0007qF\u0001\u0002BcE\u0011\u0001G\u000e\t\u0003cQj\u0011A\r\u0006\u0002g\u0005)1oY1mC&\u0011QG\r\u0002\b\u001d>$\b.\u001b8h!\t\tt'\u0003\u00029e\t\u0019\u0011I\\=\u0011\u00051RD!B\u001e\u0001\u0005\u0004y#AA!3!\taS\bB\u0003?\u0001\t\u0007qF\u0001\u0002BgA\u0011A\u0006\u0011\u0003\u0006\u0003\u0002\u0011\ra\f\u0002\u0003\u0003R\u0002\"\u0001L\"\u0005\u000b\u0011\u0003!\u0019A\u0018\u0003\u0005\u0005+\u0004C\u0001\u0017G\t\u00159\u0005A1\u00010\u0005\u0005y\u0015A\u0002>jaB,'/F\u0001K!!\t4jK\u001d=\u007f\t+\u0015B\u0001'3\u0005%1UO\\2uS>tW'A\u0004{SB\u0004XM\u001d\u0011\u0002\u001b\u0015\fw-\u001a:D_6\u0004H.\u001a;f+\u0005\u0001\u0006CA\u0019R\u0013\t\u0011&GA\u0004C_>dW-\u00198\u0002\u001d\u0015\fw-\u001a:D_6\u0004H.\u001a;fA\u00051A(\u001b8jiz\"2A\u0016-Z!!9\u0006aK\u001d=\u007f\t+U\"\u0001\r\t\u000b!+\u0001\u0019\u0001&\t\u000b9+\u0001\u0019\u0001)\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001]!\tAS,\u0003\u0002_5\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003\u001d\naa\u001d5ba\u0016\u0004\u0013aA8viV\tA\rE\u0002)K\u0016K!A\u001a\u000e\u0003\r=+H\u000f\\3u\u0003\rIg\u000eM\u000b\u0002SB\u0019\u0001F[\u0016\n\u0005-T\"!B%oY\u0016$\u0018\u0001B5oa\u0001\n1!\u001b82+\u0005y\u0007c\u0001\u0015ks\u0005!\u0011N\\\u0019!\u0003\rIgNM\u000b\u0002gB\u0019\u0001F\u001b\u001f\u0002\t%t'\u0007I\u0001\u0004S:\u001cT#A<\u0011\u0007!Rw(\u0001\u0003j]N\u0002\u0013aA5oiU\t1\u0010E\u0002)U\n\u000bA!\u001b85AQ\u0011aK \u0005\u0006\u0011R\u0001\rAS\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u0002\u0004\u0005%\u0001c\u0001\u0012\u0002\u0006%\u0019\u0011qA\u0012\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDa!a\u0003\u0016\u0001\u0004a\u0016aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005E\u0001\u0003BA\n\u0003;i!!!\u0006\u000b\t\u0005]\u0011\u0011D\u0001\u0005Y\u0006twM\u0003\u0002\u0002\u001c\u0005!!.\u0019<b\u0013\u0011\ty\"!\u0006\u0003\rM#(/\u001b8h\u0001")
public class ZipLatestWith5<A1, A2, A3, A4, A5, O>
extends GraphStage<FanInShape5<A1, A2, A3, A4, A5, O>> {
    private final Function5<A1, A2, A3, A4, A5, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape5<A1, A2, A3, A4, A5, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;
    private final Inlet<A5> in4;

    public Function5<A1, A2, A3, A4, A5, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith5");
    }

    @Override
    public FanInShape5<A1, A2, A3, A4, A5, O> shape() {
        return this.shape;
    }

    public Outlet<O> out() {
        return ((FanInShape)this.shape()).out();
    }

    public Inlet<A1> in0() {
        return this.in0;
    }

    public Inlet<A2> in1() {
        return this.in1;
    }

    public Inlet<A3> in2() {
        return this.in2;
    }

    public Inlet<A4> in3() {
        return this.in3;
    }

    public Inlet<A5> in4() {
        return this.in4;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith5$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith5$$anon$$contextPropagation;
            private final $anon$7$ZipLatestInlet<A1> inlet0;
            private final $anon$7$ZipLatestInlet<A2> inlet1;
            private final $anon$7$ZipLatestInlet<A3> inlet2;
            private final $anon$7$ZipLatestInlet<A4> inlet3;
            private final $anon$7$ZipLatestInlet<A5> inlet4;
            private boolean akka$stream$scaladsl$ZipLatestWith5$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith5$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith5 $outer;

            public int akka$stream$scaladsl$ZipLatestWith5$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith5$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith5$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith5$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith5$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith5$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith5$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith5$$anon$$contextPropagation;
            }

            private $anon$7$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$7$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$7$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$7$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$7$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith5$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith5$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith5$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith5$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith5$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith5$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith5$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith5$$anon$$staleTupleValues = x$1;
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
                this.pull(this.$outer.in4());
            }

            public boolean akka$stream$scaladsl$ZipLatestWith5$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith5$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith5$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith5$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith5$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith5$$anon$$tryPullAllIfNeeded() {
                if (!this.hasBeenPulled(this.$outer.in0())) {
                    this.tryPull(this.$outer.in0());
                }
                if (!this.hasBeenPulled(this.$outer.in1())) {
                    this.tryPull(this.$outer.in1());
                }
                if (!this.hasBeenPulled(this.$outer.in2())) {
                    this.tryPull(this.$outer.in2());
                }
                if (!this.hasBeenPulled(this.$outer.in3())) {
                    this.tryPull(this.$outer.in3());
                }
                if (!this.hasBeenPulled(this.$outer.in4())) {
                    this.tryPull(this.$outer.in4());
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith5 akka$stream$scaladsl$ZipLatestWith5$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith5$$anon$$upstreamsToWait = 5;
                this.akka$stream$scaladsl$ZipLatestWith5$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$7$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$7$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$7$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$7$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$7$ZipLatestInlet<A5>(this, $outer.in4());
                this.akka$stream$scaladsl$ZipLatestWith5$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith5$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$7 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith5$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith5$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith5$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith5$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith5$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith5$$anon$$tryPullAllIfNeeded();
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        OutHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in0(), this.inlet0());
                this.setHandler($outer.in1(), this.inlet1());
                this.setHandler($outer.in2(), this.inlet2());
                this.setHandler($outer.in3(), this.inlet3());
                this.setHandler($outer.in4(), this.inlet4());
            }
        };
    }

    public String toString() {
        return "ZipLatestWith5";
    }

    public ZipLatestWith5(Function5<A1, A2, A3, A4, A5, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape5("ZipLatestWith5");
        this.in0 = ((FanInShape5)this.shape()).in0();
        this.in1 = ((FanInShape5)this.shape()).in1();
        this.in2 = ((FanInShape5)this.shape()).in2();
        this.in3 = ((FanInShape5)this.shape()).in3();
        this.in4 = ((FanInShape5)this.shape()).in4();
    }

    public ZipLatestWith5(Function5<A1, A2, A3, A4, A5, O> zipper) {
        this(zipper, true);
    }
}

