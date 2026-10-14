/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.$less$colon$less$;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Tuple2;
import scala.collection.AbstractMapView;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.MapFactory;
import scala.collection.MapOps;
import scala.collection.MapView;
import scala.collection.MapViewFactory;
import scala.collection.View;
import scala.collection.View$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.HashMap$;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

public final class MapView$
implements MapViewFactory {
    public static final MapView$ MODULE$ = new MapView$();
    private static final MapView<Object, Nothing$> EmptyMapView = new AbstractMapView<Object, Nothing$>(){

        public Option<Nothing$> get(Object key) {
            return None$.MODULE$;
        }

        public Iterator<Nothing$> iterator() {
            return Iterator$.scala$collection$Iterator$$_empty;
        }

        public int knownSize() {
            return 0;
        }

        public boolean isEmpty() {
            return true;
        }

        public MapView<Object, Nothing$> filterKeys(Function1<Object, Object> p) {
            return this;
        }

        public <W> MapView<Object, Nothing$> mapValues(Function1<Nothing$, W> f) {
            return this;
        }

        public MapView<Object, Nothing$> filter(Function1<Tuple2<Object, Nothing$>, Object> pred) {
            return this;
        }

        public MapView<Object, Nothing$> filterNot(Function1<Tuple2<Object, Nothing$>, Object> pred) {
            return this;
        }

        public Tuple2<MapView<Object, Nothing$>, MapView<Object, Nothing$>> partition(Function1<Tuple2<Object, Nothing$>, Object> p) {
            return new Tuple2<MapView<Object, Nothing$>, MapView<Object, Nothing$>>(this, this);
        }
    };

    @Override
    public <K, V> Factory<Tuple2<K, V>, View<Tuple2<K, V>>> mapFactory() {
        return MapFactory.mapFactory$(this);
    }

    private MapView<Object, Nothing$> EmptyMapView() {
        return EmptyMapView;
    }

    @Override
    public <X, Y> Builder<Tuple2<X, Y>, MapView<X, Y>> newBuilder() {
        return HashMap$.MODULE$.newBuilder().mapResult((Function1<HashMap, MapView> & Serializable)x$1 -> x$1.view());
    }

    @Override
    public <K, V> MapView<K, V> empty() {
        return this.EmptyMapView();
    }

    @Override
    public <K, V> View<Tuple2<K, V>> from(IterableOnce<Tuple2<K, V>> it) {
        return View$.MODULE$.from((IterableOnce)it);
    }

    @Override
    public <K, V> MapView<K, V> from(MapOps<K, V, ?, Object> it) {
        if (it instanceof MapView) {
            return (MapView)it;
        }
        return new MapView.Id<K, V>(it);
    }

    @Override
    public <K, V> MapView<K, V> apply(Seq<Tuple2<K, V>> elems) {
        return this.from(elems.toMap($less$colon$less$.MODULE$.refl()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(MapView$.class);
    }

    private MapView$() {
    }
}

