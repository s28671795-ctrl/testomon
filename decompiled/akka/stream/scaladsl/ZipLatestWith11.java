/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape11;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith11$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function11;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00055e\u0001B\u0012%\u0001-B\u0001B\u001a\u0001\u0003\u0006\u0004%\ta\u001a\u0005\tW\u0002\u0011\t\u0011)A\u0005Q\"AA\u000e\u0001BC\u0002\u0013\u0005Q\u000e\u0003\u0005r\u0001\t\u0005\t\u0015!\u0003o\u0011\u0015\u0011\b\u0001\"\u0001t\u0011\u0015A\b\u0001\"\u0011z\u0011\u001di\bA1A\u0005ByDaa \u0001!\u0002\u0013\u0019\u0004bBA\u0001\u0001\u0011\u0005\u00111\u0001\u0005\n\u0003\u0017\u0001!\u0019!C\u0001\u0003\u001bA\u0001\"!\u0006\u0001A\u0003%\u0011q\u0002\u0005\n\u0003/\u0001!\u0019!C\u0001\u00033A\u0001\"!\b\u0001A\u0003%\u00111\u0004\u0005\n\u0003?\u0001!\u0019!C\u0001\u0003CA\u0001\"!\n\u0001A\u0003%\u00111\u0005\u0005\n\u0003O\u0001!\u0019!C\u0001\u0003SA\u0001\"!\f\u0001A\u0003%\u00111\u0006\u0005\n\u0003_\u0001!\u0019!C\u0001\u0003cA\u0001\"!\u000e\u0001A\u0003%\u00111\u0007\u0005\n\u0003o\u0001!\u0019!C\u0001\u0003sA\u0001\"!\u0010\u0001A\u0003%\u00111\b\u0005\n\u0003\u007f\u0001!\u0019!C\u0001\u0003\u0003B\u0001\"!\u0012\u0001A\u0003%\u00111\t\u0005\n\u0003\u000f\u0002!\u0019!C\u0001\u0003\u0013B\u0001\"!\u0014\u0001A\u0003%\u00111\n\u0005\n\u0003\u001f\u0002!\u0019!C\u0001\u0003#B\u0001\"!\u0016\u0001A\u0003%\u00111\u000b\u0005\n\u0003/\u0002!\u0019!C\u0001\u00033B\u0001\"!\u0018\u0001A\u0003%\u00111\f\u0005\n\u0003?\u0002!\u0019!C\u0001\u0003CB\u0001\"!\u001a\u0001A\u0003%\u00111\r\u0005\u0007e\u0002!\t!a\u001a\t\u000f\u0005-\u0004\u0001\"\u0011\u0002n!9\u0011\u0011\u0010\u0001\u0005B\u0005m$a\u0004.ja2\u000bG/Z:u/&$\b.M\u0019\u000b\u0005\u00152\u0013\u0001C:dC2\fGm\u001d7\u000b\u0005\u001dB\u0013AB:ue\u0016\fWNC\u0001*\u0003\u0011\t7n[1\u0004\u0001UiA&\u000f$J\u0019>\u0013V\u000bW._C\u0012\u001c\"\u0001A\u0017\u0011\u00079\n4'D\u00010\u0015\t\u0001d%A\u0003ti\u0006<W-\u0003\u00023_\tQqI]1qQN#\u0018mZ3\u0011\u001dQ*t'\u0012%L\u001dF#vKW/aG6\ta%\u0003\u00027M\taa)\u00198J]NC\u0017\r]32cA\u0011\u0001(\u000f\u0007\u0001\t\u0015Q\u0004A1\u0001<\u0005\t\t\u0015'\u0005\u0002=\u0005B\u0011Q\bQ\u0007\u0002})\tq(A\u0003tG\u0006d\u0017-\u0003\u0002B}\t9aj\u001c;iS:<\u0007CA\u001fD\u0013\t!eHA\u0002B]f\u0004\"\u0001\u000f$\u0005\u000b\u001d\u0003!\u0019A\u001e\u0003\u0005\u0005\u0013\u0004C\u0001\u001dJ\t\u0015Q\u0005A1\u0001<\u0005\t\t5\u0007\u0005\u00029\u0019\u0012)Q\n\u0001b\u0001w\t\u0011\u0011\t\u000e\t\u0003q=#Q\u0001\u0015\u0001C\u0002m\u0012!!Q\u001b\u0011\u0005a\u0012F!B*\u0001\u0005\u0004Y$AA!7!\tAT\u000bB\u0003W\u0001\t\u00071H\u0001\u0002BoA\u0011\u0001\b\u0017\u0003\u00063\u0002\u0011\ra\u000f\u0002\u0003\u0003b\u0002\"\u0001O.\u0005\u000bq\u0003!\u0019A\u001e\u0003\u0005\u0005K\u0004C\u0001\u001d_\t\u0015y\u0006A1\u0001<\u0005\r\t\u0015\u0007\r\t\u0003q\u0005$QA\u0019\u0001C\u0002m\u00121!Q\u00192!\tAD\rB\u0003f\u0001\t\u00071HA\u0001P\u0003\u0019Q\u0018\u000e\u001d9feV\t\u0001\u000e\u0005\b>S^*\u0005j\u0013(R)^SV\fY2\n\u0005)t$A\u0003$v]\u000e$\u0018n\u001c82c\u00059!0\u001b9qKJ\u0004\u0013!D3bO\u0016\u00148i\\7qY\u0016$X-F\u0001o!\tit.\u0003\u0002q}\t9!i\\8mK\u0006t\u0017AD3bO\u0016\u00148i\\7qY\u0016$X\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007Q4x\u000f\u0005\bv\u0001]*\u0005j\u0013(R)^SV\fY2\u000e\u0003\u0011BQAZ\u0003A\u0002!DQ\u0001\\\u0003A\u00029\f\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\u0005Q\bC\u0001\u001b|\u0013\tahE\u0001\u0006BiR\u0014\u0018NY;uKN\fQa\u001d5ba\u0016,\u0012aM\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002\u0007=,H/\u0006\u0002\u0002\u0006A!A'a\u0002d\u0013\r\tIA\n\u0002\u0007\u001fV$H.\u001a;\u0002\u0007%t\u0007'\u0006\u0002\u0002\u0010A!A'!\u00058\u0013\r\t\u0019B\n\u0002\u0006\u0013:dW\r^\u0001\u0005S:\u0004\u0004%A\u0002j]F*\"!a\u0007\u0011\tQ\n\t\"R\u0001\u0005S:\f\u0004%A\u0002j]J*\"!a\t\u0011\tQ\n\t\u0002S\u0001\u0005S:\u0014\u0004%A\u0002j]N*\"!a\u000b\u0011\tQ\n\tbS\u0001\u0005S:\u001c\u0004%A\u0002j]R*\"!a\r\u0011\tQ\n\tBT\u0001\u0005S:$\u0004%A\u0002j]V*\"!a\u000f\u0011\tQ\n\t\"U\u0001\u0005S:,\u0004%A\u0002j]Z*\"!a\u0011\u0011\tQ\n\t\u0002V\u0001\u0005S:4\u0004%A\u0002j]^*\"!a\u0013\u0011\tQ\n\tbV\u0001\u0005S:<\u0004%A\u0002j]b*\"!a\u0015\u0011\tQ\n\tBW\u0001\u0005S:D\u0004%A\u0002j]f*\"!a\u0017\u0011\tQ\n\t\"X\u0001\u0005S:L\u0004%\u0001\u0003j]F\u0002TCAA2!\u0011!\u0014\u0011\u00031\u0002\u000b%t\u0017\u0007\r\u0011\u0015\u0007Q\fI\u0007C\u0003gA\u0001\u0007\u0001.A\u0006de\u0016\fG/\u001a'pO&\u001cG\u0003BA8\u0003k\u00022ALA9\u0013\r\t\u0019h\f\u0002\u0010\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jG\"1\u0011qO\u0011A\u0002i\f1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003{\u0002B!a \u0002\n6\u0011\u0011\u0011\u0011\u0006\u0005\u0003\u0007\u000b))\u0001\u0003mC:<'BAAD\u0003\u0011Q\u0017M^1\n\t\u0005-\u0015\u0011\u0011\u0002\u0007'R\u0014\u0018N\\4")
public class ZipLatestWith11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O>
extends GraphStage<FanInShape11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O>> {
    private final Function11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;
    private final Inlet<A5> in4;
    private final Inlet<A6> in5;
    private final Inlet<A7> in6;
    private final Inlet<A8> in7;
    private final Inlet<A9> in8;
    private final Inlet<A10> in9;
    private final Inlet<A11> in10;

    public Function11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith11");
    }

    @Override
    public FanInShape11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O> shape() {
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

    public Inlet<A10> in9() {
        return this.in9;
    }

    public Inlet<A11> in10() {
        return this.in10;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith11$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith11$$anon$$contextPropagation;
            private final $anon$19$ZipLatestInlet<A1> inlet0;
            private final $anon$19$ZipLatestInlet<A2> inlet1;
            private final $anon$19$ZipLatestInlet<A3> inlet2;
            private final $anon$19$ZipLatestInlet<A4> inlet3;
            private final $anon$19$ZipLatestInlet<A5> inlet4;
            private final $anon$19$ZipLatestInlet<A6> inlet5;
            private final $anon$19$ZipLatestInlet<A7> inlet6;
            private final $anon$19$ZipLatestInlet<A8> inlet7;
            private final $anon$19$ZipLatestInlet<A9> inlet8;
            private final $anon$19$ZipLatestInlet<A10> inlet9;
            private final $anon$19$ZipLatestInlet<A11> inlet10;
            private boolean akka$stream$scaladsl$ZipLatestWith11$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith11$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith11 $outer;

            public int akka$stream$scaladsl$ZipLatestWith11$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith11$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith11$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith11$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith11$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith11$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith11$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith11$$anon$$contextPropagation;
            }

            private $anon$19$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$19$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$19$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$19$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$19$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$19$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$19$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$19$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            private $anon$19$ZipLatestInlet<A9> inlet8() {
                return this.inlet8;
            }

            private $anon$19$ZipLatestInlet<A10> inlet9() {
                return this.inlet9;
            }

            private $anon$19$ZipLatestInlet<A11> inlet10() {
                return this.inlet10;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith11$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith11$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith11$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith11$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith11$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith11$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith11$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith11$$anon$$staleTupleValues = x$1;
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
                this.pull(this.$outer.in9());
                this.pull(this.$outer.in10());
            }

            public boolean akka$stream$scaladsl$ZipLatestWith11$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue() && this.inlet8().hasValue() && this.inlet9().hasValue() && this.inlet10().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith11$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith11$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value(), this.inlet8().value(), this.inlet9().value(), this.inlet10().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith11$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith11$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith11$$anon$$tryPullAllIfNeeded() {
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
                }
                if (!this.hasBeenPulled(this.$outer.in9())) {
                    this.tryPull(this.$outer.in9());
                }
                if (!this.hasBeenPulled(this.$outer.in10())) {
                    this.tryPull(this.$outer.in10());
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith11 akka$stream$scaladsl$ZipLatestWith11$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith11$$anon$$upstreamsToWait = 11;
                this.akka$stream$scaladsl$ZipLatestWith11$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$19$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$19$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$19$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$19$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$19$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$19$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$19$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$19$ZipLatestInlet<A8>(this, $outer.in7());
                this.inlet8 = new $anon$19$ZipLatestInlet<A9>(this, $outer.in8());
                this.inlet9 = new $anon$19$ZipLatestInlet<A10>(this, $outer.in9());
                this.inlet10 = new $anon$19$ZipLatestInlet<A11>(this, $outer.in10());
                this.akka$stream$scaladsl$ZipLatestWith11$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith11$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$19 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith11$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith11$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith11$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith11$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith11$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith11$$anon$$tryPullAllIfNeeded();
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
                this.setHandler($outer.in9(), this.inlet9());
                this.setHandler($outer.in10(), this.inlet10());
            }
        };
    }

    public String toString() {
        return "ZipLatestWith11";
    }

    public ZipLatestWith11(Function11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape11("ZipLatestWith11");
        this.in0 = ((FanInShape11)this.shape()).in0();
        this.in1 = ((FanInShape11)this.shape()).in1();
        this.in2 = ((FanInShape11)this.shape()).in2();
        this.in3 = ((FanInShape11)this.shape()).in3();
        this.in4 = ((FanInShape11)this.shape()).in4();
        this.in5 = ((FanInShape11)this.shape()).in5();
        this.in6 = ((FanInShape11)this.shape()).in6();
        this.in7 = ((FanInShape11)this.shape()).in7();
        this.in8 = ((FanInShape11)this.shape()).in8();
        this.in9 = ((FanInShape11)this.shape()).in9();
        this.in10 = ((FanInShape11)this.shape()).in10();
    }

    public ZipLatestWith11(Function11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O> zipper) {
        this(zipper, true);
    }
}

