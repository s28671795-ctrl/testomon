/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005=f\u0001B\u000f\u001f\u0001\rB\u0001b\u000e\u0001\u0003\u0002\u0003\u0006I\u0001\u000f\u0005\u0006\u0013\u0002!\tA\u0013\u0005\u0006\u0013\u0002!\tA\u001c\u0005\u0006\u0013\u0002!\t\u0001 \u0005\b\u0003\u0017\u0002A\u0011KA'\u0011\u001d\t\u0019\u0006\u0001C!\u0003+B\u0011\"a\u0002\u0001\u0005\u0004%\t!a\u0016\t\u0011\u0005u\u0003\u0001)A\u0005\u00033B\u0011\"!\u0005\u0001\u0005\u0004%\t!a\u0018\t\u0011\u0005\u0015\u0004\u0001)A\u0005\u0003CB\u0011\"a\u0006\u0001\u0005\u0004%\t!a\u001a\t\u0011\u00055\u0004\u0001)A\u0005\u0003SB\u0011\"!\b\u0001\u0005\u0004%\t!a\u001c\t\u0011\u0005U\u0004\u0001)A\u0005\u0003cB\u0011\"a\t\u0001\u0005\u0004%\t!a\u001e\t\u0011\u0005u\u0004\u0001)A\u0005\u0003sB\u0011\"!\u000b\u0001\u0005\u0004%\t!a \t\u0011\u0005\u0015\u0005\u0001)A\u0005\u0003\u0003C\u0011\"a\f\u0001\u0005\u0004%\t!a\"\t\u0011\u00055\u0005\u0001)A\u0005\u0003\u0013C\u0011\"!\u000e\u0001\u0005\u0004%\t!a$\t\u0011\u0005U\u0005\u0001)A\u0005\u0003#C\u0011\"a\u000f\u0001\u0005\u0004%\t!a&\t\u0011\u0005u\u0005\u0001)A\u0005\u00033C\u0011\"!\u0011\u0001\u0005\u0004%\t!a(\t\u0011\u0005\u0015\u0006\u0001)A\u0005\u0003CC\u0011\"a\u0012\u0001\u0005\u0004%\t!a*\t\u0011\u00055\u0006\u0001)A\u0005\u0003S\u0013QBR1o\u001fV$8\u000b[1qKF\n$BA\u0010!\u0003\u0019\u0019HO]3b[*\t\u0011%\u0001\u0003bW.\f7\u0001A\u000b\u000eI-j\u0005k\u0015,Z9~\u0013W\r[6\u0014\u0005\u0001)\u0003c\u0001\u0014(S5\ta$\u0003\u0002)=\tYa)\u00198PkR\u001c\u0006.\u00199f!\tQ3\u0006\u0004\u0001\u0005\r1\u0002\u0001R1\u0001.\u0005\u0005I\u0015C\u0001\u00185!\ty#'D\u00011\u0015\u0005\t\u0014!B:dC2\f\u0017BA\u001a1\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aL\u001b\n\u0005Y\u0002$aA!os\u0006)q,\u001b8jiB\u0019\u0011\bP \u000f\u0005\u0019R\u0014BA\u001e\u001f\u0003-1\u0015M\\(viNC\u0017\r]3\n\u0005ur$\u0001B%oSRT!a\u000f\u0010+\u0005%\u00025&A!\u0011\u0005\t;U\"A\"\u000b\u0005\u0011+\u0015!C;oG\",7m[3e\u0015\t1\u0005'\u0001\u0006b]:|G/\u0019;j_:L!\u0001S\"\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\u0004=S:LGO\u0010\u000b\u0003\u00176\u0004bB\n\u0001*\u0019>\u0013V\u000bW._C\u0012<'\u000e\u0005\u0002+\u001b\u00121a\n\u0001CC\u00025\u0012!a\u0014\u0019\u0011\u0005)\u0002FAB)\u0001\t\u000b\u0007QF\u0001\u0002PcA\u0011!f\u0015\u0003\u0007)\u0002!)\u0019A\u0017\u0003\u0005=\u0013\u0004C\u0001\u0016W\t\u00199\u0006\u0001\"b\u0001[\t\u0011qj\r\t\u0003Ue#aA\u0017\u0001\u0005\u0006\u0004i#AA(5!\tQC\f\u0002\u0004^\u0001\u0011\u0015\r!\f\u0002\u0003\u001fV\u0002\"AK0\u0005\r\u0001\u0004AQ1\u0001.\u0005\tye\u0007\u0005\u0002+E\u001211\r\u0001CC\u00025\u0012!aT\u001c\u0011\u0005)*GA\u00024\u0001\t\u000b\u0007QF\u0001\u0002PqA\u0011!\u0006\u001b\u0003\u0007S\u0002!)\u0019A\u0017\u0003\u0005=K\u0004C\u0001\u0016l\t\u0019a\u0007\u0001\"b\u0001[\t\u0019q*\r\u0019\t\u000b]\u0012\u0001\u0019\u0001\u001d\u0015\u0005-{\u0007\"\u00029\u0004\u0001\u0004\t\u0018\u0001\u00028b[\u0016\u0004\"A]=\u000f\u0005M<\bC\u0001;1\u001b\u0005)(B\u0001<#\u0003\u0019a$o\\8u}%\u0011\u0001\u0010M\u0001\u0007!J,G-\u001a4\n\u0005i\\(AB*ue&twM\u0003\u0002yaQA2*`A\u0003\u0003\u001f\t)\"a\u0007\u0002\"\u0005\u001d\u0012QFA\u001a\u0003s\ty$!\u0012\t\u000by$\u0001\u0019A@\u0002\u0005%t\u0007\u0003\u0002\u0014\u0002\u0002%J1!a\u0001\u001f\u0005\u0015Ie\u000e\\3u\u0011\u001d\t9\u0001\u0002a\u0001\u0003\u0013\tAa\\;uaA!a%a\u0003M\u0013\r\tiA\b\u0002\u0007\u001fV$H.\u001a;\t\u000f\u0005EA\u00011\u0001\u0002\u0014\u0005!q.\u001e;2!\u00111\u00131B(\t\u000f\u0005]A\u00011\u0001\u0002\u001a\u0005!q.\u001e;3!\u00111\u00131\u0002*\t\u000f\u0005uA\u00011\u0001\u0002 \u0005!q.\u001e;4!\u00111\u00131B+\t\u000f\u0005\rB\u00011\u0001\u0002&\u0005!q.\u001e;5!\u00111\u00131\u0002-\t\u000f\u0005%B\u00011\u0001\u0002,\u0005!q.\u001e;6!\u00111\u00131B.\t\u000f\u0005=B\u00011\u0001\u00022\u0005!q.\u001e;7!\u00111\u00131\u00020\t\u000f\u0005UB\u00011\u0001\u00028\u0005!q.\u001e;8!\u00111\u00131B1\t\u000f\u0005mB\u00011\u0001\u0002>\u0005!q.\u001e;9!\u00111\u00131\u00023\t\u000f\u0005\u0005C\u00011\u0001\u0002D\u0005!q.\u001e;:!\u00111\u00131B4\t\u000f\u0005\u001dC\u00011\u0001\u0002J\u0005)q.\u001e;2aA!a%a\u0003k\u0003%\u0019wN\\:ueV\u001cG\u000fF\u0002&\u0003\u001fBa!!\u0015\u0006\u0001\u0004A\u0014\u0001B5oSR\f\u0001\u0002Z3fa\u000e{\u0007/\u001f\u000b\u0002\u0017V\u0011\u0011\u0011\f\t\u0006M\u0005-\u00111\f\u0016\u0003\u0019\u0002\u000bQa\\;ua\u0001*\"!!\u0019\u0011\u000b\u0019\nY!a\u0019+\u0005=\u0003\u0015!B8viF\u0002SCAA5!\u00151\u00131BA6U\t\u0011\u0006)A\u0003pkR\u0014\u0004%\u0006\u0002\u0002rA)a%a\u0003\u0002t)\u0012Q\u000bQ\u0001\u0006_V$8\u0007I\u000b\u0003\u0003s\u0002RAJA\u0006\u0003wR#\u0001\u0017!\u0002\u000b=,H\u000f\u000e\u0011\u0016\u0005\u0005\u0005\u0005#\u0002\u0014\u0002\f\u0005\r%FA.A\u0003\u0015yW\u000f^\u001b!+\t\tI\tE\u0003'\u0003\u0017\tYI\u000b\u0002_\u0001\u0006)q.\u001e;7AU\u0011\u0011\u0011\u0013\t\u0006M\u0005-\u00111\u0013\u0016\u0003C\u0002\u000bQa\\;uo\u0001*\"!!'\u0011\u000b\u0019\nY!a'+\u0005\u0011\u0004\u0015!B8vib\u0002SCAAQ!\u00151\u00131BARU\t9\u0007)A\u0003pkRL\u0004%\u0006\u0002\u0002*B)a%a\u0003\u0002,*\u0012!\u000eQ\u0001\u0007_V$\u0018\u0007\r\u0011")
public class FanOutShape11<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10>
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
    private final Outlet<O9> out9;
    private final Outlet<O10> out10;

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape11<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10>(init2);
    }

    @Override
    public FanOutShape11<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10> deepCopy() {
        return (FanOutShape11)super.deepCopy();
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

    public Outlet<O9> out9() {
        return this.out9;
    }

    public Outlet<O10> out10() {
        return this.out10;
    }

    public FanOutShape11(FanOutShape.Init<I> _init) {
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
        this.out9 = this.newOutlet("out9");
        this.out10 = this.newOutlet("out10");
    }

    public FanOutShape11(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape11(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7, Outlet<O8> out8, Outlet<O9> out9, Outlet<O10> out10) {
        Outlet<O0> outlet = out0;
        Outlet<O1> outlet2 = out1;
        Outlet<O2> outlet3 = out2;
        Outlet<O3> outlet4 = out3;
        Outlet<O4> outlet5 = out4;
        Outlet<O5> outlet6 = out5;
        Outlet<O6> outlet7 = out6;
        Outlet<O7> outlet8 = out7;
        Outlet<O8> outlet9 = out8;
        Outlet<O9> outlet10 = out9;
        Outlet<O10> outlet11 = out10;
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet11).$colon$colon(outlet10).$colon$colon(outlet9).$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

