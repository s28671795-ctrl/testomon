/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Predicate;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e2A\u0001B\u0003\u0001\u001d!AQ\u0005\u0001B\u0001B\u0003%a\u0005C\u00031\u0001\u0011\u0005\u0011\u0007C\u00036\u0001\u0011\u0005aGA\tGe>l'*\u0019<b!J,G-[2bi\u0016T!AB\u0004\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!\u0001C\u0005\u0002\u000b)\fg/\u0019\u001d\u000b\u0005)Y\u0011AB2p[B\fGOC\u0001\r\u0003\u0015\u00198-\u00197b\u0007\u0001)\"aD\r\u0014\u0007\u0001\u0001B\u0003\u0005\u0002\u0012%5\t1\"\u0003\u0002\u0014\u0017\t1\u0011I\\=SK\u001a\u0004B!E\u000b\u0018E%\u0011ac\u0003\u0002\n\rVt7\r^5p]F\u0002\"\u0001G\r\r\u0001\u0011)!\u0004\u0001b\u00017\t\tA+\u0005\u0002\u001d?A\u0011\u0011#H\u0005\u0003=-\u0011qAT8uQ&tw\r\u0005\u0002\u0012A%\u0011\u0011e\u0003\u0002\u0004\u0003:L\bCA\t$\u0013\t!3BA\u0004C_>dW-\u00198\u0002\u0005)4\u0007cA\u0014//5\t\u0001F\u0003\u0002*U\u0005Aa-\u001e8di&|gN\u0003\u0002,Y\u0005!Q\u000f^5m\u0015\u0005i\u0013\u0001\u00026bm\u0006L!a\f\u0015\u0003\u0013A\u0013X\rZ5dCR,\u0017A\u0002\u001fj]&$h\b\u0006\u00023iA\u00191\u0007A\f\u000e\u0003\u0015AQ!\n\u0002A\u0002\u0019\nQ!\u00199qYf$\"AI\u001c\t\u000ba\u001a\u0001\u0019A\f\u0002\u0005a\f\u0004")
public class FromJavaPredicate<T>
implements Function1<T, Object> {
    private final Predicate<T> jf;

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
    public boolean apply(T x1) {
        return this.jf.test(x1);
    }

    public FromJavaPredicate(Predicate<T> jf) {
        this.jf = jf;
        Function1.$init$(this);
    }
}

