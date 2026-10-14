/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.MapOps;
import scala.collection.Set;
import scala.collection.SortedIterableFactory;
import scala.collection.SortedMapOps;
import scala.collection.SortedOps;
import scala.collection.SortedSetFactoryDefaults;
import scala.collection.SortedSetOps;
import scala.collection.immutable.AbstractSet;
import scala.collection.immutable.Map;
import scala.collection.immutable.SetOps;
import scala.collection.immutable.SortedSet;
import scala.collection.mutable.Builder;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005-da\u0002\b\u0010!\u0003\r\tA\u0006\u0005\u0006\u001b\u0002!\tA\u0014\u0005\u0006%\u00021\tb\u0015\u0005\u0006/\u00021\t\u0001\u0017\u0005\u00065\u0002!\te\u0017\u0004\u0005?\u0002A\u0001\rC\u0003m\u000b\u0011\u0005Q\u000eC\u0003p\u000b\u0011\u0005\u0001\u000fC\u0003y\u000b\u0011\u0005\u0011\u0010C\u0003}\u000b\u0011\u0005Q\u0010\u0003\u0004\u0000\u0001\u0019\u0005\u0011\u0011\u0001\u0005\b\u0003/\u0001AQIA\r\u0011\u001d\t)\u0004\u0001C!\u0003oAq!a\u0015\u0001\t\u0003\n)F\u0001\u0007T_J$X\rZ'ba>\u00038O\u0003\u0002\u0011#\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003%M\t!bY8mY\u0016\u001cG/[8o\u0015\u0005!\u0012!B:dC2\f7\u0001A\u000b\u0006/\tbsGM\n\u0005\u0001aa\"\n\u0005\u0002\u001a55\t1#\u0003\u0002\u001c'\t1\u0011I\\=SK\u001a\u0004b!\b\u0010!W9\nT\"A\b\n\u0005}y!AB'ba>\u00038\u000f\u0005\u0002\"E1\u0001A!B\u0012\u0001\u0005\u0004!#!A&\u0012\u0005\u0015B\u0003CA\r'\u0013\t93CA\u0004O_RD\u0017N\\4\u0011\u0005eI\u0013B\u0001\u0016\u0014\u0005\r\te.\u001f\t\u0003C1\"a!\f\u0001\u0005\u0006\u0004!#!\u0001,\u0011\u0005uy\u0013B\u0001\u0019\u0010\u0005\ri\u0015\r\u001d\t\u0003CI\"aa\r\u0001\u0005\u0006\u0004!$!A\"\u0012\u0005\u0015*\u0004CB\u000f\u0001A-2\u0014\u0007\u0005\u0002\"o\u00111\u0001\b\u0001CC\u0002e\u0012!aQ\"\u0016\u0007i\u00025)\u0005\u0002&wI\u0019AHP#\u0007\tu\u0002\u0001a\u000f\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\t\u0005;=z$\t\u0005\u0002\"\u0001\u0012)\u0011i\u000eb\u0001I\t\t\u0001\f\u0005\u0002\"\u0007\u00121Ai\u000eCC\u0002\u0011\u0012\u0011!\u0017\u0019\u0003\r\"\u0003b!\b\u0001@\u0005Z:\u0005CA\u0011I\t%Iu'!A\u0001\u0002\u000b\u0005AEA\u0002`IE\u0002ba\u0013'!WY\nT\"A\t\n\u00059\t\u0012A\u0002\u0013j]&$H\u0005F\u0001P!\tI\u0002+\u0003\u0002R'\t!QK\\5u\u0003\u0011\u0019w\u000e\u001c7\u0016\u0003Q\u00132!V\u0019W\r\u0011i\u0004\u0001\u0001+\u0011\t\u0005:\u0004eK\u0001\tk:\u001cxN\u001d;fIV\t\u0011\f\u0005\u0003\u001e_\u0001Z\u0013AB6fsN+G/F\u0001]!\riR\fI\u0005\u0003=>\u0011\u0011bU8si\u0016$7+\u001a;\u0003+%kW.\u001e;bE2,7*Z=T_J$X\rZ*fiN)Q!\u0019/eSB\u0019QD\u0019\u0011\n\u0005\r|!aC!cgR\u0014\u0018m\u0019;TKR\u0004\"!\u001a4\u000e\u0003\u0001I!a\u001a5\u0003\u0013\u001d+gnS3z'\u0016$\u0018BA\u0010\u0012!\t)'.\u0003\u0002l\u0019\nyq)\u001a8LKf\u001cvN\u001d;fIN+G/\u0001\u0004=S:LGO\u0010\u000b\u0002]B\u0011Q-B\u0001\ne\u0006tw-Z%na2$2\u0001X9w\u0011\u0015\u0011x\u00011\u0001t\u0003\u00111'o\\7\u0011\u0007e!\b%\u0003\u0002v'\t1q\n\u001d;j_:DQa^\u0004A\u0002M\fQ!\u001e8uS2\fA!\u001b8dYR\u0011AL\u001f\u0005\u0006w\"\u0001\r\u0001I\u0001\u0005K2,W.\u0001\u0003fq\u000edGC\u0001/\u007f\u0011\u0015Y\u0018\u00021\u0001!\u0003\u001d)\b\u000fZ1uK\u0012,B!a\u0001\u0002\nQ1\u0011QAA\b\u0003'\u0001R!I\u001c!\u0003\u000f\u00012!IA\u0005\t\u001d\tYA\u0003b\u0001\u0003\u001b\u0011!AV\u0019\u0012\u0005-B\u0003BBA\t\u0015\u0001\u0007\u0001%A\u0002lKfDq!!\u0006\u000b\u0001\u0004\t9!A\u0003wC2,X-A\u0003%a2,8/\u0006\u0003\u0002\u001c\u0005\u0005B\u0003BA\u000f\u0003G\u0001R!I\u001c!\u0003?\u00012!IA\u0011\t\u001d\tYa\u0003b\u0001\u0003\u001bAq!!\n\f\u0001\u0004\t9#\u0001\u0002lmB1\u0011$!\u000b!\u0003?I1!a\u000b\u0014\u0005\u0019!V\u000f\u001d7fe!\u001a1\"a\f\u0011\u0007e\t\t$C\u0002\u00024M\u0011a!\u001b8mS:,\u0017aC;qI\u0006$X\rZ,ji\",B!!\u000f\u0002BQ!\u00111HA))\u0011\ti$a\u0011\u0011\u000b\u0005:\u0004%a\u0010\u0011\u0007\u0005\n\t\u0005B\u0004\u0002\f1\u0011\r!!\u0004\t\u000f\u0005\u0015C\u00021\u0001\u0002H\u0005\t\"/Z7baBLgn\u001a$v]\u000e$\u0018n\u001c8\u0011\u000fe\tI%!\u0014\u0002P%\u0019\u00111J\n\u0003\u0013\u0019+hn\u0019;j_:\f\u0004cA\ruWA!\u0011\u0004^A \u0011\u0019\t\t\u0002\u0004a\u0001A\u0005IAO]1og\u001a|'/\\\u000b\u0005\u0003/\ni\u0006\u0006\u0003\u0002Z\u0005\u0005\u0004#B\u00118A\u0005m\u0003cA\u0011\u0002^\u00111\u0011qL\u0007C\u0002\u0011\u0012\u0011a\u0016\u0005\b\u0003Gj\u0001\u0019AA3\u0003\u00051\u0007cB\r\u0002h\u0001Z\u00131L\u0005\u0004\u0003S\u001a\"!\u0003$v]\u000e$\u0018n\u001c83\u0001")
public interface SortedMapOps<K, V, CC extends Map<Object, Object>, C extends SortedMapOps<K, V, CC, C>>
extends scala.collection.immutable.MapOps<K, V, Map, C>,
scala.collection.SortedMapOps<K, V, CC, C> {
    @Override
    public C coll();

    @Override
    public Map<K, V> unsorted();

    public static /* synthetic */ SortedSet keySet$(SortedMapOps $this) {
        return $this.keySet();
    }

    @Override
    default public SortedSet<K> keySet() {
        return new ImmutableKeySortedSet();
    }

    @Override
    public <V1> CC updated(K var1, V1 var2);

    public static /* synthetic */ Map $plus$(SortedMapOps $this, Tuple2 kv) {
        return $this.$plus(kv);
    }

    @Override
    default public <V1> CC $plus(Tuple2<K, V1> kv) {
        return this.updated(kv._1(), kv._2());
    }

    public static /* synthetic */ Map updatedWith$(SortedMapOps $this, Object key, Function1 remappingFunction) {
        return $this.updatedWith(key, remappingFunction);
    }

    @Override
    default public <V1> CC updatedWith(K key, Function1<Option<V>, Option<V1>> remappingFunction) {
        Option previousValue = this.get(key);
        Option<V1> option = remappingFunction.apply(previousValue);
        if (None$.MODULE$.equals(option)) {
            Object object;
            if (previousValue == null) {
                throw null;
            }
            if (previousValue.isEmpty()) {
                object = this.coll();
            } else {
                Object v = previousValue.get();
                object = SortedMapOps.$anonfun$updatedWith$2(this, key, v);
            }
            return (CC)((Map)object);
        }
        if (option instanceof Some) {
            Object v;
            Object nextValue = ((Some)option).value();
            if (previousValue == null) {
                throw null;
            }
            if (!previousValue.isEmpty() && SortedMapOps.$anonfun$updatedWith$3(nextValue, v = previousValue.get())) {
                return (CC)((Map)this.coll());
            }
            return this.coll().updated(key, nextValue);
        }
        throw new MatchError(option);
    }

    public static /* synthetic */ Map transform$(SortedMapOps $this, Function2 f) {
        return $this.transform(f);
    }

    @Override
    default public <W> CC transform(Function2<K, V, W> f) {
        return (CC)((Map)this.map((Function1<Tuple2, Tuple2> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Object k = x0$1._1();
                Object v = x0$1._2();
                return new Tuple2(k, f.apply(k, v));
            }
            throw new MatchError((Object)null);
        }, this.ordering()));
    }

    public static /* synthetic */ SortedMapOps $anonfun$updatedWith$1(SortedMapOps $this) {
        return $this.coll();
    }

    public static /* synthetic */ SortedMapOps $anonfun$updatedWith$2(SortedMapOps $this, Object key$1, Object x$2) {
        return ((SortedMapOps)$this.removed(key$1)).coll();
    }

    public static /* synthetic */ boolean $anonfun$updatedWith$3(Object nextValue$1, Object x$3) {
        return x$3 == nextValue$1;
    }

    public static void $init$(SortedMapOps $this) {
    }

    public static /* synthetic */ Object $anonfun$updatedWith$3$adapted(Object nextValue$1, Object x$3) {
        return BoxesRunTime.boxToBoolean(SortedMapOps.$anonfun$updatedWith$3(nextValue$1, x$3));
    }

    public class ImmutableKeySortedSet
    extends AbstractSet<K>
    implements SortedSet<K>,
    SortedMapOps.GenKeySortedSet {
        @Override
        public Ordering<Object> ordering() {
            return SortedMapOps.GenKeySortedSet.ordering$(this);
        }

        @Override
        public Iterator<Object> iteratorFrom(Object start2) {
            return SortedMapOps.GenKeySortedSet.iteratorFrom$(this, start2);
        }

        @Override
        public Iterator<Object> iterator() {
            return MapOps.GenKeySet.iterator$(this);
        }

        @Override
        public boolean contains(Object key) {
            return MapOps.GenKeySet.contains$(this, key);
        }

        @Override
        public int size() {
            return MapOps.GenKeySet.size$(this);
        }

        @Override
        public int knownSize() {
            return MapOps.GenKeySet.knownSize$(this);
        }

        @Override
        public boolean isEmpty() {
            return MapOps.GenKeySet.isEmpty$(this);
        }

        @Override
        public scala.collection.immutable.Set<K> unsorted() {
            return SortedSet.unsorted$(this);
        }

        @Override
        public SortedIterableFactory<SortedSet> sortedIterableFactory() {
            return SortedSet.sortedIterableFactory$(this);
        }

        @Override
        public /* synthetic */ boolean scala$collection$SortedSet$$super$equals(Object that) {
            return Set.equals$(this, that);
        }

        @Override
        public String stringPrefix() {
            return scala.collection.SortedSet.stringPrefix$(this);
        }

        @Override
        public boolean equals(Object that) {
            return scala.collection.SortedSet.equals$(this, that);
        }

        @Override
        public scala.collection.SortedSet fromSpecific(IterableOnce coll) {
            return SortedSetFactoryDefaults.fromSpecific$(this, coll);
        }

        @Override
        public Builder<K, SortedSet<K>> newSpecificBuilder() {
            return SortedSetFactoryDefaults.newSpecificBuilder$(this);
        }

        @Override
        public scala.collection.SortedSet empty() {
            return SortedSetFactoryDefaults.empty$(this);
        }

        @Override
        public SortedSetOps.WithFilter<K, ?, ?> withFilter(Function1<K, Object> p) {
            return SortedSetFactoryDefaults.withFilter$(this, p);
        }

        @Override
        public /* synthetic */ Object scala$collection$SortedSetOps$$super$min(Ordering ord) {
            return IterableOnceOps.min$(this, ord);
        }

        @Override
        public /* synthetic */ Object scala$collection$SortedSetOps$$super$max(Ordering ord) {
            return IterableOnceOps.max$(this, ord);
        }

        @Override
        public Iterator<K> keysIteratorFrom(K start2) {
            return SortedSetOps.keysIteratorFrom$(this, start2);
        }

        @Override
        public K firstKey() {
            return SortedSetOps.firstKey$(this);
        }

        @Override
        public K lastKey() {
            return SortedSetOps.lastKey$(this);
        }

        @Override
        public Option<K> minAfter(K key) {
            return SortedSetOps.minAfter$(this, key);
        }

        @Override
        public Option<K> maxBefore(K key) {
            return SortedSetOps.maxBefore$(this, key);
        }

        @Override
        public <B> K min(Ordering<B> ord) {
            return SortedSetOps.min$(this, ord);
        }

        @Override
        public <B> K max(Ordering<B> ord) {
            return SortedSetOps.max$(this, ord);
        }

        @Override
        public SortedSetOps rangeTo(Object to) {
            return SortedSetOps.rangeTo$(this, to);
        }

        @Override
        public scala.collection.SortedSet map(Function1 f, Ordering ev) {
            return SortedSetOps.map$(this, f, ev);
        }

        @Override
        public scala.collection.SortedSet flatMap(Function1 f, Ordering ev) {
            return SortedSetOps.flatMap$(this, f, ev);
        }

        @Override
        public scala.collection.SortedSet zip(IterableOnce that, Ordering ev) {
            return SortedSetOps.zip$(this, that, ev);
        }

        @Override
        public scala.collection.SortedSet collect(PartialFunction pf, Ordering ev) {
            return SortedSetOps.collect$(this, pf, ev);
        }

        @Override
        public int compare(K k0, K k1) {
            return SortedOps.compare$(this, k0, k1);
        }

        @Override
        public Object range(Object from, Object until) {
            return SortedOps.range$(this, from, until);
        }

        @Override
        public final Object from(Object from) {
            return SortedOps.from$(this, from);
        }

        @Override
        public Object rangeFrom(Object from) {
            return SortedOps.rangeFrom$(this, from);
        }

        @Override
        public final Object until(Object until) {
            return SortedOps.until$(this, until);
        }

        @Override
        public Object rangeUntil(Object until) {
            return SortedOps.rangeUntil$(this, until);
        }

        @Override
        public final Object to(Object to) {
            return SortedOps.to$(this, to);
        }

        @Override
        public SortedSet<K> rangeImpl(Option<K> from, Option<K> until) {
            SortedMapOps map = (SortedMapOps)this.scala$collection$immutable$SortedMapOps$ImmutableKeySortedSet$$$outer().rangeImpl(from, until);
            return map.new ImmutableKeySortedSet();
        }

        @Override
        public SortedSet<K> incl(K elem) {
            return (SortedSet)((SetOps)((Object)this.fromSpecific((IterableOnce)this))).incl(elem);
        }

        @Override
        public SortedSet<K> excl(K elem) {
            return (SortedSet)((SetOps)((Object)this.fromSpecific((IterableOnce)this))).excl(elem);
        }

        public /* synthetic */ SortedMapOps scala$collection$immutable$SortedMapOps$ImmutableKeySortedSet$$$outer() {
            return SortedMapOps.this;
        }

        @Override
        public /* synthetic */ scala.collection.SortedMapOps scala$collection$SortedMapOps$GenKeySortedSet$$$outer() {
            return this.scala$collection$immutable$SortedMapOps$ImmutableKeySortedSet$$$outer();
        }

        @Override
        public /* synthetic */ MapOps scala$collection$MapOps$GenKeySet$$$outer() {
            return this.scala$collection$immutable$SortedMapOps$ImmutableKeySortedSet$$$outer();
        }

        public ImmutableKeySortedSet() {
            if (SortedMapOps.this == null) {
                throw null;
            }
        }
    }
}

