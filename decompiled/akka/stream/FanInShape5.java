/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005b\u0001B\t\u0013\u0001]A\u0001b\u000b\u0001\u0003\u0002\u0003\u0006I\u0001\f\u0005\u0006g\u0001!\t\u0001\u000e\u0005\u0006g\u0001!\tA\u0012\u0005\u0006g\u0001!\t\u0001\u0016\u0005\u0006W\u0002!\t\u0006\u001c\u0005\u0006u\u0002!\te\u001f\u0005\b-\u0002\u0011\r\u0011\"\u0001}\u0011\u0019y\b\u0001)A\u0005{\"A1\f\u0001b\u0001\n\u0003\t\t\u0001\u0003\u0005\u0002\b\u0001\u0001\u000b\u0011BA\u0002\u0011!q\u0006A1A\u0005\u0002\u0005%\u0001\u0002CA\b\u0001\u0001\u0006I!a\u0003\t\u0011\u0005\u0004!\u0019!C\u0001\u0003#A\u0001\"a\u0006\u0001A\u0003%\u00111\u0003\u0005\tI\u0002\u0011\r\u0011\"\u0001\u0002\u001a!A\u0011q\u0004\u0001!\u0002\u0013\tYBA\u0006GC:Len\u00155ba\u0016,$BA\n\u0015\u0003\u0019\u0019HO]3b[*\tQ#\u0001\u0003bW.\f7\u0001A\u000b\b1]RT\bQ\" '\t\u0001\u0011\u0004E\u0002\u001b7ui\u0011AE\u0005\u00039I\u0011!BR1o\u0013:\u001c\u0006.\u00199f!\tqr\u0004\u0004\u0001\u0005\r\u0001\u0002AQ1\u0001\"\u0005\u0005y\u0015C\u0001\u0012)!\t\u0019c%D\u0001%\u0015\u0005)\u0013!B:dC2\f\u0017BA\u0014%\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aI\u0015\n\u0005)\"#aA!os\u0006)q,\u001b8jiB\u0019Q\u0006M\u000f\u000f\u0005iq\u0013BA\u0018\u0013\u0003)1\u0015M\\%o'\"\f\u0007/Z\u0005\u0003cI\u0012A!\u00138ji*\u0011qFE\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005U*\u0005\u0003\u0003\u000e\u0001mebtHQ\u000f\u0011\u0005y9DA\u0002\u001d\u0001\u0011\u000b\u0007\u0011E\u0001\u0002UaA\u0011aD\u000f\u0003\u0007w\u0001A)\u0019A\u0011\u0003\u0005Q\u000b\u0004C\u0001\u0010>\t\u0019q\u0004\u0001#b\u0001C\t\u0011AK\r\t\u0003=\u0001#a!\u0011\u0001\t\u0006\u0004\t#A\u0001+4!\tq2\t\u0002\u0004E\u0001!\u0015\r!\t\u0002\u0003)RBQa\u000b\u0002A\u00021\"\"!N$\t\u000b!\u001b\u0001\u0019A%\u0002\t9\fW.\u001a\t\u0003\u0015Fs!aS(\u0011\u00051#S\"A'\u000b\u000593\u0012A\u0002\u001fs_>$h(\u0003\u0002QI\u00051\u0001K]3eK\u001aL!AU*\u0003\rM#(/\u001b8h\u0015\t\u0001F\u0005F\u00046+jk\u0006m\u00194\t\u000bY#\u0001\u0019A,\u0002\u0007%t\u0007\u0007E\u0002\u001b1ZJ!!\u0017\n\u0003\u000b%sG.\u001a;\t\u000bm#\u0001\u0019\u0001/\u0002\u0007%t\u0017\u0007E\u0002\u001b1fBQA\u0018\u0003A\u0002}\u000b1!\u001b83!\rQ\u0002\f\u0010\u0005\u0006C\u0012\u0001\rAY\u0001\u0004S:\u001c\u0004c\u0001\u000eY\u007f!)A\r\u0002a\u0001K\u0006\u0019\u0011N\u001c\u001b\u0011\u0007iA&\tC\u0003h\t\u0001\u0007\u0001.A\u0002pkR\u00042AG5\u001e\u0013\tQ'C\u0001\u0004PkRdW\r^\u0001\nG>t7\u000f\u001e:vGR$\"!G7\t\u000b9,\u0001\u0019A8\u0002\t%t\u0017\u000e\u001e\t\u0004[A\u0002(FA\u000frW\u0005\u0011\bCA:y\u001b\u0005!(BA;w\u0003%)hn\u00195fG.,GM\u0003\u0002xI\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005e$(!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006AA-Z3q\u0007>\u0004\u0018\u0010F\u00016+\u0005i\bc\u0001\u000eY}*\u0012a']\u0001\u0005S:\u0004\u0004%\u0006\u0002\u0002\u0004A!!\u0004WA\u0003U\tI\u0014/\u0001\u0003j]F\u0002SCAA\u0006!\u0011Q\u0002,!\u0004+\u0005q\n\u0018\u0001B5oe\u0001*\"!a\u0005\u0011\tiA\u0016Q\u0003\u0016\u0003\u007fE\fA!\u001b84AU\u0011\u00111\u0004\t\u00055a\u000biB\u000b\u0002Cc\u0006!\u0011N\u001c\u001b!\u0001")
public class FanInShape5<T0, T1, T2, T3, T4, O>
extends FanInShape<O> {
    private final Inlet<T0> in0;
    private final Inlet<T1> in1;
    private final Inlet<T2> in2;
    private final Inlet<T3> in3;
    private final Inlet<T4> in4;

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape5<T0, T1, T2, T3, T4, O>(init2);
    }

    @Override
    public FanInShape5<T0, T1, T2, T3, T4, O> deepCopy() {
        return (FanInShape5)super.deepCopy();
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

    public FanInShape5(FanInShape.Init<O> _init) {
        super(_init);
        this.in0 = this.newInlet("in0");
        this.in1 = this.newInlet("in1");
        this.in2 = this.newInlet("in2");
        this.in3 = this.newInlet("in3");
        this.in4 = this.newInlet("in4");
    }

    public FanInShape5(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape5(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Outlet<O> out) {
        Inlet<T0> inlet = in0;
        Inlet<T1> inlet2 = in1;
        Inlet<T2> inlet3 = in2;
        Inlet<T3> inlet4 = in3;
        Inlet<T4> inlet5 = in4;
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

