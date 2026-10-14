/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005}d\u0001B\r\u001b\u0001}A\u0001b\r\u0001\u0003\u0002\u0003\u0006I\u0001\u000e\u0005\u0006\u000b\u0002!\tA\u0012\u0005\u0006\u000b\u0002!\t\u0001\u001a\u0005\u0006\u000b\u0002!\tA\u001d\u0005\b\u0003W\u0001A\u0011KA\u0017\u0011\u001d\t\u0019\u0004\u0001C!\u0003kA\u0001\"\u001f\u0001C\u0002\u0013\u0005\u0011q\u0007\u0005\t\u0003{\u0001\u0001\u0015!\u0003\u0002:!Aa\u0010\u0001b\u0001\n\u0003\ty\u0004\u0003\u0005\u0002F\u0001\u0001\u000b\u0011BA!\u0011%\t\u0019\u0001\u0001b\u0001\n\u0003\t9\u0005\u0003\u0005\u0002N\u0001\u0001\u000b\u0011BA%\u0011%\tI\u0001\u0001b\u0001\n\u0003\ty\u0005\u0003\u0005\u0002V\u0001\u0001\u000b\u0011BA)\u0011%\ty\u0001\u0001b\u0001\n\u0003\t9\u0006\u0003\u0005\u0002^\u0001\u0001\u000b\u0011BA-\u0011%\t)\u0002\u0001b\u0001\n\u0003\ty\u0006\u0003\u0005\u0002f\u0001\u0001\u000b\u0011BA1\u0011%\tY\u0002\u0001b\u0001\n\u0003\t9\u0007\u0003\u0005\u0002n\u0001\u0001\u000b\u0011BA5\u0011%\t\t\u0003\u0001b\u0001\n\u0003\ty\u0007\u0003\u0005\u0002v\u0001\u0001\u000b\u0011BA9\u0011%\t9\u0003\u0001b\u0001\n\u0003\t9\b\u0003\u0005\u0002~\u0001\u0001\u000b\u0011BA=\u000511\u0015M\\(viNC\u0017\r]3:\u0015\tYB$\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002;\u0005!\u0011m[6b\u0007\u0001)2\u0002I\u0014J\u0019>\u0013V\u000bW._CN\u0011\u0001!\t\t\u0004E\r*S\"\u0001\u000e\n\u0005\u0011R\"a\u0003$b]>+Ho\u00155ba\u0016\u0004\"AJ\u0014\r\u0001\u00111\u0001\u0006\u0001EC\u0002%\u0012\u0011!S\t\u0003UA\u0002\"a\u000b\u0018\u000e\u00031R\u0011!L\u0001\u0006g\u000e\fG.Y\u0005\u0003_1\u0012qAT8uQ&tw\r\u0005\u0002,c%\u0011!\u0007\f\u0002\u0004\u0003:L\u0018!B0j]&$\bcA\u001b9w9\u0011!EN\u0005\u0003oi\t1BR1o\u001fV$8\u000b[1qK&\u0011\u0011H\u000f\u0002\u0005\u0013:LGO\u0003\u000285)\u0012Q\u0005P\u0016\u0002{A\u0011ahQ\u0007\u0002\u007f)\u0011\u0001)Q\u0001\nk:\u001c\u0007.Z2lK\u0012T!A\u0011\u0017\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002E\u007f\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\rqJg.\u001b;?)\t95\r\u0005\u0007#\u0001\u0015B5JT)U/jk\u0006\r\u0005\u0002'\u0013\u00121!\n\u0001CC\u0002%\u0012!a\u0014\u0019\u0011\u0005\u0019bEAB'\u0001\t\u000b\u0007\u0011F\u0001\u0002PcA\u0011ae\u0014\u0003\u0007!\u0002!)\u0019A\u0015\u0003\u0005=\u0013\u0004C\u0001\u0014S\t\u0019\u0019\u0006\u0001\"b\u0001S\t\u0011qj\r\t\u0003MU#aA\u0016\u0001\u0005\u0006\u0004I#AA(5!\t1\u0003\f\u0002\u0004Z\u0001\u0011\u0015\r!\u000b\u0002\u0003\u001fV\u0002\"AJ.\u0005\rq\u0003AQ1\u0001*\u0005\tye\u0007\u0005\u0002'=\u00121q\f\u0001CC\u0002%\u0012!aT\u001c\u0011\u0005\u0019\nGA\u00022\u0001\t\u000b\u0007\u0011F\u0001\u0002Pq!)1G\u0001a\u0001iQ\u0011q)\u001a\u0005\u0006M\u000e\u0001\raZ\u0001\u0005]\u0006lW\r\u0005\u0002i_:\u0011\u0011.\u001c\t\u0003U2j\u0011a\u001b\u0006\u0003Yz\ta\u0001\u0010:p_Rt\u0014B\u00018-\u0003\u0019\u0001&/\u001a3fM&\u0011\u0001/\u001d\u0002\u0007'R\u0014\u0018N\\4\u000b\u00059dCCE$tqv\f\t!a\u0002\u0002\u000e\u0005M\u0011\u0011DA\u0010\u0003KAQ\u0001\u001e\u0003A\u0002U\f!!\u001b8\u0011\u0007\t2X%\u0003\u0002x5\t)\u0011J\u001c7fi\")\u0011\u0010\u0002a\u0001u\u0006!q.\u001e;1!\r\u00113\u0010S\u0005\u0003yj\u0011aaT;uY\u0016$\b\"\u0002@\u0005\u0001\u0004y\u0018\u0001B8viF\u00022AI>L\u0011\u001d\t\u0019\u0001\u0002a\u0001\u0003\u000b\tAa\\;ueA\u0019!e\u001f(\t\u000f\u0005%A\u00011\u0001\u0002\f\u0005!q.\u001e;4!\r\u001130\u0015\u0005\b\u0003\u001f!\u0001\u0019AA\t\u0003\u0011yW\u000f\u001e\u001b\u0011\u0007\tZH\u000bC\u0004\u0002\u0016\u0011\u0001\r!a\u0006\u0002\t=,H/\u000e\t\u0004Em<\u0006bBA\u000e\t\u0001\u0007\u0011QD\u0001\u0005_V$h\u0007E\u0002#wjCq!!\t\u0005\u0001\u0004\t\u0019#\u0001\u0003pkR<\u0004c\u0001\u0012|;\"9\u0011q\u0005\u0003A\u0002\u0005%\u0012\u0001B8vib\u00022AI>a\u0003%\u0019wN\\:ueV\u001cG\u000fF\u0002\"\u0003_Aa!!\r\u0006\u0001\u0004!\u0014\u0001B5oSR\f\u0001\u0002Z3fa\u000e{\u0007/\u001f\u000b\u0002\u000fV\u0011\u0011\u0011\b\t\u0005Em\fYD\u000b\u0002Iy\u0005)q.\u001e;1AU\u0011\u0011\u0011\t\t\u0005Em\f\u0019E\u000b\u0002Ly\u0005)q.\u001e;2AU\u0011\u0011\u0011\n\t\u0005Em\fYE\u000b\u0002Oy\u0005)q.\u001e;3AU\u0011\u0011\u0011\u000b\t\u0005Em\f\u0019F\u000b\u0002Ry\u0005)q.\u001e;4AU\u0011\u0011\u0011\f\t\u0005Em\fYF\u000b\u0002Uy\u0005)q.\u001e;5AU\u0011\u0011\u0011\r\t\u0005Em\f\u0019G\u000b\u0002Xy\u0005)q.\u001e;6AU\u0011\u0011\u0011\u000e\t\u0005Em\fYG\u000b\u0002[y\u0005)q.\u001e;7AU\u0011\u0011\u0011\u000f\t\u0005Em\f\u0019H\u000b\u0002^y\u0005)q.\u001e;8AU\u0011\u0011\u0011\u0010\t\u0005Em\fYH\u000b\u0002ay\u0005)q.\u001e;9A\u0001")
public class FanOutShape9<I, O0, O1, O2, O3, O4, O5, O6, O7, O8>
extends FanOutShape<I> {
    private final Outlet<O0> out0;
    private final Outlet<O1> out1;
    private final Outlet<O2> out2;
    private final Outlet<O3> out3;
    private final Outlet<O4> out4;
    private final Outlet<O5> out5;
    private final Outlet<O6> out6;
    private final Outlet<O7> out7;
    private final Outlet<O8> out8;

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape9<I, O0, O1, O2, O3, O4, O5, O6, O7, O8>(init2);
    }

    @Override
    public FanOutShape9<I, O0, O1, O2, O3, O4, O5, O6, O7, O8> deepCopy() {
        return (FanOutShape9)super.deepCopy();
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

    public Outlet<O7> out7() {
        return this.out7;
    }

    public Outlet<O8> out8() {
        return this.out8;
    }

    public FanOutShape9(FanOutShape.Init<I> _init) {
        super(_init);
        this.out0 = this.newOutlet("out0");
        this.out1 = this.newOutlet("out1");
        this.out2 = this.newOutlet("out2");
        this.out3 = this.newOutlet("out3");
        this.out4 = this.newOutlet("out4");
        this.out5 = this.newOutlet("out5");
        this.out6 = this.newOutlet("out6");
        this.out7 = this.newOutlet("out7");
        this.out8 = this.newOutlet("out8");
    }

    public FanOutShape9(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape9(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7, Outlet<O8> out8) {
        Outlet<O0> outlet = out0;
        Outlet<O1> outlet2 = out1;
        Outlet<O2> outlet3 = out2;
        Outlet<O3> outlet4 = out3;
        Outlet<O4> outlet5 = out4;
        Outlet<O5> outlet6 = out5;
        Outlet<O6> outlet7 = out6;
        Outlet<O7> outlet8 = out7;
        Outlet<O8> outlet9 = out8;
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet9).$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

