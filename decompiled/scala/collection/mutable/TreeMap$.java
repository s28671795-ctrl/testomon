/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.SortedMapFactory;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.GrowableBuilder;
import scala.collection.mutable.TreeMap;
import scala.math.Ordering;
import scala.runtime.ModuleSerializationProxy;

public final class TreeMap$
implements SortedMapFactory<TreeMap> {
    public static final TreeMap$ MODULE$ = new TreeMap$();
    private static final long serialVersionUID = 3L;

    @Override
    public Object apply(Seq elems, Ordering evidence$38) {
        return SortedMapFactory.apply$(this, elems, evidence$38);
    }

    @Override
    public <K, V> Factory<Tuple2<K, V>, TreeMap<K, V>> sortedMapFactory(Ordering<K> evidence$40) {
        return SortedMapFactory.sortedMapFactory$(this, evidence$40);
    }

    @Override
    public <K, V> TreeMap<K, V> from(IterableOnce<Tuple2<K, V>> it, Ordering<K> evidence$1) {
        return (TreeMap)new TreeMap(evidence$1).addAll(it);
    }

    @Override
    public <K, V> TreeMap<K, V> empty(Ordering<K> evidence$2) {
        return new TreeMap(evidence$2);
    }

    @Override
    public <K, V> Builder<Tuple2<K, V>, TreeMap<K, V>> newBuilder(Ordering<K> evidence$3) {
        return new GrowableBuilder<Tuple2<K, V>, TreeMap<K, V>>(new TreeMap(evidence$3));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TreeMap$.class);
    }

    private TreeMap$() {
    }
}

