/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005)3Q!\u0003\u0006\u0001\u001dAA\u0001\"\t\u0001\u0003\u0002\u0003\u0006Ia\u0006\u0005\u0006G\u0001!\t\u0001\n\u0005\bQ\u0001\u0001\r\u0011\"\u0003*\u0011\u001dQ\u0003\u00011A\u0005\n-Ba!\r\u0001!B\u00139\u0002\"\u0002\u001a\u0001\t\u0003\u001a\u0004\"\u0002\u001f\u0001\t\u0003j\u0004\"B \u0001\t\u0003\u0001%AC*vEJ+7-Z5wK*\u00111\u0002D\u0001\u0005S6\u0004HN\u0003\u0002\u000e\u001d\u000511\u000f\u001e:fC6T\u0011aD\u0001\u0005C.\\\u0017mE\u0002\u0001#]\u0001\"AE\u000b\u000e\u0003MQ\u0011\u0001F\u0001\u0006g\u000e\fG.Y\u0005\u0003-M\u0011a!\u00118z%\u00164\u0007C\u0001\r\u001f\u001d\tIB$D\u0001\u001b\u0015\tYb\"A\u0003bGR|'/\u0003\u0002\u001e5\u0005)\u0011i\u0019;pe&\u0011q\u0004\t\u0002\b%\u0016\u001cW-\u001b<f\u0015\ti\"$A\u0004j]&$\u0018.\u00197\u0004\u0001\u00051A(\u001b8jiz\"\"!J\u0014\u0011\u0005\u0019\u0002Q\"\u0001\u0006\t\u000b\u0005\u0012\u0001\u0019A\f\u0002\u001d\r,(O]3oiJ+7-Z5wKV\tq#\u0001\ndkJ\u0014XM\u001c;SK\u000e,\u0017N^3`I\u0015\fHC\u0001\u00170!\t\u0011R&\u0003\u0002/'\t!QK\\5u\u0011\u001d\u0001D!!AA\u0002]\t1\u0001\u001f\u00132\u0003=\u0019WO\u001d:f]R\u0014VmY3jm\u0016\u0004\u0013aC5t\t\u00164\u0017N\\3e\u0003R$\"\u0001N\u001c\u0011\u0005I)\u0014B\u0001\u001c\u0014\u0005\u001d\u0011un\u001c7fC:DQ\u0001\u000f\u0004A\u0002e\n1!\\:h!\t\u0011\"(\u0003\u0002<'\t\u0019\u0011I\\=\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u00051r\u0004\"\u0002\u001d\b\u0001\u0004I\u0014A\u00022fG>lW\r\u0006\u0002-\u0003\")!\t\u0003a\u0001/\u0005Ya.Z<CK\"\fg/[8sQ\t\u0001A\t\u0005\u0002F\u00116\taI\u0003\u0002H\u001d\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005%3%aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public class SubReceive
implements PartialFunction<Object, BoxedUnit> {
    private PartialFunction<Object, BoxedUnit> currentReceive;

    @Override
    public Option<BoxedUnit> unapply(Object a) {
        return PartialFunction.unapply$(this, a);
    }

    @Override
    public PartialFunction<Object, BoxedUnit> elementWise() {
        return PartialFunction.elementWise$(this);
    }

    @Override
    public <A1, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
        return PartialFunction.orElse$(this, that);
    }

    @Override
    public <C> PartialFunction<Object, C> andThen(Function1<BoxedUnit, C> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <C> PartialFunction<Object, C> andThen(PartialFunction<BoxedUnit, C> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <R$> PartialFunction<R$, BoxedUnit> compose(PartialFunction<R$, Object> k) {
        return PartialFunction.compose$(this, k);
    }

    @Override
    public Function1<Object, Option<BoxedUnit>> lift() {
        return PartialFunction.lift$(this);
    }

    @Override
    public <A1, B1> B1 applyOrElse(A1 x, Function1<A1, B1> function1) {
        return (B1)PartialFunction.applyOrElse$(this, x, function1);
    }

    @Override
    public <U> Function1<Object, Object> runWith(Function1<BoxedUnit, U> action) {
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
    public <A> Function1<A, BoxedUnit> compose(Function1<A, Object> g2) {
        return Function1.compose$(this, g2);
    }

    @Override
    public String toString() {
        return Function1.toString$(this);
    }

    private PartialFunction<Object, BoxedUnit> currentReceive() {
        return this.currentReceive;
    }

    private void currentReceive_$eq(PartialFunction<Object, BoxedUnit> x$1) {
        this.currentReceive = x$1;
    }

    @Override
    public boolean isDefinedAt(Object msg) {
        return this.currentReceive().isDefinedAt(msg);
    }

    @Override
    public void apply(Object msg) {
        this.currentReceive().apply(msg);
    }

    public void become(PartialFunction<Object, BoxedUnit> newBehavior) {
        this.currentReceive_$eq(newBehavior);
    }

    public SubReceive(PartialFunction<Object, BoxedUnit> initial) {
        Function1.$init$(this);
        PartialFunction.$init$(this);
        this.currentReceive = initial;
    }
}

