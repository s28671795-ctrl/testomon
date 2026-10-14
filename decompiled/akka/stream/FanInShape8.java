/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005%d\u0001B\f\u0019\u0001uA\u0001\"\r\u0001\u0003\u0002\u0003\u0006IA\r\u0005\u0006s\u0001!\tA\u000f\u0005\u0006s\u0001!\t!\u0016\u0005\u0006s\u0001!\ta\u0019\u0005\b\u0003\u000f\u0001A\u0011KA\u0005\u0011\u001d\t)\u0003\u0001C!\u0003OA\u0001\"\u001a\u0001C\u0002\u0013\u0005\u0011\u0011\u0006\u0005\t\u0003_\u0001\u0001\u0015!\u0003\u0002,!A!\u000e\u0001b\u0001\n\u0003\t\t\u0004\u0003\u0005\u00028\u0001\u0001\u000b\u0011BA\u001a\u0011!i\u0007A1A\u0005\u0002\u0005e\u0002\u0002CA \u0001\u0001\u0006I!a\u000f\t\u0011A\u0004!\u0019!C\u0001\u0003\u0003B\u0001\"a\u0012\u0001A\u0003%\u00111\t\u0005\tg\u0002\u0011\r\u0011\"\u0001\u0002J!A\u0011q\n\u0001!\u0002\u0013\tY\u0005\u0003\u0005w\u0001\t\u0007I\u0011AA)\u0011!\t9\u0006\u0001Q\u0001\n\u0005M\u0003\u0002C=\u0001\u0005\u0004%\t!!\u0017\t\u0011\u0005}\u0003\u0001)A\u0005\u00037B\u0001\u0002 \u0001C\u0002\u0013\u0005\u0011\u0011\r\u0005\t\u0003O\u0002\u0001\u0015!\u0003\u0002d\tYa)\u00198J]NC\u0017\r]39\u0015\tI\"$\u0001\u0004tiJ,\u0017-\u001c\u0006\u00027\u0005!\u0011m[6b\u0007\u0001)\"BH\u001fA\u0007\u001aKEj\u0014*&'\t\u0001q\u0004E\u0002!C\rj\u0011\u0001G\u0005\u0003Ea\u0011!BR1o\u0013:\u001c\u0006.\u00199f!\t!S\u0005\u0004\u0001\u0005\r\u0019\u0002AQ1\u0001(\u0005\u0005y\u0015C\u0001\u0015/!\tIC&D\u0001+\u0015\u0005Y\u0013!B:dC2\f\u0017BA\u0017+\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!K\u0018\n\u0005AR#aA!os\u0006)q,\u001b8jiB\u00191GN\u0012\u000f\u0005\u0001\"\u0014BA\u001b\u0019\u0003)1\u0015M\\%o'\"\f\u0007/Z\u0005\u0003oa\u0012A!\u00138ji*\u0011Q\u0007G\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005m\"\u0006c\u0003\u0011\u0001y}\u0012U\tS&O#\u000e\u0002\"\u0001J\u001f\u0005\ry\u0002\u0001R1\u0001(\u0005\t!\u0006\u0007\u0005\u0002%\u0001\u00121\u0011\t\u0001EC\u0002\u001d\u0012!\u0001V\u0019\u0011\u0005\u0011\u001aEA\u0002#\u0001\u0011\u000b\u0007qE\u0001\u0002UeA\u0011AE\u0012\u0003\u0007\u000f\u0002A)\u0019A\u0014\u0003\u0005Q\u001b\u0004C\u0001\u0013J\t\u0019Q\u0005\u0001#b\u0001O\t\u0011A\u000b\u000e\t\u0003I1#a!\u0014\u0001\t\u0006\u00049#A\u0001+6!\t!s\n\u0002\u0004Q\u0001!\u0015\ra\n\u0002\u0003)Z\u0002\"\u0001\n*\u0005\rM\u0003\u0001R1\u0001(\u0005\t!v\u0007C\u00032\u0005\u0001\u0007!\u0007\u0006\u0002<-\")qk\u0001a\u00011\u0006!a.Y7f!\tI\u0006M\u0004\u0002[=B\u00111LK\u0007\u00029*\u0011Q\fH\u0001\u0007yI|w\u000e\u001e \n\u0005}S\u0013A\u0002)sK\u0012,g-\u0003\u0002bE\n11\u000b\u001e:j]\u001eT!a\u0018\u0016\u0015\u0015m\"\u0017\u000e\\8skb\\h\u0010C\u0003f\t\u0001\u0007a-A\u0002j]B\u00022\u0001I4=\u0013\tA\u0007DA\u0003J]2,G\u000fC\u0003k\t\u0001\u00071.A\u0002j]F\u00022\u0001I4@\u0011\u0015iG\u00011\u0001o\u0003\rIgN\r\t\u0004A\u001d\u0014\u0005\"\u00029\u0005\u0001\u0004\t\u0018aA5ogA\u0019\u0001eZ#\t\u000bM$\u0001\u0019\u0001;\u0002\u0007%tG\u0007E\u0002!O\"CQA\u001e\u0003A\u0002]\f1!\u001b86!\r\u0001sm\u0013\u0005\u0006s\u0012\u0001\rA_\u0001\u0004S:4\u0004c\u0001\u0011h\u001d\")A\u0010\u0002a\u0001{\u0006\u0019\u0011N\\\u001c\u0011\u0007\u0001:\u0017\u000b\u0003\u0004\u0000\t\u0001\u0007\u0011\u0011A\u0001\u0004_V$\b\u0003\u0002\u0011\u0002\u0004\rJ1!!\u0002\u0019\u0005\u0019yU\u000f\u001e7fi\u0006I1m\u001c8tiJ,8\r\u001e\u000b\u0004?\u0005-\u0001bBA\u0007\u000b\u0001\u0007\u0011qB\u0001\u0005S:LG\u000f\u0005\u00034m\u0005E!fA\u0012\u0002\u0014-\u0012\u0011Q\u0003\t\u0005\u0003/\t\t#\u0004\u0002\u0002\u001a)!\u00111DA\u000f\u0003%)hn\u00195fG.,GMC\u0002\u0002 )\n!\"\u00198o_R\fG/[8o\u0013\u0011\t\u0019#!\u0007\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\u0005eK\u0016\u00048i\u001c9z)\u0005YTCAA\u0016!\u0011\u0001s-!\f+\u0007q\n\u0019\"\u0001\u0003j]B\u0002SCAA\u001a!\u0011\u0001s-!\u000e+\u0007}\n\u0019\"\u0001\u0003j]F\u0002SCAA\u001e!\u0011\u0001s-!\u0010+\u0007\t\u000b\u0019\"\u0001\u0003j]J\u0002SCAA\"!\u0011\u0001s-!\u0012+\u0007\u0015\u000b\u0019\"\u0001\u0003j]N\u0002SCAA&!\u0011\u0001s-!\u0014+\u0007!\u000b\u0019\"\u0001\u0003j]R\u0002SCAA*!\u0011\u0001s-!\u0016+\u0007-\u000b\u0019\"\u0001\u0003j]V\u0002SCAA.!\u0011\u0001s-!\u0018+\u00079\u000b\u0019\"\u0001\u0003j]Z\u0002SCAA2!\u0011\u0001s-!\u001a+\u0007E\u000b\u0019\"\u0001\u0003j]^\u0002\u0003")
public class FanInShape8<T0, T1, T2, T3, T4, T5, T6, T7, O>
extends FanInShape<O> {
    private final Inlet<T0> in0;
    private final Inlet<T1> in1;
    private final Inlet<T2> in2;
    private final Inlet<T3> in3;
    private final Inlet<T4> in4;
    private final Inlet<T5> in5;
    private final Inlet<T6> in6;
    private final Inlet<T7> in7;

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape8<T0, T1, T2, T3, T4, T5, T6, T7, O>(init2);
    }

    @Override
    public FanInShape8<T0, T1, T2, T3, T4, T5, T6, T7, O> deepCopy() {
        return (FanInShape8)super.deepCopy();
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

    public FanInShape8(FanInShape.Init<O> _init) {
        super(_init);
        this.in0 = this.newInlet("in0");
        this.in1 = this.newInlet("in1");
        this.in2 = this.newInlet("in2");
        this.in3 = this.newInlet("in3");
        this.in4 = this.newInlet("in4");
        this.in5 = this.newInlet("in5");
        this.in6 = this.newInlet("in6");
        this.in7 = this.newInlet("in7");
    }

    public FanInShape8(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape8(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Outlet<O> out) {
        Inlet<T0> inlet = in0;
        Inlet<T1> inlet2 = in1;
        Inlet<T2> inlet3 = in2;
        Inlet<T3> inlet4 = in3;
        Inlet<T4> inlet5 = in4;
        Inlet<T5> inlet6 = in5;
        Inlet<T6> inlet7 = in6;
        Inlet<T7> inlet8 = in7;
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

