/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.MapFactory;
import scala.collection.immutable.HashMap$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector0$;
import scala.collection.immutable.VectorMap;
import scala.collection.immutable.VectorMapBuilder;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

public final class VectorMap$
implements MapFactory<VectorMap> {
    public static final VectorMap$ MODULE$ = new VectorMap$();
    private static final VectorMap<Nothing$, Nothing$> EmptyMap = new VectorMap(Vector0$.MODULE$, HashMap$.MODULE$.empty());

    @Override
    public Object apply(Seq elems) {
        return MapFactory.apply$(this, elems);
    }

    @Override
    public <K, V> Factory<Tuple2<K, V>, VectorMap<K, V>> mapFactory() {
        return MapFactory.mapFactory$(this);
    }

    @Override
    public <K, V> VectorMap<K, V> empty() {
        return EmptyMap;
    }

    @Override
    public <K, V> VectorMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        if (it instanceof VectorMap) {
            return (VectorMap)it;
        }
        return (VectorMap)((Builder)Growable.addAll$(new VectorMapBuilder(), it)).result();
    }

    @Override
    public <K, V> Builder<Tuple2<K, V>, VectorMap<K, V>> newBuilder() {
        return new VectorMapBuilder();
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(VectorMap$.class);
    }

    private VectorMap$() {
    }
}

