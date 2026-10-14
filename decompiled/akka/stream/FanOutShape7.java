/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005=c\u0001B\u000b\u0017\u0001mA\u0001b\f\u0001\u0003\u0002\u0003\u0006I\u0001\r\u0005\u0006\u0003\u0002!\tA\u0011\u0005\u0006\u0003\u0002!\tA\u0017\u0005\u0006\u0003\u0002!\t\u0001\u001b\u0005\b\u0003\u0017\u0001A\u0011KA\u0007\u0011\u001d\t\u0019\u0002\u0001C!\u0003+A\u0001b\u001c\u0001C\u0002\u0013\u0005\u0011q\u0003\u0005\t\u0003;\u0001\u0001\u0015!\u0003\u0002\u001a!AA\u000f\u0001b\u0001\n\u0003\ty\u0002\u0003\u0005\u0002&\u0001\u0001\u000b\u0011BA\u0011\u0011!9\bA1A\u0005\u0002\u0005\u001d\u0002\u0002CA\u0017\u0001\u0001\u0006I!!\u000b\t\u0011i\u0004!\u0019!C\u0001\u0003_A\u0001\"!\u000e\u0001A\u0003%\u0011\u0011\u0007\u0005\t{\u0002\u0011\r\u0011\"\u0001\u00028!A\u0011Q\b\u0001!\u0002\u0013\tI\u0004C\u0005\u0002\u0002\u0001\u0011\r\u0011\"\u0001\u0002@!A\u0011Q\t\u0001!\u0002\u0013\t\t\u0005C\u0005\u0002\b\u0001\u0011\r\u0011\"\u0001\u0002H!A\u0011Q\n\u0001!\u0002\u0013\tIE\u0001\u0007GC:|U\u000f^*iCB,wG\u0003\u0002\u00181\u000511\u000f\u001e:fC6T\u0011!G\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u0013q\u0019S\tS&O#R;6C\u0001\u0001\u001e!\rqr$I\u0007\u0002-%\u0011\u0001E\u0006\u0002\f\r\u0006tw*\u001e;TQ\u0006\u0004X\r\u0005\u0002#G1\u0001AA\u0002\u0013\u0001\u0011\u000b\u0007QEA\u0001J#\t1C\u0006\u0005\u0002(U5\t\u0001FC\u0001*\u0003\u0015\u00198-\u00197b\u0013\tY\u0003FA\u0004O_RD\u0017N\\4\u0011\u0005\u001dj\u0013B\u0001\u0018)\u0005\r\te._\u0001\u0006?&t\u0017\u000e\u001e\t\u0004cQ:dB\u0001\u00103\u0013\t\u0019d#A\u0006GC:|U\u000f^*iCB,\u0017BA\u001b7\u0005\u0011Ie.\u001b;\u000b\u0005M2\"FA\u00119W\u0005I\u0004C\u0001\u001e@\u001b\u0005Y$B\u0001\u001f>\u0003%)hn\u00195fG.,GM\u0003\u0002?Q\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\u0001[$!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u00061A(\u001b8jiz\"\"aQ-\u0011\u0015y\u0001\u0011\u0005R$K\u001bB\u001bf\u000b\u0005\u0002#\u000b\u00121a\t\u0001CC\u0002\u0015\u0012!a\u0014\u0019\u0011\u0005\tBEAB%\u0001\t\u000b\u0007QE\u0001\u0002PcA\u0011!e\u0013\u0003\u0007\u0019\u0002!)\u0019A\u0013\u0003\u0005=\u0013\u0004C\u0001\u0012O\t\u0019y\u0005\u0001\"b\u0001K\t\u0011qj\r\t\u0003EE#aA\u0015\u0001\u0005\u0006\u0004)#AA(5!\t\u0011C\u000b\u0002\u0004V\u0001\u0011\u0015\r!\n\u0002\u0003\u001fV\u0002\"AI,\u0005\ra\u0003AQ1\u0001&\u0005\tye\u0007C\u00030\u0005\u0001\u0007\u0001\u0007\u0006\u0002D7\")Al\u0001a\u0001;\u0006!a.Y7f!\tqVM\u0004\u0002`GB\u0011\u0001\rK\u0007\u0002C*\u0011!MG\u0001\u0007yI|w\u000e\u001e \n\u0005\u0011D\u0013A\u0002)sK\u0012,g-\u0003\u0002gO\n11\u000b\u001e:j]\u001eT!\u0001\u001a\u0015\u0015\u0015\rKgn\u001d<zy~\f)\u0001C\u0003k\t\u0001\u00071.\u0001\u0002j]B\u0019a\u0004\\\u0011\n\u000554\"!B%oY\u0016$\b\"B8\u0005\u0001\u0004\u0001\u0018\u0001B8viB\u00022AH9E\u0013\t\u0011hC\u0001\u0004PkRdW\r\u001e\u0005\u0006i\u0012\u0001\r!^\u0001\u0005_V$\u0018\u0007E\u0002\u001fc\u001eCQa\u001e\u0003A\u0002a\fAa\\;ueA\u0019a$\u001d&\t\u000bi$\u0001\u0019A>\u0002\t=,Ho\r\t\u0004=El\u0005\"B?\u0005\u0001\u0004q\u0018\u0001B8viR\u00022AH9Q\u0011\u001d\t\t\u0001\u0002a\u0001\u0003\u0007\tAa\\;ukA\u0019a$]*\t\u000f\u0005\u001dA\u00011\u0001\u0002\n\u0005!q.\u001e;7!\rq\u0012OV\u0001\nG>t7\u000f\u001e:vGR$2!HA\b\u0011\u0019\t\t\"\u0002a\u0001a\u0005!\u0011N\\5u\u0003!!W-\u001a9D_BLH#A\"\u0016\u0005\u0005e\u0001\u0003\u0002\u0010r\u00037Q#\u0001\u0012\u001d\u0002\u000b=,H\u000f\r\u0011\u0016\u0005\u0005\u0005\u0002\u0003\u0002\u0010r\u0003GQ#a\u0012\u001d\u0002\u000b=,H/\r\u0011\u0016\u0005\u0005%\u0002\u0003\u0002\u0010r\u0003WQ#A\u0013\u001d\u0002\u000b=,HO\r\u0011\u0016\u0005\u0005E\u0002\u0003\u0002\u0010r\u0003gQ#!\u0014\u001d\u0002\u000b=,Ho\r\u0011\u0016\u0005\u0005e\u0002\u0003\u0002\u0010r\u0003wQ#\u0001\u0015\u001d\u0002\u000b=,H\u000f\u000e\u0011\u0016\u0005\u0005\u0005\u0003\u0003\u0002\u0010r\u0003\u0007R#a\u0015\u001d\u0002\u000b=,H/\u000e\u0011\u0016\u0005\u0005%\u0003\u0003\u0002\u0010r\u0003\u0017R#A\u0016\u001d\u0002\u000b=,HO\u000e\u0011")
public class FanOutShape7<I, O0, O1, O2, O3, O4, O5, O6>
extends FanOutShape<I> {
    private final Outlet<O0> out0;
    private final Outlet<O1> out1;
    private final Outlet<O2> out2;
    private final Outlet<O3> out3;
    private final Outlet<O4> out4;
    private final Outlet<O5> out5;
    private final Outlet<O6> out6;

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape7<I, O0, O1, O2, O3, O4, O5, O6>(init2);
    }

    @Override
    public FanOutShape7<I, O0, O1, O2, O3, O4, O5, O6> deepCopy() {
        return (FanOutShape7)super.deepCopy();
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

    public Outlet<O5> out5() {
        return this.out5;
    }

    public Outlet<O6> out6() {
        return this.out6;
    }

    public FanOutShape7(FanOutShape.Init<I> _init) {
        super(_init);
        this.out0 = this.newOutlet("out0");
        this.out1 = this.newOutlet("out1");
        this.out2 = this.newOutlet("out2");
        this.out3 = this.newOutlet("out3");
        this.out4 = this.newOutlet("out4");
        this.out5 = this.newOutlet("out5");
        this.out6 = this.newOutlet("out6");
    }

    public FanOutShape7(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape7(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6) {
        Outlet<O0> outlet = out0;
        Outlet<O1> outlet2 = out1;
        Outlet<O2> outlet3 = out2;
        Outlet<O3> outlet4 = out3;
        Outlet<O4> outlet5 = out4;
        Outlet<O5> outlet6 = out5;
        Outlet<O6> outlet7 = out6;
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

