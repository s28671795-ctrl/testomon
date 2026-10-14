/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-4Aa\u0003\u0007\u0001#!AQ\u0005\u0001B\u0001B\u0003%a\u0005C\u00038\u0001\u0011\u0005\u0001\bC\u00038\u0001\u0011\u0005\u0011\tC\u00038\u0001\u0011\u0005q\nC\u0003^\u0001\u0011Ec\fC\u0003b\u0001\u0011\u0005#\rC\u0004W\u0001\t\u0007I\u0011A2\t\r\u0019\u0004\u0001\u0015!\u0003e\u0011\u001dY\u0006A1A\u0005\u0002\u001dDaA\u001b\u0001!\u0002\u0013A'\u0001\u0004$b]>+Ho\u00155ba\u0016\u0014$BA\u0007\u000f\u0003\u0019\u0019HO]3b[*\tq\"\u0001\u0003bW.\f7\u0001A\u000b\u0005%eYdh\u0005\u0002\u0001'A\u0019A#F\f\u000e\u00031I!A\u0006\u0007\u0003\u0017\u0019\u000bgnT;u'\"\f\u0007/\u001a\t\u00031ea\u0001\u0001\u0002\u0004\u001b\u0001!\u0015\ra\u0007\u0002\u0002\u0013F\u0011AD\t\t\u0003;\u0001j\u0011A\b\u0006\u0002?\u0005)1oY1mC&\u0011\u0011E\b\u0002\b\u001d>$\b.\u001b8h!\ti2%\u0003\u0002%=\t\u0019\u0011I\\=\u0002\u000b}Kg.\u001b;\u0011\u0007\u001dRSF\u0004\u0002\u0015Q%\u0011\u0011\u0006D\u0001\f\r\u0006tw*\u001e;TQ\u0006\u0004X-\u0003\u0002,Y\t!\u0011J\\5u\u0015\tICB\u000b\u0002\u0018]-\nq\u0006\u0005\u00021k5\t\u0011G\u0003\u00023g\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0003iy\t!\"\u00198o_R\fG/[8o\u0013\t1\u0014GA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fa\u0001P5oSRtDCA\u001dA!\u0015!\u0002a\u0006\u001e>!\tA2\b\u0002\u0004=\u0001\u0011\u0015\ra\u0007\u0002\u0003\u001fB\u0002\"\u0001\u0007 \u0005\r}\u0002AQ1\u0001\u001c\u0005\ty\u0015\u0007C\u0003&\u0005\u0001\u0007a\u0005\u0006\u0002:\u0005\")1i\u0001a\u0001\t\u0006!a.Y7f!\t)EJ\u0004\u0002G\u0015B\u0011qIH\u0007\u0002\u0011*\u0011\u0011\nE\u0001\u0007yI|w\u000e\u001e \n\u0005-s\u0012A\u0002)sK\u0012,g-\u0003\u0002N\u001d\n11\u000b\u001e:j]\u001eT!a\u0013\u0010\u0015\te\u0002VK\u0017\u0005\u0006#\u0012\u0001\rAU\u0001\u0003S:\u00042\u0001F*\u0018\u0013\t!FBA\u0003J]2,G\u000fC\u0003W\t\u0001\u0007q+\u0001\u0003pkR\u0004\u0004c\u0001\u000bYu%\u0011\u0011\f\u0004\u0002\u0007\u001fV$H.\u001a;\t\u000bm#\u0001\u0019\u0001/\u0002\t=,H/\r\t\u0004)ak\u0014!C2p]N$(/^2u)\t\u0019r\fC\u0003a\u000b\u0001\u0007a%\u0001\u0003j]&$\u0018\u0001\u00033fKB\u001cu\u000e]=\u0015\u0003e*\u0012\u0001\u001a\t\u0004)a+'F\u0001\u001e/\u0003\u0015yW\u000f\u001e\u0019!+\u0005A\u0007c\u0001\u000bYS*\u0012QHL\u0001\u0006_V$\u0018\u0007\t")
public class FanOutShape2<I, O0, O1>
extends FanOutShape<I> {
    private final Outlet<O0> out0;
    private final Outlet<O1> out1;

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape2<I, O0, O1>(init2);
    }

    @Override
    public FanOutShape2<I, O0, O1> deepCopy() {
        return (FanOutShape2)super.deepCopy();
    }

    public Outlet<O0> out0() {
        return this.out0;
    }

    public Outlet<O1> out1() {
        return this.out1;
    }

    public FanOutShape2(FanOutShape.Init<I> _init) {
        super(_init);
        this.out0 = this.newOutlet("out0");
        this.out1 = this.newOutlet("out1");
    }

    public FanOutShape2(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape2(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1) {
        Outlet<O0> outlet = out0;
        Outlet<O1> outlet2 = out1;
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

