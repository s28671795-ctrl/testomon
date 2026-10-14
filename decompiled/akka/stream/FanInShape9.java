/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005e\u0001B\r\u001b\u0001}A\u0001b\r\u0001\u0003\u0002\u0003\u0006I\u0001\u000e\u0005\u0006w\u0001!\t\u0001\u0010\u0005\u0006w\u0001!\tA\u0017\u0005\u0006w\u0001!\t\u0001\u001b\u0005\b\u0003/\u0001A\u0011KA\r\u0011\u001d\t)\u0004\u0001C!\u0003oA\u0001B\u001b\u0001C\u0002\u0013\u0005\u0011\u0011\b\u0005\t\u0003\u007f\u0001\u0001\u0015!\u0003\u0002<!Aq\u000e\u0001b\u0001\n\u0003\t\t\u0005\u0003\u0005\u0002H\u0001\u0001\u000b\u0011BA\"\u0011!\u0011\bA1A\u0005\u0002\u0005%\u0003\u0002CA(\u0001\u0001\u0006I!a\u0013\t\u0011U\u0004!\u0019!C\u0001\u0003#B\u0001\"a\u0016\u0001A\u0003%\u00111\u000b\u0005\tq\u0002\u0011\r\u0011\"\u0001\u0002Z!A\u0011q\f\u0001!\u0002\u0013\tY\u0006\u0003\u0005|\u0001\t\u0007I\u0011AA1\u0011!\t9\u0007\u0001Q\u0001\n\u0005\r\u0004\u0002\u0003@\u0001\u0005\u0004%\t!!\u001b\t\u0011\u0005=\u0004\u0001)A\u0005\u0003WB\u0011\"a\u0001\u0001\u0005\u0004%\t!!\u001d\t\u0011\u0005]\u0004\u0001)A\u0005\u0003gB\u0011\"!\u0003\u0001\u0005\u0004%\t!!\u001f\t\u0011\u0005}\u0004\u0001)A\u0005\u0003w\u00121BR1o\u0013:\u001c\u0006.\u00199fs)\u00111\u0004H\u0001\u0007gR\u0014X-Y7\u000b\u0003u\tA!Y6lC\u000e\u0001Qc\u0003\u0011@\u0005\u0016C5JT)U/\u001e\u001a\"\u0001A\u0011\u0011\u0007\t\u001aS%D\u0001\u001b\u0013\t!#D\u0001\u0006GC:Len\u00155ba\u0016\u0004\"AJ\u0014\r\u0001\u00111\u0001\u0006\u0001CC\u0002%\u0012\u0011aT\t\u0003UA\u0002\"a\u000b\u0018\u000e\u00031R\u0011!L\u0001\u0006g\u000e\fG.Y\u0005\u0003_1\u0012qAT8uQ&tw\r\u0005\u0002,c%\u0011!\u0007\f\u0002\u0004\u0003:L\u0018!B0j]&$\bcA\u001b9K9\u0011!EN\u0005\u0003oi\t!BR1o\u0013:\u001c\u0006.\u00199f\u0013\tI$H\u0001\u0003J]&$(BA\u001c\u001b\u0003\u0019a\u0014N\\5u}Q\u0011Q(\u0017\t\rE\u0001q\u0014\tR$K\u001bB\u001bf+\n\t\u0003M}\"a\u0001\u0011\u0001\t\u0006\u0004I#A\u0001+1!\t1#\t\u0002\u0004D\u0001!\u0015\r!\u000b\u0002\u0003)F\u0002\"AJ#\u0005\r\u0019\u0003\u0001R1\u0001*\u0005\t!&\u0007\u0005\u0002'\u0011\u00121\u0011\n\u0001EC\u0002%\u0012!\u0001V\u001a\u0011\u0005\u0019ZEA\u0002'\u0001\u0011\u000b\u0007\u0011F\u0001\u0002UiA\u0011aE\u0014\u0003\u0007\u001f\u0002A)\u0019A\u0015\u0003\u0005Q+\u0004C\u0001\u0014R\t\u0019\u0011\u0006\u0001#b\u0001S\t\u0011AK\u000e\t\u0003MQ#a!\u0016\u0001\t\u0006\u0004I#A\u0001+8!\t1s\u000b\u0002\u0004Y\u0001!\u0015\r!\u000b\u0002\u0003)bBQa\r\u0002A\u0002Q\"\"!P.\t\u000bq\u001b\u0001\u0019A/\u0002\t9\fW.\u001a\t\u0003=\u0016t!aX2\u0011\u0005\u0001dS\"A1\u000b\u0005\tt\u0012A\u0002\u001fs_>$h(\u0003\u0002eY\u00051\u0001K]3eK\u001aL!AZ4\u0003\rM#(/\u001b8h\u0015\t!G\u0006\u0006\b>S:\fHo\u001e>~\u0003\u0003\t9!!\u0004\t\u000b)$\u0001\u0019A6\u0002\u0007%t\u0007\u0007E\u0002#YzJ!!\u001c\u000e\u0003\u000b%sG.\u001a;\t\u000b=$\u0001\u0019\u00019\u0002\u0007%t\u0017\u0007E\u0002#Y\u0006CQA\u001d\u0003A\u0002M\f1!\u001b83!\r\u0011C\u000e\u0012\u0005\u0006k\u0012\u0001\rA^\u0001\u0004S:\u001c\u0004c\u0001\u0012m\u000f\")\u0001\u0010\u0002a\u0001s\u0006\u0019\u0011N\u001c\u001b\u0011\u0007\tb'\nC\u0003|\t\u0001\u0007A0A\u0002j]V\u00022A\t7N\u0011\u0015qH\u00011\u0001\u0000\u0003\rIgN\u000e\t\u0004E1\u0004\u0006bBA\u0002\t\u0001\u0007\u0011QA\u0001\u0004S:<\u0004c\u0001\u0012m'\"9\u0011\u0011\u0002\u0003A\u0002\u0005-\u0011aA5oqA\u0019!\u0005\u001c,\t\u000f\u0005=A\u00011\u0001\u0002\u0012\u0005\u0019q.\u001e;\u0011\t\t\n\u0019\"J\u0005\u0004\u0003+Q\"AB(vi2,G/A\u0005d_:\u001cHO];diR\u0019\u0011%a\u0007\t\u000f\u0005uQ\u00011\u0001\u0002 \u0005!\u0011N\\5u!\u0011)\u0004(!\t+\u0007\u0015\n\u0019c\u000b\u0002\u0002&A!\u0011qEA\u0019\u001b\t\tIC\u0003\u0003\u0002,\u00055\u0012!C;oG\",7m[3e\u0015\r\ty\u0003L\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\u001a\u0003S\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0003!!W-\u001a9D_BLH#A\u001f\u0016\u0005\u0005m\u0002\u0003\u0002\u0012m\u0003{Q3APA\u0012\u0003\u0011Ig\u000e\r\u0011\u0016\u0005\u0005\r\u0003\u0003\u0002\u0012m\u0003\u000bR3!QA\u0012\u0003\u0011Ig.\r\u0011\u0016\u0005\u0005-\u0003\u0003\u0002\u0012m\u0003\u001bR3\u0001RA\u0012\u0003\u0011IgN\r\u0011\u0016\u0005\u0005M\u0003\u0003\u0002\u0012m\u0003+R3aRA\u0012\u0003\u0011Ign\r\u0011\u0016\u0005\u0005m\u0003\u0003\u0002\u0012m\u0003;R3ASA\u0012\u0003\u0011Ig\u000e\u000e\u0011\u0016\u0005\u0005\r\u0004\u0003\u0002\u0012m\u0003KR3!TA\u0012\u0003\u0011Ig.\u000e\u0011\u0016\u0005\u0005-\u0004\u0003\u0002\u0012m\u0003[R3\u0001UA\u0012\u0003\u0011IgN\u000e\u0011\u0016\u0005\u0005M\u0004\u0003\u0002\u0012m\u0003kR3aUA\u0012\u0003\u0011Ign\u000e\u0011\u0016\u0005\u0005m\u0004\u0003\u0002\u0012m\u0003{R3AVA\u0012\u0003\u0011Ig\u000e\u000f\u0011")
public class FanInShape9<T0, T1, T2, T3, T4, T5, T6, T7, T8, O>
extends FanInShape<O> {
    private final Inlet<T0> in0;
    private final Inlet<T1> in1;
    private final Inlet<T2> in2;
    private final Inlet<T3> in3;
    private final Inlet<T4> in4;
    private final Inlet<T5> in5;
    private final Inlet<T6> in6;
    private final Inlet<T7> in7;
    private final Inlet<T8> in8;

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape9<T0, T1, T2, T3, T4, T5, T6, T7, T8, O>(init2);
    }

    @Override
    public FanInShape9<T0, T1, T2, T3, T4, T5, T6, T7, T8, O> deepCopy() {
        return (FanInShape9)super.deepCopy();
    }

    public Inlet<T0> in0() {
        return this.in0;
    }

    public Inlet<T1> in1() {
        return this.in1;
    }

    public Inlet<T2> in2() {
        return this.in2;
    }

    public Inlet<T3> in3() {
        return this.in3;
    }

    public Inlet<T4> in4() {
        return this.in4;
    }

    public Inlet<T5> in5() {
        return this.in5;
    }

    public Inlet<T6> in6() {
        return this.in6;
    }

    public Inlet<T7> in7() {
        return this.in7;
    }

    public Inlet<T8> in8() {
        return this.in8;
    }

    public FanInShape9(FanInShape.Init<O> _init) {
        super(_init);
        this.in0 = this.newInlet("in0");
        this.in1 = this.newInlet("in1");
        this.in2 = this.newInlet("in2");
        this.in3 = this.newInlet("in3");
        this.in4 = this.newInlet("in4");
        this.in5 = this.newInlet("in5");
        this.in6 = this.newInlet("in6");
        this.in7 = this.newInlet("in7");
        this.in8 = this.newInlet("in8");
    }

    public FanInShape9(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape9(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Inlet<T8> in8, Outlet<O> out) {
        Inlet<T0> inlet = in0;
        Inlet<T1> inlet2 = in1;
        Inlet<T2> inlet3 = in2;
        Inlet<T3> inlet4 = in3;
        Inlet<T4> inlet5 = in4;
        Inlet<T5> inlet6 = in5;
        Inlet<T6> inlet7 = in6;
        Inlet<T7> inlet8 = in7;
        Inlet<T8> inlet9 = in8;
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet9).$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

