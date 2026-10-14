/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t\u001de\u0001B\u00181\u0001UB\u0001\"\u0013\u0001\u0003\u0002\u0003\u0006IA\u0013\u0005\u00067\u0002!\t\u0001\u0018\u0005\u00077\u0002!\t!a\u000e\t\rm\u0003A\u0011AA*\u0011\u001d\tY\u000e\u0001C)\u0003;Dq!a9\u0001\t\u0003\n)\u000fC\u0005\u0002b\u0001\u0011\r\u0011\"\u0001\u0002h\"A\u0011Q\u001e\u0001!\u0002\u0013\tI\u000fC\u0005\u0002l\u0001\u0011\r\u0011\"\u0001\u0002p\"A\u0011Q\u001f\u0001!\u0002\u0013\t\t\u0010C\u0005\u0002r\u0001\u0011\r\u0011\"\u0001\u0002x\"A\u0011Q \u0001!\u0002\u0013\tI\u0010C\u0005\u0002x\u0001\u0011\r\u0011\"\u0001\u0002\u0000\"A!Q\u0001\u0001!\u0002\u0013\u0011\t\u0001C\u0005\u0002~\u0001\u0011\r\u0011\"\u0001\u0003\b!A!Q\u0002\u0001!\u0002\u0013\u0011I\u0001C\u0005\u0002\u0004\u0002\u0011\r\u0011\"\u0001\u0003\u0010!A!Q\u0003\u0001!\u0002\u0013\u0011\t\u0002C\u0005\u0002\n\u0002\u0011\r\u0011\"\u0001\u0003\u0018!A!Q\u0004\u0001!\u0002\u0013\u0011I\u0002C\u0005\u0002\u0010\u0002\u0011\r\u0011\"\u0001\u0003 !A!Q\u0005\u0001!\u0002\u0013\u0011\t\u0003C\u0005\u0002\u0016\u0002\u0011\r\u0011\"\u0001\u0003(!A!Q\u0006\u0001!\u0002\u0013\u0011I\u0003C\u0005\u0002\u001c\u0002\u0011\r\u0011\"\u0001\u00030!A!Q\u0007\u0001!\u0002\u0013\u0011\t\u0004C\u0005\u0002\"\u0002\u0011\r\u0011\"\u0001\u00038!A!Q\b\u0001!\u0002\u0013\u0011I\u0004C\u0005\u0002(\u0002\u0011\r\u0011\"\u0001\u0003@!A!Q\t\u0001!\u0002\u0013\u0011\t\u0005C\u0005\u0002.\u0002\u0011\r\u0011\"\u0001\u0003H!A!Q\n\u0001!\u0002\u0013\u0011I\u0005C\u0005\u00024\u0002\u0011\r\u0011\"\u0001\u0003P!A!Q\u000b\u0001!\u0002\u0013\u0011\t\u0006C\u0005\u0002:\u0002\u0011\r\u0011\"\u0001\u0003X!A!Q\f\u0001!\u0002\u0013\u0011I\u0006C\u0005\u0002@\u0002\u0011\r\u0011\"\u0001\u0003`!A!Q\r\u0001!\u0002\u0013\u0011\t\u0007C\u0005\u0002F\u0002\u0011\r\u0011\"\u0001\u0003h!A!Q\u000e\u0001!\u0002\u0013\u0011I\u0007C\u0005\u0002L\u0002\u0011\r\u0011\"\u0001\u0003p!A!Q\u000f\u0001!\u0002\u0013\u0011\t\bC\u0005\u0002R\u0002\u0011\r\u0011\"\u0001\u0003x!A!Q\u0010\u0001!\u0002\u0013\u0011I\bC\u0005\u0002X\u0002\u0011\r\u0011\"\u0001\u0003\u0000!A!Q\u0011\u0001!\u0002\u0013\u0011\tIA\u0007GC:|U\u000f^*iCB,'\u0007\r\u0006\u0003cI\naa\u001d;sK\u0006l'\"A\u001a\u0002\t\u0005\\7.Y\u0002\u0001+}1Th\u00182fQ.t\u0017\u000f^<{{\u0006\u0005\u0011qAA\u0007\u0003'\tI\"a\b\u0002&\u0005-\u0012\u0011G\n\u0003\u0001]\u00022\u0001O\u001d<\u001b\u0005\u0001\u0014B\u0001\u001e1\u0005-1\u0015M\\(viNC\u0017\r]3\u0011\u0005qjD\u0002\u0001\u0003\u0007}\u0001A)\u0019A \u0003\u0003%\u000b\"\u0001\u0011$\u0011\u0005\u0005#U\"\u0001\"\u000b\u0003\r\u000bQa]2bY\u0006L!!\u0012\"\u0003\u000f9{G\u000f[5oOB\u0011\u0011iR\u0005\u0003\u0011\n\u00131!\u00118z\u0003\u0015y\u0016N\\5u!\rYe*\u0015\b\u0003q1K!!\u0014\u0019\u0002\u0017\u0019\u000bgnT;u'\"\f\u0007/Z\u0005\u0003\u001fB\u0013A!\u00138ji*\u0011Q\n\r\u0016\u0003wI[\u0013a\u0015\t\u0003)fk\u0011!\u0016\u0006\u0003-^\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005a\u0013\u0015AC1o]>$\u0018\r^5p]&\u0011!,\u0016\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017A\u0002\u001fj]&$h\bF\u0002^\u0003k\u0001r\u0004\u000f\u0001<=\u0006$wM[7qgZLHp`A\u0003\u0003\u0017\t\t\"a\u0006\u0002\u001e\u0005\r\u0012\u0011FA\u0018!\tat\f\u0002\u0004a\u0001\u0011\u0015\ra\u0010\u0002\u0003\u001fB\u0002\"\u0001\u00102\u0005\r\r\u0004AQ1\u0001@\u0005\ty\u0015\u0007\u0005\u0002=K\u00121a\r\u0001CC\u0002}\u0012!a\u0014\u001a\u0011\u0005qBGAB5\u0001\t\u000b\u0007qH\u0001\u0002PgA\u0011Ah\u001b\u0003\u0007Y\u0002!)\u0019A \u0003\u0005=#\u0004C\u0001\u001fo\t\u0019y\u0007\u0001\"b\u0001\u007f\t\u0011q*\u000e\t\u0003yE$aA\u001d\u0001\u0005\u0006\u0004y$AA(7!\taD\u000f\u0002\u0004v\u0001\u0011\u0015\ra\u0010\u0002\u0003\u001f^\u0002\"\u0001P<\u0005\ra\u0004AQ1\u0001@\u0005\ty\u0005\b\u0005\u0002=u\u001211\u0010\u0001CC\u0002}\u0012!aT\u001d\u0011\u0005qjHA\u0002@\u0001\t\u000b\u0007qHA\u0002PcA\u00022\u0001PA\u0001\t\u001d\t\u0019\u0001\u0001CC\u0002}\u00121aT\u00192!\ra\u0014q\u0001\u0003\b\u0003\u0013\u0001AQ1\u0001@\u0005\ry\u0015G\r\t\u0004y\u00055AaBA\b\u0001\u0011\u0015\ra\u0010\u0002\u0004\u001fF\u001a\u0004c\u0001\u001f\u0002\u0014\u00119\u0011Q\u0003\u0001\u0005\u0006\u0004y$aA(2iA\u0019A(!\u0007\u0005\u000f\u0005m\u0001\u0001\"b\u0001\u007f\t\u0019q*M\u001b\u0011\u0007q\ny\u0002B\u0004\u0002\"\u0001!)\u0019A \u0003\u0007=\u000bd\u0007E\u0002=\u0003K!q!a\n\u0001\t\u000b\u0007qHA\u0002Pc]\u00022\u0001PA\u0016\t\u001d\ti\u0003\u0001CC\u0002}\u00121aT\u00199!\ra\u0014\u0011\u0007\u0003\b\u0003g\u0001AQ1\u0001@\u0005\ry\u0015'\u000f\u0005\u0006\u0013\n\u0001\rA\u0013\u000b\u0004;\u0006e\u0002bBA\u001e\u0007\u0001\u0007\u0011QH\u0001\u0005]\u0006lW\r\u0005\u0003\u0002@\u00055c\u0002BA!\u0003\u0013\u00022!a\u0011C\u001b\t\t)EC\u0002\u0002HQ\na\u0001\u0010:p_Rt\u0014bAA&\u0005\u00061\u0001K]3eK\u001aLA!a\u0014\u0002R\t11\u000b\u001e:j]\u001eT1!a\u0013C)-j\u0016QKA0\u0003S\ny'!\u001e\u0002|\u0005\u0005\u0015qQAG\u0003'\u000bI*a(\u0002&\u0006-\u0016\u0011WA\\\u0003{\u000b\u0019-!3\u0002P\u0006U\u0007bBA,\t\u0001\u0007\u0011\u0011L\u0001\u0003S:\u0004B\u0001OA.w%\u0019\u0011Q\f\u0019\u0003\u000b%sG.\u001a;\t\u000f\u0005\u0005D\u00011\u0001\u0002d\u0005!q.\u001e;1!\u0011A\u0014Q\r0\n\u0007\u0005\u001d\u0004G\u0001\u0004PkRdW\r\u001e\u0005\b\u0003W\"\u0001\u0019AA7\u0003\u0011yW\u000f^\u0019\u0011\ta\n)'\u0019\u0005\b\u0003c\"\u0001\u0019AA:\u0003\u0011yW\u000f\u001e\u001a\u0011\ta\n)\u0007\u001a\u0005\b\u0003o\"\u0001\u0019AA=\u0003\u0011yW\u000f^\u001a\u0011\ta\n)g\u001a\u0005\b\u0003{\"\u0001\u0019AA@\u0003\u0011yW\u000f\u001e\u001b\u0011\ta\n)G\u001b\u0005\b\u0003\u0007#\u0001\u0019AAC\u0003\u0011yW\u000f^\u001b\u0011\ta\n)'\u001c\u0005\b\u0003\u0013#\u0001\u0019AAF\u0003\u0011yW\u000f\u001e\u001c\u0011\ta\n)\u0007\u001d\u0005\b\u0003\u001f#\u0001\u0019AAI\u0003\u0011yW\u000f^\u001c\u0011\ta\n)g\u001d\u0005\b\u0003+#\u0001\u0019AAL\u0003\u0011yW\u000f\u001e\u001d\u0011\ta\n)G\u001e\u0005\b\u00037#\u0001\u0019AAO\u0003\u0011yW\u000f^\u001d\u0011\ta\n)'\u001f\u0005\b\u0003C#\u0001\u0019AAR\u0003\u0015yW\u000f^\u00191!\u0011A\u0014Q\r?\t\u000f\u0005\u001dF\u00011\u0001\u0002*\u0006)q.\u001e;2cA!\u0001(!\u001a\u0000\u0011\u001d\ti\u000b\u0002a\u0001\u0003_\u000bQa\\;ucI\u0002R\u0001OA3\u0003\u000bAq!a-\u0005\u0001\u0004\t),A\u0003pkR\f4\u0007E\u00039\u0003K\nY\u0001C\u0004\u0002:\u0012\u0001\r!a/\u0002\u000b=,H/\r\u001b\u0011\u000ba\n)'!\u0005\t\u000f\u0005}F\u00011\u0001\u0002B\u0006)q.\u001e;2kA)\u0001(!\u001a\u0002\u0018!9\u0011Q\u0019\u0003A\u0002\u0005\u001d\u0017!B8viF2\u0004#\u0002\u001d\u0002f\u0005u\u0001bBAf\t\u0001\u0007\u0011QZ\u0001\u0006_V$\u0018g\u000e\t\u0006q\u0005\u0015\u00141\u0005\u0005\b\u0003#$\u0001\u0019AAj\u0003\u0015yW\u000f^\u00199!\u0015A\u0014QMA\u0015\u0011\u001d\t9\u000e\u0002a\u0001\u00033\fQa\\;uce\u0002R\u0001OA3\u0003_\t\u0011bY8ogR\u0014Xo\u0019;\u0015\u0007]\ny\u000e\u0003\u0004\u0002b\u0016\u0001\rAS\u0001\u0005S:LG/\u0001\u0005eK\u0016\u00048i\u001c9z)\u0005iVCAAu!\u0015A\u0014QMAvU\tq&+A\u0003pkR\u0004\u0004%\u0006\u0002\u0002rB)\u0001(!\u001a\u0002t*\u0012\u0011MU\u0001\u0006_V$\u0018\u0007I\u000b\u0003\u0003s\u0004R\u0001OA3\u0003wT#\u0001\u001a*\u0002\u000b=,HO\r\u0011\u0016\u0005\t\u0005\u0001#\u0002\u001d\u0002f\t\r!FA4S\u0003\u0015yW\u000f^\u001a!+\t\u0011I\u0001E\u00039\u0003K\u0012YA\u000b\u0002k%\u0006)q.\u001e;5AU\u0011!\u0011\u0003\t\u0006q\u0005\u0015$1\u0003\u0016\u0003[J\u000bQa\\;uk\u0001*\"A!\u0007\u0011\u000ba\n)Ga\u0007+\u0005A\u0014\u0016!B8viZ\u0002SC\u0001B\u0011!\u0015A\u0014Q\rB\u0012U\t\u0019(+A\u0003pkR<\u0004%\u0006\u0002\u0003*A)\u0001(!\u001a\u0003,)\u0012aOU\u0001\u0006_V$\b\bI\u000b\u0003\u0005c\u0001R\u0001OA3\u0005gQ#!\u001f*\u0002\u000b=,H/\u000f\u0011\u0016\u0005\te\u0002#\u0002\u001d\u0002f\tm\"F\u0001?S\u0003\u0019yW\u000f^\u00191AU\u0011!\u0011\t\t\u0006q\u0005\u0015$1\t\u0016\u0003\u007fJ\u000baa\\;ucE\u0002SC\u0001B%!\u0015A\u0014Q\rB&U\r\t)AU\u0001\u0007_V$\u0018G\r\u0011\u0016\u0005\tE\u0003#\u0002\u001d\u0002f\tM#fAA\u0006%\u00061q.\u001e;2g\u0001*\"A!\u0017\u0011\u000ba\n)Ga\u0017+\u0007\u0005E!+\u0001\u0004pkR\fD\u0007I\u000b\u0003\u0005C\u0002R\u0001OA3\u0005GR3!a\u0006S\u0003\u0019yW\u000f^\u00196AU\u0011!\u0011\u000e\t\u0006q\u0005\u0015$1\u000e\u0016\u0004\u0003;\u0011\u0016AB8viF2\u0004%\u0006\u0002\u0003rA)\u0001(!\u001a\u0003t)\u001a\u00111\u0005*\u0002\r=,H/M\u001c!+\t\u0011I\bE\u00039\u0003K\u0012YHK\u0002\u0002*I\u000baa\\;uca\u0002SC\u0001BA!\u0015A\u0014Q\rBBU\r\tyCU\u0001\u0007_V$\u0018'\u000f\u0011")
public class FanOutShape20<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18, O19>
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

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape20<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18, O19>(init2);
    }

    @Override
    public FanOutShape20<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18, O19> deepCopy() {
        return (FanOutShape20)super.deepCopy();
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

    public FanOutShape20(FanOutShape.Init<I> _init) {
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
    }

    public FanOutShape20(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape20(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7, Outlet<O8> out8, Outlet<O9> out9, Outlet<O10> out10, Outlet<O11> out11, Outlet<O12> out12, Outlet<O13> out13, Outlet<O14> out14, Outlet<O15> out15, Outlet<O16> out16, Outlet<O17> out17, Outlet<O18> out18, Outlet<O19> out19) {
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
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet20).$colon$colon(outlet19).$colon$colon(outlet18).$colon$colon(outlet17).$colon$colon(outlet16).$colon$colon(outlet15).$colon$colon(outlet14).$colon$colon(outlet13).$colon$colon(outlet12).$colon$colon(outlet11).$colon$colon(outlet10).$colon$colon(outlet9).$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

