/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]h\u0001B\u0012%\u0001%B\u0001\"\u0010\u0001\u0003\u0002\u0003\u0006IA\u0010\u0005\u0006\u001f\u0002!\t\u0001\u0015\u0005\u0006\u001f\u0002!\t! \u0005\u0007\u001f\u0002!\t!a\u0006\t\u000f\u0005m\u0004\u0001\"\u0015\u0002~!9\u00111\u0011\u0001\u0005B\u0005\u0015\u0005\"CA\u0013\u0001\t\u0007I\u0011AAD\u0011!\ti\t\u0001Q\u0001\n\u0005%\u0005\"CA\u0018\u0001\t\u0007I\u0011AAH\u0011!\t)\n\u0001Q\u0001\n\u0005E\u0005\"CA\u001b\u0001\t\u0007I\u0011AAL\u0011!\ti\n\u0001Q\u0001\n\u0005e\u0005\"CA\u001e\u0001\t\u0007I\u0011AAP\u0011!\t)\u000b\u0001Q\u0001\n\u0005\u0005\u0006\"CA!\u0001\t\u0007I\u0011AAT\u0011!\ti\u000b\u0001Q\u0001\n\u0005%\u0006\"CA$\u0001\t\u0007I\u0011AAX\u0011!\t)\f\u0001Q\u0001\n\u0005E\u0006\"CA'\u0001\t\u0007I\u0011AA\\\u0011!\ti\f\u0001Q\u0001\n\u0005e\u0006\"CA*\u0001\t\u0007I\u0011AA`\u0011!\t)\r\u0001Q\u0001\n\u0005\u0005\u0007\"CA-\u0001\t\u0007I\u0011AAd\u0011!\ti\r\u0001Q\u0001\n\u0005%\u0007\"CA0\u0001\t\u0007I\u0011AAh\u0011!\t)\u000e\u0001Q\u0001\n\u0005E\u0007\"CA3\u0001\t\u0007I\u0011AAl\u0011!\ti\u000e\u0001Q\u0001\n\u0005e\u0007\"CA6\u0001\t\u0007I\u0011AAp\u0011!\t)\u000f\u0001Q\u0001\n\u0005\u0005\b\"CA9\u0001\t\u0007I\u0011AAt\u0011!\ti\u000f\u0001Q\u0001\n\u0005%\b\"CA<\u0001\t\u0007I\u0011AAx\u0011!\t)\u0010\u0001Q\u0001\n\u0005E(!\u0004$b]>+Ho\u00155ba\u0016\fDG\u0003\u0002&M\u000511\u000f\u001e:fC6T\u0011aJ\u0001\u0005C.\\\u0017m\u0001\u0001\u0016!)\n4KV-]?\n,\u0007n\u001b8ri^T8C\u0001\u0001,!\raSfL\u0007\u0002I%\u0011a\u0006\n\u0002\f\r\u0006tw*\u001e;TQ\u0006\u0004X\r\u0005\u00021c1\u0001AA\u0002\u001a\u0001\u0011\u000b\u00071GA\u0001J#\t!$\b\u0005\u00026q5\taGC\u00018\u0003\u0015\u00198-\u00197b\u0013\tIdGA\u0004O_RD\u0017N\\4\u0011\u0005UZ\u0014B\u0001\u001f7\u0005\r\te._\u0001\u0006?&t\u0017\u000e\u001e\t\u0004\u007f\t+eB\u0001\u0017A\u0013\t\tE%A\u0006GC:|U\u000f^*iCB,\u0017BA\"E\u0005\u0011Ie.\u001b;\u000b\u0005\u0005##FA\u0018GW\u00059\u0005C\u0001%N\u001b\u0005I%B\u0001&L\u0003%)hn\u00195fG.,GM\u0003\u0002Mm\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u00059K%!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u00061A(\u001b8jiz\"\"!\u0015?\u0011#1\u0002qFU+Y7z\u000bGm\u001a6naN4\u0018\u0010\u0005\u00021'\u00121A\u000b\u0001CC\u0002M\u0012!a\u0014\u0019\u0011\u0005A2FAB,\u0001\t\u000b\u00071G\u0001\u0002PcA\u0011\u0001'\u0017\u0003\u00075\u0002!)\u0019A\u001a\u0003\u0005=\u0013\u0004C\u0001\u0019]\t\u0019i\u0006\u0001\"b\u0001g\t\u0011qj\r\t\u0003a}#a\u0001\u0019\u0001\u0005\u0006\u0004\u0019$AA(5!\t\u0001$\r\u0002\u0004d\u0001\u0011\u0015\ra\r\u0002\u0003\u001fV\u0002\"\u0001M3\u0005\r\u0019\u0004AQ1\u00014\u0005\tye\u0007\u0005\u00021Q\u00121\u0011\u000e\u0001CC\u0002M\u0012!aT\u001c\u0011\u0005AZGA\u00027\u0001\t\u000b\u00071G\u0001\u0002PqA\u0011\u0001G\u001c\u0003\u0007_\u0002!)\u0019A\u001a\u0003\u0005=K\u0004C\u0001\u0019r\t\u0019\u0011\b\u0001\"b\u0001g\t\u0019q*\r\u0019\u0011\u0005A\"HAB;\u0001\t\u000b\u00071GA\u0002PcE\u0002\"\u0001M<\u0005\ra\u0004AQ1\u00014\u0005\ry\u0015G\r\t\u0003ai$aa\u001f\u0001\u0005\u0006\u0004\u0019$aA(2g!)QH\u0001a\u0001}Q\u0011\u0011K \u0005\u0007\u007f\u000e\u0001\r!!\u0001\u0002\t9\fW.\u001a\t\u0005\u0003\u0007\t\tB\u0004\u0003\u0002\u0006\u00055\u0001cAA\u0004m5\u0011\u0011\u0011\u0002\u0006\u0004\u0003\u0017A\u0013A\u0002\u001fs_>$h(C\u0002\u0002\u0010Y\na\u0001\u0015:fI\u00164\u0017\u0002BA\n\u0003+\u0011aa\u0015;sS:<'bAA\bmQy\u0012+!\u0007\u0002$\u00055\u00121GA\u001d\u0003\u007f\t)%a\u0013\u0002R\u0005]\u0013QLA2\u0003S\ny'!\u001e\t\u000f\u0005mA\u00011\u0001\u0002\u001e\u0005\u0011\u0011N\u001c\t\u0005Y\u0005}q&C\u0002\u0002\"\u0011\u0012Q!\u00138mKRDq!!\n\u0005\u0001\u0004\t9#\u0001\u0003pkR\u0004\u0004\u0003\u0002\u0017\u0002*IK1!a\u000b%\u0005\u0019yU\u000f\u001e7fi\"9\u0011q\u0006\u0003A\u0002\u0005E\u0012\u0001B8viF\u0002B\u0001LA\u0015+\"9\u0011Q\u0007\u0003A\u0002\u0005]\u0012\u0001B8viJ\u0002B\u0001LA\u00151\"9\u00111\b\u0003A\u0002\u0005u\u0012\u0001B8viN\u0002B\u0001LA\u00157\"9\u0011\u0011\t\u0003A\u0002\u0005\r\u0013\u0001B8viR\u0002B\u0001LA\u0015=\"9\u0011q\t\u0003A\u0002\u0005%\u0013\u0001B8viV\u0002B\u0001LA\u0015C\"9\u0011Q\n\u0003A\u0002\u0005=\u0013\u0001B8viZ\u0002B\u0001LA\u0015I\"9\u00111\u000b\u0003A\u0002\u0005U\u0013\u0001B8vi^\u0002B\u0001LA\u0015O\"9\u0011\u0011\f\u0003A\u0002\u0005m\u0013\u0001B8vib\u0002B\u0001LA\u0015U\"9\u0011q\f\u0003A\u0002\u0005\u0005\u0014\u0001B8vif\u0002B\u0001LA\u0015[\"9\u0011Q\r\u0003A\u0002\u0005\u001d\u0014!B8viF\u0002\u0004\u0003\u0002\u0017\u0002*ADq!a\u001b\u0005\u0001\u0004\ti'A\u0003pkR\f\u0014\u0007\u0005\u0003-\u0003S\u0019\bbBA9\t\u0001\u0007\u00111O\u0001\u0006_V$\u0018G\r\t\u0005Y\u0005%b\u000fC\u0004\u0002x\u0011\u0001\r!!\u001f\u0002\u000b=,H/M\u001a\u0011\t1\nI#_\u0001\nG>t7\u000f\u001e:vGR$2aKA@\u0011\u0019\t\t)\u0002a\u0001}\u0005!\u0011N\\5u\u0003!!W-\u001a9D_BLH#A)\u0016\u0005\u0005%\u0005#\u0002\u0017\u0002*\u0005-%F\u0001*G\u0003\u0015yW\u000f\u001e\u0019!+\t\t\t\nE\u0003-\u0003S\t\u0019J\u000b\u0002V\r\u0006)q.\u001e;2AU\u0011\u0011\u0011\u0014\t\u0006Y\u0005%\u00121\u0014\u0016\u00031\u001a\u000bQa\\;ue\u0001*\"!!)\u0011\u000b1\nI#a)+\u0005m3\u0015!B8viN\u0002SCAAU!\u0015a\u0013\u0011FAVU\tqf)A\u0003pkR$\u0004%\u0006\u0002\u00022B)A&!\u000b\u00024*\u0012\u0011MR\u0001\u0006_V$X\u0007I\u000b\u0003\u0003s\u0003R\u0001LA\u0015\u0003wS#\u0001\u001a$\u0002\u000b=,HO\u000e\u0011\u0016\u0005\u0005\u0005\u0007#\u0002\u0017\u0002*\u0005\r'FA4G\u0003\u0015yW\u000f^\u001c!+\t\tI\rE\u0003-\u0003S\tYM\u000b\u0002k\r\u0006)q.\u001e;9AU\u0011\u0011\u0011\u001b\t\u0006Y\u0005%\u00121\u001b\u0016\u0003[\u001a\u000bQa\\;us\u0001*\"!!7\u0011\u000b1\nI#a7+\u0005A4\u0015AB8viF\u0002\u0004%\u0006\u0002\u0002bB)A&!\u000b\u0002d*\u00121OR\u0001\u0007_V$\u0018'\r\u0011\u0016\u0005\u0005%\b#\u0002\u0017\u0002*\u0005-(F\u0001<G\u0003\u0019yW\u000f^\u00193AU\u0011\u0011\u0011\u001f\t\u0006Y\u0005%\u00121\u001f\u0016\u0003s\u001a\u000baa\\;ucM\u0002\u0003")
public class FanOutShape14<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13>
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

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape14<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13>(init2);
    }

    @Override
    public FanOutShape14<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13> deepCopy() {
        return (FanOutShape14)super.deepCopy();
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

    public FanOutShape14(FanOutShape.Init<I> _init) {
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
    }

    public FanOutShape14(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape14(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7, Outlet<O8> out8, Outlet<O9> out9, Outlet<O10> out10, Outlet<O11> out11, Outlet<O12> out12, Outlet<O13> out13) {
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
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet14).$colon$colon(outlet13).$colon$colon(outlet12).$colon$colon(outlet11).$colon$colon(outlet10).$colon$colon(outlet9).$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

