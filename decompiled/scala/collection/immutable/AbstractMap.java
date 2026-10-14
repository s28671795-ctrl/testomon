/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.$less$colon$less;
import scala.Function1;
import scala.Function2;
import scala.Tuple2;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.MapFactory;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Map;
import scala.collection.immutable.MapOps;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00152QAA\u0002\u0002\u0002)AQA\t\u0001\u0005\u0002\r\u00121\"\u00112tiJ\f7\r^'ba*\u0011A!B\u0001\nS6lW\u000f^1cY\u0016T!AB\u0004\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\t\u0003\u0015\u00198-\u00197b\u0007\u0001)2aC\t\u001d'\r\u0001AB\b\t\u0005\u001b9y1$D\u0001\u0006\u0013\t\u0011Q\u0001\u0005\u0002\u0011#1\u0001A!\u0002\n\u0001\u0005\u0004\u0019\"!A&\u0012\u0005QA\u0002CA\u000b\u0017\u001b\u00059\u0011BA\f\b\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!F\r\n\u0005i9!aA!osB\u0011\u0001\u0003\b\u0003\u0007;\u0001!)\u0019A\n\u0003\u0003Y\u0003Ba\b\u0011\u001075\t1!\u0003\u0002\"\u0007\t\u0019Q*\u00199\u0002\rqJg.\u001b;?)\u0005!\u0003\u0003B\u0010\u0001\u001fm\u0001")
public abstract class AbstractMap<K, V>
extends scala.collection.AbstractMap<K, V>
implements Map<K, V> {
    @Override
    public MapFactory<Map> mapFactory() {
        return Map.mapFactory$(this);
    }

    @Override
    public final <K2, V2> Map<K2, V2> toMap($less$colon$less<Tuple2<K, V>, Tuple2<K2, V2>> ev) {
        return Map.toMap$(this, ev);
    }

    @Override
    public <V1> Map<K, V1> withDefault(Function1<K, V1> d) {
        return Map.withDefault$(this, d);
    }

    @Override
    public <V1> Map<K, V1> withDefaultValue(V1 d) {
        return Map.withDefaultValue$(this, d);
    }

    @Override
    public final MapOps $minus(Object key) {
        return MapOps.$minus$(this, key);
    }

    @Override
    public MapOps $minus(Object key1, Object key2, Seq keys2) {
        return MapOps.$minus$(this, key1, key2, keys2);
    }

    @Override
    public MapOps removedAll(IterableOnce keys2) {
        return MapOps.removedAll$(this, keys2);
    }

    @Override
    public final MapOps $minus$minus(IterableOnce keys2) {
        return MapOps.$minus$minus$(this, keys2);
    }

    @Override
    public MapOps updatedWith(Object key, Function1 remappingFunction) {
        return MapOps.updatedWith$(this, key, remappingFunction);
    }

    @Override
    public MapOps $plus(Tuple2 kv) {
        return MapOps.$plus$(this, kv);
    }

    @Override
    public MapOps transform(Function2 f) {
        return MapOps.transform$(this, f);
    }

    @Override
    public Set<K> keySet() {
        return MapOps.keySet$(this);
    }

    @Override
    public IterableFactory<Iterable> iterableFactory() {
        return Iterable.iterableFactory$(this);
    }
}

