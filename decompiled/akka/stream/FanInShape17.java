/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t\u0005c\u0001B\u0015+\u0001=B\u0001b\u0011\u0001\u0003\u0002\u0003\u0006I\u0001\u0012\u0005\u0006\u0017\u0002!\t\u0001\u0014\u0005\u0007\u0017\u0002!\t!!\u0002\t\r-\u0003A\u0011AA\u0011\u0011\u001d\t9\n\u0001C)\u00033Cq!!.\u0001\t\u0003\n9\fC\u0005\u0002&\u0001\u0011\r\u0011\"\u0001\u0002:\"A\u0011q\u0018\u0001!\u0002\u0013\tY\fC\u0005\u00020\u0001\u0011\r\u0011\"\u0001\u0002B\"A\u0011q\u0019\u0001!\u0002\u0013\t\u0019\rC\u0005\u00026\u0001\u0011\r\u0011\"\u0001\u0002J\"A\u0011q\u001a\u0001!\u0002\u0013\tY\rC\u0005\u0002<\u0001\u0011\r\u0011\"\u0001\u0002R\"A\u0011q\u001b\u0001!\u0002\u0013\t\u0019\u000eC\u0005\u0002B\u0001\u0011\r\u0011\"\u0001\u0002Z\"A\u0011q\u001c\u0001!\u0002\u0013\tY\u000eC\u0005\u0002H\u0001\u0011\r\u0011\"\u0001\u0002b\"A\u0011q\u001d\u0001!\u0002\u0013\t\u0019\u000fC\u0005\u0002N\u0001\u0011\r\u0011\"\u0001\u0002j\"A\u0011q\u001e\u0001!\u0002\u0013\tY\u000fC\u0005\u0002T\u0001\u0011\r\u0011\"\u0001\u0002r\"A\u0011q\u001f\u0001!\u0002\u0013\t\u0019\u0010C\u0005\u0002Z\u0001\u0011\r\u0011\"\u0001\u0002z\"A\u0011q \u0001!\u0002\u0013\tY\u0010C\u0005\u0002`\u0001\u0011\r\u0011\"\u0001\u0003\u0002!A!q\u0001\u0001!\u0002\u0013\u0011\u0019\u0001C\u0005\u0002f\u0001\u0011\r\u0011\"\u0001\u0003\n!A!q\u0002\u0001!\u0002\u0013\u0011Y\u0001C\u0005\u0002l\u0001\u0011\r\u0011\"\u0001\u0003\u0012!A!q\u0003\u0001!\u0002\u0013\u0011\u0019\u0002C\u0005\u0002r\u0001\u0011\r\u0011\"\u0001\u0003\u001a!A!q\u0004\u0001!\u0002\u0013\u0011Y\u0002C\u0005\u0002x\u0001\u0011\r\u0011\"\u0001\u0003\"!A!q\u0005\u0001!\u0002\u0013\u0011\u0019\u0003C\u0005\u0002~\u0001\u0011\r\u0011\"\u0001\u0003*!A!q\u0006\u0001!\u0002\u0013\u0011Y\u0003C\u0005\u0002\u0004\u0002\u0011\r\u0011\"\u0001\u00032!A!q\u0007\u0001!\u0002\u0013\u0011\u0019\u0004C\u0005\u0002\n\u0002\u0011\r\u0011\"\u0001\u0003:!A!q\b\u0001!\u0002\u0013\u0011YD\u0001\u0007GC:Len\u00155ba\u0016\ftG\u0003\u0002,Y\u000511\u000f\u001e:fC6T\u0011!L\u0001\u0005C.\\\u0017m\u0001\u0001\u0016'Az%+\u0016-\\=\u0006$wM[7qgZLHp`\u001c\u0014\u0005\u0001\t\u0004c\u0001\u001a4k5\t!&\u0003\u00025U\tQa)\u00198J]NC\u0017\r]3\u0011\u0005Y:D\u0002\u0001\u0003\u0007q\u0001!)\u0019A\u001d\u0003\u0003=\u000b\"A\u000f!\u0011\u0005mrT\"\u0001\u001f\u000b\u0003u\nQa]2bY\u0006L!a\u0010\u001f\u0003\u000f9{G\u000f[5oOB\u00111(Q\u0005\u0003\u0005r\u00121!\u00118z\u0003\u0015y\u0016N\\5u!\r)\u0005*\u000e\b\u0003e\u0019K!a\u0012\u0016\u0002\u0015\u0019\u000bg.\u00138TQ\u0006\u0004X-\u0003\u0002J\u0015\n!\u0011J\\5u\u0015\t9%&\u0001\u0004=S:LGO\u0010\u000b\u0004\u001b\u0006\r\u0001\u0003\u0006\u001a\u0001\u001dF#vKW/aG\u001aLGn\u001c:vqntX\u0007\u0005\u00027\u001f\u00121\u0001\u000b\u0001EC\u0002e\u0012!\u0001\u0016\u0019\u0011\u0005Y\u0012FAB*\u0001\u0011\u000b\u0007\u0011H\u0001\u0002UcA\u0011a'\u0016\u0003\u0007-\u0002A)\u0019A\u001d\u0003\u0005Q\u0013\u0004C\u0001\u001cY\t\u0019I\u0006\u0001#b\u0001s\t\u0011Ak\r\t\u0003mm#a\u0001\u0018\u0001\t\u0006\u0004I$A\u0001+5!\t1d\f\u0002\u0004`\u0001!\u0015\r!\u000f\u0002\u0003)V\u0002\"AN1\u0005\r\t\u0004\u0001R1\u0001:\u0005\t!f\u0007\u0005\u00027I\u00121Q\r\u0001EC\u0002e\u0012!\u0001V\u001c\u0011\u0005Y:GA\u00025\u0001\u0011\u000b\u0007\u0011H\u0001\u0002UqA\u0011aG\u001b\u0003\u0007W\u0002A)\u0019A\u001d\u0003\u0005QK\u0004C\u0001\u001cn\t\u0019q\u0007\u0001#b\u0001s\t\u0019A+\r\u0019\u0011\u0005Y\u0002HAB9\u0001\u0011\u000b\u0007\u0011HA\u0002UcE\u0002\"AN:\u0005\rQ\u0004\u0001R1\u0001:\u0005\r!\u0016G\r\t\u0003mY$aa\u001e\u0001\t\u0006\u0004I$a\u0001+2gA\u0011a'\u001f\u0003\u0007u\u0002A)\u0019A\u001d\u0003\u0007Q\u000bD\u0007\u0005\u00027y\u00121Q\u0010\u0001EC\u0002e\u00121\u0001V\u00196!\t1t\u0010B\u0004\u0002\u0002\u0001A)\u0019A\u001d\u0003\u0007Q\u000bd\u0007C\u0003D\u0005\u0001\u0007A\tF\u0002N\u0003\u000fAq!!\u0003\u0004\u0001\u0004\tY!\u0001\u0003oC6,\u0007\u0003BA\u0007\u00037qA!a\u0004\u0002\u0018A\u0019\u0011\u0011\u0003\u001f\u000e\u0005\u0005M!bAA\u000b]\u00051AH]8pizJ1!!\u0007=\u0003\u0019\u0001&/\u001a3fM&!\u0011QDA\u0010\u0005\u0019\u0019FO]5oO*\u0019\u0011\u0011\u0004\u001f\u0015K5\u000b\u0019#!\f\u00024\u0005e\u0012qHA#\u0003\u0017\n\t&a\u0016\u0002^\u0005\r\u0014\u0011NA8\u0003k\nY(!!\u0002\b\u00065\u0005bBA\u0013\t\u0001\u0007\u0011qE\u0001\u0004S:\u0004\u0004\u0003\u0002\u001a\u0002*9K1!a\u000b+\u0005\u0015Ie\u000e\\3u\u0011\u001d\ty\u0003\u0002a\u0001\u0003c\t1!\u001b82!\u0011\u0011\u0014\u0011F)\t\u000f\u0005UB\u00011\u0001\u00028\u0005\u0019\u0011N\u001c\u001a\u0011\tI\nI\u0003\u0016\u0005\b\u0003w!\u0001\u0019AA\u001f\u0003\rIgn\r\t\u0005e\u0005%r\u000bC\u0004\u0002B\u0011\u0001\r!a\u0011\u0002\u0007%tG\u0007\u0005\u00033\u0003SQ\u0006bBA$\t\u0001\u0007\u0011\u0011J\u0001\u0004S:,\u0004\u0003\u0002\u001a\u0002*uCq!!\u0014\u0005\u0001\u0004\ty%A\u0002j]Z\u0002BAMA\u0015A\"9\u00111\u000b\u0003A\u0002\u0005U\u0013aA5ooA!!'!\u000bd\u0011\u001d\tI\u0006\u0002a\u0001\u00037\n1!\u001b89!\u0011\u0011\u0014\u0011\u00064\t\u000f\u0005}C\u00011\u0001\u0002b\u0005\u0019\u0011N\\\u001d\u0011\tI\nI#\u001b\u0005\b\u0003K\"\u0001\u0019AA4\u0003\u0011Ig.\r\u0019\u0011\tI\nI\u0003\u001c\u0005\b\u0003W\"\u0001\u0019AA7\u0003\u0011Ig.M\u0019\u0011\tI\nIc\u001c\u0005\b\u0003c\"\u0001\u0019AA:\u0003\u0011Ig.\r\u001a\u0011\tI\nIC\u001d\u0005\b\u0003o\"\u0001\u0019AA=\u0003\u0011Ig.M\u001a\u0011\tI\nI#\u001e\u0005\b\u0003{\"\u0001\u0019AA@\u0003\u0011Ig.\r\u001b\u0011\tI\nI\u0003\u001f\u0005\b\u0003\u0007#\u0001\u0019AAC\u0003\u0011Ig.M\u001b\u0011\tI\nIc\u001f\u0005\b\u0003\u0013#\u0001\u0019AAF\u0003\u0011Ig.\r\u001c\u0011\tI\nIC \u0005\b\u0003\u001f#\u0001\u0019AAI\u0003\ryW\u000f\u001e\t\u0005e\u0005MU'C\u0002\u0002\u0016*\u0012aaT;uY\u0016$\u0018!C2p]N$(/^2u)\r\t\u00141\u0014\u0005\b\u0003;+\u0001\u0019AAP\u0003\u0011Ig.\u001b;\u0011\t\u0015C\u0015\u0011\u0015\u0016\u0004k\u0005\r6FAAS!\u0011\t9+!-\u000e\u0005\u0005%&\u0002BAV\u0003[\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005=F(\u0001\u0006b]:|G/\u0019;j_:LA!a-\u0002*\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u0011\u0011,W\r]\"paf$\u0012!T\u000b\u0003\u0003w\u0003RAMA\u0015\u0003{S3ATAR\u0003\u0011Ig\u000e\r\u0011\u0016\u0005\u0005\r\u0007#\u0002\u001a\u0002*\u0005\u0015'fA)\u0002$\u0006!\u0011N\\\u0019!+\t\tY\rE\u00033\u0003S\tiMK\u0002U\u0003G\u000bA!\u001b83AU\u0011\u00111\u001b\t\u0006e\u0005%\u0012Q\u001b\u0016\u0004/\u0006\r\u0016\u0001B5og\u0001*\"!a7\u0011\u000bI\nI#!8+\u0007i\u000b\u0019+\u0001\u0003j]R\u0002SCAAr!\u0015\u0011\u0014\u0011FAsU\ri\u00161U\u0001\u0005S:,\u0004%\u0006\u0002\u0002lB)!'!\u000b\u0002n*\u001a\u0001-a)\u0002\t%tg\u0007I\u000b\u0003\u0003g\u0004RAMA\u0015\u0003kT3aYAR\u0003\u0011Ign\u000e\u0011\u0016\u0005\u0005m\b#\u0002\u001a\u0002*\u0005u(f\u00014\u0002$\u0006!\u0011N\u001c\u001d!+\t\u0011\u0019\u0001E\u00033\u0003S\u0011)AK\u0002j\u0003G\u000bA!\u001b8:AU\u0011!1\u0002\t\u0006e\u0005%\"Q\u0002\u0016\u0004Y\u0006\r\u0016!B5ocA\u0002SC\u0001B\n!\u0015\u0011\u0014\u0011\u0006B\u000bU\ry\u00171U\u0001\u0006S:\f\u0014\u0007I\u000b\u0003\u00057\u0001RAMA\u0015\u0005;Q3A]AR\u0003\u0015Ig.\r\u001a!+\t\u0011\u0019\u0003E\u00033\u0003S\u0011)CK\u0002v\u0003G\u000bQ!\u001b82g\u0001*\"Aa\u000b\u0011\u000bI\nIC!\f+\u0007a\f\u0019+A\u0003j]F\"\u0004%\u0006\u0002\u00034A)!'!\u000b\u00036)\u001a10a)\u0002\u000b%t\u0017'\u000e\u0011\u0016\u0005\tm\u0002#\u0002\u001a\u0002*\tu\"f\u0001@\u0002$\u0006)\u0011N\\\u00197A\u0001")
public class FanInShape17<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, O>
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

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape17<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, O>(init2);
    }

    @Override
    public FanInShape17<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, O> deepCopy() {
        return (FanInShape17)super.deepCopy();
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

    public FanInShape17(FanInShape.Init<O> _init) {
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
    }

    public FanInShape17(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape17(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Inlet<T8> in8, Inlet<T9> in9, Inlet<T10> in10, Inlet<T11> in11, Inlet<T12> in12, Inlet<T13> in13, Inlet<T14> in14, Inlet<T15> in15, Inlet<T16> in16, Outlet<O> out) {
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
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet17).$colon$colon(inlet16).$colon$colon(inlet15).$colon$colon(inlet14).$colon$colon(inlet13).$colon$colon(inlet12).$colon$colon(inlet11).$colon$colon(inlet10).$colon$colon(inlet9).$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

