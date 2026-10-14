/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t%b\u0001B\u0014)\u00015B\u0001\"\u0011\u0001\u0003\u0002\u0003\u0006IA\u0011\u0005\u0006\u0013\u0002!\tA\u0013\u0005\u0006\u0013\u0002!\t! \u0005\u0007\u0013\u0002!\t!a\u0006\t\u000f\u0005\u001d\u0005\u0001\"\u0015\u0002\n\"9\u0011Q\u0015\u0001\u0005B\u0005\u001d\u0006\"CA\u000e\u0001\t\u0007I\u0011AAU\u0011!\ty\u000b\u0001Q\u0001\n\u0005-\u0006\"CA\u0013\u0001\t\u0007I\u0011AAY\u0011!\t9\f\u0001Q\u0001\n\u0005M\u0006\"CA\u0016\u0001\t\u0007I\u0011AA]\u0011!\ty\f\u0001Q\u0001\n\u0005m\u0006\"CA\u0019\u0001\t\u0007I\u0011AAa\u0011!\t9\r\u0001Q\u0001\n\u0005\r\u0007\"CA\u001c\u0001\t\u0007I\u0011AAe\u0011!\ty\r\u0001Q\u0001\n\u0005-\u0007\"CA\u001f\u0001\t\u0007I\u0011AAi\u0011!\t9\u000e\u0001Q\u0001\n\u0005M\u0007\"CA\"\u0001\t\u0007I\u0011AAm\u0011!\ty\u000e\u0001Q\u0001\n\u0005m\u0007\"CA%\u0001\t\u0007I\u0011AAq\u0011!\t9\u000f\u0001Q\u0001\n\u0005\r\b\"CA(\u0001\t\u0007I\u0011AAu\u0011!\ty\u000f\u0001Q\u0001\n\u0005-\b\"CA+\u0001\t\u0007I\u0011AAy\u0011!\t9\u0010\u0001Q\u0001\n\u0005M\b\"CA.\u0001\t\u0007I\u0011AA}\u0011!\ty\u0010\u0001Q\u0001\n\u0005m\b\"CA1\u0001\t\u0007I\u0011\u0001B\u0001\u0011!\u00119\u0001\u0001Q\u0001\n\t\r\u0001\"CA4\u0001\t\u0007I\u0011\u0001B\u0005\u0011!\u0011y\u0001\u0001Q\u0001\n\t-\u0001\"CA7\u0001\t\u0007I\u0011\u0001B\t\u0011!\u00119\u0002\u0001Q\u0001\n\tM\u0001\"CA:\u0001\t\u0007I\u0011\u0001B\r\u0011!\u0011y\u0002\u0001Q\u0001\n\tm\u0001\"CA=\u0001\t\u0007I\u0011\u0001B\u0011\u0011!\u00119\u0003\u0001Q\u0001\n\t\r\"\u0001\u0004$b]&s7\u000b[1qKF2$BA\u0015+\u0003\u0019\u0019HO]3b[*\t1&\u0001\u0003bW.\f7\u0001A\u000b\u0013]5\u00036KV-]?\n,\u0007n\u001b8ri^TXg\u0005\u0002\u0001_A\u0019\u0001'M\u001a\u000e\u0003!J!A\r\u0015\u0003\u0015\u0019\u000bg.\u00138TQ\u0006\u0004X\r\u0005\u00025k1\u0001AA\u0002\u001c\u0001\t\u000b\u0007qGA\u0001P#\tAd\b\u0005\u0002:y5\t!HC\u0001<\u0003\u0015\u00198-\u00197b\u0013\ti$HA\u0004O_RD\u0017N\\4\u0011\u0005ez\u0014B\u0001!;\u0005\r\te._\u0001\u0006?&t\u0017\u000e\u001e\t\u0004\u0007\u001a\u001bdB\u0001\u0019E\u0013\t)\u0005&\u0001\u0006GC:Len\u00155ba\u0016L!a\u0012%\u0003\t%s\u0017\u000e\u001e\u0006\u0003\u000b\"\na\u0001P5oSRtDCA&}!M\u0001\u0004\u0001T(S+b[f,\u00193hU6\u00048O^=4!\t!T\n\u0002\u0004O\u0001!\u0015\ra\u000e\u0002\u0003)B\u0002\"\u0001\u000e)\u0005\rE\u0003\u0001R1\u00018\u0005\t!\u0016\u0007\u0005\u00025'\u00121A\u000b\u0001EC\u0002]\u0012!\u0001\u0016\u001a\u0011\u0005Q2FAB,\u0001\u0011\u000b\u0007qG\u0001\u0002UgA\u0011A'\u0017\u0003\u00075\u0002A)\u0019A\u001c\u0003\u0005Q#\u0004C\u0001\u001b]\t\u0019i\u0006\u0001#b\u0001o\t\u0011A+\u000e\t\u0003i}#a\u0001\u0019\u0001\t\u0006\u00049$A\u0001+7!\t!$\r\u0002\u0004d\u0001!\u0015\ra\u000e\u0002\u0003)^\u0002\"\u0001N3\u0005\r\u0019\u0004\u0001R1\u00018\u0005\t!\u0006\b\u0005\u00025Q\u00121\u0011\u000e\u0001EC\u0002]\u0012!\u0001V\u001d\u0011\u0005QZGA\u00027\u0001\u0011\u000b\u0007qGA\u0002UcA\u0002\"\u0001\u000e8\u0005\r=\u0004\u0001R1\u00018\u0005\r!\u0016'\r\t\u0003iE$aA\u001d\u0001\t\u0006\u00049$a\u0001+2eA\u0011A\u0007\u001e\u0003\u0007k\u0002A)\u0019A\u001c\u0003\u0007Q\u000b4\u0007\u0005\u00025o\u00121\u0001\u0010\u0001EC\u0002]\u00121\u0001V\u00195!\t!$\u0010\u0002\u0004|\u0001!\u0015\ra\u000e\u0002\u0004)F*\u0004\"B!\u0003\u0001\u0004\u0011ECA&\u007f\u0011\u0019y8\u00011\u0001\u0002\u0002\u0005!a.Y7f!\u0011\t\u0019!!\u0005\u000f\t\u0005\u0015\u0011Q\u0002\t\u0004\u0003\u000fQTBAA\u0005\u0015\r\tY\u0001L\u0001\u0007yI|w\u000e\u001e \n\u0007\u0005=!(\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003'\t)B\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003\u001fQDcI&\u0002\u001a\u0005\r\u0012\u0011FA\u0018\u0003k\tY$!\u0011\u0002H\u00055\u00131KA-\u0003?\n)'a\u001b\u0002r\u0005]\u0014Q\u0010\u0005\b\u00037!\u0001\u0019AA\u000f\u0003\rIg\u000e\r\t\u0005a\u0005}A*C\u0002\u0002\"!\u0012Q!\u00138mKRDq!!\n\u0005\u0001\u0004\t9#A\u0002j]F\u0002B\u0001MA\u0010\u001f\"9\u00111\u0006\u0003A\u0002\u00055\u0012aA5oeA!\u0001'a\bS\u0011\u001d\t\t\u0004\u0002a\u0001\u0003g\t1!\u001b84!\u0011\u0001\u0014qD+\t\u000f\u0005]B\u00011\u0001\u0002:\u0005\u0019\u0011N\u001c\u001b\u0011\tA\ny\u0002\u0017\u0005\b\u0003{!\u0001\u0019AA \u0003\rIg.\u000e\t\u0005a\u0005}1\fC\u0004\u0002D\u0011\u0001\r!!\u0012\u0002\u0007%tg\u0007\u0005\u00031\u0003?q\u0006bBA%\t\u0001\u0007\u00111J\u0001\u0004S:<\u0004\u0003\u0002\u0019\u0002 \u0005Dq!a\u0014\u0005\u0001\u0004\t\t&A\u0002j]b\u0002B\u0001MA\u0010I\"9\u0011Q\u000b\u0003A\u0002\u0005]\u0013aA5osA!\u0001'a\bh\u0011\u001d\tY\u0006\u0002a\u0001\u0003;\nA!\u001b82aA!\u0001'a\bk\u0011\u001d\t\t\u0007\u0002a\u0001\u0003G\nA!\u001b82cA!\u0001'a\bn\u0011\u001d\t9\u0007\u0002a\u0001\u0003S\nA!\u001b82eA!\u0001'a\bq\u0011\u001d\ti\u0007\u0002a\u0001\u0003_\nA!\u001b82gA!\u0001'a\bt\u0011\u001d\t\u0019\b\u0002a\u0001\u0003k\nA!\u001b82iA!\u0001'a\bw\u0011\u001d\tI\b\u0002a\u0001\u0003w\nA!\u001b82kA!\u0001'a\bz\u0011\u001d\ty\b\u0002a\u0001\u0003\u0003\u000b1a\\;u!\u0011\u0001\u00141Q\u001a\n\u0007\u0005\u0015\u0005F\u0001\u0004PkRdW\r^\u0001\nG>t7\u000f\u001e:vGR$2aLAF\u0011\u001d\ti)\u0002a\u0001\u0003\u001f\u000bA!\u001b8jiB!1IRAIU\r\u0019\u00141S\u0016\u0003\u0003+\u0003B!a&\u0002\"6\u0011\u0011\u0011\u0014\u0006\u0005\u00037\u000bi*A\u0005v]\u000eDWmY6fI*\u0019\u0011q\u0014\u001e\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002$\u0006e%!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006AA-Z3q\u0007>\u0004\u0018\u0010F\u0001L+\t\tY\u000bE\u00031\u0003?\tiKK\u0002M\u0003'\u000bA!\u001b81AU\u0011\u00111\u0017\t\u0006a\u0005}\u0011Q\u0017\u0016\u0004\u001f\u0006M\u0015\u0001B5oc\u0001*\"!a/\u0011\u000bA\ny\"!0+\u0007I\u000b\u0019*\u0001\u0003j]J\u0002SCAAb!\u0015\u0001\u0014qDAcU\r)\u00161S\u0001\u0005S:\u001c\u0004%\u0006\u0002\u0002LB)\u0001'a\b\u0002N*\u001a\u0001,a%\u0002\t%tG\u0007I\u000b\u0003\u0003'\u0004R\u0001MA\u0010\u0003+T3aWAJ\u0003\u0011Ig.\u000e\u0011\u0016\u0005\u0005m\u0007#\u0002\u0019\u0002 \u0005u'f\u00010\u0002\u0014\u0006!\u0011N\u001c\u001c!+\t\t\u0019\u000fE\u00031\u0003?\t)OK\u0002b\u0003'\u000bA!\u001b88AU\u0011\u00111\u001e\t\u0006a\u0005}\u0011Q\u001e\u0016\u0004I\u0006M\u0015\u0001B5oq\u0001*\"!a=\u0011\u000bA\ny\"!>+\u0007\u001d\f\u0019*\u0001\u0003j]f\u0002SCAA~!\u0015\u0001\u0014qDA\u007fU\rQ\u00171S\u0001\u0006S:\f\u0004\u0007I\u000b\u0003\u0005\u0007\u0001R\u0001MA\u0010\u0005\u000bQ3!\\AJ\u0003\u0015Ig.M\u0019!+\t\u0011Y\u0001E\u00031\u0003?\u0011iAK\u0002q\u0003'\u000bQ!\u001b82e\u0001*\"Aa\u0005\u0011\u000bA\nyB!\u0006+\u0007M\f\u0019*A\u0003j]F\u001a\u0004%\u0006\u0002\u0003\u001cA)\u0001'a\b\u0003\u001e)\u001aa/a%\u0002\u000b%t\u0017\u0007\u000e\u0011\u0016\u0005\t\r\u0002#\u0002\u0019\u0002 \t\u0015\"fA=\u0002\u0014\u0006)\u0011N\\\u00196A\u0001")
public class FanInShape16<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, O>
extends FanInShape<O> {
    private final Inlet<T0> in0;
    private final Inlet<T1> in1;
    private final Inlet<T2> in2;
    private final Inlet<T3> in3;
    private final Inlet<T4> in4;
    private final Inlet<T5> in5;
    private final Inlet<T6> in6;
    private final Inlet<T7> in7;
    private final Inlet<T8> in8;
    private final Inlet<T9> in9;
    private final Inlet<T10> in10;
    private final Inlet<T11> in11;
    private final Inlet<T12> in12;
    private final Inlet<T13> in13;
    private final Inlet<T14> in14;
    private final Inlet<T15> in15;

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape16<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, O>(init2);
    }

    @Override
    public FanInShape16<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, O> deepCopy() {
        return (FanInShape16)super.deepCopy();
    }

    public Inlet<T0> in0() {
        return this.in0;
    }

    public Inlet<T1> in1() {
        return this.in1;
    }

    public Inlet<T2> in2() {
        return this.in2;
    }

    public Inlet<T3> in3() {
        return this.in3;
    }

    public Inlet<T4> in4() {
        return this.in4;
    }

    public Inlet<T5> in5() {
        return this.in5;
    }

    public Inlet<T6> in6() {
        return this.in6;
    }

    public Inlet<T7> in7() {
        return this.in7;
    }

    public Inlet<T8> in8() {
        return this.in8;
    }

    public Inlet<T9> in9() {
        return this.in9;
    }

    public Inlet<T10> in10() {
        return this.in10;
    }

    public Inlet<T11> in11() {
        return this.in11;
    }

    public Inlet<T12> in12() {
        return this.in12;
    }

    public Inlet<T13> in13() {
        return this.in13;
    }

    public Inlet<T14> in14() {
        return this.in14;
    }

    public Inlet<T15> in15() {
        return this.in15;
    }

    public FanInShape16(FanInShape.Init<O> _init) {
        super(_init);
        this.in0 = this.newInlet("in0");
        this.in1 = this.newInlet("in1");
        this.in2 = this.newInlet("in2");
        this.in3 = this.newInlet("in3");
        this.in4 = this.newInlet("in4");
        this.in5 = this.newInlet("in5");
        this.in6 = this.newInlet("in6");
        this.in7 = this.newInlet("in7");
        this.in8 = this.newInlet("in8");
        this.in9 = this.newInlet("in9");
        this.in10 = this.newInlet("in10");
        this.in11 = this.newInlet("in11");
        this.in12 = this.newInlet("in12");
        this.in13 = this.newInlet("in13");
        this.in14 = this.newInlet("in14");
        this.in15 = this.newInlet("in15");
    }

    public FanInShape16(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape16(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Inlet<T8> in8, Inlet<T9> in9, Inlet<T10> in10, Inlet<T11> in11, Inlet<T12> in12, Inlet<T13> in13, Inlet<T14> in14, Inlet<T15> in15, Outlet<O> out) {
        Inlet<T0> inlet = in0;
        Inlet<T1> inlet2 = in1;
        Inlet<T2> inlet3 = in2;
        Inlet<T3> inlet4 = in3;
        Inlet<T4> inlet5 = in4;
        Inlet<T5> inlet6 = in5;
        Inlet<T6> inlet7 = in6;
        Inlet<T7> inlet8 = in7;
        Inlet<T8> inlet9 = in8;
        Inlet<T9> inlet10 = in9;
        Inlet<T10> inlet11 = in10;
        Inlet<T11> inlet12 = in11;
        Inlet<T12> inlet13 = in12;
        Inlet<T13> inlet14 = in13;
        Inlet<T14> inlet15 = in14;
        Inlet<T15> inlet16 = in15;
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet16).$colon$colon(inlet15).$colon$colon(inlet14).$colon$colon(inlet13).$colon$colon(inlet12).$colon$colon(inlet11).$colon$colon(inlet10).$colon$colon(inlet9).$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

