/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.util.function.BiConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.collection.Factory;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.jdk.AnyAccumulator;
import scala.math.Integral;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class AnyAccumulator$
implements SeqFactory<AnyAccumulator> {
    public static final AnyAccumulator$ MODULE$ = new AnyAccumulator$();
    private static final Object[] scala$jdk$AnyAccumulator$$emptyAnyRefArray = new Object[0];
    private static final Object[][] scala$jdk$AnyAccumulator$$emptyAnyRefArrayArray = new Object[0][];
    private static final long[] scala$jdk$AnyAccumulator$$emptyLongArray = new long[0];

    @Override
    public final SeqOps unapplySeq(SeqOps x) {
        return SeqFactory.unapplySeq$(this, x);
    }

    @Override
    public Object apply(Seq elems) {
        return IterableFactory.apply$(this, elems);
    }

    @Override
    public Object iterate(Object start2, int len, Function1 f) {
        return IterableFactory.iterate$(this, start2, len, f);
    }

    @Override
    public Object unfold(Object init2, Function1 f) {
        return IterableFactory.unfold$(this, init2, f);
    }

    @Override
    public Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(this, start2, end, evidence$3);
    }

    @Override
    public Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(this, start2, end, step, evidence$4);
    }

    @Override
    public Object fill(int n, Function0 elem) {
        return IterableFactory.fill$(this, n, elem);
    }

    @Override
    public Object fill(int n1, int n2, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, elem);
    }

    @Override
    public Object fill(int n1, int n2, int n3, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, n3, elem);
    }

    @Override
    public Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, n3, n4, elem);
    }

    @Override
    public Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, n3, n4, n5, elem);
    }

    @Override
    public Object tabulate(int n, Function1 f) {
        return IterableFactory.tabulate$(this, n, f);
    }

    @Override
    public Object tabulate(int n1, int n2, Function2 f) {
        return IterableFactory.tabulate$(this, n1, n2, f);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, Function3 f) {
        return IterableFactory.tabulate$(this, n1, n2, n3, f);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        return IterableFactory.tabulate$(this, n1, n2, n3, n4, f);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        return IterableFactory.tabulate$(this, n1, n2, n3, n4, n5, f);
    }

    @Override
    public Object concat(Seq xss) {
        return IterableFactory.concat$(this, xss);
    }

    @Override
    public <A> Factory<A, AnyAccumulator<A>> iterableFactory() {
        return IterableFactory.iterableFactory$(this);
    }

    public Object[] scala$jdk$AnyAccumulator$$emptyAnyRefArray() {
        return scala$jdk$AnyAccumulator$$emptyAnyRefArray;
    }

    public Object[][] scala$jdk$AnyAccumulator$$emptyAnyRefArrayArray() {
        return scala$jdk$AnyAccumulator$$emptyAnyRefArrayArray;
    }

    public long[] scala$jdk$AnyAccumulator$$emptyLongArray() {
        return scala$jdk$AnyAccumulator$$emptyLongArray;
    }

    public <A> Supplier<AnyAccumulator<A>> supplier() {
        return () -> new AnyAccumulator();
    }

    public <A> BiConsumer<AnyAccumulator<A>, A> adder() {
        return (ac, a) -> ac.addOne(a);
    }

    public ObjIntConsumer<AnyAccumulator<Object>> unboxedIntAdder() {
        return (ac, a) -> ac.addOne(BoxesRunTime.boxToInteger(a));
    }

    public ObjLongConsumer<AnyAccumulator<Object>> unboxedLongAdder() {
        return (ac, a) -> ac.addOne(BoxesRunTime.boxToLong(a));
    }

    public ObjDoubleConsumer<AnyAccumulator<Object>> unboxedDoubleAdder() {
        return (ac, a) -> ac.addOne(BoxesRunTime.boxToDouble(a));
    }

    public <A> BiConsumer<AnyAccumulator<A>, AnyAccumulator<A>> merger() {
        return (a1, a2) -> a1.drain(a2);
    }

    @Override
    public <A> AnyAccumulator<A> from(IterableOnce<A> source2) {
        if (source2 instanceof AnyAccumulator) {
            return (AnyAccumulator)source2;
        }
        return (AnyAccumulator)Growable.addAll$(new AnyAccumulator(), source2);
    }

    @Override
    public <A> AnyAccumulator<A> empty() {
        return new AnyAccumulator();
    }

    @Override
    public <A> Builder<A, AnyAccumulator<A>> newBuilder() {
        return new AnyAccumulator();
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AnyAccumulator$.class);
    }

    private AnyAccumulator$() {
    }
}

