/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Function;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e2A\u0001B\u0003\u0001\u001d!AQ\u0005\u0001B\u0001B\u0003%a\u0005C\u00031\u0001\u0011\u0005\u0011\u0007C\u00036\u0001\u0011\u0005aG\u0001\tGe>l'*\u0019<b\rVt7\r^5p]*\u0011aaB\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0001\"C\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003\u0015-\taaY8na\u0006$(\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0019q\"G\u0012\u0014\u0007\u0001\u0001B\u0003\u0005\u0002\u0012%5\t1\"\u0003\u0002\u0014\u0017\t1\u0011I\\=SK\u001a\u0004B!E\u000b\u0018E%\u0011ac\u0003\u0002\n\rVt7\r^5p]F\u0002\"\u0001G\r\r\u0001\u0011)!\u0004\u0001b\u00017\t\tA+\u0005\u0002\u001d?A\u0011\u0011#H\u0005\u0003=-\u0011qAT8uQ&tw\r\u0005\u0002\u0012A%\u0011\u0011e\u0003\u0002\u0004\u0003:L\bC\u0001\r$\t\u0015!\u0003A1\u0001\u001c\u0005\u0005\u0011\u0016A\u00016g!\u00119cf\u0006\u0012\u000e\u0003!R!!\u000b\u0016\u0002\u0011\u0019,hn\u0019;j_:T!a\u000b\u0017\u0002\tU$\u0018\u000e\u001c\u0006\u0002[\u0005!!.\u0019<b\u0013\ty\u0003F\u0001\u0005Gk:\u001cG/[8o\u0003\u0019a\u0014N\\5u}Q\u0011!\u0007\u000e\t\u0005g\u00019\"%D\u0001\u0006\u0011\u0015)#\u00011\u0001'\u0003\u0015\t\u0007\u000f\u001d7z)\t\u0011s\u0007C\u00039\u0007\u0001\u0007q#\u0001\u0002yc\u0001")
public class FromJavaFunction<T, R>
implements Function1<T, R> {
    private final Function<T, R> jf;

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
    public <A> Function1<A, R> compose(Function1<A, T> g2) {
        return Function1.compose$(this, g2);
    }

    @Override
    public <A> Function1<T, A> andThen(Function1<R, A> g2) {
        return Function1.andThen$(this, g2);
    }

    @Override
    public String toString() {
        return Function1.toString$(this);
    }

    @Override
    public R apply(T x1) {
        return this.jf.apply(x1);
    }

    public FromJavaFunction(Function<T, R> jf) {
        this.jf = jf;
        Function1.$init$(this);
    }
}

