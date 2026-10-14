/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Tuple2;
import scala.collection.BitSet;
import scala.collection.BitSetOps;
import scala.collection.BuildFrom;
import scala.collection.BuildFromLowPriority1;
import scala.collection.BuildFromLowPriority2;
import scala.collection.Factory;
import scala.collection.Factory$;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Map;
import scala.collection.SortedMap;
import scala.collection.SortedSet;
import scala.collection.View;
import scala.collection.View$;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.WrappedString;
import scala.collection.immutable.WrappedString$;
import scala.collection.mutable.Builder;
import scala.math.Ordering;
import scala.reflect.ClassTag;

public final class BuildFrom$
implements BuildFromLowPriority1 {
    public static final BuildFrom$ MODULE$ = new BuildFrom$();
    private static final BuildFrom<String, Object, String> buildFromString = new BuildFrom<String, Object, String>(){

        public Builder apply(Object from) {
            return BuildFrom.apply$(this, from);
        }

        public Factory toFactory(Object from) {
            return BuildFrom.toFactory$(this, from);
        }

        public String fromSpecific(String from, IterableOnce<Object> it) {
            return Factory$.MODULE$.stringFactory().fromSpecific(it);
        }

        public Builder<Object, String> newBuilder(String from) {
            return Factory$.MODULE$.stringFactory().newBuilder();
        }
    };
    private static final BuildFrom<WrappedString, Object, WrappedString> buildFromWrappedString = new BuildFrom<WrappedString, Object, WrappedString>(){

        public Builder apply(Object from) {
            return BuildFrom.apply$(this, from);
        }

        public Factory toFactory(Object from) {
            return BuildFrom.toFactory$(this, from);
        }

        public WrappedString fromSpecific(WrappedString from, IterableOnce<Object> it) {
            return WrappedString$.MODULE$.fromSpecific((IterableOnce)it);
        }

        public Builder<Object, WrappedString> newBuilder(WrappedString from) {
            return WrappedString$.MODULE$.newBuilder();
        }
    };

    @Override
    public <CC extends SortedSet<Object>, A0, A> BuildFrom<CC, A, CC> buildFromSortedSetOps(Ordering<A> evidence$3) {
        return BuildFromLowPriority1.buildFromSortedSetOps$(this, evidence$3);
    }

    @Override
    public <A> BuildFrom<String, A, IndexedSeq<A>> fallbackStringCanBuildFrom() {
        return BuildFromLowPriority1.fallbackStringCanBuildFrom$(this);
    }

    @Override
    public <CC extends Iterable<Object>, A0, A> BuildFrom<CC, A, CC> buildFromIterableOps() {
        return BuildFromLowPriority2.buildFromIterableOps$(this);
    }

    @Override
    public <A> BuildFrom<Iterator<?>, A, Iterator<A>> buildFromIterator() {
        return BuildFromLowPriority2.buildFromIterator$(this);
    }

    public <CC extends Map<Object, Object>, K0, V0, K, V> BuildFrom<CC, Tuple2<K, V>, CC> buildFromMapOps() {
        return new BuildFrom<CC, Tuple2<K, V>, CC>(){

            public Builder apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public Builder<Tuple2<K, V>, CC> newBuilder(CC from) {
                return from.mapFactory().newBuilder();
            }

            public CC fromSpecific(CC from, IterableOnce<Tuple2<K, V>> it) {
                return (CC)((Map)from.mapFactory().from(it));
            }
        };
    }

    public <CC extends SortedMap<Object, Object>, K0, V0, K, V> BuildFrom<CC, Tuple2<K, V>, CC> buildFromSortedMapOps(Ordering<K> evidence$1) {
        return new BuildFrom<CC, Tuple2<K, V>, CC>(evidence$1){
            private final Ordering evidence$1$1;

            public Builder apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public Builder<Tuple2<K, V>, CC> newBuilder(CC from) {
                return from.sortedMapFactory().newBuilder(this.evidence$1$1);
            }

            public CC fromSpecific(CC from, IterableOnce<Tuple2<K, V>> it) {
                return (CC)((SortedMap)from.sortedMapFactory().from(it, this.evidence$1$1));
            }
            {
                this.evidence$1$1 = evidence$1$1;
            }
        };
    }

    public <C extends BitSet & BitSetOps<C>> BuildFrom<C, Object, C> buildFromBitSet() {
        return new BuildFrom<C, Object, C>(){

            public Builder apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public C fromSpecific(C from, IterableOnce<Object> it) {
                return (C)((BitSet)from.bitSetFactory().fromSpecific(it));
            }

            public Builder<Object, C> newBuilder(C from) {
                return from.bitSetFactory().newBuilder();
            }
        };
    }

    public BuildFrom<String, Object, String> buildFromString() {
        return buildFromString;
    }

    public BuildFrom<WrappedString, Object, WrappedString> buildFromWrappedString() {
        return buildFromWrappedString;
    }

    public <A> BuildFrom<Object, A, Object> buildFromArray(ClassTag<A> evidence$2) {
        return new BuildFrom<Object, A, Object>(evidence$2){
            private final ClassTag evidence$2$1;

            public Builder<A, Object> apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory<A, Object> toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public Object fromSpecific(Object from, IterableOnce<A> it) {
                ClassTag arrayFactory_evidence$1 = this.evidence$2$1;
                Object var3_3 = null;
                return new Factory.ArrayFactory<A>(arrayFactory_evidence$1).fromSpecific(it);
            }

            public Builder<A, Object> newBuilder(Object from) {
                ClassTag arrayFactory_evidence$1 = this.evidence$2$1;
                Object var2_2 = null;
                return new Factory.ArrayFactory<A>(arrayFactory_evidence$1).newBuilder();
            }
            {
                this.evidence$2$1 = evidence$2$1;
            }
        };
    }

    public <A, B> BuildFrom<View<A>, B, View<B>> buildFromView() {
        return new BuildFrom<View<A>, B, View<B>>(){

            public Builder apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public View<B> fromSpecific(View<A> from, IterableOnce<B> it) {
                return View$.MODULE$.from((IterableOnce)it);
            }

            public Builder<B, View<B>> newBuilder(View<A> from) {
                return View$.MODULE$.newBuilder();
            }
        };
    }

    private BuildFrom$() {
    }
}

