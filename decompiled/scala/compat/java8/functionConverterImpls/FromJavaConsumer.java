/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Consumer;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005e2A\u0001B\u0003\u0001\u001d!AQ\u0005\u0001B\u0001B\u0003%a\u0005C\u00031\u0001\u0011\u0005\u0011\u0007C\u00036\u0001\u0011\u0005aG\u0001\tGe>l'*\u0019<b\u0007>t7/^7fe*\u0011aaB\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0001\"C\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003\u0015-\taaY8na\u0006$(\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011q\"G\n\u0004\u0001A!\u0002CA\t\u0013\u001b\u0005Y\u0011BA\n\f\u0005\u0019\te.\u001f*fMB!\u0011#F\f#\u0013\t12BA\u0005Gk:\u001cG/[8ocA\u0011\u0001$\u0007\u0007\u0001\t\u0015Q\u0002A1\u0001\u001c\u0005\u0005!\u0016C\u0001\u000f !\t\tR$\u0003\u0002\u001f\u0017\t9aj\u001c;iS:<\u0007CA\t!\u0013\t\t3BA\u0002B]f\u0004\"!E\u0012\n\u0005\u0011Z!\u0001B+oSR\f!A\u001b4\u0011\u0007\u001drs#D\u0001)\u0015\tI#&\u0001\u0005gk:\u001cG/[8o\u0015\tYC&\u0001\u0003vi&d'\"A\u0017\u0002\t)\fg/Y\u0005\u0003_!\u0012\u0001bQ8ogVlWM]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005I\"\u0004cA\u001a\u0001/5\tQ\u0001C\u0003&\u0005\u0001\u0007a%A\u0003baBd\u0017\u0010\u0006\u0002#o!)\u0001h\u0001a\u0001/\u0005\u0011\u00010\r")
public class FromJavaConsumer<T>
implements Function1<T, BoxedUnit> {
    private final Consumer<T> jf;

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
    public <A> Function1<A, BoxedUnit> compose(Function1<A, T> g2) {
        return Function1.compose$(this, g2);
    }

    @Override
    public <A> Function1<T, A> andThen(Function1<BoxedUnit, A> g2) {
        return Function1.andThen$(this, g2);
    }

    @Override
    public String toString() {
        return Function1.toString$(this);
    }

    @Override
    public void apply(T x1) {
        this.jf.accept(x1);
    }

    public FromJavaConsumer(Consumer<T> jf) {
        this.jf = jf;
        Function1.$init$(this);
    }
}

