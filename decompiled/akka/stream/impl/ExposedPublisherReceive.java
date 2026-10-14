/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.ExposedPublisher;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.collection.immutable.List;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005m3aAC\u0006\u0002\u0002=\t\u0002\u0002\u0003\u0012\u0001\u0005\u0003\u0005\u000b\u0011\u0002\r\t\u0011\u0011\u0002!\u0011!Q\u0001\n\u0015BQA\f\u0001\u0005\u0002=Bq\u0001\u000e\u0001A\u0002\u0013%Q\u0007C\u0004?\u0001\u0001\u0007I\u0011B \t\r\t\u0003\u0001\u0015)\u00037\u0011\u0015\u0019\u0005\u0001\"\u0001E\u0011\u0015Q\u0005\u0001\"\u0001L\u0011\u0015i\u0005A\"\u0001O\u0005])\u0005\u0010]8tK\u0012\u0004VO\u00197jg\",'OU3dK&4XM\u0003\u0002\r\u001b\u0005!\u0011.\u001c9m\u0015\tqq\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002!\u0005!\u0011m[6b'\r\u0001!\u0003\u0007\t\u0003'Yi\u0011\u0001\u0006\u0006\u0002+\u0005)1oY1mC&\u0011q\u0003\u0006\u0002\u0007\u0003:L(+\u001a4\u0011\u0005eybB\u0001\u000e\u001e\u001b\u0005Y\"B\u0001\u000f\u0010\u0003\u0015\t7\r^8s\u0013\tq2$A\u0003BGR|'/\u0003\u0002!C\t9!+Z2fSZ,'B\u0001\u0010\u001c\u00035\t7\r^5wKJ+7-Z5wK\u000e\u0001\u0011!C;oQ\u0006tG\r\\3e!\u0011\u0019b\u0005K\u0016\n\u0005\u001d\"\"!\u0003$v]\u000e$\u0018n\u001c82!\t\u0019\u0012&\u0003\u0002+)\t\u0019\u0011I\\=\u0011\u0005Ma\u0013BA\u0017\u0015\u0005\u0011)f.\u001b;\u0002\rqJg.\u001b;?)\r\u0001$g\r\t\u0003c\u0001i\u0011a\u0003\u0005\u0006E\r\u0001\r\u0001\u0007\u0005\u0006I\r\u0001\r!J\u0001\u0006gR\f7\u000f[\u000b\u0002mA\u0019q\u0007\u0010\u0015\u000e\u0003aR!!\u000f\u001e\u0002\u0013%lW.\u001e;bE2,'BA\u001e\u0015\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003{a\u0012A\u0001T5ti\u0006I1\u000f^1tQ~#S-\u001d\u000b\u0003W\u0001Cq!Q\u0003\u0002\u0002\u0003\u0007a'A\u0002yIE\naa\u001d;bg\"\u0004\u0013aC5t\t\u00164\u0017N\\3e\u0003R$\"!\u0012%\u0011\u0005M1\u0015BA$\u0015\u0005\u001d\u0011un\u001c7fC:DQ!S\u0004A\u0002!\n\u0011a\\\u0001\u0006CB\u0004H.\u001f\u000b\u0003W1CQ!\u0013\u0005A\u0002!\nqC]3dK&4X-\u0012=q_N,G\rU;cY&\u001c\b.\u001a:\u0015\u0005-z\u0005\"\u0002)\n\u0001\u0004\t\u0016AA3q!\t\t$+\u0003\u0002T\u0017\t\u0001R\t\u001f9pg\u0016$\u0007+\u001e2mSNDWM\u001d\u0015\u0003\u0001U\u0003\"AV-\u000e\u0003]S!\u0001W\b\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002[/\nY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public abstract class ExposedPublisherReceive
implements PartialFunction<Object, BoxedUnit> {
    private final PartialFunction<Object, BoxedUnit> activeReceive;
    private final Function1<Object, BoxedUnit> unhandled;
    private List<Object> stash;

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

    private List<Object> stash() {
        return this.stash;
    }

    private void stash_$eq(List<Object> x$1) {
        this.stash = x$1;
    }

    @Override
    public boolean isDefinedAt(Object o) {
        return true;
    }

    @Override
    public void apply(Object o) {
        Object object = o;
        if (object instanceof ExposedPublisher) {
            ExposedPublisher exposedPublisher = (ExposedPublisher)object;
            this.receiveExposedPublisher(exposedPublisher);
            if (this.stash().nonEmpty()) {
                ((List)this.stash().reverse()).foreach((Function1<Object, Object> & Serializable)msg -> {
                    this.activeReceive.applyOrElse(msg, this.unhandled);
                    return BoxedUnit.UNIT;
                });
                return;
            }
            return;
        }
        this.stash_$eq(this.stash().$colon$colon(object));
    }

    public abstract void receiveExposedPublisher(ExposedPublisher var1);

    public ExposedPublisherReceive(PartialFunction<Object, BoxedUnit> activeReceive, Function1<Object, BoxedUnit> unhandled) {
        this.activeReceive = activeReceive;
        this.unhandled = unhandled;
        Function1.$init$(this);
        PartialFunction.$init$(this);
        this.stash = package$.MODULE$.List().empty();
    }
}

