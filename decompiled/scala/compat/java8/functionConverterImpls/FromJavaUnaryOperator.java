/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.UnaryOperator;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y2A\u0001B\u0003\u0001\u001d!A!\u0005\u0001B\u0001B\u0003%1\u0005C\u0003.\u0001\u0011\u0005a\u0006C\u00033\u0001\u0011\u00051GA\u000bGe>l'*\u0019<b+:\f'/_(qKJ\fGo\u001c:\u000b\u0005\u00199\u0011A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005!I\u0011!\u00026bm\u0006D$B\u0001\u0006\f\u0003\u0019\u0019w.\u001c9bi*\tA\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005=I2c\u0001\u0001\u0011)A\u0011\u0011CE\u0007\u0002\u0017%\u00111c\u0003\u0002\u0007\u0003:L(+\u001a4\u0011\tE)rcF\u0005\u0003--\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0005aIB\u0002\u0001\u0003\u00065\u0001\u0011\ra\u0007\u0002\u0002)F\u0011Ad\b\t\u0003#uI!AH\u0006\u0003\u000f9{G\u000f[5oOB\u0011\u0011\u0003I\u0005\u0003C-\u00111!\u00118z\u0003\tQg\rE\u0002%W]i\u0011!\n\u0006\u0003M\u001d\n\u0001BZ;oGRLwN\u001c\u0006\u0003Q%\nA!\u001e;jY*\t!&\u0001\u0003kCZ\f\u0017B\u0001\u0017&\u00055)f.\u0019:z\u001fB,'/\u0019;pe\u00061A(\u001b8jiz\"\"aL\u0019\u0011\u0007A\u0002q#D\u0001\u0006\u0011\u0015\u0011#\u00011\u0001$\u0003\u0015\t\u0007\u000f\u001d7z)\t9B\u0007C\u00036\u0007\u0001\u0007q#\u0001\u0002yc\u0001")
public class FromJavaUnaryOperator<T>
implements Function1<T, T> {
    private final UnaryOperator<T> jf;

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
    public <A> Function1<A, T> compose(Function1<A, T> g2) {
        return Function1.compose$(this, g2);
    }

    @Override
    public <A> Function1<T, A> andThen(Function1<T, A> g2) {
        return Function1.andThen$(this, g2);
    }

    @Override
    public String toString() {
        return Function1.toString$(this);
    }

    @Override
    public T apply(T x1) {
        return (T)this.jf.apply(x1);
    }

    public FromJavaUnaryOperator(UnaryOperator<T> jf) {
        this.jf = jf;
        Function1.$init$(this);
    }
}

