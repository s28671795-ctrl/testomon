/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.io.Serializable;
import java.util.function.BiConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import scala.Function0;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.SpecificIterableFactory;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ArraySeq;
import scala.collection.mutable.Growable;
import scala.jdk.LongAccumulator;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class LongAccumulator$
implements SpecificIterableFactory<Object, LongAccumulator>,
Serializable {
    public static final LongAccumulator$ MODULE$ = new LongAccumulator$();
    private static final long[] scala$jdk$LongAccumulator$$emptyLongArray = new long[0];
    private static final long[][] scala$jdk$LongAccumulator$$emptyLongArrayArray = new long[0][];

    @Override
    public Object apply(Seq xs) {
        return SpecificIterableFactory.apply$(this, xs);
    }

    @Override
    public Object fill(int n, Function0 elem) {
        return SpecificIterableFactory.fill$(this, n, elem);
    }

    @Override
    public Factory<Object, LongAccumulator> specificIterableFactory() {
        return SpecificIterableFactory.specificIterableFactory$(this);
    }

    public long[] scala$jdk$LongAccumulator$$emptyLongArray() {
        return scala$jdk$LongAccumulator$$emptyLongArray;
    }

    public long[][] scala$jdk$LongAccumulator$$emptyLongArrayArray() {
        return scala$jdk$LongAccumulator$$emptyLongArrayArray;
    }

    public SpecificIterableFactory<Long, LongAccumulator> toJavaLongAccumulator(LongAccumulator$ ia) {
        return this;
    }

    public Supplier<LongAccumulator> supplier() {
        return () -> new LongAccumulator();
    }

    public ObjLongConsumer<LongAccumulator> adder() {
        return (ac, a) -> ac.addOne(a);
    }

    public BiConsumer<LongAccumulator, Object> boxedAdder() {
        return (ac, a) -> ac.addOne(BoxesRunTime.unboxToLong(a));
    }

    public BiConsumer<LongAccumulator, LongAccumulator> merger() {
        return (a1, a2) -> a1.drain((LongAccumulator)a2);
    }

    private LongAccumulator fromArray(long[] a) {
        LongAccumulator r = new LongAccumulator();
        for (int i = 0; i < a.length; ++i) {
            r.addOne(a[i]);
        }
        return r;
    }

    @Override
    public LongAccumulator fromSpecific(IterableOnce<Object> it) {
        if (it instanceof LongAccumulator) {
            return (LongAccumulator)it;
        }
        if (it instanceof ArraySeq.ofLong) {
            ArraySeq.ofLong ofLong2 = (ArraySeq.ofLong)it;
            return this.fromArray(ofLong2.unsafeArray());
        }
        if (it instanceof ArraySeq.ofLong) {
            ArraySeq.ofLong ofLong3 = (ArraySeq.ofLong)it;
            return this.fromArray(ofLong3.array());
        }
        return (LongAccumulator)Growable.addAll$(new LongAccumulator(), it);
    }

    @Override
    public LongAccumulator empty() {
        return new LongAccumulator();
    }

    public LongAccumulator newBuilder() {
        return new LongAccumulator();
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(LongAccumulator$.class);
    }

    private LongAccumulator$() {
    }
}

