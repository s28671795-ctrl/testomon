/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005h\u0001B\u0011#\u0001\u001dB\u0001b\u000f\u0001\u0003\u0002\u0003\u0006I\u0001\u0010\u0005\u0006\u0007\u0002!\t\u0001\u0012\u0005\u0006\u0007\u0002!\tA\u001c\u0005\u0006\u0007\u0002!\t\u0001 \u0005\b\u0003/\u0002A\u0011KA-\u0011\u001d\t)\b\u0001C!\u0003oB\u0001B \u0001C\u0002\u0013\u0005\u0011\u0011\u0010\u0005\t\u0003\u007f\u0002\u0001\u0015!\u0003\u0002|!I\u0011q\u0001\u0001C\u0002\u0013\u0005\u0011\u0011\u0011\u0005\t\u0003\u000f\u0003\u0001\u0015!\u0003\u0002\u0004\"I\u0011Q\u0002\u0001C\u0002\u0013\u0005\u0011\u0011\u0012\u0005\t\u0003\u001f\u0003\u0001\u0015!\u0003\u0002\f\"I\u00111\u0003\u0001C\u0002\u0013\u0005\u0011\u0011\u0013\u0005\t\u0003/\u0003\u0001\u0015!\u0003\u0002\u0014\"I\u0011\u0011\u0004\u0001C\u0002\u0013\u0005\u0011\u0011\u0014\u0005\t\u0003?\u0003\u0001\u0015!\u0003\u0002\u001c\"I\u0011q\u0004\u0001C\u0002\u0013\u0005\u0011\u0011\u0015\u0005\t\u0003O\u0003\u0001\u0015!\u0003\u0002$\"I\u0011Q\u0005\u0001C\u0002\u0013\u0005\u0011\u0011\u0016\u0005\t\u0003_\u0003\u0001\u0015!\u0003\u0002,\"I\u00111\u0006\u0001C\u0002\u0013\u0005\u0011\u0011\u0017\u0005\t\u0003o\u0003\u0001\u0015!\u0003\u00024\"I\u0011\u0011\u0007\u0001C\u0002\u0013\u0005\u0011\u0011\u0018\u0005\t\u0003\u007f\u0003\u0001\u0015!\u0003\u0002<\"I\u0011q\u0007\u0001C\u0002\u0013\u0005\u0011\u0011\u0019\u0005\t\u0003\u000f\u0004\u0001\u0015!\u0003\u0002D\"I\u0011Q\b\u0001C\u0002\u0013\u0005\u0011\u0011\u001a\u0005\t\u0003\u001f\u0004\u0001\u0015!\u0003\u0002L\"I\u00111\t\u0001C\u0002\u0013\u0005\u0011\u0011\u001b\u0005\t\u0003/\u0004\u0001\u0015!\u0003\u0002T\"I\u0011\u0011\n\u0001C\u0002\u0013\u0005\u0011\u0011\u001c\u0005\t\u0003?\u0004\u0001\u0015!\u0003\u0002\\\naa)\u00198J]NC\u0017\r]32g)\u00111\u0005J\u0001\u0007gR\u0014X-Y7\u000b\u0003\u0015\nA!Y6lC\u000e\u0001Qc\u0004\u0015H\u00156\u00036KV-]?\n,\u0007n[\u0018\u0014\u0005\u0001I\u0003c\u0001\u0016,[5\t!%\u0003\u0002-E\tQa)\u00198J]NC\u0017\r]3\u0011\u00059zC\u0002\u0001\u0003\u0007a\u0001!)\u0019A\u0019\u0003\u0003=\u000b\"A\r\u001d\u0011\u0005M2T\"\u0001\u001b\u000b\u0003U\nQa]2bY\u0006L!a\u000e\u001b\u0003\u000f9{G\u000f[5oOB\u00111'O\u0005\u0003uQ\u00121!\u00118z\u0003\u0015y\u0016N\\5u!\ri\u0004)\f\b\u0003UyJ!a\u0010\u0012\u0002\u0015\u0019\u000bg.\u00138TQ\u0006\u0004X-\u0003\u0002B\u0005\n!\u0011J\\5u\u0015\ty$%\u0001\u0004=S:LGO\u0010\u000b\u0003\u000b6\u0004\u0002C\u000b\u0001G\u00132{%+\u0016-\\=\u0006$wM[\u0017\u0011\u00059:EA\u0002%\u0001\u0011\u000b\u0007\u0011G\u0001\u0002UaA\u0011aF\u0013\u0003\u0007\u0017\u0002A)\u0019A\u0019\u0003\u0005Q\u000b\u0004C\u0001\u0018N\t\u0019q\u0005\u0001#b\u0001c\t\u0011AK\r\t\u0003]A#a!\u0015\u0001\t\u0006\u0004\t$A\u0001+4!\tq3\u000b\u0002\u0004U\u0001!\u0015\r!\r\u0002\u0003)R\u0002\"A\f,\u0005\r]\u0003\u0001R1\u00012\u0005\t!V\u0007\u0005\u0002/3\u00121!\f\u0001EC\u0002E\u0012!\u0001\u0016\u001c\u0011\u00059bFAB/\u0001\u0011\u000b\u0007\u0011G\u0001\u0002UoA\u0011af\u0018\u0003\u0007A\u0002A)\u0019A\u0019\u0003\u0005QC\u0004C\u0001\u0018c\t\u0019\u0019\u0007\u0001#b\u0001c\t\u0011A+\u000f\t\u0003]\u0015$aA\u001a\u0001\t\u0006\u0004\t$a\u0001+2aA\u0011a\u0006\u001b\u0003\u0007S\u0002A)\u0019A\u0019\u0003\u0007Q\u000b\u0014\u0007\u0005\u0002/W\u00121A\u000e\u0001EC\u0002E\u00121\u0001V\u00193\u0011\u0015Y$\u00011\u0001=)\t)u\u000eC\u0003q\u0007\u0001\u0007\u0011/\u0001\u0003oC6,\u0007C\u0001:z\u001d\t\u0019x\u000f\u0005\u0002ui5\tQO\u0003\u0002wM\u00051AH]8pizJ!\u0001\u001f\u001b\u0002\rA\u0013X\rZ3g\u0013\tQ8P\u0001\u0004TiJLgn\u001a\u0006\u0003qR\"B$R?\u0002\u0006\u0005-\u0011\u0011CA\f\u0003;\t\u0019#!\u000b\u00020\u0005U\u00121HA!\u0003\u000f\ni\u0005C\u0003\u007f\t\u0001\u0007q0A\u0002j]B\u0002BAKA\u0001\r&\u0019\u00111\u0001\u0012\u0003\u000b%sG.\u001a;\t\u000f\u0005\u001dA\u00011\u0001\u0002\n\u0005\u0019\u0011N\\\u0019\u0011\t)\n\t!\u0013\u0005\b\u0003\u001b!\u0001\u0019AA\b\u0003\rIgN\r\t\u0005U\u0005\u0005A\nC\u0004\u0002\u0014\u0011\u0001\r!!\u0006\u0002\u0007%t7\u0007\u0005\u0003+\u0003\u0003y\u0005bBA\r\t\u0001\u0007\u00111D\u0001\u0004S:$\u0004\u0003\u0002\u0016\u0002\u0002ICq!a\b\u0005\u0001\u0004\t\t#A\u0002j]V\u0002BAKA\u0001+\"9\u0011Q\u0005\u0003A\u0002\u0005\u001d\u0012aA5omA!!&!\u0001Y\u0011\u001d\tY\u0003\u0002a\u0001\u0003[\t1!\u001b88!\u0011Q\u0013\u0011A.\t\u000f\u0005EB\u00011\u0001\u00024\u0005\u0019\u0011N\u001c\u001d\u0011\t)\n\tA\u0018\u0005\b\u0003o!\u0001\u0019AA\u001d\u0003\rIg.\u000f\t\u0005U\u0005\u0005\u0011\rC\u0004\u0002>\u0011\u0001\r!a\u0010\u0002\t%t\u0017\u0007\r\t\u0005U\u0005\u0005A\rC\u0004\u0002D\u0011\u0001\r!!\u0012\u0002\t%t\u0017'\r\t\u0005U\u0005\u0005q\rC\u0004\u0002J\u0011\u0001\r!a\u0013\u0002\t%t\u0017G\r\t\u0005U\u0005\u0005!\u000eC\u0004\u0002P\u0011\u0001\r!!\u0015\u0002\u0007=,H\u000f\u0005\u0003+\u0003'j\u0013bAA+E\t1q*\u001e;mKR\f\u0011bY8ogR\u0014Xo\u0019;\u0015\u0007%\nY\u0006C\u0004\u0002^\u0015\u0001\r!a\u0018\u0002\t%t\u0017\u000e\u001e\t\u0005{\u0001\u000b\tGK\u0002.\u0003GZ#!!\u001a\u0011\t\u0005\u001d\u0014\u0011O\u0007\u0003\u0003SRA!a\u001b\u0002n\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003_\"\u0014AC1o]>$\u0018\r^5p]&!\u00111OA5\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\tI\u0016,\u0007oQ8qsR\tQ)\u0006\u0002\u0002|A)!&!\u0001\u0002~)\u001aa)a\u0019\u0002\t%t\u0007\u0007I\u000b\u0003\u0003\u0007\u0003RAKA\u0001\u0003\u000bS3!SA2\u0003\u0011Ig.\r\u0011\u0016\u0005\u0005-\u0005#\u0002\u0016\u0002\u0002\u00055%f\u0001'\u0002d\u0005!\u0011N\u001c\u001a!+\t\t\u0019\nE\u0003+\u0003\u0003\t)JK\u0002P\u0003G\nA!\u001b84AU\u0011\u00111\u0014\t\u0006U\u0005\u0005\u0011Q\u0014\u0016\u0004%\u0006\r\u0014\u0001B5oi\u0001*\"!a)\u0011\u000b)\n\t!!*+\u0007U\u000b\u0019'\u0001\u0003j]V\u0002SCAAV!\u0015Q\u0013\u0011AAWU\rA\u00161M\u0001\u0005S:4\u0004%\u0006\u0002\u00024B)!&!\u0001\u00026*\u001a1,a\u0019\u0002\t%tw\u0007I\u000b\u0003\u0003w\u0003RAKA\u0001\u0003{S3AXA2\u0003\u0011Ig\u000e\u000f\u0011\u0016\u0005\u0005\r\u0007#\u0002\u0016\u0002\u0002\u0005\u0015'fA1\u0002d\u0005!\u0011N\\\u001d!+\t\tY\rE\u0003+\u0003\u0003\tiMK\u0002e\u0003G\nQ!\u001b82a\u0001*\"!a5\u0011\u000b)\n\t!!6+\u0007\u001d\f\u0019'A\u0003j]F\n\u0004%\u0006\u0002\u0002\\B)!&!\u0001\u0002^*\u001a!.a\u0019\u0002\u000b%t\u0017G\r\u0011")
public class FanInShape13<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, O>
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

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape13<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, O>(init2);
    }

    @Override
    public FanInShape13<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, O> deepCopy() {
        return (FanInShape13)super.deepCopy();
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

    public FanInShape13(FanInShape.Init<O> _init) {
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
    }

    public FanInShape13(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape13(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Inlet<T8> in8, Inlet<T9> in9, Inlet<T10> in10, Inlet<T11> in11, Inlet<T12> in12, Outlet<O> out) {
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
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet13).$colon$colon(inlet12).$colon$colon(inlet11).$colon$colon(inlet10).$colon$colon(inlet9).$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

