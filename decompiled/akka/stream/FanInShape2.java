/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000514Aa\u0003\u0007\u0001#!AQ\u0005\u0001B\u0001B\u0003%a\u0005C\u0003.\u0001\u0011\u0005a\u0006C\u0003.\u0001\u0011\u0005q\u0007C\u0003.\u0001\u0011\u0005Q\tC\u0003T\u0001\u0011EC\u000bC\u0003c\u0001\u0011\u00053\rC\u0004H\u0001\t\u0007I\u0011\u00013\t\r\u001d\u0004\u0001\u0015!\u0003f\u0011\u001da\u0005A1A\u0005\u0002!Daa\u001b\u0001!\u0002\u0013I'a\u0003$b]&s7\u000b[1qKJR!!\u0004\b\u0002\rM$(/Z1n\u0015\u0005y\u0011\u0001B1lW\u0006\u001c\u0001!\u0006\u0003\u0013cQJ2C\u0001\u0001\u0014!\r!RcF\u0007\u0002\u0019%\u0011a\u0003\u0004\u0002\u000b\r\u0006t\u0017J\\*iCB,\u0007C\u0001\r\u001a\u0019\u0001!aA\u0007\u0001\u0005\u0006\u0004Y\"!A(\u0012\u0005q\u0011\u0003CA\u000f!\u001b\u0005q\"\"A\u0010\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0005r\"a\u0002(pi\"Lgn\u001a\t\u0003;\rJ!\u0001\n\u0010\u0003\u0007\u0005s\u00170A\u0003`S:LG\u000fE\u0002(U]q!\u0001\u0006\u0015\n\u0005%b\u0011A\u0003$b]&s7\u000b[1qK&\u00111\u0006\f\u0002\u0005\u0013:LGO\u0003\u0002*\u0019\u00051A(\u001b8jiz\"\"a\f\u001c\u0011\u000bQ\u0001\u0001gM\f\u0011\u0005a\tDA\u0002\u001a\u0001\u0011\u000b\u00071D\u0001\u0002UaA\u0011\u0001\u0004\u000e\u0003\u0007k\u0001A)\u0019A\u000e\u0003\u0005Q\u000b\u0004\"B\u0013\u0003\u0001\u00041CCA\u00189\u0011\u0015I4\u00011\u0001;\u0003\u0011q\u0017-\\3\u0011\u0005m\u0012eB\u0001\u001fA!\tid$D\u0001?\u0015\ty\u0004#\u0001\u0004=e>|GOP\u0005\u0003\u0003z\ta\u0001\u0015:fI\u00164\u0017BA\"E\u0005\u0019\u0019FO]5oO*\u0011\u0011I\b\u000b\u0005_\u0019[e\nC\u0003H\t\u0001\u0007\u0001*A\u0002j]B\u00022\u0001F%1\u0013\tQEBA\u0003J]2,G\u000fC\u0003M\t\u0001\u0007Q*A\u0002j]F\u00022\u0001F%4\u0011\u0015yE\u00011\u0001Q\u0003\ryW\u000f\u001e\t\u0004)E;\u0012B\u0001*\r\u0005\u0019yU\u000f\u001e7fi\u0006I1m\u001c8tiJ,8\r\u001e\u000b\u0003'UCQAV\u0003A\u0002]\u000bA!\u001b8jiB\u0019qE\u000b-+\u0005]I6&\u0001.\u0011\u0005m\u0003W\"\u0001/\u000b\u0005us\u0016!C;oG\",7m[3e\u0015\tyf$\u0001\u0006b]:|G/\u0019;j_:L!!\u0019/\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\u0005eK\u0016\u00048i\u001c9z)\u0005yS#A3\u0011\u0007QIeM\u000b\u000213\u0006!\u0011N\u001c\u0019!+\u0005I\u0007c\u0001\u000bJU*\u00121'W\u0001\u0005S:\f\u0004\u0005")
public class FanInShape2<T0, T1, O>
extends FanInShape<O> {
    private final Inlet<T0> in0;
    private final Inlet<T1> in1;

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape2<T0, T1, O>(init2);
    }

    @Override
    public FanInShape2<T0, T1, O> deepCopy() {
        return (FanInShape2)super.deepCopy();
    }

    public Inlet<T0> in0() {
        return this.in0;
    }

    public Inlet<T1> in1() {
        return this.in1;
    }

    public FanInShape2(FanInShape.Init<O> _init) {
        super(_init);
        this.in0 = this.newInlet("in0");
        this.in1 = this.newInlet("in1");
    }

    public FanInShape2(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape2(Inlet<T0> in0, Inlet<T1> in1, Outlet<O> out) {
        Inlet<T0> inlet = in0;
        Inlet<T1> inlet2 = in1;
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

