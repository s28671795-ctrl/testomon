/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tef\u0001B\u001a5\u0001eB\u0001\"\u0014\u0001\u0003\u0002\u0003\u0006IA\u0014\u0005\u0006+\u0002!\tA\u0016\u0005\u0007+\u0002!\t!a\u000e\t\rU\u0003A\u0011AA*\u0011\u001d\t9\u000f\u0001C)\u0003SDqA!\u0002\u0001\t\u0003\u00129\u0001C\u0005\u0002X\u0001\u0011\r\u0011\"\u0001\u0003\n!A!q\u0002\u0001!\u0002\u0013\u0011Y\u0001C\u0005\u0002b\u0001\u0011\r\u0011\"\u0001\u0003\u0012!A!q\u0003\u0001!\u0002\u0013\u0011\u0019\u0002C\u0005\u0002h\u0001\u0011\r\u0011\"\u0001\u0003\u001a!A!q\u0004\u0001!\u0002\u0013\u0011Y\u0002C\u0005\u0002n\u0001\u0011\r\u0011\"\u0001\u0003\"!A!q\u0005\u0001!\u0002\u0013\u0011\u0019\u0003C\u0005\u0002t\u0001\u0011\r\u0011\"\u0001\u0003*!A!q\u0006\u0001!\u0002\u0013\u0011Y\u0003C\u0005\u0002z\u0001\u0011\r\u0011\"\u0001\u00032!A!q\u0007\u0001!\u0002\u0013\u0011\u0019\u0004C\u0005\u0002\u0000\u0001\u0011\r\u0011\"\u0001\u0003:!A!q\b\u0001!\u0002\u0013\u0011Y\u0004C\u0005\u0002\u0006\u0002\u0011\r\u0011\"\u0001\u0003B!A!q\t\u0001!\u0002\u0013\u0011\u0019\u0005C\u0005\u0002\f\u0002\u0011\r\u0011\"\u0001\u0003J!A!q\n\u0001!\u0002\u0013\u0011Y\u0005C\u0005\u0002\u0012\u0002\u0011\r\u0011\"\u0001\u0003R!A!q\u000b\u0001!\u0002\u0013\u0011\u0019\u0006C\u0005\u0002\u0018\u0002\u0011\r\u0011\"\u0001\u0003Z!A!q\f\u0001!\u0002\u0013\u0011Y\u0006C\u0005\u0002\u001e\u0002\u0011\r\u0011\"\u0001\u0003b!A!q\r\u0001!\u0002\u0013\u0011\u0019\u0007C\u0005\u0002$\u0002\u0011\r\u0011\"\u0001\u0003j!A!q\u000e\u0001!\u0002\u0013\u0011Y\u0007C\u0005\u0002*\u0002\u0011\r\u0011\"\u0001\u0003r!A!q\u000f\u0001!\u0002\u0013\u0011\u0019\bC\u0005\u00020\u0002\u0011\r\u0011\"\u0001\u0003z!A!q\u0010\u0001!\u0002\u0013\u0011Y\bC\u0005\u00026\u0002\u0011\r\u0011\"\u0001\u0003\u0002\"A!q\u0011\u0001!\u0002\u0013\u0011\u0019\tC\u0005\u0002<\u0002\u0011\r\u0011\"\u0001\u0003\n\"A!q\u0012\u0001!\u0002\u0013\u0011Y\tC\u0005\u0002B\u0002\u0011\r\u0011\"\u0001\u0003\u0012\"A!q\u0013\u0001!\u0002\u0013\u0011\u0019\nC\u0005\u0002H\u0002\u0011\r\u0011\"\u0001\u0003\u001a\"A!q\u0014\u0001!\u0002\u0013\u0011Y\nC\u0005\u0002N\u0002\u0011\r\u0011\"\u0001\u0003\"\"A!q\u0015\u0001!\u0002\u0013\u0011\u0019\u000bC\u0005\u0002T\u0002\u0011\r\u0011\"\u0001\u0003*\"A!q\u0016\u0001!\u0002\u0013\u0011Y\u000bC\u0005\u0002Z\u0002\u0011\r\u0011\"\u0001\u00032\"A!q\u0017\u0001!\u0002\u0013\u0011\u0019L\u0001\u0007GC:Len\u00155ba\u0016\u0014$G\u0003\u00026m\u000511\u000f\u001e:fC6T\u0011aN\u0001\u0005C.\\\u0017m\u0001\u0001\u0016CiJFl\u00182fQ.t\u0017\u000f^<{{\u0006\u0005\u0011qAA\u0007\u0003'\tI\"a\b\u0002&\u0005-\u0012\u0011G!\u0014\u0005\u0001Y\u0004c\u0001\u001f>\u007f5\tA'\u0003\u0002?i\tQa)\u00198J]NC\u0017\r]3\u0011\u0005\u0001\u000bE\u0002\u0001\u0003\u0007\u0005\u0002!)\u0019A\"\u0003\u0003=\u000b\"\u0001\u0012&\u0011\u0005\u0015CU\"\u0001$\u000b\u0003\u001d\u000bQa]2bY\u0006L!!\u0013$\u0003\u000f9{G\u000f[5oOB\u0011QiS\u0005\u0003\u0019\u001a\u00131!\u00118z\u0003\u0015y\u0016N\\5u!\ry%k\u0010\b\u0003yAK!!\u0015\u001b\u0002\u0015\u0019\u000bg.\u00138TQ\u0006\u0004X-\u0003\u0002T)\n!\u0011J\\5u\u0015\t\tF'\u0001\u0004=S:LGO\u0010\u000b\u0004/\u0006U\u0002#\t\u001f\u00011ns\u0016\rZ4k[B\u001ch/\u001f?\u0000\u0003\u000b\tY!!\u0005\u0002\u0018\u0005u\u00111EA\u0015\u0003_y\u0004C\u0001!Z\t\u0019Q\u0006\u0001#b\u0001\u0007\n\u0011A\u000b\r\t\u0003\u0001r#a!\u0018\u0001\t\u0006\u0004\u0019%A\u0001+2!\t\u0001u\f\u0002\u0004a\u0001!\u0015\ra\u0011\u0002\u0003)J\u0002\"\u0001\u00112\u0005\r\r\u0004\u0001R1\u0001D\u0005\t!6\u0007\u0005\u0002AK\u00121a\r\u0001EC\u0002\r\u0013!\u0001\u0016\u001b\u0011\u0005\u0001CGAB5\u0001\u0011\u000b\u00071I\u0001\u0002UkA\u0011\u0001i\u001b\u0003\u0007Y\u0002A)\u0019A\"\u0003\u0005Q3\u0004C\u0001!o\t\u0019y\u0007\u0001#b\u0001\u0007\n\u0011Ak\u000e\t\u0003\u0001F$aA\u001d\u0001\t\u0006\u0004\u0019%A\u0001+9!\t\u0001E\u000f\u0002\u0004v\u0001!\u0015\ra\u0011\u0002\u0003)f\u0002\"\u0001Q<\u0005\ra\u0004\u0001R1\u0001D\u0005\r!\u0016\u0007\r\t\u0003\u0001j$aa\u001f\u0001\t\u0006\u0004\u0019%a\u0001+2cA\u0011\u0001) \u0003\u0007}\u0002A)\u0019A\"\u0003\u0007Q\u000b$\u0007E\u0002A\u0003\u0003!q!a\u0001\u0001\u0011\u000b\u00071IA\u0002UcM\u00022\u0001QA\u0004\t\u001d\tI\u0001\u0001EC\u0002\r\u00131\u0001V\u00195!\r\u0001\u0015Q\u0002\u0003\b\u0003\u001f\u0001\u0001R1\u0001D\u0005\r!\u0016'\u000e\t\u0004\u0001\u0006MAaBA\u000b\u0001!\u0015\ra\u0011\u0002\u0004)F2\u0004c\u0001!\u0002\u001a\u00119\u00111\u0004\u0001\t\u0006\u0004\u0019%a\u0001+2oA\u0019\u0001)a\b\u0005\u000f\u0005\u0005\u0002\u0001#b\u0001\u0007\n\u0019A+\r\u001d\u0011\u0007\u0001\u000b)\u0003B\u0004\u0002(\u0001A)\u0019A\"\u0003\u0007Q\u000b\u0014\bE\u0002A\u0003W!q!!\f\u0001\u0011\u000b\u00071IA\u0002UeA\u00022\u0001QA\u0019\t\u001d\t\u0019\u0004\u0001EC\u0002\r\u00131\u0001\u0016\u001a2\u0011\u0015i%\u00011\u0001O)\r9\u0016\u0011\b\u0005\b\u0003w\u0019\u0001\u0019AA\u001f\u0003\u0011q\u0017-\\3\u0011\t\u0005}\u0012Q\n\b\u0005\u0003\u0003\nI\u0005E\u0002\u0002D\u0019k!!!\u0012\u000b\u0007\u0005\u001d\u0003(\u0001\u0004=e>|GOP\u0005\u0004\u0003\u00172\u0015A\u0002)sK\u0012,g-\u0003\u0003\u0002P\u0005E#AB*ue&twMC\u0002\u0002L\u0019#rfVA+\u0003?\n)'a\u001b\u0002r\u0005]\u0014QPAB\u0003\u0013\u000by)!&\u0002\u001c\u0006\u0005\u0016qUAW\u0003g\u000bI,a0\u0002F\u0006-\u0017\u0011[Al\u0003;Dq!a\u0016\u0005\u0001\u0004\tI&A\u0002j]B\u0002B\u0001PA.1&\u0019\u0011Q\f\u001b\u0003\u000b%sG.\u001a;\t\u000f\u0005\u0005D\u00011\u0001\u0002d\u0005\u0019\u0011N\\\u0019\u0011\tq\nYf\u0017\u0005\b\u0003O\"\u0001\u0019AA5\u0003\rIgN\r\t\u0005y\u0005mc\fC\u0004\u0002n\u0011\u0001\r!a\u001c\u0002\u0007%t7\u0007\u0005\u0003=\u00037\n\u0007bBA:\t\u0001\u0007\u0011QO\u0001\u0004S:$\u0004\u0003\u0002\u001f\u0002\\\u0011Dq!!\u001f\u0005\u0001\u0004\tY(A\u0002j]V\u0002B\u0001PA.O\"9\u0011q\u0010\u0003A\u0002\u0005\u0005\u0015aA5omA!A(a\u0017k\u0011\u001d\t)\t\u0002a\u0001\u0003\u000f\u000b1!\u001b88!\u0011a\u00141L7\t\u000f\u0005-E\u00011\u0001\u0002\u000e\u0006\u0019\u0011N\u001c\u001d\u0011\tq\nY\u0006\u001d\u0005\b\u0003##\u0001\u0019AAJ\u0003\rIg.\u000f\t\u0005y\u0005m3\u000fC\u0004\u0002\u0018\u0012\u0001\r!!'\u0002\t%t\u0017\u0007\r\t\u0005y\u0005mc\u000fC\u0004\u0002\u001e\u0012\u0001\r!a(\u0002\t%t\u0017'\r\t\u0005y\u0005m\u0013\u0010C\u0004\u0002$\u0012\u0001\r!!*\u0002\t%t\u0017G\r\t\u0005y\u0005mC\u0010C\u0004\u0002*\u0012\u0001\r!a+\u0002\t%t\u0017g\r\t\u0005y\u0005ms\u0010C\u0004\u00020\u0012\u0001\r!!-\u0002\t%t\u0017\u0007\u000e\t\u0006y\u0005m\u0013Q\u0001\u0005\b\u0003k#\u0001\u0019AA\\\u0003\u0011Ig.M\u001b\u0011\u000bq\nY&a\u0003\t\u000f\u0005mF\u00011\u0001\u0002>\u0006!\u0011N\\\u00197!\u0015a\u00141LA\t\u0011\u001d\t\t\r\u0002a\u0001\u0003\u0007\fA!\u001b82oA)A(a\u0017\u0002\u0018!9\u0011q\u0019\u0003A\u0002\u0005%\u0017\u0001B5oca\u0002R\u0001PA.\u0003;Aq!!4\u0005\u0001\u0004\ty-\u0001\u0003j]FJ\u0004#\u0002\u001f\u0002\\\u0005\r\u0002bBAj\t\u0001\u0007\u0011Q[\u0001\u0005S:\u0014\u0004\u0007E\u0003=\u00037\nI\u0003C\u0004\u0002Z\u0012\u0001\r!a7\u0002\t%t''\r\t\u0006y\u0005m\u0013q\u0006\u0005\b\u0003?$\u0001\u0019AAq\u0003\ryW\u000f\u001e\t\u0005y\u0005\rx(C\u0002\u0002fR\u0012aaT;uY\u0016$\u0018!C2p]N$(/^2u)\rY\u00141\u001e\u0005\b\u0003[,\u0001\u0019AAx\u0003\u0011Ig.\u001b;\u0011\t=\u0013\u0016\u0011\u001f\u0016\u0004\u007f\u0005M8FAA{!\u0011\t9P!\u0001\u000e\u0005\u0005e(\u0002BA~\u0003{\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005}h)\u0001\u0006b]:|G/\u0019;j_:LAAa\u0001\u0002z\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u0011\u0011,W\r]\"paf$\u0012aV\u000b\u0003\u0005\u0017\u0001R\u0001PA.\u0005\u001bQ3\u0001WAz\u0003\u0011Ig\u000e\r\u0011\u0016\u0005\tM\u0001#\u0002\u001f\u0002\\\tU!fA.\u0002t\u0006!\u0011N\\\u0019!+\t\u0011Y\u0002E\u0003=\u00037\u0012iBK\u0002_\u0003g\fA!\u001b83AU\u0011!1\u0005\t\u0006y\u0005m#Q\u0005\u0016\u0004C\u0006M\u0018\u0001B5og\u0001*\"Aa\u000b\u0011\u000bq\nYF!\f+\u0007\u0011\f\u00190\u0001\u0003j]R\u0002SC\u0001B\u001a!\u0015a\u00141\fB\u001bU\r9\u00171_\u0001\u0005S:,\u0004%\u0006\u0002\u0003<A)A(a\u0017\u0003>)\u001a!.a=\u0002\t%tg\u0007I\u000b\u0003\u0005\u0007\u0002R\u0001PA.\u0005\u000bR3!\\Az\u0003\u0011Ign\u000e\u0011\u0016\u0005\t-\u0003#\u0002\u001f\u0002\\\t5#f\u00019\u0002t\u0006!\u0011N\u001c\u001d!+\t\u0011\u0019\u0006E\u0003=\u00037\u0012)FK\u0002t\u0003g\fA!\u001b8:AU\u0011!1\f\t\u0006y\u0005m#Q\f\u0016\u0004m\u0006M\u0018!B5ocA\u0002SC\u0001B2!\u0015a\u00141\fB3U\rI\u00181_\u0001\u0006S:\f\u0014\u0007I\u000b\u0003\u0005W\u0002R\u0001PA.\u0005[R3\u0001`Az\u0003\u0015Ig.\r\u001a!+\t\u0011\u0019\bE\u0003=\u00037\u0012)HK\u0002\u0000\u0003g\fQ!\u001b82g\u0001*\"Aa\u001f\u0011\u000bq\nYF! +\t\u0005\u0015\u00111_\u0001\u0006S:\fD\u0007I\u000b\u0003\u0005\u0007\u0003R\u0001PA.\u0005\u000bSC!a\u0003\u0002t\u0006)\u0011N\\\u00196AU\u0011!1\u0012\t\u0006y\u0005m#Q\u0012\u0016\u0005\u0003#\t\u00190A\u0003j]F2\u0004%\u0006\u0002\u0003\u0014B)A(a\u0017\u0003\u0016*\"\u0011qCAz\u0003\u0015Ig.M\u001c!+\t\u0011Y\nE\u0003=\u00037\u0012iJ\u000b\u0003\u0002\u001e\u0005M\u0018!B5oca\u0002SC\u0001BR!\u0015a\u00141\fBSU\u0011\t\u0019#a=\u0002\u000b%t\u0017'\u000f\u0011\u0016\u0005\t-\u0006#\u0002\u001f\u0002\\\t5&\u0006BA\u0015\u0003g\fQ!\u001b83a\u0001*\"Aa-\u0011\u000bq\nYF!.+\t\u0005=\u00121_\u0001\u0006S:\u0014\u0014\u0007\t")
public class FanInShape22<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, O>
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
    private final Inlet<T21> in21;

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape22<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, O>(init2);
    }

    @Override
    public FanInShape22<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, O> deepCopy() {
        return (FanInShape22)super.deepCopy();
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

    public Inlet<T21> in21() {
        return this.in21;
    }

    public FanInShape22(FanInShape.Init<O> _init) {
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
        this.in21 = this.newInlet("in21");
    }

    public FanInShape22(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape22(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Inlet<T8> in8, Inlet<T9> in9, Inlet<T10> in10, Inlet<T11> in11, Inlet<T12> in12, Inlet<T13> in13, Inlet<T14> in14, Inlet<T15> in15, Inlet<T16> in16, Inlet<T17> in17, Inlet<T18> in18, Inlet<T19> in19, Inlet<T20> in20, Inlet<T21> in21, Outlet<O> out) {
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
        Inlet<T21> inlet22 = in21;
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet22).$colon$colon(inlet21).$colon$colon(inlet20).$colon$colon(inlet19).$colon$colon(inlet18).$colon$colon(inlet17).$colon$colon(inlet16).$colon$colon(inlet15).$colon$colon(inlet14).$colon$colon(inlet13).$colon$colon(inlet12).$colon$colon(inlet11).$colon$colon(inlet10).$colon$colon(inlet9).$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

