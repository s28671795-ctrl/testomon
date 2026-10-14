/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ef\u0001B\u000f\u001f\u0001\rB\u0001b\u000e\u0001\u0003\u0002\u0003\u0006I\u0001\u000f\u0005\u0006\u007f\u0001!\t\u0001\u0011\u0005\u0006\u007f\u0001!\t\u0001\u001a\u0005\u0006\u007f\u0001!\tA\u001d\u0005\b\u0003o\u0001A\u0011KA\u001d\u0011\u001d\t)\u0006\u0001C!\u0003/B\u0001\u0002\u001e\u0001C\u0002\u0013\u0005\u0011\u0011\f\u0005\t\u0003?\u0002\u0001\u0015!\u0003\u0002\\!A\u0011\u0010\u0001b\u0001\n\u0003\t\t\u0007\u0003\u0005\u0002h\u0001\u0001\u000b\u0011BA2\u0011!a\bA1A\u0005\u0002\u0005%\u0004\u0002CA8\u0001\u0001\u0006I!a\u001b\t\u0011}\u0004!\u0019!C\u0001\u0003cB\u0001\"a\u001e\u0001A\u0003%\u00111\u000f\u0005\n\u0003\u000b\u0001!\u0019!C\u0001\u0003sB\u0001\"a \u0001A\u0003%\u00111\u0010\u0005\n\u0003\u0017\u0001!\u0019!C\u0001\u0003\u0003C\u0001\"a\"\u0001A\u0003%\u00111\u0011\u0005\n\u0003#\u0001!\u0019!C\u0001\u0003\u0013C\u0001\"a$\u0001A\u0003%\u00111\u0012\u0005\n\u0003/\u0001!\u0019!C\u0001\u0003#C\u0001\"a&\u0001A\u0003%\u00111\u0013\u0005\n\u0003;\u0001!\u0019!C\u0001\u00033C\u0001\"a(\u0001A\u0003%\u00111\u0014\u0005\n\u0003G\u0001!\u0019!C\u0001\u0003CC\u0001\"a*\u0001A\u0003%\u00111\u0015\u0005\n\u0003S\u0001!\u0019!C\u0001\u0003SC\u0001\"a,\u0001A\u0003%\u00111\u0016\u0002\r\r\u0006t\u0017J\\*iCB,\u0017'\r\u0006\u0003?\u0001\naa\u001d;sK\u0006l'\"A\u0011\u0002\t\u0005\\7.Y\u0002\u0001+5!3IR%M\u001fJ+\u0006l\u00170bWM\u0011\u0001!\n\t\u0004M\u001dJS\"\u0001\u0010\n\u0005!r\"A\u0003$b]&s7\u000b[1qKB\u0011!f\u000b\u0007\u0001\t\u0019a\u0003\u0001\"b\u0001[\t\tq*\u0005\u0002/iA\u0011qFM\u0007\u0002a)\t\u0011'A\u0003tG\u0006d\u0017-\u0003\u00024a\t9aj\u001c;iS:<\u0007CA\u00186\u0013\t1\u0004GA\u0002B]f\fQaX5oSR\u00042!\u000f\u001f*\u001d\t1#(\u0003\u0002<=\u0005Qa)\u00198J]NC\u0017\r]3\n\u0005ur$\u0001B%oSRT!a\u000f\u0010\u0002\rqJg.\u001b;?)\t\t5\r\u0005\b'\u0001\t+\u0005j\u0013(R)^SV\fY\u0015\u0011\u0005)\u001aEA\u0002#\u0001\u0011\u000b\u0007QF\u0001\u0002UaA\u0011!F\u0012\u0003\u0007\u000f\u0002A)\u0019A\u0017\u0003\u0005Q\u000b\u0004C\u0001\u0016J\t\u0019Q\u0005\u0001#b\u0001[\t\u0011AK\r\t\u0003U1#a!\u0014\u0001\t\u0006\u0004i#A\u0001+4!\tQs\n\u0002\u0004Q\u0001!\u0015\r!\f\u0002\u0003)R\u0002\"A\u000b*\u0005\rM\u0003\u0001R1\u0001.\u0005\t!V\u0007\u0005\u0002++\u00121a\u000b\u0001EC\u00025\u0012!\u0001\u0016\u001c\u0011\u0005)BFAB-\u0001\u0011\u000b\u0007QF\u0001\u0002UoA\u0011!f\u0017\u0003\u00079\u0002A)\u0019A\u0017\u0003\u0005QC\u0004C\u0001\u0016_\t\u0019y\u0006\u0001#b\u0001[\t\u0011A+\u000f\t\u0003U\u0005$aA\u0019\u0001\t\u0006\u0004i#a\u0001+2a!)qG\u0001a\u0001qQ\u0011\u0011)\u001a\u0005\u0006M\u000e\u0001\raZ\u0001\u0005]\u0006lW\r\u0005\u0002i_:\u0011\u0011.\u001c\t\u0003UBj\u0011a\u001b\u0006\u0003Y\n\na\u0001\u0010:p_Rt\u0014B\u000181\u0003\u0019\u0001&/\u001a3fM&\u0011\u0001/\u001d\u0002\u0007'R\u0014\u0018N\\4\u000b\u00059\u0004D#F!tqnt\u00181AA\u0005\u0003\u001f\t)\"a\u0007\u0002\"\u0005\u001d\u0012Q\u0006\u0005\u0006i\u0012\u0001\r!^\u0001\u0004S:\u0004\u0004c\u0001\u0014w\u0005&\u0011qO\b\u0002\u0006\u0013:dW\r\u001e\u0005\u0006s\u0012\u0001\rA_\u0001\u0004S:\f\u0004c\u0001\u0014w\u000b\")A\u0010\u0002a\u0001{\u0006\u0019\u0011N\u001c\u001a\u0011\u0007\u00192\b\n\u0003\u0004\u0000\t\u0001\u0007\u0011\u0011A\u0001\u0004S:\u001c\u0004c\u0001\u0014w\u0017\"9\u0011Q\u0001\u0003A\u0002\u0005\u001d\u0011aA5oiA\u0019aE\u001e(\t\u000f\u0005-A\u00011\u0001\u0002\u000e\u0005\u0019\u0011N\\\u001b\u0011\u0007\u00192\u0018\u000bC\u0004\u0002\u0012\u0011\u0001\r!a\u0005\u0002\u0007%tg\u0007E\u0002'mRCq!a\u0006\u0005\u0001\u0004\tI\"A\u0002j]^\u00022A\n<X\u0011\u001d\ti\u0002\u0002a\u0001\u0003?\t1!\u001b89!\r1cO\u0017\u0005\b\u0003G!\u0001\u0019AA\u0013\u0003\rIg.\u000f\t\u0004MYl\u0006bBA\u0015\t\u0001\u0007\u00111F\u0001\u0005S:\f\u0004\u0007E\u0002'm\u0002Dq!a\f\u0005\u0001\u0004\t\t$A\u0002pkR\u0004BAJA\u001aS%\u0019\u0011Q\u0007\u0010\u0003\r=+H\u000f\\3u\u0003%\u0019wN\\:ueV\u001cG\u000fF\u0002&\u0003wAq!!\u0010\u0006\u0001\u0004\ty$\u0001\u0003j]&$\b\u0003B\u001d=\u0003\u0003R3!KA\"W\t\t)\u0005\u0005\u0003\u0002H\u0005ESBAA%\u0015\u0011\tY%!\u0014\u0002\u0013Ut7\r[3dW\u0016$'bAA(a\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005M\u0013\u0011\n\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017\u0001\u00033fKB\u001cu\u000e]=\u0015\u0003\u0005+\"!a\u0017\u0011\t\u00192\u0018Q\f\u0016\u0004\u0005\u0006\r\u0013\u0001B5oa\u0001*\"!a\u0019\u0011\t\u00192\u0018Q\r\u0016\u0004\u000b\u0006\r\u0013\u0001B5oc\u0001*\"!a\u001b\u0011\t\u00192\u0018Q\u000e\u0016\u0004\u0011\u0006\r\u0013\u0001B5oe\u0001*\"!a\u001d\u0011\t\u00192\u0018Q\u000f\u0016\u0004\u0017\u0006\r\u0013\u0001B5og\u0001*\"!a\u001f\u0011\t\u00192\u0018Q\u0010\u0016\u0004\u001d\u0006\r\u0013\u0001B5oi\u0001*\"!a!\u0011\t\u00192\u0018Q\u0011\u0016\u0004#\u0006\r\u0013\u0001B5ok\u0001*\"!a#\u0011\t\u00192\u0018Q\u0012\u0016\u0004)\u0006\r\u0013\u0001B5om\u0001*\"!a%\u0011\t\u00192\u0018Q\u0013\u0016\u0004/\u0006\r\u0013\u0001B5oo\u0001*\"!a'\u0011\t\u00192\u0018Q\u0014\u0016\u00045\u0006\r\u0013\u0001B5oq\u0001*\"!a)\u0011\t\u00192\u0018Q\u0015\u0016\u0004;\u0006\r\u0013\u0001B5os\u0001*\"!a+\u0011\t\u00192\u0018Q\u0016\u0016\u0004A\u0006\r\u0013!B5ocA\u0002\u0003")
public class FanInShape11<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, O>
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

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape11<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, O>(init2);
    }

    @Override
    public FanInShape11<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, O> deepCopy() {
        return (FanInShape11)super.deepCopy();
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

    public FanInShape11(FanInShape.Init<O> _init) {
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
    }

    public FanInShape11(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape11(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Inlet<T8> in8, Inlet<T9> in9, Inlet<T10> in10, Outlet<O> out) {
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
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet11).$colon$colon(inlet10).$colon$colon(inlet9).$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

