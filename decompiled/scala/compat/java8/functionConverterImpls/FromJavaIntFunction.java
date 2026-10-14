/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntFunction;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e2A\u0001B\u0003\u0001\u001d!AQ\u0005\u0001B\u0001B\u0003%a\u0005C\u00031\u0001\u0011\u0005\u0011\u0007C\u00036\u0001\u0011\u0005aGA\nGe>l'*\u0019<b\u0013:$h)\u001e8di&|gN\u0003\u0002\u0007\u000f\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\t\u0013\u0005)!.\u0019<bq)\u0011!bC\u0001\u0007G>l\u0007/\u0019;\u000b\u00031\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\u00109M\u0019\u0001\u0001\u0005\u000b\u0011\u0005E\u0011R\"A\u0006\n\u0005MY!AB!osJ+g\r\u0005\u0003\u0012+]Q\u0012B\u0001\f\f\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002\u00121%\u0011\u0011d\u0003\u0002\u0004\u0013:$\bCA\u000e\u001d\u0019\u0001!Q!\b\u0001C\u0002y\u0011\u0011AU\t\u0003?\t\u0002\"!\u0005\u0011\n\u0005\u0005Z!a\u0002(pi\"Lgn\u001a\t\u0003#\rJ!\u0001J\u0006\u0003\u0007\u0005s\u00170\u0001\u0002kMB\u0019qE\f\u000e\u000e\u0003!R!!\u000b\u0016\u0002\u0011\u0019,hn\u0019;j_:T!a\u000b\u0017\u0002\tU$\u0018\u000e\u001c\u0006\u0002[\u0005!!.\u0019<b\u0013\ty\u0003FA\u0006J]R4UO\\2uS>t\u0017A\u0002\u001fj]&$h\b\u0006\u00023iA\u00191\u0007\u0001\u000e\u000e\u0003\u0015AQ!\n\u0002A\u0002\u0019\nQ!\u00199qYf$\"AG\u001c\t\u000ba\u001a\u0001\u0019A\f\u0002\u0005a\f\u0004")
public class FromJavaIntFunction<R>
implements Function1<Object, R> {
    private final IntFunction<R> jf;

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
    public R apply(int x1) {
        return this.jf.apply(x1);
    }

    public FromJavaIntFunction(IntFunction<R> jf) {
        this.jf = jf;
        Function1.$init$(this);
    }
}

