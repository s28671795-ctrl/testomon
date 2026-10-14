/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongFunction;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e2A\u0001B\u0003\u0001\u001d!AQ\u0005\u0001B\u0001B\u0003%a\u0005C\u00031\u0001\u0011\u0005\u0011\u0007C\u00036\u0001\u0011\u0005aG\u0001\u000bGe>l'*\u0019<b\u0019>twMR;oGRLwN\u001c\u0006\u0003\r\u001d\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003\u0011%\tQA[1wCbR!AC\u0006\u0002\r\r|W\u000e]1u\u0015\u0005a\u0011!B:dC2\f7\u0001A\u000b\u0003\u001fq\u00192\u0001\u0001\t\u0015!\t\t\"#D\u0001\f\u0013\t\u00192B\u0001\u0004B]f\u0014VM\u001a\t\u0005#U9\"$\u0003\u0002\u0017\u0017\tIa)\u001e8di&|g.\r\t\u0003#aI!!G\u0006\u0003\t1{gn\u001a\t\u00037qa\u0001\u0001B\u0003\u001e\u0001\t\u0007aDA\u0001S#\ty\"\u0005\u0005\u0002\u0012A%\u0011\u0011e\u0003\u0002\b\u001d>$\b.\u001b8h!\t\t2%\u0003\u0002%\u0017\t\u0019\u0011I\\=\u0002\u0005)4\u0007cA\u0014/55\t\u0001F\u0003\u0002*U\u0005Aa-\u001e8di&|gN\u0003\u0002,Y\u0005!Q\u000f^5m\u0015\u0005i\u0013\u0001\u00026bm\u0006L!a\f\u0015\u0003\u00191{gn\u001a$v]\u000e$\u0018n\u001c8\u0002\rqJg.\u001b;?)\t\u0011D\u0007E\u00024\u0001ii\u0011!\u0002\u0005\u0006K\t\u0001\rAJ\u0001\u0006CB\u0004H.\u001f\u000b\u00035]BQ\u0001O\u0002A\u0002]\t!\u0001_\u0019")
public class FromJavaLongFunction<R>
implements Function1<Object, R> {
    private final LongFunction<R> jf;

    @Override
    public boolean apply$mcZD$sp(double v1) {
        return Function1.apply$mcZD$sp$(this, v1);
    }

    @Override
    public double apply$mcDD$sp(double v1) {
        return Function1.apply$mcDD$sp$(this, v1);
    }

    @Override
    public float apply$mcFD$sp(double v1) {
        return Function1.apply$mcFD$sp$(this, v1);
    }

    @Override
    public int apply$mcID$sp(double v1) {
        return Function1.apply$mcID$sp$(this, v1);
    }

    @Override
    public long apply$mcJD$sp(double v1) {
        return Function1.apply$mcJD$sp$(this, v1);
    }

    @Override
    public void apply$mcVD$sp(double v1) {
        Function1.apply$mcVD$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZF$sp(float v1) {
        return Function1.apply$mcZF$sp$(this, v1);
    }

    @Override
    public double apply$mcDF$sp(float v1) {
        return Function1.apply$mcDF$sp$(this, v1);
    }

    @Override
    public float apply$mcFF$sp(float v1) {
        return Function1.apply$mcFF$sp$(this, v1);
    }

    @Override
    public int apply$mcIF$sp(float v1) {
        return Function1.apply$mcIF$sp$(this, v1);
    }

    @Override
    public long apply$mcJF$sp(float v1) {
        return Function1.apply$mcJF$sp$(this, v1);
    }

    @Override
    public void apply$mcVF$sp(float v1) {
        Function1.apply$mcVF$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZI$sp(int v1) {
        return Function1.apply$mcZI$sp$(this, v1);
    }

    @Override
    public double apply$mcDI$sp(int v1) {
        return Function1.apply$mcDI$sp$(this, v1);
    }

    @Override
    public float apply$mcFI$sp(int v1) {
        return Function1.apply$mcFI$sp$(this, v1);
    }

    @Override
    public int apply$mcII$sp(int v1) {
        return Function1.apply$mcII$sp$(this, v1);
    }

    @Override
    public long apply$mcJI$sp(int v1) {
        return Function1.apply$mcJI$sp$(this, v1);
    }

    @Override
    public void apply$mcVI$sp(int v1) {
        Function1.apply$mcVI$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZJ$sp(long v1) {
        return Function1.apply$mcZJ$sp$(this, v1);
    }

    @Override
    public double apply$mcDJ$sp(long v1) {
        return Function1.apply$mcDJ$sp$(this, v1);
    }

    @Override
    public float apply$mcFJ$sp(long v1) {
        return Function1.apply$mcFJ$sp$(this, v1);
    }

    @Override
    public int apply$mcIJ$sp(long v1) {
        return Function1.apply$mcIJ$sp$(this, v1);
    }

    @Override
    public long apply$mcJJ$sp(long v1) {
        return Function1.apply$mcJJ$sp$(this, v1);
    }

    @Override
    public void apply$mcVJ$sp(long v1) {
        Function1.apply$mcVJ$sp$(this, v1);
    }

    @Override
    public <A> Function1<A, R> compose(Function1<A, Object> g2) {
        return Function1.compose$(this, g2);
    }

    @Override
    public <A> Function1<Object, A> andThen(Function1<R, A> g2) {
        return Function1.andThen$(this, g2);
    }

    @Override
    public String toString() {
        return Function1.toString$(this);
    }

    @Override
    public R apply(long x1) {
        return this.jf.apply(x1);
    }

    public FromJavaLongFunction(LongFunction<R> jf) {
        this.jf = jf;
        Function1.$init$(this);
    }
}

