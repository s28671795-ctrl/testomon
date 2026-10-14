/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.collection.Factory;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.GrowableBuilder;
import scala.collection.mutable.HashSet;
import scala.math.Integral;
import scala.runtime.ModuleSerializationProxy;

public final class HashSet$
implements IterableFactory<HashSet> {
    public static final HashSet$ MODULE$ = new HashSet$();
    private static final long serialVersionUID = 3L;

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
    public <A> Factory<A, HashSet<A>> iterableFactory() {
        return IterableFactory.iterableFactory$(this);
    }

    @Override
    public <B> HashSet<B> from(IterableOnce<B> it) {
        int k = it.knownSize();
        int cap = k > 0 ? (int)((double)(k + 1) / 0.75) : 16;
        return new HashSet(cap, 0.75).addAll((IterableOnce)it);
    }

    @Override
    public <A> HashSet<A> empty() {
        return new HashSet();
    }

    @Override
    public <A> Builder<A, HashSet<A>> newBuilder() {
        double newBuilder_loadFactor = 0.75;
        int newBuilder_initialCapacity = 16;
        return new GrowableBuilder<A, HashSet<A>>(newBuilder_initialCapacity, newBuilder_loadFactor){

            public void sizeHint(int size) {
                ((HashSet)this.elems()).sizeHint(size);
            }
        };
    }

    public <A> Builder<A, HashSet<A>> newBuilder(int initialCapacity, double loadFactor) {
        return new /* invalid duplicate definition of identical inner class */;
    }

    public final double defaultLoadFactor() {
        return 0.75;
    }

    public final int defaultInitialCapacity() {
        return 16;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(HashSet$.class);
    }

    private HashSet$() {
    }
}

