/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoublePredicate;
import scala.Function1;
import scala.Function1$mcZD$sp;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A2A\u0001B\u0003\u0001\u001d!AA\u0004\u0001B\u0001B\u0003%Q\u0004C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QFA\fGe>l'*\u0019<b\t>,(\r\\3Qe\u0016$\u0017nY1uK*\u0011aaB\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0001\"C\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003\u0015-\taaY8na\u0006$(\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0019\u0001aD\n\u0011\u0005A\tR\"A\u0006\n\u0005IY!AB!osJ+g\r\u0005\u0003\u0011)YI\u0012BA\u000b\f\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002\u0011/%\u0011\u0001d\u0003\u0002\u0007\t>,(\r\\3\u0011\u0005AQ\u0012BA\u000e\f\u0005\u001d\u0011un\u001c7fC:\f!A\u001b4\u0011\u0005y)S\"A\u0010\u000b\u0005\u0001\n\u0013\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005\t\u001a\u0013\u0001B;uS2T\u0011\u0001J\u0001\u0005U\u00064\u0018-\u0003\u0002'?\tyAi\\;cY\u0016\u0004&/\u001a3jG\u0006$X-\u0001\u0004=S:LGO\u0010\u000b\u0003S-\u0002\"A\u000b\u0001\u000e\u0003\u0015AQ\u0001\b\u0002A\u0002u\tQ!\u00199qYf$\"!\u0007\u0018\t\u000b=\u001a\u0001\u0019\u0001\f\u0002\u0005a\f\u0004")
public class FromJavaDoublePredicate
implements Function1$mcZD$sp {
    public final DoublePredicate scala$compat$java8$functionConverterImpls$FromJavaDoublePredicate$$jf;

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
    public boolean apply(double x1) {
        return this.apply$mcZD$sp(x1);
    }

    @Override
    public boolean apply$mcZD$sp(double x1) {
        return this.scala$compat$java8$functionConverterImpls$FromJavaDoublePredicate$$jf.test(x1);
    }

    public FromJavaDoublePredicate(DoublePredicate jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaDoublePredicate$$jf = jf;
        Function1.$init$(this);
    }
}

