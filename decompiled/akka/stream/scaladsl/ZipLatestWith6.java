/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape6;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith6$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function6;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Mb\u0001B\r\u001b\u0001\u0005B\u0001\"\u0014\u0001\u0003\u0006\u0004%\tA\u0014\u0005\t%\u0002\u0011\t\u0011)A\u0005\u001f\"A1\u000b\u0001BC\u0002\u0013\u0005A\u000b\u0003\u0005Y\u0001\t\u0005\t\u0015!\u0003V\u0011\u0015I\u0006\u0001\"\u0001[\u0011\u0015y\u0006\u0001\"\u0011a\u0011\u001d!\u0007A1A\u0005B\u0015DaA\u001a\u0001!\u0002\u0013I\u0003\"B4\u0001\t\u0003A\u0007b\u00027\u0001\u0005\u0004%\t!\u001c\u0005\u0007c\u0002\u0001\u000b\u0011\u00028\t\u000fI\u0004!\u0019!C\u0001g\"1Q\u000f\u0001Q\u0001\nQDqA\u001e\u0001C\u0002\u0013\u0005q\u000f\u0003\u0004z\u0001\u0001\u0006I\u0001\u001f\u0005\bu\u0002\u0011\r\u0011\"\u0001|\u0011\u0019i\b\u0001)A\u0005y\"9a\u0010\u0001b\u0001\n\u0003y\b\u0002CA\u0002\u0001\u0001\u0006I!!\u0001\t\u0013\u0005\u0015\u0001A1A\u0005\u0002\u0005\u001d\u0001\u0002CA\u0006\u0001\u0001\u0006I!!\u0003\t\re\u0003A\u0011AA\u0007\u0011\u001d\t\t\u0002\u0001C!\u0003'Aq!a\b\u0001\t\u0003\n\tC\u0001\b[SBd\u0015\r^3ti^KG\u000f\u001b\u001c\u000b\u0005ma\u0012\u0001C:dC2\fGm\u001d7\u000b\u0005uq\u0012AB:ue\u0016\fWNC\u0001 \u0003\u0011\t7n[1\u0004\u0001UA!e\f\u001f@\u0005\u0016C5j\u0005\u0002\u0001GA\u0019AeJ\u0015\u000e\u0003\u0015R!A\n\u000f\u0002\u000bM$\u0018mZ3\n\u0005!*#AC$sCBD7\u000b^1hKBI!fK\u0017<}\u0005#uIS\u0007\u00029%\u0011A\u0006\b\u0002\f\r\u0006t\u0017J\\*iCB,g\u0007\u0005\u0002/_1\u0001A!\u0002\u0019\u0001\u0005\u0004\t$AA!2#\t\u0011\u0004\b\u0005\u00024m5\tAGC\u00016\u0003\u0015\u00198-\u00197b\u0013\t9DGA\u0004O_RD\u0017N\\4\u0011\u0005MJ\u0014B\u0001\u001e5\u0005\r\te.\u001f\t\u0003]q\"Q!\u0010\u0001C\u0002E\u0012!!\u0011\u001a\u0011\u00059zD!\u0002!\u0001\u0005\u0004\t$AA!4!\tq#\tB\u0003D\u0001\t\u0007\u0011G\u0001\u0002BiA\u0011a&\u0012\u0003\u0006\r\u0002\u0011\r!\r\u0002\u0003\u0003V\u0002\"A\f%\u0005\u000b%\u0003!\u0019A\u0019\u0003\u0005\u00053\u0004C\u0001\u0018L\t\u0015a\u0005A1\u00012\u0005\u0005y\u0015A\u0002>jaB,'/F\u0001P!%\u0019\u0004+L\u001e?\u0003\u0012;%*\u0003\u0002Ri\tIa)\u001e8di&|gNN\u0001\bu&\u0004\b/\u001a:!\u00035)\u0017mZ3s\u0007>l\u0007\u000f\\3uKV\tQ\u000b\u0005\u00024-&\u0011q\u000b\u000e\u0002\b\u0005>|G.Z1o\u00039)\u0017mZ3s\u0007>l\u0007\u000f\\3uK\u0002\na\u0001P5oSRtDcA.^=BIA\fA\u0017<}\u0005#uIS\u0007\u00025!)Q*\u0002a\u0001\u001f\")1+\u0002a\u0001+\u0006\t\u0012N\\5uS\u0006d\u0017\t\u001e;sS\n,H/Z:\u0016\u0003\u0005\u0004\"A\u000b2\n\u0005\rd\"AC!uiJL'-\u001e;fg\u0006)1\u000f[1qKV\t\u0011&\u0001\u0004tQ\u0006\u0004X\rI\u0001\u0004_V$X#A5\u0011\u0007)R'*\u0003\u0002l9\t1q*\u001e;mKR\f1!\u001b81+\u0005q\u0007c\u0001\u0016p[%\u0011\u0001\u000f\b\u0002\u0006\u0013:dW\r^\u0001\u0005S:\u0004\u0004%A\u0002j]F*\u0012\u0001\u001e\t\u0004U=\\\u0014\u0001B5oc\u0001\n1!\u001b83+\u0005A\bc\u0001\u0016p}\u0005!\u0011N\u001c\u001a!\u0003\rIgnM\u000b\u0002yB\u0019!f\\!\u0002\t%t7\u0007I\u0001\u0004S:$TCAA\u0001!\rQs\u000eR\u0001\u0005S:$\u0004%A\u0002j]V*\"!!\u0003\u0011\u0007)zw)\u0001\u0003j]V\u0002CcA.\u0002\u0010!)QJ\u0006a\u0001\u001f\u0006Y1M]3bi\u0016dunZ5d)\u0011\t)\"a\u0007\u0011\u0007\u0011\n9\"C\u0002\u0002\u001a\u0015\u0012qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u0007\u0003;9\u0002\u0019A1\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a\t\u0011\t\u0005\u0015\u0012qF\u0007\u0003\u0003OQA!!\u000b\u0002,\u0005!A.\u00198h\u0015\t\ti#\u0001\u0003kCZ\f\u0017\u0002BA\u0019\u0003O\u0011aa\u0015;sS:<\u0007")
public class ZipLatestWith6<A1, A2, A3, A4, A5, A6, O>
extends GraphStage<FanInShape6<A1, A2, A3, A4, A5, A6, O>> {
    private final Function6<A1, A2, A3, A4, A5, A6, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape6<A1, A2, A3, A4, A5, A6, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;
    private final Inlet<A5> in4;
    private final Inlet<A6> in5;

    public Function6<A1, A2, A3, A4, A5, A6, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith6");
    }

    @Override
    public FanInShape6<A1, A2, A3, A4, A5, A6, O> shape() {
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

    public Inlet<A6> in5() {
        return this.in5;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith6$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith6$$anon$$contextPropagation;
            private final $anon$9$ZipLatestInlet<A1> inlet0;
            private final $anon$9$ZipLatestInlet<A2> inlet1;
            private final $anon$9$ZipLatestInlet<A3> inlet2;
            private final $anon$9$ZipLatestInlet<A4> inlet3;
            private final $anon$9$ZipLatestInlet<A5> inlet4;
            private final $anon$9$ZipLatestInlet<A6> inlet5;
            private boolean akka$stream$scaladsl$ZipLatestWith6$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith6$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith6 $outer;

            public int akka$stream$scaladsl$ZipLatestWith6$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith6$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith6$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith6$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith6$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith6$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith6$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith6$$anon$$contextPropagation;
            }

            private $anon$9$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$9$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$9$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$9$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$9$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$9$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith6$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith6$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith6$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith6$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith6$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith6$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith6$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith6$$anon$$staleTupleValues = x$1;
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
                this.pull(this.$outer.in4());
                this.pull(this.$outer.in5());
            }

            public boolean akka$stream$scaladsl$ZipLatestWith6$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith6$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith6$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith6$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith6$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith6$$anon$$tryPullAllIfNeeded() {
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
                }
                if (!this.hasBeenPulled(this.$outer.in5())) {
                    this.tryPull(this.$outer.in5());
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith6 akka$stream$scaladsl$ZipLatestWith6$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith6$$anon$$upstreamsToWait = 6;
                this.akka$stream$scaladsl$ZipLatestWith6$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$9$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$9$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$9$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$9$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$9$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$9$ZipLatestInlet<A6>(this, $outer.in5());
                this.akka$stream$scaladsl$ZipLatestWith6$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith6$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$9 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith6$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith6$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith6$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith6$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith6$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith6$$anon$$tryPullAllIfNeeded();
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
                this.setHandler($outer.in5(), this.inlet5());
            }
        };
    }

    public String toString() {
        return "ZipLatestWith6";
    }

    public ZipLatestWith6(Function6<A1, A2, A3, A4, A5, A6, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape6("ZipLatestWith6");
        this.in0 = ((FanInShape6)this.shape()).in0();
        this.in1 = ((FanInShape6)this.shape()).in1();
        this.in2 = ((FanInShape6)this.shape()).in2();
        this.in3 = ((FanInShape6)this.shape()).in3();
        this.in4 = ((FanInShape6)this.shape()).in4();
        this.in5 = ((FanInShape6)this.shape()).in5();
    }

    public ZipLatestWith6(Function6<A1, A2, A3, A4, A5, A6, O> zipper) {
        this(zipper, true);
    }
}

