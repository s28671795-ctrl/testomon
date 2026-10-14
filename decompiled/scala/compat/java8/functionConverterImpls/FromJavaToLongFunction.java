/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToLongFunction;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e2A\u0001B\u0003\u0001\u001d!AQ\u0005\u0001B\u0001B\u0003%a\u0005C\u00031\u0001\u0011\u0005\u0011\u0007C\u00036\u0001\u0011\u0005aG\u0001\fGe>l'*\u0019<b)>duN\\4Gk:\u001cG/[8o\u0015\t1q!\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\tA\u0011\"A\u0003kCZ\f\u0007H\u0003\u0002\u000b\u0017\u000511m\\7qCRT\u0011\u0001D\u0001\u0006g\u000e\fG.Y\u0002\u0001+\ty\u0011dE\u0002\u0001!Q\u0001\"!\u0005\n\u000e\u0003-I!aE\u0006\u0003\r\u0005s\u0017PU3g!\u0011\tRc\u0006\u0012\n\u0005YY!!\u0003$v]\u000e$\u0018n\u001c82!\tA\u0012\u0004\u0004\u0001\u0005\u000bi\u0001!\u0019A\u000e\u0003\u0003Q\u000b\"\u0001H\u0010\u0011\u0005Ei\u0012B\u0001\u0010\f\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!\u0005\u0011\n\u0005\u0005Z!aA!osB\u0011\u0011cI\u0005\u0003I-\u0011A\u0001T8oO\u0006\u0011!N\u001a\t\u0004O9:R\"\u0001\u0015\u000b\u0005%R\u0013\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005-b\u0013\u0001B;uS2T\u0011!L\u0001\u0005U\u00064\u0018-\u0003\u00020Q\tqAk\u001c'p]\u001e4UO\\2uS>t\u0017A\u0002\u001fj]&$h\b\u0006\u00023iA\u00191\u0007A\f\u000e\u0003\u0015AQ!\n\u0002A\u0002\u0019\nQ!\u00199qYf$\"AI\u001c\t\u000ba\u001a\u0001\u0019A\f\u0002\u0005a\f\u0004")
public class FromJavaToLongFunction<T>
implements Function1<T, Object> {
    private final ToLongFunction<T> jf;

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
    public <A> Function1<A, Object> compose(Function1<A, T> g2) {
        return Function1.compose$(this, g2);
    }

    @Override
    public <A> Function1<T, A> andThen(Function1<Object, A> g2) {
        return Function1.andThen$(this, g2);
    }

    @Override
    public String toString() {
        return Function1.toString$(this);
    }

    @Override
    public long apply(T x1) {
        return this.jf.applyAsLong(x1);
    }

    public FromJavaToLongFunction(ToLongFunction<T> jf) {
        this.jf = jf;
        Function1.$init$(this);
    }
}

