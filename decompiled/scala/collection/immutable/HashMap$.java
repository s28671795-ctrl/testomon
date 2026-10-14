/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.MapFactory;
import scala.collection.immutable.HashMap;
import scala.collection.immutable.HashMapBuilder;
import scala.collection.immutable.MapNode$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ReusableBuilder;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

public final class HashMap$
implements MapFactory<HashMap> {
    public static final HashMap$ MODULE$ = new HashMap$();
    private static final long serialVersionUID = 3L;
    private static final transient HashMap<Nothing$, Nothing$> EmptyMap = new HashMap(MapNode$.MODULE$.empty());

    @Override
    public Object apply(Seq elems) {
        return MapFactory.apply$(this, elems);
    }

    @Override
    public <K, V> Factory<Tuple2<K, V>, HashMap<K, V>> mapFactory() {
        return MapFactory.mapFactory$(this);
    }

    private final HashMap<Nothing$, Nothing$> EmptyMap() {
        return EmptyMap;
    }

    @Override
    public <K, V> HashMap<K, V> empty() {
        return this.EmptyMap();
    }

    @Override
    public <K, V> HashMap<K, V> from(IterableOnce<Tuple2<K, V>> source2) {
        if (source2 instanceof HashMap) {
            return (HashMap)source2;
        }
        return (HashMap)new HashMapBuilder().addAll((IterableOnce)source2).result();
    }

    @Override
    public <K, V> ReusableBuilder<Tuple2<K, V>, HashMap<K, V>> newBuilder() {
        return new HashMapBuilder();
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(HashMap$.class);
    }

    private HashMap$() {
    }
}

