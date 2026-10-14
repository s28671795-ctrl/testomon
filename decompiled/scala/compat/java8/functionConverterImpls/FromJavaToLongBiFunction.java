/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToLongBiFunction;
import scala.Function1;
import scala.Function2;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y2A\u0001B\u0003\u0001\u001d!A\u0001\u0006\u0001B\u0001B\u0003%\u0011\u0006C\u00034\u0001\u0011\u0005A\u0007C\u00039\u0001\u0011\u0005\u0011H\u0001\rGe>l'*\u0019<b)>duN\\4CS\u001a+hn\u0019;j_:T!AB\u0004\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!\u0001C\u0005\u0002\u000b)\fg/\u0019\u001d\u000b\u0005)Y\u0011AB2p[B\fGOC\u0001\r\u0003\u0015\u00198-\u00197b\u0007\u0001)2aD\r$'\r\u0001\u0001\u0003\u0006\t\u0003#Ii\u0011aC\u0005\u0003'-\u0011a!\u00118z%\u00164\u0007#B\t\u0016/\t*\u0013B\u0001\f\f\u0005%1UO\\2uS>t'\u0007\u0005\u0002\u001931\u0001A!\u0002\u000e\u0001\u0005\u0004Y\"!\u0001+\u0012\u0005qy\u0002CA\t\u001e\u0013\tq2BA\u0004O_RD\u0017N\\4\u0011\u0005E\u0001\u0013BA\u0011\f\u0005\r\te.\u001f\t\u00031\r\"Q\u0001\n\u0001C\u0002m\u0011\u0011!\u0016\t\u0003#\u0019J!aJ\u0006\u0003\t1{gnZ\u0001\u0003U\u001a\u0004BAK\u0019\u0018E5\t1F\u0003\u0002-[\u0005Aa-\u001e8di&|gN\u0003\u0002/_\u0005!Q\u000f^5m\u0015\u0005\u0001\u0014\u0001\u00026bm\u0006L!AM\u0016\u0003!Q{Gj\u001c8h\u0005&4UO\\2uS>t\u0017A\u0002\u001fj]&$h\b\u0006\u00026oA!a\u0007A\f#\u001b\u0005)\u0001\"\u0002\u0015\u0003\u0001\u0004I\u0013!B1qa2LHcA\u0013;y!)1h\u0001a\u0001/\u0005\u0011\u00010\r\u0005\u0006{\r\u0001\rAI\u0001\u0003qJ\u0002")
public class FromJavaToLongBiFunction<T, U>
implements Function2<T, U, Object> {
    private final ToLongBiFunction<T, U> jf;

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
    public long apply(T x1, U x2) {
        return this.jf.applyAsLong(x1, x2);
    }

    public FromJavaToLongBiFunction(ToLongBiFunction<T, U> jf) {
        this.jf = jf;
        Function2.$init$(this);
    }
}

