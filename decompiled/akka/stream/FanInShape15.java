/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tEa\u0001B\u0013'\u0001-B\u0001b\u0010\u0001\u0003\u0002\u0003\u0006I\u0001\u0011\u0005\u0006\u000f\u0002!\t\u0001\u0013\u0005\u0006\u000f\u0002!\t\u0001\u001f\u0005\u0007\u000f\u0002!\t!!\u0004\t\u000f\u0005]\u0004\u0001\"\u0015\u0002z!9\u0011Q\u0013\u0001\u0005B\u0005]\u0005\"CA\t\u0001\t\u0007I\u0011AAM\u0011!\ty\n\u0001Q\u0001\n\u0005m\u0005\"CA\u000e\u0001\t\u0007I\u0011AAQ\u0011!\t9\u000b\u0001Q\u0001\n\u0005\r\u0006\"CA\u0011\u0001\t\u0007I\u0011AAU\u0011!\ty\u000b\u0001Q\u0001\n\u0005-\u0006\"CA\u0014\u0001\t\u0007I\u0011AAY\u0011!\t9\f\u0001Q\u0001\n\u0005M\u0006\"CA\u0017\u0001\t\u0007I\u0011AA]\u0011!\ty\f\u0001Q\u0001\n\u0005m\u0006\"CA\u001a\u0001\t\u0007I\u0011AAa\u0011!\t9\r\u0001Q\u0001\n\u0005\r\u0007\"CA\u001d\u0001\t\u0007I\u0011AAe\u0011!\ty\r\u0001Q\u0001\n\u0005-\u0007\"CA \u0001\t\u0007I\u0011AAi\u0011!\t9\u000e\u0001Q\u0001\n\u0005M\u0007\"CA#\u0001\t\u0007I\u0011AAm\u0011!\ty\u000e\u0001Q\u0001\n\u0005m\u0007\"CA&\u0001\t\u0007I\u0011AAq\u0011!\t9\u000f\u0001Q\u0001\n\u0005\r\b\"CA)\u0001\t\u0007I\u0011AAu\u0011!\ty\u000f\u0001Q\u0001\n\u0005-\b\"CA,\u0001\t\u0007I\u0011AAy\u0011!\t9\u0010\u0001Q\u0001\n\u0005M\b\"CA/\u0001\t\u0007I\u0011AA}\u0011!\ty\u0010\u0001Q\u0001\n\u0005m\b\"CA2\u0001\t\u0007I\u0011\u0001B\u0001\u0011!\u00119\u0001\u0001Q\u0001\n\t\r\u0001\"CA5\u0001\t\u0007I\u0011\u0001B\u0005\u0011!\u0011y\u0001\u0001Q\u0001\n\t-!\u0001\u0004$b]&s7\u000b[1qKF*$BA\u0014)\u0003\u0019\u0019HO]3b[*\t\u0011&\u0001\u0003bW.\f7\u0001A\u000b\u0012Y-s\u0015\u000bV,[;\u0002\u001cg-\u001b7peV\u001c4C\u0001\u0001.!\rqs&M\u0007\u0002M%\u0011\u0001G\n\u0002\u000b\r\u0006t\u0017J\\*iCB,\u0007C\u0001\u001a4\u0019\u0001!a\u0001\u000e\u0001\u0005\u0006\u0004)$!A(\u0012\u0005Yb\u0004CA\u001c;\u001b\u0005A$\"A\u001d\u0002\u000bM\u001c\u0017\r\\1\n\u0005mB$a\u0002(pi\"Lgn\u001a\t\u0003ouJ!A\u0010\u001d\u0003\u0007\u0005s\u00170A\u0003`S:LG\u000fE\u0002B\tFr!A\f\"\n\u0005\r3\u0013A\u0003$b]&s7\u000b[1qK&\u0011QI\u0012\u0002\u0005\u0013:LGO\u0003\u0002DM\u00051A(\u001b8jiz\"\"!S<\u0011%9\u0002!*\u0014)T-fcvLY3iW:\fH/\r\t\u0003e-#a\u0001\u0014\u0001\t\u0006\u0004)$A\u0001+1!\t\u0011d\n\u0002\u0004P\u0001!\u0015\r!\u000e\u0002\u0003)F\u0002\"AM)\u0005\rI\u0003\u0001R1\u00016\u0005\t!&\u0007\u0005\u00023)\u00121Q\u000b\u0001EC\u0002U\u0012!\u0001V\u001a\u0011\u0005I:FA\u0002-\u0001\u0011\u000b\u0007QG\u0001\u0002UiA\u0011!G\u0017\u0003\u00077\u0002A)\u0019A\u001b\u0003\u0005Q+\u0004C\u0001\u001a^\t\u0019q\u0006\u0001#b\u0001k\t\u0011AK\u000e\t\u0003e\u0001$a!\u0019\u0001\t\u0006\u0004)$A\u0001+8!\t\u00114\r\u0002\u0004e\u0001!\u0015\r!\u000e\u0002\u0003)b\u0002\"A\r4\u0005\r\u001d\u0004\u0001R1\u00016\u0005\t!\u0016\b\u0005\u00023S\u00121!\u000e\u0001EC\u0002U\u00121\u0001V\u00191!\t\u0011D\u000e\u0002\u0004n\u0001!\u0015\r!\u000e\u0002\u0004)F\n\u0004C\u0001\u001ap\t\u0019\u0001\b\u0001#b\u0001k\t\u0019A+\r\u001a\u0011\u0005I\u0012HAB:\u0001\u0011\u000b\u0007QGA\u0002UcM\u0002\"AM;\u0005\rY\u0004\u0001R1\u00016\u0005\r!\u0016\u0007\u000e\u0005\u0006\u007f\t\u0001\r\u0001\u0011\u000b\u0003\u0013fDQA_\u0002A\u0002m\fAA\\1nKB\u0019A0a\u0002\u000f\u0007u\f\u0019\u0001\u0005\u0002\u007fq5\tqPC\u0002\u0002\u0002)\na\u0001\u0010:p_Rt\u0014bAA\u0003q\u00051\u0001K]3eK\u001aLA!!\u0003\u0002\f\t11\u000b\u001e:j]\u001eT1!!\u00029)\u0005J\u0015qBA\r\u0003?\t)#a\u000b\u00022\u0005]\u0012QHA\"\u0003\u0013\ny%!\u0016\u0002\\\u0005\u0005\u0014qMA7\u0011\u001d\t\t\u0002\u0002a\u0001\u0003'\t1!\u001b81!\u0011q\u0013Q\u0003&\n\u0007\u0005]aEA\u0003J]2,G\u000fC\u0004\u0002\u001c\u0011\u0001\r!!\b\u0002\u0007%t\u0017\u0007\u0005\u0003/\u0003+i\u0005bBA\u0011\t\u0001\u0007\u00111E\u0001\u0004S:\u0014\u0004\u0003\u0002\u0018\u0002\u0016ACq!a\n\u0005\u0001\u0004\tI#A\u0002j]N\u0002BALA\u000b'\"9\u0011Q\u0006\u0003A\u0002\u0005=\u0012aA5oiA!a&!\u0006W\u0011\u001d\t\u0019\u0004\u0002a\u0001\u0003k\t1!\u001b86!\u0011q\u0013QC-\t\u000f\u0005eB\u00011\u0001\u0002<\u0005\u0019\u0011N\u001c\u001c\u0011\t9\n)\u0002\u0018\u0005\b\u0003\u007f!\u0001\u0019AA!\u0003\rIgn\u000e\t\u0005]\u0005Uq\fC\u0004\u0002F\u0011\u0001\r!a\u0012\u0002\u0007%t\u0007\b\u0005\u0003/\u0003+\u0011\u0007bBA&\t\u0001\u0007\u0011QJ\u0001\u0004S:L\u0004\u0003\u0002\u0018\u0002\u0016\u0015Dq!!\u0015\u0005\u0001\u0004\t\u0019&\u0001\u0003j]F\u0002\u0004\u0003\u0002\u0018\u0002\u0016!Dq!a\u0016\u0005\u0001\u0004\tI&\u0001\u0003j]F\n\u0004\u0003\u0002\u0018\u0002\u0016-Dq!!\u0018\u0005\u0001\u0004\ty&\u0001\u0003j]F\u0012\u0004\u0003\u0002\u0018\u0002\u00169Dq!a\u0019\u0005\u0001\u0004\t)'\u0001\u0003j]F\u001a\u0004\u0003\u0002\u0018\u0002\u0016EDq!!\u001b\u0005\u0001\u0004\tY'\u0001\u0003j]F\"\u0004\u0003\u0002\u0018\u0002\u0016QDq!a\u001c\u0005\u0001\u0004\t\t(A\u0002pkR\u0004BALA:c%\u0019\u0011Q\u000f\u0014\u0003\r=+H\u000f\\3u\u0003%\u0019wN\\:ueV\u001cG\u000fF\u0002.\u0003wBq!! \u0006\u0001\u0004\ty(\u0001\u0003j]&$\b\u0003B!E\u0003\u0003S3!MABW\t\t)\t\u0005\u0003\u0002\b\u0006EUBAAE\u0015\u0011\tY)!$\u0002\u0013Ut7\r[3dW\u0016$'bAAHq\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005M\u0015\u0011\u0012\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017\u0001\u00033fKB\u001cu\u000e]=\u0015\u0003%+\"!a'\u0011\u000b9\n)\"!(+\u0007)\u000b\u0019)\u0001\u0003j]B\u0002SCAAR!\u0015q\u0013QCASU\ri\u00151Q\u0001\u0005S:\f\u0004%\u0006\u0002\u0002,B)a&!\u0006\u0002.*\u001a\u0001+a!\u0002\t%t'\u0007I\u000b\u0003\u0003g\u0003RALA\u000b\u0003kS3aUAB\u0003\u0011Ign\r\u0011\u0016\u0005\u0005m\u0006#\u0002\u0018\u0002\u0016\u0005u&f\u0001,\u0002\u0004\u0006!\u0011N\u001c\u001b!+\t\t\u0019\rE\u0003/\u0003+\t)MK\u0002Z\u0003\u0007\u000bA!\u001b86AU\u0011\u00111\u001a\t\u0006]\u0005U\u0011Q\u001a\u0016\u00049\u0006\r\u0015\u0001B5om\u0001*\"!a5\u0011\u000b9\n)\"!6+\u0007}\u000b\u0019)\u0001\u0003j]^\u0002SCAAn!\u0015q\u0013QCAoU\r\u0011\u00171Q\u0001\u0005S:D\u0004%\u0006\u0002\u0002dB)a&!\u0006\u0002f*\u001aQ-a!\u0002\t%t\u0017\bI\u000b\u0003\u0003W\u0004RALA\u000b\u0003[T3\u0001[AB\u0003\u0015Ig.\r\u0019!+\t\t\u0019\u0010E\u0003/\u0003+\t)PK\u0002l\u0003\u0007\u000bQ!\u001b82c\u0001*\"!a?\u0011\u000b9\n)\"!@+\u00079\f\u0019)A\u0003j]F\u0012\u0004%\u0006\u0002\u0003\u0004A)a&!\u0006\u0003\u0006)\u001a\u0011/a!\u0002\u000b%t\u0017g\r\u0011\u0016\u0005\t-\u0001#\u0002\u0018\u0002\u0016\t5!f\u0001;\u0002\u0004\u0006)\u0011N\\\u00195A\u0001")
public class FanInShape15<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, O>
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

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape15<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, O>(init2);
    }

    @Override
    public FanInShape15<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, O> deepCopy() {
        return (FanInShape15)super.deepCopy();
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

    public FanInShape15(FanInShape.Init<O> _init) {
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
    }

    public FanInShape15(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape15(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Inlet<T8> in8, Inlet<T9> in9, Inlet<T10> in10, Inlet<T11> in11, Inlet<T12> in12, Inlet<T13> in13, Inlet<T14> in14, Outlet<O> out) {
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
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet15).$colon$colon(inlet14).$colon$colon(inlet13).$colon$colon(inlet12).$colon$colon(inlet11).$colon$colon(inlet10).$colon$colon(inlet9).$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

