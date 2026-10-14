/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

import akka.japi.pf.FI;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u00153QAB\u0004\u0001\u000f5A\u0001b\n\u0001\u0003\u0002\u0003\u0006I\u0001\u000b\u0005\ta\u0001\u0011\t\u0011)A\u0005c!)q\u0007\u0001C\u0001q!)A\b\u0001C!{!)\u0001\u0007\u0001C!\u0007\n\tRK\\5u\u0007\u0006\u001cXm\u0015;bi\u0016lWM\u001c;\u000b\u0005!I\u0011A\u00019g\u0015\tQ1\"\u0001\u0003kCBL'\"\u0001\u0007\u0002\t\u0005\\7.Y\u000b\u0004\u001di)4c\u0001\u0001\u0010+A\u0011\u0001cE\u0007\u0002#)\t!#A\u0003tG\u0006d\u0017-\u0003\u0002\u0015#\t1\u0011I\\=SK\u001a\u0004B\u0001\u0005\f\u0019I%\u0011q#\u0005\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]B\u0011\u0011D\u0007\u0007\u0001\t\u0015Y\u0002A1\u0001\u001e\u0005\u000515\u0001A\t\u0003=\u0005\u0002\"\u0001E\u0010\n\u0005\u0001\n\"a\u0002(pi\"Lgn\u001a\t\u0003!\tJ!aI\t\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u0011K%\u0011a%\u0005\u0002\u0005+:LG/A\u0005qe\u0016$\u0017nY1uKB\u0011\u0011&\f\b\u0003U-j\u0011aB\u0005\u0003Y\u001d\t!AR%\n\u00059z#!\u0003)sK\u0012L7-\u0019;f\u0015\tas!A\u0003baBd\u0017\u0010E\u0002*eQJ!aM\u0018\u0003\u0013Us\u0017\u000e^!qa2L\bCA\r6\t\u00151\u0004A1\u0001\u001e\u0005\u0005\u0001\u0016A\u0002\u001fj]&$h\bF\u0002:um\u0002BA\u000b\u0001\u0019i!)qe\u0001a\u0001Q!)\u0001g\u0001a\u0001c\u0005Y\u0011n\u001d#fM&tW\rZ!u)\tq\u0014\t\u0005\u0002\u0011\u007f%\u0011\u0001)\u0005\u0002\b\u0005>|G.Z1o\u0011\u0015\u0011E\u00011\u0001\u0019\u0003\u0005yGC\u0001\u0013E\u0011\u0015\u0011U\u00011\u0001\u0019\u0001")
public class UnitCaseStatement<F, P>
implements PartialFunction<F, BoxedUnit> {
    private final FI.Predicate predicate;
    private final FI.UnitApply<P> apply;

    @Override
    public Option<BoxedUnit> unapply(F a) {
        return PartialFunction.unapply$(this, a);
    }

    @Override
    public PartialFunction<F, BoxedUnit> elementWise() {
        return PartialFunction.elementWise$(this);
    }

    @Override
    public <A1 extends F, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
        return PartialFunction.orElse$(this, that);
    }

    @Override
    public <C> PartialFunction<F, C> andThen(Function1<BoxedUnit, C> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <C> PartialFunction<F, C> andThen(PartialFunction<BoxedUnit, C> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <R$> PartialFunction<R$, BoxedUnit> compose(PartialFunction<R$, F> k) {
        return PartialFunction.compose$(this, k);
    }

    @Override
    public Function1<F, Option<BoxedUnit>> lift() {
        return PartialFunction.lift$(this);
    }

    @Override
    public <A1 extends F, B1> B1 applyOrElse(A1 x, Function1<A1, B1> function1) {
        return (B1)PartialFunction.applyOrElse$(this, x, function1);
    }

    @Override
    public <U> Function1<F, Object> runWith(Function1<BoxedUnit, U> action) {
        return PartialFunction.runWith$(this, action);
    }

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
    public void apply$mcVJ$sp(long v1) {
        Function1.apply$mcVJ$sp$(this, v1);
    }

    @Override
    public <A> Function1<A, BoxedUnit> compose(Function1<A, F> g2) {
        return Function1.compose$(this, g2);
    }

    @Override
    public String toString() {
        return Function1.toString$(this);
    }

    @Override
    public boolean isDefinedAt(F o) {
        return this.predicate.defined(o);
    }

    @Override
    public void apply(F o) {
        this.apply.apply(o);
    }

    public UnitCaseStatement(FI.Predicate predicate, FI.UnitApply<P> apply2) {
        this.predicate = predicate;
        this.apply = apply2;
        Function1.$init$(this);
        PartialFunction.$init$(this);
    }
}

