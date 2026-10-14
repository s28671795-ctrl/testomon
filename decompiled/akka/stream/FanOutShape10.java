/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]e\u0001B\u000e\u001d\u0001\u0005B\u0001\"\u000e\u0001\u0003\u0002\u0003\u0006IA\u000e\u0005\u0006\u000f\u0002!\t\u0001\u0013\u0005\u0006\u000f\u0002!\t!\u001b\u0005\u0006\u000f\u0002!\ta\u001e\u0005\b\u0003w\u0001A\u0011KA\u001f\u0011\u001d\t\u0019\u0005\u0001C!\u0003\u000bB\u0001B \u0001C\u0002\u0013\u0005\u0011q\t\u0005\t\u0003\u001b\u0002\u0001\u0015!\u0003\u0002J!I\u0011q\u0001\u0001C\u0002\u0013\u0005\u0011q\n\u0005\t\u0003+\u0002\u0001\u0015!\u0003\u0002R!I\u0011Q\u0002\u0001C\u0002\u0013\u0005\u0011q\u000b\u0005\t\u0003;\u0002\u0001\u0015!\u0003\u0002Z!I\u00111\u0003\u0001C\u0002\u0013\u0005\u0011q\f\u0005\t\u0003K\u0002\u0001\u0015!\u0003\u0002b!I\u0011\u0011\u0004\u0001C\u0002\u0013\u0005\u0011q\r\u0005\t\u0003[\u0002\u0001\u0015!\u0003\u0002j!I\u0011q\u0004\u0001C\u0002\u0013\u0005\u0011q\u000e\u0005\t\u0003k\u0002\u0001\u0015!\u0003\u0002r!I\u0011Q\u0005\u0001C\u0002\u0013\u0005\u0011q\u000f\u0005\t\u0003{\u0002\u0001\u0015!\u0003\u0002z!I\u00111\u0006\u0001C\u0002\u0013\u0005\u0011q\u0010\u0005\t\u0003\u000b\u0003\u0001\u0015!\u0003\u0002\u0002\"I\u0011\u0011\u0007\u0001C\u0002\u0013\u0005\u0011q\u0011\u0005\t\u0003\u001b\u0003\u0001\u0015!\u0003\u0002\n\"I\u0011q\u0007\u0001C\u0002\u0013\u0005\u0011q\u0012\u0005\t\u0003+\u0003\u0001\u0015!\u0003\u0002\u0012\nia)\u00198PkR\u001c\u0006.\u00199fcAR!!\b\u0010\u0002\rM$(/Z1n\u0015\u0005y\u0012\u0001B1lW\u0006\u001c\u0001!\u0006\u0007#S-s\u0015\u000bV,[;\u0002\u001cgm\u0005\u0002\u0001GA\u0019A%J\u0014\u000e\u0003qI!A\n\u000f\u0003\u0017\u0019\u000bgnT;u'\"\f\u0007/\u001a\t\u0003Q%b\u0001\u0001\u0002\u0004+\u0001!\u0015\ra\u000b\u0002\u0002\u0013F\u0011AF\r\t\u0003[Aj\u0011A\f\u0006\u0002_\u0005)1oY1mC&\u0011\u0011G\f\u0002\b\u001d>$\b.\u001b8h!\ti3'\u0003\u00025]\t\u0019\u0011I\\=\u0002\u000b}Kg.\u001b;\u0011\u0007]RTH\u0004\u0002%q%\u0011\u0011\bH\u0001\f\r\u0006tw*\u001e;TQ\u0006\u0004X-\u0003\u0002<y\t!\u0011J\\5u\u0015\tIDD\u000b\u0002(}-\nq\b\u0005\u0002A\u000b6\t\u0011I\u0003\u0002C\u0007\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003\t:\n!\"\u00198o_R\fG/[8o\u0013\t1\u0015IA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fa\u0001P5oSRtDCA%i!5!\u0003a\n&N!N3\u0016\fX0cKB\u0011\u0001f\u0013\u0003\u0007\u0019\u0002!)\u0019A\u0016\u0003\u0005=\u0003\u0004C\u0001\u0015O\t\u0019y\u0005\u0001\"b\u0001W\t\u0011q*\r\t\u0003QE#aA\u0015\u0001\u0005\u0006\u0004Y#AA(3!\tAC\u000b\u0002\u0004V\u0001\u0011\u0015\ra\u000b\u0002\u0003\u001fN\u0002\"\u0001K,\u0005\ra\u0003AQ1\u0001,\u0005\tyE\u0007\u0005\u0002)5\u001211\f\u0001CC\u0002-\u0012!aT\u001b\u0011\u0005!jFA\u00020\u0001\t\u000b\u00071F\u0001\u0002PmA\u0011\u0001\u0006\u0019\u0003\u0007C\u0002!)\u0019A\u0016\u0003\u0005=;\u0004C\u0001\u0015d\t\u0019!\u0007\u0001\"b\u0001W\t\u0011q\n\u000f\t\u0003Q\u0019$aa\u001a\u0001\u0005\u0006\u0004Y#AA(:\u0011\u0015)$\u00011\u00017)\tI%\u000eC\u0003l\u0007\u0001\u0007A.\u0001\u0003oC6,\u0007CA7u\u001d\tq'\u000f\u0005\u0002p]5\t\u0001O\u0003\u0002rA\u00051AH]8pizJ!a\u001d\u0018\u0002\rA\u0013X\rZ3g\u0013\t)hO\u0001\u0004TiJLgn\u001a\u0006\u0003g:\"R#\u0013=~\u0003\u000b\tY!!\u0005\u0002\u0018\u0005u\u00111EA\u0015\u0003_\t)\u0004C\u0003z\t\u0001\u0007!0\u0001\u0002j]B\u0019Ae_\u0014\n\u0005qd\"!B%oY\u0016$\b\"\u0002@\u0005\u0001\u0004y\u0018\u0001B8viB\u0002B\u0001JA\u0001\u0015&\u0019\u00111\u0001\u000f\u0003\r=+H\u000f\\3u\u0011\u001d\t9\u0001\u0002a\u0001\u0003\u0013\tAa\\;ucA!A%!\u0001N\u0011\u001d\ti\u0001\u0002a\u0001\u0003\u001f\tAa\\;ueA!A%!\u0001Q\u0011\u001d\t\u0019\u0002\u0002a\u0001\u0003+\tAa\\;ugA!A%!\u0001T\u0011\u001d\tI\u0002\u0002a\u0001\u00037\tAa\\;uiA!A%!\u0001W\u0011\u001d\ty\u0002\u0002a\u0001\u0003C\tAa\\;ukA!A%!\u0001Z\u0011\u001d\t)\u0003\u0002a\u0001\u0003O\tAa\\;umA!A%!\u0001]\u0011\u001d\tY\u0003\u0002a\u0001\u0003[\tAa\\;uoA!A%!\u0001`\u0011\u001d\t\t\u0004\u0002a\u0001\u0003g\tAa\\;uqA!A%!\u0001c\u0011\u001d\t9\u0004\u0002a\u0001\u0003s\tAa\\;usA!A%!\u0001f\u0003%\u0019wN\\:ueV\u001cG\u000fF\u0002$\u0003\u007fAa!!\u0011\u0006\u0001\u00041\u0014\u0001B5oSR\f\u0001\u0002Z3fa\u000e{\u0007/\u001f\u000b\u0002\u0013V\u0011\u0011\u0011\n\t\u0006I\u0005\u0005\u00111\n\u0016\u0003\u0015z\nQa\\;ua\u0001*\"!!\u0015\u0011\u000b\u0011\n\t!a\u0015+\u00055s\u0014!B8viF\u0002SCAA-!\u0015!\u0013\u0011AA.U\t\u0001f(A\u0003pkR\u0014\u0004%\u0006\u0002\u0002bA)A%!\u0001\u0002d)\u00121KP\u0001\u0006_V$8\u0007I\u000b\u0003\u0003S\u0002R\u0001JA\u0001\u0003WR#A\u0016 \u0002\u000b=,H\u000f\u000e\u0011\u0016\u0005\u0005E\u0004#\u0002\u0013\u0002\u0002\u0005M$FA-?\u0003\u0015yW\u000f^\u001b!+\t\tI\bE\u0003%\u0003\u0003\tYH\u000b\u0002]}\u0005)q.\u001e;7AU\u0011\u0011\u0011\u0011\t\u0006I\u0005\u0005\u00111\u0011\u0016\u0003?z\nQa\\;uo\u0001*\"!!#\u0011\u000b\u0011\n\t!a#+\u0005\tt\u0014!B8vib\u0002SCAAI!\u0015!\u0013\u0011AAJU\t)g(A\u0003pkRL\u0004\u0005")
public class FanOutShape10<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9>
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

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape10<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9>(init2);
    }

    @Override
    public FanOutShape10<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9> deepCopy() {
        return (FanOutShape10)super.deepCopy();
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

    public FanOutShape10(FanOutShape.Init<I> _init) {
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
    }

    public FanOutShape10(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape10(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7, Outlet<O8> out8, Outlet<O9> out9) {
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
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet10).$colon$colon(outlet9).$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

