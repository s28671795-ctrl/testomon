/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleFunction;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e2A\u0001B\u0003\u0001\u001d!AQ\u0005\u0001B\u0001B\u0003%a\u0005C\u00031\u0001\u0011\u0005\u0011\u0007C\u00036\u0001\u0011\u0005aG\u0001\fGe>l'*\u0019<b\t>,(\r\\3Gk:\u001cG/[8o\u0015\t1q!\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\tA\u0011\"A\u0003kCZ\f\u0007H\u0003\u0002\u000b\u0017\u000511m\\7qCRT\u0011\u0001D\u0001\u0006g\u000e\fG.Y\u0002\u0001+\tyAdE\u0002\u0001!Q\u0001\"!\u0005\n\u000e\u0003-I!aE\u0006\u0003\r\u0005s\u0017PU3g!\u0011\tRc\u0006\u000e\n\u0005YY!!\u0003$v]\u000e$\u0018n\u001c82!\t\t\u0002$\u0003\u0002\u001a\u0017\t1Ai\\;cY\u0016\u0004\"a\u0007\u000f\r\u0001\u0011)Q\u0004\u0001b\u0001=\t\t!+\u0005\u0002 EA\u0011\u0011\u0003I\u0005\u0003C-\u0011qAT8uQ&tw\r\u0005\u0002\u0012G%\u0011Ae\u0003\u0002\u0004\u0003:L\u0018A\u00016g!\r9cFG\u0007\u0002Q)\u0011\u0011FK\u0001\tMVt7\r^5p]*\u00111\u0006L\u0001\u0005kRLGNC\u0001.\u0003\u0011Q\u0017M^1\n\u0005=B#A\u0004#pk\ndWMR;oGRLwN\\\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005I\"\u0004cA\u001a\u000155\tQ\u0001C\u0003&\u0005\u0001\u0007a%A\u0003baBd\u0017\u0010\u0006\u0002\u001bo!)\u0001h\u0001a\u0001/\u0005\u0011\u00010\r")
public class FromJavaDoubleFunction<R>
implements Function1<Object, R> {
    private final DoubleFunction<R> jf;

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
    public R apply(double x1) {
        return this.jf.apply(x1);
    }

    public FromJavaDoubleFunction(DoubleFunction<R> jf) {
        this.jf = jf;
        Function1.$init$(this);
    }
}

