/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005eh\u0001B\u0012%\u0001%B\u0001\"\u0010\u0001\u0003\u0002\u0003\u0006IA\u0010\u0005\u0006\u000b\u0002!\tA\u0012\u0005\u0006\u000b\u0002!\ta\u001d\u0005\u0007\u000b\u0002!\t!a\u0001\t\u000f\u0005\u001d\u0004\u0001\"\u0015\u0002j!9\u0011Q\u0011\u0001\u0005B\u0005\u001d\u0005\"CA\u0004\u0001\t\u0007I\u0011AAE\u0011!\ty\t\u0001Q\u0001\n\u0005-\u0005\"CA\t\u0001\t\u0007I\u0011AAI\u0011!\t9\n\u0001Q\u0001\n\u0005M\u0005\"CA\f\u0001\t\u0007I\u0011AAM\u0011!\ty\n\u0001Q\u0001\n\u0005m\u0005\"CA\u000f\u0001\t\u0007I\u0011AAQ\u0011!\t9\u000b\u0001Q\u0001\n\u0005\r\u0006\"CA\u0012\u0001\t\u0007I\u0011AAU\u0011!\ty\u000b\u0001Q\u0001\n\u0005-\u0006\"CA\u0015\u0001\t\u0007I\u0011AAY\u0011!\t9\f\u0001Q\u0001\n\u0005M\u0006\"CA\u0018\u0001\t\u0007I\u0011AA]\u0011!\ty\f\u0001Q\u0001\n\u0005m\u0006\"CA\u001b\u0001\t\u0007I\u0011AAa\u0011!\t9\r\u0001Q\u0001\n\u0005\r\u0007\"CA\u001e\u0001\t\u0007I\u0011AAe\u0011!\ty\r\u0001Q\u0001\n\u0005-\u0007\"CA!\u0001\t\u0007I\u0011AAi\u0011!\t9\u000e\u0001Q\u0001\n\u0005M\u0007\"CA$\u0001\t\u0007I\u0011AAm\u0011!\ty\u000e\u0001Q\u0001\n\u0005m\u0007\"CA'\u0001\t\u0007I\u0011AAq\u0011!\t9\u000f\u0001Q\u0001\n\u0005\r\b\"CA*\u0001\t\u0007I\u0011AAu\u0011!\ty\u000f\u0001Q\u0001\n\u0005-\b\"CA-\u0001\t\u0007I\u0011AAy\u0011!\t9\u0010\u0001Q\u0001\n\u0005M(\u0001\u0004$b]&s7\u000b[1qKF\"$BA\u0013'\u0003\u0019\u0019HO]3b[*\tq%\u0001\u0003bW.\f7\u0001A\u000b\u0011U%cuJU+Y7z\u000bGm\u001a6naF\u001a\"\u0001A\u0016\u0011\u00071js&D\u0001%\u0013\tqCE\u0001\u0006GC:Len\u00155ba\u0016\u0004\"\u0001M\u0019\r\u0001\u00111!\u0007\u0001CC\u0002M\u0012\u0011aT\t\u0003ii\u0002\"!\u000e\u001d\u000e\u0003YR\u0011aN\u0001\u0006g\u000e\fG.Y\u0005\u0003sY\u0012qAT8uQ&tw\r\u0005\u00026w%\u0011AH\u000e\u0002\u0004\u0003:L\u0018!B0j]&$\bcA C_9\u0011A\u0006Q\u0005\u0003\u0003\u0012\n!BR1o\u0013:\u001c\u0006.\u00199f\u0013\t\u0019EI\u0001\u0003J]&$(BA!%\u0003\u0019a\u0014N\\5u}Q\u0011qI\u001d\t\u0012Y\u0001A5JT)U/jk\u0006m\u00194jY>|\u0003C\u0001\u0019J\t\u0019Q\u0005\u0001#b\u0001g\t\u0011A\u000b\r\t\u0003a1#a!\u0014\u0001\t\u0006\u0004\u0019$A\u0001+2!\t\u0001t\n\u0002\u0004Q\u0001!\u0015\ra\r\u0002\u0003)J\u0002\"\u0001\r*\u0005\rM\u0003\u0001R1\u00014\u0005\t!6\u0007\u0005\u00021+\u00121a\u000b\u0001EC\u0002M\u0012!\u0001\u0016\u001b\u0011\u0005ABFAB-\u0001\u0011\u000b\u00071G\u0001\u0002UkA\u0011\u0001g\u0017\u0003\u00079\u0002A)\u0019A\u001a\u0003\u0005Q3\u0004C\u0001\u0019_\t\u0019y\u0006\u0001#b\u0001g\t\u0011Ak\u000e\t\u0003a\u0005$aA\u0019\u0001\t\u0006\u0004\u0019$A\u0001+9!\t\u0001D\r\u0002\u0004f\u0001!\u0015\ra\r\u0002\u0003)f\u0002\"\u0001M4\u0005\r!\u0004\u0001R1\u00014\u0005\r!\u0016\u0007\r\t\u0003a)$aa\u001b\u0001\t\u0006\u0004\u0019$a\u0001+2cA\u0011\u0001'\u001c\u0003\u0007]\u0002A)\u0019A\u001a\u0003\u0007Q\u000b$\u0007\u0005\u00021a\u00121\u0011\u000f\u0001EC\u0002M\u00121\u0001V\u00194\u0011\u0015i$\u00011\u0001?)\t9E\u000fC\u0003v\u0007\u0001\u0007a/\u0001\u0003oC6,\u0007CA<\u007f\u001d\tAH\u0010\u0005\u0002zm5\t!P\u0003\u0002|Q\u00051AH]8pizJ!! \u001c\u0002\rA\u0013X\rZ3g\u0013\ry\u0018\u0011\u0001\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005u4DcH$\u0002\u0006\u0005=\u0011QCA\u000e\u0003C\t9#!\f\u00024\u0005e\u0012qHA#\u0003\u0017\n\t&a\u0016\u0002^!9\u0011q\u0001\u0003A\u0002\u0005%\u0011aA5oaA!A&a\u0003I\u0013\r\ti\u0001\n\u0002\u0006\u0013:dW\r\u001e\u0005\b\u0003#!\u0001\u0019AA\n\u0003\rIg.\r\t\u0005Y\u0005-1\nC\u0004\u0002\u0018\u0011\u0001\r!!\u0007\u0002\u0007%t'\u0007\u0005\u0003-\u0003\u0017q\u0005bBA\u000f\t\u0001\u0007\u0011qD\u0001\u0004S:\u001c\u0004\u0003\u0002\u0017\u0002\fECq!a\t\u0005\u0001\u0004\t)#A\u0002j]R\u0002B\u0001LA\u0006)\"9\u0011\u0011\u0006\u0003A\u0002\u0005-\u0012aA5okA!A&a\u0003X\u0011\u001d\ty\u0003\u0002a\u0001\u0003c\t1!\u001b87!\u0011a\u00131\u0002.\t\u000f\u0005UB\u00011\u0001\u00028\u0005\u0019\u0011N\\\u001c\u0011\t1\nY!\u0018\u0005\b\u0003w!\u0001\u0019AA\u001f\u0003\rIg\u000e\u000f\t\u0005Y\u0005-\u0001\rC\u0004\u0002B\u0011\u0001\r!a\u0011\u0002\u0007%t\u0017\b\u0005\u0003-\u0003\u0017\u0019\u0007bBA$\t\u0001\u0007\u0011\u0011J\u0001\u0005S:\f\u0004\u0007\u0005\u0003-\u0003\u00171\u0007bBA'\t\u0001\u0007\u0011qJ\u0001\u0005S:\f\u0014\u0007\u0005\u0003-\u0003\u0017I\u0007bBA*\t\u0001\u0007\u0011QK\u0001\u0005S:\f$\u0007\u0005\u0003-\u0003\u0017a\u0007bBA-\t\u0001\u0007\u00111L\u0001\u0005S:\f4\u0007\u0005\u0003-\u0003\u0017y\u0007bBA0\t\u0001\u0007\u0011\u0011M\u0001\u0004_V$\b\u0003\u0002\u0017\u0002d=J1!!\u001a%\u0005\u0019yU\u000f\u001e7fi\u0006I1m\u001c8tiJ,8\r\u001e\u000b\u0004W\u0005-\u0004bBA7\u000b\u0001\u0007\u0011qN\u0001\u0005S:LG\u000f\u0005\u0003@\u0005\u0006E$fA\u0018\u0002t-\u0012\u0011Q\u000f\t\u0005\u0003o\n\t)\u0004\u0002\u0002z)!\u00111PA?\u0003%)hn\u00195fG.,GMC\u0002\u0002\u0000Y\n!\"\u00198o_R\fG/[8o\u0013\u0011\t\u0019)!\u001f\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\u0005eK\u0016\u00048i\u001c9z)\u00059UCAAF!\u0015a\u00131BAGU\rA\u00151O\u0001\u0005S:\u0004\u0004%\u0006\u0002\u0002\u0014B)A&a\u0003\u0002\u0016*\u001a1*a\u001d\u0002\t%t\u0017\u0007I\u000b\u0003\u00037\u0003R\u0001LA\u0006\u0003;S3ATA:\u0003\u0011IgN\r\u0011\u0016\u0005\u0005\r\u0006#\u0002\u0017\u0002\f\u0005\u0015&fA)\u0002t\u0005!\u0011N\\\u001a!+\t\tY\u000bE\u0003-\u0003\u0017\tiKK\u0002U\u0003g\nA!\u001b85AU\u0011\u00111\u0017\t\u0006Y\u0005-\u0011Q\u0017\u0016\u0004/\u0006M\u0014\u0001B5ok\u0001*\"!a/\u0011\u000b1\nY!!0+\u0007i\u000b\u0019(\u0001\u0003j]Z\u0002SCAAb!\u0015a\u00131BAcU\ri\u00161O\u0001\u0005S:<\u0004%\u0006\u0002\u0002LB)A&a\u0003\u0002N*\u001a\u0001-a\u001d\u0002\t%t\u0007\bI\u000b\u0003\u0003'\u0004R\u0001LA\u0006\u0003+T3aYA:\u0003\u0011Ig.\u000f\u0011\u0016\u0005\u0005m\u0007#\u0002\u0017\u0002\f\u0005u'f\u00014\u0002t\u0005)\u0011N\\\u00191AU\u0011\u00111\u001d\t\u0006Y\u0005-\u0011Q\u001d\u0016\u0004S\u0006M\u0014!B5ocE\u0002SCAAv!\u0015a\u00131BAwU\ra\u00171O\u0001\u0006S:\f$\u0007I\u000b\u0003\u0003g\u0004R\u0001LA\u0006\u0003kT3a\\A:\u0003\u0015Ig.M\u001a!\u0001")
public class FanInShape14<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, O>
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

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape14<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, O>(init2);
    }

    @Override
    public FanInShape14<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, O> deepCopy() {
        return (FanInShape14)super.deepCopy();
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

    public FanInShape14(FanInShape.Init<O> _init) {
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
    }

    public FanInShape14(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape14(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Inlet<T6> in6, Inlet<T7> in7, Inlet<T8> in8, Inlet<T9> in9, Inlet<T10> in10, Inlet<T11> in11, Inlet<T12> in12, Inlet<T13> in13, Outlet<O> out) {
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
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet14).$colon$colon(inlet13).$colon$colon(inlet12).$colon$colon(inlet11).$colon$colon(inlet10).$colon$colon(inlet9).$colon$colon(inlet8).$colon$colon(inlet7).$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

