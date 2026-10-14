/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape10;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith10$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function10;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005md\u0001B\u0011#\u0001%B\u0001\"\u0019\u0001\u0003\u0006\u0004%\tA\u0019\u0005\tM\u0002\u0011\t\u0011)A\u0005G\"Aq\r\u0001BC\u0002\u0013\u0005\u0001\u000e\u0003\u0005m\u0001\t\u0005\t\u0015!\u0003j\u0011\u0015i\u0007\u0001\"\u0001o\u0011\u0015\u0019\b\u0001\"\u0011u\u0011\u001dA\bA1A\u0005BeDaA\u001f\u0001!\u0002\u0013\t\u0004\"B>\u0001\t\u0003a\b\"CA\u0001\u0001\t\u0007I\u0011AA\u0002\u0011!\tY\u0001\u0001Q\u0001\n\u0005\u0015\u0001\"CA\u0007\u0001\t\u0007I\u0011AA\b\u0011!\t\u0019\u0002\u0001Q\u0001\n\u0005E\u0001\"CA\u000b\u0001\t\u0007I\u0011AA\f\u0011!\tY\u0002\u0001Q\u0001\n\u0005e\u0001\"CA\u000f\u0001\t\u0007I\u0011AA\u0010\u0011!\t\u0019\u0003\u0001Q\u0001\n\u0005\u0005\u0002\"CA\u0013\u0001\t\u0007I\u0011AA\u0014\u0011!\tY\u0003\u0001Q\u0001\n\u0005%\u0002\"CA\u0017\u0001\t\u0007I\u0011AA\u0018\u0011!\t\u0019\u0004\u0001Q\u0001\n\u0005E\u0002\"CA\u001b\u0001\t\u0007I\u0011AA\u001c\u0011!\tY\u0004\u0001Q\u0001\n\u0005e\u0002\"CA\u001f\u0001\t\u0007I\u0011AA \u0011!\t\u0019\u0005\u0001Q\u0001\n\u0005\u0005\u0003\"CA#\u0001\t\u0007I\u0011AA$\u0011!\tY\u0005\u0001Q\u0001\n\u0005%\u0003\"CA'\u0001\t\u0007I\u0011AA(\u0011!\t\u0019\u0006\u0001Q\u0001\n\u0005E\u0003BB7\u0001\t\u0003\t)\u0006C\u0004\u0002Z\u0001!\t%a\u0017\t\u000f\u0005\u001d\u0004\u0001\"\u0011\u0002j\ty!,\u001b9MCR,7\u000f^,ji\"\f\u0004G\u0003\u0002$I\u0005A1oY1mC\u0012\u001cHN\u0003\u0002&M\u000511\u000f\u001e:fC6T\u0011aJ\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u0019):Di\u0012&N!N3\u0016\fX0\u0014\u0005\u0001Y\u0003c\u0001\u00170c5\tQF\u0003\u0002/I\u0005)1\u000f^1hK&\u0011\u0001'\f\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007#\u0004\u001a4k\r3\u0015\nT(S+b[f,D\u0001%\u0013\t!DE\u0001\u0007GC:Len\u00155ba\u0016\f\u0004\u0007\u0005\u00027o1\u0001A!\u0002\u001d\u0001\u0005\u0004I$AA!2#\tQ\u0004\t\u0005\u0002<}5\tAHC\u0001>\u0003\u0015\u00198-\u00197b\u0013\tyDHA\u0004O_RD\u0017N\\4\u0011\u0005m\n\u0015B\u0001\"=\u0005\r\te.\u001f\t\u0003m\u0011#Q!\u0012\u0001C\u0002e\u0012!!\u0011\u001a\u0011\u0005Y:E!\u0002%\u0001\u0005\u0004I$AA!4!\t1$\nB\u0003L\u0001\t\u0007\u0011H\u0001\u0002BiA\u0011a'\u0014\u0003\u0006\u001d\u0002\u0011\r!\u000f\u0002\u0003\u0003V\u0002\"A\u000e)\u0005\u000bE\u0003!\u0019A\u001d\u0003\u0005\u00053\u0004C\u0001\u001cT\t\u0015!\u0006A1\u0001:\u0005\t\tu\u0007\u0005\u00027-\u0012)q\u000b\u0001b\u0001s\t\u0011\u0011\t\u000f\t\u0003me#QA\u0017\u0001C\u0002e\u0012!!Q\u001d\u0011\u0005YbF!B/\u0001\u0005\u0004I$aA!2aA\u0011ag\u0018\u0003\u0006A\u0002\u0011\r!\u000f\u0002\u0002\u001f\u00061!0\u001b9qKJ,\u0012a\u0019\t\u000ew\u0011,4IR%M\u001fJ+\u0006l\u00170\n\u0005\u0015d$A\u0003$v]\u000e$\u0018n\u001c82a\u00059!0\u001b9qKJ\u0004\u0013!D3bO\u0016\u00148i\\7qY\u0016$X-F\u0001j!\tY$.\u0003\u0002ly\t9!i\\8mK\u0006t\u0017AD3bO\u0016\u00148i\\7qY\u0016$X\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007=\f(\u000fE\u0007q\u0001U\u001ae)\u0013'P%VC6LX\u0007\u0002E!)\u0011-\u0002a\u0001G\")q-\u0002a\u0001S\u0006\t\u0012N\\5uS\u0006d\u0017\t\u001e;sS\n,H/Z:\u0016\u0003U\u0004\"A\r<\n\u0005]$#AC!uiJL'-\u001e;fg\u0006)1\u000f[1qKV\t\u0011'\u0001\u0004tQ\u0006\u0004X\rI\u0001\u0004_V$X#A?\u0011\u0007Irh,\u0003\u0002\u0000I\t1q*\u001e;mKR\f1!\u001b81+\t\t)\u0001\u0005\u00033\u0003\u000f)\u0014bAA\u0005I\t)\u0011J\u001c7fi\u0006!\u0011N\u001c\u0019!\u0003\rIg.M\u000b\u0003\u0003#\u0001BAMA\u0004\u0007\u0006!\u0011N\\\u0019!\u0003\rIgNM\u000b\u0003\u00033\u0001BAMA\u0004\r\u0006!\u0011N\u001c\u001a!\u0003\rIgnM\u000b\u0003\u0003C\u0001BAMA\u0004\u0013\u0006!\u0011N\\\u001a!\u0003\rIg\u000eN\u000b\u0003\u0003S\u0001BAMA\u0004\u0019\u0006!\u0011N\u001c\u001b!\u0003\rIg.N\u000b\u0003\u0003c\u0001BAMA\u0004\u001f\u0006!\u0011N\\\u001b!\u0003\rIgNN\u000b\u0003\u0003s\u0001BAMA\u0004%\u0006!\u0011N\u001c\u001c!\u0003\rIgnN\u000b\u0003\u0003\u0003\u0002BAMA\u0004+\u0006!\u0011N\\\u001c!\u0003\rIg\u000eO\u000b\u0003\u0003\u0013\u0002BAMA\u00041\u0006!\u0011N\u001c\u001d!\u0003\rIg.O\u000b\u0003\u0003#\u0002BAMA\u00047\u0006!\u0011N\\\u001d!)\ry\u0017q\u000b\u0005\u0006Cz\u0001\raY\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u0002^\u0005\r\u0004c\u0001\u0017\u0002`%\u0019\u0011\u0011M\u0017\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDa!!\u001a \u0001\u0004)\u0018aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005-\u0004\u0003BA7\u0003oj!!a\u001c\u000b\t\u0005E\u00141O\u0001\u0005Y\u0006twM\u0003\u0002\u0002v\u0005!!.\u0019<b\u0013\u0011\tI(a\u001c\u0003\rM#(/\u001b8h\u0001")
public class ZipLatestWith10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O>
extends GraphStage<FanInShape10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O>> {
    private final Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O> shape;
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

    public Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith10");
    }

    @Override
    public FanInShape10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith10$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith10$$anon$$contextPropagation;
            private final $anon$17$ZipLatestInlet<A1> inlet0;
            private final $anon$17$ZipLatestInlet<A2> inlet1;
            private final $anon$17$ZipLatestInlet<A3> inlet2;
            private final $anon$17$ZipLatestInlet<A4> inlet3;
            private final $anon$17$ZipLatestInlet<A5> inlet4;
            private final $anon$17$ZipLatestInlet<A6> inlet5;
            private final $anon$17$ZipLatestInlet<A7> inlet6;
            private final $anon$17$ZipLatestInlet<A8> inlet7;
            private final $anon$17$ZipLatestInlet<A9> inlet8;
            private final $anon$17$ZipLatestInlet<A10> inlet9;
            private boolean akka$stream$scaladsl$ZipLatestWith10$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith10$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith10 $outer;

            public int akka$stream$scaladsl$ZipLatestWith10$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith10$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith10$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith10$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith10$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith10$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith10$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith10$$anon$$contextPropagation;
            }

            private $anon$17$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$17$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$17$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$17$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$17$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$17$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$17$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$17$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            private $anon$17$ZipLatestInlet<A9> inlet8() {
                return this.inlet8;
            }

            private $anon$17$ZipLatestInlet<A10> inlet9() {
                return this.inlet9;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith10$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith10$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith10$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith10$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith10$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith10$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith10$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith10$$anon$$staleTupleValues = x$1;
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
            }

            public boolean akka$stream$scaladsl$ZipLatestWith10$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue() && this.inlet8().hasValue() && this.inlet9().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith10$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith10$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value(), this.inlet8().value(), this.inlet9().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith10$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith10$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith10$$anon$$tryPullAllIfNeeded() {
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
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith10 akka$stream$scaladsl$ZipLatestWith10$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith10$$anon$$upstreamsToWait = 10;
                this.akka$stream$scaladsl$ZipLatestWith10$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$17$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$17$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$17$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$17$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$17$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$17$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$17$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$17$ZipLatestInlet<A8>(this, $outer.in7());
                this.inlet8 = new $anon$17$ZipLatestInlet<A9>(this, $outer.in8());
                this.inlet9 = new $anon$17$ZipLatestInlet<A10>(this, $outer.in9());
                this.akka$stream$scaladsl$ZipLatestWith10$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith10$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$17 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith10$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith10$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith10$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith10$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith10$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith10$$anon$$tryPullAllIfNeeded();
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
            }
        };
    }

    public String toString() {
        return "ZipLatestWith10";
    }

    public ZipLatestWith10(Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape10("ZipLatestWith10");
        this.in0 = ((FanInShape10)this.shape()).in0();
        this.in1 = ((FanInShape10)this.shape()).in1();
        this.in2 = ((FanInShape10)this.shape()).in2();
        this.in3 = ((FanInShape10)this.shape()).in3();
        this.in4 = ((FanInShape10)this.shape()).in4();
        this.in5 = ((FanInShape10)this.shape()).in5();
        this.in6 = ((FanInShape10)this.shape()).in6();
        this.in7 = ((FanInShape10)this.shape()).in7();
        this.in8 = ((FanInShape10)this.shape()).in8();
        this.in9 = ((FanInShape10)this.shape()).in9();
    }

    public ZipLatestWith10(Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O> zipper) {
        this(zipper, true);
    }
}

