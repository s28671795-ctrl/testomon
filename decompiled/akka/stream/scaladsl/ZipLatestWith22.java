/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape22;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith22$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function22;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tMc\u0001B\u001d;\u0001\u0005C!\"a\u000f\u0001\u0005\u000b\u0007I\u0011AA\u001f\u0011)\t)\u0005\u0001B\u0001B\u0003%\u0011q\b\u0005\u000b\u0003\u000f\u0002!Q1A\u0005\u0002\u0005%\u0003BCA)\u0001\t\u0005\t\u0015!\u0003\u0002L!9\u00111\u000b\u0001\u0005\u0002\u0005U\u0003bBA0\u0001\u0011\u0005\u0013\u0011\r\u0005\n\u0003S\u0002!\u0019!C!\u0003WBq!!\u001c\u0001A\u0003%\u0011\nC\u0004\u0002p\u0001!\t!!\u001d\t\u0013\u0005e\u0004A1A\u0005\u0002\u0005m\u0004\u0002CAB\u0001\u0001\u0006I!! \t\u0013\u0005\u0015\u0005A1A\u0005\u0002\u0005\u001d\u0005\u0002CAF\u0001\u0001\u0006I!!#\t\u0013\u00055\u0005A1A\u0005\u0002\u0005=\u0005\u0002CAJ\u0001\u0001\u0006I!!%\t\u0013\u0005U\u0005A1A\u0005\u0002\u0005]\u0005\u0002CAN\u0001\u0001\u0006I!!'\t\u0013\u0005u\u0005A1A\u0005\u0002\u0005}\u0005\u0002CAR\u0001\u0001\u0006I!!)\t\u0013\u0005\u0015\u0006A1A\u0005\u0002\u0005\u001d\u0006\u0002CAV\u0001\u0001\u0006I!!+\t\u0013\u00055\u0006A1A\u0005\u0002\u0005=\u0006\u0002CAZ\u0001\u0001\u0006I!!-\t\u0013\u0005U\u0006A1A\u0005\u0002\u0005]\u0006\u0002CA^\u0001\u0001\u0006I!!/\t\u0013\u0005u\u0006A1A\u0005\u0002\u0005}\u0006\u0002CAb\u0001\u0001\u0006I!!1\t\u0013\u0005\u0015\u0007A1A\u0005\u0002\u0005\u001d\u0007\u0002CAf\u0001\u0001\u0006I!!3\t\u0013\u00055\u0007A1A\u0005\u0002\u0005=\u0007\u0002CAj\u0001\u0001\u0006I!!5\t\u0013\u0005U\u0007A1A\u0005\u0002\u0005]\u0007\u0002CAn\u0001\u0001\u0006I!!7\t\u0013\u0005u\u0007A1A\u0005\u0002\u0005}\u0007\u0002CAr\u0001\u0001\u0006I!!9\t\u0013\u0005\u0015\bA1A\u0005\u0002\u0005\u001d\b\u0002CAv\u0001\u0001\u0006I!!;\t\u0013\u00055\bA1A\u0005\u0002\u0005=\b\u0002CAz\u0001\u0001\u0006I!!=\t\u0013\u0005U\bA1A\u0005\u0002\u0005]\b\u0002CA~\u0001\u0001\u0006I!!?\t\u0013\u0005u\bA1A\u0005\u0002\u0005}\b\u0002\u0003B\u0002\u0001\u0001\u0006IA!\u0001\t\u0013\t\u0015\u0001A1A\u0005\u0002\t\u001d\u0001\u0002\u0003B\u0006\u0001\u0001\u0006IA!\u0003\t\u0013\t5\u0001A1A\u0005\u0002\t=\u0001\u0002\u0003B\n\u0001\u0001\u0006IA!\u0005\t\u0013\tU\u0001A1A\u0005\u0002\t]\u0001\u0002\u0003B\u000e\u0001\u0001\u0006IA!\u0007\t\u0013\tu\u0001A1A\u0005\u0002\t}\u0001\u0002\u0003B\u0012\u0001\u0001\u0006IA!\t\t\u0013\t\u0015\u0002A1A\u0005\u0002\t\u001d\u0002\u0002\u0003B\u0016\u0001\u0001\u0006IA!\u000b\t\u000f\u0005M\u0003\u0001\"\u0001\u0003.!9!\u0011\u0007\u0001\u0005B\tM\u0002b\u0002B \u0001\u0011\u0005#\u0011\t\u0002\u00105&\u0004H*\u0019;fgR<\u0016\u000e\u001e53e)\u00111\bP\u0001\tg\u000e\fG.\u00193tY*\u0011QHP\u0001\u0007gR\u0014X-Y7\u000b\u0003}\nA!Y6lC\u000e\u0001QC\t\"P9~\u0013W\r[6ocR<(0`A\u0001\u0003\u000f\ti!a\u0005\u0002\u001a\u0005}\u0011QEA\u0016\u0003c\t9d\u0005\u0002\u0001\u0007B\u0019AiR%\u000e\u0003\u0015S!A\u0012\u001f\u0002\u000bM$\u0018mZ3\n\u0005!+%AC$sCBD7\u000b^1hKB\u0011#jS'\\=\u0006$wM[7qgZLHp`A\u0003\u0003\u0017\t\t\"a\u0006\u0002\u001e\u0005\r\u0012\u0011FA\u0018\u0003ki\u0011\u0001P\u0005\u0003\u0019r\u0012ABR1o\u0013:\u001c\u0006.\u00199feI\u0002\"AT(\r\u0001\u0011)\u0001\u000b\u0001b\u0001#\n\u0011\u0011)M\t\u0003%b\u0003\"a\u0015,\u000e\u0003QS\u0011!V\u0001\u0006g\u000e\fG.Y\u0005\u0003/R\u0013qAT8uQ&tw\r\u0005\u0002T3&\u0011!\f\u0016\u0002\u0004\u0003:L\bC\u0001(]\t\u0015i\u0006A1\u0001R\u0005\t\t%\u0007\u0005\u0002O?\u0012)\u0001\r\u0001b\u0001#\n\u0011\u0011i\r\t\u0003\u001d\n$Qa\u0019\u0001C\u0002E\u0013!!\u0011\u001b\u0011\u00059+G!\u00024\u0001\u0005\u0004\t&AA!6!\tq\u0005\u000eB\u0003j\u0001\t\u0007\u0011K\u0001\u0002BmA\u0011aj\u001b\u0003\u0006Y\u0002\u0011\r!\u0015\u0002\u0003\u0003^\u0002\"A\u00148\u0005\u000b=\u0004!\u0019A)\u0003\u0005\u0005C\u0004C\u0001(r\t\u0015\u0011\bA1\u0001R\u0005\t\t\u0015\b\u0005\u0002Oi\u0012)Q\u000f\u0001b\u0001#\n\u0019\u0011)\r\u0019\u0011\u00059;H!\u0002=\u0001\u0005\u0004\t&aA!2cA\u0011aJ\u001f\u0003\u0006w\u0002\u0011\r!\u0015\u0002\u0004\u0003F\u0012\u0004C\u0001(~\t\u0015q\bA1\u0001R\u0005\r\t\u0015g\r\t\u0004\u001d\u0006\u0005AABA\u0002\u0001\t\u0007\u0011KA\u0002BcQ\u00022ATA\u0004\t\u0019\tI\u0001\u0001b\u0001#\n\u0019\u0011)M\u001b\u0011\u00079\u000bi\u0001\u0002\u0004\u0002\u0010\u0001\u0011\r!\u0015\u0002\u0004\u0003F2\u0004c\u0001(\u0002\u0014\u00111\u0011Q\u0003\u0001C\u0002E\u00131!Q\u00198!\rq\u0015\u0011\u0004\u0003\u0007\u00037\u0001!\u0019A)\u0003\u0007\u0005\u000b\u0004\bE\u0002O\u0003?!a!!\t\u0001\u0005\u0004\t&aA!2sA\u0019a*!\n\u0005\r\u0005\u001d\u0002A1\u0001R\u0005\r\t%\u0007\r\t\u0004\u001d\u0006-BABA\u0017\u0001\t\u0007\u0011KA\u0002BeE\u00022ATA\u0019\t\u0019\t\u0019\u0004\u0001b\u0001#\n\u0019\u0011I\r\u001a\u0011\u00079\u000b9\u0004\u0002\u0004\u0002:\u0001\u0011\r!\u0015\u0002\u0002\u001f\u00061!0\u001b9qKJ,\"!a\u0010\u0011GM\u000b\t%T._C\u0012<'.\u001c9tmfdx0!\u0002\u0002\f\u0005E\u0011qCA\u000f\u0003G\tI#a\f\u00026%\u0019\u00111\t+\u0003\u0015\u0019+hn\u0019;j_:\u0014$'A\u0004{SB\u0004XM\u001d\u0011\u0002\u001b\u0015\fw-\u001a:D_6\u0004H.\u001a;f+\t\tY\u0005E\u0002T\u0003\u001bJ1!a\u0014U\u0005\u001d\u0011un\u001c7fC:\fa\"Z1hKJ\u001cu.\u001c9mKR,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0007\u0003/\nY&!\u0018\u0011G\u0005e\u0003!T._C\u0012<'.\u001c9tmfdx0!\u0002\u0002\f\u0005E\u0011qCA\u000f\u0003G\tI#a\f\u000265\t!\bC\u0004\u0002<\u0015\u0001\r!a\u0010\t\u000f\u0005\u001dS\u00011\u0001\u0002L\u0005\t\u0012N\\5uS\u0006d\u0017\t\u001e;sS\n,H/Z:\u0016\u0005\u0005\r\u0004c\u0001&\u0002f%\u0019\u0011q\r\u001f\u0003\u0015\u0005#HO]5ckR,7/A\u0003tQ\u0006\u0004X-F\u0001J\u0003\u0019\u0019\b.\u00199fA\u0005\u0019q.\u001e;\u0016\u0005\u0005M\u0004#\u0002&\u0002v\u0005U\u0012bAA<y\t1q*\u001e;mKR\f1!\u001b81+\t\ti\b\u0005\u0003K\u0003\u007fj\u0015bAAAy\t)\u0011J\u001c7fi\u0006!\u0011N\u001c\u0019!\u0003\rIg.M\u000b\u0003\u0003\u0013\u0003BASA@7\u0006!\u0011N\\\u0019!\u0003\rIgNM\u000b\u0003\u0003#\u0003BASA@=\u0006!\u0011N\u001c\u001a!\u0003\rIgnM\u000b\u0003\u00033\u0003BASA@C\u0006!\u0011N\\\u001a!\u0003\rIg\u000eN\u000b\u0003\u0003C\u0003BASA@I\u0006!\u0011N\u001c\u001b!\u0003\rIg.N\u000b\u0003\u0003S\u0003BASA@O\u0006!\u0011N\\\u001b!\u0003\rIgNN\u000b\u0003\u0003c\u0003BASA@U\u0006!\u0011N\u001c\u001c!\u0003\rIgnN\u000b\u0003\u0003s\u0003BASA@[\u0006!\u0011N\\\u001c!\u0003\rIg\u000eO\u000b\u0003\u0003\u0003\u0004BASA@a\u0006!\u0011N\u001c\u001d!\u0003\rIg.O\u000b\u0003\u0003\u0013\u0004BASA@g\u0006!\u0011N\\\u001d!\u0003\u0011Ig.\r\u0019\u0016\u0005\u0005E\u0007\u0003\u0002&\u0002\u0000Y\fQ!\u001b82a\u0001\nA!\u001b82cU\u0011\u0011\u0011\u001c\t\u0005\u0015\u0006}\u00140A\u0003j]F\n\u0004%\u0001\u0003j]F\u0012TCAAq!\u0011Q\u0015q\u0010?\u0002\u000b%t\u0017G\r\u0011\u0002\t%t\u0017gM\u000b\u0003\u0003S\u0004BASA@\u007f\u0006)\u0011N\\\u00194A\u0005!\u0011N\\\u00195+\t\t\t\u0010E\u0003K\u0003\u007f\n)!A\u0003j]F\"\u0004%\u0001\u0003j]F*TCAA}!\u0015Q\u0015qPA\u0006\u0003\u0015Ig.M\u001b!\u0003\u0011Ig.\r\u001c\u0016\u0005\t\u0005\u0001#\u0002&\u0002\u0000\u0005E\u0011!B5ocY\u0002\u0013\u0001B5oc]*\"A!\u0003\u0011\u000b)\u000by(a\u0006\u0002\u000b%t\u0017g\u000e\u0011\u0002\t%t\u0017\u0007O\u000b\u0003\u0005#\u0001RASA@\u0003;\tQ!\u001b82q\u0001\nA!\u001b82sU\u0011!\u0011\u0004\t\u0006\u0015\u0006}\u00141E\u0001\u0006S:\f\u0014\bI\u0001\u0005S:\u0014\u0004'\u0006\u0002\u0003\"A)!*a \u0002*\u0005)\u0011N\u001c\u001a1A\u0005!\u0011N\u001c\u001a2+\t\u0011I\u0003E\u0003K\u0003\u007f\ny#A\u0003j]J\n\u0004\u0005\u0006\u0003\u0002X\t=\u0002bBA\u001em\u0001\u0007\u0011qH\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u00036\tm\u0002c\u0001#\u00038%\u0019!\u0011H#\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDqA!\u00108\u0001\u0004\t\u0019'A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0005u_N#(/\u001b8h)\t\u0011\u0019\u0005\u0005\u0003\u0003F\t=SB\u0001B$\u0015\u0011\u0011IEa\u0013\u0002\t1\fgn\u001a\u0006\u0003\u0005\u001b\nAA[1wC&!!\u0011\u000bB$\u0005\u0019\u0019FO]5oO\u0002")
public class ZipLatestWith22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O>
extends GraphStage<FanInShape22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O>> {
    private final Function22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O> shape;
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
    private final Inlet<A19> in18;
    private final Inlet<A20> in19;
    private final Inlet<A21> in20;
    private final Inlet<A22> in21;

    public Function22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith22");
    }

    @Override
    public FanInShape22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O> shape() {
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

    public Inlet<A19> in18() {
        return this.in18;
    }

    public Inlet<A20> in19() {
        return this.in19;
    }

    public Inlet<A21> in20() {
        return this.in20;
    }

    public Inlet<A22> in21() {
        return this.in21;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith22$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith22$$anon$$contextPropagation;
            private final $anon$41$ZipLatestInlet<A1> inlet0;
            private final $anon$41$ZipLatestInlet<A2> inlet1;
            private final $anon$41$ZipLatestInlet<A3> inlet2;
            private final $anon$41$ZipLatestInlet<A4> inlet3;
            private final $anon$41$ZipLatestInlet<A5> inlet4;
            private final $anon$41$ZipLatestInlet<A6> inlet5;
            private final $anon$41$ZipLatestInlet<A7> inlet6;
            private final $anon$41$ZipLatestInlet<A8> inlet7;
            private final $anon$41$ZipLatestInlet<A9> inlet8;
            private final $anon$41$ZipLatestInlet<A10> inlet9;
            private final $anon$41$ZipLatestInlet<A11> inlet10;
            private final $anon$41$ZipLatestInlet<A12> inlet11;
            private final $anon$41$ZipLatestInlet<A13> inlet12;
            private final $anon$41$ZipLatestInlet<A14> inlet13;
            private final $anon$41$ZipLatestInlet<A15> inlet14;
            private final $anon$41$ZipLatestInlet<A16> inlet15;
            private final $anon$41$ZipLatestInlet<A17> inlet16;
            private final $anon$41$ZipLatestInlet<A18> inlet17;
            private final $anon$41$ZipLatestInlet<A19> inlet18;
            private final $anon$41$ZipLatestInlet<A20> inlet19;
            private final $anon$41$ZipLatestInlet<A21> inlet20;
            private final $anon$41$ZipLatestInlet<A22> inlet21;
            private boolean akka$stream$scaladsl$ZipLatestWith22$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith22$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith22 $outer;

            public int akka$stream$scaladsl$ZipLatestWith22$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith22$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith22$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith22$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith22$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith22$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith22$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith22$$anon$$contextPropagation;
            }

            private $anon$41$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$41$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$41$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$41$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$41$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$41$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$41$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$41$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            private $anon$41$ZipLatestInlet<A9> inlet8() {
                return this.inlet8;
            }

            private $anon$41$ZipLatestInlet<A10> inlet9() {
                return this.inlet9;
            }

            private $anon$41$ZipLatestInlet<A11> inlet10() {
                return this.inlet10;
            }

            private $anon$41$ZipLatestInlet<A12> inlet11() {
                return this.inlet11;
            }

            private $anon$41$ZipLatestInlet<A13> inlet12() {
                return this.inlet12;
            }

            private $anon$41$ZipLatestInlet<A14> inlet13() {
                return this.inlet13;
            }

            private $anon$41$ZipLatestInlet<A15> inlet14() {
                return this.inlet14;
            }

            private $anon$41$ZipLatestInlet<A16> inlet15() {
                return this.inlet15;
            }

            private $anon$41$ZipLatestInlet<A17> inlet16() {
                return this.inlet16;
            }

            private $anon$41$ZipLatestInlet<A18> inlet17() {
                return this.inlet17;
            }

            private $anon$41$ZipLatestInlet<A19> inlet18() {
                return this.inlet18;
            }

            private $anon$41$ZipLatestInlet<A20> inlet19() {
                return this.inlet19;
            }

            private $anon$41$ZipLatestInlet<A21> inlet20() {
                return this.inlet20;
            }

            private $anon$41$ZipLatestInlet<A22> inlet21() {
                return this.inlet21;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith22$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith22$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith22$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith22$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith22$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith22$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith22$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith22$$anon$$staleTupleValues = x$1;
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
                this.pull(this.$outer.in18());
                this.pull(this.$outer.in19());
                this.pull(this.$outer.in20());
                this.pull(this.$outer.in21());
            }

            public boolean akka$stream$scaladsl$ZipLatestWith22$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue() && this.inlet8().hasValue() && this.inlet9().hasValue() && this.inlet10().hasValue() && this.inlet11().hasValue() && this.inlet12().hasValue() && this.inlet13().hasValue() && this.inlet14().hasValue() && this.inlet15().hasValue() && this.inlet16().hasValue() && this.inlet17().hasValue() && this.inlet18().hasValue() && this.inlet19().hasValue() && this.inlet20().hasValue() && this.inlet21().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith22$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith22$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value(), this.inlet8().value(), this.inlet9().value(), this.inlet10().value(), this.inlet11().value(), this.inlet12().value(), this.inlet13().value(), this.inlet14().value(), this.inlet15().value(), this.inlet16().value(), this.inlet17().value(), this.inlet18().value(), this.inlet19().value(), this.inlet20().value(), this.inlet21().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith22$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith22$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith22$$anon$$tryPullAllIfNeeded() {
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
                }
                if (!this.hasBeenPulled(this.$outer.in18())) {
                    this.tryPull(this.$outer.in18());
                }
                if (!this.hasBeenPulled(this.$outer.in19())) {
                    this.tryPull(this.$outer.in19());
                }
                if (!this.hasBeenPulled(this.$outer.in20())) {
                    this.tryPull(this.$outer.in20());
                }
                if (!this.hasBeenPulled(this.$outer.in21())) {
                    this.tryPull(this.$outer.in21());
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith22 akka$stream$scaladsl$ZipLatestWith22$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith22$$anon$$upstreamsToWait = 22;
                this.akka$stream$scaladsl$ZipLatestWith22$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$41$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$41$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$41$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$41$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$41$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$41$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$41$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$41$ZipLatestInlet<A8>(this, $outer.in7());
                this.inlet8 = new $anon$41$ZipLatestInlet<A9>(this, $outer.in8());
                this.inlet9 = new $anon$41$ZipLatestInlet<A10>(this, $outer.in9());
                this.inlet10 = new $anon$41$ZipLatestInlet<A11>(this, $outer.in10());
                this.inlet11 = new $anon$41$ZipLatestInlet<A12>(this, $outer.in11());
                this.inlet12 = new $anon$41$ZipLatestInlet<A13>(this, $outer.in12());
                this.inlet13 = new $anon$41$ZipLatestInlet<A14>(this, $outer.in13());
                this.inlet14 = new $anon$41$ZipLatestInlet<A15>(this, $outer.in14());
                this.inlet15 = new $anon$41$ZipLatestInlet<A16>(this, $outer.in15());
                this.inlet16 = new $anon$41$ZipLatestInlet<A17>(this, $outer.in16());
                this.inlet17 = new $anon$41$ZipLatestInlet<A18>(this, $outer.in17());
                this.inlet18 = new $anon$41$ZipLatestInlet<A19>(this, $outer.in18());
                this.inlet19 = new $anon$41$ZipLatestInlet<A20>(this, $outer.in19());
                this.inlet20 = new $anon$41$ZipLatestInlet<A21>(this, $outer.in20());
                this.inlet21 = new $anon$41$ZipLatestInlet<A22>(this, $outer.in21());
                this.akka$stream$scaladsl$ZipLatestWith22$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith22$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$41 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith22$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith22$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith22$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith22$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith22$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith22$$anon$$tryPullAllIfNeeded();
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
                this.setHandler($outer.in18(), this.inlet18());
                this.setHandler($outer.in19(), this.inlet19());
                this.setHandler($outer.in20(), this.inlet20());
                this.setHandler($outer.in21(), this.inlet21());
            }
        };
    }

    public String toString() {
        return "ZipLatestWith22";
    }

    public ZipLatestWith22(Function22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape22("ZipLatestWith22");
        this.in0 = ((FanInShape22)this.shape()).in0();
        this.in1 = ((FanInShape22)this.shape()).in1();
        this.in2 = ((FanInShape22)this.shape()).in2();
        this.in3 = ((FanInShape22)this.shape()).in3();
        this.in4 = ((FanInShape22)this.shape()).in4();
        this.in5 = ((FanInShape22)this.shape()).in5();
        this.in6 = ((FanInShape22)this.shape()).in6();
        this.in7 = ((FanInShape22)this.shape()).in7();
        this.in8 = ((FanInShape22)this.shape()).in8();
        this.in9 = ((FanInShape22)this.shape()).in9();
        this.in10 = ((FanInShape22)this.shape()).in10();
        this.in11 = ((FanInShape22)this.shape()).in11();
        this.in12 = ((FanInShape22)this.shape()).in12();
        this.in13 = ((FanInShape22)this.shape()).in13();
        this.in14 = ((FanInShape22)this.shape()).in14();
        this.in15 = ((FanInShape22)this.shape()).in15();
        this.in16 = ((FanInShape22)this.shape()).in16();
        this.in17 = ((FanInShape22)this.shape()).in17();
        this.in18 = ((FanInShape22)this.shape()).in18();
        this.in19 = ((FanInShape22)this.shape()).in19();
        this.in20 = ((FanInShape22)this.shape()).in20();
        this.in21 = ((FanInShape22)this.shape()).in21();
    }

    public ZipLatestWith22(Function22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O> zipper) {
        this(zipper, true);
    }
}

