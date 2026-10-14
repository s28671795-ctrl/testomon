/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.concurrent.Map;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Cloneable;
import scala.collection.mutable.Growable;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.Shrinkable;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dfa\u0002\n\u0014!\u0003\r\tA\u0007\u0005\u00069\u0002!\t!\u0018\u0005\u0006C\u0002!\tA\u0019\u0005\u0006G\u0002!)\u0001\u001a\u0005\u0006G\u0002!)!\u001d\u0005\u0006}\u0002!\ta \u0005\b\u0003\u001b\u0001A\u0011AA\b\u0011\u001d\t)\u0002\u0001C\u0001\u0003/Aq!a\n\u0001\t\u0003\tI\u0003C\u0004\u00028\u0001!\t!!\u000f\t\r\u0005u\u0002\u0001\"\u0001^\u0011\u0019\ty\u0004\u0001C!E\"9\u0011\u0011\t\u0001\u0005\u0006\u0005\r\u0003bBA3\u0001\u0011\u0005\u0011q\r\u0005\b\u0003W\u0002AQAA7\u0011\u001d\ti\b\u0001C\u0001\u0003\u007fBq!a!\u0001\t\u0003\t)\tC\u0004\u0002\u001e\u0002!\t%a(\u0003\r5\u000b\u0007o\u00149t\u0015\t!R#A\u0004nkR\f'\r\\3\u000b\u0005Y9\u0012AC2pY2,7\r^5p]*\t\u0001$A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u000bmI3g\u0010\u001e\u0014\u0011\u0001a\u0002E\u0014)T-f\u0003\"!\b\u0010\u000e\u0003]I!aH\f\u0003\r\u0005s\u0017PU3g!\u0015\t#\u0005J\u001b:\u001b\u0005)\u0012BA\u0012\u0016\u0005-IE/\u001a:bE2,w\n]:\u0011\tu)sEM\u0005\u0003M]\u0011a\u0001V;qY\u0016\u0014\u0004C\u0001\u0015*\u0019\u0001!QA\u000b\u0001C\u0002-\u0012\u0011aS\t\u0003Y=\u0002\"!H\u0017\n\u00059:\"a\u0002(pi\"Lgn\u001a\t\u0003;AJ!!M\f\u0003\u0007\u0005s\u0017\u0010\u0005\u0002)g\u0011)A\u0007\u0001b\u0001W\t\ta\u000b\u0005\u00027o5\t1#\u0003\u00029'\tA\u0011\n^3sC\ndW\r\u0005\u0002)u\u001111\b\u0001CC\u0002q\u0012\u0011aQ\t\u0003Yu\u0002bA\u000e\u0001(eyJ\u0004C\u0001\u0015@\t\u0019\u0001\u0005\u0001\"b\u0001\u0003\n\u00111iQ\u000b\u0004\u0005\u001aK\u0015C\u0001\u0017Da\t!E\n\u0005\u00047\u0001\u0015Ceh\u0013\t\u0003Q\u0019#QaR C\u0002-\u0012\u0011\u0001\u0017\t\u0003Q%#QAS C\u0002-\u0012\u0011!\u0017\t\u0003Q1#\u0011\"T \u0002\u0002\u0003\u0005)\u0011A\u0016\u0003\u0007}#\u0013\u0007\u0005\u0004\"\u001f\u001e\u0012d(O\u0005\u0003%U\u00012AN):\u0013\t\u00116CA\u0005DY>tW-\u00192mKB!a\u0007\u0016\u0013:\u0013\t)6CA\u0004Ck&dG-\u001a:\u0011\u0007Y:F%\u0003\u0002Y'\tAqI]8xC\ndW\rE\u000275\u001eJ!aW\n\u0003\u0015MC'/\u001b8lC\ndW-\u0001\u0004%S:LG\u000f\n\u000b\u0002=B\u0011QdX\u0005\u0003A^\u0011A!\u00168ji\u00061!/Z:vYR$\u0012!O\u0001\u0007I5Lg.^:\u0015\u0005e*\u0007\"\u00024\u0004\u0001\u00049\u0013aA6fs\"21\u0001[6m]>\u0004\"!H5\n\u0005)<\"A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017%A7\u0002GU\u001bX\rI\u0017!_J\u0004#/Z7pm\u0016\u0004sN\u001c\u0011b]\u0002JW.\\;uC\ndW\rI'ba\u0006)1/\u001b8dK\u0006\n\u0001/\u0001\u00043]E\u001ad\u0006\r\u000b\u0005sI$h\u000fC\u0003t\t\u0001\u0007q%\u0001\u0003lKf\f\u0004\"B;\u0005\u0001\u00049\u0013\u0001B6fsJBQa\u001e\u0003A\u0002a\fAa[3zgB\u0019Q$_\u0014\n\u0005i<\"A\u0003\u001fsKB,\u0017\r^3e}!2A\u0001[6}]>\f\u0013!`\u0001(+N,\u0007%L\u0017!_J\u0004#/Z7pm\u0016\fE\u000e\u001c\u0011p]\u0002\ng\u000eI5n[V$\u0018M\u00197fA5\u000b\u0007/A\u0002qkR$b!!\u0001\u0002\b\u0005%\u0001\u0003B\u000f\u0002\u0004IJ1!!\u0002\u0018\u0005\u0019y\u0005\u000f^5p]\")a-\u0002a\u0001O!1\u00111B\u0003A\u0002I\nQA^1mk\u0016\fa!\u001e9eCR,G#\u00020\u0002\u0012\u0005M\u0001\"\u00024\u0007\u0001\u00049\u0003BBA\u0006\r\u0001\u0007!'\u0001\u0006va\u0012\fG/Z,ji\"$B!!\u0007\u0002&Q!\u0011\u0011AA\u000e\u0011\u001d\tib\u0002a\u0001\u0003?\t\u0011C]3nCB\u0004\u0018N\\4Gk:\u001cG/[8o!\u001di\u0012\u0011EA\u0001\u0003\u0003I1!a\t\u0018\u0005%1UO\\2uS>t\u0017\u0007C\u0003g\u000f\u0001\u0007q%A\bhKR|%/\u00127tKV\u0003H-\u0019;f)\u0015\u0011\u00141FA\u0017\u0011\u00151\u0007\u00021\u0001(\u0011!\ty\u0003\u0003CA\u0002\u0005E\u0012AA8q!\u0011i\u00121\u0007\u001a\n\u0007\u0005UrC\u0001\u0005=Eft\u0017-\\3?\u0003\u0019\u0011X-\\8wKR!\u0011\u0011AA\u001e\u0011\u00151\u0017\u00021\u0001(\u0003\u0015\u0019G.Z1s\u0003\u0015\u0019Gn\u001c8f\u0003\u0019\u0011X\r^1j]R!\u0011QIA$\u001b\u0005\u0001\u0001bBA%\u0019\u0001\u0007\u00111J\u0001\u0002aB9Q$!\u0014(e\u0005E\u0013bAA(/\tIa)\u001e8di&|gN\r\t\u0004;\u0005M\u0013bAA+/\t9!i\\8mK\u0006t\u0007f\u0001\u0007\u0002ZA\u0019Q$a\u0017\n\u0007\u0005usC\u0001\u0004j]2Lg.\u001a\u0015\b\u0019!\\\u0017\u0011\r8pC\t\t\u0019'A\rVg\u0016\u0004c-\u001b7uKJLe\u000e\u00157bG\u0016\u0004\u0013N\\:uK\u0006$\u0017!\u00044jYR,'/\u00138QY\u0006\u001cW\r\u0006\u0003\u0002F\u0005%\u0004bBA%\u001b\u0001\u0007\u00111J\u0001\niJ\fgn\u001d4pe6$B!!\u0012\u0002p!9\u0011\u0011\u000f\bA\u0002\u0005M\u0014!\u00014\u0011\ru\tie\n\u001a3Q\rq\u0011\u0011\f\u0015\b\u001d!\\\u0017\u0011\u00108pC\t\tY(\u0001\u000fVg\u0016\u0004S.\u00199WC2,Xm]%o!2\f7-\u001a\u0011j]N$X-\u00193\u0002!5\f\u0007OV1mk\u0016\u001c\u0018J\u001c)mC\u000e,G\u0003BA#\u0003\u0003Cq!!\u001d\u0010\u0001\u0004\t\u0019(A\u0004va\u0012\fG/\u001a3\u0016\t\u0005\u001d\u0015Q\u0012\u000b\u0007\u0003\u0013\u000b\u0019*!&\u0011\u000b!zt%a#\u0011\u0007!\ni\tB\u0004\u0002\u0010B\u0011\r!!%\u0003\u0005Y\u000b\u0014C\u0001\u001a0\u0011\u00151\u0007\u00031\u0001(\u0011\u001d\tY\u0001\u0005a\u0001\u0003\u0017Cs\u0001\u00055l\u00033sw.\t\u0002\u0002\u001c\u00061Tk]3![:\u001aGn\u001c8fQ%r\u0013\r\u001a3P]\u0016D\u0003f\u001b\u0017wS%\u0002\u0013N\\:uK\u0006$\u0007e\u001c4![:*\b\u000fZ1uK\u0012D3\u000e\f\u0011wS\u0005I1N\\8x]NK'0Z\u000b\u0003\u0003C\u00032!HAR\u0013\r\t)k\u0006\u0002\u0004\u0013:$\b")
public interface MapOps<K, V, CC extends MapOps<Object, Object, CC, ?>, C extends MapOps<K, V, CC, C>>
extends scala.collection.MapOps<K, V, CC, C>,
Cloneable<C>,
Builder<Tuple2<K, V>, C>,
Shrinkable<K> {
    public static /* synthetic */ MapOps result$(MapOps $this) {
        return $this.result();
    }

    @Override
    default public C result() {
        return (C)((MapOps)this.coll());
    }

    public static /* synthetic */ MapOps $minus$(MapOps $this, Object key) {
        return $this.$minus(key);
    }

    default public C $minus(K key) {
        C c = this.clone();
        if (c == null) {
            throw null;
        }
        return (C)((MapOps)c.subtractOne(key));
    }

    public static /* synthetic */ MapOps $minus$(MapOps $this, Object key1, Object key2, Seq keys2) {
        return $this.$minus(key1, key2, keys2);
    }

    default public C $minus(K key1, K key2, Seq<K> keys2) {
        C c = this.clone();
        if (c == null) {
            throw null;
        }
        Shrinkable<K> shrinkable = c.subtractOne(key1);
        if (shrinkable == null) {
            throw null;
        }
        Shrinkable<K> shrinkable2 = shrinkable.subtractOne(key2);
        if (shrinkable2 == null) {
            throw null;
        }
        return (C)((MapOps)shrinkable2.subtractAll(keys2));
    }

    public static /* synthetic */ Option put$(MapOps $this, Object key, Object value) {
        return $this.put(key, value);
    }

    default public Option<V> put(K key, V value) {
        Option r = this.get(key);
        this.update(key, value);
        return r;
    }

    public static /* synthetic */ void update$(MapOps $this, Object key, Object value) {
        $this.update(key, value);
    }

    /*
     * WARNING - void declaration
     */
    default public void update(K key, V value) {
        void $plus$eq_elem;
        Growable growable = (Growable)this.coll();
        Tuple2<K, V> tuple2 = new Tuple2<K, V>(key, value);
        if (growable == null) {
            throw null;
        }
        growable.addOne($plus$eq_elem);
    }

    public static /* synthetic */ Option updateWith$(MapOps $this, Object key, Function1 remappingFunction) {
        return $this.updateWith(key, remappingFunction);
    }

    default public Option<V> updateWith(K key, Function1<Option<V>, Option<V>> remappingFunction) {
        Option previousValue = this.get(key);
        Option<V> nextValue = remappingFunction.apply(previousValue);
        Tuple2 tuple2 = new Tuple2(previousValue, nextValue);
        if (!None$.MODULE$.equals(previousValue) || !None$.MODULE$.equals(nextValue)) {
            if (previousValue instanceof Some && None$.MODULE$.equals(nextValue)) {
                this.remove(key);
            } else if (nextValue instanceof Some) {
                Object v = ((Some)nextValue).value();
                this.update(key, v);
            } else {
                throw new MatchError(tuple2);
            }
        }
        return nextValue;
    }

    public static /* synthetic */ Object getOrElseUpdate$(MapOps $this, Object key, Function0 op) {
        return $this.getOrElseUpdate(key, op);
    }

    default public V getOrElseUpdate(K key, Function0<V> op) {
        Option option = this.get(key);
        if (option instanceof Some) {
            return (V)((Some)option).value();
        }
        if (None$.MODULE$.equals(option)) {
            V d = op.apply();
            this.update(key, d);
            return d;
        }
        throw new MatchError(option);
    }

    public static /* synthetic */ Option remove$(MapOps $this, Object key) {
        return $this.remove(key);
    }

    default public Option<V> remove(K key) {
        Option r = this.get(key);
        if (r.isDefined()) {
            this.subtractOne(key);
        }
        return r;
    }

    public static /* synthetic */ void clear$(MapOps $this) {
        $this.clear();
    }

    @Override
    default public void clear() {
        this.keysIterator().foreach((Function1<Object, MapOps> & Serializable)elem -> (MapOps)this.$minus$eq(elem));
    }

    public static /* synthetic */ MapOps clone$(MapOps $this) {
        return $this.clone();
    }

    @Override
    default public C clone() {
        Growable growable = (Growable)this.empty();
        if (growable == null) {
            throw null;
        }
        return (C)((MapOps)growable.addAll(this));
    }

    public static /* synthetic */ MapOps retain$(MapOps $this, Function2 p) {
        return $this.retain(p);
    }

    default public MapOps<K, V, CC, C> retain(Function2<K, V, Object> p) {
        return this.filterInPlace(p);
    }

    public static /* synthetic */ MapOps filterInPlace$(MapOps $this, Function2 p) {
        return $this.filterInPlace(p);
    }

    /*
     * WARNING - void declaration
     */
    default public MapOps<K, V, CC, C> filterInPlace(Function2<K, V, Object> p) {
        if (!this.isEmpty()) {
            if (this instanceof Map) {
                ((Map)this).filterInPlaceImpl(p);
            } else {
                Object[] array = (Object[])this.toArray(ClassTag$.MODULE$.Any());
                int arrayLength = array.length;
                for (int i = 0; i < arrayLength; ++i) {
                    void var7_7;
                    void var6_6;
                    Tuple2 tuple2 = (Tuple2)array[i];
                    if (tuple2 == null) {
                        throw new MatchError((Object)null);
                    }
                    Object k = tuple2._1();
                    Object v = tuple2._2();
                    if (BoxesRunTime.unboxToBoolean(p.apply(var6_6, var7_7))) continue;
                    this.subtractOne(var6_6);
                }
            }
        }
        return this;
    }

    public static /* synthetic */ MapOps transform$(MapOps $this, Function2 f) {
        return $this.transform(f);
    }

    default public MapOps<K, V, CC, C> transform(Function2<K, V, V> f) {
        return this.mapValuesInPlace(f);
    }

    public static /* synthetic */ MapOps mapValuesInPlace$(MapOps $this, Function2 f) {
        return $this.mapValuesInPlace(f);
    }

    /*
     * WARNING - void declaration
     */
    default public MapOps<K, V, CC, C> mapValuesInPlace(Function2<K, V, V> f) {
        if (!this.isEmpty()) {
            if (this instanceof HashMap) {
                ((HashMap)this).mapValuesInPlaceImpl(f);
            } else if (this instanceof Map) {
                ((Map)this).mapValuesInPlaceImpl(f);
            } else {
                Object[] array = (Object[])this.toArray(ClassTag$.MODULE$.Any());
                int arrayLength = array.length;
                for (int i = 0; i < arrayLength; ++i) {
                    void var7_7;
                    void var6_6;
                    Tuple2 tuple2 = (Tuple2)array[i];
                    if (tuple2 == null) {
                        throw new MatchError((Object)null);
                    }
                    Object k = tuple2._1();
                    Object v = tuple2._2();
                    this.update(var6_6, f.apply(var6_6, var7_7));
                }
            }
        }
        return this;
    }

    public static /* synthetic */ MapOps updated$(MapOps $this, Object key, Object value) {
        return $this.updated(key, value);
    }

    default public <V1> CC updated(K key, V1 value) {
        return (CC)((MapOps)this.clone().addOne(new Tuple2<K, V1>(key, value)));
    }

    public static /* synthetic */ int knownSize$(MapOps $this) {
        return $this.knownSize();
    }

    @Override
    default public int knownSize() {
        return -1;
    }

    public static void $init$(MapOps $this) {
    }
}

