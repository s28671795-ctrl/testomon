/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.MapFactory;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Map;
import scala.collection.immutable.Map$EmptyMap$;
import scala.collection.immutable.MapBuilderImpl;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.runtime.ModuleSerializationProxy;

public final class Map$
implements MapFactory<Map> {
    public static final Map$ MODULE$ = new Map$();
    private static final long serialVersionUID = 3L;

    @Override
    public Object apply(Seq elems) {
        return MapFactory.apply$(this, elems);
    }

    @Override
    public <K, V> Factory<Tuple2<K, V>, Map<K, V>> mapFactory() {
        return MapFactory.mapFactory$(this);
    }

    @Override
    public <K, V> Map<K, V> empty() {
        return Map$EmptyMap$.MODULE$;
    }

    @Override
    public <K, V> Map<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        if (it instanceof Iterable && ((Iterable)it).isEmpty()) {
            return Map$EmptyMap$.MODULE$;
        }
        if (it instanceof Map) {
            return (Map)it;
        }
        return (Map)new MapBuilderImpl().addAll((IterableOnce)it).result();
    }

    @Override
    public <K, V> Builder<Tuple2<K, V>, Map<K, V>> newBuilder() {
        return new MapBuilderImpl();
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Map$.class);
    }

    private Map$() {
    }
}

