/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongUnaryOperator;
import scala.Function1;
import scala.Function1$mcJJ$sp;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000552A\u0001B\u0003\u0001\u001d!A\u0011\u0004\u0001B\u0001B\u0003%!\u0004C\u0003%\u0001\u0011\u0005Q\u0005C\u0003*\u0001\u0011\u0005!FA\rGe>l'*\u0019<b\u0019>tw-\u00168bef|\u0005/\u001a:bi>\u0014(B\u0001\u0004\b\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(B\u0001\u0005\n\u0003\u0015Q\u0017M^19\u0015\tQ1\"\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002\u0019\u0005)1oY1mC\u000e\u00011c\u0001\u0001\u0010'A\u0011\u0001#E\u0007\u0002\u0017%\u0011!c\u0003\u0002\u0007\u0003:L(+\u001a4\u0011\tA!bCF\u0005\u0003+-\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0005A9\u0012B\u0001\r\f\u0005\u0011auN\\4\u0002\u0005)4\u0007CA\u000e#\u001b\u0005a\"BA\u000f\u001f\u0003!1WO\\2uS>t'BA\u0010!\u0003\u0011)H/\u001b7\u000b\u0003\u0005\nAA[1wC&\u00111\u0005\b\u0002\u0012\u0019>tw-\u00168bef|\u0005/\u001a:bi>\u0014\u0018A\u0002\u001fj]&$h\b\u0006\u0002'QA\u0011q\u0005A\u0007\u0002\u000b!)\u0011D\u0001a\u00015\u0005)\u0011\r\u001d9msR\u0011ac\u000b\u0005\u0006Y\r\u0001\rAF\u0001\u0003qF\u0002")
public class FromJavaLongUnaryOperator
implements Function1$mcJJ$sp {
    public final LongUnaryOperator scala$compat$java8$functionConverterImpls$FromJavaLongUnaryOperator$$jf;

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
    public long apply(long x1) {
        return this.apply$mcJJ$sp(x1);
    }

    @Override
    public long apply$mcJJ$sp(long x1) {
        return this.scala$compat$java8$functionConverterImpls$FromJavaLongUnaryOperator$$jf.applyAsLong(x1);
    }

    public FromJavaLongUnaryOperator(LongUnaryOperator jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaLongUnaryOperator$$jf = jf;
        Function1.$init$(this);
    }
}

