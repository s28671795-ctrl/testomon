/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dg\u0001B\u0010!\u0001\u0015B\u0001\"\u000f\u0001\u0003\u0002\u0003\u0006IA\u000f\u0005\u0006\u0017\u0002!\t\u0001\u0014\u0005\u0006\u0017\u0002!\ta\u001d\u0005\u0007\u0017\u0002!\t!a\u0001\t\u000f\u0005m\u0003\u0001\"\u0015\u0002^!9\u00111\r\u0001\u0005B\u0005\u0015\u0004\"CA\t\u0001\t\u0007I\u0011AA4\u0011!\ti\u0007\u0001Q\u0001\n\u0005%\u0004\"CA\u000e\u0001\t\u0007I\u0011AA8\u0011!\t)\b\u0001Q\u0001\n\u0005E\u0004\"CA\u0011\u0001\t\u0007I\u0011AA<\u0011!\ti\b\u0001Q\u0001\n\u0005e\u0004\"CA\u0014\u0001\t\u0007I\u0011AA@\u0011!\t)\t\u0001Q\u0001\n\u0005\u0005\u0005\"CA\u0017\u0001\t\u0007I\u0011AAD\u0011!\ti\t\u0001Q\u0001\n\u0005%\u0005\"CA\u001a\u0001\t\u0007I\u0011AAH\u0011!\t)\n\u0001Q\u0001\n\u0005E\u0005\"CA\u001d\u0001\t\u0007I\u0011AAL\u0011!\ti\n\u0001Q\u0001\n\u0005e\u0005\"CA \u0001\t\u0007I\u0011AAP\u0011!\t)\u000b\u0001Q\u0001\n\u0005\u0005\u0006\"CA#\u0001\t\u0007I\u0011AAT\u0011!\ti\u000b\u0001Q\u0001\n\u0005%\u0006\"CA&\u0001\t\u0007I\u0011AAX\u0011!\t)\f\u0001Q\u0001\n\u0005E\u0006\"CA)\u0001\t\u0007I\u0011AA\\\u0011!\ti\f\u0001Q\u0001\n\u0005e\u0006\"CA,\u0001\t\u0007I\u0011AA`\u0011!\t)\r\u0001Q\u0001\n\u0005\u0005'!\u0004$b]>+Ho\u00155ba\u0016\f$G\u0003\u0002\"E\u000511\u000f\u001e:fC6T\u0011aI\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u001d\u0019jsJU+Y7z\u000bGm\u001a6naN\u0011\u0001a\n\t\u0004Q%ZS\"\u0001\u0011\n\u0005)\u0002#a\u0003$b]>+Ho\u00155ba\u0016\u0004\"\u0001L\u0017\r\u0001\u00111a\u0006\u0001EC\u0002=\u0012\u0011!S\t\u0003aY\u0002\"!\r\u001b\u000e\u0003IR\u0011aM\u0001\u0006g\u000e\fG.Y\u0005\u0003kI\u0012qAT8uQ&tw\r\u0005\u00022o%\u0011\u0001H\r\u0002\u0004\u0003:L\u0018!B0j]&$\bcA\u001e?\u0003:\u0011\u0001\u0006P\u0005\u0003{\u0001\n1BR1o\u001fV$8\u000b[1qK&\u0011q\b\u0011\u0002\u0005\u0013:LGO\u0003\u0002>A)\u00121FQ\u0016\u0002\u0007B\u0011A)S\u0007\u0002\u000b*\u0011aiR\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001\u0013\u001a\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002K\u000b\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\rqJg.\u001b;?)\ti%\u000fE\b)\u0001-r\u0015\u000bV,[;\u0002\u001cg-\u001b7p!\tas\n\u0002\u0004Q\u0001\u0011\u0015\ra\f\u0002\u0003\u001fB\u0002\"\u0001\f*\u0005\rM\u0003AQ1\u00010\u0005\ty\u0015\u0007\u0005\u0002-+\u00121a\u000b\u0001CC\u0002=\u0012!a\u0014\u001a\u0011\u00051BFAB-\u0001\t\u000b\u0007qF\u0001\u0002PgA\u0011Af\u0017\u0003\u00079\u0002!)\u0019A\u0018\u0003\u0005=#\u0004C\u0001\u0017_\t\u0019y\u0006\u0001\"b\u0001_\t\u0011q*\u000e\t\u0003Y\u0005$aA\u0019\u0001\u0005\u0006\u0004y#AA(7!\taC\r\u0002\u0004f\u0001\u0011\u0015\ra\f\u0002\u0003\u001f^\u0002\"\u0001L4\u0005\r!\u0004AQ1\u00010\u0005\ty\u0005\b\u0005\u0002-U\u001211\u000e\u0001CC\u0002=\u0012!aT\u001d\u0011\u00051jGA\u00028\u0001\t\u000b\u0007qFA\u0002PcA\u0002\"\u0001\f9\u0005\rE\u0004AQ1\u00010\u0005\ry\u0015'\r\u0005\u0006s\t\u0001\rA\u000f\u000b\u0003\u001bRDQ!^\u0002A\u0002Y\fAA\\1nKB\u0011qO \b\u0003qr\u0004\"!\u001f\u001a\u000e\u0003iT!a\u001f\u0013\u0002\rq\u0012xn\u001c;?\u0013\ti('\u0001\u0004Qe\u0016$WMZ\u0005\u0004\u007f\u0006\u0005!AB*ue&twM\u0003\u0002~eQYR*!\u0002\u0002\u0010\u0005e\u0011qDA\u0013\u0003W\t\t$a\u000e\u0002>\u0005\r\u0013\u0011JA(\u0003+Bq!a\u0002\u0005\u0001\u0004\tI!\u0001\u0002j]B!\u0001&a\u0003,\u0013\r\ti\u0001\t\u0002\u0006\u0013:dW\r\u001e\u0005\b\u0003#!\u0001\u0019AA\n\u0003\u0011yW\u000f\u001e\u0019\u0011\t!\n)BT\u0005\u0004\u0003/\u0001#AB(vi2,G\u000fC\u0004\u0002\u001c\u0011\u0001\r!!\b\u0002\t=,H/\r\t\u0005Q\u0005U\u0011\u000bC\u0004\u0002\"\u0011\u0001\r!a\t\u0002\t=,HO\r\t\u0005Q\u0005UA\u000bC\u0004\u0002(\u0011\u0001\r!!\u000b\u0002\t=,Ho\r\t\u0005Q\u0005Uq\u000bC\u0004\u0002.\u0011\u0001\r!a\f\u0002\t=,H\u000f\u000e\t\u0005Q\u0005U!\fC\u0004\u00024\u0011\u0001\r!!\u000e\u0002\t=,H/\u000e\t\u0005Q\u0005UQ\fC\u0004\u0002:\u0011\u0001\r!a\u000f\u0002\t=,HO\u000e\t\u0005Q\u0005U\u0001\rC\u0004\u0002@\u0011\u0001\r!!\u0011\u0002\t=,Ho\u000e\t\u0005Q\u0005U1\rC\u0004\u0002F\u0011\u0001\r!a\u0012\u0002\t=,H\u000f\u000f\t\u0005Q\u0005Ua\rC\u0004\u0002L\u0011\u0001\r!!\u0014\u0002\t=,H/\u000f\t\u0005Q\u0005U\u0011\u000eC\u0004\u0002R\u0011\u0001\r!a\u0015\u0002\u000b=,H/\r\u0019\u0011\t!\n)\u0002\u001c\u0005\b\u0003/\"\u0001\u0019AA-\u0003\u0015yW\u000f^\u00192!\u0011A\u0013QC8\u0002\u0013\r|gn\u001d;sk\u000e$HcA\u0014\u0002`!1\u0011\u0011M\u0003A\u0002i\nA!\u001b8ji\u0006AA-Z3q\u0007>\u0004\u0018\u0010F\u0001N+\t\tI\u0007E\u0003)\u0003+\tYG\u000b\u0002O\u0005\u0006)q.\u001e;1AU\u0011\u0011\u0011\u000f\t\u0006Q\u0005U\u00111\u000f\u0016\u0003#\n\u000bQa\\;uc\u0001*\"!!\u001f\u0011\u000b!\n)\"a\u001f+\u0005Q\u0013\u0015!B8viJ\u0002SCAAA!\u0015A\u0013QCABU\t9&)A\u0003pkR\u001c\u0004%\u0006\u0002\u0002\nB)\u0001&!\u0006\u0002\f*\u0012!LQ\u0001\u0006_V$H\u0007I\u000b\u0003\u0003#\u0003R\u0001KA\u000b\u0003'S#!\u0018\"\u0002\u000b=,H/\u000e\u0011\u0016\u0005\u0005e\u0005#\u0002\u0015\u0002\u0016\u0005m%F\u00011C\u0003\u0015yW\u000f\u001e\u001c!+\t\t\t\u000bE\u0003)\u0003+\t\u0019K\u000b\u0002d\u0005\u0006)q.\u001e;8AU\u0011\u0011\u0011\u0016\t\u0006Q\u0005U\u00111\u0016\u0016\u0003M\n\u000bQa\\;uq\u0001*\"!!-\u0011\u000b!\n)\"a-+\u0005%\u0014\u0015!B8vif\u0002SCAA]!\u0015A\u0013QCA^U\ta')\u0001\u0004pkR\f\u0004\u0007I\u000b\u0003\u0003\u0003\u0004R\u0001KA\u000b\u0003\u0007T#a\u001c\"\u0002\r=,H/M\u0019!\u0001")
public class FanOutShape12<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11>
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

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape12<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11>(init2);
    }

    @Override
    public FanOutShape12<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11> deepCopy() {
        return (FanOutShape12)super.deepCopy();
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

    public FanOutShape12(FanOutShape.Init<I> _init) {
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
    }

    public FanOutShape12(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape12(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7, Outlet<O8> out8, Outlet<O9> out9, Outlet<O10> out10, Outlet<O11> out11) {
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
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet12).$colon$colon(outlet11).$colon$colon(outlet10).$colon$colon(outlet9).$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

