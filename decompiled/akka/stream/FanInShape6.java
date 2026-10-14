/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005eb\u0001B\n\u0015\u0001eA\u0001\"\f\u0001\u0003\u0002\u0003\u0006IA\f\u0005\u0006k\u0001!\tA\u000e\u0005\u0006k\u0001!\ta\u0013\u0005\u0006k\u0001!\t!\u0017\u0005\u0006g\u0002!\t\u0006\u001e\u0005\b\u0003\u000b\u0001A\u0011IA\u0004\u0011!Y\u0006A1A\u0005\u0002\u0005%\u0001\u0002CA\b\u0001\u0001\u0006I!a\u0003\t\u0011\u0001\u0004!\u0019!C\u0001\u0003#A\u0001\"a\u0006\u0001A\u0003%\u00111\u0003\u0005\tG\u0002\u0011\r\u0011\"\u0001\u0002\u001a!A\u0011q\u0004\u0001!\u0002\u0013\tY\u0002\u0003\u0005g\u0001\t\u0007I\u0011AA\u0011\u0011!\t9\u0003\u0001Q\u0001\n\u0005\r\u0002\u0002C5\u0001\u0005\u0004%\t!!\u000b\t\u0011\u0005=\u0002\u0001)A\u0005\u0003WA\u0001\u0002\u001c\u0001C\u0002\u0013\u0005\u0011\u0011\u0007\u0005\t\u0003o\u0001\u0001\u0015!\u0003\u00024\tYa)\u00198J]NC\u0017\r]37\u0015\t)b#\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002/\u0005!\u0011m[6b\u0007\u0001)\u0002BG\u001d=\u007f\t+\u0005*I\n\u0003\u0001m\u00012\u0001H\u000f \u001b\u0005!\u0012B\u0001\u0010\u0015\u0005)1\u0015M\\%o'\"\f\u0007/\u001a\t\u0003A\u0005b\u0001\u0001\u0002\u0004#\u0001\u0011\u0015\ra\t\u0002\u0002\u001fF\u0011AE\u000b\t\u0003K!j\u0011A\n\u0006\u0002O\u0005)1oY1mC&\u0011\u0011F\n\u0002\b\u001d>$\b.\u001b8h!\t)3&\u0003\u0002-M\t\u0019\u0011I\\=\u0002\u000b}Kg.\u001b;\u0011\u0007=\u0012tD\u0004\u0002\u001da%\u0011\u0011\u0007F\u0001\u000b\r\u0006t\u0017J\\*iCB,\u0017BA\u001a5\u0005\u0011Ie.\u001b;\u000b\u0005E\"\u0012A\u0002\u001fj]&$h\b\u0006\u00028\u0015BIA\u0004\u0001\u001d<}\u0005#ui\b\t\u0003Ae\"aA\u000f\u0001\t\u0006\u0004\u0019#A\u0001+1!\t\u0001C\b\u0002\u0004>\u0001!\u0015\ra\t\u0002\u0003)F\u0002\"\u0001I \u0005\r\u0001\u0003\u0001R1\u0001$\u0005\t!&\u0007\u0005\u0002!\u0005\u001211\t\u0001EC\u0002\r\u0012!\u0001V\u001a\u0011\u0005\u0001*EA\u0002$\u0001\u0011\u000b\u00071E\u0001\u0002UiA\u0011\u0001\u0005\u0013\u0003\u0007\u0013\u0002A)\u0019A\u0012\u0003\u0005Q+\u0004\"B\u0017\u0003\u0001\u0004qCCA\u001cM\u0011\u0015i5\u00011\u0001O\u0003\u0011q\u0017-\\3\u0011\u0005=3fB\u0001)U!\t\tf%D\u0001S\u0015\t\u0019\u0006$\u0001\u0004=e>|GOP\u0005\u0003+\u001a\na\u0001\u0015:fI\u00164\u0017BA,Y\u0005\u0019\u0019FO]5oO*\u0011QK\n\u000b\toi{&-\u001a5l]\")1\f\u0002a\u00019\u0006\u0019\u0011N\u001c\u0019\u0011\u0007qi\u0006(\u0003\u0002_)\t)\u0011J\u001c7fi\")\u0001\r\u0002a\u0001C\u0006\u0019\u0011N\\\u0019\u0011\u0007qi6\bC\u0003d\t\u0001\u0007A-A\u0002j]J\u00022\u0001H/?\u0011\u00151G\u00011\u0001h\u0003\rIgn\r\t\u00049u\u000b\u0005\"B5\u0005\u0001\u0004Q\u0017aA5oiA\u0019A$\u0018#\t\u000b1$\u0001\u0019A7\u0002\u0007%tW\u0007E\u0002\u001d;\u001eCQa\u001c\u0003A\u0002A\f1a\\;u!\ra\u0012oH\u0005\u0003eR\u0011aaT;uY\u0016$\u0018!C2p]N$(/^2u)\tYR\u000fC\u0003w\u000b\u0001\u0007q/\u0001\u0003j]&$\bcA\u00183q*\u0012q$_\u0016\u0002uB\u001910!\u0001\u000e\u0003qT!! @\u0002\u0013Ut7\r[3dW\u0016$'BA@'\u0003)\tgN\\8uCRLwN\\\u0005\u0004\u0003\u0007a(!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006AA-Z3q\u0007>\u0004\u0018\u0010F\u00018+\t\tY\u0001\u0005\u0003\u001d;\u00065!F\u0001\u001dz\u0003\u0011Ig\u000e\r\u0011\u0016\u0005\u0005M\u0001\u0003\u0002\u000f^\u0003+Q#aO=\u0002\t%t\u0017\u0007I\u000b\u0003\u00037\u0001B\u0001H/\u0002\u001e)\u0012a(_\u0001\u0005S:\u0014\u0004%\u0006\u0002\u0002$A!A$XA\u0013U\t\t\u00150\u0001\u0003j]N\u0002SCAA\u0016!\u0011aR,!\f+\u0005\u0011K\u0018\u0001B5oi\u0001*\"!a\r\u0011\tqi\u0016Q\u0007\u0016\u0003\u000ff\fA!\u001b86A\u0001")
public class FanInShape6<T0, T1, T2, T3, T4, T5, O>
extends FanInShape<O> {
    private final Inlet<T0> in0;
    private final Inlet<T1> in1;
    private final Inlet<T2> in2;
    private final Inlet<T3> in3;
    private final Inlet<T4> in4;
    private final Inlet<T5> in5;

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape6<T0, T1, T2, T3, T4, T5, O>(init2);
    }

    @Override
    public FanInShape6<T0, T1, T2, T3, T4, T5, O> deepCopy() {
        return (FanInShape6)super.deepCopy();
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

    public FanInShape6(FanInShape.Init<O> _init) {
        super(_init);
        this.in0 = this.newInlet("in0");
        this.in1 = this.newInlet("in1");
        this.in2 = this.newInlet("in2");
        this.in3 = this.newInlet("in3");
        this.in4 = this.newInlet("in4");
        this.in5 = this.newInlet("in5");
    }

    public FanInShape6(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape6(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Inlet<T4> in4, Inlet<T5> in5, Outlet<O> out) {
        Inlet<T0> inlet = in0;
        Inlet<T1> inlet2 = in1;
        Inlet<T2> inlet3 = in2;
        Inlet<T3> inlet4 = in3;
        Inlet<T4> inlet5 = in4;
        Inlet<T5> inlet6 = in5;
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet6).$colon$colon(inlet5).$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

