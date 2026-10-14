/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiConsumer;
import scala.Function1;
import scala.Function2;
import scala.Tuple2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005y2A\u0001B\u0003\u0001\u001d!A\u0001\u0006\u0001B\u0001B\u0003%\u0011\u0006C\u00034\u0001\u0011\u0005A\u0007C\u00039\u0001\u0011\u0005\u0011H\u0001\nGe>l'*\u0019<b\u0005&\u001cuN\\:v[\u0016\u0014(B\u0001\u0004\b\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(B\u0001\u0005\n\u0003\u0015Q\u0017M^19\u0015\tQ1\"\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002\u0019\u0005)1oY1mC\u000e\u0001QcA\b\u001aGM\u0019\u0001\u0001\u0005\u000b\u0011\u0005E\u0011R\"A\u0006\n\u0005MY!AB!osJ+g\rE\u0003\u0012+]\u0011S%\u0003\u0002\u0017\u0017\tIa)\u001e8di&|gN\r\t\u00031ea\u0001\u0001B\u0003\u001b\u0001\t\u00071DA\u0001U#\tar\u0004\u0005\u0002\u0012;%\u0011ad\u0003\u0002\b\u001d>$\b.\u001b8h!\t\t\u0002%\u0003\u0002\"\u0017\t\u0019\u0011I\\=\u0011\u0005a\u0019C!\u0002\u0013\u0001\u0005\u0004Y\"!A+\u0011\u0005E1\u0013BA\u0014\f\u0005\u0011)f.\u001b;\u0002\u0005)4\u0007\u0003\u0002\u00162/\tj\u0011a\u000b\u0006\u0003Y5\n\u0001BZ;oGRLwN\u001c\u0006\u0003]=\nA!\u001e;jY*\t\u0001'\u0001\u0003kCZ\f\u0017B\u0001\u001a,\u0005)\u0011\u0015nQ8ogVlWM]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005U:\u0004\u0003\u0002\u001c\u0001/\tj\u0011!\u0002\u0005\u0006Q\t\u0001\r!K\u0001\u0006CB\u0004H.\u001f\u000b\u0004Kib\u0004\"B\u001e\u0004\u0001\u00049\u0012A\u0001=2\u0011\u0015i4\u00011\u0001#\u0003\tA(\u0007")
public class FromJavaBiConsumer<T, U>
implements Function2<T, U, BoxedUnit> {
    private final BiConsumer<T, U> jf;

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
    public Function1<T, Function1<U, BoxedUnit>> curried() {
        return Function2.curried$(this);
    }

    @Override
    public Function1<Tuple2<T, U>, BoxedUnit> tupled() {
        return Function2.tupled$(this);
    }

    @Override
    public String toString() {
        return Function2.toString$(this);
    }

    @Override
    public void apply(T x1, U x2) {
        this.jf.accept(x1, x2);
    }

    public FromJavaBiConsumer(BiConsumer<T, U> jf) {
        this.jf = jf;
        Function2.$init$(this);
    }
}

