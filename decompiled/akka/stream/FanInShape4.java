/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005%a\u0001B\b\u0011\u0001UA\u0001\"\u000b\u0001\u0003\u0002\u0003\u0006IA\u000b\u0005\u0006c\u0001!\tA\r\u0005\u0006c\u0001!\t!\u0011\u0005\u0006c\u0001!\ta\u0014\u0005\u0006G\u0002!\t\u0006\u001a\u0005\u0006e\u0002!\te\u001d\u0005\b#\u0002\u0011\r\u0011\"\u0001u\u0011\u00199\b\u0001)A\u0005k\"9a\u000b\u0001b\u0001\n\u0003A\bBB>\u0001A\u0003%\u0011\u0010C\u0004Z\u0001\t\u0007I\u0011\u0001?\t\r}\u0004\u0001\u0015!\u0003~\u0011!a\u0006A1A\u0005\u0002\u0005\u0005\u0001\u0002CA\u0004\u0001\u0001\u0006I!a\u0001\u0003\u0017\u0019\u000bg.\u00138TQ\u0006\u0004X\r\u000e\u0006\u0003#I\taa\u001d;sK\u0006l'\"A\n\u0002\t\u0005\\7.Y\u0002\u0001+\u00191R\u0007O\u001e?;M\u0011\u0001a\u0006\t\u00041eYR\"\u0001\t\n\u0005i\u0001\"A\u0003$b]&s7\u000b[1qKB\u0011A$\b\u0007\u0001\t\u0019q\u0002\u0001\"b\u0001?\t\tq*\u0005\u0002!MA\u0011\u0011\u0005J\u0007\u0002E)\t1%A\u0003tG\u0006d\u0017-\u0003\u0002&E\t9aj\u001c;iS:<\u0007CA\u0011(\u0013\tA#EA\u0002B]f\fQaX5oSR\u00042a\u000b\u0018\u001c\u001d\tAB&\u0003\u0002.!\u0005Qa)\u00198J]NC\u0017\r]3\n\u0005=\u0002$\u0001B%oSRT!!\f\t\u0002\rqJg.\u001b;?)\t\u0019\u0004\tE\u0004\u0019\u0001Q:$(P\u000e\u0011\u0005q)DA\u0002\u001c\u0001\u0011\u000b\u0007qD\u0001\u0002UaA\u0011A\u0004\u000f\u0003\u0007s\u0001A)\u0019A\u0010\u0003\u0005Q\u000b\u0004C\u0001\u000f<\t\u0019a\u0004\u0001#b\u0001?\t\u0011AK\r\t\u00039y\"aa\u0010\u0001\t\u0006\u0004y\"A\u0001+4\u0011\u0015I#\u00011\u0001+)\t\u0019$\tC\u0003D\u0007\u0001\u0007A)\u0001\u0003oC6,\u0007CA#M\u001d\t1%\n\u0005\u0002HE5\t\u0001J\u0003\u0002J)\u00051AH]8pizJ!a\u0013\u0012\u0002\rA\u0013X\rZ3g\u0013\tieJ\u0001\u0004TiJLgn\u001a\u0006\u0003\u0017\n\"ba\r)V1ns\u0006\"B)\u0005\u0001\u0004\u0011\u0016aA5oaA\u0019\u0001d\u0015\u001b\n\u0005Q\u0003\"!B%oY\u0016$\b\"\u0002,\u0005\u0001\u00049\u0016aA5ocA\u0019\u0001dU\u001c\t\u000be#\u0001\u0019\u0001.\u0002\u0007%t'\u0007E\u0002\u0019'jBQ\u0001\u0018\u0003A\u0002u\u000b1!\u001b84!\rA2+\u0010\u0005\u0006?\u0012\u0001\r\u0001Y\u0001\u0004_V$\bc\u0001\rb7%\u0011!\r\u0005\u0002\u0007\u001fV$H.\u001a;\u0002\u0013\r|gn\u001d;sk\u000e$HCA\ff\u0011\u00151W\u00011\u0001h\u0003\u0011Ig.\u001b;\u0011\u0007-r\u0003N\u000b\u0002\u001cS.\n!\u000e\u0005\u0002la6\tAN\u0003\u0002n]\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003_\n\n!\"\u00198o_R\fG/[8o\u0013\t\tHNA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\f\u0001\u0002Z3fa\u000e{\u0007/\u001f\u000b\u0002gU\tQ\u000fE\u0002\u0019'ZT#\u0001N5\u0002\t%t\u0007\u0007I\u000b\u0002sB\u0019\u0001d\u0015>+\u0005]J\u0017\u0001B5oc\u0001*\u0012! \t\u00041Ms(F\u0001\u001ej\u0003\u0011IgN\r\u0011\u0016\u0005\u0005\r\u0001\u0003\u0002\rT\u0003\u000bQ#!P5\u0002\t%t7\u0007\t")
public class FanInShape4<T0, T1, T2, T3, O>
extends FanInShape<O> {
    private final Inlet<T0> in0;
    private final Inlet<T1> in1;
    private final Inlet<T2> in2;
    private final Inlet<T3> in3;

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new FanInShape4<T0, T1, T2, T3, O>(init2);
    }

    @Override
    public FanInShape4<T0, T1, T2, T3, O> deepCopy() {
        return (FanInShape4)super.deepCopy();
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

    public FanInShape4(FanInShape.Init<O> _init) {
        super(_init);
        this.in0 = this.newInlet("in0");
        this.in1 = this.newInlet("in1");
        this.in2 = this.newInlet("in2");
        this.in3 = this.newInlet("in3");
    }

    public FanInShape4(String name) {
        this(new FanInShape.Name(name));
    }

    public FanInShape4(Inlet<T0> in0, Inlet<T1> in1, Inlet<T2> in2, Inlet<T3> in3, Outlet<O> out) {
        Inlet<T0> inlet = in0;
        Inlet<T1> inlet2 = in1;
        Inlet<T2> inlet3 = in2;
        Inlet<T3> inlet4 = in3;
        this(new FanInShape.Ports<O>(out, Nil$.MODULE$.$colon$colon(inlet4).$colon$colon(inlet3).$colon$colon(inlet2).$colon$colon(inlet)));
    }
}

