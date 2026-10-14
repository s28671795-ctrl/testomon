/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape13;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith13$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function13;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ef\u0001B\u0014)\u0001=B\u0001\u0002\u001d\u0001\u0003\u0006\u0004%\t!\u001d\u0005\tk\u0002\u0011\t\u0011)A\u0005e\"Aa\u000f\u0001BC\u0002\u0013\u0005q\u000f\u0003\u0005|\u0001\t\u0005\t\u0015!\u0003y\u0011\u0015a\b\u0001\"\u0001~\u0011\u001d\t)\u0001\u0001C!\u0003\u000fA\u0011\"a\u0004\u0001\u0005\u0004%\t%!\u0005\t\u000f\u0005M\u0001\u0001)A\u0005o!9\u0011Q\u0003\u0001\u0005\u0002\u0005]\u0001\"CA\u0010\u0001\t\u0007I\u0011AA\u0011\u0011!\tI\u0003\u0001Q\u0001\n\u0005\r\u0002\"CA\u0016\u0001\t\u0007I\u0011AA\u0017\u0011!\t\t\u0004\u0001Q\u0001\n\u0005=\u0002\"CA\u001a\u0001\t\u0007I\u0011AA\u001b\u0011!\tI\u0004\u0001Q\u0001\n\u0005]\u0002\"CA\u001e\u0001\t\u0007I\u0011AA\u001f\u0011!\t\t\u0005\u0001Q\u0001\n\u0005}\u0002\"CA\"\u0001\t\u0007I\u0011AA#\u0011!\tI\u0005\u0001Q\u0001\n\u0005\u001d\u0003\"CA&\u0001\t\u0007I\u0011AA'\u0011!\t\t\u0006\u0001Q\u0001\n\u0005=\u0003\"CA*\u0001\t\u0007I\u0011AA+\u0011!\tI\u0006\u0001Q\u0001\n\u0005]\u0003\"CA.\u0001\t\u0007I\u0011AA/\u0011!\t\t\u0007\u0001Q\u0001\n\u0005}\u0003\"CA2\u0001\t\u0007I\u0011AA3\u0011!\tI\u0007\u0001Q\u0001\n\u0005\u001d\u0004\"CA6\u0001\t\u0007I\u0011AA7\u0011!\t\t\b\u0001Q\u0001\n\u0005=\u0004\"CA:\u0001\t\u0007I\u0011AA;\u0011!\tI\b\u0001Q\u0001\n\u0005]\u0004\"CA>\u0001\t\u0007I\u0011AA?\u0011!\t\t\t\u0001Q\u0001\n\u0005}\u0004\"CAB\u0001\t\u0007I\u0011AAC\u0011!\tI\t\u0001Q\u0001\n\u0005\u001d\u0005B\u0002?\u0001\t\u0003\tY\tC\u0004\u0002\u0010\u0002!\t%!%\t\u000f\u0005u\u0005\u0001\"\u0011\u0002 \ny!,\u001b9MCR,7\u000f^,ji\"\f4G\u0003\u0002*U\u0005A1oY1mC\u0012\u001cHN\u0003\u0002,Y\u000511\u000f\u001e:fC6T\u0011!L\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u001fAj$*\u0014)T-fcvLY3iW:\u001c\"\u0001A\u0019\u0011\u0007I*t'D\u00014\u0015\t!$&A\u0003ti\u0006<W-\u0003\u00027g\tQqI]1qQN#\u0018mZ3\u0011!aJ4(\u0013'P%VC6LX1eO*lW\"\u0001\u0016\n\u0005iR#\u0001\u0004$b]&s7\u000b[1qKF\u001a\u0004C\u0001\u001f>\u0019\u0001!QA\u0010\u0001C\u0002}\u0012!!Q\u0019\u0012\u0005\u00013\u0005CA!E\u001b\u0005\u0011%\"A\"\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0015\u0013%a\u0002(pi\"Lgn\u001a\t\u0003\u0003\u001eK!\u0001\u0013\"\u0003\u0007\u0005s\u0017\u0010\u0005\u0002=\u0015\u0012)1\n\u0001b\u0001\u007f\t\u0011\u0011I\r\t\u0003y5#QA\u0014\u0001C\u0002}\u0012!!Q\u001a\u0011\u0005q\u0002F!B)\u0001\u0005\u0004y$AA!5!\ta4\u000bB\u0003U\u0001\t\u0007qH\u0001\u0002BkA\u0011AH\u0016\u0003\u0006/\u0002\u0011\ra\u0010\u0002\u0003\u0003Z\u0002\"\u0001P-\u0005\u000bi\u0003!\u0019A \u0003\u0005\u0005;\u0004C\u0001\u001f]\t\u0015i\u0006A1\u0001@\u0005\t\t\u0005\b\u0005\u0002=?\u0012)\u0001\r\u0001b\u0001\u007f\t\u0011\u0011)\u000f\t\u0003y\t$Qa\u0019\u0001C\u0002}\u00121!Q\u00191!\taT\rB\u0003g\u0001\t\u0007qHA\u0002BcE\u0002\"\u0001\u00105\u0005\u000b%\u0004!\u0019A \u0003\u0007\u0005\u000b$\u0007\u0005\u0002=W\u0012)A\u000e\u0001b\u0001\u007f\t\u0019\u0011)M\u001a\u0011\u0005qrG!B8\u0001\u0005\u0004y$!A(\u0002\riL\u0007\u000f]3s+\u0005\u0011\b\u0003E!tw%cuJU+Y7z\u000bGm\u001a6n\u0013\t!(I\u0001\u0006Gk:\u001cG/[8ocM\nqA_5qa\u0016\u0014\b%A\u0007fC\u001e,'oQ8na2,G/Z\u000b\u0002qB\u0011\u0011)_\u0005\u0003u\n\u0013qAQ8pY\u0016\fg.\u0001\bfC\u001e,'oQ8na2,G/\u001a\u0011\u0002\rqJg.\u001b;?)\u0015q\u0018\u0011AA\u0002!Ay\baO%M\u001fJ+\u0006l\u00170bI\u001eTW.D\u0001)\u0011\u0015\u0001X\u00011\u0001s\u0011\u00151X\u00011\u0001y\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0003\u0003\u0013\u00012\u0001OA\u0006\u0013\r\tiA\u000b\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!B:iCB,W#A\u001c\u0002\rMD\u0017\r]3!\u0003\ryW\u000f^\u000b\u0003\u00033\u0001B\u0001OA\u000e[&\u0019\u0011Q\u0004\u0016\u0003\r=+H\u000f\\3u\u0003\rIg\u000eM\u000b\u0003\u0003G\u0001B\u0001OA\u0013w%\u0019\u0011q\u0005\u0016\u0003\u000b%sG.\u001a;\u0002\t%t\u0007\u0007I\u0001\u0004S:\fTCAA\u0018!\u0011A\u0014QE%\u0002\t%t\u0017\u0007I\u0001\u0004S:\u0014TCAA\u001c!\u0011A\u0014Q\u0005'\u0002\t%t'\u0007I\u0001\u0004S:\u001cTCAA !\u0011A\u0014QE(\u0002\t%t7\u0007I\u0001\u0004S:$TCAA$!\u0011A\u0014Q\u0005*\u0002\t%tG\u0007I\u0001\u0004S:,TCAA(!\u0011A\u0014QE+\u0002\t%tW\u0007I\u0001\u0004S:4TCAA,!\u0011A\u0014Q\u0005-\u0002\t%tg\u0007I\u0001\u0004S:<TCAA0!\u0011A\u0014QE.\u0002\t%tw\u0007I\u0001\u0004S:DTCAA4!\u0011A\u0014Q\u00050\u0002\t%t\u0007\bI\u0001\u0004S:LTCAA8!\u0011A\u0014QE1\u0002\t%t\u0017\bI\u0001\u0005S:\f\u0004'\u0006\u0002\u0002xA!\u0001(!\ne\u0003\u0015Ig.\r\u0019!\u0003\u0011Ig.M\u0019\u0016\u0005\u0005}\u0004\u0003\u0002\u001d\u0002&\u001d\fQ!\u001b82c\u0001\nA!\u001b82eU\u0011\u0011q\u0011\t\u0005q\u0005\u0015\".A\u0003j]F\u0012\u0004\u0005F\u0002\u007f\u0003\u001bCQ\u0001\u001d\u0013A\u0002I\f1b\u0019:fCR,Gj\\4jGR!\u00111SAM!\r\u0011\u0014QS\u0005\u0004\u0003/\u001b$aD$sCBD7\u000b^1hK2{w-[2\t\u000f\u0005mU\u00051\u0001\u0002\n\u0005\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fg\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\"B!\u00111UAW\u001b\t\t)K\u0003\u0003\u0002(\u0006%\u0016\u0001\u00027b]\u001eT!!a+\u0002\t)\fg/Y\u0005\u0005\u0003_\u000b)K\u0001\u0004TiJLgn\u001a")
public class ZipLatestWith13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, O>
extends GraphStage<FanInShape13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, O>> {
    private final Function13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, O> shape;
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
    private final Inlet<A12> in11;
    private final Inlet<A13> in12;

    public Function13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith13");
    }

    @Override
    public FanInShape13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, O> shape() {
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

    public Inlet<A12> in11() {
        return this.in11;
    }

    public Inlet<A13> in12() {
        return this.in12;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith13$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith13$$anon$$contextPropagation;
            private final $anon$23$ZipLatestInlet<A1> inlet0;
            private final $anon$23$ZipLatestInlet<A2> inlet1;
            private final $anon$23$ZipLatestInlet<A3> inlet2;
            private final $anon$23$ZipLatestInlet<A4> inlet3;
            private final $anon$23$ZipLatestInlet<A5> inlet4;
            private final $anon$23$ZipLatestInlet<A6> inlet5;
            private final $anon$23$ZipLatestInlet<A7> inlet6;
            private final $anon$23$ZipLatestInlet<A8> inlet7;
            private final $anon$23$ZipLatestInlet<A9> inlet8;
            private final $anon$23$ZipLatestInlet<A10> inlet9;
            private final $anon$23$ZipLatestInlet<A11> inlet10;
            private final $anon$23$ZipLatestInlet<A12> inlet11;
            private final $anon$23$ZipLatestInlet<A13> inlet12;
            private boolean akka$stream$scaladsl$ZipLatestWith13$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith13$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith13 $outer;

            public int akka$stream$scaladsl$ZipLatestWith13$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith13$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith13$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith13$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith13$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith13$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith13$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith13$$anon$$contextPropagation;
            }

            private $anon$23$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$23$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$23$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$23$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$23$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$23$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$23$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$23$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            private $anon$23$ZipLatestInlet<A9> inlet8() {
                return this.inlet8;
            }

            private $anon$23$ZipLatestInlet<A10> inlet9() {
                return this.inlet9;
            }

            private $anon$23$ZipLatestInlet<A11> inlet10() {
                return this.inlet10;
            }

            private $anon$23$ZipLatestInlet<A12> inlet11() {
                return this.inlet11;
            }

            private $anon$23$ZipLatestInlet<A13> inlet12() {
                return this.inlet12;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith13$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith13$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith13$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith13$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith13$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith13$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith13$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith13$$anon$$staleTupleValues = x$1;
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
                this.pull(this.$outer.in11());
                this.pull(this.$outer.in12());
            }

            public boolean akka$stream$scaladsl$ZipLatestWith13$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue() && this.inlet8().hasValue() && this.inlet9().hasValue() && this.inlet10().hasValue() && this.inlet11().hasValue() && this.inlet12().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith13$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith13$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value(), this.inlet8().value(), this.inlet9().value(), this.inlet10().value(), this.inlet11().value(), this.inlet12().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith13$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith13$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith13$$anon$$tryPullAllIfNeeded() {
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
                }
                if (!this.hasBeenPulled(this.$outer.in11())) {
                    this.tryPull(this.$outer.in11());
                }
                if (!this.hasBeenPulled(this.$outer.in12())) {
                    this.tryPull(this.$outer.in12());
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith13 akka$stream$scaladsl$ZipLatestWith13$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith13$$anon$$upstreamsToWait = 13;
                this.akka$stream$scaladsl$ZipLatestWith13$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$23$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$23$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$23$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$23$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$23$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$23$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$23$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$23$ZipLatestInlet<A8>(this, $outer.in7());
                this.inlet8 = new $anon$23$ZipLatestInlet<A9>(this, $outer.in8());
                this.inlet9 = new $anon$23$ZipLatestInlet<A10>(this, $outer.in9());
                this.inlet10 = new $anon$23$ZipLatestInlet<A11>(this, $outer.in10());
                this.inlet11 = new $anon$23$ZipLatestInlet<A12>(this, $outer.in11());
                this.inlet12 = new $anon$23$ZipLatestInlet<A13>(this, $outer.in12());
                this.akka$stream$scaladsl$ZipLatestWith13$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith13$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$23 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith13$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith13$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith13$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith13$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith13$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith13$$anon$$tryPullAllIfNeeded();
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
                this.setHandler($outer.in11(), this.inlet11());
                this.setHandler($outer.in12(), this.inlet12());
            }
        };
    }

    public String toString() {
        return "ZipLatestWith13";
    }

    public ZipLatestWith13(Function13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape13("ZipLatestWith13");
        this.in0 = ((FanInShape13)this.shape()).in0();
        this.in1 = ((FanInShape13)this.shape()).in1();
        this.in2 = ((FanInShape13)this.shape()).in2();
        this.in3 = ((FanInShape13)this.shape()).in3();
        this.in4 = ((FanInShape13)this.shape()).in4();
        this.in5 = ((FanInShape13)this.shape()).in5();
        this.in6 = ((FanInShape13)this.shape()).in6();
        this.in7 = ((FanInShape13)this.shape()).in7();
        this.in8 = ((FanInShape13)this.shape()).in8();
        this.in9 = ((FanInShape13)this.shape()).in9();
        this.in10 = ((FanInShape13)this.shape()).in10();
        this.in11 = ((FanInShape13)this.shape()).in11();
        this.in12 = ((FanInShape13)this.shape()).in12();
    }

    public ZipLatestWith13(Function13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, O> zipper) {
        this(zipper, true);
    }
}

