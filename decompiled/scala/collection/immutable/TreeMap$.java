/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.MatchError;
import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.SortedMap;
import scala.collection.SortedMapFactory;
import scala.collection.immutable.RedBlackTree;
import scala.collection.immutable.RedBlackTree$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.TreeMap;
import scala.collection.mutable.ReusableBuilder;
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
    public <K, V> TreeMap<K, V> empty(Ordering<K> evidence$1) {
        return new TreeMap(evidence$1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <K, V> TreeMap<K, V> from(IterableOnce<Tuple2<K, V>> it, Ordering<K> ordering) {
        if (it instanceof TreeMap) {
            TreeMap treeMap = (TreeMap)it;
            Ordering<K> ordering2 = ordering;
            Ordering ordering3 = treeMap.ordering();
            if (!(ordering2 != null ? !ordering2.equals(ordering3) : ordering3 != null)) {
                return treeMap;
            }
        }
        if (it instanceof SortedMap) {
            SortedMap sortedMap = (SortedMap)it;
            Ordering<K> ordering4 = ordering;
            Ordering ordering5 = sortedMap.ordering();
            if (!(ordering4 != null ? !ordering4.equals(ordering5) : ordering5 != null)) {
                return new TreeMap(RedBlackTree$.MODULE$.fromOrderedEntries(sortedMap.iterator(), sortedMap.size()), ordering);
            }
        }
        RedBlackTree.Tree<void, void> t2 = null;
        Iterator<Tuple2<K, V>> i = it.iterator();
        while (i.hasNext()) {
            void var11_11;
            void var10_10;
            Tuple2<K, V> tuple2 = i.next();
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            K k = tuple2._1();
            V v = tuple2._2();
            t2 = RedBlackTree$.MODULE$.update(t2, var10_10, var11_11, true, ordering);
        }
        return new TreeMap(t2, ordering);
    }

    @Override
    public <K, V> ReusableBuilder<Tuple2<K, V>, TreeMap<K, V>> newBuilder(Ordering<K> ordering) {
        return new TreeMap.TreeMapBuilder(ordering);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TreeMap$.class);
    }

    private TreeMap$() {
    }
}

