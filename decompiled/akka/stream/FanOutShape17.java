/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t}b\u0001B\u0015+\u0001=B\u0001b\u0011\u0001\u0003\u0002\u0003\u0006I\u0001\u0012\u0005\u0006+\u0002!\tA\u0016\u0005\u0007+\u0002!\t!!\u0007\t\rU\u0003A\u0011AA\u001b\u0011\u001d\tY\u000b\u0001C)\u0003[Cq!a-\u0001\t\u0003\n)\fC\u0005\u0002D\u0001\u0011\r\u0011\"\u0001\u00028\"A\u0011Q\u0018\u0001!\u0002\u0013\tI\fC\u0005\u0002N\u0001\u0011\r\u0011\"\u0001\u0002@\"A\u0011Q\u0019\u0001!\u0002\u0013\t\t\rC\u0005\u0002T\u0001\u0011\r\u0011\"\u0001\u0002H\"A\u0011Q\u001a\u0001!\u0002\u0013\tI\rC\u0005\u0002Z\u0001\u0011\r\u0011\"\u0001\u0002P\"A\u0011Q\u001b\u0001!\u0002\u0013\t\t\u000eC\u0005\u0002`\u0001\u0011\r\u0011\"\u0001\u0002X\"A\u0011Q\u001c\u0001!\u0002\u0013\tI\u000eC\u0005\u0002f\u0001\u0011\r\u0011\"\u0001\u0002`\"A\u0011Q\u001d\u0001!\u0002\u0013\t\t\u000fC\u0005\u0002l\u0001\u0011\r\u0011\"\u0001\u0002h\"A\u0011Q\u001e\u0001!\u0002\u0013\tI\u000fC\u0005\u0002r\u0001\u0011\r\u0011\"\u0001\u0002p\"A\u0011Q\u001f\u0001!\u0002\u0013\t\t\u0010C\u0005\u0002x\u0001\u0011\r\u0011\"\u0001\u0002x\"A\u0011Q \u0001!\u0002\u0013\tI\u0010C\u0005\u0002~\u0001\u0011\r\u0011\"\u0001\u0002\u0000\"A!Q\u0001\u0001!\u0002\u0013\u0011\t\u0001C\u0005\u0002\u0004\u0002\u0011\r\u0011\"\u0001\u0003\b!A!Q\u0002\u0001!\u0002\u0013\u0011I\u0001C\u0005\u0002\n\u0002\u0011\r\u0011\"\u0001\u0003\u0010!A!Q\u0003\u0001!\u0002\u0013\u0011\t\u0002C\u0005\u0002\u0010\u0002\u0011\r\u0011\"\u0001\u0003\u0018!A!Q\u0004\u0001!\u0002\u0013\u0011I\u0002C\u0005\u0002\u0016\u0002\u0011\r\u0011\"\u0001\u0003 !A!Q\u0005\u0001!\u0002\u0013\u0011\t\u0003C\u0005\u0002\u001c\u0002\u0011\r\u0011\"\u0001\u0003(!A!Q\u0006\u0001!\u0002\u0013\u0011I\u0003C\u0005\u0002\"\u0002\u0011\r\u0011\"\u0001\u00030!A!Q\u0007\u0001!\u0002\u0013\u0011\t\u0004C\u0005\u0002(\u0002\u0011\r\u0011\"\u0001\u00038!A!Q\b\u0001!\u0002\u0013\u0011IDA\u0007GC:|U\u000f^*iCB,\u0017g\u000e\u0006\u0003W1\naa\u001d;sK\u0006l'\"A\u0017\u0002\t\u0005\\7.Y\u0002\u0001+]\u0001t'\u0017/`E\u0016D7N\\9uojl\u0018\u0011AA\u0004\u0003\u001b\t\u0019b\u0005\u0002\u0001cA\u0019!gM\u001b\u000e\u0003)J!\u0001\u000e\u0016\u0003\u0017\u0019\u000bgnT;u'\"\f\u0007/\u001a\t\u0003m]b\u0001\u0001\u0002\u00049\u0001!\u0015\r!\u000f\u0002\u0002\u0013F\u0011!\b\u0011\t\u0003wyj\u0011\u0001\u0010\u0006\u0002{\u0005)1oY1mC&\u0011q\b\u0010\u0002\b\u001d>$\b.\u001b8h!\tY\u0014)\u0003\u0002Cy\t\u0019\u0011I\\=\u0002\u000b}Kg.\u001b;\u0011\u0007\u0015C5J\u0004\u00023\r&\u0011qIK\u0001\f\r\u0006tw*\u001e;TQ\u0006\u0004X-\u0003\u0002J\u0015\n!\u0011J\\5u\u0015\t9%F\u000b\u00026\u0019.\nQ\n\u0005\u0002O'6\tqJ\u0003\u0002Q#\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003%r\n!\"\u00198o_R\fG/[8o\u0013\t!vJA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fa\u0001P5oSRtDcA,\u0002\u0018A9\"\u0007A\u001bY7z\u000bGm\u001a6naN4\u0018\u0010`@\u0002\u0006\u0005-\u0011\u0011\u0003\t\u0003me#aA\u0017\u0001\u0005\u0006\u0004I$AA(1!\t1D\f\u0002\u0004^\u0001\u0011\u0015\r!\u000f\u0002\u0003\u001fF\u0002\"AN0\u0005\r\u0001\u0004AQ1\u0001:\u0005\ty%\u0007\u0005\u00027E\u001211\r\u0001CC\u0002e\u0012!aT\u001a\u0011\u0005Y*GA\u00024\u0001\t\u000b\u0007\u0011H\u0001\u0002PiA\u0011a\u0007\u001b\u0003\u0007S\u0002!)\u0019A\u001d\u0003\u0005=+\u0004C\u0001\u001cl\t\u0019a\u0007\u0001\"b\u0001s\t\u0011qJ\u000e\t\u0003m9$aa\u001c\u0001\u0005\u0006\u0004I$AA(8!\t1\u0014\u000f\u0002\u0004s\u0001\u0011\u0015\r!\u000f\u0002\u0003\u001fb\u0002\"A\u000e;\u0005\rU\u0004AQ1\u0001:\u0005\ty\u0015\b\u0005\u00027o\u00121\u0001\u0010\u0001CC\u0002e\u00121aT\u00191!\t1$\u0010\u0002\u0004|\u0001\u0011\u0015\r!\u000f\u0002\u0004\u001fF\n\u0004C\u0001\u001c~\t\u0019q\b\u0001\"b\u0001s\t\u0019q*\r\u001a\u0011\u0007Y\n\t\u0001B\u0004\u0002\u0004\u0001!)\u0019A\u001d\u0003\u0007=\u000b4\u0007E\u00027\u0003\u000f!q!!\u0003\u0001\t\u000b\u0007\u0011HA\u0002PcQ\u00022ANA\u0007\t\u001d\ty\u0001\u0001CC\u0002e\u00121aT\u00196!\r1\u00141\u0003\u0003\b\u0003+\u0001AQ1\u0001:\u0005\ry\u0015G\u000e\u0005\u0006\u0007\n\u0001\r\u0001\u0012\u000b\u0004/\u0006m\u0001bBA\u000f\u0007\u0001\u0007\u0011qD\u0001\u0005]\u0006lW\r\u0005\u0003\u0002\"\u0005=b\u0002BA\u0012\u0003W\u00012!!\n=\u001b\t\t9CC\u0002\u0002*9\na\u0001\u0010:p_Rt\u0014bAA\u0017y\u00051\u0001K]3eK\u001aLA!!\r\u00024\t11\u000b\u001e:j]\u001eT1!!\f=)\u0015:\u0016qGA!\u0003\u0017\n\t&a\u0016\u0002^\u0005\r\u0014\u0011NA8\u0003k\nY(!!\u0002\b\u00065\u00151SAM\u0003?\u000b)\u000bC\u0004\u0002:\u0011\u0001\r!a\u000f\u0002\u0005%t\u0007\u0003\u0002\u001a\u0002>UJ1!a\u0010+\u0005\u0015Ie\u000e\\3u\u0011\u001d\t\u0019\u0005\u0002a\u0001\u0003\u000b\nAa\\;uaA!!'a\u0012Y\u0013\r\tIE\u000b\u0002\u0007\u001fV$H.\u001a;\t\u000f\u00055C\u00011\u0001\u0002P\u0005!q.\u001e;2!\u0011\u0011\u0014qI.\t\u000f\u0005MC\u00011\u0001\u0002V\u0005!q.\u001e;3!\u0011\u0011\u0014q\t0\t\u000f\u0005eC\u00011\u0001\u0002\\\u0005!q.\u001e;4!\u0011\u0011\u0014qI1\t\u000f\u0005}C\u00011\u0001\u0002b\u0005!q.\u001e;5!\u0011\u0011\u0014q\t3\t\u000f\u0005\u0015D\u00011\u0001\u0002h\u0005!q.\u001e;6!\u0011\u0011\u0014qI4\t\u000f\u0005-D\u00011\u0001\u0002n\u0005!q.\u001e;7!\u0011\u0011\u0014q\t6\t\u000f\u0005ED\u00011\u0001\u0002t\u0005!q.\u001e;8!\u0011\u0011\u0014qI7\t\u000f\u0005]D\u00011\u0001\u0002z\u0005!q.\u001e;9!\u0011\u0011\u0014q\t9\t\u000f\u0005uD\u00011\u0001\u0002\u0000\u0005!q.\u001e;:!\u0011\u0011\u0014qI:\t\u000f\u0005\rE\u00011\u0001\u0002\u0006\u0006)q.\u001e;2aA!!'a\u0012w\u0011\u001d\tI\t\u0002a\u0001\u0003\u0017\u000bQa\\;ucE\u0002BAMA$s\"9\u0011q\u0012\u0003A\u0002\u0005E\u0015!B8viF\u0012\u0004\u0003\u0002\u001a\u0002HqDq!!&\u0005\u0001\u0004\t9*A\u0003pkR\f4\u0007\u0005\u00033\u0003\u000fz\bbBAN\t\u0001\u0007\u0011QT\u0001\u0006_V$\u0018\u0007\u000e\t\u0006e\u0005\u001d\u0013Q\u0001\u0005\b\u0003C#\u0001\u0019AAR\u0003\u0015yW\u000f^\u00196!\u0015\u0011\u0014qIA\u0006\u0011\u001d\t9\u000b\u0002a\u0001\u0003S\u000bQa\\;ucY\u0002RAMA$\u0003#\t\u0011bY8ogR\u0014Xo\u0019;\u0015\u0007E\ny\u000b\u0003\u0004\u00022\u0016\u0001\r\u0001R\u0001\u0005S:LG/\u0001\u0005eK\u0016\u00048i\u001c9z)\u00059VCAA]!\u0015\u0011\u0014qIA^U\tAF*A\u0003pkR\u0004\u0004%\u0006\u0002\u0002BB)!'a\u0012\u0002D*\u00121\fT\u0001\u0006_V$\u0018\u0007I\u000b\u0003\u0003\u0013\u0004RAMA$\u0003\u0017T#A\u0018'\u0002\u000b=,HO\r\u0011\u0016\u0005\u0005E\u0007#\u0002\u001a\u0002H\u0005M'FA1M\u0003\u0015yW\u000f^\u001a!+\t\tI\u000eE\u00033\u0003\u000f\nYN\u000b\u0002e\u0019\u0006)q.\u001e;5AU\u0011\u0011\u0011\u001d\t\u0006e\u0005\u001d\u00131\u001d\u0016\u0003O2\u000bQa\\;uk\u0001*\"!!;\u0011\u000bI\n9%a;+\u0005)d\u0015!B8viZ\u0002SCAAy!\u0015\u0011\u0014qIAzU\tiG*A\u0003pkR<\u0004%\u0006\u0002\u0002zB)!'a\u0012\u0002|*\u0012\u0001\u000fT\u0001\u0006_V$\b\bI\u000b\u0003\u0005\u0003\u0001RAMA$\u0005\u0007Q#a\u001d'\u0002\u000b=,H/\u000f\u0011\u0016\u0005\t%\u0001#\u0002\u001a\u0002H\t-!F\u0001<M\u0003\u0019yW\u000f^\u00191AU\u0011!\u0011\u0003\t\u0006e\u0005\u001d#1\u0003\u0016\u0003s2\u000baa\\;ucE\u0002SC\u0001B\r!\u0015\u0011\u0014q\tB\u000eU\taH*\u0001\u0004pkR\f$\u0007I\u000b\u0003\u0005C\u0001RAMA$\u0005GQ#a '\u0002\r=,H/M\u001a!+\t\u0011I\u0003E\u00033\u0003\u000f\u0012YCK\u0002\u0002\u00061\u000baa\\;ucQ\u0002SC\u0001B\u0019!\u0015\u0011\u0014q\tB\u001aU\r\tY\u0001T\u0001\u0007_V$\u0018'\u000e\u0011\u0016\u0005\te\u0002#\u0002\u001a\u0002H\tm\"fAA\t\u0019\u00061q.\u001e;2m\u0001\u0002")
public class FanOutShape17<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16>
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

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape17<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16>(init2);
    }

    @Override
    public FanOutShape17<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16> deepCopy() {
        return (FanOutShape17)super.deepCopy();
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

    public FanOutShape17(FanOutShape.Init<I> _init) {
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
    }

    public FanOutShape17(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape17(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7, Outlet<O8> out8, Outlet<O9> out9, Outlet<O10> out10, Outlet<O11> out11, Outlet<O12> out12, Outlet<O13> out13, Outlet<O14> out14, Outlet<O15> out15, Outlet<O16> out16) {
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
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet17).$colon$colon(outlet16).$colon$colon(outlet15).$colon$colon(outlet14).$colon$colon(outlet13).$colon$colon(outlet12).$colon$colon(outlet11).$colon$colon(outlet10).$colon$colon(outlet9).$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

