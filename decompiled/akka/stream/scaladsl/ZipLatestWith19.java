/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape19;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith19$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function19;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tua\u0001B\u001a5\u0001mB!\"!\b\u0001\u0005\u000b\u0007I\u0011AA\u0010\u0011)\t9\u0003\u0001B\u0001B\u0003%\u0011\u0011\u0005\u0005\u000b\u0003S\u0001!Q1A\u0005\u0002\u0005-\u0002BCA\u001a\u0001\t\u0005\t\u0015!\u0003\u0002.!9\u0011Q\u0007\u0001\u0005\u0002\u0005]\u0002bBA!\u0001\u0011\u0005\u00131\t\u0005\n\u0003\u0017\u0002!\u0019!C!\u0003\u001bBq!a\u0014\u0001A\u0003%1\tC\u0004\u0002R\u0001!\t!a\u0015\t\u0013\u0005m\u0003A1A\u0005\u0002\u0005u\u0003\u0002CA3\u0001\u0001\u0006I!a\u0018\t\u0013\u0005\u001d\u0004A1A\u0005\u0002\u0005%\u0004\u0002CA7\u0001\u0001\u0006I!a\u001b\t\u0013\u0005=\u0004A1A\u0005\u0002\u0005E\u0004\u0002CA;\u0001\u0001\u0006I!a\u001d\t\u0013\u0005]\u0004A1A\u0005\u0002\u0005e\u0004\u0002CA?\u0001\u0001\u0006I!a\u001f\t\u0013\u0005}\u0004A1A\u0005\u0002\u0005\u0005\u0005\u0002CAC\u0001\u0001\u0006I!a!\t\u0013\u0005\u001d\u0005A1A\u0005\u0002\u0005%\u0005\u0002CAG\u0001\u0001\u0006I!a#\t\u0013\u0005=\u0005A1A\u0005\u0002\u0005E\u0005\u0002CAK\u0001\u0001\u0006I!a%\t\u0013\u0005]\u0005A1A\u0005\u0002\u0005e\u0005\u0002CAO\u0001\u0001\u0006I!a'\t\u0013\u0005}\u0005A1A\u0005\u0002\u0005\u0005\u0006\u0002CAS\u0001\u0001\u0006I!a)\t\u0013\u0005\u001d\u0006A1A\u0005\u0002\u0005%\u0006\u0002CAW\u0001\u0001\u0006I!a+\t\u0013\u0005=\u0006A1A\u0005\u0002\u0005E\u0006\u0002CA[\u0001\u0001\u0006I!a-\t\u0013\u0005]\u0006A1A\u0005\u0002\u0005e\u0006\u0002CA_\u0001\u0001\u0006I!a/\t\u0013\u0005}\u0006A1A\u0005\u0002\u0005\u0005\u0007\u0002CAc\u0001\u0001\u0006I!a1\t\u0013\u0005\u001d\u0007A1A\u0005\u0002\u0005%\u0007\u0002CAg\u0001\u0001\u0006I!a3\t\u0013\u0005=\u0007A1A\u0005\u0002\u0005E\u0007\u0002CAk\u0001\u0001\u0006I!a5\t\u0013\u0005]\u0007A1A\u0005\u0002\u0005e\u0007\u0002CAo\u0001\u0001\u0006I!a7\t\u0013\u0005}\u0007A1A\u0005\u0002\u0005\u0005\b\u0002CAs\u0001\u0001\u0006I!a9\t\u0013\u0005\u001d\bA1A\u0005\u0002\u0005%\b\u0002CAw\u0001\u0001\u0006I!a;\t\u0013\u0005=\bA1A\u0005\u0002\u0005E\b\u0002CA{\u0001\u0001\u0006I!a=\t\u000f\u0005U\u0002\u0001\"\u0001\u0002x\"9\u00111 \u0001\u0005B\u0005u\bb\u0002B\u0005\u0001\u0011\u0005#1\u0002\u0002\u00105&\u0004H*\u0019;fgR<\u0016\u000e\u001e52s)\u0011QGN\u0001\tg\u000e\fG.\u00193tY*\u0011q\u0007O\u0001\u0007gR\u0014X-Y7\u000b\u0003e\nA!Y6lC\u000e\u0001QC\u0007\u001fJ-fcvLY3iW:\fHo\u001e>~\u0003\u0003\t9!!\u0004\u0002\u0014\u0005e1C\u0001\u0001>!\rq\u0014iQ\u0007\u0002\u007f)\u0011\u0001IN\u0001\u0006gR\fw-Z\u0005\u0003\u0005~\u0012!b\u0012:ba\"\u001cF/Y4f!i!UiR+Y7z\u000bGm\u001a6naN4\u0018\u0010`@\u0002\u0006\u0005-\u0011\u0011CA\f\u001b\u00051\u0014B\u0001$7\u000511\u0015M\\%o'\"\f\u0007/Z\u0019:!\tA\u0015\n\u0004\u0001\u0005\u000b)\u0003!\u0019A&\u0003\u0005\u0005\u000b\u0014C\u0001'S!\ti\u0005+D\u0001O\u0015\u0005y\u0015!B:dC2\f\u0017BA)O\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!T*\n\u0005Qs%aA!osB\u0011\u0001J\u0016\u0003\u0006/\u0002\u0011\ra\u0013\u0002\u0003\u0003J\u0002\"\u0001S-\u0005\u000bi\u0003!\u0019A&\u0003\u0005\u0005\u001b\u0004C\u0001%]\t\u0015i\u0006A1\u0001L\u0005\t\tE\u0007\u0005\u0002I?\u0012)\u0001\r\u0001b\u0001\u0017\n\u0011\u0011)\u000e\t\u0003\u0011\n$Qa\u0019\u0001C\u0002-\u0013!!\u0011\u001c\u0011\u0005!+G!\u00024\u0001\u0005\u0004Y%AA!8!\tA\u0005\u000eB\u0003j\u0001\t\u00071J\u0001\u0002BqA\u0011\u0001j\u001b\u0003\u0006Y\u0002\u0011\ra\u0013\u0002\u0003\u0003f\u0002\"\u0001\u00138\u0005\u000b=\u0004!\u0019A&\u0003\u0007\u0005\u000b\u0004\u0007\u0005\u0002Ic\u0012)!\u000f\u0001b\u0001\u0017\n\u0019\u0011)M\u0019\u0011\u0005!#H!B;\u0001\u0005\u0004Y%aA!2eA\u0011\u0001j\u001e\u0003\u0006q\u0002\u0011\ra\u0013\u0002\u0004\u0003F\u001a\u0004C\u0001%{\t\u0015Y\bA1\u0001L\u0005\r\t\u0015\u0007\u000e\t\u0003\u0011v$QA \u0001C\u0002-\u00131!Q\u00196!\rA\u0015\u0011\u0001\u0003\u0007\u0003\u0007\u0001!\u0019A&\u0003\u0007\u0005\u000bd\u0007E\u0002I\u0003\u000f!a!!\u0003\u0001\u0005\u0004Y%aA!2oA\u0019\u0001*!\u0004\u0005\r\u0005=\u0001A1\u0001L\u0005\r\t\u0015\u0007\u000f\t\u0004\u0011\u0006MAABA\u000b\u0001\t\u00071JA\u0002Bce\u00022\u0001SA\r\t\u0019\tY\u0002\u0001b\u0001\u0017\n\tq*\u0001\u0004{SB\u0004XM]\u000b\u0003\u0003C\u00012$TA\u0012\u000fVC6LX1eO*l\u0007o\u001d<zy~\f)!a\u0003\u0002\u0012\u0005]\u0011bAA\u0013\u001d\nQa)\u001e8di&|g.M\u001d\u0002\u000fiL\u0007\u000f]3sA\u0005iQ-Y4fe\u000e{W\u000e\u001d7fi\u0016,\"!!\f\u0011\u00075\u000by#C\u0002\u000229\u0013qAQ8pY\u0016\fg.\u0001\bfC\u001e,'oQ8na2,G/\u001a\u0011\u0002\rqJg.\u001b;?)\u0019\tI$!\u0010\u0002@AY\u00121\b\u0001H+b[f,\u00193hU6\u00048O^=}\u007f\u0006\u0015\u00111BA\t\u0003/i\u0011\u0001\u000e\u0005\b\u0003;)\u0001\u0019AA\u0011\u0011\u001d\tI#\u0002a\u0001\u0003[\t\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\t\t)\u0005E\u0002E\u0003\u000fJ1!!\u00137\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0002\u0007\u000611\u000f[1qK\u0002\n1a\\;u+\t\t)\u0006E\u0003E\u0003/\n9\"C\u0002\u0002ZY\u0012aaT;uY\u0016$\u0018aA5oaU\u0011\u0011q\f\t\u0005\t\u0006\u0005t)C\u0002\u0002dY\u0012Q!\u00138mKR\fA!\u001b81A\u0005\u0019\u0011N\\\u0019\u0016\u0005\u0005-\u0004\u0003\u0002#\u0002bU\u000bA!\u001b82A\u0005\u0019\u0011N\u001c\u001a\u0016\u0005\u0005M\u0004\u0003\u0002#\u0002ba\u000bA!\u001b83A\u0005\u0019\u0011N\\\u001a\u0016\u0005\u0005m\u0004\u0003\u0002#\u0002bm\u000bA!\u001b84A\u0005\u0019\u0011N\u001c\u001b\u0016\u0005\u0005\r\u0005\u0003\u0002#\u0002by\u000bA!\u001b85A\u0005\u0019\u0011N\\\u001b\u0016\u0005\u0005-\u0005\u0003\u0002#\u0002b\u0005\fA!\u001b86A\u0005\u0019\u0011N\u001c\u001c\u0016\u0005\u0005M\u0005\u0003\u0002#\u0002b\u0011\fA!\u001b87A\u0005\u0019\u0011N\\\u001c\u0016\u0005\u0005m\u0005\u0003\u0002#\u0002b\u001d\fA!\u001b88A\u0005\u0019\u0011N\u001c\u001d\u0016\u0005\u0005\r\u0006\u0003\u0002#\u0002b)\fA!\u001b89A\u0005\u0019\u0011N\\\u001d\u0016\u0005\u0005-\u0006\u0003\u0002#\u0002b5\fA!\u001b8:A\u0005!\u0011N\\\u00191+\t\t\u0019\f\u0005\u0003E\u0003C\u0002\u0018!B5ocA\u0002\u0013\u0001B5ocE*\"!a/\u0011\t\u0011\u000b\tg]\u0001\u0006S:\f\u0014\u0007I\u0001\u0005S:\f$'\u0006\u0002\u0002DB!A)!\u0019w\u0003\u0015Ig.\r\u001a!\u0003\u0011Ig.M\u001a\u0016\u0005\u0005-\u0007\u0003\u0002#\u0002be\fQ!\u001b82g\u0001\nA!\u001b82iU\u0011\u00111\u001b\t\u0005\t\u0006\u0005D0A\u0003j]F\"\u0004%\u0001\u0003j]F*TCAAn!\u0011!\u0015\u0011M@\u0002\u000b%t\u0017'\u000e\u0011\u0002\t%t\u0017GN\u000b\u0003\u0003G\u0004R\u0001RA1\u0003\u000b\tQ!\u001b82m\u0001\nA!\u001b82oU\u0011\u00111\u001e\t\u0006\t\u0006\u0005\u00141B\u0001\u0006S:\ft\u0007I\u0001\u0005S:\f\u0004(\u0006\u0002\u0002tB)A)!\u0019\u0002\u0012\u0005)\u0011N\\\u00199AQ!\u0011\u0011HA}\u0011\u001d\ti\u0002\ra\u0001\u0003C\t1b\u0019:fCR,Gj\\4jGR!\u0011q B\u0003!\rq$\u0011A\u0005\u0004\u0005\u0007y$aD$sCBD7\u000b^1hK2{w-[2\t\u000f\t\u001d\u0011\u00071\u0001\u0002F\u0005\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fg\u0006AAo\\*ue&tw\r\u0006\u0002\u0003\u000eA!!q\u0002B\r\u001b\t\u0011\tB\u0003\u0003\u0003\u0014\tU\u0011\u0001\u00027b]\u001eT!Aa\u0006\u0002\t)\fg/Y\u0005\u0005\u00057\u0011\tB\u0001\u0004TiJLgn\u001a")
public class ZipLatestWith19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O>
extends GraphStage<FanInShape19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O>> {
    private final Function19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O> shape;
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

    public Function19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith19");
    }

    @Override
    public FanInShape19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith19$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith19$$anon$$contextPropagation;
            private final $anon$35$ZipLatestInlet<A1> inlet0;
            private final $anon$35$ZipLatestInlet<A2> inlet1;
            private final $anon$35$ZipLatestInlet<A3> inlet2;
            private final $anon$35$ZipLatestInlet<A4> inlet3;
            private final $anon$35$ZipLatestInlet<A5> inlet4;
            private final $anon$35$ZipLatestInlet<A6> inlet5;
            private final $anon$35$ZipLatestInlet<A7> inlet6;
            private final $anon$35$ZipLatestInlet<A8> inlet7;
            private final $anon$35$ZipLatestInlet<A9> inlet8;
            private final $anon$35$ZipLatestInlet<A10> inlet9;
            private final $anon$35$ZipLatestInlet<A11> inlet10;
            private final $anon$35$ZipLatestInlet<A12> inlet11;
            private final $anon$35$ZipLatestInlet<A13> inlet12;
            private final $anon$35$ZipLatestInlet<A14> inlet13;
            private final $anon$35$ZipLatestInlet<A15> inlet14;
            private final $anon$35$ZipLatestInlet<A16> inlet15;
            private final $anon$35$ZipLatestInlet<A17> inlet16;
            private final $anon$35$ZipLatestInlet<A18> inlet17;
            private final $anon$35$ZipLatestInlet<A19> inlet18;
            private boolean akka$stream$scaladsl$ZipLatestWith19$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith19$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith19 $outer;

            public int akka$stream$scaladsl$ZipLatestWith19$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith19$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith19$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith19$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith19$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith19$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith19$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith19$$anon$$contextPropagation;
            }

            private $anon$35$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$35$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$35$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$35$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$35$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$35$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$35$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$35$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            private $anon$35$ZipLatestInlet<A9> inlet8() {
                return this.inlet8;
            }

            private $anon$35$ZipLatestInlet<A10> inlet9() {
                return this.inlet9;
            }

            private $anon$35$ZipLatestInlet<A11> inlet10() {
                return this.inlet10;
            }

            private $anon$35$ZipLatestInlet<A12> inlet11() {
                return this.inlet11;
            }

            private $anon$35$ZipLatestInlet<A13> inlet12() {
                return this.inlet12;
            }

            private $anon$35$ZipLatestInlet<A14> inlet13() {
                return this.inlet13;
            }

            private $anon$35$ZipLatestInlet<A15> inlet14() {
                return this.inlet14;
            }

            private $anon$35$ZipLatestInlet<A16> inlet15() {
                return this.inlet15;
            }

            private $anon$35$ZipLatestInlet<A17> inlet16() {
                return this.inlet16;
            }

            private $anon$35$ZipLatestInlet<A18> inlet17() {
                return this.inlet17;
            }

            private $anon$35$ZipLatestInlet<A19> inlet18() {
                return this.inlet18;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith19$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith19$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith19$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith19$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith19$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith19$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith19$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith19$$anon$$staleTupleValues = x$1;
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
            }

            public boolean akka$stream$scaladsl$ZipLatestWith19$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue() && this.inlet8().hasValue() && this.inlet9().hasValue() && this.inlet10().hasValue() && this.inlet11().hasValue() && this.inlet12().hasValue() && this.inlet13().hasValue() && this.inlet14().hasValue() && this.inlet15().hasValue() && this.inlet16().hasValue() && this.inlet17().hasValue() && this.inlet18().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith19$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith19$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value(), this.inlet8().value(), this.inlet9().value(), this.inlet10().value(), this.inlet11().value(), this.inlet12().value(), this.inlet13().value(), this.inlet14().value(), this.inlet15().value(), this.inlet16().value(), this.inlet17().value(), this.inlet18().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith19$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith19$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith19$$anon$$tryPullAllIfNeeded() {
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
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith19 akka$stream$scaladsl$ZipLatestWith19$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith19$$anon$$upstreamsToWait = 19;
                this.akka$stream$scaladsl$ZipLatestWith19$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$35$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$35$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$35$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$35$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$35$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$35$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$35$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$35$ZipLatestInlet<A8>(this, $outer.in7());
                this.inlet8 = new $anon$35$ZipLatestInlet<A9>(this, $outer.in8());
                this.inlet9 = new $anon$35$ZipLatestInlet<A10>(this, $outer.in9());
                this.inlet10 = new $anon$35$ZipLatestInlet<A11>(this, $outer.in10());
                this.inlet11 = new $anon$35$ZipLatestInlet<A12>(this, $outer.in11());
                this.inlet12 = new $anon$35$ZipLatestInlet<A13>(this, $outer.in12());
                this.inlet13 = new $anon$35$ZipLatestInlet<A14>(this, $outer.in13());
                this.inlet14 = new $anon$35$ZipLatestInlet<A15>(this, $outer.in14());
                this.inlet15 = new $anon$35$ZipLatestInlet<A16>(this, $outer.in15());
                this.inlet16 = new $anon$35$ZipLatestInlet<A17>(this, $outer.in16());
                this.inlet17 = new $anon$35$ZipLatestInlet<A18>(this, $outer.in17());
                this.inlet18 = new $anon$35$ZipLatestInlet<A19>(this, $outer.in18());
                this.akka$stream$scaladsl$ZipLatestWith19$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith19$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$35 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith19$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith19$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith19$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith19$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith19$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith19$$anon$$tryPullAllIfNeeded();
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
            }
        };
    }

    public String toString() {
        return "ZipLatestWith19";
    }

    public ZipLatestWith19(Function19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape19("ZipLatestWith19");
        this.in0 = ((FanInShape19)this.shape()).in0();
        this.in1 = ((FanInShape19)this.shape()).in1();
        this.in2 = ((FanInShape19)this.shape()).in2();
        this.in3 = ((FanInShape19)this.shape()).in3();
        this.in4 = ((FanInShape19)this.shape()).in4();
        this.in5 = ((FanInShape19)this.shape()).in5();
        this.in6 = ((FanInShape19)this.shape()).in6();
        this.in7 = ((FanInShape19)this.shape()).in7();
        this.in8 = ((FanInShape19)this.shape()).in8();
        this.in9 = ((FanInShape19)this.shape()).in9();
        this.in10 = ((FanInShape19)this.shape()).in10();
        this.in11 = ((FanInShape19)this.shape()).in11();
        this.in12 = ((FanInShape19)this.shape()).in12();
        this.in13 = ((FanInShape19)this.shape()).in13();
        this.in14 = ((FanInShape19)this.shape()).in14();
        this.in15 = ((FanInShape19)this.shape()).in15();
        this.in16 = ((FanInShape19)this.shape()).in16();
        this.in17 = ((FanInShape19)this.shape()).in17();
        this.in18 = ((FanInShape19)this.shape()).in18();
    }

    public ZipLatestWith19(Function19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O> zipper) {
        this(zipper, true);
    }
}

