/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.SortedMapFactory;
import scala.collection.immutable.SortedMap;
import scala.collection.immutable.TreeMap$;
import scala.math.Ordering;
import scala.package$;
import scala.runtime.ModuleSerializationProxy;

public final class SortedMap$
extends SortedMapFactory.Delegate<SortedMap> {
    public static final SortedMap$ MODULE$ = new SortedMap$();
    private static final long serialVersionUID = 3L;

    @Override
    public <K, V> SortedMap<K, V> from(IterableOnce<Tuple2<K, V>> it, Ordering<K> evidence$1) {
        if (it instanceof SortedMap) {
            SortedMap sortedMap = (SortedMap)it;
            if (package$.MODULE$.Ordering() == null) {
                throw null;
            }
            Ordering<K> ordering = evidence$1;
            Ordering ordering2 = sortedMap.ordering();
            if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                return sortedMap;
            }
        }
        return (SortedMap)super.from(it, evidence$1);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SortedMap$.class);
    }

    private SortedMap$() {
        super(TreeMap$.MODULE$);
    }
}

