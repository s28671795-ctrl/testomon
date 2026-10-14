/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t\u001db\u0001B\u0014)\u00015B\u0001\"\u0011\u0001\u0003\u0002\u0003\u0006IA\u0011\u0005\u0006'\u0002!\t\u0001\u0016\u0005\u0007'\u0002!\t!a\u0004\t\rM\u0003A\u0011AA\u0016\u0011\u001d\tY\n\u0001C)\u0003;Cq!a)\u0001\t\u0003\n)\u000bC\u0005\u0002:\u0001\u0011\r\u0011\"\u0001\u0002(\"A\u0011Q\u0016\u0001!\u0002\u0013\tI\u000bC\u0005\u0002D\u0001\u0011\r\u0011\"\u0001\u00020\"A\u0011Q\u0017\u0001!\u0002\u0013\t\t\fC\u0005\u0002J\u0001\u0011\r\u0011\"\u0001\u00028\"A\u0011Q\u0018\u0001!\u0002\u0013\tI\fC\u0005\u0002P\u0001\u0011\r\u0011\"\u0001\u0002@\"A\u0011Q\u0019\u0001!\u0002\u0013\t\t\rC\u0005\u0002V\u0001\u0011\r\u0011\"\u0001\u0002H\"A\u0011Q\u001a\u0001!\u0002\u0013\tI\rC\u0005\u0002\\\u0001\u0011\r\u0011\"\u0001\u0002P\"A\u0011Q\u001b\u0001!\u0002\u0013\t\t\u000eC\u0005\u0002b\u0001\u0011\r\u0011\"\u0001\u0002X\"A\u0011Q\u001c\u0001!\u0002\u0013\tI\u000eC\u0005\u0002h\u0001\u0011\r\u0011\"\u0001\u0002`\"A\u0011Q\u001d\u0001!\u0002\u0013\t\t\u000fC\u0005\u0002n\u0001\u0011\r\u0011\"\u0001\u0002h\"A\u0011Q\u001e\u0001!\u0002\u0013\tI\u000fC\u0005\u0002t\u0001\u0011\r\u0011\"\u0001\u0002p\"A\u0011Q\u001f\u0001!\u0002\u0013\t\t\u0010C\u0005\u0002z\u0001\u0011\r\u0011\"\u0001\u0002x\"A\u0011Q \u0001!\u0002\u0013\tI\u0010C\u0005\u0002\u0000\u0001\u0011\r\u0011\"\u0001\u0002\u0000\"A!Q\u0001\u0001!\u0002\u0013\u0011\t\u0001C\u0005\u0002\u0006\u0002\u0011\r\u0011\"\u0001\u0003\b!A!Q\u0002\u0001!\u0002\u0013\u0011I\u0001C\u0005\u0002\f\u0002\u0011\r\u0011\"\u0001\u0003\u0010!A!Q\u0003\u0001!\u0002\u0013\u0011\t\u0002C\u0005\u0002\u0012\u0002\u0011\r\u0011\"\u0001\u0003\u0018!A!Q\u0004\u0001!\u0002\u0013\u0011I\u0002C\u0005\u0002\u0018\u0002\u0011\r\u0011\"\u0001\u0003 !A!Q\u0005\u0001!\u0002\u0013\u0011\tCA\u0007GC:|U\u000f^*iCB,\u0017G\u000e\u0006\u0003S)\naa\u001d;sK\u0006l'\"A\u0016\u0002\t\u0005\\7.Y\u0002\u0001+QqSg\u0016.^A\u000e4\u0017\u000e\\8skb\\h0a\u0001\u0002\nM\u0011\u0001a\f\t\u0004aE\u001aT\"\u0001\u0015\n\u0005IB#a\u0003$b]>+Ho\u00155ba\u0016\u0004\"\u0001N\u001b\r\u0001\u00111a\u0007\u0001EC\u0002]\u0012\u0011!S\t\u0003qy\u0002\"!\u000f\u001f\u000e\u0003iR\u0011aO\u0001\u0006g\u000e\fG.Y\u0005\u0003{i\u0012qAT8uQ&tw\r\u0005\u0002:\u007f%\u0011\u0001I\u000f\u0002\u0004\u0003:L\u0018!B0j]&$\bcA\"G\u0013:\u0011\u0001\u0007R\u0005\u0003\u000b\"\n1BR1o\u001fV$8\u000b[1qK&\u0011q\t\u0013\u0002\u0005\u0013:LGO\u0003\u0002FQ)\u00121GS\u0016\u0002\u0017B\u0011A*U\u0007\u0002\u001b*\u0011ajT\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001\u0015\u001e\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002S\u001b\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\rqJg.\u001b;?)\r)\u0016Q\u0002\t\u0016a\u0001\u0019d+\u0017/`E\u0016D7N\\9uojl\u0018\u0011AA\u0004!\t!t\u000b\u0002\u0004Y\u0001\u0011\u0015\ra\u000e\u0002\u0003\u001fB\u0002\"\u0001\u000e.\u0005\rm\u0003AQ1\u00018\u0005\ty\u0015\u0007\u0005\u00025;\u00121a\f\u0001CC\u0002]\u0012!a\u0014\u001a\u0011\u0005Q\u0002GAB1\u0001\t\u000b\u0007qG\u0001\u0002PgA\u0011Ag\u0019\u0003\u0007I\u0002!)\u0019A\u001c\u0003\u0005=#\u0004C\u0001\u001bg\t\u00199\u0007\u0001\"b\u0001o\t\u0011q*\u000e\t\u0003i%$aA\u001b\u0001\u0005\u0006\u00049$AA(7!\t!D\u000e\u0002\u0004n\u0001\u0011\u0015\ra\u000e\u0002\u0003\u001f^\u0002\"\u0001N8\u0005\rA\u0004AQ1\u00018\u0005\ty\u0005\b\u0005\u00025e\u001211\u000f\u0001CC\u0002]\u0012!aT\u001d\u0011\u0005Q*HA\u0002<\u0001\t\u000b\u0007qGA\u0002PcA\u0002\"\u0001\u000e=\u0005\re\u0004AQ1\u00018\u0005\ry\u0015'\r\t\u0003im$a\u0001 \u0001\u0005\u0006\u00049$aA(2eA\u0011AG \u0003\u0007\u007f\u0002!)\u0019A\u001c\u0003\u0007=\u000b4\u0007E\u00025\u0003\u0007!q!!\u0002\u0001\t\u000b\u0007qGA\u0002PcQ\u00022\u0001NA\u0005\t\u001d\tY\u0001\u0001CC\u0002]\u00121aT\u00196\u0011\u0015\t%\u00011\u0001C)\r)\u0016\u0011\u0003\u0005\b\u0003'\u0019\u0001\u0019AA\u000b\u0003\u0011q\u0017-\\3\u0011\t\u0005]\u0011Q\u0005\b\u0005\u00033\t\t\u0003E\u0002\u0002\u001cij!!!\b\u000b\u0007\u0005}A&\u0001\u0004=e>|GOP\u0005\u0004\u0003GQ\u0014A\u0002)sK\u0012,g-\u0003\u0003\u0002(\u0005%\"AB*ue&twMC\u0002\u0002$i\"2%VA\u0017\u0003o\t\t%a\u0012\u0002N\u0005M\u0013\u0011LA0\u0003K\nY'!\u001d\u0002x\u0005u\u00141QAE\u0003\u001f\u000b)\nC\u0004\u00020\u0011\u0001\r!!\r\u0002\u0005%t\u0007\u0003\u0002\u0019\u00024MJ1!!\u000e)\u0005\u0015Ie\u000e\\3u\u0011\u001d\tI\u0004\u0002a\u0001\u0003w\tAa\\;uaA!\u0001'!\u0010W\u0013\r\ty\u0004\u000b\u0002\u0007\u001fV$H.\u001a;\t\u000f\u0005\rC\u00011\u0001\u0002F\u0005!q.\u001e;2!\u0011\u0001\u0014QH-\t\u000f\u0005%C\u00011\u0001\u0002L\u0005!q.\u001e;3!\u0011\u0001\u0014Q\b/\t\u000f\u0005=C\u00011\u0001\u0002R\u0005!q.\u001e;4!\u0011\u0001\u0014QH0\t\u000f\u0005UC\u00011\u0001\u0002X\u0005!q.\u001e;5!\u0011\u0001\u0014Q\b2\t\u000f\u0005mC\u00011\u0001\u0002^\u0005!q.\u001e;6!\u0011\u0001\u0014QH3\t\u000f\u0005\u0005D\u00011\u0001\u0002d\u0005!q.\u001e;7!\u0011\u0001\u0014Q\b5\t\u000f\u0005\u001dD\u00011\u0001\u0002j\u0005!q.\u001e;8!\u0011\u0001\u0014QH6\t\u000f\u00055D\u00011\u0001\u0002p\u0005!q.\u001e;9!\u0011\u0001\u0014Q\b8\t\u000f\u0005MD\u00011\u0001\u0002v\u0005!q.\u001e;:!\u0011\u0001\u0014QH9\t\u000f\u0005eD\u00011\u0001\u0002|\u0005)q.\u001e;2aA!\u0001'!\u0010u\u0011\u001d\ty\b\u0002a\u0001\u0003\u0003\u000bQa\\;ucE\u0002B\u0001MA\u001fo\"9\u0011Q\u0011\u0003A\u0002\u0005\u001d\u0015!B8viF\u0012\u0004\u0003\u0002\u0019\u0002>iDq!a#\u0005\u0001\u0004\ti)A\u0003pkR\f4\u0007\u0005\u00031\u0003{i\bbBAI\t\u0001\u0007\u00111S\u0001\u0006_V$\u0018\u0007\u000e\t\u0006a\u0005u\u0012\u0011\u0001\u0005\b\u0003/#\u0001\u0019AAM\u0003\u0015yW\u000f^\u00196!\u0015\u0001\u0014QHA\u0004\u0003%\u0019wN\\:ueV\u001cG\u000fF\u00020\u0003?Ca!!)\u0006\u0001\u0004\u0011\u0015\u0001B5oSR\f\u0001\u0002Z3fa\u000e{\u0007/\u001f\u000b\u0002+V\u0011\u0011\u0011\u0016\t\u0006a\u0005u\u00121\u0016\u0016\u0003-*\u000bQa\\;ua\u0001*\"!!-\u0011\u000bA\ni$a-+\u0005eS\u0015!B8viF\u0002SCAA]!\u0015\u0001\u0014QHA^U\ta&*A\u0003pkR\u0014\u0004%\u0006\u0002\u0002BB)\u0001'!\u0010\u0002D*\u0012qLS\u0001\u0006_V$8\u0007I\u000b\u0003\u0003\u0013\u0004R\u0001MA\u001f\u0003\u0017T#A\u0019&\u0002\u000b=,H\u000f\u000e\u0011\u0016\u0005\u0005E\u0007#\u0002\u0019\u0002>\u0005M'FA3K\u0003\u0015yW\u000f^\u001b!+\t\tI\u000eE\u00031\u0003{\tYN\u000b\u0002i\u0015\u0006)q.\u001e;7AU\u0011\u0011\u0011\u001d\t\u0006a\u0005u\u00121\u001d\u0016\u0003W*\u000bQa\\;uo\u0001*\"!!;\u0011\u000bA\ni$a;+\u00059T\u0015!B8vib\u0002SCAAy!\u0015\u0001\u0014QHAzU\t\t(*A\u0003pkRL\u0004%\u0006\u0002\u0002zB)\u0001'!\u0010\u0002|*\u0012AOS\u0001\u0007_V$\u0018\u0007\r\u0011\u0016\u0005\t\u0005\u0001#\u0002\u0019\u0002>\t\r!FA<K\u0003\u0019yW\u000f^\u00192AU\u0011!\u0011\u0002\t\u0006a\u0005u\"1\u0002\u0016\u0003u*\u000baa\\;ucI\u0002SC\u0001B\t!\u0015\u0001\u0014Q\bB\nU\ti(*\u0001\u0004pkR\f4\u0007I\u000b\u0003\u00053\u0001R\u0001MA\u001f\u00057Q3!!\u0001K\u0003\u0019yW\u000f^\u00195AU\u0011!\u0011\u0005\t\u0006a\u0005u\"1\u0005\u0016\u0004\u0003\u000fQ\u0015AB8viF*\u0004\u0005")
public class FanOutShape16<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15>
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
    private final Outlet<O11> out11;
    private final Outlet<O12> out12;
    private final Outlet<O13> out13;
    private final Outlet<O14> out14;
    private final Outlet<O15> out15;

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new FanOutShape16<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15>(init2);
    }

    @Override
    public FanOutShape16<I, O0, O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15> deepCopy() {
        return (FanOutShape16)super.deepCopy();
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

    public Outlet<O11> out11() {
        return this.out11;
    }

    public Outlet<O12> out12() {
        return this.out12;
    }

    public Outlet<O13> out13() {
        return this.out13;
    }

    public Outlet<O14> out14() {
        return this.out14;
    }

    public Outlet<O15> out15() {
        return this.out15;
    }

    public FanOutShape16(FanOutShape.Init<I> _init) {
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
        this.out11 = this.newOutlet("out11");
        this.out12 = this.newOutlet("out12");
        this.out13 = this.newOutlet("out13");
        this.out14 = this.newOutlet("out14");
        this.out15 = this.newOutlet("out15");
    }

    public FanOutShape16(String name) {
        this(new FanOutShape.Name(name));
    }

    public FanOutShape16(Inlet<I> in, Outlet<O0> out0, Outlet<O1> out1, Outlet<O2> out2, Outlet<O3> out3, Outlet<O4> out4, Outlet<O5> out5, Outlet<O6> out6, Outlet<O7> out7, Outlet<O8> out8, Outlet<O9> out9, Outlet<O10> out10, Outlet<O11> out11, Outlet<O12> out12, Outlet<O13> out13, Outlet<O14> out14, Outlet<O15> out15) {
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
        Outlet<O11> outlet12 = out11;
        Outlet<O12> outlet13 = out12;
        Outlet<O13> outlet14 = out13;
        Outlet<O14> outlet15 = out14;
        Outlet<O15> outlet16 = out15;
        this(new FanOutShape.Ports<I>(in, Nil$.MODULE$.$colon$colon(outlet16).$colon$colon(outlet15).$colon$colon(outlet14).$colon$colon(outlet13).$colon$colon(outlet12).$colon$colon(outlet11).$colon$colon(outlet10).$colon$colon(outlet9).$colon$colon(outlet8).$colon$colon(outlet7).$colon$colon(outlet6).$colon$colon(outlet5).$colon$colon(outlet4).$colon$colon(outlet3).$colon$colon(outlet2).$colon$colon(outlet)));
    }
}

