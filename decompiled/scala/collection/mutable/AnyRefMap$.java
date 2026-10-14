/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import java.lang.reflect.Array;
import scala.Function1;
import scala.MatchError;
import scala.Tuple2;
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.collection.mutable.AnyRefMap;
import scala.collection.mutable.AnyRefMap$ToBuildFrom$;
import scala.collection.mutable.AnyRefMap$ToFactory$;
import scala.collection.mutable.Iterable;
import scala.collection.mutable.ReusableBuilder;
import scala.runtime.BoxedUnit;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;

public final class AnyRefMap$
implements Serializable {
    public static final AnyRefMap$ MODULE$ = new AnyRefMap$();
    private static final AnyRefMap.ExceptionDefault scala$collection$mutable$AnyRefMap$$exceptionDefault = new AnyRefMap.ExceptionDefault();

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

    public AnyRefMap.ExceptionDefault scala$collection$mutable$AnyRefMap$$exceptionDefault() {
        return scala$collection$mutable$AnyRefMap$$exceptionDefault;
    }

    public <K, V> AnyRefMap<K, V> apply(Seq<Tuple2<K, V>> elems) {
        return this.buildFromIterableOnce(elems);
    }

    public <K, V> ReusableBuilder<Tuple2<K, V>, AnyRefMap<K, V>> newBuilder() {
        return new AnyRefMap.AnyRefMapBuilder();
    }

    private <K, V> AnyRefMap<K, V> buildFromIterableOnce(IterableOnce<Tuple2<K, V>> elems) {
        int sz = elems.knownSize();
        if (sz < 0) {
            sz = 4;
        }
        AnyRefMap arm = new AnyRefMap(sz * 2);
        elems.iterator().foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> {
            AnyRefMap$.$anonfun$buildFromIterableOnce$1(arm, x0$1);
            return BoxedUnit.UNIT;
        });
        if (arm.size() < sz >> 3) {
            arm.repack();
        }
        return arm;
    }

    public <K, V> AnyRefMap<K, V> empty() {
        return new AnyRefMap();
    }

    public <K, V> AnyRefMap<K, V> withDefault(Function1<K, V> function1) {
        return new AnyRefMap<K, V>(function1);
    }

    public <K, V> AnyRefMap<K, V> from(IterableOnce<Tuple2<K, V>> source2) {
        if (source2 instanceof AnyRefMap) {
            return ((AnyRefMap)source2).clone();
        }
        return this.buildFromIterableOnce(source2);
    }

    public <K, V> AnyRefMap<K, V> fromZip(K[] keys2, Object values2) {
        int sz = Math.min(keys2.length, Array.getLength(values2));
        AnyRefMap<K, Object> arm = new AnyRefMap<K, Object>(sz * 2);
        for (int i = 0; i < sz; ++i) {
            arm.update(keys2[i], ScalaRunTime$.MODULE$.array_apply(values2, i));
        }
        if (arm.size() < sz >> 3) {
            arm.repack();
        }
        return arm;
    }

    public <K, V> AnyRefMap<K, V> fromZip(Iterable<K> keys2, Iterable<V> values2) {
        int sz = Math.min(keys2.size(), values2.size());
        AnyRefMap arm = new AnyRefMap(sz * 2);
        Iterator ki = keys2.iterator();
        Iterator vi = values2.iterator();
        while (ki.hasNext() && vi.hasNext()) {
            arm.update(ki.next(), vi.next());
        }
        if (arm.size() < sz >> 3) {
            arm.repack();
        }
        return arm;
    }

    public <K, V> Factory<Tuple2<K, V>, AnyRefMap<K, V>> toFactory(AnyRefMap$ dummy) {
        return AnyRefMap$ToFactory$.MODULE$;
    }

    public <K, V> BuildFrom<Object, Tuple2<K, V>, AnyRefMap<K, V>> toBuildFrom(AnyRefMap$ factory2) {
        return AnyRefMap$ToBuildFrom$.MODULE$;
    }

    public <K, V> Factory<Tuple2<K, V>, AnyRefMap<K, V>> iterableFactory() {
        return AnyRefMap$ToFactory$.MODULE$;
    }

    public <K, V> BuildFrom<AnyRefMap<?, ?>, Tuple2<K, V>, AnyRefMap<K, V>> buildFromAnyRefMap() {
        return AnyRefMap$ToBuildFrom$.MODULE$;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AnyRefMap$.class);
    }

    public static final /* synthetic */ void $anonfun$buildFromIterableOnce$1(AnyRefMap arm$3, Tuple2 x0$1) {
        if (x0$1 != null) {
            Object k = x0$1._1();
            Object v = x0$1._2();
            arm$3.update(k, v);
            return;
        }
        throw new MatchError((Object)null);
    }

    private AnyRefMap$() {
    }
}

