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
import scala.collection.immutable.IntMap;
import scala.collection.immutable.IntMap$;
import scala.collection.immutable.IntMap$Nil$;
import scala.collection.immutable.IntMap$ToBuildFrom$;
import scala.collection.immutable.IntMap$ToFactory$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.ImmutableBuilder;
import scala.runtime.ModuleSerializationProxy;

public final class IntMap$
implements Serializable {
    public static final IntMap$ MODULE$ = new IntMap$();

    public <T> IntMap<T> empty() {
        return IntMap$Nil$.MODULE$;
    }

    public <T> IntMap<T> singleton(int key, T value) {
        return new IntMap.Tip<T>(key, value);
    }

    public <T> IntMap<T> apply(Seq<Tuple2<Object, T>> elems) {
        return elems.foldLeft(IntMap$Nil$.MODULE$, (Function2<IntMap, Tuple2, IntMap> & Serializable)(x, y) -> x.updated(y._1$mcI$sp(), y._2()));
    }

    public <V> IntMap<V> from(IterableOnce<Tuple2<Object, V>> coll) {
        return (IntMap)((Builder)new ImmutableBuilder<Tuple2<Object, V>, IntMap<V>>(){

            public anon.1 addOne(Tuple2<Object, V> elem) {
                this.elems_$eq(((IntMap)this.elems()).$plus(elem));
                return this;
            }
        }.addAll(coll)).result();
    }

    public <V> Builder<Tuple2<Object, V>, IntMap<V>> newBuilder() {
        return new /* invalid duplicate definition of identical inner class */;
    }

    public <V> Factory<Tuple2<Object, V>, IntMap<V>> toFactory(IntMap$ dummy) {
        return IntMap$ToFactory$.MODULE$;
    }

    public <V> BuildFrom<Object, Tuple2<Object, V>, IntMap<V>> toBuildFrom(IntMap$ factory2) {
        return IntMap$ToBuildFrom$.MODULE$;
    }

    public <V> Factory<Tuple2<Object, V>, IntMap<V>> iterableFactory() {
        return IntMap$ToFactory$.MODULE$;
    }

    public <V> BuildFrom<IntMap<?>, Tuple2<Object, V>, IntMap<V>> buildFromIntMap() {
        return IntMap$ToBuildFrom$.MODULE$;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(IntMap$.class);
    }

    private IntMap$() {
    }
}

