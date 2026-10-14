/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.collection.JavaConverters$;
import scala.collection.immutable.Set;
import scala.collection.mutable.Builder;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005Ec\u0001\u0002\f\u0018\u0001qA\u0001\u0002\n\u0001\u0003\u0006\u0004%\t!\n\u0005\tS\u0001\u0011\t\u0011)A\u0005M!A!\u0006\u0001BC\u0002\u0013\u00051\u0006\u0003\u0005?\u0001\t\u0005\t\u0015!\u0003-\u0011\u0015y\u0004\u0001\"\u0001A\u0011\u0015y\u0004\u0001\"\u0001I\u0011\u001dy\u0005A1A\u0005\nACaA\u0017\u0001!\u0002\u0013\t\u0006bB.\u0001\u0005\u0004%I\u0001\u0018\u0005\u0007;\u0002\u0001\u000b\u0011B,\t\u000by\u0003A\u0011A0\t\u000b\u001d\u0004A\u0011\u00015\t\u000bM\u0004A\u0011\u0001;\t\u000f\u0005\u0015\u0001\u0001\"\u0001\u0002\b!9\u0011Q\u0003\u0001\u0005\u0002\u0005]\u0001bBA\u0015\u0001\u0011\u0005\u00111\u0006\u0005\b\u0003g\u0001A\u0011AA\u001b\u0011\u001d\t\u0019\u0004\u0001C\u0001\u0003wAq!a\u0011\u0001\t\u0003\t)\u0005C\u0004\u0002J\u0001!\t!a\u0013\t\u000f\u00055\u0003\u0001\"\u0001\u0002P\t)\u0011J\u001c3fq*\u0011\u0001$G\u0001\u0005kRLGNC\u0001\u001b\u0003\u0011\t7n[1\u0004\u0001U\u0019Q\u0004R\u001b\u0014\u0005\u0001q\u0002CA\u0010#\u001b\u0005\u0001#\"A\u0011\u0002\u000bM\u001c\u0017\r\\1\n\u0005\r\u0002#AB!osJ+g-A\u0004nCB\u001c\u0016N_3\u0016\u0003\u0019\u0002\"aH\u0014\n\u0005!\u0002#aA%oi\u0006AQ.\u00199TSj,\u0007%A\bwC2,XmQ8na\u0006\u0014\u0018\r^8s+\u0005a\u0003cA\u00172g5\taF\u0003\u0002\u0019_)\t\u0001'\u0001\u0003kCZ\f\u0017B\u0001\u001a/\u0005)\u0019u.\u001c9be\u0006$xN\u001d\t\u0003iUb\u0001\u0001B\u00037\u0001\t\u0007qGA\u0001W#\tA4\b\u0005\u0002 s%\u0011!\b\t\u0002\b\u001d>$\b.\u001b8h!\tyB(\u0003\u0002>A\t\u0019\u0011I\\=\u0002!Y\fG.^3D_6\u0004\u0018M]1u_J\u0004\u0013A\u0002\u001fj]&$h\bF\u0002B\r\u001e\u0003BA\u0011\u0001Dg5\tq\u0003\u0005\u00025\t\u0012)Q\t\u0001b\u0001o\t\t1\nC\u0003%\u000b\u0001\u0007a\u0005C\u0003+\u000b\u0001\u0007A\u0006F\u0002B\u0013*CQ\u0001\n\u0004A\u0002\u0019BQa\u0013\u0004A\u00021\u000b1aY7q!\u0015yRjM\u001a'\u0013\tq\u0005EA\u0005Gk:\u001cG/[8oe\u0005I1m\u001c8uC&tWM]\u000b\u0002#B!!+V\"X\u001b\u0005\u0019&B\u0001+/\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003-N\u0013\u0011cQ8oGV\u0014(/\u001a8u\u0011\u0006\u001c\b.T1q!\r\u0011\u0006lM\u0005\u00033N\u0013QcQ8oGV\u0014(/\u001a8u'.L\u0007\u000fT5tiN+G/\u0001\u0006d_:$\u0018-\u001b8fe\u0002\n\u0001\"Z7qif\u001cV\r^\u000b\u0002/\u0006IQ-\u001c9usN+G\u000fI\u0001\u0004aV$Hc\u00011dKB\u0011q$Y\u0005\u0003E\u0002\u0012qAQ8pY\u0016\fg\u000eC\u0003e\u0017\u0001\u00071)A\u0002lKfDQAZ\u0006A\u0002M\nQA^1mk\u0016\f\u0011BZ5oIZ\u000bG.^3\u0015\u0005%\u0014HC\u00016n!\ry2nM\u0005\u0003Y\u0002\u0012aa\u00149uS>t\u0007\"\u00028\r\u0001\u0004y\u0017!\u00014\u0011\t}\u00018\u0007Y\u0005\u0003c\u0002\u0012\u0011BR;oGRLwN\\\u0019\t\u000b\u0011d\u0001\u0019A\"\u0002\u001bY\fG.^3Ji\u0016\u0014\u0018\r^8s)\r)\u00181\u0001\t\u0004mz\u001cdBA<}\u001d\tA80D\u0001z\u0015\tQ8$\u0001\u0004=e>|GOP\u0005\u0002C%\u0011Q\u0010I\u0001\ba\u0006\u001c7.Y4f\u0013\ry\u0018\u0011\u0001\u0002\t\u0013R,'/\u0019;pe*\u0011Q\u0010\t\u0005\u0006I6\u0001\raQ\u0001\bM>\u0014X-Y2i)\u0011\tI!a\u0004\u0011\u0007}\tY!C\u0002\u0002\u000e\u0001\u0012A!\u00168ji\"9\u0011\u0011\u0003\bA\u0002\u0005M\u0011a\u00014v]B1q$T\"4\u0003\u0013\taA^1mk\u0016\u001cXCAA\r!\u0015\tY\"a\t4\u001d\u0011\ti\"a\b\u0011\u0005a\u0004\u0013bAA\u0011A\u00051\u0001K]3eK\u001aLA!!\n\u0002(\t\u00191+\u001a;\u000b\u0007\u0005\u0005\u0002%\u0001\u0003lKf\u001cXCAA\u0017!\u00111\u0018qF\"\n\t\u0005E\u0012\u0011\u0001\u0002\t\u0013R,'/\u00192mK\u00061!/Z7pm\u0016$R\u0001YA\u001c\u0003sAQ\u0001Z\tA\u0002\rCQAZ\tA\u0002M\"B!!\u0010\u0002BA!qd[A !\u00111\u0018qF\u001a\t\u000b\u0011\u0014\u0002\u0019A\"\u0002\u0017I,Wn\u001c<f-\u0006dW/\u001a\u000b\u0005\u0003\u0013\t9\u0005C\u0003g'\u0001\u00071'A\u0004jg\u0016k\u0007\u000f^=\u0016\u0003\u0001\fQa\u00197fCJ$\"!!\u0003")
public class Index<K, V> {
    private final int mapSize;
    private final Comparator<V> valueComparator;
    private final ConcurrentHashMap<K, ConcurrentSkipListSet<V>> container;
    private final ConcurrentSkipListSet<V> emptySet;

    public int mapSize() {
        return this.mapSize;
    }

    public Comparator<V> valueComparator() {
        return this.valueComparator;
    }

    private ConcurrentHashMap<K, ConcurrentSkipListSet<V>> container() {
        return this.container;
    }

    private ConcurrentSkipListSet<V> emptySet() {
        return this.emptySet;
    }

    public boolean put(K key, V value) {
        return this.spinPut$1(key, value);
    }

    public Option<V> findValue(K key, Function1<V, Object> f) {
        ConcurrentSkipListSet<V> concurrentSkipListSet = this.container().get(key);
        if (concurrentSkipListSet == null) {
            return None$.MODULE$;
        }
        return package$JavaConverters$.MODULE$.IteratorHasAsScala(concurrentSkipListSet.iterator()).asScala().find(f);
    }

    public Iterator<V> valueIterator(K key) {
        ConcurrentSkipListSet<V> concurrentSkipListSet = this.container().get(key);
        if (concurrentSkipListSet == null) {
            return package$.MODULE$.Iterator().empty();
        }
        return package$JavaConverters$.MODULE$.IteratorHasAsScala(concurrentSkipListSet.iterator()).asScala();
    }

    public void foreach(Function2<K, V, BoxedUnit> fun) {
        package$JavaConverters$.MODULE$.IteratorHasAsScala(this.container().entrySet().iterator()).asScala().foreach((Function1<Map.Entry, Object> & Serializable)(A e) -> {
            Index.$anonfun$foreach$1(fun, e);
            return BoxedUnit.UNIT;
        });
    }

    public Set<V> values() {
        Builder builder = Predef$.MODULE$.Set().newBuilder();
        package$JavaConverters$.MODULE$.IteratorHasAsScala(this.container().values().iterator()).asScala().foreach((Function1<ConcurrentSkipListSet, Object> & Serializable)(A values2) -> {
            Index.$anonfun$values$1(builder, values2);
            return BoxedUnit.UNIT;
        });
        return builder.result();
    }

    public Iterable<K> keys() {
        return package$JavaConverters$.MODULE$.SetHasAsScala(this.container().keySet()).asScala();
    }

    public boolean remove(K key, V value) {
        ConcurrentSkipListSet<V> set = this.container().get(key);
        if (set != null) {
            boolean bl;
            ConcurrentSkipListSet<V> concurrentSkipListSet = set;
            synchronized (concurrentSkipListSet) {
                boolean bl2;
                if (set.remove(value)) {
                    Serializable serializable = set.isEmpty() ? BoxesRunTime.boxToBoolean(this.container().remove(key, this.emptySet())) : BoxedUnit.UNIT;
                    bl2 = true;
                } else {
                    bl2 = false;
                }
                bl = bl2;
            }
            return bl;
        }
        return false;
    }

    public Option<Iterable<V>> remove(K key) {
        ConcurrentSkipListSet<V> set = this.container().get(key);
        if (set != null) {
            Some<Iterable<V>> some;
            ConcurrentSkipListSet<V> concurrentSkipListSet = set;
            synchronized (concurrentSkipListSet) {
                this.container().remove(key, set);
                Iterable ret = JavaConverters$.MODULE$.collectionAsScalaIterableConverter(set.clone()).asScala();
                set.clear();
                some = new Some<Iterable<V>>(ret);
            }
            return some;
        }
        return None$.MODULE$;
    }

    public void removeValue(V value) {
        for (Map.Entry<K, ConcurrentSkipListSet<V>> e : this.container().entrySet()) {
            BoxedUnit boxedUnit;
            ConcurrentSkipListSet<V> set = e.getValue();
            if (set != null) {
                ConcurrentSkipListSet<V> concurrentSkipListSet = set;
                synchronized (concurrentSkipListSet) {
                    BoxedUnit boxedUnit2 = set.remove(value) ? (set.isEmpty() ? BoxesRunTime.boxToBoolean(this.container().remove(e.getKey(), this.emptySet())) : BoxedUnit.UNIT) : BoxedUnit.UNIT;
                    // MONITOREXIT @DISABLED, blocks:[0, 1, 3, 4] lbl10 : MonitorExitStatement: MONITOREXIT : var5_5
                    boxedUnit = boxedUnit2;
                    continue;
                }
            }
            boxedUnit = BoxedUnit.UNIT;
        }
    }

    public boolean isEmpty() {
        return this.container().isEmpty();
    }

    public void clear() {
        for (Map.Entry<K, ConcurrentSkipListSet<V>> e : this.container().entrySet()) {
            Serializable serializable;
            ConcurrentSkipListSet<V> set = e.getValue();
            if (set != null) {
                boolean bl;
                ConcurrentSkipListSet<V> concurrentSkipListSet = set;
                synchronized (concurrentSkipListSet) {
                    set.clear();
                    bl = this.container().remove(e.getKey(), this.emptySet());
                }
                serializable = BoxesRunTime.boxToBoolean(bl);
                continue;
            }
            serializable = BoxedUnit.UNIT;
        }
    }

    private final boolean spinPut$1(Object k, Object v) {
        boolean added;
        boolean retry2;
        do {
            retry2 = false;
            added = false;
            ConcurrentSkipListSet<V> set = this.container().get(k);
            if (set != null) {
                ConcurrentSkipListSet<V> concurrentSkipListSet = set;
                synchronized (concurrentSkipListSet) {
                    if (set.isEmpty()) {
                        retry2 = true;
                    } else {
                        added = set.add(v);
                        retry2 = false;
                    }
                }
            } else {
                ConcurrentSkipListSet<V> newSet = new ConcurrentSkipListSet<V>(this.valueComparator());
                newSet.add(v);
                ConcurrentSkipListSet<V> oldSet = this.container().putIfAbsent(k, newSet);
                if (oldSet != null) {
                    ConcurrentSkipListSet<V> concurrentSkipListSet = oldSet;
                    synchronized (concurrentSkipListSet) {
                        if (oldSet.isEmpty()) {
                            retry2 = true;
                        } else {
                            added = oldSet.add(v);
                            retry2 = false;
                        }
                    }
                } else {
                    added = true;
                }
            }
        } while (retry2);
        return added;
    }

    public static final /* synthetic */ void $anonfun$foreach$1(Function2 fun$1, Map.Entry e) {
        package$JavaConverters$.MODULE$.IteratorHasAsScala(((ConcurrentSkipListSet)e.getValue()).iterator()).asScala().foreach((Function1<Object, Object> & Serializable)(A x$1) -> {
            fun$1.apply(e.getKey(), x$1);
            return BoxedUnit.UNIT;
        });
    }

    public static final /* synthetic */ void $anonfun$values$1(Builder builder$1, ConcurrentSkipListSet values2) {
        package$JavaConverters$.MODULE$.IteratorHasAsScala(values2.iterator()).asScala().foreach((Function1<Object, Builder> & Serializable)(A v) -> (Builder)builder$1.$plus$eq(v));
    }

    public Index(int mapSize, Comparator<V> valueComparator) {
        this.mapSize = mapSize;
        this.valueComparator = valueComparator;
        this.container = new ConcurrentHashMap(mapSize);
        this.emptySet = new ConcurrentSkipListSet();
    }

    public Index(int mapSize, Function2<V, V, Object> cmp) {
        this(mapSize, new Comparator<V>(cmp){
            private final Function2 cmp$1;

            public Comparator<V> reversed() {
                return Comparator.super.reversed();
            }

            public Comparator<V> thenComparing(Comparator<? super V> x$1) {
                return Comparator.super.thenComparing(x$1);
            }

            public <U> Comparator<V> thenComparing(Function<? super V, ? extends U> x$1, Comparator<? super U> x$2) {
                return Comparator.super.thenComparing(x$1, x$2);
            }

            public <U extends Comparable<? super U>> Comparator<V> thenComparing(Function<? super V, ? extends U> x$1) {
                return Comparator.super.thenComparing(x$1);
            }

            public Comparator<V> thenComparingInt(ToIntFunction<? super V> x$1) {
                return Comparator.super.thenComparingInt(x$1);
            }

            public Comparator<V> thenComparingLong(ToLongFunction<? super V> x$1) {
                return Comparator.super.thenComparingLong(x$1);
            }

            public Comparator<V> thenComparingDouble(ToDoubleFunction<? super V> x$1) {
                return Comparator.super.thenComparingDouble(x$1);
            }

            public final int compare(V x$1, V x$2) {
                return BoxesRunTime.unboxToInt(this.cmp$1.apply(x$1, x$2));
            }
            {
                this.cmp$1 = cmp$1;
            }
        });
    }
}

