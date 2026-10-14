/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tec\u0001B\u0016-\u0001EB\u0001\"\u0012\u0001\u0003\u0002\u0003\u0006IA\u0012\u0005\u0006\u001b\u0002!\tA\u0014\u0005\u0007\u001b\u0002!\t!a\u0004\t\r5\u0003A\u0011AA\u0016\u0011\u001d\t9\u000b\u0001C)\u0003SCq!!2\u0001\t\u0003\n9\rC\u0005\u00020\u0001\u0011\r\u0011\"\u0001\u0002J\"A\u0011q\u001a\u0001!\u0002\u0013\tY\rC\u0005\u0002:\u0001\u0011\r\u0011\"\u0001\u0002R\"A\u0011q\u001b\u0001!\u0002\u0013\t\u0019\u000eC\u0005\u0002@\u0001\u0011\r\u0011\"\u0001\u0002Z\"A\u0011q\u001c\u0001!\u0002\u0013\tY\u000eC\u0005\u0002F\u0001\u0011\r\u0011\"\u0001\u0002b\"A\u0011q\u001d\u0001!\u0002\u0013\t\u0019\u000fC\u0005\u0002L\u0001\u0011\r\u0011\"\u0001\u0002j\"A\u0011q\u001e\u0001!\u0002\u0013\tY\u000fC\u0005\u0002R\u0001\u0011\r\u0011\"\u0001\u0002r\"A\u0011q\u001f\u0001!\u0002\u0013\t\u0019\u0010C\u0005\u0002X\u0001\u0011\r\u0011\"\u0001\u0002z\"A\u0011q \u0001!\u0002\u0013\tY\u0010C\u0005\u0002^\u0001\u0011\r\u0011\"\u0001\u0003\u0002!A!q\u0001\u0001!\u0002\u0013\u0011\u0019\u0001C\u0005\u0002d\u0001\u0011\r\u0011\"\u0001\u0003\n!A!q\u0002\u0001!\u0002\u0013\u0011Y\u0001C\u0005\u0002j\u0001\u0011\r\u0011\"\u0001\u0003\u0012!A!q\u0003\u0001!\u0002\u0013\u0011\u0019\u0002C\u0005\u0002p\u0001\u0011\r\u0011\"\u0001\u0003\u001a!A!q\u0004\u0001!\u0002\u0013\u0011Y\u0002C\u0005\u0002v\u0001\u0011\r\u0011\"\u0001\u0003\"!A!q\u0005\u0001!\u0002\u0013\u0011\u0019\u0003C\u0005\u0002|\u0001\u0011\r\u0011\"\u0001\u0003*!A!q\u0006\u0001!\u0002\u0013\u0011Y\u0003C\u0005\u0002\u0002\u0002\u0011\r\u0011\"\u0001\u00032!A!q\u0007\u0001!\u0002\u0013\u0011\u0019\u0004C\u0005\u0002\b\u0002\u0011\r\u0011\"\u0001\u0003:!A!q\b\u0001!\u0002\u0013\u0011Y\u0004C\u0005\u0002\u000e\u0002\u0011\r\u0011\"\u0001\u0003B!A!q\t\u0001!\u0002\u0013\u0011\u0019\u0005C\u0005\u0002\u0014\u0002\u0011\r\u0011\"\u0001\u0003J!A!q\n\u0001!\u0002\u0013\u0011Y\u0005C\u0005\u0002\u001a\u0002\u0011\r\u0011\"\u0001\u0003R!A!q\u000b\u0001!\u0002\u0013\u0011\u0019F\u0001\u0007GC:Len\u00155ba\u0016\f\u0004H\u0003\u0002.]\u000511\u000f\u001e:fC6T\u0011aL\u0001\u0005C.\\\u0017m\u0001\u0001\u0016-I\nFk\u0016.^A\u000e4\u0017\u000e\\8skb\\h0a\u0001\u0002\ne\u001a\"\u0001A\u001a\u0011\u0007Q*t'D\u0001-\u0013\t1DF\u0001\u0006GC:Len\u00155ba\u0016\u0004\"\u0001O\u001d\r\u0001\u00111!\b\u0001CC\u0002m\u0012\u0011aT\t\u0003y\t\u0003\"!\u0010!\u000e\u0003yR\u0011aP\u0001\u0006g\u000e\fG.Y\u0005\u0003\u0003z\u0012qAT8uQ&tw\r\u0005\u0002>\u0007&\u0011AI\u0010\u0002\u0004\u0003:L\u0018!B0j]&$\bcA$Ko9\u0011A\u0007S\u0005\u0003\u00132\n!BR1o\u0013:\u001c\u0006.\u00199f\u0013\tYEJ\u0001\u0003J]&$(BA%-\u0003\u0019a\u0014N\\5u}Q\u0019q*!\u0004\u0011/Q\u0002\u0001k\u0015,Z9~\u0013W\r[6ocR<(0`A\u0001\u0003\u000f9\u0004C\u0001\u001dR\t\u0019\u0011\u0006\u0001#b\u0001w\t\u0011A\u000b\r\t\u0003qQ#a!\u0016\u0001\t\u0006\u0004Y$A\u0001+2!\tAt\u000b\u0002\u0004Y\u0001!\u0015\ra\u000f\u0002\u0003)J\u0002\"\u0001\u000f.\u0005\rm\u0003\u0001R1\u0001<\u0005\t!6\u0007\u0005\u00029;\u00121a\f\u0001EC\u0002m\u0012!\u0001\u0016\u001b\u0011\u0005a\u0002GAB1\u0001\u0011\u000b\u00071H\u0001\u0002UkA\u0011\u0001h\u0019\u0003\u0007I\u0002A)\u0019A\u001e\u0003\u0005Q3\u0004C\u0001\u001dg\t\u00199\u0007\u0001#b\u0001w\t\u0011Ak\u000e\t\u0003q%$aA\u001b\u0001\t\u0006\u0004Y$A\u0001+9!\tAD\u000e\u0002\u0004n\u0001!\u0015\ra\u000f\u0002\u0003)f\u0002\"\u0001O8\u0005\rA\u0004\u0001R1\u0001<\u0005\r!\u0016\u0007\r\t\u0003qI$aa\u001d\u0001\t\u0006\u0004Y$a\u0001+2cA\u0011\u0001(\u001e\u0003\u0007m\u0002A)\u0019A\u001e\u0003\u0007Q\u000b$\u0007\u0005\u00029q\u00121\u0011\u0010\u0001EC\u0002m\u00121\u0001V\u00194!\tA4\u0010\u0002\u0004}\u0001!\u0015\ra\u000f\u0002\u0004)F\"\u0004C\u0001\u001d\u007f\t\u0019y\b\u0001#b\u0001w\t\u0019A+M\u001b\u0011\u0007a\n\u0019\u0001B\u0004\u0002\u0006\u0001A)\u0019A\u001e\u0003\u0007Q\u000bd\u0007E\u00029\u0003\u0013!q!a\u0003\u0001\u0011\u000b\u00071HA\u0002Uc]BQ!\u0012\u0002A\u0002\u0019#2aTA\t\u0011\u001d\t\u0019b\u0001a\u0001\u0003+\tAA\\1nKB!\u0011qCA\u0013\u001d\u0011\tI\"!\t\u0011\u0007\u0005ma(\u0004\u0002\u0002\u001e)\u0019\u0011q\u0004\u0019\u0002\rq\u0012xn\u001c;?\u0013\r\t\u0019CP\u0001\u0007!J,G-\u001a4\n\t\u0005\u001d\u0012\u0011\u0006\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005\rb\bF\u0014P\u0003[\t9$!\u0010\u0002D\u0005%\u0013qJA+\u00037\n\t'a\u001a\u0002n\u0005M\u0014\u0011PA@\u0003\u000b\u000bY)!%\u0002\u0018\u0006u\u0005bBA\u0018\t\u0001\u0007\u0011\u0011G\u0001\u0004S:\u0004\u0004\u0003\u0002\u001b\u00024AK1!!\u000e-\u0005\u0015Ie\u000e\\3u\u0011\u001d\tI\u0004\u0002a\u0001\u0003w\t1!\u001b82!\u0011!\u00141G*\t\u000f\u0005}B\u00011\u0001\u0002B\u0005\u0019\u0011N\u001c\u001a\u0011\tQ\n\u0019D\u0016\u0005\b\u0003\u000b\"\u0001\u0019AA$\u0003\rIgn\r\t\u0005i\u0005M\u0012\fC\u0004\u0002L\u0011\u0001\r!!\u0014\u0002\u0007%tG\u0007\u0005\u00035\u0003ga\u0006bBA)\t\u0001\u0007\u00111K\u0001\u0004S:,\u0004\u0003\u0002\u001b\u00024}Cq!a\u0016\u0005\u0001\u0004\tI&A\u0002j]Z\u0002B\u0001NA\u001aE\"9\u0011Q\f\u0003A\u0002\u0005}\u0013aA5ooA!A'a\rf\u0011\u001d\t\u0019\u0007\u0002a\u0001\u0003K\n1!\u001b89!\u0011!\u00141\u00075\t\u000f\u0005%D\u00011\u0001\u0002l\u0005\u0019\u0011N\\\u001d\u0011\tQ\n\u0019d\u001b\u0005\b\u0003_\"\u0001\u0019AA9\u0003\u0011Ig.\r\u0019\u0011\tQ\n\u0019D\u001c\u0005\b\u0003k\"\u0001\u0019AA<\u0003\u0011Ig.M\u0019\u0011\tQ\n\u0019$\u001d\u0005\b\u0003w\"\u0001\u0019AA?\u0003\u0011Ig.\r\u001a\u0011\tQ\n\u0019\u0004\u001e\u0005\b\u0003\u0003#\u0001\u0019AAB\u0003\u0011Ig.M\u001a\u0011\tQ\n\u0019d\u001e\u0005\b\u0003\u000f#\u0001\u0019AAE\u0003\u0011Ig.\r\u001b\u0011\tQ\n\u0019D\u001f\u0005\b\u0003\u001b#\u0001\u0019AAH\u0003\u0011Ig.M\u001b\u0011\tQ\n\u0019$ \u0005\b\u0003'#\u0001\u0019AAK\u0003\u0011Ig.\r\u001c\u0011\u000bQ\n\u0019$!\u0001\t\u000f\u0005eE\u00011\u0001\u0002\u001c\u0006!\u0011N\\\u00198!\u0015!\u00141GA\u0004\u0011\u001d\ty\n\u0002a\u0001\u0003C\u000b1a\\;u!\u0011!\u00141U\u001c\n\u0007\u0005\u0015FF\u0001\u0004PkRdW\r^\u0001\nG>t7\u000f\u001e:vGR$2aMAV\u0011\u001d\ti+\u0002a\u0001\u0003_\u000bA!\u001b8jiB!qISAYU\r9\u00141W\u0016\u0003\u0003k\u0003B!a.\u0002B6\u0011\u0011\u0011\u0018\u0006\u0005\u0003w\u000bi,A\u0005v]\u000eDWmY6fI*\u0019\u0011q\u0018 \u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002D\u0006e&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006AA-Z3q\u0007>\u0004\u0018\u0010F\u0001P+\t\tY\rE\u00035\u0003g\tiMK\u0002Q\u0003g\u000bA!\u001b81AU\u0011\u00111\u001b\t\u0006i\u0005M\u0012Q\u001b\u0016\u0004'\u0006M\u0016\u0001B5oc\u0001*\"!a7\u0011\u000bQ\n\u0019$!8+\u0007Y\u000b\u0019,\u0001\u0003j]J\u0002SCAAr!\u0015!\u00141GAsU\rI\u00161W\u0001\u0005S:\u001c\u0004%\u0006\u0002\u0002lB)A'a\r\u0002n*\u001aA,a-\u0002\t%tG\u0007I\u000b\u0003\u0003g\u0004R\u0001NA\u001a\u0003kT3aXAZ\u0003\u0011Ig.\u000e\u0011\u0016\u0005\u0005m\b#\u0002\u001b\u00024\u0005u(f\u00012\u00024\u0006!\u0011N\u001c\u001c!+\t\u0011\u0019\u0001E\u00035\u0003g\u0011)AK\u0002f\u0003g\u000bA!\u001b88AU\u0011!1\u0002\t\u0006i\u0005M\"Q\u0002\u0016\u0004Q\u0006M\u0016\u0001B5oq\u0001*\"Aa\u0005\u0011\u000bQ\n\u0019D!\u0006+\u0007-\f\u0019,\u0001\u0003j]f\u0002SC\u0001B\u000e!\u0015!\u00141\u0007B\u000fU\rq\u00171W\u0001\u0006S:\f\u0004\u0007I\u000b\u0003\u0005G\u0001R\u0001NA\u001a\u0005KQ3!]AZ\u0003\u0015Ig.M\u0019!+\t\u0011Y\u0003E\u00035\u0003g\u0011iCK\u0002u\u0003g\u000bQ!\u001b82e\u0001*\"Aa\r\u0011\u000bQ\n\u0019D!\u000e+\u0007]\f\u0019,A\u0003j]F\u001a\u0004%\u0006\u0002\u0003<A)A'a\r\u0003>)\u001a!0a-\u0002\u000b%t\u0017\u0007\u000e\u0011\u0016\u0005\t\r\u0003#\u0002\u001b\u00024\t\u0015#fA?\u00024\u0006)\u0011N\\\u00196AU\u0011!1\n\t\u0006i\u0005M\"Q\n\u0016\u0005\u0003\u0003\t\u0019,A\u0003j]F2\u0004%\u0006\u0002\u0003TA)A'a\r\u0003V)\"\u0011qAAZ\u0003\u0015Ig.M\u001c!\u0001")
public class FanInShape18<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, O>
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
    private final Inlet<T9> in9;
    private final Inlet<T10> in10;
    private final Inlet<T11> in11;
    private final Inlet<T12> in12;
    private final Inlet<T13> in13;
    private final Inlet<T14> in14;
    private final Inlet<T15> in15;
    private final Inlet<T16> in16;
    private final Inlet<T17> in17;

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape18<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, O>(init2);
    }

    @Override
    public FanInShape18<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, O> deepCopy() {
        return (FanInShape18)super.deepCopy();
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

    public Inlet<T9> in9() {
        return this.in9;
    }

    public Inlet<T10> in10() {
        return this.in10;
    }

    public Inlet<T11> in11() {
        return this.in11;
    }

    public Inlet<T12> in12() {
        return this.in12;
    }

    public Inlet<T13> in13() {
        return this.in13;
    }

    public Inlet<T14> in14() {
        return this.in14;
    }

    public Inlet<T15> in15() {
        return this.in15;
    }

    public Inlet<T16> in16() {
        return this.in16;
    }

    public Inlet<T17> in17() {
        return this.in17;
    }

    public FanInShape18(FanInShape.Init<O> _init) {
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
        this.in9 = this.newInlet("in9");
        this.in10 = this.newInlet("in10");
        this.in11 = this.newInlet("in11");
        this.in12 = this.newInlet("in12");
        this.in13 = this.newInlet("in13");
        this.in14 = this.newInlet("in14");
        this.in15 = this.newInlet("in15");
        this.in16 = this.newInlet("in16");
        this.in17 = this.newInlet("in17");
    }

    public FanInShape18(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape18(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Inlet<T8> in8, Inlet<T9> in9, Inlet<T10> in10, Inlet<T11> in11, Inlet<T12> in12, Inlet<T13> in13, Inlet<T14> in14, Inlet<T15> in15, Inlet<T16> in16, Inlet<T17> in17, Outlet<O> out) {
        Inlet<T0> inlet = in0;
        Inlet<T1> inlet2 = in1;
        Inlet<T2> inlet3 = in2;
        Inlet<T3> inlet4 = in3;
        Inlet<T4> inlet5 = in4;
        Inlet<T5> inlet6 = in5;
        Inlet<T6> inlet7 = in6;
        Inlet<T7> inlet8 = in7;
        Inlet<T8> inlet9 = in8;
        Inlet<T9> inlet10 = in9;
        Inlet<T10> inlet11 = in10;
        Inlet<T11> inlet12 = in11;
        Inlet<T12> inlet13 = in12;
        Inlet<T13> inlet14 = in13;
        Inlet<T14> inlet15 = in14;
        Inlet<T15> inlet16 = in15;
        Inlet<T16> inlet17 = in16;
        Inlet<T17> inlet18 = in17;
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet18).$colon$colon(inlet17).$colon$colon(inlet16).$colon$colon(inlet15).$colon$colon(inlet14).$colon$colon(inlet13).$colon$colon(inlet12).$colon$colon(inlet11).$colon$colon(inlet10).$colon$colon(inlet9).$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

