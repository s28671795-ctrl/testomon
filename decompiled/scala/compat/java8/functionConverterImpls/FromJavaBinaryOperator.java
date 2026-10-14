/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BinaryOperator;
import scala.Function1;
import scala.Function2;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a2A\u0001B\u0003\u0001\u001d!A!\u0005\u0001B\u0001B\u0003%1\u0005C\u0003.\u0001\u0011\u0005a\u0006C\u00033\u0001\u0011\u00051G\u0001\fGe>l'*\u0019<b\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8s\u0015\t1q!\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\tA\u0011\"A\u0003kCZ\f\u0007H\u0003\u0002\u000b\u0017\u000511m\\7qCRT\u0011\u0001D\u0001\u0006g\u000e\fG.Y\u0002\u0001+\ty\u0011dE\u0002\u0001!Q\u0001\"!\u0005\n\u000e\u0003-I!aE\u0006\u0003\r\u0005s\u0017PU3g!\u0015\tRcF\f\u0018\u0013\t12BA\u0005Gk:\u001cG/[8oeA\u0011\u0001$\u0007\u0007\u0001\t\u0015Q\u0002A1\u0001\u001c\u0005\u0005!\u0016C\u0001\u000f !\t\tR$\u0003\u0002\u001f\u0017\t9aj\u001c;iS:<\u0007CA\t!\u0013\t\t3BA\u0002B]f\f!A\u001b4\u0011\u0007\u0011Zs#D\u0001&\u0015\t1s%\u0001\u0005gk:\u001cG/[8o\u0015\tA\u0013&\u0001\u0003vi&d'\"\u0001\u0016\u0002\t)\fg/Y\u0005\u0003Y\u0015\u0012aBQ5oCJLx\n]3sCR|'/\u0001\u0004=S:LGO\u0010\u000b\u0003_E\u00022\u0001\r\u0001\u0018\u001b\u0005)\u0001\"\u0002\u0012\u0003\u0001\u0004\u0019\u0013!B1qa2LHcA\f5m!)Qg\u0001a\u0001/\u0005\u0011\u00010\r\u0005\u0006o\r\u0001\raF\u0001\u0003qJ\u0002")
public class FromJavaBinaryOperator<T>
implements Function2<T, T, T> {
    private final BinaryOperator<T> jf;

    @Override
    public boolean apply$mcZDD$sp(double v1, double v2) {
        return Function2.apply$mcZDD$sp$(this, v1, v2);
    }

    @Override
    public double apply$mcDDD$sp(double v1, double v2) {
        return Function2.apply$mcDDD$sp$(this, v1, v2);
    }

    @Override
    public float apply$mcFDD$sp(double v1, double v2) {
        return Function2.apply$mcFDD$sp$(this, v1, v2);
    }

    @Override
    public int apply$mcIDD$sp(double v1, double v2) {
        return Function2.apply$mcIDD$sp$(this, v1, v2);
    }

    @Override
    public long apply$mcJDD$sp(double v1, double v2) {
        return Function2.apply$mcJDD$sp$(this, v1, v2);
    }

    @Override
    public void apply$mcVDD$sp(double v1, double v2) {
        Function2.apply$mcVDD$sp$(this, v1, v2);
    }

    @Override
    public boolean apply$mcZDI$sp(double v1, int v2) {
        return Function2.apply$mcZDI$sp$(this, v1, v2);
    }

    @Override
    public double apply$mcDDI$sp(double v1, int v2) {
        return Function2.apply$mcDDI$sp$(this, v1, v2);
    }

    @Override
    public float apply$mcFDI$sp(double v1, int v2) {
        return Function2.apply$mcFDI$sp$(this, v1, v2);
    }

    @Override
    public int apply$mcIDI$sp(double v1, int v2) {
        return Function2.apply$mcIDI$sp$(this, v1, v2);
    }

    @Override
    public long apply$mcJDI$sp(double v1, int v2) {
        return Function2.apply$mcJDI$sp$(this, v1, v2);
    }

    @Override
    public void apply$mcVDI$sp(double v1, int v2) {
        Function2.apply$mcVDI$sp$(this, v1, v2);
    }

    @Override
    public boolean apply$mcZDJ$sp(double v1, long v2) {
        return Function2.apply$mcZDJ$sp$(this, v1, v2);
    }

    @Override
    public double apply$mcDDJ$sp(double v1, long v2) {
        return Function2.apply$mcDDJ$sp$(this, v1, v2);
    }

    @Override
    public float apply$mcFDJ$sp(double v1, long v2) {
        return Function2.apply$mcFDJ$sp$(this, v1, v2);
    }

    @Override
    public int apply$mcIDJ$sp(double v1, long v2) {
        return Function2.apply$mcIDJ$sp$(this, v1, v2);
    }

    @Override
    public long apply$mcJDJ$sp(double v1, long v2) {
        return Function2.apply$mcJDJ$sp$(this, v1, v2);
    }

    @Override
    public void apply$mcVDJ$sp(double v1, long v2) {
        Function2.apply$mcVDJ$sp$(this, v1, v2);
    }

    @Override
    public boolean apply$mcZID$sp(int v1, double v2) {
        return Function2.apply$mcZID$sp$(this, v1, v2);
    }

    @Override
    public double apply$mcDID$sp(int v1, double v2) {
        return Function2.apply$mcDID$sp$(this, v1, v2);
    }

    @Override
    public float apply$mcFID$sp(int v1, double v2) {
        return Function2.apply$mcFID$sp$(this, v1, v2);
    }

    @Override
    public int apply$mcIID$sp(int v1, double v2) {
        return Function2.apply$mcIID$sp$(this, v1, v2);
    }

    @Override
    public long apply$mcJID$sp(int v1, double v2) {
        return Function2.apply$mcJID$sp$(this, v1, v2);
    }

    @Override
    public void apply$mcVID$sp(int v1, double v2) {
        Function2.apply$mcVID$sp$(this, v1, v2);
    }

    @Override
    public boolean apply$mcZII$sp(int v1, int v2) {
        return Function2.apply$mcZII$sp$(this, v1, v2);
    }

    @Override
    public double apply$mcDII$sp(int v1, int v2) {
        return Function2.apply$mcDII$sp$(this, v1, v2);
    }

    @Override
    public float apply$mcFII$sp(int v1, int v2) {
        return Function2.apply$mcFII$sp$(this, v1, v2);
    }

    @Override
    public int apply$mcIII$sp(int v1, int v2) {
        return Function2.apply$mcIII$sp$(this, v1, v2);
    }

    @Override
    public long apply$mcJII$sp(int v1, int v2) {
        return Function2.apply$mcJII$sp$(this, v1, v2);
    }

    @Override
    public void apply$mcVII$sp(int v1, int v2) {
        Function2.apply$mcVII$sp$(this, v1, v2);
    }

    @Override
    public boolean apply$mcZIJ$sp(int v1, long v2) {
        return Function2.apply$mcZIJ$sp$(this, v1, v2);
    }

    @Override
    public double apply$mcDIJ$sp(int v1, long v2) {
        return Function2.apply$mcDIJ$sp$(this, v1, v2);
    }

    @Override
    public float apply$mcFIJ$sp(int v1, long v2) {
        return Function2.apply$mcFIJ$sp$(this, v1, v2);
    }

    @Override
    public int apply$mcIIJ$sp(int v1, long v2) {
        return Function2.apply$mcIIJ$sp$(this, v1, v2);
    }

    @Override
    public long apply$mcJIJ$sp(int v1, long v2) {
        return Function2.apply$mcJIJ$sp$(this, v1, v2);
    }

    @Override
    public void apply$mcVIJ$sp(int v1, long v2) {
        Function2.apply$mcVIJ$sp$(this, v1, v2);
    }

    @Override
    public boolean apply$mcZJD$sp(long v1, double v2) {
        return Function2.apply$mcZJD$sp$(this, v1, v2);
    }

    @Override
    public double apply$mcDJD$sp(long v1, double v2) {
        return Function2.apply$mcDJD$sp$(this, v1, v2);
    }

    @Override
    public float apply$mcFJD$sp(long v1, double v2) {
        return Function2.apply$mcFJD$sp$(this, v1, v2);
    }

    @Override
    public int apply$mcIJD$sp(long v1, double v2) {
        return Function2.apply$mcIJD$sp$(this, v1, v2);
    }

    @Override
    public long apply$mcJJD$sp(long v1, double v2) {
        return Function2.apply$mcJJD$sp$(this, v1, v2);
    }

    @Override
    public void apply$mcVJD$sp(long v1, double v2) {
        Function2.apply$mcVJD$sp$(this, v1, v2);
    }

    @Override
    public boolean apply$mcZJI$sp(long v1, int v2) {
        return Function2.apply$mcZJI$sp$(this, v1, v2);
    }

    @Override
    public double apply$mcDJI$sp(long v1, int v2) {
        return Function2.apply$mcDJI$sp$(this, v1, v2);
    }

    @Override
    public float apply$mcFJI$sp(long v1, int v2) {
        return Function2.apply$mcFJI$sp$(this, v1, v2);
    }

    @Override
    public int apply$mcIJI$sp(long v1, int v2) {
        return Function2.apply$mcIJI$sp$(this, v1, v2);
    }

    @Override
    public long apply$mcJJI$sp(long v1, int v2) {
        return Function2.apply$mcJJI$sp$(this, v1, v2);
    }

    @Override
    public void apply$mcVJI$sp(long v1, int v2) {
        Function2.apply$mcVJI$sp$(this, v1, v2);
    }

    @Override
    public boolean apply$mcZJJ$sp(long v1, long v2) {
        return Function2.apply$mcZJJ$sp$(this, v1, v2);
    }

    @Override
    public double apply$mcDJJ$sp(long v1, long v2) {
        return Function2.apply$mcDJJ$sp$(this, v1, v2);
    }

    @Override
    public float apply$mcFJJ$sp(long v1, long v2) {
        return Function2.apply$mcFJJ$sp$(this, v1, v2);
    }

    @Override
    public int apply$mcIJJ$sp(long v1, long v2) {
        return Function2.apply$mcIJJ$sp$(this, v1, v2);
    }

    @Override
    public long apply$mcJJJ$sp(long v1, long v2) {
        return Function2.apply$mcJJJ$sp$(this, v1, v2);
    }

    @Override
    public void apply$mcVJJ$sp(long v1, long v2) {
        Function2.apply$mcVJJ$sp$(this, v1, v2);
    }

    @Override
    public Function1<T, Function1<T, T>> curried() {
        return Function2.curried$(this);
    }

    @Override
    public Function1<Tuple2<T, T>, T> tupled() {
        return Function2.tupled$(this);
    }

    @Override
    public String toString() {
        return Function2.toString$(this);
    }

    @Override
    public T apply(T x1, T x2) {
        return (T)this.jf.apply(x1, x2);
    }

    public FromJavaBinaryOperator(BinaryOperator<T> jf) {
        this.jf = jf;
        Function2.$init$(this);
    }
}

