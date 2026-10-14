/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a4A!\u0004\b\u0001'!Aq\u0005\u0001B\u0001B\u0003%\u0001\u0006C\u00030\u0001\u0011\u0005\u0001\u0007C\u00030\u0001\u0011\u0005A\bC\u00030\u0001\u0011\u0005!\nC\u0003\\\u0001\u0011EC\fC\u0003k\u0001\u0011\u00053\u000eC\u0004M\u0001\t\u0007I\u0011\u00017\t\r=\u0004\u0001\u0015!\u0003n\u0011\u001d\t\u0006A1A\u0005\u0002ADaa\u001d\u0001!\u0002\u0013\t\bb\u0002+\u0001\u0005\u0004%\t\u0001\u001e\u0005\u0007o\u0002\u0001\u000b\u0011B;\u0003\u0017\u0019\u000bg.\u00138TQ\u0006\u0004Xm\r\u0006\u0003\u001fA\taa\u001d;sK\u0006l'\"A\t\u0002\t\u0005\\7.Y\u0002\u0001+\u0015!2GN\u001d\u001c'\t\u0001Q\u0003E\u0002\u0017/ei\u0011AD\u0005\u000319\u0011!BR1o\u0013:\u001c\u0006.\u00199f!\tQ2\u0004\u0004\u0001\u0005\rq\u0001AQ1\u0001\u001e\u0005\u0005y\u0015C\u0001\u0010%!\ty\"%D\u0001!\u0015\u0005\t\u0013!B:dC2\f\u0017BA\u0012!\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aH\u0013\n\u0005\u0019\u0002#aA!os\u0006)q,\u001b8jiB\u0019\u0011\u0006L\r\u000f\u0005YQ\u0013BA\u0016\u000f\u0003)1\u0015M\\%o'\"\f\u0007/Z\u0005\u0003[9\u0012A!\u00138ji*\u00111FD\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005EZ\u0004C\u0002\f\u0001eUB\u0014\u0004\u0005\u0002\u001bg\u00111A\u0007\u0001EC\u0002u\u0011!\u0001\u0016\u0019\u0011\u0005i1DAB\u001c\u0001\u0011\u000b\u0007QD\u0001\u0002UcA\u0011!$\u000f\u0003\u0007u\u0001A)\u0019A\u000f\u0003\u0005Q\u0013\u0004\"B\u0014\u0003\u0001\u0004ACCA\u0019>\u0011\u0015q4\u00011\u0001@\u0003\u0011q\u0017-\\3\u0011\u0005\u0001;eBA!F!\t\u0011\u0005%D\u0001D\u0015\t!%#\u0001\u0004=e>|GOP\u0005\u0003\r\u0002\na\u0001\u0015:fI\u00164\u0017B\u0001%J\u0005\u0019\u0019FO]5oO*\u0011a\t\t\u000b\u0006c-\u00036K\u0016\u0005\u0006\u0019\u0012\u0001\r!T\u0001\u0004S:\u0004\u0004c\u0001\fOe%\u0011qJ\u0004\u0002\u0006\u0013:dW\r\u001e\u0005\u0006#\u0012\u0001\rAU\u0001\u0004S:\f\u0004c\u0001\fOk!)A\u000b\u0002a\u0001+\u0006\u0019\u0011N\u001c\u001a\u0011\u0007Yq\u0005\bC\u0003X\t\u0001\u0007\u0001,A\u0002pkR\u00042AF-\u001a\u0013\tQfB\u0001\u0004PkRdW\r^\u0001\nG>t7\u000f\u001e:vGR$\"!F/\t\u000by+\u0001\u0019A0\u0002\t%t\u0017\u000e\u001e\t\u0004S1\u0002'FA\rbW\u0005\u0011\u0007CA2i\u001b\u0005!'BA3g\u0003%)hn\u00195fG.,GM\u0003\u0002hA\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005%$'!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006AA-Z3q\u0007>\u0004\u0018\u0010F\u00012+\u0005i\u0007c\u0001\fO]*\u0012!'Y\u0001\u0005S:\u0004\u0004%F\u0001r!\r1bJ\u001d\u0016\u0003k\u0005\fA!\u001b82AU\tQ\u000fE\u0002\u0017\u001dZT#\u0001O1\u0002\t%t'\u0007\t")
public class FanInShape3<T0, T1, T2, O>
extends FanInShape<O> {
    private final Inlet<T0> in0;
    private final Inlet<T1> in1;
    private final Inlet<T2> in2;

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape3<T0, T1, T2, O>(init2);
    }

    @Override
    public FanInShape3<T0, T1, T2, O> deepCopy() {
        return (FanInShape3)super.deepCopy();
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

    public FanInShape3(FanInShape.Init<O> _init) {
        super(_init);
        this.in0 = this.newInlet("in0");
        this.in1 = this.newInlet("in1");
        this.in2 = this.newInlet("in2");
    }

    public FanInShape3(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape3(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Outlet<O> out) {
        Inlet<T0> inlet = in0;
        Inlet<T1> inlet2 = in1;
        Inlet<T2> inlet3 = in2;
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

