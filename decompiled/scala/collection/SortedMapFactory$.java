/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Tuple2;
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.SortedMapFactory;
import scala.math.Ordering;
import scala.runtime.ModuleSerializationProxy;

public final class SortedMapFactory$
implements Serializable {
    public static final SortedMapFactory$ MODULE$ = new SortedMapFactory$();

    public <K, V, CC> Factory<Tuple2<K, V>, CC> toFactory(SortedMapFactory<CC> factory2, Ordering<K> evidence$41) {
        return new SortedMapFactory.ToFactory(factory2, evidence$41);
    }

    public <K, V, CC> BuildFrom<Object, Tuple2<K, V>, CC> toBuildFrom(SortedMapFactory<CC> factory2, Ordering<K> evidence$43) {
        return new SortedMapFactory.SortedMapFactoryToBuildFrom(factory2, evidence$43);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SortedMapFactory$.class);
    }

    private SortedMapFactory$() {
    }
}

