/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape21;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith21$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function21;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t\u0005c\u0001B\u001c9\u0001}B!\"!\r\u0001\u0005\u000b\u0007I\u0011AA\u001a\u0011)\tY\u0004\u0001B\u0001B\u0003%\u0011Q\u0007\u0005\u000b\u0003{\u0001!Q1A\u0005\u0002\u0005}\u0002BCA$\u0001\t\u0005\t\u0015!\u0003\u0002B!9\u0011\u0011\n\u0001\u0005\u0002\u0005-\u0003bBA+\u0001\u0011\u0005\u0013q\u000b\u0005\n\u0003?\u0002!\u0019!C!\u0003CBq!a\u0019\u0001A\u0003%q\tC\u0004\u0002f\u0001!\t!a\u001a\t\u0013\u0005=\u0004A1A\u0005\u0002\u0005E\u0004\u0002CA=\u0001\u0001\u0006I!a\u001d\t\u0013\u0005m\u0004A1A\u0005\u0002\u0005u\u0004\u0002CAA\u0001\u0001\u0006I!a \t\u0013\u0005\r\u0005A1A\u0005\u0002\u0005\u0015\u0005\u0002CAE\u0001\u0001\u0006I!a\"\t\u0013\u0005-\u0005A1A\u0005\u0002\u00055\u0005\u0002CAI\u0001\u0001\u0006I!a$\t\u0013\u0005M\u0005A1A\u0005\u0002\u0005U\u0005\u0002CAM\u0001\u0001\u0006I!a&\t\u0013\u0005m\u0005A1A\u0005\u0002\u0005u\u0005\u0002CAQ\u0001\u0001\u0006I!a(\t\u0013\u0005\r\u0006A1A\u0005\u0002\u0005\u0015\u0006\u0002CAU\u0001\u0001\u0006I!a*\t\u0013\u0005-\u0006A1A\u0005\u0002\u00055\u0006\u0002CAY\u0001\u0001\u0006I!a,\t\u0013\u0005M\u0006A1A\u0005\u0002\u0005U\u0006\u0002CA]\u0001\u0001\u0006I!a.\t\u0013\u0005m\u0006A1A\u0005\u0002\u0005u\u0006\u0002CAa\u0001\u0001\u0006I!a0\t\u0013\u0005\r\u0007A1A\u0005\u0002\u0005\u0015\u0007\u0002CAe\u0001\u0001\u0006I!a2\t\u0013\u0005-\u0007A1A\u0005\u0002\u00055\u0007\u0002CAi\u0001\u0001\u0006I!a4\t\u0013\u0005M\u0007A1A\u0005\u0002\u0005U\u0007\u0002CAm\u0001\u0001\u0006I!a6\t\u0013\u0005m\u0007A1A\u0005\u0002\u0005u\u0007\u0002CAq\u0001\u0001\u0006I!a8\t\u0013\u0005\r\bA1A\u0005\u0002\u0005\u0015\b\u0002CAu\u0001\u0001\u0006I!a:\t\u0013\u0005-\bA1A\u0005\u0002\u00055\b\u0002CAy\u0001\u0001\u0006I!a<\t\u0013\u0005M\bA1A\u0005\u0002\u0005U\b\u0002CA}\u0001\u0001\u0006I!a>\t\u0013\u0005m\bA1A\u0005\u0002\u0005u\b\u0002\u0003B\u0001\u0001\u0001\u0006I!a@\t\u0013\t\r\u0001A1A\u0005\u0002\t\u0015\u0001\u0002\u0003B\u0005\u0001\u0001\u0006IAa\u0002\t\u0013\t-\u0001A1A\u0005\u0002\t5\u0001\u0002\u0003B\t\u0001\u0001\u0006IAa\u0004\t\u0013\tM\u0001A1A\u0005\u0002\tU\u0001\u0002\u0003B\r\u0001\u0001\u0006IAa\u0006\t\u000f\u0005%\u0003\u0001\"\u0001\u0003\u001c!9!q\u0004\u0001\u0005B\t\u0005\u0002b\u0002B\u0017\u0001\u0011\u0005#q\u0006\u0002\u00105&\u0004H*\u0019;fgR<\u0016\u000e\u001e53c)\u0011\u0011HO\u0001\tg\u000e\fG.\u00193tY*\u00111\bP\u0001\u0007gR\u0014X-Y7\u000b\u0003u\nA!Y6lC\u000e\u0001Qc\b!N5v\u00037MZ5m_J,\bp\u001f@\u0002\u0004\u0005%\u0011qBA\u000b\u00037\t\t#a\n\u0002.M\u0011\u0001!\u0011\t\u0004\u0005\u0016;U\"A\"\u000b\u0005\u0011S\u0014!B:uC\u001e,\u0017B\u0001$D\u0005)9%/\u00199i'R\fw-\u001a\t!\u0011&[\u0015\fX0cK\"\\g.\u001d;xuv\f\t!a\u0002\u0002\u000e\u0005M\u0011\u0011DA\u0010\u0003K\tY#D\u0001;\u0013\tQ%H\u0001\u0007GC:Len\u00155ba\u0016\u0014\u0014\u0007\u0005\u0002M\u001b2\u0001A!\u0002(\u0001\u0005\u0004y%AA!2#\t\u0001f\u000b\u0005\u0002R)6\t!KC\u0001T\u0003\u0015\u00198-\u00197b\u0013\t)&KA\u0004O_RD\u0017N\\4\u0011\u0005E;\u0016B\u0001-S\u0005\r\te.\u001f\t\u0003\u0019j#Qa\u0017\u0001C\u0002=\u0013!!\u0011\u001a\u0011\u00051kF!\u00020\u0001\u0005\u0004y%AA!4!\ta\u0005\rB\u0003b\u0001\t\u0007qJ\u0001\u0002BiA\u0011Aj\u0019\u0003\u0006I\u0002\u0011\ra\u0014\u0002\u0003\u0003V\u0002\"\u0001\u00144\u0005\u000b\u001d\u0004!\u0019A(\u0003\u0005\u00053\u0004C\u0001'j\t\u0015Q\u0007A1\u0001P\u0005\t\tu\u0007\u0005\u0002MY\u0012)Q\u000e\u0001b\u0001\u001f\n\u0011\u0011\t\u000f\t\u0003\u0019>$Q\u0001\u001d\u0001C\u0002=\u0013!!Q\u001d\u0011\u00051\u0013H!B:\u0001\u0005\u0004y%aA!2aA\u0011A*\u001e\u0003\u0006m\u0002\u0011\ra\u0014\u0002\u0004\u0003F\n\u0004C\u0001'y\t\u0015I\bA1\u0001P\u0005\r\t\u0015G\r\t\u0003\u0019n$Q\u0001 \u0001C\u0002=\u00131!Q\u00194!\tae\u0010B\u0003\u0000\u0001\t\u0007qJA\u0002BcQ\u00022\u0001TA\u0002\t\u0019\t)\u0001\u0001b\u0001\u001f\n\u0019\u0011)M\u001b\u0011\u00071\u000bI\u0001\u0002\u0004\u0002\f\u0001\u0011\ra\u0014\u0002\u0004\u0003F2\u0004c\u0001'\u0002\u0010\u00111\u0011\u0011\u0003\u0001C\u0002=\u00131!Q\u00198!\ra\u0015Q\u0003\u0003\u0007\u0003/\u0001!\u0019A(\u0003\u0007\u0005\u000b\u0004\bE\u0002M\u00037!a!!\b\u0001\u0005\u0004y%aA!2sA\u0019A*!\t\u0005\r\u0005\r\u0002A1\u0001P\u0005\r\t%\u0007\r\t\u0004\u0019\u0006\u001dBABA\u0015\u0001\t\u0007qJA\u0002BeE\u00022\u0001TA\u0017\t\u0019\ty\u0003\u0001b\u0001\u001f\n\tq*\u0001\u0004{SB\u0004XM]\u000b\u0003\u0003k\u0001\u0012%UA\u001c\u0017fcvLY3iW:\fHo\u001e>~\u0003\u0003\t9!!\u0004\u0002\u0014\u0005e\u0011qDA\u0013\u0003WI1!!\u000fS\u0005)1UO\\2uS>t''M\u0001\bu&\u0004\b/\u001a:!\u00035)\u0017mZ3s\u0007>l\u0007\u000f\\3uKV\u0011\u0011\u0011\t\t\u0004#\u0006\r\u0013bAA#%\n9!i\\8mK\u0006t\u0017AD3bO\u0016\u00148i\\7qY\u0016$X\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\r\u00055\u0013\u0011KA*!\u0005\ny\u0005A&Z9~\u0013W\r[6ocR<(0`A\u0001\u0003\u000f\ti!a\u0005\u0002\u001a\u0005}\u0011QEA\u0016\u001b\u0005A\u0004bBA\u0019\u000b\u0001\u0007\u0011Q\u0007\u0005\b\u0003{)\u0001\u0019AA!\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0003\u00033\u00022\u0001SA.\u0013\r\tiF\u000f\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!B:iCB,W#A$\u0002\rMD\u0017\r]3!\u0003\ryW\u000f^\u000b\u0003\u0003S\u0002R\u0001SA6\u0003WI1!!\u001c;\u0005\u0019yU\u000f\u001e7fi\u0006\u0019\u0011N\u001c\u0019\u0016\u0005\u0005M\u0004\u0003\u0002%\u0002v-K1!a\u001e;\u0005\u0015Ie\u000e\\3u\u0003\u0011Ig\u000e\r\u0011\u0002\u0007%t\u0017'\u0006\u0002\u0002\u0000A!\u0001*!\u001eZ\u0003\u0011Ig.\r\u0011\u0002\u0007%t''\u0006\u0002\u0002\bB!\u0001*!\u001e]\u0003\u0011IgN\r\u0011\u0002\u0007%t7'\u0006\u0002\u0002\u0010B!\u0001*!\u001e`\u0003\u0011Ign\r\u0011\u0002\u0007%tG'\u0006\u0002\u0002\u0018B!\u0001*!\u001ec\u0003\u0011Ig\u000e\u000e\u0011\u0002\u0007%tW'\u0006\u0002\u0002 B!\u0001*!\u001ef\u0003\u0011Ig.\u000e\u0011\u0002\u0007%tg'\u0006\u0002\u0002(B!\u0001*!\u001ei\u0003\u0011IgN\u000e\u0011\u0002\u0007%tw'\u0006\u0002\u00020B!\u0001*!\u001el\u0003\u0011Ign\u000e\u0011\u0002\u0007%t\u0007(\u0006\u0002\u00028B!\u0001*!\u001eo\u0003\u0011Ig\u000e\u000f\u0011\u0002\u0007%t\u0017(\u0006\u0002\u0002@B!\u0001*!\u001er\u0003\u0011Ig.\u000f\u0011\u0002\t%t\u0017\u0007M\u000b\u0003\u0003\u000f\u0004B\u0001SA;i\u0006)\u0011N\\\u00191A\u0005!\u0011N\\\u00192+\t\ty\r\u0005\u0003I\u0003k:\u0018!B5ocE\u0002\u0013\u0001B5ocI*\"!a6\u0011\t!\u000b)H_\u0001\u0006S:\f$\u0007I\u0001\u0005S:\f4'\u0006\u0002\u0002`B!\u0001*!\u001e~\u0003\u0015Ig.M\u001a!\u0003\u0011Ig.\r\u001b\u0016\u0005\u0005\u001d\b#\u0002%\u0002v\u0005\u0005\u0011!B5ocQ\u0002\u0013\u0001B5ocU*\"!a<\u0011\u000b!\u000b)(a\u0002\u0002\u000b%t\u0017'\u000e\u0011\u0002\t%t\u0017GN\u000b\u0003\u0003o\u0004R\u0001SA;\u0003\u001b\tQ!\u001b82m\u0001\nA!\u001b82oU\u0011\u0011q \t\u0006\u0011\u0006U\u00141C\u0001\u0006S:\ft\u0007I\u0001\u0005S:\f\u0004(\u0006\u0002\u0003\bA)\u0001*!\u001e\u0002\u001a\u0005)\u0011N\\\u00199A\u0005!\u0011N\\\u0019:+\t\u0011y\u0001E\u0003I\u0003k\ny\"A\u0003j]FJ\u0004%\u0001\u0003j]J\u0002TC\u0001B\f!\u0015A\u0015QOA\u0013\u0003\u0015IgN\r\u0019!)\u0011\tiE!\b\t\u000f\u0005EB\u00071\u0001\u00026\u0005Y1M]3bi\u0016dunZ5d)\u0011\u0011\u0019C!\u000b\u0011\u0007\t\u0013)#C\u0002\u0003(\r\u0013qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\b\u0005W)\u0004\u0019AA-\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003!!xn\u0015;sS:<GC\u0001B\u0019!\u0011\u0011\u0019D!\u0010\u000e\u0005\tU\"\u0002\u0002B\u001c\u0005s\tA\u0001\\1oO*\u0011!1H\u0001\u0005U\u00064\u0018-\u0003\u0003\u0003@\tU\"AB*ue&tw\r")
public class ZipLatestWith21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O>
extends GraphStage<FanInShape21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O>> {
    private final Function21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O> shape;
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

    public Function21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith21");
    }

    @Override
    public FanInShape21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith21$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith21$$anon$$contextPropagation;
            private final $anon$39$ZipLatestInlet<A1> inlet0;
            private final $anon$39$ZipLatestInlet<A2> inlet1;
            private final $anon$39$ZipLatestInlet<A3> inlet2;
            private final $anon$39$ZipLatestInlet<A4> inlet3;
            private final $anon$39$ZipLatestInlet<A5> inlet4;
            private final $anon$39$ZipLatestInlet<A6> inlet5;
            private final $anon$39$ZipLatestInlet<A7> inlet6;
            private final $anon$39$ZipLatestInlet<A8> inlet7;
            private final $anon$39$ZipLatestInlet<A9> inlet8;
            private final $anon$39$ZipLatestInlet<A10> inlet9;
            private final $anon$39$ZipLatestInlet<A11> inlet10;
            private final $anon$39$ZipLatestInlet<A12> inlet11;
            private final $anon$39$ZipLatestInlet<A13> inlet12;
            private final $anon$39$ZipLatestInlet<A14> inlet13;
            private final $anon$39$ZipLatestInlet<A15> inlet14;
            private final $anon$39$ZipLatestInlet<A16> inlet15;
            private final $anon$39$ZipLatestInlet<A17> inlet16;
            private final $anon$39$ZipLatestInlet<A18> inlet17;
            private final $anon$39$ZipLatestInlet<A19> inlet18;
            private final $anon$39$ZipLatestInlet<A20> inlet19;
            private final $anon$39$ZipLatestInlet<A21> inlet20;
            private boolean akka$stream$scaladsl$ZipLatestWith21$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith21$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith21 $outer;

            public int akka$stream$scaladsl$ZipLatestWith21$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith21$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith21$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith21$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith21$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith21$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith21$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith21$$anon$$contextPropagation;
            }

            private $anon$39$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$39$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$39$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$39$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$39$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$39$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$39$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$39$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            private $anon$39$ZipLatestInlet<A9> inlet8() {
                return this.inlet8;
            }

            private $anon$39$ZipLatestInlet<A10> inlet9() {
                return this.inlet9;
            }

            private $anon$39$ZipLatestInlet<A11> inlet10() {
                return this.inlet10;
            }

            private $anon$39$ZipLatestInlet<A12> inlet11() {
                return this.inlet11;
            }

            private $anon$39$ZipLatestInlet<A13> inlet12() {
                return this.inlet12;
            }

            private $anon$39$ZipLatestInlet<A14> inlet13() {
                return this.inlet13;
            }

            private $anon$39$ZipLatestInlet<A15> inlet14() {
                return this.inlet14;
            }

            private $anon$39$ZipLatestInlet<A16> inlet15() {
                return this.inlet15;
            }

            private $anon$39$ZipLatestInlet<A17> inlet16() {
                return this.inlet16;
            }

            private $anon$39$ZipLatestInlet<A18> inlet17() {
                return this.inlet17;
            }

            private $anon$39$ZipLatestInlet<A19> inlet18() {
                return this.inlet18;
            }

            private $anon$39$ZipLatestInlet<A20> inlet19() {
                return this.inlet19;
            }

            private $anon$39$ZipLatestInlet<A21> inlet20() {
                return this.inlet20;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith21$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith21$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith21$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith21$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith21$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith21$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith21$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith21$$anon$$staleTupleValues = x$1;
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
            }

            public boolean akka$stream$scaladsl$ZipLatestWith21$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue() && this.inlet8().hasValue() && this.inlet9().hasValue() && this.inlet10().hasValue() && this.inlet11().hasValue() && this.inlet12().hasValue() && this.inlet13().hasValue() && this.inlet14().hasValue() && this.inlet15().hasValue() && this.inlet16().hasValue() && this.inlet17().hasValue() && this.inlet18().hasValue() && this.inlet19().hasValue() && this.inlet20().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith21$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith21$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value(), this.inlet8().value(), this.inlet9().value(), this.inlet10().value(), this.inlet11().value(), this.inlet12().value(), this.inlet13().value(), this.inlet14().value(), this.inlet15().value(), this.inlet16().value(), this.inlet17().value(), this.inlet18().value(), this.inlet19().value(), this.inlet20().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith21$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith21$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith21$$anon$$tryPullAllIfNeeded() {
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
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith21 akka$stream$scaladsl$ZipLatestWith21$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith21$$anon$$upstreamsToWait = 21;
                this.akka$stream$scaladsl$ZipLatestWith21$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$39$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$39$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$39$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$39$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$39$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$39$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$39$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$39$ZipLatestInlet<A8>(this, $outer.in7());
                this.inlet8 = new $anon$39$ZipLatestInlet<A9>(this, $outer.in8());
                this.inlet9 = new $anon$39$ZipLatestInlet<A10>(this, $outer.in9());
                this.inlet10 = new $anon$39$ZipLatestInlet<A11>(this, $outer.in10());
                this.inlet11 = new $anon$39$ZipLatestInlet<A12>(this, $outer.in11());
                this.inlet12 = new $anon$39$ZipLatestInlet<A13>(this, $outer.in12());
                this.inlet13 = new $anon$39$ZipLatestInlet<A14>(this, $outer.in13());
                this.inlet14 = new $anon$39$ZipLatestInlet<A15>(this, $outer.in14());
                this.inlet15 = new $anon$39$ZipLatestInlet<A16>(this, $outer.in15());
                this.inlet16 = new $anon$39$ZipLatestInlet<A17>(this, $outer.in16());
                this.inlet17 = new $anon$39$ZipLatestInlet<A18>(this, $outer.in17());
                this.inlet18 = new $anon$39$ZipLatestInlet<A19>(this, $outer.in18());
                this.inlet19 = new $anon$39$ZipLatestInlet<A20>(this, $outer.in19());
                this.inlet20 = new $anon$39$ZipLatestInlet<A21>(this, $outer.in20());
                this.akka$stream$scaladsl$ZipLatestWith21$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith21$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$39 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith21$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith21$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith21$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith21$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith21$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith21$$anon$$tryPullAllIfNeeded();
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
            }
        };
    }

    public String toString() {
        return "ZipLatestWith21";
    }

    public ZipLatestWith21(Function21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape21("ZipLatestWith21");
        this.in0 = ((FanInShape21)this.shape()).in0();
        this.in1 = ((FanInShape21)this.shape()).in1();
        this.in2 = ((FanInShape21)this.shape()).in2();
        this.in3 = ((FanInShape21)this.shape()).in3();
        this.in4 = ((FanInShape21)this.shape()).in4();
        this.in5 = ((FanInShape21)this.shape()).in5();
        this.in6 = ((FanInShape21)this.shape()).in6();
        this.in7 = ((FanInShape21)this.shape()).in7();
        this.in8 = ((FanInShape21)this.shape()).in8();
        this.in9 = ((FanInShape21)this.shape()).in9();
        this.in10 = ((FanInShape21)this.shape()).in10();
        this.in11 = ((FanInShape21)this.shape()).in11();
        this.in12 = ((FanInShape21)this.shape()).in12();
        this.in13 = ((FanInShape21)this.shape()).in13();
        this.in14 = ((FanInShape21)this.shape()).in14();
        this.in15 = ((FanInShape21)this.shape()).in15();
        this.in16 = ((FanInShape21)this.shape()).in16();
        this.in17 = ((FanInShape21)this.shape()).in17();
        this.in18 = ((FanInShape21)this.shape()).in18();
        this.in19 = ((FanInShape21)this.shape()).in19();
        this.in20 = ((FanInShape21)this.shape()).in20();
    }

    public ZipLatestWith21(Function21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O> zipper) {
        this(zipper, true);
    }
}

