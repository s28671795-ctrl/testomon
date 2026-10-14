/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape3;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith3$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function3;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y4Aa\u0005\u000b\u00017!Aa\b\u0001BC\u0002\u0013\u0005q\b\u0003\u0005D\u0001\t\u0005\t\u0015!\u0003A\u0011!!\u0005A!b\u0001\n\u0003)\u0005\u0002C%\u0001\u0005\u0003\u0005\u000b\u0011\u0002$\t\u000b)\u0003A\u0011A&\t\u000bA\u0003A\u0011I)\t\u000fU\u0003!\u0019!C!-\"1q\u000b\u0001Q\u0001\n\rBQ\u0001\u0017\u0001\u0005\u0002eCq!\u0018\u0001C\u0002\u0013\u0005a\f\u0003\u0004c\u0001\u0001\u0006Ia\u0018\u0005\bG\u0002\u0011\r\u0011\"\u0001e\u0011\u00191\u0007\u0001)A\u0005K\"9q\r\u0001b\u0001\n\u0003A\u0007B\u00026\u0001A\u0003%\u0011\u000eC\u0003K\u0001\u0011\u00051\u000eC\u0003n\u0001\u0011\u0005c\u000eC\u0003u\u0001\u0011\u0005SO\u0001\b[SBd\u0015\r^3ti^KG\u000f[\u001a\u000b\u0005U1\u0012\u0001C:dC2\fGm\u001d7\u000b\u0005]A\u0012AB:ue\u0016\fWNC\u0001\u001a\u0003\u0011\t7n[1\u0004\u0001U)A$\u000b\u001c:yM\u0011\u0001!\b\t\u0004=\u0005\u001aS\"A\u0010\u000b\u0005\u00012\u0012!B:uC\u001e,\u0017B\u0001\u0012 \u0005)9%/\u00199i'R\fw-\u001a\t\u0007I\u0015:S\u0007O\u001e\u000e\u0003YI!A\n\f\u0003\u0017\u0019\u000bg.\u00138TQ\u0006\u0004Xm\r\t\u0003Q%b\u0001\u0001B\u0003+\u0001\t\u00071F\u0001\u0002BcE\u0011AF\r\t\u0003[Aj\u0011A\f\u0006\u0002_\u0005)1oY1mC&\u0011\u0011G\f\u0002\b\u001d>$\b.\u001b8h!\ti3'\u0003\u00025]\t\u0019\u0011I\\=\u0011\u0005!2D!B\u001c\u0001\u0005\u0004Y#AA!3!\tA\u0013\bB\u0003;\u0001\t\u00071F\u0001\u0002BgA\u0011\u0001\u0006\u0010\u0003\u0006{\u0001\u0011\ra\u000b\u0002\u0002\u001f\u00061!0\u001b9qKJ,\u0012\u0001\u0011\t\u0007[\u0005;S\u0007O\u001e\n\u0005\ts#!\u0003$v]\u000e$\u0018n\u001c84\u0003\u001dQ\u0018\u000e\u001d9fe\u0002\nQ\"Z1hKJ\u001cu.\u001c9mKR,W#\u0001$\u0011\u00055:\u0015B\u0001%/\u0005\u001d\u0011un\u001c7fC:\fa\"Z1hKJ\u001cu.\u001c9mKR,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0004\u0019:{\u0005CB'\u0001OUB4(D\u0001\u0015\u0011\u0015qT\u00011\u0001A\u0011\u0015!U\u00011\u0001G\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0002%B\u0011AeU\u0005\u0003)Z\u0011!\"\u0011;ue&\u0014W\u000f^3t\u0003\u0015\u0019\b.\u00199f+\u0005\u0019\u0013AB:iCB,\u0007%A\u0002pkR,\u0012A\u0017\t\u0004Im[\u0014B\u0001/\u0017\u0005\u0019yU\u000f\u001e7fi\u0006\u0019\u0011N\u001c\u0019\u0016\u0003}\u00032\u0001\n1(\u0013\t\tgCA\u0003J]2,G/\u0001\u0003j]B\u0002\u0013aA5ocU\tQ\rE\u0002%AV\nA!\u001b82A\u0005\u0019\u0011N\u001c\u001a\u0016\u0003%\u00042\u0001\n19\u0003\u0011IgN\r\u0011\u0015\u00051c\u0007\"\u0002 \u0011\u0001\u0004\u0001\u0015aC2sK\u0006$X\rT8hS\u000e$\"a\u001c:\u0011\u0005y\u0001\u0018BA9 \u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007\"B:\u0012\u0001\u0004\u0011\u0016aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003Y\u0004\"a\u001e?\u000e\u0003aT!!\u001f>\u0002\t1\fgn\u001a\u0006\u0002w\u0006!!.\u0019<b\u0013\ti\bP\u0001\u0004TiJLgn\u001a")
public class ZipLatestWith3<A1, A2, A3, O>
extends GraphStage<FanInShape3<A1, A2, A3, O>> {
    private final Function3<A1, A2, A3, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape3<A1, A2, A3, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;

    public Function3<A1, A2, A3, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith3");
    }

    @Override
    public FanInShape3<A1, A2, A3, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith3$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith3$$anon$$contextPropagation;
            private final $anon$3$ZipLatestInlet<A1> inlet0;
            private final $anon$3$ZipLatestInlet<A2> inlet1;
            private final $anon$3$ZipLatestInlet<A3> inlet2;
            private boolean akka$stream$scaladsl$ZipLatestWith3$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith3$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith3 $outer;

            public int akka$stream$scaladsl$ZipLatestWith3$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith3$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith3$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith3$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith3$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith3$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith3$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith3$$anon$$contextPropagation;
            }

            private $anon$3$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$3$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$3$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith3$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith3$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith3$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith3$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith3$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith3$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith3$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith3$$anon$$staleTupleValues = x$1;
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
            }

            public boolean akka$stream$scaladsl$ZipLatestWith3$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith3$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith3$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith3$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith3$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith3$$anon$$tryPullAllIfNeeded() {
                if (!this.hasBeenPulled(this.$outer.in0())) {
                    this.tryPull(this.$outer.in0());
                }
                if (!this.hasBeenPulled(this.$outer.in1())) {
                    this.tryPull(this.$outer.in1());
                }
                if (!this.hasBeenPulled(this.$outer.in2())) {
                    this.tryPull(this.$outer.in2());
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith3 akka$stream$scaladsl$ZipLatestWith3$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith3$$anon$$upstreamsToWait = 3;
                this.akka$stream$scaladsl$ZipLatestWith3$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$3$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$3$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$3$ZipLatestInlet<A3>(this, $outer.in2());
                this.akka$stream$scaladsl$ZipLatestWith3$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith3$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$3 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith3$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith3$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith3$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith3$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith3$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith3$$anon$$tryPullAllIfNeeded();
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
            }
        };
    }

    public String toString() {
        return "ZipLatestWith3";
    }

    public ZipLatestWith3(Function3<A1, A2, A3, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape3("ZipLatestWith3");
        this.in0 = ((FanInShape3)this.shape()).in0();
        this.in1 = ((FanInShape3)this.shape()).in1();
        this.in2 = ((FanInShape3)this.shape()).in2();
    }

    public ZipLatestWith3(Function3<A1, A2, A3, O> zipper) {
        this(zipper, true);
    }
}

