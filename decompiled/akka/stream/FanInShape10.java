/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005ee\u0001B\u000e\u001d\u0001\u0005B\u0001\"\u000e\u0001\u0003\u0002\u0003\u0006IA\u000e\u0005\u0006{\u0001!\tA\u0010\u0005\u0006{\u0001!\ta\u0018\u0005\u0006{\u0001!\t!\u001c\u0005\b\u0003O\u0001A\u0011KA\u0015\u0011\u001d\t)\u0005\u0001C!\u0003\u000fB\u0001b\u001c\u0001C\u0002\u0013\u0005\u0011\u0011\n\u0005\t\u0003\u001f\u0002\u0001\u0015!\u0003\u0002L!AA\u000f\u0001b\u0001\n\u0003\t\t\u0006\u0003\u0005\u0002X\u0001\u0001\u000b\u0011BA*\u0011!9\bA1A\u0005\u0002\u0005e\u0003\u0002CA0\u0001\u0001\u0006I!a\u0017\t\u0011i\u0004!\u0019!C\u0001\u0003CB\u0001\"a\u001a\u0001A\u0003%\u00111\r\u0005\t{\u0002\u0011\r\u0011\"\u0001\u0002j!A\u0011q\u000e\u0001!\u0002\u0013\tY\u0007C\u0005\u0002\u0002\u0001\u0011\r\u0011\"\u0001\u0002r!A\u0011q\u000f\u0001!\u0002\u0013\t\u0019\bC\u0005\u0002\b\u0001\u0011\r\u0011\"\u0001\u0002z!A\u0011q\u0010\u0001!\u0002\u0013\tY\bC\u0005\u0002\u000e\u0001\u0011\r\u0011\"\u0001\u0002\u0002\"A\u0011q\u0011\u0001!\u0002\u0013\t\u0019\tC\u0005\u0002\u0014\u0001\u0011\r\u0011\"\u0001\u0002\n\"A\u0011q\u0012\u0001!\u0002\u0013\tY\tC\u0005\u0002\u001a\u0001\u0011\r\u0011\"\u0001\u0002\u0012\"A\u0011q\u0013\u0001!\u0002\u0013\t\u0019J\u0001\u0007GC:Len\u00155ba\u0016\f\u0004G\u0003\u0002\u001e=\u000511\u000f\u001e:fC6T\u0011aH\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u0019\t\nEi\u0012&N!N3\u0016\fX\u0015\u0014\u0005\u0001\u0019\u0003c\u0001\u0013&O5\tA$\u0003\u0002'9\tQa)\u00198J]NC\u0017\r]3\u0011\u0005!JC\u0002\u0001\u0003\u0007U\u0001!)\u0019A\u0016\u0003\u0003=\u000b\"\u0001\f\u001a\u0011\u00055\u0002T\"\u0001\u0018\u000b\u0003=\nQa]2bY\u0006L!!\r\u0018\u0003\u000f9{G\u000f[5oOB\u0011QfM\u0005\u0003i9\u00121!\u00118z\u0003\u0015y\u0016N\\5u!\r9$h\n\b\u0003IaJ!!\u000f\u000f\u0002\u0015\u0019\u000bg.\u00138TQ\u0006\u0004X-\u0003\u0002<y\t!\u0011J\\5u\u0015\tID$\u0001\u0004=S:LGO\u0010\u000b\u0003\u007fy\u0003R\u0002\n\u0001A\u0007\u001aKEj\u0014*V1n;\u0003C\u0001\u0015B\t\u0019\u0011\u0005\u0001#b\u0001W\t\u0011A\u000b\r\t\u0003Q\u0011#a!\u0012\u0001\t\u0006\u0004Y#A\u0001+2!\tAs\t\u0002\u0004I\u0001!\u0015\ra\u000b\u0002\u0003)J\u0002\"\u0001\u000b&\u0005\r-\u0003\u0001R1\u0001,\u0005\t!6\u0007\u0005\u0002)\u001b\u00121a\n\u0001EC\u0002-\u0012!\u0001\u0016\u001b\u0011\u0005!\u0002FAB)\u0001\u0011\u000b\u00071F\u0001\u0002UkA\u0011\u0001f\u0015\u0003\u0007)\u0002A)\u0019A\u0016\u0003\u0005Q3\u0004C\u0001\u0015W\t\u00199\u0006\u0001#b\u0001W\t\u0011Ak\u000e\t\u0003Qe#aA\u0017\u0001\t\u0006\u0004Y#A\u0001+9!\tAC\f\u0002\u0004^\u0001!\u0015\ra\u000b\u0002\u0003)fBQ!\u000e\u0002A\u0002Y\"\"a\u00101\t\u000b\u0005\u001c\u0001\u0019\u00012\u0002\t9\fW.\u001a\t\u0003G*t!\u0001\u001a5\u0011\u0005\u0015tS\"\u00014\u000b\u0005\u001d\u0004\u0013A\u0002\u001fs_>$h(\u0003\u0002j]\u00051\u0001K]3eK\u001aL!a\u001b7\u0003\rM#(/\u001b8h\u0015\tIg\u0006F\t@]N4\u0018\u0010`@\u0002\u0006\u0005-\u0011\u0011CA\f\u0003;AQa\u001c\u0003A\u0002A\f1!\u001b81!\r!\u0013\u000fQ\u0005\u0003er\u0011Q!\u00138mKRDQ\u0001\u001e\u0003A\u0002U\f1!\u001b82!\r!\u0013o\u0011\u0005\u0006o\u0012\u0001\r\u0001_\u0001\u0004S:\u0014\u0004c\u0001\u0013r\r\")!\u0010\u0002a\u0001w\u0006\u0019\u0011N\\\u001a\u0011\u0007\u0011\n\u0018\nC\u0003~\t\u0001\u0007a0A\u0002j]R\u00022\u0001J9M\u0011\u001d\t\t\u0001\u0002a\u0001\u0003\u0007\t1!\u001b86!\r!\u0013o\u0014\u0005\b\u0003\u000f!\u0001\u0019AA\u0005\u0003\rIgN\u000e\t\u0004IE\u0014\u0006bBA\u0007\t\u0001\u0007\u0011qB\u0001\u0004S:<\u0004c\u0001\u0013r+\"9\u00111\u0003\u0003A\u0002\u0005U\u0011aA5oqA\u0019A%\u001d-\t\u000f\u0005eA\u00011\u0001\u0002\u001c\u0005\u0019\u0011N\\\u001d\u0011\u0007\u0011\n8\fC\u0004\u0002 \u0011\u0001\r!!\t\u0002\u0007=,H\u000f\u0005\u0003%\u0003G9\u0013bAA\u00139\t1q*\u001e;mKR\f\u0011bY8ogR\u0014Xo\u0019;\u0015\u0007\r\nY\u0003C\u0004\u0002.\u0015\u0001\r!a\f\u0002\t%t\u0017\u000e\u001e\t\u0005oi\n\tDK\u0002(\u0003gY#!!\u000e\u0011\t\u0005]\u0012\u0011I\u0007\u0003\u0003sQA!a\u000f\u0002>\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003\u007fq\u0013AC1o]>$\u0018\r^5p]&!\u00111IA\u001d\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\tI\u0016,\u0007oQ8qsR\tq(\u0006\u0002\u0002LA!A%]A'U\r\u0001\u00151G\u0001\u0005S:\u0004\u0004%\u0006\u0002\u0002TA!A%]A+U\r\u0019\u00151G\u0001\u0005S:\f\u0004%\u0006\u0002\u0002\\A!A%]A/U\r1\u00151G\u0001\u0005S:\u0014\u0004%\u0006\u0002\u0002dA!A%]A3U\rI\u00151G\u0001\u0005S:\u001c\u0004%\u0006\u0002\u0002lA!A%]A7U\ra\u00151G\u0001\u0005S:$\u0004%\u0006\u0002\u0002tA!A%]A;U\ry\u00151G\u0001\u0005S:,\u0004%\u0006\u0002\u0002|A!A%]A?U\r\u0011\u00161G\u0001\u0005S:4\u0004%\u0006\u0002\u0002\u0004B!A%]ACU\r)\u00161G\u0001\u0005S:<\u0004%\u0006\u0002\u0002\fB!A%]AGU\rA\u00161G\u0001\u0005S:D\u0004%\u0006\u0002\u0002\u0014B!A%]AKU\rY\u00161G\u0001\u0005S:L\u0004\u0005")
public class FanInShape10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, O>
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

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, O>(init2);
    }

    @Override
    public FanInShape10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, O> deepCopy() {
        return (FanInShape10)super.deepCopy();
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

    public FanInShape10(FanInShape.Init<O> _init) {
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
    }

    public FanInShape10(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape10(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Inlet<T8> in8, Inlet<T9> in9, Outlet<O> out) {
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
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet10).$colon$colon(inlet9).$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

