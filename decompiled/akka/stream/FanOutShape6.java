/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]b\u0001B\n\u0015\u0001eA\u0001\"\f\u0001\u0003\u0002\u0003\u0006IA\f\u0005\u0006\u007f\u0001!\t\u0001\u0011\u0005\u0006\u007f\u0001!\t!\u0016\u0005\u0006\u007f\u0001!\ta\u0019\u0005\u0006{\u0002!\tF \u0005\b\u0003\u0007\u0001A\u0011IA\u0003\u0011!Q\u0007A1A\u0005\u0002\u0005\u001d\u0001\u0002CA\u0007\u0001\u0001\u0006I!!\u0003\t\u0011=\u0004!\u0019!C\u0001\u0003\u001fA\u0001\"!\u0006\u0001A\u0003%\u0011\u0011\u0003\u0005\te\u0002\u0011\r\u0011\"\u0001\u0002\u0018!A\u0011Q\u0004\u0001!\u0002\u0013\tI\u0002\u0003\u0005v\u0001\t\u0007I\u0011AA\u0010\u0011!\t)\u0003\u0001Q\u0001\n\u0005\u0005\u0002\u0002\u0003=\u0001\u0005\u0004%\t!a\n\t\u0011\u00055\u0002\u0001)A\u0005\u0003SA\u0001b\u001f\u0001C\u0002\u0013\u0005\u0011q\u0006\u0005\t\u0003k\u0001\u0001\u0015!\u0003\u00022\taa)\u00198PkR\u001c\u0006.\u00199fm)\u0011QCF\u0001\u0007gR\u0014X-Y7\u000b\u0003]\tA!Y6lC\u000e\u0001Q\u0003\u0003\u000e\"\u0007\u001aKEj\u0014*\u0014\u0005\u0001Y\u0002c\u0001\u000f\u001e?5\tA#\u0003\u0002\u001f)\tYa)\u00198PkR\u001c\u0006.\u00199f!\t\u0001\u0013\u0005\u0004\u0001\u0005\r\t\u0002\u0001R1\u0001$\u0005\u0005I\u0015C\u0001\u0013+!\t)\u0003&D\u0001'\u0015\u00059\u0013!B:dC2\f\u0017BA\u0015'\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!J\u0016\n\u000512#aA!os\u0006)q,\u001b8jiB\u0019qFM\u001b\u000f\u0005q\u0001\u0014BA\u0019\u0015\u0003-1\u0015M\\(viNC\u0017\r]3\n\u0005M\"$\u0001B%oSRT!!\r\u000b+\u0005}14&A\u001c\u0011\u0005ajT\"A\u001d\u000b\u0005iZ\u0014!C;oG\",7m[3e\u0015\tad%\u0001\u0006b]:|G/\u0019;j_:L!AP\u001d\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\u0004=S:LGO\u0010\u000b\u0003\u0003R\u0003\u0012\u0002\b\u0001 \u0005\u0016C5JT)\u0011\u0005\u0001\u001aEA\u0002#\u0001\t\u000b\u00071E\u0001\u0002PaA\u0011\u0001E\u0012\u0003\u0007\u000f\u0002!)\u0019A\u0012\u0003\u0005=\u000b\u0004C\u0001\u0011J\t\u0019Q\u0005\u0001\"b\u0001G\t\u0011qJ\r\t\u0003A1#a!\u0014\u0001\u0005\u0006\u0004\u0019#AA(4!\t\u0001s\n\u0002\u0004Q\u0001\u0011\u0015\ra\t\u0002\u0003\u001fR\u0002\"\u0001\t*\u0005\rM\u0003AQ1\u0001$\u0005\tyU\u0007C\u0003.\u0005\u0001\u0007a\u0006\u0006\u0002B-\")qk\u0001a\u00011\u0006!a.Y7f!\tI\u0006M\u0004\u0002[=B\u00111LJ\u0007\u00029*\u0011Q\fG\u0001\u0007yI|w\u000e\u001e \n\u0005}3\u0013A\u0002)sK\u0012,g-\u0003\u0002bE\n11\u000b\u001e:j]\u001eT!a\u0018\u0014\u0015\u0011\u0005#\u0017N\\9uojDQ!\u001a\u0003A\u0002\u0019\f!!\u001b8\u0011\u0007q9w$\u0003\u0002i)\t)\u0011J\u001c7fi\")!\u000e\u0002a\u0001W\u0006!q.\u001e;1!\raBNQ\u0005\u0003[R\u0011aaT;uY\u0016$\b\"B8\u0005\u0001\u0004\u0001\u0018\u0001B8viF\u00022\u0001\b7F\u0011\u0015\u0011H\u00011\u0001t\u0003\u0011yW\u000f\u001e\u001a\u0011\u0007qa\u0007\nC\u0003v\t\u0001\u0007a/\u0001\u0003pkR\u001c\u0004c\u0001\u000fm\u0017\")\u0001\u0010\u0002a\u0001s\u0006!q.\u001e;5!\raBN\u0014\u0005\u0006w\u0012\u0001\r\u0001`\u0001\u0005_V$X\u0007E\u0002\u001dYF\u000b\u0011bY8ogR\u0014Xo\u0019;\u0015\u0005my\bBBA\u0001\u000b\u0001\u0007a&\u0001\u0003j]&$\u0018\u0001\u00033fKB\u001cu\u000e]=\u0015\u0003\u0005+\"!!\u0003\u0011\tqa\u00171\u0002\u0016\u0003\u0005Z\nQa\\;ua\u0001*\"!!\u0005\u0011\tqa\u00171\u0003\u0016\u0003\u000bZ\nQa\\;uc\u0001*\"!!\u0007\u0011\tqa\u00171\u0004\u0016\u0003\u0011Z\nQa\\;ue\u0001*\"!!\t\u0011\tqa\u00171\u0005\u0016\u0003\u0017Z\nQa\\;ug\u0001*\"!!\u000b\u0011\tqa\u00171\u0006\u0016\u0003\u001dZ\nQa\\;ui\u0001*\"!!\r\u0011\tqa\u00171\u0007\u0016\u0003#Z\nQa\\;uk\u0001\u0002")
public class FanOutShape6<I, O0, O1, O2, O3, O4, O5>
extends FanOutShape<I> {
    private final Outlet<O0> out0;
    private final Outlet<O1> out1;
    private final Outlet<O2> out2;
    private final Outlet<O3> out3;
    private final Outlet<O4> out4;
    private final Outlet<O5> out5;

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape6<I, O0, O1, O2, O3, O4, O5>(init2);
    }

    @Override
    public FanOutShape6<I, O0, O1, O2, O3, O4, O5> deepCopy() {
        return (FanOutShape6)super.deepCopy();
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

    public FanOutShape6(FanOutShape.Init<I> _init) {
        super(_init);
        this.out0 = this.newOutlet("out0");
        this.out1 = this.newOutlet("out1");
        this.out2 = this.newOutlet("out2");
        this.out3 = this.newOutlet("out3");
        this.out4 = this.newOutlet("out4");
        this.out5 = this.newOutlet("out5");
    }

    public FanOutShape6(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape6(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5) {
        Outlet<O0> outlet = out0;
        Outlet<O1> outlet2 = out1;
        Outlet<O2> outlet3 = out2;
        Outlet<O3> outlet4 = out3;
        Outlet<O4> outlet5 = out4;
        Outlet<O5> outlet6 = out5;
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

