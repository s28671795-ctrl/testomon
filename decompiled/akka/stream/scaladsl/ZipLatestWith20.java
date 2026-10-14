/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape20;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith20$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function20;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t=b\u0001B\u001b7\u0001uB!\"a\n\u0001\u0005\u000b\u0007I\u0011AA\u0015\u0011)\t\t\u0004\u0001B\u0001B\u0003%\u00111\u0006\u0005\u000b\u0003g\u0001!Q1A\u0005\u0002\u0005U\u0002BCA\u001f\u0001\t\u0005\t\u0015!\u0003\u00028!9\u0011q\b\u0001\u0005\u0002\u0005\u0005\u0003bBA&\u0001\u0011\u0005\u0013Q\n\u0005\n\u0003+\u0002!\u0019!C!\u0003/Bq!!\u0017\u0001A\u0003%Q\tC\u0004\u0002\\\u0001!\t!!\u0018\t\u0013\u0005\u0015\u0004A1A\u0005\u0002\u0005\u001d\u0004\u0002CA8\u0001\u0001\u0006I!!\u001b\t\u0013\u0005E\u0004A1A\u0005\u0002\u0005M\u0004\u0002CA<\u0001\u0001\u0006I!!\u001e\t\u0013\u0005e\u0004A1A\u0005\u0002\u0005m\u0004\u0002CA@\u0001\u0001\u0006I!! \t\u0013\u0005\u0005\u0005A1A\u0005\u0002\u0005\r\u0005\u0002CAD\u0001\u0001\u0006I!!\"\t\u0013\u0005%\u0005A1A\u0005\u0002\u0005-\u0005\u0002CAH\u0001\u0001\u0006I!!$\t\u0013\u0005E\u0005A1A\u0005\u0002\u0005M\u0005\u0002CAL\u0001\u0001\u0006I!!&\t\u0013\u0005e\u0005A1A\u0005\u0002\u0005m\u0005\u0002CAP\u0001\u0001\u0006I!!(\t\u0013\u0005\u0005\u0006A1A\u0005\u0002\u0005\r\u0006\u0002CAT\u0001\u0001\u0006I!!*\t\u0013\u0005%\u0006A1A\u0005\u0002\u0005-\u0006\u0002CAX\u0001\u0001\u0006I!!,\t\u0013\u0005E\u0006A1A\u0005\u0002\u0005M\u0006\u0002CA\\\u0001\u0001\u0006I!!.\t\u0013\u0005e\u0006A1A\u0005\u0002\u0005m\u0006\u0002CA`\u0001\u0001\u0006I!!0\t\u0013\u0005\u0005\u0007A1A\u0005\u0002\u0005\r\u0007\u0002CAd\u0001\u0001\u0006I!!2\t\u0013\u0005%\u0007A1A\u0005\u0002\u0005-\u0007\u0002CAh\u0001\u0001\u0006I!!4\t\u0013\u0005E\u0007A1A\u0005\u0002\u0005M\u0007\u0002CAl\u0001\u0001\u0006I!!6\t\u0013\u0005e\u0007A1A\u0005\u0002\u0005m\u0007\u0002CAp\u0001\u0001\u0006I!!8\t\u0013\u0005\u0005\bA1A\u0005\u0002\u0005\r\b\u0002CAt\u0001\u0001\u0006I!!:\t\u0013\u0005%\bA1A\u0005\u0002\u0005-\b\u0002CAx\u0001\u0001\u0006I!!<\t\u0013\u0005E\bA1A\u0005\u0002\u0005M\b\u0002CA|\u0001\u0001\u0006I!!>\t\u0013\u0005e\bA1A\u0005\u0002\u0005m\b\u0002CA\u0000\u0001\u0001\u0006I!!@\t\u0013\t\u0005\u0001A1A\u0005\u0002\t\r\u0001\u0002\u0003B\u0004\u0001\u0001\u0006IA!\u0002\t\u000f\u0005}\u0002\u0001\"\u0001\u0003\n!9!Q\u0002\u0001\u0005B\t=\u0001b\u0002B\u000e\u0001\u0011\u0005#Q\u0004\u0002\u00105&\u0004H*\u0019;fgR<\u0016\u000e\u001e53a)\u0011q\u0007O\u0001\tg\u000e\fG.\u00193tY*\u0011\u0011HO\u0001\u0007gR\u0014X-Y7\u000b\u0003m\nA!Y6lC\u000e\u0001Q\u0003\b L1ns\u0016\rZ4k[B\u001ch/\u001f?\u0000\u0003\u000b\tY!!\u0005\u0002\u0018\u0005u\u00111E\n\u0003\u0001}\u00022\u0001Q\"F\u001b\u0005\t%B\u0001\"9\u0003\u0015\u0019H/Y4f\u0013\t!\u0015I\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004RDR$J/jk\u0006m\u00194jY>\u0014X\u000f_>\u007f\u0003\u0007\tI!a\u0004\u0002\u0016\u0005m\u0011\u0011E\u0007\u0002q%\u0011\u0001\n\u000f\u0002\r\r\u0006t\u0017J\\*iCB,'\u0007\r\t\u0003\u0015.c\u0001\u0001B\u0003M\u0001\t\u0007QJ\u0001\u0002BcE\u0011a\n\u0016\t\u0003\u001fJk\u0011\u0001\u0015\u0006\u0002#\u0006)1oY1mC&\u00111\u000b\u0015\u0002\b\u001d>$\b.\u001b8h!\tyU+\u0003\u0002W!\n\u0019\u0011I\\=\u0011\u0005)CF!B-\u0001\u0005\u0004i%AA!3!\tQ5\fB\u0003]\u0001\t\u0007QJ\u0001\u0002BgA\u0011!J\u0018\u0003\u0006?\u0002\u0011\r!\u0014\u0002\u0003\u0003R\u0002\"AS1\u0005\u000b\t\u0004!\u0019A'\u0003\u0005\u0005+\u0004C\u0001&e\t\u0015)\u0007A1\u0001N\u0005\t\te\u0007\u0005\u0002KO\u0012)\u0001\u000e\u0001b\u0001\u001b\n\u0011\u0011i\u000e\t\u0003\u0015*$Qa\u001b\u0001C\u00025\u0013!!\u0011\u001d\u0011\u0005)kG!\u00028\u0001\u0005\u0004i%AA!:!\tQ\u0005\u000fB\u0003r\u0001\t\u0007QJA\u0002BcA\u0002\"AS:\u0005\u000bQ\u0004!\u0019A'\u0003\u0007\u0005\u000b\u0014\u0007\u0005\u0002Km\u0012)q\u000f\u0001b\u0001\u001b\n\u0019\u0011)\r\u001a\u0011\u0005)KH!\u0002>\u0001\u0005\u0004i%aA!2gA\u0011!\n \u0003\u0006{\u0002\u0011\r!\u0014\u0002\u0004\u0003F\"\u0004C\u0001&\u0000\t\u0019\t\t\u0001\u0001b\u0001\u001b\n\u0019\u0011)M\u001b\u0011\u0007)\u000b)\u0001\u0002\u0004\u0002\b\u0001\u0011\r!\u0014\u0002\u0004\u0003F2\u0004c\u0001&\u0002\f\u00111\u0011Q\u0002\u0001C\u00025\u00131!Q\u00198!\rQ\u0015\u0011\u0003\u0003\u0007\u0003'\u0001!\u0019A'\u0003\u0007\u0005\u000b\u0004\bE\u0002K\u0003/!a!!\u0007\u0001\u0005\u0004i%aA!2sA\u0019!*!\b\u0005\r\u0005}\u0001A1\u0001N\u0005\r\t%\u0007\r\t\u0004\u0015\u0006\rBABA\u0013\u0001\t\u0007QJA\u0001P\u0003\u0019Q\u0018\u000e\u001d9feV\u0011\u00111\u0006\t\u001f\u001f\u00065\u0012j\u0016.^A\u000e4\u0017\u000e\\8skb\\h0a\u0001\u0002\n\u0005=\u0011QCA\u000e\u0003CI1!a\fQ\u0005)1UO\\2uS>t'\u0007M\u0001\bu&\u0004\b/\u001a:!\u00035)\u0017mZ3s\u0007>l\u0007\u000f\\3uKV\u0011\u0011q\u0007\t\u0004\u001f\u0006e\u0012bAA\u001e!\n9!i\\8mK\u0006t\u0017AD3bO\u0016\u00148i\\7qY\u0016$X\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\r\u0005\r\u0013qIA%!y\t)\u0005A%X5v\u00037MZ5m_J,\bp\u001f@\u0002\u0004\u0005%\u0011qBA\u000b\u00037\t\t#D\u00017\u0011\u001d\t9#\u0002a\u0001\u0003WAq!a\r\u0006\u0001\u0004\t9$A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\"!a\u0014\u0011\u0007\u0019\u000b\t&C\u0002\u0002Ta\u0012!\"\u0011;ue&\u0014W\u000f^3t\u0003\u0015\u0019\b.\u00199f+\u0005)\u0015AB:iCB,\u0007%A\u0002pkR,\"!a\u0018\u0011\u000b\u0019\u000b\t'!\t\n\u0007\u0005\r\u0004H\u0001\u0004PkRdW\r^\u0001\u0004S:\u0004TCAA5!\u00111\u00151N%\n\u0007\u00055\u0004HA\u0003J]2,G/\u0001\u0003j]B\u0002\u0013aA5ocU\u0011\u0011Q\u000f\t\u0005\r\u0006-t+\u0001\u0003j]F\u0002\u0013aA5oeU\u0011\u0011Q\u0010\t\u0005\r\u0006-$,\u0001\u0003j]J\u0002\u0013aA5ogU\u0011\u0011Q\u0011\t\u0005\r\u0006-T,\u0001\u0003j]N\u0002\u0013aA5oiU\u0011\u0011Q\u0012\t\u0005\r\u0006-\u0004-\u0001\u0003j]R\u0002\u0013aA5okU\u0011\u0011Q\u0013\t\u0005\r\u0006-4-\u0001\u0003j]V\u0002\u0013aA5omU\u0011\u0011Q\u0014\t\u0005\r\u0006-d-\u0001\u0003j]Z\u0002\u0013aA5ooU\u0011\u0011Q\u0015\t\u0005\r\u0006-\u0014.\u0001\u0003j]^\u0002\u0013aA5oqU\u0011\u0011Q\u0016\t\u0005\r\u0006-D.\u0001\u0003j]b\u0002\u0013aA5osU\u0011\u0011Q\u0017\t\u0005\r\u0006-t.\u0001\u0003j]f\u0002\u0013\u0001B5ocA*\"!!0\u0011\t\u0019\u000bYG]\u0001\u0006S:\f\u0004\u0007I\u0001\u0005S:\f\u0014'\u0006\u0002\u0002FB!a)a\u001bv\u0003\u0015Ig.M\u0019!\u0003\u0011Ig.\r\u001a\u0016\u0005\u00055\u0007\u0003\u0002$\u0002la\fQ!\u001b82e\u0001\nA!\u001b82gU\u0011\u0011Q\u001b\t\u0005\r\u0006-40A\u0003j]F\u001a\u0004%\u0001\u0003j]F\"TCAAo!\u00111\u00151\u000e@\u0002\u000b%t\u0017\u0007\u000e\u0011\u0002\t%t\u0017'N\u000b\u0003\u0003K\u0004RARA6\u0003\u0007\tQ!\u001b82k\u0001\nA!\u001b82mU\u0011\u0011Q\u001e\t\u0006\r\u0006-\u0014\u0011B\u0001\u0006S:\fd\u0007I\u0001\u0005S:\ft'\u0006\u0002\u0002vB)a)a\u001b\u0002\u0010\u0005)\u0011N\\\u00198A\u0005!\u0011N\\\u00199+\t\ti\u0010E\u0003G\u0003W\n)\"A\u0003j]FB\u0004%\u0001\u0003j]FJTC\u0001B\u0003!\u00151\u00151NA\u000e\u0003\u0015Ig.M\u001d!)\u0011\t\u0019Ea\u0003\t\u000f\u0005\u001d\"\u00071\u0001\u0002,\u0005Y1M]3bi\u0016dunZ5d)\u0011\u0011\tBa\u0006\u0011\u0007\u0001\u0013\u0019\"C\u0002\u0003\u0016\u0005\u0013qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\b\u00053\u0019\u0004\u0019AA(\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003!!xn\u0015;sS:<GC\u0001B\u0010!\u0011\u0011\tCa\u000b\u000e\u0005\t\r\"\u0002\u0002B\u0013\u0005O\tA\u0001\\1oO*\u0011!\u0011F\u0001\u0005U\u00064\u0018-\u0003\u0003\u0003.\t\r\"AB*ue&tw\r")
public class ZipLatestWith20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O>
extends GraphStage<FanInShape20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O>> {
    private final Function20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O> shape;
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

    public Function20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith20");
    }

    @Override
    public FanInShape20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith20$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith20$$anon$$contextPropagation;
            private final $anon$37$ZipLatestInlet<A1> inlet0;
            private final $anon$37$ZipLatestInlet<A2> inlet1;
            private final $anon$37$ZipLatestInlet<A3> inlet2;
            private final $anon$37$ZipLatestInlet<A4> inlet3;
            private final $anon$37$ZipLatestInlet<A5> inlet4;
            private final $anon$37$ZipLatestInlet<A6> inlet5;
            private final $anon$37$ZipLatestInlet<A7> inlet6;
            private final $anon$37$ZipLatestInlet<A8> inlet7;
            private final $anon$37$ZipLatestInlet<A9> inlet8;
            private final $anon$37$ZipLatestInlet<A10> inlet9;
            private final $anon$37$ZipLatestInlet<A11> inlet10;
            private final $anon$37$ZipLatestInlet<A12> inlet11;
            private final $anon$37$ZipLatestInlet<A13> inlet12;
            private final $anon$37$ZipLatestInlet<A14> inlet13;
            private final $anon$37$ZipLatestInlet<A15> inlet14;
            private final $anon$37$ZipLatestInlet<A16> inlet15;
            private final $anon$37$ZipLatestInlet<A17> inlet16;
            private final $anon$37$ZipLatestInlet<A18> inlet17;
            private final $anon$37$ZipLatestInlet<A19> inlet18;
            private final $anon$37$ZipLatestInlet<A20> inlet19;
            private boolean akka$stream$scaladsl$ZipLatestWith20$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith20$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith20 $outer;

            public int akka$stream$scaladsl$ZipLatestWith20$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith20$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith20$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith20$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith20$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith20$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith20$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith20$$anon$$contextPropagation;
            }

            private $anon$37$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$37$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            private $anon$37$ZipLatestInlet<A3> inlet2() {
                return this.inlet2;
            }

            private $anon$37$ZipLatestInlet<A4> inlet3() {
                return this.inlet3;
            }

            private $anon$37$ZipLatestInlet<A5> inlet4() {
                return this.inlet4;
            }

            private $anon$37$ZipLatestInlet<A6> inlet5() {
                return this.inlet5;
            }

            private $anon$37$ZipLatestInlet<A7> inlet6() {
                return this.inlet6;
            }

            private $anon$37$ZipLatestInlet<A8> inlet7() {
                return this.inlet7;
            }

            private $anon$37$ZipLatestInlet<A9> inlet8() {
                return this.inlet8;
            }

            private $anon$37$ZipLatestInlet<A10> inlet9() {
                return this.inlet9;
            }

            private $anon$37$ZipLatestInlet<A11> inlet10() {
                return this.inlet10;
            }

            private $anon$37$ZipLatestInlet<A12> inlet11() {
                return this.inlet11;
            }

            private $anon$37$ZipLatestInlet<A13> inlet12() {
                return this.inlet12;
            }

            private $anon$37$ZipLatestInlet<A14> inlet13() {
                return this.inlet13;
            }

            private $anon$37$ZipLatestInlet<A15> inlet14() {
                return this.inlet14;
            }

            private $anon$37$ZipLatestInlet<A16> inlet15() {
                return this.inlet15;
            }

            private $anon$37$ZipLatestInlet<A17> inlet16() {
                return this.inlet16;
            }

            private $anon$37$ZipLatestInlet<A18> inlet17() {
                return this.inlet17;
            }

            private $anon$37$ZipLatestInlet<A19> inlet18() {
                return this.inlet18;
            }

            private $anon$37$ZipLatestInlet<A20> inlet19() {
                return this.inlet19;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith20$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith20$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith20$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith20$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith20$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith20$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith20$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith20$$anon$$staleTupleValues = x$1;
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
            }

            public boolean akka$stream$scaladsl$ZipLatestWith20$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue() && this.inlet2().hasValue() && this.inlet3().hasValue() && this.inlet4().hasValue() && this.inlet5().hasValue() && this.inlet6().hasValue() && this.inlet7().hasValue() && this.inlet8().hasValue() && this.inlet9().hasValue() && this.inlet10().hasValue() && this.inlet11().hasValue() && this.inlet12().hasValue() && this.inlet13().hasValue() && this.inlet14().hasValue() && this.inlet15().hasValue() && this.inlet16().hasValue() && this.inlet17().hasValue() && this.inlet18().hasValue() && this.inlet19().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith20$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith20$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value(), this.inlet2().value(), this.inlet3().value(), this.inlet4().value(), this.inlet5().value(), this.inlet6().value(), this.inlet7().value(), this.inlet8().value(), this.inlet9().value(), this.inlet10().value(), this.inlet11().value(), this.inlet12().value(), this.inlet13().value(), this.inlet14().value(), this.inlet15().value(), this.inlet16().value(), this.inlet17().value(), this.inlet18().value(), this.inlet19().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith20$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith20$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith20$$anon$$tryPullAllIfNeeded() {
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
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith20 akka$stream$scaladsl$ZipLatestWith20$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith20$$anon$$upstreamsToWait = 20;
                this.akka$stream$scaladsl$ZipLatestWith20$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$37$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$37$ZipLatestInlet<A2>(this, $outer.in1());
                this.inlet2 = new $anon$37$ZipLatestInlet<A3>(this, $outer.in2());
                this.inlet3 = new $anon$37$ZipLatestInlet<A4>(this, $outer.in3());
                this.inlet4 = new $anon$37$ZipLatestInlet<A5>(this, $outer.in4());
                this.inlet5 = new $anon$37$ZipLatestInlet<A6>(this, $outer.in5());
                this.inlet6 = new $anon$37$ZipLatestInlet<A7>(this, $outer.in6());
                this.inlet7 = new $anon$37$ZipLatestInlet<A8>(this, $outer.in7());
                this.inlet8 = new $anon$37$ZipLatestInlet<A9>(this, $outer.in8());
                this.inlet9 = new $anon$37$ZipLatestInlet<A10>(this, $outer.in9());
                this.inlet10 = new $anon$37$ZipLatestInlet<A11>(this, $outer.in10());
                this.inlet11 = new $anon$37$ZipLatestInlet<A12>(this, $outer.in11());
                this.inlet12 = new $anon$37$ZipLatestInlet<A13>(this, $outer.in12());
                this.inlet13 = new $anon$37$ZipLatestInlet<A14>(this, $outer.in13());
                this.inlet14 = new $anon$37$ZipLatestInlet<A15>(this, $outer.in14());
                this.inlet15 = new $anon$37$ZipLatestInlet<A16>(this, $outer.in15());
                this.inlet16 = new $anon$37$ZipLatestInlet<A17>(this, $outer.in16());
                this.inlet17 = new $anon$37$ZipLatestInlet<A18>(this, $outer.in17());
                this.inlet18 = new $anon$37$ZipLatestInlet<A19>(this, $outer.in18());
                this.inlet19 = new $anon$37$ZipLatestInlet<A20>(this, $outer.in19());
                this.akka$stream$scaladsl$ZipLatestWith20$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith20$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$37 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith20$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith20$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith20$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith20$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith20$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith20$$anon$$tryPullAllIfNeeded();
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
            }
        };
    }

    public String toString() {
        return "ZipLatestWith20";
    }

    public ZipLatestWith20(Function20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape20("ZipLatestWith20");
        this.in0 = ((FanInShape20)this.shape()).in0();
        this.in1 = ((FanInShape20)this.shape()).in1();
        this.in2 = ((FanInShape20)this.shape()).in2();
        this.in3 = ((FanInShape20)this.shape()).in3();
        this.in4 = ((FanInShape20)this.shape()).in4();
        this.in5 = ((FanInShape20)this.shape()).in5();
        this.in6 = ((FanInShape20)this.shape()).in6();
        this.in7 = ((FanInShape20)this.shape()).in7();
        this.in8 = ((FanInShape20)this.shape()).in8();
        this.in9 = ((FanInShape20)this.shape()).in9();
        this.in10 = ((FanInShape20)this.shape()).in10();
        this.in11 = ((FanInShape20)this.shape()).in11();
        this.in12 = ((FanInShape20)this.shape()).in12();
        this.in13 = ((FanInShape20)this.shape()).in13();
        this.in14 = ((FanInShape20)this.shape()).in14();
        this.in15 = ((FanInShape20)this.shape()).in15();
        this.in16 = ((FanInShape20)this.shape()).in16();
        this.in17 = ((FanInShape20)this.shape()).in17();
        this.in18 = ((FanInShape20)this.shape()).in18();
        this.in19 = ((FanInShape20)this.shape()).in19();
    }

    public ZipLatestWith20(Function20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O> zipper) {
        this(zipper, true);
    }
}

