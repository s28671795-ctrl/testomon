/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape15;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith15$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function15;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ug\u0001B\u0016-\u0001MB\u0001B\u001f\u0001\u0003\u0006\u0004%\ta\u001f\u0005\t\u007f\u0002\u0011\t\u0011)A\u0005y\"Q\u0011\u0011\u0001\u0001\u0003\u0006\u0004%\t!a\u0001\t\u0015\u0005-\u0001A!A!\u0002\u0013\t)\u0001C\u0004\u0002\u000e\u0001!\t!a\u0004\t\u000f\u0005e\u0001\u0001\"\u0011\u0002\u001c!I\u00111\u0005\u0001C\u0002\u0013\u0005\u0013Q\u0005\u0005\b\u0003O\u0001\u0001\u0015!\u0003<\u0011\u001d\tI\u0003\u0001C\u0001\u0003WA\u0011\"a\r\u0001\u0005\u0004%\t!!\u000e\t\u0011\u0005u\u0002\u0001)A\u0005\u0003oA\u0011\"a\u0010\u0001\u0005\u0004%\t!!\u0011\t\u0011\u0005\u0015\u0003\u0001)A\u0005\u0003\u0007B\u0011\"a\u0012\u0001\u0005\u0004%\t!!\u0013\t\u0011\u00055\u0003\u0001)A\u0005\u0003\u0017B\u0011\"a\u0014\u0001\u0005\u0004%\t!!\u0015\t\u0011\u0005U\u0003\u0001)A\u0005\u0003'B\u0011\"a\u0016\u0001\u0005\u0004%\t!!\u0017\t\u0011\u0005u\u0003\u0001)A\u0005\u00037B\u0011\"a\u0018\u0001\u0005\u0004%\t!!\u0019\t\u0011\u0005\u0015\u0004\u0001)A\u0005\u0003GB\u0011\"a\u001a\u0001\u0005\u0004%\t!!\u001b\t\u0011\u00055\u0004\u0001)A\u0005\u0003WB\u0011\"a\u001c\u0001\u0005\u0004%\t!!\u001d\t\u0011\u0005U\u0004\u0001)A\u0005\u0003gB\u0011\"a\u001e\u0001\u0005\u0004%\t!!\u001f\t\u0011\u0005u\u0004\u0001)A\u0005\u0003wB\u0011\"a \u0001\u0005\u0004%\t!!!\t\u0011\u0005\u0015\u0005\u0001)A\u0005\u0003\u0007C\u0011\"a\"\u0001\u0005\u0004%\t!!#\t\u0011\u00055\u0005\u0001)A\u0005\u0003\u0017C\u0011\"a$\u0001\u0005\u0004%\t!!%\t\u0011\u0005U\u0005\u0001)A\u0005\u0003'C\u0011\"a&\u0001\u0005\u0004%\t!!'\t\u0011\u0005u\u0005\u0001)A\u0005\u00037C\u0011\"a(\u0001\u0005\u0004%\t!!)\t\u0011\u0005\u0015\u0006\u0001)A\u0005\u0003GC\u0011\"a*\u0001\u0005\u0004%\t!!+\t\u0011\u00055\u0006\u0001)A\u0005\u0003WCq!!\u0004\u0001\t\u0003\ty\u000bC\u0004\u00024\u0002!\t%!.\t\u000f\u0005\u0005\u0007\u0001\"\u0011\u0002D\ny!,\u001b9MCR,7\u000f^,ji\"\fTG\u0003\u0002.]\u0005A1oY1mC\u0012\u001cHN\u0003\u00020a\u000511\u000f\u001e:fC6T\u0011!M\u0001\u0005C.\\\u0017m\u0001\u0001\u0016#Q\ne*\u0015+X5v\u00037MZ5m_J,\bp\u0005\u0002\u0001kA\u0019a'O\u001e\u000e\u0003]R!\u0001\u000f\u0018\u0002\u000bM$\u0018mZ3\n\u0005i:$AC$sCBD7\u000b^1hKB\u0011B(P N!N3\u0016\fX0cK\"\\g.\u001d;x\u001b\u0005q\u0013B\u0001 /\u000511\u0015M\\%o'\"\f\u0007/Z\u00196!\t\u0001\u0015\t\u0004\u0001\u0005\u000b\t\u0003!\u0019A\"\u0003\u0005\u0005\u000b\u0014C\u0001#K!\t)\u0005*D\u0001G\u0015\u00059\u0015!B:dC2\f\u0017BA%G\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!R&\n\u000513%aA!osB\u0011\u0001I\u0014\u0003\u0006\u001f\u0002\u0011\ra\u0011\u0002\u0003\u0003J\u0002\"\u0001Q)\u0005\u000bI\u0003!\u0019A\"\u0003\u0005\u0005\u001b\u0004C\u0001!U\t\u0015)\u0006A1\u0001D\u0005\t\tE\u0007\u0005\u0002A/\u0012)\u0001\f\u0001b\u0001\u0007\n\u0011\u0011)\u000e\t\u0003\u0001j#Qa\u0017\u0001C\u0002\r\u0013!!\u0011\u001c\u0011\u0005\u0001kF!\u00020\u0001\u0005\u0004\u0019%AA!8!\t\u0001\u0005\rB\u0003b\u0001\t\u00071I\u0001\u0002BqA\u0011\u0001i\u0019\u0003\u0006I\u0002\u0011\ra\u0011\u0002\u0003\u0003f\u0002\"\u0001\u00114\u0005\u000b\u001d\u0004!\u0019A\"\u0003\u0007\u0005\u000b\u0004\u0007\u0005\u0002AS\u0012)!\u000e\u0001b\u0001\u0007\n\u0019\u0011)M\u0019\u0011\u0005\u0001cG!B7\u0001\u0005\u0004\u0019%aA!2eA\u0011\u0001i\u001c\u0003\u0006a\u0002\u0011\ra\u0011\u0002\u0004\u0003F\u001a\u0004C\u0001!s\t\u0015\u0019\bA1\u0001D\u0005\r\t\u0015\u0007\u000e\t\u0003\u0001V$QA\u001e\u0001C\u0002\r\u00131!Q\u00196!\t\u0001\u0005\u0010B\u0003z\u0001\t\u00071IA\u0001P\u0003\u0019Q\u0018\u000e\u001d9feV\tA\u0010\u0005\nF{~j\u0005k\u0015,Z9~\u0013W\r[6ocR<\u0018B\u0001@G\u0005)1UO\\2uS>t\u0017'N\u0001\bu&\u0004\b/\u001a:!\u00035)\u0017mZ3s\u0007>l\u0007\u000f\\3uKV\u0011\u0011Q\u0001\t\u0004\u000b\u0006\u001d\u0011bAA\u0005\r\n9!i\\8mK\u0006t\u0017AD3bO\u0016\u00148i\\7qY\u0016$X\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\r\u0005E\u0011QCA\f!M\t\u0019\u0002A N!N3\u0016\fX0cK\"\\g.\u001d;x\u001b\u0005a\u0003\"\u0002>\u0006\u0001\u0004a\bbBA\u0001\u000b\u0001\u0007\u0011QA\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cXCAA\u000f!\ra\u0014qD\u0005\u0004\u0003Cq#AC!uiJL'-\u001e;fg\u0006)1\u000f[1qKV\t1(\u0001\u0004tQ\u0006\u0004X\rI\u0001\u0004_V$XCAA\u0017!\u0011a\u0014qF<\n\u0007\u0005EbF\u0001\u0004PkRdW\r^\u0001\u0004S:\u0004TCAA\u001c!\u0011a\u0014\u0011H \n\u0007\u0005mbFA\u0003J]2,G/\u0001\u0003j]B\u0002\u0013aA5ocU\u0011\u00111\t\t\u0005y\u0005eR*\u0001\u0003j]F\u0002\u0013aA5oeU\u0011\u00111\n\t\u0005y\u0005e\u0002+\u0001\u0003j]J\u0002\u0013aA5ogU\u0011\u00111\u000b\t\u0005y\u0005e2+\u0001\u0003j]N\u0002\u0013aA5oiU\u0011\u00111\f\t\u0005y\u0005eb+\u0001\u0003j]R\u0002\u0013aA5okU\u0011\u00111\r\t\u0005y\u0005e\u0012,\u0001\u0003j]V\u0002\u0013aA5omU\u0011\u00111\u000e\t\u0005y\u0005eB,\u0001\u0003j]Z\u0002\u0013aA5ooU\u0011\u00111\u000f\t\u0005y\u0005er,\u0001\u0003j]^\u0002\u0013aA5oqU\u0011\u00111\u0010\t\u0005y\u0005e\"-\u0001\u0003j]b\u0002\u0013aA5osU\u0011\u00111\u0011\t\u0005y\u0005eR-\u0001\u0003j]f\u0002\u0013\u0001B5ocA*\"!a#\u0011\tq\nI\u0004[\u0001\u0006S:\f\u0004\u0007I\u0001\u0005S:\f\u0014'\u0006\u0002\u0002\u0014B!A(!\u000fl\u0003\u0015Ig.M\u0019!\u0003\u0011Ig.\r\u001a\u0016\u0005\u0005m\u0005\u0003\u0002\u001f\u0002:9\fQ!\u001b82e\u0001\nA!\u001b82gU\u0011\u00111\u0015\t\u0005y\u0005e\u0012/A\u0003j]F\u001a\u0004%\u0001\u0003j]F\"TCAAV!\u0011a\u0014\u0011\b;\u0002\u000b%t\u0017\u0007\u000e\u0011\u0015\t\u0005E\u0011\u0011\u0017\u0005\u0006u\"\u0002\r\u0001`\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u00028\u0006u\u0006c\u0001\u001c\u0002:&\u0019\u00111X\u001c\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDq!a0*\u0001\u0004\ti\"A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0005u_N#(/\u001b8h)\t\t)\r\u0005\u0003\u0002H\u0006EWBAAe\u0015\u0011\tY-!4\u0002\t1\fgn\u001a\u0006\u0003\u0003\u001f\fAA[1wC&!\u00111[Ae\u0005\u0019\u0019FO]5oO\u0002")
public class ZipLatestWith15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O>
extends GraphStage<FanInShape15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O>> {
    private final Function15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O> shape;
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

    public Function15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith15");
    }

    @Override
    public FanInShape15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith15$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith15$$anon$$contextPropagation;
            private final $anon$27$ZipLatestInlet<A1> inlet0;
            private final $anon$27$ZipLatestInlet<A2> inlet1;
            private final $anon$27$ZipLatestInlet<A3> inlet2;
            private final $anon$27$ZipLatestInlet<A4> inlet3;
            private final $anon$27$ZipLatestInlet<A5> inlet4;
            private final $anon$27$ZipLatestInlet<A6> inlet5;
            private final $anon$27$ZipLatestInlet<A7> inlet6;
            private final $anon$27$ZipLatestInlet<A8> inlet7;
            private final $anon$27$ZipLatestInlet<A9> inlet8;
            private final $anon$27$ZipLatestInlet<A10> inlet9;
            private final $anon$27$ZipLatestInlet<A11> inlet10;
            private final $anon$27$ZipLatestInlet<A12> inlet11;
            private final $anon$27$ZipLatestInlet<A13> inlet12;
            private final $anon$27$ZipLatestInlet<A14> inlet13;
            private final $anon$27$ZipLatestInlet<A15> inlet14;
            private boolean akka$stream$scaladsl$ZipLatestWith15$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith15$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith15 $outer;

            public int akka$stream$scaladsl$ZipLatestWith15$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith15$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith15$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith15$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith15$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith15$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith15$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith15$$anon$$contextPropagation;
            }

            private $anon$27$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$27$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$27$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$27$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$27$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$27$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$27$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$27$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            private $anon$27$ZipLatestInlet<A9> inlet8() {
                return this.inlet8;
            }

            private $anon$27$ZipLatestInlet<A10> inlet9() {
                return this.inlet9;
            }

            private $anon$27$ZipLatestInlet<A11> inlet10() {
                return this.inlet10;
            }

            private $anon$27$ZipLatestInlet<A12> inlet11() {
                return this.inlet11;
            }

            private $anon$27$ZipLatestInlet<A13> inlet12() {
                return this.inlet12;
            }

            private $anon$27$ZipLatestInlet<A14> inlet13() {
                return this.inlet13;
            }

            private $anon$27$ZipLatestInlet<A15> inlet14() {
                return this.inlet14;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith15$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith15$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith15$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith15$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith15$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith15$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith15$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith15$$anon$$staleTupleValues = x$1;
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
            }

            public boolean akka$stream$scaladsl$ZipLatestWith15$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue() && this.inlet8().hasValue() && this.inlet9().hasValue() && this.inlet10().hasValue() && this.inlet11().hasValue() && this.inlet12().hasValue() && this.inlet13().hasValue() && this.inlet14().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith15$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith15$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value(), this.inlet8().value(), this.inlet9().value(), this.inlet10().value(), this.inlet11().value(), this.inlet12().value(), this.inlet13().value(), this.inlet14().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith15$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith15$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith15$$anon$$tryPullAllIfNeeded() {
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
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith15 akka$stream$scaladsl$ZipLatestWith15$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith15$$anon$$upstreamsToWait = 15;
                this.akka$stream$scaladsl$ZipLatestWith15$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$27$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$27$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$27$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$27$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$27$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$27$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$27$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$27$ZipLatestInlet<A8>(this, $outer.in7());
                this.inlet8 = new $anon$27$ZipLatestInlet<A9>(this, $outer.in8());
                this.inlet9 = new $anon$27$ZipLatestInlet<A10>(this, $outer.in9());
                this.inlet10 = new $anon$27$ZipLatestInlet<A11>(this, $outer.in10());
                this.inlet11 = new $anon$27$ZipLatestInlet<A12>(this, $outer.in11());
                this.inlet12 = new $anon$27$ZipLatestInlet<A13>(this, $outer.in12());
                this.inlet13 = new $anon$27$ZipLatestInlet<A14>(this, $outer.in13());
                this.inlet14 = new $anon$27$ZipLatestInlet<A15>(this, $outer.in14());
                this.akka$stream$scaladsl$ZipLatestWith15$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith15$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$27 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith15$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith15$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith15$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith15$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith15$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith15$$anon$$tryPullAllIfNeeded();
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
            }
        };
    }

    public String toString() {
        return "ZipLatestWith15";
    }

    public ZipLatestWith15(Function15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape15("ZipLatestWith15");
        this.in0 = ((FanInShape15)this.shape()).in0();
        this.in1 = ((FanInShape15)this.shape()).in1();
        this.in2 = ((FanInShape15)this.shape()).in2();
        this.in3 = ((FanInShape15)this.shape()).in3();
        this.in4 = ((FanInShape15)this.shape()).in4();
        this.in5 = ((FanInShape15)this.shape()).in5();
        this.in6 = ((FanInShape15)this.shape()).in6();
        this.in7 = ((FanInShape15)this.shape()).in7();
        this.in8 = ((FanInShape15)this.shape()).in8();
        this.in9 = ((FanInShape15)this.shape()).in9();
        this.in10 = ((FanInShape15)this.shape()).in10();
        this.in11 = ((FanInShape15)this.shape()).in11();
        this.in12 = ((FanInShape15)this.shape()).in12();
        this.in13 = ((FanInShape15)this.shape()).in13();
        this.in14 = ((FanInShape15)this.shape()).in14();
    }

    public ZipLatestWith15(Function15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O> zipper) {
        this(zipper, true);
    }
}

