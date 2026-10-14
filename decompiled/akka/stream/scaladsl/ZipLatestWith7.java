/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape7;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith7$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function7;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015c\u0001B\u000e\u001d\u0001\rB\u0001B\u0015\u0001\u0003\u0006\u0004%\ta\u0015\u0005\t/\u0002\u0011\t\u0011)A\u0005)\"A\u0001\f\u0001BC\u0002\u0013\u0005\u0011\f\u0003\u0005^\u0001\t\u0005\t\u0015!\u0003[\u0011\u0015q\u0006\u0001\"\u0001`\u0011\u0015!\u0007\u0001\"\u0011f\u0011\u001dI\u0007A1A\u0005B)Daa\u001b\u0001!\u0002\u0013Y\u0003\"\u00027\u0001\t\u0003i\u0007bB9\u0001\u0005\u0004%\tA\u001d\u0005\u0007m\u0002\u0001\u000b\u0011B:\t\u000f]\u0004!\u0019!C\u0001q\"1!\u0010\u0001Q\u0001\neDqa\u001f\u0001C\u0002\u0013\u0005A\u0010\u0003\u0004\u007f\u0001\u0001\u0006I! \u0005\t\u007f\u0002\u0011\r\u0011\"\u0001\u0002\u0002!A\u0011Q\u0001\u0001!\u0002\u0013\t\u0019\u0001C\u0005\u0002\b\u0001\u0011\r\u0011\"\u0001\u0002\n!A\u0011Q\u0002\u0001!\u0002\u0013\tY\u0001C\u0005\u0002\u0010\u0001\u0011\r\u0011\"\u0001\u0002\u0012!A\u0011Q\u0003\u0001!\u0002\u0013\t\u0019\u0002C\u0005\u0002\u0018\u0001\u0011\r\u0011\"\u0001\u0002\u001a!A\u0011Q\u0004\u0001!\u0002\u0013\tY\u0002\u0003\u0004_\u0001\u0011\u0005\u0011q\u0004\u0005\b\u0003G\u0001A\u0011IA\u0013\u0011\u001d\t\t\u0004\u0001C!\u0003g\u0011aBW5q\u0019\u0006$Xm\u001d;XSRDwG\u0003\u0002\u001e=\u0005A1oY1mC\u0012\u001cHN\u0003\u0002 A\u000511\u000f\u001e:fC6T\u0011!I\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u0013\u0011\nd(\u0011#H\u00156\u00036C\u0001\u0001&!\r1\u0013fK\u0007\u0002O)\u0011\u0001FH\u0001\u0006gR\fw-Z\u0005\u0003U\u001d\u0012!b\u0012:ba\"\u001cF/Y4f!)aSfL\u001fA\u0007\u001aKEjT\u0007\u0002=%\u0011aF\b\u0002\f\r\u0006t\u0017J\\*iCB,w\u0007\u0005\u00021c1\u0001A!\u0002\u001a\u0001\u0005\u0004\u0019$AA!2#\t!$\b\u0005\u00026q5\taGC\u00018\u0003\u0015\u00198-\u00197b\u0013\tIdGA\u0004O_RD\u0017N\\4\u0011\u0005UZ\u0014B\u0001\u001f7\u0005\r\te.\u001f\t\u0003ay\"Qa\u0010\u0001C\u0002M\u0012!!\u0011\u001a\u0011\u0005A\nE!\u0002\"\u0001\u0005\u0004\u0019$AA!4!\t\u0001D\tB\u0003F\u0001\t\u00071G\u0001\u0002BiA\u0011\u0001g\u0012\u0003\u0006\u0011\u0002\u0011\ra\r\u0002\u0003\u0003V\u0002\"\u0001\r&\u0005\u000b-\u0003!\u0019A\u001a\u0003\u0005\u00053\u0004C\u0001\u0019N\t\u0015q\u0005A1\u00014\u0005\t\tu\u0007\u0005\u00021!\u0012)\u0011\u000b\u0001b\u0001g\t\tq*\u0001\u0004{SB\u0004XM]\u000b\u0002)BQQ'V\u0018>\u0001\u000e3\u0015\nT(\n\u0005Y3$!\u0003$v]\u000e$\u0018n\u001c88\u0003\u001dQ\u0018\u000e\u001d9fe\u0002\nQ\"Z1hKJ\u001cu.\u001c9mKR,W#\u0001.\u0011\u0005UZ\u0016B\u0001/7\u0005\u001d\u0011un\u001c7fC:\fa\"Z1hKJ\u001cu.\u001c9mKR,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0004A\n\u001c\u0007CC1\u0001_u\u00025IR%M\u001f6\tA\u0004C\u0003S\u000b\u0001\u0007A\u000bC\u0003Y\u000b\u0001\u0007!,A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\u0012A\u001a\t\u0003Y\u001dL!\u0001\u001b\u0010\u0003\u0015\u0005#HO]5ckR,7/A\u0003tQ\u0006\u0004X-F\u0001,\u0003\u0019\u0019\b.\u00199fA\u0005\u0019q.\u001e;\u0016\u00039\u00042\u0001L8P\u0013\t\u0001hD\u0001\u0004PkRdW\r^\u0001\u0004S:\u0004T#A:\u0011\u00071\"x&\u0003\u0002v=\t)\u0011J\u001c7fi\u0006!\u0011N\u001c\u0019!\u0003\rIg.M\u000b\u0002sB\u0019A\u0006^\u001f\u0002\t%t\u0017\u0007I\u0001\u0004S:\u0014T#A?\u0011\u00071\"\b)\u0001\u0003j]J\u0002\u0013aA5ogU\u0011\u00111\u0001\t\u0004YQ\u001c\u0015\u0001B5og\u0001\n1!\u001b85+\t\tY\u0001E\u0002-i\u001a\u000bA!\u001b85A\u0005\u0019\u0011N\\\u001b\u0016\u0005\u0005M\u0001c\u0001\u0017u\u0013\u0006!\u0011N\\\u001b!\u0003\rIgNN\u000b\u0003\u00037\u00012\u0001\f;M\u0003\u0011IgN\u000e\u0011\u0015\u0007\u0001\f\t\u0003C\u0003S1\u0001\u0007A+A\u0006de\u0016\fG/\u001a'pO&\u001cG\u0003BA\u0014\u0003[\u00012AJA\u0015\u0013\r\tYc\n\u0002\u0010\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jG\"1\u0011qF\rA\u0002\u0019\f1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003k\u0001B!a\u000e\u0002B5\u0011\u0011\u0011\b\u0006\u0005\u0003w\ti$\u0001\u0003mC:<'BAA \u0003\u0011Q\u0017M^1\n\t\u0005\r\u0013\u0011\b\u0002\u0007'R\u0014\u0018N\\4")
public class ZipLatestWith7<A1, A2, A3, A4, A5, A6, A7, O>
extends GraphStage<FanInShape7<A1, A2, A3, A4, A5, A6, A7, O>> {
    private final Function7<A1, A2, A3, A4, A5, A6, A7, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape7<A1, A2, A3, A4, A5, A6, A7, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;
    private final Inlet<A5> in4;
    private final Inlet<A6> in5;
    private final Inlet<A7> in6;

    public Function7<A1, A2, A3, A4, A5, A6, A7, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith7");
    }

    @Override
    public FanInShape7<A1, A2, A3, A4, A5, A6, A7, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith7$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith7$$anon$$contextPropagation;
            private final $anon$11$ZipLatestInlet<A1> inlet0;
            private final $anon$11$ZipLatestInlet<A2> inlet1;
            private final $anon$11$ZipLatestInlet<A3> inlet2;
            private final $anon$11$ZipLatestInlet<A4> inlet3;
            private final $anon$11$ZipLatestInlet<A5> inlet4;
            private final $anon$11$ZipLatestInlet<A6> inlet5;
            private final $anon$11$ZipLatestInlet<A7> inlet6;
            private boolean akka$stream$scaladsl$ZipLatestWith7$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith7$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith7 $outer;

            public int akka$stream$scaladsl$ZipLatestWith7$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith7$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith7$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith7$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith7$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith7$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith7$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith7$$anon$$contextPropagation;
            }

            private $anon$11$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$11$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$11$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$11$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$11$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$11$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$11$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith7$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith7$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith7$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith7$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith7$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith7$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith7$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith7$$anon$$staleTupleValues = x$1;
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
                this.pull(this.$outer.in4());
                this.pull(this.$outer.in5());
                this.pull(this.$outer.in6());
            }

            public boolean akka$stream$scaladsl$ZipLatestWith7$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith7$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith7$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith7$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith7$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith7$$anon$$tryPullAllIfNeeded() {
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
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith7 akka$stream$scaladsl$ZipLatestWith7$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith7$$anon$$upstreamsToWait = 7;
                this.akka$stream$scaladsl$ZipLatestWith7$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$11$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$11$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$11$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$11$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$11$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$11$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$11$ZipLatestInlet<A7>(this, $outer.in6());
                this.akka$stream$scaladsl$ZipLatestWith7$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith7$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$11 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith7$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith7$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith7$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith7$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith7$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith7$$anon$$tryPullAllIfNeeded();
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
            }
        };
    }

    public String toString() {
        return "ZipLatestWith7";
    }

    public ZipLatestWith7(Function7<A1, A2, A3, A4, A5, A6, A7, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape7("ZipLatestWith7");
        this.in0 = ((FanInShape7)this.shape()).in0();
        this.in1 = ((FanInShape7)this.shape()).in1();
        this.in2 = ((FanInShape7)this.shape()).in2();
        this.in3 = ((FanInShape7)this.shape()).in3();
        this.in4 = ((FanInShape7)this.shape()).in4();
        this.in5 = ((FanInShape7)this.shape()).in5();
        this.in6 = ((FanInShape7)this.shape()).in6();
    }

    public ZipLatestWith7(Function7<A1, A2, A3, A4, A5, A6, A7, O> zipper) {
        this(zipper, true);
    }
}

