/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t=d\u0001B\u0017/\u0001MB\u0001b\u0012\u0001\u0003\u0002\u0003\u0006I\u0001\u0013\u0005\u00063\u0002!\tA\u0017\u0005\u00073\u0002!\t!!\f\t\re\u0003A\u0011AA%\u0011\u001d\tY\r\u0001C)\u0003\u001bDq!a5\u0001\t\u0003\n)\u000eC\u0005\u0002X\u0001\u0011\r\u0011\"\u0001\u0002X\"A\u0011Q\u001c\u0001!\u0002\u0013\tI\u000eC\u0005\u0002b\u0001\u0011\r\u0011\"\u0001\u0002`\"A\u0011Q\u001d\u0001!\u0002\u0013\t\t\u000fC\u0005\u0002h\u0001\u0011\r\u0011\"\u0001\u0002h\"A\u0011Q\u001e\u0001!\u0002\u0013\tI\u000fC\u0005\u0002n\u0001\u0011\r\u0011\"\u0001\u0002p\"A\u0011Q\u001f\u0001!\u0002\u0013\t\t\u0010C\u0005\u0002t\u0001\u0011\r\u0011\"\u0001\u0002x\"A\u0011Q \u0001!\u0002\u0013\tI\u0010C\u0005\u0002z\u0001\u0011\r\u0011\"\u0001\u0002\u0000\"A!Q\u0001\u0001!\u0002\u0013\u0011\t\u0001C\u0005\u0002\u0000\u0001\u0011\r\u0011\"\u0001\u0003\b!A!Q\u0002\u0001!\u0002\u0013\u0011I\u0001C\u0005\u0002\u0006\u0002\u0011\r\u0011\"\u0001\u0003\u0010!A!Q\u0003\u0001!\u0002\u0013\u0011\t\u0002C\u0005\u0002\f\u0002\u0011\r\u0011\"\u0001\u0003\u0018!A!Q\u0004\u0001!\u0002\u0013\u0011I\u0002C\u0005\u0002\u0012\u0002\u0011\r\u0011\"\u0001\u0003 !A!Q\u0005\u0001!\u0002\u0013\u0011\t\u0003C\u0005\u0002\u0018\u0002\u0011\r\u0011\"\u0001\u0003(!A!Q\u0006\u0001!\u0002\u0013\u0011I\u0003C\u0005\u0002\u001e\u0002\u0011\r\u0011\"\u0001\u00030!A!Q\u0007\u0001!\u0002\u0013\u0011\t\u0004C\u0005\u0002$\u0002\u0011\r\u0011\"\u0001\u00038!A!Q\b\u0001!\u0002\u0013\u0011I\u0004C\u0005\u0002*\u0002\u0011\r\u0011\"\u0001\u0003@!A!Q\t\u0001!\u0002\u0013\u0011\t\u0005C\u0005\u00020\u0002\u0011\r\u0011\"\u0001\u0003H!A!Q\n\u0001!\u0002\u0013\u0011I\u0005C\u0005\u00026\u0002\u0011\r\u0011\"\u0001\u0003P!A!Q\u000b\u0001!\u0002\u0013\u0011\t\u0006C\u0005\u0002<\u0002\u0011\r\u0011\"\u0001\u0003X!A!Q\f\u0001!\u0002\u0013\u0011I\u0006C\u0005\u0002B\u0002\u0011\r\u0011\"\u0001\u0003`!A!Q\r\u0001!\u0002\u0013\u0011\t\u0007C\u0005\u0002H\u0002\u0011\r\u0011\"\u0001\u0003h!A!Q\u000e\u0001!\u0002\u0013\u0011IGA\u0007GC:|U\u000f^*iCB,\u0017'\u000f\u0006\u0003_A\naa\u001d;sK\u0006l'\"A\u0019\u0002\t\u0005\\7.Y\u0002\u0001+q!4(\u00181dM&dwN];ywz\f\u0019!!\u0003\u0002\u0010\u0005U\u00111DA\u0011\u0003O\u0019\"\u0001A\u001b\u0011\u0007Y:\u0014(D\u0001/\u0013\tAdFA\u0006GC:|U\u000f^*iCB,\u0007C\u0001\u001e<\u0019\u0001!a\u0001\u0010\u0001\t\u0006\u0004i$!A%\u0012\u0005y\"\u0005CA C\u001b\u0005\u0001%\"A!\u0002\u000bM\u001c\u0017\r\\1\n\u0005\r\u0003%a\u0002(pi\"Lgn\u001a\t\u0003\u007f\u0015K!A\u0012!\u0003\u0007\u0005s\u00170A\u0003`S:LG\u000fE\u0002J\u0019>s!A\u000e&\n\u0005-s\u0013a\u0003$b]>+Ho\u00155ba\u0016L!!\u0014(\u0003\t%s\u0017\u000e\u001e\u0006\u0003\u0017:R#!\u000f),\u0003E\u0003\"AU,\u000e\u0003MS!\u0001V+\u0002\u0013Ut7\r[3dW\u0016$'B\u0001,A\u0003)\tgN\\8uCRLwN\\\u0005\u00031N\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0003\u0019a\u0014N\\5u}Q\u00191,a\u000b\u0011;Y\u0002\u0011\bX0cK\"\\g.\u001d;xuv\f\t!a\u0002\u0002\u000e\u0005M\u0011\u0011DA\u0010\u0003K\u0001\"AO/\u0005\ry\u0003AQ1\u0001>\u0005\ty\u0005\u0007\u0005\u0002;A\u00121\u0011\r\u0001CC\u0002u\u0012!aT\u0019\u0011\u0005i\u001aGA\u00023\u0001\t\u000b\u0007QH\u0001\u0002PeA\u0011!H\u001a\u0003\u0007O\u0002!)\u0019A\u001f\u0003\u0005=\u001b\u0004C\u0001\u001ej\t\u0019Q\u0007\u0001\"b\u0001{\t\u0011q\n\u000e\t\u0003u1$a!\u001c\u0001\u0005\u0006\u0004i$AA(6!\tQt\u000e\u0002\u0004q\u0001\u0011\u0015\r!\u0010\u0002\u0003\u001fZ\u0002\"A\u000f:\u0005\rM\u0004AQ1\u0001>\u0005\tyu\u0007\u0005\u0002;k\u00121a\u000f\u0001CC\u0002u\u0012!a\u0014\u001d\u0011\u0005iBHAB=\u0001\t\u000b\u0007QH\u0001\u0002PsA\u0011!h\u001f\u0003\u0007y\u0002!)\u0019A\u001f\u0003\u0007=\u000b\u0004\u0007\u0005\u0002;}\u00121q\u0010\u0001CC\u0002u\u00121aT\u00192!\rQ\u00141\u0001\u0003\b\u0003\u000b\u0001AQ1\u0001>\u0005\ry\u0015G\r\t\u0004u\u0005%AaBA\u0006\u0001\u0011\u0015\r!\u0010\u0002\u0004\u001fF\u001a\u0004c\u0001\u001e\u0002\u0010\u00119\u0011\u0011\u0003\u0001\u0005\u0006\u0004i$aA(2iA\u0019!(!\u0006\u0005\u000f\u0005]\u0001\u0001\"b\u0001{\t\u0019q*M\u001b\u0011\u0007i\nY\u0002B\u0004\u0002\u001e\u0001!)\u0019A\u001f\u0003\u0007=\u000bd\u0007E\u0002;\u0003C!q!a\t\u0001\t\u000b\u0007QHA\u0002Pc]\u00022AOA\u0014\t\u001d\tI\u0003\u0001CC\u0002u\u00121aT\u00199\u0011\u00159%\u00011\u0001I)\rY\u0016q\u0006\u0005\b\u0003c\u0019\u0001\u0019AA\u001a\u0003\u0011q\u0017-\\3\u0011\t\u0005U\u00121\t\b\u0005\u0003o\ty\u0004E\u0002\u0002:\u0001k!!a\u000f\u000b\u0007\u0005u\"'\u0001\u0004=e>|GOP\u0005\u0004\u0003\u0003\u0002\u0015A\u0002)sK\u0012,g-\u0003\u0003\u0002F\u0005\u001d#AB*ue&twMC\u0002\u0002B\u0001#\u0012fWA&\u0003+\ny&!\u001a\u0002l\u0005E\u0014qOA?\u0003\u0007\u000bI)a$\u0002\u0016\u0006m\u0015\u0011UAT\u0003[\u000b\u0019,!/\u0002@\u0006\u0015\u0007bBA'\t\u0001\u0007\u0011qJ\u0001\u0003S:\u0004BANA)s%\u0019\u00111\u000b\u0018\u0003\u000b%sG.\u001a;\t\u000f\u0005]C\u00011\u0001\u0002Z\u0005!q.\u001e;1!\u00111\u00141\f/\n\u0007\u0005ucF\u0001\u0004PkRdW\r\u001e\u0005\b\u0003C\"\u0001\u0019AA2\u0003\u0011yW\u000f^\u0019\u0011\tY\nYf\u0018\u0005\b\u0003O\"\u0001\u0019AA5\u0003\u0011yW\u000f\u001e\u001a\u0011\tY\nYF\u0019\u0005\b\u0003[\"\u0001\u0019AA8\u0003\u0011yW\u000f^\u001a\u0011\tY\nY&\u001a\u0005\b\u0003g\"\u0001\u0019AA;\u0003\u0011yW\u000f\u001e\u001b\u0011\tY\nY\u0006\u001b\u0005\b\u0003s\"\u0001\u0019AA>\u0003\u0011yW\u000f^\u001b\u0011\tY\nYf\u001b\u0005\b\u0003\u007f\"\u0001\u0019AAA\u0003\u0011yW\u000f\u001e\u001c\u0011\tY\nYF\u001c\u0005\b\u0003\u000b#\u0001\u0019AAD\u0003\u0011yW\u000f^\u001c\u0011\tY\nY&\u001d\u0005\b\u0003\u0017#\u0001\u0019AAG\u0003\u0011yW\u000f\u001e\u001d\u0011\tY\nY\u0006\u001e\u0005\b\u0003##\u0001\u0019AAJ\u0003\u0011yW\u000f^\u001d\u0011\tY\nYf\u001e\u0005\b\u0003/#\u0001\u0019AAM\u0003\u0015yW\u000f^\u00191!\u00111\u00141\f>\t\u000f\u0005uE\u00011\u0001\u0002 \u0006)q.\u001e;2cA!a'a\u0017~\u0011\u001d\t\u0019\u000b\u0002a\u0001\u0003K\u000bQa\\;ucI\u0002RANA.\u0003\u0003Aq!!+\u0005\u0001\u0004\tY+A\u0003pkR\f4\u0007E\u00037\u00037\n9\u0001C\u0004\u00020\u0012\u0001\r!!-\u0002\u000b=,H/\r\u001b\u0011\u000bY\nY&!\u0004\t\u000f\u0005UF\u00011\u0001\u00028\u0006)q.\u001e;2kA)a'a\u0017\u0002\u0014!9\u00111\u0018\u0003A\u0002\u0005u\u0016!B8viF2\u0004#\u0002\u001c\u0002\\\u0005e\u0001bBAa\t\u0001\u0007\u00111Y\u0001\u0006_V$\u0018g\u000e\t\u0006m\u0005m\u0013q\u0004\u0005\b\u0003\u000f$\u0001\u0019AAe\u0003\u0015yW\u000f^\u00199!\u00151\u00141LA\u0013\u0003%\u0019wN\\:ueV\u001cG\u000fF\u00026\u0003\u001fDa!!5\u0006\u0001\u0004A\u0015\u0001B5oSR\f\u0001\u0002Z3fa\u000e{\u0007/\u001f\u000b\u00027V\u0011\u0011\u0011\u001c\t\u0006m\u0005m\u00131\u001c\u0016\u00039B\u000bQa\\;ua\u0001*\"!!9\u0011\u000bY\nY&a9+\u0005}\u0003\u0016!B8viF\u0002SCAAu!\u00151\u00141LAvU\t\u0011\u0007+A\u0003pkR\u0014\u0004%\u0006\u0002\u0002rB)a'a\u0017\u0002t*\u0012Q\rU\u0001\u0006_V$8\u0007I\u000b\u0003\u0003s\u0004RANA.\u0003wT#\u0001\u001b)\u0002\u000b=,H\u000f\u000e\u0011\u0016\u0005\t\u0005\u0001#\u0002\u001c\u0002\\\t\r!FA6Q\u0003\u0015yW\u000f^\u001b!+\t\u0011I\u0001E\u00037\u00037\u0012YA\u000b\u0002o!\u0006)q.\u001e;7AU\u0011!\u0011\u0003\t\u0006m\u0005m#1\u0003\u0016\u0003cB\u000bQa\\;uo\u0001*\"A!\u0007\u0011\u000bY\nYFa\u0007+\u0005Q\u0004\u0016!B8vib\u0002SC\u0001B\u0011!\u00151\u00141\fB\u0012U\t9\b+A\u0003pkRL\u0004%\u0006\u0002\u0003*A)a'a\u0017\u0003,)\u0012!\u0010U\u0001\u0007_V$\u0018\u0007\r\u0011\u0016\u0005\tE\u0002#\u0002\u001c\u0002\\\tM\"FA?Q\u0003\u0019yW\u000f^\u00192AU\u0011!\u0011\b\t\u0006m\u0005m#1\b\u0016\u0004\u0003\u0003\u0001\u0016AB8viF\u0012\u0004%\u0006\u0002\u0003BA)a'a\u0017\u0003D)\u001a\u0011q\u0001)\u0002\r=,H/M\u001a!+\t\u0011I\u0005E\u00037\u00037\u0012YEK\u0002\u0002\u000eA\u000baa\\;ucQ\u0002SC\u0001B)!\u00151\u00141\fB*U\r\t\u0019\u0002U\u0001\u0007_V$\u0018'\u000e\u0011\u0016\u0005\te\u0003#\u0002\u001c\u0002\\\tm#fAA\r!\u00061q.\u001e;2m\u0001*\"A!\u0019\u0011\u000bY\nYFa\u0019+\u0007\u0005}\u0001+\u0001\u0004pkR\ft\u0007I\u000b\u0003\u0005S\u0002RANA.\u0005WR3!!\nQ\u0003\u0019yW\u000f^\u00199A\u0001")
public class FanOutShape19<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18>
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

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape19<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18>(init2);
    }

    @Override
    public FanOutShape19<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18> deepCopy() {
        return (FanOutShape19)super.deepCopy();
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

    public FanOutShape19(FanOutShape.Init<I> _init) {
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
    }

    public FanOutShape19(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape19(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7, Outlet<O8> out8, Outlet<O9> out9, Outlet<O10> out10, Outlet<O11> out11, Outlet<O12> out12, Outlet<O13> out13, Outlet<O14> out14, Outlet<O15> out15, Outlet<O16> out16, Outlet<O17> out17, Outlet<O18> out18) {
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
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet19).$colon$colon(outlet18).$colon$colon(outlet17).$colon$colon(outlet16).$colon$colon(outlet15).$colon$colon(outlet14).$colon$colon(outlet13).$colon$colon(outlet12).$colon$colon(outlet11).$colon$colon(outlet10).$colon$colon(outlet9).$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

