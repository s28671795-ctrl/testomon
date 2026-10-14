/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleToLongFunction;
import scala.Function1;
import scala.Function1$mcJD$sp;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A2A\u0001B\u0003\u0001\u001d!AA\u0004\u0001B\u0001B\u0003%Q\u0004C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QF\u0001\u000fGe>l'*\u0019<b\t>,(\r\\3U_2{gn\u001a$v]\u000e$\u0018n\u001c8\u000b\u0005\u00199\u0011A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005!I\u0011!\u00026bm\u0006D$B\u0001\u0006\f\u0003\u0019\u0019w.\u001c9bi*\tA\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001y1\u0003\u0005\u0002\u0011#5\t1\"\u0003\u0002\u0013\u0017\t1\u0011I\\=SK\u001a\u0004B\u0001\u0005\u000b\u00173%\u0011Qc\u0003\u0002\n\rVt7\r^5p]F\u0002\"\u0001E\f\n\u0005aY!A\u0002#pk\ndW\r\u0005\u0002\u00115%\u00111d\u0003\u0002\u0005\u0019>tw-\u0001\u0002kMB\u0011a$J\u0007\u0002?)\u0011\u0001%I\u0001\tMVt7\r^5p]*\u0011!eI\u0001\u0005kRLGNC\u0001%\u0003\u0011Q\u0017M^1\n\u0005\u0019z\"\u0001\u0006#pk\ndW\rV8M_:<g)\u001e8di&|g.\u0001\u0004=S:LGO\u0010\u000b\u0003S-\u0002\"A\u000b\u0001\u000e\u0003\u0015AQ\u0001\b\u0002A\u0002u\tQ!\u00199qYf$\"!\u0007\u0018\t\u000b=\u001a\u0001\u0019\u0001\f\u0002\u0005a\f\u0004")
public class FromJavaDoubleToLongFunction
implements Function1$mcJD$sp {
    public final DoubleToLongFunction scala$compat$java8$functionConverterImpls$FromJavaDoubleToLongFunction$$jf;

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
    public <A> Function1<A, Object> compose(Function1<A, Object> g2) {
        return Function1.compose$(this, g2);
    }

    @Override
    public <A> Function1<Object, A> andThen(Function1<Object, A> g2) {
        return Function1.andThen$(this, g2);
    }

    @Override
    public String toString() {
        return Function1.toString$(this);
    }

    @Override
    public long apply(double x1) {
        return this.apply$mcJD$sp(x1);
    }

    @Override
    public long apply$mcJD$sp(double x1) {
        return this.scala$compat$java8$functionConverterImpls$FromJavaDoubleToLongFunction$$jf.applyAsLong(x1);
    }

    public FromJavaDoubleToLongFunction(DoubleToLongFunction jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaDoubleToLongFunction$$jf = jf;
        Function1.$init$(this);
    }
}

