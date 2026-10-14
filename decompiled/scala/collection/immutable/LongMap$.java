/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Function2;
import scala.Tuple2;
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.immutable.LongMap;
import scala.collection.immutable.LongMap$;
import scala.collection.immutable.LongMap$Nil$;
import scala.collection.immutable.LongMap$ToBuildFrom$;
import scala.collection.immutable.LongMap$ToFactory$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.ImmutableBuilder;
import scala.runtime.ModuleSerializationProxy;

public final class LongMap$
implements Serializable {
    public static final LongMap$ MODULE$ = new LongMap$();

    public <T> LongMap<T> empty() {
        return LongMap$Nil$.MODULE$;
    }

    public <T> LongMap<T> singleton(long key, T value) {
        return new LongMap.Tip<T>(key, value);
    }

    public <T> LongMap<T> apply(Seq<Tuple2<Object, T>> elems) {
        return elems.foldLeft(LongMap$Nil$.MODULE$, (Function2<LongMap, Tuple2, LongMap> & Serializable)(x, y) -> x.updated(y._1$mcJ$sp(), y._2()));
    }

    public <V> LongMap<V> from(IterableOnce<Tuple2<Object, V>> coll) {
        return (LongMap)((Builder)new ImmutableBuilder<Tuple2<Object, V>, LongMap<V>>(){

            public anon.1 addOne(Tuple2<Object, V> elem) {
                this.elems_$eq(((LongMap)this.elems()).$plus(elem));
                return this;
            }
        }.addAll(coll)).result();
    }

    public <V> Builder<Tuple2<Object, V>, LongMap<V>> newBuilder() {
        return new /* invalid duplicate definition of identical inner class */;
    }

    public <V> Factory<Tuple2<Object, V>, LongMap<V>> toFactory(LongMap$ dummy) {
        return LongMap$ToFactory$.MODULE$;
    }

    public <V> BuildFrom<Object, Tuple2<Object, V>, LongMap<V>> toBuildFrom(LongMap$ factory2) {
        return LongMap$ToBuildFrom$.MODULE$;
    }

    public <V> Factory<Tuple2<Object, V>, LongMap<V>> iterableFactory() {
        return LongMap$ToFactory$.MODULE$;
    }

    public <V> BuildFrom<LongMap<?>, Tuple2<Object, V>, LongMap<V>> buildFromLongMap() {
        return LongMap$ToBuildFrom$.MODULE$;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(LongMap$.class);
    }

    private LongMap$() {
    }
}

