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
import scala.collection.mutable.HashMap;
import scala.runtime.ModuleSerializationProxy;

public final class HashMap$
implements MapFactory<HashMap> {
    public static final HashMap$ MODULE$ = new HashMap$();
    private static final long serialVersionUID = 3L;

    @Override
    public Object apply(Seq elems) {
        return MapFactory.apply$(this, elems);
    }

    @Override
    public <K, V> Factory<Tuple2<K, V>, HashMap<K, V>> mapFactory() {
        return MapFactory.mapFactory$(this);
    }

    @Override
    public <K, V> HashMap<K, V> empty() {
        return new HashMap();
    }

    @Override
    public <K, V> HashMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        int k = it.knownSize();
        int cap = k > 0 ? (int)((double)(k + 1) / 0.75) : 16;
        return new HashMap(cap, 0.75).addAll((IterableOnce)it);
    }

    @Override
    public <K, V> Builder<Tuple2<K, V>, HashMap<K, V>> newBuilder() {
        double newBuilder_loadFactor = 0.75;
        int newBuilder_initialCapacity = 16;
        return new GrowableBuilder<Tuple2<K, V>, HashMap<K, V>>(newBuilder_initialCapacity, newBuilder_loadFactor){

            public void sizeHint(int size) {
                ((HashMap)this.elems()).sizeHint(size);
            }
        };
    }

    public <K, V> Builder<Tuple2<K, V>, HashMap<K, V>> newBuilder(int initialCapacity, double loadFactor) {
        return new /* invalid duplicate definition of identical inner class */;
    }

    public final double defaultLoadFactor() {
        return 0.75;
    }

    public final int defaultInitialCapacity() {
        return 16;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(HashMap$.class);
    }

    private HashMap$() {
    }
}

