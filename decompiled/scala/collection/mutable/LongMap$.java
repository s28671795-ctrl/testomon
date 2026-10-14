/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import scala.Function1;
import scala.MatchError;
import scala.Tuple2;
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.collection.mutable.LongMap;
import scala.collection.mutable.LongMap$ToBuildFrom$;
import scala.collection.mutable.LongMap$ToFactory$;
import scala.collection.mutable.ReusableBuilder;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;

public final class LongMap$
implements Serializable {
    public static final LongMap$ MODULE$ = new LongMap$();
    private static final Function1<Object, Nothing$> scala$collection$mutable$LongMap$$exceptionDefault = (Function1<Object, Nothing$> & Serializable)k -> LongMap$.$anonfun$exceptionDefault$1(BoxesRunTime.unboxToLong(k));

    private final int IndexMask() {
        return 0x3FFFFFFF;
    }

    private final int MissingBit() {
        return Integer.MIN_VALUE;
    }

    private final int VacantBit() {
        return 0x40000000;
    }

    private final int MissVacant() {
        return -1073741824;
    }

    public Function1<Object, Nothing$> scala$collection$mutable$LongMap$$exceptionDefault() {
        return scala$collection$mutable$LongMap$$exceptionDefault;
    }

    public <V> LongMap<V> apply(Seq<Tuple2<Object, V>> elems) {
        return this.buildFromIterableOnce(elems);
    }

    private <V> LongMap<V> buildFromIterableOnce(IterableOnce<Tuple2<Object, V>> elems) {
        int sz = elems.knownSize();
        if (sz < 0) {
            sz = 4;
        }
        LongMap lm = new LongMap(sz * 2);
        elems.iterator().foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> {
            LongMap$.$anonfun$buildFromIterableOnce$1(lm, x0$1);
            return BoxedUnit.UNIT;
        });
        if (lm.size() < sz >> 3) {
            lm.repack();
        }
        return lm;
    }

    public <V> LongMap<V> empty() {
        return new LongMap();
    }

    public <V> LongMap<V> withDefault(Function1<Object, V> function1) {
        return new LongMap<V>(function1);
    }

    public <V> LongMap<V> from(IterableOnce<Tuple2<Object, V>> source2) {
        if (source2 instanceof LongMap) {
            return ((LongMap)source2).clone();
        }
        return this.buildFromIterableOnce(source2);
    }

    public <V> ReusableBuilder<Tuple2<Object, V>, LongMap<V>> newBuilder() {
        return new LongMap.LongMapBuilder();
    }

    public <V> LongMap<V> fromZip(long[] keys2, Object values2) {
        int sz = Math.min(keys2.length, Array.getLength(values2));
        LongMap<Object> lm = new LongMap<Object>(sz * 2);
        for (int i = 0; i < sz; ++i) {
            lm.update(keys2[i], ScalaRunTime$.MODULE$.array_apply(values2, i));
        }
        if (lm.size() < sz >> 3) {
            lm.repack();
        }
        return lm;
    }

    public <V> LongMap<V> fromZip(Iterable<Object> keys2, Iterable<V> values2) {
        int sz = Math.min(keys2.size(), values2.size());
        LongMap lm = new LongMap(sz * 2);
        Iterator ki = keys2.iterator();
        Iterator vi = values2.iterator();
        while (ki.hasNext() && vi.hasNext()) {
            lm.update(BoxesRunTime.unboxToLong(ki.next()), vi.next());
        }
        if (lm.size() < sz >> 3) {
            lm.repack();
        }
        return lm;
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

    public static final /* synthetic */ Nothing$ $anonfun$exceptionDefault$1(long k) {
        throw new NoSuchElementException(Long.toString(k));
    }

    public static final /* synthetic */ void $anonfun$buildFromIterableOnce$1(LongMap lm$2, Tuple2 x0$1) {
        if (x0$1 != null) {
            long k = x0$1._1$mcJ$sp();
            Object v = x0$1._2();
            lm$2.update(k, v);
            return;
        }
        throw new MatchError((Object)null);
    }

    private LongMap$() {
    }
}

