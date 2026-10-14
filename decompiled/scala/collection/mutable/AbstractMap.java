/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.MapFactory;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.Map;
import scala.collection.mutable.MapOps;
import scala.collection.mutable.Shrinkable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00152QAA\u0002\u0002\u0002)AQA\t\u0001\u0005\u0002\r\u00121\"\u00112tiJ\f7\r^'ba*\u0011A!B\u0001\b[V$\u0018M\u00197f\u0015\t1q!\u0001\u0006d_2dWm\u0019;j_:T\u0011\u0001C\u0001\u0006g\u000e\fG.Y\u0002\u0001+\rY\u0011\u0003H\n\u0004\u00011q\u0002\u0003B\u0007\u000f\u001fmi\u0011!B\u0005\u0003\u0005\u0015\u0001\"\u0001E\t\r\u0001\u0011)!\u0003\u0001b\u0001'\t\t1*\u0005\u0002\u00151A\u0011QCF\u0007\u0002\u000f%\u0011qc\u0002\u0002\b\u001d>$\b.\u001b8h!\t)\u0012$\u0003\u0002\u001b\u000f\t\u0019\u0011I\\=\u0011\u0005AaB!B\u000f\u0001\u0005\u0004\u0019\"!\u0001,\u0011\t}\u0001sbG\u0007\u0002\u0007%\u0011\u0011e\u0001\u0002\u0004\u001b\u0006\u0004\u0018A\u0002\u001fj]&$h\bF\u0001%!\u0011y\u0002aD\u000e")
public abstract class AbstractMap<K, V>
extends scala.collection.AbstractMap<K, V>
implements Map<K, V> {
    @Override
    public MapFactory<Map> mapFactory() {
        return Map.mapFactory$(this);
    }

    @Override
    public Map<K, V> withDefault(Function1<K, V> d) {
        return Map.withDefault$(this, d);
    }

    @Override
    public Map<K, V> withDefaultValue(V d) {
        return Map.withDefaultValue$(this, d);
    }

    @Override
    public MapOps result() {
        return MapOps.result$(this);
    }

    @Override
    public final MapOps $minus(Object key) {
        return MapOps.$minus$(this, key);
    }

    @Override
    public final MapOps $minus(Object key1, Object key2, Seq keys2) {
        return MapOps.$minus$(this, key1, key2, keys2);
    }

    @Override
    public Option<V> put(K key, V value) {
        return MapOps.put$(this, key, value);
    }

    @Override
    public void update(K key, V value) {
        MapOps.update$(this, key, value);
    }

    @Override
    public Option<V> updateWith(K key, Function1<Option<V>, Option<V>> remappingFunction) {
        return MapOps.updateWith$(this, key, remappingFunction);
    }

    @Override
    public V getOrElseUpdate(K key, Function0<V> op) {
        return (V)MapOps.getOrElseUpdate$(this, key, op);
    }

    @Override
    public Option<V> remove(K key) {
        return MapOps.remove$(this, key);
    }

    @Override
    public void clear() {
        MapOps.clear$(this);
    }

    @Override
    public MapOps clone() {
        return MapOps.clone$(this);
    }

    @Override
    public final MapOps<K, V, Map, Map<K, V>> retain(Function2<K, V, Object> p) {
        return MapOps.retain$(this, p);
    }

    @Override
    public MapOps<K, V, Map, Map<K, V>> filterInPlace(Function2<K, V, Object> p) {
        return MapOps.filterInPlace$(this, p);
    }

    @Override
    public final MapOps<K, V, Map, Map<K, V>> transform(Function2<K, V, V> f) {
        return MapOps.transform$(this, f);
    }

    @Override
    public MapOps<K, V, Map, Map<K, V>> mapValuesInPlace(Function2<K, V, V> f) {
        return MapOps.mapValuesInPlace$(this, f);
    }

    @Override
    public MapOps updated(Object key, Object value) {
        return MapOps.updated$(this, key, value);
    }

    @Override
    public int knownSize() {
        return MapOps.knownSize$(this);
    }

    @Override
    public final Shrinkable<K> $minus$eq(K elem) {
        return Shrinkable.$minus$eq$(this, elem);
    }

    @Override
    public Shrinkable<K> $minus$eq(K elem1, K elem2, Seq<K> elems) {
        return Shrinkable.$minus$eq$(this, elem1, elem2, elems);
    }

    @Override
    public Shrinkable<K> subtractAll(IterableOnce<K> xs) {
        return Shrinkable.subtractAll$(this, xs);
    }

    @Override
    public final Shrinkable<K> $minus$minus$eq(IterableOnce<K> xs) {
        return Shrinkable.$minus$minus$eq$(this, xs);
    }

    @Override
    public void sizeHint(int size) {
        Builder.sizeHint$(this, size);
    }

    @Override
    public final void sizeHint(IterableOnce<?> coll, int delta) {
        Builder.sizeHint$(this, coll, delta);
    }

    @Override
    public final int sizeHint$default$2() {
        return Builder.sizeHint$default$2$(this);
    }

    @Override
    public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
        Builder.sizeHintBounded$(this, size, boundingColl);
    }

    @Override
    public <NewTo> Builder<Tuple2<K, V>, NewTo> mapResult(Function1<Map<K, V>, NewTo> f) {
        return Builder.mapResult$(this, f);
    }

    @Override
    public final Growable $plus$eq(Object elem) {
        return Growable.$plus$eq$(this, elem);
    }

    @Override
    public final Growable $plus$eq(Object elem1, Object elem2, Seq elems) {
        return Growable.$plus$eq$(this, elem1, elem2, elems);
    }

    @Override
    public Growable<Tuple2<K, V>> addAll(IterableOnce<Tuple2<K, V>> xs) {
        return Growable.addAll$(this, xs);
    }

    @Override
    public final Growable<Tuple2<K, V>> $plus$plus$eq(IterableOnce<Tuple2<K, V>> xs) {
        return Growable.$plus$plus$eq$(this, xs);
    }

    @Override
    public /* synthetic */ Object scala$collection$mutable$Cloneable$$super$clone() {
        return super.clone();
    }

    @Override
    public IterableFactory<scala.collection.mutable.Iterable> iterableFactory() {
        return scala.collection.mutable.Iterable.iterableFactory$(this);
    }
}

