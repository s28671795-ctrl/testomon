/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToDoubleBiFunction;
import scala.Function1;
import scala.Function2;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y2A\u0001B\u0003\u0001\u001d!A\u0001\u0006\u0001B\u0001B\u0003%\u0011\u0006C\u00034\u0001\u0011\u0005A\u0007C\u00039\u0001\u0011\u0005\u0011H\u0001\u000eGe>l'*\u0019<b)>$u.\u001e2mK\nKg)\u001e8di&|gN\u0003\u0002\u0007\u000f\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\t\u0013\u0005)!.\u0019<bq)\u0011!bC\u0001\u0007G>l\u0007/\u0019;\u000b\u00031\tQa]2bY\u0006\u001c\u0001!F\u0002\u00103\r\u001a2\u0001\u0001\t\u0015!\t\t\"#D\u0001\f\u0013\t\u00192B\u0001\u0004B]f\u0014VM\u001a\t\u0006#U9\"%J\u0005\u0003--\u0011\u0011BR;oGRLwN\u001c\u001a\u0011\u0005aIB\u0002\u0001\u0003\u00065\u0001\u0011\ra\u0007\u0002\u0002)F\u0011Ad\b\t\u0003#uI!AH\u0006\u0003\u000f9{G\u000f[5oOB\u0011\u0011\u0003I\u0005\u0003C-\u00111!\u00118z!\tA2\u0005B\u0003%\u0001\t\u00071DA\u0001V!\t\tb%\u0003\u0002(\u0017\t1Ai\\;cY\u0016\f!A\u001b4\u0011\t)\ntCI\u0007\u0002W)\u0011A&L\u0001\tMVt7\r^5p]*\u0011afL\u0001\u0005kRLGNC\u00011\u0003\u0011Q\u0017M^1\n\u0005IZ#A\u0005+p\t>,(\r\\3CS\u001a+hn\u0019;j_:\fa\u0001P5oSRtDCA\u001b8!\u00111\u0004a\u0006\u0012\u000e\u0003\u0015AQ\u0001\u000b\u0002A\u0002%\nQ!\u00199qYf$2!\n\u001e=\u0011\u0015Y4\u00011\u0001\u0018\u0003\tA\u0018\u0007C\u0003>\u0007\u0001\u0007!%\u0001\u0002ye\u0001")
public class FromJavaToDoubleBiFunction<T, U>
implements Function2<T, U, Object> {
    private final ToDoubleBiFunction<T, U> jf;

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
    public double apply(T x1, U x2) {
        return this.jf.applyAsDouble(x1, x2);
    }

    public FromJavaToDoubleBiFunction(ToDoubleBiFunction<T, U> jf) {
        this.jf = jf;
        Function2.$init$(this);
    }
}

