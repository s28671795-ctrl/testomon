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
import scala.collection.mutable.ListMap;
import scala.runtime.ModuleSerializationProxy;

public final class ListMap$
implements MapFactory<ListMap> {
    public static final ListMap$ MODULE$ = new ListMap$();
    private static final long serialVersionUID = 3L;

    @Override
    public Object apply(Seq elems) {
        return MapFactory.apply$(this, elems);
    }

    @Override
    public <K, V> Factory<Tuple2<K, V>, ListMap<K, V>> mapFactory() {
        return MapFactory.mapFactory$(this);
    }

    @Override
    public <K, V> ListMap<K, V> empty() {
        return new ListMap();
    }

    @Override
    public <K, V> ListMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        return (ListMap)new ListMap().addAll(it);
    }

    @Override
    public <K, V> Builder<Tuple2<K, V>, ListMap<K, V>> newBuilder() {
        return new GrowableBuilder<Tuple2<K, V>, ListMap<K, V>>(new ListMap());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ListMap$.class);
    }

    private ListMap$() {
    }
}

