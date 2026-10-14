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
import scala.collection.mutable.OpenHashMap;
import scala.runtime.ModuleSerializationProxy;

public final class OpenHashMap$
implements MapFactory<OpenHashMap> {
    public static final OpenHashMap$ MODULE$ = new OpenHashMap$();
    private static final long serialVersionUID = 3L;

    @Override
    public Object apply(Seq elems) {
        return MapFactory.apply$(this, elems);
    }

    @Override
    public <K, V> Factory<Tuple2<K, V>, OpenHashMap<K, V>> mapFactory() {
        return MapFactory.mapFactory$(this);
    }

    @Override
    public <K, V> OpenHashMap<K, V> empty() {
        return new OpenHashMap();
    }

    @Override
    public <K, V> OpenHashMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        return (OpenHashMap)new OpenHashMap().addAll(it);
    }

    @Override
    public <K, V> Builder<Tuple2<K, V>, OpenHashMap<K, V>> newBuilder() {
        return new GrowableBuilder<Tuple2<K, V>, OpenHashMap<K, V>>(new OpenHashMap());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(OpenHashMap$.class);
    }

    private OpenHashMap$() {
    }
}

