/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Map;
import scala.collection.SortedMapFactory;
import scala.collection.SortedMapFactoryDefaults;
import scala.collection.SortedMapOps;
import scala.collection.SortedOps;
import scala.collection.SortedSet;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Map;
import scala.collection.mutable.SortedMap$;
import scala.collection.mutable.SortedMapOps;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005mfa\u0002\u000e\u001c!\u0003\r\tA\t\u0005\u0006\u0013\u0002!\tA\u0013\u0005\u0006\u001d\u0002!\te\u0014\u0005\u0006!\u0002!\t%\u0015\u0005\u0006+\u0002!\tE\u0016\u0005\u00069\u0002!\t%X\u0004\u0006?nA\t\u0001\u0019\u0004\u00065mA\t!\u0019\u0005\u0006S\u001e!\tA\u001b\u0004\u0005W\u001e\u0011A\u000eC\u0006\u0002\b%\u0011\t\u0011)A\u0005q\u0006%\u0001\u0002DA\u0006\u0013\t\u0005\t\u0015!\u0003\u0002\u000e\u0005=\u0001BB5\n\t\u0003\t\t\u0002C\u0003Q\u0013\u0011\u0005\u0013\u000bC\u0004\u0002\u0018%!\t!!\u0007\t\u000f\u0005-\u0012\u0002\"\u0001\u0002.!9\u00111G\u0005\u0005\u0004\u0005U\u0002bBA\u001f\u0013\u0011\u0005\u0011q\b\u0005\b\u0003\u001fJA\u0011IA)\u0011\u001d\tI&\u0003C!\u00037Bq!a\u0018\n\t\u0003\n\t\u0007C\u0004\u0002d%!\t%!\u001a\t\u000f\u0005}\u0014\u0002\"\u0015\u0002\u0002\"9\u0011\u0011R\u0005\u0005R\u0005-\u0005BDAJ\u0013A\u0005\u0019\u0011!A\u0005\n\u0005U\u0015q\u0002\u0005\n\u0003G;\u0011\u0011!C\u0005\u0003K\u0013\u0011bU8si\u0016$W*\u00199\u000b\u0005qi\u0012aB7vi\u0006\u0014G.\u001a\u0006\u0003=}\t!bY8mY\u0016\u001cG/[8o\u0015\u0005\u0001\u0013!B:dC2\f7\u0001A\u000b\u0004G5:4C\u0002\u0001%Qej$\t\u0005\u0002&M5\tq$\u0003\u0002(?\t1\u0011I\\=SK\u001a\u0004B!\u000b\u0016,m5\tQ$\u0003\u0002\u001b;A\u0011A&\f\u0007\u0001\t\u0015q\u0003A1\u00010\u0005\u0005Y\u0015C\u0001\u00194!\t)\u0013'\u0003\u00023?\t9aj\u001c;iS:<\u0007CA\u00135\u0013\t)tDA\u0002B]f\u0004\"\u0001L\u001c\u0005\u000ba\u0002!\u0019A\u0018\u0003\u0003Y\u0003BAO\u001e,m5\t1$\u0003\u0002=7\t\u0019Q*\u00199\u0011\rir4F\u000e!B\u0013\ty4D\u0001\u0007T_J$X\rZ'ba>\u00038\u000f\u0005\u0002;\u0001A!!\bA\u00167!\u001dI3i\u000b\u001cA\u000b\"K!\u0001R\u000f\u00031M{'\u000f^3e\u001b\u0006\u0004h)Y2u_JLH)\u001a4bk2$8\u000f\u0005\u0002;\r&\u0011qi\u0007\u0002\t\u0013R,'/\u00192mKB\u0011!hO\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003-\u0003\"!\n'\n\u00055{\"\u0001B+oSR\f\u0001\"\u001e8t_J$X\rZ\u000b\u0002s\u0005\u00012o\u001c:uK\u0012l\u0015\r\u001d$bGR|'/_\u000b\u0002%B\u0019\u0011f\u0015!\n\u0005Qk\"\u0001E*peR,G-T1q\r\u0006\u001cGo\u001c:z\u0003-9\u0018\u000e\u001e5EK\u001a\fW\u000f\u001c;\u0015\u0005\u0005;\u0006\"\u0002-\u0005\u0001\u0004I\u0016!\u00013\u0011\t\u0015R6FN\u0005\u00037~\u0011\u0011BR;oGRLwN\\\u0019\u0002!]LG\u000f\u001b#fM\u0006,H\u000e\u001e,bYV,GCA!_\u0011\u0015AV\u00011\u00017\u0003%\u0019vN\u001d;fI6\u000b\u0007\u000f\u0005\u0002;\u000fM\u0011qA\u0019\t\u0004G\u001a\u0004eBA\u0015e\u0013\t)W$\u0001\tT_J$X\rZ'ba\u001a\u000b7\r^8ss&\u0011q\r\u001b\u0002\t\t\u0016dWmZ1uK*\u0011Q-H\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003\u0001\u00141bV5uQ\u0012+g-Y;miV\u0019Q.^<\u0014\u000b%q\u00070\u001f?\u0011\t=\u0014HO\u001e\b\u0003uAL!!]\u000e\u0002\u00075\u000b\u0007/\u0003\u0002lg*\u0011\u0011o\u0007\t\u0003YU$QAL\u0005C\u0002=\u0002\"\u0001L<\u0005\u000baJ!\u0019A\u0018\u0011\ti\u0002AO\u001e\t\u0007uy\"h\u000f\u0011>\u0011\tmLAO^\u0007\u0002\u000fA\u0019Q0!\u0001\u000f\u0005\u0015r\u0018BA@ \u0003\u001d\u0001\u0018mY6bO\u0016LA!a\u0001\u0002\u0006\ta1+\u001a:jC2L'0\u00192mK*\u0011qpH\u0001\u000bk:$WM\u001d7zS:<\u0017bAA\u0004e\u0006aA-\u001a4bk2$h+\u00197vKB!QE\u0017;w\u0013\r\tYA\u001d\u000b\u0006u\u0006M\u0011Q\u0003\u0005\u0007\u0003\u000fa\u0001\u0019\u0001=\t\u000f\u0005-A\u00021\u0001\u0002\u000e\u0005a\u0011\u000e^3sCR|'O\u0012:p[R!\u00111DA\u0014!\u0015I\u0013QDA\u0011\u0013\r\ty\"\b\u0002\t\u0013R,'/\u0019;peB)Q%a\tum&\u0019\u0011QE\u0010\u0003\rQ+\b\u000f\\33\u0011\u0019\tIC\u0004a\u0001i\u0006)1\u000f^1si\u0006\u00012.Z=t\u0013R,'/\u0019;pe\u001a\u0013x.\u001c\u000b\u0005\u0003_\t\t\u0004\u0005\u0003*\u0003;!\bBBA\u0015\u001f\u0001\u0007A/\u0001\u0005pe\u0012,'/\u001b8h+\t\t9\u0004\u0005\u0003~\u0003s!\u0018\u0002BA\u001e\u0003\u000b\u0011\u0001b\u0014:eKJLgnZ\u0001\ne\u0006tw-Z%na2$RA_A!\u0003\u0017Bq!a\u0011\u0012\u0001\u0004\t)%\u0001\u0003ge>l\u0007\u0003B\u0013\u0002HQL1!!\u0013 \u0005\u0019y\u0005\u000f^5p]\"9\u0011QJ\tA\u0002\u0005\u0015\u0013!B;oi&d\u0017aC:vER\u0014\u0018m\u0019;P]\u0016$B!a\u0015\u0002V5\t\u0011\u0002\u0003\u0004\u0002XI\u0001\r\u0001^\u0001\u0005K2,W.\u0001\u0004bI\u0012|e.\u001a\u000b\u0005\u0003'\ni\u0006C\u0004\u0002XM\u0001\r!!\t\u0002\u000b\u0015l\u0007\u000f^=\u0016\u0003i\faaY8oG\u0006$X\u0003BA4\u0003[\"B!!\u001b\u0002tA)!\b\u0001;\u0002lA\u0019A&!\u001c\u0005\u000f\u0005=TC1\u0001\u0002r\t\u0011aKM\t\u0003mNBq!!\u001e\u0016\u0001\u0004\t9(\u0001\u0004tk\u001a4\u0017\u000e\u001f\t\u0006S\u0005e\u0014QP\u0005\u0004\u0003wj\"\u0001D%uKJ\f'\r\\3P]\u000e,\u0007CB\u0013\u0002$Q\fY'\u0001\u0007ge>l7\u000b]3dS\u001aL7\rF\u0002{\u0003\u0007Cq!!\"\u0017\u0001\u0004\t9)\u0001\u0003d_2d\u0007#B\u0015\u0002z\u0005\u0005\u0012A\u00058foN\u0003XmY5gS\u000e\u0014U/\u001b7eKJ,\"!!$\u0011\ri\ny)!\t{\u0013\r\t\tj\u0007\u0002\b\u0005VLG\u000eZ3s\u0003I\u0019X\u000f]3sI\u0011,g-Y;miZ\u000bG.^3\u0016\u0005\u00055\u0001fB\u0005\u0002\u001a\u0006}\u0015\u0011\u0015\t\u0004K\u0005m\u0015bAAO?\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0007\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011q\u0015\t\u0005\u0003S\u000b\u0019,\u0004\u0002\u0002,*!\u0011QVAX\u0003\u0011a\u0017M\\4\u000b\u0005\u0005E\u0016\u0001\u00026bm\u0006LA!!.\u0002,\n1qJ\u00196fGRDsaBAM\u0003?\u000b\t\u000bK\u0004\u0007\u00033\u000by*!)")
public interface SortedMap<K, V>
extends scala.collection.SortedMap<K, V>,
scala.collection.mutable.Map<K, V>,
SortedMapOps<K, V, SortedMap, SortedMap<K, V>> {
    public static <K, V> Builder<Tuple2<K, V>, SortedMap<K, V>> newBuilder(Ordering<K> evidence$48) {
        return SortedMap$.MODULE$.newBuilder(evidence$48);
    }

    @Override
    default public scala.collection.mutable.Map<K, V> unsorted() {
        return this;
    }

    public static /* synthetic */ SortedMapFactory sortedMapFactory$(SortedMap $this) {
        return $this.sortedMapFactory();
    }

    @Override
    default public SortedMapFactory<SortedMap> sortedMapFactory() {
        return SortedMap$.MODULE$;
    }

    @Override
    default public SortedMap<K, V> withDefault(Function1<K, V> d) {
        return new WithDefault<K, V>(this, d);
    }

    @Override
    default public SortedMap<K, V> withDefaultValue(V d) {
        return new WithDefault(this, (Function1<Object, Object> & Serializable)x$1 -> d);
    }

    public static void $init$(SortedMap $this) {
    }

    public static final class WithDefault<K, V>
    extends Map.WithDefault<K, V>
    implements SortedMap<K, V> {
        private static final long serialVersionUID = 3L;

        @Override
        public scala.collection.mutable.Map<K, V> unsorted() {
            return ((SortedMap)this).unsorted();
        }

        @Override
        public SortedMap<K, V> withDefault(Function1<K, V> d) {
            return ((SortedMap)this).withDefault(d);
        }

        @Override
        public SortedMap<K, V> withDefaultValue(V d) {
            return ((SortedMap)this).withDefaultValue(d);
        }

        @Override
        public scala.collection.mutable.Map updated(Object key, Object value) {
            return SortedMapOps.updated$(this, key, value);
        }

        @Override
        public /* synthetic */ boolean scala$collection$SortedMap$$super$equals(Object o) {
            return Map.equals$(this, o);
        }

        @Override
        public String stringPrefix() {
            return scala.collection.SortedMap.stringPrefix$(this);
        }

        @Override
        public boolean equals(Object that) {
            return scala.collection.SortedMap.equals$(this, that);
        }

        @Override
        public SortedMapOps.WithFilter<K, V, ?, ?, ?> withFilter(Function1<Tuple2<K, V>, Object> p) {
            return SortedMapFactoryDefaults.withFilter$(this, p);
        }

        @Override
        public final Map sortedMapFromIterable(Iterable it, Ordering ordering) {
            return scala.collection.SortedMapOps.sortedMapFromIterable$(this, it, ordering);
        }

        @Override
        public Iterator<V> valuesIteratorFrom(K start2) {
            return scala.collection.SortedMapOps.valuesIteratorFrom$(this, start2);
        }

        @Override
        public K firstKey() {
            return (K)scala.collection.SortedMapOps.firstKey$(this);
        }

        @Override
        public K lastKey() {
            return (K)scala.collection.SortedMapOps.lastKey$(this);
        }

        @Override
        public Option<Tuple2<K, V>> minAfter(K key) {
            return scala.collection.SortedMapOps.minAfter$(this, key);
        }

        @Override
        public Option<Tuple2<K, V>> maxBefore(K key) {
            return scala.collection.SortedMapOps.maxBefore$(this, key);
        }

        @Override
        public scala.collection.SortedMapOps rangeTo(Object to) {
            return scala.collection.SortedMapOps.rangeTo$(this, to);
        }

        @Override
        public SortedSet<K> keySet() {
            return scala.collection.SortedMapOps.keySet$(this);
        }

        @Override
        public Map map(Function1 f, Ordering ordering) {
            return scala.collection.SortedMapOps.map$(this, f, ordering);
        }

        @Override
        public Map flatMap(Function1 f, Ordering ordering) {
            return scala.collection.SortedMapOps.flatMap$(this, f, ordering);
        }

        @Override
        public Map collect(PartialFunction pf, Ordering ordering) {
            return scala.collection.SortedMapOps.collect$(this, pf, ordering);
        }

        @Override
        public final Map $plus$plus(IterableOnce xs) {
            return scala.collection.SortedMapOps.$plus$plus$(this, xs);
        }

        @Override
        public Map $plus(Tuple2 kv) {
            return scala.collection.SortedMapOps.$plus$(this, kv);
        }

        @Override
        public Map $plus(Tuple2 elem1, Tuple2 elem2, Seq elems) {
            return scala.collection.SortedMapOps.$plus$(this, elem1, elem2, elems);
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

        private /* synthetic */ Function1 super$defaultValue() {
            return super.defaultValue();
        }

        @Override
        public SortedMapFactory<SortedMap> sortedMapFactory() {
            return ((SortedMap)super.underlying()).sortedMapFactory();
        }

        @Override
        public Iterator<Tuple2<K, V>> iteratorFrom(K start2) {
            return ((SortedMap)super.underlying()).iteratorFrom(start2);
        }

        @Override
        public Iterator<K> keysIteratorFrom(K start2) {
            return ((SortedMap)super.underlying()).keysIteratorFrom(start2);
        }

        @Override
        public Ordering<K> ordering() {
            return ((SortedMap)super.underlying()).ordering();
        }

        @Override
        public WithDefault<K, V> rangeImpl(Option<K> from, Option<K> until) {
            return new WithDefault((SortedMap)((SortedMap)super.underlying()).rangeImpl(from, until), super.defaultValue());
        }

        @Override
        public WithDefault<K, V> subtractOne(K elem) {
            ((SortedMap)super.underlying()).subtractOne(elem);
            return this;
        }

        @Override
        public WithDefault<K, V> addOne(Tuple2<K, V> elem) {
            ((SortedMap)super.underlying()).addOne(elem);
            return this;
        }

        @Override
        public WithDefault<K, V> empty() {
            return new WithDefault((SortedMap)((SortedMap)super.underlying()).empty(), super.defaultValue());
        }

        @Override
        public <V2> SortedMap<K, V2> concat(IterableOnce<Tuple2<K, V2>> suffix) {
            return ((SortedMap)((SortedMap)super.underlying()).concat(suffix)).withDefault(super.defaultValue());
        }

        @Override
        public WithDefault<K, V> fromSpecific(IterableOnce<Tuple2<K, V>> coll) {
            return new WithDefault(this.sortedMapFactory().from(coll, this.ordering()), super.defaultValue());
        }

        @Override
        public Builder<Tuple2<K, V>, WithDefault<K, V>> newSpecificBuilder() {
            return SortedMap$.MODULE$.newBuilder(this.ordering()).mapResult((Function1<SortedMap, WithDefault> & Serializable)(To p) -> new WithDefault(p, this.super$defaultValue()));
        }

        public WithDefault(SortedMap<K, V> underlying, Function1<K, V> defaultValue) {
            super(underlying, defaultValue);
        }
    }
}

