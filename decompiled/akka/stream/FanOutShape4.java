/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001da\u0001B\b\u0011\u0001UA\u0001\"\u000b\u0001\u0003\u0002\u0003\u0006IA\u000b\u0005\u0006w\u0001!\t\u0001\u0010\u0005\u0006w\u0001!\ta\u0013\u0005\u0006w\u0001!\t!\u0017\u0005\u0006[\u0002!\tF\u001c\u0005\u0006c\u0002!\tE\u001d\u0005\bA\u0002\u0011\r\u0011\"\u0001t\u0011\u00191\b\u0001)A\u0005i\"9Q\r\u0001b\u0001\n\u00039\bB\u0002>\u0001A\u0003%\u0001\u0010C\u0004i\u0001\t\u0007I\u0011A>\t\ry\u0004\u0001\u0015!\u0003}\u0011\u001dY\u0007A1A\u0005\u0002}D\u0001\"!\u0002\u0001A\u0003%\u0011\u0011\u0001\u0002\r\r\u0006tw*\u001e;TQ\u0006\u0004X\r\u000e\u0006\u0003#I\taa\u001d;sK\u0006l'\"A\n\u0002\t\u0005\\7.Y\u0002\u0001+\u00191Rd\u0010\"F\u0011N\u0011\u0001a\u0006\t\u00041eYR\"\u0001\t\n\u0005i\u0001\"a\u0003$b]>+Ho\u00155ba\u0016\u0004\"\u0001H\u000f\r\u0001\u00111a\u0004\u0001EC\u0002}\u0011\u0011!S\t\u0003A\u0019\u0002\"!\t\u0013\u000e\u0003\tR\u0011aI\u0001\u0006g\u000e\fG.Y\u0005\u0003K\t\u0012qAT8uQ&tw\r\u0005\u0002\"O%\u0011\u0001F\t\u0002\u0004\u0003:L\u0018!B0j]&$\bcA\u0016/c9\u0011\u0001\u0004L\u0005\u0003[A\t1BR1o\u001fV$8\u000b[1qK&\u0011q\u0006\r\u0002\u0005\u0013:LGO\u0003\u0002.!)\u00121DM\u0016\u0002gA\u0011A'O\u0007\u0002k)\u0011agN\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001\u000f\u0012\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002;k\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\rqJg.\u001b;?)\ti$\nE\u0004\u0019\u0001mq\u0014\tR$\u0011\u0005qyDA\u0002!\u0001\t\u000b\u0007qD\u0001\u0002PaA\u0011AD\u0011\u0003\u0007\u0007\u0002!)\u0019A\u0010\u0003\u0005=\u000b\u0004C\u0001\u000fF\t\u00191\u0005\u0001\"b\u0001?\t\u0011qJ\r\t\u00039!#a!\u0013\u0001\u0005\u0006\u0004y\"AA(4\u0011\u0015I#\u00011\u0001+)\tiD\nC\u0003N\u0007\u0001\u0007a*\u0001\u0003oC6,\u0007CA(W\u001d\t\u0001F\u000b\u0005\u0002RE5\t!K\u0003\u0002T)\u00051AH]8pizJ!!\u0016\u0012\u0002\rA\u0013X\rZ3g\u0013\t9\u0006L\u0001\u0004TiJLgn\u001a\u0006\u0003+\n\"b!\u0010.`I\u001eT\u0007\"B.\u0005\u0001\u0004a\u0016AA5o!\rARlG\u0005\u0003=B\u0011Q!\u00138mKRDQ\u0001\u0019\u0003A\u0002\u0005\fAa\\;uaA\u0019\u0001D\u0019 \n\u0005\r\u0004\"AB(vi2,G\u000fC\u0003f\t\u0001\u0007a-\u0001\u0003pkR\f\u0004c\u0001\rc\u0003\")\u0001\u000e\u0002a\u0001S\u0006!q.\u001e;3!\rA\"\r\u0012\u0005\u0006W\u0012\u0001\r\u0001\\\u0001\u0005_V$8\u0007E\u0002\u0019E\u001e\u000b\u0011bY8ogR\u0014Xo\u0019;\u0015\u0005]y\u0007\"\u00029\u0006\u0001\u0004Q\u0013\u0001B5oSR\f\u0001\u0002Z3fa\u000e{\u0007/\u001f\u000b\u0002{U\tA\u000fE\u0002\u0019EVT#A\u0010\u001a\u0002\u000b=,H\u000f\r\u0011\u0016\u0003a\u00042\u0001\u00072zU\t\t%'A\u0003pkR\f\u0004%F\u0001}!\rA\"- \u0016\u0003\tJ\nQa\\;ue\u0001*\"!!\u0001\u0011\ta\u0011\u00171\u0001\u0016\u0003\u000fJ\nQa\\;ug\u0001\u0002")
public class FanOutShape4<I, O0, O1, O2, O3>
extends FanOutShape<I> {
    private final Outlet<O0> out0;
    private final Outlet<O1> out1;
    private final Outlet<O2> out2;
    private final Outlet<O3> out3;

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape4<I, O0, O1, O2, O3>(init2);
    }

    @Override
    public FanOutShape4<I, O0, O1, O2, O3> deepCopy() {
        return (FanOutShape4)super.deepCopy();
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

    public FanOutShape4(FanOutShape.Init<I> _init) {
        super(_init);
        this.out0 = this.newOutlet("out0");
        this.out1 = this.newOutlet("out1");
        this.out2 = this.newOutlet("out2");
        this.out3 = this.newOutlet("out3");
    }

    public FanOutShape4(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape4(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3) {
        Outlet<O0> outlet = out0;
        Outlet<O1> outlet2 = out1;
        Outlet<O2> outlet3 = out2;
        Outlet<O3> outlet4 = out3;
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

