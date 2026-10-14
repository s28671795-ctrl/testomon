/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape9;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith9$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function9;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005%d\u0001B\u0010!\u0001\u001dB\u0001\u0002\u0018\u0001\u0003\u0006\u0004%\t!\u0018\u0005\tC\u0002\u0011\t\u0011)A\u0005=\"A!\r\u0001BC\u0002\u0013\u00051\r\u0003\u0005h\u0001\t\u0005\t\u0015!\u0003e\u0011\u0015A\u0007\u0001\"\u0001j\u0011\u0015q\u0007\u0001\"\u0011p\u0011\u001d\u0019\bA1A\u0005BQDa!\u001e\u0001!\u0002\u0013y\u0003\"\u0002<\u0001\t\u00039\bbB>\u0001\u0005\u0004%\t\u0001 \u0005\b\u0003\u0003\u0001\u0001\u0015!\u0003~\u0011%\t\u0019\u0001\u0001b\u0001\n\u0003\t)\u0001\u0003\u0005\u0002\n\u0001\u0001\u000b\u0011BA\u0004\u0011%\tY\u0001\u0001b\u0001\n\u0003\ti\u0001\u0003\u0005\u0002\u0012\u0001\u0001\u000b\u0011BA\b\u0011%\t\u0019\u0002\u0001b\u0001\n\u0003\t)\u0002\u0003\u0005\u0002\u001a\u0001\u0001\u000b\u0011BA\f\u0011%\tY\u0002\u0001b\u0001\n\u0003\ti\u0002\u0003\u0005\u0002\"\u0001\u0001\u000b\u0011BA\u0010\u0011%\t\u0019\u0003\u0001b\u0001\n\u0003\t)\u0003\u0003\u0005\u0002*\u0001\u0001\u000b\u0011BA\u0014\u0011%\tY\u0003\u0001b\u0001\n\u0003\ti\u0003\u0003\u0005\u00022\u0001\u0001\u000b\u0011BA\u0018\u0011%\t\u0019\u0004\u0001b\u0001\n\u0003\t)\u0004\u0003\u0005\u0002:\u0001\u0001\u000b\u0011BA\u001c\u0011%\tY\u0004\u0001b\u0001\n\u0003\ti\u0004\u0003\u0005\u0002B\u0001\u0001\u000b\u0011BA \u0011\u0019A\u0007\u0001\"\u0001\u0002D!9\u0011q\t\u0001\u0005B\u0005%\u0003bBA+\u0001\u0011\u0005\u0013q\u000b\u0002\u000f5&\u0004H*\u0019;fgR<\u0016\u000e\u001e5:\u0015\t\t#%\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\t\u0019C%\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002K\u0005!\u0011m[6b\u0007\u0001)2\u0002K\u001bC\u000b\"[e*\u0015+X5N\u0011\u0001!\u000b\t\u0004U5zS\"A\u0016\u000b\u00051\u0012\u0013!B:uC\u001e,\u0017B\u0001\u0018,\u0005)9%/\u00199i'R\fw-\u001a\t\raE\u001a\u0014\tR$K\u001bB\u001bf+W\u0007\u0002E%\u0011!G\t\u0002\f\r\u0006t\u0017J\\*iCB,\u0017\b\u0005\u00025k1\u0001A!\u0002\u001c\u0001\u0005\u00049$AA!2#\tAd\b\u0005\u0002:y5\t!HC\u0001<\u0003\u0015\u00198-\u00197b\u0013\ti$HA\u0004O_RD\u0017N\\4\u0011\u0005ez\u0014B\u0001!;\u0005\r\te.\u001f\t\u0003i\t#Qa\u0011\u0001C\u0002]\u0012!!\u0011\u001a\u0011\u0005Q*E!\u0002$\u0001\u0005\u00049$AA!4!\t!\u0004\nB\u0003J\u0001\t\u0007qG\u0001\u0002BiA\u0011Ag\u0013\u0003\u0006\u0019\u0002\u0011\ra\u000e\u0002\u0003\u0003V\u0002\"\u0001\u000e(\u0005\u000b=\u0003!\u0019A\u001c\u0003\u0005\u00053\u0004C\u0001\u001bR\t\u0015\u0011\u0006A1\u00018\u0005\t\tu\u0007\u0005\u00025)\u0012)Q\u000b\u0001b\u0001o\t\u0011\u0011\t\u000f\t\u0003i]#Q\u0001\u0017\u0001C\u0002]\u0012!!Q\u001d\u0011\u0005QRF!B.\u0001\u0005\u00049$!A(\u0002\riL\u0007\u000f]3s+\u0005q\u0006\u0003D\u001d`g\u0005#uIS'Q'ZK\u0016B\u00011;\u0005%1UO\\2uS>t\u0017(A\u0004{SB\u0004XM\u001d\u0011\u0002\u001b\u0015\fw-\u001a:D_6\u0004H.\u001a;f+\u0005!\u0007CA\u001df\u0013\t1'HA\u0004C_>dW-\u00198\u0002\u001d\u0015\fw-\u001a:D_6\u0004H.\u001a;fA\u00051A(\u001b8jiz\"2A\u001b7n!1Y\u0007aM!E\u000f*k\u0005k\u0015,Z\u001b\u0005\u0001\u0003\"\u0002/\u0006\u0001\u0004q\u0006\"\u00022\u0006\u0001\u0004!\u0017!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\t\u0001\u000f\u0005\u00021c&\u0011!O\t\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!B:iCB,W#A\u0018\u0002\rMD\u0017\r]3!\u0003\ryW\u000f^\u000b\u0002qB\u0019\u0001'_-\n\u0005i\u0014#AB(vi2,G/A\u0002j]B*\u0012! \t\u0004ay\u001c\u0014BA@#\u0005\u0015Ie\u000e\\3u\u0003\u0011Ig\u000e\r\u0011\u0002\u0007%t\u0017'\u0006\u0002\u0002\bA\u0019\u0001G`!\u0002\t%t\u0017\u0007I\u0001\u0004S:\u0014TCAA\b!\r\u0001d\u0010R\u0001\u0005S:\u0014\u0004%A\u0002j]N*\"!a\u0006\u0011\u0007Arx)\u0001\u0003j]N\u0002\u0013aA5oiU\u0011\u0011q\u0004\t\u0004ayT\u0015\u0001B5oi\u0001\n1!\u001b86+\t\t9\u0003E\u00021}6\u000bA!\u001b86A\u0005\u0019\u0011N\u001c\u001c\u0016\u0005\u0005=\u0002c\u0001\u0019\u007f!\u0006!\u0011N\u001c\u001c!\u0003\rIgnN\u000b\u0003\u0003o\u00012\u0001\r@T\u0003\u0011Ign\u000e\u0011\u0002\u0007%t\u0007(\u0006\u0002\u0002@A\u0019\u0001G ,\u0002\t%t\u0007\b\t\u000b\u0004U\u0006\u0015\u0003\"\u0002/\u001d\u0001\u0004q\u0016aC2sK\u0006$X\rT8hS\u000e$B!a\u0013\u0002RA\u0019!&!\u0014\n\u0007\u0005=3FA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0019\t\u0019&\ba\u0001a\u0006\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fg\u0006AAo\\*ue&tw\r\u0006\u0002\u0002ZA!\u00111LA3\u001b\t\tiF\u0003\u0003\u0002`\u0005\u0005\u0014\u0001\u00027b]\u001eT!!a\u0019\u0002\t)\fg/Y\u0005\u0005\u0003O\niF\u0001\u0004TiJLgn\u001a")
public class ZipLatestWith9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O>
extends GraphStage<FanInShape9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O>> {
    private final Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;
    private final Inlet<A5> in4;
    private final Inlet<A6> in5;
    private final Inlet<A7> in6;
    private final Inlet<A8> in7;
    private final Inlet<A9> in8;

    public Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith9");
    }

    @Override
    public FanInShape9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O> shape() {
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

    public Inlet<A9> in8() {
        return this.in8;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith9$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith9$$anon$$contextPropagation;
            private final $anon$15$ZipLatestInlet<A1> inlet0;
            private final $anon$15$ZipLatestInlet<A2> inlet1;
            private final $anon$15$ZipLatestInlet<A3> inlet2;
            private final $anon$15$ZipLatestInlet<A4> inlet3;
            private final $anon$15$ZipLatestInlet<A5> inlet4;
            private final $anon$15$ZipLatestInlet<A6> inlet5;
            private final $anon$15$ZipLatestInlet<A7> inlet6;
            private final $anon$15$ZipLatestInlet<A8> inlet7;
            private final $anon$15$ZipLatestInlet<A9> inlet8;
            private boolean akka$stream$scaladsl$ZipLatestWith9$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith9$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith9 $outer;

            public int akka$stream$scaladsl$ZipLatestWith9$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith9$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith9$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith9$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith9$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith9$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith9$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith9$$anon$$contextPropagation;
            }

            private $anon$15$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$15$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$15$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$15$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$15$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$15$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$15$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$15$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            private $anon$15$ZipLatestInlet<A9> inlet8() {
                return this.inlet8;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith9$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith9$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith9$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith9$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith9$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith9$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith9$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith9$$anon$$staleTupleValues = x$1;
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
                this.pull(this.$outer.in8());
            }

            public boolean akka$stream$scaladsl$ZipLatestWith9$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue() && this.inlet8().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith9$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith9$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value(), this.inlet8().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith9$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith9$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith9$$anon$$tryPullAllIfNeeded() {
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
                }
                if (!this.hasBeenPulled(this.$outer.in8())) {
                    this.tryPull(this.$outer.in8());
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith9 akka$stream$scaladsl$ZipLatestWith9$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith9$$anon$$upstreamsToWait = 9;
                this.akka$stream$scaladsl$ZipLatestWith9$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$15$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$15$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$15$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$15$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$15$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$15$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$15$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$15$ZipLatestInlet<A8>(this, $outer.in7());
                this.inlet8 = new $anon$15$ZipLatestInlet<A9>(this, $outer.in8());
                this.akka$stream$scaladsl$ZipLatestWith9$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith9$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$15 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith9$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith9$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith9$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith9$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith9$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith9$$anon$$tryPullAllIfNeeded();
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
                this.setHandler($outer.in8(), this.inlet8());
            }
        };
    }

    public String toString() {
        return "ZipLatestWith9";
    }

    public ZipLatestWith9(Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape9("ZipLatestWith9");
        this.in0 = ((FanInShape9)this.shape()).in0();
        this.in1 = ((FanInShape9)this.shape()).in1();
        this.in2 = ((FanInShape9)this.shape()).in2();
        this.in3 = ((FanInShape9)this.shape()).in3();
        this.in4 = ((FanInShape9)this.shape()).in4();
        this.in5 = ((FanInShape9)this.shape()).in5();
        this.in6 = ((FanInShape9)this.shape()).in6();
        this.in7 = ((FanInShape9)this.shape()).in7();
        this.in8 = ((FanInShape9)this.shape()).in8();
    }

    public ZipLatestWith9(Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O> zipper) {
        this(zipper, true);
    }
}

