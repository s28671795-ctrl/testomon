/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005%g\u0001B\u0010!\u0001\u0015B\u0001\"\u000f\u0001\u0003\u0002\u0003\u0006IA\u000f\u0005\u0006\u0003\u0002!\tA\u0011\u0005\u0006\u0003\u0002!\t!\u001b\u0005\u0006\u0003\u0002!\ta\u001e\u0005\b\u0003\u000f\u0002A\u0011KA%\u0011\u001d\t)\u0007\u0001C!\u0003OB\u0001\"\u001f\u0001C\u0002\u0013\u0005\u0011\u0011\u000e\u0005\t\u0003_\u0002\u0001\u0015!\u0003\u0002l!Aa\u0010\u0001b\u0001\n\u0003\t\t\b\u0003\u0005\u0002x\u0001\u0001\u000b\u0011BA:\u0011%\t\u0019\u0001\u0001b\u0001\n\u0003\tI\b\u0003\u0005\u0002\u0000\u0001\u0001\u000b\u0011BA>\u0011%\tI\u0001\u0001b\u0001\n\u0003\t\t\t\u0003\u0005\u0002\b\u0002\u0001\u000b\u0011BAB\u0011%\ty\u0001\u0001b\u0001\n\u0003\tI\t\u0003\u0005\u0002\u0010\u0002\u0001\u000b\u0011BAF\u0011%\t)\u0002\u0001b\u0001\n\u0003\t\t\n\u0003\u0005\u0002\u0018\u0002\u0001\u000b\u0011BAJ\u0011%\tY\u0002\u0001b\u0001\n\u0003\tI\n\u0003\u0005\u0002 \u0002\u0001\u000b\u0011BAN\u0011%\t\t\u0003\u0001b\u0001\n\u0003\t\t\u000b\u0003\u0005\u0002(\u0002\u0001\u000b\u0011BAR\u0011%\t9\u0003\u0001b\u0001\n\u0003\tI\u000b\u0003\u0005\u00020\u0002\u0001\u000b\u0011BAV\u0011%\ti\u0003\u0001b\u0001\n\u0003\t\t\f\u0003\u0005\u00028\u0002\u0001\u000b\u0011BAZ\u0011%\t\u0019\u0004\u0001b\u0001\n\u0003\tI\f\u0003\u0005\u0002@\u0002\u0001\u000b\u0011BA^\u0011%\tI\u0004\u0001b\u0001\n\u0003\t\t\r\u0003\u0005\u0002H\u0002\u0001\u000b\u0011BAb\u000511\u0015M\\%o'\"\f\u0007/Z\u00193\u0015\t\t#%\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002G\u0005!\u0011m[6b\u0007\u0001)bBJ#I\u0017:\u000bFk\u0016.^A\u000e4Wf\u0005\u0002\u0001OA\u0019\u0001&K\u0016\u000e\u0003\u0001J!A\u000b\u0011\u0003\u0015\u0019\u000bg.\u00138TQ\u0006\u0004X\r\u0005\u0002-[1\u0001AA\u0002\u0018\u0001\t\u000b\u0007qFA\u0001P#\t\u0001d\u0007\u0005\u00022i5\t!GC\u00014\u0003\u0015\u00198-\u00197b\u0013\t)$GA\u0004O_RD\u0017N\\4\u0011\u0005E:\u0014B\u0001\u001d3\u0005\r\te._\u0001\u0006?&t\u0017\u000e\u001e\t\u0004wyZcB\u0001\u0015=\u0013\ti\u0004%\u0001\u0006GC:Len\u00155ba\u0016L!a\u0010!\u0003\t%s\u0017\u000e\u001e\u0006\u0003{\u0001\na\u0001P5oSRtDCA\"i!=A\u0003\u0001R$K\u001bB\u001bf+\u0017/`E\u0016\\\u0003C\u0001\u0017F\t\u00191\u0005\u0001#b\u0001_\t\u0011A\u000b\r\t\u0003Y!#a!\u0013\u0001\t\u0006\u0004y#A\u0001+2!\ta3\n\u0002\u0004M\u0001!\u0015\ra\f\u0002\u0003)J\u0002\"\u0001\f(\u0005\r=\u0003\u0001R1\u00010\u0005\t!6\u0007\u0005\u0002-#\u00121!\u000b\u0001EC\u0002=\u0012!\u0001\u0016\u001b\u0011\u00051\"FAB+\u0001\u0011\u000b\u0007qF\u0001\u0002UkA\u0011Af\u0016\u0003\u00071\u0002A)\u0019A\u0018\u0003\u0005Q3\u0004C\u0001\u0017[\t\u0019Y\u0006\u0001#b\u0001_\t\u0011Ak\u000e\t\u0003Yu#aA\u0018\u0001\t\u0006\u0004y#A\u0001+9!\ta\u0003\r\u0002\u0004b\u0001!\u0015\ra\f\u0002\u0003)f\u0002\"\u0001L2\u0005\r\u0011\u0004\u0001R1\u00010\u0005\r!\u0016\u0007\r\t\u0003Y\u0019$aa\u001a\u0001\t\u0006\u0004y#a\u0001+2c!)\u0011H\u0001a\u0001uQ\u00111I\u001b\u0005\u0006W\u000e\u0001\r\u0001\\\u0001\u0005]\u0006lW\r\u0005\u0002ni:\u0011aN\u001d\t\u0003_Jj\u0011\u0001\u001d\u0006\u0003c\u0012\na\u0001\u0010:p_Rt\u0014BA:3\u0003\u0019\u0001&/\u001a3fM&\u0011QO\u001e\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005M\u0014D#G\"y{\u0006\u0005\u0011qAA\u0007\u0003'\tI\"a\b\u0002&\u0005-\u0012\u0011GA\u001c\u0003{AQ!\u001f\u0003A\u0002i\f1!\u001b81!\rA3\u0010R\u0005\u0003y\u0002\u0012Q!\u00138mKRDQA \u0003A\u0002}\f1!\u001b82!\rA3p\u0012\u0005\b\u0003\u0007!\u0001\u0019AA\u0003\u0003\rIgN\r\t\u0004QmT\u0005bBA\u0005\t\u0001\u0007\u00111B\u0001\u0004S:\u001c\u0004c\u0001\u0015|\u001b\"9\u0011q\u0002\u0003A\u0002\u0005E\u0011aA5oiA\u0019\u0001f\u001f)\t\u000f\u0005UA\u00011\u0001\u0002\u0018\u0005\u0019\u0011N\\\u001b\u0011\u0007!Z8\u000bC\u0004\u0002\u001c\u0011\u0001\r!!\b\u0002\u0007%tg\u0007E\u0002)wZCq!!\t\u0005\u0001\u0004\t\u0019#A\u0002j]^\u00022\u0001K>Z\u0011\u001d\t9\u0003\u0002a\u0001\u0003S\t1!\u001b89!\rA3\u0010\u0018\u0005\b\u0003[!\u0001\u0019AA\u0018\u0003\rIg.\u000f\t\u0004Qm|\u0006bBA\u001a\t\u0001\u0007\u0011QG\u0001\u0005S:\f\u0004\u0007E\u0002)w\nDq!!\u000f\u0005\u0001\u0004\tY$\u0001\u0003j]F\n\u0004c\u0001\u0015|K\"9\u0011q\b\u0003A\u0002\u0005\u0005\u0013aA8viB!\u0001&a\u0011,\u0013\r\t)\u0005\t\u0002\u0007\u001fV$H.\u001a;\u0002\u0013\r|gn\u001d;sk\u000e$HcA\u0014\u0002L!9\u0011QJ\u0003A\u0002\u0005=\u0013\u0001B5oSR\u0004Ba\u000f \u0002R)\u001a1&a\u0015,\u0005\u0005U\u0003\u0003BA,\u0003Cj!!!\u0017\u000b\t\u0005m\u0013QL\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a\u00183\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003G\nIFA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\f\u0001\u0002Z3fa\u000e{\u0007/\u001f\u000b\u0002\u0007V\u0011\u00111\u000e\t\u0005Qm\fiGK\u0002E\u0003'\nA!\u001b81AU\u0011\u00111\u000f\t\u0005Qm\f)HK\u0002H\u0003'\nA!\u001b82AU\u0011\u00111\u0010\t\u0005Qm\fiHK\u0002K\u0003'\nA!\u001b83AU\u0011\u00111\u0011\t\u0005Qm\f)IK\u0002N\u0003'\nA!\u001b84AU\u0011\u00111\u0012\t\u0005Qm\fiIK\u0002Q\u0003'\nA!\u001b85AU\u0011\u00111\u0013\t\u0005Qm\f)JK\u0002T\u0003'\nA!\u001b86AU\u0011\u00111\u0014\t\u0005Qm\fiJK\u0002W\u0003'\nA!\u001b87AU\u0011\u00111\u0015\t\u0005Qm\f)KK\u0002Z\u0003'\nA!\u001b88AU\u0011\u00111\u0016\t\u0005Qm\fiKK\u0002]\u0003'\nA!\u001b89AU\u0011\u00111\u0017\t\u0005Qm\f)LK\u0002`\u0003'\nA!\u001b8:AU\u0011\u00111\u0018\t\u0005Qm\fiLK\u0002c\u0003'\nQ!\u001b82a\u0001*\"!a1\u0011\t!Z\u0018Q\u0019\u0016\u0004K\u0006M\u0013!B5ocE\u0002\u0003")
public class FanInShape12<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, O>
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

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape12<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, O>(init2);
    }

    @Override
    public FanInShape12<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, O> deepCopy() {
        return (FanInShape12)super.deepCopy();
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

    public FanInShape12(FanInShape.Init<O> _init) {
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
    }

    public FanInShape12(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape12(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Inlet<T8> in8, Inlet<T9> in9, Inlet<T10> in10, Inlet<T11> in11, Outlet<O> out) {
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
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet12).$colon$colon(inlet11).$colon$colon(inlet10).$colon$colon(inlet9).$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

