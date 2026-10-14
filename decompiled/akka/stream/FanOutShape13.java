/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005}g\u0001B\u0011#\u0001\u001dB\u0001b\u000f\u0001\u0003\u0002\u0003\u0006I\u0001\u0010\u0005\u0006\u001b\u0002!\tA\u0014\u0005\u0006\u001b\u0002!\t\u0001\u001f\u0005\u0007\u001b\u0002!\t!!\u0004\t\u000f\u0005-\u0004\u0001\"\u0015\u0002n!9\u00111\u000f\u0001\u0005B\u0005U\u0004\"CA\u000e\u0001\t\u0007I\u0011AA<\u0011!\ti\b\u0001Q\u0001\n\u0005e\u0004\"CA\u0013\u0001\t\u0007I\u0011AA@\u0011!\t)\t\u0001Q\u0001\n\u0005\u0005\u0005\"CA\u0016\u0001\t\u0007I\u0011AAD\u0011!\ti\t\u0001Q\u0001\n\u0005%\u0005\"CA\u0019\u0001\t\u0007I\u0011AAH\u0011!\t)\n\u0001Q\u0001\n\u0005E\u0005\"CA\u001c\u0001\t\u0007I\u0011AAL\u0011!\ti\n\u0001Q\u0001\n\u0005e\u0005\"CA\u001f\u0001\t\u0007I\u0011AAP\u0011!\t)\u000b\u0001Q\u0001\n\u0005\u0005\u0006\"CA\"\u0001\t\u0007I\u0011AAT\u0011!\ti\u000b\u0001Q\u0001\n\u0005%\u0006\"CA%\u0001\t\u0007I\u0011AAX\u0011!\t)\f\u0001Q\u0001\n\u0005E\u0006\"CA(\u0001\t\u0007I\u0011AA\\\u0011!\ti\f\u0001Q\u0001\n\u0005e\u0006\"CA+\u0001\t\u0007I\u0011AA`\u0011!\t)\r\u0001Q\u0001\n\u0005\u0005\u0007\"CA.\u0001\t\u0007I\u0011AAd\u0011!\ti\r\u0001Q\u0001\n\u0005%\u0007\"CA1\u0001\t\u0007I\u0011AAh\u0011!\t)\u000e\u0001Q\u0001\n\u0005E\u0007\"CA4\u0001\t\u0007I\u0011AAl\u0011!\ti\u000e\u0001Q\u0001\n\u0005e'!\u0004$b]>+Ho\u00155ba\u0016\f4G\u0003\u0002$I\u000511\u000f\u001e:fC6T\u0011!J\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u001f!z\u0013\u000bV,[;\u0002\u001cg-\u001b7peV\u001c\"\u0001A\u0015\u0011\u0007)ZS&D\u0001#\u0013\ta#EA\u0006GC:|U\u000f^*iCB,\u0007C\u0001\u00180\u0019\u0001!a\u0001\r\u0001\t\u0006\u0004\t$!A%\u0012\u0005IB\u0004CA\u001a7\u001b\u0005!$\"A\u001b\u0002\u000bM\u001c\u0017\r\\1\n\u0005]\"$a\u0002(pi\"Lgn\u001a\t\u0003geJ!A\u000f\u001b\u0003\u0007\u0005s\u00170A\u0003`S:LG\u000fE\u0002>\u0001\u000es!A\u000b \n\u0005}\u0012\u0013a\u0003$b]>+Ho\u00155ba\u0016L!!\u0011\"\u0003\t%s\u0017\u000e\u001e\u0006\u0003\u007f\tR#!\f#,\u0003\u0015\u0003\"AR&\u000e\u0003\u001dS!\u0001S%\u0002\u0013Ut7\r[3dW\u0016$'B\u0001&5\u0003)\tgN\\8uCRLwN\\\u0005\u0003\u0019\u001e\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0003\u0019a\u0014N\\5u}Q\u0011qj\u001e\t\u0011U\u0001i\u0003k\u0015,Z9~\u0013W\r[6ocR\u0004\"AL)\u0005\rI\u0003AQ1\u00012\u0005\ty\u0005\u0007\u0005\u0002/)\u00121Q\u000b\u0001CC\u0002E\u0012!aT\u0019\u0011\u00059:FA\u0002-\u0001\t\u000b\u0007\u0011G\u0001\u0002PeA\u0011aF\u0017\u0003\u00077\u0002!)\u0019A\u0019\u0003\u0005=\u001b\u0004C\u0001\u0018^\t\u0019q\u0006\u0001\"b\u0001c\t\u0011q\n\u000e\t\u0003]\u0001$a!\u0019\u0001\u0005\u0006\u0004\t$AA(6!\tq3\r\u0002\u0004e\u0001\u0011\u0015\r!\r\u0002\u0003\u001fZ\u0002\"A\f4\u0005\r\u001d\u0004AQ1\u00012\u0005\tyu\u0007\u0005\u0002/S\u00121!\u000e\u0001CC\u0002E\u0012!a\u0014\u001d\u0011\u00059bGAB7\u0001\t\u000b\u0007\u0011G\u0001\u0002PsA\u0011af\u001c\u0003\u0007a\u0002!)\u0019A\u0019\u0003\u0007=\u000b\u0004\u0007\u0005\u0002/e\u001211\u000f\u0001CC\u0002E\u00121aT\u00192!\tqS\u000f\u0002\u0004w\u0001\u0011\u0015\r!\r\u0002\u0004\u001fF\u0012\u0004\"B\u001e\u0003\u0001\u0004aDCA(z\u0011\u0015Q8\u00011\u0001|\u0003\u0011q\u0017-\\3\u0011\u0007q\f9AD\u0002~\u0003\u0007\u0001\"A \u001b\u000e\u0003}T1!!\u0001'\u0003\u0019a$o\\8u}%\u0019\u0011Q\u0001\u001b\u0002\rA\u0013X\rZ3g\u0013\u0011\tI!a\u0003\u0003\rM#(/\u001b8h\u0015\r\t)\u0001\u000e\u000b\u001e\u001f\u0006=\u0011\u0011DA\u0012\u0003S\ty#!\u000e\u0002<\u0005\u0005\u0013qIA'\u0003'\nI&a\u0018\u0002f!9\u0011\u0011\u0003\u0003A\u0002\u0005M\u0011AA5o!\u0011Q\u0013QC\u0017\n\u0007\u0005]!EA\u0003J]2,G\u000fC\u0004\u0002\u001c\u0011\u0001\r!!\b\u0002\t=,H\u000f\r\t\u0005U\u0005}\u0001+C\u0002\u0002\"\t\u0012aaT;uY\u0016$\bbBA\u0013\t\u0001\u0007\u0011qE\u0001\u0005_V$\u0018\u0007\u0005\u0003+\u0003?\u0019\u0006bBA\u0016\t\u0001\u0007\u0011QF\u0001\u0005_V$(\u0007\u0005\u0003+\u0003?1\u0006bBA\u0019\t\u0001\u0007\u00111G\u0001\u0005_V$8\u0007\u0005\u0003+\u0003?I\u0006bBA\u001c\t\u0001\u0007\u0011\u0011H\u0001\u0005_V$H\u0007\u0005\u0003+\u0003?a\u0006bBA\u001f\t\u0001\u0007\u0011qH\u0001\u0005_V$X\u0007\u0005\u0003+\u0003?y\u0006bBA\"\t\u0001\u0007\u0011QI\u0001\u0005_V$h\u0007\u0005\u0003+\u0003?\u0011\u0007bBA%\t\u0001\u0007\u00111J\u0001\u0005_V$x\u0007\u0005\u0003+\u0003?)\u0007bBA(\t\u0001\u0007\u0011\u0011K\u0001\u0005_V$\b\b\u0005\u0003+\u0003?A\u0007bBA+\t\u0001\u0007\u0011qK\u0001\u0005_V$\u0018\b\u0005\u0003+\u0003?Y\u0007bBA.\t\u0001\u0007\u0011QL\u0001\u0006_V$\u0018\u0007\r\t\u0005U\u0005}a\u000eC\u0004\u0002b\u0011\u0001\r!a\u0019\u0002\u000b=,H/M\u0019\u0011\t)\ny\"\u001d\u0005\b\u0003O\"\u0001\u0019AA5\u0003\u0015yW\u000f^\u00193!\u0011Q\u0013q\u0004;\u0002\u0013\r|gn\u001d;sk\u000e$HcA\u0015\u0002p!1\u0011\u0011O\u0003A\u0002q\nA!\u001b8ji\u0006AA-Z3q\u0007>\u0004\u0018\u0010F\u0001P+\t\tI\bE\u0003+\u0003?\tYH\u000b\u0002Q\t\u0006)q.\u001e;1AU\u0011\u0011\u0011\u0011\t\u0006U\u0005}\u00111\u0011\u0016\u0003'\u0012\u000bQa\\;uc\u0001*\"!!#\u0011\u000b)\ny\"a#+\u0005Y#\u0015!B8viJ\u0002SCAAI!\u0015Q\u0013qDAJU\tIF)A\u0003pkR\u001c\u0004%\u0006\u0002\u0002\u001aB)!&a\b\u0002\u001c*\u0012A\fR\u0001\u0006_V$H\u0007I\u000b\u0003\u0003C\u0003RAKA\u0010\u0003GS#a\u0018#\u0002\u000b=,H/\u000e\u0011\u0016\u0005\u0005%\u0006#\u0002\u0016\u0002 \u0005-&F\u00012E\u0003\u0015yW\u000f\u001e\u001c!+\t\t\t\fE\u0003+\u0003?\t\u0019L\u000b\u0002f\t\u0006)q.\u001e;8AU\u0011\u0011\u0011\u0018\t\u0006U\u0005}\u00111\u0018\u0016\u0003Q\u0012\u000bQa\\;uq\u0001*\"!!1\u0011\u000b)\ny\"a1+\u0005-$\u0015!B8vif\u0002SCAAe!\u0015Q\u0013qDAfU\tqG)\u0001\u0004pkR\f\u0004\u0007I\u000b\u0003\u0003#\u0004RAKA\u0010\u0003'T#!\u001d#\u0002\r=,H/M\u0019!+\t\tI\u000eE\u0003+\u0003?\tYN\u000b\u0002u\t\u00061q.\u001e;2e\u0001\u0002")
public class FanOutShape13<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12>
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

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape13<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12>(init2);
    }

    @Override
    public FanOutShape13<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12> deepCopy() {
        return (FanOutShape13)super.deepCopy();
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

    public FanOutShape13(FanOutShape.Init<I> _init) {
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
    }

    public FanOutShape13(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape13(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7, Outlet<O8> out8, Outlet<O9> out9, Outlet<O10> out10, Outlet<O11> out11, Outlet<O12> out12) {
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
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet13).$colon$colon(outlet12).$colon$colon(outlet11).$colon$colon(outlet10).$colon$colon(outlet9).$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

