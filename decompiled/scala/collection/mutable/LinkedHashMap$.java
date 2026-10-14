/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.MapFactory;
import scala.collection.immutable.Seq;
import scala.collection.mutable.GrowableBuilder;
import scala.collection.mutable.LinkedHashMap;
import scala.runtime.ModuleSerializationProxy;

public final class LinkedHashMap$
implements MapFactory<LinkedHashMap> {
    public static final LinkedHashMap$ MODULE$ = new LinkedHashMap$();
    private static final long serialVersionUID = 3L;

    @Override
    public Object apply(Seq elems) {
        return MapFactory.apply$(this, elems);
    }

    @Override
    public <K, V> Factory<Tuple2<K, V>, LinkedHashMap<K, V>> mapFactory() {
        return MapFactory.mapFactory$(this);
    }

    @Override
    public <K, V> LinkedHashMap<K, V> empty() {
        return new LinkedHashMap();
    }

    @Override
    public <K, V> LinkedHashMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        if (it instanceof LinkedHashMap) {
            return (LinkedHashMap)it;
        }
        return (LinkedHashMap)new LinkedHashMap().addAll(it);
    }

    @Override
    public <K, V> GrowableBuilder<Tuple2<K, V>, LinkedHashMap<K, V>> newBuilder() {
        return new GrowableBuilder<Tuple2<K, V>, LinkedHashMap<K, V>>(new LinkedHashMap());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(LinkedHashMap$.class);
    }

    private LinkedHashMap$() {
    }
}

