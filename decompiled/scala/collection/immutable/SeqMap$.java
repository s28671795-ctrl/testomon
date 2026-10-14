/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.MapFactory;
import scala.collection.immutable.Seq;
import scala.collection.immutable.SeqMap;
import scala.collection.immutable.SeqMap$EmptySeqMap$;
import scala.collection.mutable.Builder;
import scala.runtime.ModuleSerializationProxy;

public final class SeqMap$
implements MapFactory<SeqMap> {
    public static final SeqMap$ MODULE$ = new SeqMap$();

    @Override
    public Object apply(Seq elems) {
        return MapFactory.apply$(this, elems);
    }

    @Override
    public <K, V> Factory<Tuple2<K, V>, SeqMap<K, V>> mapFactory() {
        return MapFactory.mapFactory$(this);
    }

    @Override
    public <K, V> SeqMap<K, V> empty() {
        return SeqMap$EmptySeqMap$.MODULE$;
    }

    @Override
    public <K, V> SeqMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        if (it instanceof SeqMap) {
            return (SeqMap)it;
        }
        return (SeqMap)new SeqMap.SeqMapBuilderImpl().addAll((IterableOnce)it).result();
    }

    @Override
    public <K, V> Builder<Tuple2<K, V>, SeqMap<K, V>> newBuilder() {
        return new SeqMap.SeqMapBuilderImpl();
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SeqMap$.class);
    }

    private SeqMap$() {
    }
}

