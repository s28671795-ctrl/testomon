/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape14;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith14$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function14;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\rg\u0001B\u0015+\u0001EB\u0001\"\u001e\u0001\u0003\u0006\u0004%\tA\u001e\u0005\tu\u0002\u0011\t\u0011)A\u0005o\"A1\u0010\u0001BC\u0002\u0013\u0005A\u0010C\u0005\u0002\u0002\u0001\u0011\t\u0011)A\u0005{\"9\u00111\u0001\u0001\u0005\u0002\u0005\u0015\u0001bBA\b\u0001\u0011\u0005\u0013\u0011\u0003\u0005\n\u00033\u0001!\u0019!C!\u00037Aq!!\b\u0001A\u0003%\u0011\bC\u0004\u0002 \u0001!\t!!\t\t\u0013\u0005%\u0002A1A\u0005\u0002\u0005-\u0002\u0002CA\u001a\u0001\u0001\u0006I!!\f\t\u0013\u0005U\u0002A1A\u0005\u0002\u0005]\u0002\u0002CA\u001e\u0001\u0001\u0006I!!\u000f\t\u0013\u0005u\u0002A1A\u0005\u0002\u0005}\u0002\u0002CA\"\u0001\u0001\u0006I!!\u0011\t\u0013\u0005\u0015\u0003A1A\u0005\u0002\u0005\u001d\u0003\u0002CA&\u0001\u0001\u0006I!!\u0013\t\u0013\u00055\u0003A1A\u0005\u0002\u0005=\u0003\u0002CA*\u0001\u0001\u0006I!!\u0015\t\u0013\u0005U\u0003A1A\u0005\u0002\u0005]\u0003\u0002CA.\u0001\u0001\u0006I!!\u0017\t\u0013\u0005u\u0003A1A\u0005\u0002\u0005}\u0003\u0002CA2\u0001\u0001\u0006I!!\u0019\t\u0013\u0005\u0015\u0004A1A\u0005\u0002\u0005\u001d\u0004\u0002CA6\u0001\u0001\u0006I!!\u001b\t\u0013\u00055\u0004A1A\u0005\u0002\u0005=\u0004\u0002CA:\u0001\u0001\u0006I!!\u001d\t\u0013\u0005U\u0004A1A\u0005\u0002\u0005]\u0004\u0002CA>\u0001\u0001\u0006I!!\u001f\t\u0013\u0005u\u0004A1A\u0005\u0002\u0005}\u0004\u0002CAB\u0001\u0001\u0006I!!!\t\u0013\u0005\u0015\u0005A1A\u0005\u0002\u0005\u001d\u0005\u0002CAF\u0001\u0001\u0006I!!#\t\u0013\u00055\u0005A1A\u0005\u0002\u0005=\u0005\u0002CAJ\u0001\u0001\u0006I!!%\t\u0013\u0005U\u0005A1A\u0005\u0002\u0005]\u0005\u0002CAN\u0001\u0001\u0006I!!'\t\u000f\u0005\r\u0001\u0001\"\u0001\u0002\u001e\"9\u0011\u0011\u0015\u0001\u0005B\u0005\r\u0006bBAX\u0001\u0011\u0005\u0013\u0011\u0017\u0002\u00105&\u0004H*\u0019;fgR<\u0016\u000e\u001e52i)\u00111\u0006L\u0001\tg\u000e\fG.\u00193tY*\u0011QFL\u0001\u0007gR\u0014X-Y7\u000b\u0003=\nA!Y6lC\u000e\u0001Q\u0003\u0005\u001a@\u0019>\u0013V\u000bW._C\u0012<'.\u001c9t'\t\u00011\u0007E\u00025oej\u0011!\u000e\u0006\u0003m1\nQa\u001d;bO\u0016L!\u0001O\u001b\u0003\u0015\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rE\t;wuZe*\u0015+X5v\u00037MZ5m_Jl\u0011\u0001L\u0005\u0003y1\u0012ABR1o\u0013:\u001c\u0006.\u00199fcQ\u0002\"AP \r\u0001\u0011)\u0001\t\u0001b\u0001\u0003\n\u0011\u0011)M\t\u0003\u0005\"\u0003\"a\u0011$\u000e\u0003\u0011S\u0011!R\u0001\u0006g\u000e\fG.Y\u0005\u0003\u000f\u0012\u0013qAT8uQ&tw\r\u0005\u0002D\u0013&\u0011!\n\u0012\u0002\u0004\u0003:L\bC\u0001 M\t\u0015i\u0005A1\u0001B\u0005\t\t%\u0007\u0005\u0002?\u001f\u0012)\u0001\u000b\u0001b\u0001\u0003\n\u0011\u0011i\r\t\u0003}I#Qa\u0015\u0001C\u0002\u0005\u0013!!\u0011\u001b\u0011\u0005y*F!\u0002,\u0001\u0005\u0004\t%AA!6!\tq\u0004\fB\u0003Z\u0001\t\u0007\u0011I\u0001\u0002BmA\u0011ah\u0017\u0003\u00069\u0002\u0011\r!\u0011\u0002\u0003\u0003^\u0002\"A\u00100\u0005\u000b}\u0003!\u0019A!\u0003\u0005\u0005C\u0004C\u0001 b\t\u0015\u0011\u0007A1\u0001B\u0005\t\t\u0015\b\u0005\u0002?I\u0012)Q\r\u0001b\u0001\u0003\n\u0019\u0011)\r\u0019\u0011\u0005y:G!\u00025\u0001\u0005\u0004\t%aA!2cA\u0011aH\u001b\u0003\u0006W\u0002\u0011\r!\u0011\u0002\u0004\u0003F\u0012\u0004C\u0001 n\t\u0015q\u0007A1\u0001B\u0005\r\t\u0015g\r\t\u0003}A$Q!\u001d\u0001C\u0002\u0005\u00131!Q\u00195!\tq4\u000fB\u0003u\u0001\t\u0007\u0011IA\u0001P\u0003\u0019Q\u0018\u000e\u001d9feV\tq\u000fE\tDqvZe*\u0015+X5v\u00037MZ5m_JL!!\u001f#\u0003\u0015\u0019+hn\u0019;j_:\fD'A\u0004{SB\u0004XM\u001d\u0011\u0002\u001b\u0015\fw-\u001a:D_6\u0004H.\u001a;f+\u0005i\bCA\"\u007f\u0013\tyHIA\u0004C_>dW-\u00198\u0002\u001d\u0015\fw-\u001a:D_6\u0004H.\u001a;fA\u00051A(\u001b8jiz\"b!a\u0002\u0002\f\u00055\u0001CEA\u0005\u0001uZe*\u0015+X5v\u00037MZ5m_Jl\u0011A\u000b\u0005\u0006k\u0016\u0001\ra\u001e\u0005\u0006w\u0016\u0001\r!`\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cXCAA\n!\rQ\u0014QC\u0005\u0004\u0003/a#AC!uiJL'-\u001e;fg\u0006)1\u000f[1qKV\t\u0011(\u0001\u0004tQ\u0006\u0004X\rI\u0001\u0004_V$XCAA\u0012!\u0011Q\u0014Q\u0005:\n\u0007\u0005\u001dBF\u0001\u0004PkRdW\r^\u0001\u0004S:\u0004TCAA\u0017!\u0011Q\u0014qF\u001f\n\u0007\u0005EBFA\u0003J]2,G/\u0001\u0003j]B\u0002\u0013aA5ocU\u0011\u0011\u0011\b\t\u0005u\u0005=2*\u0001\u0003j]F\u0002\u0013aA5oeU\u0011\u0011\u0011\t\t\u0005u\u0005=b*\u0001\u0003j]J\u0002\u0013aA5ogU\u0011\u0011\u0011\n\t\u0005u\u0005=\u0012+\u0001\u0003j]N\u0002\u0013aA5oiU\u0011\u0011\u0011\u000b\t\u0005u\u0005=B+\u0001\u0003j]R\u0002\u0013aA5okU\u0011\u0011\u0011\f\t\u0005u\u0005=r+\u0001\u0003j]V\u0002\u0013aA5omU\u0011\u0011\u0011\r\t\u0005u\u0005=\",\u0001\u0003j]Z\u0002\u0013aA5ooU\u0011\u0011\u0011\u000e\t\u0005u\u0005=R,\u0001\u0003j]^\u0002\u0013aA5oqU\u0011\u0011\u0011\u000f\t\u0005u\u0005=\u0002-\u0001\u0003j]b\u0002\u0013aA5osU\u0011\u0011\u0011\u0010\t\u0005u\u0005=2-\u0001\u0003j]f\u0002\u0013\u0001B5ocA*\"!!!\u0011\ti\nyCZ\u0001\u0006S:\f\u0004\u0007I\u0001\u0005S:\f\u0014'\u0006\u0002\u0002\nB!!(a\fj\u0003\u0015Ig.M\u0019!\u0003\u0011Ig.\r\u001a\u0016\u0005\u0005E\u0005\u0003\u0002\u001e\u000201\fQ!\u001b82e\u0001\nA!\u001b82gU\u0011\u0011\u0011\u0014\t\u0005u\u0005=r.A\u0003j]F\u001a\u0004\u0005\u0006\u0003\u0002\b\u0005}\u0005\"B;'\u0001\u00049\u0018aC2sK\u0006$X\rT8hS\u000e$B!!*\u0002,B\u0019A'a*\n\u0007\u0005%VGA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u001d\tik\na\u0001\u0003'\t1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003g\u0003B!!.\u0002@6\u0011\u0011q\u0017\u0006\u0005\u0003s\u000bY,\u0001\u0003mC:<'BAA_\u0003\u0011Q\u0017M^1\n\t\u0005\u0005\u0017q\u0017\u0002\u0007'R\u0014\u0018N\\4")
public class ZipLatestWith14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, O>
extends GraphStage<FanInShape14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, O>> {
    private final Function14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, O> shape;
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

    public Function14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith14");
    }

    @Override
    public FanInShape14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith14$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith14$$anon$$contextPropagation;
            private final $anon$25$ZipLatestInlet<A1> inlet0;
            private final $anon$25$ZipLatestInlet<A2> inlet1;
            private final $anon$25$ZipLatestInlet<A3> inlet2;
            private final $anon$25$ZipLatestInlet<A4> inlet3;
            private final $anon$25$ZipLatestInlet<A5> inlet4;
            private final $anon$25$ZipLatestInlet<A6> inlet5;
            private final $anon$25$ZipLatestInlet<A7> inlet6;
            private final $anon$25$ZipLatestInlet<A8> inlet7;
            private final $anon$25$ZipLatestInlet<A9> inlet8;
            private final $anon$25$ZipLatestInlet<A10> inlet9;
            private final $anon$25$ZipLatestInlet<A11> inlet10;
            private final $anon$25$ZipLatestInlet<A12> inlet11;
            private final $anon$25$ZipLatestInlet<A13> inlet12;
            private final $anon$25$ZipLatestInlet<A14> inlet13;
            private boolean akka$stream$scaladsl$ZipLatestWith14$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith14$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith14 $outer;

            public int akka$stream$scaladsl$ZipLatestWith14$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith14$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith14$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith14$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith14$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith14$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith14$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith14$$anon$$contextPropagation;
            }

            private $anon$25$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$25$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$25$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$25$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$25$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$25$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$25$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$25$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            private $anon$25$ZipLatestInlet<A9> inlet8() {
                return this.inlet8;
            }

            private $anon$25$ZipLatestInlet<A10> inlet9() {
                return this.inlet9;
            }

            private $anon$25$ZipLatestInlet<A11> inlet10() {
                return this.inlet10;
            }

            private $anon$25$ZipLatestInlet<A12> inlet11() {
                return this.inlet11;
            }

            private $anon$25$ZipLatestInlet<A13> inlet12() {
                return this.inlet12;
            }

            private $anon$25$ZipLatestInlet<A14> inlet13() {
                return this.inlet13;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith14$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith14$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith14$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith14$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith14$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith14$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith14$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith14$$anon$$staleTupleValues = x$1;
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
            }

            public boolean akka$stream$scaladsl$ZipLatestWith14$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue() && this.inlet8().hasValue() && this.inlet9().hasValue() && this.inlet10().hasValue() && this.inlet11().hasValue() && this.inlet12().hasValue() && this.inlet13().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith14$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith14$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value(), this.inlet8().value(), this.inlet9().value(), this.inlet10().value(), this.inlet11().value(), this.inlet12().value(), this.inlet13().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith14$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith14$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith14$$anon$$tryPullAllIfNeeded() {
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
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith14 akka$stream$scaladsl$ZipLatestWith14$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith14$$anon$$upstreamsToWait = 14;
                this.akka$stream$scaladsl$ZipLatestWith14$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$25$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$25$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$25$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$25$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$25$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$25$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$25$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$25$ZipLatestInlet<A8>(this, $outer.in7());
                this.inlet8 = new $anon$25$ZipLatestInlet<A9>(this, $outer.in8());
                this.inlet9 = new $anon$25$ZipLatestInlet<A10>(this, $outer.in9());
                this.inlet10 = new $anon$25$ZipLatestInlet<A11>(this, $outer.in10());
                this.inlet11 = new $anon$25$ZipLatestInlet<A12>(this, $outer.in11());
                this.inlet12 = new $anon$25$ZipLatestInlet<A13>(this, $outer.in12());
                this.inlet13 = new $anon$25$ZipLatestInlet<A14>(this, $outer.in13());
                this.akka$stream$scaladsl$ZipLatestWith14$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith14$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$25 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith14$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith14$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith14$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith14$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith14$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith14$$anon$$tryPullAllIfNeeded();
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
            }
        };
    }

    public String toString() {
        return "ZipLatestWith14";
    }

    public ZipLatestWith14(Function14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape14("ZipLatestWith14");
        this.in0 = ((FanInShape14)this.shape()).in0();
        this.in1 = ((FanInShape14)this.shape()).in1();
        this.in2 = ((FanInShape14)this.shape()).in2();
        this.in3 = ((FanInShape14)this.shape()).in3();
        this.in4 = ((FanInShape14)this.shape()).in4();
        this.in5 = ((FanInShape14)this.shape()).in5();
        this.in6 = ((FanInShape14)this.shape()).in6();
        this.in7 = ((FanInShape14)this.shape()).in7();
        this.in8 = ((FanInShape14)this.shape()).in8();
        this.in9 = ((FanInShape14)this.shape()).in9();
        this.in10 = ((FanInShape14)this.shape()).in10();
        this.in11 = ((FanInShape14)this.shape()).in11();
        this.in12 = ((FanInShape14)this.shape()).in12();
        this.in13 = ((FanInShape14)this.shape()).in13();
    }

    public ZipLatestWith14(Function14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, O> zipper) {
        this(zipper, true);
    }
}

