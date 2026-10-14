/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ec\u0001B\u000b\u0017\u0001mA\u0001b\f\u0001\u0003\u0002\u0003\u0006I\u0001\r\u0005\u0006o\u0001!\t\u0001\u000f\u0005\u0006o\u0001!\t\u0001\u0015\u0005\u0006o\u0001!\tA\u0018\u0005\u0006w\u0002!\t\u0006 \u0005\b\u0003+\u0001A\u0011IA\f\u0011!\u0001\u0007A1A\u0005\u0002\u0005e\u0001\u0002CA\u0010\u0001\u0001\u0006I!a\u0007\t\u0011\u0015\u0004!\u0019!C\u0001\u0003CA\u0001\"a\n\u0001A\u0003%\u00111\u0005\u0005\tQ\u0002\u0011\r\u0011\"\u0001\u0002*!A\u0011q\u0006\u0001!\u0002\u0013\tY\u0003\u0003\u0005l\u0001\t\u0007I\u0011AA\u0019\u0011!\t9\u0004\u0001Q\u0001\n\u0005M\u0002\u0002\u00038\u0001\u0005\u0004%\t!!\u000f\t\u0011\u0005}\u0002\u0001)A\u0005\u0003wA\u0001\"\u001d\u0001C\u0002\u0013\u0005\u0011\u0011\t\u0005\t\u0003\u000f\u0002\u0001\u0015!\u0003\u0002D!AA\u000f\u0001b\u0001\n\u0003\tI\u0005\u0003\u0005\u0002P\u0001\u0001\u000b\u0011BA&\u0005-1\u0015M\\%o'\"\f\u0007/Z\u001c\u000b\u0005]A\u0012AB:ue\u0016\fWNC\u0001\u001a\u0003\u0011\t7n[1\u0004\u0001UIAd\u000f B\t\u001eSUjI\n\u0003\u0001u\u00012AH\u0010\"\u001b\u00051\u0012B\u0001\u0011\u0017\u0005)1\u0015M\\%o'\"\f\u0007/\u001a\t\u0003E\rb\u0001\u0001\u0002\u0004%\u0001\u0011\u0015\r!\n\u0002\u0002\u001fF\u0011a\u0005\f\t\u0003O)j\u0011\u0001\u000b\u0006\u0002S\u0005)1oY1mC&\u00111\u0006\u000b\u0002\b\u001d>$\b.\u001b8h!\t9S&\u0003\u0002/Q\t\u0019\u0011I\\=\u0002\u000b}Kg.\u001b;\u0011\u0007E\"\u0014E\u0004\u0002\u001fe%\u00111GF\u0001\u000b\r\u0006t\u0017J\\*iCB,\u0017BA\u001b7\u0005\u0011Ie.\u001b;\u000b\u0005M2\u0012A\u0002\u001fj]&$h\b\u0006\u0002:\u001fBQa\u0004\u0001\u001e>\u0001\u000e3\u0015\nT\u0011\u0011\u0005\tZDA\u0002\u001f\u0001\u0011\u000b\u0007QE\u0001\u0002UaA\u0011!E\u0010\u0003\u0007\u007f\u0001A)\u0019A\u0013\u0003\u0005Q\u000b\u0004C\u0001\u0012B\t\u0019\u0011\u0005\u0001#b\u0001K\t\u0011AK\r\t\u0003E\u0011#a!\u0012\u0001\t\u0006\u0004)#A\u0001+4!\t\u0011s\t\u0002\u0004I\u0001!\u0015\r!\n\u0002\u0003)R\u0002\"A\t&\u0005\r-\u0003\u0001R1\u0001&\u0005\t!V\u0007\u0005\u0002#\u001b\u00121a\n\u0001EC\u0002\u0015\u0012!\u0001\u0016\u001c\t\u000b=\u0012\u0001\u0019\u0001\u0019\u0015\u0005e\n\u0006\"\u0002*\u0004\u0001\u0004\u0019\u0016\u0001\u00028b[\u0016\u0004\"\u0001V.\u000f\u0005UK\u0006C\u0001,)\u001b\u00059&B\u0001-\u001b\u0003\u0019a$o\\8u}%\u0011!\fK\u0001\u0007!J,G-\u001a4\n\u0005qk&AB*ue&twM\u0003\u0002[QQI\u0011h\u00183hU6\u00048O\u001e\u0005\u0006A\u0012\u0001\r!Y\u0001\u0004S:\u0004\u0004c\u0001\u0010cu%\u00111M\u0006\u0002\u0006\u0013:dW\r\u001e\u0005\u0006K\u0012\u0001\rAZ\u0001\u0004S:\f\u0004c\u0001\u0010c{!)\u0001\u000e\u0002a\u0001S\u0006\u0019\u0011N\u001c\u001a\u0011\u0007y\u0011\u0007\tC\u0003l\t\u0001\u0007A.A\u0002j]N\u00022A\b2D\u0011\u0015qG\u00011\u0001p\u0003\rIg\u000e\u000e\t\u0004=\t4\u0005\"B9\u0005\u0001\u0004\u0011\u0018aA5okA\u0019aDY%\t\u000bQ$\u0001\u0019A;\u0002\u0007%tg\u0007E\u0002\u001fE2CQa\u001e\u0003A\u0002a\f1a\\;u!\rq\u00120I\u0005\u0003uZ\u0011aaT;uY\u0016$\u0018!C2p]N$(/^2u)\tiR\u0010C\u0003\u007f\u000b\u0001\u0007q0\u0001\u0003j]&$\b\u0003B\u00195\u0003\u0003Q3!IA\u0002W\t\t)\u0001\u0005\u0003\u0002\b\u0005EQBAA\u0005\u0015\u0011\tY!!\u0004\u0002\u0013Ut7\r[3dW\u0016$'bAA\bQ\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005M\u0011\u0011\u0002\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017\u0001\u00033fKB\u001cu\u000e]=\u0015\u0003e*\"!a\u0007\u0011\ty\u0011\u0017Q\u0004\u0016\u0004u\u0005\r\u0011\u0001B5oa\u0001*\"!a\t\u0011\ty\u0011\u0017Q\u0005\u0016\u0004{\u0005\r\u0011\u0001B5oc\u0001*\"!a\u000b\u0011\ty\u0011\u0017Q\u0006\u0016\u0004\u0001\u0006\r\u0011\u0001B5oe\u0001*\"!a\r\u0011\ty\u0011\u0017Q\u0007\u0016\u0004\u0007\u0006\r\u0011\u0001B5og\u0001*\"!a\u000f\u0011\ty\u0011\u0017Q\b\u0016\u0004\r\u0006\r\u0011\u0001B5oi\u0001*\"!a\u0011\u0011\ty\u0011\u0017Q\t\u0016\u0004\u0013\u0006\r\u0011\u0001B5ok\u0001*\"!a\u0013\u0011\ty\u0011\u0017Q\n\u0016\u0004\u0019\u0006\r\u0011\u0001B5om\u0001\u0002")
public class FanInShape7<T0, T1, T2, T3, T4, T5, T6, O>
extends FanInShape<O> {
    private final Inlet<T0> in0;
    private final Inlet<T1> in1;
    private final Inlet<T2> in2;
    private final Inlet<T3> in3;
    private final Inlet<T4> in4;
    private final Inlet<T5> in5;
    private final Inlet<T6> in6;

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape7<T0, T1, T2, T3, T4, T5, T6, O>(init2);
    }

    @Override
    public FanInShape7<T0, T1, T2, T3, T4, T5, T6, O> deepCopy() {
        return (FanInShape7)super.deepCopy();
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

    public FanInShape7(FanInShape.Init<O> _init) {
        super(_init);
        this.in0 = this.newInlet("in0");
        this.in1 = this.newInlet("in1");
        this.in2 = this.newInlet("in2");
        this.in3 = this.newInlet("in3");
        this.in4 = this.newInlet("in4");
        this.in5 = this.newInlet("in5");
        this.in6 = this.newInlet("in6");
    }

    public FanInShape7(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape7(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Outlet<O> out) {
        Inlet<T0> inlet = in0;
        Inlet<T1> inlet2 = in1;
        Inlet<T2> inlet3 = in2;
        Inlet<T3> inlet4 = in3;
        Inlet<T4> inlet5 = in4;
        Inlet<T5> inlet6 = in5;
        Inlet<T6> inlet7 = in6;
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

