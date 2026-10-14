/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t]c\u0001B\u0016-\u0001EB\u0001\"\u0012\u0001\u0003\u0002\u0003\u0006IA\u0012\u0005\u0006/\u0002!\t\u0001\u0017\u0005\u0007/\u0002!\t!a\t\t\r]\u0003A\u0011AA \u0011\u001d\tY\f\u0001C)\u0003{Cq!a1\u0001\t\u0003\n)\rC\u0005\u0002N\u0001\u0011\r\u0011\"\u0001\u0002H\"A\u0011Q\u001a\u0001!\u0002\u0013\tI\rC\u0005\u0002X\u0001\u0011\r\u0011\"\u0001\u0002P\"A\u0011Q\u001b\u0001!\u0002\u0013\t\t\u000eC\u0005\u0002^\u0001\u0011\r\u0011\"\u0001\u0002X\"A\u0011Q\u001c\u0001!\u0002\u0013\tI\u000eC\u0005\u0002d\u0001\u0011\r\u0011\"\u0001\u0002`\"A\u0011Q\u001d\u0001!\u0002\u0013\t\t\u000fC\u0005\u0002j\u0001\u0011\r\u0011\"\u0001\u0002h\"A\u0011Q\u001e\u0001!\u0002\u0013\tI\u000fC\u0005\u0002p\u0001\u0011\r\u0011\"\u0001\u0002p\"A\u0011Q\u001f\u0001!\u0002\u0013\t\t\u0010C\u0005\u0002v\u0001\u0011\r\u0011\"\u0001\u0002x\"A\u0011Q \u0001!\u0002\u0013\tI\u0010C\u0005\u0002|\u0001\u0011\r\u0011\"\u0001\u0002\u0000\"A!Q\u0001\u0001!\u0002\u0013\u0011\t\u0001C\u0005\u0002\u0002\u0002\u0011\r\u0011\"\u0001\u0003\b!A!Q\u0002\u0001!\u0002\u0013\u0011I\u0001C\u0005\u0002\b\u0002\u0011\r\u0011\"\u0001\u0003\u0010!A!Q\u0003\u0001!\u0002\u0013\u0011\t\u0002C\u0005\u0002\u000e\u0002\u0011\r\u0011\"\u0001\u0003\u0018!A!Q\u0004\u0001!\u0002\u0013\u0011I\u0002C\u0005\u0002\u0014\u0002\u0011\r\u0011\"\u0001\u0003 !A!Q\u0005\u0001!\u0002\u0013\u0011\t\u0003C\u0005\u0002\u001a\u0002\u0011\r\u0011\"\u0001\u0003(!A!Q\u0006\u0001!\u0002\u0013\u0011I\u0003C\u0005\u0002 \u0002\u0011\r\u0011\"\u0001\u00030!A!Q\u0007\u0001!\u0002\u0013\u0011\t\u0004C\u0005\u0002&\u0002\u0011\r\u0011\"\u0001\u00038!A!Q\b\u0001!\u0002\u0013\u0011I\u0004C\u0005\u0002,\u0002\u0011\r\u0011\"\u0001\u0003@!A!Q\t\u0001!\u0002\u0013\u0011\t\u0005C\u0005\u00022\u0002\u0011\r\u0011\"\u0001\u0003H!A!Q\n\u0001!\u0002\u0013\u0011I\u0005C\u0005\u00028\u0002\u0011\r\u0011\"\u0001\u0003P!A!Q\u000b\u0001!\u0002\u0013\u0011\tFA\u0007GC:|U\u000f^*iCB,\u0017\u0007\u000f\u0006\u0003[9\naa\u001d;sK\u0006l'\"A\u0018\u0002\t\u0005\\7.Y\u0002\u0001+e\u0011\u0014h\u00170bI\u001eTW\u000e]:wsr|\u0018QAA\u0006\u0003#\t9\"!\b\u0014\u0005\u0001\u0019\u0004c\u0001\u001b6o5\tA&\u0003\u00027Y\tYa)\u00198PkR\u001c\u0006.\u00199f!\tA\u0014\b\u0004\u0001\u0005\ri\u0002\u0001R1\u0001<\u0005\u0005I\u0015C\u0001\u001fC!\ti\u0004)D\u0001?\u0015\u0005y\u0014!B:dC2\f\u0017BA!?\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!P\"\n\u0005\u0011s$aA!os\u0006)q,\u001b8jiB\u0019qIS'\u000f\u0005QB\u0015BA%-\u0003-1\u0015M\\(viNC\u0017\r]3\n\u0005-c%\u0001B%oSRT!!\u0013\u0017+\u0005]r5&A(\u0011\u0005A+V\"A)\u000b\u0005I\u001b\u0016!C;oG\",7m[3e\u0015\t!f(\u0001\u0006b]:|G/\u0019;j_:L!AV)\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\u0004=S:LGO\u0010\u000b\u00043\u0006\u0005\u0002C\u0007\u001b\u0001oik\u0006m\u00194jY>\u0014X\u000f_>\u007f\u0003\u0007\tI!a\u0004\u0002\u0016\u0005m\u0001C\u0001\u001d\\\t\u0019a\u0006\u0001\"b\u0001w\t\u0011q\n\r\t\u0003qy#aa\u0018\u0001\u0005\u0006\u0004Y$AA(2!\tA\u0014\r\u0002\u0004c\u0001\u0011\u0015\ra\u000f\u0002\u0003\u001fJ\u0002\"\u0001\u000f3\u0005\r\u0015\u0004AQ1\u0001<\u0005\ty5\u0007\u0005\u00029O\u00121\u0001\u000e\u0001CC\u0002m\u0012!a\u0014\u001b\u0011\u0005aRGAB6\u0001\t\u000b\u00071H\u0001\u0002PkA\u0011\u0001(\u001c\u0003\u0007]\u0002!)\u0019A\u001e\u0003\u0005=3\u0004C\u0001\u001dq\t\u0019\t\b\u0001\"b\u0001w\t\u0011qj\u000e\t\u0003qM$a\u0001\u001e\u0001\u0005\u0006\u0004Y$AA(9!\tAd\u000f\u0002\u0004x\u0001\u0011\u0015\ra\u000f\u0002\u0003\u001ff\u0002\"\u0001O=\u0005\ri\u0004AQ1\u0001<\u0005\ry\u0015\u0007\r\t\u0003qq$a! \u0001\u0005\u0006\u0004Y$aA(2cA\u0011\u0001h \u0003\b\u0003\u0003\u0001AQ1\u0001<\u0005\ry\u0015G\r\t\u0004q\u0005\u0015AaBA\u0004\u0001\u0011\u0015\ra\u000f\u0002\u0004\u001fF\u001a\u0004c\u0001\u001d\u0002\f\u00119\u0011Q\u0002\u0001\u0005\u0006\u0004Y$aA(2iA\u0019\u0001(!\u0005\u0005\u000f\u0005M\u0001\u0001\"b\u0001w\t\u0019q*M\u001b\u0011\u0007a\n9\u0002B\u0004\u0002\u001a\u0001!)\u0019A\u001e\u0003\u0007=\u000bd\u0007E\u00029\u0003;!q!a\b\u0001\t\u000b\u00071HA\u0002Pc]BQ!\u0012\u0002A\u0002\u0019#2!WA\u0013\u0011\u001d\t9c\u0001a\u0001\u0003S\tAA\\1nKB!\u00111FA\u001d\u001d\u0011\ti#!\u000e\u0011\u0007\u0005=b(\u0004\u0002\u00022)\u0019\u00111\u0007\u0019\u0002\rq\u0012xn\u001c;?\u0013\r\t9DP\u0001\u0007!J,G-\u001a4\n\t\u0005m\u0012Q\b\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005]b\bF\u0014Z\u0003\u0003\nY%!\u0016\u0002\\\u0005\u0005\u0014qMA7\u0003g\nI(a \u0002\u0006\u0006-\u0015\u0011SAL\u0003;\u000b\u0019+!+\u00020\u0006U\u0006bBA\"\t\u0001\u0007\u0011QI\u0001\u0003S:\u0004B\u0001NA$o%\u0019\u0011\u0011\n\u0017\u0003\u000b%sG.\u001a;\t\u000f\u00055C\u00011\u0001\u0002P\u0005!q.\u001e;1!\u0011!\u0014\u0011\u000b.\n\u0007\u0005MCF\u0001\u0004PkRdW\r\u001e\u0005\b\u0003/\"\u0001\u0019AA-\u0003\u0011yW\u000f^\u0019\u0011\tQ\n\t&\u0018\u0005\b\u0003;\"\u0001\u0019AA0\u0003\u0011yW\u000f\u001e\u001a\u0011\tQ\n\t\u0006\u0019\u0005\b\u0003G\"\u0001\u0019AA3\u0003\u0011yW\u000f^\u001a\u0011\tQ\n\tf\u0019\u0005\b\u0003S\"\u0001\u0019AA6\u0003\u0011yW\u000f\u001e\u001b\u0011\tQ\n\tF\u001a\u0005\b\u0003_\"\u0001\u0019AA9\u0003\u0011yW\u000f^\u001b\u0011\tQ\n\t&\u001b\u0005\b\u0003k\"\u0001\u0019AA<\u0003\u0011yW\u000f\u001e\u001c\u0011\tQ\n\t\u0006\u001c\u0005\b\u0003w\"\u0001\u0019AA?\u0003\u0011yW\u000f^\u001c\u0011\tQ\n\tf\u001c\u0005\b\u0003\u0003#\u0001\u0019AAB\u0003\u0011yW\u000f\u001e\u001d\u0011\tQ\n\tF\u001d\u0005\b\u0003\u000f#\u0001\u0019AAE\u0003\u0011yW\u000f^\u001d\u0011\tQ\n\t&\u001e\u0005\b\u0003\u001b#\u0001\u0019AAH\u0003\u0015yW\u000f^\u00191!\u0011!\u0014\u0011\u000b=\t\u000f\u0005ME\u00011\u0001\u0002\u0016\u0006)q.\u001e;2cA!A'!\u0015|\u0011\u001d\tI\n\u0002a\u0001\u00037\u000bQa\\;ucI\u0002B\u0001NA)}\"9\u0011q\u0014\u0003A\u0002\u0005\u0005\u0016!B8viF\u001a\u0004#\u0002\u001b\u0002R\u0005\r\u0001bBAS\t\u0001\u0007\u0011qU\u0001\u0006_V$\u0018\u0007\u000e\t\u0006i\u0005E\u0013\u0011\u0002\u0005\b\u0003W#\u0001\u0019AAW\u0003\u0015yW\u000f^\u00196!\u0015!\u0014\u0011KA\b\u0011\u001d\t\t\f\u0002a\u0001\u0003g\u000bQa\\;ucY\u0002R\u0001NA)\u0003+Aq!a.\u0005\u0001\u0004\tI,A\u0003pkR\ft\u0007E\u00035\u0003#\nY\"A\u0005d_:\u001cHO];diR\u00191'a0\t\r\u0005\u0005W\u00011\u0001G\u0003\u0011Ig.\u001b;\u0002\u0011\u0011,W\r]\"paf$\u0012!W\u000b\u0003\u0003\u0013\u0004R\u0001NA)\u0003\u0017T#A\u0017(\u0002\u000b=,H\u000f\r\u0011\u0016\u0005\u0005E\u0007#\u0002\u001b\u0002R\u0005M'FA/O\u0003\u0015yW\u000f^\u0019!+\t\tI\u000eE\u00035\u0003#\nYN\u000b\u0002a\u001d\u0006)q.\u001e;3AU\u0011\u0011\u0011\u001d\t\u0006i\u0005E\u00131\u001d\u0016\u0003G:\u000bQa\\;ug\u0001*\"!!;\u0011\u000bQ\n\t&a;+\u0005\u0019t\u0015!B8viR\u0002SCAAy!\u0015!\u0014\u0011KAzU\tIg*A\u0003pkR,\u0004%\u0006\u0002\u0002zB)A'!\u0015\u0002|*\u0012ANT\u0001\u0006_V$h\u0007I\u000b\u0003\u0005\u0003\u0001R\u0001NA)\u0005\u0007Q#a\u001c(\u0002\u000b=,Ho\u000e\u0011\u0016\u0005\t%\u0001#\u0002\u001b\u0002R\t-!F\u0001:O\u0003\u0015yW\u000f\u001e\u001d!+\t\u0011\t\u0002E\u00035\u0003#\u0012\u0019B\u000b\u0002v\u001d\u0006)q.\u001e;:AU\u0011!\u0011\u0004\t\u0006i\u0005E#1\u0004\u0016\u0003q:\u000baa\\;ucA\u0002SC\u0001B\u0011!\u0015!\u0014\u0011\u000bB\u0012U\tYh*\u0001\u0004pkR\f\u0014\u0007I\u000b\u0003\u0005S\u0001R\u0001NA)\u0005WQ#A (\u0002\r=,H/\r\u001a!+\t\u0011\t\u0004E\u00035\u0003#\u0012\u0019DK\u0002\u0002\u00049\u000baa\\;ucM\u0002SC\u0001B\u001d!\u0015!\u0014\u0011\u000bB\u001eU\r\tIAT\u0001\u0007_V$\u0018\u0007\u000e\u0011\u0016\u0005\t\u0005\u0003#\u0002\u001b\u0002R\t\r#fAA\b\u001d\u00061q.\u001e;2k\u0001*\"A!\u0013\u0011\u000bQ\n\tFa\u0013+\u0007\u0005Ua*\u0001\u0004pkR\fd\u0007I\u000b\u0003\u0005#\u0002R\u0001NA)\u0005'R3!a\u0007O\u0003\u0019yW\u000f^\u00198A\u0001")
public class FanOutShape18<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17>
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

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape18<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17>(init2);
    }

    @Override
    public FanOutShape18<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17> deepCopy() {
        return (FanOutShape18)super.deepCopy();
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

    public FanOutShape18(FanOutShape.Init<I> _init) {
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
    }

    public FanOutShape18(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape18(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7, Outlet<O8> out8, Outlet<O9> out9, Outlet<O10> out10, Outlet<O11> out11, Outlet<O12> out12, Outlet<O13> out13, Outlet<O14> out14, Outlet<O15> out15, Outlet<O16> out16, Outlet<O17> out17) {
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
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet18).$colon$colon(outlet17).$colon$colon(outlet16).$colon$colon(outlet15).$colon$colon(outlet14).$colon$colon(outlet13).$colon$colon(outlet12).$colon$colon(outlet11).$colon$colon(outlet10).$colon$colon(outlet9).$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

