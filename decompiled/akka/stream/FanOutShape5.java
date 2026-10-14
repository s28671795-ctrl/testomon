/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005}a\u0001B\t\u0013\u0001]A\u0001b\u000b\u0001\u0003\u0002\u0003\u0006I\u0001\f\u0005\u0006{\u0001!\tA\u0010\u0005\u0006{\u0001!\t\u0001\u0015\u0005\u0006{\u0001!\tA\u0018\u0005\u0006k\u0002!\tF\u001e\u0005\u0006s\u0002!\tE\u001f\u0005\bK\u0002\u0011\r\u0011\"\u0001|\u0011\u0019q\b\u0001)A\u0005y\"9!\u000e\u0001b\u0001\n\u0003y\b\u0002CA\u0003\u0001\u0001\u0006I!!\u0001\t\u00115\u0004!\u0019!C\u0001\u0003\u000fA\u0001\"!\u0004\u0001A\u0003%\u0011\u0011\u0002\u0005\ta\u0002\u0011\r\u0011\"\u0001\u0002\u0010!A\u0011Q\u0003\u0001!\u0002\u0013\t\t\u0002\u0003\u0005t\u0001\t\u0007I\u0011AA\f\u0011!\ti\u0002\u0001Q\u0001\n\u0005e!\u0001\u0004$b]>+Ho\u00155ba\u0016,$BA\n\u0015\u0003\u0019\u0019HO]3b[*\tQ#\u0001\u0003bW.\f7\u0001A\u000b\b1}\tEi\u0012&N'\t\u0001\u0011\u0004E\u0002\u001b7ui\u0011AE\u0005\u00039I\u00111BR1o\u001fV$8\u000b[1qKB\u0011ad\b\u0007\u0001\t\u0019\u0001\u0003\u0001#b\u0001C\t\t\u0011*\u0005\u0002#QA\u00111EJ\u0007\u0002I)\tQ%A\u0003tG\u0006d\u0017-\u0003\u0002(I\t9aj\u001c;iS:<\u0007CA\u0012*\u0013\tQCEA\u0002B]f\fQaX5oSR\u00042!\f\u00194\u001d\tQb&\u0003\u00020%\u0005Ya)\u00198PkR\u001c\u0006.\u00199f\u0013\t\t$G\u0001\u0003J]&$(BA\u0018\u0013U\tiBgK\u00016!\t14(D\u00018\u0015\tA\u0014(A\u0005v]\u000eDWmY6fI*\u0011!\bJ\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001\u001f8\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005}z\u0005\u0003\u0003\u000e\u0001;\u0001\u001be)\u0013'\u0011\u0005y\tEA\u0002\"\u0001\t\u000b\u0007\u0011E\u0001\u0002PaA\u0011a\u0004\u0012\u0003\u0007\u000b\u0002!)\u0019A\u0011\u0003\u0005=\u000b\u0004C\u0001\u0010H\t\u0019A\u0005\u0001\"b\u0001C\t\u0011qJ\r\t\u0003=)#aa\u0013\u0001\u0005\u0006\u0004\t#AA(4!\tqR\n\u0002\u0004O\u0001\u0011\u0015\r!\t\u0002\u0003\u001fRBQa\u000b\u0002A\u00021\"\"aP)\t\u000bI\u001b\u0001\u0019A*\u0002\t9\fW.\u001a\t\u0003)ns!!V-\u0011\u0005Y#S\"A,\u000b\u0005a3\u0012A\u0002\u001fs_>$h(\u0003\u0002[I\u00051\u0001K]3eK\u001aL!\u0001X/\u0003\rM#(/\u001b8h\u0015\tQF\u0005F\u0004@?\u0012LGn\u001c:\t\u000b\u0001$\u0001\u0019A1\u0002\u0005%t\u0007c\u0001\u000ec;%\u00111M\u0005\u0002\u0006\u0013:dW\r\u001e\u0005\u0006K\u0012\u0001\rAZ\u0001\u0005_V$\b\u0007E\u0002\u001bO\u0002K!\u0001\u001b\n\u0003\r=+H\u000f\\3u\u0011\u0015QG\u00011\u0001l\u0003\u0011yW\u000f^\u0019\u0011\u0007i97\tC\u0003n\t\u0001\u0007a.\u0001\u0003pkR\u0014\u0004c\u0001\u000eh\r\")\u0001\u000f\u0002a\u0001c\u0006!q.\u001e;4!\rQr-\u0013\u0005\u0006g\u0012\u0001\r\u0001^\u0001\u0005_V$H\u0007E\u0002\u001bO2\u000b\u0011bY8ogR\u0014Xo\u0019;\u0015\u0005e9\b\"\u0002=\u0006\u0001\u0004a\u0013\u0001B5oSR\f\u0001\u0002Z3fa\u000e{\u0007/\u001f\u000b\u0002\u007fU\tA\u0010E\u0002\u001bOvT#\u0001\u0011\u001b\u0002\u000b=,H\u000f\r\u0011\u0016\u0005\u0005\u0005\u0001\u0003\u0002\u000eh\u0003\u0007Q#a\u0011\u001b\u0002\u000b=,H/\r\u0011\u0016\u0005\u0005%\u0001\u0003\u0002\u000eh\u0003\u0017Q#A\u0012\u001b\u0002\u000b=,HO\r\u0011\u0016\u0005\u0005E\u0001\u0003\u0002\u000eh\u0003'Q#!\u0013\u001b\u0002\u000b=,Ho\r\u0011\u0016\u0005\u0005e\u0001\u0003\u0002\u000eh\u00037Q#\u0001\u0014\u001b\u0002\u000b=,H\u000f\u000e\u0011")
public class FanOutShape5<I, O0, O1, O2, O3, O4>
extends FanOutShape<I> {
    private final Outlet<O0> out0;
    private final Outlet<O1> out1;
    private final Outlet<O2> out2;
    private final Outlet<O3> out3;
    private final Outlet<O4> out4;

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape5<I, O0, O1, O2, O3, O4>(init2);
    }

    @Override
    public FanOutShape5<I, O0, O1, O2, O3, O4> deepCopy() {
        return (FanOutShape5)super.deepCopy();
    }

    public Outlet<O0> out0() {
        return this.out0;
    }

    public Outlet<O1> out1() {
        return this.out1;
    }

    public Outlet<O2> out2() {
        return this.out2;
    }

    public Outlet<O3> out3() {
        return this.out3;
    }

    public Outlet<O4> out4() {
        return this.out4;
    }

    public FanOutShape5(FanOutShape.Init<I> _init) {
        super(_init);
        this.out0 = this.newOutlet("out0");
        this.out1 = this.newOutlet("out1");
        this.out2 = this.newOutlet("out2");
        this.out3 = this.newOutlet("out3");
        this.out4 = this.newOutlet("out4");
    }

    public FanOutShape5(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape5(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4) {
        Outlet<O0> outlet = out0;
        Outlet<O1> outlet2 = out1;
        Outlet<O2> outlet3 = out2;
        Outlet<O3> outlet4 = out3;
        Outlet<O4> outlet5 = out4;
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

