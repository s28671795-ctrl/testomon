/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape16;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith16$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function16;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dh\u0001B\u0017/\u0001UB\u0011b \u0001\u0003\u0006\u0004%\t!!\u0001\t\u0015\u0005%\u0001A!A!\u0002\u0013\t\u0019\u0001\u0003\u0006\u0002\f\u0001\u0011)\u0019!C\u0001\u0003\u001bA!\"!\u0006\u0001\u0005\u0003\u0005\u000b\u0011BA\b\u0011\u001d\t9\u0002\u0001C\u0001\u00033Aq!a\t\u0001\t\u0003\n)\u0003C\u0005\u0002.\u0001\u0011\r\u0011\"\u0011\u00020!9\u0011\u0011\u0007\u0001!\u0002\u0013i\u0004bBA\u001a\u0001\u0011\u0005\u0011Q\u0007\u0005\n\u0003{\u0001!\u0019!C\u0001\u0003\u007fA\u0001\"a\u0012\u0001A\u0003%\u0011\u0011\t\u0005\n\u0003\u0013\u0002!\u0019!C\u0001\u0003\u0017B\u0001\"a\u0014\u0001A\u0003%\u0011Q\n\u0005\n\u0003#\u0002!\u0019!C\u0001\u0003'B\u0001\"a\u0016\u0001A\u0003%\u0011Q\u000b\u0005\n\u00033\u0002!\u0019!C\u0001\u00037B\u0001\"a\u0018\u0001A\u0003%\u0011Q\f\u0005\n\u0003C\u0002!\u0019!C\u0001\u0003GB\u0001\"a\u001a\u0001A\u0003%\u0011Q\r\u0005\n\u0003S\u0002!\u0019!C\u0001\u0003WB\u0001\"a\u001c\u0001A\u0003%\u0011Q\u000e\u0005\n\u0003c\u0002!\u0019!C\u0001\u0003gB\u0001\"a\u001e\u0001A\u0003%\u0011Q\u000f\u0005\n\u0003s\u0002!\u0019!C\u0001\u0003wB\u0001\"a \u0001A\u0003%\u0011Q\u0010\u0005\n\u0003\u0003\u0003!\u0019!C\u0001\u0003\u0007C\u0001\"a\"\u0001A\u0003%\u0011Q\u0011\u0005\n\u0003\u0013\u0003!\u0019!C\u0001\u0003\u0017C\u0001\"a$\u0001A\u0003%\u0011Q\u0012\u0005\n\u0003#\u0003!\u0019!C\u0001\u0003'C\u0001\"a&\u0001A\u0003%\u0011Q\u0013\u0005\n\u00033\u0003!\u0019!C\u0001\u00037C\u0001\"a(\u0001A\u0003%\u0011Q\u0014\u0005\n\u0003C\u0003!\u0019!C\u0001\u0003GC\u0001\"a*\u0001A\u0003%\u0011Q\u0015\u0005\n\u0003S\u0003!\u0019!C\u0001\u0003WC\u0001\"a,\u0001A\u0003%\u0011Q\u0016\u0005\n\u0003c\u0003!\u0019!C\u0001\u0003gC\u0001\"a.\u0001A\u0003%\u0011Q\u0017\u0005\n\u0003s\u0003!\u0019!C\u0001\u0003wC\u0001\"a0\u0001A\u0003%\u0011Q\u0018\u0005\b\u0003/\u0001A\u0011AAa\u0011\u001d\t)\r\u0001C!\u0003\u000fDq!a5\u0001\t\u0003\n)NA\b[SBd\u0015\r^3ti^KG\u000f[\u00197\u0015\ty\u0003'\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\t\t$'\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002g\u0005!\u0011m[6b\u0007\u0001)\"CN\"Q'ZKFl\u00182fQ.t\u0017\u000f^<{{N\u0011\u0001a\u000e\t\u0004qmjT\"A\u001d\u000b\u0005i\u0002\u0014!B:uC\u001e,\u0017B\u0001\u001f:\u0005)9%/\u00199i'R\fw-\u001a\t\u0014}}\nuJU+Y7z\u000bGm\u001a6naN4\u0018\u0010`\u0007\u0002a%\u0011\u0001\t\r\u0002\r\r\u0006t\u0017J\\*iCB,\u0017G\u000e\t\u0003\u0005\u000ec\u0001\u0001B\u0003E\u0001\t\u0007QI\u0001\u0002BcE\u0011a\t\u0014\t\u0003\u000f*k\u0011\u0001\u0013\u0006\u0002\u0013\u0006)1oY1mC&\u00111\n\u0013\u0002\b\u001d>$\b.\u001b8h!\t9U*\u0003\u0002O\u0011\n\u0019\u0011I\\=\u0011\u0005\t\u0003F!B)\u0001\u0005\u0004)%AA!3!\t\u00115\u000bB\u0003U\u0001\t\u0007QI\u0001\u0002BgA\u0011!I\u0016\u0003\u0006/\u0002\u0011\r!\u0012\u0002\u0003\u0003R\u0002\"AQ-\u0005\u000bi\u0003!\u0019A#\u0003\u0005\u0005+\u0004C\u0001\"]\t\u0015i\u0006A1\u0001F\u0005\t\te\u0007\u0005\u0002C?\u0012)\u0001\r\u0001b\u0001\u000b\n\u0011\u0011i\u000e\t\u0003\u0005\n$Qa\u0019\u0001C\u0002\u0015\u0013!!\u0011\u001d\u0011\u0005\t+G!\u00024\u0001\u0005\u0004)%AA!:!\t\u0011\u0005\u000eB\u0003j\u0001\t\u0007QIA\u0002BcA\u0002\"AQ6\u0005\u000b1\u0004!\u0019A#\u0003\u0007\u0005\u000b\u0014\u0007\u0005\u0002C]\u0012)q\u000e\u0001b\u0001\u000b\n\u0019\u0011)\r\u001a\u0011\u0005\t\u000bH!\u0002:\u0001\u0005\u0004)%aA!2gA\u0011!\t\u001e\u0003\u0006k\u0002\u0011\r!\u0012\u0002\u0004\u0003F\"\u0004C\u0001\"x\t\u0015A\bA1\u0001F\u0005\r\t\u0015'\u000e\t\u0003\u0005j$Qa\u001f\u0001C\u0002\u0015\u00131!Q\u00197!\t\u0011U\u0010B\u0003\u007f\u0001\t\u0007QIA\u0001P\u0003\u0019Q\u0018\u000e\u001d9feV\u0011\u00111\u0001\t\u0015\u000f\u0006\u0015\u0011i\u0014*V1ns\u0016\rZ4k[B\u001ch/\u001f?\n\u0007\u0005\u001d\u0001J\u0001\u0006Gk:\u001cG/[8ocY\nqA_5qa\u0016\u0014\b%A\u0007fC\u001e,'oQ8na2,G/Z\u000b\u0003\u0003\u001f\u00012aRA\t\u0013\r\t\u0019\u0002\u0013\u0002\b\u0005>|G.Z1o\u00039)\u0017mZ3s\u0007>l\u0007\u000f\\3uK\u0002\na\u0001P5oSRtDCBA\u000e\u0003?\t\t\u0003\u0005\u000b\u0002\u001e\u0001\tuJU+Y7z\u000bGm\u001a6naN4\u0018\u0010`\u0007\u0002]!1q0\u0002a\u0001\u0003\u0007Aq!a\u0003\u0006\u0001\u0004\ty!A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\"!a\n\u0011\u0007y\nI#C\u0002\u0002,A\u0012!\"\u0011;ue&\u0014W\u000f^3t\u0003\u0015\u0019\b.\u00199f+\u0005i\u0014AB:iCB,\u0007%A\u0002pkR,\"!a\u000e\u0011\ty\nI\u0004`\u0005\u0004\u0003w\u0001$AB(vi2,G/A\u0002j]B*\"!!\u0011\u0011\ty\n\u0019%Q\u0005\u0004\u0003\u000b\u0002$!B%oY\u0016$\u0018\u0001B5oa\u0001\n1!\u001b82+\t\ti\u0005\u0005\u0003?\u0003\u0007z\u0015\u0001B5oc\u0001\n1!\u001b83+\t\t)\u0006\u0005\u0003?\u0003\u0007\u0012\u0016\u0001B5oe\u0001\n1!\u001b84+\t\ti\u0006\u0005\u0003?\u0003\u0007*\u0016\u0001B5og\u0001\n1!\u001b85+\t\t)\u0007\u0005\u0003?\u0003\u0007B\u0016\u0001B5oi\u0001\n1!\u001b86+\t\ti\u0007\u0005\u0003?\u0003\u0007Z\u0016\u0001B5ok\u0001\n1!\u001b87+\t\t)\b\u0005\u0003?\u0003\u0007r\u0016\u0001B5om\u0001\n1!\u001b88+\t\ti\b\u0005\u0003?\u0003\u0007\n\u0017\u0001B5oo\u0001\n1!\u001b89+\t\t)\t\u0005\u0003?\u0003\u0007\"\u0017\u0001B5oq\u0001\n1!\u001b8:+\t\ti\t\u0005\u0003?\u0003\u0007:\u0017\u0001B5os\u0001\nA!\u001b82aU\u0011\u0011Q\u0013\t\u0005}\u0005\r#.A\u0003j]F\u0002\u0004%\u0001\u0003j]F\nTCAAO!\u0011q\u00141I7\u0002\u000b%t\u0017'\r\u0011\u0002\t%t\u0017GM\u000b\u0003\u0003K\u0003BAPA\"a\u0006)\u0011N\\\u00193A\u0005!\u0011N\\\u00194+\t\ti\u000b\u0005\u0003?\u0003\u0007\u001a\u0018!B5ocM\u0002\u0013\u0001B5ocQ*\"!!.\u0011\ty\n\u0019E^\u0001\u0006S:\fD\u0007I\u0001\u0005S:\fT'\u0006\u0002\u0002>B!a(a\u0011z\u0003\u0015Ig.M\u001b!)\u0011\tY\"a1\t\r}T\u0003\u0019AA\u0002\u0003-\u0019'/Z1uK2{w-[2\u0015\t\u0005%\u0017q\u001a\t\u0004q\u0005-\u0017bAAgs\tyqI]1qQN#\u0018mZ3M_\u001eL7\rC\u0004\u0002R.\u0002\r!a\n\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a6\u0011\t\u0005e\u00171]\u0007\u0003\u00037TA!!8\u0002`\u0006!A.\u00198h\u0015\t\t\t/\u0001\u0003kCZ\f\u0017\u0002BAs\u00037\u0014aa\u0015;sS:<\u0007")
public class ZipLatestWith16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O>
extends GraphStage<FanInShape16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O>> {
    private final Function16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O> shape;
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

    public Function16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith16");
    }

    @Override
    public FanInShape16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith16$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith16$$anon$$contextPropagation;
            private final $anon$29$ZipLatestInlet<A1> inlet0;
            private final $anon$29$ZipLatestInlet<A2> inlet1;
            private final $anon$29$ZipLatestInlet<A3> inlet2;
            private final $anon$29$ZipLatestInlet<A4> inlet3;
            private final $anon$29$ZipLatestInlet<A5> inlet4;
            private final $anon$29$ZipLatestInlet<A6> inlet5;
            private final $anon$29$ZipLatestInlet<A7> inlet6;
            private final $anon$29$ZipLatestInlet<A8> inlet7;
            private final $anon$29$ZipLatestInlet<A9> inlet8;
            private final $anon$29$ZipLatestInlet<A10> inlet9;
            private final $anon$29$ZipLatestInlet<A11> inlet10;
            private final $anon$29$ZipLatestInlet<A12> inlet11;
            private final $anon$29$ZipLatestInlet<A13> inlet12;
            private final $anon$29$ZipLatestInlet<A14> inlet13;
            private final $anon$29$ZipLatestInlet<A15> inlet14;
            private final $anon$29$ZipLatestInlet<A16> inlet15;
            private boolean akka$stream$scaladsl$ZipLatestWith16$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith16$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith16 $outer;

            public int akka$stream$scaladsl$ZipLatestWith16$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith16$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith16$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith16$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith16$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith16$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith16$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith16$$anon$$contextPropagation;
            }

            private $anon$29$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$29$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$29$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$29$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$29$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$29$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$29$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$29$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            private $anon$29$ZipLatestInlet<A9> inlet8() {
                return this.inlet8;
            }

            private $anon$29$ZipLatestInlet<A10> inlet9() {
                return this.inlet9;
            }

            private $anon$29$ZipLatestInlet<A11> inlet10() {
                return this.inlet10;
            }

            private $anon$29$ZipLatestInlet<A12> inlet11() {
                return this.inlet11;
            }

            private $anon$29$ZipLatestInlet<A13> inlet12() {
                return this.inlet12;
            }

            private $anon$29$ZipLatestInlet<A14> inlet13() {
                return this.inlet13;
            }

            private $anon$29$ZipLatestInlet<A15> inlet14() {
                return this.inlet14;
            }

            private $anon$29$ZipLatestInlet<A16> inlet15() {
                return this.inlet15;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith16$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith16$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith16$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith16$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith16$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith16$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith16$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith16$$anon$$staleTupleValues = x$1;
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
            }

            public boolean akka$stream$scaladsl$ZipLatestWith16$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue() && this.inlet8().hasValue() && this.inlet9().hasValue() && this.inlet10().hasValue() && this.inlet11().hasValue() && this.inlet12().hasValue() && this.inlet13().hasValue() && this.inlet14().hasValue() && this.inlet15().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith16$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith16$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value(), this.inlet8().value(), this.inlet9().value(), this.inlet10().value(), this.inlet11().value(), this.inlet12().value(), this.inlet13().value(), this.inlet14().value(), this.inlet15().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith16$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith16$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith16$$anon$$tryPullAllIfNeeded() {
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
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith16 akka$stream$scaladsl$ZipLatestWith16$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith16$$anon$$upstreamsToWait = 16;
                this.akka$stream$scaladsl$ZipLatestWith16$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$29$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$29$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$29$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$29$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$29$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$29$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$29$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$29$ZipLatestInlet<A8>(this, $outer.in7());
                this.inlet8 = new $anon$29$ZipLatestInlet<A9>(this, $outer.in8());
                this.inlet9 = new $anon$29$ZipLatestInlet<A10>(this, $outer.in9());
                this.inlet10 = new $anon$29$ZipLatestInlet<A11>(this, $outer.in10());
                this.inlet11 = new $anon$29$ZipLatestInlet<A12>(this, $outer.in11());
                this.inlet12 = new $anon$29$ZipLatestInlet<A13>(this, $outer.in12());
                this.inlet13 = new $anon$29$ZipLatestInlet<A14>(this, $outer.in13());
                this.inlet14 = new $anon$29$ZipLatestInlet<A15>(this, $outer.in14());
                this.inlet15 = new $anon$29$ZipLatestInlet<A16>(this, $outer.in15());
                this.akka$stream$scaladsl$ZipLatestWith16$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith16$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$29 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith16$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith16$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith16$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith16$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith16$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith16$$anon$$tryPullAllIfNeeded();
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
            }
        };
    }

    public String toString() {
        return "ZipLatestWith16";
    }

    public ZipLatestWith16(Function16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape16("ZipLatestWith16");
        this.in0 = ((FanInShape16)this.shape()).in0();
        this.in1 = ((FanInShape16)this.shape()).in1();
        this.in2 = ((FanInShape16)this.shape()).in2();
        this.in3 = ((FanInShape16)this.shape()).in3();
        this.in4 = ((FanInShape16)this.shape()).in4();
        this.in5 = ((FanInShape16)this.shape()).in5();
        this.in6 = ((FanInShape16)this.shape()).in6();
        this.in7 = ((FanInShape16)this.shape()).in7();
        this.in8 = ((FanInShape16)this.shape()).in8();
        this.in9 = ((FanInShape16)this.shape()).in9();
        this.in10 = ((FanInShape16)this.shape()).in10();
        this.in11 = ((FanInShape16)this.shape()).in11();
        this.in12 = ((FanInShape16)this.shape()).in12();
        this.in13 = ((FanInShape16)this.shape()).in13();
        this.in14 = ((FanInShape16)this.shape()).in14();
        this.in15 = ((FanInShape16)this.shape()).in15();
    }

    public ZipLatestWith16(Function16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O> zipper) {
        this(zipper, true);
    }
}

