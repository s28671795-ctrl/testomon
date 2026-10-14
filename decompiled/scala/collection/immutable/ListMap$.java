/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Function2;
import scala.MatchError;
import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Map;
import scala.collection.MapFactory;
import scala.collection.MapView;
import scala.collection.immutable.ListMap;
import scala.collection.immutable.ListMap$EmptyListMap$;
import scala.collection.immutable.ListMapBuilder;
import scala.collection.immutable.Seq;
import scala.collection.mutable.LinkedHashMap;
import scala.collection.mutable.ReusableBuilder;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

public final class ListMap$
implements MapFactory<ListMap> {
    public static final ListMap$ MODULE$ = new ListMap$();
    private static final long serialVersionUID = 3L;

    @Override
    public Object apply(Seq elems) {
        return MapFactory.apply$(this, elems);
    }

    @Override
    public <K, V> Factory<Tuple2<K, V>, ListMap<K, V>> mapFactory() {
        return MapFactory.mapFactory$(this);
    }

    @Override
    public <K, V> ListMap<K, V> empty() {
        return ListMap$EmptyListMap$.MODULE$;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <K, V> ListMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        if (it instanceof ListMap) {
            return (ListMap)it;
        }
        if (it instanceof LinkedHashMap) {
            LinkedHashMap linkedHashMap = (LinkedHashMap)it;
            ListMap current = ListMap$EmptyListMap$.MODULE$;
            for (LinkedHashMap.LinkedEntry firstEntry = linkedHashMap._firstEntry(); firstEntry != null; firstEntry = firstEntry.later()) {
                current = new ListMap.Node<Object, Nothing$>(firstEntry.key(), (Nothing$)firstEntry.value(), current);
            }
            return current;
        }
        if (it instanceof Map ? true : it instanceof MapView) {
            ListMap current = ListMap$EmptyListMap$.MODULE$;
            Iterator<Tuple2<K, V>> iter = it.iterator();
            while (iter.hasNext()) {
                void var9_9;
                void var8_8;
                Tuple2<K, V> tuple2 = iter.next();
                if (tuple2 == null) {
                    throw new MatchError((Object)null);
                }
                K k = tuple2._1();
                V v = tuple2._2();
                current = new ListMap.Node<Object, Nothing$>(var8_8, (Nothing$)var9_9, current);
            }
            return current;
        }
        return (ListMap)new ListMapBuilder().addAll((IterableOnce)it).result();
    }

    @Override
    public <K, V> ReusableBuilder<Tuple2<K, V>, ListMap<K, V>> newBuilder() {
        return new ListMapBuilder();
    }

    public <K, V, Z> Z scala$collection$immutable$ListMap$$foldRightInternal(ListMap<K, V> map, Z prevValue, Function2<Tuple2<K, V>, Z, Z> op) {
        while (!map.isEmpty()) {
            Z z = op.apply((Tuple2<K, V>)map.last(), prevValue);
            prevValue = z;
            map = (ListMap)map.init();
        }
        return prevValue;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ListMap$.class);
    }

    private ListMap$() {
    }
}

