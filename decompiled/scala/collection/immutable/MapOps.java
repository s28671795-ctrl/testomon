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
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.MapOps;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.AbstractSet;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.immutable.SetOps;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015faB\t\u0013!\u0003\r\t!\u0007\u0005\u0006\u001f\u0002!\t\u0001\u0015\u0005\u0006)\u00021\t\"\u0016\u0005\u00065\u00021\ta\u0017\u0005\u0006=\u0002!)a\u0018\u0005\u0006=\u0002!\t!\u001a\u0005\u0006s\u0002!\tA\u001f\u0005\u0007\u007f\u0002!)%!\u0001\t\u000f\u0005\u001d\u0001A\"\u0001\u0002\n!9\u0011Q\u0004\u0001\u0005\u0002\u0005}\u0001bBA \u0001\u0011\u0005\u0013\u0011\t\u0005\b\u0003#\u0002A\u0011AA*\u0011\u001d\tI\u0007\u0001C!\u0003W2a!a\u001d\u0001\u0011\u0005U\u0004bBAI\u001b\u0011\u0005\u00111\u0013\u0005\b\u0003/kA\u0011AAM\u0011\u001d\ty*\u0004C\u0001\u0003C\u0013a!T1q\u001fB\u001c(BA\n\u0015\u0003%IW.\\;uC\ndWM\u0003\u0002\u0016-\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003]\tQa]2bY\u0006\u001c\u0001!F\u0003\u001bQIr\u0014h\u0005\u0003\u00017}i\u0005C\u0001\u000f\u001e\u001b\u00051\u0012B\u0001\u0010\u0017\u0005\u0019\te.\u001f*fMB)\u0001%I\u00125q5\tA#\u0003\u0002#)\tY\u0011\n^3sC\ndWm\u00149t!\u0011aBEJ\u0019\n\u0005\u00152\"A\u0002+va2,'\u0007\u0005\u0002(Q1\u0001A!B\u0015\u0001\u0005\u0004Q#!A&\u0012\u0005-r\u0003C\u0001\u000f-\u0013\ticCA\u0004O_RD\u0017N\\4\u0011\u0005qy\u0013B\u0001\u0019\u0017\u0005\r\te.\u001f\t\u0003OI\"aa\r\u0001\u0005\u0006\u0004Q#!\u0001,\u0011\u0005U2T\"\u0001\n\n\u0005]\u0012\"\u0001C%uKJ\f'\r\\3\u0011\u0005\u001dJDA\u0002\u001e\u0001\t\u000b\u00071HA\u0001D#\tYC\b\u0005\u00046\u0001\u0019\nT\b\u000f\t\u0003Oy\"aa\u0010\u0001\u0005\u0006\u0004\u0001%AA\"D+\r\tU\tS\t\u0003W\t\u0003$aQ&\u0011\rU\u0002AiR\u001fK!\t9S\tB\u0003G}\t\u0007!FA\u0001Y!\t9\u0003\n\u0002\u0004J}\u0011\u0015\rA\u000b\u0002\u00023B\u0011qe\u0013\u0003\n\u0019z\n\t\u0011!A\u0003\u0002)\u00121a\u0018\u00132!\u0019\u0001cJJ\u0019>q%\u0011\u0011\u0003F\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003E\u0003\"\u0001\b*\n\u0005M3\"\u0001B+oSR\fAaY8mYV\taKE\u0002Xqe3A\u0001\u0017\u0001\u0001-\naAH]3gS:,W.\u001a8u}A!qE\u0010\u00142\u0003\u001d\u0011X-\\8wK\u0012$\"\u0001\u000f/\t\u000bu\u001b\u0001\u0019\u0001\u0014\u0002\u0007-,\u00170\u0001\u0004%[&tWo\u001d\u000b\u0003q\u0001DQ!\u0018\u0003A\u0002\u0019B#\u0001\u00022\u0011\u0005q\u0019\u0017B\u00013\u0017\u0005\u0019Ig\u000e\\5oKR!\u0001H\u001a5k\u0011\u00159W\u00011\u0001'\u0003\u0011YW-_\u0019\t\u000b%,\u0001\u0019\u0001\u0014\u0002\t-,\u0017P\r\u0005\u0006W\u0016\u0001\r\u0001\\\u0001\u0005W\u0016L8\u000fE\u0002\u001d[\u001aJ!A\u001c\f\u0003\u0015q\u0012X\r]3bi\u0016$g\b\u000b\u0004\u0006aN$ho\u001e\t\u00039EL!A\u001d\f\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003U\f!%V:fA5j\u0003e^5uQ\u0002\ng\u000eI3ya2L7-\u001b;!G>dG.Z2uS>t\u0017!B:j]\u000e,\u0017%\u0001=\u0002\rIr\u0013g\r\u00181\u0003)\u0011X-\\8wK\u0012\fE\u000e\u001c\u000b\u0003qmDQa\u001b\u0004A\u0002q\u00042\u0001I?'\u0013\tqHC\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW-\u0001\u0007%[&tWo\u001d\u0013nS:,8\u000fF\u00029\u0003\u0007AQa[\u0004A\u0002qD#a\u00022\u0002\u000fU\u0004H-\u0019;fIV!\u00111BA\t)\u0019\ti!a\u0006\u0002\u001aA)qE\u0010\u0014\u0002\u0010A\u0019q%!\u0005\u0005\u000f\u0005M\u0001B1\u0001\u0002\u0016\t\u0011a+M\t\u0003c9BQ!\u0018\u0005A\u0002\u0019Bq!a\u0007\t\u0001\u0004\ty!A\u0003wC2,X-A\u0006va\u0012\fG/\u001a3XSRDW\u0003BA\u0011\u0003S!B!a\t\u0002>Q!\u0011QEA\u0016!\u00159cHJA\u0014!\r9\u0013\u0011\u0006\u0003\b\u0003'I!\u0019AA\u000b\u0011\u001d\ti#\u0003a\u0001\u0003_\t\u0011C]3nCB\u0004\u0018N\\4Gk:\u001cG/[8o!\u001da\u0012\u0011GA\u001b\u0003wI1!a\r\u0017\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0003\u001d\u0003o\t\u0014bAA\u001d-\t1q\n\u001d;j_:\u0004R\u0001HA\u001c\u0003OAQ!X\u0005A\u0002\u0019\nQ\u0001\n9mkN,B!a\u0011\u0002JQ!\u0011QIA&!\u00159cHJA$!\r9\u0013\u0011\n\u0003\b\u0003'Q!\u0019AA\u000b\u0011\u001d\tiE\u0003a\u0001\u0003\u001f\n!a\u001b<\u0011\u000bq!c%a\u0012\u0002\u0013Q\u0014\u0018M\\:g_JlW\u0003BA+\u00037\"B!a\u0016\u0002`A)qE\u0010\u0014\u0002ZA\u0019q%a\u0017\u0005\r\u0005u3B1\u0001+\u0005\u00059\u0006bBA1\u0017\u0001\u0007\u00111M\u0001\u0002MB9A$!\u001a'c\u0005e\u0013bAA4-\tIa)\u001e8di&|gNM\u0001\u0007W\u0016L8+\u001a;\u0016\u0005\u00055\u0004\u0003B\u001b\u0002p\u0019J1!!\u001d\u0013\u0005\r\u0019V\r\u001e\u0002\u0010\u00136lW\u000f^1cY\u0016\\U-_*fiN9Q\"a\u001e\u0002~\u0005\u0015\u0005\u0003B\u001b\u0002z\u0019J1!a\u001f\u0013\u0005-\t%m\u001d;sC\u000e$8+\u001a;\u0011\t\u0005}\u0014\u0011Q\u0007\u0002\u0001%\u0019\u00111\u0011(\u0003\u0013\u001d+gnS3z'\u0016$\b\u0003BAD\u0003\u001bk!!!#\u000b\u0007\u0005-E#A\u0004hK:,'/[2\n\t\u0005=\u0015\u0011\u0012\u0002\u0014\t\u00164\u0017-\u001e7u'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u0005U\u0005cAA@\u001b\u0005!\u0011N\\2m)\u0011\ti'a'\t\r\u0005uu\u00021\u0001'\u0003\u0011)G.Z7\u0002\t\u0015D8\r\u001c\u000b\u0005\u0003[\n\u0019\u000b\u0003\u0004\u0002\u001eB\u0001\rA\n")
public interface MapOps<K, V, CC extends MapOps<Object, Object, CC, ?>, C extends MapOps<K, V, CC, C>>
extends scala.collection.MapOps<K, V, CC, C> {
    @Override
    public C coll();

    public C removed(K var1);

    public static /* synthetic */ MapOps $minus$(MapOps $this, Object key) {
        return $this.$minus(key);
    }

    default public C $minus(K key) {
        return this.removed(key);
    }

    public static /* synthetic */ MapOps $minus$(MapOps $this, Object key1, Object key2, Seq keys2) {
        return $this.$minus(key1, key2, keys2);
    }

    default public C $minus(K key1, K key2, Seq<K> keys2) {
        return this.removed(key1).removed(key2).removedAll(keys2);
    }

    public static /* synthetic */ MapOps removedAll$(MapOps $this, IterableOnce keys2) {
        return $this.removedAll(keys2);
    }

    default public C removedAll(IterableOnce<K> keys2) {
        return (C)((MapOps)keys2.iterator().foldLeft(this.coll(), (Function2<MapOps, Object, MapOps> & Serializable)(x$2, x$3) -> x$2.$minus(x$3)));
    }

    public static /* synthetic */ MapOps $minus$minus$(MapOps $this, IterableOnce keys2) {
        return $this.$minus$minus(keys2);
    }

    @Override
    default public C $minus$minus(IterableOnce<K> keys2) {
        return this.removedAll(keys2);
    }

    public <V1> CC updated(K var1, V1 var2);

    public static /* synthetic */ MapOps updatedWith$(MapOps $this, Object key, Function1 remappingFunction) {
        return $this.updatedWith(key, remappingFunction);
    }

    default public <V1> CC updatedWith(K key, Function1<Option<V>, Option<V1>> remappingFunction) {
        Option previousValue = this.get(key);
        Option<V1> option = remappingFunction.apply(previousValue);
        if (None$.MODULE$.equals(option)) {
            if (previousValue == null) {
                throw null;
            }
            if (previousValue.isEmpty()) {
                return (CC)this.coll();
            }
            Object v = previousValue.get();
            return (CC)MapOps.$anonfun$updatedWith$2(this, key, v);
        }
        if (option instanceof Some) {
            Object v;
            Object nextValue = ((Some)option).value();
            if (previousValue == null) {
                throw null;
            }
            if (!previousValue.isEmpty() && MapOps.$anonfun$updatedWith$3(nextValue, v = previousValue.get())) {
                return (CC)this.coll();
            }
            return this.coll().updated(key, nextValue);
        }
        throw new MatchError(option);
    }

    public static /* synthetic */ MapOps $plus$(MapOps $this, Tuple2 kv) {
        return $this.$plus(kv);
    }

    @Override
    default public <V1> CC $plus(Tuple2<K, V1> kv) {
        return this.updated(kv._1(), kv._2());
    }

    public static /* synthetic */ MapOps transform$(MapOps $this, Function2 f) {
        return $this.transform(f);
    }

    default public <W> CC transform(Function2<K, V, W> f) {
        return (CC)((MapOps)this.map((Function1<Tuple2, Tuple2> & Serializable)(Tuple2<K, V> x0$1) -> {
            if (x0$1 != null) {
                Object k = x0$1._1();
                Object v = x0$1._2();
                return new Tuple2(k, f.apply(k, v));
            }
            throw new MatchError((Object)null);
        }));
    }

    public static /* synthetic */ Set keySet$(MapOps $this) {
        return $this.keySet();
    }

    @Override
    default public Set<K> keySet() {
        return new ImmutableKeySet();
    }

    public static /* synthetic */ MapOps $anonfun$updatedWith$1(MapOps $this) {
        return $this.coll();
    }

    public static /* synthetic */ MapOps $anonfun$updatedWith$2(MapOps $this, Object key$1, Object x$4) {
        return $this.removed(key$1).coll();
    }

    public static /* synthetic */ boolean $anonfun$updatedWith$3(Object nextValue$1, Object x$5) {
        return x$5 == nextValue$1;
    }

    public static void $init$(MapOps $this) {
    }

    public static /* synthetic */ Object $anonfun$updatedWith$3$adapted(Object nextValue$1, Object x$5) {
        return BoxesRunTime.boxToBoolean(MapOps.$anonfun$updatedWith$3(nextValue$1, x$5));
    }

    public class ImmutableKeySet
    extends AbstractSet<K>
    implements MapOps.GenKeySet,
    DefaultSerializable {
        @Override
        public Object writeReplace() {
            return DefaultSerializable.writeReplace$(this);
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
        public Set<K> incl(K elem) {
            if (this.contains(elem)) {
                return this;
            }
            scala.collection.SetOps setOps = (scala.collection.SetOps)this.empty();
            if (setOps == null) {
                throw null;
            }
            SetOps setOps2 = (SetOps)setOps.concat(this);
            if (setOps2 == null) {
                throw null;
            }
            return (Set)setOps2.incl(elem);
        }

        @Override
        public Set<K> excl(K elem) {
            if (this.contains(elem)) {
                scala.collection.SetOps setOps = (scala.collection.SetOps)this.empty();
                if (setOps == null) {
                    throw null;
                }
                SetOps setOps2 = (SetOps)setOps.concat(this);
                if (setOps2 == null) {
                    throw null;
                }
                return (Set)setOps2.excl(elem);
            }
            return this;
        }

        public /* synthetic */ MapOps scala$collection$immutable$MapOps$ImmutableKeySet$$$outer() {
            return MapOps.this;
        }

        @Override
        public /* synthetic */ scala.collection.MapOps scala$collection$MapOps$GenKeySet$$$outer() {
            return this.scala$collection$immutable$MapOps$ImmutableKeySet$$$outer();
        }

        public ImmutableKeySet() {
            if (MapOps.this == null) {
                throw null;
            }
        }
    }
}

