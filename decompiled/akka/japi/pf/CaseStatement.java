/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

import akka.japi.pf.CaseStatement$;
import akka.japi.pf.FI;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U;aAC\u0006\t\u0002-\tbAB\n\f\u0011\u0003YA\u0003C\u0003\u001c\u0003\u0011\u0005Q\u0004C\u0003\u001f\u0003\u0011\u0005qDB\u0003\u0014\u0017\u0001Y!\u0007\u0003\u0005:\t\t\u0005\t\u0015!\u0003;\u0011!\tEA!A!\u0002\u0013\u0011\u0005\"B\u000e\u0005\t\u0003A\u0005\"\u0002'\u0005\t\u0003j\u0005\"B!\u0005\t\u0003\u001a\u0016!D\"bg\u0016\u001cF/\u0019;f[\u0016tGO\u0003\u0002\r\u001b\u0005\u0011\u0001O\u001a\u0006\u0003\u001d=\tAA[1qS*\t\u0001#\u0001\u0003bW.\f\u0007C\u0001\n\u0002\u001b\u0005Y!!D\"bg\u0016\u001cF/\u0019;f[\u0016tGo\u0005\u0002\u0002+A\u0011a#G\u0007\u0002/)\t\u0001$A\u0003tG\u0006d\u0017-\u0003\u0002\u001b/\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002#\u0005)Q-\u001c9usV\u0019\u0001E\n\u0019\u0015\u0003\u0005\u0002BA\u0006\u0012%_%\u00111e\u0006\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]B\u0011QE\n\u0007\u0001\t\u001593A1\u0001)\u0005\u00051\u0015CA\u0015-!\t1\"&\u0003\u0002,/\t9aj\u001c;iS:<\u0007C\u0001\f.\u0013\tqsCA\u0002B]f\u0004\"!\n\u0019\u0005\u000bE\u001a!\u0019\u0001\u0015\u0003\u0003Q+Ba\r\u001cGqM\u0019A!\u0006\u001b\u0011\tY\u0011Sg\u000e\t\u0003KY\"aa\n\u0003\t\u0006\u0004A\u0003CA\u00139\t\u0015\tDA1\u0001)\u0003%\u0001(/\u001a3jG\u0006$X\r\u0005\u0002<}9\u0011!\u0003P\u0005\u0003{-\t!AR%\n\u0005}\u0002%!\u0003)sK\u0012L7-\u0019;f\u0015\ti4\"A\u0003baBd\u0017\u0010\u0005\u0003<\u0007\u0016;\u0014B\u0001#A\u0005\u0015\t\u0005\u000f\u001d7z!\t)c\t\u0002\u0004H\t\u0011\u0015\r\u0001\u000b\u0002\u0002!R\u0019\u0011JS&\u0011\u000bI!Q'R\u001c\t\u000be:\u0001\u0019\u0001\u001e\t\u000b\u0005;\u0001\u0019\u0001\"\u0002\u0017%\u001cH)\u001a4j]\u0016$\u0017\t\u001e\u000b\u0003\u001dF\u0003\"AF(\n\u0005A;\"a\u0002\"p_2,\u0017M\u001c\u0005\u0006%\"\u0001\r!N\u0001\u0002_R\u0011q\u0007\u0016\u0005\u0006%&\u0001\r!\u000e")
public class CaseStatement<F, P, T>
implements PartialFunction<F, T> {
    private final FI.Predicate predicate;
    private final FI.Apply<P, T> apply;

    public static <F, T> PartialFunction<F, T> empty() {
        return CaseStatement$.MODULE$.empty();
    }

    @Override
    public Option<T> unapply(F a) {
        return PartialFunction.unapply$(this, a);
    }

    @Override
    public PartialFunction<F, T> elementWise() {
        return PartialFunction.elementWise$(this);
    }

    @Override
    public <A1 extends F, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
        return PartialFunction.orElse$(this, that);
    }

    @Override
    public <C> PartialFunction<F, C> andThen(Function1<T, C> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <C> PartialFunction<F, C> andThen(PartialFunction<T, C> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <R$> PartialFunction<R$, T> compose(PartialFunction<R$, F> k) {
        return PartialFunction.compose$(this, k);
    }

    @Override
    public Function1<F, Option<T>> lift() {
        return PartialFunction.lift$(this);
    }

    @Override
    public <A1 extends F, B1> B1 applyOrElse(A1 x, Function1<A1, B1> function1) {
        return (B1)PartialFunction.applyOrElse$(this, x, function1);
    }

    @Override
    public <U> Function1<F, Object> runWith(Function1<T, U> action) {
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
    public <A> Function1<A, T> compose(Function1<A, F> g2) {
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
    public T apply(F o) {
        return this.apply.apply(o);
    }

    public CaseStatement(FI.Predicate predicate, FI.Apply<P, T> apply2) {
        this.predicate = predicate;
        this.apply = apply2;
        Function1.$init$(this);
        PartialFunction.$init$(this);
    }
}

