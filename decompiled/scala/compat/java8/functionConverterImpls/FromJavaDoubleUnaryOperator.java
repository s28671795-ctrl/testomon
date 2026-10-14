/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleUnaryOperator;
import scala.Function1;
import scala.Function1$mcDD$sp;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000552A\u0001B\u0003\u0001\u001d!A\u0011\u0004\u0001B\u0001B\u0003%!\u0004C\u0003%\u0001\u0011\u0005Q\u0005C\u0003*\u0001\u0011\u0005!FA\u000eGe>l'*\u0019<b\t>,(\r\\3V]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u0006\u0003\r\u001d\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003\u0011%\tQA[1wCbR!AC\u0006\u0002\r\r|W\u000e]1u\u0015\u0005a\u0011!B:dC2\f7\u0001A\n\u0004\u0001=\u0019\u0002C\u0001\t\u0012\u001b\u0005Y\u0011B\u0001\n\f\u0005\u0019\te.\u001f*fMB!\u0001\u0003\u0006\f\u0017\u0013\t)2BA\u0005Gk:\u001cG/[8ocA\u0011\u0001cF\u0005\u00031-\u0011a\u0001R8vE2,\u0017A\u00016g!\tY\"%D\u0001\u001d\u0015\tib$\u0001\u0005gk:\u001cG/[8o\u0015\ty\u0002%\u0001\u0003vi&d'\"A\u0011\u0002\t)\fg/Y\u0005\u0003Gq\u00111\u0003R8vE2,WK\\1ss>\u0003XM]1u_J\fa\u0001P5oSRtDC\u0001\u0014)!\t9\u0003!D\u0001\u0006\u0011\u0015I\"\u00011\u0001\u001b\u0003\u0015\t\u0007\u000f\u001d7z)\t12\u0006C\u0003-\u0007\u0001\u0007a#\u0001\u0002yc\u0001")
public class FromJavaDoubleUnaryOperator
implements Function1$mcDD$sp {
    public final DoubleUnaryOperator scala$compat$java8$functionConverterImpls$FromJavaDoubleUnaryOperator$$jf;

    @Override
    public boolean apply$mcZD$sp(double v1) {
        return Function1.apply$mcZD$sp$(this, v1);
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
    public double apply(double x1) {
        return this.apply$mcDD$sp(x1);
    }

    @Override
    public double apply$mcDD$sp(double x1) {
        return this.scala$compat$java8$functionConverterImpls$FromJavaDoubleUnaryOperator$$jf.applyAsDouble(x1);
    }

    public FromJavaDoubleUnaryOperator(DoubleUnaryOperator jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaDoubleUnaryOperator$$jf = jf;
        Function1.$init$(this);
    }
}

