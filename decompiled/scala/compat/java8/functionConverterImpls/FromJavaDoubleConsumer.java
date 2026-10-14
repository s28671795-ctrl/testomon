/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleConsumer;
import scala.Function1;
import scala.Function1$mcVD$sp;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005A2A\u0001B\u0003\u0001\u001d!AA\u0004\u0001B\u0001B\u0003%Q\u0004C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QF\u0001\fGe>l'*\u0019<b\t>,(\r\\3D_:\u001cX/\\3s\u0015\t1q!\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\tA\u0011\"A\u0003kCZ\f\u0007H\u0003\u0002\u000b\u0017\u000511m\\7qCRT\u0011\u0001D\u0001\u0006g\u000e\fG.Y\u0002\u0001'\r\u0001qb\u0005\t\u0003!Ei\u0011aC\u0005\u0003%-\u0011a!\u00118z%\u00164\u0007\u0003\u0002\t\u0015-eI!!F\u0006\u0003\u0013\u0019+hn\u0019;j_:\f\u0004C\u0001\t\u0018\u0013\tA2B\u0001\u0004E_V\u0014G.\u001a\t\u0003!iI!aG\u0006\u0003\tUs\u0017\u000e^\u0001\u0003U\u001a\u0004\"AH\u0013\u000e\u0003}Q!\u0001I\u0011\u0002\u0011\u0019,hn\u0019;j_:T!AI\u0012\u0002\tU$\u0018\u000e\u001c\u0006\u0002I\u0005!!.\u0019<b\u0013\t1sD\u0001\bE_V\u0014G.Z\"p]N,X.\u001a:\u0002\rqJg.\u001b;?)\tI3\u0006\u0005\u0002+\u00015\tQ\u0001C\u0003\u001d\u0005\u0001\u0007Q$A\u0003baBd\u0017\u0010\u0006\u0002\u001a]!)qf\u0001a\u0001-\u0005\u0011\u00010\r")
public class FromJavaDoubleConsumer
implements Function1$mcVD$sp {
    public final DoubleConsumer scala$compat$java8$functionConverterImpls$FromJavaDoubleConsumer$$jf;

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
    public <A> Function1<A, BoxedUnit> compose(Function1<A, Object> g2) {
        return Function1.compose$(this, g2);
    }

    @Override
    public <A> Function1<Object, A> andThen(Function1<BoxedUnit, A> g2) {
        return Function1.andThen$(this, g2);
    }

    @Override
    public String toString() {
        return Function1.toString$(this);
    }

    @Override
    public void apply(double x1) {
        this.apply$mcVD$sp(x1);
    }

    @Override
    public void apply$mcVD$sp(double x1) {
        this.scala$compat$java8$functionConverterImpls$FromJavaDoubleConsumer$$jf.accept(x1);
    }

    public FromJavaDoubleConsumer(DoubleConsumer jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaDoubleConsumer$$jf = jf;
        Function1.$init$(this);
    }
}

