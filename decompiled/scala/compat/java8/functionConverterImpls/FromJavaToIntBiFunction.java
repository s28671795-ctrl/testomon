/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToIntBiFunction;
import scala.Function1;
import scala.Function2;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y2A\u0001B\u0003\u0001\u001d!A\u0001\u0006\u0001B\u0001B\u0003%\u0011\u0006C\u00034\u0001\u0011\u0005A\u0007C\u00039\u0001\u0011\u0005\u0011HA\fGe>l'*\u0019<b)>Le\u000e\u001e\"j\rVt7\r^5p]*\u0011aaB\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0001\"C\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003\u0015-\taaY8na\u0006$(\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0019q\"G\u0012\u0014\u0007\u0001\u0001B\u0003\u0005\u0002\u0012%5\t1\"\u0003\u0002\u0014\u0017\t1\u0011I\\=SK\u001a\u0004R!E\u000b\u0018E\u0015J!AF\u0006\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004C\u0001\r\u001a\u0019\u0001!QA\u0007\u0001C\u0002m\u0011\u0011\u0001V\t\u00039}\u0001\"!E\u000f\n\u0005yY!a\u0002(pi\"Lgn\u001a\t\u0003#\u0001J!!I\u0006\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u0019G\u0011)A\u0005\u0001b\u00017\t\tQ\u000b\u0005\u0002\u0012M%\u0011qe\u0003\u0002\u0004\u0013:$\u0018A\u00016g!\u0011Q\u0013g\u0006\u0012\u000e\u0003-R!\u0001L\u0017\u0002\u0011\u0019,hn\u0019;j_:T!AL\u0018\u0002\tU$\u0018\u000e\u001c\u0006\u0002a\u0005!!.\u0019<b\u0013\t\u00114FA\bU_&sGOQ5Gk:\u001cG/[8o\u0003\u0019a\u0014N\\5u}Q\u0011Qg\u000e\t\u0005m\u00019\"%D\u0001\u0006\u0011\u0015A#\u00011\u0001*\u0003\u0015\t\u0007\u000f\u001d7z)\r)#\b\u0010\u0005\u0006w\r\u0001\raF\u0001\u0003qFBQ!P\u0002A\u0002\t\n!\u0001\u001f\u001a")
public class FromJavaToIntBiFunction<T, U>
implements Function2<T, U, Object> {
    private final ToIntBiFunction<T, U> jf;

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
    public Function1<T, Function1<U, Object>> curried() {
        return Function2.curried$(this);
    }

    @Override
    public Function1<Tuple2<T, U>, Object> tupled() {
        return Function2.tupled$(this);
    }

    @Override
    public String toString() {
        return Function2.toString$(this);
    }

    @Override
    public int apply(T x1, U x2) {
        return this.jf.applyAsInt(x1, x2);
    }

    public FromJavaToIntBiFunction(ToIntBiFunction<T, U> jf) {
        this.jf = jf;
        Function2.$init$(this);
    }
}

