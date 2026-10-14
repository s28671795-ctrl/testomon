/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape12;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith12$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function12;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005}e\u0001B\u0013'\u00015B\u0001b\u001b\u0001\u0003\u0006\u0004%\t\u0001\u001c\u0005\ta\u0002\u0011\t\u0011)A\u0005[\"A\u0011\u000f\u0001BC\u0002\u0013\u0005!\u000f\u0003\u0005w\u0001\t\u0005\t\u0015!\u0003t\u0011\u00159\b\u0001\"\u0001y\u0011\u0015i\b\u0001\"\u0011\u007f\u0011%\t)\u0001\u0001b\u0001\n\u0003\n9\u0001C\u0004\u0002\n\u0001\u0001\u000b\u0011B\u001b\t\u000f\u0005-\u0001\u0001\"\u0001\u0002\u000e!I\u0011Q\u0003\u0001C\u0002\u0013\u0005\u0011q\u0003\u0005\t\u0003?\u0001\u0001\u0015!\u0003\u0002\u001a!I\u0011\u0011\u0005\u0001C\u0002\u0013\u0005\u00111\u0005\u0005\t\u0003O\u0001\u0001\u0015!\u0003\u0002&!I\u0011\u0011\u0006\u0001C\u0002\u0013\u0005\u00111\u0006\u0005\t\u0003_\u0001\u0001\u0015!\u0003\u0002.!I\u0011\u0011\u0007\u0001C\u0002\u0013\u0005\u00111\u0007\u0005\t\u0003o\u0001\u0001\u0015!\u0003\u00026!I\u0011\u0011\b\u0001C\u0002\u0013\u0005\u00111\b\u0005\t\u0003\u007f\u0001\u0001\u0015!\u0003\u0002>!I\u0011\u0011\t\u0001C\u0002\u0013\u0005\u00111\t\u0005\t\u0003\u000f\u0002\u0001\u0015!\u0003\u0002F!I\u0011\u0011\n\u0001C\u0002\u0013\u0005\u00111\n\u0005\t\u0003\u001f\u0002\u0001\u0015!\u0003\u0002N!I\u0011\u0011\u000b\u0001C\u0002\u0013\u0005\u00111\u000b\u0005\t\u0003/\u0002\u0001\u0015!\u0003\u0002V!I\u0011\u0011\f\u0001C\u0002\u0013\u0005\u00111\f\u0005\t\u0003?\u0002\u0001\u0015!\u0003\u0002^!I\u0011\u0011\r\u0001C\u0002\u0013\u0005\u00111\r\u0005\t\u0003O\u0002\u0001\u0015!\u0003\u0002f!I\u0011\u0011\u000e\u0001C\u0002\u0013\u0005\u00111\u000e\u0005\t\u0003_\u0002\u0001\u0015!\u0003\u0002n!I\u0011\u0011\u000f\u0001C\u0002\u0013\u0005\u00111\u000f\u0005\t\u0003o\u0002\u0001\u0015!\u0003\u0002v!1q\u000f\u0001C\u0001\u0003sBq!! \u0001\t\u0003\ny\bC\u0004\u0002\f\u0002!\t%!$\u0003\u001fiK\u0007\u000fT1uKN$x+\u001b;icIR!a\n\u0015\u0002\u0011M\u001c\u0017\r\\1eg2T!!\u000b\u0016\u0002\rM$(/Z1n\u0015\u0005Y\u0013\u0001B1lW\u0006\u001c\u0001!\u0006\b/w![e*\u0015+X5v\u00037MZ5\u0014\u0005\u0001y\u0003c\u0001\u00194k5\t\u0011G\u0003\u00023Q\u0005)1\u000f^1hK&\u0011A'\r\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007c\u0004\u001c8s\u001dSU\nU*W3r{&-\u001a5\u000e\u0003!J!\u0001\u000f\u0015\u0003\u0019\u0019\u000bg.\u00138TQ\u0006\u0004X-\r\u001a\u0011\u0005iZD\u0002\u0001\u0003\u0006y\u0001\u0011\r!\u0010\u0002\u0003\u0003F\n\"A\u0010#\u0011\u0005}\u0012U\"\u0001!\u000b\u0003\u0005\u000bQa]2bY\u0006L!a\u0011!\u0003\u000f9{G\u000f[5oOB\u0011q(R\u0005\u0003\r\u0002\u00131!\u00118z!\tQ\u0004\nB\u0003J\u0001\t\u0007QH\u0001\u0002BeA\u0011!h\u0013\u0003\u0006\u0019\u0002\u0011\r!\u0010\u0002\u0003\u0003N\u0002\"A\u000f(\u0005\u000b=\u0003!\u0019A\u001f\u0003\u0005\u0005#\u0004C\u0001\u001eR\t\u0015\u0011\u0006A1\u0001>\u0005\t\tU\u0007\u0005\u0002;)\u0012)Q\u000b\u0001b\u0001{\t\u0011\u0011I\u000e\t\u0003u]#Q\u0001\u0017\u0001C\u0002u\u0012!!Q\u001c\u0011\u0005iRF!B.\u0001\u0005\u0004i$AA!9!\tQT\fB\u0003_\u0001\t\u0007QH\u0001\u0002BsA\u0011!\b\u0019\u0003\u0006C\u0002\u0011\r!\u0010\u0002\u0004\u0003F\u0002\u0004C\u0001\u001ed\t\u0015!\u0007A1\u0001>\u0005\r\t\u0015'\r\t\u0003u\u0019$Qa\u001a\u0001C\u0002u\u00121!Q\u00193!\tQ\u0014\u000eB\u0003k\u0001\t\u0007QHA\u0001P\u0003\u0019Q\u0018\u000e\u001d9feV\tQ\u000eE\b@]f:%*\u0014)T-fcvLY3i\u0013\ty\u0007I\u0001\u0006Gk:\u001cG/[8ocI\nqA_5qa\u0016\u0014\b%A\u0007fC\u001e,'oQ8na2,G/Z\u000b\u0002gB\u0011q\b^\u0005\u0003k\u0002\u0013qAQ8pY\u0016\fg.\u0001\bfC\u001e,'oQ8na2,G/\u001a\u0011\u0002\rqJg.\u001b;?)\rI8\u0010 \t\u0010u\u0002ItIS'Q'ZKFl\u00182fQ6\ta\u0005C\u0003l\u000b\u0001\u0007Q\u000eC\u0003r\u000b\u0001\u00071/A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\u0012a \t\u0004m\u0005\u0005\u0011bAA\u0002Q\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003U\naa\u001d5ba\u0016\u0004\u0013aA8viV\u0011\u0011q\u0002\t\u0005m\u0005E\u0001.C\u0002\u0002\u0014!\u0012aaT;uY\u0016$\u0018aA5oaU\u0011\u0011\u0011\u0004\t\u0005m\u0005m\u0011(C\u0002\u0002\u001e!\u0012Q!\u00138mKR\fA!\u001b81A\u0005\u0019\u0011N\\\u0019\u0016\u0005\u0005\u0015\u0002\u0003\u0002\u001c\u0002\u001c\u001d\u000bA!\u001b82A\u0005\u0019\u0011N\u001c\u001a\u0016\u0005\u00055\u0002\u0003\u0002\u001c\u0002\u001c)\u000bA!\u001b83A\u0005\u0019\u0011N\\\u001a\u0016\u0005\u0005U\u0002\u0003\u0002\u001c\u0002\u001c5\u000bA!\u001b84A\u0005\u0019\u0011N\u001c\u001b\u0016\u0005\u0005u\u0002\u0003\u0002\u001c\u0002\u001cA\u000bA!\u001b85A\u0005\u0019\u0011N\\\u001b\u0016\u0005\u0005\u0015\u0003\u0003\u0002\u001c\u0002\u001cM\u000bA!\u001b86A\u0005\u0019\u0011N\u001c\u001c\u0016\u0005\u00055\u0003\u0003\u0002\u001c\u0002\u001cY\u000bA!\u001b87A\u0005\u0019\u0011N\\\u001c\u0016\u0005\u0005U\u0003\u0003\u0002\u001c\u0002\u001ce\u000bA!\u001b88A\u0005\u0019\u0011N\u001c\u001d\u0016\u0005\u0005u\u0003\u0003\u0002\u001c\u0002\u001cq\u000bA!\u001b89A\u0005\u0019\u0011N\\\u001d\u0016\u0005\u0005\u0015\u0004\u0003\u0002\u001c\u0002\u001c}\u000bA!\u001b8:A\u0005!\u0011N\\\u00191+\t\ti\u0007\u0005\u00037\u00037\u0011\u0017!B5ocA\u0002\u0013\u0001B5ocE*\"!!\u001e\u0011\tY\nY\"Z\u0001\u0006S:\f\u0014\u0007\t\u000b\u0004s\u0006m\u0004\"B6#\u0001\u0004i\u0017aC2sK\u0006$X\rT8hS\u000e$B!!!\u0002\bB\u0019\u0001'a!\n\u0007\u0005\u0015\u0015GA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0019\tIi\ta\u0001\u007f\u0006\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fg\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\u0010B!\u0011\u0011SAN\u001b\t\t\u0019J\u0003\u0003\u0002\u0016\u0006]\u0015\u0001\u00027b]\u001eT!!!'\u0002\t)\fg/Y\u0005\u0005\u0003;\u000b\u0019J\u0001\u0004TiJLgn\u001a")
public class ZipLatestWith12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O>
extends GraphStage<FanInShape12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O>> {
    private final Function12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O> shape;
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

    public Function12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith12");
    }

    @Override
    public FanInShape12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith12$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith12$$anon$$contextPropagation;
            private final $anon$21$ZipLatestInlet<A1> inlet0;
            private final $anon$21$ZipLatestInlet<A2> inlet1;
            private final $anon$21$ZipLatestInlet<A3> inlet2;
            private final $anon$21$ZipLatestInlet<A4> inlet3;
            private final $anon$21$ZipLatestInlet<A5> inlet4;
            private final $anon$21$ZipLatestInlet<A6> inlet5;
            private final $anon$21$ZipLatestInlet<A7> inlet6;
            private final $anon$21$ZipLatestInlet<A8> inlet7;
            private final $anon$21$ZipLatestInlet<A9> inlet8;
            private final $anon$21$ZipLatestInlet<A10> inlet9;
            private final $anon$21$ZipLatestInlet<A11> inlet10;
            private final $anon$21$ZipLatestInlet<A12> inlet11;
            private boolean akka$stream$scaladsl$ZipLatestWith12$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith12$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith12 $outer;

            public int akka$stream$scaladsl$ZipLatestWith12$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith12$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith12$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith12$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith12$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith12$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith12$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith12$$anon$$contextPropagation;
            }

            private $anon$21$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$21$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$21$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$21$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$21$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$21$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$21$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$21$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            private $anon$21$ZipLatestInlet<A9> inlet8() {
                return this.inlet8;
            }

            private $anon$21$ZipLatestInlet<A10> inlet9() {
                return this.inlet9;
            }

            private $anon$21$ZipLatestInlet<A11> inlet10() {
                return this.inlet10;
            }

            private $anon$21$ZipLatestInlet<A12> inlet11() {
                return this.inlet11;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith12$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith12$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith12$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith12$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith12$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith12$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith12$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith12$$anon$$staleTupleValues = x$1;
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
            }

            public boolean akka$stream$scaladsl$ZipLatestWith12$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue() && this.inlet8().hasValue() && this.inlet9().hasValue() && this.inlet10().hasValue() && this.inlet11().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith12$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith12$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value(), this.inlet8().value(), this.inlet9().value(), this.inlet10().value(), this.inlet11().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith12$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith12$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith12$$anon$$tryPullAllIfNeeded() {
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
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith12 akka$stream$scaladsl$ZipLatestWith12$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith12$$anon$$upstreamsToWait = 12;
                this.akka$stream$scaladsl$ZipLatestWith12$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$21$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$21$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$21$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$21$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$21$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$21$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$21$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$21$ZipLatestInlet<A8>(this, $outer.in7());
                this.inlet8 = new $anon$21$ZipLatestInlet<A9>(this, $outer.in8());
                this.inlet9 = new $anon$21$ZipLatestInlet<A10>(this, $outer.in9());
                this.inlet10 = new $anon$21$ZipLatestInlet<A11>(this, $outer.in10());
                this.inlet11 = new $anon$21$ZipLatestInlet<A12>(this, $outer.in11());
                this.akka$stream$scaladsl$ZipLatestWith12$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith12$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$21 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith12$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith12$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith12$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith12$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith12$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith12$$anon$$tryPullAllIfNeeded();
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
            }
        };
    }

    public String toString() {
        return "ZipLatestWith12";
    }

    public ZipLatestWith12(Function12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape12("ZipLatestWith12");
        this.in0 = ((FanInShape12)this.shape()).in0();
        this.in1 = ((FanInShape12)this.shape()).in1();
        this.in2 = ((FanInShape12)this.shape()).in2();
        this.in3 = ((FanInShape12)this.shape()).in3();
        this.in4 = ((FanInShape12)this.shape()).in4();
        this.in5 = ((FanInShape12)this.shape()).in5();
        this.in6 = ((FanInShape12)this.shape()).in6();
        this.in7 = ((FanInShape12)this.shape()).in7();
        this.in8 = ((FanInShape12)this.shape()).in8();
        this.in9 = ((FanInShape12)this.shape()).in9();
        this.in10 = ((FanInShape12)this.shape()).in10();
        this.in11 = ((FanInShape12)this.shape()).in11();
    }

    public ZipLatestWith12(Function12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O> zipper) {
        this(zipper, true);
    }
}

