/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntUnaryOperator;
import scala.Function1;
import scala.Function1$mcII$sp;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000552A\u0001B\u0003\u0001\u001d!A\u0011\u0004\u0001B\u0001B\u0003%!\u0004C\u0003%\u0001\u0011\u0005Q\u0005C\u0003*\u0001\u0011\u0005!F\u0001\rGe>l'*\u0019<b\u0013:$XK\\1ss>\u0003XM]1u_JT!AB\u0004\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!\u0001C\u0005\u0002\u000b)\fg/\u0019\u001d\u000b\u0005)Y\u0011AB2p[B\fGOC\u0001\r\u0003\u0015\u00198-\u00197b\u0007\u0001\u00192\u0001A\b\u0014!\t\u0001\u0012#D\u0001\f\u0013\t\u00112B\u0001\u0004B]f\u0014VM\u001a\t\u0005!Q1b#\u0003\u0002\u0016\u0017\tIa)\u001e8di&|g.\r\t\u0003!]I!\u0001G\u0006\u0003\u0007%sG/\u0001\u0002kMB\u00111DI\u0007\u00029)\u0011QDH\u0001\tMVt7\r^5p]*\u0011q\u0004I\u0001\u0005kRLGNC\u0001\"\u0003\u0011Q\u0017M^1\n\u0005\rb\"\u0001E%oiVs\u0017M]=Pa\u0016\u0014\u0018\r^8s\u0003\u0019a\u0014N\\5u}Q\u0011a\u0005\u000b\t\u0003O\u0001i\u0011!\u0002\u0005\u00063\t\u0001\rAG\u0001\u0006CB\u0004H.\u001f\u000b\u0003--BQ\u0001L\u0002A\u0002Y\t!\u0001_\u0019")
public class FromJavaIntUnaryOperator
implements Function1$mcII$sp {
    public final IntUnaryOperator scala$compat$java8$functionConverterImpls$FromJavaIntUnaryOperator$$jf;

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
    public int apply(int x1) {
        return this.apply$mcII$sp(x1);
    }

    @Override
    public int apply$mcII$sp(int x1) {
        return this.scala$compat$java8$functionConverterImpls$FromJavaIntUnaryOperator$$jf.applyAsInt(x1);
    }

    public FromJavaIntUnaryOperator(IntUnaryOperator jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaIntUnaryOperator$$jf = jf;
        Function1.$init$(this);
    }
}

