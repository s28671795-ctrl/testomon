/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]4A!\u0004\b\u0001'!Aq\u0005\u0001B\u0001B\u0003%\u0001\u0006C\u0003:\u0001\u0011\u0005!\bC\u0003:\u0001\u0011\u0005a\tC\u0003:\u0001\u0011\u0005A\u000bC\u0003f\u0001\u0011Ec\rC\u0003j\u0001\u0011\u0005#\u000eC\u0004\\\u0001\t\u0007I\u0011A6\t\r9\u0004\u0001\u0015!\u0003m\u0011\u001d\u0001\u0007A1A\u0005\u0002=DaA\u001d\u0001!\u0002\u0013\u0001\bbB2\u0001\u0005\u0004%\ta\u001d\u0005\u0007m\u0002\u0001\u000b\u0011\u0002;\u0003\u0019\u0019\u000bgnT;u'\"\f\u0007/Z\u001a\u000b\u0005=\u0001\u0012AB:ue\u0016\fWNC\u0001\u0012\u0003\u0011\t7n[1\u0004\u0001U)AcG\u001fA\u0007N\u0011\u0001!\u0006\t\u0004-]IR\"\u0001\b\n\u0005aq!a\u0003$b]>+Ho\u00155ba\u0016\u0004\"AG\u000e\r\u0001\u00111A\u0004\u0001EC\u0002u\u0011\u0011!S\t\u0003=\u0011\u0002\"a\b\u0012\u000e\u0003\u0001R\u0011!I\u0001\u0006g\u000e\fG.Y\u0005\u0003G\u0001\u0012qAT8uQ&tw\r\u0005\u0002 K%\u0011a\u0005\t\u0002\u0004\u0003:L\u0018!B0j]&$\bcA\u0015-_9\u0011aCK\u0005\u0003W9\t1BR1o\u001fV$8\u000b[1qK&\u0011QF\f\u0002\u0005\u0013:LGO\u0003\u0002,\u001d)\u0012\u0011\u0004M\u0016\u0002cA\u0011!gN\u0007\u0002g)\u0011A'N\u0001\nk:\u001c\u0007.Z2lK\u0012T!A\u000e\u0011\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u00029g\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\rqJg.\u001b;?)\tYT\t\u0005\u0004\u0017\u0001eatH\u0011\t\u00035u\"aA\u0010\u0001\u0005\u0006\u0004i\"AA(1!\tQ\u0002\t\u0002\u0004B\u0001\u0011\u0015\r!\b\u0002\u0003\u001fF\u0002\"AG\"\u0005\r\u0011\u0003AQ1\u0001\u001e\u0005\ty%\u0007C\u0003(\u0005\u0001\u0007\u0001\u0006\u0006\u0002<\u000f\")\u0001j\u0001a\u0001\u0013\u0006!a.Y7f!\tQ\u0015K\u0004\u0002L\u001fB\u0011A\nI\u0007\u0002\u001b*\u0011aJE\u0001\u0007yI|w\u000e\u001e \n\u0005A\u0003\u0013A\u0002)sK\u0012,g-\u0003\u0002S'\n11\u000b\u001e:j]\u001eT!\u0001\u0015\u0011\u0015\u000bm*&l\u00182\t\u000bY#\u0001\u0019A,\u0002\u0005%t\u0007c\u0001\fY3%\u0011\u0011L\u0004\u0002\u0006\u0013:dW\r\u001e\u0005\u00067\u0012\u0001\r\u0001X\u0001\u0005_V$\b\u0007E\u0002\u0017;rJ!A\u0018\b\u0003\r=+H\u000f\\3u\u0011\u0015\u0001G\u00011\u0001b\u0003\u0011yW\u000f^\u0019\u0011\u0007Yiv\bC\u0003d\t\u0001\u0007A-\u0001\u0003pkR\u0014\u0004c\u0001\f^\u0005\u0006I1m\u001c8tiJ,8\r\u001e\u000b\u0003+\u001dDQ\u0001[\u0003A\u0002!\nA!\u001b8ji\u0006AA-Z3q\u0007>\u0004\u0018\u0010F\u0001<+\u0005a\u0007c\u0001\f^[*\u0012A\bM\u0001\u0006_V$\b\u0007I\u000b\u0002aB\u0019a#X9+\u0005}\u0002\u0014!B8viF\u0002S#\u0001;\u0011\u0007YiVO\u000b\u0002Ca\u0005)q.\u001e;3A\u0001")
public class FanOutShape3<I, O0, O1, O2>
extends FanOutShape<I> {
    private final Outlet<O0> out0;
    private final Outlet<O1> out1;
    private final Outlet<O2> out2;

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape3<I, O0, O1, O2>(init2);
    }

    @Override
    public FanOutShape3<I, O0, O1, O2> deepCopy() {
        return (FanOutShape3)super.deepCopy();
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

    public FanOutShape3(FanOutShape.Init<I> _init) {
        super(_init);
        this.out0 = this.newOutlet("out0");
        this.out1 = this.newOutlet("out1");
        this.out2 = this.newOutlet("out2");
    }

    public FanOutShape3(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape3(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2) {
        Outlet<O0> outlet = out0;
        Outlet<O1> outlet2 = out1;
        Outlet<O2> outlet3 = out2;
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

