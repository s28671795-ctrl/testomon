/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.io.Serializable;
import java.util.function.BiConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import scala.Function0;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.SpecificIterableFactory;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ArraySeq;
import scala.collection.mutable.Growable;
import scala.jdk.IntAccumulator;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class IntAccumulator$
implements SpecificIterableFactory<Object, IntAccumulator>,
Serializable {
    public static final IntAccumulator$ MODULE$ = new IntAccumulator$();
    private static final int[] scala$jdk$IntAccumulator$$emptyIntArray = new int[0];
    private static final int[][] scala$jdk$IntAccumulator$$emptyIntArrayArray = new int[0][];

    @Override
    public Object apply(Seq xs) {
        return SpecificIterableFactory.apply$(this, xs);
    }

    @Override
    public Object fill(int n, Function0 elem) {
        return SpecificIterableFactory.fill$(this, n, elem);
    }

    @Override
    public Factory<Object, IntAccumulator> specificIterableFactory() {
        return SpecificIterableFactory.specificIterableFactory$(this);
    }

    public int[] scala$jdk$IntAccumulator$$emptyIntArray() {
        return scala$jdk$IntAccumulator$$emptyIntArray;
    }

    public int[][] scala$jdk$IntAccumulator$$emptyIntArrayArray() {
        return scala$jdk$IntAccumulator$$emptyIntArrayArray;
    }

    public SpecificIterableFactory<Integer, IntAccumulator> toJavaIntegerAccumulator(IntAccumulator$ ia) {
        return this;
    }

    public Supplier<IntAccumulator> supplier() {
        return () -> new IntAccumulator();
    }

    public ObjIntConsumer<IntAccumulator> adder() {
        return (ac, a) -> ac.addOne(a);
    }

    public BiConsumer<IntAccumulator, Object> boxedAdder() {
        return (ac, a) -> ac.addOne(BoxesRunTime.unboxToInt(a));
    }

    public BiConsumer<IntAccumulator, IntAccumulator> merger() {
        return (a1, a2) -> a1.drain((IntAccumulator)a2);
    }

    private IntAccumulator fromArray(int[] a) {
        IntAccumulator r = new IntAccumulator();
        for (int i = 0; i < a.length; ++i) {
            r.addOne(a[i]);
        }
        return r;
    }

    @Override
    public IntAccumulator fromSpecific(IterableOnce<Object> it) {
        if (it instanceof IntAccumulator) {
            return (IntAccumulator)it;
        }
        if (it instanceof ArraySeq.ofInt) {
            ArraySeq.ofInt ofInt2 = (ArraySeq.ofInt)it;
            return this.fromArray(ofInt2.unsafeArray());
        }
        if (it instanceof ArraySeq.ofInt) {
            ArraySeq.ofInt ofInt3 = (ArraySeq.ofInt)it;
            return this.fromArray(ofInt3.array());
        }
        return (IntAccumulator)Growable.addAll$(new IntAccumulator(), it);
    }

    @Override
    public IntAccumulator empty() {
        return new IntAccumulator();
    }

    public IntAccumulator newBuilder() {
        return new IntAccumulator();
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(IntAccumulator$.class);
    }

    private IntAccumulator$() {
    }
}

