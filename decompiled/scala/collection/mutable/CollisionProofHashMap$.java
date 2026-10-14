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
import scala.collection.mutable.CollisionProofHashMap;
import scala.collection.mutable.GrowableBuilder;
import scala.math.Ordering;
import scala.runtime.ModuleSerializationProxy;

public final class CollisionProofHashMap$
implements SortedMapFactory<CollisionProofHashMap> {
    public static final CollisionProofHashMap$ MODULE$ = new CollisionProofHashMap$();
    private static final long serialVersionUID = 3L;

    @Override
    public Object apply(Seq elems, Ordering evidence$38) {
        return SortedMapFactory.apply$(this, elems, evidence$38);
    }

    @Override
    public <K, V> Factory<Tuple2<K, V>, CollisionProofHashMap<K, V>> sortedMapFactory(Ordering<K> evidence$40) {
        return SortedMapFactory.sortedMapFactory$(this, evidence$40);
    }

    public final String ordMsg() {
        return "No implicit Ordering[${K2}] found to build a CollisionProofHashMap[${K2}, ${V2}]. You may want to upcast to a Map[${K}, ${V}] first by calling `unsorted`.";
    }

    @Override
    public <K, V> CollisionProofHashMap<K, V> from(IterableOnce<Tuple2<K, V>> it, Ordering<K> evidence$1) {
        int k = it.knownSize();
        int cap = k > 0 ? (int)((double)(k + 1) / 0.75) : 16;
        return new CollisionProofHashMap(cap, 0.75, evidence$1).addAll((IterableOnce)it);
    }

    @Override
    public <K, V> CollisionProofHashMap<K, V> empty(Ordering<K> evidence$2) {
        return new CollisionProofHashMap(evidence$2);
    }

    @Override
    public <K, V> Builder<Tuple2<K, V>, CollisionProofHashMap<K, V>> newBuilder(Ordering<K> evidence$3) {
        double newBuilder_loadFactor = 0.75;
        int newBuilder_initialCapacity = 16;
        return new GrowableBuilder<Tuple2<K, V>, CollisionProofHashMap<K, V>>(newBuilder_initialCapacity, newBuilder_loadFactor, evidence$3){

            public void sizeHint(int size) {
                ((CollisionProofHashMap)this.elems()).sizeHint(size);
            }
        };
    }

    public <K, V> Builder<Tuple2<K, V>, CollisionProofHashMap<K, V>> newBuilder(int initialCapacity, double loadFactor, Ordering<K> evidence$4) {
        return new /* invalid duplicate definition of identical inner class */;
    }

    public final double defaultLoadFactor() {
        return 0.75;
    }

    public final int defaultInitialCapacity() {
        return 16;
    }

    private <K, V> int compare(K key, int hash, CollisionProofHashMap.LLNode<K, V> node, Ordering<K> ord) {
        int i = hash - node.hash();
        if (i != 0) {
            return i;
        }
        return ord.compare(key, node.key());
    }

    public <K, V> int scala$collection$mutable$CollisionProofHashMap$$compare(K key, int hash, CollisionProofHashMap.RBNode<K, V> node, Ordering<K> ord) {
        return ord.compare(key, node.key());
    }

    private final int treeifyThreshold() {
        return 8;
    }

    public <A, B> CollisionProofHashMap.RBNode<A, B> scala$collection$mutable$CollisionProofHashMap$$leaf(A key, int hash, B value, boolean red, CollisionProofHashMap.RBNode<A, B> parent) {
        return new CollisionProofHashMap.RBNode<A, B>(key, hash, value, red, null, null, parent);
    }

    public <A, B> CollisionProofHashMap.RBNode<A, B> scala$collection$mutable$CollisionProofHashMap$$minNodeNonNull(CollisionProofHashMap.RBNode<A, B> node) {
        while (node.left() != null) {
            node = node.left();
        }
        return node;
    }

    public <A, B> CollisionProofHashMap.RBNode<A, B> scala$collection$mutable$CollisionProofHashMap$$successor(CollisionProofHashMap.RBNode<A, B> node) {
        CollisionProofHashMap.RBNode<A, B> y;
        if (node.right() != null) {
            return this.scala$collection$mutable$CollisionProofHashMap$$minNodeNonNull(node.right());
        }
        CollisionProofHashMap.RBNode<A, B> x = node;
        for (y = node.parent(); y != null && x == y.right(); y = y.parent()) {
            x = y;
        }
        return y;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(CollisionProofHashMap$.class);
    }

    private CollisionProofHashMap$() {
    }
}

