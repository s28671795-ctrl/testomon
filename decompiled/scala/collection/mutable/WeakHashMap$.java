/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.MapFactory;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.GrowableBuilder;
import scala.collection.mutable.WeakHashMap;
import scala.runtime.ModuleSerializationProxy;

public final class WeakHashMap$
implements MapFactory<WeakHashMap> {
    public static final WeakHashMap$ MODULE$ = new WeakHashMap$();
    private static final long serialVersionUID = 3L;

    @Override
    public Object apply(Seq elems) {
        return MapFactory.apply$(this, elems);
    }

    @Override
    public <K, V> Factory<Tuple2<K, V>, WeakHashMap<K, V>> mapFactory() {
        return MapFactory.mapFactory$(this);
    }

    @Override
    public <K, V> WeakHashMap<K, V> empty() {
        return new WeakHashMap();
    }

    @Override
    public <K, V> WeakHashMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        return (WeakHashMap)new WeakHashMap().addAll(it);
    }

    @Override
    public <K, V> Builder<Tuple2<K, V>, WeakHashMap<K, V>> newBuilder() {
        return new GrowableBuilder<Tuple2<K, V>, WeakHashMap<K, V>>(new WeakHashMap());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(WeakHashMap$.class);
    }

    private WeakHashMap$() {
    }
}

