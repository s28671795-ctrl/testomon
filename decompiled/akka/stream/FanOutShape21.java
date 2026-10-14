/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t}e\u0001B\u00193\u0001]B\u0001b\u0013\u0001\u0003\u0002\u0003\u0006I\u0001\u0014\u0005\u0006;\u0002!\tA\u0018\u0005\u0007;\u0002!\t!!\u0011\t\ru\u0003A\u0011AA/\u0011\u001d\tY\u000f\u0001C)\u0003[Dq!a=\u0001\t\u0003\n)\u0010C\u0005\u0002l\u0001\u0011\r\u0011\"\u0001\u0002x\"A\u0011Q \u0001!\u0002\u0013\tI\u0010C\u0005\u0002v\u0001\u0011\r\u0011\"\u0001\u0002\u0000\"A!Q\u0001\u0001!\u0002\u0013\u0011\t\u0001C\u0005\u0002|\u0001\u0011\r\u0011\"\u0001\u0003\b!A!Q\u0002\u0001!\u0002\u0013\u0011I\u0001C\u0005\u0002\u0002\u0002\u0011\r\u0011\"\u0001\u0003\u0010!A!Q\u0003\u0001!\u0002\u0013\u0011\t\u0002C\u0005\u0002\b\u0002\u0011\r\u0011\"\u0001\u0003\u0018!A!Q\u0004\u0001!\u0002\u0013\u0011I\u0002C\u0005\u0002\u000e\u0002\u0011\r\u0011\"\u0001\u0003 !A!Q\u0005\u0001!\u0002\u0013\u0011\t\u0003C\u0005\u0002\u0014\u0002\u0011\r\u0011\"\u0001\u0003(!A!Q\u0006\u0001!\u0002\u0013\u0011I\u0003C\u0005\u0002\u001a\u0002\u0011\r\u0011\"\u0001\u00030!A!Q\u0007\u0001!\u0002\u0013\u0011\t\u0004C\u0005\u0002 \u0002\u0011\r\u0011\"\u0001\u00038!A!Q\b\u0001!\u0002\u0013\u0011I\u0004C\u0005\u0002&\u0002\u0011\r\u0011\"\u0001\u0003@!A!Q\t\u0001!\u0002\u0013\u0011\t\u0005C\u0005\u0002,\u0002\u0011\r\u0011\"\u0001\u0003H!A!Q\n\u0001!\u0002\u0013\u0011I\u0005C\u0005\u00022\u0002\u0011\r\u0011\"\u0001\u0003P!A!Q\u000b\u0001!\u0002\u0013\u0011\t\u0006C\u0005\u00028\u0002\u0011\r\u0011\"\u0001\u0003X!A!Q\f\u0001!\u0002\u0013\u0011I\u0006C\u0005\u0002>\u0002\u0011\r\u0011\"\u0001\u0003`!A!Q\r\u0001!\u0002\u0013\u0011\t\u0007C\u0005\u0002D\u0002\u0011\r\u0011\"\u0001\u0003h!A!Q\u000e\u0001!\u0002\u0013\u0011I\u0007C\u0005\u0002J\u0002\u0011\r\u0011\"\u0001\u0003p!A!Q\u000f\u0001!\u0002\u0013\u0011\t\bC\u0005\u0002P\u0002\u0011\r\u0011\"\u0001\u0003x!A!Q\u0010\u0001!\u0002\u0013\u0011I\bC\u0005\u0002V\u0002\u0011\r\u0011\"\u0001\u0003\u0000!A!Q\u0011\u0001!\u0002\u0013\u0011\t\tC\u0005\u0002\\\u0002\u0011\r\u0011\"\u0001\u0003\b\"A!Q\u0012\u0001!\u0002\u0013\u0011I\tC\u0005\u0002b\u0002\u0011\r\u0011\"\u0001\u0003\u0010\"A!Q\u0013\u0001!\u0002\u0013\u0011\t\nC\u0005\u0002h\u0002\u0011\r\u0011\"\u0001\u0003\u0018\"A!Q\u0014\u0001!\u0002\u0013\u0011IJA\u0007GC:|U\u000f^*iCB,''\r\u0006\u0003gQ\naa\u001d;sK\u0006l'\"A\u001b\u0002\t\u0005\\7.Y\u0002\u0001+\u0005Bt(\u00193hU6\u00048O^=}\u007f\u0006\u0015\u00111BA\t\u0003/\ti\"a\t\u0002*\u0005=\u0012QGA\u001e'\t\u0001\u0011\bE\u0002;wuj\u0011AM\u0005\u0003yI\u00121BR1o\u001fV$8\u000b[1qKB\u0011ah\u0010\u0007\u0001\t\u0019\u0001\u0005\u0001#b\u0001\u0003\n\t\u0011*\u0005\u0002C\u0011B\u00111IR\u0007\u0002\t*\tQ)A\u0003tG\u0006d\u0017-\u0003\u0002H\t\n9aj\u001c;iS:<\u0007CA\"J\u0013\tQEIA\u0002B]f\fQaX5oSR\u00042!\u0014)T\u001d\tQd*\u0003\u0002Pe\u0005Ya)\u00198PkR\u001c\u0006.\u00199f\u0013\t\t&K\u0001\u0003J]&$(BA(3U\tiDkK\u0001V!\t16,D\u0001X\u0015\tA\u0016,A\u0005v]\u000eDWmY6fI*\u0011!\fR\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001/X\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007}\u000by\u0004\u0005\u0012;\u0001u\u00027MZ5m_J,\bp\u001f@\u0002\u0004\u0005%\u0011qBA\u000b\u00037\t\t#a\n\u0002.\u0005M\u0012\u0011\b\t\u0003}\u0005$aA\u0019\u0001\u0005\u0006\u0004\t%AA(1!\tqD\r\u0002\u0004f\u0001\u0011\u0015\r!\u0011\u0002\u0003\u001fF\u0002\"AP4\u0005\r!\u0004AQ1\u0001B\u0005\ty%\u0007\u0005\u0002?U\u001211\u000e\u0001CC\u0002\u0005\u0013!aT\u001a\u0011\u0005yjGA\u00028\u0001\t\u000b\u0007\u0011I\u0001\u0002PiA\u0011a\b\u001d\u0003\u0007c\u0002!)\u0019A!\u0003\u0005=+\u0004C\u0001 t\t\u0019!\b\u0001\"b\u0001\u0003\n\u0011qJ\u000e\t\u0003}Y$aa\u001e\u0001\u0005\u0006\u0004\t%AA(8!\tq\u0014\u0010\u0002\u0004{\u0001\u0011\u0015\r!\u0011\u0002\u0003\u001fb\u0002\"A\u0010?\u0005\ru\u0004AQ1\u0001B\u0005\ty\u0015\b\u0005\u0002?\u007f\u00129\u0011\u0011\u0001\u0001\u0005\u0006\u0004\t%aA(2aA\u0019a(!\u0002\u0005\u000f\u0005\u001d\u0001\u0001\"b\u0001\u0003\n\u0019q*M\u0019\u0011\u0007y\nY\u0001B\u0004\u0002\u000e\u0001!)\u0019A!\u0003\u0007=\u000b$\u0007E\u0002?\u0003#!q!a\u0005\u0001\t\u000b\u0007\u0011IA\u0002PcM\u00022APA\f\t\u001d\tI\u0002\u0001CC\u0002\u0005\u00131aT\u00195!\rq\u0014Q\u0004\u0003\b\u0003?\u0001AQ1\u0001B\u0005\ry\u0015'\u000e\t\u0004}\u0005\rBaBA\u0013\u0001\u0011\u0015\r!\u0011\u0002\u0004\u001fF2\u0004c\u0001 \u0002*\u00119\u00111\u0006\u0001\u0005\u0006\u0004\t%aA(2oA\u0019a(a\f\u0005\u000f\u0005E\u0002\u0001\"b\u0001\u0003\n\u0019q*\r\u001d\u0011\u0007y\n)\u0004B\u0004\u00028\u0001!)\u0019A!\u0003\u0007=\u000b\u0014\bE\u0002?\u0003w!q!!\u0010\u0001\t\u000b\u0007\u0011IA\u0002PeABQa\u0013\u0002A\u00021#2aXA\"\u0011\u001d\t)e\u0001a\u0001\u0003\u000f\nAA\\1nKB!\u0011\u0011JA,\u001d\u0011\tY%a\u0015\u0011\u0007\u00055C)\u0004\u0002\u0002P)\u0019\u0011\u0011\u000b\u001c\u0002\rq\u0012xn\u001c;?\u0013\r\t)\u0006R\u0001\u0007!J,G-\u001a4\n\t\u0005e\u00131\f\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005UC\tF\u0017`\u0003?\nI'a\u001d\u0002z\u0005}\u0014QQAF\u0003#\u000b9*!(\u0002$\u0006%\u0016qVA[\u0003w\u000b\t-a2\u0002N\u0006M\u0017\u0011\\Ap\u0003KDq!!\u0019\u0005\u0001\u0004\t\u0019'\u0001\u0002j]B!!(!\u001a>\u0013\r\t9G\r\u0002\u0006\u0013:dW\r\u001e\u0005\b\u0003W\"\u0001\u0019AA7\u0003\u0011yW\u000f\u001e\u0019\u0011\ti\ny\u0007Y\u0005\u0004\u0003c\u0012$AB(vi2,G\u000fC\u0004\u0002v\u0011\u0001\r!a\u001e\u0002\t=,H/\r\t\u0005u\u0005=4\rC\u0004\u0002|\u0011\u0001\r!! \u0002\t=,HO\r\t\u0005u\u0005=d\rC\u0004\u0002\u0002\u0012\u0001\r!a!\u0002\t=,Ho\r\t\u0005u\u0005=\u0014\u000eC\u0004\u0002\b\u0012\u0001\r!!#\u0002\t=,H\u000f\u000e\t\u0005u\u0005=D\u000eC\u0004\u0002\u000e\u0012\u0001\r!a$\u0002\t=,H/\u000e\t\u0005u\u0005=t\u000eC\u0004\u0002\u0014\u0012\u0001\r!!&\u0002\t=,HO\u000e\t\u0005u\u0005=$\u000fC\u0004\u0002\u001a\u0012\u0001\r!a'\u0002\t=,Ho\u000e\t\u0005u\u0005=T\u000fC\u0004\u0002 \u0012\u0001\r!!)\u0002\t=,H\u000f\u000f\t\u0005u\u0005=\u0004\u0010C\u0004\u0002&\u0012\u0001\r!a*\u0002\t=,H/\u000f\t\u0005u\u0005=4\u0010C\u0004\u0002,\u0012\u0001\r!!,\u0002\u000b=,H/\r\u0019\u0011\ti\nyG \u0005\b\u0003c#\u0001\u0019AAZ\u0003\u0015yW\u000f^\u00192!\u0015Q\u0014qNA\u0002\u0011\u001d\t9\f\u0002a\u0001\u0003s\u000bQa\\;ucI\u0002RAOA8\u0003\u0013Aq!!0\u0005\u0001\u0004\ty,A\u0003pkR\f4\u0007E\u0003;\u0003_\ny\u0001C\u0004\u0002D\u0012\u0001\r!!2\u0002\u000b=,H/\r\u001b\u0011\u000bi\ny'!\u0006\t\u000f\u0005%G\u00011\u0001\u0002L\u0006)q.\u001e;2kA)!(a\u001c\u0002\u001c!9\u0011q\u001a\u0003A\u0002\u0005E\u0017!B8viF2\u0004#\u0002\u001e\u0002p\u0005\u0005\u0002bBAk\t\u0001\u0007\u0011q[\u0001\u0006_V$\u0018g\u000e\t\u0006u\u0005=\u0014q\u0005\u0005\b\u00037$\u0001\u0019AAo\u0003\u0015yW\u000f^\u00199!\u0015Q\u0014qNA\u0017\u0011\u001d\t\t\u000f\u0002a\u0001\u0003G\fQa\\;uce\u0002RAOA8\u0003gAq!a:\u0005\u0001\u0004\tI/A\u0003pkR\u0014\u0004\u0007E\u0003;\u0003_\nI$A\u0005d_:\u001cHO];diR\u0019\u0011(a<\t\r\u0005EX\u00011\u0001M\u0003\u0011Ig.\u001b;\u0002\u0011\u0011,W\r]\"paf$\u0012aX\u000b\u0003\u0003s\u0004RAOA8\u0003wT#\u0001\u0019+\u0002\u000b=,H\u000f\r\u0011\u0016\u0005\t\u0005\u0001#\u0002\u001e\u0002p\t\r!FA2U\u0003\u0015yW\u000f^\u0019!+\t\u0011I\u0001E\u0003;\u0003_\u0012YA\u000b\u0002g)\u0006)q.\u001e;3AU\u0011!\u0011\u0003\t\u0006u\u0005=$1\u0003\u0016\u0003SR\u000bQa\\;ug\u0001*\"A!\u0007\u0011\u000bi\nyGa\u0007+\u00051$\u0016!B8viR\u0002SC\u0001B\u0011!\u0015Q\u0014q\u000eB\u0012U\tyG+A\u0003pkR,\u0004%\u0006\u0002\u0003*A)!(a\u001c\u0003,)\u0012!\u000fV\u0001\u0006_V$h\u0007I\u000b\u0003\u0005c\u0001RAOA8\u0005gQ#!\u001e+\u0002\u000b=,Ho\u000e\u0011\u0016\u0005\te\u0002#\u0002\u001e\u0002p\tm\"F\u0001=U\u0003\u0015yW\u000f\u001e\u001d!+\t\u0011\t\u0005E\u0003;\u0003_\u0012\u0019E\u000b\u0002|)\u0006)q.\u001e;:AU\u0011!\u0011\n\t\u0006u\u0005=$1\n\u0016\u0003}R\u000baa\\;ucA\u0002SC\u0001B)!\u0015Q\u0014q\u000eB*U\r\t\u0019\u0001V\u0001\u0007_V$\u0018'\r\u0011\u0016\u0005\te\u0003#\u0002\u001e\u0002p\tm#fAA\u0005)\u00061q.\u001e;2e\u0001*\"A!\u0019\u0011\u000bi\nyGa\u0019+\u0007\u0005=A+\u0001\u0004pkR\f4\u0007I\u000b\u0003\u0005S\u0002RAOA8\u0005WR3!!\u0006U\u0003\u0019yW\u000f^\u00195AU\u0011!\u0011\u000f\t\u0006u\u0005=$1\u000f\u0016\u0004\u00037!\u0016AB8viF*\u0004%\u0006\u0002\u0003zA)!(a\u001c\u0003|)\u001a\u0011\u0011\u0005+\u0002\r=,H/\r\u001c!+\t\u0011\t\tE\u0003;\u0003_\u0012\u0019IK\u0002\u0002(Q\u000baa\\;uc]\u0002SC\u0001BE!\u0015Q\u0014q\u000eBFU\r\ti\u0003V\u0001\u0007_V$\u0018\u0007\u000f\u0011\u0016\u0005\tE\u0005#\u0002\u001e\u0002p\tM%fAA\u001a)\u00061q.\u001e;2s\u0001*\"A!'\u0011\u000bi\nyGa'+\u0007\u0005eB+\u0001\u0004pkR\u0014\u0004\u0007\t")
public class FanOutShape21<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18, O19, O20>
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

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape21<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18, O19, O20>(init2);
    }

    @Override
    public FanOutShape21<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18, O19, O20> deepCopy() {
        return (FanOutShape21)super.deepCopy();
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

    public FanOutShape21(FanOutShape.Init<I> _init) {
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
    }

    public FanOutShape21(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape21(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7, Outlet<O8> out8, Outlet<O9> out9, Outlet<O10> out10, Outlet<O11> out11, Outlet<O12> out12, Outlet<O13> out13, Outlet<O14> out14, Outlet<O15> out15, Outlet<O16> out16, Outlet<O17> out17, Outlet<O18> out18, Outlet<O19> out19, Outlet<O20> out20) {
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
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet21).$colon$colon(outlet20).$colon$colon(outlet19).$colon$colon(outlet18).$colon$colon(outlet17).$colon$colon(outlet16).$colon$colon(outlet15).$colon$colon(outlet14).$colon$colon(outlet13).$colon$colon(outlet12).$colon$colon(outlet11).$colon$colon(outlet10).$colon$colon(outlet9).$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

