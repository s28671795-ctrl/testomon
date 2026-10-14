/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t%e\u0001B\u00181\u0001UB\u0001\"\u0013\u0001\u0003\u0002\u0003\u0006IA\u0013\u0005\u0006#\u0002!\tA\u0015\u0005\u0007#\u0002!\t!a\t\t\rE\u0003A\u0011AA \u0011\u001d\t9\r\u0001C)\u0003\u0013Dq!!:\u0001\t\u0003\n9\u000fC\u0005\u0002D\u0001\u0011\r\u0011\"\u0001\u0002j\"A\u0011q\u001e\u0001!\u0002\u0013\tY\u000fC\u0005\u0002N\u0001\u0011\r\u0011\"\u0001\u0002r\"A\u0011q\u001f\u0001!\u0002\u0013\t\u0019\u0010C\u0005\u0002T\u0001\u0011\r\u0011\"\u0001\u0002z\"A\u0011q \u0001!\u0002\u0013\tY\u0010C\u0005\u0002Z\u0001\u0011\r\u0011\"\u0001\u0003\u0002!A!q\u0001\u0001!\u0002\u0013\u0011\u0019\u0001C\u0005\u0002`\u0001\u0011\r\u0011\"\u0001\u0003\n!A!q\u0002\u0001!\u0002\u0013\u0011Y\u0001C\u0005\u0002f\u0001\u0011\r\u0011\"\u0001\u0003\u0012!A!q\u0003\u0001!\u0002\u0013\u0011\u0019\u0002C\u0005\u0002l\u0001\u0011\r\u0011\"\u0001\u0003\u001a!A!q\u0004\u0001!\u0002\u0013\u0011Y\u0002C\u0005\u0002r\u0001\u0011\r\u0011\"\u0001\u0003\"!A!q\u0005\u0001!\u0002\u0013\u0011\u0019\u0003C\u0005\u0002x\u0001\u0011\r\u0011\"\u0001\u0003*!A!q\u0006\u0001!\u0002\u0013\u0011Y\u0003C\u0005\u0002~\u0001\u0011\r\u0011\"\u0001\u00032!A!q\u0007\u0001!\u0002\u0013\u0011\u0019\u0004C\u0005\u0002\u0004\u0002\u0011\r\u0011\"\u0001\u0003:!A!q\b\u0001!\u0002\u0013\u0011Y\u0004C\u0005\u0002\n\u0002\u0011\r\u0011\"\u0001\u0003B!A!q\t\u0001!\u0002\u0013\u0011\u0019\u0005C\u0005\u0002\u0010\u0002\u0011\r\u0011\"\u0001\u0003J!A!q\n\u0001!\u0002\u0013\u0011Y\u0005C\u0005\u0002\u0016\u0002\u0011\r\u0011\"\u0001\u0003R!A!q\u000b\u0001!\u0002\u0013\u0011\u0019\u0006C\u0005\u0002\u001c\u0002\u0011\r\u0011\"\u0001\u0003Z!A!q\f\u0001!\u0002\u0013\u0011Y\u0006C\u0005\u0002\"\u0002\u0011\r\u0011\"\u0001\u0003b!A!q\r\u0001!\u0002\u0013\u0011\u0019\u0007C\u0005\u0002(\u0002\u0011\r\u0011\"\u0001\u0003j!A!q\u000e\u0001!\u0002\u0013\u0011Y\u0007C\u0005\u0002.\u0002\u0011\r\u0011\"\u0001\u0003r!A!q\u000f\u0001!\u0002\u0013\u0011\u0019\bC\u0005\u00024\u0002\u0011\r\u0011\"\u0001\u0003z!A!q\u0010\u0001!\u0002\u0013\u0011Y\bC\u0005\u0002:\u0002\u0011\r\u0011\"\u0001\u0003\u0002\"A!q\u0011\u0001!\u0002\u0013\u0011\u0019I\u0001\u0007GC:Len\u00155ba\u0016\u0014\u0004G\u0003\u00022e\u000511\u000f\u001e:fC6T\u0011aM\u0001\u0005C.\\\u0017m\u0001\u0001\u00167Y*\u0006l\u00170bI\u001eTW\u000e]:wsr|\u0018QAA\u0006\u0003#\t9\"!\b>'\t\u0001q\u0007E\u00029smj\u0011\u0001M\u0005\u0003uA\u0012!BR1o\u0013:\u001c\u0006.\u00199f!\taT\b\u0004\u0001\u0005\ry\u0002AQ1\u0001@\u0005\u0005y\u0015C\u0001!G!\t\tE)D\u0001C\u0015\u0005\u0019\u0015!B:dC2\f\u0017BA#C\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!Q$\n\u0005!\u0013%aA!os\u0006)q,\u001b8jiB\u00191JT\u001e\u000f\u0005ab\u0015BA'1\u0003)1\u0015M\\%o'\"\f\u0007/Z\u0005\u0003\u001fB\u0013A!\u00138ji*\u0011Q\nM\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007M\u000b\t\u0003\u0005\u000f9\u0001Q;&,\u00181dM&dwN];ywz\f\u0019!!\u0003\u0002\u0010\u0005U\u00111D\u001e\u0011\u0005q*FA\u0002,\u0001\u0011\u000b\u0007qH\u0001\u0002UaA\u0011A\b\u0017\u0003\u00073\u0002A)\u0019A \u0003\u0005Q\u000b\u0004C\u0001\u001f\\\t\u0019a\u0006\u0001#b\u0001\u007f\t\u0011AK\r\t\u0003yy#aa\u0018\u0001\t\u0006\u0004y$A\u0001+4!\ta\u0014\r\u0002\u0004c\u0001!\u0015\ra\u0010\u0002\u0003)R\u0002\"\u0001\u00103\u0005\r\u0015\u0004\u0001R1\u0001@\u0005\t!V\u0007\u0005\u0002=O\u00121\u0001\u000e\u0001EC\u0002}\u0012!\u0001\u0016\u001c\u0011\u0005qRGAB6\u0001\u0011\u000b\u0007qH\u0001\u0002UoA\u0011A(\u001c\u0003\u0007]\u0002A)\u0019A \u0003\u0005QC\u0004C\u0001\u001fq\t\u0019\t\b\u0001#b\u0001\u007f\t\u0011A+\u000f\t\u0003yM$a\u0001\u001e\u0001\t\u0006\u0004y$a\u0001+2aA\u0011AH\u001e\u0003\u0007o\u0002A)\u0019A \u0003\u0007Q\u000b\u0014\u0007\u0005\u0002=s\u00121!\u0010\u0001EC\u0002}\u00121\u0001V\u00193!\taD\u0010\u0002\u0004~\u0001!\u0015\ra\u0010\u0002\u0004)F\u001a\u0004C\u0001\u001f\u0000\t\u001d\t\t\u0001\u0001EC\u0002}\u00121\u0001V\u00195!\ra\u0014Q\u0001\u0003\b\u0003\u000f\u0001\u0001R1\u0001@\u0005\r!\u0016'\u000e\t\u0004y\u0005-AaBA\u0007\u0001!\u0015\ra\u0010\u0002\u0004)F2\u0004c\u0001\u001f\u0002\u0012\u00119\u00111\u0003\u0001\t\u0006\u0004y$a\u0001+2oA\u0019A(a\u0006\u0005\u000f\u0005e\u0001\u0001#b\u0001\u007f\t\u0019A+\r\u001d\u0011\u0007q\ni\u0002B\u0004\u0002 \u0001A)\u0019A \u0003\u0007Q\u000b\u0014\bC\u0003J\u0005\u0001\u0007!\nF\u0002T\u0003KAq!a\n\u0004\u0001\u0004\tI#\u0001\u0003oC6,\u0007\u0003BA\u0016\u0003sqA!!\f\u00026A\u0019\u0011q\u0006\"\u000e\u0005\u0005E\"bAA\u001ai\u00051AH]8pizJ1!a\u000eC\u0003\u0019\u0001&/\u001a3fM&!\u00111HA\u001f\u0005\u0019\u0019FO]5oO*\u0019\u0011q\u0007\"\u0015WM\u000b\t%a\u0013\u0002R\u0005]\u0013QLA2\u0003S\ny'!\u001e\u0002|\u0005\u0005\u0015qQAG\u0003'\u000bI*a(\u0002&\u0006-\u0016\u0011WA\\\u0003{Cq!a\u0011\u0005\u0001\u0004\t)%A\u0002j]B\u0002B\u0001OA$)&\u0019\u0011\u0011\n\u0019\u0003\u000b%sG.\u001a;\t\u000f\u00055C\u00011\u0001\u0002P\u0005\u0019\u0011N\\\u0019\u0011\ta\n9e\u0016\u0005\b\u0003'\"\u0001\u0019AA+\u0003\rIgN\r\t\u0005q\u0005\u001d#\fC\u0004\u0002Z\u0011\u0001\r!a\u0017\u0002\u0007%t7\u0007\u0005\u00039\u0003\u000fj\u0006bBA0\t\u0001\u0007\u0011\u0011M\u0001\u0004S:$\u0004\u0003\u0002\u001d\u0002H\u0001Dq!!\u001a\u0005\u0001\u0004\t9'A\u0002j]V\u0002B\u0001OA$G\"9\u00111\u000e\u0003A\u0002\u00055\u0014aA5omA!\u0001(a\u0012g\u0011\u001d\t\t\b\u0002a\u0001\u0003g\n1!\u001b88!\u0011A\u0014qI5\t\u000f\u0005]D\u00011\u0001\u0002z\u0005\u0019\u0011N\u001c\u001d\u0011\ta\n9\u0005\u001c\u0005\b\u0003{\"\u0001\u0019AA@\u0003\rIg.\u000f\t\u0005q\u0005\u001ds\u000eC\u0004\u0002\u0004\u0012\u0001\r!!\"\u0002\t%t\u0017\u0007\r\t\u0005q\u0005\u001d#\u000fC\u0004\u0002\n\u0012\u0001\r!a#\u0002\t%t\u0017'\r\t\u0005q\u0005\u001dS\u000fC\u0004\u0002\u0010\u0012\u0001\r!!%\u0002\t%t\u0017G\r\t\u0005q\u0005\u001d\u0003\u0010C\u0004\u0002\u0016\u0012\u0001\r!a&\u0002\t%t\u0017g\r\t\u0005q\u0005\u001d3\u0010C\u0004\u0002\u001c\u0012\u0001\r!!(\u0002\t%t\u0017\u0007\u000e\t\u0005q\u0005\u001dc\u0010C\u0004\u0002\"\u0012\u0001\r!a)\u0002\t%t\u0017'\u000e\t\u0006q\u0005\u001d\u00131\u0001\u0005\b\u0003O#\u0001\u0019AAU\u0003\u0011Ig.\r\u001c\u0011\u000ba\n9%!\u0003\t\u000f\u00055F\u00011\u0001\u00020\u0006!\u0011N\\\u00198!\u0015A\u0014qIA\b\u0011\u001d\t\u0019\f\u0002a\u0001\u0003k\u000bA!\u001b82qA)\u0001(a\u0012\u0002\u0016!9\u0011\u0011\u0018\u0003A\u0002\u0005m\u0016\u0001B5oce\u0002R\u0001OA$\u00037Aq!a0\u0005\u0001\u0004\t\t-A\u0002pkR\u0004B\u0001OAbw%\u0019\u0011Q\u0019\u0019\u0003\r=+H\u000f\\3u\u0003%\u0019wN\\:ueV\u001cG\u000fF\u00028\u0003\u0017Dq!!4\u0006\u0001\u0004\ty-\u0001\u0003j]&$\b\u0003B&O\u0003#T3aOAjW\t\t)\u000e\u0005\u0003\u0002X\u0006\u0005XBAAm\u0015\u0011\tY.!8\u0002\u0013Ut7\r[3dW\u0016$'bAAp\u0005\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005\r\u0018\u0011\u001c\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017\u0001\u00033fKB\u001cu\u000e]=\u0015\u0003M+\"!a;\u0011\u000ba\n9%!<+\u0007Q\u000b\u0019.\u0001\u0003j]B\u0002SCAAz!\u0015A\u0014qIA{U\r9\u00161[\u0001\u0005S:\f\u0004%\u0006\u0002\u0002|B)\u0001(a\u0012\u0002~*\u001a!,a5\u0002\t%t'\u0007I\u000b\u0003\u0005\u0007\u0001R\u0001OA$\u0005\u000bQ3!XAj\u0003\u0011Ign\r\u0011\u0016\u0005\t-\u0001#\u0002\u001d\u0002H\t5!f\u00011\u0002T\u0006!\u0011N\u001c\u001b!+\t\u0011\u0019\u0002E\u00039\u0003\u000f\u0012)BK\u0002d\u0003'\fA!\u001b86AU\u0011!1\u0004\t\u0006q\u0005\u001d#Q\u0004\u0016\u0004M\u0006M\u0017\u0001B5om\u0001*\"Aa\t\u0011\u000ba\n9E!\n+\u0007%\f\u0019.\u0001\u0003j]^\u0002SC\u0001B\u0016!\u0015A\u0014q\tB\u0017U\ra\u00171[\u0001\u0005S:D\u0004%\u0006\u0002\u00034A)\u0001(a\u0012\u00036)\u001aq.a5\u0002\t%t\u0017\bI\u000b\u0003\u0005w\u0001R\u0001OA$\u0005{Q3A]Aj\u0003\u0015Ig.\r\u0019!+\t\u0011\u0019\u0005E\u00039\u0003\u000f\u0012)EK\u0002v\u0003'\fQ!\u001b82c\u0001*\"Aa\u0013\u0011\u000ba\n9E!\u0014+\u0007a\f\u0019.A\u0003j]F\u0012\u0004%\u0006\u0002\u0003TA)\u0001(a\u0012\u0003V)\u001a10a5\u0002\u000b%t\u0017g\r\u0011\u0016\u0005\tm\u0003#\u0002\u001d\u0002H\tu#f\u0001@\u0002T\u0006)\u0011N\\\u00195AU\u0011!1\r\t\u0006q\u0005\u001d#Q\r\u0016\u0005\u0003\u0007\t\u0019.A\u0003j]F*\u0004%\u0006\u0002\u0003lA)\u0001(a\u0012\u0003n)\"\u0011\u0011BAj\u0003\u0015Ig.\r\u001c!+\t\u0011\u0019\bE\u00039\u0003\u000f\u0012)H\u000b\u0003\u0002\u0010\u0005M\u0017!B5oc]\u0002SC\u0001B>!\u0015A\u0014q\tB?U\u0011\t)\"a5\u0002\u000b%t\u0017\u0007\u000f\u0011\u0016\u0005\t\r\u0005#\u0002\u001d\u0002H\t\u0015%\u0006BA\u000e\u0003'\fQ!\u001b82s\u0001\u0002")
public class FanInShape20<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, O>
extends FanInShape<O> {
    private final Inlet<T0> in0;
    private final Inlet<T1> in1;
    private final Inlet<T2> in2;
    private final Inlet<T3> in3;
    private final Inlet<T4> in4;
    private final Inlet<T5> in5;
    private final Inlet<T6> in6;
    private final Inlet<T7> in7;
    private final Inlet<T8> in8;
    private final Inlet<T9> in9;
    private final Inlet<T10> in10;
    private final Inlet<T11> in11;
    private final Inlet<T12> in12;
    private final Inlet<T13> in13;
    private final Inlet<T14> in14;
    private final Inlet<T15> in15;
    private final Inlet<T16> in16;
    private final Inlet<T17> in17;
    private final Inlet<T18> in18;
    private final Inlet<T19> in19;

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape20<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, O>(init2);
    }

    @Override
    public FanInShape20<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, O> deepCopy() {
        return (FanInShape20)super.deepCopy();
    }

    public Inlet<T0> in0() {
        return this.in0;
    }

    public Inlet<T1> in1() {
        return this.in1;
    }

    public Inlet<T2> in2() {
        return this.in2;
    }

    public Inlet<T3> in3() {
        return this.in3;
    }

    public Inlet<T4> in4() {
        return this.in4;
    }

    public Inlet<T5> in5() {
        return this.in5;
    }

    public Inlet<T6> in6() {
        return this.in6;
    }

    public Inlet<T7> in7() {
        return this.in7;
    }

    public Inlet<T8> in8() {
        return this.in8;
    }

    public Inlet<T9> in9() {
        return this.in9;
    }

    public Inlet<T10> in10() {
        return this.in10;
    }

    public Inlet<T11> in11() {
        return this.in11;
    }

    public Inlet<T12> in12() {
        return this.in12;
    }

    public Inlet<T13> in13() {
        return this.in13;
    }

    public Inlet<T14> in14() {
        return this.in14;
    }

    public Inlet<T15> in15() {
        return this.in15;
    }

    public Inlet<T16> in16() {
        return this.in16;
    }

    public Inlet<T17> in17() {
        return this.in17;
    }

    public Inlet<T18> in18() {
        return this.in18;
    }

    public Inlet<T19> in19() {
        return this.in19;
    }

    public FanInShape20(FanInShape.Init<O> _init) {
        super(_init);
        this.in0 = this.newInlet("in0");
        this.in1 = this.newInlet("in1");
        this.in2 = this.newInlet("in2");
        this.in3 = this.newInlet("in3");
        this.in4 = this.newInlet("in4");
        this.in5 = this.newInlet("in5");
        this.in6 = this.newInlet("in6");
        this.in7 = this.newInlet("in7");
        this.in8 = this.newInlet("in8");
        this.in9 = this.newInlet("in9");
        this.in10 = this.newInlet("in10");
        this.in11 = this.newInlet("in11");
        this.in12 = this.newInlet("in12");
        this.in13 = this.newInlet("in13");
        this.in14 = this.newInlet("in14");
        this.in15 = this.newInlet("in15");
        this.in16 = this.newInlet("in16");
        this.in17 = this.newInlet("in17");
        this.in18 = this.newInlet("in18");
        this.in19 = this.newInlet("in19");
    }

    public FanInShape20(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape20(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Inlet<T8> in8, Inlet<T9> in9, Inlet<T10> in10, Inlet<T11> in11, Inlet<T12> in12, Inlet<T13> in13, Inlet<T14> in14, Inlet<T15> in15, Inlet<T16> in16, Inlet<T17> in17, Inlet<T18> in18, Inlet<T19> in19, Outlet<O> out) {
        Inlet<T0> inlet = in0;
        Inlet<T1> inlet2 = in1;
        Inlet<T2> inlet3 = in2;
        Inlet<T3> inlet4 = in3;
        Inlet<T4> inlet5 = in4;
        Inlet<T5> inlet6 = in5;
        Inlet<T6> inlet7 = in6;
        Inlet<T7> inlet8 = in7;
        Inlet<T8> inlet9 = in8;
        Inlet<T9> inlet10 = in9;
        Inlet<T10> inlet11 = in10;
        Inlet<T11> inlet12 = in11;
        Inlet<T12> inlet13 = in12;
        Inlet<T13> inlet14 = in13;
        Inlet<T14> inlet15 = in14;
        Inlet<T15> inlet16 = in15;
        Inlet<T16> inlet17 = in16;
        Inlet<T17> inlet18 = in17;
        Inlet<T18> inlet19 = in18;
        Inlet<T19> inlet20 = in19;
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet20).$colon$colon(inlet19).$colon$colon(inlet18).$colon$colon(inlet17).$colon$colon(inlet16).$colon$colon(inlet15).$colon$colon(inlet14).$colon$colon(inlet13).$colon$colon(inlet12).$colon$colon(inlet11).$colon$colon(inlet10).$colon$colon(inlet9).$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

