/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tEd\u0001B\u0017/\u0001MB\u0001b\u0012\u0001\u0003\u0002\u0003\u0006I\u0001\u0013\u0005\u0006\u001f\u0002!\t\u0001\u0015\u0005\u0007\u001f\u0002!\t!!\u0007\t\r=\u0003A\u0011AA\u001b\u0011\u001d\t9\f\u0001C)\u0003sCq!!6\u0001\t\u0003\n9\u000eC\u0005\u0002:\u0001\u0011\r\u0011\"\u0001\u0002Z\"A\u0011q\u001c\u0001!\u0002\u0013\tY\u000eC\u0005\u0002D\u0001\u0011\r\u0011\"\u0001\u0002b\"A\u0011q\u001d\u0001!\u0002\u0013\t\u0019\u000fC\u0005\u0002J\u0001\u0011\r\u0011\"\u0001\u0002j\"A\u0011q\u001e\u0001!\u0002\u0013\tY\u000fC\u0005\u0002P\u0001\u0011\r\u0011\"\u0001\u0002r\"A\u0011q\u001f\u0001!\u0002\u0013\t\u0019\u0010C\u0005\u0002V\u0001\u0011\r\u0011\"\u0001\u0002z\"A\u0011q \u0001!\u0002\u0013\tY\u0010C\u0005\u0002\\\u0001\u0011\r\u0011\"\u0001\u0003\u0002!A!q\u0001\u0001!\u0002\u0013\u0011\u0019\u0001C\u0005\u0002b\u0001\u0011\r\u0011\"\u0001\u0003\n!A!q\u0002\u0001!\u0002\u0013\u0011Y\u0001C\u0005\u0002h\u0001\u0011\r\u0011\"\u0001\u0003\u0012!A!q\u0003\u0001!\u0002\u0013\u0011\u0019\u0002C\u0005\u0002n\u0001\u0011\r\u0011\"\u0001\u0003\u001a!A!q\u0004\u0001!\u0002\u0013\u0011Y\u0002C\u0005\u0002t\u0001\u0011\r\u0011\"\u0001\u0003\"!A!q\u0005\u0001!\u0002\u0013\u0011\u0019\u0003C\u0005\u0002z\u0001\u0011\r\u0011\"\u0001\u0003*!A!q\u0006\u0001!\u0002\u0013\u0011Y\u0003C\u0005\u0002\u0000\u0001\u0011\r\u0011\"\u0001\u00032!A!q\u0007\u0001!\u0002\u0013\u0011\u0019\u0004C\u0005\u0002\u0006\u0002\u0011\r\u0011\"\u0001\u0003:!A!q\b\u0001!\u0002\u0013\u0011Y\u0004C\u0005\u0002\f\u0002\u0011\r\u0011\"\u0001\u0003B!A!q\t\u0001!\u0002\u0013\u0011\u0019\u0005C\u0005\u0002\u0012\u0002\u0011\r\u0011\"\u0001\u0003J!A!q\n\u0001!\u0002\u0013\u0011Y\u0005C\u0005\u0002\u0018\u0002\u0011\r\u0011\"\u0001\u0003R!A!q\u000b\u0001!\u0002\u0013\u0011\u0019\u0006C\u0005\u0002\u001e\u0002\u0011\r\u0011\"\u0001\u0003Z!A!q\f\u0001!\u0002\u0013\u0011Y\u0006C\u0005\u0002$\u0002\u0011\r\u0011\"\u0001\u0003b!A!q\r\u0001!\u0002\u0013\u0011\u0019\u0007C\u0005\u0002*\u0002\u0011\r\u0011\"\u0001\u0003j!A!q\u000e\u0001!\u0002\u0013\u0011YG\u0001\u0007GC:Len\u00155ba\u0016\f\u0014H\u0003\u00020a\u000511\u000f\u001e:fC6T\u0011!M\u0001\u0005C.\\\u0017m\u0001\u0001\u00163Q\u001af+\u0017/`E\u0016D7N\\9uojl\u0018\u0011AA\u0004\u0003\u001b\t\u0019bO\n\u0003\u0001U\u00022AN\u001c:\u001b\u0005q\u0013B\u0001\u001d/\u0005)1\u0015M\\%o'\"\f\u0007/\u001a\t\u0003umb\u0001\u0001\u0002\u0004=\u0001\u0011\u0015\r!\u0010\u0002\u0002\u001fF\u0011a\b\u0012\t\u0003\u007f\tk\u0011\u0001\u0011\u0006\u0002\u0003\u0006)1oY1mC&\u00111\t\u0011\u0002\b\u001d>$\b.\u001b8h!\tyT)\u0003\u0002G\u0001\n\u0019\u0011I\\=\u0002\u000b}Kg.\u001b;\u0011\u0007%c\u0015H\u0004\u00027\u0015&\u00111JL\u0001\u000b\r\u0006t\u0017J\\*iCB,\u0017BA'O\u0005\u0011Ie.\u001b;\u000b\u0005-s\u0013A\u0002\u001fj]&$h\bF\u0002R\u0003/\u0001\u0012D\u000e\u0001S+b[f,\u00193hU6\u00048O^=}\u007f\u0006\u0015\u00111BA\tsA\u0011!h\u0015\u0003\u0007)\u0002A)\u0019A\u001f\u0003\u0005Q\u0003\u0004C\u0001\u001eW\t\u00199\u0006\u0001#b\u0001{\t\u0011A+\r\t\u0003ue#aA\u0017\u0001\t\u0006\u0004i$A\u0001+3!\tQD\f\u0002\u0004^\u0001!\u0015\r!\u0010\u0002\u0003)N\u0002\"AO0\u0005\r\u0001\u0004\u0001R1\u0001>\u0005\t!F\u0007\u0005\u0002;E\u001211\r\u0001EC\u0002u\u0012!\u0001V\u001b\u0011\u0005i*GA\u00024\u0001\u0011\u000b\u0007QH\u0001\u0002UmA\u0011!\b\u001b\u0003\u0007S\u0002A)\u0019A\u001f\u0003\u0005Q;\u0004C\u0001\u001el\t\u0019a\u0007\u0001#b\u0001{\t\u0011A\u000b\u000f\t\u0003u9$aa\u001c\u0001\t\u0006\u0004i$A\u0001+:!\tQ\u0014\u000f\u0002\u0004s\u0001!\u0015\r!\u0010\u0002\u0004)F\u0002\u0004C\u0001\u001eu\t\u0019)\b\u0001#b\u0001{\t\u0019A+M\u0019\u0011\u0005i:HA\u0002=\u0001\u0011\u000b\u0007QHA\u0002UcI\u0002\"A\u000f>\u0005\rm\u0004\u0001R1\u0001>\u0005\r!\u0016g\r\t\u0003uu$aA \u0001\t\u0006\u0004i$a\u0001+2iA\u0019!(!\u0001\u0005\u000f\u0005\r\u0001\u0001#b\u0001{\t\u0019A+M\u001b\u0011\u0007i\n9\u0001B\u0004\u0002\n\u0001A)\u0019A\u001f\u0003\u0007Q\u000bd\u0007E\u0002;\u0003\u001b!q!a\u0004\u0001\u0011\u000b\u0007QHA\u0002Uc]\u00022AOA\n\t\u001d\t)\u0002\u0001EC\u0002u\u00121\u0001V\u00199\u0011\u00159%\u00011\u0001I)\r\t\u00161\u0004\u0005\b\u0003;\u0019\u0001\u0019AA\u0010\u0003\u0011q\u0017-\\3\u0011\t\u0005\u0005\u0012q\u0006\b\u0005\u0003G\tY\u0003E\u0002\u0002&\u0001k!!a\n\u000b\u0007\u0005%\"'\u0001\u0004=e>|GOP\u0005\u0004\u0003[\u0001\u0015A\u0002)sK\u0012,g-\u0003\u0003\u00022\u0005M\"AB*ue&twMC\u0002\u0002.\u0001#\u0012&UA\u001c\u0003\u0003\n9%!\u0014\u0002T\u0005e\u0013qLA3\u0003W\n\t(a\u001e\u0002~\u0005\r\u0015\u0011RAH\u0003+\u000bY*!)\u0002(\u00065\u0006bBA\u001d\t\u0001\u0007\u00111H\u0001\u0004S:\u0004\u0004\u0003\u0002\u001c\u0002>IK1!a\u0010/\u0005\u0015Ie\u000e\\3u\u0011\u001d\t\u0019\u0005\u0002a\u0001\u0003\u000b\n1!\u001b82!\u00111\u0014QH+\t\u000f\u0005%C\u00011\u0001\u0002L\u0005\u0019\u0011N\u001c\u001a\u0011\tY\ni\u0004\u0017\u0005\b\u0003\u001f\"\u0001\u0019AA)\u0003\rIgn\r\t\u0005m\u0005u2\fC\u0004\u0002V\u0011\u0001\r!a\u0016\u0002\u0007%tG\u0007\u0005\u00037\u0003{q\u0006bBA.\t\u0001\u0007\u0011QL\u0001\u0004S:,\u0004\u0003\u0002\u001c\u0002>\u0005Dq!!\u0019\u0005\u0001\u0004\t\u0019'A\u0002j]Z\u0002BANA\u001fI\"9\u0011q\r\u0003A\u0002\u0005%\u0014aA5ooA!a'!\u0010h\u0011\u001d\ti\u0007\u0002a\u0001\u0003_\n1!\u001b89!\u00111\u0014Q\b6\t\u000f\u0005MD\u00011\u0001\u0002v\u0005\u0019\u0011N\\\u001d\u0011\tY\ni$\u001c\u0005\b\u0003s\"\u0001\u0019AA>\u0003\u0011Ig.\r\u0019\u0011\tY\ni\u0004\u001d\u0005\b\u0003\u007f\"\u0001\u0019AAA\u0003\u0011Ig.M\u0019\u0011\tY\nid\u001d\u0005\b\u0003\u000b#\u0001\u0019AAD\u0003\u0011Ig.\r\u001a\u0011\tY\niD\u001e\u0005\b\u0003\u0017#\u0001\u0019AAG\u0003\u0011Ig.M\u001a\u0011\tY\ni$\u001f\u0005\b\u0003##\u0001\u0019AAJ\u0003\u0011Ig.\r\u001b\u0011\tY\ni\u0004 \u0005\b\u0003/#\u0001\u0019AAM\u0003\u0011Ig.M\u001b\u0011\tY\nid \u0005\b\u0003;#\u0001\u0019AAP\u0003\u0011Ig.\r\u001c\u0011\u000bY\ni$!\u0002\t\u000f\u0005\rF\u00011\u0001\u0002&\u0006!\u0011N\\\u00198!\u00151\u0014QHA\u0006\u0011\u001d\tI\u000b\u0002a\u0001\u0003W\u000bA!\u001b82qA)a'!\u0010\u0002\u0012!9\u0011q\u0016\u0003A\u0002\u0005E\u0016aA8viB!a'a-:\u0013\r\t)L\f\u0002\u0007\u001fV$H.\u001a;\u0002\u0013\r|gn\u001d;sk\u000e$HcA\u001b\u0002<\"9\u0011QX\u0003A\u0002\u0005}\u0016\u0001B5oSR\u0004B!\u0013'\u0002B*\u001a\u0011(a1,\u0005\u0005\u0015\u0007\u0003BAd\u0003#l!!!3\u000b\t\u0005-\u0017QZ\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a4A\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003'\fIMA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\f\u0001\u0002Z3fa\u000e{\u0007/\u001f\u000b\u0002#V\u0011\u00111\u001c\t\u0006m\u0005u\u0012Q\u001c\u0016\u0004%\u0006\r\u0017\u0001B5oa\u0001*\"!a9\u0011\u000bY\ni$!:+\u0007U\u000b\u0019-\u0001\u0003j]F\u0002SCAAv!\u00151\u0014QHAwU\rA\u00161Y\u0001\u0005S:\u0014\u0004%\u0006\u0002\u0002tB)a'!\u0010\u0002v*\u001a1,a1\u0002\t%t7\u0007I\u000b\u0003\u0003w\u0004RANA\u001f\u0003{T3AXAb\u0003\u0011Ig\u000e\u000e\u0011\u0016\u0005\t\r\u0001#\u0002\u001c\u0002>\t\u0015!fA1\u0002D\u0006!\u0011N\\\u001b!+\t\u0011Y\u0001E\u00037\u0003{\u0011iAK\u0002e\u0003\u0007\fA!\u001b87AU\u0011!1\u0003\t\u0006m\u0005u\"Q\u0003\u0016\u0004O\u0006\r\u0017\u0001B5oo\u0001*\"Aa\u0007\u0011\u000bY\niD!\b+\u0007)\f\u0019-\u0001\u0003j]b\u0002SC\u0001B\u0012!\u00151\u0014Q\bB\u0013U\ri\u00171Y\u0001\u0005S:L\u0004%\u0006\u0002\u0003,A)a'!\u0010\u0003.)\u001a\u0001/a1\u0002\u000b%t\u0017\u0007\r\u0011\u0016\u0005\tM\u0002#\u0002\u001c\u0002>\tU\"fA:\u0002D\u0006)\u0011N\\\u00192AU\u0011!1\b\t\u0006m\u0005u\"Q\b\u0016\u0004m\u0006\r\u0017!B5ocI\u0002SC\u0001B\"!\u00151\u0014Q\bB#U\rI\u00181Y\u0001\u0006S:\f4\u0007I\u000b\u0003\u0005\u0017\u0002RANA\u001f\u0005\u001bR3\u0001`Ab\u0003\u0015Ig.\r\u001b!+\t\u0011\u0019\u0006E\u00037\u0003{\u0011)FK\u0002\u0000\u0003\u0007\fQ!\u001b82k\u0001*\"Aa\u0017\u0011\u000bY\niD!\u0018+\t\u0005\u0015\u00111Y\u0001\u0006S:\fd\u0007I\u000b\u0003\u0005G\u0002RANA\u001f\u0005KRC!a\u0003\u0002D\u0006)\u0011N\\\u00198AU\u0011!1\u000e\t\u0006m\u0005u\"Q\u000e\u0016\u0005\u0003#\t\u0019-A\u0003j]FB\u0004\u0005")
public class FanInShape19<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, O>
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

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape19<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, O>(init2);
    }

    @Override
    public FanInShape19<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, O> deepCopy() {
        return (FanInShape19)super.deepCopy();
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

    public FanInShape19(FanInShape.Init<O> _init) {
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
    }

    public FanInShape19(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape19(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Inlet<T8> in8, Inlet<T9> in9, Inlet<T10> in10, Inlet<T11> in11, Inlet<T12> in12, Inlet<T13> in13, Inlet<T14> in14, Inlet<T15> in15, Inlet<T16> in16, Inlet<T17> in17, Inlet<T18> in18, Outlet<O> out) {
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
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet19).$colon$colon(inlet18).$colon$colon(inlet17).$colon$colon(inlet16).$colon$colon(inlet15).$colon$colon(inlet14).$colon$colon(inlet13).$colon$colon(inlet12).$colon$colon(inlet11).$colon$colon(inlet10).$colon$colon(inlet9).$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

