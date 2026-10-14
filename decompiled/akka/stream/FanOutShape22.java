/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t]f\u0001B\u001a5\u0001eB\u0001\"\u0014\u0001\u0003\u0002\u0003\u0006IA\u0014\u0005\u0006?\u0002!\t\u0001\u0019\u0005\u0007?\u0002!\t!a\u0013\t\r}\u0003A\u0011AA4\u0011\u001d\tY\u0010\u0001C)\u0003{DqAa\u0001\u0001\t\u0003\u0012)\u0001C\u0005\u0002v\u0001\u0011\r\u0011\"\u0001\u0003\b!A!Q\u0002\u0001!\u0002\u0013\u0011I\u0001C\u0005\u0002\u0000\u0001\u0011\r\u0011\"\u0001\u0003\u0010!A!Q\u0003\u0001!\u0002\u0013\u0011\t\u0002C\u0005\u0002\u0006\u0002\u0011\r\u0011\"\u0001\u0003\u0018!A!Q\u0004\u0001!\u0002\u0013\u0011I\u0002C\u0005\u0002\f\u0002\u0011\r\u0011\"\u0001\u0003 !A!Q\u0005\u0001!\u0002\u0013\u0011\t\u0003C\u0005\u0002\u0012\u0002\u0011\r\u0011\"\u0001\u0003(!A!Q\u0006\u0001!\u0002\u0013\u0011I\u0003C\u0005\u0002\u0018\u0002\u0011\r\u0011\"\u0001\u00030!A!Q\u0007\u0001!\u0002\u0013\u0011\t\u0004C\u0005\u0002\u001e\u0002\u0011\r\u0011\"\u0001\u00038!A!Q\b\u0001!\u0002\u0013\u0011I\u0004C\u0005\u0002$\u0002\u0011\r\u0011\"\u0001\u0003@!A!Q\t\u0001!\u0002\u0013\u0011\t\u0005C\u0005\u0002*\u0002\u0011\r\u0011\"\u0001\u0003H!A!Q\n\u0001!\u0002\u0013\u0011I\u0005C\u0005\u00020\u0002\u0011\r\u0011\"\u0001\u0003P!A!Q\u000b\u0001!\u0002\u0013\u0011\t\u0006C\u0005\u00026\u0002\u0011\r\u0011\"\u0001\u0003X!A!Q\f\u0001!\u0002\u0013\u0011I\u0006C\u0005\u0002<\u0002\u0011\r\u0011\"\u0001\u0003`!A!Q\r\u0001!\u0002\u0013\u0011\t\u0007C\u0005\u0002B\u0002\u0011\r\u0011\"\u0001\u0003h!A!Q\u000e\u0001!\u0002\u0013\u0011I\u0007C\u0005\u0002H\u0002\u0011\r\u0011\"\u0001\u0003p!A!Q\u000f\u0001!\u0002\u0013\u0011\t\bC\u0005\u0002N\u0002\u0011\r\u0011\"\u0001\u0003x!A!Q\u0010\u0001!\u0002\u0013\u0011I\bC\u0005\u0002T\u0002\u0011\r\u0011\"\u0001\u0003\u0000!A!Q\u0011\u0001!\u0002\u0013\u0011\t\tC\u0005\u0002Z\u0002\u0011\r\u0011\"\u0001\u0003\b\"A!Q\u0012\u0001!\u0002\u0013\u0011I\tC\u0005\u0002`\u0002\u0011\r\u0011\"\u0001\u0003\u0010\"A!Q\u0013\u0001!\u0002\u0013\u0011\t\nC\u0005\u0002f\u0002\u0011\r\u0011\"\u0001\u0003\u0018\"A!Q\u0014\u0001!\u0002\u0013\u0011I\nC\u0005\u0002l\u0002\u0011\r\u0011\"\u0001\u0003 \"A!Q\u0015\u0001!\u0002\u0013\u0011\t\u000bC\u0005\u0002r\u0002\u0011\r\u0011\"\u0001\u0003(\"A!Q\u0016\u0001!\u0002\u0013\u0011I\u000bC\u0005\u0002x\u0002\u0011\r\u0011\"\u0001\u00030\"A!Q\u0017\u0001!\u0002\u0013\u0011\tLA\u0007GC:|U\u000f^*iCB,'G\r\u0006\u0003kY\naa\u001d;sK\u0006l'\"A\u001c\u0002\t\u0005\\7.Y\u0002\u0001+\u0011R\u0014i\u00194jY>\u0014X\u000f_>\u007f\u0003\u0007\tI!a\u0004\u0002\u0016\u0005m\u0011\u0011EA\u0014\u0003[\t\u0019$!\u000f\u0002@\u0005\u00153C\u0001\u0001<!\raThP\u0007\u0002i%\u0011a\b\u000e\u0002\f\r\u0006tw*\u001e;TQ\u0006\u0004X\r\u0005\u0002A\u00032\u0001AA\u0002\"\u0001\u0011\u000b\u00071IA\u0001J#\t!%\n\u0005\u0002F\u00116\taIC\u0001H\u0003\u0015\u00198-\u00197b\u0013\tIeIA\u0004O_RD\u0017N\\4\u0011\u0005\u0015[\u0015B\u0001'G\u0005\r\te._\u0001\u0006?&t\u0017\u000e\u001e\t\u0004\u001fJ+fB\u0001\u001fQ\u0013\t\tF'A\u0006GC:|U\u000f^*iCB,\u0017BA*U\u0005\u0011Ie.\u001b;\u000b\u0005E#$FA WW\u00059\u0006C\u0001-^\u001b\u0005I&B\u0001.\\\u0003%)hn\u00195fG.,GM\u0003\u0002]\r\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005yK&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u00061A(\u001b8jiz\"2!YA%!\u0015b\u0004a\u00102fQ.t\u0017\u000f^<{{\u0006\u0005\u0011qAA\u0007\u0003'\tI\"a\b\u0002&\u0005-\u0012\u0011GA\u001c\u0003{\t\u0019\u0005\u0005\u0002AG\u00121A\r\u0001CC\u0002\r\u0013!a\u0014\u0019\u0011\u0005\u00013GAB4\u0001\t\u000b\u00071I\u0001\u0002PcA\u0011\u0001)\u001b\u0003\u0007U\u0002!)\u0019A\"\u0003\u0005=\u0013\u0004C\u0001!m\t\u0019i\u0007\u0001\"b\u0001\u0007\n\u0011qj\r\t\u0003\u0001>$a\u0001\u001d\u0001\u0005\u0006\u0004\u0019%AA(5!\t\u0001%\u000f\u0002\u0004t\u0001\u0011\u0015\ra\u0011\u0002\u0003\u001fV\u0002\"\u0001Q;\u0005\rY\u0004AQ1\u0001D\u0005\tye\u0007\u0005\u0002Aq\u00121\u0011\u0010\u0001CC\u0002\r\u0013!aT\u001c\u0011\u0005\u0001[HA\u0002?\u0001\t\u000b\u00071I\u0001\u0002PqA\u0011\u0001I \u0003\u0007\u007f\u0002!)\u0019A\"\u0003\u0005=K\u0004c\u0001!\u0002\u0004\u00119\u0011Q\u0001\u0001\u0005\u0006\u0004\u0019%aA(2aA\u0019\u0001)!\u0003\u0005\u000f\u0005-\u0001\u0001\"b\u0001\u0007\n\u0019q*M\u0019\u0011\u0007\u0001\u000by\u0001B\u0004\u0002\u0012\u0001!)\u0019A\"\u0003\u0007=\u000b$\u0007E\u0002A\u0003+!q!a\u0006\u0001\t\u000b\u00071IA\u0002PcM\u00022\u0001QA\u000e\t\u001d\ti\u0002\u0001CC\u0002\r\u00131aT\u00195!\r\u0001\u0015\u0011\u0005\u0003\b\u0003G\u0001AQ1\u0001D\u0005\ry\u0015'\u000e\t\u0004\u0001\u0006\u001dBaBA\u0015\u0001\u0011\u0015\ra\u0011\u0002\u0004\u001fF2\u0004c\u0001!\u0002.\u00119\u0011q\u0006\u0001\u0005\u0006\u0004\u0019%aA(2oA\u0019\u0001)a\r\u0005\u000f\u0005U\u0002\u0001\"b\u0001\u0007\n\u0019q*\r\u001d\u0011\u0007\u0001\u000bI\u0004B\u0004\u0002<\u0001!)\u0019A\"\u0003\u0007=\u000b\u0014\bE\u0002A\u0003\u007f!q!!\u0011\u0001\t\u000b\u00071IA\u0002PeA\u00022\u0001QA#\t\u001d\t9\u0005\u0001CC\u0002\r\u00131a\u0014\u001a2\u0011\u0015i%\u00011\u0001O)\r\t\u0017Q\n\u0005\b\u0003\u001f\u001a\u0001\u0019AA)\u0003\u0011q\u0017-\\3\u0011\t\u0005M\u0013\u0011\r\b\u0005\u0003+\ni\u0006E\u0002\u0002X\u0019k!!!\u0017\u000b\u0007\u0005m\u0003(\u0001\u0004=e>|GOP\u0005\u0004\u0003?2\u0015A\u0002)sK\u0012,g-\u0003\u0003\u0002d\u0005\u0015$AB*ue&twMC\u0002\u0002`\u0019#r&YA5\u0003g\ni(a!\u0002\n\u0006=\u0015QSAN\u0003C\u000b9+!,\u00024\u0006e\u0016qXAc\u0003\u0017\f\t.a6\u0002^\u0006\r\u0018\u0011^Ax\u0003kDq!a\u001b\u0005\u0001\u0004\ti'\u0001\u0002j]B!A(a\u001c@\u0013\r\t\t\b\u000e\u0002\u0006\u0013:dW\r\u001e\u0005\b\u0003k\"\u0001\u0019AA<\u0003\u0011yW\u000f\u001e\u0019\u0011\tq\nIHY\u0005\u0004\u0003w\"$AB(vi2,G\u000fC\u0004\u0002\u0000\u0011\u0001\r!!!\u0002\t=,H/\r\t\u0005y\u0005eT\rC\u0004\u0002\u0006\u0012\u0001\r!a\"\u0002\t=,HO\r\t\u0005y\u0005e\u0004\u000eC\u0004\u0002\f\u0012\u0001\r!!$\u0002\t=,Ho\r\t\u0005y\u0005e4\u000eC\u0004\u0002\u0012\u0012\u0001\r!a%\u0002\t=,H\u000f\u000e\t\u0005y\u0005ed\u000eC\u0004\u0002\u0018\u0012\u0001\r!!'\u0002\t=,H/\u000e\t\u0005y\u0005e\u0014\u000fC\u0004\u0002\u001e\u0012\u0001\r!a(\u0002\t=,HO\u000e\t\u0005y\u0005eD\u000fC\u0004\u0002$\u0012\u0001\r!!*\u0002\t=,Ho\u000e\t\u0005y\u0005et\u000fC\u0004\u0002*\u0012\u0001\r!a+\u0002\t=,H\u000f\u000f\t\u0005y\u0005e$\u0010C\u0004\u00020\u0012\u0001\r!!-\u0002\t=,H/\u000f\t\u0005y\u0005eT\u0010C\u0004\u00026\u0012\u0001\r!a.\u0002\u000b=,H/\r\u0019\u0011\u000bq\nI(!\u0001\t\u000f\u0005mF\u00011\u0001\u0002>\u0006)q.\u001e;2cA)A(!\u001f\u0002\b!9\u0011\u0011\u0019\u0003A\u0002\u0005\r\u0017!B8viF\u0012\u0004#\u0002\u001f\u0002z\u00055\u0001bBAd\t\u0001\u0007\u0011\u0011Z\u0001\u0006_V$\u0018g\r\t\u0006y\u0005e\u00141\u0003\u0005\b\u0003\u001b$\u0001\u0019AAh\u0003\u0015yW\u000f^\u00195!\u0015a\u0014\u0011PA\r\u0011\u001d\t\u0019\u000e\u0002a\u0001\u0003+\fQa\\;ucU\u0002R\u0001PA=\u0003?Aq!!7\u0005\u0001\u0004\tY.A\u0003pkR\fd\u0007E\u0003=\u0003s\n)\u0003C\u0004\u0002`\u0012\u0001\r!!9\u0002\u000b=,H/M\u001c\u0011\u000bq\nI(a\u000b\t\u000f\u0005\u0015H\u00011\u0001\u0002h\u0006)q.\u001e;2qA)A(!\u001f\u00022!9\u00111\u001e\u0003A\u0002\u00055\u0018!B8viFJ\u0004#\u0002\u001f\u0002z\u0005]\u0002bBAy\t\u0001\u0007\u00111_\u0001\u0006_V$(\u0007\r\t\u0006y\u0005e\u0014Q\b\u0005\b\u0003o$\u0001\u0019AA}\u0003\u0015yW\u000f\u001e\u001a2!\u0015a\u0014\u0011PA\"\u0003%\u0019wN\\:ueV\u001cG\u000fF\u0002<\u0003\u007fDaA!\u0001\u0006\u0001\u0004q\u0015\u0001B5oSR\f\u0001\u0002Z3fa\u000e{\u0007/\u001f\u000b\u0002CV\u0011!\u0011\u0002\t\u0006y\u0005e$1\u0002\u0016\u0003EZ\u000bQa\\;ua\u0001*\"A!\u0005\u0011\u000bq\nIHa\u0005+\u0005\u00154\u0016!B8viF\u0002SC\u0001B\r!\u0015a\u0014\u0011\u0010B\u000eU\tAg+A\u0003pkR\u0014\u0004%\u0006\u0002\u0003\"A)A(!\u001f\u0003$)\u00121NV\u0001\u0006_V$8\u0007I\u000b\u0003\u0005S\u0001R\u0001PA=\u0005WQ#A\u001c,\u0002\u000b=,H\u000f\u000e\u0011\u0016\u0005\tE\u0002#\u0002\u001f\u0002z\tM\"FA9W\u0003\u0015yW\u000f^\u001b!+\t\u0011I\u0004E\u0003=\u0003s\u0012YD\u000b\u0002u-\u0006)q.\u001e;7AU\u0011!\u0011\t\t\u0006y\u0005e$1\t\u0016\u0003oZ\u000bQa\\;uo\u0001*\"A!\u0013\u0011\u000bq\nIHa\u0013+\u0005i4\u0016!B8vib\u0002SC\u0001B)!\u0015a\u0014\u0011\u0010B*U\tih+A\u0003pkRL\u0004%\u0006\u0002\u0003ZA)A(!\u001f\u0003\\)\u001a\u0011\u0011\u0001,\u0002\r=,H/\r\u0019!+\t\u0011\t\u0007E\u0003=\u0003s\u0012\u0019GK\u0002\u0002\bY\u000baa\\;ucE\u0002SC\u0001B5!\u0015a\u0014\u0011\u0010B6U\r\tiAV\u0001\u0007_V$\u0018G\r\u0011\u0016\u0005\tE\u0004#\u0002\u001f\u0002z\tM$fAA\n-\u00061q.\u001e;2g\u0001*\"A!\u001f\u0011\u000bq\nIHa\u001f+\u0007\u0005ea+\u0001\u0004pkR\fD\u0007I\u000b\u0003\u0005\u0003\u0003R\u0001PA=\u0005\u0007S3!a\bW\u0003\u0019yW\u000f^\u00196AU\u0011!\u0011\u0012\t\u0006y\u0005e$1\u0012\u0016\u0004\u0003K1\u0016AB8viF2\u0004%\u0006\u0002\u0003\u0012B)A(!\u001f\u0003\u0014*\u001a\u00111\u0006,\u0002\r=,H/M\u001c!+\t\u0011I\nE\u0003=\u0003s\u0012YJK\u0002\u00022Y\u000baa\\;uca\u0002SC\u0001BQ!\u0015a\u0014\u0011\u0010BRU\r\t9DV\u0001\u0007_V$\u0018'\u000f\u0011\u0016\u0005\t%\u0006#\u0002\u001f\u0002z\t-&fAA\u001f-\u00061q.\u001e;3a\u0001*\"A!-\u0011\u000bq\nIHa-+\u0007\u0005\rc+\u0001\u0004pkR\u0014\u0014\u0007\t")
public class FanOutShape22<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18, O19, O20, O21>
extends FanOutShape<I> {
    private final Outlet<O0> out0;
    private final Outlet<O1> out1;
    private final Outlet<O2> out2;
    private final Outlet<O3> out3;
    private final Outlet<O4> out4;
    private final Outlet<O5> out5;
    private final Outlet<O6> out6;
    private final Outlet<O7> out7;
    private final Outlet<O8> out8;
    private final Outlet<O9> out9;
    private final Outlet<O10> out10;
    private final Outlet<O11> out11;
    private final Outlet<O12> out12;
    private final Outlet<O13> out13;
    private final Outlet<O14> out14;
    private final Outlet<O15> out15;
    private final Outlet<O16> out16;
    private final Outlet<O17> out17;
    private final Outlet<O18> out18;
    private final Outlet<O19> out19;
    private final Outlet<O20> out20;
    private final Outlet<O21> out21;

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape22<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18, O19, O20, O21>(init2);
    }

    @Override
    public FanOutShape22<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18, O19, O20, O21> deepCopy() {
        return (FanOutShape22)super.deepCopy();
    }

    public Outlet<O0> out0() {
        return this.out0;
    }

    public Outlet<O1> out1() {
        return this.out1;
    }

    public Outlet<O2> out2() {
        return this.out2;
    }

    public Outlet<O3> out3() {
        return this.out3;
    }

    public Outlet<O4> out4() {
        return this.out4;
    }

    public Outlet<O5> out5() {
        return this.out5;
    }

    public Outlet<O6> out6() {
        return this.out6;
    }

    public Outlet<O7> out7() {
        return this.out7;
    }

    public Outlet<O8> out8() {
        return this.out8;
    }

    public Outlet<O9> out9() {
        return this.out9;
    }

    public Outlet<O10> out10() {
        return this.out10;
    }

    public Outlet<O11> out11() {
        return this.out11;
    }

    public Outlet<O12> out12() {
        return this.out12;
    }

    public Outlet<O13> out13() {
        return this.out13;
    }

    public Outlet<O14> out14() {
        return this.out14;
    }

    public Outlet<O15> out15() {
        return this.out15;
    }

    public Outlet<O16> out16() {
        return this.out16;
    }

    public Outlet<O17> out17() {
        return this.out17;
    }

    public Outlet<O18> out18() {
        return this.out18;
    }

    public Outlet<O19> out19() {
        return this.out19;
    }

    public Outlet<O20> out20() {
        return this.out20;
    }

    public Outlet<O21> out21() {
        return this.out21;
    }

    public FanOutShape22(FanOutShape.Init<I> _init) {
        super(_init);
        this.out0 = this.newOutlet("out0");
        this.out1 = this.newOutlet("out1");
        this.out2 = this.newOutlet("out2");
        this.out3 = this.newOutlet("out3");
        this.out4 = this.newOutlet("out4");
        this.out5 = this.newOutlet("out5");
        this.out6 = this.newOutlet("out6");
        this.out7 = this.newOutlet("out7");
        this.out8 = this.newOutlet("out8");
        this.out9 = this.newOutlet("out9");
        this.out10 = this.newOutlet("out10");
        this.out11 = this.newOutlet("out11");
        this.out12 = this.newOutlet("out12");
        this.out13 = this.newOutlet("out13");
        this.out14 = this.newOutlet("out14");
        this.out15 = this.newOutlet("out15");
        this.out16 = this.newOutlet("out16");
        this.out17 = this.newOutlet("out17");
        this.out18 = this.newOutlet("out18");
        this.out19 = this.newOutlet("out19");
        this.out20 = this.newOutlet("out20");
        this.out21 = this.newOutlet("out21");
    }

    public FanOutShape22(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape22(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7, Outlet<O8> out8, Outlet<O9> out9, Outlet<O10> out10, Outlet<O11> out11, Outlet<O12> out12, Outlet<O13> out13, Outlet<O14> out14, Outlet<O15> out15, Outlet<O16> out16, Outlet<O17> out17, Outlet<O18> out18, Outlet<O19> out19, Outlet<O20> out20, Outlet<O21> out21) {
        Outlet<O0> outlet = out0;
        Outlet<O1> outlet2 = out1;
        Outlet<O2> outlet3 = out2;
        Outlet<O3> outlet4 = out3;
        Outlet<O4> outlet5 = out4;
        Outlet<O5> outlet6 = out5;
        Outlet<O6> outlet7 = out6;
        Outlet<O7> outlet8 = out7;
        Outlet<O8> outlet9 = out8;
        Outlet<O9> outlet10 = out9;
        Outlet<O10> outlet11 = out10;
        Outlet<O11> outlet12 = out11;
        Outlet<O12> outlet13 = out12;
        Outlet<O13> outlet14 = out13;
        Outlet<O14> outlet15 = out14;
        Outlet<O15> outlet16 = out15;
        Outlet<O16> outlet17 = out16;
        Outlet<O17> outlet18 = out17;
        Outlet<O18> outlet19 = out18;
        Outlet<O19> outlet20 = out19;
        Outlet<O20> outlet21 = out20;
        Outlet<O21> outlet22 = out21;
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet22).$colon$colon(outlet21).$colon$colon(outlet20).$colon$colon(outlet19).$colon$colon(outlet18).$colon$colon(outlet17).$colon$colon(outlet16).$colon$colon(outlet15).$colon$colon(outlet14).$colon$colon(outlet13).$colon$colon(outlet12).$colon$colon(outlet11).$colon$colon(outlet10).$colon$colon(outlet9).$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

