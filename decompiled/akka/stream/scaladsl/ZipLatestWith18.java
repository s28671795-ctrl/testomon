/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape18;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith18$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function18;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t-a\u0001B\u00193\u0001eB!\"a\u0005\u0001\u0005\u000b\u0007I\u0011AA\u000b\u0011)\ti\u0002\u0001B\u0001B\u0003%\u0011q\u0003\u0005\u000b\u0003?\u0001!Q1A\u0005\u0002\u0005\u0005\u0002BCA\u0015\u0001\t\u0005\t\u0015!\u0003\u0002$!9\u00111\u0006\u0001\u0005\u0002\u00055\u0002bBA\u001c\u0001\u0011\u0005\u0013\u0011\b\u0005\n\u0003\u0003\u0002!\u0019!C!\u0003\u0007Bq!!\u0012\u0001A\u0003%\u0011\tC\u0004\u0002H\u0001!\t!!\u0013\t\u0013\u0005E\u0003A1A\u0005\u0002\u0005M\u0003\u0002CA.\u0001\u0001\u0006I!!\u0016\t\u0013\u0005u\u0003A1A\u0005\u0002\u0005}\u0003\u0002CA2\u0001\u0001\u0006I!!\u0019\t\u0013\u0005\u0015\u0004A1A\u0005\u0002\u0005\u001d\u0004\u0002CA6\u0001\u0001\u0006I!!\u001b\t\u0013\u00055\u0004A1A\u0005\u0002\u0005=\u0004\u0002CA:\u0001\u0001\u0006I!!\u001d\t\u0013\u0005U\u0004A1A\u0005\u0002\u0005]\u0004\u0002CA>\u0001\u0001\u0006I!!\u001f\t\u0013\u0005u\u0004A1A\u0005\u0002\u0005}\u0004\u0002CAB\u0001\u0001\u0006I!!!\t\u0013\u0005\u0015\u0005A1A\u0005\u0002\u0005\u001d\u0005\u0002CAF\u0001\u0001\u0006I!!#\t\u0013\u00055\u0005A1A\u0005\u0002\u0005=\u0005\u0002CAJ\u0001\u0001\u0006I!!%\t\u0013\u0005U\u0005A1A\u0005\u0002\u0005]\u0005\u0002CAN\u0001\u0001\u0006I!!'\t\u0013\u0005u\u0005A1A\u0005\u0002\u0005}\u0005\u0002CAR\u0001\u0001\u0006I!!)\t\u0013\u0005\u0015\u0006A1A\u0005\u0002\u0005\u001d\u0006\u0002CAV\u0001\u0001\u0006I!!+\t\u0013\u00055\u0006A1A\u0005\u0002\u0005=\u0006\u0002CAZ\u0001\u0001\u0006I!!-\t\u0013\u0005U\u0006A1A\u0005\u0002\u0005]\u0006\u0002CA^\u0001\u0001\u0006I!!/\t\u0013\u0005u\u0006A1A\u0005\u0002\u0005}\u0006\u0002CAb\u0001\u0001\u0006I!!1\t\u0013\u0005\u0015\u0007A1A\u0005\u0002\u0005\u001d\u0007\u0002CAf\u0001\u0001\u0006I!!3\t\u0013\u00055\u0007A1A\u0005\u0002\u0005=\u0007\u0002CAj\u0001\u0001\u0006I!!5\t\u0013\u0005U\u0007A1A\u0005\u0002\u0005]\u0007\u0002CAn\u0001\u0001\u0006I!!7\t\u0013\u0005u\u0007A1A\u0005\u0002\u0005}\u0007\u0002CAr\u0001\u0001\u0006I!!9\t\u000f\u0005-\u0002\u0001\"\u0001\u0002f\"9\u0011\u0011\u001e\u0001\u0005B\u0005-\bbBA|\u0001\u0011\u0005\u0013\u0011 \u0002\u00105&\u0004H*\u0019;fgR<\u0016\u000e\u001e52q)\u00111\u0007N\u0001\tg\u000e\fG.\u00193tY*\u0011QGN\u0001\u0007gR\u0014X-Y7\u000b\u0003]\nA!Y6lC\u000e\u0001Qc\u0006\u001eH)^SV\fY2gS2|'/\u001e=|}\u0006\r\u0011\u0011BA\b'\t\u00011\bE\u0002=\u007f\u0005k\u0011!\u0010\u0006\u0003}Q\nQa\u001d;bO\u0016L!\u0001Q\u001f\u0003\u0015\u001d\u0013\u0018\r\u001d5Ti\u0006<W\r\u0005\rC\u0007\u0016\u001bf+\u0017/`E\u0016D7N\\9uojl\u0018\u0011AA\u0004\u0003\u001bi\u0011\u0001N\u0005\u0003\tR\u0012ABR1o\u0013:\u001c\u0006.\u00199fca\u0002\"AR$\r\u0001\u0011)\u0001\n\u0001b\u0001\u0013\n\u0011\u0011)M\t\u0003\u0015B\u0003\"a\u0013(\u000e\u00031S\u0011!T\u0001\u0006g\u000e\fG.Y\u0005\u0003\u001f2\u0013qAT8uQ&tw\r\u0005\u0002L#&\u0011!\u000b\u0014\u0002\u0004\u0003:L\bC\u0001$U\t\u0015)\u0006A1\u0001J\u0005\t\t%\u0007\u0005\u0002G/\u0012)\u0001\f\u0001b\u0001\u0013\n\u0011\u0011i\r\t\u0003\rj#Qa\u0017\u0001C\u0002%\u0013!!\u0011\u001b\u0011\u0005\u0019kF!\u00020\u0001\u0005\u0004I%AA!6!\t1\u0005\rB\u0003b\u0001\t\u0007\u0011J\u0001\u0002BmA\u0011ai\u0019\u0003\u0006I\u0002\u0011\r!\u0013\u0002\u0003\u0003^\u0002\"A\u00124\u0005\u000b\u001d\u0004!\u0019A%\u0003\u0005\u0005C\u0004C\u0001$j\t\u0015Q\u0007A1\u0001J\u0005\t\t\u0015\b\u0005\u0002GY\u0012)Q\u000e\u0001b\u0001\u0013\n\u0019\u0011)\r\u0019\u0011\u0005\u0019{G!\u00029\u0001\u0005\u0004I%aA!2cA\u0011aI\u001d\u0003\u0006g\u0002\u0011\r!\u0013\u0002\u0004\u0003F\u0012\u0004C\u0001$v\t\u00151\bA1\u0001J\u0005\r\t\u0015g\r\t\u0003\rb$Q!\u001f\u0001C\u0002%\u00131!Q\u00195!\t15\u0010B\u0003}\u0001\t\u0007\u0011JA\u0002BcU\u0002\"A\u0012@\u0005\u000b}\u0004!\u0019A%\u0003\u0007\u0005\u000bd\u0007E\u0002G\u0003\u0007!a!!\u0002\u0001\u0005\u0004I%aA!2oA\u0019a)!\u0003\u0005\r\u0005-\u0001A1\u0001J\u0005\r\t\u0015\u0007\u000f\t\u0004\r\u0006=AABA\t\u0001\t\u0007\u0011JA\u0001P\u0003\u0019Q\u0018\u000e\u001d9feV\u0011\u0011q\u0003\t\u001a\u0017\u0006eQi\u0015,Z9~\u0013W\r[6ocR<(0`A\u0001\u0003\u000f\ti!C\u0002\u0002\u001c1\u0013!BR;oGRLwN\\\u00199\u0003\u001dQ\u0018\u000e\u001d9fe\u0002\nQ\"Z1hKJ\u001cu.\u001c9mKR,WCAA\u0012!\rY\u0015QE\u0005\u0004\u0003Oa%a\u0002\"p_2,\u0017M\\\u0001\u000fK\u0006<WM]\"p[BdW\r^3!\u0003\u0019a\u0014N\\5u}Q1\u0011qFA\u001a\u0003k\u0001\u0012$!\r\u0001\u000bN3\u0016\fX0cK\"\\g.\u001d;xuv\f\t!a\u0002\u0002\u000e5\t!\u0007C\u0004\u0002\u0014\u0015\u0001\r!a\u0006\t\u000f\u0005}Q\u00011\u0001\u0002$\u0005\t\u0012N\\5uS\u0006d\u0017\t\u001e;sS\n,H/Z:\u0016\u0005\u0005m\u0002c\u0001\"\u0002>%\u0019\u0011q\b\u001b\u0003\u0015\u0005#HO]5ckR,7/A\u0003tQ\u0006\u0004X-F\u0001B\u0003\u0019\u0019\b.\u00199fA\u0005\u0019q.\u001e;\u0016\u0005\u0005-\u0003#\u0002\"\u0002N\u00055\u0011bAA(i\t1q*\u001e;mKR\f1!\u001b81+\t\t)\u0006\u0005\u0003C\u0003/*\u0015bAA-i\t)\u0011J\u001c7fi\u0006!\u0011N\u001c\u0019!\u0003\rIg.M\u000b\u0003\u0003C\u0002BAQA,'\u0006!\u0011N\\\u0019!\u0003\rIgNM\u000b\u0003\u0003S\u0002BAQA,-\u0006!\u0011N\u001c\u001a!\u0003\rIgnM\u000b\u0003\u0003c\u0002BAQA,3\u0006!\u0011N\\\u001a!\u0003\rIg\u000eN\u000b\u0003\u0003s\u0002BAQA,9\u0006!\u0011N\u001c\u001b!\u0003\rIg.N\u000b\u0003\u0003\u0003\u0003BAQA,?\u0006!\u0011N\\\u001b!\u0003\rIgNN\u000b\u0003\u0003\u0013\u0003BAQA,E\u0006!\u0011N\u001c\u001c!\u0003\rIgnN\u000b\u0003\u0003#\u0003BAQA,K\u0006!\u0011N\\\u001c!\u0003\rIg\u000eO\u000b\u0003\u00033\u0003BAQA,Q\u0006!\u0011N\u001c\u001d!\u0003\rIg.O\u000b\u0003\u0003C\u0003BAQA,W\u0006!\u0011N\\\u001d!\u0003\u0011Ig.\r\u0019\u0016\u0005\u0005%\u0006\u0003\u0002\"\u0002X9\fQ!\u001b82a\u0001\nA!\u001b82cU\u0011\u0011\u0011\u0017\t\u0005\u0005\u0006]\u0013/A\u0003j]F\n\u0004%\u0001\u0003j]F\u0012TCAA]!\u0011\u0011\u0015q\u000b;\u0002\u000b%t\u0017G\r\u0011\u0002\t%t\u0017gM\u000b\u0003\u0003\u0003\u0004BAQA,o\u0006)\u0011N\\\u00194A\u0005!\u0011N\\\u00195+\t\tI\r\u0005\u0003C\u0003/R\u0018!B5ocQ\u0002\u0013\u0001B5ocU*\"!!5\u0011\t\t\u000b9&`\u0001\u0006S:\fT\u0007I\u0001\u0005S:\fd'\u0006\u0002\u0002ZB)!)a\u0016\u0002\u0002\u0005)\u0011N\\\u00197A\u0005!\u0011N\\\u00198+\t\t\t\u000fE\u0003C\u0003/\n9!A\u0003j]F:\u0004\u0005\u0006\u0003\u00020\u0005\u001d\bbBA\n]\u0001\u0007\u0011qC\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u0002n\u0006M\bc\u0001\u001f\u0002p&\u0019\u0011\u0011_\u001f\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDq!!>0\u0001\u0004\tY$A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0005u_N#(/\u001b8h)\t\tY\u0010\u0005\u0003\u0002~\n\u001dQBAA\u0000\u0015\u0011\u0011\tAa\u0001\u0002\t1\fgn\u001a\u0006\u0003\u0005\u000b\tAA[1wC&!!\u0011BA\u0000\u0005\u0019\u0019FO]5oO\u0002")
public class ZipLatestWith18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O>
extends GraphStage<FanInShape18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O>> {
    private final Function18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O> shape;
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
    private final Inlet<A18> in17;

    public Function18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith18");
    }

    @Override
    public FanInShape18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O> shape() {
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

    public Inlet<A18> in17() {
        return this.in17;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith18$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith18$$anon$$contextPropagation;
            private final $anon$33$ZipLatestInlet<A1> inlet0;
            private final $anon$33$ZipLatestInlet<A2> inlet1;
            private final $anon$33$ZipLatestInlet<A3> inlet2;
            private final $anon$33$ZipLatestInlet<A4> inlet3;
            private final $anon$33$ZipLatestInlet<A5> inlet4;
            private final $anon$33$ZipLatestInlet<A6> inlet5;
            private final $anon$33$ZipLatestInlet<A7> inlet6;
            private final $anon$33$ZipLatestInlet<A8> inlet7;
            private final $anon$33$ZipLatestInlet<A9> inlet8;
            private final $anon$33$ZipLatestInlet<A10> inlet9;
            private final $anon$33$ZipLatestInlet<A11> inlet10;
            private final $anon$33$ZipLatestInlet<A12> inlet11;
            private final $anon$33$ZipLatestInlet<A13> inlet12;
            private final $anon$33$ZipLatestInlet<A14> inlet13;
            private final $anon$33$ZipLatestInlet<A15> inlet14;
            private final $anon$33$ZipLatestInlet<A16> inlet15;
            private final $anon$33$ZipLatestInlet<A17> inlet16;
            private final $anon$33$ZipLatestInlet<A18> inlet17;
            private boolean akka$stream$scaladsl$ZipLatestWith18$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith18$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith18 $outer;

            public int akka$stream$scaladsl$ZipLatestWith18$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith18$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith18$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith18$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith18$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith18$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith18$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith18$$anon$$contextPropagation;
            }

            private $anon$33$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$33$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$33$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$33$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$33$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$33$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$33$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$33$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            private $anon$33$ZipLatestInlet<A9> inlet8() {
                return this.inlet8;
            }

            private $anon$33$ZipLatestInlet<A10> inlet9() {
                return this.inlet9;
            }

            private $anon$33$ZipLatestInlet<A11> inlet10() {
                return this.inlet10;
            }

            private $anon$33$ZipLatestInlet<A12> inlet11() {
                return this.inlet11;
            }

            private $anon$33$ZipLatestInlet<A13> inlet12() {
                return this.inlet12;
            }

            private $anon$33$ZipLatestInlet<A14> inlet13() {
                return this.inlet13;
            }

            private $anon$33$ZipLatestInlet<A15> inlet14() {
                return this.inlet14;
            }

            private $anon$33$ZipLatestInlet<A16> inlet15() {
                return this.inlet15;
            }

            private $anon$33$ZipLatestInlet<A17> inlet16() {
                return this.inlet16;
            }

            private $anon$33$ZipLatestInlet<A18> inlet17() {
                return this.inlet17;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith18$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith18$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith18$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith18$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith18$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith18$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith18$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith18$$anon$$staleTupleValues = x$1;
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
                this.pull(this.$outer.in17());
            }

            public boolean akka$stream$scaladsl$ZipLatestWith18$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue() && this.inlet8().hasValue() && this.inlet9().hasValue() && this.inlet10().hasValue() && this.inlet11().hasValue() && this.inlet12().hasValue() && this.inlet13().hasValue() && this.inlet14().hasValue() && this.inlet15().hasValue() && this.inlet16().hasValue() && this.inlet17().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith18$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith18$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value(), this.inlet8().value(), this.inlet9().value(), this.inlet10().value(), this.inlet11().value(), this.inlet12().value(), this.inlet13().value(), this.inlet14().value(), this.inlet15().value(), this.inlet16().value(), this.inlet17().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith18$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith18$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith18$$anon$$tryPullAllIfNeeded() {
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
                }
                if (!this.hasBeenPulled(this.$outer.in17())) {
                    this.tryPull(this.$outer.in17());
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith18 akka$stream$scaladsl$ZipLatestWith18$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith18$$anon$$upstreamsToWait = 18;
                this.akka$stream$scaladsl$ZipLatestWith18$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$33$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$33$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$33$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$33$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$33$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$33$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$33$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$33$ZipLatestInlet<A8>(this, $outer.in7());
                this.inlet8 = new $anon$33$ZipLatestInlet<A9>(this, $outer.in8());
                this.inlet9 = new $anon$33$ZipLatestInlet<A10>(this, $outer.in9());
                this.inlet10 = new $anon$33$ZipLatestInlet<A11>(this, $outer.in10());
                this.inlet11 = new $anon$33$ZipLatestInlet<A12>(this, $outer.in11());
                this.inlet12 = new $anon$33$ZipLatestInlet<A13>(this, $outer.in12());
                this.inlet13 = new $anon$33$ZipLatestInlet<A14>(this, $outer.in13());
                this.inlet14 = new $anon$33$ZipLatestInlet<A15>(this, $outer.in14());
                this.inlet15 = new $anon$33$ZipLatestInlet<A16>(this, $outer.in15());
                this.inlet16 = new $anon$33$ZipLatestInlet<A17>(this, $outer.in16());
                this.inlet17 = new $anon$33$ZipLatestInlet<A18>(this, $outer.in17());
                this.akka$stream$scaladsl$ZipLatestWith18$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith18$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$33 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith18$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith18$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith18$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith18$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith18$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith18$$anon$$tryPullAllIfNeeded();
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
                this.setHandler($outer.in17(), this.inlet17());
            }
        };
    }

    public String toString() {
        return "ZipLatestWith18";
    }

    public ZipLatestWith18(Function18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape18("ZipLatestWith18");
        this.in0 = ((FanInShape18)this.shape()).in0();
        this.in1 = ((FanInShape18)this.shape()).in1();
        this.in2 = ((FanInShape18)this.shape()).in2();
        this.in3 = ((FanInShape18)this.shape()).in3();
        this.in4 = ((FanInShape18)this.shape()).in4();
        this.in5 = ((FanInShape18)this.shape()).in5();
        this.in6 = ((FanInShape18)this.shape()).in6();
        this.in7 = ((FanInShape18)this.shape()).in7();
        this.in8 = ((FanInShape18)this.shape()).in8();
        this.in9 = ((FanInShape18)this.shape()).in9();
        this.in10 = ((FanInShape18)this.shape()).in10();
        this.in11 = ((FanInShape18)this.shape()).in11();
        this.in12 = ((FanInShape18)this.shape()).in12();
        this.in13 = ((FanInShape18)this.shape()).in13();
        this.in14 = ((FanInShape18)this.shape()).in14();
        this.in15 = ((FanInShape18)this.shape()).in15();
        this.in16 = ((FanInShape18)this.shape()).in16();
        this.in17 = ((FanInShape18)this.shape()).in17();
    }

    public ZipLatestWith18(Function18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O> zipper) {
        this(zipper, true);
    }
}

