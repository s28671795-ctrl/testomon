/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.io.Serializable;
import java.util.function.BiConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import scala.Function0;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.SpecificIterableFactory;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ArraySeq;
import scala.collection.mutable.Growable;
import scala.jdk.DoubleAccumulator;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class DoubleAccumulator$
implements SpecificIterableFactory<Object, DoubleAccumulator>,
Serializable {
    public static final DoubleAccumulator$ MODULE$ = new DoubleAccumulator$();
    private static final double[] scala$jdk$DoubleAccumulator$$emptyDoubleArray = new double[0];
    private static final double[][] scala$jdk$DoubleAccumulator$$emptyDoubleArrayArray = new double[0][];

    @Override
    public Object apply(Seq xs) {
        return SpecificIterableFactory.apply$(this, xs);
    }

    @Override
    public Object fill(int n, Function0 elem) {
        return SpecificIterableFactory.fill$(this, n, elem);
    }

    @Override
    public Factory<Object, DoubleAccumulator> specificIterableFactory() {
        return SpecificIterableFactory.specificIterableFactory$(this);
    }

    public double[] scala$jdk$DoubleAccumulator$$emptyDoubleArray() {
        return scala$jdk$DoubleAccumulator$$emptyDoubleArray;
    }

    public double[][] scala$jdk$DoubleAccumulator$$emptyDoubleArrayArray() {
        return scala$jdk$DoubleAccumulator$$emptyDoubleArrayArray;
    }

    public SpecificIterableFactory<Double, DoubleAccumulator> toJavaDoubleAccumulator(DoubleAccumulator$ ia) {
        return this;
    }

    public Supplier<DoubleAccumulator> supplier() {
        return () -> new DoubleAccumulator();
    }

    public ObjDoubleConsumer<DoubleAccumulator> adder() {
        return (ac, a) -> ac.addOne(a);
    }

    public BiConsumer<DoubleAccumulator, Object> boxedAdder() {
        return (ac, a) -> ac.addOne(BoxesRunTime.unboxToDouble(a));
    }

    public BiConsumer<DoubleAccumulator, DoubleAccumulator> merger() {
        return (a1, a2) -> a1.drain((DoubleAccumulator)a2);
    }

    private DoubleAccumulator fromArray(double[] a) {
        DoubleAccumulator r = new DoubleAccumulator();
        for (int i = 0; i < a.length; ++i) {
            r.addOne(a[i]);
        }
        return r;
    }

    @Override
    public DoubleAccumulator fromSpecific(IterableOnce<Object> it) {
        if (it instanceof DoubleAccumulator) {
            return (DoubleAccumulator)it;
        }
        if (it instanceof ArraySeq.ofDouble) {
            ArraySeq.ofDouble ofDouble2 = (ArraySeq.ofDouble)it;
            return this.fromArray(ofDouble2.unsafeArray());
        }
        if (it instanceof ArraySeq.ofDouble) {
            ArraySeq.ofDouble ofDouble3 = (ArraySeq.ofDouble)it;
            return this.fromArray(ofDouble3.array());
        }
        return (DoubleAccumulator)Growable.addAll$(new DoubleAccumulator(), it);
    }

    @Override
    public DoubleAccumulator empty() {
        return new DoubleAccumulator();
    }

    public DoubleAccumulator newBuilder() {
        return new DoubleAccumulator();
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DoubleAccumulator$.class);
    }

    private DoubleAccumulator$() {
    }
}

