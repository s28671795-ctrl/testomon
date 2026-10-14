/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Tuple2;
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.MapFactory;
import scala.collection.mutable.Builder;
import scala.runtime.ModuleSerializationProxy;

public final class MapFactory$
implements Serializable {
    public static final MapFactory$ MODULE$ = new MapFactory$();

    public <K, V, CC> Factory<Tuple2<K, V>, CC> toFactory(MapFactory<CC> factory2) {
        return new MapFactory.ToFactory(factory2);
    }

    public <K, V, CC> BuildFrom<Object, Tuple2<K, V>, CC> toBuildFrom(MapFactory<CC> factory2) {
        return new BuildFrom<Object, Tuple2<K, V>, CC>(factory2){
            private final MapFactory factory$2;

            public Builder<Tuple2<K, V>, CC> apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory<Tuple2<K, V>, CC> toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public CC fromSpecific(Object from, IterableOnce<Tuple2<K, V>> it) {
                return this.factory$2.from(it);
            }

            public Builder<Tuple2<K, V>, CC> newBuilder(Object from) {
                return this.factory$2.newBuilder();
            }
            {
                this.factory$2 = factory$2;
            }
        };
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(MapFactory$.class);
    }

    private MapFactory$() {
    }
}

