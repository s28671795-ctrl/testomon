/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape17;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith17$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function17;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005eh\u0001B\u00181\u0001]B!\"!\u0003\u0001\u0005\u000b\u0007I\u0011AA\u0006\u0011)\t\u0019\u0002\u0001B\u0001B\u0003%\u0011Q\u0002\u0005\u000b\u0003+\u0001!Q1A\u0005\u0002\u0005]\u0001BCA\u0010\u0001\t\u0005\t\u0015!\u0003\u0002\u001a!9\u0011\u0011\u0005\u0001\u0005\u0002\u0005\r\u0002bBA\u0017\u0001\u0011\u0005\u0013q\u0006\u0005\n\u0003o\u0001!\u0019!C!\u0003sAq!a\u000f\u0001A\u0003%q\bC\u0004\u0002>\u0001!\t!a\u0010\t\u0013\u0005\u001d\u0003A1A\u0005\u0002\u0005%\u0003\u0002CA)\u0001\u0001\u0006I!a\u0013\t\u0013\u0005M\u0003A1A\u0005\u0002\u0005U\u0003\u0002CA-\u0001\u0001\u0006I!a\u0016\t\u0013\u0005m\u0003A1A\u0005\u0002\u0005u\u0003\u0002CA1\u0001\u0001\u0006I!a\u0018\t\u0013\u0005\r\u0004A1A\u0005\u0002\u0005\u0015\u0004\u0002CA5\u0001\u0001\u0006I!a\u001a\t\u0013\u0005-\u0004A1A\u0005\u0002\u00055\u0004\u0002CA9\u0001\u0001\u0006I!a\u001c\t\u0013\u0005M\u0004A1A\u0005\u0002\u0005U\u0004\u0002CA=\u0001\u0001\u0006I!a\u001e\t\u0013\u0005m\u0004A1A\u0005\u0002\u0005u\u0004\u0002CAA\u0001\u0001\u0006I!a \t\u0013\u0005\r\u0005A1A\u0005\u0002\u0005\u0015\u0005\u0002CAE\u0001\u0001\u0006I!a\"\t\u0013\u0005-\u0005A1A\u0005\u0002\u00055\u0005\u0002CAI\u0001\u0001\u0006I!a$\t\u0013\u0005M\u0005A1A\u0005\u0002\u0005U\u0005\u0002CAM\u0001\u0001\u0006I!a&\t\u0013\u0005m\u0005A1A\u0005\u0002\u0005u\u0005\u0002CAQ\u0001\u0001\u0006I!a(\t\u0013\u0005\r\u0006A1A\u0005\u0002\u0005\u0015\u0006\u0002CAU\u0001\u0001\u0006I!a*\t\u0013\u0005-\u0006A1A\u0005\u0002\u00055\u0006\u0002CAY\u0001\u0001\u0006I!a,\t\u0013\u0005M\u0006A1A\u0005\u0002\u0005U\u0006\u0002CA]\u0001\u0001\u0006I!a.\t\u0013\u0005m\u0006A1A\u0005\u0002\u0005u\u0006\u0002CAa\u0001\u0001\u0006I!a0\t\u0013\u0005\r\u0007A1A\u0005\u0002\u0005\u0015\u0007\u0002CAe\u0001\u0001\u0006I!a2\t\u0013\u0005-\u0007A1A\u0005\u0002\u00055\u0007\u0002CAi\u0001\u0001\u0006I!a4\t\u000f\u0005\u0005\u0002\u0001\"\u0001\u0002T\"9\u0011q\u001b\u0001\u0005B\u0005e\u0007bBAs\u0001\u0011\u0005\u0013q\u001d\u0002\u00105&\u0004H*\u0019;fgR<\u0016\u000e\u001e52o)\u0011\u0011GM\u0001\tg\u000e\fG.\u00193tY*\u00111\u0007N\u0001\u0007gR\u0014X-Y7\u000b\u0003U\nA!Y6lC\u000e\u0001Q\u0003\u0006\u001dF%VC6LX1eO*l\u0007o\u001d<zy~\f)a\u0005\u0002\u0001sA\u0019!(P \u000e\u0003mR!\u0001\u0010\u001a\u0002\u000bM$\u0018mZ3\n\u0005yZ$AC$sCBD7\u000b^1hKB)\u0002)Q\"R)^SV\fY2gS2|'/\u001e=|}\u0006\rQ\"\u0001\u001a\n\u0005\t\u0013$\u0001\u0004$b]&s7\u000b[1qKF:\u0004C\u0001#F\u0019\u0001!QA\u0012\u0001C\u0002\u001d\u0013!!Q\u0019\u0012\u0005!s\u0005CA%M\u001b\u0005Q%\"A&\u0002\u000bM\u001c\u0017\r\\1\n\u00055S%a\u0002(pi\"Lgn\u001a\t\u0003\u0013>K!\u0001\u0015&\u0003\u0007\u0005s\u0017\u0010\u0005\u0002E%\u0012)1\u000b\u0001b\u0001\u000f\n\u0011\u0011I\r\t\u0003\tV#QA\u0016\u0001C\u0002\u001d\u0013!!Q\u001a\u0011\u0005\u0011CF!B-\u0001\u0005\u00049%AA!5!\t!5\fB\u0003]\u0001\t\u0007qI\u0001\u0002BkA\u0011AI\u0018\u0003\u0006?\u0002\u0011\ra\u0012\u0002\u0003\u0003Z\u0002\"\u0001R1\u0005\u000b\t\u0004!\u0019A$\u0003\u0005\u0005;\u0004C\u0001#e\t\u0015)\u0007A1\u0001H\u0005\t\t\u0005\b\u0005\u0002EO\u0012)\u0001\u000e\u0001b\u0001\u000f\n\u0011\u0011)\u000f\t\u0003\t*$Qa\u001b\u0001C\u0002\u001d\u00131!Q\u00191!\t!U\u000eB\u0003o\u0001\t\u0007qIA\u0002BcE\u0002\"\u0001\u00129\u0005\u000bE\u0004!\u0019A$\u0003\u0007\u0005\u000b$\u0007\u0005\u0002Eg\u0012)A\u000f\u0001b\u0001\u000f\n\u0019\u0011)M\u001a\u0011\u0005\u00113H!B<\u0001\u0005\u00049%aA!2iA\u0011A)\u001f\u0003\u0006u\u0002\u0011\ra\u0012\u0002\u0004\u0003F*\u0004C\u0001#}\t\u0015i\bA1\u0001H\u0005\r\t\u0015G\u000e\t\u0003\t~$a!!\u0001\u0001\u0005\u00049%aA!2oA\u0019A)!\u0002\u0005\r\u0005\u001d\u0001A1\u0001H\u0005\u0005y\u0015A\u0002>jaB,'/\u0006\u0002\u0002\u000eA1\u0012*a\u0004D#R;&,\u00181dM&dwN];ywz\f\u0019!C\u0002\u0002\u0012)\u0013!BR;oGRLwN\\\u00198\u0003\u001dQ\u0018\u000e\u001d9fe\u0002\nQ\"Z1hKJ\u001cu.\u001c9mKR,WCAA\r!\rI\u00151D\u0005\u0004\u0003;Q%a\u0002\"p_2,\u0017M\\\u0001\u000fK\u0006<WM]\"p[BdW\r^3!\u0003\u0019a\u0014N\\5u}Q1\u0011QEA\u0015\u0003W\u0001b#a\n\u0001\u0007F#vKW/aG\u001aLGn\u001c:vqnt\u00181A\u0007\u0002a!9\u0011\u0011B\u0003A\u0002\u00055\u0001bBA\u000b\u000b\u0001\u0007\u0011\u0011D\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cXCAA\u0019!\r\u0001\u00151G\u0005\u0004\u0003k\u0011$AC!uiJL'-\u001e;fg\u0006)1\u000f[1qKV\tq(\u0001\u0004tQ\u0006\u0004X\rI\u0001\u0004_V$XCAA!!\u0015\u0001\u00151IA\u0002\u0013\r\t)E\r\u0002\u0007\u001fV$H.\u001a;\u0002\u0007%t\u0007'\u0006\u0002\u0002LA!\u0001)!\u0014D\u0013\r\tyE\r\u0002\u0006\u0013:dW\r^\u0001\u0005S:\u0004\u0004%A\u0002j]F*\"!a\u0016\u0011\t\u0001\u000bi%U\u0001\u0005S:\f\u0004%A\u0002j]J*\"!a\u0018\u0011\t\u0001\u000bi\u0005V\u0001\u0005S:\u0014\u0004%A\u0002j]N*\"!a\u001a\u0011\t\u0001\u000bieV\u0001\u0005S:\u001c\u0004%A\u0002j]R*\"!a\u001c\u0011\t\u0001\u000biEW\u0001\u0005S:$\u0004%A\u0002j]V*\"!a\u001e\u0011\t\u0001\u000bi%X\u0001\u0005S:,\u0004%A\u0002j]Z*\"!a \u0011\t\u0001\u000bi\u0005Y\u0001\u0005S:4\u0004%A\u0002j]^*\"!a\"\u0011\t\u0001\u000bieY\u0001\u0005S:<\u0004%A\u0002j]b*\"!a$\u0011\t\u0001\u000biEZ\u0001\u0005S:D\u0004%A\u0002j]f*\"!a&\u0011\t\u0001\u000bi%[\u0001\u0005S:L\u0004%\u0001\u0003j]F\u0002TCAAP!\u0011\u0001\u0015Q\n7\u0002\u000b%t\u0017\u0007\r\u0011\u0002\t%t\u0017'M\u000b\u0003\u0003O\u0003B\u0001QA'_\u0006)\u0011N\\\u00192A\u0005!\u0011N\\\u00193+\t\ty\u000b\u0005\u0003A\u0003\u001b\u0012\u0018!B5ocI\u0002\u0013\u0001B5ocM*\"!a.\u0011\t\u0001\u000bi%^\u0001\u0006S:\f4\u0007I\u0001\u0005S:\fD'\u0006\u0002\u0002@B!\u0001)!\u0014y\u0003\u0015Ig.\r\u001b!\u0003\u0011Ig.M\u001b\u0016\u0005\u0005\u001d\u0007\u0003\u0002!\u0002Nm\fQ!\u001b82k\u0001\nA!\u001b82mU\u0011\u0011q\u001a\t\u0005\u0001\u00065c0A\u0003j]F2\u0004\u0005\u0006\u0003\u0002&\u0005U\u0007bBA\u0005Y\u0001\u0007\u0011QB\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u0002\\\u0006\u0005\bc\u0001\u001e\u0002^&\u0019\u0011q\\\u001e\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDq!a9.\u0001\u0004\t\t$A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0005u_N#(/\u001b8h)\t\tI\u000f\u0005\u0003\u0002l\u0006UXBAAw\u0015\u0011\ty/!=\u0002\t1\fgn\u001a\u0006\u0003\u0003g\fAA[1wC&!\u0011q_Aw\u0005\u0019\u0019FO]5oO\u0002")
public class ZipLatestWith17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O>
extends GraphStage<FanInShape17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O>> {
    private final Function17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O> shape;
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
    private final Inlet<A14> in13;
    private final Inlet<A15> in14;
    private final Inlet<A16> in15;
    private final Inlet<A17> in16;

    public Function17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith17");
    }

    @Override
    public FanInShape17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O> shape() {
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

    public Inlet<A14> in13() {
        return this.in13;
    }

    public Inlet<A15> in14() {
        return this.in14;
    }

    public Inlet<A16> in15() {
        return this.in15;
    }

    public Inlet<A17> in16() {
        return this.in16;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith17$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith17$$anon$$contextPropagation;
            private final $anon$31$ZipLatestInlet<A1> inlet0;
            private final $anon$31$ZipLatestInlet<A2> inlet1;
            private final $anon$31$ZipLatestInlet<A3> inlet2;
            private final $anon$31$ZipLatestInlet<A4> inlet3;
            private final $anon$31$ZipLatestInlet<A5> inlet4;
            private final $anon$31$ZipLatestInlet<A6> inlet5;
            private final $anon$31$ZipLatestInlet<A7> inlet6;
            private final $anon$31$ZipLatestInlet<A8> inlet7;
            private final $anon$31$ZipLatestInlet<A9> inlet8;
            private final $anon$31$ZipLatestInlet<A10> inlet9;
            private final $anon$31$ZipLatestInlet<A11> inlet10;
            private final $anon$31$ZipLatestInlet<A12> inlet11;
            private final $anon$31$ZipLatestInlet<A13> inlet12;
            private final $anon$31$ZipLatestInlet<A14> inlet13;
            private final $anon$31$ZipLatestInlet<A15> inlet14;
            private final $anon$31$ZipLatestInlet<A16> inlet15;
            private final $anon$31$ZipLatestInlet<A17> inlet16;
            private boolean akka$stream$scaladsl$ZipLatestWith17$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith17$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith17 $outer;

            public int akka$stream$scaladsl$ZipLatestWith17$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith17$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith17$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith17$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith17$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith17$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith17$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith17$$anon$$contextPropagation;
            }

            private $anon$31$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$31$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$31$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$31$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$31$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$31$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$31$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$31$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            private $anon$31$ZipLatestInlet<A9> inlet8() {
                return this.inlet8;
            }

            private $anon$31$ZipLatestInlet<A10> inlet9() {
                return this.inlet9;
            }

            private $anon$31$ZipLatestInlet<A11> inlet10() {
                return this.inlet10;
            }

            private $anon$31$ZipLatestInlet<A12> inlet11() {
                return this.inlet11;
            }

            private $anon$31$ZipLatestInlet<A13> inlet12() {
                return this.inlet12;
            }

            private $anon$31$ZipLatestInlet<A14> inlet13() {
                return this.inlet13;
            }

            private $anon$31$ZipLatestInlet<A15> inlet14() {
                return this.inlet14;
            }

            private $anon$31$ZipLatestInlet<A16> inlet15() {
                return this.inlet15;
            }

            private $anon$31$ZipLatestInlet<A17> inlet16() {
                return this.inlet16;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith17$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith17$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith17$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith17$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith17$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith17$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith17$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith17$$anon$$staleTupleValues = x$1;
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
                this.pull(this.$outer.in13());
                this.pull(this.$outer.in14());
                this.pull(this.$outer.in15());
                this.pull(this.$outer.in16());
            }

            public boolean akka$stream$scaladsl$ZipLatestWith17$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue() && this.inlet8().hasValue() && this.inlet9().hasValue() && this.inlet10().hasValue() && this.inlet11().hasValue() && this.inlet12().hasValue() && this.inlet13().hasValue() && this.inlet14().hasValue() && this.inlet15().hasValue() && this.inlet16().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith17$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith17$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value(), this.inlet8().value(), this.inlet9().value(), this.inlet10().value(), this.inlet11().value(), this.inlet12().value(), this.inlet13().value(), this.inlet14().value(), this.inlet15().value(), this.inlet16().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith17$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith17$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith17$$anon$$tryPullAllIfNeeded() {
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
                }
                if (!this.hasBeenPulled(this.$outer.in13())) {
                    this.tryPull(this.$outer.in13());
                }
                if (!this.hasBeenPulled(this.$outer.in14())) {
                    this.tryPull(this.$outer.in14());
                }
                if (!this.hasBeenPulled(this.$outer.in15())) {
                    this.tryPull(this.$outer.in15());
                }
                if (!this.hasBeenPulled(this.$outer.in16())) {
                    this.tryPull(this.$outer.in16());
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith17 akka$stream$scaladsl$ZipLatestWith17$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith17$$anon$$upstreamsToWait = 17;
                this.akka$stream$scaladsl$ZipLatestWith17$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$31$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$31$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$31$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$31$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$31$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$31$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$31$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$31$ZipLatestInlet<A8>(this, $outer.in7());
                this.inlet8 = new $anon$31$ZipLatestInlet<A9>(this, $outer.in8());
                this.inlet9 = new $anon$31$ZipLatestInlet<A10>(this, $outer.in9());
                this.inlet10 = new $anon$31$ZipLatestInlet<A11>(this, $outer.in10());
                this.inlet11 = new $anon$31$ZipLatestInlet<A12>(this, $outer.in11());
                this.inlet12 = new $anon$31$ZipLatestInlet<A13>(this, $outer.in12());
                this.inlet13 = new $anon$31$ZipLatestInlet<A14>(this, $outer.in13());
                this.inlet14 = new $anon$31$ZipLatestInlet<A15>(this, $outer.in14());
                this.inlet15 = new $anon$31$ZipLatestInlet<A16>(this, $outer.in15());
                this.inlet16 = new $anon$31$ZipLatestInlet<A17>(this, $outer.in16());
                this.akka$stream$scaladsl$ZipLatestWith17$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith17$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$31 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith17$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith17$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith17$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith17$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith17$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith17$$anon$$tryPullAllIfNeeded();
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
                this.setHandler($outer.in13(), this.inlet13());
                this.setHandler($outer.in14(), this.inlet14());
                this.setHandler($outer.in15(), this.inlet15());
                this.setHandler($outer.in16(), this.inlet16());
            }
        };
    }

    public String toString() {
        return "ZipLatestWith17";
    }

    public ZipLatestWith17(Function17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape17("ZipLatestWith17");
        this.in0 = ((FanInShape17)this.shape()).in0();
        this.in1 = ((FanInShape17)this.shape()).in1();
        this.in2 = ((FanInShape17)this.shape()).in2();
        this.in3 = ((FanInShape17)this.shape()).in3();
        this.in4 = ((FanInShape17)this.shape()).in4();
        this.in5 = ((FanInShape17)this.shape()).in5();
        this.in6 = ((FanInShape17)this.shape()).in6();
        this.in7 = ((FanInShape17)this.shape()).in7();
        this.in8 = ((FanInShape17)this.shape()).in8();
        this.in9 = ((FanInShape17)this.shape()).in9();
        this.in10 = ((FanInShape17)this.shape()).in10();
        this.in11 = ((FanInShape17)this.shape()).in11();
        this.in12 = ((FanInShape17)this.shape()).in12();
        this.in13 = ((FanInShape17)this.shape()).in13();
        this.in14 = ((FanInShape17)this.shape()).in14();
        this.in15 = ((FanInShape17)this.shape()).in15();
        this.in16 = ((FanInShape17)this.shape()).in16();
    }

    public ZipLatestWith17(Function17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O> zipper) {
        this(zipper, true);
    }
}

