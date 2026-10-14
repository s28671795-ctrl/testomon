/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dd\u0001B\f\u0019\u0001uA\u0001\"\r\u0001\u0003\u0002\u0003\u0006IA\r\u0005\u0006\u0007\u0002!\t\u0001\u0012\u0005\u0006\u0007\u0002!\ta\u0018\u0005\u0006\u0007\u0002!\t!\u001c\u0005\b\u00037\u0001A\u0011KA\u000f\u0011\u001d\t\u0019\u0003\u0001C!\u0003KA\u0001\u0002\u001e\u0001C\u0002\u0013\u0005\u0011q\u0005\u0005\t\u0003[\u0001\u0001\u0015!\u0003\u0002*!A\u0011\u0010\u0001b\u0001\n\u0003\ty\u0003\u0003\u0005\u00026\u0001\u0001\u000b\u0011BA\u0019\u0011!a\bA1A\u0005\u0002\u0005]\u0002\u0002CA\u001f\u0001\u0001\u0006I!!\u000f\t\u0011}\u0004!\u0019!C\u0001\u0003\u007fA\u0001\"!\u0012\u0001A\u0003%\u0011\u0011\t\u0005\n\u0003\u000b\u0001!\u0019!C\u0001\u0003\u000fB\u0001\"!\u0014\u0001A\u0003%\u0011\u0011\n\u0005\n\u0003\u0017\u0001!\u0019!C\u0001\u0003\u001fB\u0001\"!\u0016\u0001A\u0003%\u0011\u0011\u000b\u0005\n\u0003#\u0001!\u0019!C\u0001\u0003/B\u0001\"!\u0018\u0001A\u0003%\u0011\u0011\f\u0005\n\u0003/\u0001!\u0019!C\u0001\u0003?B\u0001\"!\u001a\u0001A\u0003%\u0011\u0011\r\u0002\r\r\u0006tw*\u001e;TQ\u0006\u0004X\r\u000f\u0006\u00033i\taa\u001d;sK\u0006l'\"A\u000e\u0002\t\u0005\\7.Y\u0002\u0001+)qRe\u0012&N!N3\u0016\fX\n\u0003\u0001}\u00012\u0001I\u0011$\u001b\u0005A\u0012B\u0001\u0012\u0019\u0005-1\u0015M\\(viNC\u0017\r]3\u0011\u0005\u0011*C\u0002\u0001\u0003\u0007M\u0001A)\u0019A\u0014\u0003\u0003%\u000b\"\u0001\u000b\u0018\u0011\u0005%bS\"\u0001\u0016\u000b\u0003-\nQa]2bY\u0006L!!\f\u0016\u0003\u000f9{G\u000f[5oOB\u0011\u0011fL\u0005\u0003a)\u00121!\u00118z\u0003\u0015y\u0016N\\5u!\r\u0019d'\u000f\b\u0003AQJ!!\u000e\r\u0002\u0017\u0019\u000bgnT;u'\"\f\u0007/Z\u0005\u0003oa\u0012A!\u00138ji*\u0011Q\u0007\u0007\u0016\u0003GiZ\u0013a\u000f\t\u0003y\u0005k\u0011!\u0010\u0006\u0003}}\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005\u0001S\u0013AC1o]>$\u0018\r^5p]&\u0011!)\u0010\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017A\u0002\u001fj]&$h\b\u0006\u0002F=BY\u0001\u0005A\u0012G\u00132{%+\u0016-\\!\t!s\t\u0002\u0004I\u0001\u0011\u0015\ra\n\u0002\u0003\u001fB\u0002\"\u0001\n&\u0005\r-\u0003AQ1\u0001(\u0005\ty\u0015\u0007\u0005\u0002%\u001b\u00121a\n\u0001CC\u0002\u001d\u0012!a\u0014\u001a\u0011\u0005\u0011\u0002FAB)\u0001\t\u000b\u0007qE\u0001\u0002PgA\u0011Ae\u0015\u0003\u0007)\u0002!)\u0019A\u0014\u0003\u0005=#\u0004C\u0001\u0013W\t\u00199\u0006\u0001\"b\u0001O\t\u0011q*\u000e\t\u0003Ie#aA\u0017\u0001\u0005\u0006\u00049#AA(7!\t!C\f\u0002\u0004^\u0001\u0011\u0015\ra\n\u0002\u0003\u001f^BQ!\r\u0002A\u0002I\"\"!\u00121\t\u000b\u0005\u001c\u0001\u0019\u00012\u0002\t9\fW.\u001a\t\u0003G*t!\u0001\u001a5\u0011\u0005\u0015TS\"\u00014\u000b\u0005\u001dd\u0012A\u0002\u001fs_>$h(\u0003\u0002jU\u00051\u0001K]3eK\u001aL!a\u001b7\u0003\rM#(/\u001b8h\u0015\tI'\u0006\u0006\bF]ND8P`A\u0002\u0003\u0013\ty!!\u0006\t\u000b=$\u0001\u0019\u00019\u0002\u0005%t\u0007c\u0001\u0011rG%\u0011!\u000f\u0007\u0002\u0006\u0013:dW\r\u001e\u0005\u0006i\u0012\u0001\r!^\u0001\u0005_V$\b\u0007E\u0002!m\u001aK!a\u001e\r\u0003\r=+H\u000f\\3u\u0011\u0015IH\u00011\u0001{\u0003\u0011yW\u000f^\u0019\u0011\u0007\u00012\u0018\nC\u0003}\t\u0001\u0007Q0\u0001\u0003pkR\u0014\u0004c\u0001\u0011w\u0019\"1q\u0010\u0002a\u0001\u0003\u0003\tAa\\;ugA\u0019\u0001E^(\t\u000f\u0005\u0015A\u00011\u0001\u0002\b\u0005!q.\u001e;5!\r\u0001cO\u0015\u0005\b\u0003\u0017!\u0001\u0019AA\u0007\u0003\u0011yW\u000f^\u001b\u0011\u0007\u00012X\u000bC\u0004\u0002\u0012\u0011\u0001\r!a\u0005\u0002\t=,HO\u000e\t\u0004AYD\u0006bBA\f\t\u0001\u0007\u0011\u0011D\u0001\u0005_V$x\u0007E\u0002!mn\u000b\u0011bY8ogR\u0014Xo\u0019;\u0015\u0007}\ty\u0002\u0003\u0004\u0002\"\u0015\u0001\rAM\u0001\u0005S:LG/\u0001\u0005eK\u0016\u00048i\u001c9z)\u0005)UCAA\u0015!\u0011\u0001c/a\u000b+\u0005\u0019S\u0014!B8viB\u0002SCAA\u0019!\u0011\u0001c/a\r+\u0005%S\u0014!B8viF\u0002SCAA\u001d!\u0011\u0001c/a\u000f+\u00051S\u0014!B8viJ\u0002SCAA!!\u0011\u0001c/a\u0011+\u0005=S\u0014!B8viN\u0002SCAA%!\u0011\u0001c/a\u0013+\u0005IS\u0014!B8viR\u0002SCAA)!\u0011\u0001c/a\u0015+\u0005US\u0014!B8viV\u0002SCAA-!\u0011\u0001c/a\u0017+\u0005aS\u0014!B8viZ\u0002SCAA1!\u0011\u0001c/a\u0019+\u0005mS\u0014!B8vi^\u0002\u0003")
public class FanOutShape8<I, O0, O1, O2, O3, O4, O5, O6, O7>
extends FanOutShape<I> {
    private final Outlet<O0> out0;
    private final Outlet<O1> out1;
    private final Outlet<O2> out2;
    private final Outlet<O3> out3;
    private final Outlet<O4> out4;
    private final Outlet<O5> out5;
    private final Outlet<O6> out6;
    private final Outlet<O7> out7;

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape8<I, O0, O1, O2, O3, O4, O5, O6, O7>(init2);
    }

    @Override
    public FanOutShape8<I, O0, O1, O2, O3, O4, O5, O6, O7> deepCopy() {
        return (FanOutShape8)super.deepCopy();
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

    public FanOutShape8(FanOutShape.Init<I> _init) {
        super(_init);
        this.out0 = this.newOutlet("out0");
        this.out1 = this.newOutlet("out1");
        this.out2 = this.newOutlet("out2");
        this.out3 = this.newOutlet("out3");
        this.out4 = this.newOutlet("out4");
        this.out5 = this.newOutlet("out5");
        this.out6 = this.newOutlet("out6");
        this.out7 = this.newOutlet("out7");
    }

    public FanOutShape8(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape8(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7) {
        Outlet<O0> outlet = out0;
        Outlet<O1> outlet2 = out1;
        Outlet<O2> outlet3 = out2;
        Outlet<O3> outlet4 = out3;
        Outlet<O4> outlet5 = out4;
        Outlet<O5> outlet6 = out5;
        Outlet<O6> outlet7 = out6;
        Outlet<O7> outlet8 = out7;
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

