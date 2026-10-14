/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape4;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith4$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function4;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005=a\u0001B\u000b\u0017\u0001uA\u0001b\u0011\u0001\u0003\u0006\u0004%\t\u0001\u0012\u0005\t\u0011\u0002\u0011\t\u0011)A\u0005\u000b\"A\u0011\n\u0001BC\u0002\u0013\u0005!\n\u0003\u0005O\u0001\t\u0005\t\u0015!\u0003L\u0011\u0015y\u0005\u0001\"\u0001Q\u0011\u0015)\u0006\u0001\"\u0011W\u0011\u001dQ\u0006A1A\u0005BmCa\u0001\u0018\u0001!\u0002\u0013)\u0003\"B/\u0001\t\u0003q\u0006b\u00022\u0001\u0005\u0004%\ta\u0019\u0005\u0007O\u0002\u0001\u000b\u0011\u00023\t\u000f!\u0004!\u0019!C\u0001S\"11\u000e\u0001Q\u0001\n)Dq\u0001\u001c\u0001C\u0002\u0013\u0005Q\u000e\u0003\u0004p\u0001\u0001\u0006IA\u001c\u0005\ba\u0002\u0011\r\u0011\"\u0001r\u0011\u0019\u0019\b\u0001)A\u0005e\")q\n\u0001C\u0001i\")a\u000f\u0001C!o\")Q\u0010\u0001C!}\nq!,\u001b9MCR,7\u000f^,ji\"$$BA\f\u0019\u0003!\u00198-\u00197bINd'BA\r\u001b\u0003\u0019\u0019HO]3b[*\t1$\u0001\u0003bW.\f7\u0001A\u000b\u0007=-B4HP!\u0014\u0005\u0001y\u0002c\u0001\u0011$K5\t\u0011E\u0003\u0002#1\u0005)1\u000f^1hK&\u0011A%\t\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007c\u0002\u0014(S]RT\bQ\u0007\u00021%\u0011\u0001\u0006\u0007\u0002\f\r\u0006t\u0017J\\*iCB,G\u0007\u0005\u0002+W1\u0001A!\u0002\u0017\u0001\u0005\u0004i#AA!2#\tqC\u0007\u0005\u00020e5\t\u0001GC\u00012\u0003\u0015\u00198-\u00197b\u0013\t\u0019\u0004GA\u0004O_RD\u0017N\\4\u0011\u0005=*\u0014B\u0001\u001c1\u0005\r\te.\u001f\t\u0003Ua\"Q!\u000f\u0001C\u00025\u0012!!\u0011\u001a\u0011\u0005)ZD!\u0002\u001f\u0001\u0005\u0004i#AA!4!\tQc\bB\u0003@\u0001\t\u0007QF\u0001\u0002BiA\u0011!&\u0011\u0003\u0006\u0005\u0002\u0011\r!\f\u0002\u0002\u001f\u00061!0\u001b9qKJ,\u0012!\u0012\t\b_\u0019KsGO\u001fA\u0013\t9\u0005GA\u0005Gk:\u001cG/[8oi\u00059!0\u001b9qKJ\u0004\u0013!D3bO\u0016\u00148i\\7qY\u0016$X-F\u0001L!\tyC*\u0003\u0002Na\t9!i\\8mK\u0006t\u0017AD3bO\u0016\u00148i\\7qY\u0016$X\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007E\u001bF\u000bE\u0004S\u0001%:$(\u0010!\u000e\u0003YAQaQ\u0003A\u0002\u0015CQ!S\u0003A\u0002-\u000b\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\u00059\u0006C\u0001\u0014Y\u0013\tI\u0006D\u0001\u0006BiR\u0014\u0018NY;uKN\fQa\u001d5ba\u0016,\u0012!J\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002\u0007=,H/F\u0001`!\r1\u0003\rQ\u0005\u0003Cb\u0011aaT;uY\u0016$\u0018aA5oaU\tA\rE\u0002'K&J!A\u001a\r\u0003\u000b%sG.\u001a;\u0002\t%t\u0007\u0007I\u0001\u0004S:\fT#\u00016\u0011\u0007\u0019*w'\u0001\u0003j]F\u0002\u0013aA5oeU\ta\u000eE\u0002'Kj\nA!\u001b83A\u0005\u0019\u0011N\\\u001a\u0016\u0003I\u00042AJ3>\u0003\u0011Ign\r\u0011\u0015\u0005E+\b\"B\"\u0013\u0001\u0004)\u0015aC2sK\u0006$X\rT8hS\u000e$\"\u0001_>\u0011\u0005\u0001J\u0018B\u0001>\"\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007\"\u0002?\u0014\u0001\u00049\u0016aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003}\u0004B!!\u0001\u0002\f5\u0011\u00111\u0001\u0006\u0005\u0003\u000b\t9!\u0001\u0003mC:<'BAA\u0005\u0003\u0011Q\u0017M^1\n\t\u00055\u00111\u0001\u0002\u0007'R\u0014\u0018N\\4")
public class ZipLatestWith4<A1, A2, A3, A4, O>
extends GraphStage<FanInShape4<A1, A2, A3, A4, O>> {
    private final Function4<A1, A2, A3, A4, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape4<A1, A2, A3, A4, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;

    public Function4<A1, A2, A3, A4, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith4");
    }

    @Override
    public FanInShape4<A1, A2, A3, A4, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith4$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith4$$anon$$contextPropagation;
            private final $anon$5$ZipLatestInlet<A1> inlet0;
            private final $anon$5$ZipLatestInlet<A2> inlet1;
            private final $anon$5$ZipLatestInlet<A3> inlet2;
            private final $anon$5$ZipLatestInlet<A4> inlet3;
            private boolean akka$stream$scaladsl$ZipLatestWith4$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith4$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith4 $outer;

            public int akka$stream$scaladsl$ZipLatestWith4$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith4$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith4$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith4$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith4$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith4$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith4$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith4$$anon$$contextPropagation;
            }

            private $anon$5$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$5$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$5$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$5$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith4$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith4$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith4$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith4$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith4$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith4$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith4$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith4$$anon$$staleTupleValues = x$1;
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
            }

            public boolean akka$stream$scaladsl$ZipLatestWith4$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith4$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith4$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith4$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith4$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith4$$anon$$tryPullAllIfNeeded() {
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
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith4 akka$stream$scaladsl$ZipLatestWith4$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith4$$anon$$upstreamsToWait = 4;
                this.akka$stream$scaladsl$ZipLatestWith4$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$5$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$5$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$5$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$5$ZipLatestInlet<A4>(this, $outer.in3());
                this.akka$stream$scaladsl$ZipLatestWith4$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith4$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$5 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith4$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith4$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith4$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith4$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith4$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith4$$anon$$tryPullAllIfNeeded();
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
            }
        };
    }

    public String toString() {
        return "ZipLatestWith4";
    }

    public ZipLatestWith4(Function4<A1, A2, A3, A4, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape4("ZipLatestWith4");
        this.in0 = ((FanInShape4)this.shape()).in0();
        this.in1 = ((FanInShape4)this.shape()).in1();
        this.in2 = ((FanInShape4)this.shape()).in2();
        this.in3 = ((FanInShape4)this.shape()).in3();
    }

    public ZipLatestWith4(Function4<A1, A2, A3, A4, O> zipper) {
        this(zipper, true);
    }
}

