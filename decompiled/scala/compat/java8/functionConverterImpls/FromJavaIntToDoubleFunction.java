/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntToDoubleFunction;
import scala.Function1;
import scala.Function1$mcDI$sp;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A2A\u0001B\u0003\u0001\u001d!AA\u0004\u0001B\u0001B\u0003%Q\u0004C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QFA\u000eGe>l'*\u0019<b\u0013:$Hk\u001c#pk\ndWMR;oGRLwN\u001c\u0006\u0003\r\u001d\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003\u0011%\tQA[1wCbR!AC\u0006\u0002\r\r|W\u000e]1u\u0015\u0005a\u0011!B:dC2\f7\u0001A\n\u0004\u0001=\u0019\u0002C\u0001\t\u0012\u001b\u0005Y\u0011B\u0001\n\f\u0005\u0019\te.\u001f*fMB!\u0001\u0003\u0006\f\u001a\u0013\t)2BA\u0005Gk:\u001cG/[8ocA\u0011\u0001cF\u0005\u00031-\u00111!\u00138u!\t\u0001\"$\u0003\u0002\u001c\u0017\t1Ai\\;cY\u0016\f!A\u001b4\u0011\u0005y)S\"A\u0010\u000b\u0005\u0001\n\u0013\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005\t\u001a\u0013\u0001B;uS2T\u0011\u0001J\u0001\u0005U\u00064\u0018-\u0003\u0002'?\t\u0019\u0012J\u001c;U_\u0012{WO\u00197f\rVt7\r^5p]\u00061A(\u001b8jiz\"\"!K\u0016\u0011\u0005)\u0002Q\"A\u0003\t\u000bq\u0011\u0001\u0019A\u000f\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0005eq\u0003\"B\u0018\u0004\u0001\u00041\u0012A\u0001=2\u0001")
public class FromJavaIntToDoubleFunction
implements Function1$mcDI$sp {
    public final IntToDoubleFunction scala$compat$java8$functionConverterImpls$FromJavaIntToDoubleFunction$$jf;

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
    public double apply(int x1) {
        return this.apply$mcDI$sp(x1);
    }

    @Override
    public double apply$mcDI$sp(int x1) {
        return this.scala$compat$java8$functionConverterImpls$FromJavaIntToDoubleFunction$$jf.applyAsDouble(x1);
    }

    public FromJavaIntToDoubleFunction(IntToDoubleFunction jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaIntToDoubleFunction$$jf = jf;
        Function1.$init$(this);
    }
}

