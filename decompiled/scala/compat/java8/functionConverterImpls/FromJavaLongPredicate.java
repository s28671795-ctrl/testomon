/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongPredicate;
import scala.Function1;
import scala.Function1$mcZJ$sp;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A2A\u0001B\u0003\u0001\u001d!AA\u0004\u0001B\u0001B\u0003%Q\u0004C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QFA\u000bGe>l'*\u0019<b\u0019>tw\r\u0015:fI&\u001c\u0017\r^3\u000b\u0005\u00199\u0011A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005!I\u0011!\u00026bm\u0006D$B\u0001\u0006\f\u0003\u0019\u0019w.\u001c9bi*\tA\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001y1\u0003\u0005\u0002\u0011#5\t1\"\u0003\u0002\u0013\u0017\t1\u0011I\\=SK\u001a\u0004B\u0001\u0005\u000b\u00173%\u0011Qc\u0003\u0002\n\rVt7\r^5p]F\u0002\"\u0001E\f\n\u0005aY!\u0001\u0002'p]\u001e\u0004\"\u0001\u0005\u000e\n\u0005mY!a\u0002\"p_2,\u0017M\\\u0001\u0003U\u001a\u0004\"AH\u0013\u000e\u0003}Q!\u0001I\u0011\u0002\u0011\u0019,hn\u0019;j_:T!AI\u0012\u0002\tU$\u0018\u000e\u001c\u0006\u0002I\u0005!!.\u0019<b\u0013\t1sDA\u0007M_:<\u0007K]3eS\u000e\fG/Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005%Z\u0003C\u0001\u0016\u0001\u001b\u0005)\u0001\"\u0002\u000f\u0003\u0001\u0004i\u0012!B1qa2LHCA\r/\u0011\u0015y3\u00011\u0001\u0017\u0003\tA\u0018\u0007")
public class FromJavaLongPredicate
implements Function1$mcZJ$sp {
    public final LongPredicate scala$compat$java8$functionConverterImpls$FromJavaLongPredicate$$jf;

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
    public boolean apply(long x1) {
        return this.apply$mcZJ$sp(x1);
    }

    @Override
    public boolean apply$mcZJ$sp(long x1) {
        return this.scala$compat$java8$functionConverterImpls$FromJavaLongPredicate$$jf.test(x1);
    }

    public FromJavaLongPredicate(LongPredicate jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaLongPredicate$$jf = jf;
        Function1.$init$(this);
    }
}

