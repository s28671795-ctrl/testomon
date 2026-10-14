/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t=a\u0001B\u0013'\u0001-B\u0001b\u0010\u0001\u0003\u0002\u0003\u0006I\u0001\u0011\u0005\u0006#\u0002!\tA\u0015\u0005\u0007#\u0002!\t!!\u0002\t\rE\u0003A\u0011AA\u0011\u0011\u001d\tY\t\u0001C)\u0003\u001bCq!a%\u0001\t\u0003\n)\nC\u0005\u00020\u0001\u0011\r\u0011\"\u0001\u0002\u0018\"A\u0011Q\u0014\u0001!\u0002\u0013\tI\nC\u0005\u0002:\u0001\u0011\r\u0011\"\u0001\u0002 \"A\u0011Q\u0015\u0001!\u0002\u0013\t\t\u000bC\u0005\u0002@\u0001\u0011\r\u0011\"\u0001\u0002(\"A\u0011Q\u0016\u0001!\u0002\u0013\tI\u000bC\u0005\u0002F\u0001\u0011\r\u0011\"\u0001\u00020\"A\u0011Q\u0017\u0001!\u0002\u0013\t\t\fC\u0005\u0002L\u0001\u0011\r\u0011\"\u0001\u00028\"A\u0011Q\u0018\u0001!\u0002\u0013\tI\fC\u0005\u0002R\u0001\u0011\r\u0011\"\u0001\u0002@\"A\u0011Q\u0019\u0001!\u0002\u0013\t\t\rC\u0005\u0002X\u0001\u0011\r\u0011\"\u0001\u0002H\"A\u0011Q\u001a\u0001!\u0002\u0013\tI\rC\u0005\u0002^\u0001\u0011\r\u0011\"\u0001\u0002P\"A\u0011Q\u001b\u0001!\u0002\u0013\t\t\u000eC\u0005\u0002d\u0001\u0011\r\u0011\"\u0001\u0002X\"A\u0011Q\u001c\u0001!\u0002\u0013\tI\u000eC\u0005\u0002j\u0001\u0011\r\u0011\"\u0001\u0002`\"A\u0011Q\u001d\u0001!\u0002\u0013\t\t\u000fC\u0005\u0002p\u0001\u0011\r\u0011\"\u0001\u0002h\"A\u0011Q\u001e\u0001!\u0002\u0013\tI\u000fC\u0005\u0002v\u0001\u0011\r\u0011\"\u0001\u0002p\"A\u0011Q\u001f\u0001!\u0002\u0013\t\t\u0010C\u0005\u0002|\u0001\u0011\r\u0011\"\u0001\u0002x\"A\u0011Q \u0001!\u0002\u0013\tI\u0010C\u0005\u0002\u0002\u0002\u0011\r\u0011\"\u0001\u0002\u0000\"A!Q\u0001\u0001!\u0002\u0013\u0011\t\u0001C\u0005\u0002\b\u0002\u0011\r\u0011\"\u0001\u0003\b!A!Q\u0002\u0001!\u0002\u0013\u0011IAA\u0007GC:|U\u000f^*iCB,\u0017'\u000e\u0006\u0003O!\naa\u001d;sK\u0006l'\"A\u0015\u0002\t\u0005\\7.Y\u0002\u0001+Ea3'\u0016-\\=\u0006$wM[7qgZLHp`\n\u0003\u00015\u00022AL\u00182\u001b\u00051\u0013B\u0001\u0019'\u0005-1\u0015M\\(viNC\u0017\r]3\u0011\u0005I\u001aD\u0002\u0001\u0003\u0007i\u0001A)\u0019A\u001b\u0003\u0003%\u000b\"A\u000e\u001f\u0011\u0005]RT\"\u0001\u001d\u000b\u0003e\nQa]2bY\u0006L!a\u000f\u001d\u0003\u000f9{G\u000f[5oOB\u0011q'P\u0005\u0003}a\u00121!\u00118z\u0003\u0015y\u0016N\\5u!\r\tEi\u0012\b\u0003]\tK!a\u0011\u0014\u0002\u0017\u0019\u000bgnT;u'\"\f\u0007/Z\u0005\u0003\u000b\u001a\u0013A!\u00138ji*\u00111I\n\u0016\u0003c![\u0013!\u0013\t\u0003\u0015>k\u0011a\u0013\u0006\u0003\u00196\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u00059C\u0014AC1o]>$\u0018\r^5p]&\u0011\u0001k\u0013\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017A\u0002\u001fj]&$h\bF\u0002T\u0003\u0007\u0001\"C\f\u00012)^SV\fY2gS2|'/\u001e=|}B\u0011!'\u0016\u0003\u0007-\u0002!)\u0019A\u001b\u0003\u0005=\u0003\u0004C\u0001\u001aY\t\u0019I\u0006\u0001\"b\u0001k\t\u0011q*\r\t\u0003em#a\u0001\u0018\u0001\u0005\u0006\u0004)$AA(3!\t\u0011d\f\u0002\u0004`\u0001\u0011\u0015\r!\u000e\u0002\u0003\u001fN\u0002\"AM1\u0005\r\t\u0004AQ1\u00016\u0005\tyE\u0007\u0005\u00023I\u00121Q\r\u0001CC\u0002U\u0012!aT\u001b\u0011\u0005I:GA\u00025\u0001\t\u000b\u0007QG\u0001\u0002PmA\u0011!G\u001b\u0003\u0007W\u0002!)\u0019A\u001b\u0003\u0005=;\u0004C\u0001\u001an\t\u0019q\u0007\u0001\"b\u0001k\t\u0011q\n\u000f\t\u0003eA$a!\u001d\u0001\u0005\u0006\u0004)$AA(:!\t\u00114\u000f\u0002\u0004u\u0001\u0011\u0015\r!\u000e\u0002\u0004\u001fF\u0002\u0004C\u0001\u001aw\t\u00199\b\u0001\"b\u0001k\t\u0019q*M\u0019\u0011\u0005IJHA\u0002>\u0001\t\u000b\u0007QGA\u0002PcI\u0002\"A\r?\u0005\ru\u0004AQ1\u00016\u0005\ry\u0015g\r\t\u0003e}$q!!\u0001\u0001\t\u000b\u0007QGA\u0002PcQBQa\u0010\u0002A\u0002\u0001#2aUA\u0004\u0011\u001d\tIa\u0001a\u0001\u0003\u0017\tAA\\1nKB!\u0011QBA\u000e\u001d\u0011\ty!a\u0006\u0011\u0007\u0005E\u0001(\u0004\u0002\u0002\u0014)\u0019\u0011Q\u0003\u0016\u0002\rq\u0012xn\u001c;?\u0013\r\tI\u0002O\u0001\u0007!J,G-\u001a4\n\t\u0005u\u0011q\u0004\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005e\u0001\bF\u0011T\u0003G\ti#a\u000e\u0002>\u0005\r\u0013\u0011JA(\u0003+\nY&!\u0019\u0002h\u00055\u00141OA=\u0003\u007f\n)\tC\u0004\u0002&\u0011\u0001\r!a\n\u0002\u0005%t\u0007\u0003\u0002\u0018\u0002*EJ1!a\u000b'\u0005\u0015Ie\u000e\\3u\u0011\u001d\ty\u0003\u0002a\u0001\u0003c\tAa\\;uaA!a&a\rU\u0013\r\t)D\n\u0002\u0007\u001fV$H.\u001a;\t\u000f\u0005eB\u00011\u0001\u0002<\u0005!q.\u001e;2!\u0011q\u00131G,\t\u000f\u0005}B\u00011\u0001\u0002B\u0005!q.\u001e;3!\u0011q\u00131\u0007.\t\u000f\u0005\u0015C\u00011\u0001\u0002H\u0005!q.\u001e;4!\u0011q\u00131G/\t\u000f\u0005-C\u00011\u0001\u0002N\u0005!q.\u001e;5!\u0011q\u00131\u00071\t\u000f\u0005EC\u00011\u0001\u0002T\u0005!q.\u001e;6!\u0011q\u00131G2\t\u000f\u0005]C\u00011\u0001\u0002Z\u0005!q.\u001e;7!\u0011q\u00131\u00074\t\u000f\u0005uC\u00011\u0001\u0002`\u0005!q.\u001e;8!\u0011q\u00131G5\t\u000f\u0005\rD\u00011\u0001\u0002f\u0005!q.\u001e;9!\u0011q\u00131\u00077\t\u000f\u0005%D\u00011\u0001\u0002l\u0005!q.\u001e;:!\u0011q\u00131G8\t\u000f\u0005=D\u00011\u0001\u0002r\u0005)q.\u001e;2aA!a&a\rs\u0011\u001d\t)\b\u0002a\u0001\u0003o\nQa\\;ucE\u0002BALA\u001ak\"9\u00111\u0010\u0003A\u0002\u0005u\u0014!B8viF\u0012\u0004\u0003\u0002\u0018\u00024aDq!!!\u0005\u0001\u0004\t\u0019)A\u0003pkR\f4\u0007\u0005\u0003/\u0003gY\bbBAD\t\u0001\u0007\u0011\u0011R\u0001\u0006_V$\u0018\u0007\u000e\t\u0005]\u0005Mb0A\u0005d_:\u001cHO];diR\u0019Q&a$\t\r\u0005EU\u00011\u0001A\u0003\u0011Ig.\u001b;\u0002\u0011\u0011,W\r]\"paf$\u0012aU\u000b\u0003\u00033\u0003RALA\u001a\u00037S#\u0001\u0016%\u0002\u000b=,H\u000f\r\u0011\u0016\u0005\u0005\u0005\u0006#\u0002\u0018\u00024\u0005\r&FA,I\u0003\u0015yW\u000f^\u0019!+\t\tI\u000bE\u0003/\u0003g\tYK\u000b\u0002[\u0011\u0006)q.\u001e;3AU\u0011\u0011\u0011\u0017\t\u0006]\u0005M\u00121\u0017\u0016\u0003;\"\u000bQa\\;ug\u0001*\"!!/\u0011\u000b9\n\u0019$a/+\u0005\u0001D\u0015!B8viR\u0002SCAAa!\u0015q\u00131GAbU\t\u0019\u0007*A\u0003pkR,\u0004%\u0006\u0002\u0002JB)a&a\r\u0002L*\u0012a\rS\u0001\u0006_V$h\u0007I\u000b\u0003\u0003#\u0004RALA\u001a\u0003'T#!\u001b%\u0002\u000b=,Ho\u000e\u0011\u0016\u0005\u0005e\u0007#\u0002\u0018\u00024\u0005m'F\u00017I\u0003\u0015yW\u000f\u001e\u001d!+\t\t\t\u000fE\u0003/\u0003g\t\u0019O\u000b\u0002p\u0011\u0006)q.\u001e;:AU\u0011\u0011\u0011\u001e\t\u0006]\u0005M\u00121\u001e\u0016\u0003e\"\u000baa\\;ucA\u0002SCAAy!\u0015q\u00131GAzU\t)\b*\u0001\u0004pkR\f\u0014\u0007I\u000b\u0003\u0003s\u0004RALA\u001a\u0003wT#\u0001\u001f%\u0002\r=,H/\r\u001a!+\t\u0011\t\u0001E\u0003/\u0003g\u0011\u0019A\u000b\u0002|\u0011\u00061q.\u001e;2g\u0001*\"A!\u0003\u0011\u000b9\n\u0019Da\u0003+\u0005yD\u0015AB8viF\"\u0004\u0005")
public class FanOutShape15<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14>
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

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape15<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14>(init2);
    }

    @Override
    public FanOutShape15<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14> deepCopy() {
        return (FanOutShape15)super.deepCopy();
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

    public FanOutShape15(FanOutShape.Init<I> _init) {
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
    }

    public FanOutShape15(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape15(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7, Outlet<O8> out8, Outlet<O9> out9, Outlet<O10> out10, Outlet<O11> out11, Outlet<O12> out12, Outlet<O13> out13, Outlet<O14> out14) {
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
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet15).$colon$colon(outlet14).$colon$colon(outlet13).$colon$colon(outlet12).$colon$colon(outlet11).$colon$colon(outlet10).$colon$colon(outlet9).$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

