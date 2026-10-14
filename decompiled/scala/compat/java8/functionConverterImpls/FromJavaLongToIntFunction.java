/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongToIntFunction;
import scala.Function1;
import scala.Function1$mcIJ$sp;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A2A\u0001B\u0003\u0001\u001d!AA\u0004\u0001B\u0001B\u0003%Q\u0004C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QFA\rGe>l'*\u0019<b\u0019>tw\rV8J]R4UO\\2uS>t'B\u0001\u0004\b\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(B\u0001\u0005\n\u0003\u0015Q\u0017M^19\u0015\tQ1\"\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002\u0019\u0005)1oY1mC\u000e\u00011c\u0001\u0001\u0010'A\u0011\u0001#E\u0007\u0002\u0017%\u0011!c\u0003\u0002\u0007\u0003:L(+\u001a4\u0011\tA!b#G\u0005\u0003+-\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0005A9\u0012B\u0001\r\f\u0005\u0011auN\\4\u0011\u0005AQ\u0012BA\u000e\f\u0005\rIe\u000e^\u0001\u0003U\u001a\u0004\"AH\u0013\u000e\u0003}Q!\u0001I\u0011\u0002\u0011\u0019,hn\u0019;j_:T!AI\u0012\u0002\tU$\u0018\u000e\u001c\u0006\u0002I\u0005!!.\u0019<b\u0013\t1sDA\tM_:<Gk\\%oi\u001a+hn\u0019;j_:\fa\u0001P5oSRtDCA\u0015,!\tQ\u0003!D\u0001\u0006\u0011\u0015a\"\u00011\u0001\u001e\u0003\u0015\t\u0007\u000f\u001d7z)\tIb\u0006C\u00030\u0007\u0001\u0007a#\u0001\u0002yc\u0001")
public class FromJavaLongToIntFunction
implements Function1$mcIJ$sp {
    public final LongToIntFunction scala$compat$java8$functionConverterImpls$FromJavaLongToIntFunction$$jf;

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
    public int apply(long x1) {
        return this.apply$mcIJ$sp(x1);
    }

    @Override
    public int apply$mcIJ$sp(long x1) {
        return this.scala$compat$java8$functionConverterImpls$FromJavaLongToIntFunction$$jf.applyAsInt(x1);
    }

    public FromJavaLongToIntFunction(LongToIntFunction jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaLongToIntFunction$$jf = jf;
        Function1.$init$(this);
    }
}

