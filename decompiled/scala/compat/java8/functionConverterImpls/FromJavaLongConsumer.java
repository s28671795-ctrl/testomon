/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongConsumer;
import scala.Function1;
import scala.Function1$mcVJ$sp;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005A2A\u0001B\u0003\u0001\u001d!AA\u0004\u0001B\u0001B\u0003%Q\u0004C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QF\u0001\u000bGe>l'*\u0019<b\u0019>twmQ8ogVlWM\u001d\u0006\u0003\r\u001d\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003\u0011%\tQA[1wCbR!AC\u0006\u0002\r\r|W\u000e]1u\u0015\u0005a\u0011!B:dC2\f7\u0001A\n\u0004\u0001=\u0019\u0002C\u0001\t\u0012\u001b\u0005Y\u0011B\u0001\n\f\u0005\u0019\te.\u001f*fMB!\u0001\u0003\u0006\f\u001a\u0013\t)2BA\u0005Gk:\u001cG/[8ocA\u0011\u0001cF\u0005\u00031-\u0011A\u0001T8oOB\u0011\u0001CG\u0005\u00037-\u0011A!\u00168ji\u0006\u0011!N\u001a\t\u0003=\u0015j\u0011a\b\u0006\u0003A\u0005\n\u0001BZ;oGRLwN\u001c\u0006\u0003E\r\nA!\u001e;jY*\tA%\u0001\u0003kCZ\f\u0017B\u0001\u0014 \u00051auN\\4D_:\u001cX/\\3s\u0003\u0019a\u0014N\\5u}Q\u0011\u0011f\u000b\t\u0003U\u0001i\u0011!\u0002\u0005\u00069\t\u0001\r!H\u0001\u0006CB\u0004H.\u001f\u000b\u000339BQaL\u0002A\u0002Y\t!\u0001_\u0019")
public class FromJavaLongConsumer
implements Function1$mcVJ$sp {
    public final LongConsumer scala$compat$java8$functionConverterImpls$FromJavaLongConsumer$$jf;

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
    public void apply(long x1) {
        this.apply$mcVJ$sp(x1);
    }

    @Override
    public void apply$mcVJ$sp(long x1) {
        this.scala$compat$java8$functionConverterImpls$FromJavaLongConsumer$$jf.accept(x1);
    }

    public FromJavaLongConsumer(LongConsumer jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaLongConsumer$$jf = jf;
        Function1.$init$(this);
    }
}

