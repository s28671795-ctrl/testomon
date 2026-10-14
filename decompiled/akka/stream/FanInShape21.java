/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t\u0005f\u0001B\u00193\u0001]B\u0001b\u0013\u0001\u0003\u0002\u0003\u0006I\u0001\u0014\u0005\u0006'\u0002!\t\u0001\u0016\u0005\u0007'\u0002!\t!!\f\t\rM\u0003A\u0011AA%\u0011\u001d\t9\u000e\u0001C)\u00033Dq!!>\u0001\t\u0003\n9\u0010C\u0005\u0002N\u0001\u0011\r\u0011\"\u0001\u0002z\"A\u0011q \u0001!\u0002\u0013\tY\u0010C\u0005\u0002X\u0001\u0011\r\u0011\"\u0001\u0003\u0002!A!q\u0001\u0001!\u0002\u0013\u0011\u0019\u0001C\u0005\u0002^\u0001\u0011\r\u0011\"\u0001\u0003\n!A!q\u0002\u0001!\u0002\u0013\u0011Y\u0001C\u0005\u0002d\u0001\u0011\r\u0011\"\u0001\u0003\u0012!A!q\u0003\u0001!\u0002\u0013\u0011\u0019\u0002C\u0005\u0002j\u0001\u0011\r\u0011\"\u0001\u0003\u001a!A!q\u0004\u0001!\u0002\u0013\u0011Y\u0002C\u0005\u0002p\u0001\u0011\r\u0011\"\u0001\u0003\"!A!q\u0005\u0001!\u0002\u0013\u0011\u0019\u0003C\u0005\u0002v\u0001\u0011\r\u0011\"\u0001\u0003*!A!q\u0006\u0001!\u0002\u0013\u0011Y\u0003C\u0005\u0002|\u0001\u0011\r\u0011\"\u0001\u00032!A!q\u0007\u0001!\u0002\u0013\u0011\u0019\u0004C\u0005\u0002\u0002\u0002\u0011\r\u0011\"\u0001\u0003:!A!q\b\u0001!\u0002\u0013\u0011Y\u0004C\u0005\u0002\b\u0002\u0011\r\u0011\"\u0001\u0003B!A!q\t\u0001!\u0002\u0013\u0011\u0019\u0005C\u0005\u0002\u000e\u0002\u0011\r\u0011\"\u0001\u0003J!A!q\n\u0001!\u0002\u0013\u0011Y\u0005C\u0005\u0002\u0014\u0002\u0011\r\u0011\"\u0001\u0003R!A!q\u000b\u0001!\u0002\u0013\u0011\u0019\u0006C\u0005\u0002\u001a\u0002\u0011\r\u0011\"\u0001\u0003Z!A!q\f\u0001!\u0002\u0013\u0011Y\u0006C\u0005\u0002 \u0002\u0011\r\u0011\"\u0001\u0003b!A!q\r\u0001!\u0002\u0013\u0011\u0019\u0007C\u0005\u0002&\u0002\u0011\r\u0011\"\u0001\u0003j!A!q\u000e\u0001!\u0002\u0013\u0011Y\u0007C\u0005\u0002,\u0002\u0011\r\u0011\"\u0001\u0003r!A!q\u000f\u0001!\u0002\u0013\u0011\u0019\bC\u0005\u00022\u0002\u0011\r\u0011\"\u0001\u0003z!A!q\u0010\u0001!\u0002\u0013\u0011Y\bC\u0005\u00028\u0002\u0011\r\u0011\"\u0001\u0003\u0002\"A!q\u0011\u0001!\u0002\u0013\u0011\u0019\tC\u0005\u0002>\u0002\u0011\r\u0011\"\u0001\u0003\n\"A!q\u0012\u0001!\u0002\u0013\u0011Y\tC\u0005\u0002D\u0002\u0011\r\u0011\"\u0001\u0003\u0012\"A!q\u0013\u0001!\u0002\u0013\u0011\u0019\nC\u0005\u0002J\u0002\u0011\r\u0011\"\u0001\u0003\u001a\"A!q\u0014\u0001!\u0002\u0013\u0011YJ\u0001\u0007GC:Len\u00155ba\u0016\u0014\u0014G\u0003\u00024i\u000511\u000f\u001e:fC6T\u0011!N\u0001\u0005C.\\\u0017m\u0001\u0001\u0016=a:&,\u00181dM&dwN];ywz\f\u0019!!\u0003\u0002\u0010\u0005U\u00111DA\u0011\u0003Oy4C\u0001\u0001:!\rQ4(P\u0007\u0002e%\u0011AH\r\u0002\u000b\r\u0006t\u0017J\\*iCB,\u0007C\u0001 @\u0019\u0001!a\u0001\u0011\u0001\u0005\u0006\u0004\t%!A(\u0012\u0005\tC\u0005CA\"G\u001b\u0005!%\"A#\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u001d#%a\u0002(pi\"Lgn\u001a\t\u0003\u0007&K!A\u0013#\u0003\u0007\u0005s\u00170A\u0003`S:LG\u000fE\u0002N!vr!A\u000f(\n\u0005=\u0013\u0014A\u0003$b]&s7\u000b[1qK&\u0011\u0011K\u0015\u0002\u0005\u0013:LGO\u0003\u0002Pe\u00051A(\u001b8jiz\"2!VA\u0016!}Q\u0004AV-]?\n,\u0007n\u001b8ri^TX0!\u0001\u0002\b\u00055\u00111CA\r\u0003?\t)#\u0010\t\u0003}]#a\u0001\u0017\u0001\t\u0006\u0004\t%A\u0001+1!\tq$\f\u0002\u0004\\\u0001!\u0015\r!\u0011\u0002\u0003)F\u0002\"AP/\u0005\ry\u0003\u0001R1\u0001B\u0005\t!&\u0007\u0005\u0002?A\u00121\u0011\r\u0001EC\u0002\u0005\u0013!\u0001V\u001a\u0011\u0005y\u001aGA\u00023\u0001\u0011\u000b\u0007\u0011I\u0001\u0002UiA\u0011aH\u001a\u0003\u0007O\u0002A)\u0019A!\u0003\u0005Q+\u0004C\u0001 j\t\u0019Q\u0007\u0001#b\u0001\u0003\n\u0011AK\u000e\t\u0003}1$a!\u001c\u0001\t\u0006\u0004\t%A\u0001+8!\tqt\u000e\u0002\u0004q\u0001!\u0015\r!\u0011\u0002\u0003)b\u0002\"A\u0010:\u0005\rM\u0004\u0001R1\u0001B\u0005\t!\u0016\b\u0005\u0002?k\u00121a\u000f\u0001EC\u0002\u0005\u00131\u0001V\u00191!\tq\u0004\u0010\u0002\u0004z\u0001!\u0015\r!\u0011\u0002\u0004)F\n\u0004C\u0001 |\t\u0019a\b\u0001#b\u0001\u0003\n\u0019A+\r\u001a\u0011\u0005yrHAB@\u0001\u0011\u000b\u0007\u0011IA\u0002UcM\u00022APA\u0002\t\u001d\t)\u0001\u0001EC\u0002\u0005\u00131\u0001V\u00195!\rq\u0014\u0011\u0002\u0003\b\u0003\u0017\u0001\u0001R1\u0001B\u0005\r!\u0016'\u000e\t\u0004}\u0005=AaBA\t\u0001!\u0015\r!\u0011\u0002\u0004)F2\u0004c\u0001 \u0002\u0016\u00119\u0011q\u0003\u0001\t\u0006\u0004\t%a\u0001+2oA\u0019a(a\u0007\u0005\u000f\u0005u\u0001\u0001#b\u0001\u0003\n\u0019A+\r\u001d\u0011\u0007y\n\t\u0003B\u0004\u0002$\u0001A)\u0019A!\u0003\u0007Q\u000b\u0014\bE\u0002?\u0003O!q!!\u000b\u0001\u0011\u000b\u0007\u0011IA\u0002UeABQa\u0013\u0002A\u00021#2!VA\u0018\u0011\u001d\t\td\u0001a\u0001\u0003g\tAA\\1nKB!\u0011QGA\"\u001d\u0011\t9$a\u0010\u0011\u0007\u0005eB)\u0004\u0002\u0002<)\u0019\u0011Q\b\u001c\u0002\rq\u0012xn\u001c;?\u0013\r\t\t\u0005R\u0001\u0007!J,G-\u001a4\n\t\u0005\u0015\u0013q\t\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005\u0005C\tF\u0017V\u0003\u0017\n)&a\u0017\u0002b\u0005\u001d\u0014QNA:\u0003s\ny(!\"\u0002\f\u0006E\u0015qSAO\u0003G\u000bI+a,\u00026\u0006m\u0016\u0011YAd\u0003\u001bDq!!\u0014\u0005\u0001\u0004\ty%A\u0002j]B\u0002BAOA)-&\u0019\u00111\u000b\u001a\u0003\u000b%sG.\u001a;\t\u000f\u0005]C\u00011\u0001\u0002Z\u0005\u0019\u0011N\\\u0019\u0011\ti\n\t&\u0017\u0005\b\u0003;\"\u0001\u0019AA0\u0003\rIgN\r\t\u0005u\u0005EC\fC\u0004\u0002d\u0011\u0001\r!!\u001a\u0002\u0007%t7\u0007\u0005\u0003;\u0003#z\u0006bBA5\t\u0001\u0007\u00111N\u0001\u0004S:$\u0004\u0003\u0002\u001e\u0002R\tDq!a\u001c\u0005\u0001\u0004\t\t(A\u0002j]V\u0002BAOA)K\"9\u0011Q\u000f\u0003A\u0002\u0005]\u0014aA5omA!!(!\u0015i\u0011\u001d\tY\b\u0002a\u0001\u0003{\n1!\u001b88!\u0011Q\u0014\u0011K6\t\u000f\u0005\u0005E\u00011\u0001\u0002\u0004\u0006\u0019\u0011N\u001c\u001d\u0011\ti\n\tF\u001c\u0005\b\u0003\u000f#\u0001\u0019AAE\u0003\rIg.\u000f\t\u0005u\u0005E\u0013\u000fC\u0004\u0002\u000e\u0012\u0001\r!a$\u0002\t%t\u0017\u0007\r\t\u0005u\u0005EC\u000fC\u0004\u0002\u0014\u0012\u0001\r!!&\u0002\t%t\u0017'\r\t\u0005u\u0005Es\u000fC\u0004\u0002\u001a\u0012\u0001\r!a'\u0002\t%t\u0017G\r\t\u0005u\u0005E#\u0010C\u0004\u0002 \u0012\u0001\r!!)\u0002\t%t\u0017g\r\t\u0005u\u0005ES\u0010C\u0004\u0002&\u0012\u0001\r!a*\u0002\t%t\u0017\u0007\u000e\t\u0006u\u0005E\u0013\u0011\u0001\u0005\b\u0003W#\u0001\u0019AAW\u0003\u0011Ig.M\u001b\u0011\u000bi\n\t&a\u0002\t\u000f\u0005EF\u00011\u0001\u00024\u0006!\u0011N\\\u00197!\u0015Q\u0014\u0011KA\u0007\u0011\u001d\t9\f\u0002a\u0001\u0003s\u000bA!\u001b82oA)!(!\u0015\u0002\u0014!9\u0011Q\u0018\u0003A\u0002\u0005}\u0016\u0001B5oca\u0002RAOA)\u00033Aq!a1\u0005\u0001\u0004\t)-\u0001\u0003j]FJ\u0004#\u0002\u001e\u0002R\u0005}\u0001bBAe\t\u0001\u0007\u00111Z\u0001\u0005S:\u0014\u0004\u0007E\u0003;\u0003#\n)\u0003C\u0004\u0002P\u0012\u0001\r!!5\u0002\u0007=,H\u000f\u0005\u0003;\u0003'l\u0014bAAke\t1q*\u001e;mKR\f\u0011bY8ogR\u0014Xo\u0019;\u0015\u0007e\nY\u000eC\u0004\u0002^\u0016\u0001\r!a8\u0002\t%t\u0017\u000e\u001e\t\u0005\u001bB\u000b\tOK\u0002>\u0003G\\#!!:\u0011\t\u0005\u001d\u0018\u0011_\u0007\u0003\u0003STA!a;\u0002n\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003_$\u0015AC1o]>$\u0018\r^5p]&!\u00111_Au\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\tI\u0016,\u0007oQ8qsR\tQ+\u0006\u0002\u0002|B)!(!\u0015\u0002~*\u001aa+a9\u0002\t%t\u0007\u0007I\u000b\u0003\u0005\u0007\u0001RAOA)\u0005\u000bQ3!WAr\u0003\u0011Ig.\r\u0011\u0016\u0005\t-\u0001#\u0002\u001e\u0002R\t5!f\u0001/\u0002d\u0006!\u0011N\u001c\u001a!+\t\u0011\u0019\u0002E\u0003;\u0003#\u0012)BK\u0002`\u0003G\fA!\u001b84AU\u0011!1\u0004\t\u0006u\u0005E#Q\u0004\u0016\u0004E\u0006\r\u0018\u0001B5oi\u0001*\"Aa\t\u0011\u000bi\n\tF!\n+\u0007\u0015\f\u0019/\u0001\u0003j]V\u0002SC\u0001B\u0016!\u0015Q\u0014\u0011\u000bB\u0017U\rA\u00171]\u0001\u0005S:4\u0004%\u0006\u0002\u00034A)!(!\u0015\u00036)\u001a1.a9\u0002\t%tw\u0007I\u000b\u0003\u0005w\u0001RAOA)\u0005{Q3A\\Ar\u0003\u0011Ig\u000e\u000f\u0011\u0016\u0005\t\r\u0003#\u0002\u001e\u0002R\t\u0015#fA9\u0002d\u0006!\u0011N\\\u001d!+\t\u0011Y\u0005E\u0003;\u0003#\u0012iEK\u0002u\u0003G\fQ!\u001b82a\u0001*\"Aa\u0015\u0011\u000bi\n\tF!\u0016+\u0007]\f\u0019/A\u0003j]F\n\u0004%\u0006\u0002\u0003\\A)!(!\u0015\u0003^)\u001a!0a9\u0002\u000b%t\u0017G\r\u0011\u0016\u0005\t\r\u0004#\u0002\u001e\u0002R\t\u0015$fA?\u0002d\u0006)\u0011N\\\u00194AU\u0011!1\u000e\t\u0006u\u0005E#Q\u000e\u0016\u0005\u0003\u0003\t\u0019/A\u0003j]F\"\u0004%\u0006\u0002\u0003tA)!(!\u0015\u0003v)\"\u0011qAAr\u0003\u0015Ig.M\u001b!+\t\u0011Y\bE\u0003;\u0003#\u0012iH\u000b\u0003\u0002\u000e\u0005\r\u0018!B5ocY\u0002SC\u0001BB!\u0015Q\u0014\u0011\u000bBCU\u0011\t\u0019\"a9\u0002\u000b%t\u0017g\u000e\u0011\u0016\u0005\t-\u0005#\u0002\u001e\u0002R\t5%\u0006BA\r\u0003G\fQ!\u001b82q\u0001*\"Aa%\u0011\u000bi\n\tF!&+\t\u0005}\u00111]\u0001\u0006S:\f\u0014\bI\u000b\u0003\u00057\u0003RAOA)\u0005;SC!!\n\u0002d\u0006)\u0011N\u001c\u001a1A\u0001")
public class FanInShape21<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, O>
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
    private final Inlet<T18> in18;
    private final Inlet<T19> in19;
    private final Inlet<T20> in20;

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape21<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, O>(init2);
    }

    @Override
    public FanInShape21<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, O> deepCopy() {
        return (FanInShape21)super.deepCopy();
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

    public Inlet<T18> in18() {
        return this.in18;
    }

    public Inlet<T19> in19() {
        return this.in19;
    }

    public Inlet<T20> in20() {
        return this.in20;
    }

    public FanInShape21(FanInShape.Init<O> _init) {
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
        this.in18 = this.newInlet("in18");
        this.in19 = this.newInlet("in19");
        this.in20 = this.newInlet("in20");
    }

    public FanInShape21(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape21(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Inlet<T8> in8, Inlet<T9> in9, Inlet<T10> in10, Inlet<T11> in11, Inlet<T12> in12, Inlet<T13> in13, Inlet<T14> in14, Inlet<T15> in15, Inlet<T16> in16, Inlet<T17> in17, Inlet<T18> in18, Inlet<T19> in19, Inlet<T20> in20, Outlet<O> out) {
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
        Inlet<T18> inlet19 = in18;
        Inlet<T19> inlet20 = in19;
        Inlet<T20> inlet21 = in20;
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet21).$colon$colon(inlet20).$colon$colon(inlet19).$colon$colon(inlet18).$colon$colon(inlet17).$colon$colon(inlet16).$colon$colon(inlet15).$colon$colon(inlet14).$colon$colon(inlet13).$colon$colon(inlet12).$colon$colon(inlet11).$colon$colon(inlet10).$colon$colon(inlet9).$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

