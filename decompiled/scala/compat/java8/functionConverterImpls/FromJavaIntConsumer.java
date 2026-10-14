/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntConsumer;
import scala.Function1;
import scala.Function1$mcVI$sp;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005A2A\u0001B\u0003\u0001\u001d!AA\u0004\u0001B\u0001B\u0003%Q\u0004C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QFA\nGe>l'*\u0019<b\u0013:$8i\u001c8tk6,'O\u0003\u0002\u0007\u000f\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\t\u0013\u0005)!.\u0019<bq)\u0011!bC\u0001\u0007G>l\u0007/\u0019;\u000b\u00031\tQa]2bY\u0006\u001c\u0001aE\u0002\u0001\u001fM\u0001\"\u0001E\t\u000e\u0003-I!AE\u0006\u0003\r\u0005s\u0017PU3g!\u0011\u0001BCF\r\n\u0005UY!!\u0003$v]\u000e$\u0018n\u001c82!\t\u0001r#\u0003\u0002\u0019\u0017\t\u0019\u0011J\u001c;\u0011\u0005AQ\u0012BA\u000e\f\u0005\u0011)f.\u001b;\u0002\u0005)4\u0007C\u0001\u0010&\u001b\u0005y\"B\u0001\u0011\"\u0003!1WO\\2uS>t'B\u0001\u0012$\u0003\u0011)H/\u001b7\u000b\u0003\u0011\nAA[1wC&\u0011ae\b\u0002\f\u0013:$8i\u001c8tk6,'/\u0001\u0004=S:LGO\u0010\u000b\u0003S-\u0002\"A\u000b\u0001\u000e\u0003\u0015AQ\u0001\b\u0002A\u0002u\tQ!\u00199qYf$\"!\u0007\u0018\t\u000b=\u001a\u0001\u0019\u0001\f\u0002\u0005a\f\u0004")
public class FromJavaIntConsumer
implements Function1$mcVI$sp {
    public final IntConsumer scala$compat$java8$functionConverterImpls$FromJavaIntConsumer$$jf;

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
    public void apply(int x1) {
        this.apply$mcVI$sp(x1);
    }

    @Override
    public void apply$mcVI$sp(int x1) {
        this.scala$compat$java8$functionConverterImpls$FromJavaIntConsumer$$jf.accept(x1);
    }

    public FromJavaIntConsumer(IntConsumer jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaIntConsumer$$jf = jf;
        Function1.$init$(this);
    }
}

