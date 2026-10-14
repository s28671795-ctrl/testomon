/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape8;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith8$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function8;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]c\u0001B\u000f\u001f\u0001\u0015B\u0001b\u0016\u0001\u0003\u0006\u0004%\t\u0001\u0017\u0005\t9\u0002\u0011\t\u0011)A\u00053\"AQ\f\u0001BC\u0002\u0013\u0005a\f\u0003\u0005c\u0001\t\u0005\t\u0015!\u0003`\u0011\u0015\u0019\u0007\u0001\"\u0001e\u0011\u0015I\u0007\u0001\"\u0011k\u0011\u001dq\u0007A1A\u0005B=Da\u0001\u001d\u0001!\u0002\u0013i\u0003\"B9\u0001\t\u0003\u0011\bb\u0002<\u0001\u0005\u0004%\ta\u001e\u0005\u0007w\u0002\u0001\u000b\u0011\u0002=\t\u000fq\u0004!\u0019!C\u0001{\"1q\u0010\u0001Q\u0001\nyD\u0011\"!\u0001\u0001\u0005\u0004%\t!a\u0001\t\u0011\u0005\u001d\u0001\u0001)A\u0005\u0003\u000bA\u0011\"!\u0003\u0001\u0005\u0004%\t!a\u0003\t\u0011\u0005=\u0001\u0001)A\u0005\u0003\u001bA\u0011\"!\u0005\u0001\u0005\u0004%\t!a\u0005\t\u0011\u0005]\u0001\u0001)A\u0005\u0003+A\u0011\"!\u0007\u0001\u0005\u0004%\t!a\u0007\t\u0011\u0005}\u0001\u0001)A\u0005\u0003;A\u0011\"!\t\u0001\u0005\u0004%\t!a\t\t\u0011\u0005\u001d\u0002\u0001)A\u0005\u0003KA\u0011\"!\u000b\u0001\u0005\u0004%\t!a\u000b\t\u0011\u0005=\u0002\u0001)A\u0005\u0003[Aaa\u0019\u0001\u0005\u0002\u0005E\u0002bBA\u001b\u0001\u0011\u0005\u0013q\u0007\u0005\b\u0003\u0007\u0002A\u0011IA#\u00059Q\u0016\u000e\u001d'bi\u0016\u001cHoV5uQbR!a\b\u0011\u0002\u0011M\u001c\u0017\r\\1eg2T!!\t\u0012\u0002\rM$(/Z1n\u0015\u0005\u0019\u0013\u0001B1lW\u0006\u001c\u0001!\u0006\u0006'g\u0001\u001be)\u0013'P%V\u001b\"\u0001A\u0014\u0011\u0007!ZS&D\u0001*\u0015\tQ\u0003%A\u0003ti\u0006<W-\u0003\u0002-S\tQqI]1qQN#\u0018mZ3\u0011\u00179z\u0013g\u0010\"F\u0011.s\u0015\u000bV\u0007\u0002A%\u0011\u0001\u0007\t\u0002\f\r\u0006t\u0017J\\*iCB,\u0007\b\u0005\u00023g1\u0001A!\u0002\u001b\u0001\u0005\u0004)$AA!2#\t1D\b\u0005\u00028u5\t\u0001HC\u0001:\u0003\u0015\u00198-\u00197b\u0013\tY\u0004HA\u0004O_RD\u0017N\\4\u0011\u0005]j\u0014B\u0001 9\u0005\r\te.\u001f\t\u0003e\u0001#Q!\u0011\u0001C\u0002U\u0012!!\u0011\u001a\u0011\u0005I\u001aE!\u0002#\u0001\u0005\u0004)$AA!4!\t\u0011d\tB\u0003H\u0001\t\u0007QG\u0001\u0002BiA\u0011!'\u0013\u0003\u0006\u0015\u0002\u0011\r!\u000e\u0002\u0003\u0003V\u0002\"A\r'\u0005\u000b5\u0003!\u0019A\u001b\u0003\u0005\u00053\u0004C\u0001\u001aP\t\u0015\u0001\u0006A1\u00016\u0005\t\tu\u0007\u0005\u00023%\u0012)1\u000b\u0001b\u0001k\t\u0011\u0011\t\u000f\t\u0003eU#QA\u0016\u0001C\u0002U\u0012\u0011aT\u0001\u0007u&\u0004\b/\u001a:\u0016\u0003e\u00032b\u000e.2\u007f\t+\u0005j\u0013(R)&\u00111\f\u000f\u0002\n\rVt7\r^5p]b\nqA_5qa\u0016\u0014\b%A\u0007fC\u001e,'oQ8na2,G/Z\u000b\u0002?B\u0011q\u0007Y\u0005\u0003Cb\u0012qAQ8pY\u0016\fg.\u0001\bfC\u001e,'oQ8na2,G/\u001a\u0011\u0002\rqJg.\u001b;?)\r)w\r\u001b\t\fM\u0002\ttHQ#I\u0017:\u000bF+D\u0001\u001f\u0011\u00159V\u00011\u0001Z\u0011\u0015iV\u00011\u0001`\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0002WB\u0011a\u0006\\\u0005\u0003[\u0002\u0012!\"\u0011;ue&\u0014W\u000f^3t\u0003\u0015\u0019\b.\u00199f+\u0005i\u0013AB:iCB,\u0007%A\u0002pkR,\u0012a\u001d\t\u0004]Q$\u0016BA;!\u0005\u0019yU\u000f\u001e7fi\u0006\u0019\u0011N\u001c\u0019\u0016\u0003a\u00042AL=2\u0013\tQ\bEA\u0003J]2,G/\u0001\u0003j]B\u0002\u0013aA5ocU\ta\u0010E\u0002/s~\nA!\u001b82A\u0005\u0019\u0011N\u001c\u001a\u0016\u0005\u0005\u0015\u0001c\u0001\u0018z\u0005\u0006!\u0011N\u001c\u001a!\u0003\rIgnM\u000b\u0003\u0003\u001b\u00012AL=F\u0003\u0011Ign\r\u0011\u0002\u0007%tG'\u0006\u0002\u0002\u0016A\u0019a&\u001f%\u0002\t%tG\u0007I\u0001\u0004S:,TCAA\u000f!\rq\u0013pS\u0001\u0005S:,\u0004%A\u0002j]Z*\"!!\n\u0011\u00079Jh*\u0001\u0003j]Z\u0002\u0013aA5ooU\u0011\u0011Q\u0006\t\u0004]e\f\u0016\u0001B5oo\u0001\"2!ZA\u001a\u0011\u00159&\u00041\u0001Z\u0003-\u0019'/Z1uK2{w-[2\u0015\t\u0005e\u0012q\b\t\u0004Q\u0005m\u0012bAA\u001fS\tyqI]1qQN#\u0018mZ3M_\u001eL7\r\u0003\u0004\u0002Bm\u0001\ra[\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm]\u0001\ti>\u001cFO]5oOR\u0011\u0011q\t\t\u0005\u0003\u0013\n\u0019&\u0004\u0002\u0002L)!\u0011QJA(\u0003\u0011a\u0017M\\4\u000b\u0005\u0005E\u0013\u0001\u00026bm\u0006LA!!\u0016\u0002L\t11\u000b\u001e:j]\u001e\u0004")
public class ZipLatestWith8<A1, A2, A3, A4, A5, A6, A7, A8, O>
extends GraphStage<FanInShape8<A1, A2, A3, A4, A5, A6, A7, A8, O>> {
    private final Function8<A1, A2, A3, A4, A5, A6, A7, A8, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape8<A1, A2, A3, A4, A5, A6, A7, A8, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;
    private final Inlet<A5> in4;
    private final Inlet<A6> in5;
    private final Inlet<A7> in6;
    private final Inlet<A8> in7;

    public Function8<A1, A2, A3, A4, A5, A6, A7, A8, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith8");
    }

    @Override
    public FanInShape8<A1, A2, A3, A4, A5, A6, A7, A8, O> shape() {
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

    public Inlet<A7> in6() {
        return this.in6;
    }

    public Inlet<A8> in7() {
        return this.in7;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith8$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith8$$anon$$contextPropagation;
            private final $anon$13$ZipLatestInlet<A1> inlet0;
            private final $anon$13$ZipLatestInlet<A2> inlet1;
            private final $anon$13$ZipLatestInlet<A3> inlet2;
            private final $anon$13$ZipLatestInlet<A4> inlet3;
            private final $anon$13$ZipLatestInlet<A5> inlet4;
            private final $anon$13$ZipLatestInlet<A6> inlet5;
            private final $anon$13$ZipLatestInlet<A7> inlet6;
            private final $anon$13$ZipLatestInlet<A8> inlet7;
            private boolean akka$stream$scaladsl$ZipLatestWith8$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith8$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith8 $outer;

            public int akka$stream$scaladsl$ZipLatestWith8$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith8$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith8$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith8$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith8$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith8$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith8$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith8$$anon$$contextPropagation;
            }

            private $anon$13$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$13$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$13$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$13$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$13$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$13$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$13$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$13$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith8$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith8$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith8$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith8$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith8$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith8$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith8$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith8$$anon$$staleTupleValues = x$1;
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
                this.pull(this.$outer.in4());
                this.pull(this.$outer.in5());
                this.pull(this.$outer.in6());
                this.pull(this.$outer.in7());
            }

            public boolean akka$stream$scaladsl$ZipLatestWith8$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith8$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith8$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith8$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith8$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith8$$anon$$tryPullAllIfNeeded() {
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
                }
                if (!this.hasBeenPulled(this.$outer.in6())) {
                    this.tryPull(this.$outer.in6());
                }
                if (!this.hasBeenPulled(this.$outer.in7())) {
                    this.tryPull(this.$outer.in7());
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith8 akka$stream$scaladsl$ZipLatestWith8$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith8$$anon$$upstreamsToWait = 8;
                this.akka$stream$scaladsl$ZipLatestWith8$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$13$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$13$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$13$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$13$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$13$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$13$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$13$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$13$ZipLatestInlet<A8>(this, $outer.in7());
                this.akka$stream$scaladsl$ZipLatestWith8$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith8$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$13 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith8$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith8$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith8$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith8$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith8$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith8$$anon$$tryPullAllIfNeeded();
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
                this.setHandler($outer.in6(), this.inlet6());
                this.setHandler($outer.in7(), this.inlet7());
            }
        };
    }

    public String toString() {
        return "ZipLatestWith8";
    }

    public ZipLatestWith8(Function8<A1, A2, A3, A4, A5, A6, A7, A8, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape8("ZipLatestWith8");
        this.in0 = ((FanInShape8)this.shape()).in0();
        this.in1 = ((FanInShape8)this.shape()).in1();
        this.in2 = ((FanInShape8)this.shape()).in2();
        this.in3 = ((FanInShape8)this.shape()).in3();
        this.in4 = ((FanInShape8)this.shape()).in4();
        this.in5 = ((FanInShape8)this.shape()).in5();
        this.in6 = ((FanInShape8)this.shape()).in6();
        this.in7 = ((FanInShape8)this.shape()).in7();
    }

    public ZipLatestWith8(Function8<A1, A2, A3, A4, A5, A6, A7, A8, O> zipper) {
        this(zipper, true);
    }
}

